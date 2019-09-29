package com.thejaneshin.pojo;

import java.io.Serializable;

public class Employee extends User implements Serializable {
	private static final long serialVersionUID = 2376562156349436628L;
	
	public Employee() {
		super();
	}

	public Employee(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
	}

}
