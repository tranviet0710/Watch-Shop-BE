package com.example.WatchShop.model.dto.res;

import com.example.WatchShop.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResDTO {

    private String fullName;
    private Date birthDate;
    private String address;
    private String email;
    private String phone;
    private String roles;

    public UserResDTO(Users users) {
        this.fullName = users.getFullName();
        this.birthDate = users.getBirthDate();
        this.address = users.getAddress();
        this.email = users.getEmail();
        this.phone = users.getPhone();
        this.roles = users.getRoles().getName();
    }
}
