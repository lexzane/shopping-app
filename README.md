# <span style="vertical-align: sub"><img src="src/main/resources/icons/shop.png"></span> Shopping Application <span style="vertical-align: sub"><img src="src/main/resources/icons/shop.png"></span>

### <span style="vertical-align: middle"><img src="src/main/resources/icons/description.png"></span> Project description:

This is a simple implementation of a shopping application, created using SpringBoot, REST principles and supports some CRUD operations.

### <span style="vertical-align: middle"><img src="src/main/resources/icons/features.png"></span> Features:

This app fetches data about all products from database, filters it using RegEx and displays result set to user in JSON format.

Supported operations:
- save product
- get all products by name with RegEx filter.

### <span style="vertical-align: middle"><img src="src/main/resources/icons/structure.png"></span> Structure:

1. `main.java.product.shop` package folders:
    - `controller` - contains presentation layer's classes
    - `dto` - contains model's DTOs
    - `model` - contains models
    - `repository` - contains database layer's interfaces
    - `service` - contains business logic layer's interfaces/classes and DTO mappers

2. `main.resources` package:
    - SpringBoot configuration folders
    - icons for `README.md`
3. `test.java.product.shop` package:
    - contains all test methods

### <span style="vertical-align: middle"><img src="src/main/resources/icons/endpoints.png"></span> List of endpoints:

- `POST`: /shop/products - save new product to database
- `GET`: /shop/products - get all product entities where product's name doesn't match RegEx

### <span style="vertical-align: middle"><img src="src/main/resources/icons/tech-stack.png"></span> Technologies:

- `Spring Boot`
- `Hibernate`
- `JPA`
- `Maven`
- `REST`

### <span style="vertical-align: middle"><img src="src/main/resources/icons/instructions.png"></span> Instructions to run the project

- Install IDE to your PC
- Clone the project to your IDE (e.g. IntelliJ IDEA)
- Install MySQL to your PC and create new MySQL connection
- Change parameters in `application.properties`: `spring.datasource.username`, `spring.datasource.password` for yours (they should match `username` and `password` from your MySQL connection)
- Run the project via Main method - `ShopApplication`
- Use `Postman` and send GET/POST request

GET request to retrieve data using Postman:
```
http://localhost:8080/shop/products?nameFilter=FILTER - URL (Change `FILTER` to your RegEx)
```