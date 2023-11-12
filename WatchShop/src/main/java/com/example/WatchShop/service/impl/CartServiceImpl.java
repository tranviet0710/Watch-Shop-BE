package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Carts;
import com.example.WatchShop.model.Users;
import com.example.WatchShop.repository.CartRepository;
import com.example.WatchShop.service.i_service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Carts> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Optional<Carts> getCartByUsers(Users users) {
        return cartRepository.findByUsers(users);
    }

    @Override
    public Carts save(Carts carts) {
        return cartRepository.save(carts);
    }
}
