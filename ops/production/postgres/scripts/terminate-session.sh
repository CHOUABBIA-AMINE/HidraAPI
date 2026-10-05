#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_DB_PSQL_URI:?Set HIDRA_DB_PSQL_URI.}"
pid="${1:-}"

[[ "${pid}" =~ ^[0-9]+$ ]] || { echo "usage: $0 <pid>" >&2; exit 2; }

if [[ "${HIDRA_DB_TERMINATE_SESSION:-}" != "YES" ]]; then
  echo "Refusing session termination without HIDRA_DB_TERMINATE_SESSION=YES." >&2
  exit 2
fi

psql "${HIDRA_DB_PSQL_URI}" -v ON_ERROR_STOP=1 -v target_pid="${pid}" <<'SQL'
SELECT pid,usename,application_name,client_addr,state,xact_start,query_start,left(query,500) AS query
FROM pg_stat_activity
WHERE pid=:'target_pid'::integer;

SELECT pg_terminate_backend(:'target_pid'::integer)
WHERE :'target_pid'::integer<>pg_backend_pid();
SQL
