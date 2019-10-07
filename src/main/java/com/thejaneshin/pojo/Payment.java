package com.thejaneshin.pojo;

import java.io.Serializable;

public class Payment implements Serializable {
	private static final long serialVersionUID = -726982492406966982L;

	private double amount;
	
	private int paidMonth;
	
	private String paidCar;
	
	private String payer;
	
	public Payment() {
		super();
	}

	public Payment(double amount, String paidCar, String payer) {
		super();
		this.amount = amount;
		this.paidCar = paidCar;
		this.payer = payer;
	}

	public Payment(double amount, int paidMonth, String paidCar, String payer) {
		super();
		this.amount = amount;
		this.paidMonth = paidMonth;
		this.paidCar = paidCar;
		this.payer = payer;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getPaidMonth() {
		return paidMonth;
	}

	public void setPaidMonth(int paidMonth) {
		this.paidMonth = paidMonth;
	}

	public String getPaidCar() {
		return paidCar;
	}

	public void setPaidCar(String paidCar) {
		this.paidCar = paidCar;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
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
		result = prime * result + paidMonth;
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
		if (paidMonth != other.paidMonth)
			return false;
		if (payer == null) {
			if (other.payer != null)
				return false;
		} else if (!payer.equals(other.payer))
			return false;
		return true;
	}
	
}
