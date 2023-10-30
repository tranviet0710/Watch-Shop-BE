package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Rating;
import com.example.WatchShop.repository.RatingRepository;
import com.example.WatchShop.service.i_service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating addRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating getRatingByUserIdAndProductId(Long idUser, Long idProduct) {
        return ratingRepository.findByUsersIdAndProductsId(idUser, idProduct);
    }

    @Override
    public Double getStarOfProduct(Long idProduct) {
        return ratingRepository.findAll().stream().filter(r -> r.getProducts().getId() == idProduct)
                .map(Rating::getStar).collect(Collectors.averagingDouble(Double::doubleValue));
    }

    @Override
    public Rating updateRating(Rating rating) {
        return null;
    }

    @Override
    public void deleteRating(Long id) {

    }
}
