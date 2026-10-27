# HPS-010 — Telemetry and Monitoring: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-010 — Telemetry and Monitoring

<a id="hapi-100"></a>
## HAPI-100 — add high velocity ingestion contracts

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: EXT-004

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add the minimum Telemetry input/output contracts for validated batch ingestion and append persistence; reuse existing reading/source/point concepts.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/TelemetryModule.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/TelemetryApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/TelemetryRestApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/mapper/TelemetryApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Add the minimum Telemetry input/output contracts for validated batch ingestion and append persistence; reuse existing reading/source/point concepts..

### 4. Functional Requirements

No duplicate telemetry aggregate introduced; application remains infrastructure-neutral.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/TelemetryModule.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/TelemetryApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/TelemetryRestApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/mapper/TelemetryApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

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

- No duplicate telemetry aggregate introduced; application remains infrastructure-neutral.
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

- [docs/roadmap/extended-capabilities.md:96](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L96)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:96 | EXT-004 | feat(telemetry): add high velocity ingestion contracts | Add the minimum Telemetry input/output contracts for validated batch ingestion and append persistence; reuse existing reading/source/point concepts. | No duplicate telemetry aggregate introduced; application remains infrastructure-neutral. | Deferred
~~~~~~~~

<a id="hapi-101"></a>
## HAPI-101 — add timeseries storage migration

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: EXT-005

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add the actual next Flyway migration implementing the approved Timescale/PostgreSQL strategy.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Add the actual next Flyway migration implementing the approved Timescale/PostgreSQL strategy..

### 4. Functional Requirements

Existing reading authority reconciled; no guessed V_Next; extension prerequisites explicit; replay on fresh PostgreSQL image tested.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

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

- Existing reading authority reconciled; no guessed V_Next; extension prerequisites explicit; replay on fresh PostgreSQL image tested.
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

- [docs/roadmap/extended-capabilities.md:97](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L97)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:97 | EXT-005 | db(telemetry): add timeseries storage migration | Add the actual next Flyway migration implementing the approved Timescale/PostgreSQL strategy. | Existing reading authority reconciled; no guessed `V_Next`; extension prerequisites explicit; replay on fresh PostgreSQL image tested. | Deferred
~~~~~~~~

<a id="hapi-102"></a>
## HAPI-102 — add jdbc high velocity persistence adapter

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: EXT-006

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement approved JDBC batch persistence behind the Telemetry output port.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/TelemetryModule.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/TelemetryApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/TelemetryRestApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/mapper/TelemetryApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Implement approved JDBC batch persistence behind the Telemetry output port..

### 4. Functional Requirements

Uses parameterized batch operations; no Hibernate per-reading path; atomicity/idempotency semantics tested.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/TelemetryModule.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/TelemetryApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/TelemetryRestApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/mapper/TelemetryApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

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

- Uses parameterized batch operations; no Hibernate per-reading path; atomicity/idempotency semantics tested.
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

- [docs/roadmap/extended-capabilities.md:98](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L98)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:98 | EXT-006 | feat(telemetry): add jdbc high velocity persistence adapter | Implement approved JDBC batch persistence behind the Telemetry output port. | Uses parameterized batch operations; no Hibernate per-reading path; atomicity/idempotency semantics tested. | Deferred
~~~~~~~~

<a id="hapi-103"></a>
## HAPI-103 — verify high velocity ingestion

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: EXT-007

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add integration/performance-oriented tests for batch writes, duplicates, quality/timestamps and schema compatibility.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Add integration/performance-oriented tests for batch writes, duplicates, quality/timestamps and schema compatibility..

### 4. Functional Requirements

Required throughput baseline is documented before claiming production readiness.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

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

- Required throughput baseline is documented before claiming production readiness.
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

- [docs/roadmap/extended-capabilities.md:99](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L99)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:99 | EXT-007 | test(telemetry): verify high velocity ingestion | Add integration/performance-oriented tests for batch writes, duplicates, quality/timestamps and schema compatibility. | Required throughput baseline is documented before claiming production readiness. | Deferred
~~~~~~~~

<a id="hapi-160"></a>
## HAPI-160 — scan monitoring domain models

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMC-015

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 2 monitoring model files.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 24. HMC-015 — Monitoring module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `monitoring`  
**Baseline model files:** 2  
**Scanned models:** 2  
**Declared fields/components:** 37  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Monitoring model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| MonitoringRule | record | 17 | `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java` | Extracted |
| PlanActualDeviation | record | 20 | `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java` | Extracted |

### Monitoring fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| MonitoringRule | 1 | id | String |
| MonitoringRule | 2 | code | String |
| MonitoringRule | 3 | nameAr | String |
| MonitoringRule | 4 | nameFr | String |
| MonitoringRule | 5 | nameEn | String |
| MonitoringRule | 6 | ruleType | MonitoringRuleType |
| MonitoringRule | 7 | evaluationFrequencyId | String |
| MonitoringRule | 8 | topologyAssetType | String |
| MonitoringRule | 9 | topologyAssetId | String |
| MonitoringRule | 10 | topologyAssetCode | String |
| MonitoringRule | 11 | telemetryPointId | String |
| MonitoringRule | 12 | planningTargetTypeId | String |
| MonitoringRule | 13 | expression | String |
| MonitoringRule | 14 | status | MonitoringLifecycleStatus |
| MonitoringRule | 15 | createdByActorId | String |
| MonitoringRule | 16 | createdAt | Instant |
| MonitoringRule | 17 | updatedAt | Instant |
| PlanActualDeviation | 1 | id | String |
| PlanActualDeviation | 2 | evaluationId | String |
| PlanActualDeviation | 3 | planTargetId | String |
| PlanActualDeviation | 4 | expectedFlowStateId | String |
| PlanActualDeviation | 5 | trustedTelemetryReadingId | String |
| PlanActualDeviation | 6 | telemetryPointId | String |
| PlanActualDeviation | 7 | topologyAssetType | String |
| PlanActualDeviation | 8 | topologyAssetId | String |
| PlanActualDeviation | 9 | topologyAssetCode | String |
| PlanActualDeviation | 10 | actualValue | BigDecimal |
| PlanActualDeviation | 11 | expectedValue | BigDecimal |
| PlanActualDeviation | 12 | differenceValue | BigDecimal |
| PlanActualDeviation | 13 | differencePercent | BigDecimal |
| PlanActualDeviation | 14 | unitId | String |
| PlanActualDeviation | 15 | severity | DeviationSeverity |
| PlanActualDeviation | 16 | status | DeviationStatus |
| PlanActualDeviation | 17 | detectedAt | Instant |
| PlanActualDeviation | 18 | resolvedAt | Instant |
| PlanActualDeviation | 19 | reasonCode | String |
| PlanActualDeviation | 20 | reasonMessage | String |

### HMC-015 reconciliation

```text
baseline model files = 2
extracted models     = 2
exceptions           = 0
declared fields      = 37
zero-field models    = 0
```

The scan records only declarations under `monitoring/domain/model`. Monitoring domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, telemetry implementation, planning implementation, or database column metadata was mixed into this module scan.

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

- `bc44599b133b5f420611d7d585bb482b516fd221` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:143](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L143)
- [docs/roadmap/model-field-catalogue.md:1762](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L1762)
- [docs/roadmap/model-field-catalogue.md:1821](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L1821)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:143 | HMC-015 | docs(catalogue): scan monitoring domain models | Scan all 2 monitoring model files. | **Completed** — 2/2 models, 37 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-168"></a>
## HAPI-168 — scan telemetry domain models

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMC-023

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 4 telemetry model files.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/ReadingState.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/ReadingState.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 32. HMC-023 — Telemetry module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `telemetry`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 63  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Telemetry model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| TelemetryPoint | record | 18 | `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java` | Extracted |
| TelemetryReading | record | 16 | `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java` | Extracted |
| TelemetrySource | record | 12 | `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java` | Extracted |
| TrustedTelemetryReading | record | 17 | `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java` | Extracted |

### Telemetry fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| TelemetryPoint | 1 | id | String |
| TelemetryPoint | 2 | deviceId | String |
| TelemetryPoint | 3 | code | String |
| TelemetryPoint | 4 | nameAr | String |
| TelemetryPoint | 5 | nameFr | String |
| TelemetryPoint | 6 | nameEn | String |
| TelemetryPoint | 7 | pointTypeId | String |
| TelemetryPoint | 8 | signalTypeId | String |
| TelemetryPoint | 9 | unitId | String |
| TelemetryPoint | 10 | defaultAggregationMethodId | String |
| TelemetryPoint | 11 | samplingPeriodSeconds | Integer |
| TelemetryPoint | 12 | externalReference | String |
| TelemetryPoint | 13 | deadbandValue | BigDecimal |
| TelemetryPoint | 14 | minOperationalValue | BigDecimal |
| TelemetryPoint | 15 | maxOperationalValue | BigDecimal |
| TelemetryPoint | 16 | status | TelemetryLifecycleStatus |
| TelemetryPoint | 17 | createdAt | Instant |
| TelemetryPoint | 18 | updatedAt | Instant |
| TelemetryReading | 1 | id | String |
| TelemetryReading | 2 | pointId | String |
| TelemetryReading | 3 | numericValue | BigDecimal |
| TelemetryReading | 4 | textValue | String |
| TelemetryReading | 5 | booleanValue | Boolean |
| TelemetryReading | 6 | qualityCodeId | String |
| TelemetryReading | 7 | sourceTimestamp | Instant |
| TelemetryReading | 8 | receivedAt | Instant |
| TelemetryReading | 9 | state | ReadingState |
| TelemetryReading | 10 | ingestionBatchId | String |
| TelemetryReading | 11 | correlationId | String |
| TelemetryReading | 12 | rejectionReason | String |
| TelemetryReading | 13 | sourceSequenceNumber | String |
| TelemetryReading | 14 | externalTagMappingId | String |
| TelemetryReading | 15 | rawPayloadHash | String |
| TelemetryReading | 16 | createdAt | Instant |
| TelemetrySource | 1 | id | String |
| TelemetrySource | 2 | code | String |
| TelemetrySource | 3 | nameAr | String |
| TelemetrySource | 4 | nameFr | String |
| TelemetrySource | 5 | nameEn | String |
| TelemetrySource | 6 | sourceTypeId | String |
| TelemetrySource | 7 | protocolId | String |
| TelemetrySource | 8 | endpointUri | String |
| TelemetrySource | 9 | externalReference | String |
| TelemetrySource | 10 | status | TelemetryLifecycleStatus |
| TelemetrySource | 11 | createdAt | Instant |
| TelemetrySource | 12 | updatedAt | Instant |
| TrustedTelemetryReading | 1 | id | String |
| TrustedTelemetryReading | 2 | readingId | String |
| TrustedTelemetryReading | 3 | pointId | String |
| TrustedTelemetryReading | 4 | numericValue | BigDecimal |
| TrustedTelemetryReading | 5 | textValue | String |
| TrustedTelemetryReading | 6 | booleanValue | Boolean |
| TrustedTelemetryReading | 7 | unitId | String |
| TrustedTelemetryReading | 8 | qualityCodeId | String |
| TrustedTelemetryReading | 9 | trustLevel | TrustLevel |
| TrustedTelemetryReading | 10 | sourceTimestamp | Instant |
| TrustedTelemetryReading | 11 | trustedAt | Instant |
| TrustedTelemetryReading | 12 | qualityAssessmentId | String |
| TrustedTelemetryReading | 13 | topologyAssetTypeCode | String |
| TrustedTelemetryReading | 14 | topologyAssetId | String |
| TrustedTelemetryReading | 15 | topologyAssetCode | String |
| TrustedTelemetryReading | 16 | topologySnapshotId | String |
| TrustedTelemetryReading | 17 | ingestionBatchId | String |

### HMC-023 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 63
zero-field models    = 0
```

The scan records only declarations under `telemetry/domain/model`. Telemetry domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure acquisition adapter, SCADA/historian connector implementation, topology implementation, or database column metadata was mixed into this module scan.

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

- `eb242de97d4b05f9cd39ac37c61e2928cee272ea` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:151](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L151)
- [docs/roadmap/model-field-catalogue.md:2696](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2696)
- [docs/roadmap/model-field-catalogue.md:2783](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2783)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:151 | HMC-023 | docs(catalogue): scan telemetry domain models | Scan all 4 telemetry model files. | **Completed** — 4/4 models, 63 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-178"></a>
## HAPI-178 — remediate semantic review TelemetryPoint

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMR-005

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review TelemetryPoint

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryPointRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointStateSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointStateSnapshotJpaRepository.java`
- `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryPointRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryPointRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointStateSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointStateSnapshotJpaRepository.java`
- `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-005 — telemetry.TelemetryPoint

- Source review: `HMSR-005`
- Exact commit: `fix(telemetry): remediate semantic review TelemetryPoint`
- Status: **Planned — HMR-005A completed.** Telemetry-owned compatibility metadata is now defined without hard-coded business taxonomy values. `SIGNAL_TYPE` catalog entries will carry nullable technical `valueShape` metadata constrained to `NUMERIC`, `TEXT`, or `BOOLEAN`; registration fails closed when a referenced SIGNAL_TYPE lacks that metadata. `POINT_TYPE` catalog entries will carry `numericUnitExempt` (default false); a NUMERIC signal requires an active `TelemetryUnit` unless the referenced POINT_TYPE explicitly sets that flag true. Non-SIGNAL_TYPE rows must keep `valueShape` null, and non-POINT_TYPE rows must keep `numericUnitExempt=false`. HMR-005 may now implement these rules additively.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
  - `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryPointRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointStateSnapshotJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryCatalogEntryJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointBindingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointStateSnapshotJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`
  - `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TelemetryPointSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Mandatory French point name:** `nameFr` is required by the Telemetry DDD and is `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank and normalizes blank text to `null`.
  2. **Point-code uniqueness:** the Telemetry DDD requires point code uniqueness per device and explicitly recommends unique `(device_id, code)`. The live migration currently has separate non-unique indexes only, and the point repository/application service provides no demonstrated uniqueness check.
  3. **Catalog-family validation:** `pointTypeId`, `signalTypeId`, and `defaultAggregationMethodId` have specific catalog-family meanings (`POINT_TYPE`, `SIGNAL_TYPE`, `AGGREGATION_METHOD`). Existing generic catalog FKs, where present, prove only that a catalog row exists; the current point registration service does not demonstrate family/active-entry validation.
  4. **Unit reference integrity:** when `unitId` is present it semantically targets `TelemetryUnit`, but current point persistence does not demonstrate FK/application validation of that optional reference.
  5. **Signal/unit compatibility:** the DDD requires `signalTypeId` to determine compatible numeric/text/boolean reading shape and requires `unitId` for numeric engineering measurements unless explicitly exempted by point type. The current point registration service simply persists the supplied IDs and does not demonstrate this conditional validation.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `2bcf7de0ed90228547d342bf62a71db80e98b70e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:32](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L32)
- [doc/model-remediation/RECONCILIATION.md:13693](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13693)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15433](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15433)
- [docs/roadmap/model-semantic-remediation.md:151](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L151)
- [docs/roadmap/model-semantic-remediation.md:515](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L515)
- [docs/roadmap/telemetry.md:338](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L338)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:151 | HMR-005 | HMSR-005 | telemetry | TelemetryPoint | — | — | fix(telemetry): remediate semantic review TelemetryPoint | **Completed**
doc/model-remediation/RECONCILIATION.md:13693 | HMR-005 | Historical reference; unchanged | INDEPENDENT_UNCHANGED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15433 | HMR-005 | Historical reference; unchanged | INDEPENDENT_UNCHANGED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-179"></a>
## HAPI-179 — define point signal-unit compatibility metadata

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMR-005A

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define point signal-unit compatibility metadata

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Telemetry.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Telemetry.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-005A — telemetry point signal/unit compatibility prerequisite

- Source: HMR-005 / HMSR-005 obligation 5.
- Exact commit: `docs(telemetry): define point signal-unit compatibility metadata`
- Status: **Completed** — defined Telemetry-owned compatibility metadata, fail-closed behavior, and the exact HMR-005 allowlist extension; no production mutation or catalog seed was performed.
- Type: documentation/design prerequisite only; no production mutation.
- Purpose: define the minimum Telemetry-owned metadata required to classify a SIGNAL_TYPE entry as numeric/text/boolean and to state whether a POINT_TYPE explicitly exempts numeric points from requiring a unit.
- Required evidence: live `docs/data definition/Telemetry.md`, live telemetry catalog/unit domain/JPA schema, existing seeds/provisioning evidence, and HMR-005/HMSR-005.
- Allowed files:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
- Acceptance:
  1. define metadata structurally without inventing concrete SIGNAL_TYPE or POINT_TYPE business entries;
  2. specify fail-closed behavior when required metadata is missing;
  3. register the exact production files/migration/tests that HMR-005 may modify after the design is approved;
  4. keep catalog-family ownership inside Telemetry;
  5. do not seed or infer business values in this documentation task.
- On completion, HMR-005 returns to Planned with its amended exact allowlist and validation commands.


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

- `906ed73e34856b2ff3d319a29009120c0a13d9e2` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:561](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L561)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-212"></a>
## HAPI-212 — remediate semantic review TelemetrySource

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMR-030

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review TelemetrySource

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Telemetry.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetrySourceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceEndpointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceJpaRepository.java`
- `src/main/resources/db/migration/V20261004_030__hmr_030_telemetry_telemetry_source.sql`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetrySourceSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetrySourceRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Telemetry.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetrySourceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceEndpointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceJpaRepository.java`
- `src/main/resources/db/migration/V20261004_030__hmr_030_telemetry_telemetry_source.sql`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetrySourceSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-030 — telemetry.TelemetrySource

- Source review: `HMSR-033`
- Exact commit: `fix(telemetry): remediate semantic review TelemetrySource`
- Status: **Completed** — source code uniqueness, active SOURCE_TYPE/PROTOCOL family validation, mandatory French naming, secret-material exclusion, and the source-specific lifecycle subset are enforced across creation/repository/database boundaries.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_030__hmr_030_telemetry_telemetry_source.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
  - `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetrySourceRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceEndpointJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_030__hmr_030_telemetry_telemetry_source.sql`
  - `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetrySourceSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TelemetrySourceSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce unique TelemetrySource code.** The DDD-defined unique source code must be protected at the persistence boundary, preferably with an additive unique constraint/index and aligned application behavior.
  2. **Validate source type and protocol catalog semantics.** `sourceTypeId` must resolve to an active `SOURCE_TYPE` entry and `protocolId` to an active `PROTOCOL` entry; generic FK row existence is insufficient.
  3. **Require the DDD-mandated French source label.** `nameFr` must not normalize to null at the canonical source creation/domain boundary while the target DDD and schema require it.
  4. **Prevent secret material in source endpoint/external-reference fields.** The source write boundary must reject or sanitize actual secrets instead of persisting them in `endpointUri` or `externalReference`; credential material belongs outside telemetry persistence.
  5. **Reconcile TelemetrySource lifecycle vocabulary.** Establish whether `PLANNED` and `MAINTENANCE` are valid source states or are only valid for other Telemetry entities, and align source domain/application/persistence/API semantics accordingly.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetrySourceSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `ed9c14c54907fcb8b4837233c27ddfd7973fa80b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:176](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L176)
- [docs/roadmap/model-semantic-remediation.md:1744](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1744)
- [docs/roadmap/telemetry.md:885](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L885)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:176 | HMR-030 | HMSR-033 | telemetry | TelemetrySource | — | — | fix(telemetry): remediate semantic review TelemetrySource | Planned
~~~~~~~~

<a id="hapi-216"></a>
## HAPI-216 — remediate semantic review TelemetryReading

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMR-033

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review TelemetryReading

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Telemetry.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/exception/TelemetryReadingValidationException.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryReadingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
- `src/main/resources/db/migration/V20261004_033__hmr_033_telemetry_telemetry_reading.sql`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryReadingSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/telemetry/domain/exception/TelemetryReadingValidationException.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryReadingRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Telemetry.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/exception/TelemetryReadingValidationException.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryReadingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
- `src/main/resources/db/migration/V20261004_033__hmr_033_telemetry_telemetry_reading.sql`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryReadingSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-033 — telemetry.TelemetryReading

- Source review: `HMSR-037`
- Exact commit: `fix(telemetry): remediate semantic review TelemetryReading`
- Status: **Completed** — optional batch/mapping references are protected, qualityCodeId is constrained to QUALITY_CODE, and raw-reading value shape is enforced consistently with the rejected/quarantined null-value exception.
- SCC: —
- Recorded upstream HMS dependencies: telemetry.TelemetryPoint
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_033__hmr_033_telemetry_telemetry_reading.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/exception/TelemetryReadingValidationException.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryReadingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryReadingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_033__hmr_033_telemetry_telemetry_reading.sql`
  - `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryReadingSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TelemetryReadingSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated ingestion-batch references.** When `ingestionBatchId` is supplied, it must resolve to the Telemetry-owned ingestion batch rather than allowing a dangling traceability ID. An additive nullable same-module FK, application validation, or equivalent fail-closed mechanism may be used.
  2. **Protect populated external-tag-mapping references.** When `externalTagMappingId` is supplied, it must resolve to the Telemetry-owned external tag mapping rather than allowing a dangling mapping-evidence ID.
  3. **Enforce quality-code catalog-family semantics.** `qualityCodeId` must resolve specifically to the `QUALITY_CODE` Telemetry catalog family; the existing generic catalog FK only proves row existence.
  4. **Establish one authoritative raw-reading value-shape policy.** Reconcile the DDD's exactly-one rule and its explicit null/state exception, then enforce that contract consistently at the raw-reading creation/ingestion boundary rather than leaving `TelemetryReadingValueValidator` unused and stricter than the documented exception model.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryReadingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `94b7a512d51e85fcb7a455e4604c3b9a2f18db02` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:184](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L184)
- [docs/roadmap/model-semantic-remediation.md:1900](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1900)
- [docs/roadmap/telemetry.md:919](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L919)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:184 | HMR-033 | HMSR-037 | telemetry | TelemetryReading | — | telemetry.TelemetryPoint | fix(telemetry): remediate semantic review TelemetryReading | Planned
~~~~~~~~

<a id="hapi-226"></a>
## HAPI-226 — remediate semantic review MonitoringRule

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMR-040

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review MonitoringRule

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Monitoring.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/request/CreateMonitoringRuleRequest.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/response/MonitoringRuleResponse.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/command/CreateMonitoringRuleCommand.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/dto/MonitoringRuleSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaMonitoringRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/resources/db/migration/V20261004_040__hmr_040_monitoring_monitoring_rule.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`, `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaMonitoringRuleRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringRuleJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Monitoring.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/request/CreateMonitoringRuleRequest.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/response/MonitoringRuleResponse.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/command/CreateMonitoringRuleCommand.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/dto/MonitoringRuleSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaMonitoringRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/resources/db/migration/V20261004_040__hmr_040_monitoring_monitoring_rule.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-040 — monitoring.MonitoringRule

- Source review: `HMSR-048`
- Exact commit: `fix(monitoring): remediate semantic review MonitoringRule`
- Status: **Completed** — populated telemetryPointId is validated fail-closed through the Telemetry-owned Monitoring application contract; no cross-module database FK is introduced.
- SCC: —
- Recorded upstream HMS dependencies: telemetry.TelemetryPoint
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_040__hmr_040_monitoring_monitoring_rule.sql`
- Owner-contract prerequisite: HMR-040A registered — live preflight proved TelemetryQueryUseCase does not expose TelemetryPoint existence and no Telemetry-owned Monitoring contract exists.
- Exact write allowlist:
  - `docs/data definition/Monitoring.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/request/CreateMonitoringRuleRequest.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/response/MonitoringRuleResponse.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/command/CreateMonitoringRuleCommand.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/dto/MonitoringRuleSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaMonitoringRuleRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringRuleJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_040__hmr_040_monitoring_monitoring_rule.sql`
  - `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MonitoringRuleSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate populated MonitoringRule telemetryPointId through the Telemetry owner boundary.** When `telemetryPointId` is supplied, Monitoring must fail closed unless the referenced TelemetryPoint exists under the Telemetry bounded context. Do not add a cross-module database FK.

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
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `df756e1ae79b77f4c7709f07f635be9b2c703a64` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Monitoring.md:152](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Monitoring.md#L152)
- [docs/roadmap/model-semantic-remediation.md:191](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L191)
- [docs/roadmap/model-semantic-remediation.md:2248](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2248)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:191 | HMR-040 | HMSR-048 | monitoring | MonitoringRule | — | telemetry.TelemetryPoint | fix(monitoring): remediate semantic review MonitoringRule | Planned
~~~~~~~~

<a id="hapi-227"></a>
## HAPI-227 — register MonitoringRule telemetry owner prerequisite

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMR-040A

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

register MonitoringRule telemetry owner prerequisite

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`, `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-040A — MonitoringRule TelemetryPoint owner-contract prerequisite

- Source: HMR-040 / HMSR-048 plus live Telemetry/Monitoring architecture evidence.
- Exact commit: `docs(model-remediation): register MonitoringRule telemetry owner prerequisite`
- Status: **Completed** — the missing Telemetry-owned Monitoring reference contract, owner-side implementation, and exact guardrail registrations are authorized; HMR-040A itself changes documentation only.
- Type: documentation/architecture prerequisite.
- Live evidence:
  1. HMR-040 requires populated `MonitoringRule.telemetryPointId` to fail closed unless the Telemetry-owned point exists.
  2. The previously recorded candidate `telemetry.application.port.in.TelemetryQueryUseCase` exposes reading/time-series and quality-code queries only; it has no TelemetryPoint existence operation.
  3. `TelemetryPointRepositoryPort.findById(String id)` exists inside Telemetry and can resolve owner truth, but it is an internal outbound port and must not be imported by Monitoring.
  4. No `telemetry.application.contract.monitoring` package or equivalent exported Telemetry-to-Monitoring contract exists on live `main`.
  5. Repository architecture requires cross-module consumers to depend only on deliberate owner-exported `application.contract.<consumer>` packages, and both architecture guardrails maintain explicit exported-package registries.
- Required decisions:
  1. Telemetry must export a narrow Monitoring-facing application contract whose only required semantic is whether a TelemetryPoint exists for a supplied point ID.
  2. The contract must expose neutral scalar values only and must not return `TelemetryPoint`, repository types, JPA entities, or persistence DTOs.
  3. A Telemetry-owned application query service must implement the contract using `TelemetryPointRepositoryPort.findById(...)` and fail closed for null/blank/unknown IDs.
  4. `MonitoringRuleApplicationService` may depend on this exported contract and must validate only populated `telemetryPointId`; null remains allowed by the reviewed HMR obligation.
  5. No cross-module database FK from Monitoring to Telemetry is authorized.
  6. Register exactly `dz.sh.hidra.modules.telemetry.application.contract.monitoring` in the existing exported-package registries of `ArchitectureGuardrailTest` and `ForensicRemediationClosureTest`; do not weaken algorithms or add wildcard/transitional exceptions.
- Newly authorized HMR-040 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`
- Newly authorized HMR-040 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-040 remains the current production remediation after this prerequisite is observed. Do not start HMR-041 automatically.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `427fc24637d41f8d3644138761dff3e2a841aa79` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:2284](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2284)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-247"></a>
## HAPI-247 — remediate semantic review TrustedTelemetryReading

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMR-053

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review TrustedTelemetryReading

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Telemetry.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TrustTelemetryReadingUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/TelemetryTrustPolicy.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryTrustEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
- `src/main/resources/db/migration/V20261006_008__hmr_053_trusted_telemetry_gate.sql`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/TelemetryTrustPolicy.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TrustTelemetryReadingUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryTrustEvidenceAdapter.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Telemetry.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TrustTelemetryReadingUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/TelemetryTrustPolicy.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryTrustEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
- `src/main/resources/db/migration/V20261006_008__hmr_053_trusted_telemetry_gate.sql`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-053 — telemetry.TrustedTelemetryReading

- Source review: `HMSR-062`
- Exact commit: `fix(telemetry): remediate semantic review TrustedTelemetryReading`
- Status: **Completed — implemented; exact-head CI pending**
- SCC: —
- Recorded upstream HMS dependencies: telemetry.TelemetryPoint, telemetry.TelemetryReading
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261006_008__hmr_053_trusted_telemetry_gate.sql`
- Owner-contract prerequisite: Telemetry-owned trust evidence port and policy admitted under HPR-P2-008; Topology remains snapshot/reference state with no cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Telemetry.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/telemetry.md`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
  - `src/main/resources/db/migration/V20261006_008__hmr_053_trusted_telemetry_gate.sql`
  - `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TrustTelemetryReadingUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/TelemetryTrustPolicy.java`
  - `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryTrustEvidenceAdapter.java`
  - `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingIntegrityMigrationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=TrustedTelemetryReadingSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce reading/point/assessment consistency.** The trusted reading, source TelemetryReading and justifying TelemetryQualityAssessment must refer to the same reading/point identity.
  2. **Implement the DDD trust gate at an authoritative Telemetry application boundary.** A trusted reading must be created only from a passing quality assessment with an explicitly defined acceptable TrustLevel; a bare repository save is insufficient as the semantic creation contract.
  3. **Enforce ACTIVE TelemetryPoint eligibility before producing a trusted reading.**
  4. **Validate `qualityCodeId` against the Telemetry `QUALITY_CODE` family and its lifecycle/eligibility semantics.**
  5. **Close same-module provenance/reference integrity for populated `unitId` and `ingestionBatchId`, and preserve the active TelemetryPointBinding snapshot coherently at trust time.** Do not introduce cross-module Topology FKs.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingIntegrityMigrationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `f676e278357ac7bcf2bc8bf55830b16cb324bd73` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:88](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L88)
- [doc/model-remediation/RECONCILIATION.md:267](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L267)
- [doc/model-remediation/RECONCILIATION.md:13700](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13700)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15440](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15440)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:87](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L87)
- [docs/data definition/Telemetry.md:1259](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Telemetry.md#L1259)
- [docs/roadmap/model-semantic-remediation.md:209](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L209)
- [docs/roadmap/model-semantic-remediation.md:3039](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3039)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:209 | HMR-053 | HMSR-062 | telemetry | TrustedTelemetryReading | — | telemetry.TelemetryPoint, telemetry.TelemetryReading | fix(telemetry): remediate semantic review TrustedTelemetryReading | Completed — HPR-P2-008; trust operation and provenance guards implemented; CI pending
doc/model-remediation/RECONCILIATION.md:88 | HMR-053 | HMSR-062 | telemetry.TrustedTelemetryReading | COMPLETED — HPR-P2-008 | Telemetry trust application use case derives values/provenance/binding from locked source evidence; PASSED plus MEDIUM/HIGH/CERTIFIED, ACTIVE point and active QUALITY_CODE required; composite assessment/reading identity and optional unit/batch FKs; snapshot preservation; V20261006_008; focused unit and PostgreSQL tests added; CI #604 passed.
doc/model-remediation/RECONCILIATION.md:13700 | HMR-053 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:87 | HMR-053 | HMSR-062 | [telemetry.TrustedTelemetryReading](../domain/SEMANTIC_DECISIONS.md#telemetry-trustedtelemetryreading) | [TrustedTelemetryReadingSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java) | [V20261006_008__hmr_053_trusted_telemetry_gate](../../src/main/resources/db/migration/V20261006_008__hmr_053_trusted_telemetry_gate.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15440 | HMR-053 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-297"></a>
## HAPI-297 — remediate semantic review PlanActualDeviation

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMR-103

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review PlanActualDeviation

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Monitoring.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/PlanActualDeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringEvaluationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/PlanActualDeviationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTrustedReadingReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/resources/db/migration/V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/PlanActualDeviationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/MonitoringTrustedReadingReferenceContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/PlanActualDeviationReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Monitoring.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/PlanActualDeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringEvaluationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/PlanActualDeviationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTrustedReadingReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/resources/db/migration/V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/PlanActualDeviationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/MonitoringTrustedReadingReferenceContractTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-103 — monitoring.PlanActualDeviation

- Source review: `HMSR-120`
- Exact commit: `fix(monitoring): remediate semantic review PlanActualDeviation`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: planning.PlanTarget, telemetry.TelemetryPoint, telemetry.TrustedTelemetryReading
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql` (accepted canonical Batch 19 envelope; original unexecuted registration superseded)
- Owner-contract prerequisite: Existing candidate owner contract(s): planning:src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java; planning:src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java; telemetry:src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java
- Exact write allowlist:
  - `docs/data definition/Monitoring.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/PlanActualDeviationJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_103__hmr_103_monitoring_plan_actual_deviation.sql`
  - `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=PlanActualDeviationSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Fail closed on the mandatory `planTargetId` through a Planning-owned public reference/lookup contract before recording a deviation; do not introduce a cross-module FK.**
  2. **Validate supplied optional `trustedTelemetryReadingId` / `telemetryPointId` through Telemetry-owned contracts when the comparison relies on them as live evidence, preserving optionality and avoiding cross-module FKs.**
  3. **Validate a supplied optional same-module `evaluationId` and preserve Monitoring evaluation/deviation coherence.**

- Batch 19 execution: accepted canonical scope supersedes this historical allowlist. Both live record and direct saves validate mandatory Planning target and fresh optional Telemetry references, reading/point agreement and locked evaluation context. Historical evidence, optionality, severity fallback and the 20-field shape remain. Six focused, one owner-provider and eight PostgreSQL/Spring-JPA methods prepared; runtime commands not executed here because no execution facility is exposed. Final CI pending.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/PlanActualDeviationSemanticPostgresIntegrationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/MonitoringTrustedReadingReferenceContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `74ea302432eff8434466d7162134b99923ce3161` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:138](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L138)
- [doc/model-remediation/RECONCILIATION.md:3935](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L3935)
- [doc/model-remediation/RECONCILIATION.md:13750](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13750)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6042](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6042)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15490](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15490)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:137](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L137)
- [docs/data definition/Monitoring.md:162](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Monitoring.md#L162)
- [docs/roadmap/model-semantic-remediation.md:284](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L284)
- [docs/roadmap/model-semantic-remediation.md:5156](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L5156)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:284 | HMR-103 | HMSR-120 | monitoring | PlanActualDeviation | — | planning.PlanTarget, telemetry.TelemetryPoint, telemetry.TrustedTelemetryReading | fix(monitoring): remediate semantic review PlanActualDeviation | Planned
doc/model-remediation/RECONCILIATION.md:3935 | HMR-103 | V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql | PlanActualDeviationSemanticRemediationTest | fix(monitoring): remediate semantic review PlanActualDeviation
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6042 | HMR-103 | V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql | PlanActualDeviationSemanticRemediationTest | fix(monitoring): remediate semantic review PlanActualDeviation
doc/model-remediation/RECONCILIATION.md:138 | HMR-103 | HMSR-120 | monitoring.PlanActualDeviation | COMPLETED — CI #600 PASSED | Independent semantic commit 74ea302432eff8434466d7162134b99923ce3161; live/direct-save owner/evaluation validation and forward 021 verified by final repaired-head CI.
doc/model-remediation/RECONCILIATION.md:13750 | HMR-103 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:137 | HMR-103 | HMSR-120 | [monitoring.PlanActualDeviation](../domain/SEMANTIC_DECISIONS.md#monitoring-planactualdeviation) | [PlanActualDeviationSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java) | [V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity](../../src/main/resources/db/migration/V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql); [MonitoringPlanTargetReferenceContract](../../src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java); [MonitoringTelemetryPointReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java); [MonitoringTrustedReadingReferenceContract](../../src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTrustedReadingReferenceContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15490 | HMR-103 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-315"></a>
## HAPI-315 — review telemetry TelemetryPoint

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMSR-005

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review telemetry TelemetryPoint

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryUnitJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryUnitJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryUnitJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 18. HMSR-005 — telemetry.TelemetryPoint review

**Decision:** REVISE  
**Review code:** HMSR-005  
**Dependency level:** 0  
**Bounded context:** telemetry  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 5 — `telemetry.TelemetryReading`, `monitoring.MonitoringRule`, `telemetry.TrustedTelemetryReading`, `planning.PlanTarget`, `monitoring.PlanActualDeviation`  
**Transitive dependents:** 9  
**Unresolved/non-subject references:** 3 — all three now semantically resolved to Telemetry-owned non-subject persistence/read models

### 18.1 Semantic role and ordering rationale

`TelemetryPoint` is the canonical Hidra telemetry tag/signal identity. It is the stable point referenced by raw readings, trusted readings, monitoring rules, planning targets, planned-versus-actual deviation logic, external tag mappings, topology bindings, quality assessment and latest-state projections.

It is Level 0 because none of its prerequisites are another one of the 123 HMS subject models. Its configuration prerequisites — device, unit and telemetry catalogs — are Telemetry-owned persistence/read models outside the HMS subject population.

Telemetry owns acquisition/source/device/point/reading/quality/trust semantics. It does not own physical topology, monitoring interpretation, alarms, incidents, workflow approval, audit storage, hydraulic simulation, risk scoring or custody fiscal records.

### 18.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable TelemetryPoint identity and persistence primary key. |
| `deviceId` | `String` | Mandatory | Parent Telemetry-owned device reference. |
| `code` | `String` | Mandatory | Canonical point/tag code; target semantics require uniqueness within the parent device. |
| `nameAr` | `String` | Optional | Arabic point display label. |
| `nameFr` | `String` | Mandatory | French point display label in the current Telemetry DDD and persistence schema. |
| `nameEn` | `String` | Optional | English point display label. |
| `pointTypeId` | `String` | Mandatory | Telemetry catalog reference in catalog family `POINT_TYPE`. |
| `signalTypeId` | `String` | Mandatory | Telemetry catalog reference in catalog family `SIGNAL_TYPE`; governs compatible reading value shape. |
| `unitId` | `String` | Conditionally required | Telemetry unit reference. Optional structurally, but required for numeric engineering measurements unless the point type explicitly exempts it. |
| `defaultAggregationMethodId` | `String` | Optional | Telemetry catalog reference in catalog family `AGGREGATION_METHOD`. |
| `samplingPeriodSeconds` | `Integer` | Optional | Expected source sampling period in seconds. |
| `externalReference` | `String` | Optional / legacy-simple reference | External source/tag reference; rich/historical aliases belong in `TelemetryExternalTagMapping`. |
| `deadbandValue` | `BigDecimal` | Optional | Point-level deadband/noise-filter threshold. |
| `minOperationalValue` | `BigDecimal` | Optional | Engineering/physical minimum used by telemetry validation when applicable. |
| `maxOperationalValue` | `BigDecimal` | Optional | Engineering/physical maximum used by telemetry validation when applicable. |
| `status` | `TelemetryLifecycleStatus` | Mandatory | Technical lifecycle state. Only ACTIVE points are eligible to produce trusted readings. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp. |

The current domain constructor already rejects blank `id`, `deviceId`, `code`, `pointTypeId`, and `signalTypeId`, rejects null `status`, trims String values, and converts blank optional text/references to `null`.

### 18.3 Resolution of the three non-subject references

The HMSR register carried three unresolved/non-subject references for this model. Current repository evidence resolves all three without creating subject-graph edges:

1. `deviceId -> TelemetryDevice`
   - `TelemetryDevice` is Telemetry-owned;
   - HRA mirror classification retains it as a `READ_PERSISTENCE_MODEL`, outside the 123 HMS subjects;
   - HRA-111 installs `fk_hra111_telemetry_009` from `hidra_telemetry_point.device_id` to `hidra_telemetry_device(id)`.

2. `unitId -> TelemetryUnit`
   - the live repository contains `TelemetryUnitJpaEntity` backed by `hidra_telemetry_unit`;
   - the Telemetry DDD defines the cardinality `TelemetryUnit -> TelemetryPoint`;
   - `TelemetryUnit` is retained as a Telemetry `READ_PERSISTENCE_MODEL`, outside the 123 HMS subjects.

3. `defaultAggregationMethodId -> TelemetryCatalogEntry`
   - the Telemetry DDD explicitly identifies catalog family `AGGREGATION_METHOD`;
   - the live catalog persistence model is `TelemetryCatalogEntryJpaEntity` backed by `hidra_telemetry_type_catalog`;
   - `TelemetryCatalogEntry` is a Telemetry `READ_PERSISTENCE_MODEL`, outside the 123 HMS subjects.

The existing `pointTypeId` and `signalTypeId` classifications remain value/catalog dependencies to `TelemetryCatalogEntry`, not subject-model dependencies.

### 18.4 Downstream dependency evidence

The validated HMS graph has five direct dependent subject models:

- `TelemetryReading.pointId -> TelemetryPoint`;
- `TrustedTelemetryReading.pointId -> TelemetryPoint`;
- `MonitoringRule.telemetryPointId -> TelemetryPoint`;
- `PlanTarget.telemetryPointId -> TelemetryPoint`;
- `PlanActualDeviation.telemetryPointId -> TelemetryPoint`.

Other Telemetry persistence/read models such as external tag mappings, bindings, quality assessments and point-state snapshots also reference the point, but they are outside the 123 HMS subject set and therefore do not change the subject graph level.

### 18.5 Persistence and reference integrity

The live domain and JPA shapes agree on all 18 declared components.

The base Telemetry migration makes `device_id`, `code`, `name_fr`, `point_type_id`, `signal_type_id`, `status`, `created_at`, and `updated_at` non-null.

HRA-111 adds same-module FKs for:

- `device_id -> hidra_telemetry_device(id)`;
- `point_type_id -> hidra_telemetry_type_catalog(id)`;
- `signal_type_id -> hidra_telemetry_type_catalog(id)`.

These FKs establish row existence, but they do not by themselves prove the catalog family or active-state semantics required by the DDD.

The current schema has normal indexes on `device_id`, `code`, `point_type_id`, `signal_type_id`, `unit_id`, and `default_aggregation_method_id`. It does not currently enforce the documented unique `(device_id, code)` key, and no current FK was found from optional `unit_id` or `default_aggregation_method_id` to their resolved Telemetry-owned targets.

### 18.6 Required revisions

The current field set and ownership are appropriate, but the implementation does not yet enforce all repository-defined TelemetryPoint semantics strongly enough for an APPROVED target baseline.

1. **Mandatory French point name:** `nameFr` is required by the Telemetry DDD and is `NOT NULL` in JPA/schema, but the domain constructor accepts null/blank and normalizes blank text to `null`.
2. **Point-code uniqueness:** the Telemetry DDD requires point code uniqueness per device and explicitly recommends unique `(device_id, code)`. The live migration currently has separate non-unique indexes only, and the point repository/application service provides no demonstrated uniqueness check.
3. **Catalog-family validation:** `pointTypeId`, `signalTypeId`, and `defaultAggregationMethodId` have specific catalog-family meanings (`POINT_TYPE`, `SIGNAL_TYPE`, `AGGREGATION_METHOD`). Existing generic catalog FKs, where present, prove only that a catalog row exists; the current point registration service does not demonstrate family/active-entry validation.
4. **Unit reference integrity:** when `unitId` is present it semantically targets `TelemetryUnit`, but current point persistence does not demonstrate FK/application validation of that optional reference.
5. **Signal/unit compatibility:** the DDD requires `signalTypeId` to determine compatible numeric/text/boolean reading shape and requires `unitId` for numeric engineering measurements unless explicitly exempted by point type. The current point registration service simply persists the supplied IDs and does not demonstrate this conditional validation.

These rules should be enforced at the appropriate domain/application/database boundary. Repository lookups and catalog-family checks must not be pushed into the domain record constructor.

HMSR-005 does not change production Java, JPA, Flyway, application/API contracts, tests, or database data.

### 18.7 Lifecycle and operational interpretation

The current registration service creates new points in `PLANNED` state. Historical/inactive points remain valid identities for retained telemetry history, while only ACTIVE points should participate in trusted-reading production.

For SONATRACH/TRC operations, a TelemetryPoint represents the canonical digital identity of an acquired signal/tag — for example pressure, flow, temperature, valve state or equipment status — not the physical instrument/equipment asset itself. Physical asset association belongs through TelemetryPointBinding/topology references, preserving the Telemetry/Topology boundary.

### 18.8 Review conclusion

**REVISE.** `TelemetryPoint` has the correct bounded-context ownership, field shape, dependency direction and downstream role, and the three previously unresolved non-subject references are now semantically resolved. However, the target baseline cannot mark it APPROVED while the documented French-name, per-device code uniqueness, catalog-family, optional-reference integrity and signal/unit compatibility rules are not consistently enforced.

HMS reconciliation must retain these correction obligations until an explicitly authorized Telemetry implementation task resolves them or the target Telemetry semantics are explicitly changed.

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

- `d6807162e465017e1870168be50f2c039a6fd5de` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:57](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L57)
- [docs/roadmap/model-semantic-review.md:200](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L200)
- [docs/roadmap/model-semantic-review.md:3539](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L3539)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:200 | HMSR-005 | 0 | telemetry | TelemetryPoint | — | — | 5 | 9 | 3 | REVISE | Completed | docs(model-review): review telemetry TelemetryPoint
doc/domain/SEMANTIC_DECISIONS.md:57 | HMSR-005 | [telemetry.TelemetryPoint](#telemetry-telemetrypoint) | REVISE | HMR-005
~~~~~~~~

<a id="hapi-343"></a>
## HAPI-343 — review telemetry TelemetrySource

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMSR-033

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review telemetry TelemetrySource

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 46. HMSR-033 — telemetry.TelemetrySource review

**Decision:** REVISE  
**Review code:** HMSR-033  
**Dependency level:** 0  
**Bounded context:** telemetry  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 0

### 46.1 Semantic role and ordering rationale

`TelemetrySource` is the Telemetry-owned acquisition-source model representing systems such as SCADA, historians, OPC-UA servers, MQTT brokers, API/import feeds, manual sources, and edge gateways.

It is Level 0 because its taxonomy references resolve to Telemetry-owned catalog rows outside the 123 HMS subject set. Downstream persistence models such as TelemetryDevice and TelemetrySourceEndpoint reference it, but they are not direct HMS subject dependents in this roadmap.

Telemetry DDD explicitly owns acquisition-source metadata while keeping secrets outside telemetry persistence and keeping physical-network ownership in Topology.

### 46.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable source identity and persistence primary key. |
| `code` | `String` | Mandatory and DDD-unique | Stable source code such as a SCADA/historian acquisition source identifier. |
| `nameAr` | `String` | Optional localized label | Arabic source name. |
| `nameFr` | `String` | DDD/persistence-required localized label | French source name. |
| `nameEn` | `String` | Optional localized label | English source name. |
| `sourceTypeId` | `String` | Mandatory controlled reference | Must resolve to an active Telemetry catalog entry in family `SOURCE_TYPE`. |
| `protocolId` | `String` | Mandatory controlled reference | Must resolve to an active Telemetry catalog entry in family `PROTOCOL`. |
| `endpointUri` | `String` | Optional legacy/simple endpoint | Endpoint URI for simple source configuration; must not contain secret material. |
| `externalReference` | `String` | Optional external identifier | SCADA/historian/integration source reference; must not contain secret material. |
| `status` | `TelemetryLifecycleStatus` | Mandatory | Source lifecycle state. Current source-specific DDD vocabulary and shared enum are not fully reconciled. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The current constructor already rejects blank `id`, `code`, `sourceTypeId`, and `protocolId`, requires non-null status, and normalizes textual fields.

### 46.3 Source-type and protocol dependency resolution

Telemetry DDD defines:

```text
sourceTypeId -> TelemetryCatalogEntry where catalogName = SOURCE_TYPE
protocolId   -> TelemetryCatalogEntry where catalogName = PROTOCOL
```

HRA-111 installs database FKs:

```text
hidra_telemetry_source.source_type_id
    -> hidra_telemetry_type_catalog.id
    via fk_hra111_telemetry_022

hidra_telemetry_source.protocol_id
    -> hidra_telemetry_type_catalog.id
    via fk_hra111_telemetry_021
```

so dangling IDs are prevented. These catalog rows are retained Telemetry read/persistence models outside the HMS subject set; no HMS graph edge is added.

However, generic FK existence does not prove that the referenced row belongs to the required catalog family or is active.

### 46.4 Missing catalog-family and active-entry validation

TelemetrySource DDD states explicitly:

```text
sourceTypeId must reference an active SOURCE_TYPE catalog entry.
protocolId must reference an active PROTOCOL catalog entry.
```

The current create command/request accepts raw IDs, and `TelemetrySourceApplicationService.createTelemetrySource()` passes them directly into the aggregate before persistence.

No current Telemetry catalog lookup/validation boundary was found for source creation. Therefore a valid catalog-row ID from the wrong family, or an inactive row, can satisfy the FK while violating source semantics.

### 46.5 Missing source-code uniqueness enforcement

The Telemetry DDD explicitly says:

```text
code must be unique.
```

The Telemetry roadmap's target metadata shape also describes source `code unique`.

The live schema currently has an ordinary index on `hidra_telemetry_source(code)` rather than a unique constraint/index, and the repository port exposes no code-existence lookup used by the create service.

Therefore two TelemetrySource rows can currently carry the same business code.

### 46.6 Required French source label mismatch

The source-specific DDD field table marks `nameFr` as required. JPA and the base migration also declare `name_fr NOT NULL`.

The canonical domain constructor nevertheless treats `nameFr` like the optional labels and normalizes null/blank input to `null`. The active create service does not add a separate guard.

Unlike a generic persistence-only label inference, this obligation is supported directly by the TelemetrySource DDD itself.

### 46.7 Secret-material persistence gap

TelemetrySource DDD explicitly requires:

```text
Secrets must never be stored in endpointUri or externalReference.
```

The active source-creation path accepts both strings and persists them directly. No Telemetry secret-material guard, sanitization policy, or external credential-reference enforcement was found for this write path.

HMSR-033 does not invent a password-detection regex, URI syntax, or vault-reference format. The future correction must fail closed against actual secret material while preserving legitimate sanitized endpoint URIs and external identifiers.

### 46.8 Source lifecycle vocabulary reconciliation

The source-specific DDD lists source lifecycle values:

```text
DRAFT
ACTIVE
INACTIVE
SUSPENDED
RETIRED
```

The shared live `TelemetryLifecycleStatus` enum also contains `PLANNED` and `MAINTENANCE`. Those additional values are supported elsewhere in Telemetry, notably device semantics, but the Telemetry roadmap conceptually distinguishes source/device/point lifecycle enums.

As currently modeled, a TelemetrySource can therefore be persisted in `PLANNED` or `MAINTENANCE` even though the source-specific DDD does not define those states.

HMSR-033 does not choose whether those states should be added to the source DDD or excluded from source state. A separate Telemetry correction must establish one authoritative source lifecycle contract.

### 46.9 ACTIVE-source ingestion rule

The DDD states that only ACTIVE sources can ingest telemetry.

The current repository does not expose the future high-velocity ingestion application contract yet; the extended-capability roadmap keeps that work deferred. Therefore HMSR-033 records this as a boundary rule that must be preserved when ingestion is implemented, rather than inventing a missing ingestion service solely to satisfy this review.

The current create path correctly creates new sources in `DRAFT`, so source creation itself does not violate the ACTIVE-only ingestion rule.

### 46.10 Multilingual and endpoint ownership semantics

The explicit `nameAr/nameFr/nameEn` fields are consistent with Telemetry's multilingual roadmap. Source type and protocol remain catalog-backed user-facing taxonomies, which is also consistent with the roadmap's prohibition on fixed Java enums for those business classifications.

`endpointUri` is documented as a legacy/simple endpoint field. Multi-endpoint configuration belongs to retained `TelemetrySourceEndpoint`, which references TelemetrySource and has its own protocol/credential-reference semantics. HMSR-033 does not remove `endpointUri` merely because the richer endpoint model exists.

### 46.11 Required revisions

Five evidence-backed correction obligations remain:

1. **Enforce unique TelemetrySource code.** The DDD-defined unique source code must be protected at the persistence boundary, preferably with an additive unique constraint/index and aligned application behavior.

2. **Validate source type and protocol catalog semantics.** `sourceTypeId` must resolve to an active `SOURCE_TYPE` entry and `protocolId` to an active `PROTOCOL` entry; generic FK row existence is insufficient.

3. **Require the DDD-mandated French source label.** `nameFr` must not normalize to null at the canonical source creation/domain boundary while the target DDD and schema require it.

4. **Prevent secret material in source endpoint/external-reference fields.** The source write boundary must reject or sanitize actual secrets instead of persisting them in `endpointUri` or `externalReference`; credential material belongs outside telemetry persistence.

5. **Reconcile TelemetrySource lifecycle vocabulary.** Establish whether `PLANNED` and `MAINTENANCE` are valid source states or are only valid for other Telemetry entities, and align source domain/application/persistence/API semantics accordingly.

HMSR-033 does not modify production Java, JPA, Flyway, API/application contracts, tests, telemetry source/catalog data, or provisioned data.

### 46.12 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, TelemetrySource identifies an authoritative acquisition origin such as SCADA, historian, OPC gateway, MQTT broker, API feed, or manual/import channel.

A source must be uniquely identifiable, classified with the correct governed source/protocol taxonomies, and free of embedded credentials. Incorrect taxonomy or duplicated source identity can contaminate provenance; embedded secrets create an operational-security exposure.

Source lifecycle also matters operationally because only sources authorized as ACTIVE should later contribute ingestable telemetry.

### 46.13 Review conclusion

**REVISE.** `TelemetrySource` has a coherent 12-field Telemetry-owned acquisition role, correct Level-0 placement, catalog-backed source/protocol references, multilingual naming shape, conservative DRAFT creation, and database-protected catalog row existence.

The target baseline cannot mark it APPROVED while source-code uniqueness, catalog family/active eligibility, required French label semantics, secret-material exclusion, and source-specific lifecycle vocabulary remain inconsistent or unenforced.

HMS reconciliation must retain these obligations until an explicitly authorized Telemetry correction task resolves them or the governing Telemetry DDD/roadmap is explicitly revised with stronger evidence.

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

- `8ee392c2182947769de23a1e77ad45c8ff58b2ef` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:85](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L85)
- [docs/roadmap/model-semantic-review.md:228](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L228)
- [docs/roadmap/model-semantic-review.md:7674](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L7674)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:228 | HMSR-033 | 0 | telemetry | TelemetrySource | — | — | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review telemetry TelemetrySource
doc/domain/SEMANTIC_DECISIONS.md:85 | HMSR-033 | [telemetry.TelemetrySource](#telemetry-telemetrysource) | REVISE | HMR-030
~~~~~~~~

<a id="hapi-347"></a>
## HAPI-347 — review telemetry TelemetryReading

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMSR-037

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review telemetry TelemetryReading

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/ReadingState.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryExternalTagMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryIngestionBatchJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryExternalTagMappingJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryIngestionBatchJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/ReadingState.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryExternalTagMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryIngestionBatchJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 50. HMSR-037 — telemetry.TelemetryReading review

**Decision:** REVISE  
**Review code:** HMSR-037  
**Dependency level:** 1  
**Bounded context:** telemetry  
**Confirmed upstream subject dependencies:** `telemetry.TelemetryPoint` through `pointId`  
**Confirmed direct HMS subject dependents:** 2 — `alarm.Alarm` through `telemetryReadingId` and `telemetry.TrustedTelemetryReading` through `readingId`  
**Transitive HMS subject dependents:** 6  
**Unresolved/non-subject references:** 2 in the original register; both are now resolved to retained Telemetry read/persistence models outside the HMS subject set

### 50.1 Semantic role and ordering rationale

`TelemetryReading` is the Telemetry-owned raw received-reading record. It preserves the canonical point identity, raw value shape, source/receive timestamps, source quality, ingestion/mapping provenance, processing state, rejection context, duplicate/idempotency evidence, and optional persistence timestamp before a downstream reading becomes trusted.

It is Level 1 because every reading must reference the already reviewed `TelemetryPoint`. Alarm and TrustedTelemetryReading depend directly on the reading identity, so raw-reading semantics must remain stable before those consumers are treated as final.

The Telemetry DDD explicitly distinguishes raw readings from `TrustedTelemetryReading`: operational downstream modules should prefer trusted readings, while the raw record remains acquisition evidence.

### 50.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable raw-reading identity and persistence primary key. |
| `pointId` | `String` | Mandatory | Same-module reference to the canonical TelemetryPoint. |
| `numericValue` | `BigDecimal` | Conditional value shape | Numeric reading value when the point/signal is numeric. |
| `textValue` | `String` | Conditional value shape | Text/state reading value when applicable. |
| `booleanValue` | `Boolean` | Conditional value shape | Boolean reading value when applicable. |
| `qualityCodeId` | `String` | Mandatory controlled reference | Telemetry quality-code catalog identity. |
| `sourceTimestamp` | `Instant` | Mandatory | Timestamp supplied by the telemetry source. |
| `receivedAt` | `Instant` | Mandatory | Timestamp at which Hidra received the reading. |
| `state` | `ReadingState` | Mandatory | Internal processing state: `RECEIVED`, `ACCEPTED`, `REJECTED`, `DUPLICATE`, `QUARANTINED`, or `TRUSTED`. |
| `ingestionBatchId` | `String` | Optional | Traceability reference to the TelemetryIngestionBatch that carried the reading. |
| `correlationId` | `String` | Optional technical reference | Platform/request correlation identity. |
| `rejectionReason` | `String` | Optional | Rejection/processing explanation when applicable. |
| `sourceSequenceNumber` | `String` | Optional | Source-side sequence identifier when available. |
| `externalTagMappingId` | `String` | Optional | Mapping identity used to resolve the external source tag to the canonical point. |
| `rawPayloadHash` | `String` | Optional | Payload hash used as duplicate/idempotency evidence. |
| `createdAt` | `Instant` | Optional by DDD/schema | Persistence timestamp when separated from `receivedAt`. |

The compact constructor already requires nonblank `id`, `pointId`, and `qualityCodeId`; requires non-null `sourceTimestamp`, `receivedAt`, and `state`; and normalizes optional text/reference fields.

### 50.3 TelemetryPoint dependency integrity

The primary HMS dependency is correct:

```text
TelemetryReading.pointId
    -> telemetry.TelemetryPoint
```

Domain construction rejects a blank point ID. JPA/schema require `point_id`, and HRA-111 installs:

```text
fk_hra111_telemetry_017
  FOREIGN KEY (point_id)
  REFERENCES hidra_telemetry_point(id)
  ON DELETE RESTRICT
```

so an authoritative persisted reading cannot reference a missing TelemetryPoint.

### 50.4 Quality-code catalog semantics

The DDD requires a quality code for every reading and defines `QUALITY_CODE` as the Telemetry catalog family for quality vocabulary.

HRA-111 installs:

```text
fk_hra111_telemetry_018
  quality_code_id -> hidra_telemetry_type_catalog(id)
```

which prevents a dangling catalog row. It does not prove that the referenced entry belongs to the `QUALITY_CODE` catalog family.

The existing telemetry query adapter itself resolves quality labels from `catalogName = QUALITY_CODE`, confirming the intended family semantics. No current raw-reading write boundary was found that validates the family before persistence.

HMSR-037 therefore resolves `qualityCodeId` to `TelemetryCatalogEntry (QUALITY_CODE)` and retains a family-validation obligation. Current DDD says the quality code must exist but does not separately require the catalog row to be active, so HMSR-037 does not invent an active-entry rule.

### 50.5 Ingestion-batch reference integrity gap

The DDD defines `ingestionBatchId` as the optional batch reference for a raw reading. The retained persistence model is `TelemetryIngestionBatchJpaEntity` / `hidra_telemetry_ingestion_batch`.

The base reading schema stores and indexes `ingestion_batch_id`, but HRA-111 does not add a foreign key for:

```text
hidra_telemetry_reading.ingestion_batch_id
    -> hidra_telemetry_ingestion_batch.id
```

and no current raw-reading application write service demonstrates a lookup/validation boundary.

Therefore a populated batch traceability ID can currently be dangling.

### 50.6 External-tag-mapping reference integrity gap

`externalTagMappingId` is documented as the mapping used to resolve the reading from an external SCADA/historian/OPC/MQTT/import tag to the canonical TelemetryPoint.

The retained persistence target is `TelemetryExternalTagMappingJpaEntity` / `hidra_telemetry_external_tag_mapping`. The reading table stores and indexes `external_tag_mapping_id`, but HRA-111 does not protect it with a same-module FK and no current application write validator was found.

A populated mapping ID can therefore be missing or unrelated to an existing mapping record. HMSR-037 does not additionally invent a requirement that the mapping's `pointId` must equal the reading's `pointId`, because the current Telemetry DDD does not state that cross-field equality rule explicitly.

### 50.7 Value-shape invariant is not authoritative end-to-end

Telemetry DDD states:

```text
Each reading must have exactly one value shape unless null/state reading is explicitly allowed.
```

and defines the three mutually exclusive value carriers:

```text
numericValue
textValue
booleanValue
```

The record exposes `hasExactlyOneValue()`, and `TelemetryReadingValueValidator` rejects readings for which that method returns false.

However:

- the canonical record constructor itself does not enforce the rule;
- repository search found no active raw-reading ingestion/write use case that invokes `TelemetryReadingValueValidator` before persistence;
- no database CHECK protects the value-shape rule;
- the validator requires exactly one value unconditionally, while the DDD explicitly reserves the possibility of allowed null/state readings.

Therefore the repository contains a useful validator but does not yet have one authoritative, end-to-end value-shape contract. A future ingestion correction must define any permitted null/state cases and enforce the reconciled rule at the authoritative write boundary.

The related TelemetryPoint rule that point signal type determines compatible value shape should be preserved when that ingestion boundary is implemented; HMSR-037 does not invent a hardcoded signal-type mapping absent a finalized ingestion policy.

### 50.8 Timestamp/clock-skew rule deliberately not guessed

The DDD states that `receivedAt` must not precede `sourceTimestamp` beyond an accepted clock-skew policy.

Both timestamps are already mandatory, but no current Telemetry roadmap or implementation evidence defines the accepted skew duration, per-source override, or handling of known clock faults.

HMSR-037 therefore does not invent a duration such as zero seconds, five minutes, or one hour. The future ingestion policy must define the accepted clock-skew contract before a precise timestamp validation can be implemented.

This unresolved threshold is recorded as a policy dependency rather than a fabricated local invariant.

### 50.9 Reading-state representation

`ReadingState` exactly matches the DDD's six raw-reading states. The Telemetry roadmap explicitly classifies `TelemetryReadingState` as an internal technical state for which a Java enum is allowed.

Therefore HMSR-037 does not replace `ReadingState` with a catalog solely because `READING_STATE` also appears among recommended catalog names. The technical-state enum is an accepted implementation choice under the active Telemetry roadmap.

No current rule requires `REJECTED` to have nonblank `rejectionReason`, forbids a reason on other states, or defines a complete state transition matrix. Those rules are not invented.

### 50.10 Append-oriented and duplicate semantics deliberately not over-promoted

Telemetry DDD says raw readings should be append-oriented and business updates should be avoided except state/rejection metadata if mutable state is chosen.

The current repository has no active raw-reading ingestion/update application service, so there is no concrete business update path to classify as defective during HMSR-037. The generic repository `save` method alone does not establish an authorized update lifecycle.

Similarly, `rawPayloadHash` and `sourceSequenceNumber` provide duplicate/idempotency evidence, but the DDD does not define a universal uniqueness key across sources, points, batches, or time. HMSR-037 does not invent a unique hash/sequence constraint.

### 50.11 Downstream semantics

Two HMS subject models depend directly on TelemetryReading:

```text
alarm.Alarm.telemetryReadingId
telemetry.TrustedTelemetryReading.readingId
```

The Alarm reference is cross-module evidence linkage; it does not transfer reading ownership to Alarm. TrustedTelemetryReading is the Telemetry-owned downstream contract for readings that pass the required trust/quality process.

Retained `TelemetryQualityAssessment` persistence also references raw readings and is HRA-111 protected through `fk_hra111_telemetry_014`, reinforcing TelemetryReading as stable acquisition evidence.

### 50.12 Required revisions

Four evidence-backed correction obligations remain:

1. **Protect populated ingestion-batch references.** When `ingestionBatchId` is supplied, it must resolve to the Telemetry-owned ingestion batch rather than allowing a dangling traceability ID. An additive nullable same-module FK, application validation, or equivalent fail-closed mechanism may be used.

2. **Protect populated external-tag-mapping references.** When `externalTagMappingId` is supplied, it must resolve to the Telemetry-owned external tag mapping rather than allowing a dangling mapping-evidence ID.

3. **Enforce quality-code catalog-family semantics.** `qualityCodeId` must resolve specifically to the `QUALITY_CODE` Telemetry catalog family; the existing generic catalog FK only proves row existence.

4. **Establish one authoritative raw-reading value-shape policy.** Reconcile the DDD's exactly-one rule and its explicit null/state exception, then enforce that contract consistently at the raw-reading creation/ingestion boundary rather than leaving `TelemetryReadingValueValidator` unused and stricter than the documented exception model.

The accepted clock-skew duration remains a separate Telemetry policy prerequisite and must not be guessed by this semantic review.

HMSR-037 does not modify production Java, JPA, Flyway, API/application contracts, tests, telemetry readings/catalogs, or provisioned data.

### 50.13 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, TelemetryReading is acquisition evidence from SCADA, historians, OPC, MQTT, gateways, imports, or other telemetry sources. It must remain traceable to a canonical point, source-quality vocabulary, ingestion context, and any external tag mapping used to interpret the source signal.

Dangling batch/mapping references or ambiguous multi-value rows weaken forensic traceability and can contaminate downstream alarms, trusted-reading qualification, analytics, leak detection, and operational reporting.

Raw readings remain evidence; operational consumers should rely on trusted readings when a trust-qualified contract is required.

### 50.14 Review conclusion

**REVISE.** `TelemetryReading` has a coherent 16-field raw-acquisition role, correct Level-1 dependency on TelemetryPoint, exact technical reading-state vocabulary, mandatory source/receive timestamps, and database-protected point and quality-code row existence.

The target baseline cannot mark it APPROVED while optional batch and external-tag mapping references can dangle, quality-code catalog-family semantics are not fail-closed, and the documented value-shape rule is not authoritative end-to-end.

HMS reconciliation must retain these obligations until an explicitly authorized Telemetry correction task resolves them or the governing Telemetry DDD/ingestion policy is explicitly revised with stronger evidence.

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

- `1be9099614d62cc7a277372bf7e465e27f6409d7` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:89](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L89)
- [docs/roadmap/model-semantic-review.md:232](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L232)
- [docs/roadmap/model-semantic-review.md:8314](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L8314)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:232 | HMSR-037 | 1 | telemetry | TelemetryReading | — | telemetry.TelemetryPoint | 2 | 6 | 2 | REVISE | Completed | docs(model-review): review telemetry TelemetryReading
doc/domain/SEMANTIC_DECISIONS.md:89 | HMSR-037 | [telemetry.TelemetryReading](#telemetry-telemetryreading) | REVISE | HMR-033
~~~~~~~~

<a id="hapi-358"></a>
## HAPI-358 — review monitoring MonitoringRule

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMSR-048

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review monitoring MonitoringRule

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringLifecycleStatus.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 61. HMSR-048 — monitoring.MonitoringRule review

**Decision:** REVISE  
**Review code:** HMSR-048  
**Dependency level:** 1  
**Bounded context:** monitoring  
**Confirmed upstream subject dependencies:** `telemetry.TelemetryPoint` through optional `telemetryPointId`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** none requiring new HMS graph edges; Topology asset targeting remains typed/polymorphic and frequency/planning-target fields remain value/classification dependencies

### 61.1 Semantic role and ordering rationale

`MonitoringRule` is the Monitoring-owned rule definition for operational evaluation.

Monitoring owns deviation/evaluation logic. Telemetry owns measured facts, Planning owns expected values, and Topology owns physical assets. Monitoring therefore stores references/snapshots to those contexts rather than owning their source records.

It is Level 1 because optional `telemetryPointId` is an unambiguous cross-module reference to the already-reviewed HMS subject `TelemetryPoint`.

### 61.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable Monitoring rule identity. |
| `code` | `String` | Mandatory language-neutral rule code. |
| `nameAr` | `String` | Optional Arabic display name. |
| `nameFr` | `String` | French display name; required by persistence, but current DDD does not independently make multilingual completeness a domain invariant. |
| `nameEn` | `String` | Optional English display name. |
| `ruleType` | `MonitoringRuleType` | Mandatory Monitoring rule classification. |
| `evaluationFrequencyId` | `String` | Optional Monitoring frequency/classification reference. |
| `topologyAssetType` | `String` | Optional discriminator for a polymorphic Topology asset reference. |
| `topologyAssetId` | `String` | Optional typed Topology asset identity. |
| `topologyAssetCode` | `String` | Optional Topology asset code snapshot/readability value. |
| `telemetryPointId` | `String` | Optional cross-module stable reference to TelemetryPoint. |
| `planningTargetTypeId` | `String` | Optional planning-target classification/value reference. |
| `expression` | `String` | Optional Monitoring evaluation expression. |
| `status` | `MonitoringLifecycleStatus` | Mandatory Monitoring rule lifecycle state. |
| `createdByActorId` | `String` | Optional Identity actor reference. |
| `createdAt` | `Instant` | Persistence-required creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required update timestamp. |

The canonical constructor requires nonblank ID and code and non-null rule type/status, while normalizing optional strings.

### 61.3 Telemetry boundary and reference-integrity gap

The Monitoring DDD states:

```text
Telemetry owns measured facts.
Monitoring stores references and snapshots, not telemetry source records.
```

The semantic graph resolves:

```text
MonitoringRule.telemetryPointId
    -> telemetry.TelemetryPoint
```

The field is optional, so HMSR-048 does not make telemetry binding mandatory for every rule.

However, `MonitoringRuleApplicationService.createMonitoringRule()` accepts `command.telemetryPointId()` and passes it directly into the canonical model and repository without resolving the Telemetry-owned target.

The Monitoring schema intentionally has no cross-module FK on `telemetry_point_id`.

Therefore, when a telemetry point is supplied, the authoritative create path can persist a dangling TelemetryPoint identity.

A cross-module database FK would violate bounded-context ownership. The correction belongs at a Telemetry-owned exported application/lookup contract or equivalent fail-closed boundary.

### 61.4 Topology asset semantics

`topologyAssetType + topologyAssetId` form an optional typed/polymorphic reference to a Topology-owned asset, with `topologyAssetCode` as a snapshot/readability value.

No single concrete HMS model can be inferred from `topologyAssetId` alone, so HMSR-048 does not manufacture a graph edge or database FK.

Current Monitoring DDD does not explicitly require every populated Topology asset reference to be resolved during MonitoringRule creation, nor does it define supported asset types for this model. HMSR-048 therefore does not create a separate production obligation without stronger owner-contract evidence.

### 61.5 Evaluation frequency and planning-target semantics

`evaluationFrequencyId` and `planningTargetTypeId` are optional.

The reviewed Monitoring DDD does not define exact catalog families, mandatory row targets, or cross-module subject identities for these fields.

They remain value/classification dependencies rather than invented HMS graph edges.

No production correction is recorded merely because the base schema indexes them without FKs.

### 61.6 Rule type, expression and lifecycle

The application defaults a missing command rule type to `MonitoringRuleType.PLAN_COMPARISON` and initializes new rules as `MonitoringLifecycleStatus.DRAFT`.

The domain itself requires non-null rule type/status.

Current Monitoring DDD does not define:

- that every rule type requires an expression;
- expression grammar or validation language;
- rule-type-specific mandatory telemetry/planning/topology bindings;
- a complete lifecycle transition matrix;
- activation preconditions.

HMSR-048 does not invent those rules.

### 61.7 Multilingual and code semantics

The canonical domain permits Arabic/French/English names to be optional, while final persistence requires `name_fr`.

The repository invariant-classification architecture explicitly keeps generic human-readable text as persistence-only unless independent domain evidence proves requiredness. The Monitoring DDD does not state that French or all three translations are mandatory for every rule.

HMSR-048 therefore does not turn `nameFr` into a new domain correction obligation solely because JPA marks it non-null.

Likewise, current Monitoring evidence does not establish global rule-code uniqueness; the schema provides an ordinary code index rather than a unique constraint. No uniqueness rule is invented.

### 61.8 Actor and audit semantics

`createdByActorId` is an optional Identity actor reference.

Monitoring does not own Identity, so no cross-module FK is appropriate. Current evidence does not require a creator actor for every rule or define actor lifecycle eligibility.

`createdAt` and `updatedAt` remain application/persistence audit metadata and are populated by the active create service.

### 61.9 Domain/JPA/Flyway/application alignment

Domain and JPA agree on all 17 components.

The final schema requires:

```text
id
code
name_fr
rule_type
status
created_at
updated_at
```

while keeping all operational-reference fields optional.

The domain correctly enforces intrinsic required ID/code/enum state. The persistence-only French-name requiredness remains deliberately outside intrinsic domain enforcement under the repository's invariant classification.

The only evidence-backed semantic-integrity defect found in the authoritative write path is the unvalidated populated TelemetryPoint reference.

### 61.10 Required revision

One evidence-backed production correction obligation remains:

1. **Validate populated MonitoringRule telemetryPointId through the Telemetry owner boundary.** When `telemetryPointId` is supplied, Monitoring must fail closed unless the referenced TelemetryPoint exists under the Telemetry bounded context. Do not add a cross-module database FK.

HMSR-048 does not modify production Java, JPA, Flyway, application/API contracts, tests, monitoring rules, telemetry points, or provisioned data.

### 61.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, MonitoringRule can define how operational pipeline conditions are evaluated against telemetry, planning expectations and asset context.

A rule that claims to monitor a TelemetryPoint must reference a real point; otherwise evaluation, traceability and operator confidence can be compromised even if the rule record itself is structurally valid.

At the same time, Monitoring must not become owner of Telemetry or Topology master data. The appropriate architecture is owner-controlled validation with stable references/snapshots.

### 61.12 Review conclusion

**REVISE.** `MonitoringRule` has a coherent 17-field Monitoring-owned rule model, correct separation from Telemetry/Planning/Topology ownership, valid lifecycle initialization, conservative polymorphic Topology handling, and correct Level-1 dependency on TelemetryPoint.

The target baseline cannot mark it APPROVED while a populated `telemetryPointId` can be persisted without demonstrated validation against the Telemetry-owned authoritative target.

HMS reconciliation must retain this TelemetryPoint reference-integrity obligation until an explicitly authorized Monitoring/Telemetry production correction resolves it or stronger repository evidence changes the reference semantics.

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

- `0e781b2f4a3828e6d9ec1b739d492bce74cff30c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:100](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L100)
- [docs/roadmap/model-semantic-review.md:243](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L243)
- [docs/roadmap/model-semantic-review.md:10222](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L10222)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:243 | HMSR-048 | 1 | monitoring | MonitoringRule | — | telemetry.TelemetryPoint | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review monitoring MonitoringRule
doc/domain/SEMANTIC_DECISIONS.md:100 | HMSR-048 | [monitoring.MonitoringRule](#monitoring-monitoringrule) | REVISE | HMR-040
~~~~~~~~

<a id="hapi-372"></a>
## HAPI-372 — review telemetry TrustedTelemetryReading

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMSR-062

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review telemetry TrustedTelemetryReading

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/AssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/AssessmentStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/AssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 75. HMSR-062 — telemetry.TrustedTelemetryReading review

**Decision:** REVISE  
**Review code:** HMSR-062  
**Dependency level:** 2  
**Bounded context:** telemetry  
**Confirmed upstream subject dependencies:** `telemetry.TelemetryPoint`, `telemetry.TelemetryReading`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 1

### 75.1 Semantic role

`TrustedTelemetryReading` is Telemetry's downstream-facing trustworthy-reading contract. It is not another raw sample: it represents a TelemetryReading that has passed Telemetry-owned quality/trust processing and is safe for monitoring, planning comparison, flow calculation, leak detection, risk, analytics and reporting.

Telemetry owns the trust decision, final quality code, effective unit and the binding snapshots copied at trust time. Topology remains owner of physical asset state.

### 75.2 Raw reading, point and assessment integrity

The trusted reading requires `readingId`, `pointId` and `qualityAssessmentId`.

HRA-111 protects row existence with:

```text
hidra_telemetry_trusted_reading.reading_id -> hidra_telemetry_reading.id
hidra_telemetry_trusted_reading.point_id -> hidra_telemetry_point.id
hidra_telemetry_trusted_reading.quality_assessment_id -> hidra_telemetry_quality_assessment.id
```

TelemetryQualityAssessment itself stores both `readingId` and `pointId`, and those relationships are separately HRA-111 protected.

Independent FK existence is not enough to establish semantic consistency. A trusted row must not combine one raw reading, another point, and an unrelated quality assessment.

The authoritative trust operation must therefore prove that:

```text
trusted.readingId == assessment.readingId
trusted.pointId == assessment.pointId
trusted.pointId == sourceReading.pointId
```

before persistence.

### 75.3 Passing assessment and acceptable trust

The Telemetry DDD explicitly states:

```text
TrustedTelemetryReading requires a passing assessment with acceptable trust level.
```

`AssessmentStatus` includes `PASSED`, `FAILED`, `WARNING`, `DUPLICATE`, `QUARANTINED` and `MANUAL_REVIEW`. `TrustLevel` includes `UNTRUSTED`, `LOW`, `MEDIUM`, `HIGH` and `CERTIFIED`.

The current model requires a non-null trustLevel and assessment ID, but the repository exposes only a generic `TrustedTelemetryReadingRepositoryPort.save(...)`; no live application use case was found that resolves the assessment and enforces the DDD trust gate before creating a trusted reading.

The target write path must therefore require a passing assessment and apply an explicit Telemetry-owned acceptable-trust policy. HMSR-062 does not invent which exact TrustLevel values are acceptable beyond the DDD's current examples and wording.

### 75.4 Active point eligibility

Telemetry DDD validation rules state:

```text
Only ACTIVE points can produce trusted readings.
```

`TelemetryPoint` carries `TelemetryLifecycleStatus`, whose values include `ACTIVE`, `INACTIVE`, `SUSPENDED`, `MAINTENANCE` and `RETIRED`.

The current repository-only save seam does not enforce point lifecycle eligibility.

The authoritative trust operation must resolve the point and fail closed unless it is eligible under the DDD's ACTIVE rule.

### 75.5 Final quality-code semantics

`qualityCodeId` is mandatory. HRA-111 protects row existence against `hidra_telemetry_type_catalog`.

Telemetry DDD defines `QUALITY_CODE` as the controlled family for reading quality. Therefore arbitrary Telemetry catalog-row existence is insufficient: the trusted reading's final quality code must belong to the `QUALITY_CODE` family and satisfy the applicable active/deprecated/inactive lifecycle policy.

HMSR-062 does not invent a new catalog family or duplicate quality taxonomy.

### 75.6 Effective unit and ingestion-batch provenance

The DDD defines:

```text
unitId           = optional effective Telemetry unit
ingestionBatchId = optional ingestion batch reference
```

Both targets are Telemetry-owned. Current trusted-reading persistence indexes these scalar fields but HRA-111 does not add trusted-reading FKs for either one.

Because these are same-module references rather than cross-module snapshots, populated IDs should fail closed against their Telemetry-owned targets. HMS reconciliation should determine the appropriate same-module FK and/or application-level protection without weakening existing historical semantics.

HMSR-062 does not make either field mandatory.

### 75.7 Topology binding snapshot

The DDD requires:

```text
Trusted reading must preserve the topology binding snapshot used at trust time.
Trusted readings should snapshot active binding at trust time.
```

The trusted model carries `topologyAssetTypeCode`, `topologyAssetId`, `topologyAssetCode` and optional `topologySnapshotId`.

These fields are snapshot/reference state, not ownership of Topology. No cross-module database FK should be introduced.

When an active TelemetryPointBinding applies at trust time, the authoritative trust operation must populate/preserve the binding snapshot coherently from that Telemetry-owned binding rather than accepting an unrelated external tuple.

Current evidence does not prove that every point necessarily has a topology binding, so HMSR-062 does not make the topology snapshot universally mandatory.

### 75.8 Value shape

The model exposes `hasExactlyOneValue()` across numeric, text and boolean representations. Telemetry's general reading-validation rules require exactly one value shape unless a null/state reading is explicitly allowed.

However, the TrustedTelemetryReading-specific DDD section does not independently state that all trusted rows must contain exactly one of those three value fields, and the model currently treats the helper as a query rather than a constructor guard.

HMSR-062 therefore does not promote the helper into a new universal invariant without stronger trusted-reading-specific evidence.

### 75.9 Source timestamps and trust time

`sourceTimestamp` and `trustedAt` are mandatory and already guarded for presence.

Current DDD evidence does not explicitly classify `sourceTimestamp <= trustedAt` as a required invariant. HMSR-062 therefore does not invent that ordering rule.

### 75.10 Required revisions

Five evidence-backed obligations remain:

1. **Enforce reading/point/assessment consistency.** The trusted reading, source TelemetryReading and justifying TelemetryQualityAssessment must refer to the same reading/point identity.

2. **Implement the DDD trust gate at an authoritative Telemetry application boundary.** A trusted reading must be created only from a passing quality assessment with an explicitly defined acceptable TrustLevel; a bare repository save is insufficient as the semantic creation contract.

3. **Enforce ACTIVE TelemetryPoint eligibility before producing a trusted reading.**

4. **Validate `qualityCodeId` against the Telemetry `QUALITY_CODE` family and its lifecycle/eligibility semantics.**

5. **Close same-module provenance/reference integrity for populated `unitId` and `ingestionBatchId`, and preserve the active TelemetryPointBinding snapshot coherently at trust time.** Do not introduce cross-module Topology FKs.

HMSR-062 does not modify production Java, JPA, Flyway, API/application contracts, tests, Telemetry catalogs, readings/assessments, Topology data, or provisioned data.

### 75.11 Review conclusion

**REVISE.** `TrustedTelemetryReading` is a coherent 17-field Telemetry-owned downstream contract. Mandatory raw-reading, point, quality-assessment and final-quality-code rows are already protected against dangling same-module IDs, and the model correctly preserves Topology as snapshot/reference state rather than owned relational state.

The target baseline cannot mark it APPROVED while independent FKs can still compose inconsistent reading/point/assessment tuples, the DDD's passing-assessment/acceptable-trust and ACTIVE-point gates have no authoritative application implementation, final quality-code family membership is not enforced, and optional same-module unit/batch provenance remains unprotected when populated.

HMS reconciliation must retain these obligations until explicitly authorized Telemetry production corrections resolve them or stronger repository evidence changes the governing DDD.

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

- `f9f53e2c5fabb618ea9a554ffc9af796eb9985d1` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:114](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L114)
- [docs/roadmap/model-semantic-review.md:257](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L257)
- [docs/roadmap/model-semantic-review.md:12356](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L12356)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:257 | HMSR-062 | 2 | telemetry | TrustedTelemetryReading | — | telemetry.TelemetryPoint, telemetry.TelemetryReading | 1 | 1 | 3 | REVISE | Completed | docs(model-review): review telemetry TrustedTelemetryReading
doc/domain/SEMANTIC_DECISIONS.md:114 | HMSR-062 | [telemetry.TrustedTelemetryReading](#telemetry-trustedtelemetryreading) | REVISE | HMR-053
~~~~~~~~

<a id="hapi-430"></a>
## HAPI-430 — review monitoring PlanActualDeviation

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HMSR-120

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review monitoring PlanActualDeviation

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 133. HMSR-120 — monitoring.PlanActualDeviation review

**Decision:** REVISE  
**Review code:** HMSR-120  
**Dependency level:** 7  
**Bounded context:** monitoring  
**Confirmed upstream subject dependencies:** mandatory Planning-owned `planning.PlanTarget`; optional Telemetry-owned `telemetry.TelemetryPoint` and `telemetry.TrustedTelemetryReading`; optional same-module Monitoring evaluation context plus neutral Topology/unit references  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 4

### 133.1 Semantic role

`PlanActualDeviation` is Monitoring's actual-vs-expected comparison record. Planning owns expected values, Telemetry owns measured/trusted facts, and Monitoring owns the resulting deviation intelligence.

The Monitoring DDD explicitly requires Monitoring to store references/snapshots rather than becoming owner of Planning or Telemetry source records.

### 133.2 Field and persistence alignment

The live domain record, JPA entity, persistence mapper, command/service path, and base Monitoring migration align on the 20-field model shape.

`DeviationStatus` (`OPEN`, `ACKNOWLEDGED`, `RESOLVED`, `IGNORED`, `SUPERSEDED`) and `DeviationSeverity` (`INFO`, `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`) are consistent with stable Monitoring lifecycle/classification enums.

The domain already fail-fast validates `id`, mandatory `planTargetId`, `topologyAssetId`, severity, status, and detection time.

### 133.3 Mandatory Planning target boundary

`planTargetId` is required by the domain model and represents the Planning-owned expected target being compared.

`DeviationApplicationService.recordDeviation(...)` currently copies the supplied ID directly into the deviation without proving that the Planning target exists or is usable for this comparison.

Because Planning owns `PlanTarget`, Monitoring must not add a cross-module database FK or directly read Planning persistence. Future production reconciliation must fail closed through a Planning-owned public lookup/reference contract before recording a deviation against a mandatory target.

### 133.4 Telemetry evidence boundary

`trustedTelemetryReadingId` and `telemetryPointId` are optional Telemetry-owned references. Their optionality is preserved by the current model and schema.

The authoritative record path currently accepts supplied IDs without owner-controlled validation.

Future production reconciliation must validate supplied Telemetry references through Telemetry-owned public contracts when the deviation use case relies on them as live evidence. No Monitoring-to-Telemetry database FK is authorized.

HMSR-120 does not make either Telemetry reference universally mandatory because the current DDD does not establish that requirement.

### 133.5 Optional Monitoring evaluation context

`evaluationId` is an optional same-module Monitoring reference linking the deviation to an evaluation execution.

The current application path does not fail closed when a non-null evaluation ID is unresolved. Future Monitoring-owned write paths should validate supplied evaluation IDs and preserve coherence between the evaluation and the deviation context.

HMSR-120 does not make `evaluationId` mandatory because both the live model and schema define it as optional.

### 133.6 Topology and unit references

`topologyAssetType`, `topologyAssetId`, `topologyAssetCode`, and `unitId` are neutral reference/snapshot fields. Topology and unit ownership remain outside Monitoring.

`topologyAssetId` is already domain-required. Where a write use case depends on current asset/unit validity, checks must use owner-controlled contracts rather than cross-module database FKs.

The current Monitoring DDD does not define stronger universal invariants for `topologyAssetType`, `topologyAssetCode`, or `unitId`, so HMSR-120 does not invent them from persistence shape alone.

### 133.7 Numeric comparison semantics

The model carries `actualValue`, `expectedValue`, `differenceValue`, and `differencePercent`, and the application may derive severity from `differencePercent` when severity is omitted.

The current Monitoring DDD does not specify a mandatory non-null matrix, arithmetic recomputation rule, zero-denominator policy, or rounding contract for these values. HMSR-120 therefore does not invent such numerical rules during semantic review.

### 133.8 Required revisions

Three evidence-backed production obligations remain:

1. **Fail closed on the mandatory `planTargetId` through a Planning-owned public reference/lookup contract before recording a deviation; do not introduce a cross-module FK.**
2. **Validate supplied optional `trustedTelemetryReadingId` / `telemetryPointId` through Telemetry-owned contracts when the comparison relies on them as live evidence, preserving optionality and avoiding cross-module FKs.**
3. **Validate a supplied optional same-module `evaluationId` and preserve Monitoring evaluation/deviation coherence.**

HMSR-120 does not modify production Java, JPA, Flyway, API/application contracts, tests, Monitoring/Planning/Telemetry/Topology data, or provisioned data.

### 133.9 Review conclusion

**REVISE.** The 20-field model shape, Monitoring ownership, status/severity enums, Planning/Telemetry reference strategy, and actual-vs-expected responsibility are structurally appropriate.

The target baseline cannot be marked APPROVED while the authoritative record path can persist a deviation against an unresolved mandatory Planning target and can accept unresolved supplied Monitoring/Telemetry evidence references.

HMS reconciliation must retain these obligations until explicitly authorized Monitoring production work resolves them or stronger repository evidence changes the governing contract.

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

- `79f87ffcee847a29a5aca6b36f6cbebede72652e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:172](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L172)
- [docs/roadmap/model-semantic-review.md:315](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L315)
- [docs/roadmap/model-semantic-review.md:19955](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L19955)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:315 | HMSR-120 | 7 | monitoring | PlanActualDeviation | — | planning.PlanTarget, telemetry.TelemetryPoint, telemetry.TrustedTelemetryReading | 0 | 0 | 4 | REVISE | Completed | docs(model-review): review monitoring PlanActualDeviation
doc/domain/SEMANTIC_DECISIONS.md:172 | HMSR-120 | [monitoring.PlanActualDeviation](#monitoring-planactualdeviation) | REVISE | HMR-103
~~~~~~~~

<a id="hapi-494"></a>
## HAPI-494 — establish capacity baseline

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P3-001

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Define approved telemetry throughput, burst, retention, latency, backlog and replay requirements and benchmark current PostgreSQL/JPA.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Define approved telemetry throughput, burst, retention, latency, backlog and replay requirements and benchmark current PostgreSQL/JPA..

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

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

- Existing gate retained; no reopening/selection; future scope needs owner authorization
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

- [doc/model-remediation/RECONCILIATION.md:13812](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13812)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6141](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6141)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:933](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L933)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15552](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15552)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:933 | HPR-P3-001 | DEFERRED | Telemetry / Performance | Doc/Infra | Define approved telemetry throughput, burst, retention, latency, backlog and replay requirements and benchmark current PostgreSQL/JPA. | perf(telemetry): establish capacity baseline | P2 closed + approved requirements
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15552 | HPR-P3-001 | DEFERRED (existing phase decision) | INDEPENDENT_UNCHANGED | Telemetry / Performance; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6141 | HPR-P3-001 | DEFERRED | Original P3 objective/message preserved; no foundation dependency
doc/model-remediation/RECONCILIATION.md:13812 | HPR-P3-001 | DEFERRED (existing phase decision) | INDEPENDENT_UNCHANGED | Telemetry / Performance; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-495"></a>
## HAPI-495 — introduce timescaledb persistence

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P3-002

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-494

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Adopt TimescaleDB only if HPR-P3-001 evidence justifies it; provision extension and convert only approved high-volume tables to hypertables.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Adopt TimescaleDB only if HPR-P3-001 evidence justifies it; provision extension and convert only approved high-volume tables to hypertables..

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

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

- Existing gate retained; no reopening/selection; future scope needs owner authorization
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-494. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13813](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13813)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6142](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6142)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:934](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L934)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15553](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15553)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:934 | HPR-P3-002 | DEFERRED | Telemetry / TimescaleDB | Infra | Adopt TimescaleDB only if HPR-P3-001 evidence justifies it; provision extension and convert only approved high-volume tables to hypertables. | feat(telemetry): introduce timescaledb persistence | HPR-P3-001 decision
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15553 | HPR-P3-002 | DEFERRED (existing phase decision) | INDEPENDENT_UNCHANGED | Telemetry / TimescaleDB; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6142 | HPR-P3-002 | DEFERRED | Original P3 objective/message preserved; no foundation dependency
doc/model-remediation/RECONCILIATION.md:13813 | HPR-P3-002 | DEFERRED (existing phase decision) | INDEPENDENT_UNCHANGED | Telemetry / TimescaleDB; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-496"></a>
## HAPI-496 — add timescale lifecycle policies

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P3-003

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-495

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add approved compression, retention and continuous aggregates only from verified workload/retention requirements.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Add approved compression, retention and continuous aggregates only from verified workload/retention requirements..

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

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

- Existing gate retained; no reopening/selection; future scope needs owner authorization
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-495. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13814](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13814)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6143](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6143)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:935](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L935)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15554](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15554)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:935 | HPR-P3-003 | DEFERRED | Telemetry / TimescaleDB | Infra | Add approved compression, retention and continuous aggregates only from verified workload/retention requirements. | feat(telemetry): add timescale lifecycle policies | HPR-P3-002
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15554 | HPR-P3-003 | DEFERRED (existing phase decision) | INDEPENDENT_UNCHANGED | Telemetry / TimescaleDB; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6143 | HPR-P3-003 | DEFERRED | Original P3 objective/message preserved; no foundation dependency
doc/model-remediation/RECONCILIATION.md:13814 | HPR-P3-003 | DEFERRED (existing phase decision) | INDEPENDENT_UNCHANGED | Telemetry / TimescaleDB; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-497"></a>
## HAPI-497 — add high throughput ingestion adapter

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P3-004

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-494

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Replace per-entity JPA ingestion only where benchmarks prove it inadequate; retain new adapter behind Telemetry ports and verify burst/replay/failure behavior.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Replace per-entity JPA ingestion only where benchmarks prove it inadequate; retain new adapter behind Telemetry ports and verify burst/replay/failure behavior..

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

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

- Existing gate retained; no reopening/selection; future scope needs owner authorization
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-494. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13815](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13815)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6144](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6144)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:936](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L936)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15555](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15555)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:936 | HPR-P3-004 | DEFERRED | Telemetry | Code/Test | Replace per-entity JPA ingestion only where benchmarks prove it inadequate; retain new adapter behind Telemetry ports and verify burst/replay/failure behavior. | perf(telemetry): add high throughput ingestion adapter | HPR-P3-001..003
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15555 | HPR-P3-004 | DEFERRED (existing phase decision) | INDEPENDENT_UNCHANGED | Telemetry; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/ULTIMATE_ROADMAP.md:6144 | HPR-P3-004 | DEFERRED | Original P3 objective/message preserved; no foundation dependency
doc/model-remediation/RECONCILIATION.md:13815 | HPR-P3-004 | DEFERRED (existing phase decision) | INDEPENDENT_UNCHANGED | Telemetry; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-510"></a>
## HAPI-510 — synchronize trusted network measurements

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P25-004

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-503, HAPI-504

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement the approved read-only acquisition adapter and coherent timestamp/unit/asset binding; verify replay, duplicates, late/missing readings, disconnect and recovery while retaining raw provenance and trust rules.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Implement the approved read-only acquisition adapter and coherent timestamp/unit/asset binding; verify replay, duplicates, late/missing readings, disconnect and recovery while retaining raw provenance and trust rules..

### 4. Functional Requirements

Original independent acceptance; design is not implementation; F1/F3 actual cases

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

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

- Original independent acceptance; design is not implementation; F1/F3 actual cases
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-503, HAPI-504. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13650](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13650)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6088](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6088)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:855](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L855)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15390](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15390)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:855 | HPR-P25-004 | PENDING | Integration / Telemetry | Code/Test/Config | Implement the approved read-only acquisition adapter and coherent timestamp/unit/asset binding; verify replay, duplicates, late/missing readings, disconnect and recovery while retaining raw provenance and trust rules. | feat(telemetry): synchronize trusted network measurements | HPR-P25-001 approved source/access/latency contract; HPR-P25-002
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15390 | HPR-P25-004 | PENDING | FOUNDATION_ACTIVE | Integration / Telemetry | 003/004: specific independent owner contracts, not all 002; 018: amendment docs CI + owner selection; 019/020: 018 | Original independent visualization/measurement or engineering provisioning objective | Any solver use remains outside this independent scope | Original independent acceptance; design is not implementation; F1/F3 actual cases | NOT SELECTED | Own bounded preflight/implementation after prerequisites
doc/roadmap/ULTIMATE_ROADMAP.md:6088 | HPR-P25-004 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13650 | HPR-P25-004 | PENDING | FOUNDATION_ACTIVE | Integration / Telemetry | 003/004: specific independent owner contracts, not all 002; 018: amendment docs CI + owner selection; 019/020: 018 | Original independent visualization/measurement or engineering provisioning objective | Any solver use remains outside this independent scope | Original independent acceptance; design is not implementation; F1/F3 actual cases | NOT SELECTED | Own bounded preflight/implementation after prerequisites
~~~~~~~~

<a id="hapi-512"></a>
## HAPI-512 — detect network issues from trusted state

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P25-006

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-511

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement the approved issue catalogue and correlated detection across the connected network; distinguish sensor/data faults, suspected process faults and safety/security signals, with severity, confidence, supported coverage and evidence.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringQueryController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/SpringMonitoringController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Implement the approved issue catalogue and correlated detection across the connected network; distinguish sensor/data faults, suspected process faults and safety/security signals, with severity, confidence, supported coverage and evidence..

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringQueryController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/SpringMonitoringController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`

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

Canonical prerequisites: HAPI-511. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13652](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13652)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6090](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6090)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:857](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L857)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15392](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15392)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:857 | HPR-P25-006 | PENDING | Monitoring / LeakDetection / Risk / Analytics | Code/Test | Implement the approved issue catalogue and correlated detection across the connected network; distinguish sensor/data faults, suspected process faults and safety/security signals, with severity, confidence, supported coverage and evidence. | feat(monitoring): detect network issues from trusted state | HPR-P25-005; approved detector thresholds/fixtures
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15392 | HPR-P25-006 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Monitoring / LeakDetection / Risk / Analytics | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6090 | HPR-P25-006 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13652 | HPR-P25-006 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Monitoring / LeakDetection / Risk / Analytics | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
~~~~~~~~

<a id="hapi-610"></a>
## HAPI-610 — Integrated closure: five telemetry-trust/quality/replay packages

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P25-056

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-615

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-056; child tasks own implementation. five telemetry-trust/quality/replay packages

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-056; child tasks own implementation. five telemetry-trust/quality/replay packages.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-056 — five telemetry-trust/quality/replay packages

Prerequisites:051/052, retained029 independent scope and D4. Current trusted monitoring
export exposes reading id/point/trust only; existing source/point/readings and Integration
jobs are preserved. No live SCADA, fixed protocol, real sensor or OT command prerequisite.

| Planning stage | Remaining delivery | Evidence / exit criterion |
|---|---|---|
| HPR-P25-056A | Configured source identity and versioned effective point/tag/asset/unit bindings. | Company/network/source authority, wrong-owner/wrong-kind references and overlapping/rebind windows validated; history records exact attribution without imposing universal current-ACTIVE rules. |
| HPR-P25-056B | Authorized ingest with immutable raw provenance and idempotency/conflict semantics. | Source and actor cannot spoof another company; source time vs ingest time and units remain distinct; same identity/same content replay vs changed content conflict, all-or-nothing persistence. |
| HPR-P25-056C | Configured quality, freshness, clock-skew and late/out-of-order assessment. | Trusted projection only after valid quality, stale/missing/unsupported data stays UNKNOWN; no hardcoded industrial thresholds or implied physical truth. |
| HPR-P25-056D | Deterministic history/replay and source outage/recovery/dead-letter behavior. | Rebind/effective revisions reproduce original attribution/quality, duplicate/loss/late/recovery cases remain explainable; no failed ingest promoted to a trusted reading. |
| HPR-P25-056E | Actual API/DB/monitoring export security and consumer qualification. | Two isolated companies/sources, actual ingest/filter/storage/query path, rollback/concurrent ingest and wrong time/units/binding denial; exact trusted source/time/quality contract for057/F08. |

D4 fixes configurable synthetic freshness/clock/units policies and exact files before
code. Owner supplies actual deployment calibration; policy configurability is required,
real-world data collection is not a blocker to these software tests.

~~~~~~~~

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-615. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23420](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23420)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1358](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1358)
- [doc/roadmap/ULTIMATE_ROADMAP.md:5196](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5196)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-611"></a>
## HAPI-611 — Configured telemetry source and effective bindings

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P25-056A

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-567, HAPI-588

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Configured source identity and versioned effective point/tag/asset/unit bindings.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Configured source identity and versioned effective point/tag/asset/unit bindings..

### 4. Functional Requirements

Company/network/source authority, wrong-owner/wrong-kind references and overlapping/rebind windows validated; history records exact attribution without imposing universal current-ACTIVE rules.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/EffectiveTelemetryAssetBinding.java` — pure Java owned aggregate/value and invariants for configured source principal and effective authoritative location/asset binding.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/EffectiveTelemetryAssetBindingUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/EffectiveTelemetryAssetBindingRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/EffectiveTelemetryAssetBindingApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/EffectiveTelemetryAssetBindingController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JdbcEffectiveTelemetryAssetBindingRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/telemetry/effective-asset-bindings` for configured source principal and effective authoritative location/asset binding. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Company/network/source authority, wrong-owner/wrong-kind references and overlapping/rebind windows validated; history records exact attribution without imposing universal current-ACTIVE rules.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-567, HAPI-588. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23428](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23428)
- [doc/model-remediation/RECONCILIATION.md:23708](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23708)
- [doc/model-remediation/RECONCILIATION.md:23754](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23754)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1016](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1016)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1062](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1062)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1366](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1366)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1016 | HPR-P25-056A | Approve source-principal authority and event-time versus ingestion-time binding selection for ambiguous/late observations; no deployment sensor data required. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1062 | HPR-P25-056A | REQUIRES_OWNER_DECISION | Configured telemetry source and effective bindings
doc/roadmap/ULTIMATE_ROADMAP.md:1366 | HPR-P25-056A | Configured source identity and versioned effective point/tag/asset/unit bindings. | Company/network/source authority, wrong-owner/wrong-kind references and overlapping/rebind windows validated; history records exact attribution without imposing universal current-ACTIVE rules.
doc/model-remediation/RECONCILIATION.md:23428 | HPR-P25-056A | Configured source identity and versioned effective point/tag/asset/unit bindings. | Company/network/source authority, wrong-owner/wrong-kind references and overlapping/rebind windows validated; history records exact attribution without imposing universal current-ACTIVE rules.
doc/model-remediation/RECONCILIATION.md:23708 | HPR-P25-056A | Approve source-principal authority and event-time versus ingestion-time binding selection for ambiguous/late observations; no deployment sensor data required. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23754 | HPR-P25-056A | REQUIRES_OWNER_DECISION | Configured telemetry source and effective bindings
~~~~~~~~

### HAPI-611 owner policy registration — 2026-10-27

**Owner-approved selection and bounded scope.** The repository owner approved Option A for each of OD-HAPI-611-01 through OD-HAPI-611-08 on 2026-10-27 and directed these decisions to be saved in GitHub as the next single task-scoped documentation stage after HAPI-599. This entry records the target policies under HAPI-611 (HPS-010, legacy HPR-P25-056A) with message `docs(telemetry): record owner-approved source binding and replay policy`. It does **not** execute source implementation, rewrite legacy evidence or change the frozen ROADMAP/PHASES registry status. HAPI-611 remains a source/effective-binding task; shared ingestion, timestamp-assessment and recovery decisions are cross-task requirements, not an implicit enlargement of the HAPI-611 implementation allowlist.

**Approved owner decisions (all Option A):**

1. **OD-HAPI-611-01 — Explicit authenticated source-principal authority.** Before an ingest operation, authenticate the actual gateway/device/integration principal and enforce its **current**, explicitly registered, approved, company-scoped source/telemetry-point grants. An approved gateway may submit readings for multiple sensors only through effective-dated, authorized mappings. Identifiers claimed inside payloads never grant authority, allow another station/company to be impersonated or revive revoked credentials. Preserve authenticated actor, source/point, company scope and binding version as audit/provenance; reject missing, invalid, revoked, cross-company and ambiguous authority without revealing protected existence.
2. **OD-HAPI-611-02 — Qualified event-time equipment attribution.** Resolve source/point-to-equipment or installed-unit association using the binding effective at the **verified measurement/event time**, not the ingestion timestamp. A 09:58 measurement acquired under Unit A but received at 10:07 after a 10:00 switch to Unit B remains attributable to Unit A if evidence qualifies. Preserve independent event time and receipt time, source-principal provenance, time assurance, effective binding ID/revision and qualification decision. Late or out-of-order arrival does not by itself change attribution. If time or binding evidence is absent, suspicious or ambiguous, quarantine/mark attribution unresolved; never silently substitute the current asset. Event-time evidence never overrides the present-time source-principal authorization in decision 01.
3. **OD-HAPI-611-03 — Auditable historical binding correction and requalification.** An authorized engineer may submit evidence-backed, effective-dated corrections as a superseding binding **revision**; retain the original binding, raw measurements, acquisition timestamps, actor/evidence and prior interpretations. Determine affected readings and downstream qualifications, analytics, monitoring, engineering inputs/results and operator decisions; mark affected qualifications for controlled reassessment. Re-attribution or subsequent requalification is authorized, reproducible and explicit. Never silently replace an issued result, mutate immutable raw events or automatically approve simulation/operational decisions.
4. **OD-HAPI-611-04 — Versioned source-specific clock assurance.** Configure approved synchronization evidence, time quality/uncertainty, clock skew, allowed lateness and validation criteria per source or source category. Measurements delayed by outages retain qualified event times when evidence passes. Readings with untrusted, missing, inconsistent or ambiguous time evidence stay quarantined/unqualified pending controlled review. Preserve event/receipt time, clock-quality basis, evaluated policy revision and outcome; do not invent enterprise-wide numeric tolerances, assume calibrated devices or convert questionable event time silently to receipt time.
5. **OD-HAPI-611-05 — Atomic, effective-dated binding changes.** An authorized reassignment closes the previous authoritative point/source-to-equipment binding and activates the successor **atomically** at an explicit effective instant. Preserve historical versions; prevent conflicting intervals where unique authoritative attribution is required and fence concurrent writers/stale changes. Handle legitimate multi-reference engineering relationships by typed semantics rather than hiding ambiguity. Qualify delayed readings against their historic event-time binding, and withhold ambiguous attribution from trusted use. The exact interval convention, transaction constraints and PostgreSQL concurrency strategy must be defined and tested in the implementation stage.
6. **OD-HAPI-611-06 — Strict revoked-source denial with separately authorized historical recovery.** Immediately reject any submission under revoked or inactive credentials, even when it claims an old event timestamp. Historical measurements acquired before revocation may enter a separately scoped, authorized import or replacement-principal workflow with verified provenance, original measurement time and immutable audit. Quarantine until source, temporal binding, time quality and replay/dedup evidence independently qualify. Backdating does not restore authority; importing old data cannot erase raw history or automatically qualify a simulation result.
7. **OD-HAPI-611-07 — Source-scoped stable reading identity and atomic deduplication.** Associate each acquisition with a durable, source-scoped identifier bound to verified source/point and canonical reading/provenance. After checking the submitting principal's **current** authority, acknowledge an identical legitimate retransmission idempotently without adding another observation, count or downstream event. Treat the same identity with changed value, unit, timestamp or attribution evidence as an audited quarantine/conflict, without overwriting the original record. Distinct acquisitions with equal values/timestamps must not be merged. Require atomic uniqueness, compare-and-insert and simultaneous-retransmission guarantees.
8. **OD-HAPI-611-08 — Legacy gateway adapter-generated identities with fail-safe fallback.** An approved acquisition adapter may generate an immutable source-scoped reading identity **at first acquisition** where a legacy device provides none, if the identifier and source/binding/evidence ledger survive restarts and retransmissions and can discriminate distinct acquisitions. Creating a new ID at each receipt, or treating equal timestamps/values as duplicate proof, is not allowed. Where durable replay-safe identity and defensible source provenance cannot be shown, quarantine as unqualified or use separately approved historical staging and reconciliation; do not promote to analytics or engineering inputs by default. Preserve present-time ingestion authority and qualified event-time attribution independently. No live SCADA/RTU/PLC integration or OT actuation is claimed.

**Task ownership and dependency reconciliation.** HAPI-611 owns the **configured identity of telemetry sources and versioned effective source/tag/point-to-asset/equipment/unit bindings**, including non-overlap, temporal correction and source-principal authority requirements; actual roles/grants and topology references must use their approved owners' public contracts. Preserve HAPI-567 and HAPI-588 as completed prerequisites without claiming they implement this task. The ingestion and immutable raw record/idempotency requirements of decisions 06–08 have delivery/qualification obligations in **HAPI-612 (HPR-P25-056B)**, source clock/freshness/quality assessment in **HAPI-613 (HPR-P25-056C)**, deterministic history/replay/outage and recovery in **HAPI-614 (HPR-P25-056D)**, and cross-owner API/monitoring security qualification in **HAPI-615 (HPR-P25-056E)**. This record establishes traceability but neither changes those downstream task definitions/statuses nor authorizes implementing them inside the HAPI-611 stage. Respect HAPI-599 physical-unit identity semantics when those contracts become implemented and source-supported.

**Remaining software acceptance, not established by policy approval.** Recover exact owner/API/port and persistence boundaries, source-principal credential verification, company/network permission checks, effective binding intervals and timestamps, source-clock evaluation, trust/quarantine states and migration inventory before registering separate bounded engineering packages. Use synthetic fixtures to prove wrong company/source/principal, revoked/backdated/unknown source, wrong-owner or wrong-kind unit, concurrent rebind conflict and rollback, event-time late readings, untrusted clock, historical correction exact-read and affected-result requalification, replay identity collisions, legacy restart/retransmission and denied history/existence. Require source-backed DTO/OpenAPI, PostgreSQL/Flyway concurrency/non-overlap, architecture and exact-SHA CI as applicable; source inspection alone and skipped database tests are not acceptance. No numeric calibration, real SCADA integration, automated control, qualified numerical solver or SITE acceptance is implied.

**Status and publication limit.** Prepublication `main` `8d3d3ee33d64bf283cfec80defaa924d1c89ab77`, HAPI-611 **PARTIAL / BLOCKED**. These eight approvals close the requested **policy-selection** gate only. The task remains BLOCKED for executable scope, implementation and verification; maintain ROADMAP/PHASES cells and all other statuses unchanged. This publication is documentation-only, modifies solely `doc/roadmap/TASKS_HPS-010.md`, and does not start another stage.

<a id="hapi-612"></a>
## HAPI-612 — Authorized ingestion and immutable raw provenance

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P25-056B

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-611

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Authorized ingest with immutable raw provenance and idempotency/conflict semantics.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Authorized ingest with immutable raw provenance and idempotency/conflict semantics..

### 4. Functional Requirements

Source and actor cannot spoof another company; source time vs ingest time and units remain distinct; same identity/same content replay vs changed content conflict, all-or-nothing persistence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/AuthorizedObservationIngestion.java` — pure Java owned aggregate/value and invariants for authorized bounded ingestion with immutable raw provenance, duplicate identities and deterministic recovery.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/AuthorizedObservationIngestionUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/AuthorizedObservationIngestionRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/AuthorizedObservationIngestionApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/AuthorizedObservationIngestionController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JdbcAuthorizedObservationIngestionRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/telemetry/observation-batches` for authorized bounded ingestion with immutable raw provenance, duplicate identities and deterministic recovery. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Source and actor cannot spoof another company; source time vs ingest time and units remain distinct; same identity/same content replay vs changed content conflict, all-or-nothing persistence.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-611. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23429](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23429)
- [doc/model-remediation/RECONCILIATION.md:23755](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23755)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1063](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1063)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1367](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1367)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1063 | HPR-P25-056B | BLOCKED_SPECIFICATION | Authorized ingestion and immutable raw provenance
doc/roadmap/ULTIMATE_ROADMAP.md:1367 | HPR-P25-056B | Authorized ingest with immutable raw provenance and idempotency/conflict semantics. | Source and actor cannot spoof another company; source time vs ingest time and units remain distinct; same identity/same content replay vs changed content conflict, all-or-nothing persistence.
doc/model-remediation/RECONCILIATION.md:23429 | HPR-P25-056B | Authorized ingest with immutable raw provenance and idempotency/conflict semantics. | Source and actor cannot spoof another company; source time vs ingest time and units remain distinct; same identity/same content replay vs changed content conflict, all-or-nothing persistence.
doc/model-remediation/RECONCILIATION.md:23755 | HPR-P25-056B | BLOCKED_SPECIFICATION | Authorized ingestion and immutable raw provenance
~~~~~~~~

<a id="hapi-613"></a>
## HAPI-613 — Configured quality, freshness and ordering assessment

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P25-056C

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-612

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Configured quality, freshness, clock-skew and late/out-of-order assessment.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Configured quality, freshness, clock-skew and late/out-of-order assessment..

### 4. Functional Requirements

Trusted projection only after valid quality, stale/missing/unsupported data stays UNKNOWN; no hardcoded industrial thresholds or implied physical truth.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/ObservationQualification.java` — pure Java owned aggregate/value and invariants for quality, freshness, late/out-of-order/future-skew states and UNKNOWN with configurable thresholds.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/ObservationQualificationUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/ObservationQualificationRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/ObservationQualificationApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/ObservationQualificationController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JdbcObservationQualificationRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/telemetry/observation-qualifications` for quality, freshness, late/out-of-order/future-skew states and UNKNOWN with configurable thresholds. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Trusted projection only after valid quality, stale/missing/unsupported data stays UNKNOWN; no hardcoded industrial thresholds or implied physical truth.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-612. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23430](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23430)
- [doc/model-remediation/RECONCILIATION.md:23709](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23709)
- [doc/model-remediation/RECONCILIATION.md:23756](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23756)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1017](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1017)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1064](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1064)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1368](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1368)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1017 | HPR-P25-056C | Approve handling policy choices for late/out-of-order/future-skew data; numerical deployment thresholds remain configurable owner input. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1064 | HPR-P25-056C | REQUIRES_OWNER_DECISION | Configured quality, freshness and ordering assessment
doc/roadmap/ULTIMATE_ROADMAP.md:1368 | HPR-P25-056C | Configured quality, freshness, clock-skew and late/out-of-order assessment. | Trusted projection only after valid quality, stale/missing/unsupported data stays UNKNOWN; no hardcoded industrial thresholds or implied physical truth.
doc/model-remediation/RECONCILIATION.md:23430 | HPR-P25-056C | Configured quality, freshness, clock-skew and late/out-of-order assessment. | Trusted projection only after valid quality, stale/missing/unsupported data stays UNKNOWN; no hardcoded industrial thresholds or implied physical truth.
doc/model-remediation/RECONCILIATION.md:23709 | HPR-P25-056C | Approve handling policy choices for late/out-of-order/future-skew data; numerical deployment thresholds remain configurable owner input. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23756 | HPR-P25-056C | REQUIRES_OWNER_DECISION | Configured quality, freshness and ordering assessment
~~~~~~~~

<a id="hapi-614"></a>
## HAPI-614 — Deterministic telemetry history, replay and recovery

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P25-056D

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-613

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Deterministic history/replay and source outage/recovery/dead-letter behavior.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Deterministic history/replay and source outage/recovery/dead-letter behavior..

### 4. Functional Requirements

Rebind/effective revisions reproduce original attribution/quality, duplicate/loss/late/recovery cases remain explainable; no failed ingest promoted to a trusted reading.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/ObservationReplay.java` — pure Java owned aggregate/value and invariants for deterministic event/binding revision replay and resumable idempotent history recovery.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/ObservationReplayUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/ObservationReplayRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/ObservationReplayApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/ObservationReplayController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JdbcObservationReplayRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/telemetry/observation-replays` for deterministic event/binding revision replay and resumable idempotent history recovery. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Rebind/effective revisions reproduce original attribution/quality, duplicate/loss/late/recovery cases remain explainable; no failed ingest promoted to a trusted reading.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-613. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23431](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23431)
- [doc/model-remediation/RECONCILIATION.md:23757](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23757)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1065](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1065)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1369](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1369)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1065 | HPR-P25-056D | BLOCKED_SPECIFICATION | Deterministic telemetry history, replay and recovery
doc/roadmap/ULTIMATE_ROADMAP.md:1369 | HPR-P25-056D | Deterministic history/replay and source outage/recovery/dead-letter behavior. | Rebind/effective revisions reproduce original attribution/quality, duplicate/loss/late/recovery cases remain explainable; no failed ingest promoted to a trusted reading.
doc/model-remediation/RECONCILIATION.md:23431 | HPR-P25-056D | Deterministic history/replay and source outage/recovery/dead-letter behavior. | Rebind/effective revisions reproduce original attribution/quality, duplicate/loss/late/recovery cases remain explainable; no failed ingest promoted to a trusted reading.
doc/model-remediation/RECONCILIATION.md:23757 | HPR-P25-056D | BLOCKED_SPECIFICATION | Deterministic telemetry history, replay and recovery
~~~~~~~~

<a id="hapi-615"></a>
## HAPI-615 — Telemetry API/security/monitoring qualification

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: HPR-P25-056E

Priority: P1

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-614

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Actual API/DB/monitoring export security and consumer qualification.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/TelemetryModule.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/TelemetryApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/TelemetryRestApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/mapper/TelemetryApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver effective source/asset binding, authorized ingestion, freshness/ordering/UNKNOWN, replay/recovery and scoped API qualification. The exact bounded remaining delivery is: Actual API/DB/monitoring export security and consumer qualification..

### 4. Functional Requirements

Two isolated companies/sources, actual ingest/filter/storage/query path, rollback/concurrent ingest and wrong time/units/binding denial; exact trusted source/time/quality contract for057/F08.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`, `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/telemetry/qualification/TelemetryOperatorQualificationTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/telemetry/TelemetryModule.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/TelemetryApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/TelemetryRestApi.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/mapper/TelemetryApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

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

- Two isolated companies/sources, actual ingest/filter/storage/query path, rollback/concurrent ingest and wrong time/units/binding denial; exact trusted source/time/quality contract for057/F08.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-614. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23432](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23432)
- [doc/model-remediation/RECONCILIATION.md:23758](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23758)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1066](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1066)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1370](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1370)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1066 | HPR-P25-056E | BLOCKED_SPECIFICATION | Telemetry API/security/monitoring qualification
doc/roadmap/ULTIMATE_ROADMAP.md:1370 | HPR-P25-056E | Actual API/DB/monitoring export security and consumer qualification. | Two isolated companies/sources, actual ingest/filter/storage/query path, rollback/concurrent ingest and wrong time/units/binding denial; exact trusted source/time/quality contract for057/F08.
doc/model-remediation/RECONCILIATION.md:23432 | HPR-P25-056E | Actual API/DB/monitoring export security and consumer qualification. | Two isolated companies/sources, actual ingest/filter/storage/query path, rollback/concurrent ingest and wrong time/units/binding denial; exact trusted source/time/quality contract for057/F08.
doc/model-remediation/RECONCILIATION.md:23758 | HPR-P25-056E | BLOCKED_SPECIFICATION | Telemetry API/security/monitoring qualification
~~~~~~~~

<a id="hapi-839"></a>
## HAPI-839 — add telemetry implementation roadmap

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-001

Priority: P1

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Create and maintain this roadmap.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

docs/roadmap/telemetry.md exists and passes this document's pass criteria.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/telemetry.md`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### TEL-001 — docs(telemetry): add telemetry implementation roadmap

| Field | Value |
|---|---|
| Commit code | `TEL-001` |
| Commit message | `docs(telemetry): add telemetry implementation roadmap` |
| Type | Documentation |
| Files | `docs/roadmap/telemetry.md` |
| Purpose | Define telemetry conception, audits, schema, module boundaries, roadmap, and validation. |
| Validation | `test -f docs/roadmap/telemetry.md` |

Acceptance criteria:

```text
The file is a roadmap/analysis artifact.
The file matches HidraAPI and dz.sh.hidra namespace.
The file includes scope checks, findings tables, audits, schema, task structure, and pass/reject criteria.
```

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

- `4e5b05c590b5419976d85247f1416fdad34d68da` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:595](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L595)
- [docs/roadmap/telemetry.md:653](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L653)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:595 | TEL-001 | docs(telemetry): add telemetry implementation roadmap | Create and maintain this roadmap. | docs/roadmap/telemetry.md` exists and passes this document's pass criteria.
~~~~~~~~

<a id="hapi-840"></a>
## HAPI-840 — add telemetry package skeleton

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-002

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add package skeleton only.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/query/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/package-info.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Package-info files exist; no behavior.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/package-info.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/package-info.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/package-info.java`, `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/package-info.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/package-info.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/package-info.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/telemetry/application/service/package-info.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration/package-info.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/package-info.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/package-info.java`, `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/package-info.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/query/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/package-info.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### TEL-002 — chore(telemetry): add telemetry package skeleton

Files to create:

```text
src/main/java/dz/sh/hidra/modules/telemetry/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/model/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/value/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/domain/service/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/command/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/query/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/dto/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/port/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/application/service/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/package-info.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/configuration/package-info.java
```

Rules:

```text
Create only package-info.java files.
Do not create tests.
Do not create behavior.
Do not create migrations.
```

Validation:

```bash
mvn -q -DskipTests compile
```

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:596](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L596)
- [docs/roadmap/telemetry.md:672](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L672)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:596 | TEL-002 | chore(telemetry): add telemetry package skeleton | Add package skeleton only. | Package-info files exist; no behavior.
~~~~~~~~

<a id="hapi-841"></a>
## HAPI-841 — add telemetry domain value objects

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-003

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add ids, codes, timestamps, value objects, topology references, catalog references, technical statuses.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

No business taxonomy enum exists.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:602](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L602)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:602 | TEL-003 | feat(telemetry): add telemetry domain value objects | Add ids, codes, timestamps, value objects, topology references, catalog references, technical statuses. | No business taxonomy enum exists.
~~~~~~~~

<a id="hapi-842"></a>
## HAPI-842 — add telemetry catalog domain models

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-004

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add TelemetryTypeCatalog and TelemetryTypeTranslation.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Translation model supports en, fr, ar.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:603](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L603)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:603 | TEL-004 | feat(telemetry): add telemetry catalog domain models | Add `TelemetryTypeCatalog` and `TelemetryTypeTranslation`. | Translation model supports `en`, `fr`, `ar`.
~~~~~~~~

<a id="hapi-843"></a>
## HAPI-843 — add telemetry domain models

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-005

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add source, device, point, binding, reading, batch models.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Domain models do not import topology domain classes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:604](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L604)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:604 | TEL-005 | feat(telemetry): add telemetry domain models | Add source, device, point, binding, reading, batch models. | Domain models do not import topology domain classes.
~~~~~~~~

<a id="hapi-844"></a>
## HAPI-844 — add telemetry domain policies

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-006

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add lifecycle, binding, quality, ingestion, catalog policies.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Invalid readings/bindings are rejected.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:605](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L605)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:605 | TEL-006 | feat(telemetry): add telemetry domain policies | Add lifecycle, binding, quality, ingestion, catalog policies. | Invalid readings/bindings are rejected.
~~~~~~~~

<a id="hapi-845"></a>
## HAPI-845 — add telemetry domain services

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-007

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add registration, binding, ingestion, catalog resolution services.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Services orchestrate domain rules only.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:606](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L606)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:606 | TEL-007 | feat(telemetry): add telemetry domain services | Add registration, binding, ingestion, catalog resolution services. | Services orchestrate domain rules only.
~~~~~~~~

<a id="hapi-846"></a>
## HAPI-846 — add telemetry application commands and queries

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-008

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add create/list/get/ingest command-query contracts.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Contracts use catalog codes/references, not enums.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:612](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L612)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:612 | TEL-008 | feat(telemetry): add telemetry application commands and queries | Add create/list/get/ingest command-query contracts. | Contracts use catalog codes/references, not enums.
~~~~~~~~

<a id="hapi-847"></a>
## HAPI-847 — add telemetry application DTOs

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-009

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add localized DTOs and reading DTOs.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

User-facing DTOs include localized catalog reference fields.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:613](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L613)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:613 | TEL-009 | feat(telemetry): add telemetry application DTOs | Add localized DTOs and reading DTOs. | User-facing DTOs include localized catalog reference fields.
~~~~~~~~

<a id="hapi-848"></a>
## HAPI-848 — add telemetry application ports

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-010

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add inbound use cases and outbound repository ports.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Ports are technology-agnostic.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:614](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L614)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:614 | TEL-010 | feat(telemetry): add telemetry application ports | Add inbound use cases and outbound repository ports. | Ports are technology-agnostic.
~~~~~~~~

<a id="hapi-849"></a>
## HAPI-849 — add telemetry application services

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-011

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add source/device/point/binding/reading/catalog services.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Services validate parent existence and domain policies.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:615](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L615)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:615 | TEL-011 | feat(telemetry): add telemetry application services | Add source/device/point/binding/reading/catalog services. | Services validate parent existence and domain policies.
~~~~~~~~

<a id="hapi-850"></a>
## HAPI-850 — add telemetry catalog tables

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-012

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add V022 catalog/translation tables and seeds.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Flyway migrates; en, fr, ar seeds exist.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:621](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L621)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:621 | TEL-012 | db(telemetry): add telemetry catalog tables | Add V022 catalog/translation tables and seeds. | Flyway migrates; `en`, `fr`, `ar` seeds exist.
~~~~~~~~

<a id="hapi-851"></a>
## HAPI-851 — add telemetry metadata tables

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-013

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add V023 source/device/point/binding/batch tables.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Tables use FK catalog ids.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:622](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L622)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:622 | TEL-013 | db(telemetry): add telemetry metadata tables | Add V023 source/device/point/binding/batch tables. | Tables use FK catalog ids.
~~~~~~~~

<a id="hapi-852"></a>
## HAPI-852 — add telemetry reading tables

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-014

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add V024 append-only reading table and indexes.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Reading table and indexes exist.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:623](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L623)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:623 | TEL-014 | db(telemetry): add telemetry reading tables | Add V024 append-only reading table and indexes. | Reading table and indexes exist.
~~~~~~~~

<a id="hapi-853"></a>
## HAPI-853 — add telemetry persistence entities and repositories

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-015

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add JPA entities/repositories.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

No legacy enum-style type columns.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:624](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L624)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:624 | TEL-015 | feat(telemetry): add telemetry persistence entities and repositories | Add JPA entities/repositories. | No legacy enum-style type columns.
~~~~~~~~

<a id="hapi-854"></a>
## HAPI-854 — add telemetry persistence mapper and adapters

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-016

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add mappers/adapters implementing outbound ports.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Repository ports are wired.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:625](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L625)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:625 | TEL-016 | feat(telemetry): add telemetry persistence mapper and adapters | Add mappers/adapters implementing outbound ports. | Repository ports are wired.
~~~~~~~~

<a id="hapi-855"></a>
## HAPI-855 — add telemetry infrastructure configuration

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-017

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add Spring bean configuration.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Application services and ports are registered.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:626](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L626)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:626 | TEL-017 | feat(telemetry): add telemetry infrastructure configuration | Add Spring bean configuration. | Application services and ports are registered.
~~~~~~~~

<a id="hapi-856"></a>
## HAPI-856 — add telemetry REST request DTOs

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-018

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add request DTOs for sources/devices/points/bindings/readings/catalogs.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Requests use catalog codes and examples.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:632](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L632)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:632 | TEL-018 | feat(telemetry): add telemetry REST request DTOs | Add request DTOs for sources/devices/points/bindings/readings/catalogs. | Requests use catalog codes and examples.
~~~~~~~~

<a id="hapi-857"></a>
## HAPI-857 — add telemetry REST response DTOs

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-019

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add localized response DTOs.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Responses expose localized catalog reference shape.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:633](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L633)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:633 | TEL-019 | feat(telemetry): add telemetry REST response DTOs | Add localized response DTOs. | Responses expose localized catalog reference shape.
~~~~~~~~

<a id="hapi-858"></a>
## HAPI-858 — add telemetry REST mapper

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-020

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Map REST to app contracts and localized responses.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Accept-Language fallback works.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:634](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L634)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:634 | TEL-020 | feat(telemetry): add telemetry REST mapper | Map REST to app contracts and localized responses. | Accept-Language` fallback works.
~~~~~~~~

<a id="hapi-859"></a>
## HAPI-859 — add telemetry REST controllers

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-021

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add endpoint groups under /api/v1/telemetry/.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Controllers expose only supported operations.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:635](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L635)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:635 | TEL-021 | feat(telemetry): add telemetry REST controllers | Add endpoint groups under `/api/v1/telemetry/**`. | Controllers expose only supported operations.
~~~~~~~~

<a id="hapi-860"></a>
## HAPI-860 — add telemetry OpenAPI coverage checklist

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-022

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Ensure Swagger/OpenAPI coverage.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Every endpoint group has @Tag, @Operation, and documented schemas.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:636](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L636)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:636 | TEL-022 | docs(telemetry): add telemetry OpenAPI coverage checklist | Ensure Swagger/OpenAPI coverage. | Every endpoint group has `@Tag`, `@Operation`, and documented schemas.
~~~~~~~~

<a id="hapi-861"></a>
## HAPI-861 — add telemetry domain tests

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-023

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Test value objects, models, policies, services.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Domain tests pass without Spring.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:642](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L642)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:642 | TEL-023 | test(telemetry): add telemetry domain tests | Test value objects, models, policies, services. | Domain tests pass without Spring.
~~~~~~~~

<a id="hapi-862"></a>
## HAPI-862 — add telemetry application service tests

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-024

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Test application services with fakes.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

No DB/Spring required.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:643](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L643)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:643 | TEL-024 | test(telemetry): add telemetry application service tests | Test application services with fakes. | No DB/Spring required.
~~~~~~~~

<a id="hapi-863"></a>
## HAPI-863 — add telemetry persistence tests

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-025

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Test mappers/adapters/repository contracts.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Persistence tests pass.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:644](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L644)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:644 | TEL-025 | test(telemetry): add telemetry persistence tests | Test mappers/adapters/repository contracts. | Persistence tests pass.
~~~~~~~~

<a id="hapi-864"></a>
## HAPI-864 — add telemetry REST mapper and controller tests

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-026

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Test REST mapper/controllers with fake use cases.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

No MockMvc required unless explicitly chosen.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:645](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L645)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:645 | TEL-026 | test(telemetry): add telemetry REST mapper and controller tests | Test REST mapper/controllers with fake use cases. | No MockMvc required unless explicitly chosen.
~~~~~~~~

<a id="hapi-865"></a>
## HAPI-865 — add telemetry application boot smoke test

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-027

Priority: P1

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Validate full Spring context with telemetry.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Testcontainers boot test passes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/SpringTelemetryController.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:646](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L646)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:646 | TEL-027 | test(telemetry): add telemetry application boot smoke test | Validate full Spring context with telemetry. | Testcontainers boot test passes.
~~~~~~~~

<a id="hapi-866"></a>
## HAPI-866 — finalize telemetry validation checklist

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-028

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Final checklist and handoff.

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Roadmap status and validation checklist complete.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/telemetry.md:647](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/telemetry.md#L647)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/telemetry.md:647 | TEL-028 | docs(telemetry): finalize telemetry validation checklist | Final checklist and handoff. | Roadmap status and validation checklist complete.
~~~~~~~~

<a id="hapi-867"></a>
## HAPI-867 — TEL-COR-001 historical bounded obligation

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-COR-001

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

TEL-COR-001 historical bounded obligation

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:42](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/telemetry_corrected_baseline_validated.md#L42)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:42 | TEL-COR-001 | telemetry | Superseded | @Component` mapper registration was not deterministic enough for the runtime baseline.
~~~~~~~~

<a id="hapi-868"></a>
## HAPI-868 — TEL-COR-002 historical bounded obligation

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-COR-002

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

TEL-COR-002 historical bounded obligation

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:43](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/telemetry_corrected_baseline_validated.md#L43)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:43 | TEL-COR-002 | telemetry | Accepted | Explicit mapper beans in `TelemetryConfiguration` provide deterministic Spring wiring.
~~~~~~~~

<a id="hapi-869"></a>
## HAPI-869 — TEL-COR-003 historical bounded obligation

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-COR-003

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: COMPLETED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

TEL-COR-003 historical bounded obligation

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:46](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/telemetry_corrected_baseline_validated.md#L46)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:46 | TEL-COR-003 | telemetry/topology docs | Accepted | Correction findings and validation path were recorded without raw logs.
~~~~~~~~

<a id="hapi-870"></a>
## HAPI-870 — TEL-COR-004 historical bounded obligation

Phase: HPS-010 — Telemetry and Monitoring

Legacy IDs: TEL-COR-004

Priority: P1

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `telemetry` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

TEL-COR-004 historical bounded obligation

### 2. Existing Implementation

Point/source creation, reading/latest/trend queries, trust assessment and binding primitives exist; Monitoring rules/deviation recording exists. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Convert authorized observations into immutable, quality-qualified, contextualized operational evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `telemetry`; primary phase owns this obligation. No live SCADA/RTU connection or automatic control is established. High-volume Timescale work remains separately deferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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

Canonical prerequisites: —. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:47](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/fixs/telemetry_corrected_baseline_validated.md#L47)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/fixs/telemetry_corrected_baseline_validated.md:47 | TEL-COR-004 | telemetry/topology docs | Current | Corrected baseline is marked validated.
~~~~~~~~

<!-- task-register:payload:end -->
