package com.siziba.zim_news.zim_news.repository;

import com.siziba.zim_news.zim_news.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, UUID> {
}
