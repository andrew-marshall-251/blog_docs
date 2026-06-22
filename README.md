# First API Application

## Summary
This repo is unfinished, but it is a basic CRUD API for a discussion form with stateless security
using JWT/refresh token system, I would like to turn it into a finished portfolio project to
apply for Spring Boot roles soon, deploy it, have full documentation, containerize it and
have automated tests.

## Main 2 Problems at the moment

### Problem 1
at the moment the endpoints have some bugs. The main ones being that I forgot to even check the expiry on the
refresh token and then the PATCH methods do not have the same database validation that are in the POST methods,
so I need to extract validation into helper functions and apply to both the POST & PATCH methods throughout the API

### Problem 2
the other problem is that the tests are not automated and depend on a db with assumptions. I can't just run in-memory
because I expect certain resources

### So far I have learned
- http, status codes, serialization/deserialization, Response/Request Entities in Spring Boot
- Building JWTs and refresh tokens, JWTs are safe because of short lifespan, while refresh tokens are safe because you can internally revoke them
- role based access
- soft deleting entities to keep resources holding onto foreign keys
- layered architecture
- http request files for testing
- JPA, hibernate, jakarta validation, SQL
