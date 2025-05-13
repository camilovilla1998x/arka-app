package com.arka.arka_app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.arka.arka_app.model.mysql.Customer;
import com.arka.arka_app.repository.mysql.CustomerRepository;
import com.arka.arka_app.service.impl.CustomerServiceImpl;

public class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerServiceImpl customerService;

    @BeforeEach //* Incializo el servicio con el mock */
    void setUp(){
        //* Creación del mock del repositorio
        customerRepository = Mockito.mock(CustomerRepository.class);
        
        //* Inyecto el mock en el servicio
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test //* Método getAll()
    void shouldReturnAllCustomers(){
        //? Preparo datos
        Customer c1 = Customer.builder()
                              .id(1L)
                              .name("Camilo")
                              .email("camilo.villa@hotmail.com")
                              .build();

        Customer c2 = Customer.builder()
                              .id(2L)
                              .name("Sebastian")
                              .email("sebastian@pragma.com")
                              .build();
        
        List<Customer> mockList = Arrays.asList(c1, c2);
        when(customerRepository.findAll()).thenReturn(mockList); //? simulo comportamiento

        //? ejecuto método a probar
        List<Customer> result = customerService.getAll();

        System.out.println("Result: " + result);

        //? Assert (verificamos el resultado)
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(c1, c2);

        //?  Verifico que el método se haya llamado una sola vez
        verify(customerRepository, times(1)).findAll();


    }

    @Test //* Método getById() solo si existe
    void shouldReturnCustomerByIdWhenExists(){

        Long id = 1L;
        Customer customer = Customer.builder() 
                                    .id(id)
                                    .name("Camilo")
                                    .email("camilo.villa@hotmail.com")
                                    .build();
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.getById(id);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Camilo");
        verify(customerRepository, times(1)).findById(1L);

    }

    @Test //* Método getById() que no existe
    void shouldReturnEmptyWhenCustomerNotFound() {

        //* Pruebo un id random
        Long id = 99L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

 
        Optional<Customer> result = customerService.getById(id);

        assertThat(result).isEmpty();
        verify(customerRepository, times(1)).findById(id);
    }

    @Test //* Método create()
    void shouldCreateCustomerSuccessfully() {

        Long id = 10L;
        Customer customerToSave = Customer.builder()
                                          .name("Andrew")
                                          .email("Andrew@correo.com")
                                          .phone("3011234567")
                                          .address("Medallo")
                                          .build();
        
        Customer savedCustomer = Customer.builder()
                                         .id(id)
                                         .name("Andrew")
                                         .email("Andrew@correo.com")
                                         .phone("3011234567")
                                         .address("Medallo")
                                         .build(); 

        when(customerRepository.save(customerToSave)).thenReturn(savedCustomer);

        Customer result = customerService.create(customerToSave);
        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getEmail()).isEqualTo("Andrew@correo.com");

        verify(customerRepository, times(1)).save(customerToSave);

    }

    @Test //* Método update() si el cliente existe
    void shouldUpdateCustomerWhenExists() {

        Long id = 1L;

        Customer existingCustomer = Customer.builder()
                                            .id(id)
                                            .name("Cristiano Ronaldo")
                                            .email("Cristiano@AlNassr.com")
                                            .build();

        Customer newData = Customer.builder()
                                   .name("Mbappé")
                                   .email("Kylian@RealMadrid.com")
                                   .phone("3214567890")
                                   .address("Madrid")
                                   .build();

        Customer updatedCustomer = Customer.builder()
                                           .id(id)
                                           .name("Mbappé")
                                           .email("Kylian@RealMadrid.com")
                                           .phone("3214567890")
                                           .address("Madrid")
                                           .build();

        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Optional<Customer> result = customerService.update(id, newData);

        assertThat(result).isNotNull();
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Mbappé"); 

        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test //* Método update() si el cliente NO existe
    void shouldReturnNullWhenUpdatingNonExistingCustomer() {
        
        Long id = 99L;
        Customer newData = Customer.builder()
            .name("No existe")
            .email("no@existe.com")
            .build();
    
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
    
        
        Optional<Customer> result = customerService.update(id, newData);
    
        
        assertThat(result).isEmpty();
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, never()).save(any());
        //* never() Verifica que ese método NO FUE LLAMADO NI UNA SOLA VEZ durante la ejecución del test.
    }

    @Test //* Método delete() si el cliente existe
    void shouldDeleteCustomerWhenExists() {
        
        Long id = 1L;
    
        when(customerRepository.existsById(id)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(id);
    
        
        boolean result = customerService.delete(id);
    
        
        assertThat(result).isTrue();
        verify(customerRepository, times(1)).existsById(id);
        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test //* Método delete() si el cliente NO existe
    void shouldReturnFalseWhenDeletingNonExistingCustomer() {
        
        Long id = 99L;

        when(customerRepository.existsById(id)).thenReturn(false);

        
        boolean result = customerService.delete(id);

        
        assertThat(result).isFalse();
        verify(customerRepository, times(1)).existsById(id);
        verify(customerRepository, never()).deleteById(any());
    }


}
