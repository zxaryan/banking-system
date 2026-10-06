package com.spring.bank_system.bank.dto;



import com.spring.bank_system.bank.BankStatus;

import java.time.Instant;
import java.util.UUID;

public class BankResponse {

    private UUID id;
    private String name;
    private String code;
    private String city;
    private BankStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public BankResponse(
            UUID id,
            String name,
            String code,
            String city,
            BankStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.city = city;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCity() {
        return city;
    }

    public BankStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}