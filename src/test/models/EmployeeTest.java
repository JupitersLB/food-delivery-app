package src.test.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import src.main.com.fooddelivery.models.Employee;


public class EmployeeTest {

    @Test
    public void employeeShouldBeInitializedWithProperties() {
        Employee employee = new Employee(1, "paul", "secret", "manager");
        assertTrue(employee instanceof Employee, "Employee should be an instance of Employee class");
    }

    @Test
    public void getIdShouldReturnEmployeeId() {
        Employee employee = new Employee(42, "paul", "secret", "manager");
        assertEquals(42, employee.getId(), "getId should return the employee id");
    }

    @Test
    public void setIdShouldSetEmployeeId() {
        Employee employee = new Employee(42, "paul", "secret", "manager");
        employee.setId(43);
        assertEquals(43, employee.getId(), "setId should set the employee id");
    }

    @Test
    public void getUsernameShouldReturnUsername() {
        Employee employee = new Employee(1, "paul", "secret", "manager");
        assertEquals("paul", employee.getUsername(), "getUsername should return the username of the Employee");
    }

    @Test
    public void getPasswordShouldReturnPassword() {
        Employee employee = new Employee(1, "paul", "secret", "manager");
        assertEquals("secret", employee.getPassword(), "getPassword should return the password of the Employee");
    }

    @Test
    public void getRoleShouldReturnRole() {
        Employee employee = new Employee(1, "paul", "secret", "delivery_guy");
        assertEquals("delivery_guy", employee.getRole(), "getRole should return the role of the Employee");
    }

    @Test
    public void isManagerShouldReturnTrueForManager() {
        Employee employee = new Employee(1, "paul", "secret", "manager");
        assertTrue(employee.isManager(), "isManager should return true if the employee is a manager");
    }

    @Test
    public void isManagerShouldReturnFalseForDeliveryGuy() {
        Employee employee = new Employee(1, "paul", "secret", "delivery_guy");
        assertFalse(employee.isManager(), "isManager should return false if the employee is a delivery guy");
    }

    @Test
    public void isDeliveryGuyShouldReturnTrueForDeliveryGuy() {
        Employee employee = new Employee(1, "paul", "secret", "delivery_guy");
        assertTrue(employee.isDeliveryGuy(), "isDeliveryGuy should return true if the employee is a delivery guy");
    }

    @Test
    public void isDeliveryGuyShouldReturnFalseForManager() {
        Employee employee = new Employee(1, "paul", "secret", "manager");
        assertFalse(employee.isDeliveryGuy(), "isDeliveryGuy should return false if the employee is a manager");
    }
}
