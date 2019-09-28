package com.thejaneshin.dao;

import java.util.List;

import com.thejaneshin.pojo.Employee;

public interface EmployeeDAO {
	public void createEmployee(Employee e);
	
	public Employee getEmployee(String username);
	
	public List<Employee> getAllEmployees();
	
	// Only include this if I have time to implement
	// change username/password/name options
	// public Employee updateEmployee(Employee e);
}
