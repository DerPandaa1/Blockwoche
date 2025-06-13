FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy in the source code
COPY ./* /app/

CMD ["java", "-jar", "/app/benchmark.jar", "-h","db","-u","DBADMIN","-w","UG6bF9TDNEnyK3aagMmV","-o","/output"]
