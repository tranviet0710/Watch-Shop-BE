package com.example.WatchShop.service;

import com.example.WatchShop.model.Rating;
import org.springframework.stereotype.Service;

public interface RatingService {
    Rating addRating(Rating rating);

    Rating getRatingByUserIdAndProductId(Long idUser, Long idProduct);

    Rating updateRating(Rating rating);

    void deleteRating(Long id);
}
