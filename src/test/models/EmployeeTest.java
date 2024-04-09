package test.models;

import org.junit.jupiter.api.Test;
import com.fooddelivery.models.Employee;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

	@Test
	void employeeInitialization() {
		Employee employee = new Employee(1, "paul", "secret", "manager");
		assertAll("Employee properties",
			() -> assertEquals(1, employee.getId()),
			() -> assertEquals("paul", employee.getUsername()),
			() -> assertEquals("secret", employee.getPassword()),
			() -> assertEquals("manager", employee.getRole())
		);
	}

	@Test
	void setId() {
		Employee employee = new Employee("paul", "secret", "manager");
		employee.setId(43);
		assertEquals(43, employee.getId());
	}

	@Test
	void roleChecks() {
		Employee manager = new Employee("paul", "secret", "manager");
		assertTrue(manager.isManager());
		assertFalse(manager.isDeliveryGuy());

		Employee deliveryGuy = new Employee("ringo", "secret", "delivery_guy");
		assertTrue(deliveryGuy.isDeliveryGuy());
		assertFalse(deliveryGuy.isManager());
	}
}
