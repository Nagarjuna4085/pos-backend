package com.thenocturn.pos.service.impl;

import com.thenocturn.pos.dto.AuthResponse;
import com.thenocturn.pos.dto.LoginRequest;
import com.thenocturn.pos.dto.RegisterRequest;
import com.thenocturn.pos.entity.User;
import com.thenocturn.pos.repository.UserRepository;
import com.thenocturn.pos.security.JwtService;
import com.thenocturn.pos.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           JwtService jwtService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(),user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .message("User registered successfully")
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail(),user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }
}