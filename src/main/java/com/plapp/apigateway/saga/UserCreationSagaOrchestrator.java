package com.plapp.apigateway.saga;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.apigateway.services.AuthorizationService;
import com.plapp.apigateway.services.SocialService;
import com.plapp.authorization.ResourceAuthority;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.social.UserDetails;
import lombok.*;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@RequiredArgsConstructor
public class UserCreationSagaOrchestrator extends SagaOrchestrator {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final SocialService socialService;

    Logger logger = LoggerFactory.getLogger(UserCreationSagaOrchestrator.class);

    private List<ResourceAuthority> createDefaultAuthorities(UserCredentials userCredentials) {
        return new ArrayList<ResourceAuthority>() {{
            add(new ResourceAuthority(
                    "/social/user/([0-9]+)/((\badd\b)|(\bupdate\b)|(\bdelete\b)",
                    userCredentials.getId()
            ));
        }};
    }

    private String setPrincipalJwt(String jwt) {
        return jwt;
    }

    private void unsetJwt(String jwt) {

    }

    @Override
    public SagaDefinition buildSaga(SagaDefinitionBuilder builder) {
        return builder
                .<UserCredentials, UserCredentials>step()
                    .invoke(authenticationService::registerUser).withArg("inputCredentials").saveTo("savedCredentials")
                    .withCompensation(authenticationService::deleteUser)

                .<String, UserCredentials>step()
                    .invoke(authenticationService::authenticateUser).withArg("savedCredentials").saveTo("jwt")

                .<String, String>step()
                    .invoke(this::setPrincipalJwt).withArg("jwt")
                    .withCompensation(this::unsetJwt)

                .<List<ResourceAuthority>, UserCredentials>step()
                    .invoke(this::createDefaultAuthorities).withArg("savedCredentials").saveTo("defaultAuthorities")

                .<List<ResourceAuthority>, List<ResourceAuthority>>step()
                    .invoke(authorizationService::addAuthorizations).withArg("defaultAuthorities")
                    .withCompensation(authorizationService::removeAuthorizations)

                .<String, UserCredentials>step()
                    .invoke(authorizationService::generateUpdatedJwt).withArg("savedCredentials").saveTo("jwt")

                .<UserDetails, UserDetails>step()
                    .invoke(socialService::setUserDetails).withArg("inputDetails").saveTo("savedDetails")
                .build();
    }

    public void createUser(UserCredentials credentials, UserDetails details) throws SagaExecutionException {
            SagaExecutionEngine.SagaArgumentResolver resolver = getExecutor()
                    .withArg("inputCredentials", credentials)
                    .withArg("inputDetails", details)
                    .run()
                    .collect();
    }
}
