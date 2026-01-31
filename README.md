# WebQuizEngine

A RESTful web service for creating and solving quizzes, built with Spring Boot. Users can create multiple-choice quizzes, solve them, and track their completion history.

## Overview

WebQuizEngine is a full-stack web application that demonstrates modern Java backend development practices. It provides a complete quiz management system with user authentication, RESTful API design, and database persistence.

## Features

### Core Functionality
- **Quiz Creation** - Create multiple-choice quizzes with custom questions and answers
- **Quiz Solving** - Submit answers and receive instant feedback
- **User Authentication** - Secure registration and login system
- **Quiz Management** - Full CRUD operations for quiz owners
- **Pagination** - Navigate through large collections of quizzes
- **Completion Tracking** - Track which quizzes users have completed
- **Ownership Control** - Only quiz creators can modify/delete their quizzes

### Technical Features
- RESTful API design with JSON request/response
- JWT or session-based authentication
- Password encryption with BCrypt
- Database persistence with JPA/Hibernate
- Input validation and error handling
- Proper HTTP status codes

## Technologies Used

- **Spring Boot** - Main application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations and ORM
- **H2/MySQL/PostgreSQL** - Database (configurable)
- **Maven/Gradle** - Dependency management
- **BCrypt** - Password hashing
- **Jackson** - JSON serialization

## API Endpoints

### Authentication
```
POST   /api/register          - Register a new user
POST   /api/login             - Authenticate user
```

### Quiz Operations
```
POST   /api/quizzes           - Create a new quiz (authenticated)
GET    /api/quizzes           - Get all quizzes (paginated)
GET    /api/quizzes/{id}      - Get specific quiz details
POST   /api/quizzes/{id}/solve - Submit an answer
DELETE /api/quizzes/{id}      - Delete quiz (owner only)
```

### User Progress
```
GET    /api/quizzes/completed - Get user's completed quizzes
```

## Getting Started

### Prerequisites

- Java JDK 11 or higher
- Maven or Gradle
- MySQL (optional, H2 embedded database works out of the box)

### Installation

```bash
# Clone the repository
git clone https://github.com/BlackMunda/WebQuizEngine.git
cd WebQuizEngine

# Build the project
mvn clean install
# or
gradle build

# Run the application
mvn spring-boot:run
# or
gradle bootRun
```

The application will start on `http://localhost:8080`

## Configuration

Edit `application.properties` to configure:

```properties
# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/quizdb
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server port
server.port=8080
```

## Example Usage

### Register a User
```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "securePassword123"
  }'
```

### Create a Quiz
```bash
curl -X POST http://localhost:8080/api/quizzes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "title": "Java Basics",
    "text": "What is the size of int in Java?",
    "options": ["16 bits", "32 bits", "64 bits", "8 bits"],
    "answer": [1]
  }'
```

### Solve a Quiz
```bash
curl -X POST http://localhost:8080/api/quizzes/1/solve \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "answer": [1]
  }'
```

Response:
```json
{
  "success": true,
  "feedback": "Congratulations, you're right!"
}
```

## Project Structure

```
WebQuizEngine/
├── src/main/java/
│   ├── controller/
│   │   ├── QuizController.java
│   │   └── UserController.java
│   ├── model/
│   │   ├── Quiz.java
│   │   ├── User.java
│   │   └── CompletedQuiz.java
│   ├── repository/
│   │   ├── QuizRepository.java
│   │   ├── UserRepository.java
│   │   └── CompletedQuizRepository.java
│   ├── service/
│   │   ├── QuizService.java
│   │   └── UserService.java
│   ├── security/
│   │   └── SecurityConfig.java
│   └── WebQuizEngineApplication.java
└── src/main/resources/
    └── application.properties
```

## Database Schema

### Users Table
- id (Primary Key)
- email (Unique)
- password (Hashed)
- created_at

### Quizzes Table
- id (Primary Key)
- title
- text
- options (JSON)
- answer (JSON)
- user_id (Foreign Key)
- created_at

### Completed Quizzes Table
- id (Primary Key)
- quiz_id (Foreign Key)
- user_id (Foreign Key)
- completed_at

## Security Features

- **Password Hashing** - BCrypt with salt
- **Authentication** - JWT tokens or session-based
- **Authorization** - Role-based access control
- **Input Validation** - Bean validation annotations
- **SQL Injection Protection** - JPA prepared statements
- **CORS Configuration** - Controlled cross-origin access

## Learning Outcomes

This project helped me master:
- RESTful API design principles
- Spring Boot ecosystem and architecture
- Authentication and authorization flows
- Database relationships and JPA
- Pagination implementation
- HTTP status codes and error handling
- Security best practices
- JSON serialization and validation

## Future Enhancements

- [ ] Quiz categories and tags
- [ ] Difficulty levels
- [ ] Timed quizzes
- [ ] Leaderboard system
- [ ] Quiz statistics and analytics
- [ ] Image support in questions
- [ ] Multi-language support
- [ ] Social sharing features

## Testing

```bash
# Run tests
mvn test
# or
gradle test
```

## Contributing

This is a learning project, but feedback and suggestions are always welcome!

## Acknowledgments

Built as part of my journey learning Spring Boot and backend development.

## Contact

**Devashish Singh**
- GitHub: [@BlackMunda](https://github.com/BlackMunda)
- Email: devashishsingh488@gmail.com

---

*Built with ☕ Java and 🍃 Spring Boot*
