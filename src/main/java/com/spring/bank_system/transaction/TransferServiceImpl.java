package com.spring.bank_system.transaction;

import com.spring.bank_system.account.Account;
import com.spring.bank_system.account.AccountRepository;
import com.spring.bank_system.account.AccountStatus;
import com.spring.bank_system.transaction.dto.TransferRequest;
import com.spring.bank_system.transaction.dto.TransferResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        if (request.getFromAccountId()
                .equals(request.getToAccountId())) {

            throw new IllegalArgumentException(
                    "Source and destination accounts cannot be the same"
            );
        }

        Account fromAccount = accountRepository
                .findById(request.getFromAccountId())
                .orElseThrow(() ->
                        new RuntimeException("Source account not found")
                );

        Account toAccount = accountRepository
                .findById(request.getToAccountId())
                .orElseThrow(() ->
                        new RuntimeException("Destination account not found")
                );

        if (fromAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Source account is not active"
            );
        }

        if (toAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Destination account is not active"
            );
        }

        BigDecimal amount = request.getAmount();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient balance"
            );
        }

        fromAccount.setBalance(
                fromAccount.getBalance().subtract(amount)
        );

        toAccount.setBalance(
                toAccount.getBalance().add(amount)
        );

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        LedgerEntry debit = LedgerEntry.builder()
                .account(fromAccount)
                .type(TransactionType.TRANSFER_DEBIT)
                .amount(amount)
                .balanceAfter(fromAccount.getBalance())
                .description(request.getDescription())
                .build();

        LedgerEntry credit = LedgerEntry.builder()
                .account(toAccount)
                .type(TransactionType.TRANSFER_CREDIT)
                .amount(amount)
                .balanceAfter(toAccount.getBalance())
                .description(request.getDescription())
                .build();

        ledgerEntryRepository.save(debit);
        ledgerEntryRepository.save(credit);

        return TransferResponse.builder()
                .fromAccountId(fromAccount.getId())
                .fromAccountNo(fromAccount.getAccountNo())
                .toAccountId(toAccount.getId())
                .toAccountNo(toAccount.getAccountNo())
                .amount(amount)
                .fromBalance(fromAccount.getBalance())
                .toBalance(toAccount.getBalance())
                .description(request.getDescription())
                .build();
    }
}