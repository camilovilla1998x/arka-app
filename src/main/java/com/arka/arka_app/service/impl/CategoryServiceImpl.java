package com.arka.arka_app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.arka.arka_app.dto.category.CategoryRequestDTO;
import com.arka.arka_app.dto.category.CategoryResponseDTO;
import com.arka.arka_app.mapper.CategoryMapper;
import com.arka.arka_app.model.mysql.Category;
import com.arka.arka_app.repository.mysql.CategoryRepository;
import com.arka.arka_app.service.interfaces.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public List<CategoryResponseDTO> getAll() {
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }


    @Override
    public Optional<CategoryResponseDTO> getById(Long id) {
        return categoryRepository.findById(id).map(categoryMapper::toResponseDTO);
    }


    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        //* Validación: si ya existe el nombre, lanzar excepción
        if (categoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toResponseDTO(categoryRepository.save(category));
    }


    @Override
    public Optional<CategoryResponseDTO> update(Long id, CategoryRequestDTO dto) {
        return categoryRepository.findById(id)
                                 .map(existing -> {
                                    existing.setName(dto.getName());
                                    Category updated = categoryRepository.save(existing);
                                    return categoryMapper.toResponseDTO(updated);
                                });
    }


    @Override
    public boolean delete(Long id) {
        if (!categoryRepository.existsById(id)) return false;
        categoryRepository.deleteById(id);
        return true;
    }

    @Override
    public List<CategoryResponseDTO> searchByName(String name) {
        return categoryMapper.toResponseList(categoryRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public List<CategoryResponseDTO> getSortedByName() {
        return categoryMapper.toResponseList(categoryRepository.findAllByOrderByNameAsc());
    }

}
