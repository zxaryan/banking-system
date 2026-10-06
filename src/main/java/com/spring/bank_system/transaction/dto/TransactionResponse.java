package com.spring.bank_system.transaction.dto;

import com.spring.bank_system.transaction.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private UUID id;

    private UUID accountId;

    private String accountNo;

    private TransactionType type;

    private BigDecimal amount;

    private BigDecimal balanceAfter;

    private String description;

    private Instant createdAt;
}