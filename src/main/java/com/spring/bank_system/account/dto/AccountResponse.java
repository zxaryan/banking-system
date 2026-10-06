package com.spring.bank_system.account.dto;

import com.spring.bank_system.account.AccountStatus;
import com.spring.bank_system.account.AccountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private UUID id;

    private String accountNo;

    private UUID customerId;

    private UUID bankId;

    private AccountType type;

    private AccountStatus status;

    private BigDecimal balance;

    private String currency;

    private Instant openedAt;

    private Instant closedAt;

    private Instant createdAt;

    private Instant updatedAt;
}