package com.siziba.zim_news.zim_news.scheduler;

import com.siziba.zim_news.zim_news.service.news.WebScrapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebScrapperScheduler {
    public final WebScrapperService webScrapperService;

    @Scheduled(cron = "0 0 */4 * * *")
    public void runScrapper() {
        log.info("Running web scrapper");
        webScrapperService.runScrapper();
    }
}
