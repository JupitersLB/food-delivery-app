package com.fooddelivery.models;

import com.fooddelivery.utils.Column;

public class Employee {
	@Column(name = "id", nullable = false, autoincrement = true, primaryKey = true)
	private int id;
}
