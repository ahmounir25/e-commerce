# 🛍️ e-commerce 

An **e-commerce REST API** built with **Spring Boot** and **MySQL**.  
It includes authentication with JWT, database migrations with Flyway, background task handling, Docker support, and CI/CD with GitHub Actions.

---

## 🚀 Features

- 🧾 RESTful API for managing users, products, carts, and orders  
- 🔐 **JWT Authentication** (Access & Refresh tokens)  
- 🛒 CRUD operations for products, carts, and orders  
- 📧 Email service integration using Gmail SMTP  
- 🗄️ **Flyway** for database version control  
- 🧵 **Background tasks** for async operations  
- 🐳 **Docker + Docker Compose** setup for local development  
- ⚙️ **GitHub Actions** for automated testing & CI  

---

## 🧰 Tech Stack

| Category | Technology |
|-----------|-------------|
| Backend Framework | Spring Boot |
| Database | MySQL |
| ORM | Spring Data JPA (Hibernate) |
| Migration Tool | Flyway |
| Authentication | JWT |
| Containerization | Docker, Docker Compose |
| CI/CD | GitHub Actions |

---

## 🏗️ Run with Docker Compose
```bash
docker-compose up --build

---

## 🧰 Environment Variables

Create a `.env` file in your project root (excluded from Git):

```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/ecommerce
MYSQL_USER=ecommerce_user
MYSQL_PASSWORD=ecommerce_pass

JWT_SECRET=your_jwt_secret_key
LIFE_TIME_ACCESS=3600000
LIFE_TIME_REFRESH=604800000

