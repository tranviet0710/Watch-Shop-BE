package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.OrderDetail;
import com.example.WatchShop.model.Orders;
import com.example.WatchShop.model.dto.res.UserResDTO;
import com.example.WatchShop.repository.OrderRepository;
import com.example.WatchShop.service.i_service.OrderService;
import com.example.WatchShop.service.i_service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final UserService userService;

  @Override
  public List<Orders> getAllOrder() {
    return orderRepository.findAll();
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
      totalMoney = orderDetails
          .stream()
          .map(x -> x.getProducts().getPrice())
          .mapToDouble(Double::doubleValue)
          .sum();

      if (totalMoneyPaidByUser.containsKey(userID)) {
        Double paidMoney = totalMoneyPaidByUser.get(userID);
        totalMoneyPaidByUser.put(userID, paidMoney + totalMoney);
      } else {
        totalMoneyPaidByUser.put(userID, totalMoney);
      }
    }

    List<Long> userIds = totalMoneyPaidByUser
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .limit(5)
        .map(Map.Entry::getKey)
        .toList();

    return userIds
        .stream()
        .map(x -> userService.getUserById(x).get())
        .map(UserResDTO::new)
        .toList();
  }

  @Override
  public List<Double> statisticalByMonthAndYear(int month, int year) {
    int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
    List<Double> response3Forms = new ArrayList<>();
    List<Map<String, Object>> results = orderRepository.statistical(year, month);

    // create days of the month
    for (int i = 0; i < daysInMonth; i++) {
      response3Forms.add(0.0);
    }

    for (Map<String, Object> result : results) {
      Date orderDate = (Date) result.get("create_date");
      double total = (double) result.get("total");

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(orderDate);

      int date = calendar.get(Calendar.DAY_OF_MONTH);
      int index = date - 1;
      double newTotalPrice = response3Forms.get(index) + total;
      response3Forms.set(index, newTotalPrice);
    }

    return response3Forms;
  }

  @Override
  public double statisticalByMonthAndYear(int year) {
    try {
      return orderRepository.statistical(year);
    } catch (Exception e) {
      return 0;
    }
  }
}
