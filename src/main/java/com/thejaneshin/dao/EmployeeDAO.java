package com.thejaneshin.dao;

import java.util.Set;

import com.thejaneshin.pojo.Employee;
import com.thejaneshin.pojo.User;

public interface EmployeeDAO {
	public void createEmployee(Employee e);
	
	public User readEmployee(String username);
	
	public Set<Employee> readAllEmployees();
	
	// Only include this if I have time to implement
	// change username/password/name options
	// public Employee updateEmployee(Employee e);
}
