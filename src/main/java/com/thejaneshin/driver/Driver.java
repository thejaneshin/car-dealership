package com.thejaneshin.driver;

import static com.thejaneshin.util.LoggerUtil.info;

import java.util.Scanner;

import com.thejaneshin.dao.UserDAO;
import com.thejaneshin.dao.UserDAOPostgres;
import com.thejaneshin.pojo.User;
import com.thejaneshin.service.CustomerService;
import com.thejaneshin.service.CustomerServiceConsoleImpl;
import com.thejaneshin.service.EmployeeService;
import com.thejaneshin.service.EmployeeServiceConsoleImpl;

public class Driver {
	public static Scanner sc = new Scanner(System.in);
	public static UserDAO userDAO = new UserDAOPostgres();
	
	public static void main(String[] args) {
		info("Car application started");
		
		while (true) {
			System.out.println("WELCOME!");
			
			String loginOrRegister = loginOrRegisterScreen();
			
			if (loginOrRegister.equals("r")) {
				registerScreen();
			}
			else {
				User currentUser = loginScreen();

				if (currentUser.getRole() == User.RoleType.CUSTOMER) {
					info(currentUser.getUsername() + " is a Customer");
					CustomerService cs = new CustomerServiceConsoleImpl();
					cs.run(currentUser);
				}
				else {
					info(currentUser.getUsername() + " is an Employee");
					EmployeeService es = new EmployeeServiceConsoleImpl();
					es.run(currentUser);
				}
				
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
		info("Register screen");
		
		while (true) {
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
			
			User user = userDAO.readUser(username);
			
			// If user exists
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
					userDAO.createUser(new User(username, password, firstName, lastName, User.RoleType.EMPLOYEE));
				}
				else {
					userDAO.createUser(new User(username, password, firstName, lastName, User.RoleType.CUSTOMER));
				}
				System.out.println("Account created successfully!");
			}
		}
	}
	
	public static User loginScreen() {
		User user = null;

		info("Login screen");
		
		while (true) {
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
			
			user = userDAO.readUser(username);
			
			// If user is null or password doesn't match
			if (user == null || !user.getPassword().equals(password)) {
				info("Incorrect username / password");
				System.out.println("Incorrect username / password");
				continue;
			}
			else {
				return user;
			}
			
		}
	}
	
}
