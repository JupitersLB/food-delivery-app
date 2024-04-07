package test.daos;

import com.fooddelivery.models.Customer;
import com.fooddelivery.utils.DatabaseUtils;
import com.fooddelivery.daos.CustomerDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest extends AbstractDAOTest {
	private CustomerDAO customerDAO;

  @BeforeEach
  @Override
  void setUp() throws Exception {
    super.setUp();
    customerDAO = new CustomerDAO(connection);
    DatabaseUtils.seedCustomersData(connection);
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
