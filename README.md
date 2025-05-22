# Notes API - Spring Boot Project

This is a basic REST API for managing personal notes, built with Java 17 and Spring Boot 3. It follows a layered architecture using DTOs, service interfaces, custom exception handling, CORS configuration, and MySQL integration.

## 📁 Project Structure

```
src/main/java/com/dacarsoft/notes/
├── config/                  # Global CORS config
├── controller/              # REST API layer
├── dto/                     # Request/Response DTOs
├── exception/               # Custom exception + global handler
├── mapper/                  # Converts between entities and DTOs
├── model/                   # JPA entity
├── repository/              # Data access layer
├── service/                 # Business logic layer
└── NotesApiApplication.java # Spring Boot entry point
```

## ⚙️ Requirements

- Java 17+
- Maven
- MySQL (running locally)
- IntelliJ IDEA (recommended)

## 🛠 Environment Variables (set in IntelliJ or system)

```
DB_URL=jdbc:mysql://localhost:3306/notas?useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=pass2025*
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
```

## 🚀 How to Run

1. Clone or unzip the project.
2. Make sure the MySQL database `notas` exists.
3. Open the project in IntelliJ and set the environment variables.
4. Run `NotesApiApplication.java`.
5. Access the API at `http://localhost:8080/api/notes`.

## 🧪 Tests

Includes unit tests for:
- `NoteServiceImpl` using JUnit + Mockito
- `NoteController` using @WebMvcTest + MockMvc

Run tests with:

```bash
./mvnw test
```

## 📦 Dependencies

- Spring Web
- Spring Data JPA
- MySQL Driver
- Spring Boot DevTools
- Spring Boot Starter Test

## 🧠 Features

- Full CRUD for Notes
- DTOs and Mapping Layer
- Global Exception Handling
- Bean Validation with `@Valid`
- CORS Config for Frontend Access

---

© 2025 Dacarsoft. Built for educational and starter template purposes.
