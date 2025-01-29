package dev.abhisek.api.services;

import dev.abhisek.api.dto.AuthRequest;
import dev.abhisek.api.dto.AuthResponse;
import dev.abhisek.api.dto.IntrospectResponse;
import dev.abhisek.api.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);

    AuthResponse login(AuthRequest request);

    IntrospectResponse introspect(String token);

    void revoke(String token);
}
