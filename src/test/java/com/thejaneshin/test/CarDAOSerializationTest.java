package com.thejaneshin.test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thejaneshin.dao.CarDAO;
import com.thejaneshin.dao.CarDAOSerialization;
import com.thejaneshin.pojo.Car;

public class CarDAOSerializationTest {
	private CarDAO carDAOSer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		carDAOSer = new CarDAOSerialization();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCreateAndReadCar() {
		Car testCar = new Car("JNRAS08U76X106242", "Hyundai", "NEXO", 2019, "white", Car.StatusType.IN_LOT);
		carDAOSer.createCar(testCar);
		
		assertEquals(testCar, carDAOSer.readCar("JNRAS08U76X106242"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSameCars() {
		Car testCar1 = new Car("2T1CG22P4YC355379", "Honda", "Accord", 2015, "blue", Car.StatusType.IN_LOT);
		carDAOSer.createCar(testCar1);
		
		Car testCar2 = new Car("2T1CG22P4YC355379", "Honda", "Accord", 2015, "blue", Car.StatusType.IN_LOT);
		carDAOSer.createCar(testCar2);
	}
	
	@Test
	public void testReadAllCars() {
		Set<Car> testCars = new HashSet<>();
		
		Car car1 = new Car("JNRAS08U76X106242", "Hyundai", "NEXO", 2019, "white", Car.StatusType.IN_LOT);
		carDAOSer.createCar(car1);
		testCars.add(car1);
		Car car2 = new Car("2HKRM4H31EH662200", "Ferrari", "California", 2019, "red", Car.StatusType.IN_LOT);
		carDAOSer.createCar(car2);
		testCars.add(car2);
		Car car3 = new Car("3GCPCSE0XBG398844", "Lexus", "CT", 2016, "silver", Car.StatusType.IN_LOT);
		carDAOSer.createCar(car3);
		testCars.add(car3);
		
		assertEquals(testCars, carDAOSer.readAllCars());
	}
}
