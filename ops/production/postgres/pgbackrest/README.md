# HidraAPI pgBackRest Backup / WAL / PITR

## Status

**IMPLEMENTED-PENDING-EXERCISE — HPR-P1-017**

The approved backup implementation uses two pgBackRest repositories with distinct retention purposes.

## Repository model

### repo1 — operational recovery

- daily full backup;
- 35-day time-based full-backup retention;
- continuous WAL archive;
- preferred source for routine/fast restore.

### repo2 — monthly retained recovery points

Owner policy `HIDRA-P1-BACKUP-RETENTION-001` requires:

- one full recovery point per month;
- 12 retained monthly full backups / 12 months;
- protected storage outside the live PostgreSQL primary/standby failure domain.

The template encodes `repo2-retention-full-type=count` and `repo2-retention-full=12`.

The monthly systemd timer executes `run-monthly-retained-backup.sh`, which creates a repo2 full backup annotated with the Hidra retention policy.

## Scheduled service configuration

Both committed pgBackRest backup services bind `HIDRA_PGBACKREST_CONFIG=/etc/pgbackrest/pgbackrest.conf`. The rendered file remains external production configuration and may contain environment-specific host/path values; those values are not committed to Git. This closes the scheduler-to-script binding defect identified by the 2026-10-06 P1 re-audit.

## WAL behavior

pgBackRest archive-push writes WAL to all configured repositories. `archive-async=y` is enabled so a temporary repository outage does not prevent the other configured repository from advancing its WAL archive.

No aggressive independent WAL expiration is configured. WAL retention follows the retained backups/repository retention model.

## Monthly-retention validation

`check-monthly-retention.sh` verifies repo2 has a completed full backup, the latest retained monthly point is no older than 35 days, no observed gap between retained monthly points exceeds 35 days, and the repository does not contain more than 12 full backups after pgBackRest expiration.

During the first year the repository naturally contains fewer than 12 monthly points. Once mature, count-based retention keeps the latest 12.

## PITR exercise

`verify-pitr-restore.sh` accepts:

- `HIDRA_PITR_REPO=1` for operational recovery validation;
- `HIDRA_PITR_REPO=2` for monthly retained-point validation;
- `HIDRA_FLYWAY_EXPECTED_HISTORY` pointing to an approved `version|checksum` evidence manifest captured from the exact current-schema source baseline.

Before an exercise, `capture-flyway-history.sh` can capture that manifest from an approved source database through `HIDRA_FLYWAY_SOURCE_PSQL_URI`. The output path must be new and is treated as retained exercise evidence. The PITR harness compares the restored database's complete successful versioned Flyway history to that manifest byte-for-byte and records both SHA-256 digests. A migration count alone is no longer accepted as current-schema proof.

HPR-P1-017 cannot be measured/verified until a production-equivalent exercise restores from repo2 and proves the chosen monthly retained point is recoverable with the required WAL.

## Remaining measured evidence

Required evidence still includes:

- exact pgBackRest and storage implementation versions/identifiers;
- repo1/repo2 physical independence from the live database failure domain;
- current-schema full backup;
- continuous WAL evidence;
- intentional timestamp PITR;
- at least one restore sourced from repo2;
- Flyway/JPA/application acceptance;
- measured achieved RPO/RTO for HPR-P1-012.

Production readiness remains NOT ESTABLISHED until those exercises pass.
