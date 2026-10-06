# HidraAPI Canonical Database Documentation

## Status

CURRENT — canonical database index established by HPR-P2-006.

## Verification Baseline

Repository baseline: `aeb9008d74b90f102ab8706b9a23f1a6eb6cbe9c`

Current executable database evidence at this baseline is unchanged from the restored P1-complete executable tree.

Verified repository facts used by this set:

- PostgreSQL is the authoritative application database.
- Flyway migration location is `src/main/resources/db/migration`.
- Current versioned migration count: **82**.
- JPA uses `hibernate.ddl-auto=validate`.
- Flyway is enabled, `validate-on-migrate=true`, `baseline-on-migrate=false`, and `clean-disabled=true`.
- Current module JPA persistence-entity count: **469** across **24** business modules.
- P1 production database stack is PostgreSQL streaming replication + Patroni + etcd + HAProxy.
- Backup/WAL/PITR implementation is pgBackRest.
- Closed P1 evidence records controlled failover/single-writer/fencing/application recovery and successful PITR with achieved RPO 15 seconds and RTO 37 minutes.

## Canonical Set

- `DATABASE_ARCHITECTURE.md` — current application/database and P1 HA/DR architecture.
- `SCHEMA_OWNERSHIP.md` — module persistence ownership and cross-module rules.
- `FLYWAY_POLICY.md` — schema-change authority and migration lifecycle.
- `DATA_DICTIONARY.md` — generated current persistence inventory from the 82-migration chain and 469 JPA entities.

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
