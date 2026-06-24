package com.buynow.controller;

import com.buynow.dto.ProductDto;
import com.buynow.payload.ApiResponse;
import com.buynow.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

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
            ProductDto productDto = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Sucess", productDto));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestParam Long id, @RequestBody ProductDto productDto){
            ProductDto updatedProductDto = productService.updateProduct(id, productDto);
            return ResponseEntity.ok(new ApiResponse("Sucess", updatedProductDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteProductById(@RequestParam Long id){
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Sucess",null));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String categoryName){
            List<ProductDto> products = productService.getProductByCategory(categoryName);
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
    }

    @GetMapping("/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brandName){
            List<ProductDto> products = productService.getProductByBrand(brandName);
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
    }

    @GetMapping("/categoryAndBrand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brandName){
            List<ProductDto> products = productService.getProductByCategoryAndBrand(categoryName, brandName);
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String productName){
            List<ProductDto> products = productService.getProductByName(productName);
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
    }

    @GetMapping("/brandAndName")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
            List<ProductDto> products = productService.getProductByBrandAndName(brandName, productName);
            return ResponseEntity.ok(new ApiResponse("Sucess",products));
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

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
