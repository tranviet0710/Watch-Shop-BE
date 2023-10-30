package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Products;
import com.example.WatchShop.repository.ProductRepository;
import com.example.WatchShop.service.i_service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Products> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Products> getProductById(Long id) {
        Optional<Products> products = productRepository.findById(id);
        if (products.isPresent()) {


        }
        return productRepository.findById(id);
    }

    @Override
    public List<Products> getTop5Saler() {
        return null;
    }

    @Override
    public Products save(Products products) {
        return productRepository.save(products);
    }
}
