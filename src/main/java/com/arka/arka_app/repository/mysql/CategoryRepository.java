package com.arka.arka_app.repository.mysql;

import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    boolean existsByNameIgnoreCase(String name);

}
