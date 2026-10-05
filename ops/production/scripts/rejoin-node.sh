#!/usr/bin/env bash
set -euo pipefail

node="${1:-}"
socket="${HIDRA_HAPROXY_SOCKET:-/run/haproxy/hidra-admin.sock}"

case "${node}" in
  hidra-api-1|hidra-api-2) ;;
  *)
    echo "usage: $0 hidra-api-1|hidra-api-2" >&2
    exit 2
    ;;
esac

command -v socat >/dev/null 2>&1 || {
  echo "socat is required to use the HAProxy runtime socket." >&2
  exit 1
}

printf 'set server hidra_rest/%s state ready\n' "${node}" | socat - "UNIX-CONNECT:${socket}"

if [[ "${node}" == "hidra-api-1" ]]; then
  printf 'set server hidra_realtime/%s state ready\n' "${node}" | socat - "UNIX-CONNECT:${socket}"
fi

echo "${node} is eligible for HAProxy health checks and traffic when ready."
