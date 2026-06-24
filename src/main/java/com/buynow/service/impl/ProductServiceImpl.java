package com.buynow.service.impl;

import com.buynow.dto.ProductDto;
import com.buynow.exception.AlreadyExistsException;
import com.buynow.mapper.ProductMapper;
import com.buynow.entity.Category;
import com.buynow.entity.Product;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.repository.CategoryRepository;
import com.buynow.repository.ProductRepository;
import com.buynow.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Value("${file.upload-dir}")
    private  String uploadDir;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Override
    public ProductDto addProduct(ProductDto productDto, List<MultipartFile> images) throws IOException {

        if(productExists(productDto.getName(),productDto.getBrand())){
            throw new AlreadyExistsException(productDto.getName() + " " + productDto.getBrand() + " already exists. you may update the product instead!");
        }
        Product product = new Product();
        Category category = Optional.ofNullable(categoryRepository.findByName(productDto.getCategoryName()))
                .orElseGet(()->{
                    Category newCategory = new Category(productDto.getCategoryName());
                    return categoryRepository.save(newCategory);
                });
        product = productRepository.save(createProduct(productDto,category));

        List<String> imagePath = new ArrayList<>();
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        int i=1;
        for(MultipartFile image:images)
        {
            String fileName = product.getName()+"_"+product.getId()+"_"+i+image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
            Path filePath = uploadPath.resolve(fileName);
            image.transferTo(filePath.toFile());
            imagePath.add( apiPrefix + "/products/download/image/" + fileName);
            i++;
        }

        product.setProductImages(imagePath);
        product= productRepository.save(product);
        return ProductMapper.productToDto(product);
    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name,brand);
    }

    private Product createProduct(ProductDto productDto, Category category){
        return new Product(
                productDto.getName(),
                productDto.getBrand(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock(),
                category
        );
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
        return ProductMapper.productToDto(product);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, productDto))
                .map(productRepository::save)
                .orElseThrow(()->new ResourceNotFoundException("Product Not Found"));

        return ProductMapper.productToDto(product);
    }

    private Product updateExistingProduct(Product existingProduct, ProductDto productDto){
        existingProduct.setName(productDto.getName());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setStock(productDto.getStock());

        Category category = categoryRepository.findByName(productDto.getCategoryName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, ()->{
            throw new ResourceNotFoundException("Product Not Found");
        });
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = Optional.of(productRepository.findAll())
                .orElseThrow(
                        ()-> new ResourceNotFoundException("No products found")
                );
        return products.stream()
                .map(ProductMapper::productToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductByCategory(String category) {
        List<Product> products = Optional.ofNullable(productRepository.findByCategoryName(category))
                .orElseThrow(
                        ()-> new ResourceNotFoundException("No products found for category: " + category)
                );
        return products.stream()
                .map(ProductMapper::productToDto)
                .toList();
    }


    @Override
    public List<ProductDto> getProductByBrand(String brand) {
        List<Product> products = Optional.ofNullable(productRepository.findByBrand(brand))
                .orElseThrow(
                        ()->  new ResourceNotFoundException("No products found for brand: " + brand)
                );
        return products.stream()
                .map(ProductMapper::productToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductByCategoryAndBrand(String category, String brand) {
        List<Product> products = Optional.ofNullable(productRepository.findByCategoryNameAndBrand(category,brand))
                .orElseThrow(
                ()-> new ResourceNotFoundException("Product Not Found")
        );
        return products.stream()
                .map(ProductMapper::productToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductByName(String name) {
        List<Product> products = Optional.ofNullable(productRepository.findByName(name)).orElseThrow(
                ()-> new ResourceNotFoundException("No products found for name: " + name)
        );
        return products.stream()
                .map(ProductMapper::productToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductByBrandAndName(String brand, String name) {
        List<Product> products = Optional.ofNullable(productRepository.findByBrandAndName(brand,name))
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Product Not Found")
                );
        return products.stream()
                .map(ProductMapper::productToDto)
                .toList();
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public ResponseEntity<Resource> getImage(String fileName) {

        try
        {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName).normalize();

            if(!Files.exists(filePath))
            {
                throw  new ResourceNotFoundException("File Not found");
            }

            Resource resource = new UrlResource(filePath.toUri());
            if(!resource.exists())
            {
                throw  new ResourceNotFoundException("File Not found");
            }

            String fileType = Files.probeContentType(filePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileType!=null?fileType:"application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"")
                    .body(resource);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error while loading image", e);
        }
    }
}
