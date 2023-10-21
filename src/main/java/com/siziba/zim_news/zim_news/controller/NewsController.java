package com.siziba.zim_news.zim_news.controller;

import com.siziba.zim_news.zim_news.dto.ApiResponse;
import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import com.siziba.zim_news.zim_news.dto.news_article.NewsArticleResponse;
import com.siziba.zim_news.zim_news.dto.publication.PublicationResponse;
import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.entity.SearchTerm;
import com.siziba.zim_news.zim_news.service.news.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
@Tag(name = "News", description = "News API")
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/articles/popular")
    @Operation(summary = "Get popular articles")
    public ResponseEntity<ApiResponse<List<NewsArticleResponse>>> getPopularArticles() {
        log.info("NewsController/getPopularArticles");
        List<NewsArticle> newsArticles = newsService.getPopularArticles();
        return ResponseEntity.ok(ApiResponse.<List<NewsArticleResponse>>builder()
                .data(NewsArticleResponse.fromList(newsArticles))
                .success(true)
                .message("Popular articles fetched successfully")
                .build());
    }

    @GetMapping("/articles/search")
    @Operation(summary = "Search for articles")
    public ResponseEntity<ApiResponse<List<NewsArticleResponse>>> getNewsArticles(
            @RequestParam String searchTerm,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "25") int size,
            @RequestBody DeviceRequest deviceRequest
    ) {
        List<NewsArticle> newsArticles = newsService.searchForArticles(searchTerm, page, size, deviceRequest);
        return ResponseEntity.ok(ApiResponse.<List<NewsArticleResponse>>builder()
                .data(NewsArticleResponse.fromList(newsArticles))
                .success(true)
                .message("News articles fetched successfully")
                .build());
    }

    @GetMapping("/articles/category")
    @Operation(summary = "Get articles by category")
    public ResponseEntity<ApiResponse<List<NewsArticleResponse>>> getNewsArticlesByCategory(
            @RequestParam String category,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "25") int size
    ) {
        log.info("NewsController/getNewsArticlesByCategory/category/paginate");
        List<NewsArticle> newsArticles = newsService.getArticlesByCategory(category, page, size);
        return ResponseEntity.ok(ApiResponse.<List<NewsArticleResponse>>builder()
                .data(NewsArticleResponse.fromList(newsArticles))
                .success(true)
                .message("News articles fetched successfully")
                .build());
    }

    @GetMapping("/articles")
    @Operation(summary = "Get articles with no specific query")
    public ResponseEntity<ApiResponse<List<NewsArticleResponse>>> getNewsArticles(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "25") int size
    ) {
        log.info("NewsController/getNewsArticles/paginate");
        List<NewsArticle> newsArticles = newsService.getArticles(page, size);
        return ResponseEntity.ok(ApiResponse.<List<NewsArticleResponse>>builder()
                .data(NewsArticleResponse.fromList(newsArticles))
                .success(true)
                .message("News articles fetched successfully")
                .build());
    }

    @GetMapping("/articles/{id}")
    @Operation(summary = "Get article by id")
    public ResponseEntity<ApiResponse<NewsArticleResponse>> getNewsArticleById(@PathVariable UUID id) {
        log.info("NewsController/getNewsArticleById");
        NewsArticle newsArticle = newsService.getNewsArticleById(id);
        return ResponseEntity.ok(ApiResponse.<NewsArticleResponse>builder()
                .data(NewsArticleResponse.from(newsArticle))
                .success(true)
                .message("News article fetched successfully")
                .build());
    }

    @GetMapping("/publications/{id}/articles")
    @Operation(summary = "Get articles by publication")
    public ResponseEntity<ApiResponse<List<NewsArticleResponse>>> getNewsArticlesByPublication(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "25") int size
    ) {
        log.info("NewsController/getNewsArticlesByPublication");
        List<NewsArticle> newsArticles = newsService.getArticlesByPublication(id, page, size);
        return ResponseEntity.ok(ApiResponse.<List<NewsArticleResponse>>builder()
                .data(NewsArticleResponse.fromList(newsArticles))
                .success(true)
                .message("News articles fetched successfully")
                .build());
    }

    @GetMapping("/publications")
    @Operation(summary = "Get publications")
    public ResponseEntity<ApiResponse<List<PublicationResponse>>> getPublications() {
        log.info("NewsController/getPublications");
        List<Publication> publications = newsService.getPublications();
        return ResponseEntity.ok(ApiResponse.<List<PublicationResponse>>builder()
                .data(PublicationResponse.fromList(publications))
                .success(true)
                .message("Publications fetched successfully")
                .build());
    }

    @GetMapping("/publications/{id}")
    @Operation(summary = "Get publication by id")
    public ResponseEntity<ApiResponse<PublicationResponse>> getPublicationById(@PathVariable UUID id) {
        log.info("NewsController/getPublicationById");
        Publication publication = newsService.getPublicationById(id);
        return ResponseEntity.ok(ApiResponse.<PublicationResponse>builder()
                .data(PublicationResponse.from(publication))
                .success(true)
                .message("Publication fetched successfully")
                .build());
    }

    @GetMapping("/top-searches")
    @Operation(summary = "Get top searches")
    public ResponseEntity<ApiResponse<List<String>>> getTopSearches() {
        log.info("NewsController/getTopSearches");
        List<SearchTerm> searches = newsService.getLatestSearches();
        List<String> searchTerms = searches.stream().map(SearchTerm::getTerm).toList();
        return ResponseEntity.ok(ApiResponse.<List<String>>builder()
                .data(searchTerms)
                .success(true)
                .message("Searches fetched successfully")
                .build());
    }

    @GetMapping("/articles/{id}/like")
    @Operation(summary = "Toggle like")
    public ResponseEntity<ApiResponse<Long>> toggleLike(@PathVariable UUID id, @RequestBody DeviceRequest deviceRequest) {
        log.info("NewsController/toggleLike");
        Long likes = newsService.toggleLike(id, deviceRequest);
        return ResponseEntity.ok(ApiResponse.<Long>builder()
                .data(likes)
                .success(true)
                .message("Like toggled successfully")
                .build());
    }

    @GetMapping("/articles/{id}/view")
    @Operation(summary = "Add view")
    public ResponseEntity<ApiResponse<Long>> addView(@PathVariable UUID id) {
        log.info("NewsController/addView");
        Long views = newsService.addView(id);
        return ResponseEntity.ok(ApiResponse.<Long>builder()
                .data(views)
                .success(true)
                .message("View added successfully")
                .build());
    }
}
