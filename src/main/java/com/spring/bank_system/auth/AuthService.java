package com.spring.bank_system.auth;

import com.spring.bank_system.auth.dto.AuthResponse;
import com.spring.bank_system.auth.dto.LoginRequest;
import com.spring.bank_system.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}