package com.thejaneshin.dao;

import java.util.List;

import com.thejaneshin.pojo.Customer;

public interface CustomerDAO {
	public void createCustomer(Customer customer);
	
	public Customer getCustomer(String username);
	
	public List<Customer> getAllCustomers();
	
	// Only include this if I have time to implement
	// change username/password/name options
	// public Customer updateCustomer(Customer customer);
}
