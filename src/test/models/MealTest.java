package test.models;

import org.junit.jupiter.api.Test;
import com.fooddelivery.models.Meal;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

	@Test
	void mealInitialization() {
		Meal meal = new Meal(1, "Margherita", 8);
		assertAll("Meal properties",
			() -> assertEquals(1, meal.getId()),
			() -> assertEquals("Margherita", meal.getName()),
			() -> assertEquals(8, meal.getPrice())
		);
	}

	@Test
	void setId() {
		Meal meal = new Meal("Margherita", 8);
		meal.setId(43);
		assertEquals(43, meal.getId());
	}
}
