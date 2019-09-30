package com.thejaneshin.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thejaneshin.dao.OfferDAO;
import com.thejaneshin.dao.OfferDAOSerialization;
import com.thejaneshin.pojo.Offer;

public class OfferDAOSerializationTest {
	private OfferDAO offerDAOSer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		offerDAOSer = new OfferDAOSerialization();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAndReadOffer() {
		Offer testOffer = new Offer(15000.00, Offer.StatusType.PENDING, "harry", "2HKRM4H31EH662200");
		offerDAOSer.createOffer(testOffer);
		
		assertEquals(testOffer, offerDAOSer.readOfferByUsernameAndVin("harry", "2HKRM4H31EH662200"));
	}
	
	@Test
	public void testReadAllOffers() {
		Set<Offer> testOffers = new HashSet<>();
		
		Offer offer1 = new Offer(15000.00, Offer.StatusType.PENDING, "harry", "2HKRM4H31EH662200");
		offerDAOSer.createOffer(offer1);
		testOffers.add(offer1);
		
		Offer offer2 = new Offer(13000.00, Offer.StatusType.PENDING, "lovecars", "2HKRM4H31EH662200");
		offerDAOSer.createOffer(offer2);
		testOffers.add(offer2);
		
		assertEquals(testOffers, offerDAOSer.readAllOffers());
	}
	
	@Test
	public void testUpdateOffer() {
		Offer testOffer = new Offer(15000.00, Offer.StatusType.PENDING, "harry", "2HKRM4H31EH662200");
		offerDAOSer.createOffer(testOffer);
		assertEquals(testOffer, offerDAOSer.readOfferByUsernameAndVin("harry", "2HKRM4H31EH662200"));
		
		testOffer.setValue(20000.00);
		offerDAOSer.updateOffer(testOffer);
		
		assertEquals(testOffer, offerDAOSer.readOfferByUsernameAndVin("harry", "2HKRM4H31EH662200"));
	}
	
	@Test(expected=Test.None.class)
	public void testDeleteOffer() {
		Offer testOffer = new Offer(1.00, Offer.StatusType.PENDING, "mrlowballer", "WDBJF65F2WA512704");
		offerDAOSer.createOffer(testOffer);

		offerDAOSer.deleteOffer(testOffer);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteNonexistantOffer() {
		Offer badOffer = new Offer(100.00, Offer.StatusType.PENDING, "nono", "WBAPK5C53BF123740");
		
		offerDAOSer.deleteOffer(badOffer);
	}
	

}
