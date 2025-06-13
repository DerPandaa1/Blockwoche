FROM openjdk:19-jdk-alpine3.16
WORKDIR /app

# Copy in the source code
COPY ./hsqldb.jar /app/hsqldb.jar
EXPOSE 5000

CMD ["java", "-classpath", "/app/hsqldb.jar", "org.hsqldb.server.Server"]
