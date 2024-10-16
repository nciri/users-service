# Users Management API

## Description

This project is a Spring Boot-based RESTful API for managing users and their roles. The application allows:
- Admin users to access all user data.
- Regular users to view their own information.
- A secure authentication and role-based access control using Spring Security.
- API for user login and logout functionalities.

## Technologies

- **Java** (Spring Boot)
- **Spring Security** (Role-based access control)
- **Spring Data JPA** (Persistence layer)
- **PostgreSQL** (Database)
- **Maven** (Build tool)
- **Jakarta Servlet API** (Servlets)
- **HTTP Basic Authentication** (Authentication method)

## Features

- **User Authentication:** Users authenticate using HTTP Basic Authentication.
- **Role-based Authorization:**
  - **Admin** users can access `/api/users` to retrieve the list of all users.
  - **Regular** users can access `/api/me` to retrieve their own profile information.
- **Logout Functionality:** Users can logout by sending a request to `/api/logout`.
- **Session Management:** Sessions are invalidated and cookies are cleared on logout.

## Prerequisites

- **Java 17+**: Make sure you have JDK 17 or a newer version installed.
- **Maven**: Apache Maven should be installed to build the project.
- **PostgreSQL**: Ensure PostgreSQL is installed and running.

## Setup

### 1. Clone the repository
```bash

git clone https://github.com/nciri/users-service.git
cd yourproject

### 2. Configure the database
Make sure PostgreSQL is installed and running. You need to create a database and configure the connection details.

1- Create a new database:

CREATE DATABASE users_management;

2- Configure the database credentials in the src/main/resources/application.properties file:

spring.datasource.url=jdbc:postgresql://localhost:5432/users_management
spring.datasource.username=yourusername
spring.datasource.password=yourpassword

# Hibernate configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

### 3. Build the project
Use Maven to build the project:  mvn clean install

### 4. Run the application
After building, run the application using the following command: mvn spring-boot:run

The application will start on http://localhost:8080.

## API Endpoints

### Authentication
HTTP Basic Authentication is required for accessing the API endpoints.

### Admin Endpoints
GET /api/users - Retrieve the list of all users (Admin only).

### User Endpoints
GET /api/me - Retrieve the profile of the currently authenticated user (User only).

### Logout
POST /api/logout - Log out the current user, invalidate the session and delete cookies.

## Testing
You can use tools like Postman or curl to test the endpoints.

### Example request with curl
Login as Admin: curl -u admin:adminpassword http://localhost:8080/api/users
Login as User: curl -u user:userpassword http://localhost:8080/api/me

## Customization

### Adding new roles
To add new roles, you can modify the database table roles and assign new roles to users via the user_roles table.

### Extending the application
We will add new features such as:

- Managing users through an admin dashboard.
- Advanced session handling and security policies.
- Token-based authentication (JWT).

## License
This project is licensed under the MIT License. See the LICENSE file for details.



