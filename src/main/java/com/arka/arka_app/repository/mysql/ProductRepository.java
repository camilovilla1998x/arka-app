package com.arka.arka_app.repository.mysql;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arka.arka_app.model.mysql.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    ///* Buscar por nombre o descripción que contenga un término (ignora mayúsculas/minúsculas)
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    //* Ordenar por nombre ascendente
    List<Product> findAllByOrderByNameAsc();

    //* Buscar productos en un rango de precio
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
}
