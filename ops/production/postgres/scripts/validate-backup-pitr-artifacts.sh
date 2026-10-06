#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "\${BASH_SOURCE[0]}")/../../../.." && pwd)"
conf="\${root}/ops/production/postgres/pgbackrest/pgbackrest.conf.tpl"
archive="\${root}/ops/production/postgres/pgbackrest/postgresql-archive.conf"
full="\${root}/ops/production/postgres/scripts/run-full-backup.sh"
monthly="\${root}/ops/production/postgres/scripts/run-monthly-retained-backup.sh"
check="\${root}/ops/production/postgres/scripts/check-backup-health.sh"
monthly_check="\${root}/ops/production/postgres/scripts/check-monthly-retention.sh"
capture="\${root}/ops/production/postgres/scripts/capture-flyway-history.sh"
pitr="\${root}/ops/production/postgres/scripts/verify-pitr-restore.sh"
full_service="\${root}/ops/production/postgres/systemd/hidra-pgbackrest-full.service"
monthly_timer="\${root}/ops/production/postgres/systemd/hidra-pgbackrest-monthly.timer"
monthly_service="\${root}/ops/production/postgres/systemd/hidra-pgbackrest-monthly.service"

grep -q '^repo1-retention-full-type=time$' "\${conf}"
grep -q '^repo1-retention-full=35$' "\${conf}"
grep -q '^repo1-host=__PGBACKREST_REPO_HOST__$' "\${conf}"
grep -q '^repo1-path=__PGBACKREST_REPO_PATH__$' "\${conf}"

grep -q '^repo2-retention-full-type=count$' "\${conf}"
grep -q '^repo2-retention-full=12$' "\${conf}"
grep -q '^repo2-host=__PGBACKREST_MONTHLY_REPO_HOST__$' "\${conf}"
grep -q '^repo2-path=__PGBACKREST_MONTHLY_REPO_PATH__$' "\${conf}"
grep -q '^archive-async=y$' "\${conf}"

grep -q '^archive_command=.pgbackrest --stanza=hidra archive-push %p.$' "\${archive}"
grep -q '^restore_command=.pgbackrest --stanza=hidra archive-get %f "%p".$' "\${archive}"

for script in "\${full}" "\${monthly}" "\${check}" "\${monthly_check}" "\${capture}" "\${pitr}"; do
  bash -n "\${script}"
done

grep -q '^Environment=HIDRA_PGBACKREST_CONFIG=/etc/pgbackrest/pgbackrest.conf$' "\${full_service}"
grep -q '^Environment=HIDRA_PGBACKREST_CONFIG=/etc/pgbackrest/pgbackrest.conf$' "\${monthly_service}"
grep -q -- '--repo=1 --type=full backup' "\${full}"
grep -q -- '--repo=2' "\${monthly}"
grep -q 'HIDRA-P1-BACKUP-RETENTION-001' "\${monthly}"
grep -q 'monthly-12' "\${monthly}"
grep -q '35 \* 24 \* 60 \* 60' "\${monthly_check}"
grep -q 'len(full) <= 12' "\${monthly_check}"
grep -q 'OnCalendar=monthly' "\${monthly_timer}"
grep -q 'run-monthly-retained-backup.sh' "\${monthly_service}"
grep -q 'check-monthly-retention.sh' "\${monthly_service}"

grep -q 'HIDRA_FLYWAY_SOURCE_PSQL_URI' "\${capture}"
grep -q 'version, checksum from flyway_schema_history' "\${capture}"
grep -q 'HIDRA_FLYWAY_EXPECTED_HISTORY' "\${pitr}"
grep -q 'version, checksum from flyway_schema_history' "\${pitr}"
grep -q 'cmp -s' "\${pitr}"
grep -q 'sha256sum' "\${pitr}"
! grep -q 'migration_count' "\${pitr}"

grep -q 'HIDRA_PITR_REPO' "\${pitr}"
grep -q -- '--repo="\${repo}"' "\${pitr}"
grep -q -- '--type=time' "\${pitr}"
grep -q -- '--target-action=promote' "\${pitr}"
grep -q 'flyway_schema_history' "\${pitr}"

echo "Production pgBackRest backup/PITR artifacts passed static validation."
