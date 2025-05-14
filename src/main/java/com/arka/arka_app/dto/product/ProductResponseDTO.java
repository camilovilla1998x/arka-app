package com.arka.arka_app.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;

}
