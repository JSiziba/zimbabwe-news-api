package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import com.siziba.zim_news.zim_news.entity.*;
import com.siziba.zim_news.zim_news.exception.CustomServiceException;
import com.siziba.zim_news.zim_news.library.CommonFunctions;
import com.siziba.zim_news.zim_news.repository.*;
import com.siziba.zim_news.zim_news.service.common.CommonService;
import com.siziba.zim_news.zim_news.type.NewsArticleCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final FileStorageService fileStorageService;
    private final DeviceRepository devicesRepository;
    private final NewsArticleRepository newsArticleRepository;
    private final PublicationRepository publicationRepository;
    private final WebScrapperService scraperService;
    private final CommonService commonService;
    private final SearchTermRepository searchTermRepository;
    private final ArticleLikeHistoryRepository articleLikeHistoryRepository;

    @Override
    public NewsArticle getNewsArticleById(UUID id) {
        return newsArticleRepository.findById(id).orElseThrow(() -> CustomServiceException.builder()
                .errorCode(HttpStatus.NOT_FOUND)
                .message("News article not found")
                .build());
    }

    @Override
    public List<NewsArticle> getPopularArticles() {
        return newsArticleRepository.findTop10ByOrderByViewsDesc();
    }

    @Override
    public List<NewsArticle> searchForArticles(String searchString, int page, int size, DeviceRequest deviceRequest) {

        SearchTerm searchTerm = searchTermRepository.findSearchTermByTermLike(searchString);
        if (searchTerm != null) {
            searchTerm.setCount(searchTerm.getCount() == null ? 1 : searchTerm.getCount() + 1);
            searchTermRepository.save(searchTerm);
        } else {
            searchTerm = SearchTerm.builder()
                    .term(searchString)
                    .device(savedDevice(deviceRequest))
                    .build();
            searchTermRepository.save(searchTerm);
        }

        Pageable pageable = PageRequest.of(page, size);
        List<NewsArticle> newsArticles = newsArticleRepository.findByTitle(
                searchString
        );
        if (newsArticles.size() > 10) {
            newsArticles = newsArticles.subList(0, 10);
        }
        return newsArticles;
    }

    private Device savedDevice(DeviceRequest deviceRequest) {
        Device device = devicesRepository.findDeviceByMacAddress(deviceRequest.getMacAddress());
        if (device == null) {
            device = Device.builder()
                    .macAddress(deviceRequest.getMacAddress())
                    .deviceInfo(deviceRequest.getDeviceInfo())
                    .ipAddress(deviceRequest.getIpAddress())
                    .ipAddressApproxLocation(commonService.getApproxLocationFromIp(deviceRequest.getIpAddress()))
                    .build();
            devicesRepository.save(device);
        }
        return device;
    }

    @Override
    public List<NewsArticle> getArticlesByCategory(String categoryString, int page, int size) {
        NewsArticleCategory category = CommonFunctions.setCategory(categoryString);

        if (category == NewsArticleCategory.OTHER) {
            throw CustomServiceException.builder()
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .message("Invalid category")
                    .build();
        }

        Pageable pageable = PageRequest.of(page, size);
        return newsArticleRepository.findNewsArticleByCategoryOrderByCreatedAtDesc(
                category,
                pageable
        );

    }

    @Override
    public List<NewsArticle> getArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsArticleRepository.findAll(
                pageable
        ).toList();
    }

    @Override
    public List<NewsArticle> getArticlesByPublication(String publicationId, int page, int size) {
        Publication publication = publicationRepository.findById(UUID.fromString(publicationId))
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("Publication not found")
                        .build());
        Pageable pageable = PageRequest.of(page, size);
        return newsArticleRepository.findNewsArticlesByPublicationOrderByCreatedAtDesc(
                publication,
                pageable
        );
    }

    @Override
    public List<Publication> getPublications() {
        return publicationRepository.findAll();
    }

    @Override
    public Publication getPublicationById(UUID id) {
        return publicationRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("Publication not found")
                        .build());
    }

    @Override
    public List<SearchTerm> getLatestSearches() {
        return searchTermRepository.findTop5ByOrderByCreatedAtDesc();
    }

    @Override
    public Long toggleLike(UUID id, DeviceRequest deviceRequest) {
        NewsArticle newsArticle = newsArticleRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("News article not found")
                        .build());
        Device device = devicesRepository.findDeviceByMacAddress(deviceRequest.getMacAddress());
        if (device == null) {
            device = Device.builder()
                    .macAddress(deviceRequest.getMacAddress())
                    .deviceInfo(deviceRequest.getDeviceInfo())
                    .ipAddress(deviceRequest.getIpAddress())
                    .ipAddressApproxLocation(commonService.getApproxLocationFromIp(deviceRequest.getIpAddress()))
                    .build();
            devicesRepository.save(device);
        }
        ArticleLikeHistory articleLikeHistory = articleLikeHistoryRepository
                .findArticleLikeHistoriesByArticleAndDevice(newsArticle, device);

        if (articleLikeHistory == null) {
            articleLikeHistory = ArticleLikeHistory.builder()
                    .article(newsArticle)
                    .device(device)
                    .build();
            articleLikeHistoryRepository.save(articleLikeHistory);
        } else {
            articleLikeHistoryRepository.delete(articleLikeHistory);
        }
        return articleLikeHistoryRepository.countArticleLikeHistoriesByArticle(newsArticle);
    }

    @Override
    public Long addView(UUID id) {
        NewsArticle newsArticle = newsArticleRepository.findById(id)
                .orElseThrow(() -> CustomServiceException.builder()
                        .errorCode(HttpStatus.NOT_FOUND)
                        .message("News article not found")
                        .build());
        newsArticle.setViews(newsArticle.getViews() == null ? 1 : newsArticle.getViews() + 1);
        newsArticleRepository.save(newsArticle);
        return newsArticle.getViews();
    }
}
