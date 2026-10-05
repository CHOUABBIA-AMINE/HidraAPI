#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_DB_PSQL_URI:?Set HIDRA_DB_PSQL_URI.}"
target="${1:-}"

if [[ ! "${target}" =~ ^[a-zA-Z_][a-zA-Z0-9_]*\.[a-zA-Z_][a-zA-Z0-9_]*$ ]]; then
  echo "usage: $0 schema.table" >&2
  exit 2
fi

if [[ "${HIDRA_DB_MAINTENANCE_EXECUTE:-}" != "YES" ]]; then
  echo "Refusing VACUUM/ANALYZE without HIDRA_DB_MAINTENANCE_EXECUTE=YES." >&2
  echo "Planned command: VACUUM (ANALYZE, VERBOSE) ${target};"
  exit 2
fi

echo "Executing reviewed VACUUM (ANALYZE, VERBOSE) on ${target}."
psql "${HIDRA_DB_PSQL_URI}" -v ON_ERROR_STOP=1 -c "VACUUM (ANALYZE, VERBOSE) ${target};"
