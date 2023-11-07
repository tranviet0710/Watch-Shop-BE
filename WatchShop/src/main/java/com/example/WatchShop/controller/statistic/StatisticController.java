package com.example.WatchShop.controller.statistic;

import com.example.WatchShop.model.dto.res.ProductStatisticalResDTO;
import com.example.WatchShop.model.dto.res.UserResDTO;
import com.example.WatchShop.service.i_service.OrderService;
import com.example.WatchShop.service.i_service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistical")
public class StatisticController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/top-5-best-sellers")
    public ResponseEntity<?> get5BestSellers() {
        List<ProductStatisticalResDTO> bestSellers = productService.findAllProduct().stream()
                .map(ProductStatisticalResDTO::new)
                .sorted(Comparator.comparing(ProductStatisticalResDTO::getQuantitySold, Comparator.reverseOrder())).limit(5).toList();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", bestSellers));
    }

    @GetMapping("/top-5-best-customers")
    public ResponseEntity<?> get5BestCustomer() {
        List<UserResDTO> bestCustomers = orderService.get5BestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", bestCustomers));
    }
}
