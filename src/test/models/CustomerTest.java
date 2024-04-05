package src.test.models;

import org.junit.jupiter.api.Test;

import src.main.com.fooddelivery.models.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest {

    @Test
    public void customerShouldBeInitializedWithProperties() {
        Customer customer = new Customer(1, "Paul McCartney", "Liverpool");
        assertTrue(customer instanceof Customer, "Customer should be an instance of Customer class");
    }

    @Test
    public void getIdShouldReturnCustomerId() {
        Customer customer = new Customer(42, "John Doe", "Nowhere");
        assertEquals(42, customer.getId(), "getId should return the customer id");
    }

    @Test
    public void setIdShouldSetCustomerId() {
        Customer customer = new Customer(42, "John Doe", "Nowhere");
        customer.setId(43);
        assertEquals(43, customer.getId(), "setId should set the customer id");
    }

    @Test
    public void getNameShouldReturnCustomerName() {
        Customer customer = new Customer(1, "Paul McCartney", "Liverpool");
        assertEquals("Paul McCartney", customer.getName(), "getName should return the name of the Customer");
    }

    @Test
    public void getAddressShouldReturnCustomerAddress() {
        Customer customer = new Customer(1, "Paul McCartney", "Liverpool");
        assertEquals("Liverpool", customer.getAddress(), "getAddress should return the address of the Customer");
    }
}