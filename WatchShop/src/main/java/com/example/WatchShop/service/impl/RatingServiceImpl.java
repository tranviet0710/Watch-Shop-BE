package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Rating;
import com.example.WatchShop.model.dto.req.RatingReqDTO;
import com.example.WatchShop.repository.ProductRepository;
import com.example.WatchShop.repository.RatingRepository;
import com.example.WatchShop.repository.UsersRepository;
import com.example.WatchShop.service.i_service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;


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
    public RatingReqDTO rate(RatingReqDTO ratingReqDTO) {
        Rating rating = ratingRepository.findByUsersIdAndProductsId(ratingReqDTO.getUserID(), ratingReqDTO.getProductID());
        if(rating != null){
            rating.setStar(ratingReqDTO.getStar());
            return new RatingReqDTO(ratingRepository.save(rating));
        }
        else{
            Rating newRating = new Rating();
            newRating.setUsers(usersRepository.findById(ratingReqDTO.getUserID()).get());
            newRating.setProducts(productRepository.findById(ratingReqDTO.getProductID()).get());
            newRating.setStar(ratingReqDTO.getStar());
            return new RatingReqDTO(ratingRepository.save(newRating));
        }
    }
}
