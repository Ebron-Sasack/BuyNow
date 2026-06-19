package com.buynow.service;

import com.buynow.dto.OrderDto;
import com.buynow.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getOrdersByUserId(Long userId);

    OrderDto convertToDto(Order order);
}
