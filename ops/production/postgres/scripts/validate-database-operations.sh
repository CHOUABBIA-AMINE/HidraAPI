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
grep -q 'pg_database_size' "${sql_dir}/inspect-capacity.sql"
grep -q 'pg_stat_replication' "${sql_dir}/inspect-replication.sql"
grep -q 'flyway_schema_history' "${sql_dir}/inspect-flyway.sql"

for script in   "${script_dir}/database-health-report.sh"   "${script_dir}/vacuum-analyze-table.sh"   "${script_dir}/reindex-concurrently.sh"   "${script_dir}/terminate-session.sh"; do
  bash -n "${script}"
done

grep -q 'HIDRA_DB_MAINTENANCE_EXECUTE' "${script_dir}/vacuum-analyze-table.sh"
grep -q 'HIDRA_DB_MAINTENANCE_EXECUTE' "${script_dir}/reindex-concurrently.sh"
grep -q 'HIDRA_DB_TERMINATE_SESSION' "${script_dir}/terminate-session.sh"
grep -q 'patronictl' "${script_dir}/database-health-report.sh"
grep -q 'pgbackrest' "${script_dir}/database-health-report.sh"

echo "Database operations procedures passed static validation."
