#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_HA_BASE_URL:?Set HIDRA_HA_BASE_URL to the HAProxy-served HidraAPI base URL.}"
: "${HIDRA_APP_NODE_1_SSH:?Set HIDRA_APP_NODE_1_SSH to the SSH target for hidra-api-1.}"
: "${HIDRA_APP_NODE_2_SSH:?Set HIDRA_APP_NODE_2_SSH to the SSH target for hidra-api-2.}"

if [[ "${HIDRA_HA_DESTRUCTIVE_EXERCISE:-}" != "YES" ]]; then
  echo "Refusing to stop application nodes without HIDRA_HA_DESTRUCTIVE_EXERCISE=YES." >&2
  exit 2
fi

evidence_dir="${HIDRA_HA_EVIDENCE_DIR:-./target/ha-evidence}"
timestamp="$(date -u +%Y%m%dT%H%M%SZ)"
evidence="${evidence_dir}/application-ha-${timestamp}.log"
mkdir -p "${evidence_dir}"

exec > >(tee -a "${evidence}") 2>&1

restore_nodes() {
  ssh "${HIDRA_APP_NODE_1_SSH}" 'sudo systemctl start hidra-api' || true
  ssh "${HIDRA_APP_NODE_2_SSH}" 'sudo systemctl start hidra-api' || true
}
trap restore_nodes EXIT

probe() {
  local label="$1"
  echo "=== ${label} ==="
  date -u --iso-8601=seconds
  curl --fail --silent --show-error "${HIDRA_HA_BASE_URL%/}/actuator/health/readiness"
  echo
}

wait_for_service() {
  local attempts=0
  until curl --fail --silent "${HIDRA_HA_BASE_URL%/}/actuator/health/readiness" >/dev/null; do
    attempts=$((attempts + 1))
    if [[ "${attempts}" -ge 30 ]]; then
      echo "HidraAPI did not become ready through HAProxy within the exercise wait bound." >&2
      exit 1
    fi
    sleep 2
  done
}

echo "HidraAPI application HA exercise"
echo "UTC start: $(date -u --iso-8601=seconds)"
echo "Base URL: ${HIDRA_HA_BASE_URL}"
probe "baseline with both nodes expected available"

echo "Stopping hidra-api-1 to simulate node loss."
ssh "${HIDRA_APP_NODE_1_SSH}" 'sudo systemctl stop hidra-api'
wait_for_service
probe "service through HAProxy with hidra-api-1 stopped"

echo "Restoring hidra-api-1."
ssh "${HIDRA_APP_NODE_1_SSH}" 'sudo systemctl start hidra-api'
wait_for_service
probe "service after hidra-api-1 rejoin"

echo "Stopping hidra-api-2 to simulate node loss."
ssh "${HIDRA_APP_NODE_2_SSH}" 'sudo systemctl stop hidra-api'
wait_for_service
probe "service through HAProxy with hidra-api-2 stopped"

echo "Restoring hidra-api-2."
ssh "${HIDRA_APP_NODE_2_SSH}" 'sudo systemctl start hidra-api'
wait_for_service
probe "service after hidra-api-2 rejoin"

echo "UTC end: $(date -u --iso-8601=seconds)"
echo "PASS: HAProxy-served readiness remained available during one-at-a-time application node loss."
echo "Evidence: ${evidence}"
