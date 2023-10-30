package com.example.WatchShop.model.dto.res;

import com.example.WatchShop.model.Products;
import com.example.WatchShop.model.Rating;
import com.example.WatchShop.model.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingResDTO {
    private long id;
    private Double star;
    private Date createDate;
    private Date updateDate;
    private String userName;
    private String productName;

    public RatingResDTO(Rating rating){
        this.id = rating.getId();
        this.star = rating.getStar();
        this.createDate = rating.getCreateDate();
        this.updateDate = rating.getUpdateDate();
        this.userName = rating.getUsers().getFullName();
        this.productName = rating.getProducts().getName();
    }
}
