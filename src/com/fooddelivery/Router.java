package src.com.fooddelivery;
import java.util.Scanner;

public class Router {
    private boolean running;
    private Scanner scanner;

    public Router() {
        this.running = true;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to the Food Delivery App!");
        while (running) {
            loopPath();
        }
    }

    private void loopPath() {
      int choice = displayChoice();
      switch (choice) {
          case 1:
              System.out.println("\nMeals");
              break;
          case 2:
              System.out.println("\nCustomers");
              break;
          case 3:
              System.out.println("\nExiting...");
              running = false;
              break;
          default:
              System.out.println("\nInvalid option, please try again.");
              break;
      }
  }

    private int displayChoice() {
      System.out.println("\nWhat do you want to do next?");
      System.out.println("1 - Meals");
      System.out.println("2 - Customers");
      System.out.println("3 - Exit");
      System.out.print("> ");
      while (!scanner.hasNextInt()) {
          System.out.println("That's not a valid choice. Please enter a number.");
          scanner.next(); // Clear the invalid input
          System.out.print("> ");
      }
      return scanner.nextInt();
  }
}
