# Backend 

Backend system written with Spring Boot.

### CURL example
```
curl -X POST 'localhost:8080/registration' \
-H 'Content-Type: application/json' \
--data-raw '{
    "username": "Nickzay",
    "email": "zaitsev.ni@phystech.edu",
    "password": "password"
}'
```
