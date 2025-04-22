# Scala Play CRUD App

This is a basic Scala 3 web application built using the [Play Framework](https://www.playframework.com/).  
The goal of this project is to demonstrate basic CRUD operations for products, categories, and shopping carts using in-memory data storage.

## Features

- ✅ Scala 3
- ✅ Play Framework
- ✅ CRUD operations (show all, show by id (GET), update (PUT), delete (DELETE), add (POST)) for: Product, Category and Cart.
- ✅ In-memory storage for all entities
- ✅ Automatic stock management when adding/removing items from a cart
- ✅ Basic data validation
- ✅ Runs via Docker and can be exposed publicly using ngrok

## CRUD App Structure
```bash
.
├── docker-compose.yml
├── Dockerfile
├── start.sh
├── app
│   ├── controllers
│   │   ├── ProductController.scala
│   │   ├── CategoryController.scala
│   │   └── CartController.scala
│   ├── models
│   │   ├── Product.scala
│   │   ├── Category.scala
│   │   ├── Cart.scala
│   │   └── CartItem.scala
│   ├── repositories
│   │   ├── ProductRepository.scala
│   │   ├── CategoryRepository.scala
│   │   └── CartRepository.scala
├── conf
│   └── routes
...
```

## CRUD App Description

**Product**
- Product.scala: Defines the Product model with fields like id, name, categoryId, description, stock, price, currency, optional discount, and timestamps (createdAt, updatedAt). Includes custom JSON serialization for Instant.

- ProductRepository.scala: In-memory storage and management of products, including automatic createdAt and updatedAt handling.

- ProductController.scala: Exposes REST endpoints for managing products. Validates business rules like price > 0, stock ≥ 0, and non-empty fields.

**Category**
- Category.scala: Represents product categories with an id, name, and description.

- CategoryRepository.scala: In-memory CRUD operations for categories.

- CategoryController.scala: Provides endpoints for category management.

**Cart**
- Cart.scala & CartItem.scala: Define the structure of shopping carts and their line items.

- CartRepository.scala: Handles adding, updating, and deleting carts. Automatically checks product availability and updates product stock accordingly.

- CartController.scala: API endpoints to manage carts.

ℹ️ Note:
When a product is added to or removed from a cart, the product's stock is automatically updated in real time. This simulates realistic inventory handling.

## Requirements

- JDK 17+
- Scala 3+
- sbt
- Docker
- ngrok CLI

## How to Run

1. **Clone the repository:**

```bash
git clone https://github.com/AdrianDajakaj/scala-play-crud.git  
cd scala-play-crud
```

2. **Install ngrok CLI (if not already installed)**

Follow instructions for your OS, then authenticate:

```bash
ngrok config add-authtoken <YOUR_AUTHTOKEN>
```
You can get your authtoken from https://dashboard.ngrok.com/get-started/setup

3. **Make the startup script executable:**  

```bash
chmod +x start.sh
```
4. **Run the app via Docker + ngrok:**

```bash
./start.sh
```

This will:

- Build and run the application in Docker on port 9000
- Start ngrok and expose your app to the internet

5. **Open the app in your browser:**
Ngrok will print a forwarding URL like:
```bash
Forwarding https://f903-194-183-60-188.ngrok-free.app -> http://localhost:9000
```
Just open that URL to use your app!


## API Endpoints
**Product**
- GET /products — Retrieve all products

- GET /products/:id — Retrieve product by ID

- POST /products — Add a new product

- PUT /products/:id — Update a product

- DELETE /products/:id — Delete a product

**Category**
- GET /categories — Retrieve all categories

- GET /categories/:id — Retrieve category by ID

- POST /categories — Add a new category

- PUT /categories/:id — Update a category

- DELETE /categories/:id — Delete a category

**Cart**
- GET /carts — Retrieve all carts

- GET /carts/:id — Retrieve cart by ID

- POST /carts — Create a new cart from a list of cart items

- PUT /carts/:id — Update an existing cart

- DELETE /carts/:id — Delete a cart and return its items to stock

## Example of adding a Category (POST)
**Request (POST /categories):**

```bash
curl -X POST http://localhost:9000/categories \
-H "Content-Type: application/json" \
-d '{
  "id": 0,
  "name": "Komputery",
  "description": "Sprzęt komputerowy"
}'

curl -X POST http://localhost:9000/categories \
-H "Content-Type: application/json" \
-d '{
  "id": 1,
  "name": "Książki",
  "description": "Książki papierowe"
}'

curl -X POST http://localhost:9000/categories \
-H "Content-Type: application/json" \
-d '{
  "id": 2,
  "name": "AGD",
  "description": "Sprzęt AGD"
}'
```

**Response**

```json
[
  {
    "id": 1,
    "name": "Komputery",
    "description": "Sprzęt komputerowy"
  },
  {
    "id": 2,
    "name": "Książki",
    "description": "Książki papierowe"
  },
  {
    "id": 3,
    "name": "AGD",
    "description": "Sprzęt AGD"
  }
]
```

## Example of updating a Category (PUT)
**Request (PUT /categories/:id):**

```bash
curl -X PUT http://localhost:9000/categories/1 \
-H "Content-Type: application/json" \
-d '{
  "id": 1,
  "name": "Komputery i RTV",
  "description": "Sprzęt komputerowy oraz RTV"
}'
```

**Response**

```json
[
  {
    "id": 2,
    "name": "Książki",
    "description": "Książki papierowe"
  },
  {
    "id": 3,
    "name": "AGD",
    "description": "Sprzęt AGD"
  },
  {
    "id": 1,
    "name": "Komputery i RTV",
    "description": "Sprzęt komputerowy oraz RTV"
  }
]
```

## Example of deleting a Category (DELETE)
**Request (DELETE /categories/:id):**

```bash
curl -X DELETE http://localhost:9000/categories/3
```
**Response**

```json
[
  {
    "id": 2,
    "name": "Książki",
    "description": "Książki papierowe"
  },
  {
    "id": 1,
    "name": "Komputery i RTV",
    "description": "Sprzęt komputerowy oraz RTV"
  }
]
```

## Example of adding a Product (POST)
**Request (POST /products):**

```bash
curl -X POST http://localhost:9000/products \
-H "Content-Type: application/json" \
-d '{
  "id": 0,
  "name": "Laptop Lenovo",
  "categoryId": 1,
  "description": "Nowoczesny laptop",
  "stock": 10,
  "price": 2999.99,
  "currency": "PLN",
  "discount": 10
}'

curl -X POST http://localhost:9000/products \
-H "Content-Type: application/json" \
-d '{
  "id": 1,
  "name": "Portret Doriana Graya",
  "categoryId": 2,
  "description": "Portret Doriana Graya - Oscar Wilde",
  "stock": 99,
  "price": 49.99,
  "currency": "PLN",
  "discount": 15
}'

curl -X POST http://localhost:9000/products \
-H "Content-Type: application/json" \
-d '{
  "id": 2,
  "name": "Lodówka Samsung",
  "categoryId": 3,
  "description": "Lodówka Samsung - gwarancja 10 lat",
  "stock": 20,
  "price": 2449.99,
  "currency": "PLN"
}'
```

**Response**

```json
[
  {
    "stock": 10,
    "categoryId": 1,
    "description": "Nowoczesny laptop",
    "price": 2999.99,
    "id": 1,
    "createdAt": "2025-04-22 18:51:58",
    "currency": "PLN",
    "discount": 10,
    "name": "Laptop Lenovo",
    "updatedAt": "2025-04-22 18:51:58"
  },
  {
    "stock": 99,
    "categoryId": 2,
    "description": "Portret Doriana Graya - Oscar Wilde",
    "price": 49.99,
    "id": 2,
    "createdAt": "2025-04-22 18:52:23",
    "currency": "PLN",
    "discount": 15,
    "name": "Portret Doriana Graya",
    "updatedAt": "2025-04-22 18:52:23"
  },
  {
    "stock": 20,
    "categoryId": 3,
    "description": "Lodówka Samsung - gwarancja 10 lat",
    "price": 2449.99,
    "id": 3,
    "createdAt": "2025-04-22 18:52:48",
    "currency": "PLN",
    "name": "Lodówka Samsung",
    "updatedAt": "2025-04-22 18:52:48"
  }
]
```

## Example of updating a Product (PUT)
**Request (PUT /products/:id):**

```bash
curl -X PUT http://localhost:9000/products/1 \
-H "Content-Type: application/json" \
-d '{
  "id": 1,
  "name": "Laptop Lenovo",
  "categoryId": 1,
  "description": "Zaktualizowany opis",
  "stock": 10,
  "price": 2999.99,
  "currency": "PLN",
  "discount": 30
}'
```

**Response**

```json
[
  {
    "stock": 99,
    "categoryId": 2,
    "description": "Portret Doriana Graya - Oscar Wilde",
    "price": 49.99,
    "id": 2,
    "createdAt": "2025-04-22 18:52:23",
    "currency": "PLN",
    "discount": 15,
    "name": "Portret Doriana Graya",
    "updatedAt": "2025-04-22 18:52:23"
  },
  {
    "stock": 20,
    "categoryId": 3,
    "description": "Lodówka Samsung - gwarancja 10 lat",
    "price": 2449.99,
    "id": 3,
    "createdAt": "2025-04-22 18:52:48",
    "currency": "PLN",
    "name": "Lodówka Samsung",
    "updatedAt": "2025-04-22 18:52:48"
  },
  {
    "stock": 10,
    "categoryId": 1,
    "description": "Zaktualizowany opis",
    "price": 2999.99,
    "id": 1,
    "createdAt": "2025-04-22 18:51:58",
    "currency": "PLN",
    "discount": 30,
    "name": "Laptop Lenovo",
    "updatedAt": "2025-04-22 18:53:09"
  }
]
```

## Example of deleting a Category (DELETE)
**Request (DELETE /products/:id):**

```bash
curl -X DELETE http://localhost:9000/products/3
```
**Response**

```json

[
  {
    "stock": 99,
    "categoryId": 2,
    "description": "Portret Doriana Graya - Oscar Wilde",
    "price": 49.99,
    "id": 2,
    "createdAt": "2025-04-22 18:52:23",
    "currency": "PLN",
    "discount": 15,
    "name": "Portret Doriana Graya",
    "updatedAt": "2025-04-22 18:52:23"
  },
  {
    "stock": 10,
    "categoryId": 1,
    "description": "Zaktualizowany opis",
    "price": 2999.99,
    "id": 1,
    "createdAt": "2025-04-22 18:51:58",
    "currency": "PLN",
    "discount": 30,
    "name": "Laptop Lenovo",
    "updatedAt": "2025-04-22 18:53:09"
  }
]
```


## Example of adding to Cart (POST)
**Request (POST /carts):**

```bash
curl -X POST http://localhost:9000/carts \
-H "Content-Type: application/json" \
-d '[
  {
    "productId": 1,
    "quantity": 2
  },
  {
    "productId": 2,
    "quantity": 3
  }
]'
```

**Response**

```json
[
  {
    "id": 1,
    "items": [
      {
        "productId": 1,
        "quantity": 2
      },
      {
        "productId": 2,
        "quantity": 3
      }
    ],
    "total": 4327.4605
  }
]
```

## Example of updating a Cart (PUT)
**Request (PUT /carts/:id):**

```bash
curl -X PUT http://localhost:9000/carts/1 -H "Content-Type: application/json" -d '[ 
  {
    "productId": 1,
    "quantity": 1
  },
  {
    "productId": 2,
    "quantity": 5
  }
]'
```

**Response**

```json
[
  {
    "id": 1,
    "items": [
      {
        "productId": 1,
        "quantity": 1
      },
      {
        "productId": 2,
        "quantity": 5
      }
    ],
    "total": 2312.4505
  }
]
```

## Example of deleting from Cart (DELETE)
**Request (DELETE /carts/:id):**

```bash
curl -X DELETE http://localhost:9000/carts/1
```
**Response**

```json
[]
```