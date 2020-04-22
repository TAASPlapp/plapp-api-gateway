package com.plapp.apigateway.saga;

import com.plapp.apigateway.saga.orchestration.*;
import com.plapp.apigateway.services.SessionTokenService;
import com.plapp.apigateway.services.config.SessionRequestContext;
import com.plapp.apigateway.services.microservices.Authorities;
import com.plapp.apigateway.services.microservices.AuthorizationService;
import com.plapp.apigateway.services.microservices.GardenerService;
import com.plapp.authorization.ResourceAuthority;
import com.plapp.entities.schedules.ScheduleAction;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

@RequiredArgsConstructor
public class ScheduleActionAddSagaOrchestrator extends SagaOrchestrator {
    private static Logger logger = LoggerFactory.getLogger(ScheduleActionAddSagaOrchestrator.class);

    private final GardenerService gardenerService;
    private final AuthorizationService authorizationService;
    private final SessionTokenService sessionTokenService;

    private ResourceAuthority updateScheduleAuthorization(ScheduleAction scheduleAction) {
        ResourceAuthority authority = authorizationService.updateAuthorization(Authorities.GARDENER_SCHEDULE, scheduleAction.getScheduleActionId());
        sessionTokenService.fetchAndUpdateJwt();
        return authority;
    }

    @Override
    protected SagaDefinition buildSaga(SagaDefinitionBuilder builder) {
        logger.info("Building login saga orchestrator");

        return builder
                    .invoke(gardenerService::addScheduleAction).withArg("scheduleAction").saveTo("scheduleAction")
                .step()
                    .invoke(this::updateScheduleAuthorization).withArg("scheduleAction")
                .build();
    }

    public ScheduleAction addScheduleAction(ScheduleAction scheduleAction) throws SagaExecutionException, Throwable {
        SagaExecutionEngine.SagaArgumentResolver resolver = getExecutor()
                .withArg("scheduleAction", scheduleAction)
                .run()
                .collect();
        return resolver.get("scheduleAction");
    }
}
