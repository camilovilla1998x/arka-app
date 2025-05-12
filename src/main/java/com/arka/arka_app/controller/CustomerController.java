package com.arka.arka_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arka.arka_app.model.mysql.Customer;
import com.arka.arka_app.service.interfaces.CustomerService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    //* GET /api/customers -> Listar todos los clientes
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }

    //* GET /api/customers/{id} -> Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //* POST /api/customers -> Crear nuevo cliente
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.create(customer));
    }

    //* PUT /api/customers/{id} -> Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer newCustomerData) {
        return customerService.update(id, newCustomerData)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //* DELETE /api/customers/{id} -> Eliminar cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        return customerService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    //* GET /api/customers/search?name=camilo -> Buscar clientes por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomersByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.searchByName(name));
    }

    //* GET /api/customers/sorted -> Listar todos los clientes ordenados alfabéticamente 
    @GetMapping("/sorted")
    public ResponseEntity<List<Customer>> getCustomersSortedByName() {
        return ResponseEntity.ok(customerService.getSortedByName());
    }
    


    
    

}
