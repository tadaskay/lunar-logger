package com.github.tadaskay.lunar.logger.ex;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ResponseStatus(UNPROCESSABLE_ENTITY)
public class AlreadyCrawledException extends RuntimeException {
}
