FROM maven:3.6.3-openjdk-17


WORKDIR /app
VOLUME ["/root/.m2"]

COPY . .
EXPOSE 8761
ENV server.port=8761

CMD  ["mvn", "spring-boot:run"]
