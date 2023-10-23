package com.example.WatchShop.repository;

import com.example.WatchShop.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Carts,Long> {
}
