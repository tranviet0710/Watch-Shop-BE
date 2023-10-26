package com.example.WatchShop.repository;

import com.example.WatchShop.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {

    List<Products> findAll();

//    Optional<Products> findById(Long id);

//    Products save(Products products);

}
