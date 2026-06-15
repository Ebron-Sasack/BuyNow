package com.buynow.controller;

import com.buynow.dto.ProductDto;
import com.buynow.entity.Product;
import com.buynow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductDto> createProduct(@RequestParam("product") String productJson, @RequestParam("images") List<MultipartFile> images) throws IOException {


        ProductDto productDto = new ObjectMapper().readValue(productJson, ProductDto.class);


        ProductDto savedProductDto= productService.addProduct(productDto,images);
       return ResponseEntity.ok(savedProductDto);
    }

    @GetMapping("/download/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName)
    {
        return productService.getImage(fileName);
    }
}
