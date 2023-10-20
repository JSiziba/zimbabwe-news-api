package com.siziba.zim_news.zim_news.library;

import com.siziba.zim_news.zim_news.exception.CustomServiceException;
import org.springframework.http.HttpStatus;

public class CommonFunctions {
    public static void validatePassword(String password) {
        if (password.length() < 8) {
            throw  CustomServiceException.builder()
                    .message("Password must be at least 8 characters")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (!password.matches(".*\\d.*")) {
            throw  CustomServiceException.builder()
                    .message("Password must contain at least one number")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (!password.matches(".*[A-Z].*")) {
            throw  CustomServiceException.builder()
                    .message("Password must contain at least one uppercase letter")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (!password.matches(".*[a-z].*")) {
            throw  CustomServiceException.builder()
                    .message("Password must contain at least one lowercase letter")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (!password.matches(".*[!@#$%^&*()\\[\\]{};:'\"<>,.?/\\\\|`~_=+].*")) {
            throw  CustomServiceException.builder()
                    .message("Password must contain at least one special character")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
    public static void validateEmail(String email) {
        if (!email.contains("@")) {
            throw  CustomServiceException.builder()
                    .message("Invalid email")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
    public static void validateName(String name) {
        if (name.length() < 2) {
            throw  CustomServiceException.builder()
                    .message("Name must be at least 2 characters")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (!name.matches("^[a-zA-Z]*$")) {
            throw  CustomServiceException.builder()
                    .message("Name must contain only letters")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (name.length() > 20) {
            throw  CustomServiceException.builder()
                    .message("Name must be at most 20 characters")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
    public static String sanitizeName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return name;
    }
    public static String sanitizeEmail(String email) {
        email = email.toLowerCase();
        return email;
    }
}
