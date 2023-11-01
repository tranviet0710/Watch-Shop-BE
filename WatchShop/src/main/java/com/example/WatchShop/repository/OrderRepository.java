package com.example.WatchShop.repository;

import com.example.WatchShop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Orders findByUsersId(Long id);

    Orders findByOrderCode(String orderCode);
}
