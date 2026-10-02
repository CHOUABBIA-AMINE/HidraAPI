# HidraAPI Persistence Schema

## 1. Purpose

This document records a high-level persistence view for HidraAPI.

It focuses on the main JPA tables and relationships used by:

```text
identity
organization
topology
catalog reference data
```

The diagram is architectural. The exact schema source of truth remains the JPA entities and Flyway migrations.

---

## 2. Persistence ER Diagram

```mermaid
erDiagram
    HIDRA_USERS {
      varchar id PK
      varchar username
      varchar email
      varchar status
      varchar employee_reference_id
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_ROLES {
      varchar id PK
      varchar code UK
      varchar name_ar
      varchar name_fr
      varchar name_en
      varchar status
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_USER_ROLE_ASSIGNMENT {
      varchar user_id FK
      varchar role_id FK
      varchar role_code
      timestamptz assigned_at
    }

    HIDRA_ROLE_PERMISSION_ASSIGNMENT {
      varchar role_id FK
      varchar permission_id FK
      varchar permission_code
      timestamptz assigned_at
    }

    HIDRA_EMPLOYEE {
      varchar id PK
      varchar employee_number UK
      varchar full_name
      varchar email
      varchar status
      varchar identity_user_reference
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_ORG_UNIT {
      varchar id PK
      varchar code UK
      varchar name_ar
      varchar name_fr
      varchar name_en
      varchar status
      varchar unit_type_id FK
      varchar parent_id FK
      varchar operational_scope_type
      varchar operational_scope_id
      varchar operational_scope_code
      varchar operational_scope_name
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_ORG_UNIT_TYPE {
      varchar id PK
      varchar code UK
      varchar status
      integer sort_order
      boolean system_defined
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_ORG_UNIT_TYPE_TRANSLATION {
      varchar id PK
      varchar unit_type_id FK
      varchar locale
      varchar name
      varchar description
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_POSITION {
      varchar id PK
      varchar code UK
      varchar title_ar
      varchar title_fr
      varchar title_en
      varchar description_ar
      varchar description_fr
      varchar description_en
      boolean active
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_PIPELINE_SYSTEM {
      varchar id PK
      varchar code UK
      varchar name_ar
      varchar name_fr
      varchar name_en
      varchar description
      varchar product_type_id FK
      varchar status
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_PIPELINE {
      varchar id PK
      varchar pipeline_system_id FK
      varchar code UK
      varchar name_ar
      varchar name_fr
      varchar name_en
      varchar description_ar
      varchar description_fr
      varchar description_en
      varchar product_type_id FK
      decimal nominal_diameter
      decimal design_length
      varchar status
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_FACILITY {
      varchar id PK
      varchar code UK
      varchar name
      varchar facility_type_id FK
      varchar product_type_id FK
      varchar status
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_TOPOLOGY_NODE {
      varchar id PK
      varchar code UK
      varchar name
      varchar node_type_id FK
      varchar facility_id FK
      varchar pipeline_appurtenance_id FK
      varchar status
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_PIPELINE_APPURTENANCE {
      varchar id PK
      varchar pipeline_id FK
      varchar code UK
      varchar name
      varchar appurtenance_type_id FK
      varchar valve_type_id FK
      varchar status
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_TOPOLOGY_CONNECTION {
      varchar id PK
      varchar source_node_id FK
      varchar target_node_id FK
      varchar connection_type_id FK
      varchar status
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_EQUIPMENT {
      varchar id PK
      varchar code UK
      varchar equipment_type_id FK
      varchar valve_type_id FK
      varchar status
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_TOPOLOGY_TYPE_CATALOG {
      varchar id PK
      varchar type_family
      varchar code
      varchar status
      integer sort_order
      boolean system_defined
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_TOPOLOGY_TYPE_TRANSLATION {
      varchar id PK
      varchar type_id FK
      varchar locale
      varchar name
      varchar description
      timestamptz created_at
      timestamptz updated_at
    }

    HIDRA_USERS ||--o{ HIDRA_USER_ROLE_ASSIGNMENT : owns
    HIDRA_ROLES ||--o{ HIDRA_USER_ROLE_ASSIGNMENT : assigned
    HIDRA_ROLES ||--o{ HIDRA_ROLE_PERMISSION_ASSIGNMENT : grants

    HIDRA_ORG_UNIT_TYPE ||--o{ HIDRA_ORG_UNIT_TYPE_TRANSLATION : has
    HIDRA_ORG_UNIT_TYPE ||--o{ HIDRA_ORG_UNIT : classifies
    HIDRA_ORG_UNIT ||--o{ HIDRA_ORG_UNIT : parent

    HIDRA_PIPELINE_SYSTEM ||--o{ HIDRA_PIPELINE : contains
    HIDRA_TOPOLOGY_TYPE_CATALOG ||--o{ HIDRA_TOPOLOGY_TYPE_TRANSLATION : has
    HIDRA_TOPOLOGY_TYPE_CATALOG ||--o{ HIDRA_PIPELINE_SYSTEM : product_type
    HIDRA_TOPOLOGY_TYPE_CATALOG ||--o{ HIDRA_PIPELINE : product_type
    HIDRA_TOPOLOGY_TYPE_CATALOG ||--o{ HIDRA_FACILITY : facility_or_product_type
    HIDRA_TOPOLOGY_TYPE_CATALOG ||--o{ HIDRA_TOPOLOGY_NODE : node_type
    HIDRA_TOPOLOGY_TYPE_CATALOG ||--o{ HIDRA_PIPELINE_APPURTENANCE : appurtenance_or_valve_type
    HIDRA_TOPOLOGY_TYPE_CATALOG ||--o{ HIDRA_TOPOLOGY_CONNECTION : connection_type
    HIDRA_TOPOLOGY_TYPE_CATALOG ||--o{ HIDRA_EQUIPMENT : equipment_or_valve_type

    HIDRA_PIPELINE ||--o{ HIDRA_PIPELINE_APPURTENANCE : contains
    HIDRA_FACILITY ||--o{ HIDRA_TOPOLOGY_NODE : hosts
    HIDRA_PIPELINE_APPURTENANCE ||--o{ HIDRA_TOPOLOGY_NODE : hosts
    HIDRA_TOPOLOGY_NODE ||--o{ HIDRA_TOPOLOGY_CONNECTION : source
    HIDRA_TOPOLOGY_NODE ||--o{ HIDRA_TOPOLOGY_CONNECTION : target
```

---

## 3. Multilingual Persistence Convention

Business labels use explicit multilingual columns.

Names:

```text
name_ar
name_fr
name_en
```

Titles:

```text
title_ar
title_fr
title_en
```

Descriptions:

```text
description_ar
description_fr
description_en
```

Single-value exceptions:

```text
technical codes
ids
usernames
email addresses
employee full names
external immutable reference names
```

---

## 4. Catalog Persistence Convention

User-facing type classifications are persisted as catalog tables or catalog entries.

The target catalog structure is:

```text
catalog table:
  id
  code
  status
  sort_order
  system_defined
  created_at
  updated_at

translation table:
  id
  catalog_entry_id
  locale
  name
  description
  created_at
  updated_at
```

Reference objects in domain code carry only:

```text
id
code
```

Labels are resolved through catalog application/API projections, not hardcoded in reference objects.

---

## 5. Request-to-Persistence Trace

```mermaid
sequenceDiagram
    participant Client
    participant Rest as REST Request
    participant Command as Application Command
    participant Aggregate as Domain Aggregate
    participant Mapper as Persistence Mapper
    participant Entity as JPA Entity
    participant DB as Database

    Client->>Rest: CreatePipelineRequest
    Rest->>Command: CreatePipelineCommand
    Command->>Aggregate: Pipeline.create
    Aggregate->>Mapper: Pipeline
    Mapper->>Entity: PipelineJpaEntity
    Entity->>DB: hidra_pipeline row
    DB-->>Entity: persisted row
    Entity-->>Mapper: entity
    Mapper-->>Aggregate: DTO / domain projection
```

---

## 6. Notes

```text
- Flyway migrations are the database schema source of truth.
- JPA entities mirror the active schema.
- Domain aggregates do not import JPA.
- Domain references store catalog ids/codes; translated labels live in catalog tables or API DTOs.
- Pipeline label persistence is multilingual after COR2-008.
- Role, organization unit, and position label persistence is multilingual after COR2-010.
```
