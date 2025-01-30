package dev.abhisek.api.exceptions.handler;

import dev.abhisek.api.dto.ErrorResponse;
import dev.abhisek.api.exceptions.BadRequestException;
import dev.abhisek.api.exceptions.InternalServerErrorException;
import dev.abhisek.api.exceptions.NotFoundException;
import jakarta.persistence.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(List.of(e.getMessage())));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(List.of(e.getMessage())));
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(List.of(e.getMessage())));
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(List.of(e.getMessage())));
    }

    @ExceptionHandler({Exception.class, InternalServerErrorException.class})
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(Exception e) {
        if (e instanceof InternalServerErrorException) {
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse(List.of(e.getMessage())));
        }
        e.printStackTrace();
        return ResponseEntity
                .internalServerError()
                .build();
    }
}
