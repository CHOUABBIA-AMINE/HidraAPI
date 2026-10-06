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

Migration count: **82**

First migration: `V20260611_001__create_identity_tables.sql`

Current tail: `V20261005_001__provision_risk_register_created_audit_taxonomy.sql`

The closed P1 recovery evidence independently reconciled the current 82-migration tail in the restored executable tree.

## Migration Failure

On migration failure, preserve Flyway/error/history evidence and determine whether partial state exists. Prefer a reviewed forward corrective migration where safe.

Exceptional Flyway history repair requires explicit Database Operations + Application Engineering review and retained justification.

## Data Dictionary Regeneration

The canonical persistence dictionary must be regenerated whenever executable changes add/remove/rename JPA persistence entities or change the Flyway migration chain.

HPR-P2-012 may add automated drift checks; HPR-P2-006 does not modify CI.
