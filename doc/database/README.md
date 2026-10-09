# HidraAPI Canonical Database Documentation

## Status

CURRENT — canonical database index established by HPR-P2-006.

## Verification Baseline

Current inventory source parent: `00c4fda266b2dfd175cca37ad789dc9462a5af0b`, verified 2026-10-09.

Historical HPR-P2-006 generation baseline: `aeb9008d74b90f102ab8706b9a23f1a6eb6cbe9c`

Current repository inventory below includes subsequent semantic-remediation migrations. P1 physical evidence retains its original executable/deployed applicability.

Verified repository facts used by this set:

- PostgreSQL is the authoritative application database.
- Flyway migration location is `src/main/resources/db/migration`.
- Current versioned migration count: **139**.
- JPA uses `hibernate.ddl-auto=validate`.
- Flyway is enabled, `validate-on-migrate=true`, `baseline-on-migrate=false`, and `clean-disabled=true`.
- Current module JPA persistence-entity count: **470** across **24** business modules.
- P1 production database stack is PostgreSQL streaming replication + Patroni + etcd + HAProxy.
- Backup/WAL/PITR implementation is pgBackRest.
- Closed P1 evidence records controlled failover/single-writer/fencing/application recovery and successful PITR with achieved RPO 15 seconds and RTO 37 minutes.

## Canonical Set

- `DATABASE_ARCHITECTURE.md` — current application/database and P1 HA/DR architecture.
- `SCHEMA_OWNERSHIP.md` — module persistence ownership and cross-module rules.
- `FLYWAY_POLICY.md` — schema-change authority and migration lifecycle.
- `DATA_DICTIONARY.md` — generated current persistence inventory from the 139-migration chain and 470 JPA entities.

## Historical P1 Stage Documents

The following remain preserved as stage evidence, but their pre-closure status statements are no longer current authority:

- `POSTGRES_HIGH_AVAILABILITY.md` — HPR-P1-003 target-model baseline.
- `DATABASE_OPERATIONS_RUNBOOK.md` — HPR-P1-011 product-neutral operating baseline.
- `POSTGRES_FAILOVER_IMPLEMENTATION.md` — HPR-P1-016 implementation-stage baseline.
- `BACKUP_PITR_IMPLEMENTATION.md` — HPR-P1-017 implementation-stage baseline.

Current runtime/operations procedures under `ops/production/postgres/**` and closed P1 evidence supersede contradictory pre-implementation statements in those historical documents.

## Authority Rule

Physical schema truth is the ordered Flyway migration chain applied to PostgreSQL. JPA mappings are runtime/application persistence mappings and are validated against that schema; they are not a second independent DDL authority.

TimescaleDB and PostGIS remain NOT IMPLEMENTED / DEFERRED.

## HPR-P2-013 source refresh and historical applicability

Current repository verification: `00c4fda266b2dfd175cca37ad789dc9462a5af0b`, 2026-10-09. Source inventory contains
**139** unique versioned migrations and **470** module @Entity classes across
**24** modules, including **25** Risk entities. Current tail:
`V20261008_026__hmr_080_planning_nomination_integrity.sql`. The original HPR-P2-006 generation at
`aeb9008d74b90f102ab8706b9a23f1a6eb6cbe9c` recorded 82 migrations/469 entities;
that is preserved historical generation evidence, superseded for current inventory.

Retained P1 deployed/recovery evidence keeps its original deployed SHA, 82-migration
scope and measured RPO/RTO. This source refresh is not an assertion that all 139
migrations have been deployed or physically recovered. Database documentation
completion and exact-head CI do not establish current production-data acceptance.
Full P2 closure verification remains pending both CI workflows on the resulting
implementation commit; P3 remains DEFERRED. No schema/data/runtime change is made.
