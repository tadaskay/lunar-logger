package com.github.tadaskay.lunar.logger.url;

import com.github.tadaskay.lunar.logger.api.CelebrityRepresentation;
import com.github.tadaskay.lunar.logger.api.CrawledUrlRepresentation;
import com.github.tadaskay.lunar.logger.api.CreateCrawledUrlRequest;
import com.github.tadaskay.lunar.logger.api.RemoteKeyRepresentation;
import com.github.tadaskay.lunar.logger.celebrities.CelebritiesResource;
import com.github.tadaskay.lunar.logger.remotekey.RemoteKeyResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/urls")
class CrawledUrlResource {

    private final CrawledUrlRepository repository;

    public CrawledUrlResource(CrawledUrlRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CrawledUrlRepresentation> list(@RequestParam(name = "incomplete", defaultValue = "false") boolean incomplete) {
        List<CrawledUrl> urls = incomplete ?
            repository.findAllIncomplete()
            : repository.findAll();

        return urls.stream()
            .map(CrawledUrlResource::represent)
            .collect(toList());
    }

    @PostMapping
    public ResponseEntity<CrawledUrlRepresentation> create(@Valid @RequestBody CreateCrawledUrlRequest request,
                                                           UriComponentsBuilder uriBuilder) {
        if (repository.crawledUrlExists(request.getUrl())) {
            return new ResponseEntity<>(CONFLICT);
        }

        CrawledUrl crawledUrl = new CrawledUrl(request.getUrl());
        crawledUrl = repository.save(crawledUrl);

        URI location = uriBuilder.path("/urls/{id}").buildAndExpand(crawledUrl.getId()).toUri();
        return created(location).body(represent(crawledUrl));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrawledUrlRepresentation> get(@PathVariable("id") String id) {
        return ok(represent(repository.requireOne(id)));
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

        return rep;
    }
}
