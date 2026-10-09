# HidraAPI Flyway Policy

## Status

CURRENT — canonical HPR-P2-006 schema-migration policy.

## Current Configuration

Repository configuration establishes:

```text
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=false
spring.flyway.validate-on-migrate=true
spring.flyway.clean-disabled=true
spring.jpa.hibernate.ddl-auto=validate
```

Production retains validation and clean-disabled behavior.

## Migration Authority

Flyway is the only approved application-schema evolution authority.

Rules:

1. applied versioned migrations are immutable;
2. do not edit an applied file to force a checksum match;
3. do not falsify Flyway history;
4. do not disable validation to force startup;
5. do not use Flyway clean in production;
6. new schema changes use new forward migrations;
7. schema migration executes against the authoritative writable primary;
8. PostgreSQL standbys receive schema changes through replication rather than independent Flyway execution;
9. deployment must prevent uncontrolled concurrent migration attempts from multiple application nodes;
10. JPA validation must succeed after migration.

## Current Chain

Migration directory: `src/main/resources/db/migration`

Migration count: **139**

First migration: `V20260611_001__create_identity_tables.sql`

Current tail: `V20261008_026__hmr_080_planning_nomination_integrity.sql`

Historical closed P1 recovery evidence reconciled its own 82-migration restored executable tree; it does not prove deployment/recovery of the current 139-migration source chain.

## Migration Failure

On migration failure, preserve Flyway/error/history evidence and determine whether partial state exists. Prefer a reviewed forward corrective migration where safe.

Exceptional Flyway history repair requires explicit Database Operations + Application Engineering review and retained justification.

## Data Dictionary Regeneration

The canonical persistence dictionary must be regenerated whenever executable changes add/remove/rename JPA persistence entities or change the Flyway migration chain.

HPR-P2-012 implements bounded canonical structure and OpenAPI drift checks. It does not compare database inventory counts to Java/SQL source. This refresh performs that source comparison explicitly; it adds no automated database drift rule.

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
