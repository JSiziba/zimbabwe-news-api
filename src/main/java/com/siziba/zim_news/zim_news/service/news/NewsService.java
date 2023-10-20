package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.entity.SearchTerm;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    NewsArticle getNewsArticleById(UUID id);
    List<NewsArticle> getNewsArticles();
    List<NewsArticle> searchNewsArticles(String query);
    List<Publication> getPublications();
    List<NewsArticle> getNewsArticlesByPublicationId(UUID id);
    Publication getPublicationById(UUID id);
    List<SearchTerm> getUserRecentSearches();
    List<SearchTerm> getTopSearches();
    Long toggleLike(UUID id);
    Long addView(UUID id);
}
