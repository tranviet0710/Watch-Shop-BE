package com.example.WatchShop.controller.rating;

import com.example.WatchShop.model.Rating;
import com.example.WatchShop.service.impl.RatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/rating")
@CrossOrigin(origins = "*")
public class RatingController {

    @Autowired
    private RatingServiceImpl ratingService;

    @GetMapping("/")
    ResponseEntity<?> getAllRating(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        Rating rating = ratingService.getRatingByUserIdAndProductId(userId, productId);
        if (rating != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", rating));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", rating));
    }
}
