package com.buynow.controller;

import com.buynow.dto.OrderDto;
import com.buynow.payload.ApiResponse;
import com.buynow.entity.Order;
import com.buynow.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@Transactional
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long id) {
            Order order = orderService.placeOrder(id);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order Placed", orderDto));
    }

    @GetMapping("/OrderId")
    public ResponseEntity<ApiResponse> getOrder(@RequestParam Long id) {
            OrderDto order = orderService.getOrder(id);
            return ResponseEntity.ok(new ApiResponse("Sucess", order));
    }

    @GetMapping("/userId")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@RequestParam Long id) {
            List<OrderDto> orders = orderService.getOrdersByUserId(id);
            return ResponseEntity.ok(new ApiResponse("Sucess", orders));
    }
}
