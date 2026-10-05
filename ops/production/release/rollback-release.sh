#!/usr/bin/env bash
set -euo pipefail

[[ "${HIDRA_ARTIFACT_ROLLBACK_COMPATIBLE:-false}" == "true" ]] || {
  echo "Artifact rollback blocked: schema compatibility not approved." >&2
  exit 2
}
[[ -L /opt/hidra/previous ]] || { echo "No previous release." >&2; exit 1; }
previous="$(readlink -f /opt/hidra/previous)"
[[ -f "${previous}/hidra.jar" ]] || exit 1

systemctl stop hidra-api
ln -sfn "${previous}" /opt/hidra/current
systemctl start hidra-api

for attempt in $(seq 1 60); do
  if curl --fail --silent http://127.0.0.1:8080/actuator/health/readiness >/dev/null; then exit 0; fi
  [[ "${attempt}" -lt 60 ]] || exit 1
  sleep 2
done
