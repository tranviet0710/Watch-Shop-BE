package com.example.WatchShop.controller.product;

import com.example.WatchShop.model.Images;
import com.example.WatchShop.model.Products;
import com.example.WatchShop.model.dto.req.ProductReqDTO;
import com.example.WatchShop.model.dto.res.ProductDetailResDTO;
import com.example.WatchShop.model.dto.res.ProductResDTO;
import com.example.WatchShop.repository.ImageRepository;
import com.example.WatchShop.service.i_service.ProductService;
import com.example.WatchShop.service.i_service.RatingService;
import com.example.WatchShop.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("api/product")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private RatingService ratingService;

    //Lấy danh sách của product
    @GetMapping("/")
    ResponseEntity<?> getAllProduct(@RequestParam(value = "brandId", required = false) Long brandID) {
        List<Products> products = productService.findAllProduct();
        if(brandID != null){
            products = products.stream().filter(x->x.getBrands().getId().equals(brandID)).toList();
        }
        List<ProductDetailResDTO> productDetailResDTOS = products.stream().map(ProductDetailResDTO::new).toList();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", productDetailResDTOS));
    }

    @GetMapping("/top5")
    ResponseEntity<?> getTop5Product() {
        List<Products> products = productService.findAllProduct().stream().filter(x -> x.getSoldQuantity() != null).
                sorted(Comparator.comparing(Products::getSoldQuantity)).limit(5).toList();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", null));
        }
        List<ProductDetailResDTO> productDetailResDTOS = products.stream().map(ProductDetailResDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", productDetailResDTOS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        Optional<Products> products = productService.getProductById(id);
        ProductDetailResDTO productDetailResDTO = new ProductDetailResDTO(products.get());
        List<Products> getSameBrandProducts = productService.getProductsByBrand(productDetailResDTO.getBrandID());
        List<ProductResDTO> getSameBrandProductsRes =  getSameBrandProducts.stream()
                .map(ProductResDTO::new)
                .toList();
        getSameBrandProductsRes.stream().forEach(p-> p.setStar(ratingService.getStarOfProduct(p.getId())));
        productDetailResDTO.setSameBrandProducts(getSameBrandProductsRes);

        return ResponseEntity.ok(productDetailResDTO);
    }

    @PostMapping("/")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductReqDTO products){
        Products products1 = productService.save(products);

        return ResponseEntity.status(HttpStatus.OK).body(products1);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductReqDTO products,@PathVariable("id") Long id){
        Optional<Products> products2 = productService.getProductById(id);
        if (products2.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Products products1 = productService.save(products);

        return ResponseEntity.status(HttpStatus.OK).body(products1);
    }
}
