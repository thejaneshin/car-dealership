package com.thejaneshin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.thejaneshin.pojo.User;
import com.thejaneshin.util.ConnectionFactory;

public class UserDAOPostgres implements UserDAO {
	private Connection conn = ConnectionFactory.getConnection();
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void createUser(User user) {
		String sql = "insert into c_user (username, pword, firstname, lastname, u_role) values"
				+ "(?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getRole().name());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public User readUser(String username) {
		String sql = "select * from c_user where username = ?";
		
		User user = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				user = new User(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), User.RoleType.valueOf(rs.getString(5)));
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public List<User> readAllUsers() {
		List<User> users = new LinkedList<>();
		String sql = "select * from c_user";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), User.RoleType.valueOf(rs.getString(5))));
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

}
