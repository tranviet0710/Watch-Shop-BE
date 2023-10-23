package com.example.WatchShop.repository;

import com.example.WatchShop.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ImageRepository extends JpaRepository<Images,Long> {

    Set<Images> findAllByProducts(Long id);
}
