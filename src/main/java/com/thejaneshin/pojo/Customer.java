package com.thejaneshin.pojo;

import java.io.Serializable;

public class Customer extends User implements Serializable {
	private static final long serialVersionUID = 8550257168760673340L;
	
	public Customer() {
		super();
	}
	
	public Customer(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
	}

}
