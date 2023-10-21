package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.dto.FileRequest;
import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import com.siziba.zim_news.zim_news.dto.news_article.NewsArticleRequest;
import com.siziba.zim_news.zim_news.dto.publication.PublicationRequest;
import com.siziba.zim_news.zim_news.entity.Device;
import com.siziba.zim_news.zim_news.entity.FileStorage;
import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;

import java.util.List;
import java.util.UUID;

public interface NewsAdminService {
    List<Device> getDevices();
    Device updateDeviceById(UUID id, DeviceRequest device);
    void deleteDeviceById(UUID id);
    Publication addPublication(PublicationRequest publication);
    void deletePublicationById(UUID id);
    Publication updatePublicationById(UUID id, PublicationRequest publication);
    FileStorage addLogoToPublication(UUID id, FileRequest fileRequest);
    List<NewsArticle> runScraper();
    NewsArticle addNewsArticle(NewsArticleRequest newsArticle);
    void deleteNewsArticleById(UUID id);
    NewsArticle updateNewsArticleById(UUID id, NewsArticleRequest newsArticle);
}
