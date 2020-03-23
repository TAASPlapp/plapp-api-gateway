package com.plapp.apigateway.controllers;

import com.plapp.apigateway.saga.SagaExecutionException;
import com.plapp.apigateway.saga.UserCreationSagaOrchestrator;
import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.apigateway.services.AuthorizationService;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sun.net.www.http.HttpClient;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;

    private UserCreationSagaOrchestrator userCreationSagaOrchestrator;

    @ControllerAdvice
    public static class AuthControllerAdvice extends ResponseEntityExceptionHandler {

        @ExceptionHandler({HttpClientErrorException.class, IllegalArgumentException.class})
        public ResponseEntity<Object> handle(RuntimeException e, WebRequest request) {
            e.printStackTrace();
            return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }

        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ExceptionHandler({SagaExecutionException.class})
        public void handleSagaException(SagaExecutionException e) {
            e.printStackTrace();
        }
    }

    public AuthController(AuthenticationService authenticationService,
                          AuthorizationService authorizationService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;

        userCreationSagaOrchestrator = new UserCreationSagaOrchestrator(
                authenticationService,
                authorizationService
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
    public ApiResponse<UserCredentials> signup(@RequestBody UserCredentials credentials) throws SagaExecutionException {
        return new ApiResponse<>(userCreationSagaOrchestrator.createUser(credentials));
    }


    //TODO: da implementare
    @CrossOrigin
    @GetMapping("/logout")
    public ApiResponse<?> logout(){
        return new ApiResponse<>("logout succeded");
    }


}
