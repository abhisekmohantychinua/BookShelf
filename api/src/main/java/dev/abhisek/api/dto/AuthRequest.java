package dev.abhisek.api.dto;

public record AuthRequest(
        String email,
        String password
) {
}
