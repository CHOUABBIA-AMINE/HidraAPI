# HPS-005 — Geospatial Routing and Elevation: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-005 — Geospatial Routing and Elevation

<a id="hapi-112"></a>
## HAPI-112 — prove postgis linear reference compatibility

Phase: HPS-005 — Geospatial Routing and Elevation

Legacy IDs: EXT-016

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add a focused proof-of-concept for PostGIS geometry, Hibernate Spatial/JTS and measured-coordinate handling before schema commitment.

### 2. Existing Implementation

Map GeoJSON is public; bounded physical revision DTOs/codecs preserve internal engineering input. Public survey authoring is absent. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Provide typed survey/profile/section lifecycle with CRS, vertical datum, units, precision and source evidence; retain old revisions. The exact bounded remaining delivery is: Add a focused proof-of-concept for PostGIS geometry, Hibernate Spatial/JTS and measured-coordinate handling before schema commitment..

### 4. Functional Requirements

Demonstrates whether M coordinates survive DB/JPA round trips; no production route schema yet.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. No inferred elevations or automatic PostGIS adoption; deployment measurements remain owner supplied. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Demonstrates whether M coordinates survive DB/JPA round trips; no production route schema yet.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/extended-capabilities.md:143](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L143)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:143 | EXT-016 | test(topology): prove postgis linear reference compatibility | Add a focused proof-of-concept for PostGIS geometry, Hibernate Spatial/JTS and measured-coordinate handling before schema commitment. | Demonstrates whether M coordinates survive DB/JPA round trips; no production route schema yet. | Deferred
~~~~~~~~

<a id="hapi-113"></a>
## HAPI-113 — add authoritative pipeline route geometry

Phase: HPS-005 — Geospatial Routing and Elevation

Legacy IDs: EXT-017

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add approved PostGIS route geometry to Topology using actual next migration.

### 2. Existing Implementation

Map GeoJSON is public; bounded physical revision DTOs/codecs preserve internal engineering input. Public survey authoring is absent. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/TopologyModule.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/TopologyApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/TopologyRestApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/mapper/TopologyApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Provide typed survey/profile/section lifecycle with CRS, vertical datum, units, precision and source evidence; retain old revisions. The exact bounded remaining delivery is: Add approved PostGIS route geometry to Topology using actual next migration..

### 4. Functional Requirements

Topology remains route owner; GiST indexes and SRID rules tested; existing KP fields preserved.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. No inferred elevations or automatic PostGIS adoption; deployment measurements remain owner supplied. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/TopologyModule.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/TopologyApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/TopologyRestApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/mapper/TopologyApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Topology remains route owner; GiST indexes and SRID rules tested; existing KP fields preserved.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/extended-capabilities.md:144](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L144)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:144 | EXT-017 | db(topology): add authoritative pipeline route geometry | Add approved PostGIS route geometry to Topology using actual next migration. | Topology remains route owner; GiST indexes and SRID rules tested; existing KP fields preserved. | Deferred
~~~~~~~~

<a id="hapi-114"></a>
## HAPI-114 — add pipeline route persistence

Phase: HPS-005 — Geospatial Routing and Elevation

Legacy IDs: EXT-018

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add Topology domain/application/persistence support required by the approved route design.

### 2. Existing Implementation

Map GeoJSON is public; bounded physical revision DTOs/codecs preserve internal engineering input. Public survey authoring is absent. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/TopologyModule.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/TopologyApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/TopologyRestApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/mapper/TopologyApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Provide typed survey/profile/section lifecycle with CRS, vertical datum, units, precision and source evidence; retain old revisions. The exact bounded remaining delivery is: Add Topology domain/application/persistence support required by the approved route design..

### 4. Functional Requirements

No Integrity-owned duplicate route master; spatial types stay out of domain where practical.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. No inferred elevations or automatic PostGIS adoption; deployment measurements remain owner supplied. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/TopologyModule.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/TopologyApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/TopologyRestApi.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/TopologyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/mapper/TopologyApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- No Integrity-owned duplicate route master; spatial types stay out of domain where practical.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/extended-capabilities.md:145](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L145)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:145 | EXT-018 | feat(topology): add pipeline route persistence | Add Topology domain/application/persistence support required by the approved route design. | No Integrity-owned duplicate route master; spatial types stay out of domain where practical. | Deferred
~~~~~~~~

<a id="hapi-115"></a>
## HAPI-115 — define ili spatial extension

Phase: HPS-005 — Geospatial Routing and Elevation

Legacy IDs: EXT-019

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `integrity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Map ILI run/anomaly requirements onto existing inspection run/finding, corrosion, wall-thickness and defect models.

### 2. Existing Implementation

Map GeoJSON is public; bounded physical revision DTOs/codecs preserve internal engineering input. Public survey authoring is absent. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/controller/SpringIntegrityController.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Provide typed survey/profile/section lifecycle with CRS, vertical datum, units, precision and source evidence; retain old revisions. The exact bounded remaining delivery is: Map ILI run/anomaly requirements onto existing inspection run/finding, corrosion, wall-thickness and defect models..

### 4. Functional Requirements

Every proposed field has an owner; duplicate IliAnomalyFeature model is rejected unless a real semantic gap is proven.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integrity`; primary phase owns this obligation. No inferred elevations or automatic PostGIS adoption; deployment measurements remain owner supplied. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/controller/SpringIntegrityController.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Every proposed field has an owner; duplicate IliAnomalyFeature model is rejected unless a real semantic gap is proven.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/extended-capabilities.md:155](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L155)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:155 | EXT-019 | docs(integrity): define ili spatial extension | Map ILI run/anomaly requirements onto existing inspection run/finding, corrosion, wall-thickness and defect models. | Every proposed field has an owner; duplicate `IliAnomalyFeature` model is rejected unless a real semantic gap is proven. | Deferred
~~~~~~~~

<a id="hapi-116"></a>
## HAPI-116 — add ili spatial evidence

Phase: HPS-005 — Geospatial Routing and Elevation

Legacy IDs: EXT-020

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `integrity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add only missing PostGIS/ILI columns/tables using actual next Flyway migration.

### 2. Existing Implementation

Map GeoJSON is public; bounded physical revision DTOs/codecs preserve internal engineering input. Public survey authoring is absent. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/controller/SpringIntegrityController.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Provide typed survey/profile/section lifecycle with CRS, vertical datum, units, precision and source evidence; retain old revisions. The exact bounded remaining delivery is: Add only missing PostGIS/ILI columns/tables using actual next Flyway migration..

### 4. Functional Requirements

Integrity stores inspection evidence and references Topology; GiST/index/KP reconciliation tested.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integrity`; primary phase owns this obligation. No inferred elevations or automatic PostGIS adoption; deployment measurements remain owner supplied. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/controller/SpringIntegrityController.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Integrity stores inspection evidence and references Topology; GiST/index/KP reconciliation tested.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/extended-capabilities.md:156](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L156)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:156 | EXT-020 | db(integrity): add ili spatial evidence | Add only missing PostGIS/ILI columns/tables using actual next Flyway migration. | Integrity stores inspection evidence and references Topology; GiST/index/KP reconciliation tested. | Deferred
~~~~~~~~

<a id="hapi-117"></a>
## HAPI-117 — add ili spatial mapping

Phase: HPS-005 — Geospatial Routing and Elevation

Legacy IDs: EXT-021

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `integrity` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add approved domain/application/persistence mapping for inspection anomalies and geometry/KP reconciliation.

### 2. Existing Implementation

Map GeoJSON is public; bounded physical revision DTOs/codecs preserve internal engineering input. Public survey authoring is absent. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/controller/SpringIntegrityController.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Provide typed survey/profile/section lifecycle with CRS, vertical datum, units, precision and source evidence; retain old revisions. The exact bounded remaining delivery is: Add approved domain/application/persistence mapping for inspection anomalies and geometry/KP reconciliation..

### 4. Functional Requirements

Raw observation remains distinct from engineering assessment.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integrity`; primary phase owns this obligation. No inferred elevations or automatic PostGIS adoption; deployment measurements remain owner supplied. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/controller/SpringIntegrityController.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssetsRecommendationPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Raw observation remains distinct from engineering assessment.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/extended-capabilities.md:157](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L157)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:157 | EXT-021 | feat(integrity): add ili spatial mapping | Add approved domain/application/persistence mapping for inspection anomalies and geometry/KP reconciliation. | Raw observation remains distinct from engineering assessment. | Deferred
~~~~~~~~

<a id="hapi-531"></a>
## HAPI-531 — define revisioned route and elevation profiles

Phase: HPS-005 — Geospatial Routing and Elevation

Legacy IDs: HPR-P25-019

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define revisioned route and elevation profiles

### 2. Existing Implementation

Map GeoJSON is public; bounded physical revision DTOs/codecs preserve internal engineering input. Public survey authoring is absent. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Original independent acceptance; design is not implementation; F1/F3 actual cases

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. No inferred elevations or automatic PostGIS adoption; deployment measurements remain owner supplied. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-019 — Authoritative route, geodesy and section/profile preservation

Objective/justification: remedy endpoint-only route representation without confusing
map geometry and mesh. Evidence: T09–T14/T22/T23 and physical revision codec T03.
Owner/contracts: Topology geometry/profile revision queries; Simulation transformation
consumer; Integrity evidence read references only where applicable. Functional/data:
ordered geographic points, CRS/datum/vertical datum, units, survey/uncertainty/source dates,
chainage origin/order, section boundaries and supplied internal diameter/wall/material/
roughness/constraints; distinguish geometric length and engineering supplied length.
API: draft/save/read/revise/import profile with precise validation and historical queries.
Persistence: versioned profile/section children, reference integrity, atomic append,
no geometry/coordinate migration inferred in this task. Validate range, datum transformations
and round-trip precision using declared test sources; reject unknown CRS/incompatible
units, reversed/duplicate/out-of-range chainage, missing section data and inconsistent
geometry. Numerical acceptance design: Case E equal endpoints/nonflat profile; derived
mesh lineage/refinement and conservation obligations for the later selected method,
without inventing survey accuracy or numerical tolerances. Cases B/I preserve old
profile bytes and earlier input. D/H/N; exclude adopting PostGIS or a geometry library
without a separately reviewed dependency decision.

~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `a691843f359126a9f439217862f780b5806c5fd2` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:12859](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12859)
- [doc/model-remediation/RECONCILIATION.md:12887](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12887)
- [doc/model-remediation/RECONCILIATION.md:13671](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13671)
- [doc/model-remediation/RECONCILIATION.md:14031](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L14031)
- [doc/model-remediation/RECONCILIATION.md:14264](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L14264)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6110](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6110)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14599](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14599)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14627](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14627)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15411](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15411)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15771](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15771)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:16004](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L16004)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DESIGN COMPLETE.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14599 | HPR-P25-019 | Geospatial route/profile and pipe engineering preflight / Topology | 018 | docs(topology): define revisioned route and elevation profiles | PLANNED — NOT SELECTED
doc/model-remediation/RECONCILIATION.md:12859 | HPR-P25-019 | Geospatial route/profile and pipe engineering preflight / Topology | 018 | docs(topology): define revisioned route and elevation profiles | PLANNED — NOT SELECTED
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15411 | HPR-P25-019 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_ACTIVE | Cross-module engineering governance | 003/004: specific independent owner contracts, not all 002; 018: amendment docs CI + owner selection; 019/020: 018 | Original independent visualization/measurement or engineering provisioning objective | Any solver use remains outside this independent scope | Original independent acceptance; design is not implementation; F1/F3 actual cases | NOT SELECTED | Own bounded preflight/implementation after prerequisites
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15771 | HPR-P25-019 | PLANNED — NOT SELECTED; next recommended owner-selected preflight | After successful 018 Documentation CI, refresh live source/CI and reconcile geometry overlap with P3-005–007; no PostGIS dependency automatically selected
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:16004 | HPR-P25-019 | IN PROGRESS — preflight delivered; published-head Documentation acceptance PENDING | Current source/design and local validation below; no production implementation
doc/roadmap/ULTIMATE_ROADMAP.md:6110 | HPR-P25-019 | DESIGN COMPLETE | Future runtime assigned 050–058; design is not executable completion
doc/model-remediation/RECONCILIATION.md:13671 | HPR-P25-019 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_ACTIVE | Cross-module engineering governance | 003/004: specific independent owner contracts, not all 002; 018: amendment docs CI + owner selection; 019/020: 018 | Original independent visualization/measurement or engineering provisioning objective | Any solver use remains outside this independent scope | Original independent acceptance; design is not implementation; F1/F3 actual cases | NOT SELECTED | Own bounded preflight/implementation after prerequisites
doc/model-remediation/RECONCILIATION.md:14031 | HPR-P25-019 | PLANNED — NOT SELECTED; next recommended owner-selected preflight | After successful 018 Documentation CI, refresh live source/CI and reconcile geometry overlap with P3-005–007; no PostGIS dependency automatically selected
doc/model-remediation/RECONCILIATION.md:14264 | HPR-P25-019 | IN PROGRESS — preflight delivered; published-head Documentation acceptance PENDING | Current source/design and local validation below; no production implementation
~~~~~~~~

<a id="hapi-589"></a>
## HAPI-589 — Survey, CRS, profile and pipe-section authoring

Phase: HPS-005 — Geospatial Routing and Elevation

Legacy IDs: HPR-P25-052C

Priority: P1

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-587

Cross-Repository Dependencies: HWEB-F02, HWEB-F03 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Survey/CRS/vertical datum/chainage/elevation/profile and pipe-section authoring.

### 2. Existing Implementation

Map GeoJSON is public; bounded physical revision DTOs/codecs preserve internal engineering input. Public survey authoring is absent. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Provide typed survey/profile/section lifecycle with CRS, vertical datum, units, precision and source evidence; retain old revisions. The exact bounded remaining delivery is: Survey/CRS/vertical datum/chainage/elevation/profile and pipe-section authoring..

### 4. Functional Requirements

Preserve intermediate elevations/section boundaries, declared units/precision/source/evidence across revisions; reject unsupported/wrong/missing metadata, no browser/default elevation or PostGIS/physics assumption.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. No inferred elevations or automatic PostGIS adoption; deployment measurements remain owner supplied. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/SurveyRouteProfile.java` — pure Java owned aggregate/value and invariants for ordered points, CRS/vertical datum, chainage, intermediate elevations, units and pipe-section boundaries.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/SurveyRouteProfileUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/SurveyRouteProfileRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/SurveyRouteProfileApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SurveyRouteProfileController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcSurveyRouteProfileRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/SpringTopologyController.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/TopologyMapController.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `PUT /api/v1/topology/engineering-networks/{id}/survey` for ordered points, CRS/vertical datum, chainage, intermediate elevations, units and pipe-section boundaries. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Preserve intermediate elevations/section boundaries, declared units/precision/source/evidence across revisions; reject unsupported/wrong/missing metadata, no browser/default elevation or PostGIS/physics assumption.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-587. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23358](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23358)
- [doc/model-remediation/RECONCILIATION.md:23702](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23702)
- [doc/model-remediation/RECONCILIATION.md:23736](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23736)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1010](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1010)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1044](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1044)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1296](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1296)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1010 | HPR-P25-052C | Approve representational CRS/datum and chainage conventions to support; deployment coordinates and calibration values are not required. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1044 | HPR-P25-052C | REQUIRES_OWNER_DECISION | Survey, CRS, profile and pipe-section authoring
doc/roadmap/ULTIMATE_ROADMAP.md:1296 | HPR-P25-052C | Survey/CRS/vertical datum/chainage/elevation/profile and pipe-section authoring. | Preserve intermediate elevations/section boundaries, declared units/precision/source/evidence across revisions; reject unsupported/wrong/missing metadata, no browser/default elevation or PostGIS/physics assumption.
doc/model-remediation/RECONCILIATION.md:23358 | HPR-P25-052C | Survey/CRS/vertical datum/chainage/elevation/profile and pipe-section authoring. | Preserve intermediate elevations/section boundaries, declared units/precision/source/evidence across revisions; reject unsupported/wrong/missing metadata, no browser/default elevation or PostGIS/physics assumption.
doc/model-remediation/RECONCILIATION.md:23702 | HPR-P25-052C | Approve representational CRS/datum and chainage conventions to support; deployment coordinates and calibration values are not required. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23736 | HPR-P25-052C | REQUIRES_OWNER_DECISION | Survey, CRS, profile and pipe-section authoring
~~~~~~~~


### HAPI-589 owner convention approval and bounded A registration — 2026-10-20

**Selection and live baseline:** the owner selected Next immediately after the explicit convention proposal: preserve supplied CRS, axis order and units without automatic coordinate conversion; require an explicit vertical datum for elevations; metre-based chainage with declared origin and direction; preserve intermediate points, precision, evidence and section boundaries. That Next approves these representational conventions and selects this documentation-only registration. Baseline main `5159a8679454a49ca0cf4064549ba65c5162d252`, parent `00c602dc090613bbb5758f15f17130e7460be952`, tree `82b58237dc208c89ee3ae1f17bc6e619b3820455`; [Documentation run39409824104](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39409824104) SUCCESS. HAPI-588's bounded graph acceptance remains recorded in its owning HPS-004 phase; no downstream implementation is inferred from it.

**Source-backed boundary:** inspected the current `EngineeringNetworkDraft`, `EngineeringGraphDraft`, `EngineeringGraphDraftUseCase`, `EngineeringGraphDraftRepositoryPort`, `EngineeringNetworkDraftController`, `EngineeringNetworkDraftApplicationService` and `TopologyPhysicalNetworkRevision` at the baseline SHA. The current service owns protected metadata/graph transactions, native authority and one shared monotonically increasing draft version. Physical revision values already exist but neither preserve ordered survey points/CRS/vertical datum nor provide survey authoring. Reuse existing exact graph identity validation. Do not duplicate the accepted draft aggregate or map survey values into a physically qualified revision. No existing Topology SurveyRouteProfile type/test was present in the exact tree.

**A — registered next executable package:** pure Java immutable survey/profile values and focused invariant tests only. Exact commit message: `feat(topology): define draft survey profile values`. This is a bounded first delivery under HAPI-589, not completion of its public authoring objective.

Exact A write allowlist:
- CREATE `src/main/java/dz/sh/hidra/modules/topology/domain/model/SurveyRouteProfile.java`.
- CREATE `src/test/java/dz/sh/hidra/modules/topology/domain/model/SurveyRouteProfileTest.java`.
- MODIFY `.github/ci-test-inventory.json` only to admit the actual new test class, actual method identities and exact source SHA256 in the existing unit-architecture lane; preserve existing classes/cases, lane routing, unrestricted oracle/fallback and qualification policy. No invented runtime timings.
- MODIFY `doc/roadmap/TASKS_HPS-005.md` only to append actual A delivery/validation evidence under HAPI-589.

No other source, test, configuration, schema, generated artifact, frozen status registry, workflow or permissions write is admitted by A. All proposed older controller/repository/service names in section6 remain non-executable until a later exact registration. No task header/status change is included in A.

**A contract and invariants:**
- One immutable `SurveyRouteProfile` value represents one declared PIPE branch profile, with exact `branchId`, explicit horizontal CRS descriptor, optional vertical-reference descriptor, chainage origin reference, direction enum `FROM_TO` or `TO_FROM`, evidence reference, ordered points and ordered pipe-section intervals. Branch identity reuses `EngineeringGraphDraft.identity`; A does not query whether that branch exists or is PIPE. That owner check belongs to later graph integration.
- CRS is a supplied authority/code reference with exactly two ordered, distinct named axes and explicit unit identifiers. These identifiers are bounded metadata, never a claim that a resolver verified an EPSG entry, projection, geodetic accuracy or axis/unit compatibility. No hardcoded EPSG default, browser position, WKT parsing, reprojection or automatic axis swap. Later public admission must register any semantic catalog restrictions explicitly.
- Each point retains an exact identifier, two required BigDecimal coordinates in the declared axis order, BigDecimal chainage in metres, optional BigDecimal elevation, and explicit positive supplied coordinate/chainage precision. An elevation additionally requires positive elevation precision and a vertical-reference descriptor with explicit datum authority/code and elevation unit identifier. Without elevation, elevation precision must be absent. Preserve BigDecimal values/scales and the supplied sequence; do not round, interpolate, sort or deduplicate. Identical horizontal coordinates at different chainages are allowed; negative elevations are allowed.
- Point identities are unique; chainages are nonnegative and strictly increasing in the declared direction, with at least two points. Origin reference is supplied metadata; the first retained point need not have zero chainage. Direction describes traversal against graph from/to orientation, not a negative chainage or guessed flow direction.
- Each section retains an exact identity, positive-length start/end chainage interval in metres and its own evidence reference. Section intervals lie within the retained first/last point chainages, are supplied in increasing order and may touch but never overlap; gaps are permitted as incomplete draft input. Section boundary chainages must be explicitly present among retained points. Sections are representational intervals in A; diameter/material/roughness/composition and qualification are not silently invented. Empty sections and absent elevations remain valid incomplete draft input.
- Freeze every collection defensively. Reject null lists/elements, blank or malformed metadata/identities, duplicate point/section IDs, invalid precision, missing required descriptors and out-of-bounds counts. Bounds: at most10000 points and10000 sections; metadata identifiers at most80 UTF-8 bytes using the existing identity rule; evidence and origin references at most2048 Unicode code points, rejecting NUL and malformed UTF-16, without trimming or normalization.
- Domain imports Java standard library and existing pure Topology domain types only. No Spring/JPA/Jackson, platform, persistence, REST or engine dependency. No lifecycle, publication, version mutation, coordinate/height conversion, inferred elevation, physical length calculation, hydraulic validation, external engine or OT operation is introduced.

**A required focused test identities** in `SurveyRouteProfileTest` (actual sources must implement these methods before inventory admission): `preservesAxisOrderDecimalsAndIntermediatePoints`, `requiresExplicitCrsAxesAndUnits`, `requiresDatumAndPrecisionWhenElevationSupplied`, `allowsMissingAndNegativeElevationsWithoutDefaults`, `requiresOriginDirectionAndStrictlyIncreasingMetreChainage`, `allowsNonzeroStartingChainageAndRepeatedCoordinates`, `rejectsDuplicatePointAndSectionIdentities`, `preservesTouchingSectionBoundariesAndPermitsGaps`, `rejectsOverlappingReversedAndOutOfRangeSections`, `requiresSectionBoundariesInRetainedPoints`, `rejectsMissingInvalidAndOversizedMetadata`, `rejectsNullElementsAndExcessiveCollectionSizes`, `defensivelyCopiesAllCollections`, `rejectsZeroOrNegativeSuppliedPrecision`. Cover both directions, metadata malformed Unicode, null required fields, exact count limits, caller mutation and retained BigDecimal scale. These are registered future cases, not tests already written or executed.

**A validation and stop:** inspect live source/CI again before code; preserve canonical Java headers. Run useful existing tooling, inventory/source consistency and focused tests if the supported local runtime exists; do not provision a complete runtime. Hosted applicable Full/Fast/Docs must test the exact implementation SHA, including existing architecture guards. Source inspection or absent local runtime is not a passing runtime result. Publish A directly to main under an expected-head guard, verify parent/tree/exact4-file scope, observe applicable CI creation/start and STOP without waiting. A requires no Flyway/JPA/PostgreSQL change and claims no database proof.

**Remaining HAPI-589 gate:** the owner convention decision is resolved by this session; public executable scope is still incomplete. HAPI-589 remains NOT_IMPLEMENTED/BLOCKED at this registration checkpoint because A has not been implemented and persistence/API integration is not yet registered. After A's actual CI, a later single stage must register the protected survey writer/reader against the accepted shared draft version, native READ/UPDATE authority, current/as-of graph binding, immutable snapshots/digests, graph-edit compatibility, CAS/rollback/final guards, strict Jackson3 DTOs, exact API errors and operation identities, actual additive migration and privilege/trigger/lock coverage, real provider fixtures, concurrency/ABA/revocation tests, generated OpenAPI and actual catalog reconciliation. The live migration chain currently ends at `V20261019_002__p25_topology_engineering_graph_snapshots.sql`; that observation reserves no migration filename. No proposed endpoint is admitted now. HAPI-590/HAPI-600, registry restructuring, simulation, commissioning and deployment remain unselected.

**This registration commit:** exact one-file MODIFY scope, this phase record. All earlier task payload and history are preserved. Existing task IDs, membership, headers/statuses and frozen ROADMAP/PHASES remain unchanged. Local checks verify append-only placement inside HAPI-589, exact path existence/nonexistence and one-file tree difference; no Java/runtime or new CI pass is claimed. Message: `docs(topology): register survey profile value package`. Observe Documentation CI creation/start on publication, then STOP.

<!-- task-register:payload:end -->
