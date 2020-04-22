package com.plapp.apigateway.saga.greenhouse;

import com.plapp.apigateway.saga.orchestration.*;
import com.plapp.apigateway.services.SessionTokenService;
import com.plapp.apigateway.services.microservices.Authorities;
import com.plapp.apigateway.services.microservices.AuthorizationService;
import com.plapp.apigateway.services.microservices.GardenerService;
import com.plapp.apigateway.services.microservices.GreenhouseService;
import com.plapp.authorization.ResourceAuthority;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.schedules.ScheduleAction;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlantAddSagaOrchestrator extends SagaOrchestrator {
    private static Logger logger = LoggerFactory.getLogger(com.plapp.apigateway.saga.schedule.ScheduleActionAddSagaOrchestrator.class);

    private final GreenhouseService greenhouseService;
    private final AuthorizationService authorizationService;
    private final SessionTokenService sessionTokenService;

    private ResourceAuthority updateScheduleAuthorization(Plant plant) {
        ResourceAuthority authority = authorizationService.updateAuthorization(Authorities.GREENHOUSE_PLANT, plant.getId());
        sessionTokenService.fetchAndUpdateJwt();
        return authority;
    }

    private Storyboard createDefaultStoryboard(Plant plant) {
        Storyboard storyboard = new Storyboard();
        storyboard.setPlant(plant);
        return storyboard;
    }

    private ResourceAuthority updateStoryboardAuthorization(Storyboard storyboard) {
        ResourceAuthority authority = authorizationService.updateAuthorization(Authorities.GREENHOUSE_STORYBOARD, storyboard.getId());
        sessionTokenService.fetchAndUpdateJwt();
        return authority;
    }

    @Override
    protected SagaDefinition buildSaga(SagaDefinitionBuilder builder) {
        logger.info("Building login saga orchestrator");

        return builder
                    .invoke(greenhouseService::addPlant).withArg("plant").saveTo("plant")
                .step()
                    .invoke(this::updateScheduleAuthorization).withArg("plant")
                .step()
                    .invoke(this::createDefaultStoryboard).withArg("plant").saveTo("storyboard")
                .step()
                    .invoke(greenhouseService::createStoryboard).withArg("storyboard").saveTo("storyboard")
                .step()
                    .invoke(this::updateStoryboardAuthorization).withArg("storyboard")
                .build();
    }

    public Plant addPlant(Plant plant) throws SagaExecutionException, Throwable {
        SagaExecutionEngine.SagaArgumentResolver resolver = getExecutor()
                .withArg("plant", plant)
                .run()
                .collect();
        return resolver.get("plant");
    }
}

