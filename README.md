# Blog Management System

This project is a simple demo of a Blog Management System built using Spring Boot. The application allows users to
create, read,
update, and delete posts and its comments. The project demonstrates the use of Spring Data JPA for database operations,
DTOs
for data transfer, and proper separation of concerns using services and controllers.

## Technologies Used

- **Java 17**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security**
- **Spring Boot v3.3.0**
- **PostgreSQL** (running in docker container)
- **Docker**
- **Lombok**
- **Maven**

## Architecture

**BlogApplication.java** is a simple microservice that exposes a set of endpoints for interaction. After obtaining a JWT
token, a user can perform CRUD operations on posts and create comments linked to those posts. All user data is stored in
the database and is returned upon a read request.

![arch-schema.png](architectural-schema.png)

## Project Structure

- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Contains business logic and handles data transformations.
- **Repository Layer**: Interacts with the database using Spring Data JPA.
- **DTOs (Data Transfer Objects)**: Used for transferring data between different layers.
- **Mappers**: Converts between entities and DTOs.

## Setup and Running

Ensure you have Docker installed as the database runs in a Docker container using **[compose.yaml](./compose.yaml)**.
The microservice itself
can be run locally.

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker (for running PostgreSQL in a container)

### Running the Application

1. **Build the project:**

```mvn clean install```

2. **Run the application:**

```mvn spring-boot:run```

## Usage

**1.** Request the creation of a new user and receive a JWT token in the response for use in subsequent requests.
   ```bash
   curl --location 'localhost:8080/api/v1/auth/register' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "firstname": "user2",
       "lastname": "user2",
       "email": "user2@mail.com",
       "password": "123",
       "role": "USER"
   }'
   ```
   Response:
   ```json
   {
       "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMkBtYWlsLmNvbSIsImlhdCI6MTcxODYxNzUzOSwiZXhwIjoxNzE4NzAzOTM5fQ.z5UR-lzj5fkdh5PqfAm6FRvQiIGrDKGEITjAeLLHjwE"
   }
   ```
   

**2.** Example request for creating a new post:
   ```bash
   curl --location 'localhost:8080/api/v1/posts' \
   --header 'Content-Type: application/json' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMkBtYWlsLmNvbSIsImlhdCI6MTcxODYxNzUzOSwiZXhwIjoxNzE4NzAzOTM5fQ.z5UR-lzj5fkdh5PqfAm6FRvQiIGrDKGEITjAeLLHjwE' \
   --data '{
   "title": "Hello word",
   "message": "This is a post body",
   "category": "test"
   }'
   ```
   Response:
   ```json
      {
      "id": 22,
      "title": "Hello word",
      "message": "This is a post body",
      "category": "test",
      "createdAt": "2024-06-17T11:49:53.8844479",
      "updatedAt": "2024-06-17T11:49:53.8844479",
      "comments": []
      }
  ```

**3.** Example request for creating a new comment:
```bash
curl --location 'localhost:8080/api/v1/comments' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMkBtYWlsLmNvbSIsImlhdCI6MTcxODYxNzUzOSwiZXhwIjoxNzE4NzAzOTM5fQ.z5UR-lzj5fkdh5PqfAm6FRvQiIGrDKGEITjAeLLHjwE' \
--data '{ 
    "postId": 22,
    "message": "my new comment to '\''Hello World'\''"
    }'
```
   Response:
```json
{
  "id": 27,
  "message": "my new comment to 'Hello World'",
  "postId": 22,
  "createdAt": "2024-06-17T11:59:08.5084456",
  "updatedAt": "2024-06-17T11:59:08.5084456"
}
```