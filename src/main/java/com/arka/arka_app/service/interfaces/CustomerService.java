package com.arka.arka_app.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.arka.arka_app.dto.customer.CustomerRequestDTO;
import com.arka.arka_app.dto.customer.CustomerResponseDTO;


public interface CustomerService {

    List<CustomerResponseDTO> getAll();

    Optional<CustomerResponseDTO> getById(Long id);

    CustomerResponseDTO create(CustomerRequestDTO customer);

    Optional<CustomerResponseDTO> update(Long id, CustomerRequestDTO newCustomerData);

    boolean delete(Long id);

    List<CustomerResponseDTO> searchByName(String nameFragment);

    List<CustomerResponseDTO> getSortedByName();

}
