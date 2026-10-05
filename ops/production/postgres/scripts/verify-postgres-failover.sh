#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_PATRONI_CONFIG:?Set HIDRA_PATRONI_CONFIG to rendered patroni.yml.}"
: "${HIDRA_PATRONI_CLUSTER:?Set HIDRA_PATRONI_CLUSTER to the Patroni cluster scope.}"
: "${HIDRA_PATRONI_CANDIDATE:?Set HIDRA_PATRONI_CANDIDATE to the standby selected for promotion.}"
: "${HIDRA_DB_STABLE_HOST:?Set HIDRA_DB_STABLE_HOST to the HAProxy PostgreSQL endpoint.}"
: "${PGDATABASE:?Set PGDATABASE.}"
: "${PGUSER:?Set PGUSER.}"
: "${PGPASSWORD:?Set PGPASSWORD through the approved secret mechanism.}"
: "${HIDRA_APP_BASE_URL:?Set HIDRA_APP_BASE_URL to the HAProxy-served HidraAPI URL.}"

if [[ "${HIDRA_DB_DESTRUCTIVE_EXERCISE:-}" != "YES" ]]; then
  echo "Refusing controlled database role change without HIDRA_DB_DESTRUCTIVE_EXERCISE=YES." >&2
  exit 2
fi

port="${HIDRA_DB_STABLE_PORT:-5432}"
evidence_dir="${HIDRA_DB_EVIDENCE_DIR:-./target/db-ha-evidence}"
stamp="$(date -u +%Y%m%dT%H%M%SZ)"
evidence="${evidence_dir}/postgres-failover-${stamp}.log"
mkdir -p "${evidence_dir}"
exec > >(tee -a "${evidence}") 2>&1

cluster_json() {
  patronictl -c "${HIDRA_PATRONI_CONFIG}" list "${HIDRA_PATRONI_CLUSTER}" --format json
}

primary_name() {
  cluster_json | python3 -c 'import json,sys; rows=json.load(sys.stdin); p=[r for r in rows if str(r.get("Role","")).lower() in {"leader","primary"}]; assert len(p)==1, f"expected exactly one primary, got {len(p)}"; print(p[0]["Member"])'
}

verify_writable_endpoint() {
  psql "host=${HIDRA_DB_STABLE_HOST} port=${port} dbname=${PGDATABASE} user=${PGUSER}" -v ON_ERROR_STOP=1 <<'SQL'
SELECT pg_is_in_recovery() AS must_be_false;
BEGIN;
CREATE TEMP TABLE hidra_ha_probe(id integer primary key, value text);
INSERT INTO hidra_ha_probe(id, value) VALUES (1, 'writable');
SELECT count(*) AS inserted_rows FROM hidra_ha_probe;
ROLLBACK;
SQL
}

wait_for_candidate() {
  local attempts=0
  while true; do
    local current
    current="$(primary_name)"
    if [[ "${current}" == "${HIDRA_PATRONI_CANDIDATE}" ]]; then
      break
    fi
    attempts=$((attempts + 1))
    if [[ "${attempts}" -ge 60 ]]; then
      echo "Candidate was not promoted within the exercise wait bound." >&2
      exit 1
    fi
    sleep 2
  done
}

echo "UTC start: $(date -u --iso-8601=seconds)"
echo "Initial Patroni topology:"
cluster_json
old_primary="$(primary_name)"
export OLD_PRIMARY="${old_primary}"
echo "Initial primary: ${old_primary}"
[[ "${old_primary}" != "${HIDRA_PATRONI_CANDIDATE}" ]] || {
  echo "Candidate is already primary; choose the standby member." >&2
  exit 2
}

echo "Verifying stable endpoint before switchover."
verify_writable_endpoint
curl --fail --silent --show-error "${HIDRA_APP_BASE_URL%/}/actuator/health/readiness"
echo

echo "Executing controlled Patroni switchover."
patronictl -c "${HIDRA_PATRONI_CONFIG}" switchover "${HIDRA_PATRONI_CLUSTER}"   --primary "${old_primary}" --candidate "${HIDRA_PATRONI_CANDIDATE}" --force

wait_for_candidate

echo "Post-switchover Patroni topology:"
cluster_json
new_primary="$(primary_name)"
[[ "${new_primary}" == "${HIDRA_PATRONI_CANDIDATE}" ]]

echo "Verifying stable endpoint and new connections after switchover."
verify_writable_endpoint

echo "Verifying HidraAPI readiness after database role change."
for attempt in $(seq 1 60); do
  if curl --fail --silent "${HIDRA_APP_BASE_URL%/}/actuator/health/readiness" >/dev/null; then
    break
  fi
  if [[ "${attempt}" -eq 60 ]]; then
    echo "HidraAPI did not recover readiness within the exercise wait bound." >&2
    exit 1
  fi
  sleep 2
done
curl --fail --silent --show-error "${HIDRA_APP_BASE_URL%/}/actuator/health/readiness"
echo

echo "Former primary status must no longer be primary:"
cluster_json | python3 -c 'import json,sys,os; rows=json.load(sys.stdin); old=os.environ["OLD_PRIMARY"]; row=next(r for r in rows if r["Member"]==old); role=str(row.get("Role","")).lower(); assert role not in {"leader","primary"}, f"former primary still reports role {role}"; print(row)'
echo "UTC end: $(date -u --iso-8601=seconds)"
echo "PASS: controlled role change preserved single-primary topology, stable-endpoint writability, and HidraAPI readiness."
echo "Evidence: ${evidence}"
