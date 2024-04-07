package com.fooddelivery.daos;

import java.sql.Connection;

public class CustomerDAO {
  private Connection connection;

	public CustomerDAO(Connection connection) {
		this.connection = connection;
	}
}
