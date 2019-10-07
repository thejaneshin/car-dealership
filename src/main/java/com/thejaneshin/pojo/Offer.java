package com.thejaneshin.pojo;

import java.io.Serializable;

public class Offer implements Serializable {
	private static final long serialVersionUID = 2978143943511402133L;

	public enum StatusType {
		PENDING, ACCEPTED, REJECTED;
	}
	
	private double amount;
	
	private StatusType status;
	
	private String offeredCar;
	
	private String offerer;
	
	public Offer() {
		super();
	}

	public Offer(double amount, StatusType status, String offeredCar, String offerer) {
		super();
		this.amount = amount;
		this.status = status;
		this.offeredCar = offeredCar;
		this.offerer = offerer;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public String getOfferer() {
		return offerer;
	}

	public void setOfferer(String offerer) {
		this.offerer = offerer;
	}

	public String getOfferedCar() {
		return offeredCar;
	}

	public void setOfferedCar(String offeredCar) {
		this.offeredCar = offeredCar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offeredCar == null) ? 0 : offeredCar.hashCode());
		result = prime * result + ((offerer == null) ? 0 : offerer.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Offer other = (Offer) obj;
		if (offeredCar == null) {
			if (other.offeredCar != null)
				return false;
		} else if (!offeredCar.equals(other.offeredCar))
			return false;
		if (offerer == null) {
			if (other.offerer != null)
				return false;
		} else if (!offerer.equals(other.offerer))
			return false;
		if (status != other.status)
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		return true;
	}
	
}
