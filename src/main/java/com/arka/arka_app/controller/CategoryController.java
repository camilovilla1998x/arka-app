package com.arka.arka_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arka.arka_app.dto.category.CategoryRequestDTO;
import com.arka.arka_app.dto.category.CategoryResponseDTO;
import com.arka.arka_app.service.interfaces.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //* GET /api/categories -> Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    //* GET /api/categories/{id} -> Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        Optional<CategoryResponseDTO> category = categoryService.getById(id);
        return category.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    //* POST /api/categories -> Crear nueva categoría
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid 
                                                              @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.create(dto);
        return ResponseEntity.ok(created);
    }

    //* PUT /api/categories/{id} -> Actualizar categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id,
                                                              @Valid 
                                                              @RequestBody CategoryRequestDTO dto) {
        Optional<CategoryResponseDTO> updated = categoryService.update(id, dto);
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    //* DELETE /api/categories/{id} -> Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return categoryService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    //* GET /api/categories/search?name=...
    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponseDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.searchByName(name));
    }

    //* GET /api/categories/sorted
    @GetMapping("/sorted")
    public ResponseEntity<List<CategoryResponseDTO>> getSorted() {
        return ResponseEntity.ok(categoryService.getSortedByName());
    }

}
