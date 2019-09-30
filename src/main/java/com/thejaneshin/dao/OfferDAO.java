package com.thejaneshin.dao;

import java.util.Set;

import com.thejaneshin.pojo.Offer;

public interface OfferDAO {
	public void createOffer(Offer o);
	
	public Offer readOfferByUsernameAndVin(String username, String vin);
	
	public Set<Offer> readAllOffers();
	
	public Offer updateOffer(Offer o);
	
	public void deleteOffer(Offer o);
}
