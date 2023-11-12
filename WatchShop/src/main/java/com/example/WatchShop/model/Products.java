package com.example.WatchShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "products", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Images> images; // to list

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<OrderDetail> orderDetails;  // to list

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CartDetail> cartDetails;  // to list

}
