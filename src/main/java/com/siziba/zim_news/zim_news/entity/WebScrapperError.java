package com.siziba.zim_news.zim_news.entity;

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
public class WebScrapperError {
    @Id
    @GeneratedValue
    public UUID id;

    public String publicationName;

    @Lob
    @Column(columnDefinition = "BLOB")
    public String message;
    public String articleUrl = "N/A";

    @CreationTimestamp
    public java.sql.Timestamp createdAt;

    @CreationTimestamp
    public java.sql.Timestamp updatedAt;
}
