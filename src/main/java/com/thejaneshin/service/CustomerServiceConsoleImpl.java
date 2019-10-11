package com.thejaneshin.service;

import static com.thejaneshin.util.LoggerUtil.*;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.thejaneshin.dao.CarDAO;
import com.thejaneshin.dao.CarDAOPostgres;
import com.thejaneshin.dao.OfferDAO;
import com.thejaneshin.dao.OfferDAOPostgres;
import com.thejaneshin.dao.PaymentDAO;
import com.thejaneshin.dao.PaymentDAOPostgres;
import com.thejaneshin.pojo.Car;
import com.thejaneshin.pojo.Offer;
import com.thejaneshin.pojo.Payment;
import com.thejaneshin.pojo.User;
import com.thejaneshin.util.PaymentCalcImpl;
import com.thejaneshin.util.PaymentCalculator;

public class CustomerServiceConsoleImpl implements CustomerService {
	private static Scanner sc = new Scanner(System.in);
	private static CarDAO carDAO = new CarDAOPostgres();
	private static OfferDAO offerDAO = new OfferDAOPostgres();
	private static PaymentDAO paymentDAO = new PaymentDAOPostgres();
	private static PaymentCalculator payCalc = new PaymentCalcImpl();
	private static User customer;
	
	@Override
	public void run(User currentCustomer) {
		customer = currentCustomer;
		
		info("Customer " + customer.getUsername() + " logged in");
		
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
			info("Viewing cars in lot");
			List<Car> lotCars = carDAO.readAllLotCars();
			
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
	
	private void offerOnCar(List<Car> lotCars) {
		info("Offering on a car");
		
		while (true) {
			System.out.print("\nEnter the corresponding vin number to offer: ");
			String chosenVin = sc.nextLine();
			
			Car chosenCar = carDAO.readCar(chosenVin);
			double offerValue = 0.00;
			
			if (chosenCar == null) {
				System.out.println("No such car in lot, please choose again");
				continue;
			}
			else {
				// Maybe add option to see highest bid available
				
				Offer newOffer = offerDAO.readOfferByUsernameAndVin(customer.getUsername(), chosenCar.getVin());
				
				if (newOffer != null && newOffer.getStatus() != Offer.StatusType.REJECTED) {
					System.out.printf("You've already offered on this car");
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
					warn("Username did not enter valid double");
					System.out.println("Please enter a valid price");
					continue;
				}
				
				// Needs to consume the newline leftover from before
				sc.nextLine();
				
				newOffer = new Offer(offerValue, Offer.StatusType.PENDING, chosenCar.getVin(), customer.getUsername());
				offerDAO.createOffer(newOffer);
				
				System.out.println("\nOffer has been submitted, thank you!");
				break;
			}
		}
	}
	
	private void updateOffer(Offer offer) {
		info("Updating an offer");
		
		while (true) {
			System.out.print("\nEnter your new offer: ");
			double newValue = 0.00;
			
			try {
				newValue = sc.nextDouble();
			} catch (InputMismatchException e) {
				sc.nextLine();
				warn("User entered invalid price");
				continue;
			}
			
			sc.nextLine();
			offer.setAmount(newValue);
			offerDAO.updateOfferAmount(offer);
			
			offer.setStatus(Offer.StatusType.PENDING);
			offerDAO.updateOfferStatus(offer);
			
			System.out.println("\nOffer has been updated, thank you!");
			break;
		}
		
	}

	@Override
	public void viewYourCars() {
		info("Viewing current customer's cars");
		
		System.out.println("\nCARS YOU OWN:");
		
		List<Car> yourCars = carDAO.readUserCars(customer.getUsername());
		
		for (Car c : yourCars) {
			Offer o = offerDAO.readOfferByUsernameAndVin(customer.getUsername(), c.getVin());
			
			System.out.printf(c.getColor() + " " + c.getMake() + " " + c.getModel()
				+ " " + c.getYear() + " [" + c.getVin() + "] - $%.2f\n", o.getAmount());
		}
		
		if (yourCars.size() == 0) {
			System.out.println("None, go buy a car :(");
		}
		
	}

	@Override
	public void viewOfferedCars() {
		info("Viewing cars that current customer offered on");
		
		while (true) {
			List<Offer> offeredCars = new LinkedList<>();
			
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
						+ " " + c.getYear() + " [" + c.getVin() + "] - $%.2f" + " (" + o.getStatus() + ")\n", o.getAmount());
				}
			}
			
			System.out.println("\nEnter 1 to update an offer");
			System.out.println("Enter 2 to remove an offer");
			System.out.println("Enter 3 to go back to the main menu");
			
			String menuOption = sc.nextLine();
			
			if (menuOption.equals("1")) {
				info("Updating an offer");
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
				info("Deleting an offer");
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
		info("Viewing remaining payments for current customer");
		
		while (true) {
			List<Offer> myAcceptedOffers = new LinkedList<>();
			
			System.out.println("\nPAYMENTS:");
			
			for (Offer o : offerDAO.readAllOffersByUsername(customer.getUsername())) {
				if (o.getStatus() == Offer.StatusType.ACCEPTED) {
					myAcceptedOffers.add(o);
				}
			}
			
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
				System.out.printf("Payment left: $%.2f\n", payCalc.calculatePriceLeft(mao.getAmount(), paymentAmounts));
				System.out.printf("Monthly Payment: $%.2f\n", payCalc.calculateMonthlyPayment(mao.getAmount(), 12));
				System.out.println();
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
		info("Making payment");
		
		while (true) {
			System.out.print("\nEnter the vin number that you'll pay for: ");
			String chosenVin = sc.nextLine();
			
			Offer offer = offerDAO.readOfferByUsernameAndVin(customer.getUsername(), chosenVin);
			
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
			
			double priceToPay = payCalc.calculateMonthlyPayment(offer.getAmount(), 12);
			
			if (payCalc.calculatePriceLeft(offer.getAmount(), paymentAmounts)
					< priceToPay) {
				priceToPay = payCalc.calculatePriceLeft(offer.getAmount(), paymentAmounts);
			}
			
			if (payCalc.calculatePriceLeft(offer.getAmount(), paymentAmounts) > 0.0) {
				int monthsPaid = paymentDAO.readPaymentsByUsernameAndVin(customer.getUsername(), offer.getOfferedCar()).size();
				
				Payment currentPayment = new Payment(priceToPay, monthsPaid + 1, offer.getOfferedCar(), customer.getUsername());
				paymentDAO.createPayment(currentPayment);
				
				System.out.println("Paid successfully!");
			}
			else {
				System.out.println("No more payments needed");
			}
	
			break;
		}
		
	}
	
}
