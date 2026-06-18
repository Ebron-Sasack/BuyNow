package com.buynow.service.impl;

import com.buynow.dto.OrderDto;
import com.buynow.entity.Cart;
import com.buynow.entity.Order;
import com.buynow.entity.OrderItem;
import com.buynow.entity.Product;
import com.buynow.enums.OrderStatus;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.repository.OrderRepository;
import com.buynow.repository.ProductRepository;
import com.buynow.service.CartService;
import com.buynow.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);
        return savedOrder;
    }


    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setDate(LocalDate.now());
        return order;
    }


    private List<OrderItem> createOrderItems(Order order, Cart  cart) {
        return cart.getCartItems()
                .stream()
                .map(item -> {
                    Product product = item.getProduct();
                    product.setStock(product.getStock() - item.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            item.getQuantity(),
                            item.getUnitPrice()
                    );
                })
                .toList();
    }


    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item ->
                    item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId).map(this::convertToDto).orElseThrow(
                ()-> new ResourceNotFoundException("Order Not Found")
        );
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
