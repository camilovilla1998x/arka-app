package com.arka.arka_app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arka.arka_app.model.mysql.Customer;
import com.arka.arka_app.repository.mysql.CustomerRepository;
import com.arka.arka_app.service.interfaces.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> update(Long id, Customer newCustomerData) {
         return customerRepository.findById(id).map(customer -> {
            customer.setName(newCustomerData.getName());
            customer.setEmail(newCustomerData.getEmail());
            customer.setPassword(newCustomerData.getPassword());
            customer.setPhone(newCustomerData.getPhone());
            customer.setAddress(newCustomerData.getAddress());
            return customerRepository.save(customer);
        });
    }

    @Override
    public boolean delete(Long id) {
        if (!customerRepository.existsById(id)) return false;
        customerRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Customer> searchByName(String nameFragment) {
        return customerRepository.findByNameContainingIgnoreCase(nameFragment);
    }

    @Override
    public List<Customer> getSortedByName() {
        return customerRepository.findAllByOrderByNameAsc();
    }
    



}
