package com.plapp.apigateway.saga;

import com.plapp.apigateway.saga.orchestration.*;
import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.apigateway.services.AuthorizationService;
import com.plapp.apigateway.services.SessionTokenService;
import com.plapp.apigateway.services.SocialService;
import com.plapp.authorization.ResourceAuthority;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.social.UserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserCreationSagaOrchestrator extends SagaOrchestrator {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final SessionTokenService sessionTokenService;

    private static Logger logger = LoggerFactory.getLogger(UserCreationSagaOrchestrator.class);

    private List<ResourceAuthority> createDefaultAuthorities(UserCredentials userCredentials) {
        return new ArrayList<ResourceAuthority>() {{
            ResourceAuthority resourceAuthority = new ResourceAuthority(
                    "/social/user/([0-9]+)/((\\badd\\b)|(\\bupdate\\b)|(\\bdelete\\b))",
                    userCredentials.getId()
            );
            resourceAuthority.addValue(userCredentials.getId());
            add(resourceAuthority);
        }};
    }

    private String setPrincipalJwt(String jwt) {
        String sessionToken = sessionTokenService.generateSessionToken(jwt);

        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        logger.info("Setting jwt attribute for request scope");
        attributes.setAttribute("sessionToken", sessionToken, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(attributes);

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
                    .invoke(authenticationService::authenticateUser).withArg("inputCredentials").saveTo("jwt")

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

                .step()
                    .invoke(this::setPrincipalJwt).withArg("jwt")

                .build();
    }

    public String createUser(UserCredentials credentials) throws SagaExecutionException, Throwable {
        SagaExecutionEngine.SagaArgumentResolver resolver = getExecutor()
                .withArg("inputCredentials", credentials)
                .run()
                .collect();
        return resolver.get("jwt");
    }
}
