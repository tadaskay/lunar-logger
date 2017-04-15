package com.github.tadaskay.lunar.logger.api;

import com.github.tadaskay.lunar.logger.api.dto.*;
import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

@Getter
public class LunarUrl {

    private CrawledUrlRepresentation data;
    private ResponseEntity rawResponse;
    private String id;
    private URI location;

    public static LunarUrl create(CreateCrawledUrlRequest req) {
        ResponseEntity<Void> createRes = LunarLogApi.restTemplate.postForEntity("/urls", req, Void.class);
        if (!createRes.getStatusCode().is2xxSuccessful()) {
            return failed(createRes);
        }

        URI createdResLocation = createRes.getHeaders().getLocation();
        String id = locationToId(createdResLocation);

        LunarUrl apiResource = get(id);
        apiResource.location = createdResLocation;
        apiResource.rawResponse = createRes;
        return apiResource;
    }

    public static LunarUrl get(String id) {
        ResponseEntity<CrawledUrlRepresentation> queryRes = LunarLogApi.restTemplate.getForEntity(
            "/urls/{urlId}", CrawledUrlRepresentation.class, id);
        if (!queryRes.getStatusCode().is2xxSuccessful()) {
            return failed(queryRes);
        }

        LunarUrl apiResource = new LunarUrl();
        apiResource.data = queryRes.getBody();
        apiResource.id = apiResource.data.getId();

        return apiResource;
    }

    public static List<LunarUrl> listIncomplete() {
        CrawledUrlListingFilter filter = CrawledUrlListingFilter.builder()
            .incompleteOnly(true)
            .build();
        return list(filter);
    }

    public static List<LunarUrl> list() {
        return list(CrawledUrlListingFilter.builder().build());
    }

    private static List<LunarUrl> list(CrawledUrlListingFilter filter) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/urls");
        if (filter.isIncompleteOnly()) {
            uriBuilder.queryParam("incomplete", "true");
        }
        ResponseEntity<List<CrawledUrlRepresentation>> res = LunarLogApi.restTemplate.exchange(
            uriBuilder.toUriString(), GET,
            null, new ParameterizedTypeReference<List<CrawledUrlRepresentation>>() {
            });
        return res.getBody().stream()
            .map(rep -> {
                LunarUrl apiResource = new LunarUrl();
                apiResource.data = rep;
                apiResource.id = rep.getId();
                apiResource.rawResponse = res;
                return apiResource;
            })
            .collect(toList());
    }

    public void registerRemoteKey(RegisterRemoteKeyRequest req) {
        LunarLogApi.restTemplate.exchange(
            "/urls/{id}/remote-key",
            PUT, new HttpEntity<>(req), Void.class, data.getId());
    }

    public void registerCelebrities(RegisterCelebritiesRequest req) {
        LunarLogApi.restTemplate.exchange(
            "/urls/{id}/celebrities",
            PUT, new HttpEntity<>(req), Void.class, data.getId());
    }

    private static LunarUrl failed(ResponseEntity<?> res) {
        LunarUrl failed = new LunarUrl();
        failed.rawResponse = res;
        return failed;
    }

    private static String locationToId(URI locationUri) {
        String str = locationUri.toString();
        return str.substring(str.lastIndexOf('/') + 1);
    }
}
