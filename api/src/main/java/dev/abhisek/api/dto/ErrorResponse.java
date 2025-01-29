package dev.abhisek.api.dto;

import java.util.List;

public record ErrorResponse(
        List<String> messages
) {
}
