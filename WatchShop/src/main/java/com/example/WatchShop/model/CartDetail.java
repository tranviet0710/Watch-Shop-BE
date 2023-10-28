package com.example.WatchShop.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;
    private Date createDate;
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Products products;

    @ManyToOne
    @JoinColumn(name = "idCart")
    private Carts carts;
}
