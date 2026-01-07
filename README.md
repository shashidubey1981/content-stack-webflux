# Content Stack WebFlux Application

A Spring Boot WebFlux application for integrating with Contentstack CMS. This application provides REST APIs to fetch content entries from Contentstack, with support for variants and locales.

## Features

- **Fetch Entries**: Retrieve content entries by content type with support for variants and locales
- **Fetch Entry by URL**: Retrieve a specific entry using its URL
- **Reactive Programming**: Built with Spring WebFlux for non-blocking, reactive operations
- **CORS Enabled**: Configured to work with Next.js frontend applications
- **Comprehensive Error Handling**: Global exception handling for better error responses

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Contentstack API credentials (API Key, Delivery Token, Environment)

## Configuration

Configure the application using environment variables or by editing `application.properties`:

```properties
contentstack.api.key=your-api-key
contentstack.delivery.token=your-delivery-token
contentstack.environment=production
contentstack.region=us
```

### Environment Variables

You can also set these via environment variables:
- `CONTENTSTACK_API_KEY`
- `CONTENTSTACK_DELIVERY_TOKEN`
- `CONTENTSTACK_ENVIRONMENT`
- `CONTENTSTACK_REGION`

## Running the Application

1. Build the project:
```bash
mvn clean install
```

2. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Fetch Entries

**GET** `/api/contentstack/entries`

Query Parameters:
- `contentTypeUid` (required): The content type UID
- `locale` (optional): Locale code (e.g., "en-us")
- `variant` (optional): Variant name
- `include` (optional): List of fields to include
- `exclude` (optional): List of fields to exclude
- `query` (optional): Query parameters as key-value pairs
- `skip` (optional): Number of entries to skip (pagination)
- `limit` (optional): Maximum number of entries to return

**Example:**
```
GET /api/contentstack/entries?contentTypeUid=blog_post&locale=en-us&variant=default
```

**POST** `/api/contentstack/entries`

Request Body:
```json
{
  "contentTypeUid": "blog_post",
  "locale": "en-us",
  "variant": "default",
  "include": ["title", "description"],
  "skip": 0,
  "limit": 10
}
```

### 2. Fetch Entry by URL

**GET** `/api/contentstack/entries/url`

Query Parameters:
- `url` (required): The URL of the entry
- `locale` (optional): Locale code (e.g., "en-us")
- `variant` (optional): Variant name

**Example:**
```
GET /api/contentstack/entries/url?url=/blog/my-first-post&locale=en-us&variant=default
```

**POST** `/api/contentstack/entries/url`

Request Body:
```json
{
  "url": "/blog/my-first-post",
  "locale": "en-us",
  "variant": "default"
}
```

### 3. Health Check

**GET** `/api/contentstack/health`

Returns the health status of the API.

## Usage with Next.js

This API is designed to be consumed by Next.js applications. Example usage:

```javascript
// Fetch entries
const response = await fetch('http://localhost:8080/api/contentstack/entries?contentTypeUid=blog_post&locale=en-us');
const data = await response.json();

// Fetch entry by URL
const entryResponse = await fetch('http://localhost:8080/api/contentstack/entries/url?url=/blog/my-post&locale=en-us');
const entry = await entryResponse.json();
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/contentstack/webflux/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/       # REST controllers
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── exception/       # Exception handlers
│   │       └── service/         # Business logic services
│   └── resources/
│       └── application.properties
└── test/
```

## Technologies Used

- Spring Boot 3.2.0
- Spring WebFlux
- Project Reactor
- Lombok
- Jackson (JSON processing)
- Maven

## License

This project is licensed under the MIT License.

