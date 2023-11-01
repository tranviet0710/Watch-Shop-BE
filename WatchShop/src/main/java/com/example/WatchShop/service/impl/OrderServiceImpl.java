package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Orders;
import com.example.WatchShop.repository.OrderRepository;
import com.example.WatchShop.service.i_service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Orders> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Orders getOrderByUserId(Long id) {
        return orderRepository.findByUsersId(id);
    }

    @Override
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public Optional<Orders> getOrderById(Long id) {
        return orderRepository.findById(id);
    }



}
