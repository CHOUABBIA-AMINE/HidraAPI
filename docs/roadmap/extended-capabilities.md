# HidraAPI Extended Industrial Capabilities Roadmap

## 1. Document control

| Field | Value |
|---|---|
| Product | Hidra — Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Repository | `CHOUABBIA-AMINE/HidraAPI` |
| Roadmap | `docs/roadmap/extended-capabilities.md` |
| Roadmap code | `EXT` |
| Baseline | `d84fe0513b35c61c1de6d23bb4a5af5c3a408018` |
| Development line | `0.6.0-SNAPSHOT` |
| Created | 2026-09-29 |
| Status | Active — EXT-001 completed; implementation not started |
| Execution mode | Exactly one roadmap code per commit; stop after each task |

This roadmap extends HidraAPI with OT/IT telemetry ingestion, external leak-detection computation, and spatial/ILI integrity capability while preserving the current modular-monolith, DDD and hexagonal boundaries.

The factual baseline is `docs/architecture/extended-capability-audit.md`.

## 2. Non-negotiable boundaries

### 2.1 Integration versus Telemetry

```text
integration
  owns external connectivity, MQTT/Sparkplug transport, connector health,
  external identities, mappings, retries/dead letters

telemetry
  owns sources, devices/points where justified, readings, quality,
  ingestion semantics and time-series persistence
```

No MQTT adapter may write telemetry tables directly without an approved Telemetry application boundary.

### 2.2 Topology versus Integrity

```text
topology
  owns authoritative physical pipeline/segment route and engineering extent

integrity
  owns inspection evidence, corrosion/defect observations,
  engineering assessments and maintenance recommendations
```

Integrity references Topology through approved identifiers/ports; it does not create a duplicate physical pipeline master.

### 2.3 HidraAPI versus LeakDetectionAPI

```text
LeakDetectionAPI
  owns transient hydraulic/CPM computational runtime

HidraAPI
  owns topology identity, telemetry business records,
  leak candidates/cases, audit/workflow/alarm integration
```

Protobuf is an integration contract, not a Hidra domain model.

## 3. Global rules

- Read `AGENTS.md`, this roadmap, coding policy and relevant module documentation before each task.
- One roadmap code per commit.
- Use the exact commit message defined below.
- Do not change version numbers as part of individual tasks.
- Never edit an applied Flyway migration; add a new immutable migration.
- Never introduce cross-module database foreign keys.
- Domain stays free of Spring/JPA/gRPC/MQTT/PostGIS implementation types.
- Production external communications use application ports and infrastructure adapters.
- Do not create `shared`, `sharedkernel`, `common`, `core`, `utils`, `helper`, `helpers`, or `misc`.
- Do not reopen HDP-004 or import data through this roadmap.
- Database extension installation policy must support controlled environments where application credentials cannot execute `CREATE EXTENSION`.
- Algorithmic pipeline-integrity calculations require approved engineering specifications and reference cases before implementation.

## 4. Phase A — Architecture and dependency decisions

| Code | Exact commit message | Scope | Acceptance | Status |
|---|---|---|---|---|
| EXT-001 | `docs(architecture): audit extended industrial capabilities` | Create the live-repository capability audit and this roadmap only. | Existing/partial/missing capability matrix documented; no production code/dependency/schema change. | **Completed** |
| EXT-002 | `docs(architecture): decide industrial extension boundaries` | Add ADRs for MQTT/Sparkplug ownership, high-volume telemetry persistence, PostGIS/route ownership, gRPC service boundary, database-extension provisioning, and B31G governance. | Decisions reconcile current source/schema and explicitly reject duplicate models/tables. | Planned |
| EXT-003 | `docs(architecture): define industrial extension contracts` | Specify versioned cross-boundary contracts and exact file allowlists for first implementation tasks, including idempotency and failure semantics. | No implementation ambiguity remains for Telemetry, Integration, LeakDetection and Integrity tasks. | Planned |

EXT-002 and EXT-003 are documentation-only gates. No Maven dependency, Java class, Protobuf file or migration is authorized before they are complete.

## 5. Phase B — High-velocity telemetry storage

The existing `TelemetryReading` contract and `hidra_telemetry_reading` schema are the starting point.

| Code | Exact commit message | Scope | Acceptance | Status |
|---|---|---|---|---|
| EXT-004 | `feat(telemetry): add high velocity ingestion contracts` | Add the minimum Telemetry input/output contracts for validated batch ingestion and append persistence; reuse existing reading/source/point concepts. | No duplicate telemetry aggregate introduced; application remains infrastructure-neutral. | Planned |
| EXT-005 | `db(telemetry): add timeseries storage migration` | Add the actual next Flyway migration implementing the approved Timescale/PostgreSQL strategy. | Existing reading authority reconciled; no guessed `V_Next`; extension prerequisites explicit; replay on fresh PostgreSQL image tested. | Planned |
| EXT-006 | `feat(telemetry): add jdbc high velocity persistence adapter` | Implement approved JDBC batch persistence behind the Telemetry output port. | Uses parameterized batch operations; no Hibernate per-reading path; atomicity/idempotency semantics tested. | Planned |
| EXT-007 | `test(telemetry): verify high velocity ingestion` | Add integration/performance-oriented tests for batch writes, duplicates, quality/timestamps and schema compatibility. | Required throughput baseline is documented before claiming production readiness. | Planned |

Important design gate for EXT-005: decide whether `hidra_telemetry_reading` itself becomes a hypertable or an explicitly justified append-store design is used. Do not create a parallel table merely because an earlier prompt named `telemetry_hypertable`.

## 6. Phase C — MQTT / Sparkplug B edge ingestion

| Code | Exact commit message | Scope | Acceptance | Status |
|---|---|---|---|---|
| EXT-008 | `feat(integration): add sparkplug boundary contracts` | Define Integration-side external identities and mapping of Group/Edge Node/Device/Metric to Telemetry references. | No Sparkplug type leaks into Telemetry domain; deterministic mapping and lifecycle semantics specified. | Planned |
| EXT-009 | `feat(integration): add mqtt sparkplug adapter` | Add the approved MQTT client/configuration and Sparkplug decoding adapter. | Handles approved Sparkplug message types, secure configuration, reconnect/backoff and payload validation. | Planned |
| EXT-010 | `test(integration): verify mqtt telemetry handoff` | Verify MQTT/Sparkplug adapter hands accepted measurements to Telemetry through the approved application boundary. | No direct foreign-table write; duplicate/reconnect behavior and malformed payload quarantine tested. | Planned |

EXT-009 must not include OT control/actuation. The Integration data-definition safety rule prohibiting PLC/RTU/SCADA command actuation remains authoritative.

## 7. Phase D — LeakDetectionAPI contract and Hidra integration

No separate repository is created until the service boundary/contract is approved.

| Code | Exact commit message | Scope | Acceptance | Status |
|---|---|---|---|---|
| EXT-011 | `docs(leakdetection): define cpm service contract` | Define LeakDetectionAPI responsibility, model/config input, telemetry input, anomaly output, availability and idempotency semantics. | API 1130/CPM scope is explicit; Hidra remains business system of record. | Planned |
| EXT-012 | `feat(leakdetection): add cpm protobuf contract` | Add the approved versioned Protobuf contract in the authorized contract location. | Contract is implementation-language neutral and carries stable anomaly identity/correlation. | Planned |
| EXT-013 | `feat(leakdetection): add cpm grpc client port` | Add Hidra application output port and mapping contracts for anomaly stream consumption. | Application layer contains no generated-stub or gRPC runtime types. | Planned |
| EXT-014 | `feat(leakdetection): add cpm grpc client adapter` | Add gRPC client configuration/adapter, stream resilience and mapping to existing `CreateLeakCandidateUseCase`. | Reconnect/backoff/cancel/duplicate semantics tested; LeakDetectionAPI outage does not fail Hidra startup/core operations. | Planned |
| EXT-015 | `test(leakdetection): verify cpm anomaly integration` | Contract/integration tests for anomaly mapping and idempotent candidate creation. | Duplicate streamed anomaly cannot create uncontrolled duplicate candidates. | Planned |

### Separate LeakDetectionAPI repository sequence

After EXT-011/EXT-012 approval and explicit repository creation authorization, initialize a separate roadmap in that repository, beginning with:

```text
LDS-001 — chore(leakdetection): bootstrap service repository
LDS-002 — feat(leakdetection): implement protobuf server boundary
LDS-003 — feat(leakdetection): add cpm model parameter domain
LDS-004 — feat(leakdetection): add transient model runtime
LDS-005 — test(leakdetection): add hydraulic reference validation
```

Those are not HidraAPI commits and must not be executed from this repository roadmap without a separate repository and its own instructions.

## 8. Phase E — Topology spatial route foundation

| Code | Exact commit message | Scope | Acceptance | Status |
|---|---|---|---|---|
| EXT-016 | `test(topology): prove postgis linear reference compatibility` | Add a focused proof-of-concept for PostGIS geometry, Hibernate Spatial/JTS and measured-coordinate handling before schema commitment. | Demonstrates whether M coordinates survive DB/JPA round trips; no production route schema yet. | Planned |
| EXT-017 | `db(topology): add authoritative pipeline route geometry` | Add approved PostGIS route geometry to Topology using actual next migration. | Topology remains route owner; GiST indexes and SRID rules tested; existing KP fields preserved. | Planned |
| EXT-018 | `feat(topology): add pipeline route persistence` | Add Topology domain/application/persistence support required by the approved route design. | No Integrity-owned duplicate route master; spatial types stay out of domain where practical. | Planned |

If EXT-016 shows unreliable M-coordinate handling across the Java stack, EXT-017 must use geometry plus explicit KP/chainage rather than making `LINESTRINGM` the sole engineering reference.

## 9. Phase F — ILI and predictive integrity

Existing inspection/corrosion/defect structures must be extended rather than duplicated.

| Code | Exact commit message | Scope | Acceptance | Status |
|---|---|---|---|---|
| EXT-019 | `docs(integrity): define ili spatial extension` | Map ILI run/anomaly requirements onto existing inspection run/finding, corrosion, wall-thickness and defect models. | Every proposed field has an owner; duplicate `IliAnomalyFeature` model is rejected unless a real semantic gap is proven. | Planned |
| EXT-020 | `db(integrity): add ili spatial evidence` | Add only missing PostGIS/ILI columns/tables using actual next Flyway migration. | Integrity stores inspection evidence and references Topology; GiST/index/KP reconciliation tested. | Planned |
| EXT-021 | `feat(integrity): add ili spatial mapping` | Add approved domain/application/persistence mapping for inspection anomalies and geometry/KP reconciliation. | Raw observation remains distinct from engineering assessment. | Planned |
| EXT-022 | `docs(integrity): specify modified b31g calculation` | Record approved engineering equation set, standard/edition, inputs, units, limits, ERF/MAOP semantics, rounding and reference cases. | Engineering reviewer can reproduce expected outputs independently. | Planned |
| EXT-023 | `feat(integrity): add modified b31g assessment` | Implement the calculator from EXT-022 and map outputs into the existing assessment model or an explicitly approved extension. | Pure deterministic domain service; versioned method; reference tests pass. | Planned |
| EXT-024 | `test(integrity): verify ili b31g workflow` | End-to-end tests from approved ILI evidence through spatial/KP resolution and assessment persistence. | No fabricated engineering inputs; traceable input/result/version evidence. | Planned |

## 10. Phase G — Cross-capability hardening

| Code | Exact commit message | Scope | Acceptance | Status |
|---|---|---|---|---|
| EXT-025 | `test(architecture): enforce industrial extension boundaries` | Add architecture guards for Integration→Telemetry handoff, gRPC isolation and Topology/Integrity ownership. | API/application/domain do not depend on connector/spatial implementation types. | Planned |
| EXT-026 | `docs(operations): add industrial extension runbooks` | Document MQTT, Timescale/PostGIS prerequisites, gRPC operations, secrets, health and rollback. | Production prerequisites are explicit and no secret is committed. | Planned |
| EXT-027 | `docs(architecture): close extended industrial capability milestone` | Reconcile all EXT tasks, CI evidence, compatibility and unresolved risks. | Milestone closure documented; release decision remains separate. | Planned |

## 11. Validation policy

Documentation tasks:
- verify current `main` SHA and paths;
- compare claims with live source/migrations;
- verify only authorized documentation files changed.

Java/dependency tasks:
- `./mvnw -B -q -DskipTests compile`;
- focused tests;
- `./mvnw -B -q test`;
- `./mvnw -B -q clean verify` when task scope requires full verification.

Database tasks:
- fresh PostgreSQL/Testcontainers migration;
- extension prerequisite verification;
- Hibernate schema validation where applicable;
- idempotent fresh-schema replay;
- never modify an applied migration.

Performance-sensitive telemetry tasks must document the test dataset, environment and measurement method. No throughput claim may be made without measured evidence.

Engineering-calculation tasks must use approved reference cases from EXT-022; generic unit tests generated from the same implementation formula are insufficient validation.

## 12. Release policy

All tasks remain on `0.6.0-SNAPSHOT` unless an independently authorized release procedure establishes a release boundary.

Completion of one EXT task, one capability, or one external-service commit does not automatically create a release.

## 13. EXT-001 completion evidence

EXT-001 inspected live `main` and established:

- existing telemetry semantics and schema;
- existing Integration external-system/mapping infrastructure;
- existing LeakDetection candidate/use-case boundary;
- existing integrity inspection/corrosion/assessment persistence;
- existing topology KP/network ownership;
- absence of production Sparkplug, Timescale hypertables, gRPC/Protobuf, PostGIS/Hibernate Spatial and Modified B31G code.

Files authorized by EXT-001:

```text
docs/architecture/extended-capability-audit.md
docs/roadmap/extended-capabilities.md
```

No production source, test source, Maven dependency, migration or version file is authorized by EXT-001.

## 14. Next authorized task

```text
EXT-002 — docs(architecture): decide industrial extension boundaries
```

Do not execute EXT-003 or later work in the same task.
