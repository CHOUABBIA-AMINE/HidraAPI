# HidraAPI

Hydrocarbon Intelligence for Data, Risk, and Analytics.

HidraAPI is a modular monolith backend for Oil & Gas pipeline operations. It models
trusted operational data, identity, organization, topology and additional bounded
contexts with strict module boundaries.

---

## 1. Project Overview

HidraAPI provides the backend foundation for the Hidra platform.

The product goal is to support hydrocarbon transportation digitalization through clean
domain models and reliable APIs for:

```text
identity and access foundations
organizational structure and operational responsibility
pipeline topology and visualization
telemetry and operational monitoring
planning, alarms, incidents and leak detection
integrity, assets, custody and HSE
workflow, documents, configuration and notifications
simulation, risk, analytics and reporting
governed external integration
trilingual Arabic/French/English business labels
```

The repository intentionally favors a strict modular monolith over premature
microservices.

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

The main architecture rule is:

```text
Domain owns business rules.
Application owns use cases and ports.
API owns REST contracts.
Infrastructure owns persistence adapters and external technical details.
```

Domain code must not depend on REST, Spring MVC, JPA, platform infrastructure, or foreign
module aggregate classes.

Architecture sources:

- [Architecture index](docs/ARCHITECTURE.md)
- [Macro architecture](docs/architecture/Hidra%20%E2%80%94%20Macro%20Architecture.md)
- [Micro architecture](docs/architecture/Hidra%20%E2%80%94%20Micro%20Architecture.md)
- [Module catalog](docs/architecture/module-catalog.md)
- [Domain schema](docs/architecture/domain-schema.md)
- [Persistence migration inventory](docs/architecture/persistence-schema.md)
- [Coding policy](docs/policy/Coding-policy.md)

---

## 3. Module Map

Root package:

```text
dz.sh.hidra
```

Main source tree:

```text
src/main/java/dz/sh/hidra
├── HidraApplication.java
├── kernel
├── platform
└── modules
```

Top-level ownership:

| Package | Owner | Responsibility |
|---|---|---|
| `dz.sh.hidra.kernel` | Shared kernel | Generic business-neutral DDD/result/validation/pagination primitives. |
| `dz.sh.hidra.platform` | Technical platform | Configuration, security plumbing, persistence setup, messaging/outbox, observability, web and realtime infrastructure. |
| `dz.sh.hidra.modules.*` | Business bounded contexts | Domain, application, API and infrastructure owned by each module. |

The live repository contains 24 business-module roots. Their intended ownership and
maturity caveat are documented in the [module catalog](docs/architecture/module-catalog.md).

Architectural presence does **not** mean production maturity. Before extending a module,
read its roadmap where one exists and inspect its current implementation.

Allowed cross-module modeling uses references/ports/events rather than foreign aggregate
imports.

Forbidden examples:

```text
organization.domain -> identity.domain.model.User
topology.domain -> organization.domain.model.OrganizationUnit
identity.domain -> organization.domain.model.Employee
```

Do not introduce generic dumping-ground packages:

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

Current build baseline:

```text
Java 21
Spring Boot 4.0.7
Spring MVC
Spring Validation
Spring Data JPA / Hibernate
PostgreSQL
Flyway
Spring Security
OAuth2 Resource Server
WebSocket / STOMP
Actuator
Micrometer / Prometheus
SpringDoc OpenAPI
Maven
JUnit
Testcontainers
ArchUnit
```

The `pom.xml` is the dependency/version source of truth.

---

## 5. Getting Started

Clone:

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

Run development profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The repository-level Spring smoke test uses PostgreSQL Testcontainers and therefore needs
a Docker-compatible runtime when it executes.

---

## 6. API Documentation

REST APIs live under module API packages:

```text
src/main/java/dz/sh/hidra/modules/<module>/api/rest
```

OpenAPI rules:

```text
@Tag on controllers
@Operation on endpoint methods
@ApiResponse or @ApiResponses on endpoint methods
@Parameter on path, query, and header parameters where useful
@Schema on REST request and response records
```

Domain models must not import OpenAPI annotations.

Language-sensitive endpoints may accept:

```text
Accept-Language: ar
Accept-Language: fr
Accept-Language: en
```

Persisted/API-exposed user-facing business labels follow the project trilingual policy:

```text
nameAr
nameFr
nameEn
descriptionAr
descriptionFr
descriptionEn
```

---

## 7. Domain Conventions

Aggregates:

```text
src/main/java/dz/sh/hidra/modules/<module>/domain/model
```

Value objects:

```text
src/main/java/dz/sh/hidra/modules/<module>/domain/value
```

Ports belong toward the inside of the architecture; JPA implementations remain in
infrastructure.

User-facing/configurable classifications should be catalog-backed. Lifecycle and
technical state may remain enums when allowed by the coding policy.

---

## 8. Persistence

Flyway migrations live under:

```text
src/main/resources/db/migration
```

The current repository uses date-based migrations such as:

```text
V20260611_001__create_identity_tables.sql
...
V20260611_024__create_reporting_tables.sql
```

See [persistence-schema.md](docs/architecture/persistence-schema.md) for the current
migration inventory.

---

## 9. Testing and Architecture Guardrails

Repository-level tests live under:

```text
src/test/java/dz/sh/hidra
```

The stabilization baseline includes:

```text
HidraApplicationTests
ArchitectureGuardrailTest
```

The architecture test checks framework/layer boundaries without using Spring Boot test
slice annotations. Module-specific behavior tests should be rebuilt against each module's
current contracts under its own roadmap.

---

## 10. Contributing

Contribution rules are documented in [CONTRIBUTING.md](CONTRIBUTING.md).

Core rules:

```text
one roadmap task per branch or PR
exact roadmap commit message
preserve module ownership and hexagonal boundaries
do not introduce shared/common/helper/misc packages
run task-specific validation commands
record exact blockers instead of claiming validation passed
```

---

## 11. Roadmaps

Current roadmap locations:

- [Kernel roadmap](docs/roadmap/kernel.md)
- [Platform roadmap](docs/roadmap/platform.md)
- [Identity roadmap](docs/roadmap/identity.md)
- [Organization roadmap](docs/roadmap/organization.md)
- [Topology roadmap](docs/roadmap/topology.md)
- [Telemetry roadmap](docs/roadmap/telemetry.md)
- [Workflow roadmap](docs/roadmap/workflow.md)
- [Stabilization 02](docs/roadmap/fixs/stabilization_02.md)

Historical/correction material currently stored under `docs/roadmap/fixs/`:

- [Correction 01](docs/roadmap/fixs/correction_01.md)
- [Correction 02](docs/roadmap/fixs/correction_02.md)
- [Correction 02 final checklist](docs/roadmap/fixs/correction_02_final_checklist.md)
- [Correction 02 type-enum audit](docs/roadmap/fixs/correction_02_cor2_007_type_enum_audit.md)
- [Correction 02 name-value-object audit](docs/roadmap/fixs/correction_02_cor2_009_name_value_object_audit.md)
- [Correction 02 OpenAPI policy](docs/roadmap/fixs/correction_02_cor2_012_openapi_policy.md)
- [Stabilization 01](docs/roadmap/fixs/stabilization_01.md)
- [Topology validation checklist](docs/roadmap/fixs/topology_validation_checklist.md)
- [Workflow validation checklist](docs/roadmap/fixs/workflow_validation_checklist.md)

---

## 12. Useful Validation Commands

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify

grep -R "io.swagger.v3.oas.annotations"   src/main/java/dz/sh/hidra/modules/*/domain   src/main/java/dz/sh/hidra/kernel/domain || true

git ls-files bin
find . -name '*.class' -not -path './target/*'
```

Expected repository hygiene:

```text
no tracked bin/ files
no source-controlled .class files
```

---

## 13. Security Bootstrap

HidraAPI declares explicit platform security configuration rather than relying on a
random generated Spring Security development password.

Use environment/externalized secrets for production-like profiles. Development/test
defaults must never be copied into production credentials.
