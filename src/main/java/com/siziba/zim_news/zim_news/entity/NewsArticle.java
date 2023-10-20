package com.siziba.zim_news.zim_news.entity;

import com.siziba.zim_news.zim_news.type.NewsArticleCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsArticle {
    @Id
    @GeneratedValue
    private UUID id;

    private Long views;
    private String title;
    private String extract;
    private String articleUrl;
    private String author;
    private String pictureUrl;
    private java.sql.Date publishedAt;

    @Enumerated(EnumType.STRING)
    private NewsArticleCategory category;

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    private Publication publication;

    @CreationTimestamp
    private java.sql.Timestamp createdAt;

    @CreationTimestamp
    private java.sql.Timestamp updatedAt;
}
