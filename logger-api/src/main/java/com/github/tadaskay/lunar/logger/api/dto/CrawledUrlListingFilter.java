package com.github.tadaskay.lunar.logger.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CrawledUrlListingFilter {

    private boolean incompleteOnly;
}
