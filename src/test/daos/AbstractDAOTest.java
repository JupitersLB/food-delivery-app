package test.daos;

import com.fooddelivery.utils.DatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class AbstractDAOTest {
	protected Connection connection;

	@BeforeEach
	void setUp() throws Exception {
		// Adjust connectToTestDatabase to ensure it correctly points to your test DB
		DatabaseUtils.connectToDatabase();

		connection = DriverManager.getConnection("jdbc:sqlite:data/test.db");
		DatabaseUtils.setupAndSeedAllData(connection);
	}

	@AfterEach
	void tearDown() throws Exception {
		if (connection != null) {
		connection.close();
		}
	}
}
