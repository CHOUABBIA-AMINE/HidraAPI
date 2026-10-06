#!/usr/bin/env bash
set -euo pipefail

node="${1:-}"
node_ssh="${2:-}"
artifact="${3:-}"
source_sha="${4:-}"
artifact_sha256="${5:-}"

case "${node}" in hidra-api-1|hidra-api-2) ;; *) exit 2 ;; esac
: "${HIDRA_APP_NODE_1_SSH:?missing node 1 target}"
: "${HIDRA_APP_NODE_2_SSH:?missing node 2 target}"
: "${HIDRA_HA_CONTROL_SSH:?missing HAProxy control target}"
: "${HIDRA_HA_BASE_URL:?missing HA endpoint}"
: "${HIDRA_PRODUCTION_ACCEPTANCE_PATH:?missing acceptance path}"
: "${HIDRA_PRODUCTION_ACCEPTANCE_BEARER_TOKEN:?missing acceptance token}"
: "${ARTIFACT_ROLLBACK_COMPATIBLE:?missing rollback declaration}"

if [[ "${node}" == "hidra-api-1" ]]; then
  survivor="hidra-api-2"
  survivor_ssh="${HIDRA_APP_NODE_2_SSH}"
else
  survivor="hidra-api-1"
  survivor_ssh="${HIDRA_APP_NODE_1_SSH}"
fi

tmp_artifact="/tmp/hidra-api-${source_sha}.jar"
scp "${artifact}" "${node_ssh}:${tmp_artifact}"
scp ops/production/release/install-release.sh "${node_ssh}:/tmp/hidra-install-release.sh"
scp ops/production/release/rollback-release.sh "${node_ssh}:/tmp/hidra-rollback-release.sh"
scp ops/production/scripts/drain-node.sh "${HIDRA_HA_CONTROL_SSH}:/tmp/hidra-drain-node.sh"
scp ops/production/scripts/rejoin-node.sh "${HIDRA_HA_CONTROL_SSH}:/tmp/hidra-rejoin-node.sh"

remote_acceptance() {
  local ssh_target="$1"
  ssh "${ssh_target}" "curl --fail --silent --show-error --header 'Authorization: Bearer ${HIDRA_PRODUCTION_ACCEPTANCE_BEARER_TOKEN}' 'http://127.0.0.1:8080${HIDRA_PRODUCTION_ACCEPTANCE_PATH}' >/dev/null"
}

ha_acceptance() {
  curl --fail --silent --show-error "${HIDRA_HA_BASE_URL%/}/actuator/health/readiness" >/dev/null
  curl --fail --silent --show-error --header "Authorization: Bearer ${HIDRA_PRODUCTION_ACCEPTANCE_BEARER_TOKEN}" "${HIDRA_HA_BASE_URL%/}${HIDRA_PRODUCTION_ACCEPTANCE_PATH}" >/dev/null
}

echo "Verifying survivor ${survivor} before draining ${node}."
ssh "${survivor_ssh}" "curl --fail --silent http://127.0.0.1:8080/actuator/health/readiness >/dev/null"
remote_acceptance "${survivor_ssh}"
ha_acceptance

ssh "${HIDRA_HA_CONTROL_SSH}" "sudo bash /tmp/hidra-drain-node.sh '${node}' '${survivor}'"

rollback_and_fail() {
  code="$1"
  trap - ERR
  echo "Deployment failed for ${node}; attempting guarded rollback when permitted." >&2
  if [[ "${ARTIFACT_ROLLBACK_COMPATIBLE}" == "true" ]]; then
    if ssh "${node_ssh}" "sudo HIDRA_ARTIFACT_ROLLBACK_COMPATIBLE=true bash /tmp/hidra-rollback-release.sh"; then
      if ssh "${node_ssh}" "curl --fail --silent http://127.0.0.1:8080/actuator/health/readiness >/dev/null" && remote_acceptance "${node_ssh}"; then
        ssh "${HIDRA_HA_CONTROL_SSH}" "sudo bash /tmp/hidra-rejoin-node.sh '${node}'" || true
        ha_acceptance || true
      fi
    fi
  fi
  exit "${code}"
}
trap 'rollback_and_fail $?' ERR

ssh "${node_ssh}" "sudo bash /tmp/hidra-install-release.sh '${source_sha}' '${tmp_artifact}' '${artifact_sha256}'"
ssh "${node_ssh}" "curl --fail --silent http://127.0.0.1:8080/actuator/health/readiness >/dev/null"
remote_acceptance "${node_ssh}"
ssh "${HIDRA_HA_CONTROL_SSH}" "sudo bash /tmp/hidra-rejoin-node.sh '${node}'"
ha_acceptance

trap - ERR
echo "Deployment and rejoin acceptance passed for ${node}; survivor ${survivor} remained available through the drain boundary."
