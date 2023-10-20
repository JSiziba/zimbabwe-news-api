package com.siziba.zim_news.zim_news.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLikeHistory {
    @Id
    @GeneratedValue
    public UUID id;

    @ManyToOne()
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    public Device device;

    @ManyToOne()
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    public NewsArticle article;
}
