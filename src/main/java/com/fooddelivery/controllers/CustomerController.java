package com.fooddelivery.controllers;

import com.fooddelivery.daos.CustomerDAO;

public class CustomerController {
	private CustomerDAO dao;

	public CustomerController(CustomerDAO dao) {
		this.dao = dao;
	}
}
