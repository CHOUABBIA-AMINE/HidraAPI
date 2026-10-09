# HidraAPI Canonical Domain Semantics

## Status

CURRENT — canonical domain-semantic index, refreshed by HPR-P2-009.

## Applicability

Repository: `CHOUABBIA-AMINE/HidraAPI`

Verification baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

This set is derived from current domain model, value, policy and service source plus current architecture enforcement. Legacy `docs/**` semantic material remains evidence/history and does not override current source or this canonical baseline.

## Metadata Inheritance

Unless a document explicitly says otherwise, documents in this directory inherit:

- status: CURRENT description of the verified source baseline, with TARGET/DEFERRED sections explicitly labelled;
- accountable ownership: the owning business module for domain semantics; canonicalization does not invent a named human owner;
- implementation applicability: the current `dz.sh.hidra.modules.*` source tree at the verification baseline above;
- source evidence: the linked domain/model, JPA, controlled application/repository write paths, owner contracts/providers, migrations and semantic tests in [the decision register](SEMANTIC_DECISIONS.md);
- unresolved decisions: explicitly marked NOT ESTABLISHED, DEFERRED or delegated to later HPR tasks;
- last verification point: HPR-P2-009 source verification on 2026-10-09 at the parent above; no runtime test is executed by documentation transfer.

## Canonical Set

- [Ubiquitous Language](UBIQUITOUS_LANGUAGE.md) — canonical vocabulary grounded in current source terminology.
- [Domain Ownership](DOMAIN_OWNERSHIP.md) — ownership anchors for all 24 current module roots.
- [Topology Telemetry](TOPOLOGY_TELEMETRY.md) — topology graph and telemetry trust semantics.
- [Alarm Incident Leak](ALARM_INCIDENT_LEAK.md) — alarm lifecycle, incident governance and leak-detection semantics.
- [Assets Integrity](ASSETS_INTEGRITY.md) — maintainable-asset/maintenance and pipeline-integrity boundaries.
- [Simulation, Analytics and AI](SIMULATION_ANALYTICS_AI.md) — simulation/analytics semantics and the explicit limit on current AI claims.
- [Permanent Semantic Decisions](SEMANTIC_DECISIONS.md) — 123-subject traceability and source-evidenced rules; historical verdicts are provenance rather than execution status.

## Scope Boundary

HPR-P2-003 established this domain set; HPR-P2-009 transfers lasting semantics from the 123 legacy reviews after current-source reconciliation. This set records meaning already supported by source. It does not:

- replace [module inventories](../modules/README.md), now refreshed at the same parent;
- change Java/domain behavior;
- reopen completed HMRs: HPR-P2-008 is closed, while review/commit history remains in [reconciliation](../model-remediation/RECONCILIATION.md);
- invent SCADA/PLC/RTU/SIS/ESD integration behavior;
- claim a runtime digital twin, autonomous AI, automated operational actuation or model inference without implementation evidence.

## Current verification population

The 123 historical review subjects remain represented individually: 19 APPROVED,
104 REVISE and 104 subject HMR mappings. Current source has 124 domain model types
plus 24 package descriptors; AlarmLifecycleEvent is the added model, indexed in
[Alarm](../modules/alarm.md). Owner policies/mappings for fail-closed deployment
remain explicit prerequisites, not inferred business facts. Semantic transfer
claims no new physical survivability, production-data acceptance or runtime results.
