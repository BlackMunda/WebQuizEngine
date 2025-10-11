package org.example.quizengine.quiz.configurations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice  // This tells Spring: "Hey, I handle errors for ALL controllers"
public class GlobalExceptionHandler {

    // This catches validation errors (like password too short, invalid email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // Make a map to store error messages
        Map<String, String> errors = new HashMap<>();

        // Get all the validation errors and put them in the map
        // Example: {"email": "must be valid email", "password": "size must be at least 5"}
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        // Return 400 Bad Request with the errors
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // This catches your custom "username already taken" error
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {

        // Make a simple error message
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getReason());  // Gets your "username already taken" message

        // Return 400 Bad Request
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }
}