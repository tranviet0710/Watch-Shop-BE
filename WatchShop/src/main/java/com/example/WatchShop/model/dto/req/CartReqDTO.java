package com.example.WatchShop.model.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartReqDTO {
    private Long userId;
    private Long productId;
    private Integer amount =1;
}
