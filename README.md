# Distributed Systems, Matrix Multiplication

## Setup:
There should be 8 servers 
1. Clone this repo into each server
2. Execute ``` sudo apt install default-jdk maven ``` on each instance
3. Execute this on client side  ```cd``` into grpc-client and run ```./mvnw clean spring-boot:run``` 
4. Execute the following command on the rest of the instances ```cd``` into grpc-server and run ```./mvnw clean spring-boot:run -Dmaven.test.skip=true```

For uploading a matrix file we use ```<server_public_ip>:8082/upload```
