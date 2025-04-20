# URL Shortener

A simple URL shortener application built with Spring Boot and Thymeleaf that allows users to create shortened versions of long URLs.

## Features

- Shorten long URLs to easy-to-share links
- Redirect from shortened URLs to original URLs
- Clean and responsive user interface
- In-memory database for storing URL mappings

## Technologies Used

- Java 17
- Spring Boot 3.4.4
- Spring Data JPA
- Thymeleaf
- H2 Database
- Bootstrap 5
- JUnit 5 & Mockito for testing

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher

## Installation and Setup

1. Clone the repository:
   ```
   git clone <repository-url>
   cd URL-shortner
   ```

2. Build the application:
   ```
   mvn clean install
   ```

## Running the Application

Run the application using Maven:
```
mvn spring:boot run
```

Or run the JAR file directly:
```
java -jar target/URLShortner-0.0.1-SNAPSHOT.jar
```

The application will be available at: http://localhost:8080

## How to Use

1. Open your browser and navigate to http://localhost:8080
2. Enter a long URL in the input field
3. Click the "Shorten URL" button
4. Copy the generated short URL and share it
5. When someone accesses the short URL, they will be redirected to the original URL

## API Endpoints

- `GET /` - Home page with URL shortening form
- `POST /shorten` - Create a shortened URL
- `GET /{shortUrl}` - Redirect to the original URL

## Database

The application uses an H2 in-memory database. You can access the H2 console at:
http://localhost:8080/h2-console

Database credentials:
- JDBC URL: `jdbc:h2:mem:urldb`
- Username: `sa`
- Password: `password`

## Testing

The application includes comprehensive unit tests for the service and controller layers. Run the tests using:
```
mvn test
```

## Project Structure

```
src
├── main
│   ├── java
│   │   └── org.example.urlshortner
│   │       ├── controller
│   │       │   └── UrlController.java
│   │       ├── model
│   │       │   └── Url.java
│   │       ├── repository
│   │       │   └── UrlRepository.java
│   │       ├── service
│   │       │   └── UrlService.java
│   │       └── UrlShortnerApplication.java
│   └── resources
│       ├── application.properties
│       └── templates
│           ├── index.html
│           └── result.html
└── test
    └── java
        └── org.example.urlshortner
            ├── controller
            │   └── UrlControllerTest.java
            ├── service
            │   └── UrlServiceTest.java
            └── UrlShortnerApplicationTests.java
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.