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
import com.plapp.entities.greenhouse.StoryboardItem;
import com.plapp.entities.schedules.ScheduleAction;
import lombok.RequiredArgsConstructor;
import org.lists.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StoryboardItemCreationSagaOrchestrator extends SagaOrchestrator {
    private static Logger logger = LoggerFactory.getLogger(StoryboardItemCreationSagaOrchestrator.class);

    private final GreenhouseService greenhouseService;
    private final AuthorizationService authorizationService;
    private final GardenerService gardenerService;
    private final SessionTokenService sessionTokenService;

    private ResourceAuthority updateStoryboardItemAuthorization(StoryboardItem storyboardItem) {
        ResourceAuthority authority = authorizationService.updateAuthorization(Authorities.GREENHOUSE_STORYBOARD_ITEM, storyboardItem.getId());
        sessionTokenService.fetchAndUpdateJwt();
        return authority;
    }

    private Void requestDiagnosis(StoryboardItem item) {
        List<Storyboard> storyboardList = greenhouseService.getStoryboards();
        List<Storyboard> match = Lists.filter(storyboardList, storyboard -> storyboard.getId() == item.getStoryboardId());
        if (match.size() > 0)
            gardenerService.getDiagnosisAsync(item.getImage(), match.get(0).getPlant().getId());

        return null;
    }

    @Override
    protected SagaDefinition buildSaga(SagaDefinitionBuilder builder) {
        logger.info("Building saga orchestrator");

        return builder
                    .invoke(greenhouseService::addStoryboardItem).withArg("item").saveTo("item")
                .step()
                    .invoke(this::updateStoryboardItemAuthorization).withArg("item")
                .step()
                    .invoke(this::requestDiagnosis).withArg("item")
                .build();
    }

    public StoryboardItem addItem(StoryboardItem item) throws SagaExecutionException, Throwable {
        SagaExecutionEngine.SagaArgumentResolver resolver = getExecutor()
                .withArg("item", item)
                .run()
                .collect();
        return resolver.get("item");
    }
}

