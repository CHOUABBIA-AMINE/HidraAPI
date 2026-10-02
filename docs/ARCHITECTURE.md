# HidraAPI Architecture

## 1. Architecture style

HidraAPI is a **modular monolith first** backend using Domain-Driven Design and
hexagonal/ports-and-adapters architecture.

Canonical dependency direction:

```text
API -> Application -> Domain -> Kernel
Infrastructure -> Application ports + Domain
Platform -> Kernel + technical frameworks
```

Business domains must not be coupled through direct foreign aggregate ownership.

---

## 2. Sources of truth

Architecture references, in precedence order:

1. [`Hidra — Macro Architecture`](architecture/Hidra%20%E2%80%94%20Macro%20Architecture.md)
2. [`Hidra — Micro Architecture`](architecture/Hidra%20%E2%80%94%20Micro%20Architecture.md)
3. [`Coding policy`](policy/Coding-policy.md)
4. Module-specific roadmap under `roadmap/`
5. Current production source and Flyway migrations

Repository inventories:

- [Module catalog](architecture/module-catalog.md)
- [Domain schema](architecture/domain-schema.md)
- [Persistence migration inventory](architecture/persistence-schema.md)

---

## 3. Top-level package ownership

```text
dz.sh.hidra
├── kernel       generic business-neutral primitives
├── platform     technical infrastructure and cross-cutting runtime concerns
└── modules      bounded business contexts
```

### Kernel

Kernel may expose stable primitives such as value-object, result, validation, pagination,
domain-event, and exception contracts. It must remain framework-neutral and must not own
business concepts.

### Platform

Platform owns technical concerns such as:

```text
configuration
security plumbing
web/error handling
persistence configuration
messaging/outbox
observability
realtime transport
workbench/technical support
```

Platform must not become a business-domain owner.

### Business modules

A mature module follows:

```text
module
├── api
├── application
├── domain
└── infrastructure
```

Architectural presence does not imply production maturity. The macro architecture uses
the maturity states Prepare, Operate, Industrialize, and Intelligize.

---

## 4. Core guardrails

Domain code must not depend on:

```text
Spring
JPA/Hibernate
Jackson
OpenAPI
REST
platform infrastructure
foreign module aggregate classes
```

Application code must not depend on:

```text
REST request/response contracts
controllers
JPA entities/repositories
infrastructure implementations
```

Controllers must not access persistence repositories directly.

Infrastructure adapters implement ports defined toward the inside of the architecture.

---

## 5. OpenAPI boundary

OpenAPI annotations belong to the API boundary.

```text
@Tag           controller
@Operation     endpoint
@ApiResponses  endpoint
@Parameter     path/query/header parameters
@Schema        REST request/response contracts
```

Domain packages must not import OpenAPI annotations.

---

## 6. Persistence ownership

Flyway migrations under `src/main/resources/db/migration` are the database evolution
source of truth. Each bounded context must use its own table prefix/ownership and must not
directly mutate another module's tables.

See [persistence-schema.md](architecture/persistence-schema.md) for the current migration
inventory.

---

## 7. Verification

Repository-level architecture tests live under `src/test/java/dz/sh/hidra`.

Minimum verification:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

Architecture violations should be corrected in production code under the owning module
roadmap rather than weakened in the guardrail.
