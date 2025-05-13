package com.arka.arka_app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arka.arka_app.dto.customer.CustomerRequestDTO;
import com.arka.arka_app.dto.customer.CustomerResponseDTO;
import com.arka.arka_app.mapper.CustomerMapper;
import com.arka.arka_app.model.mysql.Customer;
import com.arka.arka_app.repository.mysql.CustomerRepository;
import com.arka.arka_app.service.interfaces.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerResponseDTO> getAll() {
        return customerMapper.toResponseList(customerRepository.findAll());
    }

    @Override
    public Optional<CustomerResponseDTO> getById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toResponseDTO);
    }

    @Override
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        return customerMapper.toResponseDTO(customerRepository.save(customer));
    }

    @Override
    public Optional<CustomerResponseDTO> update(Long id, CustomerRequestDTO dto) {
        return customerRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());
                    existing.setPhone(dto.getPhone());
                    existing.setAddress(dto.getAddress());
                    return customerMapper.toResponseDTO(customerRepository.save(existing));
                });
    }

    @Override
    public boolean delete(Long id) {
        if (!customerRepository.existsById(id)) return false;
        customerRepository.deleteById(id);
        return true;
    }

    @Override
    public List<CustomerResponseDTO> searchByName(String name) {
        return customerMapper.toResponseList(customerRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public List<CustomerResponseDTO> getSortedByName() {
        return customerMapper.toResponseList(customerRepository.findAllByOrderByNameAsc());
    }
    



}
