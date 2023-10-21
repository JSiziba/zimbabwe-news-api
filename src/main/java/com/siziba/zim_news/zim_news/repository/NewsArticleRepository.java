package com.siziba.zim_news.zim_news.repository;

import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.type.NewsArticleCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, UUID> {
    List<NewsArticle> findTop10ByOrderByViewsDesc();

    List<NewsArticle> findNewsArticlesByTitleContainingOrExtractContainingOrderByCreatedAtDesc(String searchTerm, String searchTerm1, Pageable pageable);
    List<NewsArticle> findNewsArticlesByTitleContainingOrExtractContainingOrderByCreatedAtDesc(String searchTerm, String searchTerm1);
    List<NewsArticle> findNewsArticlesByCategoryOrderByCreatedAtDesc(NewsArticleCategory category, Pageable pageable);
    List<NewsArticle> findNewsArticlesByCategoryOrderByCreatedAtDesc(NewsArticleCategory category);

    List<NewsArticle> findNewsArticlesByPublicationOrderByCreatedAtDesc(Publication publication, Pageable pageable);
    List<NewsArticle> findNewsArticlesByPublicationOrderByCreatedAtDesc(Publication publication);

    List<NewsArticle> findNewsArticlesByPublicationNotInOrderByCreatedAtDesc(List<Publication> excludedPublications, Pageable pageable);
    List<NewsArticle> findNewsArticlesByPublicationNotInOrderByCreatedAtDesc(List<Publication> excludedPublications);
}
