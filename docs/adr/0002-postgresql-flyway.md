# ADR-0002 — PostgreSQL and Flyway are HidraAPI persistence authorities

**Status:** Accepted (existing stack; recorded 2026-09-22).

## Context
Legacy HyFlo sources contain spreadsheets and historical MariaDB SQL whose schema and business meaning differ from HidraAPI contracts.

## Decision
Use **PostgreSQL** as target relational DBMS, Spring Data JPA/Hibernate in module-owned persistence adapters, and **Flyway** SQL under src/main/resources/db/migration for target schema evolution. Review actual current domain models, JPA entities, Flyway SQL and public module write contracts for each proposed mapping. Never execute legacy SQL dumps as target migrations or rewrite already applied Flyway files.

## Consequences
Staging/import must be separately approved and verified against the current schema on disposable PostgreSQL before authorized release. This ADR does not authorize an import or change any database.

## Source
[README persistence/stack](../../README.md), [architecture persistence rule](../ARCHITECTURE.md), [data roadmap](../roadmap/data-provisioning.md), [target inventory](../data-provisioning/target-inventory.md).
