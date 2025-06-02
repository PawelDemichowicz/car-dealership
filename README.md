# 🚗 Car Dealership

## 📌 Project Description
Car Dealership is an application written in Java that simulates the operation of a car dealership service system. The project was implemented as part of a bootcamp on the Zajavka platform to practice backend development skills.

---

## 🛠 Technologies
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway (database migrations)
- Lombok (reducing boilerplate code)
- Gradle (build & dependency management)
- **Docker & Docker Compose**
- **Testcontainers (for integration testing)**

---

## ✨ Features
- Handling customers and dealership employees
- Recording vehicle purchases
- Creating car service requests
- Integration with PostgreSQL database
- Entity relationships using Hibernate
- Automatic DB migrations via Flyway
- Basic authentication setup
- External API integration (CEPiK)

---

## 🚀 Running the Application

### Option 1: Run Locally Without Docker

1. **Clone the repository**
   ```bash
   git clone https://github.com/PawelDemichowicz/car-dealership.git
   cd car-dealership
   ```

2. **Configure the database**
   - Create a PostgreSQL database manually.
   - Add a `.env` file in the root directory with:
     ```
     DB_URL=jdbc:postgresql://localhost:5432/your_db
     DB_USERNAME=your_user
     DB_PASSWORD=your_password
     ```

3. **Start the application**
   ```bash
   ./gradlew bootRun
   ```

---

### Option 2: Run with Docker Compose (Recommended)

> Make sure Docker and Docker Compose are installed and running.

1. **Clone the repository**
   ```bash
   git clone https://github.com/PawelDemichowicz/car-dealership.git
   cd car-dealership
   ```

2. **Configure the database**
   - Add a `.env` file in the root directory with:
     ```
     DB_URL=jdbc:postgresql://localhost:5432/your_db
     DB_USERNAME=your_user
     DB_PASSWORD=your_password
     ```


3. **Start containers**
   ```bash
   docker compose up --build
   ```

4. The application will be accessible at `http://localhost:8190` (or as defined in `docker-compose.yml`)


5. **To stop and clean up containers**
   ```bash
   docker compose down -v
   ```

---

## 🧪 Running Tests

> Integration tests use **Testcontainers**, so Docker must be running.

Run all tests:
```bash
./gradlew test
```

---

## 📂 Project Structure
```
├── api/                      # API layer (MVC controllers)
├── business/                 # Business logic layer
├── domain/                   # Business domain models
└── infrastructure/
    ├── configuration/        # Spring configuration classes
    ├── database/             # Data access layer (repositories)
    ├── security/             # Basic security config
    └── cepik/                # Integration with external CEPiK API
```