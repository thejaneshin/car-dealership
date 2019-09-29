package com.thejaneshin.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import com.thejaneshin.pojo.Car;

public class CarDAOSerialization implements CarDAO {

	@Override
	public void createCar(Car car) {
		String fileName;
		
		if (car.getVin() != null) {
			fileName = "./serializedfiles/cars/" + car.getVin() + ".dat";
			if (new File(fileName).exists()) {
				throw new IllegalArgumentException("Vin already exists");
			}
		}
		else {
			fileName = "./serializedfiles/mysterycar.dat";
		}
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(car);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Car readCar(String vin) {
		String fileName = "./serializedfiles/cars/" + vin + ".dat";
		
		Car returnCar = null;
		
		try (FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			returnCar = (Car) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return returnCar;
	}

	@Override
	public Set<Car> readAllCars() {
		Set<Car> allCars = new HashSet<>();
		
		File file = new File("./serializedfiles/cars");
		for (File f : file.listFiles()) {
			String tempVin = f.getName().replace(".dat", "");
			Car tempCar = readCar(tempVin);
			allCars.add(tempCar);
		}
		
		return allCars;
	}

}