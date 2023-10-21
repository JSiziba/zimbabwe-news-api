package com.siziba.zim_news.zim_news.dto.news_article;

import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsArticlesQuery {
    private boolean paginate;
    private int page;
    private int size;
    private boolean isPopular;
    private String searchTerm;
    private String category;
    private UUID publicationId;
    private List<UUID> excludedPublications;
    private DeviceRequest device;
}
