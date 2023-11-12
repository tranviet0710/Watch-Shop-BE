package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.OrderDetail;
import com.example.WatchShop.model.Orders;
import com.example.WatchShop.model.dto.res.UserResDTO;
import com.example.WatchShop.repository.OrderRepository;
import com.example.WatchShop.service.i_service.OrderService;
import com.example.WatchShop.service.i_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Orders> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Orders getOrderByUserId(Long id) {
        return orderRepository.findByUsersId(id);
    }

    @Override
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public Optional<Orders> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<UserResDTO> get5BestSellers() {
        List<Orders> allOrders = getAllOrder();
        List<OrderDetail> orderDetails;
        Map<Long, Double> totalMoneyPaidByUser = new HashMap<>();
        Double totalMoney;
        Long userID;
        for (Orders allOrder : allOrders) {
            userID = allOrder.getUsers().getId();
            orderDetails = allOrder.getOrderDetails();
            totalMoney = orderDetails.stream().map(x -> x.getProducts().getPrice()).mapToDouble(Double::doubleValue).sum();
            if (totalMoneyPaidByUser.containsKey(userID)) {
                Double paidMoney = totalMoneyPaidByUser.get(userID);
                totalMoneyPaidByUser.put(userID, paidMoney + totalMoney);
            } else {
                totalMoneyPaidByUser.put(userID, totalMoney);
            }
        }
        List<Long> userIds = totalMoneyPaidByUser.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();

        return userIds.stream()
                .map(x -> userService.getUserById(x).get())
                .map(UserResDTO::new)
                .collect(Collectors.toList());

    }
}
