package com.microservice.ps.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.ps.api.entity.Payment;
import com.microservice.ps.api.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/doPayment")
	public Payment doPayment(@RequestBody Payment entity) {
		return paymentService.doPayments(entity);
	}
	@GetMapping("/{orderId}")
	public Payment getByOrderId(@PathVariable int orderId) {
		return paymentService.getByOrderId(orderId);
	}
	@GetMapping("/all")
	public List<Payment> getAll() {
		return paymentService.getAllPayments();
	}
}
