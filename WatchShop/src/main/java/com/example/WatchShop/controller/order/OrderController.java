package com.example.WatchShop.controller.order;

import com.example.WatchShop.model.*;


import com.example.WatchShop.model.dto.req.OrderReqDTO;

import com.example.WatchShop.model.dto.res.OrderDetailResDTO;
import com.example.WatchShop.model.dto.res.OrderResDTO;

import com.example.WatchShop.service.i_service.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CartService cartService;


    @GetMapping("/")
    public ResponseEntity<?> getOrder(HttpServletRequest request) {
        Users user = userService.getUserFromRequest(request).get();
        if (user != null) {
            Orders orders = orderService.getOrderByUserId(user.getId());
            // Create OrderDTO từ Orders
            OrderResDTO orderResDTO = new OrderResDTO();
            orderResDTO.setId(orders.getId());
            orderResDTO.setEmailUser(user.getEmail());
            orderResDTO.setAddress(user.getAddress());
            orderResDTO.setStatus(orders.getStatus());
            orderResDTO.setTotalAmount(orders.getTotal());
            orderResDTO.setOrderCode(orders.getOrderCode());

            // Trả về OrderDTO thay vì Orders
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", orderResDTO));
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/order-detail")
    public ResponseEntity<?> getCarts(HttpServletRequest request) {
        Users user = userService.getUserFromRequest(request).get();

        if (user != null) {
            Orders orders = orderService.getOrderByUserId(user.getId());
            System.err.println(orders + "ordersDetal");
            List<OrderDetailResDTO> orderResDTOS = orders.getOrderDetails().stream().map(OrderDetailResDTO::new).toList();
            if (orders != null) {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", orderResDTOS));
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/")
    public ResponseEntity<?> addToOrder(@RequestBody OrderReqDTO orderReqDTO) {
        Users user = userService.getUserById(orderReqDTO.getUserId()).get();
        if (user != null) {
            Optional<Carts> cart = cartService.getCartById(user.getId());
            // tao order tu cart
            Orders orders = new Orders();
            orders.setDate(new java.sql.Date(new Date().getTime()));

            // Create HD + Date
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
            String formattedDate = dateFormat.format(calendar.getTime());
            orders.setOrderCode("HD" + formattedDate);
            orders.setStatus(orderReqDTO.getStatus());
            orders.setTotal(orderReqDTO.getTotal());
            orders.setUsers(user);
            Orders orders1 = orderService.save(orders);
            Set<CartDetail> cartDetails = cart.get().getCartDetails();
            OrderDetail orderDetail;
            for(CartDetail cartDetail: cartDetails){
                orderDetail = new OrderDetail();
                orderDetail.setOrders(orders1);
                orderDetail.setProducts(cartDetail.getProducts());
                orderDetail.setQuantity(cartDetail.getQuantity());
                orderDetailService.save(orderDetail);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Insert Successfuly");
        }
        return ResponseEntity.status(HttpStatus.OK).body("null");
    }

    @PutMapping("/")
    public ResponseEntity<?> updateOrder(@RequestBody OrderReqDTO orderReqDTO) {
        Optional<Orders> orderOptional = orderService.getOrderById(orderReqDTO.getOrderId());
        if (orderOptional.isPresent()) {
            Orders orders = orderOptional.get();
            orders.setStatus(orderReqDTO.getStatus());
            orderService.save(orders);
            return ResponseEntity.status(HttpStatus.OK).body("Update Status Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Order found with the given OrderId.");
        }
    }









}
