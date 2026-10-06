# HidraAPI Database Operations Procedures

## Status

**IMPLEMENTED-PENDING-PRODUCTION-EQUIVALENT-VALIDATION — HPR-P1-020 + HPR-P1-027**

This directory operationalizes the database runbook for the approved PostgreSQL + Patroni + etcd + HAProxy + pgBackRest stack.

Read-only SQL procedures cover sessions, blocking locks, long-running work, VACUUM/ANALYZE history and dead-tuple pressure, index usage, index validity/readiness, extension-free space-risk indicators, database/schema/table capacity, replication/slots, and Flyway history.

`database-health-report.sh` runs those inspections and also records Patroni topology plus pgBackRest stanza health and repository information. When `HIDRA_POSTGRES_DATA_PATH` is supplied on the database host it additionally runs `inspect-host-capacity.sh`, which records filesystem bytes and inode capacity with `df`; optional WAL and local pgBackRest spool paths can be included through `HIDRA_POSTGRES_WAL_PATH` and `HIDRA_PGBACKREST_LOCAL_SPOOL_PATH`.

## Index and space-risk interpretation

`inspect-index-health.sql` reads PostgreSQL catalog state from `pg_index` and explicitly surfaces `indisvalid`, `indisready`, and `indislive`. An invalid, not-ready, or not-live index requires Database Operations review before use or remediation.

`inspect-bloat-space-risk.sql` deliberately does **not** claim an exact physical bloat percentage. Without an approved extension such as `pgstattuple`, the repository uses extension-free triage indicators only:

- estimated live/dead tuple counts and dead-tuple percentage from `pg_stat_user_tables`;
- table total size;
- index size, scan count, and index-to-table-size ratio.

These values identify objects for investigation; they do not authorize a drop, rebuild, or capacity change by themselves.

`inspect-capacity.sql` reports PostgreSQL object sizes only. It must not be used as proof of free host storage. Host/filesystem capacity is a separate operating-system observation produced by `inspect-host-capacity.sh`.

## Guarded maintenance

Mutating procedures are deliberately guarded:

- `vacuum-analyze-table.sh schema.table` requires `HIDRA_DB_MAINTENANCE_EXECUTE=YES`;
- `reindex-concurrently.sh schema.index` requires `HIDRA_DB_MAINTENANCE_EXECUTE=YES`;
- `terminate-session.sh PID` requires `HIDRA_DB_TERMINATE_SESSION=YES`.

Routine PostgreSQL autovacuum remains the default. Large or unused indexes are inspection candidates only; `idx_scan=0` is not authorization to drop an index.

Flyway governance remains unchanged: never edit applied migrations, falsify history, disable validation, or run production clean.

## Production-equivalent validation

`verify-database-maintenance.sh` is the governed maintenance-exercise harness. It refuses to run unless `HIDRA_DB_MAINTENANCE_EXERCISE=YES` is explicitly set and requires:

- the stable PostgreSQL endpoint;
- an approved `vacuum-analyze` or `reindex-concurrently` target;
- an approved authenticated database-backed HidraAPI acceptance URL;
- authentication supplied only through an external readable curl configuration.

The harness records index health, extension-free space-risk indicators, and database object capacity before maintenance; runs exactly one explicitly selected guarded maintenance action; repeats those diagnostics; and requires authenticated application acceptance both before and after maintenance. It retains a timestamped evidence log and does not print authentication material.

Repository CI performs static and shell-syntax validation only. Actual production-equivalent diagnostic/maintenance execution, host-capacity capture, post-maintenance acceptance, and no-secret evidence remain part of the integrated HPR-P1-029 campaign; repository configuration alone is not mislabeled as measured operational validation.
