# HidraAPI Module Boundaries

## Status

CURRENT structural boundary model.

## Repository-Level Ownership

| Area | Responsibility |
|---|---|
| `bootstrap` | Spring Boot application startup and composition. |
| `kernel` | Framework-independent primitives available without business-module ownership. |
| `platform` | Cross-cutting technical infrastructure and platform services, not business truth. |
| `modules/<name>` | Business-module/bounded-context code with private internal layers and explicit public contracts. |

## Business Module Boundary

All 24 current module roots contain `api`, `application`, `domain` and `infrastructure` directories. A module owns its internal domain and infrastructure implementation. Another module must not import those internals.

Private cross-module packages include another module's:

- `domain/**`;
- `infrastructure/**`;
- non-exported `application/**`.

Deliberately exported `application.contract.<consumer>` packages are the current exception and are listed in `CROSS_MODULE_CONTRACTS.md`.

## API Boundary

REST controllers are delivery adapters. They may map HTTP input/output and invoke application-facing contracts, but the architecture guardrail prevents direct persistence/repository access and module infrastructure coupling.

## Persistence Boundary

JPA/persistence classes remain infrastructure details of their owning module. Repository-wide tests additionally require unique JPA table names and unique explicit column names within an entity.

Platform is not allowed to become a generic persistence bypass. The only reviewed generic JPA reader is the Workbench service, which remains bound to the fail-closed exposure policy and is not allowed to depend on module persistence packages.

## Kernel and Platform Boundary

Kernel remains independent of Spring, JPA/Hibernate, platform and modules.

Platform provides technical mechanisms. Business concepts remain in business modules; platform mechanisms must not become an alternate owner of module domain truth.

## Current Module Inventory

```text
alarm analytics assets audit configuration custody documents hse
identity incident integration integrity leakdetection monitoring notification
organization party planning reporting risk simulation telemetry topology workflow
```

HPR-P2-004 will create one current-state module document per root. This document deliberately does not pre-assign detailed aggregate, table, API or lifecycle ownership that belongs to that task.

## Target / Deferred Boundary

`agents`, `environment` and `otsecurity` are not current source roots. No module boundary is reserved or created for them by HPR-P2-002.
