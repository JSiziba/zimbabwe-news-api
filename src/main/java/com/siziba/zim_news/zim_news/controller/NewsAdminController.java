package com.siziba.zim_news.zim_news.controller;

import com.siziba.zim_news.zim_news.dto.ApiResponse;
import com.siziba.zim_news.zim_news.dto.FileRequest;
import com.siziba.zim_news.zim_news.dto.FileResponse;
import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import com.siziba.zim_news.zim_news.dto.device.DeviceResponse;
import com.siziba.zim_news.zim_news.dto.news_article.NewsArticleResponse;
import com.siziba.zim_news.zim_news.dto.publication.PublicationRequest;
import com.siziba.zim_news.zim_news.dto.publication.PublicationResponse;
import com.siziba.zim_news.zim_news.entity.Device;
import com.siziba.zim_news.zim_news.entity.FileStorage;
import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.exception.CustomServiceException;
import com.siziba.zim_news.zim_news.service.auth.ApplicationUserDetailsService;
import com.siziba.zim_news.zim_news.service.news.NewsAdminService;
import com.siziba.zim_news.zim_news.type.Role;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news-admin")
@Tag(name = "NewsAdmin", description = "News Admin API")
@SecurityRequirement(name = "bearerAuth")
public class NewsAdminController {
    private final NewsAdminService newsAdminService;
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @GetMapping("/devices")
    public ResponseEntity<ApiResponse<List<DeviceResponse>>> getDevices(Principal principal) {
        log.info("NewsAdminController/getDevices");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        List<Device> devices = newsAdminService.getDevices();
        return ResponseEntity.ok(ApiResponse.<List<DeviceResponse>>builder()
                .data(DeviceResponse.fromList(devices))
                .success(true)
                .message("Devices fetched successfully")
                .build());
    }

    @PutMapping("/devices")
    public ResponseEntity<ApiResponse<DeviceResponse>> updateDevice(Principal principal, @RequestBody DeviceRequest device) {
        log.info("NewsAdminController/updateDevice");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        Device updatedDevice = newsAdminService.updateDeviceById(device);
        return ResponseEntity.ok(ApiResponse.<DeviceResponse>builder()
                .data(DeviceResponse.from(updatedDevice))
                .success(true)
                .message("Device updated successfully")
                .build());
    }

    @DeleteMapping("/devices/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDevice(Principal principal, @PathVariable UUID id) {
        log.info("NewsAdminController/deleteDevice");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        newsAdminService.deleteDeviceById(id);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message("Device deleted successfully")
                .build());
    }

    @PostMapping("/publications")
    public ResponseEntity<ApiResponse<PublicationResponse>> addPublication(Principal principal, @RequestBody PublicationRequest publicationRequest) {
        log.info("NewsAdminController/addPublication");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        Publication publication = newsAdminService.addPublication(publicationRequest);
        return ResponseEntity.ok(ApiResponse.<PublicationResponse>builder()
                .data(PublicationResponse.from(publication))
                .success(true)
                .message("Publication added successfully")
                .build());
    }

    @DeleteMapping("/publications/{id}")
    public ResponseEntity<ApiResponse<String>> deletePublication(Principal principal, @PathVariable UUID id) {
        log.info("NewsAdminController/deletePublication");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        newsAdminService.deletePublicationById(id);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message("Publication deleted successfully")
                .build());
    }

    @PutMapping("/publications/{id}")
    public ResponseEntity<ApiResponse<PublicationResponse>> updatePublication(Principal principal, @PathVariable UUID id, @RequestBody PublicationRequest publicationRequest) {
        log.info("NewsAdminController/updatePublication");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        Publication publication = newsAdminService.updatePublicationById(id, publicationRequest);
        return ResponseEntity.ok(ApiResponse.<PublicationResponse>builder()
                .data(PublicationResponse.from(publication))
                .success(true)
                .message("Publication updated successfully")
                .build());
    }

    @PostMapping("/publications/{id}/logo")
    public ResponseEntity<ApiResponse<FileResponse>> addLogoToPublication(Principal principal, @PathVariable UUID id, @RequestBody FileRequest fileRequest) {
        log.info("NewsAdminController/addLogoToPublication");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        FileStorage fileStorage = newsAdminService.addLogoToPublication(id, fileRequest);
        return ResponseEntity.ok(ApiResponse.<FileResponse>builder()
                .data(FileResponse.from(fileStorage))
                .success(true)
                .message("Publication logo added successfully")
                .build());
    }

    @PostMapping("/scraper")
    public ResponseEntity<ApiResponse<List<NewsArticleResponse>>> runScraper(Principal principal) {
        log.info("NewsAdminController/runScraper");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        List<NewsArticle> newsArticles = newsAdminService.runScraper();
        return ResponseEntity.ok(ApiResponse.<List<NewsArticleResponse>>builder()
                .data(NewsArticleResponse.fromList(newsArticles))
                .success(true)
                .message("Scraper ran successfully")
                .build());
    }

    @PostMapping("/news-articles")
    public ResponseEntity<ApiResponse<NewsArticleResponse>> addNewsArticle(Principal principal, @RequestBody NewsArticle newsArticle) {
        log.info("NewsAdminController/addNewsArticle");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        NewsArticle article = newsAdminService.addNewsArticle(newsArticle);
        return ResponseEntity.ok(ApiResponse.<NewsArticleResponse>builder()
                .data(NewsArticleResponse.from(article))
                .success(true)
                .message("News article added successfully")
                .build());
    }

    @DeleteMapping("/news-articles/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNewsArticle(Principal principal, @PathVariable UUID id) {
        log.info("NewsAdminController/deleteNewsArticle");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        newsAdminService.deleteNewsArticleById(id);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .message("News article deleted successfully")
                .build());
    }

    @PutMapping("/news-articles/{id}")
    public ResponseEntity<ApiResponse<NewsArticleResponse>> updateNewsArticle(Principal principal, @PathVariable UUID id, @RequestBody NewsArticle newsArticle) {
        log.info("NewsAdminController/updateNewsArticle");

        if (!applicationUserDetailsService.isAdmin(principal, Role.ADMIN)) {
            throw new CustomServiceException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        NewsArticle article = newsAdminService.updateNewsArticleById(id, newsArticle);
        return ResponseEntity.ok(ApiResponse.<NewsArticleResponse>builder()
                .data(NewsArticleResponse.from(article))
                .success(true)
                .message("News article updated successfully")
                .build());
    }
}