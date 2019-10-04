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

import static com.thejaneshin.util.LoggerUtil.*;

import com.thejaneshin.pojo.Customer;
import com.thejaneshin.pojo.User;

public class CustomerDAOSerialization implements CustomerDAO {

	@Override
	public void createCustomer(Customer customer) {
		String fileName;
		
		info("Serializing Customer object");
		
		if (customer.getUsername() != null) {
			fileName = "./serializedfiles/users/" + customer.getUsername() + ".dat";
			if (new File(fileName).exists()) {
				warn("Customer with username " + customer.getUsername() + " already exists");
			}
		}
		else {
			fileName = "./serializedfiles/mysteryuser.dat";
		}
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(customer);
		} catch (FileNotFoundException e) {
			error(e.getMessage());
		} catch (IOException e) {
			error(e.getMessage());
		}
	}

	@Override
	public User readCustomer(String username) {
		String fileName = "./serializedfiles/users/" + username + ".dat";
		
		info("Deserializing Customer object");
		
		User ret = null;
		
		try (FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			ret = (User) ois.readObject();
		} catch (IOException e) {
			info(fileName + " is not in serializedfiles");
		} catch (ClassNotFoundException e) {
			error(e.getMessage());
		}
		
		return ret;
	}

	@Override
	public Set<Customer> readAllCustomers() {
		Set<Customer> allCustomers = new HashSet<>();
		
		info("Deserializing all Customer objects");
		
		File file = new File("./serializedfiles/users");
		for (File f : file.listFiles()) {
			try (FileInputStream fis = new FileInputStream(f.getAbsoluteFile());
					ObjectInputStream ois = new ObjectInputStream(fis);) {
				User tempUser = (User) ois.readObject();
				
				if (tempUser instanceof Customer) {
					allCustomers.add((Customer) tempUser);
				}
			} catch (FileNotFoundException e) {
				error(e.getMessage());
			} catch (IOException e) {
				error(e.getMessage());
			} catch (ClassNotFoundException e) {
				error(e.getMessage());
			}
		}
		
		return allCustomers;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		String fileName = "./serializedfiles/users/" + customer.getUsername() + ".dat";
		
		info("Serializing Customer object through update");
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(customer);
		} catch (FileNotFoundException e) {
			warn(e.getMessage());
		} catch (IOException e) {
			error(e.getMessage());
		}
		return customer;
	}

}
