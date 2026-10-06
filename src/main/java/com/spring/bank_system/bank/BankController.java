package com.spring.bank_system.bank;

import com.spring.bank_system.bank.dto.BankRequest;
import com.spring.bank_system.bank.dto.BankResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<BankResponse> createBank(
            @RequestBody BankRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bankService.createBank(request));
    }

    @GetMapping
    public ResponseEntity<List<BankResponse>> getAllBanks() {

        return ResponseEntity.ok(
                bankService.getAllBanks()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankResponse> getBankById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                bankService.getBankById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankResponse> updateBank(
            @PathVariable UUID id,
            @RequestBody BankRequest request
    ) {
        return ResponseEntity.ok(
                bankService.updateBank(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(
            @PathVariable UUID id
    ) {
        bankService.deleteBank(id);

        return ResponseEntity.noContent().build();
    }
}