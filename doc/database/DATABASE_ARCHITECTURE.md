# HidraAPI Database Architecture

## Status

CURRENT — HPR-P2-006 architecture baseline.

## Application Persistence Contract

HidraAPI uses PostgreSQL through the PostgreSQL JDBC driver, Spring Data JPA/Hibernate and HikariCP.

Production datasource identity is externalized through:

- `HIDRA_DATASOURCE_URL`;
- `HIDRA_DATASOURCE_USERNAME`;
- `HIDRA_DATASOURCE_PASSWORD`.

The application does not own node-specific PostgreSQL addresses as part of its normal persistence contract.

JPA configuration uses schema validation rather than schema creation:

```text
spring.jpa.hibernate.ddl-auto=validate
```

Flyway therefore remains the schema evolution authority.

## Current P1 Production Topology

```text
HidraAPI nodes
     |
     v
stable PostgreSQL endpoint (HAProxy)
     |
     v
PostgreSQL primary <----> standby
        ^                  ^
        |                  |
        +---- Patroni -----+
              |
             etcd quorum

PostgreSQL/WAL ---> pgBackRest repo1/repo2 ---> restore/PITR
```

Patroni/etcd owns database role authority. HAProxy routes application write connectivity through the stable endpoint and does not independently elect a primary.

Closed P1 production-equivalent evidence records:

- controlled Patroni switchover;
- exactly one writable primary after role change;
- former-primary demotion and fencing evidence;
- application recovery through the stable endpoint;
- pgBackRest PITR;
- achieved RPO **15 seconds** against the approved ≤5 minute objective;
- achieved RTO **37 minutes** against the approved ≤60 minute objective.

## Backup and Recovery

Current repository implementation uses pgBackRest for:

- full backup;
- continuous WAL archive integration;
- timestamp PITR;
- operational repo1 retention controls;
- protected repo2 monthly recovery-point retention.

HA replication does not replace backup.

## Schema Evolution

All application schema evolution is versioned under `src/main/resources/db/migration`. The current chain contains **82** versioned SQL migrations.

Schema changes execute against the writable primary. Standbys receive them through PostgreSQL replication; they are not independently migrated.

## Boundaries

- TimescaleDB: NOT IMPLEMENTED / DEFERRED.
- PostGIS: NOT IMPLEMENTED / DEFERRED.
- JPA entities do not authorize cross-module persistence access.
- The reviewed platform Workbench JPA boundary remains an architecture-controlled exception and does not transfer table ownership away from modules.
