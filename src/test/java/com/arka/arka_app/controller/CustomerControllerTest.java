package com.arka.arka_app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.arka.arka_app.dto.customer.CustomerRequestDTO;
import com.arka.arka_app.dto.customer.CustomerResponseDTO;
import com.arka.arka_app.service.interfaces.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest( controllers =  CustomerController.class)
public class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerResponseDTO dto1;
    private CustomerResponseDTO dto2;
    private CustomerRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        dto1 = CustomerResponseDTO.builder()
                .id(1L)
                .name("Camilo")
                .email("camilo@email.com")
                .phone("3001234567")
                .address("Calle 123")
                .build();
        dto2 = CustomerResponseDTO.builder()
                .id(2L)
                .name("Alejandra")
                .email("alejandra@email.com")
                .phone("3111234567")
                .address("Carrera 456")
                .build();
        requestDTO = CustomerRequestDTO.builder()
                .name("Nuevo")
                .email("nuevo@email.com")
                .password("pass1234")
                .phone("3003003000")
                .address("Cra 7")
                .build();
    }

    @Test
    @DisplayName("shouldReturnAllCustomersWhenGetAll")
    void shouldReturnAllCustomersWhenGetAll() throws Exception {
        List<CustomerResponseDTO> list = Arrays.asList(dto1, dto2);
        when(customerService.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/customers")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Camilo"));
    }

    @Test
    @DisplayName("shouldReturnCustomerByIdWhenIdExists")
    void shouldReturnCustomerByIdWhenIdExists() throws Exception {
        when(customerService.getById(1L)).thenReturn(Optional.of(dto1));

        mockMvc.perform(get("/api/customers/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("camilo@email.com"));
    }

    @Test
    @DisplayName("shouldReturn404WhenCustomerNotFound")
    void shouldReturn404WhenCustomerNotFound() throws Exception {
        when(customerService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/customers/{id}", 99L))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("shouldCreateCustomerWhenPost")
    void shouldCreateCustomerWhenPost() throws Exception {
        when(customerService.create(any(CustomerRequestDTO.class))).thenReturn(dto1);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Camilo"));
    }

    @Test
    @DisplayName("shouldUpdateCustomerWhenIdExists")
    void shouldUpdateCustomerWhenIdExists() throws Exception {
        when(customerService.update(eq(1L), any(CustomerRequestDTO.class)))
            .thenReturn(Optional.of(dto2));

        mockMvc.perform(put("/api/customers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Alejandra"));
    }

    @Test
    @DisplayName("shouldReturn404WhenUpdateIfNotFound")
    void shouldReturn404WhenUpdateIfNotFound() throws Exception {
        when(customerService.update(eq(99L), any(CustomerRequestDTO.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/customers/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("shouldReturnNoContentWhenDeleteExistingCustomer")
    void shouldReturnNoContentWhenDeleteExistingCustomer() throws Exception {
        when(customerService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/customers/{id}", 1L))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("shouldReturn404WhenDeleteNonExistingCustomer")
    void shouldReturn404WhenDeleteNonExistingCustomer() throws Exception {
        when(customerService.delete(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/customers/{id}", 99L))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("shouldReturnCustomersWhenSearchByName")
    void shouldReturnCustomersWhenSearchByName() throws Exception {
        when(customerService.searchByName("Camilo")).thenReturn(List.of(dto1));

        mockMvc.perform(get("/api/customers/search")
                .param("name", "Camilo"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].name").value("Camilo"));
    }

    @Test
    @DisplayName("shouldReturnSortedCustomersWhenGetSorted")
    void shouldReturnSortedCustomersWhenGetSorted() throws Exception {
        when(customerService.getSortedByName()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/customers/sorted"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }



}
