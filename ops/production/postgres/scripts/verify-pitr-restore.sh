#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_PGBACKREST_CONFIG:?Set HIDRA_PGBACKREST_CONFIG to rendered pgbackrest.conf.}"
: "${HIDRA_PITR_TARGET:?Set HIDRA_PITR_TARGET to an approved timestamp, e.g. 2026-10-05 18:00:00+01.}"
: "${HIDRA_PITR_PGDATA:?Set HIDRA_PITR_PGDATA to an empty isolated recovery data directory.}"

if [[ "${HIDRA_PITR_DESTRUCTIVE_EXERCISE:-}" != "YES" ]]; then
  echo "Refusing restore/PITR without HIDRA_PITR_DESTRUCTIVE_EXERCISE=YES." >&2
  exit 2
fi

if [[ -e "${HIDRA_PITR_PGDATA}" && -n "$(find "${HIDRA_PITR_PGDATA}" -mindepth 1 -maxdepth 1 -print -quit 2>/dev/null)" ]]; then
  echo "Recovery directory must be empty: ${HIDRA_PITR_PGDATA}" >&2
  exit 2
fi

evidence_dir="${HIDRA_PITR_EVIDENCE_DIR:-./target/pitr-evidence}"
stamp="$(date -u +%Y%m%dT%H%M%SZ)"
evidence="${evidence_dir}/pitr-${stamp}.log"
mkdir -p "${evidence_dir}" "${HIDRA_PITR_PGDATA}"
exec > >(tee -a "${evidence}") 2>&1

start_epoch="$(date +%s)"
echo "UTC restore start: $(date -u --iso-8601=seconds)"
echo "Target: ${HIDRA_PITR_TARGET}"

pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}"   --stanza=hidra   --pg1-path="${HIDRA_PITR_PGDATA}"   --type=time   --target="${HIDRA_PITR_TARGET}"   --target-action=promote   restore

echo "Restore files complete: $(date -u --iso-8601=seconds)"

: "${HIDRA_PITR_POSTGRES_START_CMD:?Set HIDRA_PITR_POSTGRES_START_CMD to the approved isolated PostgreSQL start command.}"
: "${HIDRA_PITR_POSTGRES_STOP_CMD:?Set HIDRA_PITR_POSTGRES_STOP_CMD to the approved isolated PostgreSQL stop command.}"
: "${HIDRA_PITR_PSQL_URI:?Set HIDRA_PITR_PSQL_URI to the isolated recovery database URI.}"

cleanup() {
  bash -lc "${HIDRA_PITR_POSTGRES_STOP_CMD}" || true
}
trap cleanup EXIT

bash -lc "${HIDRA_PITR_POSTGRES_START_CMD}"

for attempt in $(seq 1 60); do
  if psql "${HIDRA_PITR_PSQL_URI}" -Atqc 'select 1' >/dev/null 2>&1; then
    break
  fi
  if [[ "${attempt}" -eq 60 ]]; then
    echo "Recovered PostgreSQL did not become queryable within the exercise wait bound." >&2
    exit 1
  fi
  sleep 2
done

echo "Recovery state:"
psql "${HIDRA_PITR_PSQL_URI}" -v ON_ERROR_STOP=1 -c "select pg_is_in_recovery(), now();"
echo "Flyway migration baseline:"
psql "${HIDRA_PITR_PSQL_URI}" -v ON_ERROR_STOP=1 -c "select version, description, success from flyway_schema_history order by installed_rank desc limit 10;"

migration_count="$(psql "${HIDRA_PITR_PSQL_URI}" -Atqc "select count(*) from flyway_schema_history where success")"
echo "Successful Flyway migration count: ${migration_count}"
if [[ "${migration_count}" -lt 82 ]]; then
  echo "Recovered schema has fewer than the current audited 82 successful Flyway migrations." >&2
  exit 1
fi

end_epoch="$(date +%s)"
elapsed="$((end_epoch-start_epoch))"
echo "UTC validation end: $(date -u --iso-8601=seconds)"
echo "Restore/PITR technical elapsed seconds: ${elapsed}"
echo "PASS: pgBackRest restore/PITR produced a queryable current-schema recovery target."
echo "Evidence: ${evidence}"
