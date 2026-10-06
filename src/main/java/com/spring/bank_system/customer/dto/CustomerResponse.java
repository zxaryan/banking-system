package com.spring.bank_system.customer.dto;

import com.spring.bank_system.customer.KycStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class CustomerResponse {

    private UUID id;
    private UUID bankId;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private KycStatus kycStatus;
    private Instant createdAt;
    private Instant updatedAt;

    public CustomerResponse(
            UUID id,
            UUID bankId,
            String fullName,
            String email,
            String phone,
            LocalDate dateOfBirth,
            KycStatus kycStatus,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.bankId = bankId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.kycStatus = kycStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBankId() {
        return bankId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}