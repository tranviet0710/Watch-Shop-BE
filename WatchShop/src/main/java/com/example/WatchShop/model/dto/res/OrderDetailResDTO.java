package com.example.WatchShop.model.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResDTO {
    private long id;
    private int quantity;
    private String productName;
    private double price;
    private Date createDate;
}
