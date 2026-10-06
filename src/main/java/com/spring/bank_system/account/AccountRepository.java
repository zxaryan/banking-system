package com.spring.bank_system.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByAccountNo(String accountNo);

    List<Account> findByCustomerId(UUID customerId);

    List<Account> findByBankId(UUID bankId);

    List<Account> findByStatus(AccountStatus status);
}