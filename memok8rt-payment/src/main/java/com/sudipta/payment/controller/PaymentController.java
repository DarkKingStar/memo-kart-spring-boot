package com.sudipta.payment.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.sudipta.payment.exception.OrderException;
import com.sudipta.payment.exception.UserException;
import com.sudipta.payment.modal.Order;
import com.sudipta.payment.repository.OrderRepository;
import com.sudipta.payment.response.ApiResponse;
import com.sudipta.payment.response.PaymentLinkResponse;
import com.sudipta.payment.service.OrderService;
import com.sudipta.payment.user.domain.OrderStatus;
import com.sudipta.payment.user.domain.PaymentStatus;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;


@RestController
@CrossOrigin("*")
@RequestMapping("/payments")
public class PaymentController {
	@Value("${razorpay.api.key}")
	private String apiKey;
	@Value("${razorpay.api.secret}")
	private String apiSecret;
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;

	//Payment Link generation and process the payment
	@PostMapping("/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt)
			throws RazorpayException, UserException, OrderException {
				//Fetch the order
			Order order = orderService.findOrderById(orderId);
		try {
			RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
			//Creating Payment Link Request
			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", order.getTotalDiscountedPrice() * 100);
			paymentLinkRequest.put("currency", "INR");
			//Customer Details and Notification
			JSONObject customer = new JSONObject();
			customer.put("name", order.getUser().getFirstName() + " " + order.getUser().getLastName());
			customer.put("contact", order.getUser().getMobile());
			customer.put("email", order.getUser().getEmail());
			paymentLinkRequest.put("customer", customer);

			JSONObject notify = new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);

			paymentLinkRequest.put("reminder_enable", true);

			// callback URL is provided where Razorpay will send notifications after payment completion
			paymentLinkRequest.put("callback_url", "http://localhost:4200/payment-success?order_id=" + orderId);
			paymentLinkRequest.put("callback_method", "get");

			//Creating Payment Link using prepared request
			PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

			String paymentLinkId = payment.get("id");
			String paymentLinkUrl = payment.get("short_url");

			PaymentLinkResponse res = new PaymentLinkResponse(paymentLinkUrl, paymentLinkId);

			PaymentLink fetchedPayment = razorpay.paymentLink.fetch(paymentLinkId);
			//Updating Order and save to repo
			order.setOrderStatus(OrderStatus.PLACED);

			order.setOrderId(fetchedPayment.get("order_id"));


			orderRepository.save(order);

			System.out.println("Payment link ID: " + paymentLinkId);
			System.out.println("Payment link URL: " + paymentLinkUrl);
			System.out.println("Order Id : " + fetchedPayment.get("order_id") + fetchedPayment);

			return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.ACCEPTED);

		} catch (RazorpayException e) {

			System.out.println("Error creating payment link: " + e.getMessage());
			throw new RazorpayException(e.getMessage());
		}

	}

	//After Payment fetching the payemnt status -failed or successful
	@GetMapping("/")
	public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
			@RequestParam("order_id") Long orderId) throws RazorpayException, OrderException {
		RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
		Order order = orderService.findOrderById(orderId);

		try {
			//Fetching Payment Details:
			Payment payment = razorpay.payments.fetch(paymentId);
			System.out.println("payment details --- " + payment + payment.get("status"));
			//Update Payment Details,set status as completed and order status as placed
			if (payment.get("status").equals("captured")) {
				System.out.println("payment details --- " + payment + payment.get("status"));

				order.getPaymentDetails().setPaymentId(paymentId);
				order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
				order.setOrderStatus(OrderStatus.PLACED);
				// order.setOrderItems(order.getOrderItems());
				System.out.println(order.getPaymentDetails().getStatus() + "payment status ");
				orderRepository.save(order);
			}
			ApiResponse res = new ApiResponse("your order get placed", true);
			return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("errrr payment -------- ");
			new RedirectView("https://localhost:4200/payment/failed");
			throw new RazorpayException(e.getMessage());
		}

	}

}
