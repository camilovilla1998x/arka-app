package com.arka.arka_app.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

//* Salida de datos
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
