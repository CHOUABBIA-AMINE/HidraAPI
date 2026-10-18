# HPS-019 — Simulation and Digital Twin: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-019 — Simulation and Digital Twin

<a id="hapi-167"></a>
## HAPI-167 — scan simulation domain models

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMC-022

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 6 simulation model files.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationCandidateStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationCandidateStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 31. HMC-022 — Simulation module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `simulation`  
**Baseline model files:** 6  
**Scanned models:** 6  
**Declared fields/components:** 81  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Simulation model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| SimulationCandidateChange | record | 13 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java` | Extracted |
| SimulationModel | record | 12 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java` | Extracted |
| SimulationOptimizationCandidate | record | 11 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java` | Extracted |
| SimulationRecommendation | record | 13 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java` | Extracted |
| SimulationRun | record | 16 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java` | Extracted |
| SimulationScenario | record | 16 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java` | Extracted |

### Simulation fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| SimulationCandidateChange | 1 | id | String |
| SimulationCandidateChange | 2 | candidateId | String |
| SimulationCandidateChange | 3 | changeTypeId | String |
| SimulationCandidateChange | 4 | targetType | String |
| SimulationCandidateChange | 5 | targetId | String |
| SimulationCandidateChange | 6 | beforeValue | String |
| SimulationCandidateChange | 7 | afterValue | String |
| SimulationCandidateChange | 8 | unitCode | String |
| SimulationCandidateChange | 9 | requiresTopologyChange | boolean |
| SimulationCandidateChange | 10 | requiresOperationalProcedure | boolean |
| SimulationCandidateChange | 11 | safetyCritical | boolean |
| SimulationCandidateChange | 12 | explanation | String |
| SimulationCandidateChange | 13 | createdAt | Instant |
| SimulationModel | 1 | id | String |
| SimulationModel | 2 | code | String |
| SimulationModel | 3 | nameAr | String |
| SimulationModel | 4 | nameFr | String |
| SimulationModel | 5 | nameEn | String |
| SimulationModel | 6 | modelTypeId | String |
| SimulationModel | 7 | topologyScopeType | String |
| SimulationModel | 8 | topologyScopeId | String |
| SimulationModel | 9 | status | SimulationModelStatus |
| SimulationModel | 10 | description | String |
| SimulationModel | 11 | createdAt | Instant |
| SimulationModel | 12 | updatedAt | Instant |
| SimulationOptimizationCandidate | 1 | id | String |
| SimulationOptimizationCandidate | 2 | runId | String |
| SimulationOptimizationCandidate | 3 | candidateNumber | int |
| SimulationOptimizationCandidate | 4 | candidateStatus | SimulationCandidateStatus |
| SimulationOptimizationCandidate | 5 | feasible | boolean |
| SimulationOptimizationCandidate | 6 | objectiveScore | BigDecimal |
| SimulationOptimizationCandidate | 7 | rank | Integer |
| SimulationOptimizationCandidate | 8 | summaryText | String |
| SimulationOptimizationCandidate | 9 | selectedByActorId | String |
| SimulationOptimizationCandidate | 10 | selectedAt | Instant |
| SimulationOptimizationCandidate | 11 | createdAt | Instant |
| SimulationRecommendation | 1 | id | String |
| SimulationRecommendation | 2 | runId | String |
| SimulationRecommendation | 3 | candidateId | String |
| SimulationRecommendation | 4 | recommendationTypeId | String |
| SimulationRecommendation | 5 | recommendationStatus | SimulationRecommendationStatus |
| SimulationRecommendation | 6 | title | String |
| SimulationRecommendation | 7 | description | String |
| SimulationRecommendation | 8 | confidenceLevelId | String |
| SimulationRecommendation | 9 | targetModule | String |
| SimulationRecommendation | 10 | targetProposalReference | String |
| SimulationRecommendation | 11 | publishedByActorId | String |
| SimulationRecommendation | 12 | publishedAt | Instant |
| SimulationRecommendation | 13 | createdAt | Instant |
| SimulationRun | 1 | id | String |
| SimulationRun | 2 | scenarioId | String |
| SimulationRun | 3 | modelVersionId | String |
| SimulationRun | 4 | inputSnapshotId | String |
| SimulationRun | 5 | runTypeId | String |
| SimulationRun | 6 | status | SimulationRunStatus |
| SimulationRun | 7 | requestedByActorId | String |
| SimulationRun | 8 | requestedByDisplayNameSnapshot | String |
| SimulationRun | 9 | queuedAt | Instant |
| SimulationRun | 10 | startedAt | Instant |
| SimulationRun | 11 | completedAt | Instant |
| SimulationRun | 12 | durationMillis | Long |
| SimulationRun | 13 | solverProfileId | String |
| SimulationRun | 14 | correlationId | String |
| SimulationRun | 15 | failureReason | String |
| SimulationRun | 16 | createdAt | Instant |
| SimulationScenario | 1 | id | String |
| SimulationScenario | 2 | code | String |
| SimulationScenario | 3 | nameAr | String |
| SimulationScenario | 4 | nameFr | String |
| SimulationScenario | 5 | nameEn | String |
| SimulationScenario | 6 | scenarioTypeId | String |
| SimulationScenario | 7 | modelId | String |
| SimulationScenario | 8 | modelVersionId | String |
| SimulationScenario | 9 | topologySnapshotId | String |
| SimulationScenario | 10 | planningReferenceId | String |
| SimulationScenario | 11 | monitoringContextId | String |
| SimulationScenario | 12 | status | SimulationScenarioStatus |
| SimulationScenario | 13 | createdByActorId | String |
| SimulationScenario | 14 | createdByDisplayNameSnapshot | String |
| SimulationScenario | 15 | createdAt | Instant |
| SimulationScenario | 16 | updatedAt | Instant |

### HMC-022 reconciliation

```text
baseline model files = 6
extracted models     = 6
exceptions           = 0
declared fields      = 81
zero-field models    = 0
```

The scan records only declarations under `simulation/domain/model`. Simulation domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure model, solver implementation, topology implementation, planning implementation, monitoring implementation, or database column metadata was mixed into this module scan.

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

- `6ce9380e748981768a47425534d47c9461a75ebe` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:150](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L150)
- [docs/roadmap/model-field-catalogue.md:2575](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2575)
- [docs/roadmap/model-field-catalogue.md:2682](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2682)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:150 | HMC-022 | docs(catalogue): scan simulation domain models | Scan all 6 simulation model files. | **Completed** — 6/6 models, 81 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-185"></a>
## HAPI-185 — remediate semantic review SimulationModel

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMR-009

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review SimulationModel

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationModelRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationModelResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationModelCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationModelSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java`
- `src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationModelSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java`, `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationServiceTest.java`, `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelVersionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationModelRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationModelResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationModelCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationModelSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java`
- `src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationModelSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-009 — simulation.SimulationModel

- Source review: `HMSR-009`
- Exact commit: `fix(simulation): remediate semantic review SimulationModel`
- Status: **Completed** — unique code is enforced application-side and by database index; French name, governed topology scope type, and timestamps are intrinsic domain invariants; model type resolves to an active `SIMULATION_MODEL_TYPE`; supplied topology scope pairs use the dedicated Topology-owned Simulation contract; `PIPELINE_SYSTEM`/`PIPELINE` must exist and be ACTIVE, while supplied `SEGMENT_GROUP`/`FACILITY_NETWORK` references fail closed until Topology owns those representations.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 6
- Additive Flyway: `src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationModelRequest.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationModelResponse.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationModelCommand.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationModelSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelVersionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql`
  - `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationModelSemanticRemediationTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationModelSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Unique model code:** the Simulation target definition explicitly marks `code` unique. The live schema has only non-unique `ix_hidra_simulation_model_code`, and the repository/application create path has no demonstrated uniqueness guard.
  2. **Mandatory French name:** `nameFr` is required by target semantics and `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank and normalizes blank input to `null`.
  3. **Mandatory and governed topology scope type:** `topologyScopeType` is required by target semantics and JPA/schema but is not guarded by the domain/application path; no demonstrated validation restricts it to the documented scope vocabulary.
  4. **Topology scope pair validation:** when `topologyScopeId` is supplied, the application must validate `topologyScopeType + topologyScopeId` through a Topology-owned contract. No single subject target may be guessed and no current validation is demonstrated.
  5. **Model-type catalog-family validation:** HRA-111 proves only that `modelTypeId` points to some Simulation catalog row. Creation does not demonstrate that it belongs to `SIMULATION_MODEL_TYPE` or satisfies the applicable active/reference policy.
  6. **Required timestamps:** `createdAt` and `updatedAt` are required by persistence semantics but not guarded by the domain constructor. The current service supplies both; a deliberate enforcement boundary must remain part of the final baseline.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationModelSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `a76cdcfa60f826cb6f97756bedc3a20c7b27c79e` — exact registered-message commit; surviving paths matched to current tree.
- `0ded3693cbbfc1821f3f3d9969e9ff42f12d8976` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:49](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L49)
- [doc/model-remediation/RECONCILIATION.md:13694](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13694)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15434](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15434)
- [docs/data definition/Simulation.md:1102](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Simulation.md#L1102)
- [docs/roadmap/model-semantic-remediation.md:155](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L155)
- [docs/roadmap/model-semantic-remediation.md:759](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L759)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:155 | HMR-009 | HMSR-009 | simulation | SimulationModel | — | — | fix(simulation): remediate semantic review SimulationModel | **Blocked — HMR-009A required: SimulationModel creation needs a Topology-owned `(scopeType, scopeId)` validation contract, but the live Simulation `TopologySnapshotLookupPort` is unimplemented/insufficient and the only exported Topology operational-scope contract is Organization-specific with a mismatched vocabulary (`FACILITY`/`EQUIPMENT` instead of `SEGMENT_GROUP`/`FACILITY_NETWORK`).**
doc/model-remediation/RECONCILIATION.md:13694 | HMR-009 | Historical reference; unchanged | INDEPENDENT_UNCHANGED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15434 | HMR-009 | Historical reference; unchanged | INDEPENDENT_UNCHANGED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-217"></a>
## HAPI-217 — remediate semantic review SimulationScenario

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMR-034

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review SimulationScenario

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationScenarioRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationScenarioResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationScenarioCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationScenarioSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationScenarioUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioAssumptionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioAssumptionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioJpaRepository.java`
- `src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationScenarioSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationScenarioUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioAssumptionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationScenarioRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationScenarioResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationScenarioCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationScenarioSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationScenarioUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioAssumptionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioAssumptionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioJpaRepository.java`
- `src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationScenarioSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-034 — simulation.SimulationScenario

- Source review: `HMSR-038`
- Exact commit: `fix(simulation): remediate semantic review SimulationScenario`
- Status: **Completed** — unique code, scenario-type family, model/version parent consistency, mandatory French/creator snapshot fields, and fail-closed execution-time owner-reference validation are enforced.
- SCC: —
- Recorded upstream HMS dependencies: simulation.SimulationModel
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationScenarioRequest.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationScenarioResponse.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationScenarioCommand.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationScenarioSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationScenarioUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioAssumptionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioAssumptionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationScenarioSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationScenarioSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce unique scenario code.** Protect the DDD-defined unique `SimulationScenario.code` at the persistence boundary and align application behavior.
  2. **Enforce scenario-type catalog-family semantics.** `scenarioTypeId` must resolve specifically to `SIMULATION_SCENARIO_TYPE`, not merely any Simulation catalog row.
  3. **Enforce model/model-version parent consistency.** The selected `modelVersionId` must belong to the selected `modelId`.
  4. **Require DDD-mandated nonblank `nameFr` and `createdByDisplayNameSnapshot`.** Canonical construction must not admit values that violate the stated scenario contract and final schema.
  5. **Fail closed on scenario input references at the appropriate lifecycle boundary.** Before READY/LOCKED/execution, the required Topology snapshot and any populated Planning/Monitoring references must resolve through their existing owner-module lookup ports or equivalent boundary validation.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationScenarioSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `65f8ee77cb198f01e47a84890338291ffa1a44cc` — exact registered-message commit; surviving paths matched to current tree.
- `225360ee08ac9058b4878310fa338b1565c57bc8` — exact registered-message commit; surviving paths matched to current tree.
- `7f30e8e95d50b8fd3da59cae1d620900595635c9` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:185](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L185)
- [docs/roadmap/model-semantic-remediation.md:1940](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1940)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:185 | HMR-034 | HMSR-038 | simulation | SimulationScenario | — | simulation.SimulationModel | fix(simulation): remediate semantic review SimulationScenario | Planned
~~~~~~~~

<a id="hapi-218"></a>
## HAPI-218 — register SimulationScenario reference prerequisite

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMR-034A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

register SimulationScenario reference prerequisite

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationExternalReferenceResolver.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationExternalReferenceResolver.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationExternalReferenceResolver.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-034A — SimulationScenario owner-reference validation prerequisite

- Source: HMR-034 / HMSR-038 obligation 5 plus live Simulation application/port evidence.
- Exact commit: `docs(model-remediation): register SimulationScenario reference prerequisite`
- Status: **Completed** — the missing authoritative owner-reference validation scope is explicitly registered; no production Java/JPA/Flyway mutation is part of HMR-034A.
- Type: documentation/architecture prerequisite.
- Live evidence:
  1. `SimulationApplicationService.createSimulationScenario(...)` is the authoritative scenario creation path and is not present in the original HMR-034 write allowlist.
  2. Simulation already declares `TopologySnapshotLookupPort`, `PlanningSnapshotLookupPort`, and `MonitoringContextLookupPort`, each exposing `available(String referenceId)`, but no production implementation of those ports is present on live `main`.
  3. `SimulationExternalReferenceResolver` exposes topology/planning/monitoring availability methods, but the only live implementation is `NoopSimulationExternalReferenceResolver`, which returns `true` unconditionally and therefore cannot satisfy the fail-closed HMSR obligation.
  4. The repository architecture classifies cross-module stable references as owner-validated through exported application contracts and explicitly forbids cross-module database foreign keys.
  5. The Simulation DDD defines `topologySnapshotId` as a required Topology snapshot reference, `planningReferenceId` as an optional plan/revision reference, and `monitoringContextId` as an optional monitoring/evaluation context; no narrower target meaning is established and none may be invented by HMR-034.
- Required decisions:
  1. HMR-034 must validate scenario input references at the application lifecycle boundary before a scenario can become READY/LOCKED/executable; DRAFT construction alone is not sufficient evidence of readiness.
  2. The existing Simulation lookup ports remain the consumer-side contracts. HMR-034 may wire them into `SimulationApplicationService`; it must not perform repository lookups in the domain constructor.
  3. Owner-side validation must remain behind deliberate exported application contracts. HMR-034 must not import Topology/Planning/Monitoring domain models, repositories, JPA entities, or infrastructure adapters and must not add cross-module database foreign keys.
  4. The unconditional no-op resolver is not acceptable for authoritative validation and must not be used to satisfy obligation 5.
  5. `planningReferenceId` remains the already documented opaque plan/revision reference and `monitoringContextId` remains the already documented opaque monitoring/evaluation reference; HMR-034 must not invent a narrower ownership subtype without owner-module evidence.
  6. HMR-034 production scope is expanded only as necessary to include the authoritative application service and the existing Simulation lookup-port/integration wiring required to enforce obligation 5. Any required owner-module exported contract that is absent on live `main` must be introduced as a narrow Simulation-facing availability contract, not as shared/domain leakage.
  7. The original HMR-034 semantic obligations 1-4, exact commit message, additive migration rule, and original allowlist remain unchanged.
- Newly authorized HMR-034 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`
- HMR-034 remains the next production remediation after this prerequisite commit is observed. Do not start HMR-035 automatically.


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

- `ed8e4dd69e6759abe0bd2c723896f3010a0114c5` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:1981](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1981)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-235"></a>
## HAPI-235 — remediate semantic review SimulationRun

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMR-046

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review SimulationRun

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/QueueSimulationRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/QueueSimulationRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStepStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunStepJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStepStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunStepJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/QueueSimulationRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/QueueSimulationRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStepStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunStepJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-046 — simulation.SimulationRun

- Source review: `HMSR-055`
- Exact commit: `fix(simulation): remediate semantic review SimulationRun`
- Status: **Completed** — queueing now requires a LOCKED scenario plus SIMULATION_RUN_TYPE and SIMULATION_SOLVER_PROFILE family membership, and persisted COMPLETED run state is immutable under repository writes.
- SCC: —
- Recorded upstream HMS dependencies: simulation.SimulationScenario
- HMSR correction count: 4
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/QueueSimulationRunRequest.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/command/QueueSimulationRunCommand.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStepStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunStepJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunStepJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationRunSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require the referenced SimulationScenario to be `LOCKED` before a SimulationRun is queued/executed.** Scenario existence alone is insufficient; enforce lifecycle eligibility at the authoritative Simulation application boundary.
  2. **Enforce `SIMULATION_RUN_TYPE` catalog-family semantics for `runTypeId`.** The referenced catalog row must belong to the documented run-type family.
  3. **Enforce `SIMULATION_SOLVER_PROFILE` catalog-family semantics for `solverProfileId`.** The referenced catalog row must belong to the documented solver-profile family.
  4. **Enforce immutability of COMPLETED SimulationRun business state.** Once completed, ordinary run fields must not be changed through repository/application writes, except separately modeled publication/archival metadata authorized by the DDD.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `a615725113de5a55bb9320b5c130339a452de4e3` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:202](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L202)
- [docs/roadmap/model-semantic-remediation.md:2585](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2585)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:202 | HMR-046 | HMSR-055 | simulation | SimulationRun | — | simulation.SimulationScenario | fix(simulation): remediate semantic review SimulationRun | Planned
~~~~~~~~

<a id="hapi-236"></a>
## HAPI-236 — register SimulationRun application prerequisite

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMR-046A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

register SimulationRun application prerequisite

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunJpaRepository.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunJpaRepository.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-046A — SimulationRun application-boundary prerequisite

- Source: HMR-046 / HMSR-055 plus live Simulation queue/catalog evidence.
- Exact commit: `docs(model-remediation): register SimulationRun application prerequisite`
- Status: **Completed** — the authoritative queue application service is added to the HMR-046 write scope; HMR-046A itself changes documentation only.
- Type: documentation/application-boundary prerequisite.
- Live evidence:
  1. HMR-046 requires a SimulationScenario to be LOCKED before a run is queued/executed.
  2. The actual authoritative queue path is `SimulationApplicationService.queueSimulationRun(...)`; it already resolves the scenario and invokes `scenario.executable()`, but `SimulationApplicationService.java` is absent from the original HMR-046 allowlist.
  3. HMR-046 also requires `runTypeId` to belong to `SIMULATION_RUN_TYPE` and `solverProfileId` to belong to `SIMULATION_SOLVER_PROFILE`; these checks belong at the same queue boundary and can be backed by native catalog-family queries in the already-authorized `SimulationRunJpaRepository` / adapter / port.
  4. No cross-module owner contract is needed: SimulationScenario and Simulation catalog rows are Simulation-owned.
  5. COMPLETED-run immutability can be enforced at the already-authorized SimulationRun repository adapter by comparing the persisted completed run before save, analogous to the HMR-037 published-version guard.
- Required decisions:
  1. Add `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java` to the HMR-046 write allowlist.
  2. Queue execution must fail closed unless the referenced SimulationScenario exists and has status `LOCKED`.
  3. Queue execution must fail closed unless `runTypeId` belongs specifically to `SIMULATION_RUN_TYPE`.
  4. Queue execution must fail closed unless `solverProfileId` belongs specifically to `SIMULATION_SOLVER_PROFILE`.
  5. Do not invent additional run/scenario lifecycle transitions beyond the reviewed obligations.
  6. Once an existing persisted SimulationRun is `COMPLETED`, an identical re-save may be treated as a no-op, but any ordinary business-state mutation must fail closed.
  7. No Flyway migration is required for HMR-046 unless live evidence later proves a database-level correction is necessary; existing same-module FKs/catalog tables already exist.
- Newly authorized HMR-046 production file:
  - `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- HMR-046 remains the current production remediation after this prerequisite is observed. Do not start HMR-047 automatically.


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

- `9bdefd18c6ff1156420f4c9036bb81942fe4ba63` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:2625](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2625)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-272"></a>
## HAPI-272 — remediate semantic review SimulationCandidateChange

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMR-078

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review SimulationCandidateChange

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationCandidateChangeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCandidateChangeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/SimulationTopologyTargetContractAdapter.java`
- `src/main/resources/db/migration/V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/integration/SimulationTopologyTargetContractAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationCandidateChangeRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCandidateChangeJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationCandidateChangeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCandidateChangeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/SimulationTopologyTargetContractAdapter.java`
- `src/main/resources/db/migration/V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/integration/SimulationTopologyTargetContractAdapterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-078 — simulation.SimulationCandidateChange

- Source review: `HMSR-092`
- Exact commit: `fix(simulation): remediate semantic review SimulationCandidateChange`
- Status: **Completed — implementation; exact-head batch CI pending**
- SCC: —
- Recorded upstream HMS dependencies: simulation.SimulationOptimizationCandidate
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationCandidateChangeRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCandidateChangeJpaRepository.java`
  - `src/main/resources/db/migration/V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationCandidateChangeSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce all DDD-required candidate-change values before persistence.** `targetType` and `afterValue` must be nonblank in addition to the already-enforced `targetId`.
  2. **Validate `changeTypeId` against the active/eligible exact `SIMULATION_CHANGE_TYPE` catalog family**, not only generic Simulation catalog existence.
  3. **Validate the Topology-owned target through a Topology-owned lookup/application boundary** for supported `targetType`/`targetId` combinations while preserving scalar references and avoiding cross-module database FKs.


Accepted SIM-PREREQ-01 supersedes the original narrow scope with the exhaustive Batch 13
Ultimate Roadmap list. Live HMSR-092 obligations revalidated; scalar owner lookup, required
content and catalog/SQL guards implemented. Java syntax/type checks and 30 boundary harness
checks passed with dependency stubs. Maven compile/focused tests blocked before compilation
by uncached Spring Boot parent 4.1.1 in offline mode; full Java 21/PostgreSQL CI pending.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/integration/SimulationTopologyTargetContractAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `d15e78f4c37c8a5915d28e2aa13d9c080a109224` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:113](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L113)
- [doc/model-remediation/RECONCILIATION.md:13725](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13725)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15465](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15465)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:112](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L112)
- [docs/data definition/Simulation.md:1123](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Simulation.md#L1123)
- [docs/roadmap/model-semantic-remediation.md:244](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L244)
- [docs/roadmap/model-semantic-remediation.md:4168](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4168)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:244 | HMR-078 | HMSR-092 | simulation | SimulationCandidateChange | — | simulation.SimulationOptimizationCandidate | fix(simulation): remediate semantic review SimulationCandidateChange | Planned
doc/model-remediation/RECONCILIATION.md:113 | HMR-078 | HMSR-092 | simulation.SimulationCandidateChange | COMPLETED — CI #590 GREEN | Accepted SIM-PREREQ-01; required change values, active exact catalog and Topology target lookup; forward 001.
doc/model-remediation/RECONCILIATION.md:13725 | HMR-078 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:112 | HMR-078 | HMSR-092 | [simulation.SimulationCandidateChange](../domain/SEMANTIC_DECISIONS.md#simulation-simulationcandidatechange) | [SimulationCandidateChangeSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java) | [V20261008_001__hmr_078_simulation_candidate_change_integrity](../../src/main/resources/db/migration/V20261008_001__hmr_078_simulation_candidate_change_integrity.sql); [SimulationTopologyTargetContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15465 | HMR-078 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-273"></a>
## HAPI-273 — remediate semantic review SimulationRecommendation

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMR-079

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review SimulationRecommendation

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/SimulationRecommendationAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/SimulationRecommendationAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/PublishSimulationRecommendationRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRecommendationResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/PublishSimulationRecommendationCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRecommendationSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRecommendationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/resources/db/migration/V20261008_002__hmr_079_simulation_recommendation_integrity.sql`
- `src/main/resources/db/migration/V20261008_003__provision_simulation_recommendation_audit_taxonomy.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/SimulationRecommendationAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/SimulationRecommendationAuditContractAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/data definition/Simulation.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/SimulationRecommendationAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/SimulationRecommendationAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/PublishSimulationRecommendationRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRecommendationResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/PublishSimulationRecommendationCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRecommendationSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRecommendationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/resources/db/migration/V20261008_002__hmr_079_simulation_recommendation_integrity.sql`
- `src/main/resources/db/migration/V20261008_003__provision_simulation_recommendation_audit_taxonomy.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/SimulationRecommendationAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-079 — simulation.SimulationRecommendation

- Source review: `HMSR-093`
- Exact commit: `fix(simulation): remediate semantic review SimulationRecommendation`
- Status: **Completed — implementation; exact-head batch CI pending**
- SCC: —
- Recorded upstream HMS dependencies: simulation.SimulationOptimizationCandidate, simulation.SimulationRun
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261008_002__hmr_079_simulation_recommendation_integrity.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Simulation.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/PublishSimulationRecommendationRequest.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRecommendationResponse.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/command/PublishSimulationRecommendationCommand.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRecommendationSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRecommendationJpaRepository.java`
  - `src/main/resources/db/migration/V20261008_002__hmr_079_simulation_recommendation_integrity.sql`
  - `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=SimulationRecommendationSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce all DDD-required recommendation content before persistence:** `title`, `description`, and `createdAt` must be valid at the domain/application boundary.
  2. **Validate `recommendationTypeId` against the active/eligible exact `SIMULATION_RECOMMENDATION_TYPE` family**, not merely generic Simulation catalog existence.
  3. **When `confidenceLevelId` is supplied, validate it against the active/eligible exact `SIMULATION_CONFIDENCE_LEVEL` family.**
  4. **When `candidateId` is supplied, validate the optional same-module candidate reference fail-closed and add/retain appropriate same-module persistence protection consistent with repository integrity architecture.**
  5. **Make publication emit audit-ready evidence through the Audit-owned/application boundary** as required by the Simulation DDD; do not introduce cross-module database coupling.


Accepted SIM-PREREQ-01 supersedes the original narrow scope with the exhaustive Batch 13
Ultimate Roadmap list. Live HMSR-093 obligations revalidated independently. Required content,
exact eligible catalog references, optional local candidate integrity and audited transactional
publication implemented; Audit taxonomy forward 003 attached. Generic PUBLISHED writes and
legacy publication implementations fail closed. Existing lifecycle and optional actor/export
metadata preserved. Local Java syntax/type and 28 actual-boundary harness checks passed with
dependency stubs; Maven compile/focused/full test/clean verify blocked before compilation by
uncached Boot parent 4.1.1 offline. Real Spring/JPA/PostgreSQL tests await final exact-head CI.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/SimulationRecommendationAuditContractAdapterTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationSemanticPostgresIntegrationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `9863f11ed20ee5eec415756f55f4add9d2b0084f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:114](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L114)
- [doc/model-remediation/RECONCILIATION.md:13726](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13726)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15466](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15466)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:113](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L113)
- [docs/data definition/Simulation.md:1134](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Simulation.md#L1134)
- [docs/roadmap/model-semantic-remediation.md:245](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L245)
- [docs/roadmap/model-semantic-remediation.md:4206](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4206)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:245 | HMR-079 | HMSR-093 | simulation | SimulationRecommendation | — | simulation.SimulationOptimizationCandidate, simulation.SimulationRun | fix(simulation): remediate semantic review SimulationRecommendation | Planned
doc/model-remediation/RECONCILIATION.md:114 | HMR-079 | HMSR-093 | simulation.SimulationRecommendation | COMPLETED — CI #590 GREEN | Required content, exact catalogs, nullable local references and transactional Audit-owned publication; forward 002/003.
doc/model-remediation/RECONCILIATION.md:13726 | HMR-079 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:113 | HMR-079 | HMSR-093 | [simulation.SimulationRecommendation](../domain/SEMANTIC_DECISIONS.md#simulation-simulationrecommendation) | [SimulationRecommendationSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java) | [V20261008_002__hmr_079_simulation_recommendation_integrity](../../src/main/resources/db/migration/V20261008_002__hmr_079_simulation_recommendation_integrity.sql); [SimulationRecommendationAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/SimulationRecommendationAuditContract.java); [SimulationTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15466 | HMR-079 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-319"></a>
## HAPI-319 — review simulation SimulationModel

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMSR-009

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review simulation SimulationModel

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 22. HMSR-009 — simulation.SimulationModel review

**Decision:** REVISE  
**Review code:** HMSR-009  
**Dependency level:** 0  
**Bounded context:** simulation  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `simulation.SimulationScenario` through `modelId`  
**Transitive dependents:** 5  
**Unresolved/non-subject references:** 1 — `topologyScopeId`, now resolved as a typed/polymorphic Topology reference with no single HMS subject target

### 22.1 Semantic role and ordering rationale

`SimulationModel` is the Simulation-owned reusable model-family definition. It identifies a simulation model by business code, localized names, model type, default Topology scope and technical lifecycle state. Executable immutable configuration belongs to `SimulationModelVersion`; scenarios reference the stable model identity and, when frozen for execution, a specific model version.

It is Level 0 because none of its prerequisites are another HMS subject model. `modelTypeId` is a Simulation-owned catalog prerequisite outside the 123 HMS subjects, while `topologyScopeId` is a typed cross-module reference selected by `topologyScopeType`.

Simulation remains decision-support only. It does not own physical Topology, measured Telemetry facts, approved Planning state, Monitoring deviation truth, Workflow approval state or Audit evidence.

### 22.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable SimulationModel identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable unique business code for the reusable model definition. |
| `nameAr` | `String` | Optional | Arabic model display name. |
| `nameFr` | `String` | Mandatory | French model display name in the target Simulation contract and persistence schema. |
| `nameEn` | `String` | Optional | English model display name. |
| `modelTypeId` | `String` | Mandatory | Simulation-owned catalog reference in family `SIMULATION_MODEL_TYPE`. |
| `topologyScopeType` | `String` | Mandatory | Discriminator for the default Topology target namespace; target vocabulary includes `PIPELINE_SYSTEM`, `PIPELINE`, `SEGMENT_GROUP`, `FACILITY_NETWORK`. |
| `topologyScopeId` | `String` | Optional | Optional Topology scope reference whose target meaning is selected by `topologyScopeType`. |
| `status` | `SimulationModelStatus` | Mandatory | Technical lifecycle: `DRAFT`, `ACTIVE`, or `RETIRED`. |
| `description` | `String` | Optional | Human-readable model purpose. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp. |

The current constructor already rejects blank `id`, `code`, and `modelTypeId`, rejects null `status`, trims textual values and converts blank optional strings to `null`.

### 22.3 Model-type dependency resolution

Current repository evidence resolves `modelTypeId` to the Simulation-owned `SIMULATION_MODEL_TYPE` catalog:

- the Simulation data definition identifies `SIMULATION_MODEL_TYPE` as the model classification family;
- `SimulationCatalogEntry` is a Simulation read/persistence model outside the 123 HMS subjects;
- HRA-111 installs `fk_hra111_simulation_016`;
- that FK points `hidra_simulation_model.model_type_id` to `hidra_simulation_catalog_entry(id)` with `ON DELETE RESTRICT`.

Thus `modelTypeId` is a value/catalog dependency, not a subject edge. The FK proves row existence only; it does not prove that the row belongs to `SIMULATION_MODEL_TYPE` or is usable.

### 22.4 Topology-scope reference resolution

The Simulation target definition gives `topologyScopeId` the missing discriminator semantics through the pair `topologyScopeType + topologyScopeId`. Because the discriminator can identify different Topology namespaces, this is a typed/polymorphic cross-module reference, not one direct edge to `PipelineSystem`, `Pipeline`, `Facility`, or another single HMS subject.

It therefore remains outside the 123-node graph. Simulation must not introduce a cross-module database FK for this typed reference; validation belongs through a Topology-owned lookup/contract that interprets both fields together.

### 22.5 Model/version boundary

The target design distinguishes stable model identity from immutable executable configuration. `SimulationModelVersion` owns solver profile, model-definition hash, compatibility marker and version lifecycle. Completed runs must reference an immutable model version rather than only the mutable model definition.

`SimulationModelVersion` is outside the 123 HMS subjects, so it does not add a graph edge. `SimulationScenario` remains the one direct HMS dependent through `modelId`.

### 22.6 Persistence and application consistency

The live domain and JPA models agree on all 12 declared components. The base migration makes `id`, `code`, `name_fr`, `model_type_id`, `topology_scope_type`, `status`, `created_at`, and `updated_at` non-null.

The current `SimulationApplicationService.createSimulationModel` creates a generated ID, copies command values, initializes status to `DRAFT`, supplies timestamps and saves directly through `SimulationModelRepositoryPort`.

The repository port exposes only `save` and `findById`. No code uniqueness lookup, Simulation catalog validation or Topology-scope validation contract is used by the current create path.

### 22.7 Required revisions

The aggregate role and dependency direction are correct, but the target semantics are not enforced consistently enough for APPROVED status.

1. **Unique model code:** the Simulation target definition explicitly marks `code` unique. The live schema has only non-unique `ix_hidra_simulation_model_code`, and the repository/application create path has no demonstrated uniqueness guard.
2. **Mandatory French name:** `nameFr` is required by target semantics and `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank and normalizes blank input to `null`.
3. **Mandatory and governed topology scope type:** `topologyScopeType` is required by target semantics and JPA/schema but is not guarded by the domain/application path; no demonstrated validation restricts it to the documented scope vocabulary.
4. **Topology scope pair validation:** when `topologyScopeId` is supplied, the application must validate `topologyScopeType + topologyScopeId` through a Topology-owned contract. No single subject target may be guessed and no current validation is demonstrated.
5. **Model-type catalog-family validation:** HRA-111 proves only that `modelTypeId` points to some Simulation catalog row. Creation does not demonstrate that it belongs to `SIMULATION_MODEL_TYPE` or satisfies the applicable active/reference policy.
6. **Required timestamps:** `createdAt` and `updatedAt` are required by persistence semantics but not guarded by the domain constructor. The current service supplies both; a deliberate enforcement boundary must remain part of the final baseline.

These corrections belong at the appropriate domain/application/database boundary. Repository lookups for catalog or Topology references must remain outside the domain record constructor.

HMSR-009 does not modify production Java, JPA, Flyway, API/application contracts, tests, catalog data or Topology data.

### 22.8 Lifecycle and operational interpretation

`SimulationModelStatus` is correctly represented as a technical lifecycle enum: `DRAFT`, `ACTIVE`, `RETIRED`. Historical model identities remain valid after retirement, while new scenarios should use governed active model/version combinations.

For SONATRACH/TRC pipeline operations, a SimulationModel may represent hydraulic, optimization, capacity/what-if or incident-response analytical behavior over a pipeline system, pipeline, segment group or facility network. It never becomes the authority for the physical network.

An optimized configuration remains a Simulation recommendation/candidate until exported to the owning module, approved through Workflow where required, recorded by Audit and executed through authorized operational procedures outside direct Simulation control.

### 22.9 Review conclusion

**REVISE.** `SimulationModel` is the correct foundational Simulation model and its dependency direction is sound. `modelTypeId` is now resolved to `SIMULATION_MODEL_TYPE`, while `topologyScopeId` is resolved as a typed/polymorphic Topology reference with no single HMS target.

The target baseline cannot mark it APPROVED while model-code uniqueness, required French name, topology-scope discriminator/pair validation and model-type catalog-family validation remain unenforced. HMS reconciliation must retain these obligations until an explicitly authorized Simulation correction task resolves them or the target semantics are explicitly changed.

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

- `a4c77fa83c672a827af4df7030eb99e10d5d00d1` — exact registered-message commit; surviving paths matched to current tree.
- `fafc3187814c65c55f5c9c98592493fd4249e832` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:61](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L61)
- [docs/roadmap/model-semantic-review.md:204](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L204)
- [docs/roadmap/model-semantic-review.md:4051](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L4051)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:204 | HMSR-009 | 0 | simulation | SimulationModel | — | — | 1 | 5 | 1 | REVISE | Completed | docs(model-review): review simulation SimulationModel
doc/domain/SEMANTIC_DECISIONS.md:61 | HMSR-009 | [simulation.SimulationModel](#simulation-simulationmodel) | REVISE | HMR-009
~~~~~~~~

<a id="hapi-348"></a>
## HAPI-348 — review simulation SimulationScenario

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMSR-038

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review simulation SimulationScenario

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 51. HMSR-038 — simulation.SimulationScenario review

**Decision:** REVISE  
**Review code:** HMSR-038  
**Dependency level:** 1  
**Bounded context:** simulation  
**Confirmed upstream subject dependencies:** `simulation.SimulationModel` through `modelId`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 4  
**Unresolved/non-subject references:** 3 in the original register; all are now semantically classified

### 51.1 Semantic role and ordering rationale

`SimulationScenario` is the Simulation-owned what-if/optimization case that selects a simulation model and immutable model version, fixes an input Topology snapshot, optionally links Planning and Monitoring context, records scenario classification and author snapshot, and moves through DRAFT/READY/LOCKED/ARCHIVED lifecycle before execution.

It is Level 1 because `modelId` references the already-reviewed HMS subject `SimulationModel`. `SimulationModelVersion` is a retained Simulation read/persistence model outside the 123 HMS subject set. Topology, Planning, Monitoring, and actor references remain boundary references and do not create new HMS ownership edges.

### 51.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable scenario identity. |
| `code` | `String` | Mandatory business code; Simulation DDD explicitly says it is unique. |
| `nameAr` | `String` | Optional Arabic label. |
| `nameFr` | `String` | Mandatory French label in DDD/JPA/schema. |
| `nameEn` | `String` | Optional English label. |
| `scenarioTypeId` | `String` | Mandatory `SIMULATION_SCENARIO_TYPE` catalog reference. |
| `modelId` | `String` | Required SimulationModel reference. |
| `modelVersionId` | `String` | Required immutable SimulationModelVersion reference. |
| `topologySnapshotId` | `String` | Required immutable Topology input snapshot reference. |
| `planningReferenceId` | `String` | Optional Planning plan/revision/snapshot context. |
| `monitoringContextId` | `String` | Optional Monitoring/evaluation context. |
| `status` | `SimulationScenarioStatus` | DRAFT, READY, LOCKED, ARCHIVED. |
| `createdByActorId` | `String` | Required actor identity reference/snapshot source. |
| `createdByDisplayNameSnapshot` | `String` | Required actor display snapshot. |
| `createdAt` | `Instant` | Persistence-required creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required update timestamp. |

The domain already rejects blank ID, code, scenario type, model, model version, topology snapshot and creator actor, and requires a non-null status. `executable()` correctly returns true only for LOCKED scenarios, matching the DDD rule that a scenario must be locked before execution.

### 51.3 Model and model-version integrity

HRA-111 protects:

```text
scenario.model_id         -> hidra_simulation_model.id
scenario.model_version_id -> hidra_simulation_model_version.id
```

so both rows must exist.

However, `SimulationModelVersion` itself has a mandatory `modelId` parent. Neither the record, application service, nor database constraints prove that the selected `modelVersionId` belongs to the same `modelId` stored on the scenario.

The create service accepts both IDs independently and persists them directly. A scenario can therefore pair model A with a version belonging to model B while satisfying both independent foreign keys.

That parent-consistency relationship must fail closed before the scenario becomes authoritative.

### 51.4 Scenario-type catalog semantics

Simulation DDD lists `SIMULATION_SCENARIO_TYPE` among the governed business catalog families and describes `scenarioTypeId` as the scenario classification.

HRA-111 protects only generic row existence:

```text
scenario_type_id -> hidra_simulation_catalog_entry.id
```

No current create-path lookup verifies that the selected row belongs to `SIMULATION_SCENARIO_TYPE` or is otherwise eligible under Simulation catalog policy.

HMSR-038 therefore resolves the target but retains a catalog-family validation obligation.

### 51.5 Unique scenario code

Simulation DDD explicitly defines `code` as a **unique scenario code**.

The base schema creates an ordinary index on `hidra_simulation_scenario(code)`, not a unique constraint/index, and `SimulationScenarioRepositoryPort` exposes no code lookup used by `createSimulationScenario()`.

Duplicate scenario business codes are therefore currently persistable.

### 51.6 Required French label and creator display snapshot

The DDD marks both `nameFr` and `createdByDisplayNameSnapshot` as required. JPA/schema likewise declare both columns `NOT NULL`.

The domain constructor normalizes both fields but does not reject null/blank values, and the create service passes command values directly. Thus the canonical domain can accept a state that cannot satisfy its documented/persistence contract.

Both fields require consistent nonblank validation at the authoritative domain/application boundary.

### 51.7 Topology, Planning and Monitoring input references

Simulation DDD requires `topologySnapshotId` and provides `TopologySnapshotLookupPort`. It also provides `PlanningSnapshotLookupPort` and `MonitoringContextLookupPort` for the optional Planning and Monitoring references.

The current `SimulationApplicationService.createSimulationScenario()` injects none of those lookup ports and performs no availability validation.

A DRAFT scenario need not necessarily prove every execution-time condition immediately, so HMSR-038 does not invent that all external references must be validated specifically at creation. But before READY/LOCKED/execution, required Topology snapshot availability and any populated optional Planning/Monitoring references must be resolved through the owner-module lookup boundaries.

The repository currently lists `LockSimulationScenarioUseCase` and validation use cases as target contracts in the DDD, but they are not present in the active service. HMSR-038 records the boundary obligation without manufacturing those capabilities during semantic review.

### 51.8 Snapshot/execution semantics

The DDD rule:

```text
A scenario must be locked before execution to freeze inputs and assumptions.
```

is reflected locally by `SimulationScenario.executable()`.

The broader Simulation contract also requires runs to use immutable model versions and immutable input snapshots. A scenario stores a topology snapshot directly, while a run later references a separate `SimulationInputSnapshot`. HMSR-038 does not collapse these concepts: the scenario's topology snapshot is scenario input context; the run snapshot freezes the complete execution input set.

No status-transition matrix beyond the lock-before-execution rule is explicitly defined, so no DRAFT→READY→LOCKED transition sequence is invented here.

### 51.9 Optional references and actor semantics deliberately bounded

`planningReferenceId` and `monitoringContextId` are optional by DDD and schema; HMSR-038 does not make them mandatory.

`createdByActorId` remains a cross-module actor reference. Simulation does not own Identity state, so no cross-module database FK is introduced. The required display-name snapshot provides historical readability, not actor authorization.

No evidence establishes scenario-code uniqueness per model or per organization; DDD says simply unique, so the correction must honor that stated scope unless Simulation DDD is explicitly revised.

### 51.10 Required revisions

Five evidence-backed correction obligations remain:

1. **Enforce unique scenario code.** Protect the DDD-defined unique `SimulationScenario.code` at the persistence boundary and align application behavior.
2. **Enforce scenario-type catalog-family semantics.** `scenarioTypeId` must resolve specifically to `SIMULATION_SCENARIO_TYPE`, not merely any Simulation catalog row.
3. **Enforce model/model-version parent consistency.** The selected `modelVersionId` must belong to the selected `modelId`.
4. **Require DDD-mandated nonblank `nameFr` and `createdByDisplayNameSnapshot`.** Canonical construction must not admit values that violate the stated scenario contract and final schema.
5. **Fail closed on scenario input references at the appropriate lifecycle boundary.** Before READY/LOCKED/execution, the required Topology snapshot and any populated Planning/Monitoring references must resolve through their existing owner-module lookup ports or equivalent boundary validation.

HMSR-038 does not modify production Java, JPA, Flyway, API/application contracts, tests, simulation scenarios/models/catalogs, or provisioned data.

### 51.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, a SimulationScenario is governed decision-support input. Its model/version pairing must identify the exact hydraulic or operational model configuration being evaluated, and its Topology snapshot must anchor the scenario to a known network state.

Duplicate scenario codes, wrong scenario taxonomy, mismatched model/version identities, or unresolved snapshot/context references weaken reproducibility and auditability and can invalidate later optimization or operational recommendations.

Simulation remains advisory and must not mutate Topology, Planning, Monitoring, telemetry, or control systems directly.

### 51.12 Review conclusion

**REVISE.** `SimulationScenario` has a coherent 16-field Simulation-owned model, correct Level-1 dependency on SimulationModel, explicit immutable model-version and Topology-snapshot references, appropriate lifecycle vocabulary, and a correct lock-before-execution predicate.

The target baseline cannot mark it APPROVED while unique-code enforcement, scenario catalog-family semantics, model/version parent consistency, required French/actor-snapshot fields, and owner-boundary reference validation remain incomplete.

HMS reconciliation must retain these obligations until an explicitly authorized Simulation correction task resolves them or the governing Simulation DDD is explicitly revised with stronger evidence.

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

- `784b24b56b88fdbfcfbaa7a07d22d08aa27ec945` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:90](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L90)
- [docs/roadmap/model-semantic-review.md:233](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L233)
- [docs/roadmap/model-semantic-review.md:8516](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L8516)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:233 | HMSR-038 | 1 | simulation | SimulationScenario | — | simulation.SimulationModel | 1 | 4 | 3 | REVISE | Completed | docs(model-review): review simulation SimulationScenario
doc/domain/SEMANTIC_DECISIONS.md:90 | HMSR-038 | [simulation.SimulationScenario](#simulation-simulationscenario) | REVISE | HMR-034
~~~~~~~~

<a id="hapi-365"></a>
## HAPI-365 — review simulation SimulationRun

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMSR-055

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review simulation SimulationRun

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 68. HMSR-055 — simulation.SimulationRun review

**Decision:** REVISE  
**Review code:** HMSR-055  
**Dependency level:** 2  
**Bounded context:** simulation  
**Confirmed upstream subject dependencies:** `simulation.SimulationScenario` through mandatory `scenarioId`  
**Confirmed same-module non-HMS dependencies:** `SimulationModelVersion`, `SimulationInputSnapshot`, Simulation business catalogs  
**Confirmed direct HMS subject dependents:** 2  
**Transitive HMS subject dependents:** 3

### 68.1 Semantic role and ordering rationale

`SimulationRun` is the Simulation-owned execution record for a locked scenario, fixed model version, frozen input snapshot and selected solver profile.

The DDD positions Simulation as decision support rather than operational truth. Runs must remain reproducible and traceable to the exact scenario/model/input configuration that produced their results.

It is Level 2 because mandatory `scenarioId` references the already-reviewed HMS subject `SimulationScenario`.

### 68.2 Field semantics

The 16-field model contains run identity; Scenario, ModelVersion and frozen input references; run-type and solver-profile classifications; lifecycle status; requesting actor/snapshot; queue/start/completion timing; duration; correlation/failure metadata; and creation time.

The canonical constructor already requires nonblank ID, scenario ID, model-version ID, input-snapshot ID, run-type ID, requesting actor ID and solver-profile ID; non-null status; and non-null queue time.

### 68.3 Scenario and model-version integrity

HRA-111 installs and validates:

```text
scenario_id      -> hidra_simulation_scenario.id
model_version_id -> hidra_simulation_model_version.id
```

Therefore both mandatory same-module identities are fail-closed for row existence.

The DDD also states that completed runs must reference a model version rather than a mutable model definition, which the current field model satisfies structurally.

### 68.4 Scenario must be locked before execution

The Simulation DDD explicitly states:

```text
A scenario must be locked before execution to freeze inputs and assumptions.
```

`SimulationApplicationService.queueSimulationRun()` currently accepts `scenarioId` from the command, constructs a `QUEUED` SimulationRun, and saves it directly.

The service does not load the SimulationScenario or verify `SimulationScenarioStatus.LOCKED` before queueing.

The database FK guarantees only scenario existence, not scenario lifecycle eligibility.

Therefore an unlocked DRAFT/READY/ARCHIVED scenario can be queued for execution despite the explicit DDD rule.

### 68.5 Frozen input snapshot semantics

`inputSnapshotId` is mandatory and the DDD defines SimulationInputSnapshot as an immutable snapshot reference set used for a run:

```text
Simulation must run on snapshots, not live mutable records.
```

The repository's scalar-reference integrity classification explicitly places `SimulationRun.inputSnapshotId -> SimulationInputSnapshot` in the historical/snapshot category, even though the target is same-module.

HMSR-055 preserves that architectural classification and does not manufacture a relational FK merely because the target table exists.

Current evidence reviewed here does not explicitly define a separate `run.scenarioId == inputSnapshot.scenarioId` persisted invariant, so HMSR-055 does not invent one.

### 68.6 Run-type catalog family

The DDD defines `runTypeId` as a mandatory business catalog reference with examples:

```text
WHAT_IF
OPTIMIZATION
VALIDATION
BACKTEST
```

and lists the canonical catalog family:

```text
SIMULATION_RUN_TYPE
```

HRA-111 protects `run_type_id` only with a generic FK to `hidra_simulation_catalog_entry(id)`.

That proves row existence but not family membership. No authoritative application validation was found that checks `catalogName == SIMULATION_RUN_TYPE` before queueing.

### 68.7 Solver-profile catalog family

The DDD defines `solverProfileId` as the mandatory solver profile used by the run and lists:

```text
SIMULATION_SOLVER_PROFILE
```

as the canonical business catalog family.

HRA-111 likewise protects only generic catalog-row existence for `solver_profile_id`.

No application guard was found proving that the selected catalog entry belongs to `SIMULATION_SOLVER_PROFILE`.

### 68.8 Completed-run immutability

The Simulation DDD explicitly states:

```text
A run is immutable after completion except for publication/archival metadata.
```

`SimulationRunRepositoryPort` exposes unrestricted `save(SimulationRun)` and the domain record itself has no mutation guard tied to prior persisted state.

The current application service only creates QUEUED runs, but no authoritative repository/application boundary demonstrates protection against saving changed business fields for an already COMPLETED run.

Because immutability depends on comparing proposed state with persisted state, it belongs in an application/repository/persistence policy rather than a record-local constructor check.

### 68.9 Lifecycle timestamps and duration deliberately not over-specified

The model stores `queuedAt`, optional `startedAt`, optional `completedAt` and optional `durationMillis`.

The repository-wide HRA-050 temporal-order classification did not approve SimulationRun timestamp-order rules, and the current Simulation DDD does not state exact scalar constraints such as `queuedAt <= startedAt <= completedAt`, nonnegative duration, or duration equality.

HMSR-055 therefore does not invent those rules.

Likewise, current evidence does not define a complete status/timestamp matrix requiring `completedAt` for every terminal status or `failureReason` only for FAILED.

### 68.10 Requested actor and display snapshot

`requestedByActorId` is mandatory and cross-module Identity-owned; `requestedByDisplayNameSnapshot` is persistence-required human-readable snapshot text.

No cross-module database FK should be introduced for the actor identity.

The invariant-classification policy treats generic display snapshots as persistence-only text absent stronger independent DDD evidence, so HMSR-055 does not add a separate constructor obligation for the display snapshot.

### 68.11 Required revisions

Four evidence-backed production correction obligations remain:

1. **Require the referenced SimulationScenario to be `LOCKED` before a SimulationRun is queued/executed.** Scenario existence alone is insufficient; enforce lifecycle eligibility at the authoritative Simulation application boundary.

2. **Enforce `SIMULATION_RUN_TYPE` catalog-family semantics for `runTypeId`.** The referenced catalog row must belong to the documented run-type family.

3. **Enforce `SIMULATION_SOLVER_PROFILE` catalog-family semantics for `solverProfileId`.** The referenced catalog row must belong to the documented solver-profile family.

4. **Enforce immutability of COMPLETED SimulationRun business state.** Once completed, ordinary run fields must not be changed through repository/application writes, except separately modeled publication/archival metadata authorized by the DDD.

HMSR-055 does not modify production Java, JPA, Flyway, application/API contracts, tests, simulation runs, scenarios, model versions, snapshots, catalogs, or provisioned data.

### 68.12 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, a SimulationRun must be reproducible evidence of exactly what was simulated, using a frozen scenario/input/model/solver configuration.

Executing an unlocked scenario can produce results from mutable assumptions, while wrong catalog-family references can select semantically invalid run or solver classifications. Post-completion mutation would weaken traceability of recommendations and digital-twin evidence.

These protections are therefore important for engineering decision support even though simulation results remain advisory until accepted by the owning operational module.

### 68.13 Review conclusion

**REVISE.** `SimulationRun` has a coherent 16-field execution model, protected Scenario and ModelVersion row identities, mandatory frozen input reference, explicit lifecycle status, and appropriately separated actor/correlation state.

The target baseline cannot mark it APPROVED while unlocked scenarios can be queued, run/solver catalog families are not fail-closed, and completed-run immutability is not demonstrated at an authoritative write boundary.

HMS reconciliation must retain these four obligations until explicitly authorized Simulation production corrections resolve them or stronger repository evidence changes the governing Simulation DDD.

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

- `dd5d6009945d1695ac31b13bae6195ab81e094f0` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:107](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L107)
- [docs/roadmap/model-semantic-review.md:250](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L250)
- [docs/roadmap/model-semantic-review.md:11454](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L11454)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:250 | HMSR-055 | 2 | simulation | SimulationRun | — | simulation.SimulationScenario | 2 | 3 | 2 | REVISE | Completed | docs(model-review): review simulation SimulationRun
doc/domain/SEMANTIC_DECISIONS.md:107 | HMSR-055 | [simulation.SimulationRun](#simulation-simulationrun) | REVISE | HMR-046
~~~~~~~~

<a id="hapi-376"></a>
## HAPI-376 — review simulation SimulationOptimizationCandidate

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMSR-066

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review simulation SimulationOptimizationCandidate

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationCandidateStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationCandidateStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationCandidateStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 79. HMSR-066 — simulation.SimulationOptimizationCandidate review

**Decision:** APPROVED  
**Review code:** HMSR-066  
**Dependency level:** 3  
**Bounded context:** simulation  
**Confirmed upstream subject dependencies:** `simulation.SimulationRun`  
**Confirmed direct HMS subject dependents:** 2  
**Transitive HMS subject dependents:** 2

### 79.1 Semantic role

`SimulationOptimizationCandidate` is the Simulation-owned candidate network or operating configuration produced by an optimization run. It carries parent-run identity, candidate ordering, lifecycle/selection state, feasibility, score/rank, human-readable summary and optional selection audit metadata.

Simulation owns the candidate. It does not own official Topology state.

### 79.2 Parent run integrity

`runId` is mandatory and unambiguously references `simulation.SimulationRun`.

HRA-111 protects same-module row existence with:

```text
hidra_simulation_optimization_candidate.run_id
    -> hidra_simulation_run.id
```

That is the correct bounded-context ownership model for the candidate's parent run.

HMSR-066 found no evidence requiring a cross-module dependency for candidate ownership.

### 79.3 Candidate lifecycle state

`SimulationCandidateStatus` defines:

```text
GENERATED
SELECTED
DISCARDED
PROPOSED_TO_TOPOLOGY
```

These values describe the candidate's own technical/domain lifecycle rather than a general reusable business taxonomy.

The Simulation DDD recommends a `SIMULATION_CANDIDATE_STATUS` catalog family but also explicitly allows technical lifecycle states to use constrained enums/check constraints.

Current evidence therefore does not justify replacing `SimulationCandidateStatus` with a catalog solely because a recommended catalog family is listed.

### 79.4 Feasibility, score and ranking

The model carries:

```text
feasible
objectiveScore
rank
candidateNumber
```

The DDD defines `feasible` as whether blocking constraints are satisfied and `objectiveScore` / `rank` as optimization result metadata.

Current repository evidence does not define:
- a mandatory objective score for every candidate,
- a mandatory rank for every candidate,
- a numeric bound for objective score,
- a uniqueness rule for candidateNumber or rank inside a run.

HMSR-066 therefore does not invent those constraints.

### 79.5 Selection metadata

`selectedByActorId` and `selectedAt` are optional.

The actor identity is an external Identity reference. Simulation does not own users/actors, and no cross-module database FK is appropriate.

Current DDD evidence does not state a universal invariant that every `SELECTED` row must carry both selection fields, nor that non-selected rows must keep them null. HMSR-066 therefore does not invent that state-field coupling.

Any future selection use case may validate the authenticated actor through the Identity boundary, but the present model is not semantically defective merely because selection metadata is optional.

### 79.6 Operational-safety boundary

The model exposes:

```text
officialTopologyState() == false
```

This directly reinforces the governing Simulation DDD rule:

```text
A candidate is a recommendation candidate, not an official topology version.
```

The module-level adoption path remains:

```text
SimulationOptimizationCandidate
  -> SimulationCandidateChange[]
      -> TopologyChangeProposal
          -> Workflow approval
              -> TopologySnapshot / TopologyVersion
```

Simulation must not directly update Topology or actuate SCADA/PLC/RTU/SIS/ESD equipment.

HMSR-066 finds the candidate model aligned with this safety architecture.

### 79.7 Review conclusion

**APPROVED.** `SimulationOptimizationCandidate` is a coherent 11-field Simulation-owned optimization-result model. Its mandatory parent SimulationRun is correctly protected by same-module FK, its candidate status is a legitimate lifecycle state, optional selection metadata preserves the external Identity boundary, and the domain explicitly prevents the candidate from being treated as official Topology state.

No production correction is required by HMSR-066.

HMSR-066 does not modify production Java, JPA, Flyway, API/application contracts, tests, Simulation data, Topology data, Identity data, or provisioned data.

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

- `699eef4c7285ae378bff0d021d060bd5b38ddcee` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:118](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L118)
- [docs/roadmap/model-semantic-review.md:261](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L261)
- [docs/roadmap/model-semantic-review.md:12950](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L12950)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:261 | HMSR-066 | 3 | simulation | SimulationOptimizationCandidate | — | simulation.SimulationRun | 2 | 2 | 0 | APPROVED | Completed | docs(model-review): review simulation SimulationOptimizationCandidate
doc/domain/SEMANTIC_DECISIONS.md:118 | HMSR-066 | [simulation.SimulationOptimizationCandidate](#simulation-simulationoptimizationcandidate) | APPROVED | —
~~~~~~~~

<a id="hapi-402"></a>
## HAPI-402 — review simulation SimulationCandidateChange

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMSR-092

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review simulation SimulationCandidateChange

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 105. HMSR-092 — simulation.SimulationCandidateChange review

**Decision:** REVISE  
**Review code:** HMSR-092  
**Dependency level:** 4  
**Bounded context:** simulation  
**Confirmed upstream subject dependencies:** mandatory `simulation.SimulationOptimizationCandidate`; Topology-owned target reference by scalar type/ID  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0

### 105.1 Semantic role

`SimulationCandidateChange` is the Simulation-owned descriptive representation of a proposed topology or operating change inside an optimization candidate.

The governing Simulation DDD is explicit:

```text
SimulationCandidateChange is descriptive only.
It must not execute field commands.
```

Simulation owns the recommendation candidate. Topology owns official network state. Workflow governs adoption where operationally material. This model must therefore remain proposal/evidence data rather than an actuation command.

### 105.2 Parent candidate integrity

`candidateId` is mandatory and same-module.

HRA-111 already protects it with:

```text
hidra_simulation_candidate_change.candidate_id
  -> hidra_simulation_optimization_candidate.id
```

The domain constructor also rejects blank `candidateId`.

No further basic existence correction is required for the parent candidate.

### 105.3 Change-type catalog semantics

`changeTypeId` is mandatory and Simulation-owned catalog data.

HRA-111 already protects generic existence through:

```text
hidra_simulation_candidate_change.change_type_id
  -> hidra_simulation_catalog_entry.id
```

The Simulation DDD explicitly defines the business catalog family:

```text
SIMULATION_CHANGE_TYPE
```

with examples such as:

```text
ROUTE_CHANGE
EQUIPMENT_STATE
FLOW_TARGET
PRESSURE_TARGET
SEGMENT_STATUS
FACILITY_MODE
```

Generic catalog existence alone does not prove family membership or eligibility. The authoritative write boundary must therefore validate that `changeTypeId` belongs to the active/eligible `SIMULATION_CHANGE_TYPE` family.

### 105.4 Required target/change values

The Simulation DDD marks these fields required:

```text
targetType
targetId
afterValue
```

The live constructor already rejects blank `targetId`, but only normalizes `targetType` and `afterValue`; both may therefore become null.

JPA/Flyway persist both `target_type` and `after_value` as `NOT NULL`.

Because the DDD explicitly classifies them as required candidate-change semantics, the domain/application boundary must fail fast for blank/null `targetType` and `afterValue` rather than relying on persistence failure.

### 105.5 Topology target ownership

The DDD enumerates candidate-change target types such as:

```text
PIPELINE
SEGMENT
FACILITY
EQUIPMENT
NODE
CONNECTION
```

These represent Topology-owned network objects. Simulation must not import Topology domain/JPA models or write Topology tables.

The correct boundary is therefore a scalar `targetType` + `targetId` reference resolved through a Topology-owned lookup/application contract when a candidate change is created or validated.

No cross-module Topology database FK should be introduced.

Current repository evidence does not justify copying Topology labels/codes into new mandatory fields, so HMSR-092 does not invent additional snapshot requirements.

### 105.6 Safety flags and adoption semantics

The model carries:

```text
requiresTopologyChange
requiresOperationalProcedure
safetyCritical
```

The Simulation DDD states:

```text
A candidate change marked safetyCritical must require workflow review before adoption.
A candidate submitted to topology must create a topology change proposal, not update topology tables.
Simulation must not actuate field equipment.
```

These rules govern adoption/export workflows rather than the persistence shape of the descriptive change record itself.

Repository search found no live `SimulationCandidateChange` application creation/adoption service. HMSR-092 therefore does not invent an observed workflow-bypass defect, but the future adoption path must preserve these rules.

### 105.7 Optional before value, unit and explanation

`beforeValue`, `unitCode`, and `explanation` are explicitly optional in the Simulation DDD.

HMSR-092 preserves them as optional metadata and does not promote them into invariants.

### 105.8 Required revisions

Three evidence-backed production obligations remain:

1. **Enforce all DDD-required candidate-change values before persistence.** `targetType` and `afterValue` must be nonblank in addition to the already-enforced `targetId`.

2. **Validate `changeTypeId` against the active/eligible exact `SIMULATION_CHANGE_TYPE` catalog family**, not only generic Simulation catalog existence.

3. **Validate the Topology-owned target through a Topology-owned lookup/application boundary** for supported `targetType`/`targetId` combinations while preserving scalar references and avoiding cross-module database FKs.

HMSR-092 does not modify production Java, JPA, Flyway, API/application contracts, tests, Simulation/Topology/Workflow data, or provisioned data.

### 105.9 Review conclusion

**REVISE.** `SimulationCandidateChange` correctly remains a descriptive Simulation-owned proposal record, already has same-module parent/catalog existence protection, and preserves the no-actuation boundary.

The target baseline cannot mark it APPROVED while DDD-required `targetType`/`afterValue` can reach persistence as null, `changeTypeId` is not proven to belong to `SIMULATION_CHANGE_TYPE`, and Topology-owned targets are not owner-resolved.

HMS reconciliation must retain these obligations until explicitly authorized Simulation production corrections resolve them or stronger repository evidence changes the governing Simulation DDD.

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

- `b014d5cef8a90d08854b776a00bacf470c8aa039` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:144](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L144)
- [docs/roadmap/model-semantic-review.md:287](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L287)
- [docs/roadmap/model-semantic-review.md:16554](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L16554)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:287 | HMSR-092 | 4 | simulation | SimulationCandidateChange | — | simulation.SimulationOptimizationCandidate | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review simulation SimulationCandidateChange
doc/domain/SEMANTIC_DECISIONS.md:144 | HMSR-092 | [simulation.SimulationCandidateChange](#simulation-simulationcandidatechange) | REVISE | HMR-078
~~~~~~~~

<a id="hapi-403"></a>
## HAPI-403 — review simulation SimulationRecommendation

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HMSR-093

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review simulation SimulationRecommendation

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve reproducible gas-first GZ2 orchestration and a future internal/external-engine evolution path.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 106. HMSR-093 — simulation.SimulationRecommendation review

**Decision:** REVISE  
**Review code:** HMSR-093  
**Dependency level:** 4  
**Bounded context:** simulation  
**Confirmed upstream subject dependencies:** mandatory `simulation.SimulationRun`; optional `simulation.SimulationOptimizationCandidate`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0

### 106.1 Semantic role

`SimulationRecommendation` is the Simulation-owned human-facing decision-support recommendation derived from a simulation run and, optionally, an optimization candidate.

The governing Simulation DDD keeps Simulation as decision support only. A recommendation may be exported toward an owning module, but Simulation must not directly mutate Topology, Planning, Monitoring, or field-control state.

### 106.2 Run and optional candidate integrity

`runId` is mandatory and same-module. HRA-111 already protects it with:

```text
hidra_simulation_recommendation.run_id
  -> hidra_simulation_run.id
```

The domain constructor also rejects blank `runId`.

`candidateId` is optional in the DDD but, when present, denotes a real same-module `SimulationOptimizationCandidate`. The live schema has no corresponding FK and the publication application path performs no candidate existence validation.

Because the review register already confirms `SimulationOptimizationCandidate` as an upstream model dependency, a supplied candidate reference must fail closed against the Simulation-owned candidate aggregate. Same-module persistence protection may be added consistently with repository architecture; no cross-module database dependency is involved.

### 106.3 Recommendation and confidence catalogs

`recommendationTypeId` is mandatory. HRA-111 already protects generic Simulation catalog-row existence, but the DDD defines the exact business family:

```text
SIMULATION_RECOMMENDATION_TYPE
```

with examples including `ADOPT_CONFIGURATION`, `REVIEW_CONSTRAINT`, `REJECT_SCENARIO`, and `RUN_ADDITIONAL_CASE`.

Generic catalog existence therefore does not prove family membership or active/eligible use. The authoritative publication boundary must validate the exact active/eligible recommendation-type family.

`confidenceLevelId` is optional, but the same DDD defines `SIMULATION_CONFIDENCE_LEVEL`. When supplied, it must resolve to an active/eligible entry in that exact family rather than any Simulation catalog row.

### 106.4 Required recommendation content

The DDD explicitly marks these fields required:

```text
title
description
createdAt
```

JPA/Flyway also persist them as `NOT NULL`.

The live domain constructor normalizes `title` and `description` without rejecting blank/null values and does not guard `createdAt`. The current publication service passes command values directly and relies on persistence for these failures.

Because these are explicit DDD-required recommendation semantics, the domain/application boundary must fail fast before persistence.

### 106.5 Lifecycle and publication semantics

`recommendationStatus` remains a technical lifecycle enum with:

```text
DRAFT
PUBLISHED
SENT_TO_WORKFLOW
ACCEPTED
REJECTED
SUPERSEDED
```

The live publication use case constructs the model directly in `PUBLISHED` state and stamps `publishedAt` / `createdAt`.

The governing Simulation DDD explicitly requires:

```text
A published recommendation must emit audit-ready evidence.
```

and forbids bypassing audit for published recommendations.

Repository search found no Audit port/use in `SimulationApplicationService`; the current `publishSimulationRecommendation(...)` implementation saves the published recommendation directly. The future production correction must therefore make publication emit the required audit-ready evidence through the proper Audit-owned/application boundary rather than by cross-module persistence coupling.

The DDD keeps `publishedByActorId` and `publishedAt` optional at model level, so HMSR-093 does not invent stronger unconditional field requirements beyond the explicit publication behavior above.

### 106.6 Target module/proposal reference ownership

`targetModule` and `targetProposalReference` are optional export metadata. The DDD describes the latter as a proposal identifier once exported to the target module.

These values must remain scalar/reference metadata. HMSR-093 does not introduce cross-module database foreign keys or direct writes into target-module tables.

### 106.7 Required revisions

Five evidence-backed production obligations remain:

1. **Enforce all DDD-required recommendation content before persistence:** `title`, `description`, and `createdAt` must be valid at the domain/application boundary.
2. **Validate `recommendationTypeId` against the active/eligible exact `SIMULATION_RECOMMENDATION_TYPE` family**, not merely generic Simulation catalog existence.
3. **When `confidenceLevelId` is supplied, validate it against the active/eligible exact `SIMULATION_CONFIDENCE_LEVEL` family.**
4. **When `candidateId` is supplied, validate the optional same-module candidate reference fail-closed and add/retain appropriate same-module persistence protection consistent with repository integrity architecture.**
5. **Make publication emit audit-ready evidence through the Audit-owned/application boundary** as required by the Simulation DDD; do not introduce cross-module database coupling.

HMSR-093 does not modify production Java, JPA, Flyway, API/application contracts, tests, Simulation/Audit/Topology/Planning/Monitoring data, or provisioned data.

### 106.8 Review conclusion

**REVISE.** `SimulationRecommendation` is correctly owned by Simulation and correctly modeled as a decision-support recommendation with scalar export metadata. Mandatory `runId` and recommendation-type row existence already have same-module persistence protection.

The target baseline cannot mark it APPROVED while DDD-required recommendation content can reach persistence invalid, catalog references are not family-qualified, an optional candidate reference can be accepted without fail-closed integrity, and the live publication path does not emit the audit-ready evidence explicitly required by the Simulation DDD.

HMS reconciliation must retain these obligations until explicitly authorized Simulation production corrections resolve them or stronger repository evidence changes the governing Simulation contract.

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

- `9b5fc0585121ceda274c46578cc43c61288dda51` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:145](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L145)
- [docs/roadmap/model-semantic-review.md:288](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L288)
- [docs/roadmap/model-semantic-review.md:16707](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L16707)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:288 | HMSR-093 | 4 | simulation | SimulationRecommendation | — | simulation.SimulationOptimizationCandidate, simulation.SimulationRun | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review simulation SimulationRecommendation
doc/domain/SEMANTIC_DECISIONS.md:145 | HMSR-093 | [simulation.SimulationRecommendation](#simulation-simulationrecommendation) | REVISE | HMR-079
~~~~~~~~

<a id="hapi-505"></a>
## HAPI-505 — establish immutable input manifest contracts

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-002A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-504

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement immutable manifest, source-version identities/validity/origin and steady-state/transient metadata invariants under the exact six-file envelope below.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/exception/InvalidSimulationValueException.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationInputMode.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Original evidence retained; no reopening

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/exception/InvalidSimulationValueException.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationInputMode.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/exception/InvalidSimulationValueException.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationInputMode.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
##### HPR-P25-002A exact implementation envelope

The next owner Next after successful preflight Documentation CI selects ONLY
HPR-P25-002A implementation; this preflight already registers its exact scope.
Do not create another documentation-only preflight for A unless evidence changes.

Create ONLY:

1. `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationInputMode.java`
   — enum STEADY_STATE and TRANSIENT; no implicit mode default.
2. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
   — immutable Java record with nested SourceKind/Origin enums and local validation.
3. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
   — immutable Java record with defensive list copy and coherent mode/time/version rules.
4. `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java`
   — meaningful positive/negative metadata and temporal/immutability cases.

Update ONLY `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. Total authorized scope: six paths.
Canonical Java headers keep Author Abir MEDJERAB and CreatedOn 2025-06-26.
Reuse existing InvalidSimulationValueException; production imports only Java standard
library and local Simulation types. No Spring/JPA/foreign modules, physical calculation,
controller/API, dictionary/OpenAPI regeneration, dependencies, POM, workflows, existing
models/entities or ArchitectureGuardrailTest may change. No migration is needed because
these contracts are not persisted or exposed; later capture has a separate schema gate.
Nested validation/private helpers stay within these three production files.

Minimum behavioral validation: valid synthetic steady-state and transient manifests;
required IDs and positive schema version; valid SHA-256 syntax; effective interval and
captured/recorded/state/watermark order; missing/duplicate source kinds; transient horizon
and schedule requirements; rejection of schedule in steady-state; unmodifiable defensive
copy; synthetic origin detection and preserved original version after replacement input.
Use fixed timestamps; never depend on wall-clock time or actual GZ2 values. Test only
metadata guarantees, not hydraulic accuracy. Origin declarations do not bypass trust.

Commands when A is selected:
`./mvnw -B -q -Dtest=SimulationInputManifestTest,ArchitectureGuardrailTest test`,
then `./mvnw -B -q clean verify`; also canonical documentation/OpenAPI/P1 evidence
validators and `git diff --check`. Require exact-head applicable full CI and report
local toolchain/dependency blocks accurately. Publish with expected-parent lease;
observe CI startup and STOP for owner Next/Fail. Do not implement B..D or the engine.

For B/C/D, first select a supporting exact preflight message
`docs(twin): register HPR-P25-002X execution preflight`, replacing X with B/C/D,
writing these two execution memories only. Later exact files/contracts/migrations are
not authorized by this high-level decomposition. Physical formulation/units/reference
fixtures can be designed with synthetic cases; actual GZ2 data gates field calibration.
HPR-P25-008A numerical design may start after the physical input contract is established;
no solver method is dictated by a manifest. HPR-P25-017 closure includes A..D as well as
008A..F; incomplete inputs cannot be hidden by parent-task metadata completion.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java` — exact-head oracle suite 14 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-504. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `eef35dbd35d4558a4ceb57f87d8670fb97c933ea` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:7471](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L7471)
- [doc/model-remediation/RECONCILIATION.md:13632](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13632)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6069](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6069)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:678](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L678)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:683](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L683)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:9245](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L9245)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15372](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15372)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:678 | HPR-P25-002A | COMPLETED — implementation eef35dbd35d4558a4ceb57f87d8670fb97c933ea passed full Java 21 CI #612 and Documentation #146; three immutable contracts and 14 JUnit tests present; per-class report not independently retained at this baseline | Simulation / Input manifest | Code/Test | Implement immutable manifest, source-version identities/validity/origin and steady-state/transient metadata invariants under the exact six-file envelope below. | feat(simulation): establish immutable input manifest contracts | HPR-P25-002 preflight Documentation CI success
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15372 | HPR-P25-002A | COMPLETED within recorded bounded scope | COMPLETED_PRESERVED | Simulation / Input manifest | None; historical dependencies retained | All accepted source and evidence | None in accepted scope | Original evidence retained; no reopening | Preserved completion | None
doc/roadmap/ULTIMATE_ROADMAP.md:6069 | HPR-P25-002A | COMPLETED / ACCEPTED (recorded bounded scope) | 049 directly confirmed #666/#266; other accepted subsets retain original source/CI limits
doc/model-remediation/RECONCILIATION.md:13632 | HPR-P25-002A | COMPLETED within recorded bounded scope | COMPLETED_PRESERVED | Simulation / Input manifest | None; historical dependencies retained | All accepted source and evidence | None in accepted scope | Original evidence retained; no reopening | Preserved completion | None
~~~~~~~~

<a id="hapi-506"></a>
## HAPI-506 — define versioned physical input payloads

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-002B

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-505

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Define validated typed network/fluid/equipment/initial-state and transient timeline payloads with units, reference basis and missing-data semantics; register exact files and numeric design assumptions before mutation.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/domain/exception/InvalidSimulationValueException.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPipeSegmentInput.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Original evidence retained; no reopening

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/exception/InvalidSimulationValueException.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/domain/exception/InvalidSimulationValueException.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPipeSegmentInput.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-002B Physical-Input Payload Preflight — 2026-10-09

Owner Next selects ONLY `docs(twin): register HPR-P25-002B execution preflight`.
Write ONLY this roadmap and reconciliation. Verified parent
`929073662786da201e051170520013d4d3402ebf`, tree
`67290cdd14ca646819de3e252508e724a6abc0b7`; Documentation #148
(run 37962488460) PASSED. Full optimized #613 remains attached to executable
3e9c19142a750b1aab3ca6fb3afdf5df5906b318. Mandatory instructions/execution memories
read; source tree matches before mutation. No production source is changed here.

##### Source and data-contract findings

SimulationInputManifest and SimulationInputSourceVersion enforce immutable metadata,
source kinds, initial effective-time/capture coherence and mode/schedule references.
They contain no typed pipe geometry, fluid properties, equipment characteristics or
state/timeline values. They cannot prove physical readiness from a digest reference.
Topology's PipelineSegmentJpaEntity exposes endpoints, nullable lengthKm/KP and direction;
TopologyNodeJpaEntity exposes nullable elevationMeters/coordinates. Pipeline nominal
size/design pressure are not internal diameter/approved operating limits. Equipment
identity/attachment is not a measured characteristic curve. No such missing value
may become zero, inferred nominal dimensions or a hardcoded GZ2 parameter.

Simulation owns immutable normalized payloads copied through owner contracts in later
002C. Physical data validity/provenance comes from source versions and actual capture
verification in 002D. Keep Topology truth and existing tables unchanged. Prefer explicit
unit-bearing field names in SI for numerical payloads; retain original units/conversion
provenance in capture. Pressures will be absolute pascals, temperatures kelvin and flows
mass kg/s for the future gas contracts; volumetric readings require explicit reference
conditions/conversion rather than implicit equivalence. These are internal representation
choices, not approval of fluid equations or operating limits.

##### Physical payload stages

Split HPR-P25-002B to preserve bounded implementations. Parent B stays PENDING until
all required payload stages are complete; no stage is executed by this preflight.

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P25-002B1 | COMPLETED — 803efff1326cc20bc54b4a22d6daa8608b5233dc passed Java 21 production CI #614 and Documentation #150; three immutable pipe-graph records and 13 synthetic JUnit tests present; individual report not independently inspected | Simulation / Physical network | Code/Test | Implement immutable typed nodes/pipe segments and connected network validation using the exact six-path envelope below. | `feat(simulation): define physical network input contracts` | HPR-P25-002A; successful B preflight documentation CI |
| HPR-P25-002B2 | COMPLETED — 97332bba3861348a6d4acf2aa995d1ad537cbc1c passed Java 21 production CI #615 and Documentation #152; four immutable records and 19 synthetic JUnit tests present; per-class XML not independently inspected | Simulation / Fluid and equipment | Code/Test | Define versioned gas composition/property-method references and current equipment/curve/configuration payloads with explicit units/validity; no property calculation or universal product support is implied. | `feat(simulation): define fluid and equipment input contracts` | HPR-P25-002B1; exact B2 preflight |
| HPR-P25-002B3 | COMPLETED — 599bb58115f3d06140d8230a54d7969f743c0c3a passed Java 21 production CI #616 and Documentation #154; four immutable records and 19 synthetic JUnit tests present; per-class XML not independently inspected | Simulation / State and boundaries | Code/Test | Define measured/estimated initial-state quantities and time-dependent boundary/action schedules with quantity basis, time ordering, interpolation declarations and missing-value semantics. | `feat(simulation): define state and boundary timeline inputs` | HPR-P25-002B2; exact B3 preflight |
| HPR-P25-002B4 | COMPLETED — 938fa25d8856243cf340cecbf65ffcdbfaa27659 passed Java 21 Production #617 and Documentation #156; two assembly records and 20 JUnit methods present; per-class XML not independently inspected | Simulation / Payload assembly | Code/Test | Bind typed payload/version identities to manifests, validate node/equipment references and steady/transient completeness, preserving synthetic/provenance distinctions; canonical hash verification remains capture scope. | `feat(simulation): assemble consistent physical input payloads` | HPR-P25-002B1..B3; exact B4 preflight |

Gas/property/equipment methods and transient thermal/event fidelity will be specified
in numerical design HPR-P25-008A; payload references must not silently select a method.
Owner-provided SIMONE/SPS and other tool information supplies future benchmark context,
not proof of access, interchangeable models or accuracy. External comparisons require
identical qualified inputs, units and boundary conditions plus independent reference
and measured evidence. H2/liquid/other-product physics requires separate qualification.

##### HPR-P25-002B1 exact next implementation envelope

After successful preflight Documentation CI, the next owner Next selects ONLY B1.
This preflight already registers B1; do not repeat a preflight unless evidence changes.

Create ONLY:

1. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`
   — immutable Java record: id and required elevationMeters (BigDecimal). Signed/zero
   elevation is valid; missing elevation is not silently converted to sea level.
2. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPipeSegmentInput.java`
   — immutable Java record: id, fromNodeId, toNodeId, lengthMeters,
   internalDiameterMeters and absoluteRoughnessMeters (BigDecimal). Required positive
   length/diameter; required nonnegative roughness; distinct endpoints and nonblank IDs.
   Endpoint orientation defines a reference direction, not a prohibition of reverse flow.
   No nominal-to-internal diameter inference or hydraulic equation is introduced.
3. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
   — immutable Java record: id, topologyRevisionId, nodes and pipeSegments. Defensive
   copies; at least two nodes and one pipe; unique normalized node/pipe IDs; no null
   entries, dangling endpoints, unused isolated nodes or disconnected components.
   Validate undirected connected scope while preserving each pipe's reference direction.
   Allow cycles and distinct parallel pipes: a meshed network is not a tree.
4. `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java`
   — meaningful synthetic graph/physical-value/immutability tests.

Update ONLY this roadmap and reconciliation. Total six tracked paths; no migration
because records are not persisted/exposed. Reuse InvalidSimulationValueException.
Canonical Java headers retain Author Abir MEDJERAB, CreatedOn 2025-06-26; production
imports only Java standard library and local Simulation types. No changes to existing
manifest/source-version classes, Topology/Custody/Telemetry entities/contracts, schema,
API/OpenAPI/dictionary, dependency/POM/workflow, Kernel/Platform or architecture exports.
Private helpers stay within these three new production records; no utility package.

B1 is a pipe-graph payload, not the complete station/equipment graph or a physical
solver. Compressor/valve links and boundary policies come in later payload stages;
record any required graph schema extension in their preflights. Do not claim all assets
or a disconnected portfolio can be simulated from this connected pipe-only contract.
A geographical selection must include the connected consequence scope or be expanded
by later HPR-P25-007; rejecting a disconnected payload does not select field boundaries.

Minimum tests: valid single pipe, branched/meshed graph and parallel pipes; valid signed
or zero elevation/zero roughness; rejection of blank/null identities, missing numbers,
nonpositive length/diameter, negative roughness, identical endpoints, duplicate IDs,
dangling endpoints, null/empty lists, isolated node/disconnected components; source-list
mutation cannot alter the record and exposed lists are unmodifiable. Replacement revision
preserves earlier physical values. Use explicitly synthetic BigDecimal fixtures and no
current GZ2 values, arbitrary freshness/accuracy bounds or wall-clock dependencies.

When B1 is selected run
`./mvnw -B -q -Dtest=SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test`,
then `./mvnw -B -q clean verify`, canonical docs/OpenAPI/P1 validators and whitespace/
exact-scope checks. Report local Java/Maven/dependency limitations accurately. Full
optimized production CI and Documentation CI must start on the published implementation;
stop for owner Next/Fail, do not automatically execute B2 or the numerical engine.

B2/B3/B4 first require exact-file/migration/units/test preflights, messages
`docs(twin): register HPR-P25-002B2 execution preflight`,
`docs(twin): register HPR-P25-002B3 execution preflight`, and
`docs(twin): register HPR-P25-002B4 execution preflight`, two-document scope only.
Parent 002B/002 and phase closure include all four substeps; code or mathematical
choices beyond B1 are not authorized by this envelope. Live GZ2 data remains a
calibration/field-acceptance input; synthetic contract development can proceed.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java` — exact-head oracle suite 14 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-505. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:7635](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L7635)
- [doc/model-remediation/RECONCILIATION.md:13633](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13633)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6070](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6070)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:679](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L679)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:734](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L734)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:9375](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L9375)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15373](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15373)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:679 | HPR-P25-002B | COMPLETED — B1..B4 CI-confirmed; status aligned by owner-approved consolidation; source resolution/capture remain parent 002 obligations | Simulation / Physical payloads | Code/Test | Define validated typed network/fluid/equipment/initial-state and transient timeline payloads with units, reference basis and missing-data semantics; register exact files and numeric design assumptions before mutation. | feat(simulation): define versioned physical input payloads | HPR-P25-002A; selected payload/physics preflight
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15373 | HPR-P25-002B | COMPLETED within recorded bounded scope | COMPLETED_PRESERVED | Simulation / Physical payloads | None; historical dependencies retained | All accepted source and evidence | None in accepted scope | Original evidence retained; no reopening | Preserved completion | None
doc/roadmap/ULTIMATE_ROADMAP.md:6070 | HPR-P25-002B | COMPLETED / ACCEPTED (recorded bounded scope) | 049 directly confirmed #666/#266; other accepted subsets retain original source/CI limits
doc/model-remediation/RECONCILIATION.md:13633 | HPR-P25-002B | COMPLETED within recorded bounded scope | COMPLETED_PRESERVED | Simulation / Physical payloads | None; historical dependencies retained | All accepted source and evidence | None in accepted scope | Original evidence retained; no reopening | Preserved completion | None
~~~~~~~~

<a id="hapi-507"></a>
## HAPI-507 — resolve versioned owner input references

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-002C

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-506

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement bounded owner-exported topology/product/measurement queries and Simulation adapters; preserve eligibility/trust/ownership and reject unavailable physical inputs.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TelemetryTrustedReadingSnapshotPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/simulation/SimulationTrustedReadingContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePortTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Implement bounded owner-exported topology/product/measurement queries and Simulation adapters; preserve eligibility/trust/ownership and reject unavailable physical inputs..

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TelemetryTrustedReadingSnapshotPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TelemetryTrustedReadingSnapshotPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/simulation/SimulationTrustedReadingContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePortTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryServiceTest.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-P25-002C Owner Query and Adapter Preflight — 2026-10-09

Owner Next selects ONLY `docs(twin): register HPR-P25-002C execution preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Verified main 938fa25d8856243cf340cecbf65ffcdbfaa27659, tree
926fb7fdf87a351eb9aafe40c1d31e920ebdd4b7: Production #617/run 37976570634
and Documentation #156/run 37976570645 completed SUCCESS on that exact SHA.
B4's 20 JUnit methods are present; individual XML reports were not independently
inspected. Remote full Java 21 CI supersedes the recorded local Maven limitation;
that historical limitation is retained. Mandatory instructions and both execution
memories read, source synchronized before mutation. No implementation executes here.

### Owner evidence and missing-data boundary

- Topology application/contract/simulation/SimulationTopologyScopeContract.java and
  application/service/TopologySimulationScopeQueryService.java export supported,
  exists and ACTIVE eligibility only for PIPELINE_SYSTEM/PIPELINE; SEGMENT_GROUP and
  FACILITY_NETWORK are explicitly unsupported. They do not export physical revisions.
  Pipeline domain/model/Pipeline.java has nominal diameter/design pressure; persistence
  PipelineSegmentJpaEntity has endpoints/nullable lengthKm, TopologyNodeJpaEntity has
  nullable elevationMeters. No domain revision/roughness/internal-diameter contract
  was found in the inspected Topology domain. Do not manufacture a revision from
  updatedAt, use a live catalog row as immutable history, or infer physical geometry.
- Custody application/contract/planning/PlanningProductReferenceContract.java and
  infrastructure/integration/PlanningProductReferenceQueryAdapter.java export identity,
  code and Planning policy eligibility under mandatory transaction/shared locks.
  Planning approval is not Simulation gas eligibility. No inspected Custody domain
  contract provides versioned gas composition, property methods or reference basis.
- Telemetry domain/model/TrustedTelemetryReading.java already retains actual values,
  units, quality assessment/code, TrustLevel, source/trusted times, original reading,
  point, asset binding, topologySnapshotId and ingestionBatchId. Its existing Monitoring
  export provides only id/pointId/trustLevel. TrustedTelemetryReadingRepositoryPort
  supports exact findById, allowing a useful owner-controlled evidence export now.
  Presence of a trusted-reading row, enum name or topologySnapshotId does not prove
  Simulation suitability, a coherent network revision or canonical input digest.
- Simulation application/port/out/TopologySnapshotLookupPort.java and
  TelemetryTrustedReadingSnapshotPort.java are boolean availability interfaces; do not
  retrofit their meaning or pretend they capture the new immutable physical payload.
  Owner equipment characteristic versions/approved limits remain missing prerequisites.

The next bounded implementation is C1: export existing raw Telemetry evidence and
adapt it into a Simulation-owned application projection without exposing Telemetry
domain/JPA classes. This is useful read-only acquisition, not completed versioned
physical-input resolution. Parent 002C/002 and aggregate B closure remain PENDING;
B1..B4 implementation CI is confirmed, no independent closure task is selected here.

### Bounded C stages

| Code | Status | Exact purpose | Exact commit message | Gate |
|---|---|---|---|---|
| HPR-P25-002C1 | COMPLETED — 28cd17ca96c7f9bbd195601f0cd24e718c8ce632 plus c6b2fa7257fd603965f4a6faa9a05b1cd094e8cb repair passed Java 21 Production #619 and Documentation #159; 16 JUnit methods present; per-class XML not independently inspected | Owner-controlled trusted-reading evidence export and Simulation adapter | `feat(simulation): resolve owner trusted reading evidence` | Successful C preflight Documentation CI; B1..B4 exact-head CI confirmed |
| HPR-P25-002C2 | COMPLETED — 15795dc000c60080dea0566d275d42d843420ba8 passed Java 21 Production #620 and Documentation #161; six contracts/adapters and 22 JUnit methods present; per-class XML not independently inspected | Topology/product eligibility adapters and explicit missing physical-revision results | `feat(simulation): resolve owner topology and product eligibility` | C1 CI; `docs(twin): register HPR-P25-002C2 execution preflight` |
| HPR-P25-002C3 | PENDING — source gaps decomposed into C3A..F; C3A exact envelope registered, later stages require preflights | Versioned physical source resolution, unit/reference conversion and Simulation suitability policy | `feat(simulation): resolve qualified physical source revisions` | C2 CI; `docs(twin): register HPR-P25-002C3 execution preflight`; demonstrated owner gaps/ownership/schema and approval evidence |

C2/C3 are ordered planning registrations, not exact write authorization. C3 may need
further bounded stages once actual source storage and policies are designed. Do not
add owner tables, trust thresholds, unit conversions or synthetic production resolvers
under C1. Parent completion requires topology/product/equipment/state revisions to be
resolved and unavailable or unqualified inputs rejected, not only these projections.
Canonical hash/capture/persistence remains 002D; solver design/execution remains 008A..F.

### C1 exact next implementation envelope

After this preflight Documentation CI succeeds, owner Next selects ONLY C1.
Create ONLY these seven paths:

1. src/main/java/dz/sh/hidra/modules/telemetry/application/contract/simulation/SimulationTrustedReadingContract.java
2. src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java
3. src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePort.java
4. src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapter.java
5. src/test/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryServiceTest.java
6. src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapterTest.java
7. src/test/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePortTest.java

Update ONLY src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java and both execution
memories: ten tracked paths total. No migration, entity/repository change, REST/API,
OpenAPI/dictionary regeneration, POM/dependencies/workflows, Kernel/Platform, existing
Simulation payload/manifest/availability port or other owner code changes.

Owner contract method: Optional<ReadingEvidence> resolve(String id). Nested immutable
record ReadingEvidence carries exactly existing fields, preserving their values:
String id, readingId, pointId; BigDecimal numericValue; String textValue;
Boolean booleanValue; String unitId, qualityCodeId, trustLevel;
Instant sourceTimestamp, trustedAt; String qualityAssessmentId, topologyAssetTypeCode,
topologyAssetId, topologyAssetCode, topologySnapshotId, ingestionBatchId.
TrustLevel is exported as its existing enum name, not a foreign domain enum.
No field called revisionId/digest, synthetic marker or approved/eligible flag is invented.
No extra value validation or normalization modifies stored evidence. The immutable
projection is not a SimulationStateQuantityInput and supplies no default physical value.

Owner service is @Service, implements owner contract, constructor-injects only
TrustedTelemetryReadingRepositoryPort. Blank/null IDs return Optional.empty without
repository access; trim nonblank lookup IDs; findById and require returned id equals
normalized requested id. Missing/mismatched rows return empty. Map all fields unchanged,
including nullable optional metadata and numeric/text/boolean alternatives. Do not
silently suppress low/untrusted evidence or promote it; explicit downstream qualification
will decide suitability. Preserve even ambiguous value alternatives for assessment;
never convert raw evidence into a known physical scalar here. Repository failures
propagate, not masquerade as a successful absence or usable snapshot.

Simulation port method Optional<ReadingEvidence> resolve(String id); its own nested
immutable record has the same fields/types, with no Telemetry import. Adapter is
@Component, constructor-injects only SimulationTrustedReadingContract; blank/null
requests return empty without owner call, nonblank IDs trimmed, returned ID must match.
Copy every field into Simulation-owned record; missing/mismatch remains empty and
owner failures propagate. No call to solver, capture, acquisition endpoint or OT.
No wiring into existing scenario/run workflows, no default/noop fallback bean.

Architecture decision: add ONLY
 dz.sh.hidra.modules.telemetry.application.contract.simulation
to deliberate exported package prefixes and add a focused positive export assertion
plus negative owner domain/persistence access assertions. Keep all existing rules and
exceptions. Foreign Telemetry access is confined to this Simulation infrastructure
adapter through that public contract; owner never imports Simulation. All new production
imports Java/local types, appropriate Spring stereotype, and this deliberate contract
only. Preserve canonical headers, Author Abir MEDJERAB, CreatedOn 2025-06-26 and actual
execution-date UpdatedOn. Nested records/helpers stay in listed files.

### Required C1 validation and stop boundary

Meaningful deterministic JUnit tests with synthetic fixtures and stub/mock ports cover:
all field values round-trip; null/blank/trimmed IDs and zero calls for invalid IDs;
missing/mismatched IDs; each TrustLevel name without reclassification; numeric zero and
negative values preserved; text/boolean and multiple/missing alternatives preserved;
null optional provenance/unit/asset fields preserved without defaults; exact fixed
source/trusted timestamps and raw units; repository/owner exceptions propagate;
constructor null dependency rejection; projection equality and replacement evidence
leaves original values unchanged. At least one distinct-field fixture guards against
swapped mapping fields. Tests claim metadata transport only, not trusted physics.

Run ./mvnw -B -q -Dtest=SimulationTrustedReadingQueryServiceTest,TelemetryTrustedReadingEvidenceQueryAdapterTest,SimulationTrustedReadingEvidencePortTest,SimulationPhysicalInputPayloadTest,ArchitectureGuardrailTest test
then ./mvnw -B -q clean verify. Use bash if wrapper is nonexecutable without chmod;
report actual environment/dependency failures. Run maintained validator tests, canonical
docs, offline OpenAPI, P1 closure evidence, whitespace and exact ten-path checks.
Require remote full CI for implementation completion. Publish exact registered message
with expected-parent lease; verify remote parent/tree/file scope, observe production
and Documentation CI startup on exact implementation head, then STOP for Next/Fail.
Do not execute C2/C3/002D/engine or phase closure automatically.

This documentation-only preflight runs the maintained 37 validator tests, canonical
docs/offline OpenAPI/P1 evidence, whitespace and exact two-path checks; no Java source
change means no new Maven verification claim. Publish with expected-parent lease,
verify remote parent/tree/two files and observe exact-head Documentation CI startup,
then STOP. Next registered selection: C1 implementation after documentation success.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR implementations, 123 permanent subjects
and development 0.7.0-SNAPSHOT preserved; no runtime solver or GZ2 calibration claimed.

Preflight validation completed: 37 maintained validator tests PASSED; validate_docs.py
PASSED (95 documents, 5063 links, 24 modules, 13 P2 rows); offline OpenAPI validator
PASSED (244 paths, 263 operations, 231 schemas); validate_p1_closure.py and
git diff --check PASSED. Exact two-document scope and historical registry retention
checked; no Maven run required for this documentation-only change.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePortTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java` — exact-head oracle suite 21 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapterTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryServiceTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Independent cases A–G; mixed parent not closed
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-506. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:8539](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L8539)
- [doc/model-remediation/RECONCILIATION.md:13638](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13638)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6075](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6075)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:680](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L680)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:10279](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L10279)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15378](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15378)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:680 | HPR-P25-002C | PENDING — C1/C2 CI-confirmed; remaining C3/002D organized into four delivery outcomes; next shared network-delivery preflight | Owner modules / Simulation | Code/Test | Implement bounded owner-exported topology/product/measurement queries and Simulation adapters; preserve eligibility/trust/ownership and reject unavailable physical inputs. | feat(simulation): resolve versioned owner input references | HPR-P25-002B; exact owner-contract/export preflight
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15378 | HPR-P25-002C | PENDING — C1/C2 CI-confirmed; remaining C3/002D organized into four delivery outcomes; next shared network-delivery preflight | FOUNDATION_SCOPE_REFINEMENT | Owner modules / Simulation | Independent successor/design 018/028; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 018/028 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6075 | HPR-P25-002C | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13638 | HPR-P25-002C | PENDING — C1/C2 CI-confirmed; remaining C3/002D organized into four delivery outcomes; next shared network-delivery preflight | FOUNDATION_SCOPE_REFINEMENT | Owner modules / Simulation | Independent successor/design 018/028; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 018/028 separately selected scope separation
~~~~~~~~

<a id="hapi-508"></a>
## HAPI-508 — capture reproducible network calculation inputs

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-002D

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-507

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Assemble coherent immutable payload/manifest captures, verify canonical hashes and evidence, persist reproducible revisions and reject incompatible/missing inputs; register forward migration only if source-backed design requires one.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Assemble coherent immutable payload/manifest captures, verify canonical hashes and evidence, persist reproducible revisions and reject incompatible/missing inputs; register forward migration only if source-backed design requires one..

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

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

- Independent cases A–G; mixed parent not closed
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-507. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13648](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13648)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6085](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6085)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:681](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L681)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15388](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15388)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:681 | HPR-P25-002D | PENDING | Simulation / Capture | Code/Test | Assemble coherent immutable payload/manifest captures, verify canonical hashes and evidence, persist reproducible revisions and reject incompatible/missing inputs; register forward migration only if source-backed design requires one. | feat(simulation): capture reproducible network calculation inputs | HPR-P25-002C; exact capture/schema preflight
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15388 | HPR-P25-002D | PENDING | FOUNDATION_SCOPE_REFINEMENT | Simulation / Capture | Independent successor/design 028; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 028 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6085 | HPR-P25-002D | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13648 | HPR-P25-002D | PENDING | FOUNDATION_SCOPE_REFINEMENT | Simulation / Capture | Independent successor/design 028; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 028 separately selected scope separation
~~~~~~~~

<a id="hapi-513"></a>
## HAPI-513 — resolve connected scope and boundary conditions

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-007

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-504, HAPI-511

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Select connected local/regional/full-network simulation scope; derive validated boundary conditions, propagate potential effects and expand scope or reject execution when boundaries are inadequate.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Select connected local/regional/full-network simulation scope; derive validated boundary conditions, propagate potential effects and expand scope or reject execution when boundaries are inadequate..

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-504, HAPI-511. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13653](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13653)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6091](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6091)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:858](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L858)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15393](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15393)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:858 | HPR-P25-007 | PENDING | Simulation / Topology | Code/Test | Select connected local/regional/full-network simulation scope; derive validated boundary conditions, propagate potential effects and expand scope or reject execution when boundaries are inadequate. | feat(simulation): resolve connected scope and boundary conditions | HPR-P25-005; HPR-P25-002
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15393 | HPR-P25-007 | PENDING | SIMULATOR_DEFERRED | Simulation / Topology | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6091 | HPR-P25-007 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13653 | HPR-P25-007 | PENDING | SIMULATOR_DEFERRED | Simulation / Topology | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-514"></a>
## HAPI-514 — execute approved hydraulic solver

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-008

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-513, HAPI-515

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

After HPR-P25-008A..F, establish governed execution of Hidra-owned gas engine for both modes with immutable inputs, product/mode checks, diagnostics, failure handling and real execution evidence.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: After HPR-P25-008A..F, establish governed execution of Hidra-owned gas engine for both modes with immutable inputs, product/mode checks, diagnostics, failure handling and real execution evidence..

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-513, HAPI-515. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13654](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13654)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6092](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6092)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:859](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L859)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15394](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15394)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:859 | HPR-P25-008 | PENDING | Simulation / Integration | Code/Test/Config | After HPR-P25-008A..F, establish governed execution of Hidra-owned gas engine for both modes with immutable inputs, product/mode checks, diagnostics, failure handling and real execution evidence. | feat(simulation): execute approved hydraulic solver | HPR-P25-008A..F; HPR-P25-007
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15394 | HPR-P25-008 | PENDING | SIMULATOR_DEFERRED | Simulation / Integration | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6092 | HPR-P25-008 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13654 | HPR-P25-008 | PENDING | SIMULATOR_DEFERRED | Simulation / Integration | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-515"></a>
## HAPI-515 — define internal gas engine numerical design

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-008A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-503, HAPI-504

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Specify gas equations/assumptions, gas-property methods, units/reference basis, boundary/initial conditions, supported network/equipment behavior, numerical method/convergence/stability plan, test tolerances and independent reference strategy.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/domain/SIMULATION_ANALYTICS_AI.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/modules/simulation.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationBoundaryScheduleInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationBoundarySeriesInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationConnectedNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentModelInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationGasFluidInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInitialStateInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayload.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Original evidence retained; no reopening

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `doc/domain/SIMULATION_ANALYTICS_AI.md`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationBoundaryScheduleInput.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationBoundarySeriesInput.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationConnectedNetworkInput.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/domain/SIMULATION_ANALYTICS_AI.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/modules/simulation.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationBoundaryScheduleInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationBoundarySeriesInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationConnectedNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentModelInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationGasFluidInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInitialStateInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayload.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-P25-008A — Internal Gas Numerical Design Execution Preflight (2026-10-10)

### Selected task and verified evidence

Owner requests development using available GZ4 documents and synthetic scenarios, **prioritizing HPR-P25-008A** and following all roadmap gates. The mandatory first step is the supporting documentation-only preflight `docs(twin): register HPR-P25-008A execution preflight`; it is **not** the separate implementation/design task `docs(simulation): define internal gas engine numerical design`. Exact source HEAD on selection: `2e897bf1c52719c0a1ff76ee949b659c28c9a654`, Documentation Validation #173 PASSED; latest executable source CI #631 PASSED at `e972f4997b1e36af2158e839d86330bb5167f36b`. Physical-input structural contracts 002A/B1–B4 and owner revision contracts C3A–D have verified previous CI evidence. P25-001 selects gas, both modes, Hidra-owned engine; GZ4 is the forward pilot under the 2026-10-10 selection amendment.

**Allowed write scope for this preflight:** exactly `doc/roadmap/ULTIMATE_ROADMAP.md` and `doc/model-remediation/RECONCILIATION.md`. No numerical algorithm, Java code, numerical test, persistence change, dependency addition, imported field data, solver run, release, deployment or later 008B–F authorization.

### HPR-P25-008A next-design envelope (requires separate owner Next after passing documentation CI)

The 008A design deliverable must provide a traceable *internal-gas* numerical specification with explicit assumptions, applicable limits, equations, error measures and independent reference plans, while remaining agnostic to real GZ4 telemetry and preserving the mandatory distinction between development fixtures and qualified operating-state data:

1. **Equations and domains:** state whether steady-state is isothermal or non-isothermal, which compressible conservation/momentum equations and friction law are proposed, how elevation and branch-node mass conservation are treated, and which simplifying assumptions constrain supported pressure/temperature/flow regimes. State transient PDE method, friction/heat-transfer/event assumptions and limitations (e.g., no unsupported waterhammer/two-phase accuracy claims). Record rejected alternatives and their evidence.
2. **Fluid properties:** explicitly select and justify qualified *candidate* gas-property/EOS/viscosity/compressibility method(s), gas composition inputs, temperature/pressure applicability, and mixture validity. NeqSim may be evaluated as a reference/property candidate, pandapipes for independent steady-state network verification, DWSIM for supplementary process comparison. Verify actual license, reproducibility and accessible interface before adopting any; do not assert external benchmark results without run evidence.
3. **Unit and reference contract:** numerical internals consistently use declared SI pressure absolute Pa, K, kg/s, m, m² and seconds; all conversions of field gauge pressure, standard volume or composition-dependent quantities are **outside solver design** and remain blocked by source-owner policy under C3E. Document conversion refusal where bases are unknown; gas-reference conditions must not be guessed.
4. **Network, equipment and boundaries:** map existing immutable `SimulationPhysicalNetworkInput`, `SimulationInitialStateInput`, `SimulationStateQuantityInput`, boundary/event contracts, `SimulationInputSourceVersion`, fluid/equipment revisions and equipment connectivity to numerical degrees of freedom; define boundary well-posedness, disconnected/unknown GZ3 links, node supply/demand sign convention, valid equipment law scope, uncertainty/status reporting and topology revision checks.
5. **Algorithms and acceptance:** propose steady network solve, residual scaling, conservation checks, singularity/nonconvergence and under-/over-constrained rejection; propose transient space/time discretization, Courant/stability/positivity criteria where applicable, event step handling, numerical conservation accounting and deterministic replay. Define a test-matrix and **design-target tolerances only**, distinguishing them from independently established solver accuracy and field operational approval.
6. **Independent validation:** define reproducible simple analytical/manufactured networks, branched conservation, inclined pipe/elevation, shut/open boundary and failure cases, spatial/time refinement and cross-method comparisons. Do not use the 960 fictional fixture records as hydraulically consistent truth; they are contract/timestamp testing only. The 2024 GZ4 sheet supplies preliminary 513.172 km mainline and 120.37 km spur, 48-inch nominal diameter, 0.015 mm roughness and five/GZ3 station interconnections for source context, NOT a fully qualified physical model or numerical reference. Six unqualified daily aggregates are excluded from property/boundary calibration.

### Exact design output ownership and gates

Before a later 008A design commit, verify existing architecture and relevant simulation contract filenames and register a **specific document output path** (prefer an existing `doc/` numerical specification if present; do not create files without an explicitly recorded allowlist). A design output may be purely documentary; no migration is selected. Retain `HPR-P25-008A` PENDING until its own exact commit `docs(simulation): define internal gas engine numerical design` is created, independently reviewed and its exact-head Documentation Validation succeeds. The future 008A owner selection must not auto-select B, C, D, E, F, P25-008 parent or C3E implementation.

This preflight completes only after successful exact-head documentation validation. P0/P1/P2 remain CLOSED; P2.5 OPEN; P3 DEFERRED; 0.7.0-SNAPSHOT unchanged; GZ4 selected first gas pilot. C3E implementation remains source-policy BLOCKED. On validation failure stop and repair only this bounded preflight. Next eligible owner Next: 008A design document exact-file assessment, then design commit with the already registered exact message if its output allowlist is settled.


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

Canonical prerequisites: HAPI-503, HAPI-504. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `d2104489de0065d950c3cf956307c18f3b79e4ef` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:10693](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10693)
- [doc/model-remediation/RECONCILIATION.md:10701](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10701)
- [doc/model-remediation/RECONCILIATION.md:10719](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10719)
- [doc/model-remediation/RECONCILIATION.md:10735](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10735)
- [doc/model-remediation/RECONCILIATION.md:13655](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13655)
- [doc/modules/simulation.md:171](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/modules/simulation.md#L171)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6093](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6093)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:561](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L561)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12433](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12433)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12441](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12441)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12459](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12459)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12475](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12475)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15395](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15395)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DESIGN COMPLETE / ACCEPTED within numerical-design scope.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:561 | HPR-P25-008A | COMPLETED — numerical design d2104489de0065d950c3cf956307c18f3b79e4ef passed exact-head Documentation Validation #176; design only, no solver execution | Simulation / Numerical design | Doc | Specify gas equations/assumptions, gas-property methods, units/reference basis, boundary/initial conditions, supported network/equipment behavior, numerical method/convergence/stability plan, test tolerances and independent reference strategy. | docs(simulation): define internal gas engine numerical design | HPR-P25-001 decisions; HPR-P25-002 input contract
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15395 | HPR-P25-008A | COMPLETED within recorded bounded scope | COMPLETED_PRESERVED | Simulation / Numerical design | None; historical dependencies retained | All accepted source and evidence | None in accepted scope | Original evidence retained; no reopening | Preserved completion | None
doc/roadmap/ULTIMATE_ROADMAP.md:6093 | HPR-P25-008A | DESIGN COMPLETE / ACCEPTED within numerical-design scope | Original numerical design/evidence retained; no runtime functionality inferred from design
doc/model-remediation/RECONCILIATION.md:13655 | HPR-P25-008A | COMPLETED within recorded bounded scope | COMPLETED_PRESERVED | Simulation / Numerical design | None; historical dependencies retained | All accepted source and evidence | None in accepted scope | Original evidence retained; no reopening | Preserved completion | None
~~~~~~~~

<a id="hapi-516"></a>
## HAPI-516 — implement internal steady state gas solver

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-008B

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-515

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement verified steady-state pipe/network calculation against analytical or independent synthetic reference cases; expose residuals, conservation, validity and nonconvergence, without claiming GZ2 calibration.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `AGENTS.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/modules/simulation.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationSolverPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationBoundarySeriesInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationGasFluidInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInitialStateInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayload.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPipeSegmentInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SteadyStateGasSolution.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/service/SimulationSafetyGuard.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolver.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateBoundaryInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolverTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Original evidence retained; no reopening

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationBoundarySeriesInput.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationGasFluidInput.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInitialStateInput.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationSolverPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `AGENTS.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/modules/simulation.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationSolverPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationBoundarySeriesInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationGasFluidInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInitialStateInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayload.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPipeSegmentInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SteadyStateGasSolution.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/service/SimulationSafetyGuard.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolver.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateBoundaryInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolverTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-P25-008B — Steady-State Gas Solver Execution Preflight (2026-10-10)

### Exact-source prerequisites and bounded selection

Owner Next selects **only** the supporting HPR-P25-008B execution preflight, exact message `docs(twin): register HPR-P25-008B execution preflight`, following the successfully accepted 008A mathematical design. Exact parent `d2104489de0065d950c3cf956307c18f3b79e4ef` passed Documentation Validation #176; 008A design is COMPLETED **as documentation only**. The last observed full source CI #631 passed on `e972f4997b1e36af2158e839d86330bb5167f36b`; no new Java code is present in subsequent documentation commits. Existing `doc/modules/simulation.md` supplies the source-verified isothermal single-phase compressible design, Darcy friction/elevation/branch conservation, explicit boundaries, error reporting and independent synthetic numerical oracle requirements. C3E qualified operating-state implementation remains BLOCKED; gas-fluid method availability and real GZ4 calibration remain unresolved.

### Preflight-only file/commit boundary

This preflight writes **exactly** `doc/roadmap/ULTIMATE_ROADMAP.md` and `doc/model-remediation/RECONCILIATION.md` and does not execute `feat(simulation): implement internal steady state gas solver`. No Java files, tests, POM, migrations, fixture imports, runtime adapters, additional modules or later 008C–F tasks may be changed here. HPR-P25-008B remains PENDING.

### Required exact-file implementation authorization before any 008B code

A separately owner-selected next review must enumerate **actual existing simulation module/domain numerical and application API ownership**; propose the smallest Java/test class list and exact write allowlist; verify no numerical classes or conflicts already exist; confirm gas-property evaluation approach and mathematical equation closure; and prohibit source methods or external library dependencies not explicitly admitted by 008A. No Java or test filenames are authorized yet. No database migration is presumed. The authorization must identify the applicable `AGENTS.md` Java header, architecture guardrails, source root, exact expected commit, and verification commands.

The eventual 008B implementation must deliver an independently testable **steady-state** synthetic ideal-gas/explicitly property-qualified calculation of connected pipe/network pressures and oriented mass flows with properly specified boundary conditions; pipe geometry/elevation/friction, property-domain checks, mass residuals, explicit convergence/nonconvergence, under-/over-constraint and unsupported-state rejection. Reproducible analytical/manufactured reference cases must be independent of the 960 GZ4-inspired fictional state rows, which are **not** a mass-conserving pressure/flow oracle. Unqualified daily aggregates, GZ4 field SCADA, and guessed gas composition/reference conditions must not be used to assert GZ4 calibration or approved operations. A simplistic ideal-gas reference is synthetic benchmark math only, not the GZ4 approved property method. No transient physics, equipment law, external direct OT actuation or physical GZ4 readiness may be inferred.

**Validation gate for later 008B:** targeted deterministic JUnit and numerical reference tests, conservation/residual/nonconvergence and architecture tests, `bash ./mvnw -B -q clean verify`, and exact-head full Java 21 CI and Documentation Validation as applicable. State the actual test count/observed result only after execution; an external reference tool comparison requires actual same-assumption run evidence.

The next eligible owner Next is the **HPR-P25-008B exact-file implementation authorization/preflight assessment**, not the implementation itself. No auto-execution of 008B, 008C, 008D, 008E or C3E. P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, GZ4 selected gas pilot; `0.7.0-SNAPSHOT` remains unchanged.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java` — exact-head oracle suite 21 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateBoundaryInputTest.java` — exact-head oracle suite 19 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolverTest.java` — exact-head oracle suite 32 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-515. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `f09f6bad12051100fa26a6d91d3ab7961fef6779` — exact registered-message commit; surviving paths matched to current tree.
- `c56313b9b8199947b423e41f91549ff9b82bcc86` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:10742](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10742)
- [doc/model-remediation/RECONCILIATION.md:10763](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10763)
- [doc/model-remediation/RECONCILIATION.md:10792](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10792)
- [doc/model-remediation/RECONCILIATION.md:10797](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10797)
- [doc/model-remediation/RECONCILIATION.md:10802](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10802)
- [doc/model-remediation/RECONCILIATION.md:10826](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10826)
- [doc/model-remediation/RECONCILIATION.md:10847](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L10847)
- [doc/model-remediation/RECONCILIATION.md:11039](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11039)
- [doc/model-remediation/RECONCILIATION.md:11095](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11095)
- [doc/model-remediation/RECONCILIATION.md:13656](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13656)
- [doc/modules/simulation.md:230](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/modules/simulation.md#L230)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6094](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6094)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:562](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L562)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12482](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12482)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12503](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12503)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12532](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12532)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12537](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12537)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12542](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12542)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12566](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12566)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12587](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12587)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12779](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12779)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12835](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12835)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15396](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15396)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: COMPLETED / ACCEPTED (recorded bounded scope).

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:562 | HPR-P25-008B | COMPLETED — synthetic connected pipe-network baseline f09f6bad12051100fa26a6d91d3ab7961fef6779; exact-head Production #634 and Documentation #184 passed; no field/EOS qualification | Simulation / Steady-state | Code/Test | Implement verified steady-state pipe/network calculation against analytical or independent synthetic reference cases; expose residuals, conservation, validity and nonconvergence, without claiming GZ2 calibration. | feat(simulation): implement internal steady state gas solver | HPR-P25-008A
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15396 | HPR-P25-008B | COMPLETED within recorded bounded scope | COMPLETED_PRESERVED | Simulation / Steady-state | None; historical dependencies retained | All accepted source and evidence | None in accepted scope | Original evidence retained; no reopening | Preserved completion | None
doc/roadmap/ULTIMATE_ROADMAP.md:6094 | HPR-P25-008B | COMPLETED / ACCEPTED (recorded bounded scope) | 049 directly confirmed #666/#266; other accepted subsets retain original source/CI limits
doc/model-remediation/RECONCILIATION.md:13656 | HPR-P25-008B | COMPLETED within recorded bounded scope | COMPLETED_PRESERVED | Simulation / Steady-state | None; historical dependencies retained | All accepted source and evidence | None in accepted scope | Original evidence retained; no reopening | Preserved completion | None
~~~~~~~~

<a id="hapi-517"></a>
## HAPI-517 — model gas network equipment behavior

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-008C

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-504, HAPI-516

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Integrate approved compressor/valve/regulator behavior and current curve/configuration input contracts; test constraints, flow changes and effects across connected branches.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `.github/database-dictionary-ownership.json`
- `.github/scripts/generate_data_dictionary.py`
- `AGENTS.md`
- `doc/database/DATA_DICTIONARY.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/modules/simulation.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationConnectedNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentGasSolution.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentModelInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayload.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRegulatorCharacteristicRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationSyntheticEquipmentNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SteadyStateGasSolution.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/service/SimulationEquipmentBehaviorEvaluator.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TopologyStoredPhysicalNetworkRevisionQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcSimulationEquipmentParameterRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodec.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyPhysicalNetworkRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodec.java`
- `src/main/resources/db/migration/V20261011_001__p25_simulation_regulator_parameter_revision_format.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationFluidEquipmentInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRegulatorCharacteristicRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationSyntheticEquipmentNetworkInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/service/SimulationEquipmentBehaviorEvaluatorTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolverTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/QualifiedFluidEquipmentDeliveryIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationEquipmentParameterRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodecTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Integrate approved compressor/valve/regulator behavior and current curve/configuration input contracts; test constraints, flow changes and effects across connected branches..

### 4. Functional Requirements

No new acceptance; later exact-source applicable CI and numerical evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationConnectedNetworkInput.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentGasSolution.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentInput.java`, `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentModelInput.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterRevisionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TopologyStoredPhysicalNetworkRevisionQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcSimulationEquipmentParameterRevisionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodec.java`, `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `.github/database-dictionary-ownership.json`
- `.github/scripts/generate_data_dictionary.py`
- `AGENTS.md`
- `doc/database/DATA_DICTIONARY.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/modules/simulation.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationConnectedNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentGasSolution.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentModelInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayload.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRegulatorCharacteristicRevision.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateQuantityInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationSyntheticEquipmentNetworkInput.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SteadyStateGasSolution.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/service/SimulationEquipmentBehaviorEvaluator.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TopologyStoredPhysicalNetworkRevisionQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JdbcSimulationEquipmentParameterRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodec.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyPhysicalNetworkRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodec.java`
- `src/main/resources/db/migration/V20261011_001__p25_simulation_regulator_parameter_revision_format.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationFluidEquipmentInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRegulatorCharacteristicRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationSyntheticEquipmentNetworkInputTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/service/SimulationEquipmentBehaviorEvaluatorTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolverTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/QualifiedFluidEquipmentDeliveryIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationEquipmentParameterRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodecTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## HPR-P25-008C — Equipment Execution Preflight (2026-10-10)

Owner Next selects ONLY `docs(twin): register HPR-P25-008C equipment execution
preflight`, writing exactly doc/roadmap/ULTIMATE_ROADMAP.md and
doc/model-remediation/RECONCILIATION.md. Current main
`77cdead5a48990b1f6ac72441ee3c27a8e456f92` passed exact-head Documentation
#185/run 38050232637. Executable baseline remains
`f09f6bad12051100fa26a6d91d3ab7961fef6779`, accepted by Production #634 and
Documentation #184. This action is source review and documentation, not 008C code.

### Binding owner clarification: use available data, optional requests

The owner will provide actual field data and handle calibration at deployment.
Use existing repository data/contracts and explicitly synthetic development fixtures
now. Additional information requests are OPTIONAL and must not become a prerequisite
for continuing synthetic development. Do not ask the owner to supply field maps,
measurements, operating thresholds or approvals merely to execute this numerical work.
Record unknowns, unsupported behavior and resulting validity limits explicitly; do not
fabricate unavailable facts. This clarification supersedes interpreting missing actual
field values as a development blocker. It does not turn synthetic evidence into qualified
field data or remove provenance, numerical well-posedness or operational qualification
checks. Actual runs require their necessary inputs; optional information requests do
not mean an engine can compute a missing physical parameter. C3E's real-source policy
and field/calibration claims remain separately gated, not a dependency of synthetic 008C.

### Source findings and reusable evidence

SimulationEquipmentParameterRevision already owns immutable compressor/valve data,
exact equipment endpoints, configured speed/opening, ordered maps, SI head/flow/pressure,
efficiency fractions, definition/interpolation references, supplied limits, independent
origin/effective times and pinned network/fluid identities. Its query service verifies
exact qualified source, Workflow and topology evidence; qualification is not an executable
law or operator approval. Preserve those contracts and their append-only source stores.
SimulationEquipmentModelInput / SimulationEquipmentInput provide the older local
configuration contracts; do not substitute them for the fuller stored valve maps.
SimulationConnectedNetworkInput checks connectivity of the pipe/equipment union.
SimulationPhysicalNetworkInput and the accepted SteadyStateGasSolver entry points
currently use pipe-only incidence. A genuine integration gap is equipment edges and
connectivity across equipment: equipment-only connections must not be silently dropped,
replaced by fictitious pipes, or rejected solely because the pipe subgraph is disconnected.

SimulationEquipmentParameterRevisionTest already supplies SYNTHETIC curves at speeds
1000 and 2000 RPM, flows 1 and 2 kg/s, respective heads 100/80 and 200/160 J/kg,
efficiencies 0.80/0.75, reference inlet 200000 Pa/300 K, valve maps at openings
0 and 1 with differential pressure 0/1000 Pa and flow 0/2 kg/s on the open line.
These are development data, not GZ4 equipment. They suffice for independently computed
interpolation candidates: at 1500 RPM and 1.5 kg/s, sequential linear interpolation
would yield head 135 J/kg and efficiency 0.775; at opening 0.5 and differential pressure
500 Pa, sequential linear interpolation would yield flow 0.5 kg/s. These are arithmetic
references for a proposed method, not evidence that the existing text reference executes
that method or defines compressor thermodynamics. No additional field data is requested.

### Material method/schema gaps and bounded disposition

Stored headDefinitionReference, efficiencyDefinitionReference and
interpolationMethodReference are opaque text, including generic synthetic fixture labels.
No runtime method registry, pressure-ratio/head conversion, extrapolation policy,
reverse/choked compressor/valve treatment or active equipment residual exists in these
contracts. Select and independently derive an explicitly named synthetic method before
implementing it; unsupported definitions must fail rather than all text being treated as
a common approved law. Distinguish head from shaft work/efficiency, declare temperature
and reference-inlet restrictions, and expose out-of-map/limit refusal. Do not infer real
surge/choke or thermal behavior from the supplied points. Non-strict/flat valve maps,
including the supplied zero-flow closed characteristic, require a well-posed closed-edge
constraint rather than division by zero or invented leakage. Closed equipment may split
hydraulic components; pressure anchors/well-posedness must be evaluated on the resulting
active network, while preserving every physical edge identity and selected state.

REGULATOR is absent from current topology and Simulation equipment-kind contracts;
COMPRESSOR and VALVE are the only admitted kinds. Treat regulator support as an
explicit schema/ownership gap under existing 008C, not as a valve alias. It requires
separate source-backed exact-file registration before mutation. Do not claim the whole
008C equipment obligation complete from a partial compressor/valve demonstration.
These are design tasks for the agent, not mandatory information requests to the owner.
No new deeper routine codes, broad schema rewrite or qualification bypass is selected.

### Candidate implementation envelope and independent acceptance

The next source-backed authorization must settle executable method definitions and
register a final exact-file allowlist before Java changes. Candidate bounded local paths:
- src/main/java/dz/sh/hidra/modules/simulation/domain/service/SimulationEquipmentBehaviorEvaluator.java (new)
- src/test/java/dz/sh/hidra/modules/simulation/domain/service/SimulationEquipmentBehaviorEvaluatorTest.java (new)
- src/main/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolver.java (update)
- src/main/java/dz/sh/hidra/modules/simulation/domain/model/SteadyStateGasSolution.java (update if equipment diagnostics require it)
- src/test/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolverTest.java (update)
- doc/modules/simulation.md (update)
- doc/roadmap/ULTIMATE_ROADMAP.md (update)
- doc/model-remediation/RECONCILIATION.md (update)

These eight candidates are NOT a write authorization: exact method/union-input design
may require a different bounded set, which must be registered explicitly. Numerical
code remains Simulation-domain/Java only; no controller, JPA, Workflow or owner-query
call inside iteration. Caller-supplied immutable synthetic input can reuse stored value
contracts without claiming actual qualification. Preserve both legacy solver signatures,
result compatibility and existing physical/source contracts; do not alter pipe-only
contract validation to hide equipment connectivity. No new dependency, EOS, migration,
dictionary, export, workflow, public API, Kernel/Platform, transients or OT action.

Required future acceptance: independent map interpolation/endpoints and derivatives;
unknown-method/out-of-map/reference/limit rejection; closed valve with zero flow;
compressor/valve pressure and flow relation benchmarks independently derived for the
selected synthetic law; connected branches, equipment bridges, loops/parallel edges,
orientation and calculated boundary exchanges; global mass balance; changing supplied
speed/opening changes the whole connected solution; deterministic replay, tighter
tolerances and alternative initial guesses; disconnected/ill-conditioned/unsupported
regimes and budget failure; all existing pipe-only independent references preserved.
Numerical targets must be derived and fixed during method authorization, not loosened
to conceal failures. No synthetic accuracy result is claimed by this preflight.

### Next selection, validation and publication

HPR-P25-008C remains PENDING (preflight delivered; material method/union/regulator
gaps identified). After this preflight's exact-head Documentation CI passes, a separate
owner Next selects ONLY the two-memory source-backed method and exact-file authorization,
message `docs(twin): authorize HPR-P25-008C synthetic equipment methods`.
The agent researches/derives supported synthetic methods from primary technical sources
and available fixtures; optional owner information may refine them but is not obligatory.
No implementation is selected until that authorization has registered a concrete scope.
This is a genuine mathematical/schema gap assessment, not another data-request gate.

Run maintained validator tests, canonical docs, offline OpenAPI, P1 closure, whitespace
and exact two-document append-only scope. No Maven/runtime pass is claimed for this
source-review-only action. Publish directly to main with expected-parent lease, verify
remote parent/tree/scope, observe Documentation CI startup, then STOP. Retain 008B's
accepted synthetic baseline; 008C-F/008, C3F/002D remain PENDING, C3E real qualification
BLOCKED; GZ4 first pilot/GZ2 later candidate, 0.7.0-SNAPSHOT, P0/P1/P2 CLOSED,
P2.5 OPEN, P3 DEFERRED, 57 completed HMRs and 123 catalogue subjects unchanged.

008C equipment-preflight documentation validation PASSED: 51 maintained validator
tests; canonical docs 95 documents/5,115 links/24 modules/13 P2 rows; offline OpenAPI
244 paths/263 operations/231 schemas; P1 closure and whitespace. Exact two-file
append-only scope verified. Both architecture export registries, all Java/test code,
POM/version, dictionary and workflows are unchanged. No new numerical, runtime,
PostgreSQL or deployment acceptance is claimed; executable baseline remains f09f6bad.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationEquipmentParameterRevisionTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationFluidEquipmentInputTest.java` — exact-head oracle suite 20 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java` — exact-head oracle suite 21 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRegulatorCharacteristicRevisionTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationSyntheticEquipmentNetworkInputTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/service/SimulationEquipmentBehaviorEvaluatorTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/domain/service/SteadyStateGasSolverTest.java` — exact-head oracle suite 32 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/QualifiedFluidEquipmentDeliveryIntegrationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationEquipmentParameterRevisionPostgresIntegrationTest.java` — exact-head oracle suite 11 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/SimulationEquipmentParameterRevisionCodecTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java` — exact-head oracle suite 20 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- No new acceptance; later exact-source applicable CI and numerical evidence
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-504, HAPI-516. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `434ec3df5d16dca047494e204cc4b8a2c37880f0` — exact registered-message commit; surviving paths matched to current tree.
- `a4b385677a56117914a563119bb9ceddd6a8499e` — exact registered-message commit; surviving paths matched to current tree.
- `ed035bf6f28c9d2fbbd3624a0a73d06cc988bae2` — exact registered-message commit; surviving paths matched to current tree.
- `5c5a836a2f802bc134597757889beb9c139a2722` — exact registered-message commit; surviving paths matched to current tree.
- `6df925ea80da9ad7a75a7f4a503bcb68f4db4805` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:11156](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11156)
- [doc/model-remediation/RECONCILIATION.md:11292](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11292)
- [doc/model-remediation/RECONCILIATION.md:11569](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11569)
- [doc/model-remediation/RECONCILIATION.md:11578](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11578)
- [doc/model-remediation/RECONCILIATION.md:11609](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11609)
- [doc/model-remediation/RECONCILIATION.md:11650](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11650)
- [doc/model-remediation/RECONCILIATION.md:11661](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11661)
- [doc/model-remediation/RECONCILIATION.md:11680](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11680)
- [doc/model-remediation/RECONCILIATION.md:11707](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11707)
- [doc/model-remediation/RECONCILIATION.md:11722](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11722)
- [doc/model-remediation/RECONCILIATION.md:11746](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11746)
- [doc/model-remediation/RECONCILIATION.md:11771](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11771)
- [doc/model-remediation/RECONCILIATION.md:11785](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11785)
- [doc/model-remediation/RECONCILIATION.md:11806](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11806)
- [doc/model-remediation/RECONCILIATION.md:11844](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11844)
- [doc/model-remediation/RECONCILIATION.md:11915](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11915)
- [doc/model-remediation/RECONCILIATION.md:11966](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11966)
- [doc/model-remediation/RECONCILIATION.md:11990](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L11990)
- [doc/model-remediation/RECONCILIATION.md:12015](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12015)
- [doc/model-remediation/RECONCILIATION.md:12040](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12040)
- [doc/model-remediation/RECONCILIATION.md:12146](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12146)
- [doc/model-remediation/RECONCILIATION.md:12318](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12318)
- [doc/model-remediation/RECONCILIATION.md:12433](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12433)
- [doc/model-remediation/RECONCILIATION.md:13657](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13657)
- [doc/modules/simulation.md:272](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/modules/simulation.md#L272)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6095](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6095)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:563](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L563)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:12896](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L12896)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13032](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13032)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13309](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13309)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13318](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13318)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13349](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13349)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13390](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13390)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13401](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13401)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13420](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13420)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13447](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13447)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13462](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13462)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13486](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13486)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13511](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13511)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13525](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13525)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13546](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13546)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13584](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13584)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13655](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13655)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13706](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13706)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13730](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13730)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13755](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13755)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13780](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13780)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:13886](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L13886)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14058](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14058)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14173](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14173)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15397](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15397)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:563 | HPR-P25-008C | PENDING | Simulation / Equipment | Code/Test | Integrate approved compressor/valve/regulator behavior and current curve/configuration input contracts; test constraints, flow changes and effects across connected branches. | feat(simulation): model gas network equipment behavior | HPR-P25-008B; HPR-P25-002 equipment inputs
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15397 | HPR-P25-008C | PARTIAL accepted scopes; REG Slice B verification PENDING | BLOCKED_OR_PENDING_ACCEPTANCE + SIMULATOR_DEFERRED | Simulation / Equipment | Original dependencies retained, selection suspended | Accepted compressor/valve and REG Slice A; committed B source | Unaccepted B verification suspended; C not authorized; remaining numerics | No new acceptance; later exact-source applicable CI and numerical evidence | NOT SELECTED | Explicit reactivation or separately selected existing-defect verification
doc/roadmap/ULTIMATE_ROADMAP.md:6095 | HPR-P25-008C | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13657 | HPR-P25-008C | PARTIAL accepted scopes; REG Slice B verification PENDING | BLOCKED_OR_PENDING_ACCEPTANCE + SIMULATOR_DEFERRED | Simulation / Equipment | Original dependencies retained, selection suspended | Accepted compressor/valve and REG Slice A; committed B source | Unaccepted B verification suspended; C not authorized; remaining numerics | No new acceptance; later exact-source applicable CI and numerical evidence | NOT SELECTED | Explicit reactivation or separately selected existing-defect verification
~~~~~~~~

<a id="hapi-518"></a>
## HAPI-518 — implement internal transient gas solver

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-008D

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-517

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement time-dependent gas-network behavior with initial state and boundary/action timelines; verify conservation, spatial/time refinement, stability and applicable event response within declared fidelity.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Implement time-dependent gas-network behavior with initial state and boundary/action timelines; verify conservation, spatial/time refinement, stability and applicable event response within declared fidelity..

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-517. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13658](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13658)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6096](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6096)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:564](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L564)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15398](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15398)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:564 | HPR-P25-008D | PENDING | Simulation / Transient | Code/Test | Implement time-dependent gas-network behavior with initial state and boundary/action timelines; verify conservation, spatial/time refinement, stability and applicable event response within declared fidelity. | feat(simulation): implement internal transient gas solver | HPR-P25-008C
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15398 | HPR-P25-008D | PENDING | SIMULATOR_DEFERRED | Simulation / Transient | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6096 | HPR-P25-008D | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13658 | HPR-P25-008D | PENDING | SIMULATOR_DEFERRED | Simulation / Transient | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-519"></a>
## HAPI-519 — verify internal gas engine numerical behavior

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-008E

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-516

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Independently verify both engine modes against reproducible analytical/manufactured/reference cases, numerical refinement and adverse/failure cases; publish supported range and errors, distinct from GZ2 calibration.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Independently verify both engine modes against reproducible analytical/manufactured/reference cases, numerical refinement and adverse/failure cases; publish supported range and errors, distinct from GZ2 calibration..

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-516. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13659](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13659)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6097](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6097)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:565](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L565)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15399](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15399)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:565 | HPR-P25-008E | PENDING | Simulation / Independent verification | Test/Doc | Independently verify both engine modes against reproducible analytical/manufactured/reference cases, numerical refinement and adverse/failure cases; publish supported range and errors, distinct from GZ2 calibration. | test(simulation): verify internal gas engine numerical behavior | HPR-P25-008B..D
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15399 | HPR-P25-008E | PENDING | SIMULATOR_DEFERRED | Simulation / Independent verification | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6097 | HPR-P25-008E | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13659 | HPR-P25-008E | PENDING | SIMULATOR_DEFERRED | Simulation / Independent verification | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-520"></a>
## HAPI-520 — integrate internal gas engine execution

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-008F

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-513, HAPI-519

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Bind versioned engine to run lifecycle and immutable input/result contracts; verify actual execution, cancellation/timeouts/resource budgets and reproducibility; keep numerical code isolated from controllers/JPA.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Bind versioned engine to run lifecycle and immutable input/result contracts; verify actual execution, cancellation/timeouts/resource budgets and reproducibility; keep numerical code isolated from controllers/JPA..

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-513, HAPI-519. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13660](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13660)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6098](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6098)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:566](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L566)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15400](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15400)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:566 | HPR-P25-008F | PENDING | Simulation / Execution adapter | Code/Test | Bind versioned engine to run lifecycle and immutable input/result contracts; verify actual execution, cancellation/timeouts/resource budgets and reproducibility; keep numerical code isolated from controllers/JPA. | feat(simulation): integrate internal gas engine execution | HPR-P25-008E; HPR-P25-007 connected scope
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15400 | HPR-P25-008F | PENDING | SIMULATOR_DEFERRED | Simulation / Execution adapter | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6098 | HPR-P25-008F | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13660 | HPR-P25-008F | PENDING | SIMULATOR_DEFERRED | Simulation / Execution adapter | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-521"></a>
## HAPI-521 — validate calibrated network physics

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-009

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-511, HAPI-514

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Calibrate and validate solver/estimator behavior against approved measured/reference cases and current equipment curves; publish per-product/mode tolerances, residuals, uncertainty and validity envelope.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Calibrate and validate solver/estimator behavior against approved measured/reference cases and current equipment curves; publish per-product/mode tolerances, residuals, uncertainty and validity envelope..

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-511, HAPI-514. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13661](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13661)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6100](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6100)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:860](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L860)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15401](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15401)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:860 | HPR-P25-009 | PENDING | Simulation / Analytics | Test/Doc | Calibrate and validate solver/estimator behavior against approved measured/reference cases and current equipment curves; publish per-product/mode tolerances, residuals, uncertainty and validity envelope. | test(simulation): validate calibrated network physics | HPR-P25-008; HPR-P25-005; approved validation data
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15401 | HPR-P25-009 | PENDING | SIMULATOR_DEFERRED | Simulation / Analytics | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6100 | HPR-P25-009 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13661 | HPR-P25-009 | PENDING | SIMULATOR_DEFERRED | Simulation / Analytics | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-522"></a>
## HAPI-522 — orchestrate issue triggered network runs

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-010

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-512

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Automatically trigger governed real simulations from eligible issues using traceable state snapshots, debounce/deduplication, priorities and bounded resource budgets; retain failures and preserve independent alarm handling.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Automatically trigger governed real simulations from eligible issues using traceable state snapshots, debounce/deduplication, priorities and bounded resource budgets; retain failures and preserve independent alarm handling..

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-512. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13662](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13662)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6101](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6101)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:861](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L861)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15402](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15402)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:861 | HPR-P25-010 | PENDING | Monitoring / Simulation / Workflow | Code/Test | Automatically trigger governed real simulations from eligible issues using traceable state snapshots, debounce/deduplication, priorities and bounded resource budgets; retain failures and preserve independent alarm handling. | feat(simulation): orchestrate issue triggered network runs | HPR-P25-006..009
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15402 | HPR-P25-010 | PENDING | SIMULATOR_DEFERRED | Monitoring / Simulation / Workflow | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6101 | HPR-P25-010 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13662 | HPR-P25-010 | PENDING | SIMULATOR_DEFERRED | Monitoring / Simulation / Workflow | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-523"></a>
## HAPI-523 — propose network validated operator decisions

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-011

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-521

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Evaluate candidate operator actions against baseline and affected-network constraints; present expected benefits, adverse effects elsewhere, uncertainty and alternatives; reject infeasible/out-of-validity recommendations.

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: Evaluate candidate operator actions against baseline and affected-network constraints; present expected benefits, adverse effects elsewhere, uncertainty and alternatives; reject infeasible/out-of-validity recommendations..

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-521. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13663](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13663)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6102](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6102)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:862](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L862)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15403](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15403)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:862 | HPR-P25-011 | PENDING | Simulation / Risk / Analytics | Code/Test | Evaluate candidate operator actions against baseline and affected-network constraints; present expected benefits, adverse effects elsewhere, uncertainty and alternatives; reject infeasible/out-of-validity recommendations. | feat(simulation): propose network validated operator decisions | HPR-P25-009..010; approved constraints and candidate action contract
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15403 | HPR-P25-011 | PENDING | SIMULATOR_DEFERRED | Simulation / Risk / Analytics | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6102 | HPR-P25-011 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13663 | HPR-P25-011 | PENDING | SIMULATOR_DEFERRED | Simulation / Risk / Analytics | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-535"></a>
## HAPI-535 — define reproducible engineering baselines

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-023

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define reproducible engineering baselines

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: define reproducible engineering baselines.

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-023 — Baseline manifests, scenario overlays and historical retrieval

Objective/justification: extend reusable sources and 002D capture into a complete
engineering baseline without another competing snapshot store. Evidence: T01–T04/T21,
C01–C04, S01–S08/S15 and X01/X04/X10/X14. Owners/contracts: exact Topology geometry/
station/network, Custody fluid, Simulation characteristics/overlay/models, Telemetry
operating source and Workflow governance. Functional/data: canonical ordered baseline
references/digests, effective/captured times, source origin/evidence, supported constraint
and profile revisions; immutable scenario overlay and execution freeze includes provider/
model/controls/boundary schedules and measurement watermark. API: draft validation,
freeze/read/clone/history, exact revision selection and execution-eligibility diagnostics.
Persistence: atomic capture/index references, retention/retrievability and corruption
checks; retain existing withdrawn-governance semantics while keeping historical facts
accessible through authorized read contracts. Acceptance: B/I byte-exact historical
reproduction after later changes; never substitute latest; reject dangling/digest-conflict/
wrong-effective-time captures, synthetic laundering and overlay changes to installed
assets; crash/partial commit and competing freeze replay cases. Numerical: matching frozen
inputs/model yields reproducible contract within declared platform tolerance, not universal
bit-identical floating results across providers. D/H/N; no existing source digests rewritten.

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

- Independent cases A–G; mixed parent not closed
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

- [doc/model-remediation/RECONCILIATION.md:12863](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12863)
- [doc/model-remediation/RECONCILIATION.md:12966](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12966)
- [doc/model-remediation/RECONCILIATION.md:13675](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13675)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6114](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6114)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14603](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14603)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14706](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14706)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15415](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15415)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14603 | HPR-P25-023 | Engineering baseline/scenario freeze preflight / Topology, Simulation | 019–022; existing 002C/002D contracts | docs(simulation): define reproducible engineering baselines | PLANNED — NOT SELECTED
doc/model-remediation/RECONCILIATION.md:12863 | HPR-P25-023 | Engineering baseline/scenario freeze preflight / Topology, Simulation | 019–022; existing 002C/002D contracts | docs(simulation): define reproducible engineering baselines | PLANNED — NOT SELECTED
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15415 | HPR-P25-023 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_SCOPE_REFINEMENT | Cross-module engineering governance | Independent successor/design 028; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 028 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6114 | HPR-P25-023 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13675 | HPR-P25-023 | Registered in prior amendment; PLANNED — NOT SELECTED | FOUNDATION_SCOPE_REFINEMENT | Cross-module engineering governance | Independent successor/design 028; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 028 separately selected scope separation
~~~~~~~~

<a id="hapi-536"></a>
## HAPI-536 — define model and provider extension contracts

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-024

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define model and provider extension contracts

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: define model and provider extension contracts.

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-024 — Configurable models and eligible internal/external providers

Objective/justification: separate supported data parameterization, reviewed model additions
and execution providers; S14 availability is not execution. Evidence: S09–S11/S14/S16,
X07/X08, external primary pages above and earlier three-engine research amendment.
Owner/contracts: Simulation-owned neutral immutable request/result and property/behavior
contracts; infrastructure contains optional vendor adapters. Functional/data: registry
identity/family/revision, units/schema/products/phases/regimes, required inputs/reference
conditions, validity, numerical strategy, verification errors, diagnostics/budgets/modes;
capabilities and deterministically pinned selections, not silent fallback. API: describe
eligible capabilities/configurations; no vendor graphs or operator executable scripts.
Persistence: model/profile metadata compatibility and exact engine/transform/artifact
references; do not persist mutable solver graphs as engineering truth. Acceptance:
unsupported product/equipment/mode/ref condition rejected before execution; provider
unavailable/license absent/timeout/crash/malformed result all explicit; parameter sweeps
change supported supplied values without Java branches. Numerical: independent analytical/
manufactured/reference matrix, dimensional normalization and NONCOMPARABLE classification;
freeze expected tolerances before running. D/H/N; external integration remains optional,
requires exact artifact/license/SBOM/isolation and separate closed implementation scope;
no change to accepted internal synthetic laws or compulsory external benchmark before 008D.

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
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

- [doc/model-remediation/RECONCILIATION.md:12864](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12864)
- [doc/model-remediation/RECONCILIATION.md:12986](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12986)
- [doc/model-remediation/RECONCILIATION.md:13676](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13676)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6115](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6115)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14604](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14604)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14726](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14726)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15416](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15416)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14604 | HPR-P25-024 | Model registry and provider-neutral extension preflight / Simulation | 021–023; existing 008A/B/C numerical boundaries | docs(simulation): define model and provider extension contracts | PLANNED — NOT SELECTED
doc/model-remediation/RECONCILIATION.md:12864 | HPR-P25-024 | Model registry and provider-neutral extension preflight / Simulation | 021–023; existing 008A/B/C numerical boundaries | docs(simulation): define model and provider extension contracts | PLANNED — NOT SELECTED
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15416 | HPR-P25-024 | Registered in prior amendment; PLANNED — NOT SELECTED | SIMULATOR_DEFERRED | Cross-module engineering governance | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6115 | HPR-P25-024 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13676 | HPR-P25-024 | Registered in prior amendment; PLANNED — NOT SELECTED | SIMULATOR_DEFERRED | Cross-module engineering governance | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-537"></a>
## HAPI-537 — define governed execution modes

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-025

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define governed execution modes

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: define governed execution modes.

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-025 — One validated request lifecycle for manual and proactive modes

Objective/justification: replace queue-only assumptions with specified actual execution,
while retaining 007/008F integration meanings. Evidence: S12–S16, S07/S08, Q01–Q04, X09/X12.
Owner/contracts: Simulation execution/scenario/results; Telemetry trusted trigger data;
Workflow/identity policy, notifications through established owners. Functional/data:
interactive, compare, batch/sweep, schedule and qualified event modes share frozen
assembly/preflight/result semantics; source freshness, active revision resolution,
trigger evidence, idempotency/dedup, quotas, concurrency, cancellation, bounded retries,
status/history and diagnostics. API: authorized start/status/cancel/result/compare and
policy-managed triggers, no OT endpoint. Persistence: run/attempt/trigger lineage,
atomic admission and crash/recovery/idempotency plan using existing run structures.
Acceptance: G same scenario/controls yields consistent manual/proactive input digests and
results within selected numerical contract; stale/late/missing trigger, duplicate event,
withdrawn qualification, canceled/orphaned run, exhaustion and provider outage handled.
Numerical: preserve convergence/feasibility/qualification distinctions and input immutability.
D/H/N; synthetic modes can be verified without live SCADA; operational proactive admission
requires F1/F2/F3, qualified source/state and selected provider evidence, never OT actuation.

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
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

- [doc/model-remediation/RECONCILIATION.md:12865](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12865)
- [doc/model-remediation/RECONCILIATION.md:13007](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13007)
- [doc/model-remediation/RECONCILIATION.md:13677](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13677)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6116](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6116)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14605](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14605)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14747](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14747)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15417](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15417)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14605 | HPR-P25-025 | Common manual/proactive execution preflight / Simulation, Telemetry | 023/024; existing 004/007/008F dependency design | docs(simulation): define governed execution modes | PLANNED — NOT SELECTED
doc/model-remediation/RECONCILIATION.md:12865 | HPR-P25-025 | Common manual/proactive execution preflight / Simulation, Telemetry | 023/024; existing 004/007/008F dependency design | docs(simulation): define governed execution modes | PLANNED — NOT SELECTED
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15417 | HPR-P25-025 | Registered in prior amendment; PLANNED — NOT SELECTED | SIMULATOR_DEFERRED | Cross-module engineering governance | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6116 | HPR-P25-025 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13677 | HPR-P25-025 | Registered in prior amendment; PLANNED — NOT SELECTED | SIMULATOR_DEFERRED | Cross-module engineering governance | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<a id="hapi-538"></a>
## HAPI-538 — define calibration readiness contracts

Phase: HPS-019 — Simulation and Digital Twin

Legacy IDs: HPR-P25-026

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `simulation` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define calibration readiness contracts

### 2. Existing Implementation

Model/scenario/run/recommendation orchestration and a bounded synthetic ideal-gas steady-state reference solver exist; tests cover their bounded contracts. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Retain deferred numerical, transient, calibrated equipment/regulator and external-engine targets; do not put these ahead of engineering/operations. The exact bounded remaining delivery is: define calibration readiness contracts.

### 4. Functional Requirements

Explicit owner reactivation, live preflight, applicable CI and original evidence

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `simulation`; primary phase owns this obligation. NeqSim, DWSIM and pandapipes remain targets. Synthetic solver output is not SITE qualification or a complete operational twin. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`, `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/controller/SpringSimulationController.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationEquipmentParameterApprovalEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationStoredPhysicalNetworkRevisionPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologyChangeProposalPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterQualificationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationEquipmentParameterRevisionQueryService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-026 — Calibration-ready correspondence without changing asset facts

Objective/justification: make future physically interpretable calibration possible from
retrievable qualified source data. Evidence: Q01–Q04, S07/S15, C01/S01 and existing
009/013/014 ownership. Owners/contracts: Telemetry measurement/binding/quality history;
Simulation predictions/model/run/parameter lineage; Analytics may consume datasets.
Functional/data: sensor/quantity/location and effective binding, unit/reference basis,
source timestamps/time alignment, engineering baseline, initial/boundary inputs,
observations/predictions/residuals/uncertainty, dataset revision and parameter provenance.
API: authorized reproducible dataset/prediction retrieval and eligibility diagnostics;
calibration-derived revisions separate from installed facts and certificates.
Persistence: immutable correspondence/dataset lineage and retention plan, no raw-reading
or certificate mutation. Acceptance: F retrieval after engineering/sensor changes;
reject ambiguous binding/time basis, stale/poor/unqualified readings and unit mismatch;
prevent overwriting original source/history. Future algorithm obligations: parameter
bounds, sensitivity/identifiability, objective, independent validation holdout, uncertainty/
residual diagnostics, overfitting checks and model-version comparisons with reproducible
method. Numerical accuracy/fit is not claimed by readiness or AI training. D/H/N;
actual deployment calibration data remains later owner-provided evidence, not a blocker
for synthetic readiness contract development.

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

- Explicit owner reactivation, live preflight, applicable CI and original evidence
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

- [doc/model-remediation/RECONCILIATION.md:12866](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L12866)
- [doc/model-remediation/RECONCILIATION.md:13026](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13026)
- [doc/model-remediation/RECONCILIATION.md:13678](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13678)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6117](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6117)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14606](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14606)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14766](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L14766)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15418](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15418)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:14606 | HPR-P25-026 | Calibration dataset/correspondence preflight / Telemetry, Simulation; Analytics consumer | 023/025; existing 009/013/014 obligations | docs(simulation): define calibration readiness contracts | PLANNED — NOT SELECTED
doc/model-remediation/RECONCILIATION.md:12866 | HPR-P25-026 | Calibration dataset/correspondence preflight / Telemetry, Simulation; Analytics consumer | 023/025; existing 009/013/014 obligations | docs(simulation): define calibration readiness contracts | PLANNED — NOT SELECTED
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15418 | HPR-P25-026 | Registered in prior amendment; PLANNED — NOT SELECTED | SIMULATOR_DEFERRED | Cross-module engineering governance | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
doc/roadmap/ULTIMATE_ROADMAP.md:6117 | HPR-P25-026 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13678 | HPR-P25-026 | Registered in prior amendment; PLANNED — NOT SELECTED | SIMULATOR_DEFERRED | Cross-module engineering governance | Original numerical/whole-cycle prerequisites retained | Existing implementation and original future requirements | Remaining original simulator-dependent scope (017: full-cycle closure) | Explicit owner reactivation, live preflight, applicable CI and original evidence | NOT SELECTED | None until reactivation
~~~~~~~~

<!-- task-register:payload:end -->
