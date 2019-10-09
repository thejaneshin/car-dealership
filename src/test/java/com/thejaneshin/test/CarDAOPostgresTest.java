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

import com.thejaneshin.dao.CarDAOPostgres;
import com.thejaneshin.pojo.Car;
import com.thejaneshin.util.ConnectionFactory;

@RunWith(MockitoJUnitRunner.class)
public class CarDAOPostgresTest {
	private CarDAOPostgres carDAO = new CarDAOPostgres();
	private Car car;
	private String sql;
	
	@Mock
	private Connection conn;
	
	@Spy
	PreparedStatement createCarStmt = ConnectionFactory.getConnection().prepareStatement("insert into car (vin, make, model, car_year, color, car_status, car_owner) values"
			+ "(?, ?, ?, ?, ?, ?, ?)");
	
	@Spy
	PreparedStatement readCarStmt = ConnectionFactory.getConnection().prepareStatement("select * from car where vin = ?");
	
	@Spy
	PreparedStatement readLotCarsStmt = ConnectionFactory.getConnection().prepareStatement("select * from car where car_status = ?");
	
	@Spy
	PreparedStatement readUserCarsStmt = ConnectionFactory.getConnection().prepareStatement("select * from car where car_owner = ?");
	
	@Spy
	PreparedStatement readAllCarsStmt = ConnectionFactory.getConnection().prepareStatement("select * from car");
	
	@Spy
	PreparedStatement updateCarOwnerStmt = ConnectionFactory.getConnection().prepareStatement("update car set car_owner = ? where vin = ?");
	
	@Spy
	PreparedStatement updateCarStatusStmt = ConnectionFactory.getConnection().prepareStatement("update car set car_status = ? where vin = ?");
	
	@Spy
	PreparedStatement deleteCarStmt = ConnectionFactory.getConnection().prepareStatement("delete from car where vin = ?");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		car = new Car("JHMCM56643C004323", "Honda", "Accord", 2014, "Red", Car.StatusType.IN_LOT, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateCar() {
		sql = "insert into car (vin, make, model, car_year, color, car_status, car_owner) values"
				+ "(?, ?, ?, ?, ?, ?, ?)";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(createCarStmt);
			carDAO.setConn(conn);
			carDAO.createCar(car);
			Mockito.verify(createCarStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testReadCar() {
		sql = "select * from car where vin = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readCarStmt);
			carDAO.setConn(conn);
			carDAO.readCar(car.getVin());
			Mockito.verify(readCarStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReadAllLotCars() {
		sql = "select * from car where car_status = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readLotCarsStmt);
			carDAO.setConn(conn);
			carDAO.readAllLotCars();
			Mockito.verify(readLotCarsStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadUserCars() {
		sql = "select * from car where car_owner = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readUserCarsStmt);
			carDAO.setConn(conn);
			carDAO.readUserCars(car.getOwner());
			Mockito.verify(readUserCarsStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadAllCars() {
		sql = "select * from car";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readAllCarsStmt);
			carDAO.setConn(conn);
			carDAO.readAllCars();
			Mockito.verify(readAllCarsStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateCarOwner() {
		sql = "update car set car_owner = ? where vin = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(updateCarOwnerStmt);
			carDAO.setConn(conn);
			carDAO.updateCarOwner(car);
			Mockito.verify(updateCarOwnerStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateCarStatus() {
		sql = "update car set car_status = ? where vin = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(updateCarStatusStmt);
			carDAO.setConn(conn);
			carDAO.updateCarStatus(car);
			Mockito.verify(updateCarStatusStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteCar() {
		sql = "delete from car where vin = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(deleteCarStmt);
			carDAO.setConn(conn);
			carDAO.deleteCar(car);
			Mockito.verify(deleteCarStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public CarDAOPostgresTest() throws SQLException {
		super();
	}
}
