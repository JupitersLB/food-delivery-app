package com.fooddelivery.models;

import com.fooddelivery.utils.Column;

public class Meal {
	@Column(name = "id", nullable = false, autoincrement = true, primaryKey = true)
	private int id;
}
