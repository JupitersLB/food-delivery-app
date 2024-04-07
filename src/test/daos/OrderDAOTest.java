package test.daos;

import com.fooddelivery.models.Order;

import com.fooddelivery.daos.CustomerDAO;
import com.fooddelivery.utils.DatabaseUtils;

import com.fooddelivery.daos.OrderDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class OrderDAOTest extends AbstractDAOTest {
	private OrderDAO orderDAO;

	@BeforeEach
  @Override
  void setUp() throws Exception {
    super.setUp();
    orderDAO = new OrderDAO(connection);
  }

	@Test
	void testAll() throws Exception {
		List<Order> orders = orderDAO.all();
		assertNotNull(orders);
		assertTrue(orders.size() > 0); // Assuming there are orders seeded in the database
	}

	@Test
	void testCreateAndSetId() throws Exception {
		Order newOrder = new Order(1, false, 1, 1, 2); // Assuming these IDs exist in meal, customer, and employee tables
		orderDAO.create(newOrder);
		assertNotNull(newOrder.getId());
		assertTrue(newOrder.getId() > 0); // ID should be set by the DAO upon creation

		Order foundOrder = orderDAO.findById(newOrder.getId());
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
