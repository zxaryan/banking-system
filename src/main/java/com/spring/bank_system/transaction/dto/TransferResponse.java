package com.spring.bank_system.transaction.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponse {

    private UUID fromAccountId;
    private String fromAccountNo;

    private UUID toAccountId;
    private String toAccountNo;

    private BigDecimal amount;

    private BigDecimal fromBalance;
    private BigDecimal toBalance;

    private String description;
}