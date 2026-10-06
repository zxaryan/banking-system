package com.spring.bank_system.account;

import com.spring.bank_system.account.dto.AccountRequest;
import com.spring.bank_system.account.dto.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
           @Valid @RequestBody AccountRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {

        return ResponseEntity.ok(
                accountService.getAllAccounts()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                accountService.getAccountById(id)
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByCustomer(
            @PathVariable UUID customerId
    ) {
        return ResponseEntity.ok(
                accountService.getAccountsByCustomer(customerId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable UUID id,
            @RequestBody AccountRequest request
    ) {
        return ResponseEntity.ok(
                accountService.updateAccount(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> closeAccount(
            @PathVariable UUID id
    ) {
        accountService.closeAccount(id);

        return ResponseEntity.noContent().build();
    }
}