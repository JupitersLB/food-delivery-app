package src.test.models;

import org.junit.jupiter.api.Test;

import src.main.com.fooddelivery.models.Customer;
import src.main.com.fooddelivery.models.Employee;
import src.main.com.fooddelivery.models.Meal;
import src.main.com.fooddelivery.models.Order;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void orderShouldBeInitializedWithProperties() {
        Order order = new Order(1, false);
        assertTrue(order instanceof Order, "Order should be an instance of Order class");
    }

    @Test
    void getIdShouldReturnOrderId() {
        Order order = new Order(42, false);
        assertEquals(42, order.getId(), "getId should return the order id");
    }

    @Test
    void setIdShouldSetOrderId() {
        Order order = new Order(42, false);
        order.setId(43);
        assertEquals(43, order.getId(), "setId should set the order id");
    }

    @Test
    void isDeliveredShouldReturnDeliveryStatus() {
        Order order = new Order(1, true);
        assertTrue(order.isDelivered(), "isDelivered should return true if the order has been delivered");
        order = new Order(2, false);
        assertFalse(order.isDelivered(), "isDelivered should return false if the order has not yet been delivered");
    }

    @Test
    void deliverShouldMarkOrderAsDelivered() {
        Order order = new Order(12, false);
        assertFalse(order.isDelivered(), "Order should not be delivered initially");
        order.deliver();
        assertTrue(order.isDelivered(), "deliver should mark an order as delivered");
    }

    @Test
    void mealShouldReturnAssociatedMeal() {
        Meal meal = new Meal(1, "Pizza", 10.0);
        Order order = new Order(1, meal, null, null, false);
        assertEquals(meal, order.getMeal(), "getMeal should return the meal associated with the order");
    }

    @Test
    void employeeShouldReturnAssociatedEmployee() {
        Employee employee = new Employee(1, "JohnDoe", "password", "Chef");
        Order order = new Order(1, null, employee, null, false);
        assertEquals(employee, order.getEmployee(), "getEmployee should return the employee associated with the order");
    }

    @Test
    void customerShouldReturnAssociatedCustomer() {
        Customer customer = new Customer(1, "Jane Doe", "123 Main St");
        Order order = new Order(1, null, null, customer, false);
        assertEquals(customer, order.getCustomer(), "getCustomer should return the customer associated with the order");
    }
}

