package com.thejaneshin.driver;

import static com.thejaneshin.util.LoggerUtil.info;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.thejaneshin.dao.CarDAO;
import com.thejaneshin.dao.CarDAOPostgres;
import com.thejaneshin.dao.OfferDAO;
import com.thejaneshin.dao.OfferDAOPostgres;
import com.thejaneshin.dao.PaymentDAO;
import com.thejaneshin.dao.PaymentDAOPostgres;
import com.thejaneshin.dao.UserDAO;
import com.thejaneshin.dao.UserDAOPostgres;
import com.thejaneshin.pojo.Customer;
import com.thejaneshin.pojo.Employee;
import com.thejaneshin.pojo.Payment;
import com.thejaneshin.pojo.User;
import com.thejaneshin.service.CustomerService;
import com.thejaneshin.service.CustomerServiceConsoleImpl;
import com.thejaneshin.service.EmployeeService;
import com.thejaneshin.service.EmployeeServiceConsoleImpl;
import com.thejaneshin.util.ConnectionFactory;

public class Driver {
	public static Scanner sc = new Scanner(System.in);
	public static CarDAO carDAO = new CarDAOPostgres(); 
	public static UserDAO userDAO = new UserDAOPostgres();
	public static OfferDAO offerDAO = new OfferDAOPostgres();
	public static PaymentDAO paymentDAO = new PaymentDAOPostgres();
	
	public static void main(String[] args) {
		info("Car application started");
		
		//carDAO.createCar(new Car("234234", "Honda", "Accord", 2014, "Red", Car.StatusType.IN_LOT, null));
		
		//carDAO.updateCarStatus(new Car("234234", "Honda", "Accord", 2014, "Red", Car.StatusType.SOLD, null));
		
		//carDAO.updateCarOwner(new Car("234234", "Honda", "Accord", 2014, "Red", Car.StatusType.SOLD, "ned"));
		
		//carDAO.deleteCar(carDAO.readCar("234234"));
		
		//userDAO.createUser(new User("ned", "nedboi", "Ned", "White", User.RoleType.CUSTOMER));
		
		//System.out.println(userDAO.readUser("ned").getRole());
		
		//offerDAO.createOffer(new Offer(210.41, Offer.StatusType.PENDING, "234234", "ned"));
		
		//System.out.println(offerDAO.readOfferByUsernameAndVin("ned", "234234").getOfferedCar());
		
		//offerDAO.deleteOffer(new Offer(210.41, Offer.StatusType.PENDING, "234234", "ned"));
		
		paymentDAO.createPayment(new Payment(12000.00, 1, "234234", "ned"));
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			System.out.println("Ok");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		while (true) {
			System.out.println("WELCOME!");
			
			String loginOrRegister = loginOrRegisterScreen();
			
			if (loginOrRegister.equals("r")) {
				registerScreen();
			}
			else {
				User currentUser = loginScreen();
				/*
				if (currentUser instanceof Customer) {
					info(currentUser.getUsername() + " is a Customer");
					CustomerService cs = new CustomerServiceConsoleImpl();
					cs.run((Customer) currentUser);
				}
				else {
					info(currentUser.getUsername() + " is an Employee");
					EmployeeService es = new EmployeeServiceConsoleImpl();
					es.run((Employee) currentUser);
				}*/
				
			}
		}
		
	}
	
	public static String loginOrRegisterScreen() {
		String lOrR = "";
		
		info("Login and Register screen");
		
		do {
			System.out.println("\nEnter l to Login");
			System.out.println("Enter r to Register");
			lOrR = sc.nextLine().toLowerCase();
		} while(!lOrR.equals("l") && 
				!lOrR.equals("r"));
		
		return lOrR;
	}
	
	public static void registerScreen() {
		boolean registerCheck = true;
		User user;
		
		info("Register screen");
		
		do {
			System.out.println("\nREGISTER");
			
			System.out.println("\nEnter e if you're an Employee");
			System.out.println("Enter c if you're a Customer");
			
			String employeeOrCustomer = sc.nextLine().toLowerCase();
			
			if (employeeOrCustomer.equals("e")) {
				System.out.print("\nEnter the secret key: ");
				String secretKey = sc.nextLine();
				
				if (!secretKey.equals("employee")) {
					info("Incorrect employee key");
					System.out.println("Incorrect key");
					continue;
				}
			}
			
			System.out.print("\nUsername: ");
			String username = sc.nextLine();
			
			if (username.equals("")) {
				System.out.println("Please enter a username");
				continue;
			}
			
			user = customerDAO.readCustomer(username);
			
			// If user is not a customer
			if (user == null) {
				user = employeeDAO.readEmployee(username);
			}
			
			// If user is not null by now, then user does exist
			if (user != null) {
				info("User with username " + username + " already exists");
				System.out.println("User already exists");
				continue;
			}
			else {
				System.out.print("Password: ");
				String password = sc.nextLine();
				if (password.equals("")) {
					System.out.println("Please enter a password");
					continue;
				}
				
				System.out.print("First name: ");
				String firstName = sc.nextLine();
				if (firstName.equals("")) {
					System.out.println("Please enter your first name");
					continue;
				}
				
				System.out.print("Last name: ");
				String lastName = sc.nextLine();
				if (lastName.equals("")) {
					System.out.println("Please enter your last name");
					continue;
				}
				
				if (employeeOrCustomer.equals("e")) {
					employeeDAO.createEmployee(new Employee(username, password, firstName, lastName));
				}
				else {
					customerDAO.createCustomer(new Customer(username, password, firstName, lastName));
				}
			}
			// If reached the end, then registered successfully
			registerCheck = false;
		} while (registerCheck);
		
		System.out.println("\nRegistered successfully!");	
	}
	
	public static User loginScreen() {
		boolean loginCheck = true;
		User user = null;

		info("Login screen");
		
		do {
			System.out.println("\nLOGIN");
			System.out.print("\nUsername: ");
			String username = sc.nextLine();
			if (username.equals("")) {
				System.out.println("Please enter a username");
				continue;
			}
			
			System.out.print("Password: ");
			String password = sc.nextLine();
			if (password.equals("")) {
				System.out.println("Please enter a password");
				continue;
			}
			
			user = customerDAO.readCustomer(username);
			
			// If user is not a customer
			if (user == null) {
				user = employeeDAO.readEmployee(username);
			}
			
			// If user is not null by now, then user does exist
			if (user == null || !user.getPassword().equals(password)) {
				info("Incorrect username / password");
				System.out.println("Incorrect username / password");
				continue;
			}
			
			loginCheck = false;
		} while(loginCheck);

		return user;
	}
	
}
