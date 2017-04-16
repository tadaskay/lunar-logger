package com.github.tadaskay.lunar.logger.ex;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class DuplicateUrlException extends RuntimeException {
}
