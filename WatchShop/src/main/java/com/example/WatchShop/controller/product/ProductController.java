package com.example.WatchShop.controller.product;

import com.example.WatchShop.model.Products;
import com.example.WatchShop.service.impl.ProductImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductImpl product;

    //Lấy danh sách của product
    @GetMapping("")
    ResponseEntity<?> getAllProduct() {
        List<Products> products = product.findAllProduct();
        return ResponseEntity.ok(products);
    }

    //lấy product theo id (detail)
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        System.out.println(id);
        Optional<Products> products = product.getProductById(id);
        return ResponseEntity.ok(products.orElse(null));
    }
}
