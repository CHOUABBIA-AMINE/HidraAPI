# HidraAPI Controlled Vocabulary Policy

```text
Commit code    : COR-004
Commit message : docs(architecture): define controlled vocabulary policy
Type           : Documentation
Layer          : Architecture
Module         : architecture
Status         : Active architecture policy
```

## 1. Purpose

This document defines when HidraAPI must use Java enums and when it must use catalog entities for controlled vocabularies.

The rule exists because HidraAPI is an industrial hydrocarbon platform that must support:

```text
multilingual labels
localized descriptions
business taxonomy governance
auditability
configuration without Java redeployment
future extension by business administrators
```

This policy applies to all current and future modules, including:

```text
organization
identity
topology
measurement
operations
flow
risk
analytics
workflow
reporting
notification
```

## 2. Core rule

Use Java enums only for true technical states.

Use catalog entities for business taxonomies, business classifications, user-visible types, and any value that may need localization or configuration.

```text
Technical state        -> Java enum allowed
Business taxonomy      -> Catalog entity required
Localized display name -> Catalog translation required
Configurable values    -> Catalog entity required
```

## 3. Java enums are allowed for technical states

A Java enum is allowed when all of these are true:

```text
the value is stable across deployments
the value is controlled by code, not business configuration
the value is part of a state machine or technical invariant
the value is not a multilingual business label
the value is not expected to be extended by business users
```

Allowed examples:

```text
TopologyStatus
OrganizationUnitStatus
EmploymentStatus
AssignmentStatus
ReportingLineStatus
OutboxEventStatus
AuditAction
SortDirection
```

Typical enum use cases:

```text
ACTIVE / INACTIVE / RETIRED / DECOMMISSIONED lifecycle states
PENDING / PROCESSED / FAILED processing states
ASC / DESC sorting direction
CREATED / UPDATED / DELETED audit action
```

These values are technical or lifecycle states. They are not business catalog records.

## 4. Catalog entities are required for business taxonomies

A catalog entity is required when any of these are true:

```text
the value is visible to users as business terminology
the value needs labels in French, Arabic, English, or another language
the value needs a localized description
the value may be configured, activated, deactivated, sorted, or extended
the value may be managed by business administrators
the value represents an industrial taxonomy rather than an internal state
the value appears in reports, dashboards, dropdowns, forms, API responses, or reference data screens
```

Catalog entities are required for:

```text
facility types
organization unit types
pipeline appurtenance types
valve types
node types
equipment types
product types
connection types
reporting line types
operational scope types
measurement point types
measurement unit families
flow product families
risk category types
inspection types
workflow task types
analytics indicator types
```

## 5. Forbidden enum patterns

Do not create or keep Java enums for business type names such as:

```text
*FacilityType
*PipelineAppurtenanceType
*ValveType
*NodeType
*EquipmentType
*ProductType
*ConnectionType
*OrganizationUnitType
*OperationalScopeType
*ReportingLineType
*MeasurementPointType
*InspectionType
*RiskCategoryType
*WorkflowTaskType
```

Exception: a suffix like `Status`, `State`, or `Direction` may remain an enum only when it represents a true technical lifecycle, state-machine, or internal invariant.

## 6. Required catalog model

Every business taxonomy catalog must have a language-neutral base table and a translation table.

### 6.1 Base catalog fields

Required fields:

```text
id
code
status
sort_order
system_defined
created_at
updated_at
```

Recommended SQL shape:

```sql
id varchar(80) primary key,
code varchar(80) unique not null,
status varchar(40) not null,
sort_order integer not null,
system_defined boolean not null,
created_at timestamp with time zone not null,
updated_at timestamp with time zone not null
```

Field meanings:

| Field | Purpose |
|---|---|
| `id` | Stable technical identifier. |
| `code` | Stable language-neutral business code, for example `COMPRESSION_STATION`. |
| `status` | Technical lifecycle status for the catalog entry. |
| `sort_order` | UI and reporting ordering. |
| `system_defined` | Protects seeded values from unsafe deletion. |
| `created_at` | Audit baseline. |
| `updated_at` | Audit baseline. |

### 6.2 Translation fields

Required fields:

```text
id
<catalog>_id
locale
name
description
created_at
updated_at
```

Recommended SQL shape:

```sql
id varchar(80) primary key,
<catalog>_id varchar(80) not null references <catalog_table>(id),
locale varchar(10) not null,
name varchar(160) not null,
description varchar(500),
created_at timestamp with time zone not null,
updated_at timestamp with time zone not null,
unique(<catalog>_id, locale)
```

Required first locales:

```text
en
fr
ar
```

## 7. Domain modeling rule

Domain assets must not depend on business taxonomy enums.

Instead, use explicit reference value objects.

Examples:

```text
FacilityTypeReference
ProductTypeReference
NodeTypeReference
PipelineAppurtenanceTypeReference
ValveTypeReference
EquipmentTypeReference
ConnectionTypeReference
OrganizationUnitTypeReference
```

A type reference must carry at least:

```text
id
code
```

It may also carry catalog family information when a generic model is used:

```text
catalog
id
code
```

Do not put localized labels inside core domain aggregates unless the label is part of the domain invariant. Localized labels belong in application DTOs and REST responses.

## 8. Application layer rule

Application services must resolve catalog references before creating or updating domain assets.

For create commands, prefer stable codes as input:

```text
facilityTypeCode
productTypeCode
nodeTypeCode
appurtenanceTypeCode
valveTypeCode
equipmentTypeCode
connectionTypeCode
```

The application service must:

```text
resolve code to catalog id/code
reject unknown codes
reject inactive catalog entries when used for new assets
apply domain rules based on stable catalog code
return localized labels through DTOs when requested
```

## 9. REST API rule

REST request DTOs should accept stable type codes, not Java enum values.

Example request shape:

```json
{
  "code": "CS-EAST-01",
  "name": "Compression Station East 01",
  "facilityTypeCode": "COMPRESSION_STATION",
  "productTypeCode": "GAS"
}
```

REST response DTOs should expose both the stable code and the localized label.

Example response shape:

```json
{
  "facilityId": "fac_001",
  "code": "CS-EAST-01",
  "name": "Compression Station East 01",
  "facilityType": {
    "id": "ft_compression_station",
    "code": "COMPRESSION_STATION",
    "label": "Station de compression",
    "locale": "fr"
  }
}
```

Localization source priority:

```text
Accept-Language header
locale query parameter when explicitly designed for list/reference endpoints
application default locale fallback
English fallback when no localized label exists
```

## 10. Database rule

Do not enforce business taxonomies with enum-style `CHECK (...) IN (...)` constraints.

Forbidden example:

```sql
facility_type varchar(80) not null check (facility_type in ('COMPRESSION_STATION', 'TERMINAL'))
```

Required pattern:

```sql
facility_type_id varchar(80) not null references hidra_topology_facility_type(id)
```

Status fields may still use check constraints when they are true technical lifecycle states.

Allowed example:

```sql
status varchar(40) not null check (status in ('ACTIVE', 'INACTIVE', 'RETIRED'))
```

## 11. Module ownership rule

Each bounded context owns its own catalog tables unless a later roadmap explicitly defines a cross-module reference-data bounded context.

Examples:

```text
topology owns topology facility types, node types, valve types, product types
organization owns organization unit types and reporting line types
measurement owns measurement point types and measurement unit taxonomies
risk owns risk category types
workflow owns workflow task types
```

A module may expose catalog values through its API, but other modules must not import its internal catalog persistence or domain implementation.

## 12. Migration rule

When correcting an existing enum-based taxonomy:

```text
1. Add catalog tables and translation tables in an additive migration.
2. Seed catalog rows using current enum codes.
3. Add foreign-key columns to asset tables.
4. Backfill foreign-key columns from existing varchar codes.
5. Update domain/application/persistence/API code to use references.
6. Update tests.
7. Only after code is migrated, drop old varchar type columns and enum-style check constraints.
```

Never edit an already-applied Flyway migration. Add a new migration.

## 13. Test rule

Every catalog-based taxonomy must have tests for:

```text
catalog lookup by code
catalog lookup by id
localized label fallback
inactive catalog rejection when creating new business assets
unknown type rejection
API response localization
migration seed presence
architecture guardrail against enum reintroduction
```

## 14. Architecture guardrail rule

A future architecture test must fail when new business taxonomy enums are introduced.

The test should reject enum classes matching names such as:

```text
*FacilityType
*PipelineAppurtenanceType
*ValveType
*NodeType
*EquipmentType
*ProductType
*ConnectionType
*OrganizationUnitType
*OperationalScopeType
*ReportingLineType
```

The test may allow enums matching:

```text
*Status
*State
*Direction
```

only if they are lifecycle, state-machine, or technical concepts.

## 15. Current correction impact

This policy confirms that these current concepts must be corrected from Java enums to catalog entities:

```text
FacilityType
PipelineAppurtenanceType
ValveType
NodeType
EquipmentType
ConnectionType
ProductType
OrganizationUnitType
```

The correction must be completed before starting measurement implementation, because measurement will reference topology and must not inherit enum-based, non-localizable taxonomy contracts.

## 16. Acceptance criteria

COR-004 is accepted when:

```text
docs/architecture/controlled-vocabulary-policy.md exists
the file explains enum-vs-catalog decision rules
the file lists allowed enum categories
the file lists catalog-required business taxonomy categories
the file includes multilingual translation requirements
the file includes database rules against enum-style business CHECK constraints
the file includes REST request/response rules
the file includes migration strategy
the file includes test and architecture guardrail expectations
no Java production code is modified
no test Java code is modified
no migration file is modified
```

## 17. Next correction task

After COR-004, execute:

```text
COR-005 — db(topology): add topology type catalog tables
```
