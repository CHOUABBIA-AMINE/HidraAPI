#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../.." && pwd)"
cfg="${root}/ops/production/haproxy/hidra-api.cfg"
unit="${root}/ops/production/systemd/hidra-api.service"
node_a="${root}/ops/production/env/node-a.env"
node_b="${root}/ops/production/env/node-b.env"
exercise="${root}/ops/production/scripts/verify-application-ha.sh"

rest_count="$(awk '
  /^backend hidra_rest$/ { in_backend=1; next }
  /^backend / { in_backend=0 }
  in_backend && /^[[:space:]]+server hidra-api-/ { count++ }
  END { print count+0 }
' "${cfg}")"

realtime_count="$(awk '
  /^backend hidra_realtime$/ { in_backend=1; next }
  /^backend / { in_backend=0 }
  in_backend && /^[[:space:]]+server hidra-api-/ { count++ }
  END { print count+0 }
' "${cfg}")"

[[ "${rest_count}" -eq 2 ]] || {
  echo "Expected exactly two HidraAPI REST backend nodes, found ${rest_count}." >&2
  exit 1
}

[[ "${realtime_count}" -eq 1 ]] || {
  echo "Expected exactly one P1 realtime backend node, found ${realtime_count}." >&2
  exit 1
}

grep -Fq 'option httpchk GET /actuator/health/readiness' "${cfg}"
grep -Fq 'balance roundrobin' "${cfg}"

if grep -Eq '^[[:space:]]*(cookie|stick-table|stick[[:space:]]+on)([[:space:]]|$)' "${cfg}"; then
  echo "REST HAProxy configuration must not introduce sticky-session correctness." >&2
  exit 1
fi

grep -Fqx 'HIDRA_REALTIME_ENABLED=true' "${node_a}"
grep -Fqx 'HIDRA_REALTIME_ENABLED=false' "${node_b}"
grep -Fqx 'Restart=on-failure' "${unit}"
grep -Fqx 'KillSignal=SIGTERM' "${unit}"

grep -Fq 'HIDRA_APP_NODE_1_BASE_URL' "${exercise}"
grep -Fq 'HIDRA_APP_NODE_2_BASE_URL' "${exercise}"
grep -Fq 'HIDRA_HA_ACCEPTANCE_URL' "${exercise}"
grep -Fq 'HIDRA_HA_ACCEPTANCE_CURL_CONFIG' "${exercise}"
grep -Fq 'start_continuity_probe' "${exercise}"
grep -Fq 'stop_continuity_probe' "${exercise}"
grep -Fq 'verify_both_nodes_ready' "${exercise}"
grep -Fq 'Authentication material is supplied only through the external curl config and is not printed' "${exercise}"

for script in "${root}"/ops/production/scripts/*.sh; do
  bash -n "${script}"
done

echo "Production application HA runtime artifacts passed static validation."
