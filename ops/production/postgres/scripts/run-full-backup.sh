#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_PGBACKREST_CONFIG:?Set HIDRA_PGBACKREST_CONFIG to rendered pgbackrest.conf.}"

pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra check
pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra --type=full backup
pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra info --output=json
