package com.thejaneshin.dao;

import com.thejaneshin.pojo.Payment;

public interface PaymentDAO {
	public void createPayment(Payment p);
	
	public Payment getPayment(/*What to put in here...*/);
	
	public Payment getAllPayments();
	
}
