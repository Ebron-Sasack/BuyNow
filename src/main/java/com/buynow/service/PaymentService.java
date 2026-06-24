package com.buynow.service;

import com.buynow.request.PaymentRequest;
import com.buynow.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest request);
    PaymentResponse getPaymentById(Long id);
    PaymentResponse getPaymentByOrderId(Long orderId);
}
