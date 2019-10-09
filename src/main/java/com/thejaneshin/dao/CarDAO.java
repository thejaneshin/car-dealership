package com.thejaneshin.dao;

import java.util.List;

import com.thejaneshin.pojo.Car;

public interface CarDAO {
	public void createCar(Car car);
	
	public Car readCar(String vin);
	
	public List<Car> readAllLotCars();
	
	public List<Car> readUserCars(String username);
	
	public List<Car> readAllCars();
	
	public void updateCarStatus(Car car);
	
	public void updateCarOwner(Car car);
	
	public void deleteCar(Car car);
}
