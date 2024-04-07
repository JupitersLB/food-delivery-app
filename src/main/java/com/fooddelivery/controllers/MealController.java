package com.fooddelivery.controllers;
import com.fooddelivery.daos.MealDAO;

public class MealController {
	private MealDAO dao;

	public MealController(MealDAO dao) {
		this.dao = dao;
	}
}
