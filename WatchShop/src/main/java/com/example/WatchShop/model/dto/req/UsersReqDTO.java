package com.example.WatchShop.model.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersReqDTO {
  private String fullName;
  private Date birthDate;
  private String address;
  private String password;
  private String email;
  private String phone;
}
