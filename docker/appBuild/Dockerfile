FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy in the source code
COPY ./* /app/

CMD ["java", "-classpath", "/app/hsqldb.jar", "org.hsqldb.server.Server"]
