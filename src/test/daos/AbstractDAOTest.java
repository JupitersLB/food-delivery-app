package test.daos;

import com.fooddelivery.utils.DatabaseConnectionManager;
import com.fooddelivery.utils.ModelSchemaGenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class AbstractDAOTest {
	protected Connection connection;

	@BeforeEach
	void setUp() throws Exception {
		DatabaseConnectionManager.getConnection();
		connection = DriverManager.getConnection("jdbc:sqlite:data/test.db");
		if (connection != null) {
			System.out.println("connection!");
			ModelSchemaGenerator.generateSchemaForAllModels(connection);
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		if (connection != null) {
			connection.close();
		}
	}
}
