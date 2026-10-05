# HidraAPI pgBackRest Backup / WAL / PITR

## Status

**IMPLEMENTED-PENDING-POLICY-AND-EXERCISE — HPR-P1-017**

The approved backup implementation is pgBackRest with a repository hosted outside the live PostgreSQL primary/standby nodes.

This repository implementation establishes:

- daily full-backup execution;
- continuous PostgreSQL WAL archive-push/archive-get integration;
- 35-day time-based full-backup retention;
- backup-health validation;
- isolated timestamp-target PITR restore verification;
- current Flyway schema-history validation;
- static CI validation of the backup/PITR artifacts.

It does not claim that the backup repository or PostgreSQL recovery host has already been provisioned.

## Repository independence

The template uses `repo1-host` and `repo1-path`, requiring the pgBackRest repository to live on an independent repository host/storage boundary rather than inside the live primary/standby data directories.

The exact storage product remains an infrastructure provisioning choice, but the deployed repository must satisfy the approved independent-failure-path requirement.

## Operational retention

`repo1-retention-full-type=time` and `repo1-retention-full=35` encode the approved 35-day operational recovery window.

pgBackRest expiration keeps WAL consistently with retained backups unless operators deliberately override archive retention. The HidraAPI template therefore does not configure aggressive independent WAL expiration.

## Monthly retained recovery point

Still **BLOCKED-DECISION**:

- enterprise/SONATRACH governing policy identifier;
- exact monthly recovery-point hold duration/rule.

No monthly duration is encoded in this implementation.

HPR-P1-017 cannot be fully closed until that rule is supplied and bound to the repository/storage mechanism.

## WAL integration

PostgreSQL uses:

- `archive_command='pgbackrest --stanza=hidra archive-push %p'`;
- `restore_command='pgbackrest --stanza=hidra archive-get %f "%p"'`.

A production deployment must render/install the pgBackRest configuration on the PostgreSQL nodes and repository host with the required credentials/SSH/TLS permissions outside Git.

## Daily backup

The provided systemd timer runs one full backup each day.

A successful backup is followed by `pgbackrest info --output=json`.

The health script rejects an environment where no completed backup exists or where the latest completed backup is older than 24 hours.

## PITR exercise

`verify-pitr-restore.sh` restores into an empty isolated `PGDATA` and requires an explicit approved target timestamp.

It:

1. refuses execution without `HIDRA_PITR_DESTRUCTIVE_EXERCISE=YES`;
2. restores with `--type=time --target=<timestamp> --target-action=promote`;
3. starts the isolated recovered PostgreSQL with an operator-supplied approved service command;
4. verifies database queryability;
5. checks recovery state;
6. inspects `flyway_schema_history`;
7. requires at least the currently audited 82 successful migrations;
8. records technical restore/PITR elapsed time.

The script does not restore normal production traffic. Full DR acceptance/RTO/RPO remains HPR-P1-012 scope.

## Required measured evidence

HPR-P1-017 requires production-equivalent evidence of:

- a successful full backup from the current schema;
- continuous WAL archival around the selected PITR target;
- restore from the retained independent repository;
- recovery to an intentionally selected timestamp;
- current Flyway schema history;
- backup/repository identifiers;
- achieved recovery point;
- technical restore duration;
- backup/WAL warnings or gaps;
- proof that the repository is outside the live database failure path.

Until that exercise exists, this HPR remains implementation-complete but not survivability-verified.
