package com.buynow.repository;

import com.buynow.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoryName(String categoryName);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String categoryName, String categoryName1);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String brand1);

    Long countByBrandAndName(String brand, String brand1);
}
