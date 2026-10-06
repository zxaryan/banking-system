package com.spring.bank_system.transaction;

import com.spring.bank_system.transaction.dto.TransferRequest;
import com.spring.bank_system.transaction.dto.TransferResponse;

public interface TransferService {

    TransferResponse transfer(TransferRequest request);
}