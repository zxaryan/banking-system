package com.spring.bank_system.user.dto;

import com.spring.bank_system.user.UserRole;

import java.time.Instant;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private UUID customerId;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private boolean enabled;
    private Instant lastLoginAt;
    private Instant createdAt;
    private Instant updatedAt;

    public UserResponse(
            UUID id,
            UUID customerId,
            String email,
            String fullName,
            String phone,
            UserRole role,
            boolean enabled,
            Instant lastLoginAt,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.customerId = customerId;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.role = role;
        this.enabled = enabled;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}