package com.thejaneshin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.thejaneshin.pojo.Offer;
import com.thejaneshin.util.ConnectionFactory;

import static com.thejaneshin.util.LoggerUtil.*;

public class OfferDAOPostgres implements OfferDAO {
	private Connection conn = ConnectionFactory.getConnection();
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void createOffer(Offer o) {
		String sql = "insert into offer (offer_amount, offer_status, offered_car, offerer) values"
				+ "(?, ?, ?, ?)";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, o.getAmount());
			stmt.setString(2, o.getStatus().name());
			stmt.setString(3, o.getOfferedCar());
			stmt.setString(4, o.getOfferer());
			stmt.executeUpdate();
			info("Successfully created offer on car " + o.getOfferedCar() + " by user " + o.getOfferer());
		} catch (SQLException e) {
			error(e.getMessage());
		}	
	}

	@Override
	public Offer readOfferByUsernameAndVin(String username, String vin) {
		String sql = "select * from offer where offered_car = ? and offerer = ?";
		
		Offer offer = null;
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, vin);
			stmt.setString(2, username);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				offer = new Offer(rs.getDouble(2), Offer.StatusType.valueOf(rs.getString(3)), rs.getString(4), rs.getString(5));
			}
			
			info("Successfully read offer on car " + vin + " by user " + username);
		} catch (SQLException e) {
			error(e.getMessage());
		}
		
		return offer;
	}
	
	@Override
	public List<Offer> readAllOffersByUsername(String username) {
		List<Offer> offers = new LinkedList<>();
		String sql = "select * from offer where offerer = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				offers.add(new Offer(rs.getDouble(2), Offer.StatusType.valueOf(rs.getString(3)), rs.getString(4), rs.getString(5)));
			}
			
			info("Successfully read all offers by user " + username);
		} catch (SQLException e) {
			error(e.getMessage());
		}
		
		return offers;
	}

	@Override
	public List<Offer> readAllPendingOffers() {
		List<Offer> offers = new LinkedList<>();
		String sql = "select * from offer where offer_status = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, Offer.StatusType.PENDING.name());
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				offers.add(new Offer(rs.getDouble(2), Offer.StatusType.valueOf(rs.getString(3)), rs.getString(4), rs.getString(5)));
			}
			
			info("Successfully read all pending offers");
		} catch (SQLException e) {
			error(e.getMessage());
		}
		
		return offers;
	}
	
	@Override
	public List<Offer> readAllAcceptedOffers() {
		List<Offer> offers = new LinkedList<>();
		String sql = "select * from offer where offer_status = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, Offer.StatusType.ACCEPTED.name());
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				offers.add(new Offer(rs.getDouble(2), Offer.StatusType.valueOf(rs.getString(3)), rs.getString(4), rs.getString(5)));
			}
					
			info("Successfully read all accepted offers");
		} catch (SQLException e) {
			error(e.getMessage());
		}
		
		return offers;
	}

	
	@Override
	public List<Offer> readAllOffers() {
		List<Offer> offers = new LinkedList<>();
		String sql = "select * from offer";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				offers.add(new Offer(rs.getDouble(2), Offer.StatusType.valueOf(rs.getString(3)), rs.getString(4), rs.getString(5)));
			}
			
			info("Successfully read all offers");		
		} catch (SQLException e) {
			error(e.getMessage());
		}
		
		return offers;
	}

	@Override
	public void updateOfferAmount(Offer o) {
		String sql = "update offer set offer_amount = ? where offered_car = ? and offerer = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, o.getAmount());
			stmt.setString(2, o.getOfferedCar());
			stmt.setString(3, o.getOfferer());
			stmt.executeUpdate();
			info("Successfully updated offer on car " + o.getOfferedCar() + " by user " + o.getOfferer()
					+ " with value of " + o.getAmount());
		} catch (SQLException e) {
			error(e.getMessage());
		}
		
	}

	@Override
	public void updateOfferStatus(Offer o) {
		String sql = "update offer set offer_status = ? where offered_car = ? and offerer = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, o.getStatus().name());
			stmt.setString(2, o.getOfferedCar());
			stmt.setString(3, o.getOfferer());
			stmt.executeUpdate();
			info("Successfully updated offer on car " + o.getOfferedCar() + " by user " + o.getOfferer()
			+ " to status " + o.getStatus());
		} catch (SQLException e) {
			error(e.getMessage());
		}
		
	}

	@Override
	public void deleteOffer(Offer o) {
		String sql = "delete from offer where offered_car = ? and offerer = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, o.getOfferedCar());
			stmt.setString(2, o.getOfferer());
			stmt.executeUpdate();
			info("Successfully deleted offer on car " + o.getOfferedCar() + " by user " + o.getOfferer());
		} catch (SQLException e) {
			error(e.getMessage());
		}

	}
}
