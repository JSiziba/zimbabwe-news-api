package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import com.siziba.zim_news.zim_news.dto.news_article.NewsArticlesQuery;
import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.entity.SearchTerm;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    NewsArticle getNewsArticleById(UUID id);
    List<NewsArticle> getNewsArticles(NewsArticlesQuery newsArticlesQuery);
    List<Publication> getPublications();
    Publication getPublicationById(UUID id);
    List<SearchTerm> getLatestSearches();
    Long toggleLike(UUID id, DeviceRequest deviceRequest);
    Long addView(UUID id);
}
