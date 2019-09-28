package com.thejaneshin.dao;

import com.thejaneshin.pojo.Offer;

public interface OfferDAO {
	public void createOffer(Offer o);
	
	public Offer getOffer(/*What to put in here...*/);
	
	public Offer getAllOffers();
	
	public Offer updateOffer(Offer o);
	
	public void deleteOffer(Offer o);
}
