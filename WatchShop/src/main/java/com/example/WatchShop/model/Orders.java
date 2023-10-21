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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;
    private long status;
    private Double total;
    private String orderCode;
    private Date createDate;
    private Date updateDate;

    @OneToOne
    @JoinColumn(name = "id_user")
    private Users users;

    @OneToMany(mappedBy = "orders")
    @JsonBackReference
    private Set<OrderDetail> orderDetails;
}
