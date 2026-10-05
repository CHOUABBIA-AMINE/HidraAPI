#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_PGBACKREST_CONFIG:?Set HIDRA_PGBACKREST_CONFIG to rendered pgbackrest.conf.}"

info_json="$(pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra --repo=2 info --output=json)"
printf '%s\n' "${info_json}"

python3 - "${info_json}" <<'PY'
import json
import sys
import time

payload = json.loads(sys.argv[1])
assert payload and payload[0].get("name") == "hidra", "hidra stanza not found in monthly repository"
backups = payload[0].get("backup", [])
full = [
    b for b in backups
    if b.get("type") == "full" and b.get("timestamp", {}).get("stop")
]
assert full, "no completed monthly full backup exists in repo2"

full.sort(key=lambda b: b["timestamp"]["stop"])
latest = full[-1]
age = time.time() - latest["timestamp"]["stop"]
assert age <= 35 * 24 * 60 * 60, (
    f"latest monthly retained recovery point is older than 35 days: {age:.0f}s"
)

assert len(full) <= 12, (
    f"repo2 contains {len(full)} full backups; retention policy is 12 monthly recovery points"
)

gaps = [
    later["timestamp"]["stop"] - earlier["timestamp"]["stop"]
    for earlier, later in zip(full, full[1:])
]
if gaps:
    max_gap = max(gaps)
    assert max_gap <= 35 * 24 * 60 * 60, (
        f"monthly recovery-point gap exceeds 35 days: {max_gap:.0f}s"
    )

print(f"Monthly repo2 full backups retained: {len(full)}")
print(f"Latest monthly recovery-point age: {age:.0f}s")
PY

pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra check
