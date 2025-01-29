package dev.abhisek.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.abhisek.api.entities.Role;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record IntrospectResponse(
        boolean valid,
        Role role
) {
}
