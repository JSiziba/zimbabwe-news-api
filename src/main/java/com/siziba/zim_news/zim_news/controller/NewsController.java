package com.siziba.zim_news.zim_news.controller;

import com.siziba.zim_news.zim_news.dto.ApiResponse;
import com.siziba.zim_news.zim_news.dto.news_article.NewsArticleResponse;
import com.siziba.zim_news.zim_news.dto.publication.PublicationResponse;
import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.entity.SearchTerm;
import com.siziba.zim_news.zim_news.service.news.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/articles")
    public ResponseEntity<ApiResponse<List<NewsArticleResponse>>> getNewsArticles() {
        log.info("NewsController/getNewsArticles");
        List<NewsArticle> newsArticles = newsService.getNewsArticles();
        return ResponseEntity.ok(ApiResponse.<List<NewsArticleResponse>>builder()
                .data(NewsArticleResponse.fromList(newsArticles))
                .success(true)
                .message("News articles fetched successfully")
                .build());
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ApiResponse<NewsArticleResponse>> getNewsArticleById(@PathVariable UUID id) {
        log.info("NewsController/getNewsArticleById");
        NewsArticle newsArticle = newsService.getNewsArticleById(id);
        return ResponseEntity.ok(ApiResponse.<NewsArticleResponse>builder()
                .data(NewsArticleResponse.from(newsArticle))
                .success(true)
                .message("News article fetched successfully")
                .build());
    }

    @GetMapping("/articles/search/{query}")
    public ResponseEntity<ApiResponse<List<NewsArticleResponse>>> searchNewsArticles(@PathVariable String query) {
        log.info("NewsController/searchNewsArticles");
        List<NewsArticle> newsArticles = newsService.searchNewsArticles(query);
        return ResponseEntity.ok(ApiResponse.<List<NewsArticleResponse>>builder()
                .data(NewsArticleResponse.fromList(newsArticles))
                .success(true)
                .message("News articles fetched successfully")
                .build());
    }

    @GetMapping("/publications")
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
    public ResponseEntity<ApiResponse<PublicationResponse>> getPublicationById(@PathVariable UUID id) {
        log.info("NewsController/getPublicationById");
        Publication publication = newsService.getPublicationById(id);
        return ResponseEntity.ok(ApiResponse.<PublicationResponse>builder()
                .data(PublicationResponse.from(publication))
                .success(true)
                .message("Publication fetched successfully")
                .build());
    }

    @GetMapping("/publications/{id}/articles")
    public ResponseEntity<ApiResponse<List<NewsArticleResponse>>> getNewsArticlesByPublicationId(@PathVariable UUID id) {
        log.info("NewsController/getNewsArticlesByPublicationId");
        List<NewsArticle> newsArticles = newsService.getNewsArticlesByPublicationId(id);
        return ResponseEntity.ok(ApiResponse.<List<NewsArticleResponse>>builder()
                .data(NewsArticleResponse.fromList(newsArticles))
                .success(true)
                .message("News articles fetched successfully")
                .build());
    }

    @GetMapping("/searches")
    public ResponseEntity<ApiResponse<List<String>>> getUserRecentSearches() {
        log.info("NewsController/getUserRecentSearches");
        List<SearchTerm> searches = newsService.getUserRecentSearches();
        List<String> searchTerms = searches.stream().map(SearchTerm::getTerm).toList();
        return ResponseEntity.ok(ApiResponse.<List<String>>builder()
                .data(searchTerms)
                .success(true)
                .message("Searches fetched successfully")
                .build());
    }

    @GetMapping("/top-searches")
    public ResponseEntity<ApiResponse<List<String>>> getTopSearches() {
        log.info("NewsController/getTopSearches");
        List<SearchTerm> searches = newsService.getTopSearches();
        List<String> searchTerms = searches.stream().map(SearchTerm::getTerm).toList();
        return ResponseEntity.ok(ApiResponse.<List<String>>builder()
                .data(searchTerms)
                .success(true)
                .message("Searches fetched successfully")
                .build());
    }

    @GetMapping("/articles/{id}/like")
    public ResponseEntity<ApiResponse<Long>> toggleLike(@PathVariable UUID id) {
        log.info("NewsController/toggleLike");
        Long likes = newsService.toggleLike(id);
        return ResponseEntity.ok(ApiResponse.<Long>builder()
                .data(likes)
                .success(true)
                .message("Like toggled successfully")
                .build());
    }

    @GetMapping("/articles/{id}/view")
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
