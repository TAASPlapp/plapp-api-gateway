package com.plapp.apigateway.saga;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.apigateway.services.AuthorizationService;
import com.plapp.apigateway.services.SocialService;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.social.UserDetails;
import lombok.*;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@RequiredArgsConstructor
public class UserCreationSagaOrchestrator extends SagaOrchestrator {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final SocialService socialService;

    Logger logger = LoggerFactory.getLogger(UserCreationSagaOrchestrator.class);

    @Override
    public SagaDefinition buildSaga(SagaDefinitionBuilder builder) {
        return null;
    }

    public void createUser(UserCredentials credentials, UserDetails details) {
        /*
            1. credentials = authenticationService.registerUser() - compensate (return error response)
            2. authorizationService.addAuthorizations(all) - compensate (delete credentials)
            3. socialService.setUserDetails(details) - compensate (delete authorizations)
         */
        /*SagaDefinitionBuilder builder = new SagaDefinitionBuilder();
        SagaDefinition sagaDefinition = builder
                .<UserCredentials, UserCredentials>step()
                    .invoke(authenticationService::registerUser).withArg(credentials)
                    .withCompensation(authenticationService::deleteUser)
                .<UserDetails, UserDetails>step()
                    .invoke(socialService::setUserDetails).withArg(details)
                .build();

        SagaExecutionEngine executor = new SagaExecutionEngine();
        try {
            executor.run(sagaDefinition);
        } catch (SagaExecutionException e){
            logger.error(e.getMessage());
        }*/
                /*

                    .invoke(authenticationService.registerUser)
                    .withRollback(authenticationService.deleteUser)
                .step()
                    .invoke(authorizationService.addAuthorization)
                    .withRollback(authorizationService.removeAuthorization)
                .step()
                    .invoke(socialService.setUserDetails)
                .build();*/
        /*
            SagaTransaction transaction =

         */
    }
}
