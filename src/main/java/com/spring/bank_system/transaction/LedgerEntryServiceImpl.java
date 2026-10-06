package com.spring.bank_system.transaction;

import com.spring.bank_system.account.Account;
import com.spring.bank_system.account.AccountRepository;
import com.spring.bank_system.account.AccountStatus;

import com.spring.bank_system.transaction.dto.DepositRequest;
import com.spring.bank_system.transaction.dto.TransactionResponse;
import com.spring.bank_system.transaction.dto.WithdrawRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LedgerEntryServiceImpl implements LedgerEntryService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    @Override
    @Transactional
    public TransactionResponse deposit(DepositRequest request) {

        validateAmount(request.getAmount());

        Account account = getActiveAccount(request.getAccountId());

        BigDecimal newBalance = account.getBalance()
                .add(request.getAmount());

        account.setBalance(newBalance);

        accountRepository.save(account);

        LedgerEntry ledgerEntry = LedgerEntry.builder()
                .account(account)
                .type(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .balanceAfter(newBalance)
                .description(request.getDescription())
                .build();

        LedgerEntry savedEntry =
                ledgerEntryRepository.save(ledgerEntry);

        return mapToResponse(savedEntry);
    }

    @Override
    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {

        validateAmount(request.getAmount());

        Account account = getActiveAccount(request.getAccountId());

        BigDecimal currentBalance = account.getBalance();

        if (currentBalance.compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal newBalance = currentBalance
                .subtract(request.getAmount());

        account.setBalance(newBalance);

        accountRepository.save(account);

        LedgerEntry ledgerEntry = LedgerEntry.builder()
                .account(account)
                .type(TransactionType.WITHDRAW)
                .amount(request.getAmount())
                .balanceAfter(newBalance)
                .description(request.getDescription())
                .build();

        LedgerEntry savedEntry =
                ledgerEntryRepository.save(ledgerEntry);

        return mapToResponse(savedEntry);
    }

    @Override
    public List<TransactionResponse> getAccountTransactions(
            UUID accountId
    ) {

        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found");
        }

        return ledgerEntryRepository
                .findByAccountIdOrderByCreatedAtDesc(accountId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Account getActiveAccount(UUID accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException(
                    "Account is not active"
            );
        }

        return account;
    }

    private void validateAmount(BigDecimal amount) {

        if (amount == null) {
            throw new RuntimeException("Amount is required");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(
                    "Amount must be greater than zero"
            );
        }
    }

    private TransactionResponse mapToResponse(
            LedgerEntry entry
    ) {

        return TransactionResponse.builder()
                .id(entry.getId())
                .accountId(entry.getAccount().getId())
                .accountNo(entry.getAccount().getAccountNo())
                .type(entry.getType())
                .amount(entry.getAmount())
                .balanceAfter(entry.getBalanceAfter())
                .description(entry.getDescription())
                .createdAt(entry.getCreatedAt())
                .build();
    }
}