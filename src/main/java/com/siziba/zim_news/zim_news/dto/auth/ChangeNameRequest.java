package com.siziba.zim_news.zim_news.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeNameRequest {
    private String firstName;
    private String middleNames;
    private String lastName;
}
