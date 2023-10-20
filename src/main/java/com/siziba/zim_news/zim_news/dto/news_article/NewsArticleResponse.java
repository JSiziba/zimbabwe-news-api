package com.siziba.zim_news.zim_news.dto.news_article;

import com.siziba.zim_news.zim_news.entity.NewsArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsArticleResponse {
    private UUID id;
    private String title;
    private String extract;
    private String url;
    private Long views;
    private String author;
    private UUID publicationId;
    private java.sql.Date publishedAt;
    private String imageUrl;
    private String articleUrl;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;

    public static NewsArticleResponse from(NewsArticle newsArticle) {
        return NewsArticleResponse.builder()
                .id(newsArticle.getId())
                .title(newsArticle.getTitle())
                .extract(newsArticle.getExtract())
                .url(newsArticle.getArticleUrl())
                .views(newsArticle.getViews())
                .author(newsArticle.getAuthor())
                .publicationId(newsArticle.getPublication().getId())
                .publishedAt(newsArticle.getPublishedAt())
                .imageUrl(newsArticle.getPictureUrl())
                .articleUrl(newsArticle.getArticleUrl())
                .createdAt(newsArticle.getCreatedAt())
                .updatedAt(newsArticle.getUpdatedAt())
                .build();
    }

    public static List<NewsArticleResponse> fromList(List<NewsArticle> newsArticles) {
        return newsArticles.stream()
                .map(NewsArticleResponse::from)
                .toList();
    }
}
