package com.thejaneshin.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thejaneshin.dao.CustomerDAO;
import com.thejaneshin.dao.CustomerDAOSerialization;
import com.thejaneshin.pojo.Customer;

public class CustomerDAOSerializationTest {
	private CustomerDAO customerDAOSer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		customerDAOSer = new CustomerDAOSerialization();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAndReadCustomer() {
		Customer testCustomer = new Customer("iliekcars", "cars123", "Danny", "Shin");
		customerDAOSer.createCustomer(testCustomer);
		
		assertEquals(testCustomer, customerDAOSer.readCustomer("iliekcars"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSameCustomers() {
		Customer testCust1 = new Customer("cust1", "cust123", "George", "White");
		customerDAOSer.createCustomer(testCust1);
		
		Customer testCust2 = new Customer("cust1", "cust123", "Carol", "Lee");
		customerDAOSer.createCustomer(testCust2);
	}
	
	// Problem is that the users directory has to be completely empty...
	@Test
	public void testReadAllCustomers() {
		Set<Customer> testCustomers = new HashSet<>();
		
		Customer cust1 = new Customer("ralph", "g4ryisc001", "Ralph", "Schneider");
		customerDAOSer.createCustomer(cust1);
		testCustomers.add(cust1);
		
		assertEquals(testCustomers, customerDAOSer.readAllCustomers());
	}
}
