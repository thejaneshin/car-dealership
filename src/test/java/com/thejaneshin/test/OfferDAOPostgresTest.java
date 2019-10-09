package com.thejaneshin.test;

import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.thejaneshin.dao.OfferDAOPostgres;
import com.thejaneshin.pojo.Offer;
import com.thejaneshin.util.ConnectionFactory;

@RunWith(MockitoJUnitRunner.class)
public class OfferDAOPostgresTest {
	private OfferDAOPostgres offerDAO = new OfferDAOPostgres();
	private Offer offer;
	private String sql;
	
	@Mock
	private Connection conn;
	
	@Spy
	PreparedStatement createOfferStmt = ConnectionFactory.getConnection().prepareStatement("insert into offer (offer_amount, offer_status, offered_car, offerer) values"
			+ "(?, ?, ?, ?)");

	@Spy
	PreparedStatement readOfferByUsernameAndVinStmt = ConnectionFactory.getConnection().prepareStatement("select * from offer where offered_car = ? and offerer = ?");
	
	@Spy
	PreparedStatement readAllOffersByUsernameStmt = ConnectionFactory.getConnection().prepareStatement("select * from offer where offerer = ?");
	
	@Spy
	PreparedStatement readAllPendingOffersStmt = ConnectionFactory.getConnection().prepareStatement("select * from offer where offer_status = ?");
	
	@Spy
	PreparedStatement readAllAcceptedOffersStmt = ConnectionFactory.getConnection().prepareStatement("select * from offer where offer_status = ?");
	
	@Spy
	PreparedStatement readAllOffersStmt = ConnectionFactory.getConnection().prepareStatement("select * from offer");
	
	@Spy
	PreparedStatement updateOfferAmountStmt = ConnectionFactory.getConnection().prepareStatement("update offer set offer_amount = ? where offered_car = ? and offerer = ?");
	
	@Spy
	PreparedStatement updateOfferStatusStmt = ConnectionFactory.getConnection().prepareStatement("update offer set offer_status = ? where offered_car = ? and offerer = ?");
	
	@Spy
	PreparedStatement deleteOfferStmt = ConnectionFactory.getConnection().prepareStatement("delete from offer where offered_car = ? and offerer = ?");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		offer = new Offer(80000.00, Offer.StatusType.PENDING, "JHMCM56643C004323", "ned");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateOffer() {
		sql = "insert into offer (offer_amount, offer_status, offered_car, offerer) values"
				+ "(?, ?, ?, ?)";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(createOfferStmt);
			offerDAO.setConn(conn);
			offerDAO.createOffer(offer);
			Mockito.verify(createOfferStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	@Test
	public void testReadOfferByUsernameAndVin() {
		sql = "select * from offer where offered_car = ? and offerer = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readOfferByUsernameAndVinStmt);
			offerDAO.setConn(conn);
			offerDAO.readOfferByUsernameAndVin(offer.getOfferer(), offer.getOfferedCar());
			Mockito.verify(readOfferByUsernameAndVinStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadAllOffersByUsername() {
		sql = "select * from offer where offerer = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readAllOffersByUsernameStmt);
			offerDAO.setConn(conn);
			offerDAO.readAllOffersByUsername(offer.getOfferer());
			Mockito.verify(readAllOffersByUsernameStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadAllPendingOffers() {
		sql = "select * from offer where offer_status = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readAllPendingOffersStmt);
			offerDAO.setConn(conn);
			offerDAO.readAllPendingOffers();
			Mockito.verify(readAllPendingOffersStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadAllAcceptedOffers() {
		sql = "select * from offer where offer_status = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readAllAcceptedOffersStmt);
			offerDAO.setConn(conn);
			offerDAO.readAllAcceptedOffers();
			Mockito.verify(readAllAcceptedOffersStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadAllOffers() {
		sql = "select * from offer";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readAllOffersStmt);
			offerDAO.setConn(conn);
			offerDAO.readAllOffers();
			Mockito.verify(readAllOffersStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateOfferAmount() {
		sql = "update offer set offer_amount = ? where offered_car = ? and offerer = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(updateOfferAmountStmt);
			offerDAO.setConn(conn);
			offerDAO.updateOfferAmount(offer);
			Mockito.verify(updateOfferAmountStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateOfferStatus() {
		sql = "update offer set offer_status = ? where offered_car = ? and offerer = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(updateOfferStatusStmt);
			offerDAO.setConn(conn);
			offerDAO.updateOfferStatus(offer);
			Mockito.verify(updateOfferStatusStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteOffer() {
		sql = "delete from offer where offered_car = ? and offerer = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(deleteOfferStmt);
			offerDAO.setConn(conn);
			offerDAO.deleteOffer(offer);
			Mockito.verify(deleteOfferStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public OfferDAOPostgresTest() throws SQLException {
		super();
	}
}
