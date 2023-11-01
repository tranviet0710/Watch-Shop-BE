package com.example.WatchShop.model.dto.res;

import lombok.Data;

@Data
public class OrderResDTO {
    private long id;
    private String emailUser;
    private String address;
    private String status;
    private String orderCode;

    private double totalAmount;

}
