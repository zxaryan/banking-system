package com.spring.bank_system.transaction;
import com.spring.bank_system.transaction.dto.*;


import java.util.List;
import java.util.UUID;

public interface LedgerEntryService {

    TransactionResponse deposit(DepositRequest request);

    TransactionResponse withdraw(WithdrawRequest request);

    List<TransactionResponse> getAccountTransactions(
            UUID accountId
    );
}