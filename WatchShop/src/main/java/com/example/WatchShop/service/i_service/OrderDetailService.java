package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.CartDetail;
import com.example.WatchShop.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    public List<OrderDetail> getAllOrderDetails();

    OrderDetail save(OrderDetail orderDetail);

}
