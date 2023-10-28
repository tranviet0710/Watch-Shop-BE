package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Carts;
import com.example.WatchShop.repository.CartRepository;
import com.example.WatchShop.service.i_service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Carts> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Carts getCartByUserId(Long id) {
        return cartRepository.findByUsersId(id);
    }
}
