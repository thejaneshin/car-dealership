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
import static com.thejaneshin.util.LoggerUtil.*;

public class OfferDAOSerialization implements OfferDAO {

	@Override
	public void createOffer(Offer o) {
		String fileName;
		
		info("Serializing Offer object");
		
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
			warn(e.getMessage());
		} catch (IOException e) {
			error(e.getMessage());
		}
		
	}

	@Override
	public Offer readOfferByUsernameAndVin(String username, String vin) {
		String fileName = "./serializedfiles/offers/" + username +
				"+" + vin + ".dat";
		
		info("Deserializing Offer object");
		
		Offer returnOffer = null;
		
		try (FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			returnOffer = (Offer) ois.readObject();
		} catch (IOException e) {
			info("Offer by " + username + " on vin " + vin + " does not exist");
		} catch (ClassNotFoundException e) {
			error(e.getMessage());
		}
		
		return returnOffer;
	}

	@Override
	public Set<Offer> readAllOffers() {
		Set<Offer> allOffers = new HashSet<>();
		
		info("Deserializing all Offer objects");
		
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
		
		info("Deleting Offer object");
		
		File file = new File(fileName);
		
		if (file.exists()) {
			file.delete();
		}
		else {
			warn("Offer is nonexistent");
		}
		
	}

}
