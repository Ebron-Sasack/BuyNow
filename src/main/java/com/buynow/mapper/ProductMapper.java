package com.buynow.mapper;

import com.buynow.dto.ProductDto;
import com.buynow.entity.Product;

public class ProductMapper {

    public static ProductDto productToDto(Product product)
    {
         return ProductDto.builder()
                 .id(product.getId())
                 .name(product.getName())
                 .brand(product.getBrand())
                 .description(product.getDescription())
                 .price(product.getPrice())
                 .stock(product.getStock())
                 .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                 .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                 .productImages(product.getProductImages())
                 .build();
    }

    public static Product DtoToProduct(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .brand(productDto.getBrand())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .productImages(productDto.getProductImages())
                .build();
    }

}
