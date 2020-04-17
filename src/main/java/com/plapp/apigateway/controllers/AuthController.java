package com.plapp.apigateway.controllers;

import com.plapp.apigateway.saga.UserLoginSagaOrchestrator;
import com.plapp.apigateway.saga.orchestration.SagaExecutionException;
import com.plapp.apigateway.saga.UserCreationSagaOrchestrator;
import com.plapp.apigateway.services.microservices.AuthenticationService;
import com.plapp.apigateway.services.microservices.AuthorizationService;
import com.plapp.apigateway.services.SessionTokenService;
import com.plapp.entities.auth.UserCredentials;
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

    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final SessionTokenService sessionTokenService;

    private UserCreationSagaOrchestrator userCreationSagaOrchestrator;
    private UserLoginSagaOrchestrator userLoginSagaOrchestrator;

    public AuthController(AuthenticationService authenticationService,
                          AuthorizationService authorizationService,
                          SessionTokenService sessionTokenService) {

        this.sessionTokenService = sessionTokenService;

        userCreationSagaOrchestrator = new UserCreationSagaOrchestrator(
                authenticationService,
                authorizationService,
                sessionTokenService
        );

        userLoginSagaOrchestrator = new UserLoginSagaOrchestrator(
                authenticationService,
                sessionTokenService
        );
    }
    @CrossOrigin
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserCredentials credentials) throws SagaExecutionException, Throwable {
       return new ApiResponse<>(userLoginSagaOrchestrator.authenticateUser(credentials));
    }

    @CrossOrigin
    @PostMapping("/signup")
    public ApiResponse<String> signup(@RequestBody UserCredentials credentials) throws SagaExecutionException, Throwable {
        return new ApiResponse<>(userCreationSagaOrchestrator.createUser(credentials));
    }


    @CrossOrigin
    @GetMapping("/logout")
    public ApiResponse<?> logout(){
        return new ApiResponse<>(true);
    }


}
