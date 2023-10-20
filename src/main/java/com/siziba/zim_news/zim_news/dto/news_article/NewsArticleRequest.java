package com.siziba.zim_news.zim_news.dto.news_article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsArticleRequest {
    private String title;
    private String extract;
    private String url;
    private Long views;
    private String author;
    private UUID publicationId;
    private String publishedAt;
    private String imageUrl;
    private String articleUrl;
}
