package com.siziba.zim_news.zim_news.repository;

import com.siziba.zim_news.zim_news.entity.ArticleLikeHistory;
import com.siziba.zim_news.zim_news.entity.Device;
import com.siziba.zim_news.zim_news.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleLikeHistoryRepository extends JpaRepository<ArticleLikeHistory, UUID> {
    ArticleLikeHistory findArticleLikeHistoriesByArticleAndDevice(NewsArticle newsArticle, Device device);

    Long countArticleLikeHistoriesByArticle(NewsArticle newsArticle);
}
