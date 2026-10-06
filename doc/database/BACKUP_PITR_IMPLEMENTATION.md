# Backup and PITR Implementation

## Status

**HISTORICAL — HPR-P1-017 implementation-stage baseline; retained for P1 stage provenance.**

Current database authority is the HPR-P2-006 set indexed by `doc/database/README.md`, together with executable configuration/migrations and the closed P1 evidence. Later P1 closure supersedes any pre-closure “pending”, “target”, “not selected” or “not established” statements below when they conflict with current evidence.

Execution base: `9e1503154e59c7de5b06ca00bb045c2328770c1b`.

## Implemented control

HidraAPI P1 now has an executable pgBackRest baseline for PostgreSQL backup, continuous WAL archiving and timestamp-based PITR.

The configuration enforces the approved 35-day operational full-backup retention using pgBackRest time-based retention.

A daily systemd timer provides at least one scheduled full backup opportunity every 24 hours.

## Recovery commands

Backup:

`pgbackrest --config=<rendered config> --stanza=hidra --type=full backup`

Repository/backup check:

`pgbackrest --config=<rendered config> --stanza=hidra check`

Timestamp PITR:

`pgbackrest --config=<rendered config> --stanza=hidra --pg1-path=<isolated PGDATA> --type=time --target=<approved timestamp> --target-action=promote restore`

PostgreSQL archive integration:

- archive push: `pgbackrest --stanza=hidra archive-push %p`;
- archive get: `pgbackrest --stanza=hidra archive-get %f "%p"`.

## Validation boundaries

The repository can validate command/configuration structure in CI.

It cannot prove actual recoverability without a real pgBackRest repository and PostgreSQL recovery environment.

The supplied PITR harness therefore remains deliberately destructive/opt-in and records evidence when executed.

## Retention binding

The 35-day operational window is implemented.

Owner approval on 2026-10-05 resolves the monthly-retention policy decision:

- one monthly recovery point;
- 12 monthly points retained for 12 months;
- policy identifier `HIDRA-P1-BACKUP-RETENTION-001`;
- protected-copy independence from the live database failure domain.

The repository implementation now binds the policy to pgBackRest repo2 with one scheduled monthly full backup and count-based retention of 12 full backups. repo1 remains the 35-day operational repository.

The remaining gap is measured evidence: production-equivalent storage must be provisioned and a retained repo2 monthly recovery point must be demonstrated restorable with the required WAL.

## HPR-P1-012 relationship

HPR-P1-012 must use real output from the PITR harness and must independently calculate:

- achieved RPO against ≤ 5 minutes;
- achieved RTO against ≤ 60 minutes;
- application/security acceptance after recovery.

Configuration presence alone is not DR evidence.
