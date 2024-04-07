package com.fooddelivery.daos;

import java.sql.Connection;

public class OrderDAO {
  private Connection connection;
  private MealDAO mealDAO;
  private CustomerDAO customerDAO;
  private EmployeeDAO employeeDAO;

  public OrderDAO(Connection connection, MealDAO mealDAO, CustomerDAO customerDAO, EmployeeDAO employeeDAO) {
    this.connection = connection;
    this.mealDAO = mealDAO;
    this.customerDAO = customerDAO;
    this.employeeDAO = employeeDAO;
  }  
}
