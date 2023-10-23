package com.example.WatchShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
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
