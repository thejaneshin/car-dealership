package com.thejaneshin.dao;

import java.util.List;

import com.thejaneshin.pojo.Payment;

public interface PaymentDAO {
	public void createPayment(Payment p);
	
	public List<Payment> readPaymentsByUsernameAndVin(String username, String vin);
	
	public List<Payment> readAllPayments();
	
}
