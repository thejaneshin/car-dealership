package com.thejaneshin.pojo;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class Customer extends User implements Serializable {
	private static final long serialVersionUID = 8550257168760673340L;

	private Set<Offer> offers;
	
	private Set<Car> ownedCars;
	
	// Might need to change this to include monthly
	private Map<Car, Payment> remainingPayments;
	
	public Customer() {
		super();
	}
	
	public Customer(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
	}
	
	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	public Set<Car> getOwnedCars() {
		return ownedCars;
	}

	public void setOwnedCars(Set<Car> ownedCars) {
		this.ownedCars = ownedCars;
	}

	public Map<Car, Payment> getRemainingPayments() {
		return remainingPayments;
	}

	public void setRemainingPayments(Map<Car, Payment> remainingPayments) {
		this.remainingPayments = remainingPayments;
	}
	
}
