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

## HPR-P2-013 current canonical review — 2026-10-09

Verified source parent: `00c4fda266b2dfd175cca37ad789dc9462a5af0b`. Revised preflight Documentation Validation
#126 (37913628486) PASSED before this implementation. HPR-P2-012 documentation #124
(37909982710) and production #605 (37909982823) PASSED at
`e4dba168c9e612a5fd49d50b155fa3b2d8d64e40`.

All 24 module documents and canonical indexes remain registered; maintained metadata,
local links and source module roots are checked by canonical validation. Permanent
semantics retain 123 subjects and reconciliation retains 57 completed HMR identities.
The executable source/test/resources/POM and production operations are unchanged from
the semantic-transfer and CI #604 baselines. Legacy docs/** remains subordinate
history; no review verdict is used as current execution authority.

Current database inventory is refreshed to 139 unique versioned migrations and 470
module entities. Current architecture exports are 70 Java files in 63 packages,
including neutral extension roles. Original architecture/database/P1 provenance
retains its own source/deployed applicability. The current API object is unchanged:
CI #604 generated the snapshot; #605 freshly verified equality except source-SHA
provenance and independently passed supported compatibility. No endpoint/schema or
security value is manually edited.

HPR-P2-013 documentation implementation is complete, but P2 final VERIFIED/CLOSED
disposition is PENDING both successful CI workflows on the resulting implementation
SHA. Current implementation publication is not CI success. No P3 task is selected.
Prior dated pending/publication statements above retain historical applicability and
are superseded by this current review where they describe the earlier execution state.

Unknown business retention/policy approvals, complete endpoint/performance/OT coverage,
per-class no-skips evidence, deployed-data/import acceptance, runtime inference/actuation
and fresh physical survivability are not established by this documentation closure.
P0/P1 disposition and original physical evidence remain unchanged absent regression.
TimescaleDB, PostGIS and unimplemented industrial/AI extensions remain DEFERRED/TARGET.

## Renewed HPR-P2-013 audit and closure gate — 2026-10-09

The read-only P2 audit at `7be1c9cb47ed9b6f73a7692d0a328ad870e2b4d4`
returned PASS: all twelve checks VERIFIED. Preflight Documentation #135 passed at
that SHA. HPR-P2-005 correction is COMPLETED after #131; HPR-P2-006 is COMPLETED
at `1bc3c1bba2d08a0b493e5ece43e834e8d56d04f0` after Documentation #134 and
Production #608 passed, including fresh physical dictionary comparison with zero
unresolved owners. These later facts supersede earlier publication-pending summaries;
dated historical records and their original applicability remain preserved.

This closure metadata implementation records the audit and verified prerequisites.
HPR-P2-013 is IN PROGRESS; P2 remains OPEN pending both documentation and full
production CI on the resulting closure commit. The exact current disposition and
retained twelve-check evidence follow [the roadmap](../roadmap/ULTIMATE_ROADMAP.md).
Docs-only push does not start full production CI: the existing HidraAPI CI manual
workflow must run on the exact closure head. No preceding CI result substitutes for
that gate. Stop after startup observation; no P3 task is selected.

All 24 module slices, 123 semantic subjects and 57 completed HMR identities remain.
Source-derived schema facts retain disposable PostgreSQL-16 capture applicability;
P1 deployed/recovery evidence retains its original scope. No production-data/import
approval, business retention/policy values, hydraulic/ML runtime execution or field
actuation is established. Version remains 0.6.0-SNAPSHOT. No executable, migration,
contract snapshot, dictionary, ownership metadata or operating artifact is changed.
