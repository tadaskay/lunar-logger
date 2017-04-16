package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.api.dto.CelebrityRepresentation;
import com.github.tadaskay.lunar.logger.api.dto.CrawledUrlRepresentation;
import com.github.tadaskay.lunar.logger.api.dto.CreateCrawledUrlRequest;
import com.github.tadaskay.lunar.logger.api.dto.RemoteKeyRepresentation;
import com.github.tadaskay.lunar.logger.celebrities.CelebritiesResource;
import com.github.tadaskay.lunar.logger.remotekey.RemoteKeyResource;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/urls")
public class CrawledUrlResource {

    private final UrlRegistrar registrar;

    CrawledUrlResource(UrlRegistrar registrar) {
        this.registrar = registrar;
    }

    @GetMapping
    public List<CrawledUrlRepresentation> list(@RequestParam(name = "incomplete", defaultValue = "false") Boolean incomplete) {
        List<CrawledUrl> urls = incomplete ?
            registrar.listIncomplete()
            : registrar.list();

        return urls.stream()
            .map(CrawledUrlResource::represent)
            .collect(toList());
    }

    @ApiResponses({
        @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Created"),
        @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Already exists")
    })
    @PostMapping
    public ResponseEntity<CrawledUrlRepresentation> create(@Valid @RequestBody CreateCrawledUrlRequest request,
                                                           UriComponentsBuilder uriBuilder) {
        CrawledUrl crawledUrl = registrar.register(request.getUrl());
        URI location = uriBuilder.path("/urls/{id}").buildAndExpand(crawledUrl.getId()).toUri();
        return created(location).body(represent(crawledUrl));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrawledUrlRepresentation> get(@PathVariable("id") String id) {
        return ok(represent(registrar.get(id)));
    }

    private static CrawledUrlRepresentation represent(CrawledUrl entity) {
        CrawledUrlRepresentation rep = new CrawledUrlRepresentation();
        rep.setId(entity.getId());
        rep.setUrl(entity.getUrl());
        rep.setCelebritiesReceived(entity.isCelebritiesReceived());

        List<CelebrityRepresentation> celebrities = entity.getCelebrities().stream()
            .map(CelebritiesResource::represent)
            .collect(toList());
        rep.setCelebrities(celebrities);

        if (entity.getRemoteKey() != null) {
            RemoteKeyRepresentation remoteKey = RemoteKeyResource.represent(entity.getRemoteKey());
            rep.setRemoteKey(remoteKey);
        }

        addLinks(rep);
        return rep;
    }

    private static void addLinks(CrawledUrlRepresentation rep) {
        if (!rep.isCelebritiesReceived()) {
            rep.add(linkTo(methodOn(CelebritiesResource.class).put(rep.getId(), null)).withRel("register-celebrities"));
        }
        if (rep.getRemoteKey() == null) {
            rep.add(linkTo(methodOn(RemoteKeyResource.class).put(rep.getId(), null)).withRel("register-remote-key"));
        }
    }
}
