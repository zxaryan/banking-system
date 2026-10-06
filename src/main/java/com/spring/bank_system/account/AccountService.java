package com.spring.bank_system.account;

import com.spring.bank_system.account.dto.AccountRequest;
import com.spring.bank_system.account.dto.AccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request);

    List<AccountResponse> getAllAccounts();

    AccountResponse getAccountById(UUID id);

    List<AccountResponse> getAccountsByCustomer(UUID customerId);

    AccountResponse updateAccount(UUID id, AccountRequest request);

    void closeAccount(UUID id);
}