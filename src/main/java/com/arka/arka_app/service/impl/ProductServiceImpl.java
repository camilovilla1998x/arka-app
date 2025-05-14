package com.arka.arka_app.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arka.arka_app.dto.product.ProductRequestDTO;
import com.arka.arka_app.dto.product.ProductResponseDTO;
import com.arka.arka_app.mapper.ProductMapper;
import com.arka.arka_app.model.mysql.Product;
import com.arka.arka_app.repository.mysql.ProductRepository;
import com.arka.arka_app.service.interfaces.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public List<ProductResponseDTO> getAll() {
       return productMapper.toResponseList(productRepository.findAll());
    }
    @Override
    public Optional<ProductResponseDTO> getById(Long id) {
        return productRepository.findById(id).map(productMapper::toResponseDTO);
    }
    @Override
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return productMapper.toResponseDTO(saved);
    }
    @Override
    public Optional<ProductResponseDTO> update(Long id, ProductRequestDTO dto) {
        return productRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            existing.setPrice(dto.getPrice());
            existing.setStock(dto.getStock());
            Product updated = productRepository.save(existing);
            return productMapper.toResponseDTO(updated);
        });
    }
    @Override
    public boolean delete(Long id) {
        if(!productRepository.existsById(id)) return false;
        productRepository.deleteById(id);
        return true;
    }
    @Override
    public List<ProductResponseDTO> searchByNameOrDescription(String term) {
        List<Product> results = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(term, term);
        return productMapper.toResponseList(results);
    }
    @Override
    public List<ProductResponseDTO> getSortedByName() {
        return productMapper.toResponseList(productRepository.findAllByOrderByNameAsc());
    }
    @Override
    public List<ProductResponseDTO> getByPriceRange(BigDecimal min, BigDecimal max) {
        return productMapper.toResponseList(productRepository.findByPriceBetween(min, max));
    }

    

}
