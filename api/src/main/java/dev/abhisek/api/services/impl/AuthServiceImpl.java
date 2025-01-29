package dev.abhisek.api.services.impl;

import dev.abhisek.api.dto.AuthRequest;
import dev.abhisek.api.dto.AuthResponse;
import dev.abhisek.api.dto.IntrospectResponse;
import dev.abhisek.api.dto.RegisterRequest;
import dev.abhisek.api.entities.Role;
import dev.abhisek.api.entities.Token;
import dev.abhisek.api.entities.User;
import dev.abhisek.api.exceptions.BadRequestException;
import dev.abhisek.api.repo.TokenRepository;
import dev.abhisek.api.repo.UserRepository;
import dev.abhisek.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    public void register(RegisterRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.email());
        if (optionalUser.isPresent()) {
            throw new BadRequestException("Email already exists!");
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(Role.valueOf(request.role()));
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("Email not found on server!"));
        if (!user.getPassword().equals(request.password())) {
            throw new BadRequestException("Wrong password!");
        }
        Token token = tokenRepository.findByUser(user);
        if (token == null) {
            token = new Token();
            token.setToken(UUID.randomUUID().toString());
            token.setUser(user);
            tokenRepository.save(token);
        }
        return new AuthResponse(token.getToken());
    }

    @Override
    public IntrospectResponse introspect(String token) {
        Optional<Token> optionalToken = tokenRepository.findById(token);
        return optionalToken
                .map(value -> new IntrospectResponse(true, value.getUser().getRole()))
                .orElseGet(() -> new IntrospectResponse(false, null));
    }

    @Override
    public void revoke(String token) {
        Optional<Token> optionalToken = tokenRepository.findById(token);
        if (optionalToken.isPresent()) {
            tokenRepository.delete(optionalToken.get());
        } else {
            throw new BadRequestException("Token not found!");
        }
    }
}
