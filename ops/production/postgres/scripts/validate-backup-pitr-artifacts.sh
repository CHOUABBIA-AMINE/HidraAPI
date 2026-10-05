#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../../.." && pwd)"
conf="${root}/ops/production/postgres/pgbackrest/pgbackrest.conf.tpl"
archive="${root}/ops/production/postgres/pgbackrest/postgresql-archive.conf"
full="${root}/ops/production/postgres/scripts/run-full-backup.sh"
check="${root}/ops/production/postgres/scripts/check-backup-health.sh"
pitr="${root}/ops/production/postgres/scripts/verify-pitr-restore.sh"

grep -q '^repo1-retention-full-type=time$' "${conf}"
grep -q '^repo1-retention-full=35$' "${conf}"
grep -q '^repo1-host=__PGBACKREST_REPO_HOST__$' "${conf}"
grep -q '^repo1-path=__PGBACKREST_REPO_PATH__$' "${conf}"
grep -q '^archive_command=.pgbackrest --stanza=hidra archive-push %p.$' "${archive}"
grep -q '^restore_command=.pgbackrest --stanza=hidra archive-get %f "%p".$' "${archive}"

for script in "${full}" "${check}" "${pitr}"; do
  bash -n "${script}"
done

grep -q -- '--type=full backup' "${full}"
grep -q '24\*60\*60' "${check}"
grep -q -- '--type=time' "${pitr}"
grep -q -- '--target-action=promote' "${pitr}"
grep -q 'flyway_schema_history' "${pitr}"

if grep -Eq 'repo1-retention-full=[0-9]+.*monthly|monthly.*repo1-retention-full=[0-9]+' "${conf}"; then
  echo "Monthly long-term retention must not be invented in repository configuration." >&2
  exit 1
fi

echo "Production pgBackRest backup/PITR artifacts passed static validation."
