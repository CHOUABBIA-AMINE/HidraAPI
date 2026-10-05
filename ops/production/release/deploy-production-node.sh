#!/usr/bin/env bash
set -euo pipefail

node="${1:-}"
node_ssh="${2:-}"
artifact="${3:-}"
source_sha="${4:-}"
artifact_sha256="${5:-}"

case "${node}" in hidra-api-1|hidra-api-2) ;; *) exit 2 ;; esac
: "${HIDRA_HA_CONTROL_SSH:?missing HAProxy control target}"
: "${HIDRA_HA_BASE_URL:?missing HA endpoint}"
: "${HIDRA_PRODUCTION_ACCEPTANCE_PATH:?missing acceptance path}"
: "${HIDRA_PRODUCTION_ACCEPTANCE_BEARER_TOKEN:?missing acceptance token}"
: "${ARTIFACT_ROLLBACK_COMPATIBLE:?missing rollback declaration}"

tmp_artifact="/tmp/hidra-api-${source_sha}.jar"
scp "${artifact}" "${node_ssh}:${tmp_artifact}"
scp ops/production/release/install-release.sh "${node_ssh}:/tmp/hidra-install-release.sh"
scp ops/production/release/rollback-release.sh "${node_ssh}:/tmp/hidra-rollback-release.sh"
scp ops/production/scripts/drain-node.sh "${HIDRA_HA_CONTROL_SSH}:/tmp/hidra-drain-node.sh"
scp ops/production/scripts/rejoin-node.sh "${HIDRA_HA_CONTROL_SSH}:/tmp/hidra-rejoin-node.sh"

ssh "${HIDRA_HA_CONTROL_SSH}" "sudo bash /tmp/hidra-drain-node.sh '${node}'"

rollback_and_fail() {
  code="$1"
  echo "Deployment failed for ${node}; node remains drained." >&2
  if [[ "${ARTIFACT_ROLLBACK_COMPATIBLE}" == "true" ]]; then
    ssh "${node_ssh}" "sudo HIDRA_ARTIFACT_ROLLBACK_COMPATIBLE=true bash /tmp/hidra-rollback-release.sh" || true
    if ssh "${node_ssh}" "curl --fail --silent http://127.0.0.1:8080/actuator/health/readiness >/dev/null"; then
      ssh "${HIDRA_HA_CONTROL_SSH}" "sudo bash /tmp/hidra-rejoin-node.sh '${node}'" || true
    fi
  fi
  exit "${code}"
}
trap 'rollback_and_fail $?' ERR

ssh "${node_ssh}" "sudo bash /tmp/hidra-install-release.sh '${source_sha}' '${tmp_artifact}' '${artifact_sha256}'"
ssh "${node_ssh}" "curl --fail --silent http://127.0.0.1:8080/actuator/health/readiness >/dev/null"
ssh "${node_ssh}" "curl --fail --silent --header 'Authorization: Bearer ${HIDRA_PRODUCTION_ACCEPTANCE_BEARER_TOKEN}' 'http://127.0.0.1:8080${HIDRA_PRODUCTION_ACCEPTANCE_PATH}' >/dev/null"
ssh "${HIDRA_HA_CONTROL_SSH}" "sudo bash /tmp/hidra-rejoin-node.sh '${node}'"

for attempt in $(seq 1 30); do
  if curl --fail --silent "${HIDRA_HA_BASE_URL%/}/actuator/health/readiness" >/dev/null; then
    trap - ERR
    exit 0
  fi
  [[ "${attempt}" -lt 30 ]] || false
  sleep 2
done
