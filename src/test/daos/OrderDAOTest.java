package test.daos;

import com.fooddelivery.models.Customer;
import com.fooddelivery.models.Employee;
import com.fooddelivery.models.Meal;
import com.fooddelivery.models.Order;
import com.fooddelivery.utils.DatabaseUtils;
import com.fooddelivery.daos.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

public class OrderDAOTest extends AbstractDAOTest {
	private OrderDAO orderDAO;

	@BeforeEach
  @Override
  void setUp() throws Exception {
    super.setUp();
    orderDAO = new OrderDAO(connection, new MealDAO(connection), new CustomerDAO(connection), new EmployeeDAO(connection));
    DatabaseUtils.setupAndSeedAllData(connection);
  }

	@Test
	void testAll() throws Exception {
		List<Order> orders = orderDAO.all();
		assertNotNull(orders);
		assertTrue(orders.size() > 0); // Assuming there are orders seeded in the database
	}

	@Test
	void testCreateAndSetId() throws Exception {
    Meal meal = new Meal(2, "Capricciosa", 11);
		Customer customer = new Customer(2, "John Bonham", "Redditch");
		Employee employee = new Employee(3, "ringo", "secret", "delivery_guy");

		Order newOrder = new Order(1, false, meal, customer, employee);
		orderDAO.create(newOrder);
		assertNotNull(newOrder.getId());
		assertTrue(newOrder.getId() > 0); // ID should be set by the DAO upon creation

		Order foundOrder = orderDAO.find(newOrder.getId());
		assertNotNull(foundOrder);
		assertEquals(newOrder.getId(), foundOrder.getId());
	}

	@Test
	void testUndeliveredOrders() throws Exception {
		List<Order> undeliveredOrders = orderDAO.undeliveredOrders();
		assertNotNull(undeliveredOrders);
		for (Order order : undeliveredOrders) {
			assertFalse(order.isDelivered());
		}
	}
}
