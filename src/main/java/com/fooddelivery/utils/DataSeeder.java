package com.fooddelivery.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Seeds the database with initial or test data.
 */
public class DataSeeder {

    /**
	 * Resets test data in the database.
	 * @param connection The database connection to use.
	 * @param table The table to clear.
	 */
	public static void resetTable(Connection connection, String tableName) {
		try (Statement statement = connection.createStatement()) {
			statement.execute("DELETE FROM " + tableName);
			resetAutoIncrement(connection, tableName);
		} catch (SQLException e) {
			System.err.println("Failed to reset table " + tableName + ": " + e.getMessage());
		}
	}

	/**
	 * Seeds the meals table with predefined data.
	 * @param connection The database connection to use.
	 */
	public static void seedMealsData(Connection connection) {
		try (Statement statement = connection.createStatement()) {
			resetTable(connection, "meals");
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
			resetTable(connection, "customers");
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
			resetTable(connection, "employees");
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

	/**
	 * Resets the auto-increment counter for a specified table.
	 * 
	 * @param conn The database connection.
	 * @param tableName The name of the table for which to reset the auto-increment counter.
	 * @throws SQLException if a database access error occurs or the method is called on a closed connection.
	 */
	public static void resetAutoIncrement(Connection conn, String tableName) throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			stmt.execute("UPDATE sqlite_sequence SET seq = 0 WHERE name = '" + tableName + "'");
		}
	}
}
