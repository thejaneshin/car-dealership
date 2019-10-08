package com.thejaneshin.service;

import com.thejaneshin.pojo.User;

public interface EmployeeService {
	public void run(User employee);
	
	public void viewLotCars();
	
	public void addCarToLot();
	
	public void removeCarFromLot();
	
	public void viewAllPendingOffers();
	
	public void viewAllPayments();

}
