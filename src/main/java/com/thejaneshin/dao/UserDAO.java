package com.thejaneshin.dao;

import java.util.List;

import com.thejaneshin.pojo.User;

public interface UserDAO {
	public void createUser(User user);
	
	public User readUser(String username);
	
	public List<User> readAllUsers();

}
