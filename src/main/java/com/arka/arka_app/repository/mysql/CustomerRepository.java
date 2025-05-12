package com.arka.arka_app.repository.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arka.arka_app.model.mysql.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //* Buscar por nombre (conteniendo parte del texto)
    List<Customer> findByNameContainingIgnoreCase(String name);

    //* Listar todos los clientes ordenados alfabéticamente por nombre
    List<Customer> findAllByOrderByNameAsc();

}
