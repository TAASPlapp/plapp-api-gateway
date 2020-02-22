package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private AuthenticationService authenticationService;

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserCredentials credentials) {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        ApiResponse responseBody = new ApiResponse();

        try {
            ApiResponse authenticationResponse = authenticationService.authenticateUser(credentials);
            if (!authenticationResponse.getSuccess())
                return responseBuilder.body(authenticationResponse);

            String token = authenticationResponse.getMessage();
            httpHeaders.add(HEADER_STRING, TOKEN_PREFIX + token);

        } catch (Exception e) {
            responseBody = new ApiResponse(false, e.getMessage());
            return responseBuilder.body(responseBody);
        }

        return responseBuilder
                .headers(httpHeaders)
                .body(responseBody);
    }

    @CrossOrigin
    @PostMapping("/signup")
    public ApiResponse signup(@RequestBody UserCredentials credentials) {
        try {
            return authenticationService.registerUser(credentials);
        } catch (Exception e) {
            return new ApiResponse(false, e.getMessage());
        }
    }
}
