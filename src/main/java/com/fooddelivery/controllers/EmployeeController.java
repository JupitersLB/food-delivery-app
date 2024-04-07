package com.fooddelivery.controllers;
import com.fooddelivery.daos.EmployeeDAO;

public class EmployeeController {
	private EmployeeDAO dao;

	public EmployeeController(EmployeeDAO dao) {
		this.dao = dao;
	}
}
