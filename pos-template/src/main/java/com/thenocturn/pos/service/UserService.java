package com.thenocturn.pos.service;

import com.thenocturn.pos.dto.AuthResponse;
import com.thenocturn.pos.dto.LoginRequest;
import com.thenocturn.pos.dto.RegisterRequest;

public interface UserService {

	AuthResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);

}
