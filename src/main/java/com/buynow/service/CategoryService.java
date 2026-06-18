package com.buynow.service;

import com.buynow.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto getCategoryById(Long id);
    CategoryDto getCategoryByName(String name);
    List<CategoryDto> getAllCategory();
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
    void deleteCategoryById(Long id);
}
