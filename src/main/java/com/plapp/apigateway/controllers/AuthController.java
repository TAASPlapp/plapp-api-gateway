package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.AuthenticationService;
import com.plapp.entities.auth.UserCredentials;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationService authenticationService;

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
                    .body(new ApiResponse<String>(token));

        } catch (Exception e) {
            return responseBuilder.body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @CrossOrigin
    @PostMapping("/signup")
    public ApiResponse<UserCredentials> signup(@RequestBody UserCredentials credentials) {
        try {
            return new ApiResponse<>(authenticationService.registerUser(credentials));
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage());
        }
    }


    //TODO: da implementare
    @CrossOrigin
    @GetMapping("/logout")
    public ApiResponse logout(){
        return new ApiResponse("logout succeded");
    }


}
