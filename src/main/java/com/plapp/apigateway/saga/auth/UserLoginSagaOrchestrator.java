package com.plapp.apigateway.saga.auth;

import com.plapp.apigateway.saga.orchestration.*;
import com.plapp.apigateway.services.SessionTokenService;
import com.plapp.apigateway.services.microservices.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLoginSagaOrchestrator extends SagaOrchestrator {
    private static Logger logger = LoggerFactory.getLogger(UserLoginSagaOrchestrator.class);

    private final AuthenticationService authenticationService;
    private final SessionTokenService sessionTokenService;

    @Override
    protected SagaDefinition buildSaga(SagaDefinitionBuilder builder) {
        logger.info("Building login saga orchestrator");

        return builder
                    .invoke(authenticationService::authenticateUser).withArg("inputCredentials").saveTo("jwt")
                .step()
                    .invoke(sessionTokenService::generateSessionToken).withArg("jwt").saveTo("sessionToken")
                .step()
                    .invoke(sessionTokenService::updateJwt).withArg("jwt")
                .build();
    }

    public String authenticateUser(UserCredentials credentials) throws SagaExecutionException, Throwable {
        SagaExecutionEngine.SagaArgumentResolver resolver = getExecutor()
                .withArg("inputCredentials", credentials)
                .run()
                .collect();
        return resolver.get("sessionToken");
    }
}
