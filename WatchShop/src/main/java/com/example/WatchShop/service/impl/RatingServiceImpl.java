package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Rating;
import com.example.WatchShop.repository.RatingRepository;
import com.example.WatchShop.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return ratingRepository.findByUsersIdAndProductsId(idUser,idProduct);
    }

    @Override
    public Rating updateRating(Rating rating) {
        return null;
    }

    @Override
    public void deleteRating(Long id) {

    }
}
