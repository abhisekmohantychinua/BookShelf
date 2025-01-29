package dev.abhisek.api.dto;

public record RentResponse(
        String id,
        UserResponse user,
        BookResponse book,
        String rentedAt,
        String returnedAt,
        Integer fine
) {
}
