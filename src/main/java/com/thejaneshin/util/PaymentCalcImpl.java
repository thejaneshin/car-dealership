package com.thejaneshin.util;

import java.util.List;
import static com.thejaneshin.util.LoggerUtil.*;

public class PaymentCalcImpl implements PaymentCalculator {
	@Override
	public Double calculatePaidSoFar(List<Double> payments) {
		double totalPaid = 0;
		
		info("Calculating how much customer paid so far");
		
		for (Double d : payments) {
			if (d <= 0.0) {
				error("Payment is negative");
			}
			totalPaid += d;
		}
		
		return totalPaid;
	}
	
	@Override
	public Double calculatePriceLeft(double price, List<Double> payments) {
		double totalPaid = 0;
		
		info("Calculating how much more is owed");
		
		if (price < 0) {
			error("Price is negative");
		}
		
		for (Double d : payments) {
			if (d <= 0.0) {
				error("Payment is 0 or negative");
			}
			totalPaid += d;
		}
		
		return price - totalPaid;
	}
	
	@Override
	public Double calculateMonthlyPayment(double price, int months) {
		info("Calculating monthly payment");
		
		if (price < 0 || months <= 0) {
			error("Price and/or months are invalid");
		}
		return price / months;
	}
}
