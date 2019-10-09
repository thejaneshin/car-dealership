package com.thejaneshin.test;

import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.thejaneshin.dao.UserDAOPostgres;
import com.thejaneshin.pojo.User;
import com.thejaneshin.util.ConnectionFactory;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOPostgresTest {
	private UserDAOPostgres userDAO = new UserDAOPostgres();
	private User user;
	private String sql;

	@Mock
	private Connection conn;
	
	@Spy
	PreparedStatement createUserStmt = ConnectionFactory.getConnection().prepareStatement("insert into c_user (username, pword, firstname, lastname, u_role) values"
			+ "(?, ?, ?, ?, ?)");
	
	@Spy
	PreparedStatement readUserStmt = ConnectionFactory.getConnection().prepareStatement("select * from c_user where username = ?");
	
	@Spy
	PreparedStatement readAllUsersStmt = ConnectionFactory.getConnection().prepareStatement("select * from c_user");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		user = new User("lily", "ilikepie", "Lily", "Stevens", User.RoleType.CUSTOMER);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateUser() {
		sql = "insert into c_user (username, pword, firstname, lastname, u_role) values"
				+ "(?, ?, ?, ?, ?)";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(createUserStmt);
			userDAO.setConn(conn);
			userDAO.createUser(user);
			Mockito.verify(createUserStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testReadUser() {
		sql = "select * from c_user where username = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readUserStmt);
			userDAO.setConn(conn);
			userDAO.readUser(user.getUsername());
			Mockito.verify(readUserStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testReadAllUsers() {
		sql = "select * from c_user";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readAllUsersStmt);
			userDAO.setConn(conn);
			userDAO.readAllUsers();
			Mockito.verify(readAllUsersStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	
	public UserDAOPostgresTest() throws SQLException {
		super();
	}
}
