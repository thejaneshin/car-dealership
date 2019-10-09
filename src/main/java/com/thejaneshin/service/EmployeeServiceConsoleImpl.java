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

public class EmployeeServiceConsoleImpl implements EmployeeService {
	private static Scanner sc = new Scanner(System.in);
	private static CarDAO carDAO = new CarDAOPostgres();
	private static OfferDAO offerDAO = new OfferDAOPostgres();
	private static PaymentDAO paymentDAO = new PaymentDAOPostgres();
	private static PaymentCalculator payCalc = new PaymentCalcImpl();
	private static User employee;
	
	@Override
	public void run(User currentEmployee) {
		employee = currentEmployee;
		
		info("Employee " + employee.getUsername() + " logged in");
		
		System.out.println("\nWELCOME, " + employee.getFirstName() +
				" " + employee.getLastName() + "!");
		
		while (true) {
				System.out.println("\nEnter 1 to see the lot");
				System.out.println("Enter 2 to add a car to the lot");
				System.out.println("Enter 3 to remove a car from the lot");
				System.out.println("Enter 4 to look at all the offers");
				System.out.println("Enter 5 to view all payments");
				System.out.println("Enter q to logout");
				
				String menuOption = sc.nextLine();
				
				if (menuOption.equals("1")) {
					viewLotCars();
				}
				else if (menuOption.equals("2")) {
					addCarToLot();
				}
				else if (menuOption.equals("3")) {
					removeCarFromLot();
				}
				else if (menuOption.equals("4")) {
					viewAllPendingOffers();
				}
				else if (menuOption.equals("5")) {
					viewAllPayments();
				}
				else if (menuOption.equals("q")) {
					break;
				}
			}
		
	}

	@Override
	public void viewLotCars() {
		info("Viewing all cars in lot");
		List<Car> lotCars = carDAO.readAllLotCars();
		
		System.out.println("\nCARS ON THE LOT:");
		
		if (lotCars.size() == 0) {
			System.out.println("No cars at the moment");
		}
		else {
			for (Car c : lotCars) {
				System.out.println(c.getColor() + " " + c.getMake() + " " + c.getModel() + " " + c.getYear() + " [" + c.getVin() + "]");
			}
		}
	}

	@Override
	public void addCarToLot() {
		info("Adding a car to lot");
		
		while (true) {
			System.out.print("\nEnter the vin number: ");
			String vin = sc.nextLine();
			
			if (vin.equals("")) {
				System.out.println("Please enter a vin number");
				continue;
			}
			
			System.out.print("Enter the make: ");
			String make = sc.nextLine();
			
			if (make.equals("")) {
				System.out.println("Please enter the make");
				continue;
			}
			
			System.out.print("Enter the model: ");
			String model = sc.nextLine();
			
			if (model.equals("")) {
				System.out.println("Please enter a model");
				continue;
			}
			
			System.out.print("Enter the car year: ");
			int year = 0;
			
			try {
				year = sc.nextInt();
			} catch(InputMismatchException e) {
				sc.nextLine();
				warn("User entered invalid year");
				System.out.println("Please enter a valid year");
				continue;
			}
			
			sc.nextLine();
			
			System.out.print("Enter the car color: ");
			String color = sc.nextLine();
			
			if (color.equals("")) {
				System.out.println("Please enter a color");
				continue;
			}
			
			Car newCar = new Car(vin, make, model, year, color, Car.StatusType.IN_LOT, null);
			carDAO.createCar(newCar);
			
			System.out.println("\nCar successfully added to lot!");
			break;
		}
	}

	@Override
	public void removeCarFromLot() {
		info("Removing car from lot");
		
		while (true) {
			System.out.print("\nEnter the vin number to remove from lot: ");
			String vin = sc.nextLine();
			
			if (vin.equals("")) {
				System.out.println("Please enter a vin number");
				continue;
			}
			
			Car removedCar = carDAO.readCar(vin);
			
			if (removedCar == null) {
				System.out.println("Please enter a valid vin number from the lot");
				continue;
			}
			
			carDAO.deleteCar(removedCar);
			
			System.out.println("\nCar successfully removed from lot");
			break;
		}
		
	}

	@Override
	public void viewAllPendingOffers() {
		info("Viewing all pending offers");
		
		while (true) {
			System.out.println("\nOFFERS:");
			
			List<Offer> pendingOffers = offerDAO.readAllPendingOffers();
			
			if (pendingOffers.size() == 0) {
				System.out.println("No offers at the moment :(");
				break;
			}
			
			for (Offer o : pendingOffers) {
				Car c = carDAO.readCar(o.getOfferedCar());
				
				System.out.printf(o.getOfferer() + ": " + c.getColor() + " " + c.getMake() + " " + c.getModel()
					+ " " + c.getYear() + " [" + c.getVin() + "] - $%.2f\n", o.getAmount());
			}
			
			System.out.println("\nEnter 1 to ACCEPT offer");
			System.out.println("Enter 2 to REJECT offer");
			System.out.println("Enter 3 to go back to the main menu");
			
			String menuOption = sc.nextLine();
			
			if (menuOption.equals("1")) {
				acceptOffer();
				break;
			}
			else if (menuOption.equals("2")) {
				rejectOffer();
				break;
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

	private void acceptOffer() {
		info("Accepting an offer");
		
		while (true) {
			System.out.print("\nEnter the vin number for car you'll accept offer for: ");
			String chosenVin = sc.nextLine();
			
			System.out.print("Enter username of customer whose offer you'll accept: ");
			String customerUsername = sc.nextLine();
			
			Offer offer = offerDAO.readOfferByUsernameAndVin(customerUsername, chosenVin);
			
			if (offer == null) {
				System.out.println("\nInvalid vin number / username, please try again");
				continue;
			}
			
			offer.setStatus(Offer.StatusType.ACCEPTED);
			offerDAO.updateOfferStatus(offer);

			Car car = carDAO.readCar(offer.getOfferedCar());
			car.setStatus(Car.StatusType.SOLD);
			car.setOwner(offer.getOfferer());
			
			carDAO.updateCarStatus(car);
			carDAO.updateCarOwner(car);
			
			for (Offer o : offerDAO.readAllPendingOffers()) {
				if (o.getOfferedCar().equals(chosenVin)) {
					o.setStatus(Offer.StatusType.REJECTED);
					offerDAO.updateOfferStatus(o);
				}
			}
			
			System.out.println("\nOffer accepted!");
			break;
		}
	}
	
	private void rejectOffer() {
		info("Rejecting an offer");
		
		while (true) {
			System.out.print("\nEnter the vin number for car you'll reject offer for: ");
			String chosenVin = sc.nextLine();
			
			System.out.print("Enter username of customer whose offer you'll reject: ");
			String customerUsername = sc.nextLine();
			
			Offer offer = offerDAO.readOfferByUsernameAndVin(customerUsername, chosenVin);
			
			if (offer == null || offer.getStatus().equals(Offer.StatusType.ACCEPTED)) {
				System.out.println("\nInvalid vin number / username, please try again");
				continue;
			}
			
			offer.setStatus(Offer.StatusType.REJECTED);
			offerDAO.updateOfferStatus(offer);
			
			System.out.println("\nOffer rejected!");
			break;
		}
			
	}
	
	@Override
	public void viewAllPayments() {
		info("Viewing all payments");
		
		while (true) {
			System.out.println("\nPAYMENTS:");
			
			for (Offer o : offerDAO.readAllAcceptedOffers()) {
				Car c = carDAO.readCar(o.getOfferedCar());
				
				System.out.println(o.getOfferer() + ": " + c.getColor() + " " + c.getMake() + " " + c.getModel()
					+ " " + c.getYear() + " [" + c.getVin() + "]");
				
				List<Payment> payments = paymentDAO.readPaymentsByUsernameAndVin(o.getOfferer(), o.getOfferedCar());
				List<Double> paymentAmounts = new LinkedList<>();
				
				for (Payment p : payments) {
					paymentAmounts.add(p.getAmount());
				}
				
				System.out.printf("Paid so far: $%.2f\n", payCalc.calculatePaidSoFar(paymentAmounts));
				
				System.out.printf("Payment left: $%.2f\n", payCalc.calculatePriceLeft(o.getAmount(), paymentAmounts));
				System.out.println();
			}
			
			if (offerDAO.readAllAcceptedOffers().size() == 0) {
				System.out.println("No one bought cars yet :(");
			}
			
			break;
		}
	}
	
}
