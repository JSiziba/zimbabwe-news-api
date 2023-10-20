package com.siziba.zim_news.zim_news.exception;

import com.siziba.zim_news.zim_news.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<ApiResponse<?>> handleServiceException(CustomServiceException ex) {
        ApiResponse<?> response = new ApiResponse<>(false, null, ex.getMessage());
        return ResponseEntity.status(ex.getErrorCode()).body(response);
    }
}
