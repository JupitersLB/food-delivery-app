package com.fooddelivery.models;

import com.fooddelivery.utils.Column;

public class Customer {
	@Column(name = "id", nullable = false)
	private int id;

	/**
	 * Constructs a new Customer instance.
	 *
	 * @param id The unique identifier of the meal.
	 */
	public Customer(int id) {
		this.id = id;
	}
}
