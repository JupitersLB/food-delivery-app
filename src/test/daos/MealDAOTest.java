package test.daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.fooddelivery.models.Meal;
import com.fooddelivery.utils.DatabaseUtils;
import com.fooddelivery.daos.MealDAO;

public class MealDAOTest extends AbstractDAOTest {
	private MealDAO mealDAO;

	@BeforeEach
	@Override
	void setUp() throws Exception {
		super.setUp();
		mealDAO = new MealDAO(connection);
		DatabaseUtils.seedMealsData(connection);
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
