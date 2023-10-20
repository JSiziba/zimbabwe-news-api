package com.siziba.zim_news.zim_news.dto.auth;


import com.siziba.zim_news.zim_news.entity.ApplicationUser;
import com.siziba.zim_news.zim_news.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
    private UUID id;
    private String firstName;
    private String middleNames;
    private String lastName;
    private String email;
    private Role role;

    public UserDetailsResponse(ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        this.firstName = applicationUser.getFirstName();
        this.middleNames = applicationUser.getMiddleNames();
        this.lastName = applicationUser.getLastName();
        this.email = applicationUser.getEmail();
        this.role = applicationUser.getRole();
    }
}
