package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    NewsArticle getNewsArticleById(UUID id);
    List<NewsArticle> getNewsArticles();
    List<Publication> getPublications();
    Publication getPublicationById(UUID id);
}
