package com.siziba.zim_news.zim_news.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
public class FileStorage {
    @Id
    @GeneratedValue
    public UUID id;

    @Lob
    public byte[] data;
    public String fileName;
    public String fileType;

    @CreationTimestamp
    public java.sql.Timestamp createdAt;

    @CreationTimestamp
    public java.sql.Timestamp updatedAt;
}
