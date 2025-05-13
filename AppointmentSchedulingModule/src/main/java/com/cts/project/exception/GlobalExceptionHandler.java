package com.cts.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
			LOGGER.error("Validation Error - Field: {} | Message: {}", error.getField(), error.getDefaultMessage()); // ✅
																														// LOGGING
																														// HERE
		});

		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolations(ConstraintViolationException ex) {
	    String errorMessage = ex.getConstraintViolations().stream()
	        .map(violation -> violation.getMessage())
	        .findFirst()
	        .orElse("Invalid request data."); // Picks only first error for simplicity

	    LOGGER.error(" Validation Error: {}", errorMessage); // Logs a simple, user-friendly error
	    return Map.of("error", errorMessage);
	}

	
	/** Handles unauthorized access errors */
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 
    @ExceptionHandler(UnauthorizedAccessException.class)
    public Map<String, String> handleUnauthorizedAccess(UnauthorizedAccessException ex) {
        LOGGER.error(" Unauthorized Access: {}", ex.getMessage());
        return Map.of("error", ex.getMessage());
    }
}
