# HidraAPI pgBackRest production template.
# Render values during provisioning; do not commit real credentials or host identities.
[global]
# repo1: operational recovery repository, daily full backups, 35-day time retention.
repo1-host=__PGBACKREST_REPO_HOST__
repo1-host-user=__PGBACKREST_REPO_USER__
repo1-path=__PGBACKREST_REPO_PATH__
repo1-retention-full-type=time
repo1-retention-full=35
repo1-bundle=y
repo1-block=y

# repo2: monthly retained recovery-point repository.
# HPR-P1-017 / HIDRA-P1-BACKUP-RETENTION-001:
# one full backup per month, retain 12 complete monthly full backups.
repo2-host=__PGBACKREST_MONTHLY_REPO_HOST__
repo2-host-user=__PGBACKREST_MONTHLY_REPO_USER__
repo2-path=__PGBACKREST_MONTHLY_REPO_PATH__
repo2-retention-full-type=count
repo2-retention-full=12
repo2-bundle=y
repo2-block=y

# archive-push writes WAL to all configured repositories. Async archive is required
# so temporary loss of one repository does not prevent WAL from reaching the other.
archive-async=y
start-fast=y
process-max=__PGBACKREST_PROCESS_MAX__
log-level-console=info
log-level-file=detail
spool-path=__PGBACKREST_SPOOL_PATH__

[hidra]
pg1-path=__POSTGRES_DATA_DIR__
pg1-port=5432
