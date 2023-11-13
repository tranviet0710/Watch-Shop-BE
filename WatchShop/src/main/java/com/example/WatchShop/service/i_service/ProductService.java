package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.Products;
import com.example.WatchShop.model.dto.req.ProductReqDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Products> findAllProduct();

    Optional<Products> getProductById(Long id);

    List<Products> getTop5Saler();

    Products save(ProductReqDTO products);

    Products save(Products products);

    List<Products> getProductsByBrand(Long brandId);

    Products update(ProductReqDTO products);

    boolean removeProduct(Long id);
}
