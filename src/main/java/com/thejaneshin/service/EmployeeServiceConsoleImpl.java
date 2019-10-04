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
import com.thejaneshin.pojo.Employee;
import com.thejaneshin.pojo.Offer;
import com.thejaneshin.pojo.Payment;
import com.thejaneshin.util.PaymentCalcImpl;
import com.thejaneshin.util.PaymentCalculator;
import static com.thejaneshin.util.LoggerUtil.*;

public class EmployeeServiceConsoleImpl implements EmployeeService {
	private static Scanner sc = new Scanner(System.in);
	private static CarDAO carDAO = new CarDAOSerialization();
	private static OfferDAO offerDAO = new OfferDAOSerialization();
	private static PaymentDAO paymentDAO = new PaymentDAOSerialization();
	private static PaymentCalculator payCalc = new PaymentCalcImpl();
	private static Employee employee;
	
	@Override
	public void run(Employee currentEmployee) {
		employee = currentEmployee;
		
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
					viewAllOffers();
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
		Set<Car> lotCars = new HashSet<>();
		
		for (Car c : carDAO.readAllCars()) {
			if (c.getStatus() == Car.StatusType.IN_LOT) {
				lotCars.add(c);
			}
		}
		
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
			
			Car newCar = new Car(vin, make, model, year, color, Car.StatusType.IN_LOT);
			carDAO.createCar(newCar);
			
			System.out.println("\nCar successfully added to lot!");
			break;
		}
	}

	@Override
	public void removeCarFromLot() {
		while (true) {
			System.out.print("\nEnter the vin number to remove from lot: ");
			String vin = sc.nextLine();
			
			if (vin.equals("")) {
				System.out.println("Please enter a vin number");
				continue;
			}
			
			Car removedCar = carDAO.readCar(vin);
			
			if (removedCar == null) {
				System.out.println("Please enter a valid vin number");
				continue;
			}
			
			carDAO.deleteCar(removedCar);
			
			
			for (Offer o : offerDAO.readAllOffers()) {
				if (o.getOfferedCar().equals(vin)) {
					offerDAO.deleteOffer(o);
				}
			}
			
			System.out.println("\nCar successfully removed from lot");
			break;
		}
		
	}

	@Override
	public void viewAllOffers() {
		while (true) {
			System.out.println("\nOFFERS:");
			
			List<Offer> pendingOffers = new LinkedList<>();
			for (Offer o : offerDAO.readAllOffers()) {
				if (o.getStatus() == Offer.StatusType.PENDING) {
					Car c = carDAO.readCar(o.getOfferedCar());
					
					System.out.printf(o.getOfferer() + ": " + c.getColor() + " " + c.getMake() + " " + c.getModel()
						+ " " + c.getYear() + " [" + c.getVin() + "] - $%.2f\n", o.getValue());
					pendingOffers.add(o);
				}
			}
			
			if (pendingOffers.size() == 0) {
				System.out.println("No offers at the moment :(");
				break;
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
			offerDAO.updateOffer(offer);

			Car car = carDAO.readCar(offer.getOfferedCar());
			car.setStatus(Car.StatusType.SOLD);
			carDAO.updateCar(car);
			
			for (Offer o : offerDAO.readAllOffers()) {
				if (o.getOfferedCar().equals(chosenVin) && !o.getOfferer().equals(customerUsername)) {
					o.setStatus(Offer.StatusType.REJECTED);
					offerDAO.updateOffer(o);
				}
			}
			
			System.out.println("\nOffer accepted!");
			break;
		}
	}
	
	private void rejectOffer() {
		while (true) {
			System.out.print("\nEnter the vin number for car you'll reject offer for: ");
			String chosenVin = sc.nextLine();
			
			System.out.println("Enter username of customer whose offer you'll reject: ");
			String customerUsername = sc.nextLine();
			
			Offer offer = offerDAO.readOfferByUsernameAndVin(customerUsername, chosenVin);
			
			if (offer == null || offer.getStatus().equals(Offer.StatusType.ACCEPTED)) {
				System.out.println("\nInvalid vin number / username, please try again");
				continue;
			}
			
			offer.setStatus(Offer.StatusType.REJECTED);
			offerDAO.updateOffer(offer);
			
			System.out.println("\nOffer rejected!");
			break;
		}
			
	}
	
	@Override
	public void viewAllPayments() {
		while (true) {
			System.out.println("\nPAYMENTS:");
			
			List<Offer> pendingOffers = new LinkedList<>();
			
			for (Offer o : offerDAO.readAllOffers()) {
				if (o.getStatus() == Offer.StatusType.ACCEPTED) {
					Car c = carDAO.readCar(o.getOfferedCar());
					
					pendingOffers.add(o);
					System.out.println(o.getOfferer() + ": " + c.getColor() + " " + c.getMake() + " " + c.getModel()
						+ " " + c.getYear() + " [" + c.getVin() + "]");
					
					List<Payment> payments = paymentDAO.readPaymentsByUsernameAndVin(o.getOfferer(), o.getOfferedCar());
					List<Double> paymentAmounts = new LinkedList<>();
					
					for (Payment p : payments) {
						paymentAmounts.add(p.getAmount());
					}
					
					System.out.printf("Paid so far: $%.2f\n", payCalc.calculatePaidSoFar(paymentAmounts));
					
					System.out.printf("Payment left: $%.2f\n", payCalc.calculatePriceLeft(o.getValue(), paymentAmounts));
					System.out.println();
				}
			}
			
			if (pendingOffers.size() == 0) {
				System.out.println("No one bought cars yet :(");
			}
			
			break;
		}
	}
	
}
