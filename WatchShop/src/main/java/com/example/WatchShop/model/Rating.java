package com.example.WatchShop.model;

import com.example.WatchShop.model.dto.req.RatingReqDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Double star;
    private Date createDate;
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Products products;
}
