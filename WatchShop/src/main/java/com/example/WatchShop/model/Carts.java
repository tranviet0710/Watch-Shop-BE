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
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long quantity;


    @OneToOne
    @JoinColumn(name = "User_id")
    private Users users;

    @OneToMany(mappedBy = "carts")
    @JsonBackReference
    private Set<CartDetail> cartDetails;
}
