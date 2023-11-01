package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Brands;
import com.example.WatchShop.model.Images;
import com.example.WatchShop.model.Products;
import com.example.WatchShop.model.dto.req.ProductReqDTO;
import com.example.WatchShop.repository.ImageRepository;
import com.example.WatchShop.repository.ProductRepository;
import com.example.WatchShop.service.i_service.ProductService;
import com.example.WatchShop.util.ImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandServiceImpl brandService;

    @Autowired
    private ImageRepository imageRepository;


    @Override
    public List<Products> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Products> getProductById(Long id) {
        Optional<Products> products = productRepository.findById(id);
        if (products.isPresent()) {


        }
        return productRepository.findById(id);
    }

    @Override
    public List<Products> getTop5Saler() {
        return null;
    }

    @Override
    public Products save(ProductReqDTO products) {
        Brands brands = brandService.findById(products.getIdBrand());

        if (brands == null) {
            return null;
        }
        Images images1 = imageRepository.findBySource(products.getImages().getOriginalFilename());

        if (images1 == null) {
            try {
                String fileName = ImageFile.saveImageFile(products.getImages());
                if (fileName.isEmpty()) {
                    return null;
                }
                Calendar calendar = Calendar.getInstance();
                Date currentDate = new Date(calendar.getTime().getTime());

                Products newProduct = new Products();
                newProduct.setName(products.getName());
                newProduct.setPrice(products.getPrice());
                newProduct.setDescription(products.getDescription());
                newProduct.setDiscount(products.getDiscount());
                newProduct.setQuantity(products.getQuantity());
                newProduct.setSoldQuantity(products.getSoldQuantity());
                newProduct.setModel(products.getModel());
                newProduct.setColor(products.getColor());
                newProduct.setOrigin(products.getOrigin());
                newProduct.setWarrantyPeriod(products.getWarrantyPeriod());
                newProduct.setScreenSize(products.getScreenSize());
                newProduct.setFaceMaterial(products.getFaceMaterial());
                newProduct.setFaceSize(products.getFaceSize());
                newProduct.setFrameMaterial(products.getFrameMaterial());
                newProduct.setWireMaterial(products.getWireMaterial());
                newProduct.setProductWeight(products.getProductWeight());
                newProduct.setCreateDate(currentDate);
                newProduct.setBrands(brands);
                Products products1 = productRepository.save(newProduct);

                Images image = new Images();
                image.setCreateDate(currentDate);
                image.setSource(fileName);
                image.setProducts(products1);

                Images images = imageRepository.save(image);
                if (images == null) {
                    return null;
                }

                products1.setImages(new HashSet<>(Collections.singleton(images)));
                return products1;

            } catch (IOException e) {
                System.err.println("save file error " + e);
                return null;
            }

        }else {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = new Date(calendar.getTime().getTime());

            Products newProduct = new Products();
            newProduct.setId(products.getId());
            newProduct.setName(products.getName());
            newProduct.setPrice(products.getPrice());
            newProduct.setDescription(products.getDescription());
            newProduct.setDiscount(products.getDiscount());
            newProduct.setQuantity(products.getQuantity());
            newProduct.setSoldQuantity(products.getSoldQuantity());
            newProduct.setModel(products.getModel());
            newProduct.setColor(products.getColor());
            newProduct.setOrigin(products.getOrigin());
            newProduct.setWarrantyPeriod(products.getWarrantyPeriod());
            newProduct.setScreenSize(products.getScreenSize());
            newProduct.setFaceMaterial(products.getFaceMaterial());
            newProduct.setFaceSize(products.getFaceSize());
            newProduct.setFrameMaterial(products.getFrameMaterial());
            newProduct.setWireMaterial(products.getWireMaterial());
            newProduct.setProductWeight(products.getProductWeight());
            newProduct.setCreateDate(currentDate);
            newProduct.setBrands(brands);
            Products products1 = productRepository.save(newProduct);
            products1.setImages(new HashSet<>(Collections.singleton(images1)));
            return products1;
        }
    }

    @Override
    public List<Products> getProductsByBrand(Long brandId) {
        return productRepository.findAllByBrandsId(brandId);
    }

    @Override
    public Products update(ProductReqDTO products) {

        Products newProduct = productRepository.getById(products.getId());

        if (newProduct == null) {
            return null;
        }

        Images images = imageRepository.findBySource(products.getImages().getName());

        Brands brands = brandService.findById(products.getIdBrand());

        if (brands == null) {
            return null;
        }
        try {
            if (images == null) {
                String fileName = ImageFile.saveImageFile(products.getImages());
                if (fileName.isEmpty()) {
                    return null;
                }
                newProduct.setImages(new HashSet<>(Collections.singleton(images)));
            }

            Calendar calendar = Calendar.getInstance();
            Date currentDate = new Date(calendar.getTime().getTime());

            newProduct.setName(products.getName());
            newProduct.setPrice(products.getPrice());
            newProduct.setDescription(products.getDescription());
            newProduct.setDiscount(products.getDiscount());
            newProduct.setQuantity(products.getQuantity());
            newProduct.setSoldQuantity(products.getSoldQuantity());
            newProduct.setModel(products.getModel());
            newProduct.setColor(products.getColor());
            newProduct.setOrigin(products.getOrigin());
            newProduct.setWarrantyPeriod(products.getWarrantyPeriod());
            newProduct.setScreenSize(products.getScreenSize());
            newProduct.setFaceMaterial(products.getFaceMaterial());
            newProduct.setFaceSize(products.getFaceSize());
            newProduct.setFrameMaterial(products.getFrameMaterial());
            newProduct.setWireMaterial(products.getWireMaterial());
            newProduct.setProductWeight(products.getProductWeight());
            newProduct.setCreateDate(currentDate);
            newProduct.setBrands(brands);
            Products products2 = productRepository.save(newProduct);

//            Images image = new Images();
//            image.setCreateDate(currentDate);
//            image.setSource(fileName);
//            image.setProducts(products1);
//
//            Images images = imageRepository.save(image);
//            if (images == null){
//                return null;
//            }

//            products1.setImages(new HashSet<>(Collections.singleton(images)));
            return products2;

        } catch (IOException e) {
            System.err.println("save file error " + e);
            return null;
        }
    }
}
