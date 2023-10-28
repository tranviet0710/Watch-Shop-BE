package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.CartDetail;
import com.example.WatchShop.repository.CartDetailRepository;
import com.example.WatchShop.service.i_service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<CartDetail> getAllCartDetails() {
        return cartDetailRepository.findAll();
    }

    @Override
    public void save(CartDetail cartDetail) {
        cartDetailRepository.save(cartDetail);
    }
}
