package com.example.WatchShop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private Date birthDate;
    private String address;
    private String password;
    private String email;
    private String phone;

    @OneToOne
    @JoinColumn(name = "idRole")
    private Roles idRole;


    public Users(String fullName, Date birthDate, String address, String password, String email, String phone, Roles idRole) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.idRole = idRole;
    }
}
