package com.siziba.zim_news.zim_news.controller;

import com.siziba.zim_news.zim_news.dto.ApiResponse;
import com.siziba.zim_news.zim_news.service.common.CommonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/misc")
@Tag(name = "MiscAPI", description = "Miscellaneous APIs")
public class MiscApiController {
    private final CommonService commonService;

    @GetMapping("/get-approx-location-from-ip")
    public ResponseEntity<ApiResponse<String>> getApproxLocationFromIp(@RequestParam String stringIpAddress) {
        String location = commonService.getApproxLocationFromIp(stringIpAddress);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .data(location)
                .message(location.equalsIgnoreCase("unknown") ? "Could not get location from IP" : "Location successfully retrieved")
                .success(true)
                .build());
    }
}
