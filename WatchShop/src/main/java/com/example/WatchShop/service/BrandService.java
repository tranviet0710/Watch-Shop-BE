package com.example.WatchShop.service;

import com.example.WatchShop.model.Brands;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BrandService {
    List<Brands> getAllBrands();
}