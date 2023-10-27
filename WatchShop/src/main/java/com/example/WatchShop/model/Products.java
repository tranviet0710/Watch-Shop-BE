package com.example.WatchShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "images")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double price;
    private double discount;
    private String description;
    private int quantity;
    private String model;
    private String color;
    private String origin;
    private int warrantyPeriod;
    private double screenSize;
    private double faceSize;
    private String faceMaterial;
    private String frameMaterial;
    private String wireMaterial;
    private double productWeight;
    private Date createDate;
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "idBrand")
    private Brands brands;

    @OneToMany(mappedBy = "products", fetch = FetchType.EAGER)
//    @JsonManagedReference
    private Set<Images> images;

    @OneToMany(mappedBy = "products")
    @JsonBackReference
    private Set<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "products")
    @JsonBackReference
    private Set<CartDetail> cartDetails;
}
