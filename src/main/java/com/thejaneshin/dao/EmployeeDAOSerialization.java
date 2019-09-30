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

public class EmployeeDAOSerialization implements EmployeeDAO {

	@Override
	public void createEmployee(Employee employee) {
		String fileName;
		
		if (employee.getUsername() != null) {
			fileName = "./serializedfiles/users/" + employee.getUsername() + ".dat";
			if (new File(fileName).exists()) {
				throw new IllegalArgumentException("Username already taken");
			}
		}
		else {
			fileName = "./serializedfiles/mysteryuser.dat";
		}
		
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(employee);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Employee readEmployee(String username) {
		String fileName = "./serializedfiles/users/" + username + ".dat";
		
		Employee ret = null;
		
		try (FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			ret = (Employee) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	@Override
	public Set<Employee> readAllEmployees() {
		Set<Employee> allEmployees = new HashSet<>();
		
		File file = new File("./serializedfiles/users");
		for (File f : file.listFiles()) {
			try (FileInputStream fis = new FileInputStream(f.getAbsoluteFile());
					ObjectInputStream ois = new ObjectInputStream(fis);) {
				User tempUser = (User) ois.readObject();
				
				if (tempUser instanceof Employee) {
					allEmployees.add((Employee) tempUser);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		return allEmployees;
	}
	
}
