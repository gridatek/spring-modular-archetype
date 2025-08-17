# ${artifactId}

A multi-module Maven project generated from the multimodule-workspace-archetype.

## Project Structure

```
${artifactId}/
├── pom.xml                    # Parent POM with dependency management
├── ${artifactId}-core/        # Core domain models and utilities
├── ${artifactId}-api/         # API contracts and DTOs
├── ${artifactId}-service/     # Business logic implementation
└── ${artifactId}-web/         # Spring Boot web application
```

## Modules

### Core Module (`${artifactId}-core`)
- Contains domain models and utility classes
- Base entities with common fields (id, timestamps)
- Date utilities and other helper classes
- No external dependencies except logging

### API Module (`${artifactId}-api`)
- Defines service contracts and interfaces
- Data Transfer Objects (DTOs)
- API response wrappers
- Depends on core module

### Service Module (`${artifactId}-service`)
- Business logic implementation
- Repository interfaces and implementations
- Service implementations
- Domain object mapping
- Depends on core and api modules

### Web Module (`${artifactId}-web`)
- REST controllers
- Spring Boot configuration
- Web-specific configurations
- Main application entry point
- Depends on service module

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Building the Project

```bash
# Build all modules
mvn clean install

# Build specific module
cd ${artifactId}-web
mvn clean install
```

### Running the Application

```bash
# Run from web module
cd ${artifactId}-web
mvn spring-boot:run

# Or run the generated JAR
java -jar ${artifactId}-web/target/${artifactId}-web-${version}.jar
```

The application will start on `http://localhost:8080`

### API Endpoints

#### User Management
- `POST /api/users` - Create a new user
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/username/{username}` - Get user by username
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

#### Health Check
- `GET /api/health` - Application health status

### Example API Usage

```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username": "john_doe", "email": "john@example.com", "firstName": "John", "lastName": "Doe"}'

# Get all users
curl http://localhost:8080/api/users

# Get user by ID
curl http://localhost:8080/api/users/1

# Health check
curl http://localhost:8080/api/health
```

## Testing

### Run all tests
```bash
mvn test
```

### Run tests for specific module
```bash
cd ${artifactId}-core
mvn test
```

## Profiles

The application supports different profiles:

### Development (default)
- Enhanced logging
- Detailed error messages

### Production
- Minimal logging
- Error details hidden from API responses

```bash
# Run with production profile
java -jar -Dspring.profiles.active=production ${artifactId}-web/target/${artifactId}-web-${version}.jar
```

## Configuration

Configuration files are located in:
- `${artifactId}-service/src/main/resources/application.properties`
- `${artifactId}-web/src/main/resources/application.yml`

## Architecture Notes

### Dependency Flow
```
web -> service -> api
          ↓       ↓
        core <- core
```

### Design Patterns Used
- **Repository Pattern**: Data access abstraction
- **Service Layer**: Business logic encapsulation
- **DTO Pattern**: Data transfer between layers
- **Factory Pattern**: API response creation
- **Dependency Injection**: Spring-based IoC

### Best Practices Implemented
- Clear module boundaries
- Separation of concerns
- Comprehensive testing
- Proper error handling
- Logging throughout the application
- RESTful API design

## Customization

This project template can be extended by:

1. **Adding new modules**: Create additional modules following the same pattern
2. **Database integration**: Replace in-memory repository with JPA/Hibernate
3. **Security**: Add Spring Security for authentication/authorization
4. **Validation**: Add Bean Validation annotations to DTOs
5. **Documentation**: Add Swagger/OpenAPI documentation
6. **Caching**: Add Redis or other caching solutions
7. **Message queues**: Integrate RabbitMQ or Kafka

