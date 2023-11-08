package com.example.WatchShop.controller.rating;

import com.example.WatchShop.model.Rating;
import com.example.WatchShop.model.dto.req.RatingReqDTO;
import com.example.WatchShop.model.dto.res.RatingResDTO;
import com.example.WatchShop.service.i_service.RatingService;
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
    private RatingService ratingService;

    @GetMapping("/")
    ResponseEntity<?> getRating(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        Rating rating = ratingService.getRatingByUserIdAndProductId(userId, productId);
        if (rating != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", new RatingResDTO(rating)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "failed", "data", new RatingResDTO()));
    }

    @PutMapping("/")
    ResponseEntity<?> getRating(@RequestBody RatingReqDTO ratingReqDTO) {
        RatingReqDTO rate = ratingService.rate(ratingReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", rate));
    }
}
