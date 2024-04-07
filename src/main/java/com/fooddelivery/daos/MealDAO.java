package com.fooddelivery.daos;

import java.sql.Connection;

public class MealDAO {
  private Connection connection;

	public MealDAO(Connection connection) {
		this.connection = connection;
	}
}
