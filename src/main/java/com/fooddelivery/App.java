package com.fooddelivery;

import java.sql.Connection;

import com.fooddelivery.utils.DatabaseUtils;

public class App {
	public static void main(String[] args) {
		// Attempt to connect to the database
		Connection conn = DatabaseUtils.connectToDatabase();
		if (conn != null) {
			System.out.println("Connected to the database.");
			Router router = new Router();
			// Start the app
			router.run();
		}  
	}
}
