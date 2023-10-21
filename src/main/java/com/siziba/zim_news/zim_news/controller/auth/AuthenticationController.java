package com.siziba.zim_news.zim_news.controller.auth;


import com.siziba.zim_news.zim_news.dto.ApiResponse;
import com.siziba.zim_news.zim_news.dto.auth.AuthenticationRequest;
import com.siziba.zim_news.zim_news.dto.auth.AuthenticationResponse;
import com.siziba.zim_news.zim_news.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(
//            @RequestBody RegistrationRequest registrationRequest
//    ){
//        log.info("Registering user");
//
//        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
//                .data(authenticationService.register(registrationRequest))
//                .success(true)
//                .message("User registered successfully")
//                .build());
//    }
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(
            @RequestBody AuthenticationRequest registrationRequest
    ){
        log.info("AuthenticationController/existsByEmail");

        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                .data(authenticationService.authenticate(registrationRequest))
                .success(true)
                .message("User authenticated successfully")
                .build());
    }
}
