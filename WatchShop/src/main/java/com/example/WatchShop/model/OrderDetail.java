package com.example.WatchShop.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;
    private Date createDate;
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "idOrder")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Products products;
}
