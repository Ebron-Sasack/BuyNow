package com.buynow.controller;

import com.buynow.dto.ProductDto;
import com.buynow.exception.ProductNotFoundException;
import com.buynow.response.ApiResponse;
import com.buynow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestParam("product") String productJson, @RequestParam("images") List<MultipartFile> images) throws IOException {
        ProductDto productDto = new ObjectMapper().readValue(productJson, ProductDto.class);
        ProductDto savedProductDto = productService.addProduct(productDto,images);
       return ResponseEntity.ok(new ApiResponse("Sucess", savedProductDto));
    }

    @GetMapping("/id")
    public ResponseEntity<ApiResponse> getProductById(@RequestParam Long id){
        try {
            ProductDto productDto = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Sucess", productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestParam Long id, @RequestBody ProductDto productDto){
        try {
            ProductDto updatedProductDto = productService.updateProduct(id, productDto);
            return ResponseEntity.ok(new ApiResponse("Sucess", updatedProductDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteProductById(@RequestParam Long id){
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Sucess",null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        try {
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String categoryName){
        try {
            List<ProductDto> products = productService.getProductByCategory(categoryName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brandName){
        try {
            List<ProductDto> products = productService.getProductByBrand(brandName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/categoryAndBrand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brandName){
        try {
            List<ProductDto> products = productService.getProductByCategoryAndBrand(categoryName, brandName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String productName){
        try {
            List<ProductDto> products = productService.getProductByName(productName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/brandAndName")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
        try {
            List<ProductDto> products = productService.getProductByBrandAndName(brandName, productName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/totalProducts")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
        Long totalProducts = productService.countProductByBrandAndName(brandName, productName);
        return ResponseEntity.ok(new ApiResponse("Sucess",totalProducts));
    }

    @GetMapping("/download/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName)
    {
        return productService.getImage(fileName);
    }
}
