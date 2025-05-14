package com.arka.arka_app.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;

import com.arka.arka_app.dto.product.ProductRequestDTO;
import com.arka.arka_app.dto.product.ProductResponseDTO;
import com.arka.arka_app.model.mysql.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    //* Convierte la entidad a DTO
    ProductResponseDTO toResponseDTO(Product product);

    //* Convierte lista de entidades a lista de DTOs
    List<ProductResponseDTO> toREsponseList(List<Product> products);

    //* Convierte DTO a entidad
    Product toEntity(ProductRequestDTO dto);

    //* Conversión BigDecimal a Double
    default Double map(BigDecimal value){
        return value != null ? value.doubleValue() : null;
    }

    //* Conversión Double a BigDecimal
     default BigDecimal map(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }
    
}
