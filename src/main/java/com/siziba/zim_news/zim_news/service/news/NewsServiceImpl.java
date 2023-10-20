package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.entity.SearchTerm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    @Override
    public NewsArticle getNewsArticleById(UUID id) {
        return null;
    }

    @Override
    public List<NewsArticle> getNewsArticles() {
        return null;
    }

    @Override
    public List<NewsArticle> searchNewsArticles(String query) {
        return null;
    }

    @Override
    public List<Publication> getPublications() {
        return null;
    }

    @Override
    public List<NewsArticle> getNewsArticlesByPublicationId(UUID id) {
        return null;
    }

    @Override
    public Publication getPublicationById(UUID id) {
        return null;
    }

    @Override
    public List<SearchTerm> getUserRecentSearches() {
        return null;
    }

    @Override
    public List<SearchTerm> getTopSearches() {
        return null;
    }

    @Override
    public Long toggleLike(UUID id) {
        return null;
    }

    @Override
    public Long addView(UUID id) {
        return null;
    }
}
