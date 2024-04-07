package test.daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import com.fooddelivery.utils.DatabaseUtils;

import com.fooddelivery.daos.MealDAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class MealDAOTest {
	private MealDAO mealDAO;
	private Connection connection;


	@BeforeEach
	void setUp() throws Exception {
		// Use DatabaseUtils to establish a connection for testing
		DatabaseUtils.connectToDatabase();

    connection = DriverManager.getConnection("jdbc:sqlite:data/test.db");
		DatabaseUtils.prepareMealTestData(connection);
		mealDAO = new MealDAO(connection);
	}

	@AfterEach
	void tearDown() throws Exception {
		// Close connection
		if (connection != null) {
				connection.close();
		}
	}

	@Test
	void testCreateAndFind() throws Exception {
		Meal hawaiiMeal = new Meal("Hawaii", 11);
		mealDAO.create(hawaiiMeal);
		assertNotNull(hawaiiMeal.getId());
		
		Meal foundMeal = mealDAO.find(hawaiiMeal.getId());
		assertEquals("Hawaii", foundMeal.getName());
		assertEquals(11, foundMeal.getPrice());
	}

	@Test
	void testAll() throws Exception {
		assertEquals(5, mealDAO.all().size());
		
		Meal rucolaMeal = new Meal("Rucola", 12);
		mealDAO.create(rucolaMeal);
		
		assertEquals(6, mealDAO.all().size());
	}
}
