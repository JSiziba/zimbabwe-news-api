package com.siziba.zim_news.zim_news.service.auth;

import com.siziba.zim_news.zim_news.dto.auth.AuthenticationRequest;
import com.siziba.zim_news.zim_news.dto.auth.AuthenticationResponse;
import com.siziba.zim_news.zim_news.dto.auth.RegistrationRequest;
import com.siziba.zim_news.zim_news.entity.ApplicationUser;
import com.siziba.zim_news.zim_news.exception.CustomServiceException;
import com.siziba.zim_news.zim_news.library.CommonFunctions;
import com.siziba.zim_news.zim_news.repository.ApplicationUserRepository;
import com.siziba.zim_news.zim_news.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ApplicationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        log.info("AuthenticationService/register");
        this.checkIfUserExists(registrationRequest.getEmail());
        CommonFunctions.validateEmail(registrationRequest.getEmail());
        CommonFunctions.validatePassword(registrationRequest.getPassword());
        CommonFunctions.validateName(registrationRequest.getFirstName());
        CommonFunctions.validateName(registrationRequest.getLastName());

        var user = ApplicationUser.builder()
                .firstName(CommonFunctions.sanitizeName(registrationRequest.getFirstName()))
                .middleNames(CommonFunctions.sanitizeName(registrationRequest.getMiddleNames()))
                .lastName(CommonFunctions.sanitizeName(registrationRequest.getLastName()))
                .email(CommonFunctions.sanitizeEmail(registrationRequest.getEmail()))
                .role(Role.USER)
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
    private void checkIfUserExists(String email) {
        log.info("AuthenticationService/checkIfUserExists");
        if (userRepository.existsByEmail(email)) {
            throw  CustomServiceException.builder()
                    .message("User already exists")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
    public AuthenticationResponse authenticate(AuthenticationRequest registrationRequest) {
        log.info("AuthenticationService/authenticate");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registrationRequest.getEmail(),
                        registrationRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(registrationRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
