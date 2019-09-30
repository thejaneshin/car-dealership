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

import com.thejaneshin.pojo.Offer;

public class OfferDAOSerialization implements OfferDAO {

	@Override
	public void createOffer(Offer o) {
		String fileName;
		
		if (o.getOfferer() != null && o.getOfferedCar() != null) {
			fileName = "./serializedfiles/offers/" + o.getOfferer() +
					"+" + o.getOfferedCar() + ".dat";
		}
		else {
			fileName = "./serializedfiles/mysteryoffer.dat";
		}
		
		try (FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(o);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Offer readOfferByUsernameAndVin(String username, String vin) {
		String fileName = "./serializedfiles/offers/" + username +
				"+" + vin + ".dat";
		
		Offer returnOffer = null;
		
		try (FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			returnOffer = (Offer) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return returnOffer;
	}

	@Override
	public Set<Offer> readAllOffers() {
		Set<Offer> allOffers = new HashSet<>();
		
		File file = new File("./serializedfiles/offers");
		for (File f : file.listFiles()) {
			String noExt = f.getName().replace(".dat", "");
			String[] parts = noExt.split("\\+");
			Offer tempOffer = readOfferByUsernameAndVin(parts[0], parts[1]);
			allOffers.add(tempOffer);
		}
		
		return allOffers;
	}

	@Override
	public Offer updateOffer(Offer o) {
		String fileName = "./serializedfiles/offers/" + o.getOfferer() +
				"+" + o.getOfferedCar() + ".dat";
		
		if (new File(fileName).exists()) {
			createOffer(o);
			return o;
		}
		else {
			throw new IllegalArgumentException("Offer is nonexistent");
		}
	}

	@Override
	public void deleteOffer(Offer o) {
		String fileName = "./serializedfiles/offers/" + o.getOfferer() +
				"+" + o.getOfferedCar() + ".dat";
		
		File file = new File(fileName);
		
		if (file.exists()) {
			file.delete();
		}
		else {
			throw new IllegalArgumentException("Offer is nonexistent");
		}
		
	}

}
