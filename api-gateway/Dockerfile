FROM openjdk:17-jdk-alpine
EXPOSE 8083
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]