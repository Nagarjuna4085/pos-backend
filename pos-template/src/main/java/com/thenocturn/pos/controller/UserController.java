package com.thenocturn.pos.controller;


import com.thenocturn.pos.dto.AuthResponse;
import com.thenocturn.pos.dto.LoginRequest;
import com.thenocturn.pos.dto.RegisterRequest;
import com.thenocturn.pos.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
