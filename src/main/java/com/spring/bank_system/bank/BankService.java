package com.spring.bank_system.bank;

import com.spring.bank_system.bank.dto.BankRequest;
import com.spring.bank_system.bank.dto.BankResponse;

import java.util.List;
import java.util.UUID;

public interface BankService {

    BankResponse createBank(BankRequest request);

    List<BankResponse> getAllBanks();

    BankResponse getBankById(UUID id);

    BankResponse updateBank(UUID id, BankRequest request);

    void deleteBank(UUID id);
}