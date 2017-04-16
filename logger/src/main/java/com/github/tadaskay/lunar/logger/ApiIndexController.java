package com.github.tadaskay.lunar.logger;

import com.github.tadaskay.lunar.logger.api.dto.ApiIndexRepresentation;
import com.github.tadaskay.lunar.logger.url.CrawledUrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
class ApiIndexController {

    @GetMapping("/api")
    public ApiIndexRepresentation index() {
        ApiIndexRepresentation index = new ApiIndexRepresentation();
        index.add(linkTo(methodOn(CrawledUrlResource.class).list(null)).withRel("urls"));
        index.add(linkTo(methodOn(CrawledUrlResource.class).list(true)).withRel("incomplete-urls"));
        return index;
    }

}
