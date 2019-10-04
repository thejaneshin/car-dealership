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

import com.thejaneshin.pojo.Employee;
import com.thejaneshin.pojo.User;
import static com.thejaneshin.util.LoggerUtil.*;

public class EmployeeDAOSerialization implements EmployeeDAO {

	@Override
	public void createEmployee(Employee employee) {
		String fileName;
		
		info("Serializing Employee object");
		
		if (employee.getUsername() != null) {
			fileName = "./serializedfiles/users/" + employee.getUsername() + ".dat";
			if (new File(fileName).exists()) {
				warn("Employee with username " + employee.getUsername() + " already exists");
			}
		}
		else {
			error("Employee doesn't have a username");
			fileName = "./serializedfiles/mysteryuser.dat";
		}
		
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(employee);
		} catch (FileNotFoundException e) {
			error(e.getMessage());
		} catch (IOException e) {
			error(e.getMessage());
		}
	}

	@Override
	public User readEmployee(String username) {
		String fileName = "./serializedfiles/users/" + username + ".dat";
		
		info("Deserializing Employee object");
		
		User ret = null;
		
		try (FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			ret = (User) ois.readObject();
		} catch (IOException e) {
			info("Employee with username " + username + " does not exist");
		} catch (ClassNotFoundException e) {
			error(e.getMessage());
		}
		
		return ret;
	}

	@Override
	public Set<Employee> readAllEmployees() {
		Set<Employee> allEmployees = new HashSet<>();
		
		info("Deserializing all Employee objects");
		
		File file = new File("./serializedfiles/users");
		for (File f : file.listFiles()) {
			try (FileInputStream fis = new FileInputStream(f.getAbsoluteFile());
					ObjectInputStream ois = new ObjectInputStream(fis);) {
				User tempUser = (User) ois.readObject();
				
				if (tempUser instanceof Employee) {
					allEmployees.add((Employee) tempUser);
				}
			} catch (FileNotFoundException e) {
				warn(e.getMessage());
			} catch (IOException e) {
				error(e.getMessage());
			} catch (ClassNotFoundException e) {
				error(e.getMessage());
			}		
		}
		
		return allEmployees;
	}
	
}
