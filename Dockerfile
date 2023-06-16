FROM openjdk:17-jdk-slim
EXPOSE 8091
COPY "./build/libs/plazamicroservice-1.0.0-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java", "-jar", "app.jar"]