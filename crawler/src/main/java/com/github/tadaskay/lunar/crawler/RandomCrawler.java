package com.github.tadaskay.lunar.crawler;

import com.github.tadaskay.lunar.logger.api.LunarLogApi;
import com.github.tadaskay.lunar.logger.api.LunarUrl;
import com.github.tadaskay.lunar.logger.api.dto.CreateCrawledUrlRequest;
import com.github.tadaskay.lunar.logger.api.dto.RegisterCelebritiesRequest;
import com.github.tadaskay.lunar.logger.api.dto.RegisterRemoteKeyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.IntStream;

class RandomCrawler implements Crawler {

    private static final Logger log = LoggerFactory.getLogger(RandomCrawler.class);
    private final Randomizer randomizer;

    public RandomCrawler(RestTemplate restTemplate, Randomizer randomizer) {
        this.randomizer = randomizer;
        LunarLogApi.restTemplate = restTemplate;
    }

    @Scheduled(fixedDelay = 2_000)
    @Override
    public void crawl() throws Exception {
        URL url = nextUrl();
        try {
            doCrawl(url);
        } catch (CrawlHaltException e) {
            log.warn("Skipping current URL {}: {}", url, e.getMessage());
        }
    }

    protected URL nextUrl() {
        try {
            return randomizer.url();
        } catch (MalformedURLException ex) {
            try {
                return new URL("https://goo.gl/m77TgJ");
            } catch (MalformedURLException ignored) {
                return null;
            }
        }
    }

    protected void doCrawl(URL url) {
        LunarUrl urlResource = registerUrl(url);
        sleepRandom();

        registerCelebrities(urlResource);
        sleepRandom();

        registerRemoteKey(urlResource);
    }

    protected LunarUrl registerUrl(URL url) {
        LunarUrl urlResource;
        log.info("Crawling URL {}", url);
        try {
            urlResource = LunarUrl.create(new CreateCrawledUrlRequest(url));
        } catch (HttpStatusCodeException ex) {
            throw CrawlHaltException.registerUrlFailed(
                url, ex.getStatusCode(), ex);
        }
        log.info("Registered URL {}", url);
        return urlResource;
    }

    protected void registerCelebrities(LunarUrl urlResource) {
        RegisterCelebritiesRequest registerCelebritiesRequest = new RegisterCelebritiesRequest();
        int celebrityCount = randomizer.celebrityCount();
        IntStream.range(0, celebrityCount).forEach(i -> {
            RegisterCelebritiesRequest.Entry entry = randomizer.registrableCelebrity();
            registerCelebritiesRequest.withEntry(entry);
        });

        log.info("Registering {} celebrities for url {}",
            celebrityCount, urlResource.getData().getUrl());
        try {
            urlResource.registerCelebrities(registerCelebritiesRequest);
        } catch (HttpStatusCodeException ex) {
            throw CrawlHaltException.registerCelebritiesFailed(
                urlResource.getData().getUrl(), ex.getStatusCode(), ex);
        }
        log.info("Registered celebrities for URL {}", urlResource.getData().getUrl());
    }

    private void registerRemoteKey(LunarUrl urlResource) {
        log.info("Registering remote key for url {}", urlResource.getData().getUrl());
        String key = randomizer.remoteKey();
        try {
            urlResource.registerRemoteKey(new RegisterRemoteKeyRequest(key));
        } catch (HttpStatusCodeException ex) {
            throw CrawlHaltException.registerRemoteKeyFailed(
                urlResource.getData().getUrl(), ex.getStatusCode(), ex);
        }
    }

    private void sleepRandom() {
        try {
            Thread.sleep(randomizer.sleepDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
