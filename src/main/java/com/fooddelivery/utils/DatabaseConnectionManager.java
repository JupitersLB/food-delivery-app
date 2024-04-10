package com.fooddelivery.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages connections to the SQLite database.
 */
public class DatabaseConnectionManager {
    private static final String DATABASE_DIR = "data";
    private static final String DATABASE_NAME = System.getProperty("db.name", "database.db");
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_DIR + "/" + DATABASE_NAME;

    /**
     * Ensures the data directory exists and returns a connection to the SQLite database.
     *
     * @return a connection to the database.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        ensureDataDirectoryExists();
        return DriverManager.getConnection(CONNECTION_STRING);
    }

    /**
     * Ensures the directory for the SQLite database exists.
     */
    private static void ensureDataDirectoryExists() {
        File directory = new File(DATABASE_DIR);
        if (!directory.exists() && !directory.mkdir()) {
            throw new RuntimeException("Failed to create the data directory at " + DATABASE_DIR);
        }
    }
}
