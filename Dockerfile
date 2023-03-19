FROM openjdk:17
ADD target/realestateapi.jar realestateapi.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","realestateapi.jar"]