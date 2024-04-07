package com.fooddelivery.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fooddelivery.models.Meal;

public class MealDAO {
  private Connection connection;

	public MealDAO(Connection connection) {
		this.connection = connection;
	}
}
