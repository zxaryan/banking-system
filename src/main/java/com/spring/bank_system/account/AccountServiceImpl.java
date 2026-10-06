package com.spring.bank_system.account;

import com.spring.bank_system.account.dto.AccountRequest;
import com.spring.bank_system.account.dto.AccountResponse;
import com.spring.bank_system.bank.Bank;
import com.spring.bank_system.bank.BankRepository;
import com.spring.bank_system.customer.Customer;
import com.spring.bank_system.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            BankRepository bankRepository,
            CustomerRepository customerRepository
    ) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public AccountResponse createAccount(AccountRequest request) {

        Bank bank = bankRepository.findById(request.getBankId())
                .orElseThrow(() ->
                        new RuntimeException("Bank not found"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .filter(c -> c.getDeletedAt() == null)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        if (!customer.getBank().getId().equals(bank.getId())) {
            throw new RuntimeException(
                    "Customer does not belong to this bank"
            );
        }

        Account account = Account.builder()
                .accountNo(generateAccountNumber())
                .customer(customer)
                .bank(bank)
                .type(request.getType())
                .build();

        Account savedAccount = accountRepository.save(account);

        return mapToResponse(savedAccount);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AccountResponse getAccountById(UUID id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        return mapToResponse(account);
    }

    @Override
    public List<AccountResponse> getAccountsByCustomer(UUID customerId) {

        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AccountResponse updateAccount(
            UUID id,
            AccountRequest request
    ) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new RuntimeException(
                    "Closed account cannot be updated"
            );
        }

        account.setType(request.getType());

        Account updatedAccount = accountRepository.save(account);

        return mapToResponse(updatedAccount);
    }

    @Override
    public void closeAccount(UUID id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new RuntimeException("Account already closed");
        }

        if (account.getBalance().signum() != 0) {
            throw new RuntimeException(
                    "Account balance must be zero before closing"
            );
        }

        account.setStatus(AccountStatus.CLOSED);
        account.setClosedAt(Instant.now());

        accountRepository.save(account);
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {
            accountNumber = String.valueOf(
                    100000000000L +
                    (long) (Math.random() * 900000000000L)
            );
        } while (accountRepository.existsByAccountNo(accountNumber));

        return accountNumber;
    }

    private AccountResponse mapToResponse(Account account) {

        return AccountResponse.builder()
                .id(account.getId())
                .accountNo(account.getAccountNo())
                .customerId(account.getCustomer().getId())
                .bankId(account.getBank().getId())
                .type(account.getType())
                .status(account.getStatus())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .openedAt(account.getOpenedAt())
                .closedAt(account.getClosedAt())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }
}