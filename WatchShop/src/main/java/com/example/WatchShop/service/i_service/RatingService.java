package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.Rating;

public interface RatingService {
    Rating addRating(Rating rating);

    Rating getRatingByUserIdAndProductId(Long idUser, Long idProduct);

    Double getStarOfProduct(Long idProduct);

    Rating updateRating(Rating rating);

    void deleteRating(Long id);
}
