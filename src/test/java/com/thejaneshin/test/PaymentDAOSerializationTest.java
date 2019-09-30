package com.thejaneshin.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thejaneshin.dao.PaymentDAO;
import com.thejaneshin.dao.PaymentDAOSerialization;
import com.thejaneshin.pojo.Payment;

public class PaymentDAOSerializationTest {
	PaymentDAO paymentDAOSer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		paymentDAOSer = new PaymentDAOSerialization();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAndReadPayment() {
		Payment testPayment = new Payment(3000.00, "harry", "WBAPK5C53BF123740");
		paymentDAOSer.createPayment(testPayment);

		assertEquals(testPayment, paymentDAOSer.readPaymentByUsernameAndVin("harry", "WBAPK5C53BF123740"));
	}
	
	@Test
	public void testReadAllPayments() {
		Set<Payment> testPayments = new HashSet<>();
		
		Payment payment1 = new Payment(3000.00, "harry", "2HKRM4H31EH662200");
		paymentDAOSer.createPayment(payment1);
		testPayments.add(payment1);
		
		Payment payment2 = new Payment(1000.00, "lovecars", "2HKRM4H31EH662200");
		paymentDAOSer.createPayment(payment2);
		testPayments.add(payment2);
		
		assertEquals(testPayments, paymentDAOSer.readAllPayments());
	}

}
