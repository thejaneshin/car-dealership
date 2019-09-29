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

import com.thejaneshin.pojo.Customer;
import com.thejaneshin.pojo.User;

public class CustomerDAOSerialization implements CustomerDAO {

	@Override
	public void createCustomer(Customer customer) {
		String fileName;
		
		if (customer.getUsername() != null) {
			fileName = "./serializedfiles/users/" + customer.getUsername() + ".dat";
			if (new File(fileName).exists()) {
				throw new IllegalArgumentException("Username already taken");
			}
		}
		else {
			fileName = "./serializedfiles/mysteryuser.dat";
		}
		
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(customer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Customer readCustomer(String username) {
		String fileName = "./serializedfiles/users/" + username + ".dat";
		
		Customer ret = null;
		
		try (FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			ret = (Customer) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	@Override
	public Set<Customer> readAllCustomers() {
		Set<Customer> allCustomers = new HashSet<>();
		
		File file = new File("./serializedfiles/users");
		for (File f : file.listFiles()) {
			try (FileInputStream fis = new FileInputStream(f.getAbsoluteFile());
					ObjectInputStream ois = new ObjectInputStream(fis);) {
				User tempUser = (User) ois.readObject();
				
				if (tempUser instanceof Customer) {
					allCustomers.add((Customer) tempUser);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		return allCustomers;
	}

}
