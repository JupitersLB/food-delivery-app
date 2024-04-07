package com.fooddelivery.controllers;
import com.fooddelivery.daos.*;

// new OrderController(mealDAO, customerDAO, employeeDAO, orderDAO);

public class OrderController {
	private MealDAO mealDAO;
	private CustomerDAO customerDAO;
	private EmployeeDAO employeeDAO;
	private OrderDAO orderDAO;


	public OrderController(MealDAO mealDAO, CustomerDAO customerDAO, EmployeeDAO employeeDAO, OrderDAO orderDAO) {
		this.mealDAO = mealDAO;
		this.customerDAO = customerDAO;
		this.employeeDAO = employeeDAO;
		this.orderDAO = orderDAO;
	}
}
