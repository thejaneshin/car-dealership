package com.thejaneshin.dao;

import java.util.Set;

import com.thejaneshin.pojo.Car;

public interface CarDAO {
	public void createCar(Car car);
	
	public Car readCar(String vin);
	
	public Set<Car> readAllCars();
}
