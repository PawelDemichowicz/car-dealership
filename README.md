# Car Dealership

## Project Description

**Car Dealership** is an application written in Java that simulates the operation of a car dealership service system. The project was implemented as part of the learning process on the Zajavka platform and aimed at practicing the knowledge acquired from course materials.

## Technologies
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Flyway** (database migrations)
- **Lombok** (reducing boilerplate code)
- **Gradle** (dependency management)

## Features
- Handling customers and dealership employees
- Recording vehicle purchases
- Creating car service requests
- Integration with PostgreSQL database
- Using relationships between entities with Hibernate
- Automatic database migrations using Flyway

## Installation and Running
1. **Clone the repository:**

```bash
git clone https://github.com/PawelDemichowicz/car-dealership.git
```

2. **Navigate to the project directory:**

```bash
cd car-dealership
```

3. **Configure the PostgreSQL database:**
    - Create a database.
    - Create a .env file in the main directory of the project.
    - Fill the .env file with the necessary database credentials, e.g.:
      - DB_URL=database url
      - DB_USERNAME=username
      - DB_PASSWORD=password


4. **Run the application:**

```bash
./gradlew bootRun
```

5. **Run tests:**

   > **Important:** If you want to run all tests, make sure Docker is running before proceeding. This is necessary because the tests use **Testcontainers**, which relies on Docker to create and manage containerized environments for testing.
   
   
   To run all tests:

```bash
./gradlew test
```

## Project Structure
- **infrastructure/cepik/** – Configuration for consuming external API
- **infrastructure/security/** – Basic security with user login configurations
- **infrastructure/configuration/** – Spring configurations
- **infrastructure/database/** – Data access layer
- **business/** – Business logic layer of the application
- **domain/** – Layer for encapsulating business objects
- **api/** – API handling requests and views as MVC