package com.example.WatchShop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String role_name;
}
