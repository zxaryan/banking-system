package com.spring.bank_system.service;

import com.spring.bank_system.entity.Customer;
import com.spring.bank_system.repository.CustomerRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public User createCustomer(@RequestBody Customer customer){
        return (User) customerRepository.save(customer);
    }
}
