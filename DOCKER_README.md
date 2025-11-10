# Feedback Analytics Consumer - Docker Setup

This Spring Boot application is containerized and ready to be deployed using Docker and Docker Compose.

## Prerequisites

- Docker and Docker Compose installed on your system
- Java 21 (for local development)

## Quick Start

### Using Docker Compose

1. **Start the entire stack** (Kafka + your application):
   ```bash
   docker-compose up -d
   ```

2. **View logs**:
   ```bash
   # View all services
   docker-compose logs -f
   
   # View just your application
   docker-compose logs -f feedback-analytics-consumer
   ```

3. **Check health**:
   ```bash
   curl http://localhost:8081/actuator/health
   ```

4. **Stop the stack**:
   ```bash
   docker-compose down
   ```

### Building Just the Application Container

If you want to build just your application container:

```bash
cd feedback-analytics-consumer
docker build -t feedback-analytics-consumer .
```

### Running with Another Docker Compose

Since you mentioned this will be spun up with another program's docker-compose, you can:

1. **Copy the service definition** from the provided `docker-compose.yml` into your main compose file
2. **Adjust the build context** path as needed
3. **Modify environment variables** and dependencies as required

Example service definition to add to another docker-compose.yml:

```yaml
services:
  feedback-analytics-consumer:
    build:
      context: ./path/to/feedback-analytics-consumer
      dockerfile: Dockerfile
    ports:
      - "8081:8081"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:9092
    depends_on:
      - kafka  # adjust based on your kafka service name
```

## Configuration

### Environment Variables

- `SPRING_PROFILES_ACTIVE`: Set to `docker` to use Docker-specific configuration
- `SPRING_KAFKA_BOOTSTRAP_SERVERS`: Kafka broker addresses (default: `kafka:9092`)

### Ports

- **Application**: 8081
- **Kafka**: 9092
- **Zookeeper**: 2181

## Development

For local development without Docker:

```bash
cd feedback-analytics-consumer
./mvnw spring-boot:run
```

## Monitoring

The application includes Spring Boot Actuator for monitoring:

- Health check: `http://localhost:8081/actuator/health`
- Info endpoint: `http://localhost:8081/actuator/info`

## File Structure

```
├── feedback-analytics-consumer/
│   ├── Dockerfile                 # Container definition
│   ├── .dockerignore             # Files to exclude from build
│   └── src/main/resources/
│       ├── application.properties           # Default config
│       └── application-docker.properties    # Docker-specific config
└── docker-compose.yml           # Full stack setup with Kafka
```