# HidraAPI

Hydrocarbon Intelligence for Data, Risk, and Analytics.

HidraAPI is a modular monolith backend for Oil & Gas pipeline operations. It models identity, organization, and topology foundations for managing hydrocarbon transportation assets, reference catalogs, operational ownership, and pipeline-domain data with strict module boundaries.

---

## 1. Project Overview

HidraAPI provides the backend foundation for the Hidra platform.

The product goal is to support hydrocarbon transportation digitalization through clean domain models and reliable APIs for:

```text
identity and access foundations
organizational structure and operational responsibility
pipeline systems, pipelines, facilities, nodes, appurtenances, equipment, and connections
catalog-backed business classifications
trilingual Arabic/French/English business labels
future analytics, workflow, risk, measurement, and operational modules
```

The current repository focuses on the core platform and foundational business modules. It intentionally favors a strict modular monolith over premature microservices.

---

## 2. Architecture Overview

HidraAPI uses:

```text
DDD
Hexagonal architecture
Modular monolith
Spring Boot REST APIs
JPA persistence adapters
Flyway database migrations
OpenAPI documentation at the API boundary
```

The main architecture rule is simple:

```text
Domain owns business rules.
Application owns use cases and ports.
API owns REST contracts.
Infrastructure owns persistence adapters and external technical details.
```

Domain code must not depend on REST, Spring MVC, JPA, platform infrastructure, or foreign module aggregate classes.

OpenAPI annotations are intentionally kept out of domain packages. The documented policy is available in [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

Architecture diagrams:

- [Domain schema](docs/architecture/domain-schema.md)
- [Persistence schema](docs/architecture/persistence-schema.md)

---

## 3. Module Map

Root package:

```text
dz.sh.hidra
```

Main source tree:

```text
src/main/java/dz/sh/hidra
```

Module ownership:

| Package | Owner | Responsibility |
|---|---|---|
| `dz.sh.hidra.kernel` | Shared kernel | Generic DDD/application primitives only. |
| `dz.sh.hidra.platform` | Technical platform | Configuration, exceptions, security context, tenancy, observability, outbox, JPA platform setup. |
| `dz.sh.hidra.modules.identity` | Identity module | Users, roles, permissions, identity references, account lifecycle. |
| `dz.sh.hidra.modules.organization` | Organization module | Employees, organization units, positions, assignments, reporting lines, organization reference catalogs. |
| `dz.sh.hidra.modules.topology` | Topology module | Pipeline systems, pipelines, facilities, nodes, segments, appurtenances, equipment, topology catalogs. |

Allowed cross-module modeling uses references, not aggregate imports.

Examples:

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

Do not introduce these packages:

```text
shared
sharedkernel
common
core
utils
helper
helpers
misc
identityaccess
```

---

## 4. Tech Stack

Primary stack:

```text
Java 21+
Spring Boot 3.x
Spring Web
Spring Validation
Spring Data JPA / Hibernate
Flyway migrations
SpringDoc OpenAPI
Maven
JUnit / architecture tests where present
```

Main runtime layers:

```text
REST controllers
application commands, queries, DTOs, and use-case ports
domain aggregates, entities, value objects, policies, and repository ports
infrastructure persistence entities, mappers, repository adapters, and migrations
```

---

## 5. Getting Started

Clone the repository:

```bash
git clone https://github.com/CHOUABBIA-AMINE/HidraAPI.git
cd HidraAPI
```

Compile:

```bash
mvn -q -DskipTests compile
```

Run tests:

```bash
mvn -q test
```

Run full verification:

```bash
mvn -q clean verify
```

Run the application with the default Spring Boot command:

```bash
mvn spring-boot:run
```

If a roadmap task requires a narrower validation command, follow the task-specific validation section in the relevant roadmap file.

---

## 6. API Documentation

REST APIs live under module API packages such as:

```text
src/main/java/dz/sh/hidra/modules/identity/api/rest
src/main/java/dz/sh/hidra/modules/organization/api/rest
src/main/java/dz/sh/hidra/modules/topology/api/rest
```

OpenAPI rules:

```text
@Tag on controllers
@Operation on endpoint methods
@ApiResponse or @ApiResponses on endpoint methods
@Parameter on path, query, and header parameters
@Schema on REST request and response records
```

Domain models must not import OpenAPI annotations. Domain classes are mapped to REST contracts through application DTOs and REST mappers.

Language-sensitive endpoints may accept:

```text
Accept-Language: ar
Accept-Language: fr
Accept-Language: en
```

REST responses should expose explicit trilingual fields for business labels when full catalog data is returned:

```text
nameAr
nameFr
nameEn
descriptionAr
descriptionFr
descriptionEn
```

---

## 7. Multilingual Convention

Any persisted or API-exposed human-readable business label must be modeled with Arabic, French, and English fields.

Required naming for names:

```text
nameAr
nameFr
nameEn
```

Required naming for descriptions:

```text
descriptionAr
descriptionFr
descriptionEn
```

For titles:

```text
titleAr
titleFr
titleEn
```

Examples of business labels that must be trilingual:

```text
RoleName
OrganizationUnitName
PositionTitle
TopologyMultilingualName
Product type catalog labels
Organization unit type catalog labels
Pipeline names and descriptions
```

Exceptions that remain single-value:

```text
technical codes
identifiers
usernames
email addresses
employee numbers
personal names such as EmployeeFullName
external immutable references
```

---

## 8. Domain Conventions

Use explicit domain modeling.

Aggregates belong in:

```text
src/main/java/dz/sh/hidra/modules/<module>/domain/model
```

Value objects belong in:

```text
src/main/java/dz/sh/hidra/modules/<module>/domain/value
```

Repository ports belong in domain or application ports depending on current module convention. Infrastructure adapters must stay in infrastructure packages.

Business classifications that users see or administrators may manage must be catalog-backed.

Examples:

```text
ProductTypeReference
OrganizationUnitTypeReference
FacilityTypeReference
NodeTypeReference
ConnectionTypeReference
EquipmentTypeReference
PipelineAppurtenanceTypeReference
ValveTypeReference
```

Avoid Java enums or enum-like constant classes for user-facing classifications. Lifecycle/status values may remain enums.

Allowed status enum examples:

```text
UserStatus
RoleStatus
EmploymentStatus
OrganizationUnitStatus
TopologyStatus
```

Reference objects should carry stable identity and language-neutral code only:

```text
id
code
```

Localized labels belong in catalog entries, DTOs, responses, or read models, not in domain reference objects.

---

## 9. Contributing

Contribution rules are documented in [CONTRIBUTING.md](CONTRIBUTING.md).

Core rules:

```text
one roadmap task per branch or PR
use the exact roadmap commit message when executing a roadmap task
preserve module ownership and hexagonal boundaries
do not introduce shared/common/helper/misc packages
do not create compatibility wrappers to replace deleted compatibility wrappers
run the task-specific validation commands when working locally
record the exact reason when validation cannot be run
```

Use the PR checklist in `CONTRIBUTING.md` before opening or merging a change.

---

## 10. Roadmap

Roadmap files are execution memory for the repository.

Primary roadmap files:

- [Correction 02](docs/roadmap/correction_02.md)
- [Correction 01](docs/roadmap/correction_01.md)
- [Kernel roadmap](docs/roadmap/kernel.md)
- [Platform roadmap](docs/roadmap/platform.md)
- [Identity roadmap](docs/roadmap/identity.md)
- [Organization roadmap](docs/roadmap/organization.md)
- [Topology roadmap](docs/roadmap/topology.md)
- [Topology validation checklist](docs/roadmap/topology_validation_checklist.md)

Correction 02 companion artifacts:

- [COR2-007 type enum audit](docs/roadmap/correction_02_cor2_007_type_enum_audit.md)
- [COR2-009 name value object audit](docs/roadmap/correction_02_cor2_009_name_value_object_audit.md)
- [COR2-012 OpenAPI policy](docs/roadmap/correction_02_cor2_012_openapi_policy.md)

Current Correction 02 focus:

```text
catalog-backed user-facing classifications
trilingual business labels
OpenAPI boundary documentation
architecture diagrams
developer documentation
architecture guardrails
```

---

## Useful Validation Commands

Compile without tests:

```bash
mvn -q -DskipTests compile
```

Run tests:

```bash
mvn -q test
```

Run full verification:

```bash
mvn -q clean verify
```

Check for forbidden Swagger imports in domain packages:

```bash
grep -R "io.swagger.v3.oas.annotations" src/main/java/dz/sh/hidra/modules/*/domain src/main/java/dz/sh/hidra/kernel/domain || true
```

Check for deprecated compatibility wrappers:

```bash
grep -R "@Deprecated" src/main/java/dz/sh/hidra/modules src/test/java/dz/sh/hidra/modules || true
```

Check for multilingual fields:

```bash
grep -R "nameAr\|nameFr\|nameEn\|descriptionAr\|descriptionFr\|descriptionEn" src/main/java/dz/sh/hidra/modules src/main/resources/db/migration || true
```

## Security bootstrap

HidraAPI declares an explicit Spring Security configuration so Spring Boot does not create a random generated development password at startup. Local development uses `hidra-admin` with the password supplied by `HIDRA_SECURITY_BOOTSTRAP_PASSWORD`, falling back only in non-production profiles to `hidra-dev-change-me`. Production-like profiles must provide `HIDRA_SECURITY_BOOTSTRAP_USERNAME` and `HIDRA_SECURITY_BOOTSTRAP_PASSWORD` through environment variables or externalized secrets.
