# Extended Industrial Capability Audit

## 1. Document control

| Field | Value |
|---|---|
| Product | Hidra — Hydrocarbon Intelligence for Data, Risk, and Analytics |
| Repository | `CHOUABBIA-AMINE/HidraAPI` |
| Audit code | `EXT-001` |
| Baseline branch | `main` |
| Baseline commit | `d84fe0513b35c61c1de6d23bb4a5af5c3a408018` |
| Development version | `0.6.0-SNAPSHOT` |
| Audit date | 2026-09-29 |
| Scope | OT/IT MQTT/Sparkplug ingestion, high-volume telemetry persistence, leak-detection service boundary, PostGIS/ILI, Modified B31G |
| Change type | Documentation-only forensic audit |

This audit is based on the live repository state, not on ZIP snapshots or older inventories. Where older documentation differs from the current source tree, current `main` is authoritative.

## 2. Executive result

The proposed capabilities are **not greenfield**. HidraAPI already contains substantial domain, persistence and schema foundations in `telemetry`, `integration`, `leakdetection`, `integrity`, and `topology`.

The missing work is primarily production-grade protocol/computation/storage specialization:

- MQTT is documented as a supported integration/acquisition direction, but no production MQTT client adapter is present.
- Sparkplug B is not implemented.
- Telemetry reading, point and source domain contracts already exist, and the schema already contains device, ingestion-batch and external-tag-mapping tables.
- High-volume telemetry is currently represented through normal PostgreSQL/JPA persistence; no TimescaleDB extension, hypertable migration, or JDBC batch-write adapter is present.
- Leak detection already owns candidates, cases, profiles, runs and localization persistence, and `CreateLeakCandidateUseCase` already exists.
- No gRPC, Protobuf, API 1130 or CPM service integration exists.
- Integrity already models defects and persists inspection runs/findings, corrosion features, wall-thickness measurements, defect assessments and failure pressure.
- Topology already owns pipelines, segments, nodes, facilities, measurement locations and kilometer-point ranges.
- No PostGIS/Hibernate Spatial/JTS geometry implementation exists.
- No Modified B31G calculation implementation exists.
- No current production code contains a transient hydraulic leak-detection engine.

Therefore, the extension must **build on existing concepts instead of introducing parallel replacements**.

## 3. Evidence inspected

Primary evidence:

- `AGENTS.md`
- `PROJECT_STATE.md`
- `docs/policy/Coding-policy.md`
- `docs/roadmap/telemetry.md`
- `docs/roadmap/topology.md`
- `docs/data definition/Telemetry.md`
- `docs/data definition/Integration.md`
- `docs/data definition/LeakDetection.md`
- `docs/data definition/Integrity.md`
- current module source trees under `src/main/java/dz/sh/hidra/modules/**`
- current Flyway migrations under `src/main/resources/db/migration/**`
- current `pom.xml`

Historical inventories such as `docs/data-provisioning/target-inventory.md` are useful context but predate later repository remediation. Current source-tree evidence takes precedence.

## 4. Capability matrix

| Capability | Current status | Evidence | Required extension |
|---|---|---|---|
| Telemetry source/point/readings | Implemented baseline | `TelemetrySource`, `TelemetryPoint`, `TelemetryReading`, `TrustedTelemetryReading` | Reuse; do not replace |
| Telemetry device/batch/tag mapping persistence | Persistence/schema baseline exists | JPA entities and `V20260611_005__create_telemetry_tables.sql` | Reconcile application/domain contracts before new types |
| MQTT conceptual support | Documented readiness only | Telemetry/Integration data definitions mention MQTT | Production inbound adapter and secure connection configuration |
| Sparkplug B | Missing | No Sparkplug code/dependency found | Protocol decoder, lifecycle handling and deterministic identity mapping |
| JDBC high-volume write path | Missing | No telemetry `JdbcTemplate.batchUpdate` adapter found | Dedicated outbound persistence port + JDBC adapter |
| TimescaleDB | Documentation-only future strategy | Telemetry data definition mentions PostgreSQL/TimescaleDB strategy | Deployment prerequisite decision, migration strategy, hypertable tests |
| Leak candidate business lifecycle | Implemented baseline | `LeakCandidate`, `CreateLeakCandidateCommand`, `CreateLeakCandidateUseCase` | Reuse as receiving business boundary |
| Leak detection configuration/run/localization persistence | Persistence/schema baseline exists | profile/run/localization JPA entities and `V20260611_009__create_leakdetection_tables.sql` | Reconcile external compute identity and idempotency |
| gRPC / Protobuf | Missing | No gRPC/protobuf code/dependency found | Contract + outbound adapter |
| API 1130 / CPM | Missing | No matching source/docs implementation found | Separate computational-service specification |
| ILI/inspection persistence | Partially implemented | inspection run/finding, wall thickness, corrosion, defect assessment tables | Extend instead of creating duplicate ILI tables |
| Kilometer-point linear reference | Implemented as scalar KP baseline | topology segment start/end KP; integrity KP columns | Preserve as engineering reference; add spatial reconciliation |
| PostGIS | Missing | No PostGIS/geometry/JTS/Hibernate Spatial evidence | Spatial dependency/schema/POC |
| Pipeline spatial route | Missing | topology uses scalar lat/lon and KP, no route geometry | Topology-owned route geometry design |
| Modified B31G | Missing algorithm; assessment storage partially ready | defect assessment already has failure pressure/safety factor | Engineering spec first, then calculator and traceable results |
| Transient hydraulic engine | Vision-only/future evidence | archived simulation vision mentions transient solver | Keep outside HidraAPI leak business lifecycle; define separate service |

## 5. Telemetry and OT/IT ingestion audit

### 5.1 Existing domain boundary

Current `main` contains these canonical telemetry domain models:

- `TelemetrySource`
- `TelemetryPoint`
- `TelemetryReading`
- `TrustedTelemetryReading`

`TelemetryReading` already carries:

- point identifier;
- numeric/text/boolean mutually exclusive value slots;
- quality identifier;
- source timestamp;
- receipt timestamp;
- reading state;
- ingestion-batch identifier;
- correlation identifier;
- source sequence number;
- external-tag-mapping identifier;
- raw-payload hash.

This is already the correct semantic center for incoming measurement data. A new `TelemetrySampleBatch` must not be introduced as a replacement without first proving a semantic gap.

### 5.2 Existing persistence baseline

The telemetry migration already creates:

- `hidra_telemetry_device`;
- `hidra_telemetry_external_tag_mapping`;
- `hidra_telemetry_ingestion_batch`;
- `hidra_telemetry_reading`;
- source, endpoint, point, binding, quality and related tables.

The ingestion-batch table already tracks received, accepted, rejected, duplicate and quarantined counts.

The reading table already stores source timestamps, quality, sequence number, correlation, external mapping and payload hash.

The current application output port `TelemetryReadingRepositoryPort` is single-record oriented:

```text
save(TelemetryReading)
findById(String)
```

That is adequate for ordinary repository semantics but is not a high-velocity bulk-ingestion contract.

### 5.3 Repository-remediation consequence

Older documentation inventories list domain/application contracts for `TelemetryDevice`, `TelemetryIngestionBatch`, `TelemetryExternalTagMapping` and many repository ports. Current `main` no longer contains all of those domain mirrors/ports, while their JPA entities and tables remain.

This is deliberate repository-remediation reality and must not be reversed casually. Any new high-velocity ingestion design must introduce only the minimum domain/application contracts required by an actual use case.

### 5.4 MQTT and Sparkplug result

MQTT is explicitly mentioned by current product/data-definition documentation as an acquisition/integration protocol, and the Integration definition says Integration owns connectors/mappings while Telemetry owns telemetry business facts.

No current production MQTT client implementation, MQTT library dependency, or Sparkplug B implementation was found.

Recommended ownership:

```text
Edge / MQTT / Sparkplug B
        |
        v
integration infrastructure adapter
  - transport
  - Sparkplug decoding
  - external identity
  - connector health/retry
        |
        v
telemetry application input boundary
  - point resolution
  - validation/quality
  - batch semantics
        |
        v
telemetry high-volume persistence output port
        |
        v
JDBC / PostgreSQL-Timescale adapter
```

Integration moves data across the boundary; Telemetry owns the accepted measurement meaning.

### 5.5 TimescaleDB result

No TimescaleDB dependency, extension migration or hypertable currently exists.

The Telemetry data-definition document already anticipates partitioning `TelemetryReading` and `TrustedTelemetryReading` by time/source/point depending on PostgreSQL/TimescaleDB strategy.

Therefore a future task must evaluate whether the existing `hidra_telemetry_reading` table becomes the hypertable or whether a specialized append store is justified. A parallel `telemetry_hypertable` table must not be created by default because it could duplicate the canonical reading store.

Database-extension installation must also distinguish local/test convenience from controlled production DBA/platform provisioning.

## 6. Integration module audit

Current Integration domain/application code already provides:

- external-system registration;
- exchange-message recording;
- integration-job runs;
- dead-letter handling;
- connector and target-module port abstractions.

Persistence already contains `ExternalObjectReference`, endpoint, connector, data-contract, mapping-profile, field-mapping, inbound-record, retry, reconciliation and health structures.

`ExternalConnectorGateway` currently provides infrastructure-side connector plumbing and a no-op implementation exists. No production MQTT/Sparkplug adapter exists.

This module is therefore the correct home for the external MQTT/Sparkplug transport adapter and external-object resolution, but **not** for ownership of telemetry readings.

## 7. Leak detection audit

### 7.1 Existing Hidra business boundary

Current domain models include:

- `LeakCandidate`;
- `LeakDetectionCase`;
- `LeakEscalationReference`.

Current application input already includes:

- `CreateLeakCandidateUseCase`;
- `OpenLeakCaseUseCase`;
- `EscalateLeakCaseUseCase`;
- query use cases.

`CreateLeakCandidateCommand` already accepts run/profile identifiers, candidate number, topology asset identity, suspected/evidence timestamps, confidence, summary and correlation id.

Persistence/schema already contains:

- detection methods;
- profiles and rules;
- detection runs;
- candidates;
- localization estimates;
- severity assessments;
- verification actions;
- evidence and dismissal data.

### 7.2 Missing computational integration

No gRPC dependency, Protobuf contract, generated stub, API 1130 implementation, CPM model or hydraulic transient leak algorithm was found.

The proposed separate `LeakDetectionAPI` therefore complements rather than replaces the current module:

```text
LeakDetectionAPI
  owns transient/CPM computation and runtime model state

HidraAPI leakdetection
  owns profiles/business references, leak candidates, cases,
  escalation, evidence, workflow/alarm/audit integration
```

From HidraAPI's hexagonal perspective, a gRPC client is an **outbound infrastructure adapter**. Server-streamed anomaly messages received through that adapter should be mapped into the existing application input boundary, normally `CreateLeakCandidateUseCase`.

The contract must also add an explicit external anomaly identity/idempotency strategy before production streaming is enabled.

## 8. Integrity / ILI audit

### 8.1 Existing integrity foundation

Current domain includes `PipelineDefect`, `IntegrityAssessment`, `IntegrityCase`, and `IntegrityProgram`.

Persistence/schema already contains substantially more inspection detail, including:

- inspection campaigns and runs;
- inspection findings;
- wall-thickness measurements;
- corrosion features;
- coating observations;
- cathodic-protection data;
- defect measurements;
- defect assessments;
- remaining-life estimates.

`CorrosionFeatureJpaEntity` already stores kilometer point, length, width and depth.

`DefectAssessmentJpaEntity` already stores assessment method, failure pressure, pressure unit, safety factor and fit-for-service result.

Therefore new ILI capability must extend these structures instead of creating a separate `IliAnomalyFeature` model without reconciliation.

### 8.2 Modified B31G result

No Modified B31G calculator, B31G reference implementation, bulk-modulus model, or ERF implementation was found.

The persistence model is partially ready for engineering results, but production implementation is blocked until an approved engineering specification defines:

- governing standard/edition;
- exact Modified B31G equations;
- units and conversions;
- diameter, wall thickness, defect dimensions;
- material-strength inputs and flow-stress rule;
- Folias factor;
- MAOP source;
- ERF definition;
- validity limits and interaction rules;
- numerical precision/rounding;
- reference verification cases.

Algorithm code must follow that specification; it must not be generated from undocumented general knowledge.

## 9. Topology and PostGIS audit

Topology owns physical network structure. Current persistence already has:

- pipelines;
- pipeline segments;
- nodes;
- facilities;
- measurement locations.

Pipeline segments already preserve `start_kilometer_point`, `end_kilometer_point` and `length_km`.

Facilities/nodes and some integrity records use scalar latitude/longitude.

No current PostGIS extension, geometry/geography column, Hibernate Spatial dependency, JTS dependency, GiST spatial index or route geometry was found.

Recommended ownership:

- Topology owns authoritative pipeline/segment route geometry.
- Integrity owns ILI observations, defects and assessment geometry derived/located against topology.
- KP/chainage remains a first-class engineering reference even after spatial geometry is added.

A future POC must verify `LINESTRINGM`/M-coordinate round-trip through PostgreSQL/PostGIS, Hibernate Spatial, JTS and serialization before making M the sole KP representation. Explicit KP columns should remain authoritative engineering evidence unless that POC proves otherwise.

## 10. Dependency audit

Current `pom.xml` contains Spring Boot web/security/data-JPA/Flyway, PostgreSQL, MapStruct, Lombok, observability and testing dependencies.

It does **not** currently contain production dependencies for:

- MQTT client;
- Sparkplug B;
- gRPC;
- Protobuf generation/runtime;
- Hibernate Spatial;
- JTS;
- TimescaleDB-specific Java integration.

Spring JDBC is not currently established as a dedicated production telemetry adapter dependency/configuration in this capability.

Dependency additions must be performed only by later approved roadmap tasks.

## 11. Decisions established by EXT-001

EXT-001 establishes these architecture constraints for the roadmap:

1. Do not create a parallel telemetry domain inside Integration.
2. Reuse `TelemetryReading`, `TelemetryPoint`, `TelemetrySource` and existing telemetry schema.
3. Reconcile persistence-only telemetry structures before reintroducing domain mirrors.
4. Put MQTT/Sparkplug transport/decoding in Integration infrastructure.
5. Put accepted telemetry ingestion semantics and high-volume persistence ports in Telemetry.
6. Do not create `telemetry_hypertable` until the existing `hidra_telemetry_reading` migration strategy is explicitly decided.
7. Keep HidraAPI leak-detection business state; place transient/CPM computation in the separate LeakDetectionAPI service.
8. Implement the Hidra gRPC side as an outbound adapter feeding existing leak-detection input use cases.
9. Keep authoritative route geometry in Topology; Integrity references topology and owns inspection/defect/assessment data.
10. Preserve explicit KP/chainage alongside spatial geometry.
11. Extend existing inspection/corrosion/defect-assessment structures rather than creating duplicate ILI aggregates.
12. Require an approved Modified B31G engineering specification before calculation code.
13. Do not change project version for individual extension tasks; remain on `0.6.0-SNAPSHOT` until a real release boundary is authorized.

## 12. Explicitly not performed

EXT-001 does not:

- change Java production code;
- change `pom.xml`;
- add MQTT/Sparkplug dependencies;
- add gRPC/Protobuf;
- create LeakDetectionAPI;
- create Flyway migrations;
- enable PostGIS or TimescaleDB;
- create database tables;
- implement B31G;
- import or modify data;
- alter HDP-004 status.

## 13. Outcome

The extended capabilities are viable, but the original implementation prompt must be decomposed into controlled tasks that build on the repository's existing models and persistence structures.

Execution continues through `docs/roadmap/extended-capabilities.md`.
