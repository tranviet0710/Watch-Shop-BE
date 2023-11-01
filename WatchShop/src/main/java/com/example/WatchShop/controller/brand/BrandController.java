package com.example.WatchShop.controller.brand;

import com.example.WatchShop.model.Brands;
import com.example.WatchShop.service.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/brands")
@CrossOrigin(origins = "*")
public class BrandController {

    @Autowired
    private BrandServiceImpl brandService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBrand() {
        List<Brands> brandsList = brandService.getAllBrands();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", brandsList));
    }

    @PostMapping("/")
    public ResponseEntity<?> addBrand(@RequestBody Brands brands) {
        Brands savedBrands = brandService.save(brands);
        if (savedBrands != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", "Brand added successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "false", "data", "Failed added brand!"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable("id") Long id, @RequestBody Brands brands) {
        Brands brands1 = brandService.findById(id);
        brands1.setName(brands.getName());
        Brands brands2 = brandService.save(brands1);
        if (brands2 != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", "Brand updated successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "false", "data", "Failed updated brand!"));
        }
    }
}
