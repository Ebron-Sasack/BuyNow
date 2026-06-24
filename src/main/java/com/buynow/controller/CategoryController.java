package com.buynow.controller;

import com.buynow.dto.CategoryDto;
import com.buynow.payload.ApiResponse;
import com.buynow.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
            CategoryDto categoryDto = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", categoryDto));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
            CategoryDto categoryDto = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", categoryDto));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategory(){
            List<CategoryDto> categories = categoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Found!",categories));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryDto categoryDto){
            CategoryDto addedCategory = categoryService.addCategory(categoryDto);
            return ResponseEntity.ok(new ApiResponse("Sucess!",addedCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody CategoryDto categoryDto){
            CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
            return ResponseEntity.ok(new ApiResponse("Update Sucess",updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id){
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Success",null));
    }
}
