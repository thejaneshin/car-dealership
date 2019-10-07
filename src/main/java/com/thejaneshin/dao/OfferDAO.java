package com.thejaneshin.dao;

import java.util.List;

import com.thejaneshin.pojo.Offer;

public interface OfferDAO {
	public void createOffer(Offer o);
	
	public Offer readOfferByUsernameAndVin(String username, String vin);
	
	public List<Offer> readAllOffers();
	
	public void updateOfferAmount(Offer o);
	
	public void updateOfferStatus(Offer o);
	
	public void deleteOffer(Offer o);
}
