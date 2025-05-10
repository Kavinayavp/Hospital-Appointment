package com.cts.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

    //  Handle Validation Errors (Bad Request - 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());

            // ✅ Log validation errors in console
            LOGGER.warning("Validation Error - Field: " + error.getField() + " | Message: " + error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

    //  Handle Not Found Errors (404)
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(AppointmentNotFoundException ex) {
        LOGGER.warning("Error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //  Handle Unauthorized Access Errors (403)
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        LOGGER.warning("Unauthorized Access Attempt: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    //  Handle Generic Exceptions (500)
	/*
	 * @ExceptionHandler(Exception.class) public ResponseEntity<String>
	 * handleGenericException(Exception ex) { LOGGER.severe("Unexpected Error: " +
	 * ex.getMessage()); return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
	 * body("An unexpected error occurred. Please try again."); }
	 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Unexpected Error");
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("cause", ex.getCause() != null ? ex.getCause().toString() : "No specific cause");

        LOGGER.severe("Unexpected Error: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

}
