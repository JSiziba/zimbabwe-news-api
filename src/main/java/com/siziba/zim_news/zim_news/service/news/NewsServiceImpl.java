package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import com.siziba.zim_news.zim_news.dto.news_article.NewsArticlesQuery;
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
    public List<NewsArticle> getNewsArticles(NewsArticlesQuery newsArticlesQuery) {

        this.validateNewsArticlesQuery(newsArticlesQuery);

        if (newsArticlesQuery.isPopular()){
            return newsArticleRepository.findTop10ByOrderByViewsDesc();
        }
        if (newsArticlesQuery.getSearchTerm() != null) {
            SearchTerm searchTerm = searchTermRepository.findSearchTermByTermLike(newsArticlesQuery.getSearchTerm());
            if (searchTerm == null) {
                searchTerm = SearchTerm.builder()
                        .term(newsArticlesQuery.getSearchTerm())
                        .device(Device.builder()
                                .macAddress(newsArticlesQuery.getDevice().getMacAddress())
                                .deviceInfo(newsArticlesQuery.getDevice().getDeviceInfo())
                                .ipAddress(newsArticlesQuery.getDevice().getIpAddress())
                                .ipAddressApproxLocation(commonService.getApproxLocationFromIp(newsArticlesQuery.getDevice().getIpAddress()))
                                .build())
                        .build();
                searchTermRepository.save(searchTerm);
            }
            searchTermRepository.save(searchTerm);
            if (newsArticlesQuery.isPaginate()) {
                Pageable pageable = PageRequest.of(newsArticlesQuery.getPage(), newsArticlesQuery.getSize());
                return newsArticleRepository.findNewsArticlesByTitleContainingOrExtractContainingOrderByCreatedAtDesc(
                        newsArticlesQuery.getSearchTerm(), newsArticlesQuery.getSearchTerm(),
                        pageable
                );
            }
            return newsArticleRepository.findNewsArticlesByTitleContainingOrExtractContainingOrderByCreatedAtDesc(
                    newsArticlesQuery.getSearchTerm(), newsArticlesQuery.getSearchTerm()
            );
        }
        if (newsArticlesQuery.getPublicationId() != null) {
            Publication publication = publicationRepository.findById(newsArticlesQuery.getPublicationId())
                    .orElseThrow(() -> CustomServiceException.builder()
                            .errorCode(HttpStatus.NOT_FOUND)
                            .message("Publication not found")
                            .build());
            if (newsArticlesQuery.isPaginate()) {
                Pageable pageable = PageRequest.of(newsArticlesQuery.getPage(), newsArticlesQuery.getSize());
                return newsArticleRepository.findNewsArticlesByPublicationOrderByCreatedAtDesc(
                        publication,
                        pageable
                );
            }
            return newsArticleRepository.findNewsArticlesByPublicationOrderByCreatedAtDesc(
                    publication
            );
        }
        if (newsArticlesQuery.getCategory() != null) {

            NewsArticleCategory category = CommonFunctions.setCategory(newsArticlesQuery.getCategory());

            if (category == NewsArticleCategory.OTHER) {
                throw CustomServiceException.builder()
                        .errorCode(HttpStatus.BAD_REQUEST)
                        .message("Invalid category")
                        .build();
            }

            if (newsArticlesQuery.isPaginate()) {
                Pageable pageable = PageRequest.of(newsArticlesQuery.getPage(), newsArticlesQuery.getSize());
                return newsArticleRepository.findNewsArticlesByCategoryOrderByCreatedAtDesc(
                        NewsArticleCategory.valueOf(newsArticlesQuery.getCategory()),
                        pageable
                );
            }
            return newsArticleRepository.findNewsArticlesByCategoryOrderByCreatedAtDesc(
                    NewsArticleCategory.valueOf(newsArticlesQuery.getCategory())
            );
        }

        if (!newsArticlesQuery.getExcludedPublications().isEmpty()){
            List<Publication> excludedPublications = publicationRepository.findPublicationsByIdNotIn(newsArticlesQuery.getExcludedPublications());
            if (excludedPublications.isEmpty()) {
                throw CustomServiceException.builder()
                        .errorCode(HttpStatus.BAD_REQUEST)
                        .message("Invalid excluded publications")
                        .build();
            }
            if (newsArticlesQuery.isPaginate()) {
                Pageable pageable = PageRequest.of(newsArticlesQuery.getPage(), newsArticlesQuery.getSize());
                return newsArticleRepository.findNewsArticlesByPublicationNotInOrderByCreatedAtDesc(
                        excludedPublications,
                        pageable
                );
            }
            return newsArticleRepository.findNewsArticlesByPublicationNotInOrderByCreatedAtDesc(
                    excludedPublications
            );
        }

        if (newsArticlesQuery.isPaginate()) {
            Pageable pageable = PageRequest.of(newsArticlesQuery.getPage(), newsArticlesQuery.getSize());
            return newsArticleRepository.findAll(pageable).toList();
        }

        return newsArticleRepository.findAll();
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
        return searchTermRepository.findTop5OrderByCreatedAtDesc();
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
        newsArticle.setViews(newsArticle.getViews() + 1);
        newsArticleRepository.save(newsArticle);
        return newsArticle.getViews();
    }
    private void validateNewsArticlesQuery(NewsArticlesQuery newsArticlesQuery) {
        if (
                newsArticlesQuery.isPopular() &&
                        (
                                newsArticlesQuery.getSearchTerm() != null ||
                                        newsArticlesQuery.getCategory() != null ||
                                        newsArticlesQuery.getPublicationId() != null ||
                                        !newsArticlesQuery.getExcludedPublications().isEmpty()
                        )
        ) {
            throw CustomServiceException.builder()
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .message("Invalid query parameters. isPopular cannot be set with any other parameters, i.e searchTerm, category, publicationId, and excludedPublications")
                    .build();
        }

        if (
                newsArticlesQuery.getSearchTerm() != null &&
                        (
                                newsArticlesQuery.getCategory() != null ||
                                        newsArticlesQuery.getPublicationId() != null ||
                                        !newsArticlesQuery.getExcludedPublications().isEmpty()
                        )
        ) {
            throw CustomServiceException.builder()
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .message("Invalid query parameters. searchTerm cannot be set with any other parameters, i.e category, publicationId, and excludedPublications, isPopular")
                    .build();
        }
        if (
                newsArticlesQuery.getCategory() != null &&
                        (
                                newsArticlesQuery.getPublicationId() != null ||
                                        !newsArticlesQuery.getExcludedPublications().isEmpty()
                        )
        ) {
            throw CustomServiceException.builder()
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .message("Invalid query parameters. category cannot be set with any other parameters, i.e publicationId, and excludedPublications, isPopular, searchTerm")
                    .build();
        }
        if (
                newsArticlesQuery.getPublicationId() != null &&
                        (
                                !newsArticlesQuery.getExcludedPublications().isEmpty()
                        )
        ) {
            throw CustomServiceException.builder()
                    .errorCode(HttpStatus.BAD_REQUEST)
                    .message("Invalid query parameters. publicationId cannot be set with excludedPublications, isPopular, searchTerm, category")
                    .build();
        }
    }
}
