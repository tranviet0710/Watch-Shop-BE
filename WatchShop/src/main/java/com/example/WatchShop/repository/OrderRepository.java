package com.example.WatchShop.repository;

import com.example.WatchShop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Orders, Long> {
    Orders findByUsersId(Long id);


}
