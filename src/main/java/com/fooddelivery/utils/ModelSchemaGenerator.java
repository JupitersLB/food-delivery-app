package com.fooddelivery.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for generating SQL table creation statements based on Java model classes.
 * It uses reflection to dynamically generate SQL statements that correspond to the structure of the model classes.
 */
public class ModelSchemaGenerator {
	private static final Map<Class<?>, String> typeMapping = new HashMap<>();

	static {
		typeMapping.put(int.class, "INTEGER");
		typeMapping.put(Integer.class, "INTEGER");
		typeMapping.put(String.class, "TEXT");
		// Additional type mappings can be added here.
	}

	/**
	 * Generates a SQL CREATE TABLE statement for a given model class.
	 * 
	 * @param modelClass The class representing the model for which to generate the SQL table creation statement.
	 * @return A SQL CREATE TABLE statement as a String.
	 */
	public static String createTableSQL(Class<?> modelClass) {
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		sql.append("\"").append(pluralize(modelClass.getSimpleName().toLowerCase())).append("\" (");

		// Reflectively access declared fields of the class to generate column definitions.
		Field[] fields = modelClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Column columnAnnotation = field.getAnnotation(Column.class);
			
			String columnName = (columnAnnotation != null && !columnAnnotation.name().isEmpty()) 
													? columnAnnotation.name() 
													: field.getName();
			String columnType = typeMapping.getOrDefault(field.getType(), "TEXT");
			String nullable = (columnAnnotation != null && !columnAnnotation.nullable()) 
												? " NOT NULL" 
												: "";

			sql.append(columnName).append(" ").append(columnType).append(nullable);

			if (i < fields.length - 1) {
				sql.append(", ");
			}
		}

		sql.append(");");
		return sql.toString();
	}

	/**
	 * Pluralizes a given class name based on common English pluralization rules.
	 * Converts the class name to lowercase before pluralizing.
	 *
	 * @param className The singular form of the class name to be pluralized.
	 * @return The pluralized form of the class name, converted to lowercase.
	 */
	private static String pluralize(String className) {
		String pluralizedName;
		if (className.endsWith("y")) {
			pluralizedName = className.substring(0, className.length() - 1) + "ies";
		} else if (className.endsWith("s") || className.endsWith("x") || className.endsWith("z") || 
								className.endsWith("ch") || className.endsWith("sh")) {
			pluralizedName = className + "es";
		} else {
			pluralizedName = className + "s";
		}
		return pluralizedName.toLowerCase();
	}
}