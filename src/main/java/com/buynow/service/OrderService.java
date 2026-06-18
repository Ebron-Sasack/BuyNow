package com.buynow.service;

import com.buynow.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
    List<Order> getOrdersByUserId(Long userId);
}
