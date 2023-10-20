package com.siziba.zim_news.zim_news.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Device {
    @Id
    @GeneratedValue
    public UUID id;

    public String macAddress;
    public String ipAddress;
    public String deviceInfo;
    public String ipAddressApproxLocation;

    @CreationTimestamp
    public java.sql.Timestamp createdAt;

    @CreationTimestamp
    public java.sql.Timestamp updatedAt;
}
