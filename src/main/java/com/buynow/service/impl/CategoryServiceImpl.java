package com.buynow.service.impl;

import com.buynow.dto.CategoryDto;
import com.buynow.entity.Category;
import com.buynow.exception.AlreadyExistsException;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.mapper.CategoryMapper;
import com.buynow.repository.CategoryRepository;
import com.buynow.repository.ProductRepository;
import com.buynow.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category Not Found!"));

        return CategoryMapper.categoryToDto(category);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        Category category = Optional.ofNullable(categoryRepository.findByName(name)).orElseThrow(()->
                new ResourceNotFoundException("Category Not Found"));
        return CategoryMapper.categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::categoryToDto)
                .toList();

    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category addedCategory = Optional.of(categoryDto).filter(c-> !categoryRepository.existsByName(c.getName()))
                .map(CategoryMapper::dtoToCategory)
                .map(categoryRepository::save)
                .orElseThrow(()-> new AlreadyExistsException(categoryDto.getName() + " Already Exists"));
        return CategoryMapper.categoryToDto(addedCategory);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category Not Found"));

        category.setName(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category Not Found"));
        if (!productRepository.findByCategoryName(category.getName()).isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete category because products exist in it");
        }
        categoryRepository.deleteById(id);
    }
}
