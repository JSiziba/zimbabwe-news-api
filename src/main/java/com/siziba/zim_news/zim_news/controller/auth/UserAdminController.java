package com.siziba.zim_news.zim_news.controller.auth;

import com.siziba.zim_news.zim_news.service.auth.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/admin")
public class UserAdminController {
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAllUsers(){
        return "All users";
    }
}
