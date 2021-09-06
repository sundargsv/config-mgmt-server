package com.sundar.config.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequest extends ResponseStatusException {

    public BadRequest(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public BadRequest(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
