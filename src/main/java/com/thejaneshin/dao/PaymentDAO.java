package com.thejaneshin.dao;

import java.util.Set;

import com.thejaneshin.pojo.Payment;

public interface PaymentDAO {
	public void createPayment(Payment p);
	
	public Payment readPaymentByUsernameAndVin(String username, String vin);
	
	public Set<Payment> readAllPayments();
	
}
