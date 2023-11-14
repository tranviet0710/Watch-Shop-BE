package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Brands;
import com.example.WatchShop.repository.BrandRepository;
import com.example.WatchShop.service.i_service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
  private final BrandRepository brandRepository;

  @Override
  public List<Brands> getAllBrands() {
    return brandRepository.findAll();
  }

  @Override
  public Brands save(Brands brands) {
    return brandRepository.save(brands);
  }

  @Override
  public Brands findById(Long id) {
    Optional<Brands> brands = brandRepository.findById(id);
    return brands.orElse(null);
  }
}
