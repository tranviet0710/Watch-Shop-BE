package com.example.WatchShop.model.dto.res;

import com.example.WatchShop.model.CartDetail;
import com.example.WatchShop.model.OrderDetail;
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
    private Date createDate;
    private Date updateDate;

    private String productName;

    private Long orderId;

    public OrderDetailResDTO(OrderDetail orderDetail){
        this.id = orderDetail.getId();
        this.quantity = orderDetail.getQuantity();
        this.createDate = orderDetail.getCreateDate();
        this.updateDate = orderDetail.getUpdateDate();
        this.productName = orderDetail.getProducts().getName();
        this.orderId = orderDetail.getOrders().getId();
    }
}
