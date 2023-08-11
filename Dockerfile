FROM amazoncorretto:17-alpine3.17

# on copie le jar généré
COPY target/trackix-0.0.1-SNAPSHOT.jar /app.jar

# Commande pour exécuter l'application Spring Boot
CMD ["java", "-jar", "/app.jar"]
