package dev.abhisek.api.exceptions.handler;

import dev.abhisek.api.dto.ErrorResponse;
import dev.abhisek.api.exceptions.BadRequestException;
import jakarta.persistence.Entity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(List.of(e.getMessage())));
    }
}
