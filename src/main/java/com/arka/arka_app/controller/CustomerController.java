package com.arka.arka_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arka.arka_app.dto.customer.CustomerRequestDTO;
import com.arka.arka_app.dto.customer.CustomerResponseDTO;
import com.arka.arka_app.service.interfaces.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }

    //* GET /api/customers/{id} -> Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        Optional<CustomerResponseDTO> customer = customerService.getById(id);
        return customer.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    //* POST /api/customers -> Crear nuevo cliente
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO created = customerService.create(dto);
        return ResponseEntity.ok(created);
    }

    //* PUT /api/customers/{id} -> Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id,
                                                              @Valid @RequestBody CustomerRequestDTO dto) {
        Optional<CustomerResponseDTO> updated = customerService.update(id, dto);
        return updated.map(ResponseEntity::ok)
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
    public ResponseEntity<List<CustomerResponseDTO>> searchCustomersByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.searchByName(name));
    }

    //* GET /api/customers/sorted -> Listar todos los clientes ordenados alfabéticamente 
    @GetMapping("/sorted")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomersSortedByName() {
        return ResponseEntity.ok(customerService.getSortedByName());
    }
    
    
    //* -----------------------------------
    //* Métodos de conversión (mapper)
    //* -----------------------------------

    //? Recorderis: ModelMapper es una librería que automáticamente convierte objetos entre clases con
    //? campos similares, en este caso Customer -> CustomerResponseDTO && CustomerRequestDTO -> Customer
    //? Haga de cuenta un semi-espejo
    //? La idea es evitar que el controller y service no convierta manualmente objetos, separando las responsabilidades
    //? Además, es más mantenible y fácil de testear
    /* 
    private CustomerResponseDTO toResponseDTO(Customer customer) {
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    // Convertimos DTO → entidad para guardar o actualizar
    private Customer toEntity(CustomerRequestDTO dto) {
        return modelMapper.map(dto, Customer.class);
    }
    */
}
