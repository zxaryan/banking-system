package com.spring.bank_system.customer;

import com.spring.bank_system.bank.Bank;
import com.spring.bank_system.bank.BankRepository;
import com.spring.bank_system.customer.dto.CustomerRequest;
import com.spring.bank_system.customer.dto.CustomerResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BankRepository bankRepository;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            BankRepository bankRepository
    ) {
        this.customerRepository = customerRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Customer email already exists");
        }

        Bank bank = bankRepository.findById(request.getBankId())
                .orElseThrow(() ->
                        new RuntimeException("Bank not found"));

        Customer customer = new Customer();

        customer.setBank(bank);
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setKycStatus(KycStatus.PENDING);

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(UUID id) {

        Customer customer = customerRepository.findById(id)
                .filter(c -> c.getDeletedAt() == null)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return mapToResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(
            UUID id,
            CustomerRequest request
    ) {

        Customer customer = customerRepository.findById(id)
                .filter(c -> c.getDeletedAt() == null)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        customer.setFullName(request.getFullName());
        customer.setPhone(request.getPhone());
        customer.setDateOfBirth(request.getDateOfBirth());

        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {

        Customer customer = customerRepository.findById(id)
                .filter(c -> c.getDeletedAt() == null)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        customer.setDeletedAt(Instant.now());

        customerRepository.save(customer);
    }

    private CustomerResponse mapToResponse(Customer customer) {

        return new CustomerResponse(
                customer.getId(),
                customer.getBank().getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getDateOfBirth(),
                customer.getKycStatus(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}