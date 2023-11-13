package com.example.WatchShop.controller.statistic;

import com.example.WatchShop.model.dto.res.ProductStatisticalResDTO;
import com.example.WatchShop.model.dto.res.Response1Form;
import com.example.WatchShop.model.dto.res.UserResDTO;
import com.example.WatchShop.service.i_service.OrderService;
import com.example.WatchShop.service.i_service.ProductService;
import com.example.WatchShop.service.i_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistical")
@CrossOrigin
public class StatisticController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/top-5-best-sellers")
    public ResponseEntity<?> get5BestSellers() {
        List<ProductStatisticalResDTO> bestSellers = productService.findAllProduct()
                .stream()
                .map(ProductStatisticalResDTO::new)
                .sorted(Comparator.comparing(ProductStatisticalResDTO::getQuantitySold, Comparator.reverseOrder()))
                .limit(5)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", bestSellers));
    }

    @GetMapping("/top-5-best-customers")
    public ResponseEntity<?> get5BestCustomer() {
        List<UserResDTO> bestCustomers = orderService.get5BestSellers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("status", "success", "data", bestCustomers));
    }

    /**
     * Statistical.
     *
     * @param month the month want to statistic
     * @param year the year want to statistic
     * @return statistical
     */
    @GetMapping(path = "/{month}/{year}")
    public ResponseEntity<List<Double>> statistical(
            @PathVariable(name = "month") int month,
            @PathVariable(name = "year") int year
    ) {
        List<Double> statistical = orderService.statisticalByMonthAndYear(month, year);
        return ResponseEntity.ok().body(statistical);
    }

    @GetMapping(path = "/top-buy-the-most")
    public ResponseEntity<List<Response1Form>> getTopBuyTheMost() {
        List<Response1Form> result = userService.topUserBuyTheMost(5);
        return ResponseEntity.ok().body(result);
    }
}
