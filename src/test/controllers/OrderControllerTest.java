package test.controllers;

import com.fooddelivery.controllers.OrderController;
import com.fooddelivery.daos.*;
import com.fooddelivery.models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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
	void addShouldAskUserForMealCustomerEmployeeIndexesThenStoreNewOrder() throws SQLException {
		Meal capricciosa = new Meal(2, "Capricciosa", 11);
		Customer johnBonham = new Customer(2, "John Bonham", "Redditch");
		Employee ringo = new Employee(3, "ringo", "secret", "delivery_guy");

		when(mealDAO.find(2)).thenReturn(capricciosa);
		when(customerDAO.find(2)).thenReturn(johnBonham);
		when(employeeDAO.find(3)).thenReturn(ringo);

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
	void listUndeliveredOrdersShouldDisplayCorrectInfo() throws SQLException {
    Meal mealOne = new Meal(1, "Margherita", 8);
    Meal mealTwo = new Meal(2, "Capricciosa", 11);
    Customer customerOne = new Customer(1, "Paul McCartney", "Liverpool");
    Customer customerTwo = new Customer(2, "John Bonham", "Redditch");
    Employee employeeOne = new Employee(2, "john", "secret", "delivery_guy");
    Employee employeeTwo = new Employee(3, "ringo", "secret", "delivery_guy");
		Order order1 = new Order(1, false, mealOne, customerOne, employeeOne);
		Order order2 = new Order(2, false, mealTwo, customerTwo, employeeTwo);

		when(orderDAO.undeliveredOrders()).thenReturn(List.of(order1, order2));
		when(mealDAO.find(1)).thenReturn(mealOne);
		when(mealDAO.find(2)).thenReturn(mealTwo);
		when(customerDAO.find(1)).thenReturn(customerOne);
		when(customerDAO.find(2)).thenReturn(customerTwo);
		when(employeeDAO.find(2)).thenReturn(employeeOne);
		when(employeeDAO.find(3)).thenReturn(employeeTwo);

		controller.listUndeliveredOrders();

		String output = getOutput();
    System.out.println(output);
		assertTrue(output.contains("Margherita"));
		assertTrue(output.contains("Capricciosa"));
		assertTrue(output.contains("Paul McCartney"));
		assertTrue(output.contains("John Bonham"));
	}

	@Test
	void markAsDeliveredShouldUpdateOrderStatus() throws SQLException {
    Meal meal = new Meal(1, "Margherita", 8);
    Customer customer = new Customer(1, "Paul McCartney", "Liverpool");
    Employee employee = new Employee(2, "john", "secret", "delivery_guy");
		Order orderToMark = new Order(4, false, meal, customer, employee);
		when(orderDAO.find(4)).thenReturn(orderToMark);

		provideInput("4\n");

		controller.markAsDelivered(employee.getId());

		assertTrue(orderToMark.isDelivered());
		verify(orderDAO).update(orderToMark);
	}
}
