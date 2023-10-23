package com.example.WatchShop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="full_name")
    private String fullName;
    @Column(name="birth_data")
    private Date birthDate;
    @Column(name="address")
    private String address;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="phone")
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
