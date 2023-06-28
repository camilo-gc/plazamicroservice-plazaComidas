FROM openjdk:17-jdk-slim
EXPOSE 8091
COPY "./build/libs/plazamicroservice-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java", "-jar", "app.jar"]