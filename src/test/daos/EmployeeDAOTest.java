package test.daos;

import com.fooddelivery.models.Employee;
import com.fooddelivery.daos.EmployeeDAO;
import com.fooddelivery.utils.DatabaseUtils;

import com.fooddelivery.daos.MealDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class EmployeeDAOTest extends AbstractDAOTest {
	private EmployeeDAO employeeDAO;

	@BeforeEach
  public void setUp() {
    try {
      super.setUp("employees");
      DatabaseUtils.seedEmployeesData(connection);
      employeeDAO = new EmployeeDAO(connection);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error setting up tests");
    }
  }

	@AfterEach
	void tearDown() throws Exception {
		if (connection != null) {
				connection.close();
		}
	}

	@Test
	void testAllEmployees() throws Exception {
		List<Employee> employees = employeeDAO.all();
		assertEquals(3, employees.size());
	}

	@Test
	void testAllDeliveryGuys() throws Exception {
		List<Employee> deliveryGuys = employeeDAO.allDeliveryGuys();
		assertEquals(2, deliveryGuys.size());
		assertTrue(deliveryGuys.stream().anyMatch(e -> "john".equals(e.getUsername())));
	}

	@Test
	void testFindById() throws Exception {
		Employee employee = employeeDAO.findById(1);
		assertNotNull(employee);
		assertEquals("paul", employee.getUsername());
	}

	@Test
	void testFindByUsername() throws Exception {
		Employee employee = employeeDAO.findByUsername("john");
		assertNotNull(employee);
		assertEquals(2, employee.getId());
	}
}
