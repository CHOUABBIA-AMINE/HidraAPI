# Backup and PITR Implementation

## Status

**IMPLEMENTED-PENDING-RETENTION-BINDING-AND-EXERCISE — HPR-P1-017**

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

The remaining gap is implementation/evidence: the selected repository/storage mechanism must enforce the monthly 12-month hold and a retained monthly point must be demonstrated restorable.

## HPR-P1-012 relationship

HPR-P1-012 must use real output from the PITR harness and must independently calculate:

- achieved RPO against ≤ 5 minutes;
- achieved RTO against ≤ 60 minutes;
- application/security acceptance after recovery.

Configuration presence alone is not DR evidence.
