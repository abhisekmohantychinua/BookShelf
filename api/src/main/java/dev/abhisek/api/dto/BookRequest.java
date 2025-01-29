package dev.abhisek.api.dto;

public record BookRequest(
        String title,
        String author,
        String description,
        Integer price,
        Integer quantity
) {
}
