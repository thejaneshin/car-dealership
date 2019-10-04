package com.thejaneshin.dao;

import java.util.Set;

import com.thejaneshin.pojo.Customer;
import com.thejaneshin.pojo.User;

public interface CustomerDAO {
	public void createCustomer(Customer customer);
	
	public User readCustomer(String username);
	
	public Set<Customer> readAllCustomers();
	
	public Customer updateCustomer(Customer customer);
}
