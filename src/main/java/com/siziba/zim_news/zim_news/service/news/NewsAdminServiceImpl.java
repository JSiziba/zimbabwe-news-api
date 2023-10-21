package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.dto.FileRequest;
import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import com.siziba.zim_news.zim_news.dto.news_article.NewsArticleRequest;
import com.siziba.zim_news.zim_news.dto.publication.PublicationRequest;
import com.siziba.zim_news.zim_news.entity.Device;
import com.siziba.zim_news.zim_news.entity.FileStorage;
import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.exception.CustomServiceException;
import com.siziba.zim_news.zim_news.library.CommonFunctions;
import com.siziba.zim_news.zim_news.repository.DeviceRepository;
import com.siziba.zim_news.zim_news.repository.NewsArticleRepository;
import com.siziba.zim_news.zim_news.repository.PublicationRepository;
import com.siziba.zim_news.zim_news.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsAdminServiceImpl implements NewsAdminService {
    private final FileStorageService fileStorageService;
    private final DeviceRepository devicesRepository;
    private final NewsArticleRepository newsArticleRepository;
    private final PublicationRepository publicationRepository;
    private final WebScrapperService scraperService;
    private final CommonService commonService;

    @Override
    public List<Device> getDevices() {
        return devicesRepository.findAll();
    }

    @Override
    public Device updateDeviceById(UUID id, DeviceRequest device) {
        Device deviceToUpdate = devicesRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("Device not found")
                        .build());
        deviceToUpdate.setIpAddress(device.getIpAddress());
        deviceToUpdate.setDeviceInfo(device.getDeviceInfo());
        deviceToUpdate.setMacAddress(device.getMacAddress());
        String approxLocationFromIp = commonService.getApproxLocationFromIp(device.getIpAddress());
        deviceToUpdate.setIpAddressApproxLocation(approxLocationFromIp);
        return devicesRepository.save(deviceToUpdate);
    }

    @Override
    public void deleteDeviceById(UUID id) {
        Device deviceToDelete = devicesRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("Device not found")
                        .build());
        devicesRepository.delete(deviceToDelete);
    }

    @Override
    public Publication addPublication(PublicationRequest publication) {
        Publication publicationToAdd = Publication.builder()
                .name(publication.getName())
                .url(publication.getUrl())
                .location(publication.getLocation())
                .description(publication.getDescription())
                .build();
        return publicationRepository.save(publicationToAdd);
    }

    @Override
    public void deletePublicationById(UUID id) {
        Publication publicationToDelete = publicationRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("Publication not found")
                        .build());
        publicationRepository.delete(publicationToDelete);
    }

    @Override
    public Publication updatePublicationById(UUID id, PublicationRequest publication) {
        Publication publicationToUpdate = publicationRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("Publication not found")
                        .build());
        publicationToUpdate.setName(publication.getName());
        publicationToUpdate.setUrl(publication.getUrl());
        publicationToUpdate.setLocation(publication.getLocation());
        publicationToUpdate.setDescription(publication.getDescription());
        return publicationRepository.save(publicationToUpdate);
    }

    @Override
    public FileStorage addLogoToPublication(UUID id, FileRequest fileRequest) {
        Publication publicationToUpdate = publicationRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("Publication not found")
                        .build());
        if (publicationToUpdate.getLogo() != null) {
            fileStorageService.deleteFile(publicationToUpdate.getLogo().getId());
        }
        FileStorage fileStorage = fileStorageService.saveFile(fileRequest);
        publicationToUpdate.setLogo(fileStorage);
        publicationRepository.save(publicationToUpdate);
        return fileStorage;
    }

    @Override
    public List<NewsArticle> runScraper() {
        Optional<List<NewsArticle>> articles = scraperService.runScrapper();
        return articles.orElse(new ArrayList<>());
    }

    @Override
    public NewsArticle addNewsArticle(NewsArticleRequest newsArticle) {
        NewsArticle newsArticleToAdd = NewsArticle.builder()
                .title(newsArticle.getTitle())
                .author(newsArticle.getAuthor())
                .articleUrl(newsArticle.getArticleUrl())
                .pictureUrl(newsArticle.getPictureUrl())
                .publication(publicationRepository.findById(newsArticle.getPublicationId())
                        .orElseThrow(() -> CustomServiceException.builder()
                                .errorCode(HttpStatus.NOT_FOUND)
                                .message("Publication not found")
                                .build()))
                .category(CommonFunctions.setCategory(newsArticle.getCategory()))
                .views(0L)
                .build();
        return newsArticleRepository.save(newsArticleToAdd);
    }

    @Override
    public void deleteNewsArticleById(UUID id) {
        NewsArticle newsArticleToDelete = newsArticleRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("News article not found")
                        .build());
        newsArticleRepository.delete(newsArticleToDelete);
    }

    @Override
    public NewsArticle updateNewsArticleById(UUID id, NewsArticleRequest newsArticle) {
        NewsArticle newsArticleToUpdate = newsArticleRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("News article not found")
                        .build());
        newsArticleToUpdate.setTitle(newsArticle.getTitle());
        newsArticleToUpdate.setAuthor(newsArticle.getAuthor());
        newsArticleToUpdate.setArticleUrl(newsArticle.getArticleUrl());
        newsArticleToUpdate.setPictureUrl(newsArticle.getPictureUrl());
        newsArticleToUpdate.setPublication(publicationRepository.findById(newsArticle.getPublicationId())
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("Publication not found")
                        .build()));
        newsArticleToUpdate.setCategory(CommonFunctions.setCategory(newsArticle.getCategory()));
        return newsArticleRepository.save(newsArticleToUpdate);
    }
}
