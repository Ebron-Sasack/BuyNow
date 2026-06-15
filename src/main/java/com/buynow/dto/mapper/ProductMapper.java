package com.buynow.dto.mapper;

import com.buynow.dto.ProductDto;
import com.buynow.entity.Product;

public class ProductMapper {

    public static ProductDto productToDto(Product product)
    {
         return ProductDto.builder()
                 .name(product.getName())
                 .id(product.getId())
                 .categoryId(product.getCategory().getId())
                 .categoryName(product.getCategory().getName())
                 .productImages(product.getProductImages())
                         .price(product.getPrice())
        .build();
    }

}
