package test.daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import com.fooddelivery.models.Meal;

import com.fooddelivery.utils.DatabaseUtils;

import com.fooddelivery.daos.MealDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

class MealDAOTest {
	private MealDAO mealDAO;
	private Connection connection;
	private Connection connection2;


	@BeforeEach
	void setUp() throws Exception {
		// Use DatabaseUtils to establish a connection for testing
		connection = DatabaseUtils.connectToDatabase();

		String url = "jdbc:sqlite:data/test.db"; // Adjust based on your test DB path
    connection2 = DriverManager.getConnection(url);
		System.out.println("con: " + connection2);

		// Ensure DatabaseUtils points to the test database
		mealDAO = new MealDAO(connection2);

		try (Statement statement = connection2.createStatement()) {
				statement.execute("DELETE FROM meals");
				statement.execute("INSERT INTO meals (name, price) VALUES ('Margherita', 8), ('Capricciosa', 11), ('Napolitana', 9), ('Funghi', 12), ('Calzone', 10)");
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		// Close connection
		if (connection != null) {
				connection2.close();
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
