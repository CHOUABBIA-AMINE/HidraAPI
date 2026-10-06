#!/usr/bin/env bash
set -euo pipefail

node="${1:-}"
socket="${HIDRA_HAPROXY_SOCKET:-/run/haproxy/hidra-admin.sock}"
rejoin_timeout="${HIDRA_HA_REJOIN_TIMEOUT_SECONDS:-60}"

case "${node}" in hidra-api-1|hidra-api-2) ;; *) echo "usage: $0 hidra-api-1|hidra-api-2" >&2; exit 2 ;; esac
command -v socat >/dev/null 2>&1 || { echo "socat is required to use the HAProxy runtime socket." >&2; exit 1; }

stat_status() {
  local backend="$1" server="$2"
  printf 'show stat\n' | socat - "UNIX-CONNECT:${socket}" | python3 -c 'import csv,sys; b,s=sys.argv[1:3]; rows=csv.DictReader(sys.stdin); row=next((r for r in rows if r.get("# pxname", r.get("pxname"))==b and r.get("svname")==s), None); assert row is not None, f"missing {b}/{s}"; print(row["status"])' "${backend}" "${server}"
}

printf 'set server hidra_rest/%s state ready\n' "${node}" | socat - "UNIX-CONNECT:${socket}"
if [[ "${node}" == "hidra-api-1" ]]; then
  printf 'set server hidra_realtime/%s state ready\n' "${node}" | socat - "UNIX-CONNECT:${socket}"
fi

for attempt in $(seq 0 "${rejoin_timeout}"); do
  rest_status="$(stat_status hidra_rest "${node}")"
  realtime_ok=true
  realtime_status="N/A"
  if [[ "${node}" == "hidra-api-1" ]]; then
    realtime_status="$(stat_status hidra_realtime "${node}")"
    [[ "${realtime_status}" == UP* ]] || realtime_ok=false
  fi
  if [[ "${rest_status}" == UP* && "${realtime_ok}" == true ]]; then
    echo "${node} rejoined HAProxy: REST=${rest_status}, realtime=${realtime_status}."
    exit 0
  fi
  [[ "${attempt}" -lt "${rejoin_timeout}" ]] || { echo "Rejoin timeout for ${node}: REST=${rest_status}, realtime=${realtime_status}." >&2; exit 1; }
  sleep 1
done
