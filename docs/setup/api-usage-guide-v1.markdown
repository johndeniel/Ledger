# API Usage Guide

| **Field**                | **Details**                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| **Title**                | API Usage Guide                                                            |
| **Version**              | 1.0                                                                        |
| **Date**                 | August 1, 2025                                                             |
| **Author**               | John Deniel Dela Peña                                                      |
| **Document ID**          | SMS-API-001                                                                |
| **Status**               | Draft, Pending Approval                                                    |
| **Location**             | `docs/setup/api-usage-guide.md`                                            |

---

## Overview

The Supplier Management System provides REST APIs at `/api/v1` for managing suppliers, RFQs, and compliance. APIs use JSON payloads, OAuth 2.0 authentication, and are documented in OpenAPI 3.0.

---

## Authentication

- **OAuth 2.0**: Obtain a token via Auth0.
- Include token in headers:
  ```http
  Authorization: Bearer <token>
  ```

---

## Key Endpoints

1. **Create Supplier**:
   ```http
   POST /api/v1/suppliers
   Content-Type: application/json
   ```
   **Payload**:
   ```json
   {
       "name": "SteelCorp",
       "address": "123 Industrial Ave, Chicago, IL 60601",
       "tax_id": "123-45-6789",
       "certifications": ["ISO 9001"],
       "diversity_status": "minority-owned",
       "products": [{"name": "Steel Plate", "type": "steel"}],
       "contacts": [{"name": "John Doe", "email": "john.doe@steelcorp.com"}]
   }
   ```

2. **Get Supplier**:
   ```http
   GET /api/v1/suppliers/{id}
   ```

3. **Create RFQ**:
   ```http
   POST /api/v1/rfqs
   Content-Type: application/json
   ```
   **Payload**:
   ```json
   {
       "supplier_id": "<uuid>",
       "product_id": "<uuid>",
       "material_specs": {"quantity": 10000, "delivery_date": "2025-12-01"}
   }
   ```

4. **Get Compliance Report**:
   ```http
   GET /api/v1/compliance/reports
   ```

---

## Rate Limiting

- Limited to 100 requests per minute per user, enforced via Redis.

---

## Testing

- Use the [Postman Collection](../api/supplier-management/suppliermanagement-postman-collection.json) to test APIs.
- View documentation in [Redoc](../api/supplier-management/redoc.html).

---

## References

- [API Specification](../api/supplier-management/openapi-v1.yaml)
- [Requirements Specification](../specification/supplier-management/supplier-management-specification-v1.0.md)