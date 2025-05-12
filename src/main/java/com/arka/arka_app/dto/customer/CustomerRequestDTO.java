package com.arka.arka_app.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//* Entrada de datos
public class CustomerRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6)
    private String password;

    @Size(max = 20)
    private String phone;

    @NotBlank(message = "La dirección es obligatoria")
    private String address;

}
