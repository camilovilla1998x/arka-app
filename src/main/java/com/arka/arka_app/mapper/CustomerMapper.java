package com.arka.arka_app.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.arka.arka_app.dto.customer.CustomerRequestDTO;
import com.arka.arka_app.dto.customer.CustomerResponseDTO;
import com.arka.arka_app.model.mysql.Customer;

//? Esta interfaz se va a encargar de convertir los objetos entre Customer y los DTOs (CustomerRequestDTO y CustomerResponseDTO)

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    
    //* Convierte la entidad a DTO de salida (output)
    CustomerResponseDTO toResponseDTO(Customer customer);

    //* Convierte lista de entidades a lista de DTOs
    List<CustomerResponseDTO> toResponseList(List<Customer> customers);

    //* Convierte DTO de entrada a entidad
    @InheritInverseConfiguration(name = "toResponseDTO")
    Customer toEntity(CustomerRequestDTO dto);

}

//? 
