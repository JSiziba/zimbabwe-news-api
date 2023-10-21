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
public class SearchTerm {
    @Id
    @GeneratedValue
    public UUID id;

    public String term;

    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    public Device device;

    public Long count;

    @CreationTimestamp
    public java.sql.Timestamp createdAt;

    @CreationTimestamp
    public java.sql.Timestamp updatedAt;
}
