package com.fooddelivery.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
		typeMapping.put(double.class, "REAL");
		typeMapping.put(Double.class, "REAL");
		// Additional type mappings can be added here.
	}

	/**
     * Generates and executes SQL statements to create tables based on the loaded model classes.
     *
     * @param connection the database connection.
     * @throws SQLException if a database access error occurs or this method is called on a closed connection.
     */
    public static void generateSchemaForAllModels(Connection connection) throws SQLException {
        for (Class<?> modelClass : ModelLoader.loadModelClasses()) {
            String createTableSQL = createTableSQL(modelClass);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
            }
        }
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

		Field[] fields = modelClass.getDeclaredFields();
		boolean isFirst = true;
		for (Field field : fields) {
			Column columnAnnotation = field.getAnnotation(Column.class);

			if (!isFirst) {
				sql.append(", ");
			}
			isFirst = false;

			String columnName = columnAnnotation.name().isEmpty() ? field.getName() : columnAnnotation.name();
			String columnType = typeMapping.getOrDefault(field.getType(), "TEXT");
			boolean isPrimaryKey = columnAnnotation.primaryKey();
			boolean isAutoIncrement = columnAnnotation.autoincrement();
			boolean isNotNull = columnAnnotation.nullable() == false;

			sql.append(columnName).append(" ").append(columnType);

			if (isPrimaryKey) {
				sql.append(" PRIMARY KEY");
				if (isAutoIncrement) {
					sql.append(" AUTOINCREMENT");
				}
			}

			if (isNotNull && !isPrimaryKey) { // PRIMARY KEY columns are inherently NOT NULL in SQLite
				sql.append(" NOT NULL");
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
