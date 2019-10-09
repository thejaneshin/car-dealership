package com.thejaneshin.test;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thejaneshin.util.PaymentCalcImpl;
import com.thejaneshin.util.PaymentCalculator;

public class PaymentCalculatorTest {
	private PaymentCalculator paymentCalc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		paymentCalc = new PaymentCalcImpl();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCalculateValidPaidSoFar() {
		List<Double> testPayments = new LinkedList<>();
		testPayments.add(1000.00);
		testPayments.add(2000.00);
		
		assertEquals(new Double(3000.00), paymentCalc.calculatePaidSoFar(testPayments));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCalculateInvalidPaidSoFar() {
		List<Double> testPayments = new LinkedList<>();
		testPayments.add(1000.00);
		testPayments.add(-2000.00);
		
		paymentCalc.calculatePaidSoFar(testPayments);
	}
	
	@Test
	public void testCalculateValidPriceLeft() {
		List<Double> testPayments = new LinkedList<>();
		testPayments.add(1000.00);
		testPayments.add(2000.00);
		
		assertEquals(new Double(10000.00), paymentCalc.calculatePriceLeft(13000.00, testPayments));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCalculateInvalidPriceLeft() {
		List<Double> testPayments = new LinkedList<>();
		testPayments.add(1000.00);
		testPayments.add(-2000.00);
		
		paymentCalc.calculatePriceLeft(13000.00, testPayments);
	}
	
	@Test
	public void testCalculateValidMonthlyPayment() {
		assertEquals(new Double(1000), paymentCalc.calculateMonthlyPayment(12000.00, 12));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCalculateInvalidMonthlyPayment() {
		paymentCalc.calculateMonthlyPayment(12000.00, -1);
	}

}
