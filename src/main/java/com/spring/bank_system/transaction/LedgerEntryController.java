package com.spring.bank_system.transaction;

import com.spring.bank_system.transaction.dto.DepositRequest;
import com.spring.bank_system.transaction.dto.TransactionResponse;
import com.spring.bank_system.transaction.dto.WithdrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class LedgerEntryController {

    private final LedgerEntryService ledgerEntryService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(
            @RequestBody DepositRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ledgerEntryService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(
            @RequestBody WithdrawRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ledgerEntryService.withdraw(request));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>>
    getAccountTransactions(
            @PathVariable UUID accountId
    ) {

        return ResponseEntity.ok(
                ledgerEntryService
                        .getAccountTransactions(accountId)
        );
    }
}