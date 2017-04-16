package com.github.tadaskay.lunar.logger.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class HalResource {

    @JsonProperty("_links")
    private final List<Link> links = new ArrayList<>();

    public void add(Link link) {
        Assert.notNull(link, "Link must not be null!");
        this.links.add(link);
    }

    public Link getLink(String rel) {
        return links.stream()
            .filter(l -> l.getRel().equals(rel))
            .findAny()
            .orElse(null);
    }

    public List<Link> getLinks() {
        return links;
    }

    public boolean hasLink(String rel) {
        return getLink(rel) != null;
    }

}
