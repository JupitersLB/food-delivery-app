package com.fooddelivery.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    private static final String DATABASE_DIR = "data";
    private static final String DATABASE_NAME = "database.db";
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
    public static void connectToDatabase() {
        try (Connection conn = connect()) {
            if (conn != null) {
                System.out.println("Connection to SQLite has been established.");
                // This is a good place to perform initial setup tasks, like creating tables.
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> getModelClassNames(String modelsDirectoryPath) {
        File modelsDir = new File(modelsDirectoryPath);
        List<String> classNames = new ArrayList<>();
        if (modelsDir.exists() && modelsDir.isDirectory()) {
            File[] files = modelsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".java")) { 
                        classNames.add(pluralize(file.getName().replace(".java", "").toLowerCase()));
                    }
                }
            }
        }
        return classNames;
    }

    private static String pluralize(String className) {
        if (className.endsWith("y")) {
            return className.substring(0, className.length() - 1) + "ies";
        } else if (className.endsWith("s") || className.endsWith("x") || className.endsWith("z") || 
            className.endsWith("ch") || className.endsWith("sh")) {
            return className + "es";
        } else {
            return className + "s";
        }
    }

    public static void createTableForModel(String tableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
                System.out.println("Table created for model: " + tableName);
        } catch (SQLException e) {
            System.out.println("Create table failed for model: " + tableName + " Error: " + e.getMessage());
        }
    }
}
