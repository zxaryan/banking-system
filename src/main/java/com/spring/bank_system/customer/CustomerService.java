package com.spring.bank_system.customer;

import com.spring.bank_system.customer.dto.CustomerRequest;
import com.spring.bank_system.customer.dto.CustomerResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(UUID id);

    CustomerResponse updateCustomer(UUID id, CustomerRequest request);

    void deleteCustomer(UUID id);
}