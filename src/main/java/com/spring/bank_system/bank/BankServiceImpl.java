package com.spring.bank_system.bank;

import com.spring.bank_system.bank.dto.BankRequest;
import com.spring.bank_system.bank.dto.BankResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public BankResponse createBank(BankRequest request) {

        if (bankRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Bank code already exists");
        }

        Bank bank = new Bank();

        bank.setName(request.getName());
        bank.setCode(request.getCode().toUpperCase());
        bank.setCity(request.getCity());
        bank.setStatus(BankStatus.ACTIVE);

        Bank savedBank = bankRepository.save(bank);

        return mapToResponse(savedBank);
    }

    @Override
    public List<BankResponse> getAllBanks() {

        return bankRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BankResponse getBankById(UUID id) {

        Bank bank = bankRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Bank not found"));

        return mapToResponse(bank);
    }

    @Override
    public BankResponse updateBank(UUID id, BankRequest request) {

        Bank bank = bankRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Bank not found"));

        bank.setName(request.getName());
        bank.setCity(request.getCity());

        Bank updatedBank = bankRepository.save(bank);

        return mapToResponse(updatedBank);
    }

    @Override
    public void deleteBank(UUID id) {

        if (!bankRepository.existsById(id)) {
            throw new RuntimeException("Bank not found");
        }

        bankRepository.deleteById(id);
    }

    private BankResponse mapToResponse(Bank bank) {

        return new BankResponse(
                bank.getId(),
                bank.getName(),
                bank.getCode(),
                bank.getCity(),
                bank.getStatus(),
                bank.getCreatedAt(),
                bank.getUpdatedAt()
        );
    }
}