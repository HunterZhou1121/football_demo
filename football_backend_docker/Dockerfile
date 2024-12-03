# Use Amazon Corretto 21 with Maven for build
FROM amazoncorretto:21 AS builder

# Install Maven
RUN yum install -y maven

# Set working directory
WORKDIR /app

# Configure Maven to use Aliyun mirror
RUN mkdir -p /root/.m2 \
    && echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" \
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" \
            xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 \
            https://maven.apache.org/xsd/settings-1.0.0.xsd"> \
            <mirrors> \
                <mirror> \
                    <id>aliyun</id> \
                    <name>Aliyun Maven Mirror</name> \
                    <url>https://maven.aliyun.com/repository/public</url> \
                    <mirrorOf>central</mirrorOf> \
                </mirror> \
            </mirrors> \
        </settings>' > /root/.m2/settings.xml

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use Amazon Corretto 21 for the final image
FROM amazoncorretto:21

WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
