package com.sundar.config.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFound extends ResponseStatusException {

    public NotFound(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public NotFound(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
