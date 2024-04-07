package com.fooddelivery.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for managing database connections and operations in the SQLite database.
 */
public class DatabaseUtils {
	private static final String DATABASE_DIR = "data";
  private static final String DATABASE_NAME = System.getProperty("db.name", "database.db"); // Default to "database.db"
	private static final String URL = "jdbc:sqlite:" + DATABASE_DIR + "/" + DATABASE_NAME;

	/**
	 * Connects to the SQLite database.
	 * @return a Connection object to the database.
	 */
	public static Connection connect() {
		ensureDataDirectoryExists(); // Ensure the directory exists
		try {
			return DriverManager.getConnection(URL);
		} catch (SQLException e) {
			System.out.println("Connection failed: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Ensures the data directory exists.
	 */
	private static void ensureDataDirectoryExists() {
		File directory = new File(DATABASE_DIR);
		if (!directory.exists()){
			directory.mkdir();
		}
	}

	/**
	 * Demonstrates establishing a connection to the database.
	 * Primarily used for testing the connection.
	 */
	public static Connection connectToDatabase() {
		try (Connection conn = connect()) {
			if (conn != null) {
				System.out.println("Connection to SQLite has been established.");
				generateSchemaForAllModels();
				return conn;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Loads model classes from a predefined list.
	 * @return A list of Class objects representing the models.
	 */
	public static List<Class<?>> loadModelClasses() {
		List<Class<?>> classes = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/model_classes.txt"))) {
			String className;
			while ((className = reader.readLine()) != null) {
					try {
							Class<?> clazz = Class.forName(className);
							classes.add(clazz);
					} catch (ClassNotFoundException e) {
							System.err.println("Class not found: " + className);
					}
			}
		} catch (IOException e) {
			System.err.println("Could not read model class list: " + e.getMessage());
		}
		return classes;
	}

	/**
	 * Generates the database schema for all loaded models.
	 */
	public static void generateSchemaForAllModels() {
		List<Class<?>> modelClasses = loadModelClasses();
		modelClasses.forEach(modelClass -> {
			// Assuming createTableSQL is a method in ModelSchemaGenerator that generates SQL for creating a table
			String sql = ModelSchemaGenerator.createTableSQL(modelClass);
			try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
				stmt.execute(sql);
				System.out.println("Table created for model: " + modelClass.getSimpleName());
			} catch (SQLException e) {
				System.err.println("Failed to create table for model: " + modelClass.getSimpleName() + ". Error: " + e.getMessage());
			}
		});
  }

  /**
   * Resets test data in the database.
   * @param connection The database connection to use.
   * @param table The table to clear.
   */
  public static void resetTable(Connection connection, String tableName) {
    try (Statement statement = connection.createStatement()) {
      // Clear existing data
      statement.execute("DELETE FROM " + tableName);
    } catch (SQLException e) {
      System.err.println("Failed to reset table " + tableName + ": " + e.getMessage());
    }
  }

  /**
   * Seeds the customers table with predefined data.
   * @param connection The database connection to use.
   */
  public static void seedMealsData(Connection connection) {
    try (Statement statement = connection.createStatement()) {
        // Insert predefined test data
        statement.execute("INSERT INTO meals (name, price) VALUES ('Margherita', 8), ('Capricciosa', 11), ('Napolitana', 9), ('Funghi', 12), ('Calzone', 10)");
    } catch (SQLException e) {
        System.err.println("Error preparing test data: " + e.getMessage());
    }
  }

  /**
   * Seeds the customers table with predefined data.
   * @param connection The database connection to use.
   */
  public static void seedCustomersData(Connection connection) {
    try (Statement statement = connection.createStatement()) {
        // Insert predefined test data
        statement.execute("INSERT INTO customers (name, address) VALUES ('Paul McCartney', 'Liverpool'), ('John Bonham', 'Redditch'), ('John Entwistle', 'Chiswick')");
    } catch (SQLException e) {
        System.err.println("Error preparing test data: " + e.getMessage());
    }
  }

  /**
   * Seeds the employees table with predefined data.
   * @param connection The connection to the database.
   */
  public static void seedEmployeesData(Connection connection) {
    try (Statement statement = connection.createStatement()) {
        // Insert predefined test data
        statement.execute("INSERT INTO employees (username, password, role) VALUES ('paul', 'secret', 'manager'), ('john', 'secret', 'delivery_guy'), ('ringo', 'secret', 'delivery_guy');");
    } catch (SQLException e) {
        System.err.println("Error seeding employees table: " + e.getMessage());
    }
  }

  /**
   * Seeds the orders table with test data.
   * @param connection A Connection object to the test database.
   */
  public static void seedOrderData(Connection connection) {
    // SQL statement for inserting sample order data
    String insertOrderSQL = "INSERT INTO orders (id, delivered, meal_id, customer_id, employee_id) VALUES (?, ?, ?, ?, ?);";

    // Sample order data
    Object[][] sampleOrders = {
      {1, false, 1, 1, 2}, // order ID, delivery status, meal ID, customer ID, employee ID
      {2, true, 2, 2, 1},
      {3, false, 3, 3, 2},
    };

    try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrderSQL)) {
      for (Object[] order : sampleOrders) {
        // Set parameters for the insert statement from the sample order data
        preparedStatement.setInt(1, (Integer) order[0]); // order ID
        preparedStatement.setBoolean(2, (Boolean) order[1]); // delivery status
        preparedStatement.setInt(3, (Integer) order[2]); // meal ID
        preparedStatement.setInt(4, (Integer) order[3]); // customer ID
        preparedStatement.setInt(5, (Integer) order[4]); // employee ID
        
        preparedStatement.executeUpdate();
      }
      System.out.println("Order data seeded successfully.");
    } catch (SQLException e) {
      System.err.println("Failed to seed order data: " + e.getMessage());
    }
  }

  /**
   * Seeds the orders table with predefined data after resetting all tables.
   * Assumes other seeding methods are defined and called prior to this method.
   * @param connection A Connection object to the database.
   */
  public static void setupAndSeedAllData(Connection connection) {
    // Reset all tables
    resetTable(connection, "meals");
    resetTable(connection, "customers");
    resetTable(connection, "employees");
    resetTable(connection, "orders");

    // Call your seeding methods for each entity
    seedMealsData(connection);
    seedCustomersData(connection);
    seedEmployeesData(connection);
    
    // Now seed the orders table with dependencies fulfilled
    seedOrderData(connection);
  }
}
