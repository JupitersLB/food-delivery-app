package test.models;

import org.junit.jupiter.api.Test;
import com.fooddelivery.models.Customer;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

	@Test
	void customerInitialization() {
		Customer customer = new Customer(1, "Paul McCartney", "Liverpool");
		assertAll("Customer properties",
			() -> assertEquals(1, customer.getId()),
			() -> assertEquals("Paul McCartney", customer.getName()),
			() -> assertEquals("Liverpool", customer.getAddress())
		);
	}

	@Test
	void setId() {
		Customer customer = new Customer("John Doe", "Nowhere");
		customer.setId(43);
		assertEquals(43, customer.getId());
	}
}