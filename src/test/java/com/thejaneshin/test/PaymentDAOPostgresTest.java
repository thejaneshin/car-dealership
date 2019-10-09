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

import com.thejaneshin.dao.PaymentDAOPostgres;
import com.thejaneshin.pojo.Payment;
import com.thejaneshin.util.ConnectionFactory;

@RunWith(MockitoJUnitRunner.class)
public class PaymentDAOPostgresTest {
	private PaymentDAOPostgres paymentDAO = new PaymentDAOPostgres();
	private Payment payment;
	private String sql;
	
	@Mock
	private Connection conn;
	
	@Spy
	PreparedStatement createPaymentStmt = ConnectionFactory.getConnection().prepareStatement("insert into payment (payment_amount, p_month, paid_car, payer) values"
			+ "(?, ?, ?, ?)");
	
	@Spy
	PreparedStatement readPaymentsByUsernameAndVinStmt = ConnectionFactory.getConnection().prepareStatement("select * from payment where payer = ? and paid_car = ?");
	
	@Spy
	PreparedStatement readAllPaymentsStmt = ConnectionFactory.getConnection().prepareStatement("select * from payment");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		payment = new Payment(8333.33, 1, "seruslhkjdf", "payer");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreatePayment() {
		sql = "insert into payment (payment_amount, p_month, paid_car, payer) values"
				+ "(?, ?, ?, ?)";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(createPaymentStmt);
			paymentDAO.setConn(conn);
			paymentDAO.createPayment(payment);
			Mockito.verify(createPaymentStmt).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	@Test
	public void testReadPaymentsByUsernameAndVin() {
		sql = "select * from payment where payer = ? and paid_car = ?";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readPaymentsByUsernameAndVinStmt);
			paymentDAO.setConn(conn);
			paymentDAO.readPaymentsByUsernameAndVin(payment.getPayer(), payment.getPaidCar());
			Mockito.verify(readPaymentsByUsernameAndVinStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testReadAllPayments() {
		sql = "select * from payment";
		
		try {
			when(conn.prepareStatement(sql)).thenReturn(readAllPaymentsStmt);
			paymentDAO.setConn(conn);
			paymentDAO.readAllPayments();
			Mockito.verify(readAllPaymentsStmt).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public PaymentDAOPostgresTest() throws SQLException {
		super();
	}
}
