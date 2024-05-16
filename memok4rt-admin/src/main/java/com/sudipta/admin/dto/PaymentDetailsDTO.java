package com.sudipta.admin.dto;

import com.sudipta.admin.enums.PaymentMethod;
import com.sudipta.admin.enums.PaymentStatus;

import lombok.Data;

@Data
public class PaymentDetailsDTO {

	private PaymentMethod paymentMethod;
	private PaymentStatus status;
	private String paymentId;
	private String razorpayPaymentLinkId;
	private String razorpayPaymentLinkReferenceId;
	private String razorpayPaymentLinkStatus;
	private String razorpayPaymentId​;

}
