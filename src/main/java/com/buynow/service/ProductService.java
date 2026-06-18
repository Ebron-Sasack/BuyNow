package com.buynow.service;

import com.buynow.dto.ProductDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto, List<MultipartFile> images) throws IOException;
    ProductDto getProductById(Long id);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProductById(Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductByCategory(String category);
    List<ProductDto> getProductByBrand(String brand);
    List<ProductDto> getProductByCategoryAndBrand(String category, String brand);
    List<ProductDto> getProductByName(String name);
    List<ProductDto> getProductByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand,String name);
    ResponseEntity<Resource> getImage(String fileName);
}
