package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Carts;
import com.example.WatchShop.model.Users;
import com.example.WatchShop.repository.CartRepository;
import com.example.WatchShop.service.i_service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;

  @Override
  public Carts getCartByUserId(Long id) {
    return cartRepository.findByUsersId(id);
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
