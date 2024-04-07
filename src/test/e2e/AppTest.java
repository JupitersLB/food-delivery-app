package test.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fooddelivery.App;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.setUpOutput();
    }

    @AfterEach
    void tearDown() {
        super.restoreSystemInputOutput();
    }

    @Test
    void testInitialChoices() {
        // Simulate user input
        provideInput("3\n");
        
        App.main(new String[]{});
        
        String output = getOutput();

        assertTrue(output.contains("Welcome to the Food Delivery App!"), "App should welcome users.");
        assertTrue(output.contains("1 - Meals"), "Initial choices should include option 1 for Meals.");
        assertTrue(output.contains("2 - Customers"), "Initial choices should include option 2 for Customers.");
        assertTrue(output.contains("3 - Exit"), "Initial choices should include option 3 to Exit.");
    }
}
