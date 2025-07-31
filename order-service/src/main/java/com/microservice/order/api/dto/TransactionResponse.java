package com.microservice.order.api.dto;

import com.microservice.order.api.entity.OrderEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private OrderEntity order;
    private double amount;
    private String transactionId;
    private String message;
}