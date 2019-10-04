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
import static com.thejaneshin.util.LoggerUtil.*;

public class CarDAOSerialization implements CarDAO {

	@Override
	public void createCar(Car car) {
		String fileName;
		
		info("Serializing Car object");
		
		if (car.getVin() != null) {
			fileName = "./serializedfiles/cars/" + car.getVin() + ".dat";
			if (new File(fileName).exists()) {
				warn("Vin" + car.getVin() + " already exists");
			}
		}
		else {
			fileName = "./serializedfiles/mysterycar.dat";
		}
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(car);
		} catch (FileNotFoundException e) {
			error(e.getMessage());
		} catch (IOException e) {
			error(e.getMessage());
		}
	}

	@Override
	public Car readCar(String vin) {
		String fileName = "./serializedfiles/cars/" + vin + ".dat";
		
		info("Deserializing Car object");
		
		Car returnCar = null;
		
		try (FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			returnCar = (Car) ois.readObject();
		} catch (IOException e) {
			info("Car with vin " + vin + " does not exist");
		} catch (ClassNotFoundException e) {
			error(e.getMessage());
		}
		
		return returnCar;
	}

	@Override
	public Set<Car> readAllCars() {
		Set<Car> allCars = new HashSet<>();
		
		info("Deserializing all Car objects");
		
		File file = new File("./serializedfiles/cars");
		for (File f : file.listFiles()) {
			String tempVin = f.getName().replace(".dat", "");
			Car tempCar = readCar(tempVin);
			allCars.add(tempCar);
		}
		
		return allCars;
	}

	@Override
	public Car updateCar(Car car) {
		String fileName = "./serializedfiles/cars/" + car.getVin() + ".dat";
		
		info("Serializing Car object through update");
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(car);
		} catch (FileNotFoundException e) {
			warn(e.getMessage());
		} catch (IOException e) {
			error(e.getMessage());
		}
		return car;
	}
	
	@Override
	public void deleteCar(Car car) {
		String fileName = "./serializedfiles/cars/" + car.getVin() + ".dat";
		
		File file = new File(fileName);
		
		info("Deleting Car file");
		
		if (file.exists()) {
			file.delete();
		}
		else {
			warn("Car with vin " + car.getVin() + " is nonexistent");
		}
		
	}

}