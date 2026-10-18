# HPS-013 — Planning and Nominations: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-013 — Planning and Nominations

<a id="hapi-164"></a>
## HAPI-164 — scan planning domain models

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMC-019

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 5 planning model files.

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 28. HMC-019 — Planning module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `planning`  
**Baseline model files:** 5  
**Scanned models:** 5  
**Declared fields/components:** 95  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Planning model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| Nomination | record | 26 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java` | Extracted |
| OperationalPlan | record | 19 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java` | Extracted |
| PlanRevision | record | 15 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java` | Extracted |
| PlanTarget | record | 22 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java` | Extracted |
| PlanningPeriod | record | 13 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java` | Extracted |

### Planning fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| Nomination | 1 | id | String |
| Nomination | 2 | revisionId | String |
| Nomination | 3 | scenarioId | String |
| Nomination | 4 | code | String |
| Nomination | 5 | nominationTypeId | String |
| Nomination | 6 | productTypeId | String |
| Nomination | 7 | quantity | BigDecimal |
| Nomination | 8 | quantityUnitId | String |
| Nomination | 9 | rate | BigDecimal |
| Nomination | 10 | rateUnitId | String |
| Nomination | 11 | sourceAssetType | String |
| Nomination | 12 | sourceAssetId | String |
| Nomination | 13 | sourceAssetCode | String |
| Nomination | 14 | destinationAssetType | String |
| Nomination | 15 | destinationAssetId | String |
| Nomination | 16 | destinationAssetCode | String |
| Nomination | 17 | shipperPartyId | String |
| Nomination | 18 | shipperPartyCodeSnapshot | String |
| Nomination | 19 | counterpartyId | String |
| Nomination | 20 | contractReferenceId | String |
| Nomination | 21 | priority | Integer |
| Nomination | 22 | status | NominationStatus |
| Nomination | 23 | periodStart | Instant |
| Nomination | 24 | periodEnd | Instant |
| Nomination | 25 | createdAt | Instant |
| Nomination | 26 | updatedAt | Instant |
| OperationalPlan | 1 | id | String |
| OperationalPlan | 2 | periodId | String |
| OperationalPlan | 3 | code | String |
| OperationalPlan | 4 | nameAr | String |
| OperationalPlan | 5 | nameFr | String |
| OperationalPlan | 6 | nameEn | String |
| OperationalPlan | 7 | planTypeId | String |
| OperationalPlan | 8 | productTypeId | String |
| OperationalPlan | 9 | topologyScopeType | String |
| OperationalPlan | 10 | topologyScopeId | String |
| OperationalPlan | 11 | topologyScopeCode | String |
| OperationalPlan | 12 | topologyScopeNameSnapshot | String |
| OperationalPlan | 13 | responsibleOrganizationUnitId | String |
| OperationalPlan | 14 | status | OperationalPlanStatus |
| OperationalPlan | 15 | currentRevisionId | String |
| OperationalPlan | 16 | approvedRevisionId | String |
| OperationalPlan | 17 | createdByActorId | String |
| OperationalPlan | 18 | createdAt | Instant |
| OperationalPlan | 19 | updatedAt | Instant |
| PlanRevision | 1 | id | String |
| PlanRevision | 2 | planId | String |
| PlanRevision | 3 | revisionNumber | int |
| PlanRevision | 4 | revisionCode | String |
| PlanRevision | 5 | status | PlanRevisionStatus |
| PlanRevision | 6 | changeReasonCodeId | String |
| PlanRevision | 7 | changeReasonText | String |
| PlanRevision | 8 | baseRevisionId | String |
| PlanRevision | 9 | submittedByActorId | String |
| PlanRevision | 10 | submittedAt | Instant |
| PlanRevision | 11 | approvedByActorId | String |
| PlanRevision | 12 | approvedAt | Instant |
| PlanRevision | 13 | workflowInstanceId | String |
| PlanRevision | 14 | createdAt | Instant |
| PlanRevision | 15 | updatedAt | Instant |
| PlanTarget | 1 | id | String |
| PlanTarget | 2 | revisionId | String |
| PlanTarget | 3 | scenarioId | String |
| PlanTarget | 4 | nominationId | String |
| PlanTarget | 5 | targetTypeId | String |
| PlanTarget | 6 | topologyAssetType | String |
| PlanTarget | 7 | topologyAssetId | String |
| PlanTarget | 8 | topologyAssetCode | String |
| PlanTarget | 9 | topologyAssetNameSnapshot | String |
| PlanTarget | 10 | telemetryPointId | String |
| PlanTarget | 11 | telemetryPointCodeSnapshot | String |
| PlanTarget | 12 | targetValue | BigDecimal |
| PlanTarget | 13 | targetTextValue | String |
| PlanTarget | 14 | unitId | String |
| PlanTarget | 15 | toleranceLow | BigDecimal |
| PlanTarget | 16 | toleranceHigh | BigDecimal |
| PlanTarget | 17 | validFrom | Instant |
| PlanTarget | 18 | validTo | Instant |
| PlanTarget | 19 | priority | Integer |
| PlanTarget | 20 | status | PlanTargetStatus |
| PlanTarget | 21 | createdAt | Instant |
| PlanTarget | 22 | updatedAt | Instant |
| PlanningPeriod | 1 | id | String |
| PlanningPeriod | 2 | code | String |
| PlanningPeriod | 3 | nameAr | String |
| PlanningPeriod | 4 | nameFr | String |
| PlanningPeriod | 5 | nameEn | String |
| PlanningPeriod | 6 | periodTypeId | String |
| PlanningPeriod | 7 | periodStart | Instant |
| PlanningPeriod | 8 | periodEnd | Instant |
| PlanningPeriod | 9 | timeZone | String |
| PlanningPeriod | 10 | status | PlanningPeriodStatus |
| PlanningPeriod | 11 | createdByActorId | String |
| PlanningPeriod | 12 | createdAt | Instant |
| PlanningPeriod | 13 | updatedAt | Instant |

### HMC-019 reconciliation

```text
baseline model files = 5
extracted models     = 5
exceptions           = 0
declared fields      = 95
zero-field models    = 0
```

The scan records only declarations under `planning/domain/model`. Planning domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure model, monitoring implementation, party implementation, topology implementation, or database column metadata was mixed into this module scan.

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

- `14bf7ad241a28d597da2636c3a9728026bf04839` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:147](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L147)
- [docs/roadmap/model-field-catalogue.md:2228](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2228)
- [docs/roadmap/model-field-catalogue.md:2348](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2348)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:147 | HMC-019 | docs(catalogue): scan planning domain models | Scan all 5 planning model files. | **Completed** — 5/5 models, 95 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-180"></a>
## HAPI-180 — remediate semantic review PlanningPeriod

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMR-006

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review PlanningPeriod

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreatePlanningPeriodRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningPeriodResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/command/CreatePlanningPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/dto/PlanningPeriodSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningPeriodApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/service/PlanningPeriodValidator.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningPeriodRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningPeriodJpaRepository.java`
- `src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanningPeriodSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/service/PlanningPeriodValidator.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningPeriodApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningPeriodRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningPeriodJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreatePlanningPeriodRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningPeriodResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/command/CreatePlanningPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/dto/PlanningPeriodSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningPeriodApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/service/PlanningPeriodValidator.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningPeriodRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningPeriodJpaRepository.java`
- `src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanningPeriodSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-006 — planning.PlanningPeriod

- Source review: `HMSR-006`
- Exact commit: `fix(planning): remediate semantic review PlanningPeriod`
- Status: **Completed** — strict interval, required French label, deterministic valid IANA time zone, unique code, active PERIOD_TYPE validation, and database constraints are enforced. Canonical PERIOD_TYPE vocabulary is reconciled to DAY/WEEK/MONTH/CAMPAIGN/OPERATION_WINDOW. CLOSED-period revision governance is exposed by the domain and explicitly carried into the later OperationalPlan/PlanRevision application remediation where the revision write actually occurs.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 7
- Additive Flyway: `src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreatePlanningPeriodRequest.java`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningPeriodResponse.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/command/CreatePlanningPeriodCommand.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/dto/PlanningPeriodSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningPeriodApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/service/PlanningPeriodValidator.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningPeriodRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningPeriodJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanningPeriodSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PlanningPeriodSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Strict non-zero interval:** align the live invariant with the Planning DDD's `periodStart < periodEnd`; equality must not silently represent a valid planning horizon unless the target semantics are explicitly changed.
  2. **Unique period code:** the Planning DDD and required-constraints section explicitly require `unique(hidra_planning_period.code)`, while the live schema currently has only a normal index and the application/repository contract shows no uniqueness guard.
  3. **Canonical PERIOD_TYPE vocabulary:** resolve `DAILY/WEEKLY/MONTHLY` versus `DAY/WEEK/MONTH` before catalog/data provisioning. Do not map or seed by guesswork.
  4. **Catalog-family and active-entry validation:** the HRA-111 FK proves only that `periodTypeId` points to some planning catalog row. Creation must ensure it belongs to the intended `PERIOD_TYPE` family and satisfies the applicable active/reference policy.
  5. **Time-zone validity:** `timeZone` is a required IANA zone. The current service defaults only null values; blank input is normalized by the domain to null and arbitrary invalid zone strings can reach persistence because the column is plain varchar. Validation/defaulting semantics must be made deterministic.
  6. **Required French label boundary:** the Planning DDD and persistence contract require `nameFr`, while current domain creation can normalize blank/null to `null`. The eventual correction must ensure the required French label is enforced at an appropriate input/application/persistence boundary, without contradicting HRA's prohibition on blindly promoting all generic text to domain-constructor invariants.
  7. **Closed-period plan governance:** the Planning DDD states that closed periods cannot receive new plan revisions unless reopened by a workflow-approved action. This lifecycle rule must remain visible to the later `OperationalPlan`/`PlanRevision` reviews and be enforced at the owning application/workflow boundary rather than inferred by clients.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanningPeriodSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.

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

- `53bdb7133428ac6a36f8003cee3aa9698beec185` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Planning.md:1276](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Planning.md#L1276)
- [docs/roadmap/model-semantic-remediation.md:152](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L152)
- [docs/roadmap/model-semantic-remediation.md:582](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L582)
- [docs/roadmap/planning.md:198](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L198)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:152 | HMR-006 | HMSR-006 | planning | PlanningPeriod | — | — | fix(planning): remediate semantic review PlanningPeriod | **Completed**
~~~~~~~~

<a id="hapi-258"></a>
## HAPI-258 — remediate semantic review PlanRevision

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMR-064

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review PlanRevision

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261007_001__hmr_064_planning_plan_revision.sql`
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanningSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningCatalogEligibilityPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`, `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningCatalogEligibilityAdapter.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261007_001__hmr_064_planning_plan_revision.sql`
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanningSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-064 — planning.PlanRevision

- Source review: `HMSR-076`
- Exact commit: `fix(planning): remediate semantic review PlanRevision`
- Status: **Completed — CI pending**
- Batch 8 implementation: Positive per-plan revision numbers, nullable validated base lineage, active REVISION_REASON and approved metadata/persistence/database immutability enforced. Forward V20261007_001; four focused checks passed using temporary API/assertion stubs, not Maven/JUnit. Five PostgreSQL cases registered; local compile/focused Maven blocked by uncached Boot 4.1.1 parent. Full database validation pending CI.
- SCC: SCC-04
- Recorded upstream HMS dependencies: planning.OperationalPlan, planning.PlanRevision, workflow.WorkflowInstance
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261007_001__hmr_064_planning_plan_revision.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
  - `src/main/resources/db/migration/V20261007_001__hmr_064_planning_plan_revision.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java`
  - `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PlanRevisionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce revision-number semantics.** Require a positive revision number and add concurrency-safe PostgreSQL uniqueness for `(plan_id, revision_number)`.
  2. **Protect populated base-revision lineage.** Fail closed when `baseRevisionId` is populated, using a same-module FK or another explicitly justified same-module existence boundary; retain the existing no-self-reference rule.
  3. **Enforce the `REVISION_REASON` catalog family.** Validate populated `changeReasonCodeId` against an eligible/active Planning catalog entry from that exact family.
  4. **Enforce approved-revision immutability.** The metadata-update path must reject changes to an approved revision; supersession/post-approval change must proceed through creation of a new revision as defined by the Planning DDD.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanningSemanticPostgresTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `502de5aeee9219bcd0ee74c63bf2752d005d186e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:99](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L99)
- [doc/model-remediation/RECONCILIATION.md:13711](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13711)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2886](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2886)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15451](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15451)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:98](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L98)
- [docs/data definition/Planning.md:1310](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Planning.md#L1310)
- [docs/roadmap/model-semantic-remediation.md:230](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L230)
- [docs/roadmap/model-semantic-remediation.md:3552](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3552)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:230 | HMR-064 | HMSR-076 | planning | PlanRevision | SCC-04 | planning.OperationalPlan, planning.PlanRevision, workflow.WorkflowInstance | fix(planning): remediate semantic review PlanRevision | Completed — CI pending
doc/model-remediation/RECONCILIATION.md:99 | HMR-064 | HMSR-076 | planning.PlanRevision | COMPLETED — CI #582 GREEN | Positive per-plan revision numbers, nullable validated base lineage, active REVISION_REASON and approved metadata/persistence/database immutability enforced. Forward V20261007_001; four focused checks passed using temporary API/assertion stubs, not Maven/JUnit. Five PostgreSQL cases registered; local compile/focused Maven blocked by uncached Boot 4.1.1 parent. Full database validation confirmed by CI #604.
doc/model-remediation/RECONCILIATION.md:13711 | HMR-064 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:98 | HMR-064 | HMSR-076 | [planning.PlanRevision](../domain/SEMANTIC_DECISIONS.md#planning-planrevision) | [PlanRevisionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java) | [V20261007_001__hmr_064_planning_plan_revision](../../src/main/resources/db/migration/V20261007_001__hmr_064_planning_plan_revision.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15451 | HMR-064 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-259"></a>
## HAPI-259 — remediate semantic review OperationalPlan

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMR-065

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review OperationalPlan

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/planning/PlanningCreatorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/planning/PlanningResponsibleUnitContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/query/PlanningResponsibleUnitQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreateOperationalPlanRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/OperationalPlanResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/command/CreateOperationalPlanCommand.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/dto/OperationalPlanSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/OperationalPlanApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/OperationalPlanJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryService.java`
- `src/main/resources/db/migration/V20261007_002__hmr_065_planning_operational_plan.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/query/PlanningResponsibleUnitQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanningSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`, `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryService.java`, `src/main/java/dz/sh/hidra/modules/planning/application/service/OperationalPlanApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryService.java`, `src/test/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/query/PlanningResponsibleUnitQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/planning/PlanningCreatorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/planning/PlanningResponsibleUnitContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/query/PlanningResponsibleUnitQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreateOperationalPlanRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/OperationalPlanResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/command/CreateOperationalPlanCommand.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/dto/OperationalPlanSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/OperationalPlanApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/OperationalPlanJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryService.java`
- `src/main/resources/db/migration/V20261007_002__hmr_065_planning_operational_plan.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/query/PlanningResponsibleUnitQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanningSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-065 — planning.OperationalPlan

- Source review: `HMSR-077`
- Exact commit: `fix(planning): remediate semantic review OperationalPlan`
- Status: **Completed — CI pending**
- Batch 8 implementation: Required French name/scope type, unique plan code, active PLAN_TYPE, owner-controlled Topology/Identity/Organization references and same-plan nullable revision pointers enforced. Creation binds authenticated eligible actor and snapshots owner display values; unsupported REGION/NETWORK denied. Forward V20261007_002; twelve focused HMR-065/owner/catalog checks passed with temporary API stubs. Nine combined PostgreSQL cases registered; Maven compile/focused/full test/clean verify blocked before compilation by uncached Boot 4.1.1/Maven Central DNS. Full CI pending.
- SCC: SCC-04
- Recorded upstream HMS dependencies: organization.OrganizationUnit, planning.PlanRevision, planning.PlanningPeriod
- HMSR correction count: 6
- Additive Flyway: `src/main/resources/db/migration/V20261007_002__hmr_065_planning_operational_plan.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreateOperationalPlanRequest.java`
  - `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/OperationalPlanResponse.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/command/CreateOperationalPlanCommand.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/dto/OperationalPlanSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/application/service/OperationalPlanApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/OperationalPlanJpaRepository.java`
  - `src/main/resources/db/migration/V20261007_002__hmr_065_planning_operational_plan.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=OperationalPlanSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce DDD-required `nameFr` and `topologyScopeType` before persistence.**
  2. **Enforce global plan-code uniqueness in PostgreSQL** for `hidra_planning_operational_plan.code`.
  3. **Enforce `PLAN_TYPE` catalog-family eligibility** for mandatory `planTypeId`; the existing generic catalog FK is not sufficient.
  4. **Validate mandatory Topology operational-scope identity through a Topology-owned contract** while preserving scope snapshots and without a cross-module DB FK.
  5. **Validate cross-context ownership references through owner-controlled contracts:** mandatory creator actor and populated responsible Organization unit; do not introduce cross-module FKs.
  6. **Protect populated current/approved revision pointers and their parent-plan correlation.** Both IDs must reference existing Planning revisions belonging to the same OperationalPlan; use same-module persistence/application integrity without weakening the SCC boundary.

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
- `src/test/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/query/PlanningResponsibleUnitQueryAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanningSemanticPostgresTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `5b6e2c243afea9df3520df1910d1bac621f85f82` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:100](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L100)
- [doc/model-remediation/RECONCILIATION.md:13712](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13712)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2911](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2911)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15452](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15452)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:99](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L99)
- [docs/data definition/Planning.md:1319](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Planning.md#L1319)
- [docs/roadmap/model-semantic-remediation.md:231](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L231)
- [docs/roadmap/model-semantic-remediation.md:3592](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3592)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:231 | HMR-065 | HMSR-077 | planning | OperationalPlan | SCC-04 | organization.OrganizationUnit, planning.PlanRevision, planning.PlanningPeriod | fix(planning): remediate semantic review OperationalPlan | Completed — CI pending
doc/model-remediation/RECONCILIATION.md:100 | HMR-065 | HMSR-077 | planning.OperationalPlan | COMPLETED — CI #582 GREEN | Required French name/scope type, unique plan code, active PLAN_TYPE, owner-controlled Topology/Identity/Organization references and same-plan nullable revision pointers enforced. Creation binds authenticated eligible actor and snapshots owner display values; unsupported REGION/NETWORK denied. Forward V20261007_002; twelve focused HMR-065/owner/catalog checks passed with temporary API stubs. Nine combined PostgreSQL cases registered; Maven compile/focused/full test/clean verify blocked before compilation by uncached Boot 4.1.1/Maven Central DNS. Full CI #604 passed.
doc/model-remediation/RECONCILIATION.md:13712 | HMR-065 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:99 | HMR-065 | HMSR-077 | [planning.OperationalPlan](../domain/SEMANTIC_DECISIONS.md#planning-operationalplan) | [OperationalPlanSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java) | [V20261007_002__hmr_065_planning_operational_plan](../../src/main/resources/db/migration/V20261007_002__hmr_065_planning_operational_plan.sql); [PlanningCreatorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/planning/PlanningCreatorContract.java); [PlanningResponsibleUnitContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/planning/PlanningResponsibleUnitContract.java); [PlanningTopologyScopeContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15452 | HMR-065 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-274"></a>
## HAPI-274 — remediate semantic review Nomination

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMR-080

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review Nomination

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/planning/PlanningPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/PlanningPartyReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaNominationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/NominationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationScheduleLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationScheduleLineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/PlanningUnitReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryUnitJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/resources/db/migration/V20261008_025__hmr_080_nomination_owner_reference_policies.sql`
- `src/main/resources/db/migration/V20261008_026__hmr_080_planning_nomination_integrity.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/InternalReferenceIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/party/infrastructure/integration/PlanningPartyReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/NominationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/PlanningUnitReferenceQueryAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyCatalogEntryJpaRepository.java`, `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/PlanningPartyReferenceQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/planning/PlanningPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/PlanningPartyReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaNominationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/NominationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationScheduleLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationScheduleLineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/PlanningUnitReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryUnitJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/resources/db/migration/V20261008_025__hmr_080_nomination_owner_reference_policies.sql`
- `src/main/resources/db/migration/V20261008_026__hmr_080_planning_nomination_integrity.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/InternalReferenceIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/party/infrastructure/integration/PlanningPartyReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/NominationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/PlanningUnitReferenceQueryAdapterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-080 — planning.Nomination

- Source review: `HMSR-094`
- Exact commit: `fix(planning): remediate semantic review Nomination`
- Status: **Blocked — owner lookup contract prerequisite unresolved**
- SCC: —
- Recorded upstream HMS dependencies: party.Party, planning.PlanRevision
- HMSR correction count: 7
- Additive Flyway: `src/main/resources/db/migration/V20261004_080__hmr_080_planning_nomination.sql`
- Owner-contract prerequisite: No suitable exported owner lookup found in live tree for party; task must introduce/authorize an owner contract by roadmap amendment before cross-module validation changes.
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaNominationRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationScheduleLineJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationScheduleLineJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_080__hmr_080_planning_nomination.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NominationSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce `quantity > 0`**, not merely non-null quantity.
  2. **Enforce strict `periodStart < periodEnd`**, rejecting zero-duration nominations.
  3. **Enforce required `createdAt` and `updatedAt` before persistence.**
  4. **Enforce unique `(revisionId, code)` nomination identity** using application/database integrity consistent with the DDD.
  5. **Validate `nominationTypeId` against the active/eligible exact `NOMINATION_TYPE` family**, not merely generic Planning catalog existence.
  6. **Reconcile product/unit reference ownership** for `productTypeId`, `quantityUnitId`, and `rateUnitId`; the DDD does not establish corresponding Planning catalog families, so the existing generic Planning-catalog FK shape is not by itself sufficient semantic proof.
  7. **Validate supplied Topology and Party references through owner-controlled contracts**, preserving scalar/snapshot fields and avoiding cross-module database FKs; separately reconcile the optional `scenarioId` graph edge during HMS-006 before production changes.

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
- `src/test/java/dz/sh/hidra/InternalReferenceIntegrityMigrationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/party/infrastructure/integration/PlanningPartyReferenceQueryAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/NominationSemanticPostgresIntegrationTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java` — exact-head oracle suite 11 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/PlanningUnitReferenceQueryAdapterTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `617c2eec812e3a5734957ee9fa0360f6f5613032` — exact registered-message commit; surviving paths matched to current tree.
- `41904baff2becbdd03e19d3b2da005a28ad8d8a0` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:71](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L71)
- [doc/model-remediation/RECONCILIATION.md:115](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L115)
- [doc/model-remediation/RECONCILIATION.md:3550](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L3550)
- [doc/model-remediation/RECONCILIATION.md:3800](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L3800)
- [doc/model-remediation/RECONCILIATION.md:4011](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4011)
- [doc/model-remediation/RECONCILIATION.md:4056](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4056)
- [doc/model-remediation/RECONCILIATION.md:4111](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4111)
- [doc/model-remediation/RECONCILIATION.md:4732](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4732)
- [doc/model-remediation/RECONCILIATION.md:13727](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13727)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:5657](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L5657)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:5907](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L5907)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6118](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6118)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6163](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6163)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6218](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6218)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6839](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6839)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15467](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15467)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:114](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L114)
- [docs/data definition/Hse.md:257](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Hse.md#L257)
- [docs/roadmap/model-semantic-remediation.md:251](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L251)
- [docs/roadmap/model-semantic-remediation.md:4255](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4255)
- [docs/roadmap/model-semantic-remediation.md:5650](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L5650)
- [docs/roadmap/model-semantic-remediation.md:5712](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L5712)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:251 | HMR-080 | HMSR-094 | planning | Nomination | — | party.Party, planning.PlanRevision | fix(planning): remediate semantic review Nomination | Blocked — owner lookup contract prerequisite unresolved
docs/roadmap/model-semantic-remediation.md:5650 | HMR-080 | Blocked | Independent unresolved prerequisite
docs/roadmap/model-semantic-remediation.md:5712 | HMR-080 | Blocked | Independent unresolved prerequisite
doc/model-remediation/RECONCILIATION.md:115 | HMR-080 | HMSR-094 | planning.Nomination | COMPLETED — CI #604 PASSED | NOM-OWNER-01/NOM-EXEC-01 accepted; implementation 41904baff2becbdd03e19d3b2da005a28ad8d8a0 and fixture repair 617c2eec812e3a5734957ee9fa0360f6f5613032; full Java 21 verification, PostgreSQL/Spring-JPA tests and OpenAPI compatibility passed; forward 025/026.
doc/model-remediation/RECONCILIATION.md:3550 | HMR-080 | Blocked | Independent unresolved prerequisite
doc/model-remediation/RECONCILIATION.md:3800 | HMR-080 | Blocked | Independent unresolved prerequisite
doc/model-remediation/RECONCILIATION.md:4011 | HMR-080 | BLOCKED | Independent unresolved prerequisite remains.
doc/model-remediation/RECONCILIATION.md:4056 | HMR-080 | BLOCKED | Existing unresolved owner contract is unchanged.
doc/model-remediation/RECONCILIATION.md:4111 | HMR-080 | BLOCKED | Independent unresolved owner contract remains.
doc/model-remediation/RECONCILIATION.md:4732 | HMR-080 | BLOCKED | Unresolved Party-to-Planning owner contract remains separate.
doc/model-remediation/RECONCILIATION.md:13727 | HMR-080 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:114 | HMR-080 | HMSR-094 | [planning.Nomination](../domain/SEMANTIC_DECISIONS.md#planning-nomination) | [NominationSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java) | [V20261008_025__hmr_080_nomination_owner_reference_policies](../../src/main/resources/db/migration/V20261008_025__hmr_080_nomination_owner_reference_policies.sql); [V20261008_026__hmr_080_planning_nomination_integrity](../../src/main/resources/db/migration/V20261008_026__hmr_080_planning_nomination_integrity.sql); [PlanningProductReferenceContract](../../src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java); [PlanningPartyReferenceContract](../../src/main/java/dz/sh/hidra/modules/party/application/contract/planning/PlanningPartyReferenceContract.java); [PlanningUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningUnitReferenceContract.java); [PlanningTargetTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:5657 | HMR-080 | Blocked | Independent unresolved prerequisite
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:5907 | HMR-080 | Blocked | Independent unresolved prerequisite
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6118 | HMR-080 | BLOCKED | Independent unresolved prerequisite remains.
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6163 | HMR-080 | BLOCKED | Existing unresolved owner contract is unchanged.
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6218 | HMR-080 | BLOCKED | Independent unresolved owner contract remains.
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6839 | HMR-080 | BLOCKED | Unresolved Party-to-Planning owner contract remains separate.
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15467 | HMR-080 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
docs/data definition/Hse.md:257 | HMR-080 | Blocked | Independent unresolved prerequisite
~~~~~~~~

<a id="hapi-288"></a>
## HAPI-288 — remediate semantic review PlanTarget

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMR-094

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review PlanTarget

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/package-info.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/MonitoringPlanTargetReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/configuration/PlanningTargetValuePolicy.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/PlanTargetReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanScenarioJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/PlanningTargetTopologyReferenceQueryAdapter.java`
- `src/main/resources/db/migration/V20261008_019__hmr_094_planning_target_value_policy.sql`
- `src/main/resources/db/migration/V20261008_020__hmr_094_planning_plan_target_integrity.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanTargetSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/MonitoringPlanTargetReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/integration/PlanningTargetTopologyReferenceContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/planning/application/service/MonitoringPlanTargetReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/configuration/PlanningTargetValuePolicy.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/PlanTargetReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/package-info.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/MonitoringPlanTargetReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/configuration/PlanningTargetValuePolicy.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/PlanTargetReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanScenarioJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/PlanningTargetTopologyReferenceQueryAdapter.java`
- `src/main/resources/db/migration/V20261008_019__hmr_094_planning_target_value_policy.sql`
- `src/main/resources/db/migration/V20261008_020__hmr_094_planning_plan_target_integrity.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanTargetSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/MonitoringPlanTargetReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/integration/PlanningTargetTopologyReferenceContractTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-094 — planning.PlanTarget

- Source review: `HMSR-111`
- Exact commit: `fix(planning): remediate semantic review PlanTarget`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: planning.Nomination, planning.PlanRevision, telemetry.TelemetryPoint
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261008_019__hmr_094_planning_target_value_policy.sql` and `src/main/resources/db/migration/V20261008_020__hmr_094_planning_plan_target_integrity.sql` (accepted canonical Batch 19 envelope; original unexecuted registration superseded)
- Owner-contract prerequisite: Existing candidate owner contract(s): telemetry:src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java
- Exact write allowlist:
  - `docs/data definition/Planning.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/planning.md`
  - `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
  - `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_094__hmr_094_planning_plan_target.sql`
  - `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PlanTargetSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce target-type-dependent value shape:** numeric target types require `targetValue` and `unitId`; state/text target types must carry the representation required by Planning target-type policy.
  2. **Validate `targetTypeId` as a member of the Planning target-type catalog family, not merely any existing Planning catalog row.**
  3. **Fail closed on missing `topologyAssetType` before persistence; validate supplied topology references through Topology-owned public contracts when required.**
  4. **For supplied optional `nominationId` / `scenarioId`, validate same-module existence and revision compatibility instead of allowing cross-revision references.**
  5. **For supplied `telemetryPointId`, preserve the cross-module scalar boundary and validate through Telemetry-owned contracts when the use case requires a live point.**

- Batch 19 execution: PTMD-PREREQ-01 accepted in canonical execution memory. The canonical exhaustive scope supersedes this historical allowlist. Locked parents, exact TARGET_TYPE/value policy, canonical fresh owner snapshots, unchanged historical provenance and narrow Monitoring target export implemented. Seven focused, three owner-provider and ten PostgreSQL/Spring-JPA methods prepared. No local Java/Maven/PostgreSQL execution facility is exposed; all required runtime checks await CI.

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
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanTargetSemanticPostgresIntegrationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/MonitoringPlanTargetReferenceContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/integration/PlanningTargetTopologyReferenceContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `0cc3e5c6c4b675880a634b3a073905f4119a36b7` — exact registered-message commit; surviving paths matched to current tree.
- `3845b7d30637ec9fc7306a527a0ae569b501fa89` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:129](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L129)
- [doc/model-remediation/RECONCILIATION.md:3934](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L3934)
- [doc/model-remediation/RECONCILIATION.md:13741](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13741)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6041](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6041)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15481](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15481)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:128](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L128)
- [docs/data definition/Planning.md:1334](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Planning.md#L1334)
- [docs/roadmap/model-semantic-remediation.md:270](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L270)
- [docs/roadmap/model-semantic-remediation.md:4743](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4743)
- [docs/roadmap/planning.md:212](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L212)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:270 | HMR-094 | HMSR-111 | planning | PlanTarget | — | planning.Nomination, planning.PlanRevision, telemetry.TelemetryPoint | fix(planning): remediate semantic review PlanTarget | Planned
doc/model-remediation/RECONCILIATION.md:3934 | HMR-094 | V20261008_019__hmr_094_planning_target_value_policy.sql; V20261008_020__hmr_094_planning_plan_target_integrity.sql | PlanTargetSemanticRemediationTest | fix(planning): remediate semantic review PlanTarget
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6041 | HMR-094 | V20261008_019__hmr_094_planning_target_value_policy.sql; V20261008_020__hmr_094_planning_plan_target_integrity.sql | PlanTargetSemanticRemediationTest | fix(planning): remediate semantic review PlanTarget
doc/model-remediation/RECONCILIATION.md:129 | HMR-094 | HMSR-111 | planning.PlanTarget | COMPLETED — CI #600 PASSED | Independent semantic commit 3845b7d30637ec9fc7306a527a0ae569b501fa89 plus narrow repair 0cc3e5c6c4b675880a634b3a073905f4119a36b7; Java 21 clean verify and OpenAPI green on repaired Batch 19 final tree.
doc/model-remediation/RECONCILIATION.md:13741 | HMR-094 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:128 | HMR-094 | HMSR-111 | [planning.PlanTarget](../domain/SEMANTIC_DECISIONS.md#planning-plantarget) | [PlanTargetSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java) | [V20261008_019__hmr_094_planning_target_value_policy](../../src/main/resources/db/migration/V20261008_019__hmr_094_planning_target_value_policy.sql); [V20261008_020__hmr_094_planning_plan_target_integrity](../../src/main/resources/db/migration/V20261008_020__hmr_094_planning_plan_target_integrity.sql); [MonitoringPlanTargetReferenceContract](../../src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java); [PlanningTelemetryPointReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java); [PlanningTargetTopologyReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15481 | HMR-094 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-316"></a>
## HAPI-316 — review planning PlanningPeriod

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMSR-006

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review planning PlanningPeriod

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 19. HMSR-006 — planning.PlanningPeriod review

**Decision:** REVISE  
**Review code:** HMSR-006  
**Dependency level:** 0  
**Bounded context:** planning  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `planning.OperationalPlan` through `periodId`  
**Transitive dependents:** 9  
**Unresolved/non-subject references:** 0

### 19.1 Semantic role and ordering rationale

`PlanningPeriod` is the Planning-owned planning-horizon reference for daily, weekly, monthly, campaign and operational-window planning. It establishes the temporal container in which operational plans are created and reviewed.

It is Level 0 because it has no upstream dependency on another HMS subject model. `OperationalPlan.periodId` depends directly on it, so period semantics must be settled before plan/revision/nomination/target review proceeds.

Planning owns expected operational state only. Telemetry owns actual readings, Monitoring owns deviation semantics, Workflow owns approval process state, Topology owns physical assets, Organization owns internal responsibility structures, and Identity/platform owns actor identity.

### 19.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable PlanningPeriod identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable business code for the planning horizon; current Planning DDD requires uniqueness. |
| `nameAr` | `String` | Optional | Arabic period display label. |
| `nameFr` | `String` | Mandatory in target/persistence semantics | French period display label. The Planning DDD and JPA/schema mark it required. |
| `nameEn` | `String` | Optional | English period display label. |
| `periodTypeId` | `String` | Mandatory | Planning-owned catalog reference for the planning-horizon type; semantically belongs to `PERIOD_TYPE`. |
| `periodStart` | `Instant` | Mandatory | Inclusive start instant of the horizon. |
| `periodEnd` | `Instant` | Mandatory | Exclusive end instant of the horizon. |
| `timeZone` | `String` | Mandatory | IANA planning time-zone identifier. Current default is `Africa/Algiers`. |
| `status` | `PlanningPeriodStatus` | Mandatory | Technical lifecycle: `OPEN`, `LOCKED`, `CLOSED`, or `CANCELLED`. |
| `createdByActorId` | `String` | Mandatory reference | Creator actor identity. This is an Identity/platform actor reference, not an HMS subject-model edge. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp. |

The current domain constructor already rejects blank `id`, `code`, `periodTypeId`, and `createdByActorId`, rejects null start/end/status values, trims strings, and defaults the creation service to `PlanningPeriodStatus.OPEN`.

### 19.3 Dependency and catalog evidence

HMS-003 correctly classified:

- `periodTypeId` as a value/catalog dependency rather than a subject-model graph edge;
- `createdByActorId` as an Identity actor reference, with no fake `identity.User` subject edge.

Stronger persistence evidence confirms `periodTypeId` is Planning-owned:

- `PlanningPeriodJpaEntity.periodTypeId` maps to `hidra_planning_period.period_type_id`;
- HRA-111 installs `fk_hra111_planning_017`;
- that FK targets `hidra_planning_catalog_entry(id)` with `ON DELETE RESTRICT`;
- `PlanningCatalogEntry` is retained as a Planning read/persistence model outside the 123 HMS subject set.

Therefore `PlanningPeriod` has no upstream HMS subject dependency.

### 19.4 Period-type vocabulary conflict

The live Planning DDD contains a semantic inconsistency that must not be guessed away during provisioning:

- the `PlanningPeriod.periodTypeId` field description lists `DAILY`, `WEEKLY`, `MONTHLY`, `CAMPAIGN`, `OPERATION_WINDOW`;
- the same document's canonical `PERIOD_TYPE` catalog table lists `DAY`, `WEEK`, `MONTH`, `CAMPAIGN`, `OPERATION_WINDOW`.

No current migration/seed evidence found during HMSR-006 resolves which code family is authoritative. The eventual correction must select one canonical code vocabulary and align DDD, catalog provisioning, API behavior and existing data consistently. HMSR-006 does not invent the answer.

### 19.5 Temporal semantics

The target Planning DDD is explicit:

```text
periodStart < periodEnd
```

and describes `periodEnd` as exclusive.

The current domain/HRA-051 invariant is weaker:

```text
periodStart <= periodEnd
```

because it rejects only `periodEnd.isBefore(periodStart)`. A zero-length period with equal start/end is therefore currently accepted by the domain even though it violates the target PlanningPeriod contract.

This is a real target-model semantic contradiction, not merely a persistence detail.

### 19.6 Persistence and application consistency

The live domain and JPA models expose the same 13 components.

The base Planning migration makes `id`, `code`, `name_fr`, `period_type_id`, `period_start`, `period_end`, `time_zone`, `status`, `created_by_actor_id`, `created_at`, and `updated_at` non-null.

The application service:

- creates periods as `OPEN`;
- defaults `timeZone` to `Africa/Algiers` only when the command value is null;
- persists directly through `PlanningPeriodRepositoryPort.save`;
- performs no demonstrated code-uniqueness check;
- performs no demonstrated `PERIOD_TYPE` catalog-family/active-entry validation;
- performs no demonstrated IANA time-zone validation.

The repository port/Spring Data repository expose ID-based save/find operations only; no `findByCode`/`existsByCode` capability is currently present.

### 19.7 Required revisions

The model's role and dependency direction are correct, but the target baseline cannot be APPROVED until these evidence-backed inconsistencies are resolved:

1. **Strict non-zero interval:** align the live invariant with the Planning DDD's `periodStart < periodEnd`; equality must not silently represent a valid planning horizon unless the target semantics are explicitly changed.
2. **Unique period code:** the Planning DDD and required-constraints section explicitly require `unique(hidra_planning_period.code)`, while the live schema currently has only a normal index and the application/repository contract shows no uniqueness guard.
3. **Canonical PERIOD_TYPE vocabulary:** resolve `DAILY/WEEKLY/MONTHLY` versus `DAY/WEEK/MONTH` before catalog/data provisioning. Do not map or seed by guesswork.
4. **Catalog-family and active-entry validation:** the HRA-111 FK proves only that `periodTypeId` points to some planning catalog row. Creation must ensure it belongs to the intended `PERIOD_TYPE` family and satisfies the applicable active/reference policy.
5. **Time-zone validity:** `timeZone` is a required IANA zone. The current service defaults only null values; blank input is normalized by the domain to null and arbitrary invalid zone strings can reach persistence because the column is plain varchar. Validation/defaulting semantics must be made deterministic.
6. **Required French label boundary:** the Planning DDD and persistence contract require `nameFr`, while current domain creation can normalize blank/null to `null`. The eventual correction must ensure the required French label is enforced at an appropriate input/application/persistence boundary, without contradicting HRA's prohibition on blindly promoting all generic text to domain-constructor invariants.
7. **Closed-period plan governance:** the Planning DDD states that closed periods cannot receive new plan revisions unless reopened by a workflow-approved action. This lifecycle rule must remain visible to the later `OperationalPlan`/`PlanRevision` reviews and be enforced at the owning application/workflow boundary rather than inferred by clients.

HMSR-006 does not change production Java, JPA, Flyway, API/application contracts, tests, or database data.

### 19.8 Operational interpretation

For SONATRACH/TRC operations, a PlanningPeriod is the governed temporal frame for a transport program — for example a daily dispatch horizon, weekly program, monthly transport program, campaign, or special operational window.

The stored time zone is operationally significant because schedule interpretation, cut-off times, nominations, planned targets and approvals must resolve the same local planning horizon while timestamps remain stored as instants. The default `Africa/Algiers` is consistent with the current application intent, but the field still requires valid and explicit semantics for non-default cases.

A PlanningPeriod does not itself own telemetry measurements, topology state, workflow tasks or monitoring deviations.

### 19.9 Review conclusion

**REVISE.** `PlanningPeriod` is the correct foundational Planning model and its subject dependency direction is sound, but the target semantic baseline has unresolved enforcement and definition conflicts around interval strictness, code uniqueness, period-type vocabulary, catalog validation, time-zone validity, required French labeling and closed-period governance.

HMS reconciliation must retain these obligations until an explicitly authorized Planning implementation/documentation task resolves them or the target semantics are explicitly changed.

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

- `0a6befa617d4343d3c0336d410455b9d7c13409a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:58](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L58)
- [docs/roadmap/model-semantic-review.md:201](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L201)
- [docs/roadmap/model-semantic-review.md:3658](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L3658)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:201 | HMSR-006 | 0 | planning | PlanningPeriod | — | — | 1 | 9 | 0 | REVISE | Completed | docs(model-review): review planning PlanningPeriod
doc/domain/SEMANTIC_DECISIONS.md:58 | HMSR-006 | [planning.PlanningPeriod](#planning-planningperiod) | REVISE | HMR-006
~~~~~~~~

<a id="hapi-386"></a>
## HAPI-386 — review planning PlanRevision

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMSR-076

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review planning PlanRevision

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 89. HMSR-076 — planning.PlanRevision review

**Decision:** REVISE  
**Review code:** HMSR-076  
**Dependency level:** 4  
**Bounded context:** planning  
**SCC:** SCC-04 with `planning.OperationalPlan` and `planning.PlanRevision`  
**Confirmed upstream subject dependencies:** `planning.OperationalPlan`, optional prior `planning.PlanRevision`, optional `workflow.WorkflowInstance`  
**Confirmed direct HMS subject dependents:** 4  
**Transitive HMS subject dependents:** 8

### 89.1 Semantic role

`PlanRevision` is the Planning-owned immutable/versioned baseline candidate for an `OperationalPlan`. It carries revision identity/number/code, Planning lifecycle state, an optional catalog-backed revision reason, optional lineage to a base revision, submission/approval actor snapshots, and an optional Workflow-owned approval-process reference.

Planning owns the revision lifecycle state. Workflow owns process/task/transition state. Identity owns actor identities. Cross-module references remain scalar/application-contract references and must not become cross-module database foreign keys.

### 89.2 Parent-plan integrity

`planId` is mandatory and same-module. HRA-111 already protects it with a Planning-local foreign key from `hidra_planning_plan_revision.plan_id` to `hidra_planning_operational_plan.id`.

The application update path also loads the referenced `OperationalPlan` and fails closed if it does not exist. No further parent-plan integrity correction is required by this review.

### 89.3 Revision number semantics

The Planning DDD explicitly defines `revisionNumber` as monotonic per plan, starting at 1, and requires:

```text
unique(hidra_planning_plan_revision.plan_id, revision_number)
```

The live Flyway table defines only a non-unique index on `plan_id`; no unique constraint/index for `(plan_id, revision_number)` is present. The domain record also accepts zero or negative revision numbers.

The authoritative Planning baseline therefore requires a positive revision number and concurrency-safe database uniqueness per plan. Application/domain checks may improve errors but must not replace the database uniqueness guarantee.

### 89.4 Base-revision lineage

`baseRevisionId` is an optional same-module lineage reference. The domain already rejects direct self-reference, which is appropriate.

However, repository evidence shows no database FK for this optional same-module reference and no application lookup proving that a populated base revision exists. HRA-111 intentionally covered mandatory references only, so its absence there is not evidence that optional lineage may remain unchecked.

When populated, `baseRevisionId` should fail closed as a Planning-owned revision reference. A same-module FK is architecturally acceptable here; alternatively an application existence check must be evidence-backed if a database constraint is deliberately rejected.

This review does not invent a stronger rule such as requiring the base revision to belong to the same plan or to have a lower revision number because current repository evidence does not explicitly establish those constraints.

### 89.5 Revision-reason catalog family

The Planning DDD defines `changeReasonCodeId` as an optional `CATALOG_REF`, and the Planning catalog defines the dedicated family `REVISION_REASON`.

The live update path copies `changeReasonCodeId` directly into the revision and repository search found no Planning catalog-family eligibility validation for this field.

When populated, the value must resolve to an eligible/active `PlanningCatalogEntry` in the `REVISION_REASON` family; generic catalog-row existence alone is insufficient.

### 89.6 Approval workflow and actor boundaries

`workflowInstanceId` is an optional Workflow-owned reference. `submittedByActorId` and `approvedByActorId` are Identity-owned actor references/snapshots.

The live approval service correctly uses the exported `PlanningWorkflowContract`, verifies that the workflow instance targets the Planning revision, and maps workflow decisions into Planning-owned lifecycle state. No cross-module Workflow database FK should be introduced.

The actor IDs remain cross-context references. HMSR-076 does not require a cross-module FK; owner-controlled validation should be used where creation/submission/approval write paths accept actor identity.

### 89.7 Approved-revision immutability

The Planning DDD explicitly states:

```text
approved revisions are immutable
superseding an approved revision requires a new revision
```

The live `PlanRevisionUpdateApplicationService` only checks that the revision is the plan's current revision and that `expectedUpdatedAt` matches. It does not reject `APPROVED` (or other non-editable lifecycle states) before changing reason metadata and persisting a new `updatedAt`.

Therefore an approved revision can still pass the reviewed metadata-update path when it remains current. The authoritative write boundary must enforce the DDD's immutability rule and require a new revision for post-approval changes.

HMSR-076 does not invent the full editable-state matrix beyond the explicit approved-immutability rule.

### 89.8 Lifecycle enum

`PlanRevisionStatus` contains:

```text
DRAFT
SUBMITTED
APPROVED
REJECTED
SUPERSEDED
WITHDRAWN
```

These are stable Planning lifecycle states explicitly documented by the Planning DDD. They are appropriate as a technical/domain enum and should not be converted into a business taxonomy catalog merely because other Planning classifications are catalog-backed.

### 89.9 Required revisions

Four evidence-backed obligations remain:

1. **Enforce revision-number semantics.** Require a positive revision number and add concurrency-safe PostgreSQL uniqueness for `(plan_id, revision_number)`.

2. **Protect populated base-revision lineage.** Fail closed when `baseRevisionId` is populated, using a same-module FK or another explicitly justified same-module existence boundary; retain the existing no-self-reference rule.

3. **Enforce the `REVISION_REASON` catalog family.** Validate populated `changeReasonCodeId` against an eligible/active Planning catalog entry from that exact family.

4. **Enforce approved-revision immutability.** The metadata-update path must reject changes to an approved revision; supersession/post-approval change must proceed through creation of a new revision as defined by the Planning DDD.

HMSR-076 does not modify production Java, JPA, Flyway, API/application contracts, tests, Planning catalog data, Workflow, Identity, or provisioned data.

### 89.10 Review conclusion

**REVISE.** `PlanRevision` is a coherent Planning-owned version model with correct bounded-context ownership, an appropriate lifecycle enum, a protected mandatory parent-plan reference, and a properly exported Workflow orchestration boundary.

The target baseline cannot mark it APPROVED while per-plan revision-number uniqueness/positivity is unenforced, optional same-module base lineage is not fail-closed, the revision-reason catalog family is unchecked, and the live metadata-update path can mutate an approved revision contrary to the Planning DDD.

HMS reconciliation must retain these obligations until explicitly authorized Planning production corrections resolve them or stronger repository evidence changes the governing Planning DDD.

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

- `747a0f29bf40f6a8ac66ccb9c2857dd94f65bda5` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:128](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L128)
- [docs/roadmap/model-semantic-review.md:271](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L271)
- [docs/roadmap/model-semantic-review.md:14455](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L14455)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:271 | HMSR-076 | 4 | planning | PlanRevision | SCC-04 | planning.OperationalPlan, planning.PlanRevision, workflow.WorkflowInstance | 4 | 8 | 2 | REVISE | Completed | docs(model-review): review planning PlanRevision
doc/domain/SEMANTIC_DECISIONS.md:128 | HMSR-076 | [planning.PlanRevision](#planning-planrevision) | REVISE | HMR-064
~~~~~~~~

<a id="hapi-387"></a>
## HAPI-387 — review planning OperationalPlan

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMSR-077

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review planning OperationalPlan

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 90. HMSR-077 — planning.OperationalPlan review

**Decision:** REVISE  
**Review code:** HMSR-077  
**Dependency level:** 4  
**Bounded context:** planning  
**SCC:** SCC-04 with `planning.PlanRevision` and `planning.OperationalPlan`  
**Confirmed upstream subject dependencies:** optional `organization.OrganizationUnit`, optional `planning.PlanRevision` pointers, mandatory `planning.PlanningPeriod`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 8

### 90.1 Semantic role

`OperationalPlan` is the Planning-owned plan/program header for a planning period and operational scope. It owns plan identity/code, multilingual display names, Planning plan-type and lifecycle state, scope snapshots, optional responsible-unit reference, pointers to current/approved revisions, creator identity, and audit timestamps.

Planning owns the plan header and revision pointers. PlanningPeriod and PlanRevision are same-module owners. Organization owns organization units, Topology owns physical/operational scope identity, and Identity owns actor identity. Cross-module references must remain scalar/application-contract references rather than database foreign keys.

### 90.2 Required field semantics

The Planning DDD declares `nameFr` and `topologyScopeType` required, and JPA/Flyway persist both as `NOT NULL`.

The live domain constructor does not reject either field when null/blank; it normalizes blank values to null. The create service passes command values directly into the aggregate.

The authoritative Planning boundary must therefore fail fast for required French display name and topology scope type instead of relying on a later persistence failure.

HMSR-077 does not promote optional Arabic/English names or optional product/responsible-unit fields into mandatory invariants.

### 90.3 Period ownership

`periodId` is mandatory and same-module. HRA-111 already adds a Planning-local FK from `hidra_planning_operational_plan.period_id` to `hidra_planning_period.id`.

No further database relationship correction is required for the parent planning period. Any additional lifecycle rule such as whether CLOSED periods may accept a new plan belongs to the authoritative creation/application policy and is not invented here beyond explicit Planning DDD evidence.

### 90.4 Plan code uniqueness

The Planning DDD explicitly requires:

```text
unique(hidra_planning_operational_plan.code)
```

The live migration defines `code NOT NULL` and an ordinary index, but repository evidence shows no unique constraint/index and `OperationalPlanRepositoryPort` exposes no code lookup.

The plan code must therefore receive concurrency-safe PostgreSQL uniqueness. Application duplicate detection may improve errors but must not replace the database guarantee.

### 90.5 PLAN_TYPE catalog family

`planTypeId` is mandatory and HRA-111 already protects generic same-module existence by FK to `hidra_planning_catalog_entry`.

The Planning DDD, however, defines a specific `PLAN_TYPE` family. Generic catalog-row existence is insufficient because an ID from another Planning catalog family could satisfy the FK.

The authoritative write path must validate that `planTypeId` resolves to an eligible/active Planning catalog entry in the exact `PLAN_TYPE` family.

### 90.6 Topology operational-scope boundary

The model stores:

```text
topologyScopeType
topologyScopeId
topologyScopeCode
topologyScopeNameSnapshot
```

The scalar-reference inventory classifies mandatory `topologyScopeId` as a cross-module stable Topology operational-scope reference. The Planning DDD likewise states that Planning stores topology references/snapshots while Topology remains owner.

The live create service accepts the typed scope tuple directly and repository evidence does not show a Planning-facing Topology lookup contract validating it.

The authoritative creation boundary must validate populated mandatory scope identity through a Topology-owned application/lookup contract while preserving the stored code/name snapshots. No cross-module Topology FK should be introduced.

### 90.7 Responsible Organization and creator actor

`responsibleOrganizationUnitId` is optional and Organization-owned. `createdByActorId` is mandatory and Identity-owned.

The current create path copies both values directly from the command. Repository evidence does not show owner-controlled lookup validation in this use case.

The authoritative creation boundary must validate mandatory creator identity and, when populated, responsible organization-unit identity through their owning contracts. No cross-module Organization or Identity database FK should be introduced.

### 90.8 Current/approved revision pointers

`currentRevisionId` and `approvedRevisionId` are optional same-module references to `PlanRevision`. They form the cyclic SCC-04 relationship with `PlanRevision.planId`.

The base Planning migration creates only indexes for these pointers. HRA-111 did not add FKs because its enforcement scope covered mandatory references only.

When populated, both pointers must fail closed to existing Planning-owned revisions. Same-module FKs are architecturally acceptable, provided the cyclic schema is introduced additively and safely; an explicitly justified application existence boundary may supplement this.

The governing relation must also prevent a plan from pointing at a revision owned by a different plan. A plain FK to revision ID alone would not establish that ownership correlation; implementation must preserve the documented `OperationalPlan -> PlanRevision` parent relationship.

### 90.9 Lifecycle state and approved baseline

`OperationalPlanStatus` is explicitly documented as the stable Planning lifecycle:

```text
DRAFT
SUBMITTED
APPROVED
ACTIVE
SUPERSEDED
CANCELLED
CLOSED
```

It is appropriate as a domain/technical enum rather than a catalog taxonomy.

The DDD states that only one approved revision should be active at a time and that `approvedRevisionId` is the revision used downstream. HMSR-077 records the pointer-integrity obligation but does not invent a full lifecycle-transition state machine not otherwise specified in current evidence.

### 90.10 Product reference

`productTypeId` is optional. Current repository evidence does not establish a definitive owner/contract or exact catalog family for this HMS subject review.

HMSR-077 therefore preserves it as an unresolved/non-subject reference and does not invent a relational target or validation contract. HMS-006 reconciliation may revisit it if stronger evidence becomes available.

### 90.11 Required revisions

Six evidence-backed obligations remain:

1. **Enforce DDD-required `nameFr` and `topologyScopeType` before persistence.**

2. **Enforce global plan-code uniqueness in PostgreSQL** for `hidra_planning_operational_plan.code`.

3. **Enforce `PLAN_TYPE` catalog-family eligibility** for mandatory `planTypeId`; the existing generic catalog FK is not sufficient.

4. **Validate mandatory Topology operational-scope identity through a Topology-owned contract** while preserving scope snapshots and without a cross-module DB FK.

5. **Validate cross-context ownership references through owner-controlled contracts:** mandatory creator actor and populated responsible Organization unit; do not introduce cross-module FKs.

6. **Protect populated current/approved revision pointers and their parent-plan correlation.** Both IDs must reference existing Planning revisions belonging to the same OperationalPlan; use same-module persistence/application integrity without weakening the SCC boundary.

HMSR-077 does not modify production Java, JPA, Flyway, API/application contracts, tests, Planning/Topology/Organization/Identity data, or provisioned data.

### 90.12 Review conclusion

**REVISE.** `OperationalPlan` is a coherent Planning aggregate header with correct separation of planned state from telemetry/monitoring, an appropriate lifecycle enum, protected mandatory PlanningPeriod ownership, and snapshot-based cross-context scope semantics.

The target baseline cannot mark it APPROVED while DDD-required fields can reach persistence as null, plan-code uniqueness is absent, `PLAN_TYPE` family eligibility is unchecked, cross-context scope/identity references are trusted directly, and current/approved revision pointers are not fail-closed or correlated to the owning plan.

HMS reconciliation must retain these obligations until explicitly authorized Planning production corrections resolve them or stronger repository evidence changes the governing Planning DDD.

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

- `c8b9644fc865510191e60d2f8e172f962abd32b8` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:129](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L129)
- [docs/roadmap/model-semantic-review.md:272](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L272)
- [docs/roadmap/model-semantic-review.md:14568](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L14568)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:272 | HMSR-077 | 4 | planning | OperationalPlan | SCC-04 | organization.OrganizationUnit, planning.PlanRevision, planning.PlanningPeriod | 1 | 8 | 1 | REVISE | Completed | docs(model-review): review planning OperationalPlan
doc/domain/SEMANTIC_DECISIONS.md:129 | HMSR-077 | [planning.OperationalPlan](#planning-operationalplan) | REVISE | HMR-065
~~~~~~~~

<a id="hapi-404"></a>
## HAPI-404 — review planning Nomination

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMSR-094

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review planning Nomination

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 107. HMSR-094 — planning.Nomination review

**Decision:** REVISE  
**Review code:** HMSR-094  
**Dependency level:** 5  
**Bounded context:** planning  
**Confirmed upstream subject dependencies:** mandatory `planning.PlanRevision`; optional Party-owned shipper/counterparty references represented by `party.Party`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 6  
**Unresolved/non-subject reference count:** 2

### 107.1 Semantic role

`Nomination` is the Planning-owned request for a hydrocarbon movement quantity over a defined period. It may represent injection, receipt, delivery, transfer, export, import, or an internal operational movement.

Planning owns the nomination and expected operational state. It does not own physical topology assets, Party master data, contract masters, or actual telemetry/custody quantities.

### 107.2 Plan-revision ownership

`revisionId` is mandatory and same-module.

HRA-111 already protects it with:

```text
hidra_planning_nomination.revision_id
  -> hidra_planning_plan_revision.id
```

The domain constructor also rejects blank `revisionId`.

No further basic existence correction is required for the mandatory parent revision.

### 107.3 Quantity and period invariants

The Planning DDD explicitly requires:

```text
quantity > 0
periodStart < periodEnd
```

The live domain constructor only checks `quantity != null`; zero and negative quantities are currently accepted.

The temporal guard rejects only `periodEnd < periodStart`, which permits `periodStart == periodEnd`. That is weaker than the DDD's strict interval rule.

The target baseline therefore requires fail-fast domain/application enforcement of positive quantity and a strictly positive nomination interval.

### 107.4 Required audit timestamps

The DDD marks both `createdAt` and `updatedAt` required. JPA/Flyway persist both as `NOT NULL`.

The current domain constructor does not reject null values for either timestamp.

Because these fields are explicitly required nomination state, the domain/application write boundary must fail fast before persistence.

### 107.5 Nomination code uniqueness

The DDD requires:

```text
unique(hidra_planning_nomination.revision_id, code)
```

The live creation migration currently provides ordinary indexes for `revision_id` and `code`, but repository search found no database unique constraint for the pair.

The future production correction must enforce nomination-code uniqueness within a plan revision using the repository's normal application/database integrity pattern rather than relying on convention.

### 107.6 Nomination-type catalog semantics

`nominationTypeId` is mandatory and Planning-owned catalog data.

HRA-111 already protects generic catalog-row existence through:

```text
hidra_planning_nomination.nomination_type_id
  -> hidra_planning_catalog_entry.id
```

The Planning DDD defines the exact business family:

```text
NOMINATION_TYPE
```

with values such as `INJECTION`, `RECEIPT`, `DELIVERY`, `TRANSFER`, `EXPORT`, and `IMPORT`.

Generic row existence does not prove exact-family membership or eligibility. The authoritative write boundary must therefore validate `nominationTypeId` against the active/eligible `NOMINATION_TYPE` family.

### 107.7 Product and unit reference ownership

The DDD classifies `productTypeId`, `quantityUnitId`, and optional `rateUnitId` as references rather than declaring Planning-owned `PRODUCT_TYPE` or unit catalog families.

However, HRA-111 currently points the mandatory `product_type_id` and `quantity_unit_id` columns at `hidra_planning_catalog_entry`.

The Planning catalog list does not define a `PRODUCT_TYPE`, `QUANTITY_UNIT`, or `RATE_UNIT` family. HMSR-094 therefore cannot treat those generic Planning-catalog FKs as semantically sufficient ownership evidence.

HMS reconciliation must preserve an explicit obligation to resolve the authoritative product/unit owner or catalog contract. Any production correction must then align application validation and persistence constraints to that proven ownership; it must not invent a new Planning taxonomy merely to satisfy the existing FK shape.

### 107.8 Topology references

`sourceAssetType + sourceAssetId` and `destinationAssetType + destinationAssetId` are optional Topology references. Their code fields are snapshots.

The DDD explicitly requires neutral topology references and forbids embedding/importing Topology aggregates into Planning.

When supplied, these asset references must be validated through a Topology-owned public lookup/reference contract appropriate to the asset type. No cross-module database FK should be introduced.

The snapshot code fields remain descriptive snapshots and are not ownership relationships.

### 107.9 Party references

`shipperPartyId` and `counterpartyId` are optional Party-owned references. `shipperPartyCodeSnapshot` is a historical/readability snapshot.

When supplied, Party identities must be resolved through a Party-owned application/reference contract. Planning must not add a cross-module Party database FK.

The DDD explicitly rejects free-text shipper naming in the final model, so the current scalar Party-reference shape is directionally correct.

### 107.10 Scenario and neutral contract references

The DDD describes optional `scenarioId` as the scenario to which the nomination belongs, but the current HMS review register does not list `PlanScenario` as a confirmed upstream subject dependency.

HMSR-094 therefore does not silently rewrite the dependency graph. HMS-006 must reconcile that DDD evidence against the graph/persistence classification before any production FK or ownership change is authorized.

`contractReferenceId` remains a neutral optional contract reference because the current repository does not establish a canonical contract-master owner for this field. HMSR-094 does not invent one.

### 107.11 Lifecycle status

`NominationStatus` contains:

```text
DRAFT
CONFIRMED
ALLOCATED
REJECTED
CANCELLED
SUPERSEDED
```

The Planning DDD explicitly classifies technical lifecycle states as suitable for stable enums or constrained strings. No catalog conversion is required for this field on current evidence.

### 107.12 Required revisions

Seven evidence-backed obligations remain:

1. **Enforce `quantity > 0`**, not merely non-null quantity.
2. **Enforce strict `periodStart < periodEnd`**, rejecting zero-duration nominations.
3. **Enforce required `createdAt` and `updatedAt` before persistence.**
4. **Enforce unique `(revisionId, code)` nomination identity** using application/database integrity consistent with the DDD.
5. **Validate `nominationTypeId` against the active/eligible exact `NOMINATION_TYPE` family**, not merely generic Planning catalog existence.
6. **Reconcile product/unit reference ownership** for `productTypeId`, `quantityUnitId`, and `rateUnitId`; the DDD does not establish corresponding Planning catalog families, so the existing generic Planning-catalog FK shape is not by itself sufficient semantic proof.
7. **Validate supplied Topology and Party references through owner-controlled contracts**, preserving scalar/snapshot fields and avoiding cross-module database FKs; separately reconcile the optional `scenarioId` graph edge during HMS-006 before production changes.

HMSR-094 does not modify production Java, JPA, Flyway, API/application contracts, tests, Planning/Party/Topology data, or provisioned data.

### 107.13 Review conclusion

**REVISE.** The core Planning ownership and mandatory PlanRevision relationship are sound, the status enum is appropriate, and the model correctly uses scalar/snapshot references for external bounded contexts.

The target baseline cannot mark `Nomination` APPROVED while positive-quantity and strict-period invariants are weaker than the DDD, required timestamps are not fail-fast, revision-scoped code uniqueness is not enforced in the live schema, nomination-type membership is only generically protected, product/unit ownership remains semantically unresolved, and owner-controlled validation for optional Topology/Party references is not established.

HMS reconciliation must retain these obligations until explicitly authorized Planning production corrections resolve them or stronger repository evidence changes the governing Planning contract.

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

- `db3f08c3f4ffb71c0ae7c1b5607c564fb638f6bc` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:146](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L146)
- [docs/roadmap/model-semantic-review.md:289](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L289)
- [docs/roadmap/model-semantic-review.md:16821](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L16821)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:289 | HMSR-094 | 5 | planning | Nomination | — | party.Party, planning.PlanRevision | 1 | 6 | 2 | REVISE | Completed | docs(model-review): review planning Nomination
doc/domain/SEMANTIC_DECISIONS.md:146 | HMSR-094 | [planning.Nomination](#planning-nomination) | REVISE | HMR-080
~~~~~~~~

<a id="hapi-421"></a>
## HAPI-421 — review planning PlanTarget

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HMSR-111

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review planning PlanTarget

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 124. HMSR-111 — planning.PlanTarget review

**Decision:** REVISE  
**Review code:** HMSR-111  
**Dependency level:** 6  
**Bounded context:** planning  
**Confirmed upstream subject dependencies:** mandatory `planning.PlanRevision`; optional `planning.Nomination`; optional Telemetry-owned `telemetry.TelemetryPoint`; optional same-module `PlanScenario` plus topology/unit references outside the HMS subject set  
**Confirmed direct HMS subject dependents:** 2  
**Transitive HMS subject dependents:** 5  
**Unresolved/non-subject reference count:** 2

### 124.1 Semantic role

`PlanTarget` is Planning's generic expected-value record consumed downstream by Monitoring. It represents planned flow, pressure, volume, temperature, density, linepack, equipment-state, operating-mode, or similar target semantics over a validity interval.

Planning owns the expected state. Telemetry owns actual measurements, Monitoring owns deviation facts, and Topology owns physical assets.

### 124.2 Field and persistence alignment

The live domain record, JPA entity, persistence mapper, and base Planning migration agree on the 22-field target shape.

Required persistence fields include `revisionId`, `targetTypeId`, `topologyAssetType`, `topologyAssetId`, `topologyAssetCode`, `validFrom`, `validTo`, and `status`. The domain already fail-fast validates the mandatory identifiers except `topologyAssetType`, plus the validity timestamps/status, and rejects `validTo < validFrom`.

`PlanTargetStatus` contains `DRAFT`, `ACTIVE`, `SUPERSEDED`, and `CANCELLED`. Current Planning evidence supports retaining it as a lifecycle enum.

### 124.3 Same-module PlanRevision integrity

`revisionId` is the mandatory Planning-owned parent reference.

HRA-111 correctly installs:

```text
hidra_planning_plan_target.revision_id
  -> hidra_planning_plan_revision.id
```

and `PlanTarget` already rejects a blank revision ID.

Because the current repository exposes PlanTarget primarily through persistence/query contracts and repository search found no authoritative PlanTarget creation application service, HMSR-111 does not invent a new write workflow. Any future write path must nevertheless fail closed on an unknown PlanRevision before persistence.

### 124.4 Optional Nomination and Scenario references

`nominationId` and `scenarioId` are optional same-module references. The DDD permits a target to exist without either.

The current schema indexes these fields but does not add mandatory FKs through HRA-111 because they are nullable. Future production reconciliation should preserve optionality while ensuring that any supplied same-module ID resolves to the correct Planning aggregate/entity and is semantically compatible with the target's `revisionId`.

Specifically, a supplied Nomination or PlanScenario should not silently belong to another revision. The repository evidence does not currently expose an authoritative creation path that performs this compatibility check.

### 124.5 Target type catalog semantics

`targetTypeId` is a required same-module catalog reference. HRA-111 correctly provides row-existence protection against `hidra_planning_catalog_entry(id)`.

The DDD defines its business family as Planning target types such as `FLOW_RATE`, `PRESSURE`, `VOLUME`, `TEMPERATURE`, `DENSITY`, `LINEPACK`, `EQUIPMENT_STATE`, and `OPERATING_MODE`.

Row existence alone does not prove target-type family membership. Future production reconciliation must validate `targetTypeId` through Planning-owned catalog-family policy instead of accepting any Planning catalog row.

### 124.6 Numeric versus state target value shape

The governing DDD explicitly states:

```text
numeric target must have targetValue and unitId
state target may use targetTextValue
```

The current domain record allows `targetValue`, `targetTextValue`, and `unitId` to all be null and contains no target-type-aware value-shape validation.

This means a numeric FLOW_RATE/PRESSURE/VOLUME/etc. target can be persisted without a numeric value or unit, and a state-style target can be created without text state content.

Future production reconciliation must enforce the value representation appropriate to the resolved target type. HMSR-111 does not hard-code a brittle Java switch over catalog IDs; the rule should be driven by Planning-owned target-type semantics/configuration.

### 124.7 Topology reference boundary

`topologyAssetType`, `topologyAssetId`, `topologyAssetCode`, and optional name snapshot are Planning-side neutral references/snapshots. Topology remains the owner.

The DDD marks `topologyAssetType`, `topologyAssetId`, and `topologyAssetCode` as required. The current domain model validates the ID and code but not a blank/null `topologyAssetType`.

Future production reconciliation must fail closed on missing `topologyAssetType` before persistence. Live asset existence/type compatibility, when required, belongs behind a Topology-owned public reference contract; no cross-module database FK is authorized.

### 124.8 Telemetry reference boundary

`telemetryPointId` and `telemetryPointCodeSnapshot` are optional Telemetry-owned references used to associate an expected target with the point providing actual comparison data.

They must remain cross-module scalar/snapshot references with no Planning-to-Telemetry database FK.

When `telemetryPointId` is supplied and business behavior depends on it, validity should be checked through Telemetry's public reference contract. Planning must not import Telemetry persistence or reading models.

### 124.9 Validity and tolerance semantics

The domain correctly prevents `validTo` from preceding `validFrom`. The DDD source does not establish a stricter non-equality rule, so HMSR-111 does not invent one.

`toleranceLow` and `toleranceHigh` are optional. The current source does not explicitly define ordering or sign invariants for them, so no unsupported tolerance rule is introduced by this review.

### 124.10 Required revisions

Five evidence-backed production obligations remain:

1. **Enforce target-type-dependent value shape:** numeric target types require `targetValue` and `unitId`; state/text target types must carry the representation required by Planning target-type policy.
2. **Validate `targetTypeId` as a member of the Planning target-type catalog family, not merely any existing Planning catalog row.**
3. **Fail closed on missing `topologyAssetType` before persistence; validate supplied topology references through Topology-owned public contracts when required.**
4. **For supplied optional `nominationId` / `scenarioId`, validate same-module existence and revision compatibility instead of allowing cross-revision references.**
5. **For supplied `telemetryPointId`, preserve the cross-module scalar boundary and validate through Telemetry-owned contracts when the use case requires a live point.**

HMSR-111 does not modify production Java, JPA, Flyway, APIs, application contracts, tests, Planning/Telemetry/Topology data, or provisioned data.

### 124.11 Review conclusion

**REVISE.** The ownership boundary, 22-field shape, mandatory PlanRevision FK, lifecycle enum, validity-window guard, and neutral external-reference strategy are appropriate.

The target baseline cannot be marked APPROVED while target-type-specific value semantics, catalog-family membership, required topology asset type, and optional same-module reference compatibility remain unenforced.

HMS reconciliation must retain these obligations until explicitly authorized Planning production work resolves them or stronger repository evidence changes the governing contract.

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

- `6b3fd2d19db72d54c54007810d68a7eabf9e968d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:163](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L163)
- [docs/roadmap/model-semantic-review.md:306](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L306)
- [docs/roadmap/model-semantic-review.md:19000](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L19000)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:306 | HMSR-111 | 6 | planning | PlanTarget | — | planning.Nomination, planning.PlanRevision, telemetry.TelemetryPoint | 2 | 5 | 2 | REVISE | Completed | docs(model-review): review planning PlanTarget
doc/domain/SEMANTIC_DECISIONS.md:163 | HMSR-111 | [planning.PlanTarget](#planning-plantarget) | REVISE | HMR-094
~~~~~~~~

<a id="hapi-659"></a>
## HAPI-659 — return application revision result

Phase: HPS-013 — Planning and Nominations

Legacy IDs: HRA-100

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Return an application result DTO from plan-revision updates rather than a domain PlanRevision to REST.

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`, `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `21d9ea73641c698de8f37ad0dbb1215ae14d3dd3` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/repository-remediation.md:344](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/repository-remediation.md#L344)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/repository-remediation.md:344 | HRA-100 | refactor(planning): return application revision result | Return an application result DTO from plan-revision updates rather than a domain `PlanRevision` to REST. | Planning controller no longer depends on the domain model for this flow. | HRA-002 | **Completed** — `UpdatePlanRevisionUseCase` now returns an application-owned `Result` record containing the existing response fields with status represented as a string; `PlanRevisionUpdateApplicationService` maps the persisted domain revision into that result; `PlanRevisionCommandController` no longer imports `PlanRevision` or `PlanRevisionStatus`; focused service/controller tests cover the application-result boundary without changing the REST response shape.
~~~~~~~~

<a id="hapi-796"></a>
## HAPI-796 — expose HWEB-010 query contracts

Phase: HPS-013 — Planning and Nominations

Legacy IDs: PLN-001

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Read-only list/detail contracts for periods, operational plans, revisions, nominations and plan targets; stable pagination; deterministic 400/404; route-permission publication; no new lifecycle mutations. PR CI 34657410805 passed compile, tests, full verification, acceptance compile/test/verify, deterministic OpenAPI publication and artifact upload on exact head 0735592fca3075754c06a3d1a98d0a8236e8e6ec.

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanningQueryController.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningQueryApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningQueryApplicationServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningQueryApplicationService.java`, `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningQueryApplicationServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanningQueryController.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningQueryApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningQueryApplicationServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### PLN-001 public read contract

```text
GET /api/v1/planning/periods?page={page}&size={size}
GET /api/v1/planning/periods/{id}
GET /api/v1/planning/operational-plans?page={page}&size={size}
GET /api/v1/planning/operational-plans/{id}
GET /api/v1/planning/revisions?planId={planId}&page={page}&size={size}
GET /api/v1/planning/revisions/{id}
GET /api/v1/planning/nominations?revisionId={revisionId}&page={page}&size={size}
GET /api/v1/planning/nominations/{id}
GET /api/v1/planning/targets?revisionId={revisionId}&page={page}&size={size}
GET /api/v1/planning/targets/{id}
```

Paging is zero-based. Default size is 50. Valid size range is 1..200. Missing/blank relationship identifiers and invalid paging are 400-class request errors. Unknown detail identifiers are 404 through the platform exception handler.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningQueryApplicationServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `80174bd59b5f3cae441fff91a8fae99a1bad3a92` — exact registered-message commit; surviving paths matched to current tree.
- `0735592fca3075754c06a3d1a98d0a8236e8e6ec` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/planning.md:41](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L41)
- [docs/roadmap/planning.md:46](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L46)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/planning.md:41 | PLN-001 | feat(planning): expose HWEB-010 query contracts | Completed | Read-only list/detail contracts for periods, operational plans, revisions, nominations and plan targets; stable pagination; deterministic 400/404; route-permission publication; no new lifecycle mutations. PR CI `34657410805` passed compile, tests, full verification, acceptance compile/test/verify, deterministic OpenAPI publication and artifact upload on exact head `0735592fca3075754c06a3d1a98d0a8236e8e6ec`.
~~~~~~~~

<a id="hapi-797"></a>
## HAPI-797 — publish authoritative workflow approval integration

Phase: HPS-013 — Planning and Nominations

Legacy IDs: PLN-002

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Revision-scoped approval status/actions and backend-owned transition execution using workflow public contracts; planning applies the resulting lifecycle effect; deterministic stale-task conflict is preserved; no client task scanning or transition-name inference. Tracks issue #70. Final merge 6ef581f557e42e8d96b03ccf429562e646f2e321; merge-SHA OpenAPI artifact 10293549730.

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanningApprovalController.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/ExecutePlanningApprovalActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningApprovalExecutionResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningApprovalResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningApprovalUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTargetTransitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/query/JpaWorkflowQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningApprovalUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTargetTransitionUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java`, `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/query/JpaWorkflowQueryAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanningApprovalController.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/ExecutePlanningApprovalActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningApprovalExecutionResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningApprovalResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningApprovalUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTargetTransitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/query/JpaWorkflowQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### PLN-002 public contract

```text
GET  /api/v1/planning/revisions/{revisionId}/approval
POST /api/v1/planning/revisions/{revisionId}/approval/actions/{transitionId}/execute
```

The GET response identifies the revision, its planning status, workflow instance, authoritative current task (when actionable), task `updatedAt`, and backend-defined available actions. The POST request carries the authoritative `expectedTaskUpdatedAt` plus optional reason/note/comment/correlation fields. The backend resolves the task from the revision's workflow instance, verifies the workflow target belongs to that revision, executes the selected workflow transition, and then applies the planning lifecycle effect from the returned workflow decision.

Lifecycle mapping owned by planning for PLN-002:

```text
APPROVE            -> APPROVED
REJECT             -> REJECTED
REQUEST_CORRECTION -> REJECTED
RETURN             -> REJECTED
CANCEL             -> WITHDRAWN
CORRECT            -> SUBMITTED
DELEGATE           -> SUBMITTED
ESCALATE           -> SUBMITTED
```

`COMMENT` is not state advancing and remains unavailable through workflow transition execution. Approval writes preserve workflow's `expectedTaskUpdatedAt` stale-task conflict behavior. A revision without `workflowInstanceId`, a workflow instance targeting another module/id, or a missing current task is rejected deterministically rather than guessed.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationServiceTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `6ef581f557e42e8d96b03ccf429562e646f2e321` — exact registered-message commit; surviving paths matched to current tree.
- `f62b87481c7f6555633edb8a9e95c3424122c968` — exact registered-message commit; surviving paths matched to current tree.
- `bea61b3727168fe276d3995ce07d749698307ab8` — exact registered-message commit; surviving paths matched to current tree.
- `8597285b3e64e445f074fa6ade6bf33cd13e0729` — exact registered-message commit; surviving paths matched to current tree.
- `88c2ae80bdbcf498d1ac2ec0fbbfe1a09d563fcd` — exact registered-message commit; surviving paths matched to current tree.
- `0dc3cfec461ca79da5ab92705e50603f0f0ee772` — exact registered-message commit; surviving paths matched to current tree.
- `0e86fe280f44f674d1180976bad8bece6d0a77cb` — exact registered-message commit; surviving paths matched to current tree.
- `209cdbb641cc7c260cb516a750eb5ab390d276db` — exact registered-message commit; surviving paths matched to current tree.
- `32f6ea8f130514cef2007571e60ce7248d3d5c21` — exact registered-message commit; surviving paths matched to current tree.
- `612e0761d85515c9b1f1d904d2f1b1a7eef60185` — exact registered-message commit; surviving paths matched to current tree.
- `48044de24a20541b06e3a8067b3167aded4642ab` — exact registered-message commit; surviving paths matched to current tree.
- `05ad150a949bbeebb35329035357152a35466198` — exact registered-message commit; surviving paths matched to current tree.
- `7d47637c1c30dd4edae0ec47ab596aef5ac81f64` — exact registered-message commit; surviving paths matched to current tree.
- `ba33f552d9da55b5ab960b01d63455a9978c8613` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/planning.md:42](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L42)
- [docs/roadmap/planning.md:63](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L63)
- [docs/roadmap/planning.md:87](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L87)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/planning.md:42 | PLN-002 | feat(planning): publish authoritative workflow approval integration | Completed | Revision-scoped approval status/actions and backend-owned transition execution using workflow public contracts; planning applies the resulting lifecycle effect; deterministic stale-task conflict is preserved; no client task scanning or transition-name inference. Tracks issue #70. Final merge `6ef581f557e42e8d96b03ccf429562e646f2e321`; merge-SHA OpenAPI artifact `10293549730`.
~~~~~~~~

<a id="hapi-798"></a>
## HAPI-798 — expose plan-target-scoped deviations

Phase: HPS-013 — Planning and Nominations

Legacy IDs: PLN-003

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Extend the existing monitoring deviation collection with an optional exact planTargetId filter so HWEB-010-05 can retrieve authoritative comparison rows for one planning target without broad-page scanning or frontend arithmetic. Tracks issue #71. Final merge df8c012be9034886e53f2ec64c28946f18f67b31.

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringQueryController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/query/JpaMonitoringQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/query/JpaMonitoringQueryAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/query/JpaMonitoringQueryAdapter.java`, `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/query/JpaMonitoringQueryAdapterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringQueryController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/query/JpaMonitoringQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/query/JpaMonitoringQueryAdapterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### PLN-003 public contract

```text
GET /api/v1/monitoring/deviations?planTargetId={planTargetId}&status={status}&severity={severity}&topologyAssetId={topologyAssetId}&telemetryPointId={telemetryPointId}&from={from}&to={to}&page={page}&size={size}
```

`planTargetId` is an exact filter over monitoring-owned `PlanActualDeviation.planTargetId`. It composes with the existing monitoring filters and existing paging. When no deviation matches the requested target, the collection returns an empty page rather than forcing HidraWEB to scan other pages or infer a missing-resource lifecycle.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/query/JpaMonitoringQueryAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `e8df49c79f6765e36d6bcf80558f8a5c61aae160` — exact registered-message commit; surviving paths matched to current tree.
- `b99c459e0257110be3d036029751044537607046` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/planning.md:43](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L43)
- [docs/roadmap/planning.md:96](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L96)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/planning.md:43 | PLN-003 | feat(monitoring): expose plan-target-scoped deviations | Completed | Extend the existing monitoring deviation collection with an optional exact `planTargetId` filter so HWEB-010-05 can retrieve authoritative comparison rows for one planning target without broad-page scanning or frontend arithmetic. Tracks issue #71. Final merge `df8c012be9034886e53f2ec64c28946f18f67b31`.
~~~~~~~~

<a id="hapi-799"></a>
## HAPI-799 — publish revision concurrency contract

Phase: HPS-013 — Planning and Nominations

Legacy IDs: PLN-004

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `planning` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Publish an explicit concurrency-protected update for the current editable plan revision's change-reason metadata. expectedUpdatedAt is the authoritative client precondition; stale requests return deterministic 409 PLANNING_REVISION_CONFLICT; the successful response returns the refreshed revision including its new updatedAt. No status or workflow lifecycle mutation is added. Tracks issue #72. Final merge 7bbdb49dbc40c5637d93a05863e69f9576a2edba; merge-SHA OpenAPI artifact 10298289002.

### 2. Existing Implementation

Dedicated query APIs, revision-scoped Workflow approval and Party/Custody/Telemetry reference validation exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/PlanningRevisionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/exception/PlanningRevisionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/PlanningRevisionApiExceptionHandlerTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern periods, plan revisions, targets, nominations and accountable approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `planning`; primary phase owns this obligation. A nomination API does not establish a capacity solver, dispatch optimizer or custody settlement. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/planning/domain/exception/PlanningRevisionConflictException.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`, `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/PlanningRevisionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/exception/PlanningRevisionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/PlanningRevisionApiExceptionHandlerTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### PLN-004 public contract

```text
PATCH /api/v1/planning/revisions/{revisionId}
```

Request:

```json
{
  "expectedUpdatedAt": "<PlanRevisionView.updatedAt>",
  "changeReasonCodeId": "<optional catalog id>",
  "changeReasonText": "<optional explanation>"
}
```

The revision read contract's `updatedAt` is explicitly promoted to the write precondition for this mutation. The application locks and reloads the revision, checks that the parent operational plan still identifies it as `currentRevisionId`, compares `expectedUpdatedAt` exactly with the persisted revision token, and only then saves the change-reason metadata with a refreshed `updatedAt`. A mismatch returns HTTP 409 with problem code `PLANNING_REVISION_CONFLICT` and instructs the client to refetch before retrying. The mutation does not accept or alter revision status, workflow state, revision number/code, lineage, approval fields, or planning targets.

Canonical route permission remains backend-derived from the route descriptor and PATCH action; HidraWEB must consume that descriptor rather than hard-code authorization semantics.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/planning/api/rest/PlanningRevisionApiExceptionHandlerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `5316d0d5e0010f462829beb09cadc22007875e7c` — exact registered-message commit; surviving paths matched to current tree.
- `ba28204981717aac83ee1deecd4460e23989acb1` — exact registered-message commit; surviving paths matched to current tree.
- `ef7aab59564613135357ebadd2f3e9f009463712` — exact registered-message commit; surviving paths matched to current tree.
- `b51d9590aaa116e1f68e589841ad1861b2ddf804` — exact registered-message commit; surviving paths matched to current tree.
- `cc1ed92b265cb8e9a95204f306aa6a2c338ff431` — exact registered-message commit; surviving paths matched to current tree.
- `33c7cfa654551ad19368c979087e9b44a780e197` — exact registered-message commit; surviving paths matched to current tree.
- `61312b60ab54a724a65e959f5e162c3300ceef45` — exact registered-message commit; surviving paths matched to current tree.
- `f34f8d1f76f4c91633ec7d8c5867d58a13b1ca06` — exact registered-message commit; surviving paths matched to current tree.
- `65183151f1c255b2d6a19633eaf724dd7e87ed92` — exact registered-message commit; surviving paths matched to current tree.
- `205e60453b0d8836c67ac8996d2340777b1cc849` — exact registered-message commit; surviving paths matched to current tree.
- `27ffcabd999346ac31ce635a730a9c287e849ddf` — exact registered-message commit; surviving paths matched to current tree.
- `36aa3729a91933082d4c5fe64c6558c4816c566f` — exact registered-message commit; surviving paths matched to current tree.
- `49ae821ff2c8923da995bfc80e7d24eff1e9ec4c` — exact registered-message commit; surviving paths matched to current tree.
- `73897e6170b759edd2ee626c5babb6687b8f8b38` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/planning.md:44](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L44)
- [docs/roadmap/planning.md:104](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L104)
- [docs/roadmap/planning.md:124](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/planning.md#L124)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/planning.md:44 | PLN-004 | feat(planning): publish revision concurrency contract | Completed | Publish an explicit concurrency-protected update for the current editable plan revision's change-reason metadata. `expectedUpdatedAt` is the authoritative client precondition; stale requests return deterministic `409 PLANNING_REVISION_CONFLICT`; the successful response returns the refreshed revision including its new `updatedAt`. No status or workflow lifecycle mutation is added. Tracks issue #72. Final merge `7bbdb49dbc40c5637d93a05863e69f9576a2edba`; merge-SHA OpenAPI artifact `10298289002`.
~~~~~~~~

<!-- task-register:payload:end -->
