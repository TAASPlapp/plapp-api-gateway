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
    //private final SocialService socialService;

    private static Logger logger = LoggerFactory.getLogger(UserCreationSagaOrchestrator.class);

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
    protected SagaDefinition buildSaga(SagaDefinitionBuilder builder) {
        logger.info("Building user creation saga");
        System.out.println("Builder: " + builder);

        return builder
                    .invoke(authenticationService::registerUser).withArg("inputCredentials").saveTo("savedCredentials")
                    .withCompensation(authenticationService::deleteUser)

                .step()
                    .invoke(authenticationService::authenticateUser).withArg("savedCredentials").saveTo("jwt")

                .step()
                    .invoke(this::setPrincipalJwt).withArg("jwt")
                    .withCompensation(this::unsetJwt)

                .step()
                    .invoke(this::createDefaultAuthorities).withArg("savedCredentials").saveTo("defaultAuthorities")

                .step()
                    .invoke(authorizationService::addAuthorizations).withArg("defaultAuthorities")
                    .withCompensation(authorizationService::removeAuthorizations)

                .step()
                    .invoke(authorizationService::generateUpdatedJwt).withArg("jwt").saveTo("jwt")

                //.step()
                //    .invoke(socialService::setUserDetails).withArg("inputDetails").saveTo("savedDetails")
                .build();
    }

    public UserCredentials createUser(UserCredentials credentials) throws SagaExecutionException {
            SagaExecutionEngine.SagaArgumentResolver resolver = getExecutor()
                    .withArg("inputCredentials", credentials)
                    //.withArg("inputDetails", details)
                    .run()
                    .collect();
            return resolver.get("savedCredentials");
    }
}
