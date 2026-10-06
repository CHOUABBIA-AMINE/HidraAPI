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
: "${HIDRA_APP_DB_ACCEPTANCE_URL:?Set HIDRA_APP_DB_ACCEPTANCE_URL to an approved authenticated database-backed HidraAPI read endpoint.}"
: "${HIDRA_APP_CURL_CONFIG:?Set HIDRA_APP_CURL_CONFIG to a readable external curl config containing approved authentication.}"

if [[ ! -r "${HIDRA_APP_CURL_CONFIG}" ]]; then
  echo "HidraAPI authenticated curl configuration is not readable: ${HIDRA_APP_CURL_CONFIG}" >&2
  exit 2
fi

if [[ "${HIDRA_DB_DESTRUCTIVE_EXERCISE:-}" != "YES" ]]; then
  echo "Refusing controlled database role change without HIDRA_DB_DESTRUCTIVE_EXERCISE=YES." >&2
  exit 2
fi

port="${HIDRA_DB_STABLE_PORT:-5432}"
hikari_metric_url="${HIDRA_APP_HIKARI_CREATION_METRIC_URL:-${HIDRA_APP_BASE_URL%/}/actuator/metrics/hikaricp.connections.creation}"
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

record_fencing_context() {
  local label="$1"
  echo "=== ${label}: Patroni/etcd authority context ==="
  patronictl -c "${HIDRA_PATRONI_CONFIG}" show-config "${HIDRA_PATRONI_CLUSTER}"
  cluster_json | python3 -c 'import json,sys; rows=json.load(sys.stdin); primaries=[r for r in rows if str(r.get("Role","")).lower() in {"leader","primary"}]; assert len(primaries)==1, f"expected exactly one Patroni leader/primary, got {len(primaries)}"; print(json.dumps({"primary": primaries[0].get("Member"), "members": rows}, sort_keys=True))'
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

application_acceptance() {
  curl --config "${HIDRA_APP_CURL_CONFIG}" --fail --silent --show-error --output /dev/null "${HIDRA_APP_DB_ACCEPTANCE_URL}"
}

hikari_creation_count() {
  curl --config "${HIDRA_APP_CURL_CONFIG}" --fail --silent --show-error "${hikari_metric_url}" |
    python3 -c 'import json,sys; d=json.load(sys.stdin); values=[m.get("value") for m in d.get("measurements",[]) if m.get("statistic")=="COUNT"]; assert len(values)==1, f"expected one COUNT measurement, got {len(values)}"; print(int(float(values[0])))'
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

wait_for_application_pool_recovery() {
  local baseline_count="$1"
  local start_epoch="$2"
  local attempt current_count elapsed
  for attempt in $(seq 1 60); do
    if application_acceptance; then
      current_count="$(hikari_creation_count)"
      if [[ "${current_count}" -gt "${baseline_count}" ]]; then
        elapsed="$(( $(date +%s) - start_epoch ))"
        echo "PASS: authenticated database-backed HidraAPI acceptance recovered after failover."
        echo "PASS: Hikari connection creation count increased from ${baseline_count} to ${current_count}."
        echo "Application database recovery elapsed seconds: ${elapsed}"
        return 0
      fi
    fi
    sleep 2
  done
  echo "HidraAPI did not demonstrate authenticated database-backed acceptance plus Hikari connection replacement within the exercise wait bound." >&2
  return 1
}

echo "UTC start: $(date -u --iso-8601=seconds)"
echo "Initial Patroni topology:"
cluster_json
record_fencing_context "pre-switchover"
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

echo "Verifying authenticated database-backed HidraAPI acceptance before switchover."
application_acceptance
hikari_before="$(hikari_creation_count)"
echo "Pre-switchover Hikari connection creation count: ${hikari_before}"

echo "Executing controlled Patroni switchover."
recovery_start_epoch="$(date +%s)"
recovery_start_utc="$(date -u --iso-8601=seconds)"
echo "Application recovery measurement start: ${recovery_start_utc}"
patronictl -c "${HIDRA_PATRONI_CONFIG}" switchover "${HIDRA_PATRONI_CLUSTER}" \
  --primary "${old_primary}" --candidate "${HIDRA_PATRONI_CANDIDATE}" --force

wait_for_candidate

echo "Post-switchover Patroni topology:"
cluster_json
record_fencing_context "post-switchover"
new_primary="$(primary_name)"
[[ "${new_primary}" == "${HIDRA_PATRONI_CANDIDATE}" ]]

echo "Verifying stable endpoint and new direct database connections after switchover."
verify_writable_endpoint

echo "Verifying HidraAPI-managed pool replacement and authenticated database-backed recovery."
wait_for_application_pool_recovery "${hikari_before}" "${recovery_start_epoch}"

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
echo "Interrupted or in-doubt transactions are NOT treated as successful and are NOT blindly retried by this exercise."
echo "Retry remains the caller/use-case responsibility only where explicit idempotency and transaction semantics allow it."
echo "Control-plane fencing evidence above records the Patroni/etcd authority view before and after switchover."
echo "A partition/fencing exercise remains required by HPR-P1-029; this controlled switchover does not claim partition validation."
echo "UTC end: $(date -u --iso-8601=seconds)"
echo "PASS: controlled role change preserved single-primary topology, stable-endpoint writability, HidraAPI readiness, authenticated database-backed acceptance, and observed Hikari connection replacement."
echo "Evidence: ${evidence}"
