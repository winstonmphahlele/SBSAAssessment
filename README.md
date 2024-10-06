## Standarddank Assessment Challenge

Jakarta EE Application with REST, Authentication, and Database Integration

The application exposes REST endpoints with  basic CRUD operations for 

- /users
- /products

and also provides below endpoint for basic authentication to obtain JWT token which is required for the delete operation.

- /auth/login

### Technology
The following list of technologies were used to develop the application from the
requirements

- Jakarta EE 10
- JWT Authentication with basic username and password
- Postgres DB
- Wildfly
- Docker

### How it works
First build the application using maven

	mvn clean install

To start the application you the following docker command 

	docker-compose up --build

To stop and remove containers, images and volumes:

	docker-compose down 

### Accessing the application
The application exposes the above-mentioned endpoints under the default context

    http://localhost:8080/backend/api

There is the src/main/resources/openapi/resources.yaml