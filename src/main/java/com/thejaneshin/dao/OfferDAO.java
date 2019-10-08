package com.thejaneshin.dao;

import java.util.List;

import com.thejaneshin.pojo.Offer;

public interface OfferDAO {
	public void createOffer(Offer o);
	
	public Offer readOfferByUsernameAndVin(String username, String vin);
	
	public List<Offer> readAllOffersByUsername(String username);
	
	public List<Offer> readAllPendingOffers();
	
	public List<Offer> readAllAcceptedOffers();
	
	public List<Offer> readAllOffers();
	
	public void updateOfferAmount(Offer o);
	
	public void updateOfferStatus(Offer o);
	
	public void deleteOffer(Offer o);
}
