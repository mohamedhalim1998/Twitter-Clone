FROM maven:3.6.3-openjdk-17


WORKDIR /app
VOLUME ["/root/.m2"]

COPY . .
EXPOSE 8081

CMD ["./mvnw", "spring-boot:run"]