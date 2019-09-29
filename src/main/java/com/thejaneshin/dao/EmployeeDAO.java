package com.thejaneshin.dao;

import java.util.Set;

import com.thejaneshin.pojo.Employee;

public interface EmployeeDAO {
	public void createEmployee(Employee e);
	
	public Employee readEmployee(String username);
	
	public Set<Employee> readAllEmployees();
	
	// Only include this if I have time to implement
	// change username/password/name options
	// public Employee updateEmployee(Employee e);
}
