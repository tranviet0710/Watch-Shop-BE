package com.example.WatchShop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String source;
    private Date createDate;
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "idProduct", nullable = false)
    @JsonIgnore
    private Products products;
}
