package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.entity.NewsArticle;

import java.util.List;
import java.util.Optional;

public interface WebScrapperService {
    Optional<List<NewsArticle>> runScrapper();
}
