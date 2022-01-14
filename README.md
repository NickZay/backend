# Backend 

Backend system written with Spring Boot.

### CURL example
```
curl -vv --user user1:user1Pass -X POST 'http://localhost:8080/registration' \
-H 'Content-Type: application/json' \
--data '{ \
    "username": "Nickzay", \
    "password": "password" \
}'
```
