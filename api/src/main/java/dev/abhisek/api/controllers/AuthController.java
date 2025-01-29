package dev.abhisek.api.controllers;

import dev.abhisek.api.dto.AuthRequest;
import dev.abhisek.api.dto.AuthResponse;
import dev.abhisek.api.dto.IntrospectResponse;
import dev.abhisek.api.dto.RegisterRequest;
import dev.abhisek.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> token(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestParam String token) {
        return ResponseEntity.ok(authService.introspect(token));
    }

    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(@RequestParam String token) {
        authService.revoke(token);
        return ResponseEntity.noContent().build();
    }
}
