package com.siziba.zim_news.zim_news.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomServiceException extends RuntimeException{
    private HttpStatus errorCode = HttpStatus.BAD_REQUEST;
    private String message = "Something went wrong";
}