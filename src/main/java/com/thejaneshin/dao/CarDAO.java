package com.thejaneshin.dao;

import java.util.List;

import com.thejaneshin.pojo.Car;

public interface CarDAO {
	public void createCar(Car car);
	
	public Car getCar(String vin);
	
	public List<Car> getAllCars();
	
	public void deleteCar(Car car);
}
