package com.buynow.service.impl;

import com.buynow.entity.Order;
import com.buynow.entity.Payment;
import com.buynow.enums.PaymentStatus;
import com.buynow.exception.ResourceNotFoundException;
import com.buynow.repository.OrderRepository;
import com.buynow.repository.PaymentRepository;
import com.buynow.request.PaymentRequest;
import com.buynow.response.PaymentResponse;
import com.buynow.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;


    @Override
    public PaymentResponse makePayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(request.getPaymentMethod());

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        payment.setPaymentDate(LocalDateTime.now());

        payment.setTransactionId(UUID.randomUUID().toString());

        Payment saved = paymentRepository.save(payment);

        return modelMapper.map(saved, PaymentResponse.class);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Payment Not Found")
                );
        return modelMapper.map(payment, PaymentResponse.class);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = Optional.ofNullable(paymentRepository.findByOrderId(orderId))
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Payment Not Found")
                );
        return modelMapper.map(payment, PaymentResponse.class);
    }

}
