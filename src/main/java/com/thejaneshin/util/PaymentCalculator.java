package com.thejaneshin.util;

import java.util.List;

public interface PaymentCalculator {
	public Double calculatePaidSoFar(List<Double> payments);
	
	public Double calculatePriceLeft(double price, List<Double> payments);
	
	public Double calculateMonthlyPayment(double price, int months);
	
}
