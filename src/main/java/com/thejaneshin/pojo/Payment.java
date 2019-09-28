package com.thejaneshin.pojo;

import java.io.Serializable;

public class Payment implements Serializable {
	private static final long serialVersionUID = -726982492406966982L;

	private double amount;
	
	private Car paidCar;
	
	private Customer payer;
	
	public Payment() {
		super();
	}

	public Payment(double amount, Car paidCar, Customer payer) {
		super();
		this.amount = amount;
		this.paidCar = paidCar;
		this.payer = payer;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Car getPaidCar() {
		return paidCar;
	}

	public void setPaidCar(Car paidCar) {
		this.paidCar = paidCar;
	}

	public Customer getPayer() {
		return payer;
	}

	public void setPayer(Customer payer) {
		this.payer = payer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((paidCar == null) ? 0 : paidCar.hashCode());
		result = prime * result + ((payer == null) ? 0 : payer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (paidCar == null) {
			if (other.paidCar != null)
				return false;
		} else if (!paidCar.equals(other.paidCar))
			return false;
		if (payer == null) {
			if (other.payer != null)
				return false;
		} else if (!payer.equals(other.payer))
			return false;
		return true;
	}
	
}
