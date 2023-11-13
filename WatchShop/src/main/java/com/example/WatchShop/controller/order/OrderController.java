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

import java.util.*;

@RestController
@RequestMapping("api/order")
@CrossOrigin
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
        List<Orders> ordersList = null;

        if ("ROLE_USER".equals(user.getRoles().getName())) {
            ordersList = new ArrayList<>(user.getOrders());
        } else {
            ordersList = orderService.getAllOrder();
        }

        List<OrderResDTO> orderResDTOList = new ArrayList<>();
        for (Orders orders : ordersList) {
            OrderResDTO orderResDTO = new OrderResDTO();
            orderResDTO.setId(orders.getId());
            orderResDTO.setUsername(user.getUsername());
            orderResDTO.setAddress(user.getAddress());
            orderResDTO.setStatus(orders.getStatus());
            orderResDTO.setTotalAmount(orders.getTotal());
            orderResDTO.setOrderCode(orders.getOrderCode());
            orderResDTO.setCreateDate(orders.getCreateDate());
            orderResDTOList.add(orderResDTO);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("status", "success", "data", orderResDTOList));
    }

    @GetMapping("/order-detail/{id}")
    public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable("id") Long id) {
        Optional<Orders> ordersOptional = orderService.getOrderById(id);

        if (ordersOptional.isPresent()) {
            Orders orders = ordersOptional.get();
            List<OrderDetail> orderDetails = orders.getOrderDetails();
            List<OrderDetailResDTO> orderDetailResDTOList = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetails) {
                OrderDetailResDTO orderDetailResDTO = getOrderDetailResDTO(orderDetail);
                orderDetailResDTOList.add(orderDetailResDTO);
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("status", "success", "data", orderDetailResDTOList));
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(null);
        }
    }

    private static OrderDetailResDTO getOrderDetailResDTO(OrderDetail orderDetail) {
        OrderDetailResDTO orderDetailResDTO = new OrderDetailResDTO();
        orderDetailResDTO.setId(orderDetail.getId());
        orderDetailResDTO.setQuantity(orderDetail.getQuantity());
        orderDetailResDTO.setProducts(orderDetail.getProducts());
        orderDetailResDTO.setPrice(orderDetail.getProducts().getPrice());
        orderDetailResDTO.setCreateDate(orderDetail.getCreateDate());
        orderDetailResDTO.setUserId(orderDetail.getOrders().getUsers().getId());
        return orderDetailResDTO;
    }

    @PostMapping("/")
    public ResponseEntity<?> addToOrder(@RequestBody OrderReqDTO orderReqDTO) {
        Users user = userService.getUserById(orderReqDTO.getUserId()).get();
        Optional<Carts> cart = cartService.getCartByUsers(user);
        // tao order tu cart
        Orders orders = new Orders();
        orders.setDate(new java.sql.Date(new Date().getTime()));

        // Create HD + Date
        orders.setOrderCode("HD" + new Date().getTime());
        orders.setStatus(orderReqDTO.getStatus());
        orders.setTotal(orderReqDTO.getTotal());
        orders.setUsers(user);
        orders.setCreateDate(new java.sql.Date(new Date().getTime()));
        Orders orders1 = orderService.save(orders);
        List<CartDetail> cartDetails = null;

        if (cart.isPresent()) {
            cartDetails = cart.get().getCartDetails();
        } else {
            cartDetails = new ArrayList<>();
        }

        OrderDetail orderDetail;
        for (CartDetail cartDetail : cartDetails) {
            orderDetail = new OrderDetail();
            orderDetail.setOrders(orders1);
            orderDetail.setProducts(cartDetail.getProducts());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetailService.save(orderDetail);
            // Tăng soldQuantity và giảm quantity của sản phẩm
            Products product = cartDetail.getProducts();
            product.setSoldQuantity(product.getSoldQuantity() + cartDetail.getQuantity());
            product.setQuantity(product.getQuantity() - cartDetail.getQuantity());
            productService.save(product);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Insert Successfully");
    }

    @PutMapping("/")
    public ResponseEntity<?> updateOrder(@RequestBody OrderReqDTO orderReqDTO) {
        Optional<Orders> orderOptional = orderService.getOrderById(orderReqDTO.getOrderId());
        if (orderOptional.isPresent()) {
            Orders orders = orderOptional.get();
            orders.setStatus(orderReqDTO.getStatus());
            orderService.save(orders);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Update Status Successfully");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No Order found with the given OrderId.");
        }
    }
}
