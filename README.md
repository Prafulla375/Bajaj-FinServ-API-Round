# Bajaj Finserv API Round - Campus Hiring (May 2026)

## 👤 Details
- **Name**: Prafulla Dongre
- **Registration No**: 0827CS231183
- **Email**: prafulladongre230508@acropolis.in

## 🚀 API Endpoint

**POST** `/bfhl`

### Request Body
```json
{
  "data": ["a", "1", "334", "4", "R", "$"]
}
```

### Response
```json
{
  "is_success": true,
  "user_id": "prafulla_dongre_08052005",
  "email": "prafulladongre230508@acropolis.in",
  "roll_number": "0827CS231183",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

### GET `/bfhl`
Returns operation code for health check.

## 🏗️ Tech Stack
- **Java 17** with **Spring Boot 3.2.5**
- **Maven** for build management

## 📁 Project Structure
```
src/main/java/com/bajaj/finserv/
├── BajajFinservApplication.java          # Main entry point
├── controller/
│   └── BfhlController.java              # REST controller
├── dto/
│   ├── BfhlRequest.java                 # Request DTO
│   └── BfhlResponse.java                # Response DTO
├── exception/
│   └── GlobalExceptionHandler.java      # Global error handling
└── service/
    ├── BfhlService.java                 # Service interface
    └── BfhlServiceImpl.java             # Service implementation
```

## 🧪 Running Tests
```bash
mvn test
```

## 🚀 Running Locally
```bash
mvn spring-boot:run
```

## 🌐 Deployment (Railway)
The project includes a Dockerfile for easy Railway deployment.

```bash
# Build
mvn clean package -DskipTests

# Run
java -jar target/bajaj-finserv-1.0.0.jar
```

## 📌 Logic
- **Numbers**: Classified as even/odd; returned as strings
- **Alphabets**: Pure letter strings converted to uppercase
- **Special Characters**: Everything that's not purely numeric or purely alphabetic
- **Sum**: Sum of all numeric values
- **concat_string**: All alphabetical characters extracted individually, reversed, then alternating caps applied (Upper, lower, Upper, ...)
