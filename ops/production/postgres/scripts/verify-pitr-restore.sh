#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_PGBACKREST_CONFIG:?Set HIDRA_PGBACKREST_CONFIG to rendered pgbackrest.conf.}"
: "${HIDRA_PITR_TARGET:?Set HIDRA_PITR_TARGET to an approved timestamp, e.g. 2026-10-05 18:00:00+01.}"
: "${HIDRA_PITR_PGDATA:?Set HIDRA_PITR_PGDATA to an empty isolated recovery data directory.}"
: "${HIDRA_FLYWAY_EXPECTED_HISTORY:?Set HIDRA_FLYWAY_EXPECTED_HISTORY to the approved current-schema version/checksum manifest.}"
repo="${HIDRA_PITR_REPO:-1}"
if [[ "${repo}" != "1" && "${repo}" != "2" ]]; then
  echo "HIDRA_PITR_REPO must be 1 (operational) or 2 (monthly retained)." >&2
  exit 2
fi

if [[ ! -r "${HIDRA_FLYWAY_EXPECTED_HISTORY}" ]]; then
  echo "Flyway expected-history manifest is not readable: ${HIDRA_FLYWAY_EXPECTED_HISTORY}" >&2
  exit 2
fi

if ! awk -F'|' '
  NF != 2 || $1 == "" || $2 !~ /^-?[0-9]+$/ { bad=1 }
  END { exit (NR == 0 || bad) ? 1 : 0 }
' "${HIDRA_FLYWAY_EXPECTED_HISTORY}"; then
  echo "Flyway expected-history manifest must contain non-empty version|checksum rows." >&2
  exit 2
fi

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
actual_history="${evidence_dir}/pitr-${stamp}-flyway-history.txt"
mkdir -p "${evidence_dir}" "${HIDRA_PITR_PGDATA}"
exec > >(tee -a "${evidence}") 2>&1

start_epoch="$(date +%s)"
echo "UTC restore start: $(date -u --iso-8601=seconds)"
echo "Target: ${HIDRA_PITR_TARGET}"
echo "Repository: repo${repo}"
echo "Expected Flyway history SHA-256:"
sha256sum "${HIDRA_FLYWAY_EXPECTED_HISTORY}"

pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" \
  --stanza=hidra \
  --repo="${repo}" \
  --pg1-path="${HIDRA_PITR_PGDATA}" \
  --type=time \
  --target="${HIDRA_PITR_TARGET}" \
  --target-action=promote \
  restore

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
psql "${HIDRA_PITR_PSQL_URI}" -v ON_ERROR_STOP=1 -c \
  "select installed_rank, version, description, checksum, success from flyway_schema_history order by installed_rank;"

psql "${HIDRA_PITR_PSQL_URI}" -v ON_ERROR_STOP=1 -AtF'|' -c \
  "select version, checksum from flyway_schema_history where success and version is not null order by installed_rank;" \
  > "${actual_history}"

if ! cmp -s "${HIDRA_FLYWAY_EXPECTED_HISTORY}" "${actual_history}"; then
  echo "Recovered Flyway version/checksum history does not match the approved current-schema manifest." >&2
  diff -u "${HIDRA_FLYWAY_EXPECTED_HISTORY}" "${actual_history}" || true
  exit 1
fi

echo "Recovered Flyway history SHA-256:"
sha256sum "${actual_history}"
echo "PASS: exact successful Flyway version/checksum history matches approved current-schema evidence."

end_epoch="$(date +%s)"
elapsed="$((end_epoch-start_epoch))"
echo "UTC validation end: $(date -u --iso-8601=seconds)"
echo "Restore/PITR technical elapsed seconds: ${elapsed}"
echo "PASS: pgBackRest restore/PITR produced a queryable recovery target with exact Flyway history acceptance."
echo "Evidence: ${evidence}"
echo "Flyway history evidence: ${actual_history}"
