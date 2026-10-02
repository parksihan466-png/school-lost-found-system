# School Lost and Found Management System

A simple web application for reporting lost items, found items, tracking claims, and managing item recovery records in a school environment.

## Features
- User registration and login screens
- Report Lost Item form
- Report Found Item form
- Search and browse lost/found records
- Dashboard with statistics
- Claims and message tracking
- Admin controls for managing users and records
- SQL schema and sample data

## Tech Stack
- Java 17
- Spring Boot 3
- Thymeleaf
- Spring Data JPA
- H2 Database
- HTML/CSS/JavaScript

## Project Structure
- `src/main/java` – Java backend code
- `src/main/resources/templates` – Thymeleaf pages
- `src/main/resources/static` – CSS and JavaScript assets
- `src/main/resources/application.properties` – app configuration
- `src/main/resources/schema.sql` – database structure
- `src/main/resources/data.sql` – sample data

## Run the app
```bash
mvn spring-boot:run
```

Then open:
- http://localhost:8080/

## Default admin account
- Username: `admin`
- Password: `admin123`

## Notes
This project is a working starter application for the described system and is designed to be extended with full authentication, file upload, and role-based authorization in a later phase.
