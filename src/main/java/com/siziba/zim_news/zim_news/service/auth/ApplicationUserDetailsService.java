package com.siziba.zim_news.zim_news.service.auth;

import com.siziba.zim_news.zim_news.dto.auth.*;
import com.siziba.zim_news.zim_news.entity.ApplicationUser;
import com.siziba.zim_news.zim_news.exception.CustomServiceException;
import com.siziba.zim_news.zim_news.library.CommonFunctions;
import com.siziba.zim_news.zim_news.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationUserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse changeEmailAddress(ChangeEmailRequest changeEmailRequest, Principal principal) {
        log.info("ApplicationUserDetailsService/changeEmailAddress");
        var user = getUserDetailsByPrincipal(principal);
        validateChangeEmailRequest(changeEmailRequest, user);
        user.setEmail(CommonFunctions.sanitizeEmail(changeEmailRequest.getNewEmail()));
        applicationUserRepository.save(user);
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
    public boolean changePassword(ChangePasswordRequest changePasswordRequest, Principal principal) {
        log.info("ApplicationUserDetailsService/changePassword");
        var user = getUserDetailsByPrincipal(principal);
        validateChangePasswordRequest(changePasswordRequest, user);
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        applicationUserRepository.save(user);
        return true;
    }
    public UserDetailsResponse changeUserName(ChangeNameRequest changeNameRequest, Principal principal) {
        log.info("ApplicationUserDetailsService/changeUserName");
        var user = getUserDetailsByPrincipal(principal);
        validateChangeNameRequest(changeNameRequest, user);
        user.setFirstName(changeNameRequest.getFirstName());
        user.setMiddleNames(changeNameRequest.getMiddleNames());
        user.setLastName(changeNameRequest.getLastName());
        applicationUserRepository.save(user);
        return UserDetailsResponse.builder()
                .id(user.getId())
                .firstName(CommonFunctions.sanitizeName(changeNameRequest.getFirstName()))
                .lastName(CommonFunctions.sanitizeName(changeNameRequest.getLastName()))
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    private void validateChangeNameRequest(ChangeNameRequest changeNameRequest, ApplicationUser user) {
        log.info("ApplicationUserDetailsService/validateChangeNameRequest");
        CommonFunctions.validateName(changeNameRequest.getFirstName());
        if (changeNameRequest.getMiddleNames() != null) {
            CommonFunctions.validateName(changeNameRequest.getMiddleNames());
        }
        CommonFunctions.validateName(changeNameRequest.getLastName());
        if (user.getFirstName().equals(changeNameRequest.getFirstName()) && user.getLastName().equals(changeNameRequest.getLastName()) && user.getMiddleNames().equals(changeNameRequest.getMiddleNames())) {
            throw CustomServiceException.builder()
                    .message("New name cannot be the same as the old one")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    private void validateChangePasswordRequest(ChangePasswordRequest changePasswordRequest, ApplicationUser user) {
        log.info("ApplicationUserDetailsService/validateChangePasswordRequest");
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw CustomServiceException.builder()
                    .message("Current password is incorrect")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        CommonFunctions.validatePassword(changePasswordRequest.getNewPassword());
        if (changePasswordRequest.getCurrentPassword().equals(changePasswordRequest.getNewPassword())) {
            throw CustomServiceException.builder()
                    .message("New password cannot be the same as the old one")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
    private void validateChangeEmailRequest(ChangeEmailRequest changeEmailRequest, ApplicationUser user) {
        log.info("ApplicationUserDetailsService/validateChangeEmailRequest");
        if (!passwordEncoder.matches(changeEmailRequest.getPassword(), user.getPassword())) {
            throw CustomServiceException.builder()
                    .message("Password is incorrect")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        CommonFunctions.validateEmail(changeEmailRequest.getNewEmail());
        if (!user.getEmail().equals(changeEmailRequest.getCurrentEmail())) {
            throw CustomServiceException.builder()
                    .message("Current email address is incorrect")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (user.getEmail().equals(changeEmailRequest.getNewEmail())) {
            throw CustomServiceException.builder()
                    .message("New email address cannot be the same as the old one")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (existsByEmail(changeEmailRequest.getNewEmail())) {
            throw CustomServiceException.builder()
                    .message("Email address already exists")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
    public boolean existsByEmail(String email) {
        log.info("ApplicationUserDetailsService/existsByEmail");
        return applicationUserRepository.existsByEmail(email);
    }
    public UserDetailsResponse getUserDetails(Principal principal) {
        log.info("ApplicationUserDetailsService/getUserDetails");
        var user = getUserDetailsByPrincipal(principal);
        return new UserDetailsResponse(user);
    }
    private ApplicationUser getUserDetailsByPrincipal(Principal principal) {
        log.info("ApplicationUserDetailsService/getUserDetailsByPrincipal");
        String email = principal.getName();
        return applicationUserRepository.findByEmail(email)
                .orElseThrow(() ->  CustomServiceException.builder()
                        .message("An authenticated user is required to perform this action")
                        .errorCode(HttpStatus.FORBIDDEN)
                        .build());
    }
}
