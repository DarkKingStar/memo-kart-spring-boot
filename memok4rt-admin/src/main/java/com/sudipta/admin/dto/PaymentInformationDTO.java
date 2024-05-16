package com.sudipta.admin.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PaymentInformationDTO { 
	private String cardholderName;
	private String cardNumber;
	private LocalDate expirationDate;
	private String cvv;
}