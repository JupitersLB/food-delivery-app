package com.fooddelivery.daos;
import java.sql.Connection;


public class EmployeeDAO {
  private Connection connection;

  public EmployeeDAO(Connection connection) {
		this.connection = connection;
	}
}