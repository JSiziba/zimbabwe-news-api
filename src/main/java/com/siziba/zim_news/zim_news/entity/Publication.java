package com.siziba.zim_news.zim_news.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publication {

    @Id
    @GeneratedValue
    public UUID id;

    public String name;

    public String description;

    public String url;

    public String country;

    @OneToMany(orphanRemoval = true)
    public List<NewsArticle> newsArticles;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "logo_id", referencedColumnName = "id")
    public FileStorage logo;

    @CreationTimestamp
    public java.sql.Timestamp createdAt;

    @CreationTimestamp
    public java.sql.Timestamp updatedAt;
}
