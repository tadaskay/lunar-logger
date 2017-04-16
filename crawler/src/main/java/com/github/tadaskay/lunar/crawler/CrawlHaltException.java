package com.github.tadaskay.lunar.crawler;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.net.URL;

class CrawlHaltException extends RuntimeException {

    public CrawlHaltException(String message) {
        super(message);
    }

    public CrawlHaltException(String message, Throwable cause) {
        super(message, cause);
    }

    static CrawlHaltException registerUrlFailed(URL url, HttpStatus status, HttpStatusCodeException cause) {
        return new CrawlHaltException(
            String.format("Received %d when registering URL %s", status.value(), url),
            cause);
    }

    static CrawlHaltException registerCelebritiesFailed(URL url, HttpStatus status, HttpStatusCodeException cause) {
        return new CrawlHaltException(
            String.format("Received %d when registering celebrities for URL %s", status.value(), url),
            cause);
    }

    static CrawlHaltException registerRemoteKeyFailed(URL url, HttpStatus status, HttpStatusCodeException cause) {
        return new CrawlHaltException(
            String.format("Received %d when registering remote key for URL %s", status.value(), url),
            cause);
    }
}
