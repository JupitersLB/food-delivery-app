package test.controllers;

import com.fooddelivery.controllers.CustomerController;
import com.fooddelivery.daos.CustomerDAO;
import com.fooddelivery.models.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerControllerTest extends AbstractControllerTest<Customer, CustomerController> {

	@Mock
	private CustomerDAO dao;

	@BeforeEach
	@Override
	public void setUp() {
		super.setUp();
		controller = new CustomerController(dao);

		// Mock initial data
		when(dao.all()).thenReturn(List.of(
			new Customer(1, "Paul McCartney", "Liverpool"),
			new Customer(2, "John Bonham", "Redditch"),
			new Customer(3, "John Entwistle", "Chiswick")
	));
	}

	@Test
	void shouldBeInitializedWithCustomerDAOInstance() {
		assertNotNull(controller, "Controller should be instantiated");
	}

	@Test
	void shouldAskForNameAndAddressThenStoreNewCustomer() {
		// Simulate user input for name and address
		provideInput("John\nJohn's Address\n");

		controller.add();

		verify(dao).create(captor.capture());
		Customer addedCustomer = captor.getValue();

		assertEquals("John", addedCustomer.getName());
		assertEquals("John's Address", addedCustomer.getAddress());
	}

	@Test
	void shouldGrabCustomersFromDAOAndDisplayThem() {
		controller.list();

		String output = getOutput();
		assertTrue(output.contains("Paul McCartney"));
		assertTrue(output.contains("John Bonham"));
		assertTrue(output.contains("John Entwistle"));
	}
}
