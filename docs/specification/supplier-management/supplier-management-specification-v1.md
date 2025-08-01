# Document Information

| **Field**                | **Details**                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| **Title**                | Supplier Management System Requirements Specification                       |
| **Version**              | 1.0                                                                        |
| **Date**                 | August 1, 2025                                                             |
| **Author**               | John Deniel Dela Peña                                                      |
| **Document ID**          | SMS-SRS-001                                                                |
| **Status**               | Draft, Pending Approval                                                    |
| **Location**             | `docs/specification/supplier-management/supplier-management-specification-v1.0.md` |
| **Approval Authority**   | Procurement Director, Information Technology Lead, Compliance Officer       |

---

## Table of Contents

1. [Introduction](#1-introduction)
   - 1.1 [Purpose](#11-purpose)
   - 1.2 [Scope](#12-scope)
   - 1.3 [References](#13-references)
2. [Objectives](#2-objectives)
3. [Compliance](#3-compliance)
4. [Functional Requirements](#4-functional-requirements)
   - 4.1 [Supplier Onboarding](#41-supplier-onboarding)
   - 4.2 [Supplier Profile Management](#42-supplier-profile-management)
   - 4.3 [Request for Quotation Processing](#43-request-for-quotation-processing)
   - 4.4 [Supplier Performance Tracking](#44-supplier-performance-tracking)
   - 4.5 [System Integrations](#45-system-integrations)
   - 4.6 [Analytics and Reporting](#46-analytics-and-reporting)
   - 4.7 [Security and Access Control](#47-security-and-access-control)
5. [Non-Functional Requirements](#5-non-functional-requirements)
6. [Technical Specifications](#6-technical-specifications)
   - 6.1 [System Architecture](#61-system-architecture)
   - 6.2 [Software Framework](#62-software-framework)
   - 6.3 [Database Structure](#63-database-structure)
   - 6.4 [File Storage](#64-file-storage)
   - 6.5 [APIs](#65-apis)
   - 6.6 [Caching](#66-caching)
   - 6.7 [Security](#67-security)
   - 6.8 [Deployment](#68-deployment)
   - 6.9 [Monitoring](#69-monitoring)
   - 6.10 [Logging](#610-logging)
   - 6.11 [Testing](#611-testing)
   - 6.12 [Continuous Integration](#612-continuous-integration)
   - 6.13 [Coding Standards](#613-coding-standards)
   - 6.14 [Documentation](#614-documentation)
7. [Development Process](#7-development-process)
   - 7.1 [Timeline](#71-timeline)
   - 7.2 [Risks and Mitigations](#72-risks-and-mitigations)
8. [Assumptions and Constraints](#8-assumptions-and-constraints)
9. [Stakeholders](#9-stakeholders)
   - 9.1 [User Needs](#91-user-needs)
   - 9.2 [Acceptance Criteria](#92-acceptance-criteria)
   - 9.3 [Dependencies](#93-dependencies)
10. [Document Control](#10-document-control)

---

## 1. Introduction

The **Supplier Management System** delivers robust procurement capabilities, streamlining supply chain operations with enterprise-grade scalability, security, and compliance. Built as a *monolithic Java application* using Spring Boot 3.2, it restricts access to authorized company personnel, ensuring no direct supplier interaction.

### 1.1 Purpose
This *Software Requirements Specification (SRS)* defines the functional and non-functional requirements for the Supplier Management System. It provides a definitive framework for development, testing, deployment, and maintenance, ensuring alignment with organizational objectives and regulatory standards. The document targets stakeholders, including the **Procurement Director**, **Information Technology Lead**, **Compliance Officer**, and development teams.

### 1.2 Scope
The system enables:
- Supplier onboarding
- Profile management
- Request for Quotation (RFQ) processing
- Performance tracking
- Compliance validation
- Analytics
- Integration with enterprise systems (e.g., SAP S/4HANA, Oracle NetSuite)

It provides secure **REST APIs** for frontend integration and adheres to *IEEE 830-1998*, *ISO/IEC/IEEE 29148:2018*, and *ISO 9001:2015* standards.

### 1.3 References
- **API Specification**: [`docs/api/supplier-management/openapi-v1.yaml`](#)
- **System Architecture**: [`docs/architecture/system-architecture.md`](#)
- **Business Process Workflow**: [`docs/business/workflows/marketing/marketing-finance-workflow.md`](#)
- **Compliance Policy**: [`docs/policies/supplier/supplier-compliance-policy-v1.0.md`](#)
- **Standards**:
  - *IEEE 830-1998*: Software Requirements Specifications
  - *ISO/IEC/IEEE 29148:2018*: Systems and Software Engineering — Requirements Engineering
  - *ISO 9001:2015*: Quality Management Systems

---

## 2. Objectives

The Supplier Management System aims to:
- Securely manage **supplier data**, **products**, and **contacts** within a unified application.
- Enable seamless data synchronization with enterprise systems (e.g., *SAP S/4HANA*) through scheduled jobs.
- Provide robust tools for *strategic sourcing*, *performance evaluation*, and *compliance validation*.
- Deliver API responses for **1,000 concurrent requests** in under *2 seconds*.
- Ensure compliance with *GDPR*, *CCPA*, *NIST 800-53*, *ISO 9001:2015*, and *Buy American Act* standards, as detailed in [Section 3](#3-compliance).
- Automate development processes via a **GitHub Actions pipeline**, deploying Docker images to *DockerHub*.

---

## 3. Compliance

The system ensures adherence to regulatory, ethical, and organizational standards, safeguarding data integrity and supplier accountability.

### Regulatory Standards
- Comply with:
  - *GDPR*: General Data Protection Regulation
  - *CCPA*: California Consumer Privacy Act
  - *NIST 800-53*: Security and Privacy Controls
  - *ISO 9001:2015*: Quality Management Systems
  - *Buy American Act*: Prioritize U.S.-made products
- Validate supplier certifications (e.g., *ISO 9001*, *ISO 14001*) via `POST /api/v1/suppliers/{id}/compliance`.
- Verify financial stability using *Dun & Bradstreet APIs* through weekly scheduled jobs.
- Track product sustainability metrics (e.g., carbon footprint) in the `sustainability_metrics` table.
- Flag non-compliant suppliers or products, logging issues in the `audit_logs` table.
- Prioritize diverse suppliers (e.g., minority-owned) in search results per diversity policies.

### Data Protection
- Encrypt sensitive data (e.g., tax IDs, contact emails, product details) using:
  - *AES-256* at rest
  - *TLS 1.3* in transit
- Implement *OAuth 2.0* with *Role-Based Access Control (RBAC)* for roles (procurement, admin, analyst) and enforce *Multi-Factor Authentication (MFA)* via Auth0.
- Mitigate *OWASP Top 10* risks (e.g., input validation, CSRF prevention).
- Restrict API usage to *100 requests per minute per user* using *Redis*.

### Audit and Reporting
- Log all API and compliance actions in the `audit_logs` table with:
  - User IDs
  - Actions
  - Endpoints
  - Timestamps
- Generate compliance reports via `GET /api/v1/compliance/reports` in *JSON* format for *PDF* or *CSV* export.
- Support quarterly audits by exporting logs and metrics to *Confluence* or *ServiceNow*.

*Example*: A Compliance Officer validates a supplier’s *ISO 14001* certification for chemical products, flags non-compliance via `POST /api/v1/suppliers/{id}/compliance`, and generates a report for audit purposes.

---

## 4. Functional Requirements

### 4.1 Supplier Onboarding
- **Purpose**: Enable authorized personnel to register suppliers with validated data.
- **Requirements**:
  1. Create supplier records via `POST /api/v1/suppliers`, capturing:
     - Name
     - Address
     - Tax ID
     - Certifications
     - Diversity status
     - Products
     - Contacts
  2. Validate tax IDs and certifications per [Section 3](#3-compliance).
  3. Store documents (e.g., contracts, up to *10 MB*) in *AWS S3* with *AES-256* encryption.
  4. Notify procurement staff of registration status via *AWS SES* emails.
  5. Log actions (e.g., submission, approval) in the `audit_logs` table.
- *Example*: A Procurement Manager registers a minority-owned supplier with steel products, validated and stored securely.

### 4.2 Supplier Profile Management
- **Purpose**: Facilitate updates and retrieval of supplier profiles, including products and contacts.
- **Requirements**:
  1. Retrieve profiles via `GET /api/v1/suppliers/{id}`, including products and contacts.
  2. Update profiles via:
     - `PUT /api/v1/suppliers/{id}` (full updates)
     - `PATCH /api/v1/suppliers/{id}` (partial updates) for details like contact emails or product types.
  3. Support search by material type (from `products` table) or diversity status.
  4. Synchronize interaction history with *Salesforce CRM* via daily scheduled jobs.
  5. Ensure reliable data updates using *PostgreSQL* transactions.
- *Example*: Staff update a supplier’s contact email and product list, logged in `audit_logs`.

### 4.3 Request for Quotation Processing
- **Purpose**: Streamline RFQ creation, distribution, and quote evaluation.
- **Requirements**:
  1. Create RFQs via `POST /api/v1/rfqs` with:
     - Product details (from `products` table)
     - Quantity
     - Delivery date
  2. Retrieve RFQs via `GET /api/v1/rfqs/{id}`.
  3. Distribute RFQs to supplier contacts (from `contacts` table) via *AWS SES* emails.
  4. Enter quotes (price, lead time, quality) via `POST /api/v1/rfqs/{id}/quotes`.
  5. Rank quotes using a weighted formula:
     - Cost: *40%*
     - Quality: *30%*
     - Delivery time: *30%*
  6. Store contract terms in the `contracts` table.
- *Example*: Staff create an RFQ for *10,000 steel units*, distribute it to contacts, and rank supplier quotes.

### 4.4 Supplier Performance Tracking
- **Purpose**: Monitor supplier performance for products.
- **Requirements**:
  1. Calculate KPIs (e.g., delivery rate, defect rate) via `GET /api/v1/suppliers/{id}/performance`.
  2. Store metrics in the `performance_metrics` table with supplier and product IDs.
  3. Compute scores (0–100) based on:
     - Delivery: *40%*
     - Quality: *30%*
     - Cost: *30%*
  4. Send alerts for scores below *70%* via daily scheduled *AWS SES* emails.
  5. Collect feedback via `POST /api/v1/suppliers/{id}/feedback`.
- *Example*: Staff record a late delivery, triggering a *60% score* and an email alert.

### 4.5 System Integrations
- **Purpose**: Enable data synchronization with enterprise systems.
- **Requirements**:
  1. Synchronize inventory via `POST /api/v1/integrations/scm` and payments via `POST /api/v1/integrations/finance`, referencing products, using daily scheduled jobs.
  2. Implement retry logic for failed API calls (*3 attempts*, *5-second intervals*).
  3. Ensure reliable data updates with *PostgreSQL* rollbacks.
- *Example*: A scheduled job updates *SAP S/4HANA* with product inventory data.

### 4.6 Analytics and Reporting
- **Purpose**: Provide actionable insights into supplier and product performance.
- **Requirements**:
  1. Generate metrics (e.g., total spend, KPI trends) via `GET /api/v1/analytics/suppliers` using daily scheduled jobs.
  2. Track product deliveries using *Google Maps Platform APIs*, cached for performance.
  3. Produce *JSON* reports for *PDF* or *CSV* export.
  4. Cache results in *Redis* for *60 seconds*.
- *Example*: A report shows *80%* of suppliers meet KPIs for steel products, cached for dashboard access.

### 4.7 Security and Access Control
- **Purpose**: Protect supplier, product, and contact data.
- **Requirements**:
  1. Secure access with *OAuth 2.0*, *RBAC*, and *MFA*, as per [Section 3](#3-compliance).
  2. Encrypt data with *AES-256* and *TLS 1.3*.
  3. Log all API requests in the `audit_logs` table.
  4. Restrict API usage to *100 requests per minute per user* using *Redis*.
- *Example*: A secure API request retrieves encrypted product data.

---

## 5. Non-Functional Requirements

| **Category**       | **Requirement**                                                                 |
|--------------------|---------------------------------------------------------------------------------|
| **Performance**    | Process *1,000 concurrent API requests* in under *2 seconds*.                   |
| **Scalability**    | Support *10,000+* supplier, product, and contact records using *AWS Auto Scaling*. |
| **Availability**   | Achieve *99.9% uptime* with *AWS RDS* failover.                                |
| **Security**       | Comply with *GDPR*, *CCPA*, *NIST 800-53*, and *ISO 9001:2015* standards, as per [Section 3](#3-compliance). |
| **Reliability**    | Ensure *100% data accuracy* with reliable database transactions.                |
| **Maintainability**| Deliver modular code with API and code documentation; automate builds via *GitHub Actions*. |

---

## 6. Technical Specifications

### 6.1 System Architecture
- **Design**: Monolithic application built with *Spring Boot 3.2*, deployed on *AWS ECS* with *Auto Scaling*.
- **Purpose**: Ensures simplicity, reliability, and performance for procurement operations.

### 6.2 Software Framework
- **Framework**: *Spring Boot 3.2* with:
  - *Spring Data JPA*: Database access
  - *Spring Security*: Authentication
- **Dependencies**:
  - *Springdoc OpenAPI*: Generates API documentation
  - *Jackson*: Processes JSON data
  - *Hibernate*: Maps data to the database
  - *AWS SDK*: Integrates with *S3*, *SES*, and *KMS*
  - *Testcontainers*: Simulates databases for testing
  - *JUnit 5* and *MockMvc*: Supports unit and API testing
- **Java Packages**:
  - `org.ledger.supplier.profile`: Manages supplier profiles and contact data
  - `org.ledger.supplier.rfqs`: Handles RFQ creation, distribution, and quote evaluation
  - `org.ledger.supplier.compliance`: Validates supplier certifications and regulatory compliance
  - `org.ledger.supplier.performance`: Tracks and computes supplier performance metrics
  - `org.ledger.supplier.integrations`: Manages synchronization with enterprise systems
  - `org.ledger.supplier.analytics`: Generates performance and compliance reports
  - `org.ledger.supplier.security`: Implements authentication and access control

### 6.3 Database Structure
- **Database**: *PostgreSQL 16* for reliable data storage.
- **Tables**:

```sql
CREATE TABLE suppliers (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    tax_id VARCHAR(50),
    diversity_status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id UUID PRIMARY KEY,
    supplier_id UUID REFERENCES suppliers(id),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contacts (
    id UUID PRIMARY KEY,
    supplier_id UUID REFERENCES suppliers(id),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rfqs (
    id UUID PRIMARY KEY,
    supplier_id UUID REFERENCES suppliers(id),
    product_id UUID REFERENCES products(id),
    material_specs JSONB,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE performance_metrics (
    id UUID PRIMARY KEY,
    supplier_id UUID REFERENCES suppliers(id),
    product_id UUID REFERENCES products(id),
    delivery_rate DECIMAL,
    defect_rate DECIMAL,
    score INTEGER,
    recorded_at TIMESTAMP
);

CREATE TABLE contracts (
    id UUID PRIMARY KEY,
    supplier_id UUID REFERENCES suppliers(id),
    product_id UUID REFERENCES products(id),
    terms JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE audit_logs (
    id UUID PRIMARY KEY,
    user_id UUID,
    action VARCHAR(100),
    endpoint VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sustainability_metrics (
    id UUID PRIMARY KEY,
    product_id UUID REFERENCES products(id),
    carbon_footprint DECIMAL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 6.4 File Storage
- Store documents (e.g., contracts, certifications; up to *10 MB*) in *AWS S3* with *AES-256* encryption via *AWS KMS*.

### 6.5 APIs
- Provide *REST APIs* at `/api/v1` with *JSON* payloads, documented using *OpenAPI 3.0* and *HATEOAS*.
- **Example**: `POST /api/v1/suppliers`:

```json
{
    "name": "SteelCorp",
    "address": "123 Industrial Ave, Chicago, IL 60601",
    "tax_id": "123-45-6789",
    "certifications": ["ISO 9001", "ISO 14001"],
    "document_url": "s3://bucket/contract.pdf",
    "diversity_status": "minority-owned",
    "products": [
        {
            "name": "Steel Plate",
            "type": "steel",
            "description": "High-grade steel plate"
        }
    ],
    "contacts": [
        {
            "name": "John Doe",
            "email": "john.doe@steelcorp.com",
            "phone": "+1-555-123-4567"
        }
    ]
}
```

### 6.6 Caching
- Use *Redis 7.2* to cache analytics and performance data for *60 seconds*, enhancing response times.

### 6.7 Security
- Implement *OAuth 2.0*, *AES-256* encryption, and *TLS 1.3*, as per [Section 3](#3-compliance).
- Mitigate *OWASP Top 10* risks (e.g., input validation, CSRF protection).

### 6.8 Deployment
- Build a single *Docker container* via *GitHub Actions*, push to *DockerHub* (`yourcompany/supplier-management-system`), and deploy to *AWS ECS* with *Auto Scaling*.

### 6.9 Monitoring
- Use *Prometheus 2.47* for metrics collection and *Grafana 10.2* for visualization.

### 6.10 Logging
- Log events using *SLF4J* and *Logback* to *AWS CloudWatch* for monitoring.

### 6.11 Testing
- Conduct *unit*, *integration*, and *API tests* with *JUnit 5*, *Testcontainers*, and *MockMvc*, targeting *80% code coverage*.
- Run tests in the *GitHub Actions* pipeline using a *PostgreSQL* container, blocking *DockerHub* pushes on failures.

### 6.12 Continuous Integration
- **Purpose**: Automate building, testing, and deployment.
- **Requirements**:
  1. Implement a *GitHub Actions* workflow at `.github/workflows/ci-pipeline.yml`, triggered on `push` or `pull_request` to `main`.
  2. Build a *Docker image* with the *Spring Boot* application (via *Maven*).
  3. Run tests with *JUnit 5*, *Testcontainers*, and *MockMvc*, ensuring *80% coverage*.
  4. Push images to *DockerHub* using *GitHub Secrets* (`DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`).
  5. Tag images with *Git commit SHA* and `latest`.
  6. Validate Markdown files with `markdownlint-cli` across `docs/specification/`, `docs/api/`, `docs/policies/`, `docs/business/`, `docs/architecture/`, and `docs/setup/`.

### 6.13 Coding Standards
- Adhere to *Oracle Java Coding Conventions*, enforced by *SonarQube* in the CI pipeline.

### 6.14 Documentation
- Provide *JavaDoc* for `org.ledger.supplier.*` packages, covering:
  - `profile`
  - `rfqs`
  - `compliance`
  - `performance`
  - `integrations`
  - `analytics`
  - `security`
- Maintain *OpenAPI 3.0* specification at `docs/api/supplier-management/openapi-v1.yaml`.
- Include a *README* at `docs/setup/readme.md` for setup and CI details.

---

## 7. Development Process

### 7.1 Timeline

| **Phase**                     | **Timeline**           | **Tasks**                                                                 |
|-------------------------------|------------------------|---------------------------------------------------------------------------|
| **Design**                    | August–September 2025 | Design database (`suppliers`, `products`, `contacts`, `sustainability_metrics`), plan APIs, configure security and CI pipeline, define Java packages (`org.ledger.supplier.*`). |
| **Development**               | October–November 2025 | Implement APIs in `org.ledger.supplier.*` packages and scheduled jobs with tests; configure CI pipeline for *DockerHub*. |
| **Integration and Deployment**| December 2025–January 2026 | Test integrations, optimize performance, deploy to *AWS ECS* with *Auto Scaling*. |

### 7.2 Risks and Mitigations

| **Risk**                              | **Mitigation**                                                        |
|---------------------------------------|----------------------------------------------------------------------|
| Performance degradation (*10,000+* records) | Implement *Redis* caching and *PostgreSQL* indexing.                 |
| Package conflicts                     | Use unique package names (`org.ledger.supplier.*`) and modular design. |
| Integration failures                  | Use mock APIs with *Testcontainers* and retry logic (*3 attempts*, *5-second intervals*). |
| Data security breaches                | Apply *AES-256*, *MFA*, and quarterly *OWASP* audits, as per [Section 3](#3-compliance). |
| Delayed batch jobs                    | Optimize scheduled jobs and monitor with *Prometheus*.               |
| CI pipeline failures                  | Monitor *GitHub Actions* logs and use fallback *Docker* builds.      |

---

## 8. Assumptions and Constraints

- **Assumptions**:
  - External APIs (e.g., *Dun & Bradstreet*, *Google Maps*) provide *99% uptime*.
  - Internal systems (e.g., *SAP S/4HANA*) support *REST APIs* with *JSON* payloads.
  - Authorized personnel provide accurate supplier, product, and contact data.
  - Scheduled jobs meet non-real-time performance requirements.
  - *DockerHub* credentials are securely stored in *GitHub Secrets*.
- **Constraints**:
  - Budget: *$100,000* for initial release.
  - Timeline: Completion by *January 28, 2026*.
  - External API usage capped at *10,000 calls/month*.
  - CI pipeline runtime limited to *30 minutes*.

---

## 9. Stakeholders

### 9.1 User Needs

| **Role**               | **Need**                                                                 |
|-----------------------|--------------------------------------------------------------------------|
| **Procurement Manager** | Register validated suppliers, products, and contacts via APIs.            |
| **Procurement Staff**   | Enter email-received quotes for RFQs.                                     |
| **Compliance Officer**  | Ensure supplier and product compliance, as per [Section 3](#3-compliance). |
| **Finance Manager**     | Synchronize product payment data with *QuickBooks* via scheduled jobs.    |
| **Procurement Analyst** | Access supplier and product KPI reports.                                  |
| **Developer**           | Automate builds and deployments via *GitHub Actions* and *DockerHub*, using `org.ledger.supplier.*` packages. |

### 9.2 Acceptance Criteria
- Supplier onboarding completes in under *5 seconds* with valid data.
- RFQs are distributed to contacts with *100% success* via *AWS SES*.
- Performance KPIs return in under *1 second*.
- Compliance checks flag issues and log them in `audit_logs`, as per [Section 3](#3-compliance).
- Analytics reports are accurate and generated daily.
- Integrations with *SAP S/4HANA*, *Oracle NetSuite*, and *QuickBooks* are reliable.
- Audit logs capture all API actions for compliance audits.
- CI pipeline builds and deploys *Docker* images without test failures.

### 9.3 Dependencies
- **Internal**:
  - Supply Chain: *SAP S/4HANA* APIs for inventory synchronization
  - Finance: *Oracle NetSuite* APIs for payment processing
  - Accounting: *QuickBooks* APIs for invoice reconciliation
- **External**:
  - *Dun & Bradstreet* APIs for compliance validation
  - *Google Maps Platform* APIs for delivery tracking
  - *DockerHub* for container storage
- **Teams**: Frontend, procurement, compliance, IT, DevOps

---

## 10. Document Control

| **Field**                | **Details**                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| **Version**              | 1.0                                                                        |
| **Date**                 | August 1, 2025                                                             |
| **Author**               | John Deniel Dela Peña                                                      |
| **Document ID**          | SMS-SRS-001                                                                |
| **Location**             | `docs/specification/supplier-management/supplier-management-specification-v1.0.md` |
| **Status**               | Draft, Pending Approval                                                    |
| **Approval Authority**   | Procurement Director, Information Technology Lead, Compliance Officer       |

**Change Log**:
- v1.0 (August 1, 2025): Initial document by John Deniel Dela Peña
