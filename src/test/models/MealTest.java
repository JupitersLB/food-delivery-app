package src.test.models;

import org.junit.jupiter.api.Test;

import src.main.com.fooddelivery.models.Meal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MealTest {

    @Test
    public void mealShouldBeInitializedWithProperties() {
        Meal meal = new Meal(1, "Margherita", 8);
        assertEquals(Meal.class, meal.getClass(), "Meal should be an instance of Meal class");
    }

    @Test
    public void getIdShouldReturnMealId() {
        Meal meal = new Meal(42, "Margherita", 8);
        assertEquals(42, meal.getId(), "getId should return the meal id");
    }

    @Test
    public void setIdShouldSetMealId() {
        Meal meal = new Meal(42, "Margherita", 8);
        meal.setId(43);
        assertEquals(43, meal.getId(), "setId should set the meal id");
    }

    @Test
    public void getNameShouldReturnMealName() {
        Meal meal = new Meal(1, "Margherita", 8);
        assertEquals("Margherita", meal.getName(), "getName should return the name of the meal");
    }

    @Test
    public void getPriceShouldReturnMealPrice() {
        Meal meal = new Meal(1, "Margherita", 8);
        assertEquals(8, meal.getPrice(), "getPrice should return the price of the meal");
    }
}
