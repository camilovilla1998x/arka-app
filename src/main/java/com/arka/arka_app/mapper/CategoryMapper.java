package com.arka.arka_app.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.arka.arka_app.dto.category.CategoryRequestDTO;
import com.arka.arka_app.dto.category.CategoryResponseDTO;
import com.arka.arka_app.model.mysql.Category;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toResponseDTO(Category category);
    List<CategoryResponseDTO> toResponseList(List<Category> categories);
    @InheritInverseConfiguration(name = "toResponseDTO")
    Category toEntity(CategoryRequestDTO dto);

}
