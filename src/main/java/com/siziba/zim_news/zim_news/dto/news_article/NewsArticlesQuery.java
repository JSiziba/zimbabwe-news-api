package com.siziba.zim_news.zim_news.dto.news_article;

import com.siziba.zim_news.zim_news.dto.device.DeviceRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class NewsArticlesQuery {
    private boolean paginate = false;
    private int page;
    private int size;
    private boolean isPopular;
    private String searchTerm;
    private String category;
    private UUID publicationId;
    private List<UUID> excludedPublications;
    private DeviceRequest device;

    public NewsArticlesQuery(){
        this.paginate = false;
        this.page = 1;
        this.size = 10;
        this.isPopular = false;
        this.searchTerm = null;
        this.category = null;
        this.publicationId = null;
        this.excludedPublications = List.of();
        this.device = null;
    }
}
