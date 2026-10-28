# Spring Boot Microservice Demo

A lightweight demonstration of a microservice architecture built with **Spring Boot 4**, **Spring Cloud 2025**, **Netflix Eureka**, **Spring Cloud Gateway**, and **OpenFeign**.

---

## 🏛️ Architecture Overview

```
                      ┌─────────────────────────┐
                      │   Client / Browser      │
                      └────────────┬────────────┘
                                   │ HTTP
                                   ▼
                      ┌─────────────────────────┐
                      │  API Gateway (:8080)    │
                      └─────┬─────────────┬─────┘
                            │             │
              /api/users/** │             │ /api/orders/**
                            ▼             ▼
             ┌───────────────────┐     ┌───────────────────┐
             │   User Service    │◄────┤   Order Service   │
             │      (:8081)      │ Feign      (:8082)      │
             └─────────┬─────────┘     └─────────┬─────────┘
                       │                         │
                       └───────────┬─────────────┘
                                   │ Register / Discover
                                   ▼
                      ┌─────────────────────────┐
                      │ Service Registry Eureka │
                      │         (:8761)         │
                      └─────────────────────────┘
```

### Components

| Service | Port | Description |
| :--- | :--- | :--- |
| **`service-registry`** | `8761` | Eureka Server for service registration and discovery |
| **`api-gateway`** | `8080` | Spring Cloud Gateway for centralized routing to microservices |
| **`user-service`** | `8081` | Handles user data and user-related APIs |
| **`order-service`** | `8082` | Handles orders and communicates with `user-service` via OpenFeign |

---

## 📋 Prerequisites

- **Java 17** or higher
- **Maven** (a wrapper `./mvnw` is included in the project)
- **Docker & Docker Compose** (optional, for containerized execution)

---

## 🛠️ Build the Project

Build all microservices and create runnable JARs using the included Maven wrapper:

```bash
./mvnw clean package -DskipTests
```

To run with test execution:

```bash
./mvnw clean package
```

---

## 🚀 Running the Applications

### Option 1: Run with Docker Compose (Recommended)

1. Make sure **Docker Desktop** is running.
2. Build the JARs first (if you haven't already):
   ```bash
   ./mvnw clean package -DskipTests
   ```
3. Start all services using Docker Compose:
   ```bash
   docker compose -f Docker-compose.yml up --build
   ```
   > **Note:** Run in the background using detached mode `-d`:
   > ```bash
   > docker compose -f Docker-compose.yml up --build -d
   > ```
4. Stop the services:
   ```bash
   docker compose -f Docker-compose.yml down
   ```

---

### Option 2: Run Locally (Without Docker)

Run each service in a separate terminal window in the order listed below:

1. **Service Registry (Eureka Server):**
   ```bash
   java -jar service-registry/target/service-registry-0.0.1-SNAPSHOT.jar
   ```
   *Wait until Eureka starts at http://localhost:8761 before starting the other services.*

2. **User Service:**
   ```bash
   java -jar user-service/target/user-service-0.0.1-SNAPSHOT.jar
   ```

3. **Order Service:**
   ```bash
   java -jar order-service/target/order-service-0.0.1-SNAPSHOT.jar
   ```

4. **API Gateway:**
   ```bash
   java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar
   ```

---

## 🧪 Testing the Endpoints

### 1. Eureka Dashboard
Open your browser and visit:
- **[http://localhost:8761](http://localhost:8761)**

Verify that `USER-SERVICE`, `ORDER-SERVICE`, and `API-GATEWAY` are actively registered:

![Eureka Dashboard - Registered Instances](docs/images/eureka-dashboard.png)

![Eureka Server - General & Instance Info](docs/images/eureka-general-info.png)

### 2. User Service Endpoints (via API Gateway)
- **Get all users:**
  ```bash
  curl http://localhost:8080/api/users
  ```
- **Get user by ID:**
  ```bash
  curl http://localhost:8080/api/users/1
  ```

### 3. Order Service Endpoints (via API Gateway)
- **Get all orders:**
  ```bash
  curl http://localhost:8080/api/orders
  ```

### 4. Inter-Service Communication (OpenFeign)
Fetches an order and automatically calls `user-service` to enrich the response with customer details:
```bash
curl http://localhost:8080/api/orders/1/details
```

**Sample Response:**
```json
{
  "order": {
    "id": 1,
    "userId": 1,
    "product": "iPhone 15",
    "price": 89999.0,
    "status": "DELIVERED"
  },
  "user": {
    "id": 1,
    "name": "Ravi Kumar",
    "email": "ravi@gmail.com"
  }
}
```

---

## 📁 Project Structure

```
microservice-demo/
├── pom.xml                 # Root multi-module Maven configuration
├── mvnw                    # Maven wrapper script
├── Docker-compose.yml      # Docker compose definition for all services
├── README.md               # Documentation
├── service-registry/       # Eureka Discovery Server
├── api-gateway/            # Spring Cloud Gateway
├── user-service/           # User Microservice
└── order-service/          # Order Microservice (includes Feign client)
```

