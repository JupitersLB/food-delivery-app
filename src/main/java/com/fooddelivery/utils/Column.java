package com.fooddelivery.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	String name() default "";
	boolean nullable() default true;
	boolean autoincrement() default false;
	boolean primaryKey() default false;
}
