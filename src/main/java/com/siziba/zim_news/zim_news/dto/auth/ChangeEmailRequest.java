package com.siziba.zim_news.zim_news.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeEmailRequest {
    private String currentEmail;
    private String newEmail;
    private String password;
}