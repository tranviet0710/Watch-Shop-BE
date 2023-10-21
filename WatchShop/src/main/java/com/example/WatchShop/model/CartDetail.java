package com.example.WatchShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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
