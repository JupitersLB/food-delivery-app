package test.controllers;

import com.fooddelivery.controllers.MealController;
import com.fooddelivery.daos.MealDAO;
import com.fooddelivery.models.Meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MealControllerTest extends AbstractControllerTest<Meal, MealController> {

	@Mock
	private MealDAO mealDAO;

	@BeforeEach
	@Override
	public void setUp() {
		super.setUp();
		controller = new MealController(mealDAO);
	}

	@Test
	void addShouldAskUserForNameAndPriceThenStoreNewMeal() throws SQLException  {
		provideInput("Margherita\n800\n");

		controller.add();

		verify(mealDAO).create(captor.capture());
		Meal savedMeal = captor.getValue();

		assertEquals("Margherita", savedMeal.getName());
		assertEquals(800, savedMeal.getPrice());
	}

	@Test
	void listShouldDisplayMeals() throws SQLException {
			Meal meal1 = new Meal(1, "Margherita", 800);
			Meal meal2 = new Meal(2, "Pepperoni", 950);

			when(mealDAO.all()).thenReturn(Arrays.asList(meal1, meal2));

			controller.list();

			String output = getOutput();
			assertTrue(output.contains("Margherita"));
			assertTrue(output.contains("Pepperoni"));
			assertTrue(output.contains("800"));
			assertTrue(output.contains("950"));
	}
}
