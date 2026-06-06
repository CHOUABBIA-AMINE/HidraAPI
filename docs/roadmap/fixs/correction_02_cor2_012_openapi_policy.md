# COR2-012 — OpenAPI Domain Schema Annotation Policy

## 1. Task

| Field | Value |
|---|---|
| Roadmap | `docs/roadmap/correction_02.md` |
| Task | `COR2-012` |
| Commit message | `docs(openapi): decide domain schema annotation policy` |
| Scope | Decide whether domain classes carry Swagger/OpenAPI annotations |
| Status | Completed as policy artifact |

---

## 2. Decision

HidraAPI selects:

```text
Option B — API boundary only
```

This means:

```text
Domain layer does not import Swagger/OpenAPI.
API request/response DTOs and application DTOs carry @Schema when they are REST-facing.
Controllers carry @Tag, @Operation, @ApiResponse/@ApiResponses, and @Parameter.
OpenAPI documentation belongs to the API/application boundary, not domain aggregates or value objects.
```

---

## 3. Rationale

HidraAPI follows strict hexagonal architecture and DDD boundaries.

Domain packages must stay independent from transport concerns.

Swagger/OpenAPI annotations document REST contracts. They should not couple domain aggregates, domain entities, domain services, or value objects to HTTP documentation.

This keeps the domain model portable and prevents REST concerns from leaking into core business logic.

---

## 4. Allowed OpenAPI Locations

OpenAPI annotations are allowed in:

```text
src/main/java/dz/sh/hidra/modules/*/api/rest/controller/**
src/main/java/dz/sh/hidra/modules/*/api/rest/request/**
src/main/java/dz/sh/hidra/modules/*/api/rest/response/**
src/main/java/dz/sh/hidra/modules/*/application/dto/**, only when REST-facing
```

Allowed annotations include:

```text
@Tag
@Operation
@Parameter
@Schema
@ApiResponse
@ApiResponses
```

---

## 5. Forbidden OpenAPI Locations

OpenAPI annotations are forbidden in:

```text
src/main/java/dz/sh/hidra/modules/*/domain/**
src/main/java/dz/sh/hidra/kernel/domain/**
```

Forbidden imports:

```text
io.swagger.v3.oas.annotations.*
```

---

## 6. Search Findings

Connector search for Swagger/OpenAPI imports returned API request/response files, not domain packages.

Representative API-boundary hits:

```text
ProductTypeResponse
OrganizationUnitTypeResponse
GeoCoordinateRequest
GeoCoordinateResponse
PipelineSegmentResponse
TopologyTypeReferenceResponse
OrganizationUnitReferenceResponse
OperationalOwnerReferenceResponse
AssignEmployeeToUnitRequest
CreatePipelineAppurtenanceRequest
```

No domain package hit was identified by connector search for:

```text
io.swagger.v3.oas.annotations
```

---

## 7. Architecture Documentation

The decision is documented in:

```text
docs/ARCHITECTURE.md
```

That file defines:

```text
- API-boundary-only OpenAPI policy
- Allowed OpenAPI import locations
- Forbidden OpenAPI import locations
- REST documentation requirements
- Validation commands
```

---

## 8. Validation

Required validation commands:

```bash
grep -R "io.swagger.v3.oas.annotations" src/main/java/dz/sh/hidra/modules/*/domain src/main/java/dz/sh/hidra/kernel/domain || true
grep -R "@Schema" src/main/java/dz/sh/hidra/modules/*/api src/main/java/dz/sh/hidra/modules/*/application/dto || true
mvn -q -DskipTests compile
```

Connector-only execution result:

```text
NOT_RUN_IN_SANDBOX
Reason: repository was inspected through the GitHub connector; no local checkout or Maven execution environment was available in this session.
```

---

## 9. Acceptance Criteria Result

| Criterion | Result |
|---|---|
| The repository has one documented OpenAPI annotation policy | Completed in `docs/ARCHITECTURE.md` |
| The selected policy is enforced by tests or documented validation commands | Completed through documented validation commands; architecture test enforcement remains appropriate for COR2-016 |
| No module follows a conflicting policy | Connector search found OpenAPI imports in API-boundary files, not domain packages |
