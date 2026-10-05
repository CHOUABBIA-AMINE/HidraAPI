# HidraAPI pgBackRest production template.
# Render values during provisioning; do not commit real credentials or host identities.
[global]
repo1-host=__PGBACKREST_REPO_HOST__
repo1-host-user=__PGBACKREST_REPO_USER__
repo1-path=__PGBACKREST_REPO_PATH__
repo1-retention-full-type=time
repo1-retention-full=35
repo1-bundle=y
repo1-block=y
start-fast=y
process-max=__PGBACKREST_PROCESS_MAX__
log-level-console=info
log-level-file=detail
spool-path=__PGBACKREST_SPOOL_PATH__

[hidra]
pg1-path=__POSTGRES_DATA_DIR__
pg1-port=5432
