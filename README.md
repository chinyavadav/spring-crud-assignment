# CRUD Assignment

We have a collection of userDetail, and we wish to create a microservice that will perform the basic CRUD
operations (Create, Read Update &amp; Delete), plus the ability to search for a userDetail by their first name, last name and ID.
The userDetail information must be persisted to a database (can be any database: NoSQL or SQL DB)

## What I would have done better

1. Implement Spring Security for the rest of the endpoints
2. Write Tests to ensure TDD
3. Add environments for production, test & staging


## Installation

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/assignment
spring.datasource.username=postgres
spring.datasource.password=
```

1. Create postgres database named "assignment"
2. Update postgres username,password and port as per above snippet
3. Run the following maven commands within the terminal from inside the project root folder

```bash
mvn install
mvn spring-boot:run
```

## Documentation

Swagger is running on [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 

