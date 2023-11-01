package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.Carts;

import java.util.List;
import java.util.Optional;

public interface CartService {
    public List<Carts> getAllCarts();

    public Carts getCartByUserId(Long id);

    public Optional<Carts> getCartById(Long id);

}
