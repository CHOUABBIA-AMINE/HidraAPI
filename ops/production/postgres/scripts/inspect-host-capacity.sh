#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_POSTGRES_DATA_PATH:?Set HIDRA_POSTGRES_DATA_PATH to the PostgreSQL data-directory path on the database host.}"

paths=("${HIDRA_POSTGRES_DATA_PATH}")
if [[ -n "${HIDRA_POSTGRES_WAL_PATH:-}" ]]; then
  paths+=("${HIDRA_POSTGRES_WAL_PATH}")
fi
if [[ -n "${HIDRA_PGBACKREST_LOCAL_SPOOL_PATH:-}" ]]; then
  paths+=("${HIDRA_PGBACKREST_LOCAL_SPOOL_PATH}")
fi

echo "UTC host capacity inspection start: $(date -u --iso-8601=seconds)"
for path in "${paths[@]}"; do
  [[ -e "${path}" ]] || { echo "Configured capacity-inspection path does not exist: ${path}" >&2; exit 1; }
  echo "=== filesystem capacity for ${path} ==="
  df -P -B1 "${path}"
  echo "=== inode capacity for ${path} ==="
  df -P -i "${path}"
done
echo "UTC host capacity inspection end: $(date -u --iso-8601=seconds)"
