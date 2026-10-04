package com.spring.bank_system.controller;

import com.spring.bank_system.entity.Customer;
import com.spring.bank_system.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(Customer customer){
        return ResponseEntity.status(HttpStatus.CREATED).body((Customer) customerService.createCustomer(customer));
    }
}
