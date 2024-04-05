package com.fooddelivery;

import java.sql.Connection;
import java.util.List;

import com.fooddelivery.utils.DatabaseUtils;

public class App {
    public static void main(String[] args) {
        // Attempt to connect to the database
        Connection conn = DatabaseUtils.connect();
        if (conn != null) {
            System.out.println("Connected to the database.");

            // Automatically discover model classes and create corresponding tables
            List<String> modelClassNames = DatabaseUtils.getModelClassNames("src/main/java/com/fooddelivery/models");
            for (String tableName : modelClassNames) {
                DatabaseUtils.createTableForModel(tableName);
            }

            Router router = new Router();
            // Start the app
            router.run();
        }  
    }
}
