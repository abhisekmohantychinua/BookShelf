package dev.abhisek.api.dto;

import dev.abhisek.api.entities.Role;

public record IntrospectResponse(
        boolean valid,
        Role role
) {
}
