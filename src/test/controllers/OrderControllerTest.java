package test.controllers;

import com.fooddelivery.controllers.OrderController;
import com.fooddelivery.daos.*;
import com.fooddelivery.models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class OrderControllerTest extends AbstractControllerTest<Order, OrderController> {

	@Mock
	private MealDAO mealDAO;
	@Mock
	private CustomerDAO customerDAO;
	@Mock
	private EmployeeDAO employeeDAO;
	@Mock
	private OrderDAO orderDAO;

	@BeforeEach
	@Override
	public void setUp() {
		super.setUp();
		controller = new OrderController(mealDAO, customerDAO, employeeDAO, orderDAO);
	}

	@Test
	void addShouldAskUserForMealCustomerEmployeeIndexesThenStoreNewOrder() {
		Meal capricciosa = new Meal(2, "Capricciosa", 11);
		Customer johnBonham = new Customer(2, "John Bonham", "Redditch");
		Employee ringo = new Employee(3, "ringo", "secret", "delivery_guy");

		when(mealDAO.findById(2)).thenReturn(capricciosa);
		when(customerDAO.findById(2)).thenReturn(johnBonham);
		when(employeeDAO.findById(3)).thenReturn(ringo);

		provideInput("2\n2\n3\n");

		controller.add();

		verify(orderDAO).create(captor.capture());
		Order capturedOrder = captor.getValue();
		assertEquals(capricciosa, capturedOrder.getMeal());
		assertEquals(johnBonham, capturedOrder.getCustomer());
		assertEquals(ringo, capturedOrder.getEmployee());
		assertFalse(capturedOrder.isDelivered());
	}

	@Test
	void listUndeliveredOrdersShouldDisplayCorrectInfo() {
		Order order1 = new Order(1, false, 1, 1, 2);
		Order order2 = new Order(2, false, 2, 2, 3);

		when(orderDAO.undeliveredOrders()).thenReturn(List.of(order1, order2));
		when(mealDAO.findById(1)).thenReturn(new Meal(1, "Margherita", 8));
		when(mealDAO.findById(2)).thenReturn(new Meal(2, "Capricciosa", 11));
		when(customerDAO.findById(1)).thenReturn(new Customer(1, "Paul McCartney", "Liverpool"));
		when(customerDAO.findById(2)).thenReturn(new Customer(2, "John Bonham", "Redditch"));
		when(employeeDAO.findById(2)).thenReturn(new Employee(2, "john", "secret", "delivery_guy"));
		when(employeeDAO.findById(3)).thenReturn(new Employee(3, "ringo", "secret", "delivery_guy"));

		controller.listUndeliveredOrders();

		String output = getOutput();
		assertTrue(output.contains("Margherita"));
		assertTrue(output.contains("Capricciosa"));
		assertTrue(output.contains("Paul McCartney"));
		assertTrue(output.contains("John Bonham"));
	}

	@Test
	void markAsDeliveredShouldUpdateOrderStatus() {
		Order orderToMark = new Order(4, false, 5, 2, 3);
		when(orderDAO.findById(4)).thenReturn(orderToMark);

		provideInput("4");

		controller.markAsDelivered(3);

		assertTrue(orderToMark.isDelivered());
		verify(orderDAO).update(orderToMark);
	}
}
