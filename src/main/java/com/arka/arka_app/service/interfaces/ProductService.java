package com.arka.arka_app.service.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.arka.arka_app.dto.product.ProductRequestDTO;
import com.arka.arka_app.dto.product.ProductResponseDTO;

public interface ProductService {

    List<ProductResponseDTO> getAll();
    Optional<ProductResponseDTO> getById(Long id);
    ProductResponseDTO create (ProductRequestDTO dto);
    Optional<ProductResponseDTO> update (Long id, ProductRequestDTO dto);
    boolean delete(Long id);

    List<ProductResponseDTO> searchByNameOrDescription(String term);
    List<ProductResponseDTO> getSortedByName();
    List<ProductResponseDTO> getByPriceRange(BigDecimal min, BigDecimal max);

}
