package com.example.WatchShop.controller.brand;

import com.example.WatchShop.model.Brands;
import com.example.WatchShop.service.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/brands")
@CrossOrigin(origins = "*")
public class BrandController {

    @Autowired
    private BrandServiceImpl brandService;

    @GetMapping("/")
    public ResponseEntity<?> getALlBrand() {
        List<Brands> brandsList = brandService.getAllBrands();
        return ResponseEntity.ok(brandsList);
    }

}
