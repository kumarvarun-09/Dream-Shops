# Dream-Shops

**Dream-Shops** is a full-featured e-commerce backend application built with **Spring Boot** and **PostgreSQL**.  
It provides a secure, scalable RESTful API for managing users, products, categories, carts, orders, and product images.  

---

## Features

- **User Management**
  - Create, update, and delete users
  - Role-based authorization (`ROLE_USER`, `ROLE_ADMIN`)
  - Secure password storage using **BCrypt**

- **Authentication & Security**
  - JWT-based authentication
  - Role-based access control for admin-only operations
  - Exception handling and secure endpoints

- **Product & Category Management**
  - Add, update, delete, and fetch products and categories
  - Search products by name, category, brand, or combinations
  - Count products by brand and name

- **Cart & Order Management**
  - Create new carts, add/remove items
  - Calculate total price of cart
  - Place orders and fetch user-specific orders

- **Image Management**
  - Upload multiple images per product
  - Download, update, and delete images stored as BLOBs in the database
  - Support for various image formats

- **Database**
  - PostgreSQL for relational data storage
  - Proper transaction management for critical operations

---

## Tech Stack

- **Backend:** Java, Spring Boot  
- **Security:** Spring Security, JWT  
- **Database:** PostgreSQL  
- **Other Libraries:** ModelMapper, Lombok  
- **Build Tool:** Maven  

---

## API Endpoints Overview

- **Auth**
  - `POST /api/v1/auth/login` - Login and receive JWT token

- **Users**
  - `POST /api/v1/users/createUser` - Create new user
  - `GET /api/v1/users/user/{id}` - Fetch user by ID
  - `PUT /api/v1/users/user/{id}` - Update user
  - `DELETE /api/v1/users/user/{id}` - Delete user

- **Categories**
  - `GET /api/v1/categories/all` - Get all categories
  - `POST /api/v1/categories/category/add` - Add a category
  - `GET /api/v1/categories/category/{id}` - Get category by ID
  - `GET /api/v1/categories/category/byName{categoryName}` - Get category by name
  - `PUT /api/v1/categories/category/{id}` - Update category
  - `DELETE /api/v1/categories/category/{id}` - Delete category

- **Products**
  - `GET /api/v1/products/all` - Get all products
  - `POST /api/v1/products/product/add` - Add product (Admin only)
  - `GET /api/v1/products/product/{id}` - Get product by ID
  - `PUT /api/v1/products/product/{id}` - Update product (Admin only)
  - `DELETE /api/v1/products/product/{id}` - Delete product (Admin only)
  - Various search endpoints by brand, category, and name

- **Cart**
  - `GET /api/v1/cart/{cartId}` - Get cart by ID
  - `DELETE /api/v1/cart/{cartId}` - Clear cart
  - `GET /api/v1/cart/{cartId}/getTotalAmount` - Get total price
  - `GET /api/v1/cart/newCart` - Create a new cart

- **Orders**
  - `POST /api/v1/orders/order?userId=` - Place order
  - `GET /api/v1/orders/order/{id}` - Get order by ID
  - `GET /api/v1/orders/user/{userId}/orders` - Get all orders of a user

- **Images**
  - `POST /api/v1/images/upload?productId=` - Upload multiple images
  - `GET /api/v1/images/image/download/{imageId}` - Download image
  - `PUT /api/v1/images/image/{imageId}/update` - Update image
  - `DELETE /api/v1/images/image/{imageId}/delete` - Delete image

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/kumarvarun-09/Dream-Shops.git
   cd Dream-Shops
