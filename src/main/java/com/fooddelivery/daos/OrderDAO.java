package com.fooddelivery.daos;

import java.sql.Connection;

public class OrderDAO {
  private Connection connection;

	public OrderDAO(Connection connection) {
		this.connection = connection;
	}
}
