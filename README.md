# Scala Play CRUD App

This is a basic Scala 3 web application built using the [Play Framework](https://www.playframework.com/).  
The purpose of this project is to implement simple CRUD functionality using in-memory data structures.

## Features

- ✅ Scala 3
- ✅ Play Framework
- ✅ CRUD operations (show all, show by id (GET), update (PUT), delete (DELETE), add (POST)) for Product
- ✅ In-memory storage for products

## CRUD App Structure
```bash
├── app
│   ├── controllers
│   │   └── ProductController.scala
│   ├── models
│   │   └── Product.scala
│   ├── repositories
│   │   └── ProductRepository.scala
```

## CRUD App Description
- **Product.scala**: Defines the Product case class, which includes fields like id, name, categoryId, description, stock, price, currency, and timestamps for createdAt and updatedAt. Includes JSON formatters for Play Framework.

- **ProductRepository.scala**: Provides in-memory storage for products, with methods to add, update, delete, and retrieve products by ID.

- **ProductController.scala**: Contains the routes for interacting with the Product entity, including endpoints to add, update, delete, and retrieve products. Includes validation for product data (e.g., checking if the price is valid, stock is non-negative).

## Requirements

- JDK 17+
- sbt

## How to Run

1. **Clone the repository:**

```bash
git clone https://github.com/AdrianDajakaj/scala-play-crud.git  
```

2. **Run the application with sbt:**

```bash
sbt run
```

3. **The application will be available at http://localhost:9000.**  

## API Endpoints
- GET /products: Retrieves all products.

- GET /products/:id: Retrieves a product by its ID.

- POST /products: Adds a new product (requires JSON payload).

- PUT /products/:id: Updates an existing product by its ID (requires JSON payload).

- DELETE /products/:id: Deletes a product by its ID.  

## Example of Adding a Product (POST)
**Request Body:**

```json
{
  "name": "Product Name",
  "categoryId": 1,
  "description": "Product Description",
  "stock": 10,
  "price": 19.99,
  "currency": "USD",
  "discount": null
}
```

```json
{
  "id": 1,
  "name": "Product Name",
  "categoryId": 1,
  "description": "Product Description",
  "stock": 10,
  "price": 19.99,
  "currency": "USD",
  "discount": null,
  "createdAt": "2025-04-22 14:30:00",
  "updatedAt": "2025-04-22 14:30:00"
}
```