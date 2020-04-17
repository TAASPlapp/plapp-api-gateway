package com.plapp.apigateway.saga;

import com.plapp.apigateway.saga.orchestration.*;
import com.plapp.apigateway.services.config.SessionRequestContext;
import com.plapp.apigateway.services.microservices.AuthenticationService;
import com.plapp.apigateway.services.microservices.Authorities;
import com.plapp.apigateway.services.microservices.AuthorizationService;
import com.plapp.apigateway.services.SessionTokenService;
import com.plapp.authorization.ResourceAuthority;
import com.plapp.entities.auth.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class UserCreationSagaOrchestrator extends SagaOrchestrator {

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final SessionTokenService sessionTokenService;

    private static Logger logger = LoggerFactory.getLogger(UserCreationSagaOrchestrator.class);

    private List<ResourceAuthority> createDefaultAuthorities(UserCredentials userCredentials) {
        return new ArrayList<ResourceAuthority>() {{
            ResourceAuthority socialUserAuthority = new ResourceAuthority(
                    Authorities.SOCIAL_USER,
                    userCredentials.getId()
            );
            socialUserAuthority.addValue(userCredentials.getId());
            add(socialUserAuthority);

            for(String urlRegex : Arrays.asList(Authorities.asArray).subList(1, Authorities.asArray.length)) {
                add(new ResourceAuthority(
                        urlRegex,
                        userCredentials.getId()
                ));
            }
        }};
    }

    private String generateSessionToken(String jwt) {
        String sessionToken = sessionTokenService.generateSessionToken(jwt);
        SessionRequestContext.setSessionToken(sessionToken);

        return sessionToken;
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
                    .invoke(this::generateSessionToken).withArg("jwt").saveTo("sessionToken")
                    .withCompensation(sessionTokenService::deleteSession)

                .step()
                    .invoke(this::createDefaultAuthorities).withArg("savedCredentials").saveTo("defaultAuthorities")

                .step()
                    .invoke(authorizationService::addAuthorizations).withArg("defaultAuthorities")
                    .withCompensation(authorizationService::removeAuthorizations)

                .step()
                    .invoke(authorizationService::generateUpdatedJwt).withArg("jwt").saveTo("jwt")

                .step()
                    .invoke(sessionTokenService::updateJwt).withArg("jwt")

                .build();
    }

    public String createUser(UserCredentials credentials) throws SagaExecutionException, Throwable {
        SagaExecutionEngine.SagaArgumentResolver resolver = getExecutor()
                .withArg("inputCredentials", credentials)
                .run()
                .collect();
        return resolver.get("sessionToken");
    }
}
