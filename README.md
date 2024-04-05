# Food Delivery App Documentation

This document outlines the development process, features, and instructions for setting up and extending the Food Delivery application designed exclusively for a single restaurant's staff use.

## App Components

The Food Delivery App is structured around several key components:

- **Meals**: Representation of meals sold by the restaurant, including attributes like `id`, `name`, and `price`.
- **Customers**: Details about customers including `id`, `name`, and `address`.
- **Employees**: The staff of the restaurant, categorized into managers and delivery guys, each with `id`, `username`, and `password`.
- **Orders**: The orders made by customers, tying meals, customers, and employees together with a delivery status.

### User Actions

Users of the app can perform a variety of actions, including:

- As an employee, I can log in.
- As a manager, I can add a new meal.
- As a manager, I can list all the meals.
- As a manager, I can add a new customer.
- As a manager, I can list all the customers.
- As a manager, I can add a new order.
- As a manager, I can list all the undelivered orders.
- As a delivery guy, I can mark one of my orders as delivered.
- As a delivery guy, I can list all my undelivered orders.

## Development Guide

### Meals

1. **Model**: Implement a `Meal` model to represent meals.
2. **Repository**: Create a `MealRepository` for storage, initialized with a CSV file path.
3. **Controller**: Develop a `MealsController` to handle adding and listing meals.

### Customers

1. **Model**: Define a `Customer` model for customer details.
2. **Repository**: Set up a `CustomerRepository` for customer data management.
3. **Controller**: Implement a `CustomersController` for customer-related actions.

### Employees and Orders

1. **Employee Model**: Differentiate between managers and delivery guys in the `Employee` model.
2. **Order Model**: Represent orders with an `Order` model, linking meals, customers, and employees.
3. **Session Controller**: Manage user sessions and display relevant actions based on the employee's role.
4. **Order Controller**: Control order-related activities, including adding new orders and updating the delivery status.

### Extending the App

- TBD

## Contribution Guide

To contribute to the Food Delivery App, follow these steps:

1. **Git Workflow**: Make sure to `git add`, `commit`, and `push` your changes after each significant update or completion of app components.
2. **Testing**: Continuously test your code to ensure everything is functioning as expected.

This document should serve as a comprehensive guide for developing and maintaining the Food Delivery App. Let's build something great together!