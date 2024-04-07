package test.models;

import org.junit.jupiter.api.Test;
import com.fooddelivery.models.Customer;
import com.fooddelivery.models.Employee;
import com.fooddelivery.models.Meal;
import com.fooddelivery.models.Order;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

	@Test
	void orderInitialization() {
		Order order = new Order(1, new Meal(1, "Pizza", 10), new Employee(1, "JohnDoe", "password", "Chef"), new Customer(1, "Jane Doe", "123 Main St"), false);
		assertAll("Order properties",
			() -> assertEquals(1, order.getId()),
			() -> assertEquals("Pizza", order.getMeal().getName()),
			() -> assertEquals("JohnDoe", order.getEmployee().getUsername()),
			() -> assertEquals("Jane Doe", order.getCustomer().getName()),
			() -> assertFalse(order.isDelivered())
		);
	}

	@Test
	void deliverOrder() {
		Order order = new Order(12, false);
		assertFalse(order.isDelivered());
		order.deliver();
		assertTrue(order.isDelivered());
	}
}
