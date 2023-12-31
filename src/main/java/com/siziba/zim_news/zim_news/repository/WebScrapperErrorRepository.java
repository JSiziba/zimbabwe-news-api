package com.siziba.zim_news.zim_news.repository;

import com.siziba.zim_news.zim_news.entity.WebScrapperError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WebScrapperErrorRepository extends JpaRepository<WebScrapperError, UUID> {
}
