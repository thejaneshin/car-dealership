package com.thejaneshin.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Customer extends User implements Serializable {
	private static final long serialVersionUID = 8550257168760673340L;

	// Doing List instead of Set because it looks better sorted
	private List<Offer> offers;
	
	private List<Car> ownedCars;
	
	// Might need to change this to include monthly
	private Map<Car, Payment> remainingPayments;
	
	public Customer() {
		super();
	}
	
	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public List<Car> getOwnedCars() {
		return ownedCars;
	}

	public void setOwnedCars(List<Car> ownedCars) {
		this.ownedCars = ownedCars;
	}

	public Map<Car, Payment> getRemainingPayments() {
		return remainingPayments;
	}

	public void setRemainingPayments(Map<Car, Payment> remainingPayments) {
		this.remainingPayments = remainingPayments;
	}
	
}
