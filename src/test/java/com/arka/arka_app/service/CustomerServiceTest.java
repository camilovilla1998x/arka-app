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

import com.arka.arka_app.dto.customer.CustomerRequestDTO;
import com.arka.arka_app.dto.customer.CustomerResponseDTO;
import com.arka.arka_app.mapper.CustomerMapper;
import com.arka.arka_app.model.mysql.Customer;
import com.arka.arka_app.repository.mysql.CustomerRepository;
import com.arka.arka_app.service.impl.CustomerServiceImpl;

public class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private CustomerServiceImpl customerService;


    @BeforeEach //* Incializo el servicio con el mock */
    void setUp(){
        //* Creación del mock del repositorio
        customerRepository = Mockito.mock(CustomerRepository.class); // 👈 ¡Esto faltaba!
        customerMapper = Mockito.mock(CustomerMapper.class);
    
        //* Inyecto los mocks en el servicio
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
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

        CustomerResponseDTO dto1 = new CustomerResponseDTO(1L, "Camilo", "camilo.villa@hotmail.com", null, null);
        CustomerResponseDTO dto2 = new CustomerResponseDTO(2L, "Sebastian", "sebastian@pragma.com", null, null);

        //? simulo comportamiento
        when(customerRepository.findAll()).thenReturn(mockList);
        when(customerMapper.toResponseList(mockList)).thenReturn(Arrays.asList(dto1, dto2));

        //? ejecuto método a probar
        List<CustomerResponseDTO> result = customerService.getAll();

        //? Assert (verificamos el resultado)
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dto1, dto2);
        

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
        CustomerResponseDTO dto = new CustomerResponseDTO(id, "Camilo", "camilo.villa@hotmail.com", null, null);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(customerMapper.toResponseDTO(customer)).thenReturn(dto);

        Optional<CustomerResponseDTO> result = customerService.getById(id);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Camilo");
        verify(customerRepository, times(1)).findById(id);

    }

    @Test //* Método getById() que no existe
    void shouldReturnEmptyWhenCustomerNotFound() {

        //* Pruebo un id random
        Long id = 99L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CustomerResponseDTO> result = customerService.getById(id);

        assertThat(result).isEmpty();
        verify(customerRepository, times(1)).findById(id);
    }

    @Test //* Método create()
    void shouldCreateCustomerSuccessfully() {

        Long id = 10L;
        CustomerRequestDTO dto = CustomerRequestDTO.builder()
                                                   .name("Andrew")
                                                   .email("Andrew@correo.com")
                                                   .phone("3011234567")
                                                   .address("Medallo")
                                                   .build();

        Customer entityToSave  = Customer.builder()
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
        
        CustomerResponseDTO expected = new CustomerResponseDTO(id, "Andrew", "Andrew@correo.com", "3011234567", "Medallo");

        when(customerMapper.toEntity(dto)).thenReturn(entityToSave);
        when(customerRepository.save(entityToSave)).thenReturn(savedCustomer);
        when(customerMapper.toResponseDTO(savedCustomer)).thenReturn(expected);

        CustomerResponseDTO result = customerService.create(dto);
        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getEmail()).isEqualTo("Andrew@correo.com");

        verify(customerRepository, times(1)).save(entityToSave);

    }

    @Test //* Método update() si el cliente existe
    void shouldUpdateCustomerWhenExists() {

        Long id = 1L;
        CustomerRequestDTO dto = CustomerRequestDTO.builder()
                                                   .name("Mbappé")
                                                   .email("Kylian@RealMadrid.com")
                                                   .phone("3214567890")
                                                   .address("Madrid")
                                                   .build();

        Customer existingCustomer = Customer.builder()
                                            .id(id)
                                            .name("Cristiano Ronaldo")
                                            .email("Cristiano@AlNassr.com")
                                            .build();

        Customer updatedCustomer = Customer.builder()
                                           .id(id)
                                           .name("Mbappé")
                                           .email("Kylian@RealMadrid.com")
                                           .phone("3214567890")
                                           .address("Madrid")
                                           .build();

        CustomerResponseDTO expected = new CustomerResponseDTO(id, "Mbappé", "Kylian@RealMadrid.com", "3214567890", "Madrid");

        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);
        when(customerMapper.toResponseDTO(updatedCustomer)).thenReturn(expected);

        Optional<CustomerResponseDTO> result = customerService.update(id, dto);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Mbappé");

        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test //* Método update() si el cliente NO existe
    void shouldReturnNullWhenUpdatingNonExistingCustomer() {
        
        Long id = 99L;
        CustomerRequestDTO dto = CustomerRequestDTO.builder()
                                                   .name("No existe")
                                                   .email("no@existe.com")
                                                   .build();

    
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
    
        
        Optional<CustomerResponseDTO> result = customerService.update(id, dto);
    
        
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

    @Test //* Método searchByName()
    void shouldReturnCustomersMatchingName() {
        String name = "Cam";

        Customer c1 = Customer.builder()
                            .id(1L)
                            .name("Camilo")
                            .email("camilo@example.com")
                            .build();

        Customer c2 = Customer.builder()
                            .id(2L)
                            .name("Camila")
                            .email("camila@example.com")
                            .build();

        CustomerResponseDTO dto1 = new CustomerResponseDTO(1L, "Camilo", "camilo@example.com", null, null);
        CustomerResponseDTO dto2 = new CustomerResponseDTO(2L, "Camila", "camila@example.com", null, null);

        List<Customer> mockList = Arrays.asList(c1, c2);

        when(customerRepository.findByNameContainingIgnoreCase(name)).thenReturn(mockList);
        when(customerMapper.toResponseList(mockList)).thenReturn(Arrays.asList(dto1, dto2));

        List<CustomerResponseDTO> result = customerService.searchByName(name);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dto1, dto2);

        verify(customerRepository, times(1)).findByNameContainingIgnoreCase(name);
    }

    @Test //* Método getSortedByName()
    void shouldReturnCustomersSortedByName() {
        Customer c1 = Customer.builder()
                            .id(1L)
                            .name("Ana")
                            .email("ana@example.com")
                            .build();

        Customer c2 = Customer.builder()
                            .id(2L)
                            .name("Zara")
                            .email("zara@example.com")
                            .build();

        CustomerResponseDTO dto1 = new CustomerResponseDTO(1L, "Ana", "ana@example.com", null, null);
        CustomerResponseDTO dto2 = new CustomerResponseDTO(2L, "Zara", "zara@example.com", null, null);

        List<Customer> mockList = Arrays.asList(c1, c2);

        when(customerRepository.findAllByOrderByNameAsc()).thenReturn(mockList);
        when(customerMapper.toResponseList(mockList)).thenReturn(Arrays.asList(dto1, dto2));

        List<CustomerResponseDTO> result = customerService.getSortedByName();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dto1, dto2);

        verify(customerRepository, times(1)).findAllByOrderByNameAsc();
    }





}
