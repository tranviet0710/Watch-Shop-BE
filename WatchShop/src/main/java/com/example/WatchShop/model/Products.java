package com.example.WatchShop.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Double price;
    private Double discount;
    private String description;
    private Integer quantity;
    private Integer soldQuantity = 0;
    private String model;
    private String color;
    private String origin;
    private Integer warrantyPeriod;
    private Double screenSize;
    private Double faceSize;
    private String faceMaterial;
    private String frameMaterial;
    private String wireMaterial;
    private Double productWeight;
    private Date createDate;
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "idBrand")
    private Brands brands;

    @OneToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<Images> images;

    @OneToMany(mappedBy = "products")
    private Set<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "products")
    private Set<CartDetail> cartDetails;
}
