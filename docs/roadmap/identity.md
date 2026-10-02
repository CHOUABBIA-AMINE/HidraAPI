# HidraAPI Identity Roadmap — Recovered Execution Memory

## 1. Document control

| Field | Value |
|---|---|
| Project | HidraAPI |
| Product | Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Roadmap file | `docs/roadmap/identity.md` |
| Module | `identity` |
| Root package | `dz.sh.hidra.modules.identity` |
| API root | `/api/v1/identity` |
| Database prefix | `hidra_identity_*` |
| Author | Abir MEDJERAB |
| CreatedOn | 2025-06-26 |
| Recovery baseline | `c8947feab934fe95c7eaff3c863afaaba73ddb8e` |
| Recovery date | 2026-09-08 |
| Status | Recovered execution memory; live validation pending |

---

## 2. Recovery record

The live `main` branch contained only the ID-021 final-checklist fragment. That fragment
explicitly said it should be merged into the full roadmap rather than replace it.

Git history shows that the complete pre-ID-021 roadmap existed in parent commit:

```text
761d759f53ba4f5b23eb7df3857662d2d24b87b8
```

The ID-021 replacement commit was:

```text
59f59bc8e3c358c41d32bacf0a33f3fa7b3f9a37
```

This recovered file preserves the original roadmap's mission, boundary rules, task
sequence, test plan, and final checklist while adding a separate live-v0.2.0 status
section. It intentionally does not pretend that the 2026-05 implementation tree still
matches the evolved 2026-06 source one-for-one.

---

## 3. Identity mission

The `identity` module owns business identity and access meaning:

```text
users
roles
permissions
authorities/access policy
identity lifecycle
permission evaluation
role/permission assignments
```

It does not own technical Spring Security plumbing.

```text
platform.security = technical authentication/security integration
modules.identity  = business identity/access model
```

It does not own employee or organizational hierarchy.

```text
identity.User          = security/login identity
organization.Employee  = operational person
```

Cross-module links must use stable references rather than importing foreign aggregates.

---

## 4. Naming and boundary rules

Canonical identity names:

```text
Module name : identity
Package     : dz.sh.hidra.modules.identity
API path    : /api/v1/identity
DB prefix   : hidra_identity_*
```

Forbidden replacement module/package names:

```text
identityaccess
auth
iam
users
account
```

The word `security` may exist only in its technical platform ownership context; it must
not replace the Identity business module.

Allowed dependency direction:

```text
identity.api -> identity.application
identity.application -> identity.domain
identity.infrastructure -> identity.application ports
identity.infrastructure -> identity.domain mappings
identity.domain -> kernel / Java standard library
```

Forbidden:

```text
identity.domain -> Spring
identity.domain -> JPA/Hibernate
identity.domain -> identity.api
identity.domain -> identity.infrastructure
identity.application -> identity.api
identity.application -> identity.infrastructure
identity.api -> identity.infrastructure
identity.domain -> organization aggregate classes
```

---

## 5. Permission model standard

Permission codes use:

```text
<context>:<resource>:<action>
```

Examples:

```text
identity:user:create
identity:user:activate
identity:role:assign
identity:permission:grant
telemetry:flow-reading:approve
```

Rules:

```text
exactly three non-empty parts
lower-case business codes
hyphen allowed inside multi-word parts
authorization must not be reduced to hard-coded role-name checks
```

---

## 6. Historical roadmap task sequence

The original roadmap defined the following mandatory execution order:

| Code | Commit message | Historical purpose |
|---|---|---|
| ID-001 | `docs(identity): add identity roadmap` | Create execution memory |
| ID-002 | `chore(identity): add identity package skeleton` | Production package boundaries |
| ID-003 | `feat(identity): add identity domain value objects` | IDs, username, email, codes, names, statuses, references |
| ID-004 | `feat(identity): add permission domain model and policy` | Permission model and code policy |
| ID-005 | `feat(identity): add role domain model` | Role aggregate and permission assignment |
| ID-006 | `feat(identity): add user domain model` | User aggregate and role assignment |
| ID-007 | `feat(identity): add identity domain exceptions` | Domain-specific failures |
| ID-008 | `feat(identity): add identity domain events` | User/role/permission events |
| ID-009 | `feat(identity): add identity domain policies and services` | Assignment/evaluation/SoD policies |
| ID-010 | `feat(identity): add application commands and queries` | Framework-neutral use-case inputs |
| ID-011 | `feat(identity): add application ports and DTOs` | Inbound/outbound contracts |
| ID-012 | `feat(identity): add application services` | Use-case orchestration |
| ID-013 | `feat(identity): add persistence entities and repositories` | JPA adapters and migration |
| ID-014 | `feat(identity): add infrastructure adapters and configuration` | Password/event/configuration adapters |
| ID-015 | `feat(identity): add REST API contracts and controllers` | REST boundary |
| ID-016 | `test(identity): add identity domain tests` | Domain/value/policy tests |
| ID-017 | `test(identity): add identity application tests` | Application service tests |
| ID-018 | `test(identity): add identity persistence tests` | Persistence adapter tests |
| ID-019 | `test(identity): add identity API tests` | Controller/API tests |
| ID-020 | `test(identity): add identity architecture guardrail` | ArchUnit boundary checks |
| ID-021 | `docs(identity): finalize identity checklist` | Final execution status |

---

## 7. Historical test intent

The original test plan required:

```text
domain/value validation tests
User/Role lifecycle tests
permission and assignment policy tests
application service tests
persistence adapter tests
REST controller tests
IdentityArchitectureTest
```

The current live `main` baseline at `c8947feab934fe95c7eaff3c863afaaba73ddb8e` has **no `src/test` tree**.
Therefore historical completion claims cannot be treated as current executable evidence.

STB2-006 restores only repository-level smoke/architecture coverage. Rebuilding detailed
Identity behavior tests must be done against the evolved current Identity APIs and domain
model, not by reintroducing obsolete Spring Boot 3-era test files.

---

## 8. Live v0.2.0 divergence from the original v1 roadmap

Verified live-state differences include:

```text
Spring Boot parent is 4.0.7.
Identity production code is broader than the original User/Role/Permission v1 tree.
Identity contains current authorization/provider/session/grant concepts.
Current Flyway identity migration is:
  V20260611_001__create_identity_tables.sql
rather than the historical planned:
  V010__create_identity_tables.sql
The current source root exists at:
  src/main/java/dz/sh/hidra/modules/identity
The live repository has no src/test directory.
ArchUnit dependency is present in pom.xml.
```

The date-based migration is the current repository source of truth. Do not rename it back
to the historical V010 name merely to match the old roadmap.

---

## 9. Documentation and API rules retained from the original roadmap

Production Identity Java types must retain the canonical HidraAPI header.

Domain:

```text
no Bean Validation annotations
no Spring/JPA/Hibernate/OpenAPI imports
invariants enforced by value objects, aggregates, policies, and domain services
```

API request DTOs:

```text
Bean Validation at the REST boundary
@Schema aligned with validation constraints
realistic, non-secret examples
```

Controllers:

```text
depend on application inbound ports
do not access repositories/JPA entities
@Valid on request bodies
@Tag on controller
@Operation and @ApiResponses on endpoints
OpenAPI annotations restricted to API layer
```

---

## 10. Recovered ID-021 final checklist

Current evidence should be recorded honestly:

```text
[x] Identity package structure exists
[x] No identityaccess source package observed in live module inventory
[ ] Identity domain Spring-free rule verified by executable architecture test
[ ] Identity domain JPA-free rule verified by executable architecture test
[ ] Identity application/API/infrastructure direction verified by executable test
[x] Identity production source exists
[x] Identity migration exists (current date-based migration)
[ ] Identity domain behavior tests restored and passing
[ ] Identity application tests restored and passing
[ ] Identity API tests restored and passing
[ ] Identity persistence tests restored and passing
[ ] Identity architecture guardrail passes
[ ] mvn -q clean verify passes
```

---

## 11. Next action

Apply:

```text
docs/roadmap/fixs/stabilization_02.md
```

First restore repository-level tests and CI. Then create a dedicated Identity v0.2 test
re-baseline roadmap that inventories the **current** Identity public contracts before
rebuilding ID-016 through ID-020 coverage.

Do not copy the earlier Spring Boot test-slice imports into the current source tree
without confirming their availability under Spring Boot 4.0.7.
