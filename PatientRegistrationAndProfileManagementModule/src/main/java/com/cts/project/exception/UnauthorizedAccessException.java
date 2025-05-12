package com.cts.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Custom exception for unauthorized access attempts */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnauthorizedAccessException extends RuntimeException {

    /** Constructor that accepts a custom error message */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
