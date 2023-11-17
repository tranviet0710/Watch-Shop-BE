package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.CartDetail;
import com.example.WatchShop.repository.CartDetailRepository;
import com.example.WatchShop.service.i_service.CartDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {
  private final CartDetailRepository cartDetailRepository;

  @Override
  public CartDetail save(CartDetail cartDetail) {
    return cartDetailRepository.save(cartDetail);
  }

  @Override
  public void remove(CartDetail cartDetail) {
    cartDetailRepository.deleteById(cartDetail.getId());
  }
}
