package com.sundar.config.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InternalServerError extends ResponseStatusException {

    public InternalServerError(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public InternalServerError(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
