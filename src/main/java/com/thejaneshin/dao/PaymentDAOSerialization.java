package com.thejaneshin.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import com.thejaneshin.pojo.Payment;
import static com.thejaneshin.util.LoggerUtil.*;

public class PaymentDAOSerialization implements PaymentDAO {

	@Override
	public void createPayment(Payment p) {
		String startFileName = p.getPayer() + "+" + p.getPaidCar();
		
		info("Serializing Payment object");
		
		int count = 1;
		
		for (File f : new File("./serializedfiles/payments").listFiles()) {
			if (f.getName().startsWith(startFileName)) {
				count++;
			}
		}
		
		String fileName = "./serializedfiles/payments/" + startFileName + "+" + count + ".dat";
		
		p.setPaidMonth(count);
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(p);
		} catch (FileNotFoundException e) {
			error(e.getMessage());
		} catch (IOException e) {
			error(e.getMessage());
		}
	}

	@Override
	public List<Payment> readPaymentsByUsernameAndVin(String username, String vin) {
		String startFileName = username + "+" + vin;
		
		info("Deserializing Payment objects with username " + username + " on vin "
				+ vin);
		
		List<Payment> paidPayments = new LinkedList<>();
		
		for (File f : new File("./serializedfiles/payments").listFiles()) {
			if (f.getName().startsWith(startFileName)) {
				try (FileInputStream fis = new FileInputStream(f);
						ObjectInputStream ois = new ObjectInputStream(fis);) {
					paidPayments.add((Payment) ois.readObject());
				} catch (IOException e) {
					warn(e.getMessage());
				} catch (ClassNotFoundException e) {
					error(e.getMessage());
				}
			}
		}
		
		return paidPayments;
	}

	@Override
	public List<Payment> readAllPayments() {
		List<Payment> allPayments = new LinkedList<>();
		
		info("Deserializing all Payment objects");
		
		for (File f : new File("./serializedfiles/payments").listFiles()) {
			try (FileInputStream fis = new FileInputStream(f);
					ObjectInputStream ois = new ObjectInputStream(fis);) {
				allPayments.add((Payment) ois.readObject());
			} catch (IOException e) {
				warn(e.getMessage());
			} catch (ClassNotFoundException e) {
				error(e.getMessage());
			}
		}
		
		return allPayments;
	}

}
