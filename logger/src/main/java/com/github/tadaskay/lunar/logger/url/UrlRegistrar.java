package com.github.tadaskay.lunar.logger.url;

import java.net.URL;
import java.util.List;

public interface UrlRegistrar {

    List<CrawledUrl> list();

    List<CrawledUrl> listIncomplete();

    CrawledUrl get(String id);

    CrawledUrl register(URL url);
}
