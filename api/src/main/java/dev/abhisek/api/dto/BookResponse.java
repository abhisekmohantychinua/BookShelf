package dev.abhisek.api.dto;

public record BookResponse(
        String id,
        String title,
        String author,
        String description,
        Integer price,
        Integer quantity,
        boolean featured
) {
}
