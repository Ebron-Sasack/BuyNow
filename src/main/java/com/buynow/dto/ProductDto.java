package com.buynow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer stock;

    private Long categoryId;
    private String categoryName;

    private List<String> productImages;

    private List<ReviewDto> reviews;
}