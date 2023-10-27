package com.example.WatchShop.service;

import com.example.WatchShop.model.Products;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Products> findAllProduct();

    Optional<Products> getProductById(Long id);

    List<Products> getTop5Saler();

    Products save(Products products);

}
