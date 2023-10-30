package com.example.WatchShop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long quantity;

    @ManyToOne
    @JoinColumn(name = "idOrder")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Products products;
}
