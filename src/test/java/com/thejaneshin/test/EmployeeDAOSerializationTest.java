package com.thejaneshin.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thejaneshin.dao.EmployeeDAO;
import com.thejaneshin.dao.EmployeeDAOSerialization;
import com.thejaneshin.pojo.Employee;

public class EmployeeDAOSerializationTest {
	private EmployeeDAO employeeDAOSer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		employeeDAOSer = new EmployeeDAOSerialization();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAndReadEmployee() {
		Employee testEmployee = new Employee("boss", "test123", "Bob", "Smith");
		employeeDAOSer.createEmployee(testEmployee);
		
		assertEquals(testEmployee, employeeDAOSer.readEmployee("boss"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSameEmployees() {
		Employee testEmp1 = new Employee("emp1", "emp123", "George", "White");
		employeeDAOSer.createEmployee(testEmp1);
		
		Employee testEmp2 = new Employee("emp1", "emp123", "Carol", "Lee");
		employeeDAOSer.createEmployee(testEmp2);
	}
	
	// Problem is that the users directory has to be completely empty...
	@Test
	public void testReadAllEmployees() {
		Set<Employee> testEmployees = new HashSet<>();
		
		Employee emp1 = new Employee("gary", "g4ryisc001", "Gary", "Schneider");
		employeeDAOSer.createEmployee(emp1);
		testEmployees.add(emp1);
		Employee emp2 = new Employee("carol", "password", "Carol", "Lee");
		employeeDAOSer.createEmployee(emp2);
		testEmployees.add(emp2);
		
		assertEquals(testEmployees, employeeDAOSer.readAllEmployees());
	}

}
