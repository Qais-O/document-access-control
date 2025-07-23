# Document Access Control System

A Spring Boot application implementing a robust document access control system with CQRS (Command Query Responsibility Segregation) architecture pattern.

## Features

- 🔐 **Document Access Control**: Secure permission-based document access
- 🏗️ **CQRS Architecture**: Separation of command and query operations for better scalability
- 🌐 **REST API**: RESTful endpoints for document management
- 🔒 **Security Integration**: Built-in security configuration
- 🌍 **Internationalization**: Multi-language support (English/Arabic)
- 📊 **Batch Operations**: Efficient batch access checking

## Technology Stack

- **Java 24**
- **Spring Boot** - Application framework
- **Spring Data JPA** - Data persistence
- **Spring MVC** - Web framework
- **Spring Security** - Authentication and authorization
- **Jakarta EE** - Enterprise Java specifications
- **Lombok** - Boilerplate code reduction
- **Maven** - Build automation


## Getting Started

### Prerequisites

- Java 24 or higher
- Maven 3.6+
- Git

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Qais-O/document-access-control.git
   cd document-access-control
   ```

2. **Build the project:**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### Document Management
Thank you for providing the DocumentController code! Here's the updated API endpoints section with all the available endpoints:

## API Endpoints

### Document Management

#### Query Operations (Read)
- `GET /api/v1/documents/{id}` - Get specific document by ID
  - **Security**: Requires Read permission
  - **Parameters**: `id` (Long) - Document ID
  - **Returns**: `DocumentResponseDTO`

- `GET /api/v1/documents` - Get all accessible documents for the authenticated user
  - **Returns**: `List<DocumentResponseDTO>`

- `POST /api/v1/documents/access-check` - Batch access verification
  - **Body**: `BatchAccessCheckRequestDTO`
  - **Returns**: `BatchAccessCheckResponseDTO`

#### Command Operations (Write)
- `POST /api/v1/documents` - Create a new document
  - **Security**: Admin only (`@PreAuthorize("hasRole('ADMIN')")`)
  - **Body**: `DocumentRequestDTO`
  - **Returns**: `DocumentResponseDTO`

- `DELETE /api/v1/documents/{id}` - Delete a document
  - **Security**: Requires Delete permission
  - **Parameters**: `id` (Long) - Document ID
  - **Returns**: void

- `POST /api/v1/documents/{id}/grant` - Grant access to a document
  - **Parameters**: `id` (Long) - Document ID
  - **Body**: `GrantAccessRequestDTO`
  - **Returns**: `DocumentResponseDTO`

### Security Notes
- All endpoints require authentication (uses `Authentication` parameter)
- Custom security annotations are used:
  - `@RequiresPermission.Read` - For read operations
  - `@RequiresPermission.Delete` - For delete operations
  - `@RequiresPermission.AdminOnly` - For admin-only operations
- Spring Security's `@PreAuthorize` is also used for role-based access control

This gives you a complete CRUD (Create, Read, Update, Delete) API with additional access control features for document management.

## Architecture

### CQRS Pattern
This application implements the CQRS pattern with:

- **Commands**: Handle write operations (create, update, delete)
- **Queries**: Handle read operations (get, search, list)
- **Command Bus**: Routes commands to appropriate handlers
- **Query Bus**: Routes queries to appropriate handlers

### Key Components

- **CommandBus**: Executes commands through middleware pipeline
- **QueryBus**: Executes queries with optional middleware
- **DocumentPermissionService**: Manages document access permissions
- **SecurityConfig**: Configures authentication and authorization

## Configuration

### Application Properties
Configure the application in `src/main/resources/application.properties`:

```properties
# Database configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver

# JPA configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Security configuration
# Add your security configurations here
```

Author: Qais Alomari
