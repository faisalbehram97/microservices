package com.microservice.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String paymentStatus;
    private String transactionId;
    private int orderId;
    private double amount;
}