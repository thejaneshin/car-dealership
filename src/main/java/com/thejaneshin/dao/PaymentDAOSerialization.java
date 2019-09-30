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

import com.thejaneshin.pojo.Payment;

public class PaymentDAOSerialization implements PaymentDAO {

	@Override
	public void createPayment(Payment p) {
		String fileName;
		
		if (p.getPayer() != null && p.getPaidCar() != null) {
			fileName = "./serializedfiles/payments/" + p.getPayer() +
					"+" + p.getPaidCar() + ".dat";
		}
		else {
			fileName = "./serializedfiles/mysterypayment.dat";
		}
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(p);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Payment readPaymentByUsernameAndVin(String username, String vin) {
		String fileName = "./serializedfiles/payments/" + username +
				"+" + vin + ".dat";
		
		Payment returnPayment = null;
		
		try (FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			returnPayment = (Payment) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return returnPayment;
	}

	@Override
	public Set<Payment> readAllPayments() {
		Set<Payment> allPayments = new HashSet<>();
		
		File file = new File("./serializedfiles/payments");
		for (File f : file.listFiles()) {
			String noExt = f.getName().replace(".dat", "");
			String[] parts = noExt.split("\\+");
			Payment tempPayment = readPaymentByUsernameAndVin(parts[0], parts[1]);
			allPayments.add(tempPayment);
		}
		
		return allPayments;
	}

}
