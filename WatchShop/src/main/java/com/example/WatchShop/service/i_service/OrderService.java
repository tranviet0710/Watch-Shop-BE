package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.Carts;
import com.example.WatchShop.model.OrderDetail;
import com.example.WatchShop.model.Orders;

import java.util.List;

public interface OrderService {

    public List<Orders> getAllOrder();

    public Orders getOrderByUserId(Long id);

    public Orders save(Orders orders);

    public Orders getOrderByOrderCode(String orderCode);

}
