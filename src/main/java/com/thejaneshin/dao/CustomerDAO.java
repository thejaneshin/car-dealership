package com.thejaneshin.dao;

import java.util.Set;

import com.thejaneshin.pojo.Customer;

public interface CustomerDAO {
	public void createCustomer(Customer customer);
	
	public Customer readCustomer(String username);
	
	public Set<Customer> readAllCustomers();
	
	// Only include this if I have time to implement
	// change username/password/name options
	// public Customer updateCustomer(Customer customer);
}
