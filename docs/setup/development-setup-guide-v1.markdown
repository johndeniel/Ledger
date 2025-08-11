# Development Setup Guide

| **Field**                | **Details**                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| **Title**                | Development Setup Guide                                                    |
| **Version**              | 1.0                                                                        |
| **Date**                 | August 1, 2025                                                             |
| **Author**               | John Deniel Dela Peña                                                      |
| **Document ID**          | SMS-SETUP-001                                                              |
| **Status**               | Draft, Pending Approval                                                    |
| **Location**             | `docs/setup/development-setup-guide-v1.md`                                    |

---

## Prerequisites

- **Java**: JDK 17
- **Maven**: 3.9.6
- **Docker**: Latest
- **PostgreSQL**: 16
- **AWS CLI**: Configured with access keys
- **Git**: Latest
- **Node.js**: For `markdownlint-cli`

---

## Setup Steps

1. **Clone Repository**:
   ```bash
   git clone https://github.com/yourcompany/supplier-management-system.git
   cd supplier-management-system
   ```

2. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

3. **Configure Database**:
   - Start PostgreSQL locally or via Docker:
     ```bash
     docker run -d -p 5432:5432 --name supplier-db -e POSTGRES_DB=supplier -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password postgres:16
     ```
   - Update `src/main/resources/application.yml`:
     ```yaml
     spring:
       datasource:
         url: jdbc:postgresql://localhost:5432/supplier
         username: user
         password: password
     ```

4. **Run Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Validate Markdown**:
   ```bash
   npm install -g markdownlint-cli
   markdownlint docs/**/*.md
   ```

6. **Build Docker Image**:
   ```bash
   docker build -t yourcompany/supplier-management-system .
   ```

---

## Testing

- Run unit and integration tests:
  ```bash
  mvn test
  ```
- Use `Testcontainers` for database simulation.

---

## Deployment

- Push to DockerHub:
  ```bash
  docker login
  docker push yourcompany/supplier-management-system
  ```
- Deploy to AWS ECS via the CI pipeline (`.github/workflows/ci-pipeline.yml`).

---

## References

- [Requirements Specification](../specification/supplier-management/supplier-management-specification-v1.0.md)
- [API Usage Guide](api-usage-guide.md)