package com.arka.arka_app.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.arka.arka_app.dto.category.CategoryRequestDTO;
import com.arka.arka_app.dto.category.CategoryResponseDTO;

public interface CategoryService {
    List<CategoryResponseDTO> getAll();
    Optional<CategoryResponseDTO> getById(Long id);
    CategoryResponseDTO create(CategoryRequestDTO dto);
    Optional<CategoryResponseDTO> update(Long id, CategoryRequestDTO dto);
    boolean delete(Long id);

    // Métodos personalizados
    List<CategoryResponseDTO> searchByName(String name);
    List<CategoryResponseDTO> getSortedByName();

}
