#!/usr/bin/env bash
set -euo pipefail

: "\${HIDRA_FLYWAY_SOURCE_PSQL_URI:?Set HIDRA_FLYWAY_SOURCE_PSQL_URI to the approved current-schema PostgreSQL URI.}"
: "\${HIDRA_FLYWAY_EXPECTED_HISTORY_OUT:?Set HIDRA_FLYWAY_EXPECTED_HISTORY_OUT to a new evidence-manifest path.}"

if [[ -e "\${HIDRA_FLYWAY_EXPECTED_HISTORY_OUT}" ]]; then
  echo "Refusing to overwrite existing Flyway history evidence: \${HIDRA_FLYWAY_EXPECTED_HISTORY_OUT}" >&2
  exit 2
fi

mkdir -p "$(dirname "\${HIDRA_FLYWAY_EXPECTED_HISTORY_OUT}")"
umask 077

psql "\${HIDRA_FLYWAY_SOURCE_PSQL_URI}" -v ON_ERROR_STOP=1 -AtF'|' -c \
  "select version, checksum from flyway_schema_history where success and version is not null order by installed_rank;" \
  > "\${HIDRA_FLYWAY_EXPECTED_HISTORY_OUT}"

if ! awk -F'|' '
  NF != 2 || $1 == "" || $2 !~ /^-?[0-9]+$/ { bad=1 }
  END { exit (NR == 0 || bad) ? 1 : 0 }
' "\${HIDRA_FLYWAY_EXPECTED_HISTORY_OUT}"; then
  echo "Captured Flyway history is empty or is not an exact version|checksum manifest." >&2
  rm -f "\${HIDRA_FLYWAY_EXPECTED_HISTORY_OUT}"
  exit 1
fi

echo "Captured exact successful Flyway version/checksum manifest."
sha256sum "\${HIDRA_FLYWAY_EXPECTED_HISTORY_OUT}"
