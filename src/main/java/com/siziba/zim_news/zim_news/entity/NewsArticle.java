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

    public void setTitle(String title) {
        this.title = title.substring(0, Math.min(title.length(), 255));
    }

    @Lob
    @Column(columnDefinition = "BLOB")
    private String extract;

    @Lob
    @Column(columnDefinition = "BLOB")
    private String articleUrl;

    @Lob
    @Column(columnDefinition = "BLOB")
    private String author;

    @Lob
    @Column(columnDefinition = "BLOB")
    private String pictureUrl;

    private java.sql.Date publishedAt;

    @Enumerated(EnumType.STRING)
    private NewsArticleCategory category = NewsArticleCategory.UNKNOWN;

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    private Publication publication;

    @CreationTimestamp
    private java.sql.Timestamp createdAt;

    @CreationTimestamp
    private java.sql.Timestamp updatedAt;
}
