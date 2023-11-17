package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Brands;
import com.example.WatchShop.model.Images;
import com.example.WatchShop.model.Products;
import com.example.WatchShop.model.dto.req.ProductReqDTO;
import com.example.WatchShop.repository.ImageRepository;
import com.example.WatchShop.repository.ProductRepository;
import com.example.WatchShop.service.i_service.ProductService;
import com.example.WatchShop.util.ImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final BrandServiceImpl brandService;
  private final ImageRepository imageRepository;

  @Override
  public List<Products> findAllProduct() {
    return productRepository.findAll();
  }

  @Override
  public Optional<Products> getProductById(Long id) {
    return productRepository.findById(id);
  }

  @Override
  public Products save(ProductReqDTO products) {
    Brands brands = brandService.findById(products.getIdBrand());
    if (brands == null) {
      return null;
    }

    Calendar calendar = Calendar.getInstance();
    Date currentDate = new Date(calendar.getTime().getTime());

    if (products.getId() == null) {
      //addProduct
      try {
        String fileName = ImageFile.saveImageFile(products.getImages());
        if (fileName.isEmpty()) {
          return null;
        }
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
        newProduct.setUpdateDate(currentDate);
        newProduct.setBrands(brands);

        Images image = new Images();
        image.setCreateDate(currentDate);
        image.setUpdateDate(currentDate);
        image.setSource(fileName);
        image.setProducts(newProduct);
        newProduct.setImages(Collections.singletonList(image));
        return productRepository.save(newProduct);
      } catch (IOException e) {
        System.err.println("save file error " + e);
        return null;
      }
    }
    //update
    else {
      Optional<Products> optionalProducts = productRepository.findById(products.getId());
      if (optionalProducts.isEmpty()) {
        return null;
      }

      Products oldProduct = optionalProducts.get();
      if (!Objects.equals(oldProduct.getBrands().getId(), products.getIdBrand())) {
        oldProduct.setBrands(brands);
      }

      if (products.getImages() != null) {
        String fileName = null;
        try {
          fileName = ImageFile.saveImageFile(products.getImages());
        } catch (IOException e) {
          e.printStackTrace();
          return null;
        }
        boolean hasRemoved = ImageFile.deleteImageFile(
            oldProduct
                .getImages()
                .stream()
                .findFirst()
                .get()
                .getSource()
        );

        if (fileName.isEmpty() && hasRemoved) {
          return null;
        }

        Images images = imageRepository.findBySource(
            oldProduct
                .getImages()
                .stream()
                .findFirst()
                .get()
                .getSource()
        );
        images.setSource(fileName);
        images.setUpdateDate(currentDate);
        oldProduct.setImages(Collections.singletonList(images));
      }

      oldProduct.setName(products.getName());
      oldProduct.setPrice(products.getPrice());
      oldProduct.setDescription(products.getDescription());
      oldProduct.setDiscount(products.getDiscount());
      oldProduct.setQuantity(products.getQuantity());
      oldProduct.setSoldQuantity(products.getSoldQuantity());
      oldProduct.setModel(products.getModel());
      oldProduct.setColor(products.getColor());
      oldProduct.setOrigin(products.getOrigin());
      oldProduct.setWarrantyPeriod(products.getWarrantyPeriod());
      oldProduct.setScreenSize(products.getScreenSize());
      oldProduct.setFaceMaterial(products.getFaceMaterial());
      oldProduct.setFaceSize(products.getFaceSize());
      oldProduct.setFrameMaterial(products.getFrameMaterial());
      oldProduct.setWireMaterial(products.getWireMaterial());
      oldProduct.setProductWeight(products.getProductWeight());
      oldProduct.setUpdateDate(currentDate);
      oldProduct.setBrands(brands);
      return productRepository.save(oldProduct);
    }
  }

  @Override
  public Products save(Products products) {
    return productRepository.save(products);
  }

  @Override
  public List<Products> getProductsByBrand(Long brandId) {
    return productRepository.findAllByBrandsId(brandId);
  }

  @Override
  public boolean removeProduct(Long id) {
    Optional<Products> optionalProducts = productRepository.findById(id);
    if (optionalProducts.isEmpty()) {
      return false;
    }
    Products products = optionalProducts.get();

    try {
      ImageFile.deleteImageFile(
          products
              .getImages()
              .stream()
              .findFirst()
              .get()
              .getSource()
      );
    } catch (Exception e) {
      System.err.println(e);
    }

    productRepository.deleteById(products.getId());
    return true;
  }
}
