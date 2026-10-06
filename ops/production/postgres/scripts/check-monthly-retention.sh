#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_PGBACKREST_CONFIG:?Set HIDRA_PGBACKREST_CONFIG to rendered pgbackrest.conf.}"

policy_start_month="2026-10"
info_json="$(pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra --repo=2 info --output=json)"
printf '%s\n' "${info_json}"

python3 - "${info_json}" "${policy_start_month}" <<'PY'
import json
import sys
import time
from datetime import datetime, timezone

payload = json.loads(sys.argv[1])
policy_start_raw = sys.argv[2]
assert payload and payload[0].get("name") == "hidra", "hidra stanza not found in monthly repository"

backups = payload[0].get("backup", [])
full = [
    b for b in backups
    if b.get("type") == "full" and b.get("timestamp", {}).get("stop")
]
assert full, "no completed monthly full backup exists in repo2"
full.sort(key=lambda b: b["timestamp"]["stop"])

now = time.time()
latest = full[-1]
latest_stop = latest["timestamp"]["stop"]
assert latest_stop <= now + 300, "latest monthly recovery point timestamp is unexpectedly in the future"
age = now - latest_stop
assert age <= 35 * 24 * 60 * 60, (
    f"latest monthly retained recovery point is older than 35 days: {age:.0f}s"
)
assert len(full) <= 12, (
    f"repo2 contains {len(full)} full backups; retention policy is 12 monthly recovery points"
)

def ym_from_timestamp(value):
    dt = datetime.fromtimestamp(value, tz=timezone.utc)
    return (dt.year, dt.month)

def parse_ym(value):
    year, month = map(int, value.split("-"))
    assert 1 <= month <= 12
    return (year, month)

def add_month(value):
    year, month = value
    if month == 12:
        return (year + 1, 1)
    return (year, month + 1)

def month_label(value):
    return f"{value[0]:04d}-{value[1]:02d}"

policy_start = parse_ym(policy_start_raw)
current_month = ym_from_timestamp(now)
assert policy_start <= current_month, "retention policy start month is in the future"

expected = []
cursor = policy_start
while cursor <= current_month:
    expected.append(cursor)
    cursor = add_month(cursor)
if len(expected) > 12:
    expected = expected[-12:]

actual = [ym_from_timestamp(b["timestamp"]["stop"]) for b in full]
assert len(actual) == len(set(actual)), (
    "repo2 contains more than one completed full recovery point in the same UTC calendar month"
)

expected_set = set(expected)
actual_set = set(actual)
missing = sorted(expected_set - actual_set)
assert not missing, (
    "repo2 is missing required UTC monthly recovery points: "
    + ", ".join(month_label(v) for v in missing)
)

mode = "MATURE" if len(expected) == 12 else "BOOTSTRAP"
if mode == "MATURE":
    assert len(full) == 12, (
        f"mature repo2 must contain exactly 12 full backups, found {len(full)}"
    )
    assert actual_set == expected_set, (
        "mature repo2 monthly membership differs from the required latest 12 UTC calendar months"
    )

print(f"Retention coverage mode: {mode}")
print(f"Policy start month: {policy_start_raw}")
print("Required UTC months: " + ", ".join(month_label(v) for v in expected))
print("Retained UTC months: " + ", ".join(month_label(v) for v in actual))
print(f"Monthly repo2 full backups retained: {len(full)}")
print(f"Latest monthly recovery-point age: {age:.0f}s")
PY

pgbackrest --config="${HIDRA_PGBACKREST_CONFIG}" --stanza=hidra check
