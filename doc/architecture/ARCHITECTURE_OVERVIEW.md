# HidraAPI Architecture Overview

## Status

CURRENT architecture; target/deferred statements are isolated below.

## Architectural Style

HidraAPI is implemented as a modular monolith using Domain-Driven Design boundaries and Hexagonal/Ports-and-Adapters layering inside one Spring Boot application.

The production package root is:

```text
dz.sh.hidra
├── bootstrap
├── kernel
├── platform
└── modules
    └── <24 business module roots>
```

`HidraApplication.java` is the application bootstrap entry point.

## Current Structural Layers

Every current business module root contains:

```text
<module>/
├── api/
├── application/
├── domain/
├── infrastructure/
├── <ModuleName>Module.java
└── package-info.java
```

This structural consistency is verified from the current source tree. Detailed semantic ownership for each module is intentionally deferred to HPR-P2-003/HPR-P2-004.

## Core Boundaries

- **kernel** — framework- and module-independent primitives; ArchUnit prevents dependencies on Spring, JPA/Hibernate, platform and business modules.
- **platform** — technical capabilities such as security, observability, persistence plumbing, events/realtime and the reviewed operational Workbench boundary; platform does not become a business-domain owner.
- **modules** — business bounded-context roots with API/application/domain/infrastructure layering.
- **bootstrap** — application startup/composition.

## Dependency Direction

Normal request flow is inward:

```text
HTTP / API adapter
      |
      v
application inbound contract/use case
      |
      v
domain model / policy
      |
      v
application outbound port
      |
      v
infrastructure adapter / persistence / external integration
```

The architecture guardrail forbids application code from depending on API or infrastructure and forbids domain code from depending on Spring/JPA/Hibernate/Jackson/Swagger or platform packages.

## Current Runtime Composition

The current P1-complete architecture adds production operations around the modular monolith:

- minimum two active HidraAPI REST nodes managed as systemd services on Linux VMs;
- HAProxy readiness-based application traffic distribution;
- PostgreSQL with Patroni/etcd and a stable HAProxy database endpoint;
- pgBackRest backup/WAL/PITR;
- Vault-backed secret handling;
- Prometheus/Alertmanager/Grafana/Loki observability;
- controlled release/drain/rejoin/rollback scripts under `ops/production/**`.

Realtime STOMP remains based on Spring's in-process simple broker and is single-active for P1.

## Security and Persistence Boundaries

Production configuration externalizes sensitive inputs. JPA uses schema validation and Flyway is the migration authority with clean disabled. REST controllers are prohibited from direct outbound-port, persistence or Spring Data repository access.

Platform JPA access is exceptional rather than general: `HidraOperationalWorkbenchService` is the reviewed generic persistence reader and must retain the fail-closed Workbench exposure policy.

## Target / Deferred Architecture

HPR-P2-002 does not convert future ideas into current architecture. TimescaleDB, PostGIS, distributed cache, clustered realtime, new module roots, service extraction and specific industrial integration protocols remain absent/deferred unless separately approved and implemented.

A future extraction from the modular monolith is not implied by this documentation set.
