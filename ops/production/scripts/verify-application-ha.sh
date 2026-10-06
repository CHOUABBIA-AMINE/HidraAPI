#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_HA_BASE_URL:?Set HIDRA_HA_BASE_URL to the HAProxy-served HidraAPI base URL.}"
: "${HIDRA_APP_NODE_1_BASE_URL:?Set HIDRA_APP_NODE_1_BASE_URL to direct hidra-api-1 HTTP base URL.}"
: "${HIDRA_APP_NODE_2_BASE_URL:?Set HIDRA_APP_NODE_2_BASE_URL to direct hidra-api-2 HTTP base URL.}"
: "${HIDRA_APP_NODE_1_SSH:?Set HIDRA_APP_NODE_1_SSH to the SSH target for hidra-api-1.}"
: "${HIDRA_APP_NODE_2_SSH:?Set HIDRA_APP_NODE_2_SSH to the SSH target for hidra-api-2.}"
: "${HIDRA_HA_ACCEPTANCE_URL:?Set HIDRA_HA_ACCEPTANCE_URL to an authenticated representative REST endpoint through HAProxy.}"
: "${HIDRA_HA_ACCEPTANCE_CURL_CONFIG:?Set HIDRA_HA_ACCEPTANCE_CURL_CONFIG to a readable external curl config containing approved authentication.}"

if [[ ! -r "${HIDRA_HA_ACCEPTANCE_CURL_CONFIG}" ]]; then
  echo "Acceptance curl configuration is not readable: ${HIDRA_HA_ACCEPTANCE_CURL_CONFIG}" >&2
  exit 2
fi

if [[ "${HIDRA_HA_DESTRUCTIVE_EXERCISE:-}" != "YES" ]]; then
  echo "Refusing to stop application nodes without HIDRA_HA_DESTRUCTIVE_EXERCISE=YES." >&2
  exit 2
fi

probe_interval="${HIDRA_HA_PROBE_INTERVAL_SECONDS:-1}"
evidence_dir="${HIDRA_HA_EVIDENCE_DIR:-./target/ha-evidence}"
timestamp="$(date -u +%Y%m%dT%H%M%SZ)"
evidence="${evidence_dir}/application-ha-${timestamp}.log"
continuity_log="${evidence_dir}/application-ha-${timestamp}-continuity.log"
continuity_fail="${evidence_dir}/application-ha-${timestamp}-continuity.fail"
mkdir -p "${evidence_dir}"
rm -f "${continuity_fail}"

exec > >(tee -a "${evidence}") 2>&1

continuity_pid=""

restore_nodes() {
  if [[ -n "${continuity_pid}" ]]; then
    kill "${continuity_pid}" 2>/dev/null || true
    wait "${continuity_pid}" 2>/dev/null || true
  fi
  ssh "${HIDRA_APP_NODE_1_SSH}" 'sudo systemctl start hidra-api' || true
  ssh "${HIDRA_APP_NODE_2_SSH}" 'sudo systemctl start hidra-api' || true
}
trap restore_nodes EXIT

readiness_url() {
  printf '%s/actuator/health/readiness' "${1%/}"
}

probe_readiness() {
  local label="$1"
  local base_url="$2"
  echo "=== ${label} ==="
  date -u --iso-8601=seconds
  curl --fail --silent --show-error "$(readiness_url "${base_url}")"
  echo
}

wait_for_node_ready() {
  local node_name="$1"
  local base_url="$2"
  local attempts=0
  until curl --fail --silent "$(readiness_url "${base_url}")" >/dev/null; do
    attempts=$((attempts + 1))
    if [[ "${attempts}" -ge 60 ]]; then
      echo "${node_name} did not become directly ready within the exercise wait bound." >&2
      exit 1
    fi
    sleep 2
  done
}

acceptance_probe() {
  curl --config "${HIDRA_HA_ACCEPTANCE_CURL_CONFIG}" --fail --silent --show-error --output /dev/null "${HIDRA_HA_ACCEPTANCE_URL}"
}

start_continuity_probe() {
  local label="$1"
  rm -f "${continuity_fail}"
  echo "Starting continuous authenticated REST probe: ${label}"
  (
    while true; do
      local_ts="$(date -u --iso-8601=seconds)"
      if acceptance_probe; then
        echo "${local_ts} PASS ${label}" >> "${continuity_log}"
      else
        rc="$?"
        echo "${local_ts} FAIL rc=${rc} ${label}" | tee -a "${continuity_log}"
        : > "${continuity_fail}"
      fi
      sleep "${probe_interval}"
    done
  ) &
  continuity_pid="$!"
}

stop_continuity_probe() {
  local label="$1"
  if [[ -n "${continuity_pid}" ]]; then
    kill "${continuity_pid}" 2>/dev/null || true
    wait "${continuity_pid}" 2>/dev/null || true
    continuity_pid=""
  fi
  if [[ -e "${continuity_fail}" ]]; then
    echo "Authenticated REST continuity failed during ${label}." >&2
    exit 1
  fi
  echo "PASS: authenticated REST continuity preserved during ${label}."
}

verify_both_nodes_ready() {
  wait_for_node_ready "hidra-api-1" "${HIDRA_APP_NODE_1_BASE_URL}"
  wait_for_node_ready "hidra-api-2" "${HIDRA_APP_NODE_2_BASE_URL}"
  probe_readiness "direct readiness hidra-api-1" "${HIDRA_APP_NODE_1_BASE_URL}"
  probe_readiness "direct readiness hidra-api-2" "${HIDRA_APP_NODE_2_BASE_URL}"
}

echo "HidraAPI application HA exercise"
echo "UTC start: $(date -u --iso-8601=seconds)"
echo "HAProxy base URL: ${HIDRA_HA_BASE_URL}"
echo "Representative acceptance URL: ${HIDRA_HA_ACCEPTANCE_URL}"
echo "Authentication material is supplied only through the external curl config and is not printed."

echo "Verifying both nodes are individually ready before disruption."
verify_both_nodes_ready
probe_readiness "HAProxy baseline readiness" "${HIDRA_HA_BASE_URL}"
acceptance_probe
echo "PASS: authenticated representative REST acceptance succeeded before disruption."

start_continuity_probe "hidra-api-1 loss/rejoin"
sleep "${probe_interval}"
echo "Stopping hidra-api-1 to simulate node loss."
ssh "${HIDRA_APP_NODE_1_SSH}" 'sudo systemctl stop hidra-api'
probe_readiness "HAProxy readiness with hidra-api-1 stopped" "${HIDRA_HA_BASE_URL}"
sleep "$((probe_interval * 3))"
echo "Restoring hidra-api-1."
ssh "${HIDRA_APP_NODE_1_SSH}" 'sudo systemctl start hidra-api'
wait_for_node_ready "hidra-api-1" "${HIDRA_APP_NODE_1_BASE_URL}"
probe_readiness "direct readiness after hidra-api-1 rejoin" "${HIDRA_APP_NODE_1_BASE_URL}"
sleep "$((probe_interval * 2))"
stop_continuity_probe "hidra-api-1 loss/rejoin"

echo "Verifying both nodes are ready before stopping hidra-api-2."
verify_both_nodes_ready

start_continuity_probe "hidra-api-2 loss/rejoin"
sleep "${probe_interval}"
echo "Stopping hidra-api-2 to simulate node loss."
ssh "${HIDRA_APP_NODE_2_SSH}" 'sudo systemctl stop hidra-api'
probe_readiness "HAProxy readiness with hidra-api-2 stopped" "${HIDRA_HA_BASE_URL}"
sleep "$((probe_interval * 3))"
echo "Restoring hidra-api-2."
ssh "${HIDRA_APP_NODE_2_SSH}" 'sudo systemctl start hidra-api'
wait_for_node_ready "hidra-api-2" "${HIDRA_APP_NODE_2_BASE_URL}"
probe_readiness "direct readiness after hidra-api-2 rejoin" "${HIDRA_APP_NODE_2_BASE_URL}"
sleep "$((probe_interval * 2))"
stop_continuity_probe "hidra-api-2 loss/rejoin"

echo "Final two-node readiness verification."
verify_both_nodes_ready
acceptance_probe
echo "UTC end: $(date -u --iso-8601=seconds)"
echo "PASS: both nodes were initially ready, authenticated REST continuity was preserved during each one-node loss, and each restarted node rejoined before the next disruption."
echo "Evidence: ${evidence}"
echo "Continuity evidence: ${continuity_log}"
