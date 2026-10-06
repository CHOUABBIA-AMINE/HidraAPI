#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../../.." && pwd)"
sql_dir="${root}/ops/production/postgres/sql"
script_dir="${root}/ops/production/postgres/scripts"

required_sql=(
  inspect-sessions.sql
  inspect-locks.sql
  inspect-long-running.sql
  inspect-vacuum-analyze.sql
  inspect-index-usage.sql
  inspect-index-health.sql
  inspect-bloat-space-risk.sql
  inspect-capacity.sql
  inspect-replication.sql
  inspect-flyway.sql
)

for file in "${required_sql[@]}"; do
  [[ -s "${sql_dir}/${file}" ]] || { echo "Missing SQL procedure: ${file}" >&2; exit 1; }
done

grep -q 'pg_blocking_pids' "${sql_dir}/inspect-locks.sql"
grep -q 'pg_stat_activity' "${sql_dir}/inspect-sessions.sql"
grep -q 'n_dead_tup' "${sql_dir}/inspect-vacuum-analyze.sql"
grep -q 'pg_stat_user_indexes' "${sql_dir}/inspect-index-usage.sql"
grep -q 'pg_index' "${sql_dir}/inspect-index-health.sql"
grep -q 'indisvalid' "${sql_dir}/inspect-index-health.sql"
grep -q 'indisready' "${sql_dir}/inspect-index-health.sql"
grep -q 'indislive' "${sql_dir}/inspect-index-health.sql"
grep -q 'n_dead_tup' "${sql_dir}/inspect-bloat-space-risk.sql"
grep -q 'index_to_table_pct' "${sql_dir}/inspect-bloat-space-risk.sql"
grep -q 'pg_database_size' "${sql_dir}/inspect-capacity.sql"
grep -q 'pg_stat_replication' "${sql_dir}/inspect-replication.sql"
grep -q 'flyway_schema_history' "${sql_dir}/inspect-flyway.sql"

required_scripts=(
  database-health-report.sh
  inspect-host-capacity.sh
  verify-database-maintenance.sh
  vacuum-analyze-table.sh
  reindex-concurrently.sh
  terminate-session.sh
)

for file in "${required_scripts[@]}"; do
  [[ -s "${script_dir}/${file}" ]] || { echo "Missing database operations script: ${file}" >&2; exit 1; }
  bash -n "${script_dir}/${file}"
done

grep -q 'HIDRA_DB_MAINTENANCE_EXECUTE' "${script_dir}/vacuum-analyze-table.sh"
grep -q 'HIDRA_DB_MAINTENANCE_EXECUTE' "${script_dir}/reindex-concurrently.sh"
grep -q 'HIDRA_DB_TERMINATE_SESSION' "${script_dir}/terminate-session.sh"
grep -q 'HIDRA_POSTGRES_DATA_PATH' "${script_dir}/inspect-host-capacity.sh"
grep -q 'df -P -B1' "${script_dir}/inspect-host-capacity.sh"
grep -q 'HIDRA_DB_MAINTENANCE_EXERCISE' "${script_dir}/verify-database-maintenance.sh"
grep -q 'HIDRA_APP_ACCEPTANCE_URL' "${script_dir}/verify-database-maintenance.sh"
grep -q 'HIDRA_APP_CURL_CONFIG' "${script_dir}/verify-database-maintenance.sh"
grep -q 'inspect-index-health.sql' "${script_dir}/database-health-report.sh"
grep -q 'inspect-bloat-space-risk.sql' "${script_dir}/database-health-report.sh"
grep -q 'inspect-host-capacity.sh' "${script_dir}/database-health-report.sh"
grep -q 'patronictl' "${script_dir}/database-health-report.sh"
grep -q 'pgbackrest' "${script_dir}/database-health-report.sh"

if grep -RqiE 'pgstattuple|pg_stat_statements|hypopg|pg_repack' "${sql_dir}/inspect-bloat-space-risk.sql"; then
  echo "Bloat/space-risk inspection must not assume unapproved PostgreSQL extensions." >&2
  exit 1
fi

echo "Database operations procedures passed static validation."
