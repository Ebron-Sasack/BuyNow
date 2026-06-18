package com.buynow.controller;

import com.buynow.response.ApiResponse;
import com.buynow.entity.Order;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long id) {
        try {
            Order order = orderService.placeOrder(id);
            return ResponseEntity.ok(new ApiResponse("Order Placed", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Order Failed", e.getMessage()));
        }
    }

    @GetMapping("/OrderId")
    public ResponseEntity<ApiResponse> getOrder(@RequestParam Long id) {
        try {
            Order order = orderService.getOrder(id);
            return ResponseEntity.ok(new ApiResponse("Sucess", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/userId")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@RequestParam Long id) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(id);
            return ResponseEntity.ok(new ApiResponse("Sucess", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
}
