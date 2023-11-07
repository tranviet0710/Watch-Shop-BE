package com.example.WatchShop.model.dto.res;

import com.example.WatchShop.model.Products;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStatisticalResDTO {
    private String productName;
    private Integer quantitySold;

    public ProductStatisticalResDTO(Products products) {
        this.productName = products.getName();
        this.quantitySold = products.getSoldQuantity();
    }
}
