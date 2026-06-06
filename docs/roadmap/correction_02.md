# HidraAPI Correction 02 Roadmap

## 1. Document Control

| Field | Value |
|---|---|
| Project | HidraAPI |
| Product | Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Roadmap file | `docs/roadmap/correction_02.md` |
| Scope | Repository-wide correction roadmap after structural/domain audit |
| Root package | `dz.sh.hidra` |
| Source root | `src/main/java/dz/sh/hidra` |
| Test root | `src/test/java/dz/sh/hidra` |
| Resource root | `src/main/resources` |
| Author | Abir MEDJERAB |
| CreatedOn | 2025-06-26 |
| UpdatedOn | 2026-06-06 |
| Status | Ready for AI-agent execution after repository validation |
| Execution mode | One correction task per commit |

---

## 2. Correction 02 Mission

Correction 02 is a repository-wide cleanup and alignment roadmap.

It exists to turn the audit findings into executable implementation work while preserving the HidraAPI architecture:

```text
DDD
Hexagonal architecture
Modular monolith
Strict module boundaries
Trilingual business labels
OpenAPI-first REST contracts
Catalog-backed user-facing classifications
```

Correction 02 focuses on five problems discovered during the audit:

```text
1. Deprecated compatibility wrappers still exist for catalog-backed concepts.
2. Some user-facing type classifications still behave like enums or enum bridges.
3. Some API/domain labels still use single-language fields such as name or description.
4. Swagger/OpenAPI coverage is good in some APIs but still incomplete and inconsistent.
5. README and developer documentation do not yet describe the actual architecture and conventions.
```

This roadmap must be executed after the existing kernel, identity, organization, and topology baselines compile.

---

## 3. Naming Standard

Use:

```text
Roadmap name: correction_02
Roadmap file: docs/roadmap/correction_02.md
Task prefix: COR2-xxx
Commit mode: one task per commit
Commit examples:
  docs(roadmap): add correction 02 roadmap
  refactor(topology): remove product type compatibility wrapper
  refactor(organization): remove organization unit type compatibility wrapper
  feat(topology): complete product type multilingual catalog
```

Do not create:

```text
correction02 package
migration helper package
legacy package
bridge package
shared/common/helper/misc package
identityaccess package
```

Correction 02 is a roadmap and execution plan. It must not introduce a new business module.

---

## 4. Architectural Boundaries

### 4.1 Kernel

The kernel remains the only place for generic DDD and application primitives.

Allowed kernel concerns:

```text
AggregateRoot
Entity
ValueObject
DomainEvent
DomainException
Command
Query
PageRequest
PageResult
ApiResponse
ApiErrorResponse
```

Forbidden in kernel:

```text
ProductType
OrganizationUnitType
Role
User
Employee
Pipeline
Spring controllers
JPA repositories
module-specific catalog rules
```

### 4.2 Platform

The platform remains technical infrastructure.

Allowed platform concerns:

```text
configuration
exception handling
security context
correlation
observability
outbox/event dispatching
JPA configuration
Jackson/OpenAPI configuration
```

Forbidden in platform:

```text
business catalogs
business labels
business workflow rules
module-owned domain services
module-owned use cases
```

### 4.3 Business Modules

Business modules remain the owners of their own domain concepts.

```text
identity     = security identity, users, roles, permissions, permission decisions
organization = employees, organization units, positions, assignments, reporting lines
topology     = pipeline systems, pipelines, topology nodes, facilities, assets, equipment
```

Cross-module relationships must use references, not imports of foreign aggregates.

Correct examples:

```text
identity.domain.value.EmployeeReference
organization.domain.value.IdentityUserReference
topology.domain.value.OrganizationUnitReference
topology.domain.value.OperationalOwnerReference
```

Forbidden examples:

```text
organization.domain -> identity.domain.model.User
topology.domain -> organization.domain.model.OrganizationUnit
identity.domain -> organization.domain.model.Employee
```

---

## 5. Correction Rules

### 5.1 Deprecated and Bridge Code

The application is under active development and is not in production.

Therefore:

```text
Do not preserve deprecated compatibility wrappers.
Do not preserve enum-style bridge APIs.
Do not preserve old valueOf or values methods for deleted enum replacements.
Do not keep overloads only because older callers may use them.
```

Any file marked as:

```text
@Deprecated
legacy
transitional
bridge
compatibility
temporary
migration adapter
kept for backward compatibility
```

must be removed unless the current production build still requires it.

If the build requires it, first update the active code path, then remove the compatibility layer in the same correction sprint.

### 5.2 Catalog Rule

A user-facing domain classification must not remain as a plain enum.

Examples of user-facing classifications:

```text
Product type
Organization unit type
Facility type
Node type
Asset type
Connection type
Position type, if exposed as a catalog
```

Target model:

```text
<Type>Catalog or <Type>CatalogEntry
<Type>Reference
```

A catalog entry must expose at least:

```text
id
code
nameAr
nameFr
nameEn
status, if lifecycle-managed
sortOrder, if displayed in UI lists
createdAt / updatedAt, if persisted
```

A reference object must expose at least:

```text
id
code
```

A reference object must not hardcode Arabic, French, or English labels.

### 5.3 Status Enum Rule

Lifecycle and technical enums may remain enums.

Allowed enum examples:

```text
ACTIVE / INACTIVE / SUSPENDED
DRAFT / PUBLISHED / ARCHIVED
ASC / DESC
SUCCESS / FAILURE
```

These do not require multilingual catalogs unless a business UI explicitly manages them as editable reference data.

### 5.4 Trilingual Label Rule

Any persisted or API-exposed human-readable business label must use Arabic, French, and English as first-class fields.

Required naming:

```text
nameAr
nameFr
nameEn
```

For descriptions:

```text
descriptionAr
descriptionFr
descriptionEn
```

Avoid single-label fields for business concepts:

```text
name
label
title
description
designation
shortName
fullName
abbreviation
category, when used as a display label
type, when used as a display label
```

Exception:

```text
Pure technical codes, identifiers, usernames, email addresses, and immutable external codes stay single-value.
```

### 5.5 Swagger Rule

Every REST-facing class must be OpenAPI documented.

Required:

```text
@Schema on request records
@Schema on response records
@Schema on application DTO records exposed to REST
@Tag on controllers
@Operation on endpoint methods
@ApiResponse / @ApiResponses on endpoint methods
@Parameter on every path, query, and header parameter
```

Domain classes may either:

```text
Option A: receive @Schema annotations if the project chooses domain-visible Swagger documentation.
Option B: remain Swagger-free if the architecture standard is updated to say OpenAPI belongs only to API/application DTOs.
```

Correction 02 requires one explicit decision and then consistency.

---

## 6. Required Preconditions

Before executing any correction task, verify:

```bash
mvn -q -DskipTests compile
```

If the command fails:

```text
Stop.
Record the exact failure.
Do not perform cleanup that hides unrelated compilation failures.
```

Before deleting deprecated code, locate active usages:

```bash
grep -R "ProductType" src/main/java src/test/java || true
grep -R "OrganizationUnitType" src/main/java src/test/java || true
grep -R "@Deprecated" src/main/java src/test/java || true
grep -R "transitional\|legacy\|bridge\|compatibility\|temporary" src/main/java docs README.md || true
```

---

## 7. Commit Plan Overview

| Commit code | Commit message | Purpose |
|---|---|---|
| `COR2-001` | `docs(roadmap): add correction 02 roadmap` | Add this executable correction roadmap |
| `COR2-002` | `chore(repo): inventory deprecated and transitional files` | Create the verified deletion inventory before code changes |
| `COR2-003` | `refactor(topology): remove product type compatibility wrapper` | Delete deprecated `ProductType` wrapper and legacy topology bridges |
| `COR2-004` | `refactor(organization): remove organization unit type compatibility wrapper` | Delete deprecated `OrganizationUnitType` wrapper and legacy organization bridges |
| `COR2-005` | `feat(topology): complete product type catalog model` | Ensure product types are catalog-backed and trilingual |
| `COR2-006` | `feat(organization): complete organization unit type catalog model` | Ensure organization unit types are catalog-backed and trilingual |
| `COR2-007` | `chore(domain): audit remaining user-facing type enums` | Classify remaining `domain/value` type enums and references |
| `COR2-008` | `refactor(api): standardize pipeline multilingual labels` | Replace single-language pipeline API labels with trilingual fields |
| `COR2-009` | `refactor(domain): standardize domain name value objects` | Align name/title/designation value objects with trilingual convention |
| `COR2-010` | `refactor(persistence): standardize multilingual columns` | Align JPA entities and migrations with trilingual label fields |
| `COR2-011` | `docs(openapi): complete REST parameter documentation` | Add missing `@Parameter`, examples, and API response documentation |
| `COR2-012` | `docs(openapi): decide domain schema annotation policy` | Make the domain `@Schema` policy explicit and consistent |
| `COR2-013` | `docs(architecture): add domain and persistence schema diagrams` | Add Mermaid domain and persistence diagrams |
| `COR2-014` | `docs(readme): rewrite project README` | Replace minimal README with full developer onboarding documentation |
| `COR2-015` | `docs(contributing): add contribution and execution rules` | Add `CONTRIBUTING.md` with module and review rules |
| `COR2-016` | `test(architecture): add correction 02 guardrails` | Add/extend architecture tests for deprecated imports and module boundaries |
| `COR2-017` | `docs(roadmap): finalize correction 02 checklist` | Record final validation status and checklist |

---

# 8. Detailed Commit Specifications

## COR2-001 — Add Correction 02 Roadmap

### Commit message

```text
docs(roadmap): add correction 02 roadmap
```

### Description

Create this roadmap file as the execution memory for repository-wide correction work.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `docs/roadmap/correction_02.md` | Repository-wide correction plan |

### Acceptance criteria

- The file exists.
- The file uses one executable task per commit.
- The roadmap does not request creating a new business module.
- The roadmap preserves the modular monolith boundaries.

### Validation

```bash
test -f docs/roadmap/correction_02.md
```

---

## COR2-002 — Inventory Deprecated and Transitional Files

### Commit message

```text
chore(repo): inventory deprecated and transitional files
```

### Description

Create a verified cleanup inventory before deleting code.

Search the full repository for deprecated, transitional, bridge, temporary, and compatibility files or methods.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Update | `docs/roadmap/correction_02.md` | Add actual inventory result and update task status |

### Required search commands

```bash
grep -R "@Deprecated" src/main/java src/test/java || true
grep -R "SuppressWarnings(\"deprecated\")" src/main/java src/test/java || true
grep -R "transitional\|legacy\|bridge\|compatibility\|temporary\|migration adapter" src/main/java src/test/java docs README.md || true
find docs/roadmap -type f | sort
```

### Acceptance criteria

- Every deprecated production file is listed.
- Every deprecated method in active aggregate/model code is listed.
- Every transition document is listed.
- Every item has one of these statuses:

```text
DELETE
REPLACE_FIRST_THEN_DELETE
KEEP_WITH_REASON
```

- `KEEP_WITH_REASON` is allowed only if removing the item breaks active compilation and no replacement exists yet.

### Validation

```bash
mvn -q -DskipTests compile
```

---

## COR2-003 — Remove Topology Product Type Compatibility Wrapper

### Commit message

```text
refactor(topology): remove product type compatibility wrapper
```

### Description

Remove deprecated product type compatibility code from topology.

The old `ProductType` wrapper is a user-facing business classification and must not remain as a pseudo-enum compatibility layer.

### Files to inspect

| File | Expected action |
|---|---|
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductType.java` | Delete |
| `src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductTypeReference.java` | Remove legacy bridge methods if present |
| `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java` | Remove overloads accepting `ProductType` |
| `src/main/java/dz/sh/hidra/modules/topology/**` | Replace any remaining usage with `ProductTypeReference` |
| `src/test/java/dz/sh/hidra/modules/topology/**` | Update tests to use `ProductTypeReference` |

### Acceptance criteria

- `ProductType.java` no longer exists.
- No production code imports or references `ProductType`.
- `Pipeline.create(...)` accepts `ProductTypeReference`, not `ProductType`.
- `Pipeline.restore(...)` accepts `ProductTypeReference`, not `ProductType`.
- `ProductTypeReference` does not expose enum-like `values()`, `valueOf()`, or `from(ProductType)` APIs.
- Tests use explicit catalog reference fixtures.

### Validation

```bash
! test -f src/main/java/dz/sh/hidra/modules/topology/domain/value/ProductType.java
grep -R "ProductType" src/main/java src/test/java || true
mvn -q -DskipTests compile
mvn -q test -Dtest='*Topology*Test'
```

---

## COR2-004 — Remove Organization Unit Type Compatibility Wrapper

### Commit message

```text
refactor(organization): remove organization unit type compatibility wrapper
```

### Description

Remove deprecated organization unit type compatibility code from organization.

`OrganizationUnitType` is a user-facing classification and must be represented by catalog-backed references, not by compatibility constants.

### Files to inspect

| File | Expected action |
|---|---|
| `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitType.java` | Delete |
| `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitTypeReference.java` | Remove legacy bridge methods and hardcoded labels if replaced by catalog |
| `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java` | Remove overloads accepting `OrganizationUnitType` |
| `src/main/java/dz/sh/hidra/modules/organization/**` | Replace remaining usages with `OrganizationUnitTypeReference` |
| `src/test/java/dz/sh/hidra/modules/organization/**` | Update tests to use `OrganizationUnitTypeReference` |

### Acceptance criteria

- `OrganizationUnitType.java` no longer exists.
- No production code imports or references `OrganizationUnitType`.
- `OrganizationUnit.create(...)` accepts `OrganizationUnitTypeReference`, not `OrganizationUnitType`.
- `OrganizationUnit.restore(...)` accepts `OrganizationUnitTypeReference`, not `OrganizationUnitType`.
- Reference objects no longer contain compatibility-only APIs.
- Tests use explicit catalog reference fixtures.

### Validation

```bash
! test -f src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitType.java
grep -R "OrganizationUnitType" src/main/java src/test/java || true
mvn -q -DskipTests compile
mvn -q test -Dtest='*Organization*Test'
```

---

## COR2-005 — Complete Product Type Catalog Model

### Commit message

```text
feat(topology): complete product type catalog model
```

### Description

Complete product type modeling as a multilingual catalog.

Product type is a user-facing classification, so the UI/API must be able to display Arabic, French, and English names without hardcoded enum labels.

### Target model

```text
ProductTypeCatalog or ProductTypeCatalogEntry
ProductTypeReference
ProductTypeCatalogRepository / ProductTypeCatalogPort, if persisted or externally loaded
ProductTypeResponse, if exposed through REST
```

### Files to create or update

| Action | File / Package | Purpose |
|---|---|---|
| Create or update | `topology/domain/model` | Product type catalog entry model if not present |
| Update | `topology/domain/value/ProductTypeReference.java` | Keep only `id` and `code` reference semantics |
| Create or update | `topology/application/dto` | Product type DTO with trilingual names |
| Create or update | `topology/api/rest/response` | Product type response with trilingual names |
| Create or update | `topology/infrastructure/persistence` | Persistence mapping if catalog is stored in DB |
| Create or update | `src/main/resources/db/migration` | Catalog table/seed data if persisted |

### Required catalog fields

```text
id
code
nameAr
nameFr
nameEn
descriptionAr, optional
descriptionFr, optional
descriptionEn, optional
status, if lifecycle-managed
sortOrder, if displayed in UI
createdAt / updatedAt, if persisted
```

### Acceptance criteria

- Product type labels are not hardcoded in `ProductTypeReference`.
- Product type API output exposes trilingual labels.
- Pipeline creation/listing does not require the deleted compatibility wrapper.
- Seeded product types preserve known business codes:

```text
GAS
CRUDE_OIL
CONDENSATE
LPG
REFINED_PRODUCT
MULTIPHASE
UNKNOWN
```

- The catalog supports future additions without Java source changes.

### Validation

```bash
grep -R "enum ProductType\|class ProductType" src/main/java/dz/sh/hidra/modules/topology || true
grep -R "nameAr\|nameFr\|nameEn" src/main/java/dz/sh/hidra/modules/topology src/main/resources/db/migration || true
mvn -q -DskipTests compile
mvn -q test -Dtest='*ProductType*Test,*Pipeline*Test'
```

---

## COR2-006 — Complete Organization Unit Type Catalog Model

### Commit message

```text
feat(organization): complete organization unit type catalog model
```

### Description

Complete organization unit type modeling as a multilingual catalog.

Organization unit type is user-facing because it appears in organizational structure screens and reports.

### Target model

```text
OrganizationUnitTypeCatalog or OrganizationUnitTypeCatalogEntry
OrganizationUnitTypeReference
OrganizationUnitTypeCatalogRepository / OrganizationUnitTypeCatalogPort, if persisted or externally loaded
OrganizationUnitTypeResponse, if exposed through REST
```

### Files to create or update

| Action | File / Package | Purpose |
|---|---|---|
| Create or update | `organization/domain/model` | Organization unit type catalog entry model if not present |
| Update | `organization/domain/value/OrganizationUnitTypeReference.java` | Keep only `id` and `code` reference semantics |
| Create or update | `organization/application/dto` | Organization unit type DTO with trilingual names |
| Create or update | `organization/api/rest/response` | Organization unit type response with trilingual names |
| Create or update | `organization/infrastructure/persistence` | Persistence mapping if catalog is stored in DB |
| Create or update | `src/main/resources/db/migration` | Catalog table/seed data if persisted |

### Required seeded codes

```text
COMPANY
DIVISION
DIRECTION
DEPARTMENT
REGION
AREA
DISTRICT
STATION
TEAM
PROJECT_TEAM
OTHER
```

### Acceptance criteria

- Organization unit type labels are not hardcoded in `OrganizationUnitTypeReference`.
- Organization unit responses expose trilingual type labels or a typed reference plus expandable catalog endpoint.
- `OrganizationUnit` stores a reference, not a deleted compatibility wrapper.
- The catalog supports future additions without Java source changes.

### Validation

```bash
grep -R "enum OrganizationUnitType\|class OrganizationUnitType" src/main/java/dz/sh/hidra/modules/organization || true
grep -R "nameAr\|nameFr\|nameEn" src/main/java/dz/sh/hidra/modules/organization src/main/resources/db/migration || true
mvn -q -DskipTests compile
mvn -q test -Dtest='*OrganizationUnitType*Test,*OrganizationUnit*Test'
```

---

## COR2-007 — Audit Remaining User-Facing Type Enums

### Commit message

```text
chore(domain): audit remaining user-facing type enums
```

### Description

Classify every enum or enum-like type under `domain/value` as one of:

```text
STATUS_ENUM
TECHNICAL_ENUM
TYPE_ENUM_TO_CATALOG
REFERENCE_OBJECT
```

### Files to inspect

```bash
find src/main/java/dz/sh/hidra/modules -path '*/domain/value/*.java' | sort
```

### Required output

Update this roadmap with a table:

| Type | Module | Current kind | Used by | Decision | Replacement |
|---|---|---|---|---|---|

### Classification rule

Keep as enum:

```text
status flags
technical directions
technical result states
technical sort directions
```

Convert to catalog:

```text
business type
asset type
node type
facility type
connection type
position type
classification shown to users
```

### Acceptance criteria

- Every `domain/value` enum or enum-like type is classified.
- Every user-facing type enum has a follow-up task or is added to Sprint 1 scope.
- Status enums are explicitly marked as staying enum.
- Technical enums are explicitly marked as staying enum.

### Validation

```bash
find src/main/java/dz/sh/hidra/modules -path '*/domain/value/*.java' -print | sort
grep -R "enum " src/main/java/dz/sh/hidra/modules/*/domain/value || true
mvn -q -DskipTests compile
```

---

## COR2-008 — Standardize Pipeline Multilingual Labels

### Commit message

```text
refactor(api): standardize pipeline multilingual labels
```

### Description

Replace single-language pipeline labels in REST contracts and DTOs.

Pipeline names and descriptions are human-readable business text and must follow the trilingual convention.

### Files to inspect

| File / Package | Expected work |
|---|---|
| `topology/api/rest/request/CreatePipelineRequest.java` | Replace `name` and `description` with trilingual fields |
| `topology/api/rest/request/UpdatePipelineRequest.java`, if present | Apply same convention |
| `topology/api/rest/response/PipelineResponse.java` | Expose trilingual fields or documented locale projection |
| `topology/application/command` | Update command records |
| `topology/application/dto` | Update DTO records |
| `topology/api/rest/mapper` | Update mapping |
| `topology/domain/model/Pipeline.java` | Align with trilingual value object if needed |
| `topology/infrastructure/persistence` | Align columns if persistence stores labels |

### Required API fields

Preferred full trilingual contract:

```text
nameAr
nameFr
nameEn
descriptionAr
descriptionFr
descriptionEn
```

Alternative accepted only if explicitly documented:

```text
name
```

may appear only in a locale-projected read model where:

```text
source fields remain trilingual
projection language is determined by Accept-Language
OpenAPI documents the projection clearly
```

### Acceptance criteria

- No create/update request for pipeline accepts a single business `name` field.
- No persistence entity stores only a single pipeline display name unless explicitly technical.
- Swagger examples exist for Arabic, French, and English labels.
- Tests validate mapping from request to command to domain.

### Validation

```bash
grep -R "String name\|String description" src/main/java/dz/sh/hidra/modules/topology/api src/main/java/dz/sh/hidra/modules/topology/application || true
grep -R "nameAr\|nameFr\|nameEn" src/main/java/dz/sh/hidra/modules/topology || true
mvn -q -DskipTests compile
mvn -q test -Dtest='*Pipeline*Test,*Topology*Mapper*Test'
```

---

## COR2-009 — Standardize Domain Name Value Objects

### Commit message

```text
refactor(domain): standardize domain name value objects
```

### Description

Audit and standardize domain value objects that represent business labels.

### Files to inspect

```text
src/main/java/dz/sh/hidra/modules/**/domain/value/*Name.java
src/main/java/dz/sh/hidra/modules/**/domain/value/*Title.java
src/main/java/dz/sh/hidra/modules/**/domain/value/*Designation.java
src/main/java/dz/sh/hidra/modules/**/domain/value/*Label.java
src/main/java/dz/sh/hidra/modules/**/domain/value/*Description.java
```

Known examples to inspect:

```text
RoleName
TopologyName
OrganizationUnitName
EmployeeFullName
PositionTitle
```

### Decision rule

Use trilingual value objects for business display names:

```text
record TrilingualName(String nameAr, String nameFr, String nameEn)
```

or module-specific equivalents:

```text
TopologyName(nameAr, nameFr, nameEn)
OrganizationUnitName(nameAr, nameFr, nameEn)
RoleName(nameAr, nameFr, nameEn)
```

Do not convert technical identifiers:

```text
Username
EmailAddress
RoleCode
TopologyCode
EmployeeNumber
```

### Acceptance criteria

- Every label-like value object is classified.
- Business display labels are trilingual.
- Technical values remain single-value.
- Validation requires at least one language when business rules allow incomplete translation, or requires all three when the API requires complete translation.
- Mapping tests cover trilingual fields.

### Validation

```bash
find src/main/java/dz/sh/hidra/modules -path '*/domain/value/*.java' | grep -E 'Name|Title|Designation|Label|Description' || true
grep -R "record .*Name\|class .*Name\|record .*Title\|class .*Title" src/main/java/dz/sh/hidra/modules || true
mvn -q -DskipTests compile
mvn -q test
```

---

## COR2-010 — Standardize Multilingual Persistence Columns

### Commit message

```text
refactor(persistence): standardize multilingual columns
```

### Description

Align persistence entities and migrations with the trilingual domain/API convention.

### Files to inspect

```text
src/main/java/dz/sh/hidra/modules/**/infrastructure/persistence/entity/*.java
src/main/java/dz/sh/hidra/modules/**/infrastructure/persistence/mapper/*.java
src/main/resources/db/migration/*.sql
```

### Required column convention

For names:

```text
name_ar
name_fr
name_en
```

For descriptions:

```text
description_ar
description_fr
description_en
```

### Acceptance criteria

- Persistence entities do not use single `name`, `label`, `title`, or `description` columns for user-facing business labels.
- Mappers convert between trilingual domain value objects and trilingual columns.
- Database migrations create or alter trilingual columns.
- Existing single-column label migrations are replaced or superseded because the project is not in production.

### Validation

```bash
grep -R "name\|label\|title\|description" src/main/java/dz/sh/hidra/modules/*/infrastructure/persistence/entity src/main/resources/db/migration || true
grep -R "name_ar\|name_fr\|name_en\|description_ar\|description_fr\|description_en" src/main/resources/db/migration || true
mvn -q -DskipTests compile
mvn -q test -Dtest='*Persistence*Test,*RepositoryAdapterTest'
```

---

## COR2-011 — Complete REST OpenAPI Parameter Documentation

### Commit message

```text
docs(openapi): complete REST parameter documentation
```

### Description

Complete Swagger/OpenAPI annotations on every REST controller and REST DTO.

### Files to inspect

```text
src/main/java/dz/sh/hidra/modules/**/api/rest/controller/*.java
src/main/java/dz/sh/hidra/modules/**/api/rest/request/*.java
src/main/java/dz/sh/hidra/modules/**/api/rest/response/*.java
src/main/java/dz/sh/hidra/modules/**/application/dto/*.java
```

### Required controller annotations

```text
@Tag on every controller class
@Operation on every endpoint method
@ApiResponse or @ApiResponses on every endpoint method
@Parameter on every path parameter
@Parameter on every query parameter
@Parameter on every header parameter
```

### Required DTO annotations

```text
@Schema(name = "...", description = "...") on every request/response/DTO type
@Schema(description = "...", example = "...") on every record component where practical
@Schema(type = "string", format = "date-time", example = "2026-06-06T12:00:00Z") for timestamps
```

### Acceptance criteria

- No REST query/path/header parameter lacks `@Parameter`.
- Every REST request/response type has class-level `@Schema`.
- Every public record component has field/component-level `@Schema`.
- OpenAPI loads successfully at runtime.

### Validation

```bash
grep -R "@GetMapping\|@PostMapping\|@PutMapping\|@PatchMapping\|@DeleteMapping" src/main/java/dz/sh/hidra/modules/*/api/rest/controller || true
grep -R "@RequestParam\|@PathVariable\|@RequestHeader" src/main/java/dz/sh/hidra/modules/*/api/rest/controller || true
grep -R "@Schema" src/main/java/dz/sh/hidra/modules/*/api/rest src/main/java/dz/sh/hidra/modules/*/application/dto || true
mvn -q -DskipTests compile
```

---

## COR2-012 — Decide Domain Schema Annotation Policy

### Commit message

```text
docs(openapi): decide domain schema annotation policy
```

### Description

Make a single architectural decision about whether domain classes should carry Swagger `@Schema` annotations.

The audit requirement requested `@Schema` on domain model/value classes, but strict hexagonal architecture often keeps OpenAPI annotations out of the domain.

This task must make the decision explicit.

### Option A — Domain annotated

Use if the project wants domain classes to be visible in generated schemas.

Rules:

```text
Domain model/value classes may import io.swagger.v3.oas.annotations.media.Schema.
Every public domain type has @Schema.
Every domain record component has @Schema.
```

### Option B — API boundary only

Preferred for strict hexagonal architecture.

Rules:

```text
Domain layer does not import Swagger/OpenAPI.
API request/response DTOs and application DTOs carry @Schema.
Architecture docs explicitly say OpenAPI belongs to API/application DTOs only.
Audit checklist is updated accordingly.
```

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Update | `docs/roadmap/correction_02.md` | Record selected policy |
| Create or update | `docs/ARCHITECTURE.md` or `ARCHITECTURE.md` | Document OpenAPI boundary policy |
| Update | Architecture tests, if present | Enforce selected policy |

### Acceptance criteria

- The repository has one documented OpenAPI annotation policy.
- The selected policy is enforced by tests or documented validation commands.
- No module follows a conflicting policy.

### Validation

```bash
grep -R "io.swagger.v3.oas.annotations" src/main/java/dz/sh/hidra/modules/*/domain || true
grep -R "@Schema" src/main/java/dz/sh/hidra/modules/*/api src/main/java/dz/sh/hidra/modules/*/application/dto || true
mvn -q -DskipTests compile
```

---

## COR2-013 — Add Domain and Persistence Schema Diagrams

### Commit message

```text
docs(architecture): add domain and persistence schema diagrams
```

### Description

Add diagrams that make the repository understandable for future developers.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create or update | `docs/architecture/domain-schema.md` | Domain class diagrams and classification matrix |
| Create or update | `docs/architecture/persistence-schema.md` | JPA/persistence relationship diagrams |
| Update | `README.md` | Link to diagrams |

### Required diagrams

```text
High-level module diagram
Domain class diagram
Persistence ER diagram
Enum/class conversion map
Request-to-domain-to-persistence trace
```

### Required classification stereotypes

```text
[AGGREGATE_ROOT]
[ENTITY]
[VALUE_OBJECT]
[ENUM]
[CATALOG]
[REFERENCE_OBJECT]
[DTO]
[JPA_ENTITY]
[CONTROLLER]
[REPOSITORY_PORT]
[REPOSITORY_ADAPTER]
[SERVICE]
[USE_CASE]
[POLICY]
```

### Acceptance criteria

- Diagrams are Mermaid-compatible.
- Diagrams distinguish references from composition.
- Diagrams mark catalog migration targets.
- README links to the diagram files.

### Validation

```bash
test -f docs/architecture/domain-schema.md
test -f docs/architecture/persistence-schema.md
grep -R "```mermaid" docs/architecture README.md || true
```

---

## COR2-014 — Rewrite Project README

### Commit message

```text
docs(readme): rewrite project README
```

### Description

Replace the minimal README with a full developer onboarding document.

### Files to update

| Action | File | Purpose |
|---|---|---|
| Update | `README.md` | Full project overview and developer guide |

### Required README sections

```text
1. Project Overview
2. Architecture Overview
3. Module Map
4. Tech Stack
5. Getting Started
6. API Documentation
7. Multilingual Convention
8. Domain Conventions
9. Contributing
10. Roadmap
```

### Content requirements

README must describe:

```text
HidraAPI mission
modular monolith architecture
kernel/platform/module separation
identity module responsibility
organization module responsibility
topology module responsibility
catalog-backed type classifications
trilingual labels
Swagger/OpenAPI access
local build/test commands
roadmap links
```

### Acceptance criteria

- README is useful to a new developer.
- README does not duplicate all roadmap details but links to roadmap files.
- README contains concrete package names and module names.
- README explains that user-facing classifications are catalog-backed.

### Validation

```bash
test -f README.md
grep -n "Project Overview\|Architecture Overview\|Module Map\|Multilingual Convention\|Roadmap" README.md
```

---

## COR2-015 — Add Contributing and Execution Rules

### Commit message

```text
docs(contributing): add contribution and execution rules
```

### Description

Add contributor rules that preserve the architecture and roadmap execution style.

### Files to create or update

| Action | File | Purpose |
|---|---|---|
| Create | `CONTRIBUTING.md` | Contribution rules |
| Update | `README.md` | Link to `CONTRIBUTING.md` |

### Required content

```text
branch naming
commit message format
one roadmap task per commit
module ownership rules
forbidden imports
Java header rule
DDD aggregate/value object rules
OpenAPI documentation rules
trilingual label rules
catalog-backed classification rules
test expectations
PR checklist
```

### Acceptance criteria

- `CONTRIBUTING.md` exists.
- The file contains a PR checklist.
- The file explains one-task-per-commit execution.
- The file warns against creating shared/common/helper/misc packages.

### Validation

```bash
test -f CONTRIBUTING.md
grep -n "one roadmap task per commit\|module ownership\|trilingual\|catalog" CONTRIBUTING.md
```

---

## COR2-016 — Add Correction 02 Architecture Guardrails

### Commit message

```text
test(architecture): add correction 02 guardrails
```

### Description

Add or extend architecture tests so the correction work does not regress.

### Guardrails to enforce

```text
No domain class imports Spring.
No domain class imports JPA.
No domain class imports platform.
No organization domain imports identity domain model.
No topology domain imports organization domain model.
No identityaccess package exists.
No deleted compatibility wrappers are reintroduced.
No ProductType compatibility class exists.
No OrganizationUnitType compatibility class exists.
No domain reference object hardcodes multilingual labels.
```

### Files to create or update

| Action | File / Package | Purpose |
|---|---|---|
| Create or update | `src/test/java/dz/sh/hidra/**/ArchitectureTest.java` | Enforce module and cleanup rules |
| Update | `docs/roadmap/correction_02.md` | Record guardrail status |

### Acceptance criteria

- Architecture tests fail if deleted compatibility classes are reintroduced.
- Architecture tests fail if domain imports Spring/JPA/platform.
- Architecture tests fail if forbidden cross-module imports are introduced.
- Tests are part of normal `mvn test` execution.

### Validation

```bash
mvn -q test -Dtest='*ArchitectureTest'
mvn -q test
```

---

## COR2-017 — Finalize Correction 02 Checklist

### Commit message

```text
docs(roadmap): finalize correction 02 checklist
```

### Description

Finalize the roadmap after all correction tasks have been executed.

### Final checklist

```text
[ ] COR2-001 roadmap file exists
[ ] COR2-002 deprecated/transitional inventory completed
[ ] COR2-003 topology ProductType compatibility wrapper removed
[ ] COR2-004 organization OrganizationUnitType compatibility wrapper removed
[ ] COR2-005 product type catalog completed
[ ] COR2-006 organization unit type catalog completed
[ ] COR2-007 remaining user-facing type enums audited
[ ] COR2-008 pipeline multilingual labels standardized
[ ] COR2-009 domain name value objects standardized
[ ] COR2-010 persistence multilingual columns standardized
[ ] COR2-011 REST OpenAPI parameter documentation completed
[ ] COR2-012 domain/API OpenAPI annotation policy decided
[ ] COR2-013 domain and persistence diagrams added
[ ] COR2-014 README rewritten
[ ] COR2-015 CONTRIBUTING.md added
[ ] COR2-016 architecture guardrails added
[ ] No identityaccess package exists
[ ] No shared/common/helper/misc packages were introduced
[ ] No deprecated compatibility bridge remains without explicit reason
[ ] mvn -q -DskipTests compile passes
[ ] mvn -q test passes or unrelated blocker is recorded
[ ] mvn -q clean verify passes or unrelated blocker is recorded
```

### Validation commands

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

### Completion rule

Mark Correction 02 completed only when:

```text
- Every COR2 task has either been completed or explicitly deferred with reason.
- All deleted compatibility wrappers are absent.
- All catalog-backed classifications have a clear model.
- All multilingual label fields follow the selected convention.
- OpenAPI coverage is consistent with the selected architecture policy.
- README and architecture diagrams are updated.
- Validation commands have passed, or exact unrelated blockers are recorded.
```

---

## 9. Execution Status Table

| Task | Status | Notes |
|---|---|---|
| COR2-001 | Pending | Add this file |
| COR2-002 | Pending | Inventory before deletion |
| COR2-003 | Pending | Topology product type cleanup |
| COR2-004 | Pending | Organization unit type cleanup |
| COR2-005 | Pending | Product type catalog completion |
| COR2-006 | Pending | Organization unit type catalog completion |
| COR2-007 | Pending | Remaining enum audit |
| COR2-008 | Pending | Pipeline multilingual labels |
| COR2-009 | Pending | Domain name value objects |
| COR2-010 | Pending | Persistence multilingual columns |
| COR2-011 | Pending | REST OpenAPI parameters |
| COR2-012 | Pending | Domain/API OpenAPI policy |
| COR2-013 | Pending | Diagrams |
| COR2-014 | Pending | README rewrite |
| COR2-015 | Pending | CONTRIBUTING.md |
| COR2-016 | Pending | Architecture guardrails |
| COR2-017 | Pending | Final checklist |

---

## 10. Notes for AI-Agent Execution

When executing this roadmap:

```text
Read AGENTS.md first.
Read this file second.
Execute exactly one COR2 task at a time.
Do not skip ahead to later tasks.
Do not combine unrelated cleanup with feature work.
Do not create broad helper packages.
Do not introduce compatibility layers to replace deleted compatibility layers.
Always run the task-specific validation commands.
Record blockers exactly.
```

If a task reveals that a later task must be changed, update this roadmap in the current commit only when the change is necessary to keep execution accurate.
