#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_PGBACKREST_CONFIG:?Set HIDRA_PGBACKREST_CONFIG to rendered pgbackrest.conf.}"

info_json="$(pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra info --output=json)"
printf '%s\n' "${info_json}"

python3 - "${info_json}" <<'PY'
import json, sys, time
payload=json.loads(sys.argv[1])
assert payload and payload[0].get("name") == "hidra", "hidra stanza not found"
backups=payload[0].get("backup", [])
assert backups, "no completed pgBackRest backup exists"
completed=[b for b in backups if b.get("timestamp", {}).get("stop")]
assert completed, "no completed backup found"
latest=max(completed, key=lambda b: b["timestamp"]["stop"])
age=time.time()-latest["timestamp"]["stop"]
assert age <= 24*60*60, f"latest completed backup is older than 24h: {age:.0f}s"
print(f"Latest completed backup age: {age:.0f}s")
PY

pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra check
