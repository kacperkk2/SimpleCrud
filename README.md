# SimpleCrud

Simple REST API for quotes. Quotes consists of quote content, author name and author surname (every field is mandatory).

### To run application:
```
$ mvn clean compile package  
$ java -jar target/simple-crud-0.0.1-SNAPSHOT.jar
```
Aplication runs on port 8080, api url: localhost:8080/api/quotes

### Endpoints:
- POST /api/quotes - add quote object to collection  
- GET /api/quotes - get all quotes  
- GET /api/quotes/{id} - get specific quote  
- PUT /api/quotes/{id} - update quote or add new if quote with specified id does not exists  
- DELETE /apu/quotes/{id} - remove specific quote  

