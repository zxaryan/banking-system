package com.spring.bank_system.user;

import com.spring.bank_system.user.dto.UserRequest;
import com.spring.bank_system.user.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface AppUserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(UUID id);

    UserResponse updateUser(UUID id, UserRequest request);

    void deleteUser(UUID id);
}