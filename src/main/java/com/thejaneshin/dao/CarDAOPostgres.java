package com.thejaneshin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.thejaneshin.pojo.Car;
import com.thejaneshin.util.ConnectionFactory;

public class CarDAOPostgres implements CarDAO {
	private Connection conn = ConnectionFactory.getConnection();
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void createCar(Car car) {
		String sql = "insert into car (vin, make, model, car_year, color, car_status, car_owner) values"
				+ "(?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, car.getVin());
			stmt.setString(2, car.getMake());
			stmt.setString(3, car.getModel());
			stmt.setInt(4, car.getYear());
			stmt.setString(5, car.getColor());
			stmt.setString(6, car.getStatus().name());
			stmt.setString(7, car.getOwner());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Car readCar(String vin) {
		String sql = "select * from car where vin = ?";
		
		Car car = null;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, vin);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				car = new Car(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), Car.StatusType.valueOf(rs.getString(6)), rs.getString(7));
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return car;
	}

	@Override
	public List<Car> readAllLotCars() {
		List<Car> cars = new LinkedList<>();
		String sql = "select * from car where car_status = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, Car.StatusType.IN_LOT.name());
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				cars.add(new Car(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), Car.StatusType.valueOf(rs.getString(6)), rs.getString(7)));
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cars;
	}
	
	@Override
	public List<Car> readYourCars(String username) {
		List<Car> cars = new LinkedList<>();
		String sql = "select * from car where car_owner = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				cars.add(new Car(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), Car.StatusType.valueOf(rs.getString(6)), rs.getString(7)));
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cars;
	}
	
	@Override
	public List<Car> readAllCars() {
		List<Car> cars = new LinkedList<>();
		String sql = "select * from car";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				cars.add(new Car(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), Car.StatusType.valueOf(rs.getString(6)), rs.getString(7)));
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cars;
	}

	@Override
	public void updateCarOwner(Car car) {
		String sql = "update car set car_owner = ? where vin = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, car.getOwner());
			stmt.setString(2, car.getVin());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void updateCarStatus(Car car) {
		String sql = "update car set car_status = ? where vin = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, car.getStatus().name());
			stmt.setString(2, car.getVin());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCar(Car car) {
		String sql = "delete from car where vin = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, car.getVin());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
