package com.thejaneshin.service;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.thejaneshin.dao.CarDAO;
import com.thejaneshin.dao.CarDAOSerialization;
import com.thejaneshin.dao.OfferDAO;
import com.thejaneshin.dao.OfferDAOSerialization;
import com.thejaneshin.dao.PaymentDAO;
import com.thejaneshin.dao.PaymentDAOSerialization;
import com.thejaneshin.pojo.Car;
import com.thejaneshin.pojo.Customer;
import com.thejaneshin.pojo.Offer;
import com.thejaneshin.pojo.Payment;
import com.thejaneshin.util.PaymentCalcImpl;
import com.thejaneshin.util.PaymentCalculator;
import static com.thejaneshin.util.LoggerUtil.*;

public class CustomerServiceConsoleImpl implements CustomerService {
	private static Scanner sc = new Scanner(System.in);
	private static CarDAO carDAO = new CarDAOSerialization();
	private static OfferDAO offerDAO = new OfferDAOSerialization();
	private static PaymentDAO paymentDAO = new PaymentDAOSerialization();
	private static PaymentCalculator payCalc = new PaymentCalcImpl();
	private static Customer customer;
	
	@Override
	public void run(Customer currentCustomer) {
		customer = currentCustomer;
		
		info(customer.getUsername() + " logged in");
		
		System.out.println("\nWELCOME, " + customer.getFirstName() +
				" " + customer.getLastName() + "!");
		
		while (true) {
			System.out.println("\nWhat would you like to do today?");
			
			System.out.println("\nEnter 1 to see the lot");
			System.out.println("Enter 2 to view cars you own");
			System.out.println("Enter 3 to view cars you offered on");
			System.out.println("Enter 4 to view your remaining payments");
			System.out.println("Enter q to logout");
			
			String menuOption = sc.nextLine();
			
			if (menuOption.equals("1")) {
				viewLotCars();
			}
			else if (menuOption.equals("2")) {
				viewYourCars();
			}
			else if (menuOption.equals("3")) {
				viewOfferedCars();
			}
			else if (menuOption.equals("4")) {
				viewRemainingPayments();
			}
			else if (menuOption.equals("q")) {
				break;
			}
		}
		
	}

	@Override
	public void viewLotCars() {
		while (true) {
			Set<Car> lotCars = new HashSet<>();
			
			for (Car c : carDAO.readAllCars()) {
				if (c.getStatus() == Car.StatusType.IN_LOT) {
					lotCars.add(c);
				}
			}
			
			System.out.println("\nCARS ON THE LOT:");
			
			if (lotCars.size() == 0) {
				System.out.println("No cars at the moment, check back later!");
			}
			else {
				for (Car c : lotCars) {
					System.out.println(c.getColor() + " " + c.getMake() + " " + c.getModel() + " " + c.getYear() + " [" + c.getVin() + "]");
				}
			}
			
			System.out.println("\nEnter 1 to place an offer");
			System.out.println("Enter 2 to go back to the main menu");
			
			String menuOption = sc.nextLine();
			
			if (menuOption.equals("1")) {
				offerOnCar(lotCars);
				break;
			}
			else if (menuOption.equals("2")) {
				break;
			}
			else {
				continue;
			}
		}
		
	}
	
	private void offerOnCar(Set<Car> lotCars) {
		while (true) {
			System.out.print("\nEnter the corresponding vin number to offer: ");
			String chosenVin = sc.nextLine();
			
			Car chosenCar = null;
			double offerValue = 0.00;
			
			for (Car c : lotCars) {
				if (chosenVin.equals(c.getVin())) {
					chosenCar = c;
				}
			}
			
			if (chosenCar == null) {
				info("No car with vin " + chosenVin + " in lot");
				System.out.println("No such car in lot, please choose again");
				continue;
			}
			else {
				// If already offered, then give option to change
				// Maybe add option to see highest bid available
				
				Offer newOffer = offerDAO.readOfferByUsernameAndVin(customer.getUsername(), chosenCar.getVin());
				
				if (newOffer != null) {
					System.out.printf("You've already offered $%.2f\n" + newOffer.getValue());
					System.out.println("\nEnter 1 to update your offer");
					System.out.println("Enter 2 to offer on a different car");
					
					String menuOption = sc.nextLine();
					
					if (menuOption.equals("1")) {
						updateOffer(newOffer);
						break;
					}
					else if (menuOption.equals("2")) {
						continue;
					}
					else {
						System.out.println("Please enter a valid option");
						continue;
					}
				}
				
				System.out.print("Enter your offer: ");

				try {
					offerValue = sc.nextDouble();
				} catch (InputMismatchException e) {
					// Needs to consume the newline leftover from before
					sc.nextLine();
					error("Username did not enter valid double");
					System.out.println("Please enter a valid price");
					continue;
				}
				
				// Needs to consume the newline leftover from before
				sc.nextLine();
				
				newOffer = new Offer(offerValue, Offer.StatusType.PENDING, customer.getUsername(), chosenCar.getVin());
				offerDAO.createOffer(newOffer);
				
				System.out.println("\nOffer has been submitted, thank you!");
				break;
			}
		}
	}
	
	private void updateOffer(Offer offer) {
		while (true) {
			System.out.print("\nEnter your new offer: ");
			double newValue = 0.00;
			
			try {
				newValue = sc.nextDouble();
			} catch (InputMismatchException e) {
				sc.nextLine();
				info("User entered invalid price");
				continue;
			}
			
			sc.nextLine();
			offer.setValue(newValue);
			
			offer.setStatus(Offer.StatusType.PENDING);
			offerDAO.updateOffer(offer);
			
			System.out.println("\nOffer has been updated, thank you!");
			break;
		}
		
	}

	@Override
	public void viewYourCars() {
		System.out.println("\nCARS YOU OWN:");
		
		List<Offer> acceptedCars = new LinkedList<>();
		
		for (Offer o : offerDAO.readAllOffers()) {
			if (o.getOfferer().equals(customer.getUsername()) && o.getStatus().equals(Offer.StatusType.ACCEPTED)) {
				acceptedCars.add(o);
				Car c = carDAO.readCar(o.getOfferedCar());
				System.out.printf(c.getColor() + " " + c.getMake() + " " + c.getModel()
						+ " " + c.getYear() + " [" + c.getVin() + "] - $%.2f\n", o.getValue());
			}
		}
		
		if (acceptedCars.size() == 0) {
			System.out.println("None, go buy a car :(");
		}
		
	}

	@Override
	public void viewOfferedCars() {
		while (true) {
			Set<Offer> offeredCars = new HashSet<>();
			
			for (Offer o : offerDAO.readAllOffers()) {
				if (o.getOfferer().equals(customer.getUsername()) && o.getStatus() != Offer.StatusType.ACCEPTED) {
					offeredCars.add(o);
				}
			}
			
			System.out.println("\nCARS YOU OFFERED ON:");
			
			if (offeredCars.size() == 0) {
				System.out.println("No offers at the moment");
				break;
			}
			else {
				for (Offer o : offeredCars) {
					Car c = carDAO.readCar(o.getOfferedCar());
					System.out.printf(c.getColor() + " " + c.getMake() + " " + c.getModel()
						+ " " + c.getYear() + " [" + c.getVin() + "] - $%.2f" + " (" + o.getStatus() + ")\n", o.getValue());
				}
			}
			
			System.out.println("\nEnter 1 to update an offer");
			System.out.println("Enter 2 to remove an offer");
			System.out.println("Enter 3 to go back to the main menu");
			
			String menuOption = sc.nextLine();
			
			if (menuOption.equals("1")) {
				System.out.print("\nEnter the corresponding vin number to update offer: ");
				String chosenVin = sc.nextLine();
				
				Offer offer = offerDAO.readOfferByUsernameAndVin(customer.getUsername(), chosenVin);
				
				if (offer == null) {
					System.out.println("Incorrect vin number, please try again");
					continue;
				}
				
				updateOffer(offer);
				continue;
			}
			else if (menuOption.equals("2")) {
				System.out.print("\nEnter the corresponding vin number to delete offer: ");
				String chosenVin = sc.nextLine();
				
				Offer offer = offerDAO.readOfferByUsernameAndVin(customer.getUsername(), chosenVin);
				
				if (offer == null) {
					System.out.println("Incorrect vin number, please try again");
					continue;
				}
				
				removeOffer(offer);
				continue;
			}
			else if (menuOption.equals("3")) {
				break;
			}
			else {
				System.out.println("\nPlease enter a valid menu option");
				continue;
			}
		}
	}
	
	private void removeOffer(Offer offer) {
		offerDAO.deleteOffer(offer);
		
		System.out.println("Removed offer!");
	}

	@Override
	public void viewRemainingPayments() {
		
		while (true) {
			List<Offer> myAcceptedOffers = new LinkedList<>();
			
			for (Offer o : offerDAO.readAllOffers()) {
				if (o.getOfferer().equals(customer.getUsername()) && o.getStatus() == Offer.StatusType.ACCEPTED) {
					myAcceptedOffers.add(o);
				}
			}
			
			System.out.println("\nPAYMENTS:");
			
			if (myAcceptedOffers.size() == 0) {
				System.out.println("You don't own any cars");
				break;
			}
			
			for (Offer mao : myAcceptedOffers) {
				List<Payment> payments = paymentDAO.readPaymentsByUsernameAndVin(customer.getUsername(), mao.getOfferedCar());
				List<Double> paymentAmounts = new LinkedList<>();
				
				for (Payment p : payments) {
					paymentAmounts.add(p.getAmount());
				}
				
				Car c = carDAO.readCar(mao.getOfferedCar());
				System.out.println(c.getColor() + " " + c.getMake() + " " + c.getModel()
					+ " " + c.getYear() + " [" + c.getVin() + "]");
				System.out.printf("Paid so far: $%.2f\n", payCalc.calculatePaidSoFar(paymentAmounts));
				System.out.printf("Payment left: $%.2f\n", payCalc.calculatePriceLeft(mao.getValue(), paymentAmounts));
				System.out.printf("Monthly Payment: $%.2f\n", payCalc.calculateMonthlyPayment(mao.getValue(), 12));
			}
		
			System.out.println("\nEnter 1 to make a payment");
			System.out.println("Enter 2 to go back to the main menu");
			
			String menuOption = sc.nextLine();
			
			if (menuOption.equals("1")) {
				makePayment(myAcceptedOffers);
				continue;
			}
			else if (menuOption.equals("2")) {
				break;
			}
			else {
				continue;
			}
		}
		
	}
	
	private void makePayment(List<Offer> myAcceptedOffers) {
		while (true) {
			System.out.print("\nEnter the vin number that you'll pay for: ");
			String chosenVin = sc.nextLine();
			
			Offer offer = null;
			
			for (Offer o : myAcceptedOffers) {
				if (o.getOfferedCar().equals(chosenVin)) {
					offer = o;
				}
			}
			
			if (offer == null) {
				System.out.println("\nPlease enter a valid vin number");
				continue;
			}
			
			List<Payment> payments = 
					paymentDAO.readPaymentsByUsernameAndVin(customer.getUsername(), offer.getOfferedCar());
			List<Double> paymentAmounts = new LinkedList<>();
			
			for (Payment p : payments) {
				paymentAmounts.add(p.getAmount());
			}
			
			double priceToPay = payCalc.calculateMonthlyPayment(offer.getValue(), 12);
			
			if (payCalc.calculatePriceLeft(offer.getValue(), paymentAmounts)
					< priceToPay) {
				priceToPay = payCalc.calculatePriceLeft(offer.getValue(), paymentAmounts);
			}
			
			if (payCalc.calculatePriceLeft(offer.getValue(), paymentAmounts) > 0.0) {
				Payment currentPayment = new Payment(priceToPay, customer.getUsername(), offer.getOfferedCar());
				paymentDAO.createPayment(currentPayment);
			}
			
			System.out.println("Paid successfully!");
			break;
		}
		
	}
	
}
