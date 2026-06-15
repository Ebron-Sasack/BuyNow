package com.buynow.repository;

import com.buynow.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String category);

    boolean existsByName(String name);
}
