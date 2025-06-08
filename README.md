# 🏥 Patient Management API

A Spring Boot RESTful API to manage patient data including CRUD operations, filtering, sorting, pagination, validation, JWT authentication, and MySQL persistence – fully Dockerized.

---

## 🚀 Features

- ✅ Add, Update, Get, Delete Patients
- 🔎 Filter by age, gender, and diagnosis
- 🧾 Pagination & Sorting
- 🔐 JWT-based Authentication (Login/Register)
- 🛡️ Role-based Access Control (ADMIN, USER)
- ✅ Input Validation with clear error responses
- 🐳 Docker & Docker Compose support
- 🌐 RESTful architecture with DTOs
- 📦 Clean project structure

---

## 🛠️ Tech Stack

| Layer       | Technology         |
|-------------|--------------------|
| Language    | Java 21            |
| Framework   | Spring Boot 3      |
| Database    | MySQL 8 (Docker)   |
| Auth        | Spring Security + JWT |
| Docs        | Springdoc Swagger UI |
| Build Tool  | Maven              |
| Dev Tools   | IntelliJ, Docker, Postman |

---

## 🏁 Getting Started

### 🔧 Prerequisites
- Docker & Docker Compose
- Java 21
- Maven

---

### 🐳 Run with Docker Compose

```bash
# Build and run containers
docker-compose up --build


📚 Sample API Endpoints
Method	   Endpoint	                Description
GET	    /api/patients	        Get all patients
POST	    /api/patients	        Create a new patient
PUT	    /api/patients/{id}	        Update a patient
DELETE	    /api/patients/{id}	        Delete a patient
PATCH	    /api/patients/{id}	        Partially update fields
GET	    /api/patients?gender=male	Filter by gender
```




#### 🔐 Authentication APIs

### POST `/api/auth/register`
Register a new user.

### POST `/api/auth/login`
Login with credentials to receive a JWT.

**Example Login Request:**
```json
{
  "email": "admin@gmail.com",
  "password": "admin123"
}
```
### Use the returned JWT for secured endpoints:

Authorization: Bearer <your_token>

---
#### ✅ Environment Variables

```
Create a .env file in the root with the following:

DB_NAME=patient_management
DB_USER=root
DB_PASSWORD=your_password
DB_ROOT_PASSWORD=your_root_pwd
```
---