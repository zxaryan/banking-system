package com.spring.bank_system.exception;

import com.spring.bank_system.account.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AccountRequest {

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    @NotNull(message = "Bank ID is required")
    private UUID bankId;

    @NotNull(message = "Account type is required")
    private AccountType type;
}