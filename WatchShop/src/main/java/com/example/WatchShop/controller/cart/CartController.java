package com.example.WatchShop.controller.cart;

import com.example.WatchShop.model.CartDetail;
import com.example.WatchShop.model.Carts;
import com.example.WatchShop.model.Products;
import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.req.CartDetailResDTO;
import com.example.WatchShop.model.dto.req.CartReqDTO;
import com.example.WatchShop.service.i_service.CartDetailService;
import com.example.WatchShop.service.i_service.CartService;
import com.example.WatchShop.service.i_service.ProductService;
import com.example.WatchShop.service.i_service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;
    @Autowired
    private CartDetailService cartDetailService;

    @GetMapping("/")
    public ResponseEntity<?> getCarts(HttpServletRequest request) {
        Users user = userService.getUserFromRequest(request).get();
        if (user != null) {
            Carts c = cartService.getCartByUserId(user.getId());
            if (c != null) {
                List<CartDetailResDTO> cartDetailResDTOS = c.getCartDetails().stream().map(CartDetailResDTO::new).toList();
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", cartDetailResDTOS));
            }
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", new ArrayList<>()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartReqDTO cartReqDTO) {
        Users user = userService.getUserById(cartReqDTO.getUserId()).get();
        if (user != null) {
            Carts cart = cartService.getCartByUserId(user.getId());
            if(cart == null){
                Carts createCart = new Carts();
                createCart.setUsers(user);
                cart = cartService.save(createCart);
            }
            Optional<CartDetail> cartDetail = cart.getCartDetails().stream().filter(c -> c.getProducts().getId() == cartReqDTO.getProductId()).findFirst();
            CartDetail cartDetail1;
            if (cartDetail.isPresent()) {
                cartDetail1 = cartDetail.get();
                if (cartReqDTO.getAmount() != null) {
                    cartDetail1.setQuantity(cartDetail1.getQuantity() + cartReqDTO.getAmount());
                } else {
                    cartDetail1.setQuantity(cartDetail1.getQuantity() + 1);
                }
            } else {
                Products products = productService.getProductById(cartReqDTO.getProductId()).get();
                cartDetail1 = new CartDetail();
                cartDetail1.setCarts(cart);
                cartDetail1.setProducts(products);
                cartDetail1.setQuantity(cartReqDTO.getAmount());
            }
            CartDetail savedCartDetail = cartDetailService.save(cartDetail1);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", new CartDetailResDTO(savedCartDetail)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteFromCart(@Valid @RequestBody CartReqDTO cartReqDTO) {
        Users user = userService.getUserById(cartReqDTO.getUserId()).get();
        if (user != null) {
            Carts cart = cartService.getCartByUserId(user.getId());
            Optional<CartDetail> cartDetail = cart.getCartDetails().stream().filter(c -> c.getProducts().getId() == cartReqDTO.getProductId()).findFirst();
            if (cartDetail.isPresent()) {
                cartDetailService.remove(cartDetail.get());
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", new CartDetailResDTO(cartDetail.get())));
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
