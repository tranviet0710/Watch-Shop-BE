package com.example.WatchShop.model.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartReqDTO {
    private Long userId;
    private Long productId;
}
