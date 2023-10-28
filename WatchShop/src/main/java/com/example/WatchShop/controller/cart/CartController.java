package com.example.WatchShop.controller.cart;

import com.example.WatchShop.model.CartDetail;
import com.example.WatchShop.model.Carts;
import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.req.CartDetailReqDTO;
import com.example.WatchShop.model.dto.req.CartReqDTO;
import com.example.WatchShop.service.i_service.CartDetailService;
import com.example.WatchShop.service.i_service.CartService;
import com.example.WatchShop.service.i_service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @PostMapping("/")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartReqDTO cartReqDTO) {
        Users user = userService.getUserById(cartReqDTO.getUserId()).get();
        if (user != null) {
            Carts cart = cartService.getCartByUserId(user.getId());
            CartDetail cartDetail = cart.getCartDetails().stream().filter(c->c.getProducts().getId() == cartReqDTO.getProductId()).findFirst().get();
            cartDetail.setQuantity(cartDetail.getQuantity() + 1);
            cartDetailService.save(cartDetail);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", new CartDetailReqDTO(cartDetail)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
