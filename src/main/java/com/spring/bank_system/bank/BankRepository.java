package com.spring.bank_system.bank;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankRepository
        extends JpaRepository<Bank, UUID> {
    boolean existsByCode(String code);
}