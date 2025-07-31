package com.microservice.order.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.order.api.dto.TransactionResponse;
import com.microservice.order.api.entity.OrderEntity;
import com.microservice.order.api.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/bookOrder")
	public TransactionResponse bookOrder(@RequestBody OrderEntity entity) {
		return orderService.saveOrder(entity);
	}
	
	@GetMapping("/all")
	public List<OrderEntity> getAllOrders() {
		return orderService.findAllOrder();
	}
}
