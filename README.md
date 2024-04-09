# Food Delivery App Documentation

This document outlines the development process, features, and instructions for setting up and extending the Food Delivery application designed exclusively for a single restaurant's staff use.

## App Components

The Food Delivery App is structured around several key components:

- **Meals**: Representation of meals sold by the restaurant, including properties like `id`, `name`, and `price`.
- **Customers**: Details about customers including `id`, `name`, and `address`.
- **Employees**: The staff of the restaurant, categorized into managers and delivery guys, each with `id`, `username`, and `password`.
- **Orders**: The orders, tie meals, customers, and employees together with a delivery status.

## User Actions

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

### Dependencies

- [MAKE](https://www.gnu.org/software/make/)
- [SQLite](https://www.sqlite.org/)

### Meals

1. **Model**: Implement a `Meal` model to represent meals.
2. **DAO**: Create a `MealDAO` for data management.
3. **Controller**: Develop a `MealController` to handle adding and listing meals.
4. **View**: Implement a `MealView` to handle communication with the CLI.

### Customers

1. **Model**: Define a `Customer` model for customer details.
2. **DAO**: Set up a `CustomerDAO` for data management.
3. **Controller**: Implement a `CustomerController` for customer-related actions.
4. **View**: Implement a `CustomerView` to handle communication with the CLI.

### Employees

1. **Model**: Differentiate between managers and delivery guys in the `Employee` model.
2. **DAO**: Set up an `EmployeeDAO` for data management.

### Orders

1. **Order Model**: Represent orders with an `Order` model, linking meals, customers, and employees.
2. **DAO**: Set up an `OrderDAO` for data management.
3. **Controller**: Implement an `OrderController` for order-related actions.
4. **View**: Implement an `OrderView` to handle communication with the CLI.

### Session

1. **Session Controller**: Manage user sessions and display relevant actions based on the employee's role.
2. **View**: Implement an `SessionView` to handle communication with the CLI.

## Contribution Guide

To contribute to the Food Delivery App, follow these steps:

### Git Flow
Follow the basic in [GitHub Flow](https://docs.github.com/en/get-started/using-github/github-flow). To handle conflicts, I recommend rebasing rather than merging as it is much cleaner (although a bit riskier). [Learn more here.](https://www.atlassian.com/git/tutorials/merging-vs-rebasing).

### Testing

Testing is implemented using JUnit and some custom scripts to help with specific tests. There are unit tests, and eventually, there will be some E2E testing as well. There are a few ways to test. First to run all the tests...

1. `make run-tests` - This will run all the tests. Note, until everything is implemented, this will be full of compilation errors.
2. `make run-specific-test ENTITY=Customer COMPONENT=models` - This will compile and run only the tests associated to the Customer model.
3. `make run-specific-test ENTITY=Customer` - This will compile and run all tests related to a Customer.

### CI/CD
This will eventually become a very useful tool after the MVP is completed, however, the pipeline will fail until that point is reached so best to wait for then.
   
### Extending the App

The initial goal is to fulfill the requirements as setup via the TDD process as an MVP. After such completion, the next step is to refactor and improve on the existing structure perhaps moving to build tools etc. Finally, introducing and extending features.

This document should serve as a comprehensive guide for developing and maintaining the Food Delivery App. Let's build something great together!