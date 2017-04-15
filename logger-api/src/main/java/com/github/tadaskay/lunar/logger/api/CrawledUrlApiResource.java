package com.github.tadaskay.lunar.logger.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.GET;

@Getter
@Setter
public class CrawledUrlApiResource {

    private CrawledUrlRepresentation data;
    private ResponseEntity rawResponse;
    private String id;
    private URI location;

    public static CrawledUrlApiResource create(CreateCrawledUrlRequest req) {
        ResponseEntity<Void> createRes = LogApi.restTemplate.postForEntity("/urls", req, Void.class);
        if (!createRes.getStatusCode().is2xxSuccessful()) {
            return failed(createRes);
        }

        URI createdResLocation = createRes.getHeaders().getLocation();
        String id = locationToId(createdResLocation);

        CrawledUrlApiResource apiResource = get(id);
        apiResource.location = createdResLocation;
        apiResource.rawResponse = createRes;
        return apiResource;
    }

    public static CrawledUrlApiResource get(String id) {
        ResponseEntity<CrawledUrlRepresentation> queryRes = LogApi.restTemplate.getForEntity(
            "/urls/{urlId}", CrawledUrlRepresentation.class, id);
        if (!queryRes.getStatusCode().is2xxSuccessful()) {
            return failed(queryRes);
        }

        CrawledUrlApiResource apiResource = new CrawledUrlApiResource();
        apiResource.data = queryRes.getBody();
        apiResource.id = apiResource.data.getId();

        return apiResource;
    }

    public static List<CrawledUrlApiResource> listIncomplete() {
        CrawledUrlListingFilter filter = CrawledUrlListingFilter.builder()
            .incompleteOnly(true)
            .build();
        return list(filter);
    }

    public static List<CrawledUrlApiResource> list() {
        return list(CrawledUrlListingFilter.builder().build());
    }

    private static List<CrawledUrlApiResource> list(CrawledUrlListingFilter filter) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/urls");
        if (filter.isIncompleteOnly()) {
            uriBuilder.queryParam("incomplete", "true");
        }
        ResponseEntity<List<CrawledUrlRepresentation>> res = LogApi.restTemplate.exchange(
            uriBuilder.toUriString(), GET,
            null, new ParameterizedTypeReference<List<CrawledUrlRepresentation>>() {
            });
        return res.getBody().stream()
            .map(rep -> {
                CrawledUrlApiResource apiResource = new CrawledUrlApiResource();
                apiResource.setData(rep);
                apiResource.setId(rep.getId());
                apiResource.setRawResponse(res);
                return apiResource;
            })
            .collect(toList());
    }

    private static CrawledUrlApiResource failed(ResponseEntity<?> res) {
        CrawledUrlApiResource failed = new CrawledUrlApiResource();
        failed.rawResponse = res;
        return failed;
    }

    private static String locationToId(URI locationUri) {
        String str = locationUri.toString();
        return str.substring(str.lastIndexOf('/') + 1);
    }
}
