# HidraAPI Canonical Domain Semantics

## Status

CURRENT — canonical domain-semantic index established by HPR-P2-003.

## Applicability

Repository: `CHOUABBIA-AMINE/HidraAPI`

Verification baseline: `b11cec49de45137417d0aeab2199958166284032`

This set is derived from current domain model, value, policy and service source plus current architecture enforcement. Legacy `docs/**` semantic material remains evidence/history and does not override current source or this canonical baseline.

## Metadata Inheritance

Unless a document explicitly says otherwise, documents in this directory inherit:

- status: CURRENT description of the verified source baseline, with TARGET/DEFERRED sections explicitly labelled;
- accountable ownership: the owning business module for domain semantics; HPR-P2-003 does not invent a named human owner;
- implementation applicability: the current `dz.sh.hidra.modules.*` source tree at the verification baseline above;
- source evidence: domain model/value/policy/service classes, current architecture contracts and roadmap state;
- unresolved decisions: explicitly marked NOT ESTABLISHED, DEFERRED or delegated to later HPR tasks;
- last verification point: HPR-P2-003 execution baseline above.

## Canonical Set

- `UBIQUITOUS_LANGUAGE.md` — canonical vocabulary grounded in current source terminology.
- `DOMAIN_OWNERSHIP.md` — ownership anchors for all 24 current module roots.
- `TOPOLOGY_TELEMETRY.md` — topology graph and telemetry trust semantics.
- `ALARM_INCIDENT_LEAK.md` — alarm lifecycle, incident governance and leak-detection semantics.
- `ASSETS_INTEGRITY.md` — maintainable-asset/maintenance and pipeline-integrity boundaries.
- `SIMULATION_ANALYTICS_AI.md` — simulation/analytics semantics and the explicit limit on current AI claims.

## Scope Boundary

HPR-P2-003 records semantic meaning already supported by source. It does not:

- replace the per-module current-state documents under `doc/modules/**`; HPR-P2-004 owns those module inventories;
- change Java/domain behavior;
- resolve or restart legacy HMR/HMSR obligations assigned to HPR-P2-007/008;
- invent SCADA/PLC/RTU/SIS/ESD integration behavior;
- claim a runtime digital twin, autonomous AI, automated operational actuation or model inference without implementation evidence.
