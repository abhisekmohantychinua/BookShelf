package dev.abhisek.api.dto;

public record RegisterRequest(
        String name,
        String email,
        String password,
        String role
) {
}
