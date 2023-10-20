package com.siziba.zim_news.zim_news.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsArticle {
    @Id
    @GeneratedValue
    public UUID id;

    public String title;

    public String extract;

    public String url;

    public Long views;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    public FileStorage image;

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    private Publication publication;

    public Date date;

    @CreationTimestamp
    public java.sql.Timestamp createdAt;

    @CreationTimestamp
    public java.sql.Timestamp updatedAt;
}
