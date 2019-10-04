package com.thejaneshin.service;

import com.thejaneshin.pojo.Customer;

public interface CustomerService {
	public void run(Customer currentCustomer);
	
	public void viewLotCars();
	
	public void viewYourCars();
	
	public void viewOfferedCars();
	
	public void viewRemainingPayments();
	
}
