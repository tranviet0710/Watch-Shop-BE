package com.example.WatchShop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "nvarchar(250)")
    private String fullName;
    private Date birthDate;
    @Column(columnDefinition = "nvarchar(250)")
    private String address;
    @JsonIgnore
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
