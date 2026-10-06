package com.spring.bank_system.transaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class WithdrawRequest {

    private UUID accountId;

    private BigDecimal amount;

    private String description;
}