package com.buynow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ElementCollection
    @CollectionTable(name="Product_Image", joinColumns = @JoinColumn(name="product_id"))
    @Column(name="image_url")
    private List<String> productImages;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    public Product(String name, String brand, String description, BigDecimal price, Integer stock, Category category) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }
}
