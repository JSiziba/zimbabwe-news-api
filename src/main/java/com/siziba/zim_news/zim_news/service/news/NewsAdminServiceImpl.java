package com.siziba.zim_news.zim_news.service.news;


import com.siziba.zim_news.zim_news.dto.FileRequest;
import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import com.siziba.zim_news.zim_news.dto.publication.PublicationRequest;
import com.siziba.zim_news.zim_news.entity.Device;
import com.siziba.zim_news.zim_news.entity.FileStorage;
import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsAdminServiceImpl implements NewsAdminService {
    @Override
    public List<Device> getDevices() {
        return null;
    }

    @Override
    public Device updateDeviceById(DeviceRequest device) {
        return null;
    }

    @Override
    public void deleteDeviceById(UUID id) {

    }

    @Override
    public Publication addPublication(PublicationRequest publication) {
        return null;
    }

    @Override
    public void deletePublicationById(UUID id) {

    }

    @Override
    public Publication updatePublicationById(UUID id, PublicationRequest publication) {
        return null;
    }

    @Override
    public FileStorage addLogoToPublication(UUID id, FileRequest fileRequest) {
        return null;
    }

    @Override
    public List<NewsArticle> runScraper() {
        return null;
    }

    @Override
    public NewsArticle addNewsArticle(NewsArticle newsArticle) {
        return null;
    }

    @Override
    public void deleteNewsArticleById(UUID id) {

    }

    @Override
    public NewsArticle updateNewsArticleById(UUID id, NewsArticle newsArticle) {
        return null;
    }
}
