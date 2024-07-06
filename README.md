## Meal App Backend

### Project Overview

Welcome to the backend of the Meal App Website! This backend service is built using Spring Boot and provides a robust and scalable foundation for managing meal bookings, user authentication, and various other functionalities. The backend interacts with the frontend to ensure a seamless experience for users.

### Key Features

1. User Authentication and Authorization
2. Meal Booking Management
3. OTP-Based Password Reset
4. QR Code Generation
5. Time Constraints on Bookings

### Installation and Setup

#### Prerequisites

- Java 17
- Maven
- MySQL

#### Installation Steps

1. **Clone the Repository**
    ```sh
    git clone https://github.com/yourusername/meal-app-backend.git
    ```
2. **Navigate to the Project Directory**
    ```sh
    cd meal-app-backend
    ```
3. **Set Up Database**
    - Create a MySQL database named `mealapp`.
    - Update the database configuration in `src/main/resources/application.properties` as shown below.

4. **Configure `application.properties`**
   Update the `src/main/resources/application.properties` file with the following properties:

    ```properties
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    #spring.jpa.hibernate.ddl-auto=create

    spring.datasource.url=jdbc:mysql://localhost:3306/mealApp?createDatabaseIfNotExist=true
    spring.datasource.username=root
    #root
    spring.datasource.password=root

    #logging.level.org.springframework=DEBUG
    #logging.level.org.hibernate=DEBUG

    springdoc.swagger-ui.path=/mealApp/api/swagger-ui.html
    springdoc.api-docs.path=/mealApp/api/api-docs
    #http://localhost:8080/mealApp/api/swagger-ui/index.html#/

    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    #spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL57Dialect
    spring.jpa.generate-ddl=true
    #spring.jpa.show-sql=true

    jwt.secret=5367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
    #spring-boot.run.jvmArguments=--add-opens java.base/sun.security.util=ALL-UNNAMED
    #logging.level.org.springframework.security=DEBUG

    spring.mail.username=Your@gmail.com
    spring.mail.properties.mail.smtp.starttls.required=true
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.password=Yourpassword
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.transport.protocol=smtp
    spring.mail.properties.mail.smtp.starttls.enable=true
    spring.mail.properties.smtp.ssl.enable=true
    ```

5. **Build the Project**
    ```sh
    mvn clean install
    ```
6. **Run the Application**
    ```sh
    mvn spring-boot:run
    ```
   The backend server will start on `http://localhost:8080`.

### Project Structure

```plaintext
src/
    ├── main/
    │   ├── java/
    │   │   ├── com.mealapp.backend/
    │   │   │   ├── config/
    │   │   │   ├── controller/
    │   │   │   ├── dtos/
    │   │   │   │   ├── Request/
    │   │   │   │   ├── Response/
    │   │   │   ├── entities/
    │   │   │   ├── enums/
    │   │   │   ├── repository/
    │   │   │   ├── service/
    │   │   │   │   ├── Implementation/
    │   │   │   │   ├── JWT/
    │   │   │   ├── util/
```

### Key Modules

#### Config Module

Contains configuration classes for security, JWT, and other configurations.

#### Controller Module

Handles HTTP requests and maps them to appropriate service methods.

#### DTOs (Data Transfer Objects) Module

Contains classes for transferring data between different layers of the application.

- **Request DTOs**: Classes used for incoming requests.
- **Response DTOs**: Classes used for outgoing responses.

#### Entities Module

Contains JPA entity classes that represent database tables.

#### Enums Module

Contains enumeration types used across the application.

#### Repository Module

Contains interfaces for database access and CRUD operations using Spring Data JPA.

#### Service Module

Contains service interfaces and their implementations, encapsulating the business logic.

- **Implementation**: Contains concrete implementations of the service interfaces.
- **JWT**: Contains classes related to JWT generation and validation.

#### Util Module

Contains utility classes and methods used across the application.

### API Documentation

The API documentation provides detailed information about the available endpoints, request/response formats, and usage examples. You can access the API documentation here: [API Documentation](API%20DOC.mhtml)

### Usage

#### Authentication

The backend handles user authentication and authorization using JWT. Use the `AuthController` for endpoints related to login, registration, and OTP verification.

#### Booking

Manage meal bookings using the `BookingController`. This includes creating, canceling, and retrieving bookings.

#### Notifications

Manage and send notifications to users based on various events and actions.

#### QR Code Generation

Generate QR codes for meal collection using the `QRCodeService`.

### Conclusion

The backend of the Meal App Website is designed to provide a secure and efficient foundation for meal booking and management. With features like JWT-based authentication, robust service architecture, and detailed API documentation, this backend ensures a seamless interaction with the frontend and a smooth user experience.

For any further questions or contributions, please refer to the project's GitHub repository.

---

Feel free to adjust the paths and links according to your actual project setup.