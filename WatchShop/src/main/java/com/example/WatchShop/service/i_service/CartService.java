package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.Carts;
import com.example.WatchShop.model.Users;

import java.util.Optional;

public interface CartService {
  public Carts getCartByUserId(Long id);

  Optional<Carts> getCartByUsers(Users users);

  Carts save(Carts carts);
}
