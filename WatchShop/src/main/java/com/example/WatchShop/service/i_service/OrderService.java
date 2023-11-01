package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    public List<Orders> getAllOrder();

    public Orders getOrderByUserId(Long id);

    public Orders save(Orders orders);

    public Optional<Orders> getOrderById(Long id);

}