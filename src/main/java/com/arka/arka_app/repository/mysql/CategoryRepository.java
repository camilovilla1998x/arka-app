package com.arka.arka_app.repository.mysql;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arka.arka_app.model.mysql.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    //* Buscar por nombre exacto, ignorando mayúsculas
    Optional<Category> findByNameIgnoreCase(String name);

    //* Verificar si ya existe una categoría con ese nombre
    boolean existsByNameIgnoreCase(String name);

    //* Buscar por coincidencia parcial en el nombre
    List<Category> findByNameContainingIgnoreCase(String name);

    //* Listar todas ordenadas alfabéticamente
    List<Category> findAllByOrderByNameAsc();



}
