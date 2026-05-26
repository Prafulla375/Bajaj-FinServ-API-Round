# Bajaj Finserv Health - Qualifier 1 (JAVA)

Spring Boot app for the Bajaj Finserv Health hiring qualifier.

## How to run

Make sure you have Java 17 and Maven installed.

```
mvn clean package -DskipTests
java -jar target/bajaj-finserv-1.0.0.jar
```

Or directly:
```
mvn spring-boot:run
```

## JAR file

The final JAR is included in the repo root: `bajaj-finserv.jar`

Download link: `https://github.com/loopholend/Bajaj-FinServ-api-round/raw/main/bajaj-finserv.jar`

## What it does

When the app starts up it automatically:
1. Sends a POST request to the generateWebhook API with my details
2. Gets back a webhook URL and access token
3. Sends the SQL query solution to testWebhook API with the token in Authorization header

No controller or manual trigger needed - everything runs on startup using CommandLineRunner.

## SQL Query

The query solves Question 1 (my regNo 0827CS231185 ends in 85 which is odd).

It finds the highest salary that was not credited on the 1st of any month, along with employee name, age, and department.

## Author
Pranjal Pal - 0827CS231185
