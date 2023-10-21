package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.entity.NewsArticle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebScrapperServiceImpl implements WebScrapperService {
    @Override
    public Optional<List<NewsArticle>> runScrapper() {
        return Optional.empty();
    }
}
