# HidraAPI Canonical Architecture

## Status

CURRENT — canonical architecture index established by HPR-P2-002.

## Applicability and Evidence

This set describes the current repository architecture at the HPR-P2-002 execution baseline `ec5f912d0b671849714562d71b0d0a702637e78c`, reconciled with the completed P1 roadmap state and retained P1 production-equivalent evidence.

Implementation claims are grounded in current source/configuration, `ArchitectureGuardrailTest`, `pom.xml`, `ops/production/**`, and canonical P1 evidence. Legacy `docs/architecture/**` material is preserved as reference/provenance and does not override this set.

## Canonical Set

- `SYSTEM_CONTEXT.md` — system boundary, external actors/systems and current versus target context.
- `ARCHITECTURE_OVERVIEW.md` — modular-monolith structure and runtime composition.
- `BOUNDED_CONTEXT_MAP.md` — current 24 module roots and verified contract relationships.
- `HEXAGONAL_ARCHITECTURE.md` — inward dependency rules and adapter/port boundaries.
- `MODULE_BOUNDARIES.md` — kernel, platform and business-module ownership boundaries.
- `CROSS_MODULE_CONTRACTS.md` — exact exported application-contract surface enforced by architecture tests.
- `TECHNOLOGY_STACK.md` — current application and P1 infrastructure technologies with target/deferred separation.
- `RUNTIME_ARCHITECTURE.md` — HISTORICAL HPR-P1-001 baseline retained for provenance.

## Current Architecture Summary

HidraAPI is a Java 21 / Spring Boot 4.1.1 modular monolith rooted at `dz.sh.hidra` with four top-level production areas: bootstrap, kernel, platform and 24 business modules. Every current business module root contains API, application, domain and infrastructure areas.

Repository-wide ArchUnit rules enforce framework-independent kernel/domain boundaries, prevent application-to-API/infrastructure coupling, prevent REST controllers from reaching outbound/persistence repositories directly, restrict platform JPA access to the reviewed Workbench boundary, and prohibit cross-module access to another module's private domain/infrastructure/application packages except deliberate exported application contracts.

P1 production infrastructure is represented under `ops/production/**` and the P1 closure evidence: Linux VM/systemd runtime, HAProxy application/database routing, PostgreSQL streaming HA with Patroni/etcd, pgBackRest backup/PITR, Vault-backed secret handling, and Prometheus/Alertmanager/Grafana/Loki observability. P1 REST is multi-node; realtime STOMP remains single-active.

## Target / Deferred Discipline

Target or deferred architecture is not current implementation. In particular:

- TimescaleDB is DEFERRED / not implemented.
- PostGIS is DEFERRED / not implemented.
- clustered realtime via a shared/external broker is not current P1 architecture.
- Redis/distributed cache is not selected merely because process-local cache exists.
- `agents`, `environment` and `otsecurity` are not current module source roots and must not be documented as implemented modules.
- canonical domain semantics are maintained under `doc/domain/**` by HPR-P2-003; per-module current-state documents remain HPR-P2-004 scope.

## HPR-P2-013 architecture verification

Verified source parent `00c4fda266b2dfd175cca37ad789dc9462a5af0b`, 2026-10-09. The current
[contract inventory](CROSS_MODULE_CONTRACTS.md) and [context map](BOUNDED_CONTEXT_MAP.md)
are refreshed to 70 Java files in 63 exported packages, exactly matching architecture
enforcement. Neutral target/evidence extension roles are separated from named module
consumers. This current export inventory supersedes the old HPR-P2-002 relationship
tables; its original baseline remains historical provenance. No architecture rule,
export permission, runtime dependency or P1 physical evidence is changed. Other
architecture descriptions keep their own applicability. P2 final verification is
pending both exact-head CI gates; P3 remains DEFERRED.
