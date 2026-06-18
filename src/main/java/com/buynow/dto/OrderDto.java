package com.buynow.dto;

import com.buynow.entity.User;
import com.buynow.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDate date;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private User user;
    private Set<OrderItemDto> orderItems;
}
