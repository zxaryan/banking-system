package com.spring.bank_system.transaction;

import com.spring.bank_system.transaction.dto.TransferRequest;
import com.spring.bank_system.transaction.dto.TransferResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(
            @Valid @RequestBody TransferRequest request
    ) {
        return ResponseEntity.ok(
                transferService.transfer(request)
        );
    }
}