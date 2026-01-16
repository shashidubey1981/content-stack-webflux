# Content Stack WebFlux

A Spring Boot WebFlux-based Backend for Frontend (BFF) application that provides a reactive API layer for integrating with Contentstack CMS. This application serves as an intermediary between frontend applications and Contentstack's Content Delivery API, offering optimized endpoints for web configuration, navigation, personalized content, and page entries.

## Features

- **Reactive Architecture**: Built with Spring WebFlux for non-blocking, asynchronous request handling
- **Contentstack Integration**: Seamless integration with Contentstack Content Delivery API
- **Multiple Content Types Support**: 
  - Web configuration
  - Navigation configuration
  - Personalized configuration
  - Page entries with URL-based lookup
- **Localization Support**: Multi-locale content retrieval
- **Personalization**: Support for personalized variants
- **Health Monitoring**: Built-in actuator endpoints for health checks and metrics
- **Global Exception Handling**: Centralized error handling
- **CORS Support**: Cross-origin resource sharing enabled

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring WebFlux**: Reactive web framework
- **Project Reactor**: Reactive programming library
- **Lombok**: Reduces boilerplate code
- **Jackson**: JSON processing
- **Spring Boot Actuator**: Application monitoring and management

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Contentstack account with API credentials

## Configuration

The application can be configured via `application.yml` or environment variables:

### Application Properties

```yaml
server:
  port: 8080

contentstack:
  api-key: ${CONTENTSTACK_API_KEY:your-api-key}
  delivery-token: ${CONTENTSTACK_DELIVERY_TOKEN:your-delivery-token}
  environment: ${CONTENTSTACK_ENVIRONMENT:preview}
  region: ${CONTENTSTACK_REGION:us}
  api:
    base-url: ${CONTENTSTACK_BASE_URL:https://cdn.contentstack.io/v3}
```

### Environment Variables

You can override the default configuration using environment variables:

- `CONTENTSTACK_API_KEY`: Your Contentstack API key
- `CONTENTSTACK_DELIVERY_TOKEN`: Your Contentstack delivery token
- `CONTENTSTACK_ENVIRONMENT`: Contentstack environment (e.g., preview, production)
- `CONTENTSTACK_REGION`: Contentstack region (e.g., us, eu, azure-na)
- `CONTENTSTACK_BASE_URL`: Base URL for Contentstack API (default: https://cdn.contentstack.io/v3)

## API Endpoints

Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI JSON: http://localhost:8080/v3/api-docs



All endpoints are prefixed with `/api/contentstack`.

### 1. Get Web Configuration
Retrieves web configuration entries from Contentstack.

**Endpoint**: `GET /api/contentstack/web-config`

**Query Parameters**:
- `contentTypeUid` (required): The content type UID
- `locale` (optional): Locale code (e.g., "en-us")
- `variant` (optional): Variant name for personalization

**Example**:
```bash
curl "http://localhost:8080/api/contentstack/web-config?contentTypeUid=web_config&locale=en-us"
```

### 2. Get Navigation Configuration
Retrieves navigation configuration entries from Contentstack.

**Endpoint**: `GET /api/contentstack/navigation-config`

**Query Parameters**:
- `contentTypeUid` (required): The content type UID
- `locale` (optional): Locale code (e.g., "en-us")
- `variant` (optional): Variant name for personalization

**Example**:
```bash
curl "http://localhost:8080/api/contentstack/navigation-config?contentTypeUid=navigation_config&locale=en-us"
```

### 3. Get Personalized Configuration
Retrieves personalized configuration entries from Contentstack.

**Endpoint**: `GET /api/contentstack/personalized-config`

**Query Parameters**:
- `contentTypeUid` (required): The content type UID
- `locale` (optional): Locale code (e.g., "en-us")
- `variant` (optional): Variant name for personalization

**Example**:
```bash
curl "http://localhost:8080/api/contentstack/personalized-config?contentTypeUid=personalized_config&locale=en-us"
```

### 4. Get Entries
Retrieves page entries from Contentstack by content type.

**Endpoint**: `GET /api/contentstack/entries`

**Query Parameters**:
- `contentTypeUid` (required): The content type UID
- `locale` (required): Locale code (e.g., "en-us")
- `personalizedVariant` (optional): Personalized variant name

**Example**:
```bash
curl "http://localhost:8080/api/contentstack/entries?contentTypeUid=page&locale=en-us&personalizedVariant=variant1"
```

### 5. Health Check
Simple health check endpoint.

**Endpoint**: `GET /api/contentstack/health`

**Example**:
```bash
curl "http://localhost:8080/api/contentstack/health"
```

## Actuator Endpoints

The application exposes Spring Boot Actuator endpoints for monitoring:

- `/actuator/health`: Application health status
- `/actuator/info`: Application information
- `/actuator/metrics`: Application metrics

## Running the Application

### Using Maven

1. Clone the repository:
```bash
git clone <repository-url>
cd content-stack-webflux
```

2. Configure your Contentstack credentials in `application.yml` or set environment variables.

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

### Using JAR

1. Build the JAR:
```bash
mvn clean package
```

2. Run the JAR:
```bash
java -jar target/content-stack-webflux-1.0.0.jar
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── contentstack/
│   │           └── webflux/
│   │               ├── config/              # Configuration classes
│   │               │   ├── ContentstackConfig.java
│   │               │   ├── WebClientConfig.java
│   │               │   └── WebConfig.java
│   │               ├── controller/          # REST controllers
│   │               │   └── ContentstackController.java
│   │               ├── dto/                 # Data Transfer Objects
│   │               │   ├── ContentstackPageResponse.java
│   │               │   ├── EntryRequest.java
│   │               │   ├── NavigationResponse.java
│   │               │   ├── PersonalizeConfigResponse.java
│   │               │   └── WebConfigResponse.java
│   │               ├── exception/           # Exception handling
│   │               │   └── GlobalExceptionHandler.java
│   │               ├── service/            # Business logic
│   │               │   ├── ContentstackClientService.java
│   │               │   └── ContentstackIncludes.java
│   │               └── ContentStackWebfluxApplication.java
│   └── resources/
│       └── application.yml                 # Application configuration
└── pom.xml                                 # Maven dependencies
```

## Development

### Logging

The application uses SLF4J with Logback. Logging levels can be configured in `application.yml`:

```yaml
logging:
  level:
    com.contentstack: DEBUG
    org.springframework.web: INFO
    reactor.netty: INFO
```

### Error Handling

The application includes a global exception handler (`GlobalExceptionHandler`) that provides consistent error responses across all endpoints.

## License

This project is a proof of concept (POC) for Contentstack integration using Spring WebFlux.
