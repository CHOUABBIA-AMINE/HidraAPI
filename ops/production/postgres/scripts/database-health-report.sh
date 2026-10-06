#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_DB_PSQL_URI:?Set HIDRA_DB_PSQL_URI to the approved stable PostgreSQL endpoint URI.}"
: "${HIDRA_PATRONI_CONFIG:?Set HIDRA_PATRONI_CONFIG to rendered patroni.yml.}"
: "${HIDRA_PATRONI_CLUSTER:?Set HIDRA_PATRONI_CLUSTER to the Patroni cluster name.}"
: "${HIDRA_PGBACKREST_CONFIG:?Set HIDRA_PGBACKREST_CONFIG to rendered pgBackRest configuration.}"

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../../.." && pwd)"
sql_dir="${root}/ops/production/postgres/sql"
script_dir="${root}/ops/production/postgres/scripts"

run_sql() {
  local file="$1"
  echo "=== ${file##*/} ==="
  psql "${HIDRA_DB_PSQL_URI}" -v ON_ERROR_STOP=1 -f "${file}"
}

echo "UTC database operations health start: $(date -u --iso-8601=seconds)"
run_sql "${sql_dir}/inspect-sessions.sql"
run_sql "${sql_dir}/inspect-locks.sql"
run_sql "${sql_dir}/inspect-long-running.sql"
run_sql "${sql_dir}/inspect-vacuum-analyze.sql"
run_sql "${sql_dir}/inspect-index-usage.sql"
run_sql "${sql_dir}/inspect-index-health.sql"
run_sql "${sql_dir}/inspect-bloat-space-risk.sql"
run_sql "${sql_dir}/inspect-capacity.sql"
run_sql "${sql_dir}/inspect-replication.sql"
run_sql "${sql_dir}/inspect-flyway.sql"

if [[ -n "${HIDRA_POSTGRES_DATA_PATH:-}" ]]; then
  echo "=== host/filesystem capacity ==="
  "${script_dir}/inspect-host-capacity.sh"
else
  echo "Host/filesystem capacity not executed: set HIDRA_POSTGRES_DATA_PATH on the database host to retain filesystem evidence."
fi

echo "=== Patroni topology ==="
patronictl -c "${HIDRA_PATRONI_CONFIG}" list "${HIDRA_PATRONI_CLUSTER}"

echo "=== pgBackRest check ==="
pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra check
pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra info

echo "UTC database operations health end: $(date -u --iso-8601=seconds)"
