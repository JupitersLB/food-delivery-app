package com.fooddelivery.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads model classes for the application.
 */
public class ModelLoader {

	/**
	 * Loads model classes from a predefined list.
	 * @return A list of Class objects representing the models.
	 */
	public static List<Class<?>> loadModelClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/model_classes.txt"))) {
			String className;
			while ((className = reader.readLine()) != null) {
				try {
					Class<?> clazz = Class.forName(className);
					classes.add(clazz);
				} catch (ClassNotFoundException e) {
					System.err.println("Class not found: " + className);
				}
			}
		} catch (IOException e) {
			System.err.println("Could not read model class list: " + e.getMessage());
		}
		return classes;
	}
}
