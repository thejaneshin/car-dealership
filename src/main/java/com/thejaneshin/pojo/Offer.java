package com.thejaneshin.pojo;

import java.io.Serializable;

public class Offer implements Serializable {
	private static final long serialVersionUID = 2978143943511402133L;

	public enum StatusType {
		PENDING, ACCEPTED, REJECTED;
	}
	
	private double value;
	
	private StatusType status;
	
	private Customer offerer;
	
	private Car offeredCar;
	
	public Offer() {
		super();
	}

	public Offer(double value, StatusType status, Customer offerer, Car offeredCar) {
		super();
		this.value = value;
		this.status = status;
		this.offerer = offerer;
		this.offeredCar = offeredCar;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Customer getOfferer() {
		return offerer;
	}

	public void setOfferer(Customer offerer) {
		this.offerer = offerer;
	}

	public Car getOfferedCar() {
		return offeredCar;
	}

	public void setOfferedCar(Car offeredCar) {
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
		temp = Double.doubleToLongBits(value);
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
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
	
}
