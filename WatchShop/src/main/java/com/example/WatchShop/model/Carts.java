package com.example.WatchShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long quantity;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "carts")
    @JsonBackReference
    private List<CartDetail> cartDetails;
}
