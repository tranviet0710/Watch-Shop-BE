package com.example.WatchShop.controller.product;

import com.example.WatchShop.model.Products;
import com.example.WatchShop.model.dto.req.ProductReqDTO;
import com.example.WatchShop.model.dto.res.ProductDetailResDTO;
import com.example.WatchShop.model.dto.res.ProductResDTO;
import com.example.WatchShop.service.impl.ProductServiceImpl;
import com.example.WatchShop.service.impl.RatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private RatingServiceImpl ratingService;

    //Lấy danh sách của product
    @GetMapping("/")
    ResponseEntity<?> getAllProduct(@RequestParam(value = "brandId", required = false) Long brandID) {
        List<Products> products = productService.findAllProduct();
        if (brandID != null) {
            products = products.stream().filter(x -> x.getBrands().getId().equals(brandID)).toList();
        }
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", products));
        }
        List<ProductDetailResDTO> productDetailResDTOS = products.stream().map(ProductDetailResDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", productDetailResDTOS));
    }

    @GetMapping("/top5")
    ResponseEntity<?> getTop5Product() {
        List<Products> products = productService.findAllProduct().stream().filter(x -> x.getSoldQuantity() != null).
                sorted(Comparator.comparing(Products::getSoldQuantity)).limit(5).toList();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", products));
        }
        List<ProductDetailResDTO> productDetailResDTOS = products.stream().map(ProductDetailResDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", productDetailResDTOS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        Optional<Products> products = productService.getProductById(id);
        ProductDetailResDTO productDetailResDTO = new ProductDetailResDTO(products.get());
        List<Products> getSameBrandProducts = productService.getProductsByBrand(productDetailResDTO.getBrandID());
        List<ProductResDTO> getSameBrandProductsRes = getSameBrandProducts.stream()
                .map(ProductResDTO::new)
                .toList();
        getSameBrandProductsRes.stream().forEach(p -> p.setStar(ratingService.getStarOfProduct(p.getId())));
        productDetailResDTO.setSameBrandProducts(getSameBrandProductsRes);

        return ResponseEntity.ok(productDetailResDTO);
    }

    @PostMapping("/")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductReqDTO products) {
        System.err.println(products);
        Products products1 = productService.save(products);
        if (products1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(products1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        if (!productService.removeProduct(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status", "error"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success"));
    }
}
