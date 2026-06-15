package com.buynow.service;

import com.buynow.dto.ProductDto;
import com.buynow.entity.Product;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto, List<MultipartFile> images) throws IOException;
    Product getProductById(Long id);
    Product updateProduct(ProductDto productDto, Long id);
    void deleteProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand,String name);

    ResponseEntity<Resource> getImage(String fileName);
}
