
# Inventory Management System

A simple Spring Boot application to manage an inventory system for a store. This system allows users to:

- View a list of available products.
- Add new products.
- Update existing products.

Product details include name, description, price, and quantity. The application uses Hibernate for persistence and a MySQL database for storage. Docker Compose is used for containerized deployment.

---

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- Add new products to the inventory.
- Retrieve a list of all products.
- Update existing product information.
- Exception handling for invalid requests.
- Validations for product input data.

---

## Technologies Used

- **Spring Boot** (Web, Data JPA, Validation)
- **MySQL** (Database)
- **Hibernate** (ORM)
- **Docker Compose** (Containerized deployment)
- **JUnit 5** (Unit Testing)
- **Mockito** (Mocking dependencies)

---

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Docker & Docker Compose
- Maven
- MySQL (optional, if not using Docker)

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/inventory-system.git
   cd inventory-system
   ```

2. **Build the Project**
   ```bash
   mvn clean install
   ```

3. **Run with Docker Compose**
   Ensure Docker is running, then start the application with:
   ```bash
   docker-compose up
   ```

4. **Access the Application**
    - API Documentation: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## API Endpoints

### Base URL
`http://localhost:8080/api/products`

| HTTP Method | Endpoint             | Description                |
|-------------|----------------------|----------------------------|
| `GET`       | `/`                  | Fetch all products         |
| `POST`      | `/`                  | Add a new product          |
| `PUT`       | `/{id}`              | Update an existing product |
| `GET`       | `/{id}`              | Get a specific product     |

### Example Request for Adding a Product
```json
POST /api/products
Content-Type: application/json

{
  "name": "Product A",
  "description": "Description of Product A",
  "price": 100.0,
  "quantity": 10
}
```

---

## Project Structure

```
src/main/java/com/example/inventorysystem
├── controller
│   └── ProductController.java       // REST endpoints
├── dto
│   ├── ProductRequestDto.java       // DTO for incoming requests
│   └── ProductResponseDto.java      // DTO for outgoing responses
├── exception
│   ├── ProductNotFoundException.java // Custom exception
│   └── GlobalExceptionHandler.java   // Centralized exception handling
├── model
│   └── Product.java                 // Product entity
├── repository
│   └── ProductRepository.java       // JPA repository interface
├── service
│   ├── ProductService.java          // Service interface
│   └── ProductServiceImpl.java      // Service implementation
└── InventorysystemApplication.java  // Main application
```

---

## Testing

- Unit tests are written for the repository, service, and controller layers.
- Run tests using:
  ```bash
  mvn test
  ```

### Test Coverage

- **Repository Layer**: Verifies database operations.
- **Service Layer**: Ensures business logic correctness.
- **Controller Layer**: Tests API endpoints using `MockMvc`.

---

## Docker Compose Configuration

The `docker-compose.yml` file sets up:

- **MySQL Database**
- **Spring Boot Application**

```yaml
version: '3.8'

services:
  db:
    image: mysql:8.0
    container_name: mysql_container
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: inventorydb
    ports:
      - "3306:3306"

  app:
    build:
      context: .
    container_name: inventory_app
    ports:
      - "8080:8080"
    depends_on:
      - db
```

---

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature-branch
   ```
3. Make changes and commit:
   ```bash
   git commit -m "Description of changes"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-branch
   ```
5. Open a pull request.

---

