# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Maven archetype project that generates a multi-module Spring Boot application with a clean architecture pattern. It creates four modules: core (domain models), api (contracts/DTOs), service (business logic), and web (REST controllers).

## Essential Commands

### Archetype Development
```bash
# Build the archetype
mvn clean install

# Install locally for testing
mvn install

# Test archetype generation
mvn archetype:generate -DarchetypeGroupId=com.gridatek -DarchetypeArtifactId=spring-modular-archetype -DarchetypeVersion=1.0.0 -DgroupId=com.example -DartifactId=test-project -DinteractiveMode=false

# Format code (uses Spotless plugin)
mvn spotless:apply

# Check code formatting
mvn spotless:check
```

### Generated Project Commands
When working with projects generated from this archetype:
```bash
# Build all modules
mvn clean install

# Run the application (from web module)
cd [project-name]-web
mvn spring-boot:run

# Run tests
mvn test

# Run specific module tests
cd [project-name]-core && mvn test
```

## Architecture

### Module Structure
- **core**: Domain models (User, BaseEntity) and utilities (DateUtil) - no external dependencies except logging
- **api**: Service contracts (UserService interface) and DTOs (UserDto, CreateUserRequest, ApiResponse)
- **service**: Business logic implementations, repositories, and mappers
- **web**: REST controllers, Spring Boot configuration, and main application entry point

### Dependency Flow
```
web → service → api
       ↓        ↓
     core ←── core
```

### Key Patterns
- Repository pattern for data access abstraction
- Service layer for business logic encapsulation
- DTO pattern for data transfer between layers
- Dependency injection via Spring
- Generic API response wrapper (ApiResponse<T>)

## File Structure

### Archetype Definition
- `src/main/resources/META-INF/maven/archetype-metadata.xml`: Defines module structure and file filtering
- `src/main/resources/archetype-resources/`: Template files for generated projects
- `pom.xml`: Main archetype configuration with Spotless formatting

### Generated Project Structure
```
[project-name]/
├── pom.xml                    # Parent POM with dependency management
├── [project-name]-core/       # Domain models and utilities
├── [project-name]-api/        # Contracts and DTOs
├── [project-name]-service/    # Business logic
└── [project-name]-web/        # Spring Boot application
```

## Development Guidelines

### Code Formatting
This project uses Spotless with PalantirJavaFormat. Always run `mvn spotless:apply` before committing changes.

### Testing Strategy
- Each module includes comprehensive unit tests
- Uses JUnit 5 and Mockito for testing
- Web layer uses Spring Boot Test for integration testing

### Configuration
- Uses Java 17 as minimum version
- Spring Boot 3.1.2 for generated projects
- SLF4J + Logback for logging
- Maven Surefire 3.1.2 for test execution

## Template Variables
When modifying archetype resources, be aware of Maven archetype filtering:
- `${groupId}`, `${artifactId}`, `${version}`: Standard Maven coordinates
- `${rootArtifactId}`: Base name for module naming
- `${package}`, `__packageInPathFormat__`: Package structure
- All files in archetype-resources are filtered unless explicitly excluded