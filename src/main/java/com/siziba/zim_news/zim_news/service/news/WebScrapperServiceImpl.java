package com.siziba.zim_news.zim_news.service.news;

import com.siziba.zim_news.zim_news.dto.publication.PublicationRequest;
import com.siziba.zim_news.zim_news.entity.NewsArticle;
import com.siziba.zim_news.zim_news.entity.Publication;
import com.siziba.zim_news.zim_news.entity.WebScrapperError;
import com.siziba.zim_news.zim_news.repository.NewsArticleRepository;
import com.siziba.zim_news.zim_news.repository.PublicationRepository;
import com.siziba.zim_news.zim_news.repository.WebScrapperErrorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebScrapperServiceImpl implements WebScrapperService {
    private final NewsArticleRepository newsArticleRepository;
    private final WebScrapperErrorRepository webScrapperErrorRepository;
    private final PublicationRepository publicationRepository;

    @Override
    public Optional<List<NewsArticle>> runScrapper() {

        try {
            List<NewsArticle> forTheChronicle = getForTheChronicle();
            log.info("Scrapped for The Chronicle and found {} articles", forTheChronicle != null ? forTheChronicle.size() : 0);
            if (forTheChronicle != null && !forTheChronicle.isEmpty()) {
                newsArticleRepository.saveAll(forTheChronicle);
            }
        }
        catch (Exception e) {
            log.error("Error while scrapping for The Chronicle", e);
            webScrapperErrorRepository.save(WebScrapperError.builder()
                    .publicationName("The Chronicle")
                    .message(e.getMessage())
                    .build());
        }
        return Optional.empty();
    }

    public void deleteOldArticles() {
        newsArticleRepository.deleteByPublishedAtBefore(new java.sql.Date(System.currentTimeMillis() - 50L * 24 * 60 * 60 * 1000));
    }

    public List<NewsArticle> getForTheChronicle() {
        Publication publication = publicationRepository.findByUrl("https://www.chronicle.co.zw/");
        if (publication == null) {
            publication = publicationRepository.save(Publication.builder()
                    .location("Bulawayo, Zimbabwe")
                    .name("The Chronicle")
                    .description("The Chronicle is Bulawayo's most popular daily newspaper. Articles on business, sports, entertainment, opinions, and more.")
                    .url("https://www.chronicle.co.zw/")
                    .build());
        }

        List<String> urlsList = List.of(
                "https://www.chronicle.co.zw/category/s6-demo-section/c37-top-stories/",
                "https://www.chronicle.co.zw/category/s6-demo-section/c38-local-news/",
                "https://www.chronicle.co.zw/category/s6-demo-section/c41-business/",
                "https://www.chronicle.co.zw/category/s6-demo-section/c50-sport/",
                "https://www.chronicle.co.zw/category/s6-demo-section/c43-entertainment/",
                "https://www.chronicle.co.zw/category/s6-demo-section/c45-international-news/",
                "https://www.chronicle.co.zw/category/s6-demo-section/c39-opinion-a-analysis/",
                "https://www.chronicle.co.zw/category/coronavirus-watch/"
        );

        List<NewsArticle> newsArticles = new ArrayList<>();

        SimpleDateFormat articlesDateFormat = new SimpleDateFormat("MMMM d, yyyy");

        Publication finalPublication = publication;

        urlsList.forEach(url -> {
            try {
                Document document = Jsoup.connect(url).get();
                Element postItems = document.selectFirst(".main--content .post--items");

                List<Element> articles = Objects.requireNonNull(postItems).select(".post--item");

                articles.forEach(article -> {
                    Element titleElement = article.selectFirst("h3 a");
                    Element image = article.selectFirst(".post--img img");
                    Element extractElement = article.selectFirst(".post--content p");
                    Element authorElement = article.selectFirst(".post--info .meta a");
                    List<Element> dateElements = article.select(".post--info .meta a");
                    Element dateElement = dateElements.get(dateElements.size() - 1);

                    String title = Objects.requireNonNull(titleElement).text();
                    String articleUrl = Objects.requireNonNull(titleElement).attr("href");
                    String imageUrl = Objects.requireNonNull(image).attr("src");
                    String extract = Objects.requireNonNull(extractElement).text();
                    String author = Objects.requireNonNull(authorElement).text();
                    String publishedAtDateString = Objects.requireNonNull(dateElement).text();

                    java.sql.Date publishedAt;

                    try {
                        publishedAt = new java.sql.Date(articlesDateFormat.parse(publishedAtDateString).getTime());
                    } catch (ParseException e) {
                        log.error("Error while parsing date", e);
                       publishedAt = new java.sql.Date(System.currentTimeMillis());
                    }


                    NewsArticle newsArticle = NewsArticle.builder()
                            .title(title)
                            .articleUrl(articleUrl)
                            .pictureUrl(imageUrl)
                            .extract(extract)
                            .author(author)
                            .publishedAt(publishedAt)
                            .publication(finalPublication)
                            .build();
                    boolean exists = newsArticleRepository.existsByArticleUrl(articleUrl);
                    if (!exists) {
                        newsArticles.add(newsArticle);
                    }
                });

            } catch (IOException e) {
                log.error("Error while scrapping for The Chronicle", e);
                webScrapperErrorRepository.save(WebScrapperError.builder()
                        .publicationName("The Chronicle")
                        .message(e.getMessage())
                        .build());
            }
        });

        return newsArticles;
    }

    public List<NewsArticle> getForTheSundayMail() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("The Sunday Mail")
                .description("The Sunday Mail is Zimbabwe's most popular Sunday newspaper. Articles on business, sports, entertainment, opinions, and more.")
                .url("https://www.sundaymail.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getFor263Chat() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("263Chat")
                .description("263Chat is Zimbabwe's leading online news platform for Zimbabweans at home and in the diaspora for up-to-date news, views, and interviews.")
                .url("https://www.263chat.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForIHarareNews() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("iHarare News")
                .description("iHarare news offers the latest Zimbabwe news, daily news in Zimbabwe, news online, South Africa news, sports news as well as international breaking news.")
                .url("https://iharare.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForMbareNews() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Mbare News")
                .description("Mbare News is a Zimbabwean online news publication that highlights the role of the ghetto in the development of the country.")
                .url("https://mbaretimes.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForMyZimbabweNews() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("My Zimbabwe News")
                .description("My Zimbabwe News is your Zimbabwean Internet Newspaper. We provide you with the latest breaking news, be it Political, Social, Economic, Community or Sports News, from around Zimbabwe and abroad.")
                .url("https://www.myzimbabwe.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForNewsDzeZimbabwe() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Zimbabwe")
                .name("News DzeZimbabwe")
                .description("News DzeZimbabwe aims to provide general news about Zimbabweans in the diaspora.")
                .url("https://newsdzezimbabwe.co.uk/")
                .build();
        return null;
    }

    public List<NewsArticle> getForNewZimbabwe() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Zimbabwe")
                .name("New Zimbabwe")
                .description("NewZimbabwe.com is Zimbabwe's leading online news publication. The platform brings you the latest breaking news, business, showbiz, sports, diaspora and gives you everything you've come to expect and love.")
                .url("https://www.newzimbabwe.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForDailyNews() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Daily News")
                .description("DailyNews is Zimbabwe's most read daily newspaper. Find articles on business, entertainment, sports, technology, and more.")
                .url("https://dailynews.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForZimEye() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("ZimEye")
                .description("ZimEye is a spread out media-network market of professionals in various fields that include arts & journalism as practiced by many of its subscribers scattered across the globe.")
                .url("https://www.zimeye.net/")
                .build();
        return null;
    }

    public List<NewsArticle> getForZimMorningPost() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Zim Morning Post")
                .description("Zim Morning Post is an online news website in Zimbabwe that publishes breaking news in politics, business, sports, entertainment, technology, and more.")
                .url("https://zimmorningpost.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForTheHerald() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("The Herald")
                .description("The Herald is Zimbabwe's most popular daily newspaper. Articles on business, sports, entertainment, opinions, and more.")
                .url("https://www.herald.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForBulawayo24News() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Bulawayo, Zimbabwe")
                .name("Bulawayo24 News")
                .description("Bulawayo24 News is a news website that provides news on politics, business, entertainment, sports, and more.")
                .url("https://bulawayo24.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForZimbabweSituation() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Zimbabwe")
                .name("Zimbabwe Situation")
                .description("Zimbabwe Situation is a news website that provides news on politics, business, entertainment, sports, and more.")
                .url("https://www.zimbabwesituation.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForZimLive() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("ZimLive")
                .description("ZimLive is a news website that provides news on politics, business, entertainment, sports, and more.")
                .url("https://www.zimlive.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForZimNewsNet() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Zim News")
                .description("Zim News is a news website that provides news on politics, business, entertainment, sports, and more.")
                .url("https://zwnews.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getFor3Mob() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Three Men On A Boat")
                .description("Zimbabwe's leading social commentary, news and opinion media site.")
                .url("https://3-mob.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForZimBuzz() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Zim Buzz")
                .description("Latest entertainment and celebrity gossip news from Zimbabwe.")
                .url("https://zimbuzz.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForTheFinancialGazette() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("The Financial Gazette")
                .description("The Financial Gazette is Zimbabwe's leading business and financial newspaper.")
                .url("https://fingaz.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForHMetro() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("H-Metro")
                .description("Articles on news, sports, entertainment, crime, religion, and more.")
                .url("https://www.hmetro.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForZiMetroNews() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("ZiMetro News")
                .description("ZiMetro News is a news website that provides news on politics, business, entertainment, sports, and more.")
                .url("https://www.zimetro.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForTheSundayNews() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Bulawayo, Zimbabwe")
                .name("The Sunday News")
                .description("The Sunday News is Bulawayo's most popular Sunday newspaper. Articles on business, sports, entertainment, opinions, and more.")
                .url("https://www.sundaynews.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForBMetro() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Bulawayo, Zimbabwe")
                .name("B-Metro")
                .description("Articles on news, sports, entertainment, crime, religion, and more.")
                .url("https://www.bmetro.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForVoiceOfAmericaZimbabwe() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Washington, DC, USA")
                .name("Voice of America Zimbabwe")
                .description("News and information from Zimbabwe and around the world. The Voice of America is one of the world's most trusted sources for news and information from the United States and around the world.")
                .url("https://www.voazimbabwe.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForTheInsider() {
PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("The Insider")
                .description("The Insider is a news website that provides news on politics, business, entertainment, sports, and more.")
                .url("https://insiderzim.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForEBusinessWeekly() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("eBusiness Weekly")
                .description("eBusiness Weekly is Zimbabwe's leading business and financial newspaper.")
                .url("https://ebusinessweekly.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForZimbabweStar() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Zimbabwe Star")
                .description("Zimbabwe Star is a news website that provides news on politics, business, entertainment, sports, and more.")
                .url("https://www.zimbabwestar.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForNewsDayZimbabwe() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("NewsDay Zimbabwe")
                .description("NewsDay Zimbabwe is Zimbabwe's leading online news publication for business, sport, entertainment, and more.")
                .url("https://www.newsday.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForTheStandard() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("The Standard")
                .description("The Standard is Zimbabwe's leading Sunday newspaper. Articles on business, sports, entertainment, opinions, and more.")
                .url("https://www.thestandard.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForZimbabweIndependent() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Zimbabwe Independent")
                .description("Zimbabwe Independent is Zimbabwe's leading business and financial newspaper.")
                .url("https://www.theindependent.co.zw/")
                .build();
        return null;
    }

    public List<NewsArticle> getForVantuNews() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Vantu News")
                .description("Vantu News is a news website that provides news on politics, business, entertainment, sports, and more.")
                .url("https://vantunews.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForHarareMetroNews() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Harare, Zimbabwe")
                .name("Harare Metro News")
                .description("Harare Metro News is a news website that provides news on politics, business, entertainment, sports, and more.")
                .url("https://hararemetronews.com/")
                .build();
        return null;
    }

    public List<NewsArticle> getForSouthernEye() {
        PublicationRequest publicationRequest = PublicationRequest.builder()
                .location("Bulawayo, Zimbabwe")
                .name("Southern Eye")
                .description("Southern Eye is Bulawayo's most popular daily newspaper. Articles on business, sports, entertainment, opinions, and more.")
                .url("https://www.southerneye.co.zw/")
                .build();
        return null;
    }


}
