package com.microservice.order.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.order.api.dto.Payment;
import com.microservice.order.api.dto.TransactionResponse;
import com.microservice.order.api.entity.OrderEntity;
import com.microservice.order.api.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
@RefreshScope
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	@Lazy
	private RestTemplate template;

	@Value("${microservice.payment-service.endpoints.endpoint.uri}")
	private String ENDPOINT_URL;

	public List<OrderEntity> findAllOrder() {
		return orderRepository.findAll();
	}

	private static final String PAYMENT_SERVICE = "paymentService";

	@CircuitBreaker(name = PAYMENT_SERVICE, fallbackMethod = "paymentFallback")
	public TransactionResponse saveOrder(OrderEntity request) {
		String response = "";
		request = orderRepository.save(request);

		if (request.getOrderId() > 0) {
			Payment payment = new Payment();
			payment.setAmount(request.getPrice());
			payment.setOrderId(request.getOrderId());

			Payment paymentResponse = template.postForObject(ENDPOINT_URL, payment, Payment.class);
			response = paymentResponse.getPaymentStatus().equalsIgnoreCase("success")
					? "payment processing successful and order placed"
					: "there is a failure in payment API, order added to cart";

			return new TransactionResponse(request, paymentResponse.getAmount(), paymentResponse.getTransactionId(),
					response);
		} else {
			response = "Order Not Saved";
			return new TransactionResponse(request, request.getPrice(), null, response);
		}
	}

	// Fallback method
	public TransactionResponse paymentFallback(OrderEntity request, Throwable ex) {
		String response = "Payment Service is Down - Order Saved to Cart";
		return new TransactionResponse(request, request.getPrice(), null, response);
	}

}
