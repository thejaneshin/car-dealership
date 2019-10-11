package com.thejaneshin.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.thejaneshin.util.ConnectionFactory;

public class CallableDriver {
	public static void main(String[] args) {
		Connection conn = ConnectionFactory.getConnection();
		
		try {
			conn.setAutoCommit(false);
			PreparedStatement call = conn.prepareCall("call deleteTestUsersAndCars()");
			call.executeUpdate();
			
			System.out.println("Deleted test cars and users");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
