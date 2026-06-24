package com.buynow.response;

import com.buynow.enums.PaymentMethod;
import com.buynow.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentResponse {

    private Long paymentId;

    private Long orderId;

    private BigDecimal amount;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    private String transactionId;
}
