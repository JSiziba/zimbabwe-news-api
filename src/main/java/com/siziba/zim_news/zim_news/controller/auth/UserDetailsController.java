package com.siziba.zim_news.zim_news.controller.auth;


import com.siziba.zim_news.zim_news.dto.ApiResponse;
import com.siziba.zim_news.zim_news.dto.auth.*;
import com.siziba.zim_news.zim_news.service.auth.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserDetailsController {
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @GetMapping(value = {"/details", "/verify-token"})
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getUserDetails(Principal principal){
        log.info("UserDetailsController/getUserDetails");
        UserDetailsResponse userDetailsResponse = applicationUserDetailsService.getUserDetails(principal);
        return ResponseEntity.ok(ApiResponse.<UserDetailsResponse>builder()
                .data(userDetailsResponse)
                .success(true)
                .message("User details fetched successfully")
                .build());
    }
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<?>> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest,
            Principal principal
    ){
        log.info("UserDetailsController/changePassword");
        boolean userDetailsResponse = applicationUserDetailsService.changePassword(changePasswordRequest, principal);

        return ResponseEntity.ok(ApiResponse.<UserDetailsResponse>builder()
                .success(userDetailsResponse)
                .message("Password changed successfully")
                .build());
    }
    @PostMapping("/change-email")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> changeEmail(
            @RequestBody ChangeEmailRequest changeEmailRequest,
            Principal principal
    ){
        log.info("UserDetailsController/changeEmail");
        AuthenticationResponse authenticationResponse = applicationUserDetailsService.changeEmailAddress(changeEmailRequest, principal);

        return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                .success(true)
                .data(authenticationResponse)
                .message("Email changed successfully")
                .build());
    }
    @PostMapping("/change-username")
    public ResponseEntity<ApiResponse<UserDetailsResponse>> changeUsername(
            @RequestBody ChangeNameRequest changeUsernameRequest,
            Principal principal
    ){
        log.info("UserDetailsController/changeUsername");
        UserDetailsResponse userDetailsResponse = applicationUserDetailsService.changeUserName(changeUsernameRequest, principal);

        return ResponseEntity.ok(ApiResponse.<UserDetailsResponse>builder()
                .success(true)
                .data(userDetailsResponse)
                .message("Username changed successfully")
                .build());
    }
}
