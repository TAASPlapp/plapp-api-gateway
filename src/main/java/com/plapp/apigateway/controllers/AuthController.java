package com.plapp.apigateway.controllers;

import com.plapp.apigateway.saga.orchestration.SagaExecutionException;
import com.plapp.apigateway.saga.UserCreationSagaOrchestrator;
import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.apigateway.services.AuthorizationService;
import com.plapp.apigateway.services.SessionTokenService;
import com.plapp.apigateway.services.SocialService;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.social.UserDetails;
import com.plapp.entities.utils.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final SessionTokenService sessionTokenService;

    private UserCreationSagaOrchestrator userCreationSagaOrchestrator;

    @ControllerAdvice
    public static class AuthControllerAdvice extends ResponseEntityExceptionHandler {

        @ExceptionHandler({HttpClientErrorException.class})
        public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException e, WebRequest request) {
            e.printStackTrace();
            return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), e.getStatusCode(), request);
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler({IllegalArgumentException.class})
        public void handle(RuntimeException e, WebRequest request) {
            e.printStackTrace();
        }

        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ExceptionHandler({SagaExecutionException.class})
        public void handleSagaException(SagaExecutionException e) {
            e.printStackTrace();
        }
    }

    public AuthController(AuthenticationService authenticationService,
                          AuthorizationService authorizationService,
                          SessionTokenService sessionTokenService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
        this.sessionTokenService = sessionTokenService;

        userCreationSagaOrchestrator = new UserCreationSagaOrchestrator(
                authenticationService,
                authorizationService,
                sessionTokenService
        );
    }
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserCredentials credentials) {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();

        try {
            String token = authenticationService.authenticateUser(credentials);
            httpHeaders.add(HEADER_STRING, TOKEN_PREFIX + token);

            return responseBuilder
                    .headers(httpHeaders)
                    .body(new ApiResponse<>(token));

        } catch (Exception e) {
            return responseBuilder.body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @CrossOrigin
    @PostMapping("/signup")
    public ApiResponse<String> signup(@RequestBody UserCredentials credentials) throws SagaExecutionException, Throwable {
        return new ApiResponse<>(userCreationSagaOrchestrator.createUser(credentials));
    }


    //TODO: da implementare
    @CrossOrigin
    @GetMapping("/logout")
    public ApiResponse<?> logout(){
        return new ApiResponse<>("logout succeded");
    }


}
