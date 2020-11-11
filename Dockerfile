FROM openjdk:8
EXPOSE 8080
ADD target/product-microservice.jar product-microservice.jar
ENTRYPOINT ["java", "-jar", "/product-microservice.jar"]