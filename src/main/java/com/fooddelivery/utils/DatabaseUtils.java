package com.fooddelivery.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
   * Prepares test data in the database.
   * Clears existing data and inserts predefined rows.
   * @param connection The database connection to use.
   */
  public static void prepareMealTestData(Connection connection) {
    try (Statement statement = connection.createStatement()) {
        // Clear existing data
        statement.execute("DELETE FROM meals");
        // Insert predefined test data
        statement.execute("INSERT INTO meals (name, price) VALUES ('Margherita', 8), ('Capricciosa', 11), ('Napolitana', 9), ('Funghi', 12), ('Calzone', 10)");
    } catch (SQLException e) {
        System.err.println("Error preparing test data: " + e.getMessage());
    }
  }
}
