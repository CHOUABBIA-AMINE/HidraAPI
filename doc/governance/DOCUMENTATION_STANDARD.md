# HidraAPI Documentation Standard

## Status

CURRENT — canonical governance rule.

## Purpose

Define the minimum evidence standard for maintained documents under `doc/`.

## Required Metadata

Every canonical document must identify, directly or through its containing index:

- status;
- accountable owner or required owner decision;
- implementation baseline/applicability;
- source evidence;
- current versus target statements;
- unresolved decisions;
- last verification point.

## Evidence Rules

1. CURRENT implementation claims require source/configuration/migration/test evidence.
2. Runtime OpenAPI generation is not equivalent to a version-controlled API contract.
3. Historical execution results are not current validation.
4. TARGET architecture is not current implementation.
5. DEFERRED capability remains deferred until implementation evidence exists.
6. Unknown values remain explicit `TBD`, `UNVERIFIED`, or `NOT ESTABLISHED`; values are never guessed.
7. Legacy `docs/` content may feed canonical documents only after reconciliation against current evidence.
8. Roadmaps may control execution, but permanent architecture/domain decisions must move into the appropriate canonical document.
9. No canonical document may claim TimescaleDB, PostGIS, HA, DR, MQTT/Sparkplug, solver execution, AI inference, or automatic control without evidence required by the Ultimate Roadmap.

## Canonical Roots

- `doc/` — canonical maintained documentation.
- `docs/` — legacy/reference/evidence estate.

## Roadmap Authority

`doc/roadmap/ULTIMATE_ROADMAP.md` governs platform-finalization sequencing and task codes.
