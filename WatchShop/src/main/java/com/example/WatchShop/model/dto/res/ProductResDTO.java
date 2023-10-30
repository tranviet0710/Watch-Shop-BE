package com.example.WatchShop.model.dto.res;

import com.example.WatchShop.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResDTO {
    private long id;
    private String name;
    private Double price;
    private Double discount;
    private String description;
    private Integer quantity;
    private Integer soldQuantity;
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

    private String brands;

    private Set<String> imageSource;

    public ProductResDTO(Products products){
        this.id = products.getId();
        this.name = products.getName();
        this.price = products.getPrice();
        this.discount = products.getDiscount();
        this.description = products.getDescription();
        this.quantity = products.getQuantity();
        this.soldQuantity = products.getSoldQuantity();
        this.model = products.getModel();
        this.color = products.getColor();
        this.origin = products.getOrigin();
        this.warrantyPeriod = products.getWarrantyPeriod();
        this.screenSize = products.getScreenSize();
        this.faceSize = products.getFaceSize();
        this.faceMaterial = products.getFaceMaterial();
        this.frameMaterial = products.getFrameMaterial();
        this.wireMaterial = products.getWireMaterial();
        this.productWeight = products.getProductWeight();
        this.createDate = products.getCreateDate();
        this.updateDate = products.getUpdateDate();
        this.brands = products.getBrands().getName();
        this.imageSource = products.getImages().stream().map(Images::getSource).collect(Collectors.toSet());
    }
}
