package com.example.WatchShop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Data
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
    private String password;
    private String email;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "idRole")
    private Roles roles;

}
