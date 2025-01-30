package dev.abhisek.api.dto;

public record RentResponse(
        Integer id,
        UserResponse user,
        BookResponse book,
        String rentedAt,
        String returnedAt,
        Integer fine,
        Integer quantity
) {
}
