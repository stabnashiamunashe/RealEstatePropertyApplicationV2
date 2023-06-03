FROM openjdk:17

WORKDIR /app

COPY target/realestateapi.jar /app

CMD ["java", "-jar", "/app/realestateapi.jar"]
