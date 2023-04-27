# recommendation_service

This API exposes endpoints to manage crypto recommendations

View endpoints by swagger: http://{host:port}/swagger-ui/index.html,

for example http://localhost:8080/swagger-ui/index.html

### fill database

Files with data add to repository. 
Information from files save to database automatically when application startup.

### lunch integration tests

- Run docker-compose.yaml
- Execute in terminal of IntelliJ IDEA **./mvnw liquibase:update**
