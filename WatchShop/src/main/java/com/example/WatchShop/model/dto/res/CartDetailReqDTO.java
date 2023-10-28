package com.example.WatchShop.model.dto.res;

import com.example.WatchShop.model.CartDetail;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CartDetailReqDTO {
    private long id;
    private int quantity;
    private Date createDate;
    private Date updateDate;

    private String productName;

    private Long cartId;
    public CartDetailReqDTO(CartDetail cartDetail){
        this.id = cartDetail.getId();
        this.quantity = cartDetail.getQuantity();
        this.createDate = cartDetail.getCreateDate();
        this.updateDate = cartDetail.getUpdateDate();
        this.productName = cartDetail.getProducts().getName();
        this.cartId = cartDetail.getCarts().getId();
    }
}
