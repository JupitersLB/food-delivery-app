package com.fooddelivery;

import java.sql.Connection;

import com.fooddelivery.utils.DatabaseConnectionManager;
import com.fooddelivery.utils.ModelSchemaGenerator;

public class App {
	public static void main(String[] args) {
		// Attempt to connect to the database
		try {
			Connection conn = DatabaseConnectionManager.getConnection();
			if (conn != null) {
				System.out.println("Connected to the database.");
				ModelSchemaGenerator.generateSchemaForAllModels(conn);
				Router router = new Router();
				// Start the app
				router.run();
			}  
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
}
