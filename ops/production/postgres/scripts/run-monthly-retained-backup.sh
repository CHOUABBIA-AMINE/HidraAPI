#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_PGBACKREST_CONFIG:?Set HIDRA_PGBACKREST_CONFIG to rendered pgbackrest.conf.}"

policy="HIDRA-P1-BACKUP-RETENTION-001"

pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra check
pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}"   --stanza=hidra   --repo=2   --type=full   --annotation="hidra-policy=${policy}"   --annotation="hidra-retention=monthly-12"   backup
pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra --repo=2 info --output=json
