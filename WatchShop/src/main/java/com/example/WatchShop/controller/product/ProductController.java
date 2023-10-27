package com.example.WatchShop.controller.product;

import com.example.WatchShop.model.Images;
import com.example.WatchShop.model.Products;
import com.example.WatchShop.repository.ImageRepository;
import com.example.WatchShop.service.impl.ProductImpl;
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
    private ProductImpl product;

    @Autowired
    private ImageRepository imageRepository;

    //Lấy danh sách của product
    @GetMapping( "/")
    ResponseEntity<?> getAllProduct(){
        List<Products> products  = product.findAllProduct();
        if (products.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data",null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data",products));
    }

    //lấy product theo id (detail)
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
        Optional<Products> products = product.getProductById(id);
        return ResponseEntity.ok(products.orElse(null));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Products products,@RequestParam("imgFile") MultipartFile imgFile) throws IOException {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(calendar.getTime().getTime());

        Images images =new Images();
        if (!imgFile.isEmpty()) {
            byte[] bytes = imgFile.getBytes();
            Path path = Paths.get("src/main/resources/img/" + imgFile.getOriginalFilename());
            Files.write(path, bytes);
            images.setSource(imgFile.getOriginalFilename());
            images.setCreateDate(currentDate);
        }
        products.setCreateDate(currentDate);
        Products savedProduct = product.save(products);
        if (savedProduct != null) {
            imageRepository.save(images);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data","Product added successfully!"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "Failed to add product!"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody Products updatedProduct) {
        Optional<Products> optionalProduct = product.getProductById(id);
        if (optionalProduct.isPresent()) {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = new Date(calendar.getTime().getTime());
            updatedProduct.setUpdateDate(currentDate);
            Products savedProduct = product.save(updatedProduct);
            if (savedProduct != null) {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data","Product update successful!"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "Failed to add product!"));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
