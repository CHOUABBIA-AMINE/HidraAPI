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

Implementation Status: VERIFIED

Planning Status: COMPLETED

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


### HAPI-589 package A implementation delivery — 2026-10-20; runtime CI pending

**Registered stage:** `feat(topology): define draft survey profile values`. Owner Next selects the bounded A implementation registered at `4053eef88d224c6bb99a739de558cc9abd71d4c7`, parent `5159a8679454a49ca0cf4064549ba65c5162d252`, tree `6c37b35f82b81821a758db76d5b7c51af08c69bf`. Registration [Documentation run39410325784](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39410325784) completed SUCCESS. That result verifies the registration documentation, not this new Java implementation.

**Actual exact4-file scope:** CREATE `src/main/java/dz/sh/hidra/modules/topology/domain/model/SurveyRouteProfile.java`; CREATE `src/test/java/dz/sh/hidra/modules/topology/domain/model/SurveyRouteProfileTest.java`; MODIFY `.github/ci-test-inventory.json`; append this phase record. No pre-existing Java, API, migration, protected coordinator, permissions, workflow, generated artifact or frozen registry changes.

**Delivered values:** pure Java immutable SurveyRouteProfile, Axis, Crs, VerticalReference, Point and Section records, with FROM_TO/TO_FROM traversal. Existing EngineeringGraphDraft exact identity validation is reused. The model preserves supplied descriptors, axis order/unit identifiers, BigDecimal values/scales and all retained points/section boundaries. It requires positive precision, explicit vertical reference when elevation exists, explicit origin/evidence, nonnegative strictly increasing metre chainage, numeric boundary membership and nonoverlapping supplied section order. Missing elevations/sections, negative elevations, nonzero origin offsets, repeated horizontal coordinates, touching intervals and gaps remain representable without inferred data. Metadata/reference validation, defensive collection copies and10000-element bounds are enforced. CRS/datum are supplied metadata only, without resolution or coordinate conversion. Section intervals introduce no inferred pipe characteristics or engineering qualification.

**Actual test identities:** all14 methods registered above now exist in SurveyRouteProfileTest. Cases include both traversal directions, numerically equal boundaries with different decimal scales, caller mutation/unmodifiable collections, synthetic projected/geographic descriptors, preserved elevation units, negative/missing elevations, malformed Unicode, exact80-byte identity/2048-code-point reference limits, valid10000-point/9999-interval input and oversized rejection. Test source SHA256 `841d8f6e81725103b8a9f15e0b6df816a2006a16ea80df6ddcc61d0e41a84aa6`. The inventory adds only this class and its14 actual case identities to the existing fast unit-architecture lane, with this hash. All previous entries and other inventory fields are structurally identical; zero baseline_seconds is explicitly an unmeasured placeholder, not a runtime observation.

**Checks actually performed:** available OpenJDK17 compiler successfully compiled the new production record together with the unchanged current EngineeringGraphDraft dependency via `java com.sun.tools.javac.Main -d survey-a/classes .../EngineeringGraphDraft.java .../SurveyRouteProfile.java`. The compiler parser accepted the actual JUnit source via a temporary JavacTask.parse harness; this is syntax evidence, not test execution or full JUnit type-checking. A Python source/inventory check verified all14 unique method identities match the registration, exact test-source SHA256, preserved prior inventory content, canonical header fields and Java-only imports in the new domain file. No local Java21/Maven/JUnit suite execution or PostgreSQL test is claimed; Maven/JUnit dependencies and the supported Java21 runtime are unavailable, and no runtime was provisioned. Hosted Full/Fast/Docs must execute the real source and existing architecture checks.

**Disposition and next gate:** A is delivered pending exact implementation-SHA CI, not formally accepted. HAPI-589's full public authoring objective remains incomplete and its frozen status/header remain unchanged. A adds no API/persistence/version writer and claims no public survey workflow, database verification or physical qualification. After actual A feedback, the next single stage is source-backed registration of protected survey persistence/API integration, as described above; no unregistered integration starts automatically. Publish under expected-registration-HEAD guard, verify parent/tree/blob/exact4-file scope, observe applicable CI creation/start on the published SHA, then STOP without waiting.


### HAPI-589 A CI checkpoint and protected survey integration registration — 2026-10-20

**Current source and real CI:** A implementation `b49fbeadea2319f7349540aa5ac08d3d3afdebe4`, parent `4053eef88d224c6bb99a739de558cc9abd71d4c7`, tree `9fec6c159213a163e016ebcfdc5efe8dc5f369b2`, passed [Full #774](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39410913571), [Fast #98](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39410913545) and [Docs #451](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39410913615) on that exact SHA. These are observed successful workflow conclusions; this registration does not claim an independent raw-report audit or formal completion of the full HAPI-589 objective. Owner Next selects this one documentation registration after A's successful feedback.

**Source recovery and ownership:** refreshed AGENTS, canonical ROADMAP, compatibility RECONCILIATION and this task; inspected current engineering command/repository ports, protected coordinator, service/controller, graph codec/storage, strict Jackson3 DTOs, exception mapping, technical executor/SQL admission, exact route delegation, graph boundary guards, architecture guards and actual PostgreSQL provider fixtures. Current metadata has31 fences; graph has34, including current station/facility generation witnesses. The draft application service is the compiled private credential owner; a separate SurveyRouteProfileApplicationService or SurveyRouteProfileController would duplicate that owner and is not admitted. Earlier generic section6 proposed names and the bare network-level survey endpoint are superseded only by this bounded registration.

**B — next registered implementation:** `feat(topology): persist protected draft survey profiles`. One existing engineering draft owns metadata, graph and per-PIPE survey versions. B delivers the protected per-branch current/as-of survey read/write, strict DTOs, bounded canonical codec, actual additive migration, native permission integration and real negative/concurrency tests. No new aggregate, permission domain, public publication, physical revision, geodetic resolver or hydraulic qualification is introduced.

**Exact B CREATE allowlist (11 files):**
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/SurveyRouteProfileUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/SurveyRouteProfileRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/SurveyRouteProfileCodec.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcSurveyRouteProfileRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/SurveyRouteProfileRequests.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/SurveyRouteProfileResponses.java`
- `src/main/resources/db/migration/V20261020_001__p25_topology_survey_profile_snapshots.sql`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/SurveyRouteProfileCodecTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/SurveyRouteProfileApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/SurveyRouteProfileControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/SurveyRouteProfileBoundaryGuardrailTest.java`

**Exact B MODIFY allowlist (13 files):**
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringNetworkDraftUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringNetworkDraftRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringNetworkDraftApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedEngineeringDraftAdmissionCoordinator.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringNetworkDraftRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringNetworkDraftController.java`
- `src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java`
- `src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringNetworkDraftPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java`
- `src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java`
- `.github/ci-test-inventory.json`
- `doc/roadmap/TASKS_HPS-005.md`

No DELETE. The accepted domain SurveyRouteProfile and existing metadata/graph/physical payload formats remain unchanged. Existing Identity/Party contracts and policy, source classifiers, workflows, database dictionary generator and all historical migrations remain unchanged. The inventory may add actual new classes/cases and refresh source hashes/case membership for only changed tests; preserve previous case identities, lanes, qualification/oracle/fallback policy. Any zero timing is explicitly unmeasured. No invented test, runtime timing or artifact pass.

**Application/port contract:** SurveyRouteProfileUseCase declares typed READ/UPDATE commands with exact companyPartyId, pipelineSystemId, draft UUID and branchId; UPDATE requires a positive expected shared version and one SurveyRouteProfile with matching branchId; READ accepts only an optional positive requested shared version. The existing service implements this port and a typed executeSurvey entry, using its existing protected transaction/executor and authority participants. Add SURVEY_READ/SURVEY_UPDATE to the internal engineering command kind with validated survey fields and retained old metadata/graph constructors. Map only SURVEY_READ to native READ and SURVEY_UPDATE to native UPDATE. Reject survey kinds through generic metadata execution so a caller cannot bypass typed validation. No caller supplies actor, permission, witness, backend or transaction evidence.

The repository port exposes immutable Snapshot(version, graphVersion, graphSha256, sha256, profile), asOf(draftId,branchId,selectedVersion) and append under the existing owner ticket/physical transaction. A survey UPDATE preserves metadata, appends exactly one next shared metadata version and one branch survey snapshot atomically, without advancing graphVersion or rewriting graph bytes. Sequential updates of different branches still share that one draft version. Reads select a metadata version that actually belongs to the authorized draft, then the newest branch survey snapshot at or before it. The current graph at that selected metadata version must contain the exact branchId as PIPE; CONNECTOR, absent graph or absent branch is NOT_FOUND after native authorization. Empty survey history returns200 with null snapshot provenance/profile and SURVEY_NOT_AUTHORED diagnostics, never invented values. Graph station references still require current company/kind/status/generation witnesses even on historical survey reads.

**Graph-binding and staleness rule:** a written survey captures the actual current graph snapshot version and digest. A metadata-only change retains a valid survey as-of its newer shared version. A graph update retains immutable prior survey history but changes the bound graph version; survey READ returns CONFLICT if an existing selected survey's bound graph version/digest differs from the graph selected for that read, even when the textual branchId remains or the re-authored graph has identical bytes. A subsequent authorized survey UPDATE at the latest expected version may re-author against that new graph. Historical reads at the original compatible metadata version remain reproducible subject to current native authority/witnesses. B does not silently rebase, interpolate, delete surveys, block ordinary graph edits or treat stale coordinates as current engineering facts.

**Exact REST operations:** extend only EngineeringNetworkDraftController with:
- `GET /api/v1/topology/engineering-networks/{draftId}/survey/{branchId}`, method `readSurvey`, operationId `readEngineeringNetworkDraftSurvey`; required companyPartyId/pipelineSystemId query scope and optional integral positive `version`.
- `PUT /api/v1/topology/engineering-networks/{draftId}/survey/{branchId}`, method `updateSurvey`, operationId `updateEngineeringNetworkDraftSurvey`; same required scope and body Update(expectedVersion, profile). profile contains the exact A fields: branchId, horizontalCrs(authority,code,axes(name,unitIdentifier)), optional verticalReference(datumAuthority,datumCode,unitIdentifier), chainageOriginReference, direction, evidenceReference, points(id,firstCoordinate,secondCoordinate,chainageMetres,elevation,coordinatePrecision,chainagePrecisionMetres,elevationPrecision), sections(id,startChainageMetres,endChainageMetres,evidenceReference).

All decimal values are explicit JSON strings parsed directly to BigDecimal and returned as BigDecimal.toString strings, preserving values/scales without binary floating-point coercion or toPlainString expansion. Reject blank/invalid decimal text, scalar type coercion, unknown nested fields, missing required fields, null list items and out-of-range versions. expectedVersion remains an integral JSON number checked for exact64-bit range; the two read version query states are absent or positive long. Each decimal string is bounded at4096 characters before parsing; output uses the retained BigDecimal value/scale. Nullable elevation/precision remain absent/null together. Preserve the A count/metadata bounds and declared units. Do not resolve or assert validity of arbitrary CRS/datum codes. Controller-owned mapping converts transport records to/from domain; request/response packages contain no domain types/imports.

Response Survey contains draftId, branchId, selected shared version, nullable surveyVersion/surveySha256/boundGraphVersion/boundGraphSha256 and nullable transport-owned profile, state DRAFT, engineeringComplete=false and diagnostics. Provenance fields are present together only for a real snapshot. Current graph provenance, if separately returned, must not be confused with bound provenance. No list/search/export/history collection is added. Existing handler retains400 invalid/malformed input,403 denied,404 scoped unavailable,409 stale shared version or graph binding,503 unavailable/indeterminate; actual configured authentication retains401. Native authorization precedes protected existence/history/witness disclosure. Extend interceptor delegation only for the exact compiled controller/method/verb/route tuples above; similarly named handlers, routes or callers never inherit the delegation.

**Canonical codec:** SurveyRouteProfileCodec FORMAT `HIDRA_ENGINEERING_SURVEY_DRAFT_V1`, maximum16MiB, deterministic length-prefixed binary UTF-8 with explicit nullable markers and the original ordered axes/points/sections. BigDecimals encode signed scale plus bounded canonical unscaled-integer bytes, never double conversion, trailing-zero stripping, rounding or enormous plain-string expansion. No reordering/deduplication. Bound lengths/counts against both the domain limit and remaining bytes before allocation, reject malformed UTF-8, invalid enum/marker/format, truncation, trailing bytes and noncanonical integer encodings. Decode through A invariants and require byte-equal re-encoding. Encoding exceeding16MiB is invalid input before provisional mutation, not partial storage. A canonical bytes digest is lowercase SHA256. Codec canonicalization need not preserve the spelling of equivalent decimal lexemes; it must preserve BigDecimal value and scale.

**Additive schema and catalog:** live migration inventory ends V20261019_002. B may CREATE only V20261020_001__p25_topology_survey_profile_snapshots.sql; refresh the live chain before implementation/publication and re-register if that filename/version is occupied. Create public.hidra_topology_engineering_survey_snapshot with draft_id, branch_id, version, graph_version, graph_sha256, payload_format, canonical_payload, sha256 and transaction_id; PK(draft_id,branch_id,version), required positive versions/transaction, graph_version < version, explicit branch byte bound, exact format and16MiB byte bounds, stored digest checked against sha256(canonical_payload). FK(draft_id,version) references the immutable metadata-version row; FK(draft_id,graph_version) references graph snapshot; both validated/nondeferrable/ON DELETE RESTRICT. A same-transaction BEFORE INSERT trigger verifies the new metadata row was inserted in txid_current(), graph version/digest is the latest real graph at that metadata version and referenced bytes match their actual digest. Immutable BEFORE UPDATE/DELETE and BEFORE TRUNCATE statement triggers reuse the existing immutable function.

Use new public.hidra_p25_survey_snapshot_same_transaction(), SECURITY DEFINER with exact pg_catalog,pg_temp search_path, fully qualified tables and explicit trigger-context checks. REVOKE PUBLIC table/function privileges. The existing private publication role needs only SELECT/INSERT/UPDATE on the new table, with UPDATE solely for existing SHARE_ROW_EXCLUSIVE lock eligibility and forbidden by immutable triggers; deny DELETE/TRUNCATE/TRIGGER and function EXECUTE, membership/ownership or ordinary-role writes. These are the same approved graph storage privilege semantics, not blanket mutation authority. Implement exact table/function/trigger/constraint/privilege catalog validation with actual migration-derived body hash; fail closed on tampering. Real test role provisioning must match these grants. No production data/backfill or owner calibration is required.

**Transaction/final proof:** extend only survey operations to35 distinct fences: existing graph34 plus the survey snapshot table SHARE_ROW_EXCLUSIVE; topology contribution9, Identity24, Party2; modes31 SHARE_ROW_EXCLUSIVE/3 ACCESS_SHARE/1 SHARE. Existing metadata31 and graph34 paths remain unchanged. Acquire all needed locks in the existing deterministic owner order before provisional SQL, including current graph/facility generations and the new survey table. Match native Identity, Party and Topology evidence to the same physical connection, backend pid and txid. Witness current stations from the authorized selected graph; prove graph+survey selection and original graph bindings inside the one final scalar SQL guard, with actual latest metadata/shared version and sampled validity boundary. Survey WRITE binds exactly the new same-version survey row and preserved metadata; READ binds the selected historical metadata plus latest eligible survey/graph row and current draft head observation. No second final statement or post-guard data query may replace that proof.

Pin the exact metadata-version INSERT plus exact survey-snapshot INSERT SQL hash set independently in ProtectedEngineeringDraftAdmissionCoordinator. Only that set may enter the survey provisional phase through the existing compiled service; the technical executor may add only the specific survey table INSERT family for that already-reviewed coordinator. Do not widen caller gates, generic prefixes, credentials, admission phases or retry behavior. Restrict the new JDBC adapter to the same protected physical transaction/private compiled owner, never a directly invocable authorization shortcut. At any failed witness, CAS, codec/canonical check, insert, flush or expired/revoked final guard, all provisional shared/survey rows roll back. Preserve retained internal causes; classify unknown commit as INDETERMINATE and never retry automatically.

**Required B cases — future source identities, not already implemented/run:**
- SurveyRouteProfileCodecTest: `roundTripsAllDeclaredValuesAndScales`, `preservesAxisPointAndSectionOrder`, `rejectsMalformedLengthsCountsUtf8AndMarkers`, `rejectsUnknownFormatTruncationAndTrailingBytes`, `rejectsNoncanonicalDecimalsAndPayloadOverflow`.
- SurveyRouteProfileApplicationServiceTest: `requiresTypedSurveyCommandAndMatchingBranch`, `usesNativeReadUpdateOnOneDraftOwner`, `rollsBackSurveyFailureAndRetainsCause`, `indeterminateSurveyWriteIsNotRetried`.
- SurveyRouteProfileControllerTest: `mapsStrictSurveyTransportWithoutDomainSerialization`, `rejectsNestedUnknownFieldsAndScalarCoercion`, `preservesDecimalStringsAndRejectsInvalidOrOversizedValues`, `rejectsBadScopeVersionsAndBranchMismatch`, `returnsRegisteredNonDisclosingErrors`.
- SurveyRouteProfileBoundaryGuardrailTest: `surveyUsesExistingDraftOwnerAndTransportOwnedDtos`, `surveyRepositoryIsProxyableAndDomainStaysPure`, `surveyHasOnlyTheTwoRegisteredOperations`.
- Add to existing EngineeringNetworkDraftPostgresIntegrationTest: `realNativeProvidersRoundTripSurveyWithCurrentDeny` (real LOCAL/OIDC/LDAP/AD synthetic producer paths, not mocked permission claims); `surveyMetadataGraphInterleavingPreservesSharedVersions`; `surveyRejectsMissingForeignAndConnectorBranches`; `surveyGraphReauthoringConflictsUntilRebound`; `surveyExactHistoricalReadsRetainOriginalBytes`; `concurrentSurveyAndMetadataWritersHaveOneSuccessor`; `concurrentSurveyAndGraphWritersHaveOneSuccessor`; `surveyForeignOwnerAndOwnerAbaNeverReuseWitness`; `surveyWriterFirstAndOwnerFirstPreserveFacilityFences` (two actual transactions and55P03); `expiredSurveyGuardRollsBackBothRows`; `surveyCatalogAndPrivilegeTamperingRejects`; `surveyDatabaseRejectsWrongTransactionDigestAndGraphBinding`. Existing full Identity mapping/authentication regression suites remain required.
- Extend ProtectedTransactionExecutorTest and HidraRouteAuthorizationInterceptorTest with actual survey-specific cases for provisional exact-SQL/caller fencing, no anonymous or wrong-controller delegation and only the exact two new route tuples. Preserve all previous case identities; inventory actual method names and SHA256 after implementation. Full architecture, actual migrated PostgreSQL, API and unrestricted oracle lanes remain mandatory; skipped DB cases never count as proof.

**C artifact reconciliation and later acceptance, independently gated:** after actual B feedback passes, the next registered documentation stage is `docs(topology): reconcile survey authoring runtime artifacts`, exact MODIFY scope: doc/api/openapi.yaml, doc/database/DATA_DICTIONARY.md and this phase record. Recover actual successful hosted OpenAPI and migrated PostgreSQL catalog artifacts with exact SHA/run/attempt/source/digests, validate backward compatibility and generated ownership; never hand-author a runtime snapshot/dictionary. Preserve generated files during B if no real local runtime is available and record pending regeneration explicitly. C does not declare engineering acceptance. A later `docs(topology): record draft survey authoring acceptance` stage may modify only this record, ROADMAP and PHASES to synchronize HAPI-589's own two status fields/header after verifying actual required case membership/results and acceptance evidence. No formal status change is selected by this registration.

**Registration scope/checks and stop:** this commit MODIFY only this phase file, append-only inside HAPI-589 before the payload-end marker; historical payload, task headings/IDs/statuses and all frozen registry content remain unchanged. Every B CREATE path is absent and MODIFY path present at the exact baseline tree; migration filename is unoccupied. No runtime source or tests are added by registration, and no runtime result is newly claimed. Exact message `docs(topology): register protected survey authoring integration`. Publish directly to main under expected-A-SHA guard, verify parent/tree/exact one-file scope, observe applicable Documentation CI creation/start, then STOP. The next Next executes only registered B after live refresh. HAPI-590/HAPI-600, numerical simulation, SITE commissioning, release/deployment and autonomous control remain unselected.


### HAPI-589 package B implementation delivery — 2026-10-20; exact-SHA runtime acceptance pending

**Registered stage:** `feat(topology): persist protected draft survey profiles`. Owner Next selects the24-file B implementation registered at `3461939855ad2f97a2694f5ddafa0443337657ea`, parent `b49fbeadea2319f7349540aa5ac08d3d3afdebe4`, tree `9441073a7bf5d1cd74d9a3dacc2d299badab4422`. Registration [Documentation run39413218382](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39413218382) completed SUCCESS. A's Full774/Fast98/Docs451 successes remain bounded to A, not this new integration.

**Actual delivery, exact11 CREATE/13 MODIFY scope:** the B allowlists above, with no other writes. Added typed SurveyRouteProfileUseCase and repository snapshots, bounded canonical SurveyRouteProfileCodec, private JdbcSurveyRouteProfileRepositoryAdapter, strict Jackson3 request records/deserializer and separate response records, the additive V20261020_001 migration and four focused test classes. Extended only the existing engineering draft service/controller/command/admission/storage owner, narrow technical provisional table family and exact survey route delegation; retained old metadata/graph constructors and operation semantics. Accepted A domain values and their tests are unchanged.

Survey writes preserve metadata and append the next shared version and one immutable per-branch survey row in one protected physical transaction. Current/as-of survey reads require native company/network READ and current station owner/generation evidence. PUT requires native UPDATE and the expected shared version. Branch existence and PIPE kind are checked against the actual selected graph; stored bound graph version/digest prevents a later graph re-authoring from being silently accepted. Historical compatible reads and metadata-only successors preserve original bytes. Responses are DRAFT/engineeringComplete=false with explicit nullable snapshot provenance; no absent elevation or section is fabricated. Decimal strings become BigDecimal directly, and values/scales/axis order are preserved without a conversion/resolver. All nested transport objects reject unknown/coerced input through the strict deserializer.

The new PostgreSQL table binds metadata and graph through validated immediate FKs, digest/format/size/version constraints and a same-physical-transaction graph-binding trigger, plus immutable update/delete/truncate triggers. Runtime catalog admission validates trigger/function shape and actual migration-derived function body MD5 `a65f0d43b26c1493b6f943b3d6f2963c`, expected relation/attribute/constraint counts, PK/FK keys/targets and reviewed publisher/ordinary-role privilege boundaries. The new profile INSERT SHA256 is `8598cd80072021556e73a03078f80cea5018df673bcf5e3d7fd2c95720ef9999`, independently pinned with the unchanged metadata INSERT in the coordinator. No generic provisional caller or credential admission is broadened. Survey adds one SHARE_ROW_EXCLUSIVE fence to the accepted graph path, yielding35 distinct relations with31 SHARE_ROW_EXCLUSIVE/3 ACCESS_SHARE/1 SHARE; metadata31 and graph34 remain unchanged. Survey and graph payload/binding, actual head/historical selection, actor/transaction/time and witnesses are checked in the one final scalar SQL guard; failure rolls back provisional metadata and survey rows. Indeterminate outcomes retain internal causes and are never automatically retried.

**Actual test source/inventory evidence:**31 new JUnit case identities across seven new/updated sources; all registered17 cases in the four new test classes and12 new real PostgreSQL cases exist, plus one survey provisional rejection case and one exact-route/authentication delegation case. All previous case identities are retained. The provider case executes native LOCAL/OIDC/LDAP/AD synthetic producer paths, current UPDATE DENY and signed-bearer survey GET through the actual HTTP filter. Other cases exercise metadata/graph interleaving, strict transport, current/history/stale graph behavior, canonical bytes and malformed/oversized input, same-version competing writers, foreign owner/ABA, real writer-first/owner-first55P03 blocking, expired final guard rollback, actual trigger/privilege tampering and database constraint rejection. Service unit failure cases prove classification/no retry; database rollback proof remains in the PostgreSQL cases. Their presence is not a claim that they passed.

- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/SurveyRouteProfileCodecTest.java`: 5 actual case identities (+5); source SHA256 `667b054105d0e56d7213baf6c3759e6e95883e05dae6c4647981b560cd05d3db`.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/SurveyRouteProfileApplicationServiceTest.java`: 4 actual case identities (+4); source SHA256 `c57d3d52b0cbdc693955ae6140be9011c6c1627bd663ef0122751efa90cce334`.
- `src/test/java/dz/sh/hidra/modules/topology/api/rest/SurveyRouteProfileControllerTest.java`: 5 actual case identities (+5); source SHA256 `f3028459a497909f73721f01c63e11db2444ef91f6f2b3116682bbeb7f5aef79`.
- `src/test/java/dz/sh/hidra/modules/topology/SurveyRouteProfileBoundaryGuardrailTest.java`: 3 actual case identities (+3); source SHA256 `91f5ca87d78c66eed6c0c1cacdb64cc56a4069f9112fc49833d0abbd9daeecfd`.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringNetworkDraftPostgresIntegrationTest.java`: 39 actual case identities (+12); source SHA256 `73b0d7639d025b6c06469ab10394c703f0793246af0948f6756df4452a459716`.
- `src/test/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutorTest.java`: 37 actual case identities (+1); source SHA256 `d6b2df88124c85afd6d8595d1e46b873310d3927f3f460adb4c396254ddb6b91`.
- `src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java`: 8 actual case identities (+1); source SHA256 `60353dcc17617d5b69355b6e40b2838c15e046f05b94d5d77a6b9c4f34dd4b65`.

The inventory records these actual identities/hashes only. New classes use the existing fast unit-architecture lane with explicitly unmeasured zero timing; existing PostgreSQL lane/source hashes are refreshed without changing routing. Qualification/oracle/fallback policy and workflows are unchanged.

**Checks actually run:** OpenJDK17 compiled the unchanged domain dependencies plus new pure survey command/repository/codec and extended internal command classes successfully. A temporary executable codec smoke harness passed500 checks across100 varied BigDecimal scales, Unicode references, optional/negative elevations and traversal directions, including canonical roundtrips and corrupt/truncated/trailing-byte rejection. A temporary command harness passed typed survey validation plus retained graph/metadata constructor compatibility. JavacTask.parse accepted all recovered/changed Java source files. Python checks verified31 added method identities, preservation of prior cases, seven exact source SHA256 inventory pins, registered new case names, transport/domain separation, exact profile/metadata DML digests and migration function-body MD5. These checks do not establish Spring wiring, Jackson runtime behavior, PostgreSQL transactions or Java21/JUnit success.

No Maven/Java21/JUnit/PostgreSQL runtime was provisioned or executed locally. The available runtime is OpenJDK17, with Maven and the supported test dependencies absent. Hosted Full/Fast/Docs must compile and execute the complete implementation, architecture guards and actual migrated database tests; skipped cases are not database proof. No full runtime pass or formal engineering acceptance is recorded now.

**Pending artifacts and acceptance:** doc/api/openapi.yaml and doc/database/DATA_DICTIONARY.md remain untouched pending the actual successful hosted API and migrated-catalog artifacts. C must recover/validate those generated outputs and exact-SHA source/report provenance under its independently registered3-file scope; no hand-authored schema or inferred catalog is substituted. HAPI-589's full objective remains pending runtime/artifact/acceptance evidence, and frozen statuses/header are unchanged. No HAPI-590/HAPI-600 publication/installed-unit completion, geodetic qualification, numerical solver, deployment/SITE commissioning or autonomous OT control is selected. Publish B under expected-registration-HEAD guard, verify exact24-file scope, parent/tree/blob identities, observe applicable CI creation/start, then STOP without waiting. A later Next must inspect B feedback before registered C; Fail repairs only responsible B scope.


### HAPI-589 B CI repair — exact caller registration, physical ownership and generated API snapshot — 2026-10-20

**Failed source and observed results:** main `6c846f80671614c4774cf4f63cc52728f9124d1b`, tree `f05d24ea77c053b155dd11bd0de6f09428337738`. [Full #775 run39414979009](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39414979009) and [Fast #99 run39414979003](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39414979003) failed; [Documentation run39414978935](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39414978935) succeeded. Recovered actual failing job logs, not an inferred transient failure. Owner Fail selects repair of these B integration omissions before C or downstream work.

**Root causes and bounded fixes:**
1. ProtectedStationAdmissionBoundaryGuardrailTest's exact requireAdmission caller set omitted B's new JdbcSurveyRouteProfileRepositoryAdapter. The actual source scan found exactly that fifth caller. Admit only this reviewed adapter filename to the exact set, retaining all other caller sets, private credential checks and byte-pinned owner exports; keep all five existing test identities and refresh only its source hash in the inventory.
2. Full's three catalog-regeneration tests rejected unresolved physical ownership for public.hidra_topology_engineering_survey_snapshot. Add exactly that Topology-owned relation to the existing ownership map, citing its actual JDBC adapter and immutable additive migration. Do not change the generator, skip catalog capture or assign ownership by a broad name prefix.
3. API contract job118601692959 built the current source and successfully ran step7 Publish deterministic OpenAPI contract, then failed step8 canonical equality because B's new operations were absent from the old snapshot. Recover the actual generated JSON from diagnostic artifact12262045063, not a hand-authored contract. Refresh canonical OpenAPI and its existing manifest pins. The previously registered B allowlist omitted these necessary repository integration dependencies; this failure repair admits the exact six paths below, without a new feature, task ID or wider permission policy.

**Actual generated API evidence:** artifact `hidra-ci-diagnostics-6c846f80671614c4774cf4f63cc52728f9124d1b-39414979009-1-api-contract`, run39414979009/attempt1, member `openapi/hidra-api.json`; ZIP SHA256 `6e4240449d86254ca8842cd5b10ca88cb0744d48f7830558f9a2173157de50e1`, raw JSON SHA256 `3583f85b6dcd100be7b82253dedd9719ef850fd5e4c64409f13fe61b14f5c910`. The recovered document names the actual source SHA and validates. Canonical JSON SHA256 `fec5cdcdfc2da9a100998dcdef1dfde223629552a8e52faf90032ed48de1acf5`;249 paths/271 operations/258 schemas. The sole added path is the registered per-branch survey path with GET/PUT, and14 survey schemas are added; every prior path item and prior schema is structurally identical. Supported backward compatibility passes.

The manifest's success conclusion is explicitly limited to the successful source build/generation step; job_conclusion and workflow_conclusion are both failure. It retains the prior accepted provenance separately and lists repair-SHA CI, runtime equality/compatibility, real dictionary capture and engineering acceptance as remaining gates. It does not label the failed Full run, skipped API catalog/compatibility steps or this task as passed.

**Exact6 MODIFY scope:**
- `src/test/java/dz/sh/hidra/modules/topology/ProtectedStationAdmissionBoundaryGuardrailTest.java`.
- `.github/ci-test-inventory.json`.
- `.github/database-dictionary-ownership.json`.
- `doc/api/openapi.yaml`.
- `.github/documentation-validation.json` — only the openapi section.
- This phase record, append-only under HAPI-589.

No CREATE/DELETE, production Java, migration, runtime contract implementation, workflow, validation script or frozen task-status changes. Guardrail test source SHA256 `371572057f524056ffad90b359ea35c62658ad2c45a60613bd5a1097f91f433f`; all five case identities and all other inventory content remain unchanged. The ownership map adds one precise entry; all prior entries remain identical.

**Validation actually completed:** recovered ZIP integrity/member/source/digests checked; unchanged OpenAPI validator passes offline manifest/digest/count checks and runtime semantic equality to the recovered artifact; unchanged compatibility script passes against the previous accepted canonical snapshot. Seventeen existing data-dictionary generator fixture tests and ten existing OpenAPI validator fixture tests passed. OpenJDK17 parser accepts the changed guardrail source; source comparison verifies one exact caller addition and preserved byte pins. No local Java21/JUnit/Maven/PostgreSQL execution is claimed. Full's oracle reported2606 cases with four failures, zero errors/skips; the failures were this exact caller assertion and three unresolved-ownership catalog cases. Other connection/cleanup messages in logs are not independently promoted to additional test failures or silently declared fixed.

**Remaining gate and stop:** the dictionary remains unchanged until actual successful hosted migrated-catalog evidence exists; no inferred physical metadata is written. The repaired generated snapshot is generation-step evidence, not formal C acceptance or successful repaired runtime verification. Reuse B's registered message `feat(topology): persist protected draft survey profiles`; publish under expected-failed-HEAD guard, verify parent/tree/exact6-file scope and blobs, observe applicable Full/Fast/Docs creation/start, then STOP without waiting. Inspect that feedback before C artifact reconciliation/formal acceptance. HAPI-589 remains unaccepted and downstream tasks remain unselected.


### HAPI-589 B second CI repair — actual migrated dictionary reconciliation — 2026-10-20

**Observed failed source:** `2e35b60a143b8b1fab1c08f89975fea6c7aa2558`, tree `54d85bd96e153634a7881f46a623b4c903642406`. [Fast #100](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39417826060) and [Docs #454](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39417826053) SUCCESS. [Full #776](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39417826042) failed only after successful Java verification, at the API/oracle current migrated dictionary equality steps. The unrestricted oracle log reports430 classes/2606 cases with zero failures/errors/skips. This is successful test execution evidence within a failed workflow, not formal Full acceptance.

**Cause and repair:** the previous fix registered the survey table's physical owner, enabling full catalog validation, but retained the older501-relation dictionary. Both dictionary-capture steps now report `Physical dictionary drift; review regenerated metadata/content`. Owner Fail, followed by Continue, selects this repair. Regenerate the CURRENT dictionary using the unchanged generator, exact-source migrations/JPA mappings and actual hosted PostgreSQL catalog. Do not bypass drift equality, disable capture, modify tests or invent database metadata.

**Recovered evidence:** API diagnostic artifact12261754876, `hidra-ci-diagnostics-2e35b60a143b8b1fab1c08f89975fea6c7aa2558-39417826042-1-api-contract`, run39417826042/attempt1, job118611061939. ZIP SHA256 `6febc99f111f7978164fa24c4b215ed4bada25accf1edbde2c320def561e3966`; CRC/integrity checked. Member `database/schema.json` captures the actual current PostgreSQL16.15 migrated catalog. Member `database/verification.json` truthfully records the failed stale-dictionary equality; the artifact/job/workflow are not marked successful. The oracle's actual catalog verification marker independently reports the same catalog/source bundle/dictionary digests.

Recovered and matched all627 migration/JPA source files against the artifact's exact SHA256 inventory at the captured GitHub SHA, plus the actual source-linked ownership evidence paths. The unchanged generator's validate_document/inventory checks pass against this source tree; no source-bundle mismatch is ignored. Catalog document SHA256 `f277873a363e90b2366f2f47e6e23ec30472ffb62b473517359d0974cf5e66ad`; source bundle SHA256 `be0f97cd7280aa97c0e84de040a632fbf494518e705fa10b358f793d136b9221`. Actual157 migrations,470 JPA mappings,502 relations and zero unresolved owners. The new survey snapshot's real columns, PK/FKs, checks, indexes, triggers and function definitions come from PostgreSQL, not inferred annotations.

**Exact2 MODIFY scope:** `doc/database/DATA_DICTIONARY.md` and this phase record. No runtime source, migration, tests/inventory, ownership policy, OpenAPI/manifest, workflow, validation script or frozen task-status changes. Reuse B's registered message `feat(topology): persist protected draft survey profiles` for this responsible CI repair; this is not formal C or HAPI-589 acceptance.

**Validation actually completed:** unchanged `generate_data_dictionary.py --root <recovered-source> --input <actual-artifact>/database/schema.json --source-sha 2e35b60a143b8b1fab1c08f89975fea6c7aa2558 --ownership <source>/.github/database-dictionary-ownership.json --render <regenerated-dictionary> --check <regenerated-dictionary>` succeeded, with reviewed_dictionary_checked=true. Generated dictionary SHA256 `6813d00d1bfb7b262415bca60fbadc63e639ee4ce10f3f5afe359022c630575e`. This matches the same-source oracle's actual verification marker. Local generation consumes the hosted catalog and verifies source; no local database/runtime provisioning or new Java test execution is claimed.

**Remaining gate and stop:** retain all prior failed-run evidence. Full repair-SHA dictionary equality and workflow acceptance remain pending; tests passing in Full776 does not turn that failed workflow green. Formal artifact/provenance review and bounded software acceptance remain separate. Publish directly to main under expected-2e35b60 guard, verify exact2-file scope/parent/tree/blob identities, observe applicable CI creation/start, then STOP without waiting. No downstream implementation, simulation, release/deployment or SITE qualification is selected.


### HAPI-589 package C — successful runtime artifact reconciliation — 2026-10-20

**Registered stage:** `docs(topology): reconcile survey authoring runtime artifacts`. Owner Next selects C after B and both responsible CI repairs. Exact source `067929306170ad69b453823a90bb7beeb6b8130d`, tree `443757db54b5854d5091385d44415dee93f68e7f`, has [Full #777](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39423247020), [Fast #101](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39426090155) and [Docs #455](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39422781763) all SUCCESS. Full/Fast were explicitly workflow-dispatched on that same SHA after the documentation-only dictionary repair; no missing push-triggered run is invented. Full's seven jobs all succeeded, including API118629255266, unit118629255342, PostgreSQL0 118629255586, PostgreSQL1 118629255298, oracle118629255451, static118629255022 and final gate118638972432.

**Authenticated successful aggregate:** artifact12265527590, `hidra-full-ci-evidence-067929306170ad69b453823a90bb7beeb6b8130d-39423247020-1`, run39423247020/attempt1. ZIP SHA256 `9aa04df6a595ed37388f08125d16db7efcba4d52fa181dd01fdc4f83c80e93c3`; ZIP CRC/integrity, all six gate-pinned lane receipt digests and every retained file digest validated. Gate/receipts agree on exact source/run/attempt and inventory fingerprint `2c2c4347e433f95ba677afe4295a3eb163ed9b5e38b513547867dbbc508f176d`. Actual XML case identities reparse as unit1717, PostgreSQL0 243 and PostgreSQL1 646; their disjoint union equals the unrestricted oracle's2606 cases/430 classes with zero failures/errors/skips. Same-run equivalence is true. Unrestricted fallback remains mandatory and optimized-only adoption remains ineligible; this reconciliation changes no CI policy.

**Actual source-matched task cases:** current Git blob identities and SHA256 pins for the eight A/B task-related test sources were matched to recovered source and inventory; every case appears in the successful raw oracle and the appropriate partition. The14 A domain cases,17 new B codec/service/HTTP/boundary cases,12 additional B PostgreSQL cases and two technical/route survey cases are present within the retained larger suites:
- `dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.SurveyRouteProfileCodecTest`: 5 actual cases, zero failures/errors/skips.
- `dz.sh.hidra.modules.topology.application.service.SurveyRouteProfileApplicationServiceTest`: 4 actual cases, zero failures/errors/skips.
- `dz.sh.hidra.modules.topology.api.rest.SurveyRouteProfileControllerTest`: 5 actual cases, zero failures/errors/skips.
- `dz.sh.hidra.modules.topology.SurveyRouteProfileBoundaryGuardrailTest`: 3 actual cases, zero failures/errors/skips.
- `dz.sh.hidra.modules.topology.infrastructure.persistence.EngineeringNetworkDraftPostgresIntegrationTest`: 39 actual cases, zero failures/errors/skips.
- `dz.sh.hidra.platform.persistence.ProtectedTransactionExecutorTest`: 37 actual cases, zero failures/errors/skips.
- `dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptorTest`: 8 actual cases, zero failures/errors/skips.
- `dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfileTest`: 14 actual cases, zero failures/errors/skips.

These are executed software evidence, not deployment, real geodetic calibration or SITE acceptance.

**Successful API artifact review:** API and unrestricted oracle members `files/target/openapi/hidra-api.json` are byte-identical and source0679293-pinned, SHA256 `46e16275b762a1109c4f6c60f08698368f56c34e8349fdff64d8baef425b0c04`. Both actual generated base members use `b49fbeadea2319f7349540aa5ac08d3d3afdebe4`, base member SHA256 `bf75c75401cda1387c880e07c2e147aeea65516ac07842870894aa7b3253203f`. The unchanged validator passes runtime equality to the committed canonical snapshot after removing only its explicitly validated generator-source SHA; the unchanged compatibility script passes against the actual generated base. Current retained canonical snapshot is249 paths/271 operations/258 schemas, SHA256 `fec5cdcdfc2da9a100998dcdef1dfde223629552a8e52faf90032ed48de1acf5`. Its earlier generation-step provenance remains historical and truthful; this source-linked successful C record supplies the later full-workflow verification without rewriting failed-run history.

**Successful physical artifact review:** API and oracle `files/target/database/schema.json` and `files/target/database/DATA_DICTIONARY.md` are each byte-identical. Actual source0679293 catalog SHA256 `fd3c04a9dc6eb9d4d0c99ec722fa7399458618c9cd5b4f6a1d51cf8f51579eb4`, source bundle `be0f97cd7280aa97c0e84de040a632fbf494518e705fa10b358f793d136b9221`, generated dictionary SHA256 `90f156c418a74a82b0c319d04e08126c3b17377f3d363304a26587291f9637cd`. Verified all627 migration/JPA source SHA256s and current exact-tree Git blob identities using the already recovered unchanged source files; no new full runtime was provisioned. Unchanged generator inventory/validate_document/render checks reproduce the hosted dictionary exactly: PostgreSQL16.15,157 migrations,470 mappings,502 relations, zero unresolved owners. Equality to the committed dictionary passes after only the validator-approved capture-SHA line is excluded; its retained bytes remain SHA256 `6813d00d1bfb7b262415bca60fbadc63e639ee4ce10f3f5afe359022c630575e`. No inferred physical metadata, synthetic catalog, hand-edited generated object or schema weakening is used.

**Exact one-file C evidence write:** only this phase record is appended inside HAPI-589. C's registered snapshot paths already contain the reviewed runtime content because the responsible CI repairs reconciled them; their exact current Git blobs were matched to the reviewed local bytes. Preserve both generated files and manifest instead of creating a capture-SHA-only rewrite. All historical text, task IDs/headings/statuses, frozen ROADMAP/PHASES, source/tests/migrations, inventory and workflows remain unchanged.

**Checks and stop:** artifact/file/source hashes, raw XML/case-set equivalence, source membership, canonical snapshot/runtime equality, generated-base compatibility and actual catalog/dictionary regeneration checks above all actually passed. Hosted Full/Fast/Docs are the runtime execution evidence; this documentation stage claims no newly executed local Java21/PostgreSQL test suite. Publish directly to main under expected0679293 guard, verify parent/tree/exact one-file scope, observe Documentation CI creation/start, then STOP without waiting. C is complete as evidence reconciliation, not formal HAPI-589 VERIFIED/COMPLETED. The next single registered stage is `docs(topology): record draft survey authoring acceptance` after C's real Documentation result, assessing the bounded A/B contract and preserving all broader publication/qualification/deployment gates. HAPI-590/HAPI-600 and later tasks are not selected.


### HAPI-589 formal bounded draft survey authoring acceptance — 2026-10-20

**Registered acceptance stage:** `docs(topology): record draft survey authoring acceptance`. Owner Next selects acceptance after the registered A/B implementation, two responsible CI dependency/artifact repairs and successful C evidence reconciliation. HAPI-589 / HPR-P25-052C is now **VERIFIED / COMPLETED for the explicitly registered draft survey/profile authoring contract**. This decision supersedes the preceding pending/status statements at their dated checkpoints; all failed-run, repair, decision and scope history remains preserved. Only HAPI-589's two status fields/header synchronize in this phase record and the frozen ROADMAP/PHASES task rows. Every other task and the HPS-005 phase keep their independent disposition.

**Accepted exact-source baseline and unchanged-current proof:** runtime/test/artifact source `067929306170ad69b453823a90bb7beeb6b8130d`, tree `443757db54b5854d5091385d44415dee93f68e7f`, passed [Full #777 run39423247020](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39423247020), [Fast #101 run39426090155](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39426090155) and [Docs #455 run39422781763](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39422781763). C `068433dd2a07c30d1406b80369f0c0aaa3a4c2fb`, parent0679293, tree `b803ea7f4b979c83ad5fa4e908b09ff0ca7d6946`, passed [Docs #456 run39427028211](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/39427028211). Current-tree comparison to the successful runtime tree changes only C's phase evidence file; runtime code, test sources, migrations, security, inventory, generated API and physical dictionary blobs are identical. No nonexistent Full/Fast run on the C documentation SHA is claimed.

**Evidence revalidated for acceptance:** successful aggregate12265527590 `hidra-full-ci-evidence-067929306170ad69b453823a90bb7beeb6b8130d-39423247020-1`, ZIP SHA256 `9aa04df6a595ed37388f08125d16db7efcba4d52fa181dd01fdc4f83c80e93c3`. Rechecked all six gate-pinned lane receipts and every retained file digest, exact source/run/attempt/inventory context, actual raw XML identities and disjoint partition/oracle equality. Inventory fingerprint `2c2c4347e433f95ba677afe4295a3eb163ed9b5e38b513547867dbbc508f176d`; unit1717 + PostgreSQL0 243 + PostgreSQL1 646 = oracle2606 cases/430 classes, zero failures/errors/skips. C's source-matched eight relevant suites contain115 actual cases: A domain14, codec5, service4, HTTP5, survey boundary3, combined engineering PostgreSQL39, protected executor37 and route authorization8. This includes all14 A cases and31 B additions within retained regression suites. Exact source hashes/case membership remain in B/C evidence; presence alone was not substituted for actual execution. Unrestricted oracle fallback remains mandatory; acceptance changes no CI qualification policy.

**Accepted behavior and proof:** immutable pure-Java per-PIPE survey/profile values preserve explicit supplied CRS authority/code, ordered axes/units, optional declared vertical datum, origin/direction, metre chainage, decimal values/scales, precision, evidence, intermediate points and section boundaries. Invalid required metadata, null/duplicate/unordered/overlapping/out-of-range values and excessive inputs reject deterministically; omitted elevation or section information remains absent. Canonical bounded storage is reproducible and rejects malformed, unknown, noncanonical, truncated/trailing and oversized payloads.

The existing company/network engineering draft remains the one aggregate/transaction/version owner. Survey UPDATE requires current native UPDATE, positive expected shared version and an actual PIPE in the selected graph; it atomically appends preserved metadata plus the immutable per-branch survey snapshot. READ requires native READ and an actual metadata version in that draft, with exact current/as-of selection. Missing survey returns explicit incomplete draft diagnostics; a new graph version invalidates the prior current binding with409 until re-authored, while compatible historical reads and metadata-only successors preserve original bytes. Strict Jackson3 DTOs and exact compiled GET/PUT route delegation expose transport-owned records and decimal strings, never persistence/domain objects or caller-created authority evidence.

Real synthetic LOCAL/OIDC/LDAP/AD producer paths, signed bearer HTTP survey GET and current UPDATE DENY executed successfully. Actual PostgreSQL proofs cover wrong/absent/CONNECTOR and foreign scope, shared metadata/graph/survey version interleaving and stale CAS, historical bytes, concurrent survey/metadata and survey/graph writers with exactly one successor, foreign station owner and committed owner ABA, real writer-first/owner-first55P03 fences, expired final guard full rollback and actual catalog/privilege/constraint rejection. Domain/HTTP/architecture and existing Identity/regression suites remain in Full777. The35-relation survey path (31 SHARE_ROW_EXCLUSIVE/3 ACCESS_SHARE/1 SHARE) binds actor/time/physical transaction, exact graph/survey bytes and station generations in one final scalar SQL guard. Existing metadata31 and graph34 paths remain accepted and unchanged. Indeterminate-write classification/no retry is explicitly tested at the service boundary, with retained technical executor tests; no real unknown database commit is claimed to have been induced.

**Accepted generated contract/catalog:** the successful API/oracle documents are byte-identical. Current API member SHA256 `46e16275b762a1109c4f6c60f08698368f56c34e8349fdff64d8baef425b0c04`,249 paths/271 operations/258 schemas; actual base is b49fbeadea2319f7349540aa5ac08d3d3afdebe4. Canonical runtime equality and supported generated-base compatibility pass; retained canonical SHA256 `fec5cdcdfc2da9a100998dcdef1dfde223629552a8e52faf90032ed48de1acf5`. Successful catalog SHA256 `fd3c04a9dc6eb9d4d0c99ec722fa7399458618c9cd5b4f6a1d51cf8f51579eb4`, source bundle `be0f97cd7280aa97c0e84de040a632fbf494518e705fa10b358f793d136b9221`; PostgreSQL16.15,157 migrations/470 JPA mappings/502 relations/zero unresolved owners. All627 migration/JPA source hashes and current Git blobs match; unchanged inventory/validate/render reproduces successful dictionary SHA256 `90f156c418a74a82b0c319d04e08126c3b17377f3d363304a26587291f9637cd`. Retained committed dictionary SHA256 `6813d00d1bfb7b262415bca60fbadc63e639ee4ce10f3f5afe359022c630575e` matches after only the approved capture-SHA normalization. Earlier failed-generation provenance stays historical; C's successful run/artifact record is the later full verification.

**Bounded acceptance and retained gates:** supported CRS/datum descriptors are explicit supplied metadata; no EPSG resolution, projection/axis compatibility, geodetic accuracy, coordinate/height conversion, inferred elevation, calibration or real survey correctness is established. Chainage is a supplied reference, not computed physical pipe length. Pipe sections are authored intervals/evidence only; no diameter/material/roughness/composition or hydraulic qualification is inferred. This acceptance covers protected draft authoring/history reads, not public governed publication/effective history HAPI-590, installed-unit completion HAPI-600, physical/network engineering approval, numerical simulation, production deployment, SITE commissioning or autonomous OT action. Downstream tasks retain their independent status and exact policy/registration gates.

**Exact3 MODIFY scope and validation:** this phase record, doc/roadmap/ULTIMATE_ROADMAP.md and doc/roadmap/ULTIMATE_PHASES.md. Preserve all IDs, descriptions, objectives, priorities, dependency edges, membership/order, other statuses and earlier evidence; synchronize only HAPI-589's two status fields/header. Artifact/hash/source/case equivalence and current-tree/runtime identity checks above actually passed; Java21/PostgreSQL results are the actual hosted runs, not newly executed local runtime claims. Publish under expected-C-HEAD guard, verify parent/tree/exact3-file scope and blobs, observe applicable Documentation CI creation/start, then STOP without waiting. The next review may inspect HAPI-590's live prerequisites and registration, without automatically selecting blocked publication code.

<!-- task-register:payload:end -->
