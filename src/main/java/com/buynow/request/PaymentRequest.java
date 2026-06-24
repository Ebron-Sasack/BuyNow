package com.buynow.request;

import com.buynow.enums.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequest {

    private Long orderId;
    private PaymentMethod paymentMethod;
}
