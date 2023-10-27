package com.example.WatchShop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersDTO {
    private String fullName;
    private Date birthDate;
    private String address;
    private String password;
    private String email;
    private String phone;
}
