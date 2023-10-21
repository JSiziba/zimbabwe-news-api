package com.siziba.zim_news.zim_news.library;

import com.siziba.zim_news.zim_news.exception.CustomServiceException;
import com.siziba.zim_news.zim_news.type.NewsArticleCategory;
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

    public static void validateIpAddress(String stringIpAddress) {
        if (!stringIpAddress.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            throw  CustomServiceException.builder()
                    .message("Invalid IP address")
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    public static NewsArticleCategory setCategory(String category) {
        return switch (category) {
            case "SPORTS" -> NewsArticleCategory.SPORTS;
            case "BUSINESS" -> NewsArticleCategory.BUSINESS;
            case "ENTERTAINMENT" -> NewsArticleCategory.ENTERTAINMENT;
            case "HEALTH" -> NewsArticleCategory.HEALTH;
            case "TECHNOLOGY" -> NewsArticleCategory.TECHNOLOGY;
            case "SCIENCE" -> NewsArticleCategory.SCIENCE;
            case "POLITICS" -> NewsArticleCategory.POLITICS;
            case "LIFESTYLE" -> NewsArticleCategory.LIFESTYLE;
            case "WORLD" -> NewsArticleCategory.WORLD;
            case "LOCAL" -> NewsArticleCategory.LOCAL;
            default -> NewsArticleCategory.OTHER;
        };
    }
}
