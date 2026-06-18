package com.buynow.mapper;

import com.buynow.dto.CategoryDto;
import com.buynow.entity.Category;

public class CategoryMapper {

    public static CategoryDto categoryToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category dtoToCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName(),
                null
        );
    }
}
