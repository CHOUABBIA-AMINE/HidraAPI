#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_DB_PSQL_URI:?Set HIDRA_DB_PSQL_URI to the approved stable PostgreSQL endpoint URI.}"
: "${HIDRA_DB_MAINTENANCE_EXERCISE:?Set HIDRA_DB_MAINTENANCE_EXERCISE=YES only for an approved production-equivalent maintenance exercise.}"
: "${HIDRA_DB_MAINTENANCE_ACTION:?Set HIDRA_DB_MAINTENANCE_ACTION to vacuum-analyze or reindex-concurrently.}"
: "${HIDRA_DB_MAINTENANCE_TARGET:?Set HIDRA_DB_MAINTENANCE_TARGET to the reviewed schema.table or schema.index target.}"
: "${HIDRA_APP_ACCEPTANCE_URL:?Set HIDRA_APP_ACCEPTANCE_URL to an approved authenticated database-backed HidraAPI read endpoint.}"
: "${HIDRA_APP_CURL_CONFIG:?Set HIDRA_APP_CURL_CONFIG to an external readable curl config containing approved authentication.}"

[[ "${HIDRA_DB_MAINTENANCE_EXERCISE}" == "YES" ]] || { echo "Refusing maintenance exercise without HIDRA_DB_MAINTENANCE_EXERCISE=YES." >&2; exit 2; }
[[ -r "${HIDRA_APP_CURL_CONFIG}" ]] || { echo "Application acceptance curl config is not readable." >&2; exit 2; }

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../../.." && pwd)"
script_dir="${root}/ops/production/postgres/scripts"
sql_dir="${root}/ops/production/postgres/sql"
evidence_dir="${HIDRA_DB_MAINTENANCE_EVIDENCE_DIR:-./target/database-operations-evidence}"
timestamp="$(date -u +%Y%m%dT%H%M%SZ)"
evidence="${evidence_dir}/database-maintenance-${timestamp}.log"
mkdir -p "${evidence_dir}"
exec > >(tee -a "${evidence}") 2>&1

acceptance() {
  curl --config "${HIDRA_APP_CURL_CONFIG}" --fail --silent --show-error --output /dev/null "${HIDRA_APP_ACCEPTANCE_URL}"
}

run_sql() {
  psql "${HIDRA_DB_PSQL_URI}" -v ON_ERROR_STOP=1 -f "$1"
}

echo "HidraAPI production-equivalent database maintenance validation"
echo "UTC start: $(date -u --iso-8601=seconds)"
echo "Action: ${HIDRA_DB_MAINTENANCE_ACTION}"
echo "Target: ${HIDRA_DB_MAINTENANCE_TARGET}"
echo "Authentication material is supplied only through the external curl config and is not printed."

acceptance
echo "PASS: authenticated database-backed application acceptance before maintenance."
run_sql "${sql_dir}/inspect-index-health.sql"
run_sql "${sql_dir}/inspect-bloat-space-risk.sql"
run_sql "${sql_dir}/inspect-capacity.sql"

case "${HIDRA_DB_MAINTENANCE_ACTION}" in
  vacuum-analyze)
    HIDRA_DB_MAINTENANCE_EXECUTE=YES HIDRA_DB_PSQL_URI="${HIDRA_DB_PSQL_URI}" \
      "${script_dir}/vacuum-analyze-table.sh" "${HIDRA_DB_MAINTENANCE_TARGET}"
    ;;
  reindex-concurrently)
    HIDRA_DB_MAINTENANCE_EXECUTE=YES HIDRA_DB_PSQL_URI="${HIDRA_DB_PSQL_URI}" \
      "${script_dir}/reindex-concurrently.sh" "${HIDRA_DB_MAINTENANCE_TARGET}"
    ;;
  *)
    echo "Unsupported HIDRA_DB_MAINTENANCE_ACTION: ${HIDRA_DB_MAINTENANCE_ACTION}" >&2
    exit 2
    ;;
esac

run_sql "${sql_dir}/inspect-index-health.sql"
run_sql "${sql_dir}/inspect-bloat-space-risk.sql"
run_sql "${sql_dir}/inspect-capacity.sql"
acceptance
echo "PASS: authenticated database-backed application acceptance after maintenance."
echo "UTC end: $(date -u --iso-8601=seconds)"
echo "Evidence: ${evidence}"
