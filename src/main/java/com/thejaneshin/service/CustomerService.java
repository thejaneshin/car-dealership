package com.thejaneshin.service;

import com.thejaneshin.pojo.User;

public interface CustomerService {
	public void run(User currentUser);
	
	public void viewLotCars();
	
	public void viewYourCars();
	
	public void viewOfferedCars();
	
	public void viewRemainingPayments();
	
}
