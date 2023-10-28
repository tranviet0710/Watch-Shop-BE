package com.example.WatchShop.controller.cart;

import com.example.WatchShop.model.Carts;
import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.res.CartDetailReqDTO;
import com.example.WatchShop.service.i_service.CartDetailService;
import com.example.WatchShop.service.i_service.CartService;
import com.example.WatchShop.service.i_service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDetailService cartDetailService;

    @GetMapping("/")
    public ResponseEntity<?> getCarts(HttpServletRequest request) {
        Users user = userService.getUserFromRequest(request).get();
        if (user != null) {
            Carts c = cartService.getCartByUserId(user.getId());
            List<CartDetailReqDTO> cartDetailReqDTOS = c.getCartDetails().stream().map(CartDetailReqDTO::new).toList();
            if (c != null) {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", cartDetailReqDTOS));
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
