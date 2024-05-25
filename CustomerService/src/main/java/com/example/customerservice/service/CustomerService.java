package com.example.customerservice.service;

import com.example.customerservice.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer getCustomerByUsername(String username);

    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);

    List<Customer> getAllCustomer();
}
