package com.arka.arka_app.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.arka.arka_app.model.mysql.Customer;


public interface CustomerService {

    List<Customer> getAll();

    Optional<Customer> getById(Long id);

    Customer create(Customer customer);

    Optional<Customer> update(Long id, Customer newCustomerData);

    boolean delete(Long id);

    List<Customer> searchByName(String nameFragment);

    List<Customer> getSortedByName();

}
