#!/usr/bin/env bash
set -euo pipefail

node="${1:-}"
survivor="${2:-}"
socket="${HIDRA_HAPROXY_SOCKET:-/run/haproxy/hidra-admin.sock}"
drain_timeout="${HIDRA_HA_DRAIN_TIMEOUT_SECONDS:-60}"

case "${node}" in hidra-api-1|hidra-api-2) ;; *) echo "usage: $0 hidra-api-1|hidra-api-2 hidra-api-1|hidra-api-2" >&2; exit 2 ;; esac
case "${survivor}" in hidra-api-1|hidra-api-2) ;; *) echo "survivor node is required" >&2; exit 2 ;; esac
[[ "${node}" != "${survivor}" ]] || { echo "target and survivor must differ" >&2; exit 2; }
command -v socat >/dev/null 2>&1 || { echo "socat is required to use the HAProxy runtime socket." >&2; exit 1; }

stat_value() {
  local backend="$1" server="$2" field="$3"
  printf 'show stat\n' | socat - "UNIX-CONNECT:${socket}" | python3 -c 'import csv,sys; b,s,f=sys.argv[1:4]; rows=csv.DictReader(sys.stdin); row=next((r for r in rows if r.get("# pxname", r.get("pxname"))==b and r.get("svname")==s), None); assert row is not None, f"missing {b}/{s}"; print(row[f])' "${backend}" "${server}" "${field}"
}

survivor_status="$(stat_value hidra_rest "${survivor}" status)"
[[ "${survivor_status}" == UP* ]] || { echo "Refusing drain: survivor ${survivor} status is ${survivor_status}." >&2; exit 1; }

printf 'set server hidra_rest/%s state drain\n' "${node}" | socat - "UNIX-CONNECT:${socket}"
if [[ "${node}" == "hidra-api-1" ]]; then
  printf 'set server hidra_realtime/%s state drain\n' "${node}" | socat - "UNIX-CONNECT:${socket}"
fi

for attempt in $(seq 0 "${drain_timeout}"); do
  rest_scur="$(stat_value hidra_rest "${node}" scur)"
  realtime_scur="0"
  if [[ "${node}" == "hidra-api-1" ]]; then realtime_scur="$(stat_value hidra_realtime "${node}" scur)"; fi
  if [[ "${rest_scur}" -eq 0 && "${realtime_scur}" -eq 0 ]]; then
    echo "${node} drained with zero active HAProxy sessions; survivor ${survivor} remains ${survivor_status}."
    exit 0
  fi
  [[ "${attempt}" -lt "${drain_timeout}" ]] || { echo "Drain timeout: ${node} still has REST=${rest_scur}, realtime=${realtime_scur} active sessions." >&2; exit 1; }
  sleep 1
done
