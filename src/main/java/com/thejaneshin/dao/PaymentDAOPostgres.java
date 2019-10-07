package com.thejaneshin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.thejaneshin.pojo.Payment;
import com.thejaneshin.util.ConnectionFactory;

public class PaymentDAOPostgres implements PaymentDAO {
	private Connection conn = ConnectionFactory.getConnection();
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void createPayment(Payment p) {
		String sql = "insert into payment (payment_amount, p_month, paid_car, payer) values"
				+ "(?, ?, ?, ?)";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, p.getAmount());
			stmt.setInt(2, p.getPaidMonth());
			stmt.setString(3, p.getPaidCar());
			stmt.setString(4, p.getPayer());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Payment> readPaymentsByUsernameAndVin(String username, String vin) {
		List<Payment> payments = new LinkedList<>();
		String sql = "select * from car where username = ? and vin = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, vin);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				payments.add(new Payment(rs.getDouble(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return payments;
	}

	@Override
	public List<Payment> readAllPayments() {
		List<Payment> payments = new LinkedList<>();
		String sql = "select * from car";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				payments.add(new Payment(rs.getDouble(2), rs.getInt(3), rs.getString(4), rs.getString(5)));
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return payments;
	}

}
