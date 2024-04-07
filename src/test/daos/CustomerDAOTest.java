package test.daos;

import com.fooddelivery.models.Customer;
import com.fooddelivery.daos.CustomerDAO;
import com.fooddelivery.utils.DatabaseUtils;

import com.fooddelivery.daos.MealDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CustomerDAOTest extends AbstractDAOTest {
	private CustomerDAO customerDAO;

  @BeforeEach
  public void setUp() {
    try {
      super.setUp("customers");
      DatabaseUtils.seedCustomersData(connection);
		customerDAO = new CustomerDAO(connection);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error setting up tests");
    }
  }

	@Test
	void testAll() throws Exception {
		assertEquals(3, customerDAO.all().size());
	}

	@Test
	void testCreateAndSetId() throws Exception {
		Customer newCustomer = new Customer("Michael Jackson", "Gary");
		customerDAO.create(newCustomer);
		assertNotNull(newCustomer.getId());
		assertEquals(4, customerDAO.all().size());
	}

	@Test
	void testFind() throws Exception {
		Customer customer = customerDAO.find(3);
		assertEquals(3, customer.getId());
		assertEquals("John Entwistle", customer.getName());
	}
}
