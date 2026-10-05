# HidraAPI Database Operations Procedures

## Status

**IMPLEMENTED-PENDING-PRODUCTION-EQUIVALENT-VALIDATION — HPR-P1-020**

This directory operationalizes the database runbook for the approved PostgreSQL + Patroni + etcd + HAProxy + pgBackRest stack.

Read-only SQL procedures cover sessions, blocking locks, long-running work, VACUUM/ANALYZE history and dead-tuple pressure, index usage, database/schema/table capacity, replication/slots, and Flyway history.

`database-health-report.sh` runs those inspections and also records Patroni topology plus pgBackRest stanza health and repository information.

Mutating procedures are deliberately guarded:

- `vacuum-analyze-table.sh schema.table` requires `HIDRA_DB_MAINTENANCE_EXECUTE=YES`;
- `reindex-concurrently.sh schema.index` requires `HIDRA_DB_MAINTENANCE_EXECUTE=YES`;
- `terminate-session.sh PID` requires `HIDRA_DB_TERMINATE_SESSION=YES`.

Routine PostgreSQL autovacuum remains the default. Large or unused indexes are inspection candidates only; `idx_scan=0` is not authorization to drop an index.

Flyway governance remains unchanged: never edit applied migrations, falsify history, disable validation, or run production clean.

Repository CI performs static validation only. HPR-P1-020 still requires controlled execution against production-equivalent PostgreSQL, retained diagnostics, one approved maintenance exercise, post-maintenance database/application acceptance, and confirmation that retained evidence contains no secrets.
