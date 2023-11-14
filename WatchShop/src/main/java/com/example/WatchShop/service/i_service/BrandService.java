package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.Brands;

import java.util.List;

public interface BrandService {
  List<Brands> getAllBrands();

  Brands save(Brands brands);

  Brands findById(Long id);
}
