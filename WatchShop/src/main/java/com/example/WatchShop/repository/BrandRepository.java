package com.example.WatchShop.repository;

import com.example.WatchShop.model.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brands,Long> {

    List<Brands> findAll();
}
