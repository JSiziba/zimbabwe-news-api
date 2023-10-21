package com.siziba.zim_news.zim_news.repository;

import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.type.NewsArticleCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, UUID> {
    List<NewsArticle> findTop10ByOrderByViewsDesc();

    @Query("SELECT n FROM NewsArticle n WHERE n.title LIKE CONCAT('%', :title, '%')")
    List<NewsArticle> findByTitle(@Param("title") String title);

    List<NewsArticle> findNewsArticleByPublicationNotInAndCategoryOrderByCreatedAtDesc(List<Publication> excludedPublications, NewsArticleCategory category, Pageable pageable);
    List<NewsArticle> findNewsArticleByCategoryOrderByCreatedAtDesc(NewsArticleCategory category, Pageable pageable);

    List<NewsArticle> findNewsArticlesByPublicationOrderByCreatedAtDesc(Publication publication, Pageable pageable);
    List<NewsArticle> findNewsArticlesByPublicationOrderByCreatedAtDesc(Publication publication);

    List<NewsArticle> findNewsArticlesByPublicationNotInOrderByCreatedAtDesc(List<Publication> excludedPublications, Pageable pageable);
    List<NewsArticle> findNewsArticlesByPublicationNotInOrderByCreatedAtDesc(List<Publication> excludedPublications);

    List<NewsArticle> findByPublicationNotIn(List<Publication> excludedPublications);

    boolean existsByArticleUrl(String articleUrl);

    void deleteByPublishedAtBefore(Date date);
}
