package com.thejaneshin.test;

import static org.mockito.Mockito.when;

import java.sql.Connection;
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
import org.postgresql.core.BaseStatement;

import com.thejaneshin.dao.CarDAOPostgres;
import com.thejaneshin.pojo.Car;
import com.thejaneshin.util.ConnectionFactory;

@RunWith(MockitoJUnitRunner.class)
public class CarDAOPostgresTest {
	private CarDAOPostgres carDAO = new CarDAOPostgres();
	
	private Car car;
	
	@Mock
	private Connection conn;
	
	@Spy
	private BaseStatement stmt = (BaseStatement) ConnectionFactory.getConnection().createStatement();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ConnectionFactory.getConnection().createStatement().executeUpdate("truncate table car");
	}

	@Before
	public void setUp() throws Exception {
		when(conn.createStatement()).thenReturn(stmt);
		car = new Car();
		carDAO.setConn(conn);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateCar() {
		car = new Car("JHMCM56643C004323", "Honda", "Accord", 2014, "Red", Car.StatusType.IN_LOT, null);
		
		carDAO.createCar(car);	
		
		try {
			Mockito.verify(stmt).executeUpdate("insert into car (vin, make, model, car_year, color, car_status) values ('"
					+ car.getVin() + "', '" + car.getMake() + "', " + car.getModel() + "', '" + car.getYear() + "', '" + car.getColor() 
					+ "', '" + car.getStatus() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public CarDAOPostgresTest() throws SQLException {
		super();
	}
}
