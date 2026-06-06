# HidraAPI Architecture

## 1. Architecture Style

HidraAPI follows a strict modular monolith architecture.

The architectural baseline is:

```text
DDD
Hexagonal architecture
Strict module boundaries
API/application boundary DTOs
Infrastructure adapters behind ports
Catalog-backed user-facing classifications
Trilingual business labels
```

Business modules own their domains independently:

```text
identity      = users, roles, permissions, identity references
organization  = employees, organization units, positions, assignments, reporting lines
topology      = pipeline systems, pipelines, facilities, nodes, segments, connections, equipment
```

The `kernel` package owns reusable DDD/application primitives only.

The `platform` package owns technical infrastructure only.

---

## 2. OpenAPI Annotation Policy

### Decision

HidraAPI uses **Option B — API boundary only** for Swagger/OpenAPI annotations.

```text
Domain layer does not import Swagger/OpenAPI.
API request/response DTOs and application DTOs carry OpenAPI annotations when they are REST-facing.
OpenAPI documentation belongs to REST and application boundary contracts, not domain models.
```

### Rationale

Domain code must stay independent from transport concerns.

Swagger annotations are REST documentation concerns. Adding them to domain aggregates or value objects would couple the domain model to HTTP/API documentation and weaken the hexagonal boundary.

### Allowed imports

Allowed in API REST packages:

```text
io.swagger.v3.oas.annotations.Operation
io.swagger.v3.oas.annotations.Parameter
io.swagger.v3.oas.annotations.media.Schema
io.swagger.v3.oas.annotations.responses.ApiResponse
io.swagger.v3.oas.annotations.responses.ApiResponses
io.swagger.v3.oas.annotations.tags.Tag
```

Allowed in REST-facing application DTO packages when a DTO is directly exposed through REST:

```text
io.swagger.v3.oas.annotations.media.Schema
```

### Forbidden imports

Forbidden in all domain packages:

```text
src/main/java/dz/sh/hidra/modules/*/domain/** -> io.swagger.v3.oas.annotations.*
src/main/java/dz/sh/hidra/kernel/domain/** -> io.swagger.v3.oas.annotations.*
```

Domain packages must not import:

```text
Spring MVC
JPA
OpenAPI / Swagger
platform infrastructure
foreign module aggregate classes
```

---

## 3. REST Documentation Requirements

Every REST controller must use:

```text
@Tag on the controller class
@Operation on endpoint methods
@ApiResponse or @ApiResponses on endpoint methods
@Parameter on every path parameter
@Parameter on every query parameter
@Parameter on every header parameter
```

Every REST request/response record must use:

```text
@Schema(name = "...", description = "...") on the record type
@Schema(description = "...", example = "...") on public record components where practical
@Schema(type = "string", format = "date-time", example = "2026-06-06T12:00:00Z") for timestamps
```

Application DTOs should receive `@Schema` only when they are directly exposed through REST or reused as REST boundary payloads.

---

## 4. Validation Commands

Use these commands to enforce the OpenAPI boundary policy:

```bash
grep -R "io.swagger.v3.oas.annotations" src/main/java/dz/sh/hidra/modules/*/domain src/main/java/dz/sh/hidra/kernel/domain || true
grep -R "@Schema" src/main/java/dz/sh/hidra/modules/*/api src/main/java/dz/sh/hidra/modules/*/application/dto || true
mvn -q -DskipTests compile
```

Expected result:

```text
No Swagger/OpenAPI imports in domain packages.
Swagger/OpenAPI annotations present in REST request/response/controller contracts.
Compilation succeeds.
```
