package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.Orders;
import com.example.WatchShop.model.dto.res.UserResDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

     List<Orders> getAllOrder();

     Orders getOrderByUserId(Long id);

     Orders save(Orders orders);

     Optional<Orders> getOrderById(Long id);

     List<UserResDTO> get5BestSellers();
}
