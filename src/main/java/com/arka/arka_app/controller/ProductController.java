package com.arka.arka_app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arka.arka_app.dto.product.ProductRequestDTO;
import com.arka.arka_app.dto.product.ProductResponseDTO;
import com.arka.arka_app.service.interfaces.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //* GET /api/products -> Listar todos los productos
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAll());
    }

    //* GET /api/products/{id} -> Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Optional<ProductResponseDTO> product = productService.getById(id);
        return product.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    //* POST /api/products -> Crear nuevo producto
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO created = productService.create(dto);
        return ResponseEntity.ok(created);
    }

    //* PUT /api/products/{id} -> Actualizar producto por ID
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, 
                                                            @Valid @RequestBody ProductRequestDTO dto ) {
        Optional<ProductResponseDTO> updated = productService.update(id, dto);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    //* DELETE /api/products/{id} -> Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        return productService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    //* GET /api/products/search?term=... -> Buscar por nombre o descripción
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> search(@RequestParam String term) {
        return ResponseEntity.ok(productService.searchByNameOrDescription(term));
    }

    //* GET /api/products/sorted -> Listar productos ordenados alfabéticamente
    @GetMapping("/sorted")
    public ResponseEntity<List<ProductResponseDTO>> getProductsSortedByName() {
        return ResponseEntity.ok(productService.getSortedByName());
    }

    //* GET /api/products/price-range?min=1000&max=5000 -> Filtrar por rango de precios
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByPriceRange(@RequestParam BigDecimal min,
                                                                            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(productService.getByPriceRange(min, max));
    }
    
    



    
    


}
