# HPS-022 — Integration and Interoperability: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-022 — Integration and Interoperability

<a id="hapi-104"></a>
## HAPI-104 — add sparkplug boundary contracts

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: EXT-008

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Define Integration-side external identities and mapping of Group/Edge Node/Device/Metric to Telemetry references.

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/integration/IntegrationModule.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/IntegrationApi.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/IntegrationRestApi.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/IntegrationController.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/SpringIntegrationController.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/mapper/IntegrationGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/mapper/IntegrationRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/StartIntegrationJobRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationExchangeMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationJobRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/StartIntegrationJobRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationExchangeMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationJobRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/mapper/IntegrationApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationNotificationPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual protocol adapters, domain event publishers and external providers; preserve parked MQTT/Sparkplug/CPM interfaces. The exact bounded remaining delivery is: Define Integration-side external identities and mapping of Group/Edge Node/Device/Metric to Telemetry references..

### 4. Functional Requirements

No Sparkplug type leaks into Telemetry domain; deterministic mapping and lifecycle semantics specified.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/integration/IntegrationModule.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/IntegrationApi.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/IntegrationRestApi.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/IntegrationController.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/SpringIntegrationController.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/mapper/IntegrationGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/mapper/IntegrationRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/StartIntegrationJobRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationExchangeMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationJobRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/StartIntegrationJobRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationExchangeMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationJobRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/mapper/IntegrationApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationNotificationPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`

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

- No Sparkplug type leaks into Telemetry domain; deterministic mapping and lifecycle semantics specified.
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

- [docs/roadmap/extended-capabilities.md:107](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L107)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:107 | EXT-008 | feat(integration): add sparkplug boundary contracts | Define Integration-side external identities and mapping of Group/Edge Node/Device/Metric to Telemetry references. | No Sparkplug type leaks into Telemetry domain; deterministic mapping and lifecycle semantics specified. | Deferred
~~~~~~~~

<a id="hapi-105"></a>
## HAPI-105 — add mqtt sparkplug adapter

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: EXT-009

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add the approved MQTT client/configuration and Sparkplug decoding adapter.

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/SpringIntegrationController.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual protocol adapters, domain event publishers and external providers; preserve parked MQTT/Sparkplug/CPM interfaces. The exact bounded remaining delivery is: Add the approved MQTT client/configuration and Sparkplug decoding adapter..

### 4. Functional Requirements

Handles approved Sparkplug message types, secure configuration, reconnect/backoff and payload validation.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/SpringIntegrationController.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`

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

- Handles approved Sparkplug message types, secure configuration, reconnect/backoff and payload validation.
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

- [docs/roadmap/extended-capabilities.md:108](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L108)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:108 | EXT-009 | feat(integration): add mqtt sparkplug adapter | Add the approved MQTT client/configuration and Sparkplug decoding adapter. | Handles approved Sparkplug message types, secure configuration, reconnect/backoff and payload validation. | Deferred
~~~~~~~~

<a id="hapi-106"></a>
## HAPI-106 — verify mqtt telemetry handoff

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: EXT-010

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Verify MQTT/Sparkplug adapter hands accepted measurements to Telemetry through the approved application boundary.

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/SpringIntegrationController.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual protocol adapters, domain event publishers and external providers; preserve parked MQTT/Sparkplug/CPM interfaces. The exact bounded remaining delivery is: Verify MQTT/Sparkplug adapter hands accepted measurements to Telemetry through the approved application boundary..

### 4. Functional Requirements

No direct foreign-table write; duplicate/reconnect behavior and malformed payload quarantine tested.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/integration/api/rest/controller/SpringIntegrationController.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`

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

- No direct foreign-table write; duplicate/reconnect behavior and malformed payload quarantine tested.
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

- [docs/roadmap/extended-capabilities.md:109](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/extended-capabilities.md#L109)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Owner-parked industrial extension; no activation or implementation selected. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/extended-capabilities.md:109 | EXT-010 | test(integration): verify mqtt telemetry handoff | Verify MQTT/Sparkplug adapter hands accepted measurements to Telemetry through the approved application boundary. | No direct foreign-table write; duplicate/reconnect behavior and malformed payload quarantine tested. | Deferred
~~~~~~~~

<a id="hapi-157"></a>
## HAPI-157 — scan integration domain models

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMC-012

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 4 integration model files.

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/DeadLetterStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExchangeMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationCriticality.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationDirection.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationEnvironment.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobTriggerType.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/PayloadStorageMode.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Expose stable consumer contracts and govern external identities, exchange and recovery.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/DeadLetterStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExchangeMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationCriticality.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationDirection.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationEnvironment.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobTriggerType.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/PayloadStorageMode.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 21. HMC-012 — Integration module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `integration`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 66  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Integration model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| ExternalSystem | record | 13 | `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java` | Extracted |
| IntegrationDeadLetterRecord | record | 18 | `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java` | Extracted |
| IntegrationExchangeMessage | record | 17 | `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java` | Extracted |
| IntegrationJobRun | record | 18 | `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java` | Extracted |

### Integration fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| ExternalSystem | 1 | id | String |
| ExternalSystem | 2 | code | String |
| ExternalSystem | 3 | nameAr | String |
| ExternalSystem | 4 | nameFr | String |
| ExternalSystem | 5 | nameEn | String |
| ExternalSystem | 6 | systemTypeId | String |
| ExternalSystem | 7 | ownerOrganizationUnitId | String |
| ExternalSystem | 8 | environment | IntegrationEnvironment |
| ExternalSystem | 9 | criticality | IntegrationCriticality |
| ExternalSystem | 10 | status | ExternalSystemStatus |
| ExternalSystem | 11 | description | String |
| ExternalSystem | 12 | createdAt | Instant |
| ExternalSystem | 13 | updatedAt | Instant |
| IntegrationDeadLetterRecord | 1 | id | String |
| IntegrationDeadLetterRecord | 2 | externalSystemId | String |
| IntegrationDeadLetterRecord | 3 | jobRunId | String |
| IntegrationDeadLetterRecord | 4 | exchangeMessageId | String |
| IntegrationDeadLetterRecord | 5 | inboundRecordId | String |
| IntegrationDeadLetterRecord | 6 | outboundRecordId | String |
| IntegrationDeadLetterRecord | 7 | targetModule | String |
| IntegrationDeadLetterRecord | 8 | failureStage | String |
| IntegrationDeadLetterRecord | 9 | reasonCode | String |
| IntegrationDeadLetterRecord | 10 | reasonMessage | String |
| IntegrationDeadLetterRecord | 11 | payloadHash | String |
| IntegrationDeadLetterRecord | 12 | sanitizedPayload | String |
| IntegrationDeadLetterRecord | 13 | status | DeadLetterStatus |
| IntegrationDeadLetterRecord | 14 | resolvedByActorId | String |
| IntegrationDeadLetterRecord | 15 | resolvedAt | Instant |
| IntegrationDeadLetterRecord | 16 | resolutionComment | String |
| IntegrationDeadLetterRecord | 17 | createdAt | Instant |
| IntegrationDeadLetterRecord | 18 | updatedAt | Instant |
| IntegrationExchangeMessage | 1 | id | String |
| IntegrationExchangeMessage | 2 | jobRunId | String |
| IntegrationExchangeMessage | 3 | externalSystemId | String |
| IntegrationExchangeMessage | 4 | endpointId | String |
| IntegrationExchangeMessage | 5 | direction | IntegrationDirection |
| IntegrationExchangeMessage | 6 | messageTypeId | String |
| IntegrationExchangeMessage | 7 | externalMessageId | String |
| IntegrationExchangeMessage | 8 | payloadFormatId | String |
| IntegrationExchangeMessage | 9 | payloadStorageMode | PayloadStorageMode |
| IntegrationExchangeMessage | 10 | payloadSanitized | String |
| IntegrationExchangeMessage | 11 | payloadReference | String |
| IntegrationExchangeMessage | 12 | payloadHash | String |
| IntegrationExchangeMessage | 13 | contentLengthBytes | Long |
| IntegrationExchangeMessage | 14 | receivedOrSentAt | Instant |
| IntegrationExchangeMessage | 15 | correlationId | String |
| IntegrationExchangeMessage | 16 | status | ExchangeMessageStatus |
| IntegrationExchangeMessage | 17 | createdAt | Instant |
| IntegrationJobRun | 1 | id | String |
| IntegrationJobRun | 2 | jobDefinitionId | String |
| IntegrationJobRun | 3 | runNumber | long |
| IntegrationJobRun | 4 | triggerType | JobTriggerType |
| IntegrationJobRun | 5 | triggeredByActorId | String |
| IntegrationJobRun | 6 | status | JobRunStatus |
| IntegrationJobRun | 7 | correlationId | String |
| IntegrationJobRun | 8 | startedAt | Instant |
| IntegrationJobRun | 9 | completedAt | Instant |
| IntegrationJobRun | 10 | receivedCount | long |
| IntegrationJobRun | 11 | mappedCount | long |
| IntegrationJobRun | 12 | acceptedCount | long |
| IntegrationJobRun | 13 | rejectedCount | long |
| IntegrationJobRun | 14 | deadLetterCount | long |
| IntegrationJobRun | 15 | retryCount | long |
| IntegrationJobRun | 16 | failureReason | String |
| IntegrationJobRun | 17 | createdAt | Instant |
| IntegrationJobRun | 18 | updatedAt | Instant |

### HMC-012 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 66
zero-field models    = 0
```

The scan records only declarations under `integration/domain/model`. Integration domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure adapter, external-system connector configuration, or database column metadata was mixed into this module scan.

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

- `048663bc93cab9f5e4b5f92ba857b1f365484e26` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:140](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L140)
- [docs/roadmap/model-field-catalogue.md:1464](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L1464)
- [docs/roadmap/model-field-catalogue.md:1554](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L1554)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:140 | HMC-012 | docs(catalogue): scan integration domain models | Scan all 4 integration model files. | **Completed** — 4/4 models, 66 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-191"></a>
## HAPI-191 — remediate semantic review IntegrationJobRun

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMR-014

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review IntegrationJobRun

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/StartIntegrationJobRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationJobRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/StartIntegrationJobRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationJobRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationJobRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunStepJpaRepository.java`
- `src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationJobRunSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Expose stable consumer contracts and govern external identities, exchange and recovery.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationJobRunRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/StartIntegrationJobRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationJobRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/StartIntegrationJobRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationJobRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationJobRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunStepJpaRepository.java`
- `src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationJobRunSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-014 — integration.IntegrationJobRun

- Source review: `HMSR-014`
- Exact commit: `fix(integration): remediate semantic review IntegrationJobRun`
- Status: **Completed** — run numbers are database-allocated per job through a concurrency-safe sequence trigger and unique key; new-run persistence validates active job definitions, current JOB_TYPE metadata, automated connector readiness, IMPORT/SYNC mapping prerequisites, and MANUAL actor/permission governance; counters and completion ordering are guarded in domain/database; terminal lifecycle transitions are monotonic at repository/database boundaries; audit timestamps remain explicitly enforced at persistence.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 8
- Additive Flyway: `src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Integration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/StartIntegrationJobRunRequest.java`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationJobRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/command/StartIntegrationJobRunCommand.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationJobRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationJobRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunStepJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql`
  - `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationJobRunSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IntegrationJobRunSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Authoritative monotonic run number:** stop treating `runNumber` as an unchecked caller-owned value; allocate/validate it per job in a concurrency-safe application/database design.
  2. **Job-definition eligibility:** before starting a run, load/validate the parent job definition and enforce its `active` state plus applicable connector/mapping/target prerequisites.
  3. **Manual-run governance:** enforce `manualRunAllowed` for `MANUAL` triggers and define/enforce actor provenance for manual execution.
  4. **Non-negative counters:** enforce non-negative `received`, `mapped`, `accepted`, `rejected`, `deadLetter`, and `retry` counts.
  5. **Counter consistency:** enforce the DDD aggregate-count rule, while preserving an explicit exception only where multi-record expansion is intentionally modeled.
  6. **Completion-time ordering:** reject `completedAt < startedAt`.
  7. **Monotonic lifecycle transitions:** terminal runs must not return to `RUNNING`; completion/failure/cancellation orchestration must update state through a transition-aware boundary.
  8. **Required audit timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationJobRunSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.

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

- `e3e6abc05649b41f544ed2500920ee384f3e2ecf` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:160](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L160)
- [docs/roadmap/model-semantic-remediation.md:1011](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1011)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:160 | HMR-014 | HMSR-014 | integration | IntegrationJobRun | — | — | fix(integration): remediate semantic review IntegrationJobRun | **Completed**
~~~~~~~~

<a id="hapi-237"></a>
## HAPI-237 — remediate semantic review ExternalSystem

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMR-047

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review ExternalSystem

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/RegisterExternalSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/ExternalSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/RegisterExternalSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/ExternalSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaExternalSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/ExternalSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/resources/db/migration/V20261004_047__hmr_047_integration_external_system.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/ExternalSystemSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Expose stable consumer contracts and govern external identities, exchange and recovery.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`, `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaExternalSystemRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/ExternalSystemJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/RegisterExternalSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/ExternalSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/RegisterExternalSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/ExternalSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaExternalSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/ExternalSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/resources/db/migration/V20261004_047__hmr_047_integration_external_system.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/ExternalSystemSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-047 — integration.ExternalSystem

- Source review: `HMSR-056`
- Exact commit: `fix(integration): remediate semantic review ExternalSystem`
- Status: **Completed** — ExternalSystem code uniqueness is deterministic at application level and race-safe in PostgreSQL, systemTypeId is constrained to EXTERNAL_SYSTEM_TYPE, and populated ownerOrganizationUnitId resolves through an Organization-owned Integration contract.
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_047__hmr_047_integration_external_system.sql`
- Owner-contract prerequisite: HMR-048A registered — live preflight proved the Reporting request/queue service and deliberate Identity/Workflow/Organization contracts needed by the reviewed obligations were missing from the original execution scope.
- Exact write allowlist:
  - `docs/data definition/Integration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/RegisterExternalSystemRequest.java`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/ExternalSystemResponse.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/command/RegisterExternalSystemCommand.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/dto/ExternalSystemSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaExternalSystemRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/ExternalSystemJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_047__hmr_047_integration_external_system.sql`
  - `src/test/java/dz/sh/hidra/modules/integration/semantic/ExternalSystemSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ExternalSystemSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce uniqueness of `ExternalSystem.code`.** Implement the explicit DDD invariant with a concurrency-safe authoritative mechanism, preferably the DDD-specified unique database index/constraint plus appropriate application conflict handling.
  2. **Enforce `EXTERNAL_SYSTEM_TYPE` catalog-family semantics for `systemTypeId`.** Generic Integration catalog-row existence is insufficient; the selected row must belong to the documented external-system-type family.
  3. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Do not add a cross-module database FK; fail closed through an Organization-owned lookup/application contract or equivalent boundary.

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
- `src/test/java/dz/sh/hidra/modules/integration/semantic/ExternalSystemSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `c9317d7c194d7664d2bb5515fbcb61eade352328` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:203](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L203)
- [docs/roadmap/model-semantic-remediation.md:2650](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2650)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:203 | HMR-047 | HMSR-056 | integration | ExternalSystem | — | organization.OrganizationUnit | fix(integration): remediate semantic review ExternalSystem | Planned
~~~~~~~~

<a id="hapi-238"></a>
## HAPI-238 — register ExternalSystem owner prerequisite

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMR-047A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

register ExternalSystem owner prerequisite

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Expose stable consumer contracts and govern external identities, exchange and recovery.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`, `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-047A — ExternalSystem registration/Organization owner prerequisite

- Source: HMR-047 / HMSR-056 plus live Integration/Organization boundary evidence.
- Exact commit: `docs(model-remediation): register ExternalSystem owner prerequisite`
- Status: **Completed** — the missing Integration registration service, Organization-owned Integration lookup contract, owner-side implementation/test, and architecture guardrail registrations are authorized; HMR-047A itself changes documentation only.
- Type: documentation/application-boundary prerequisite.
- Live evidence:
  1. HMR-047 requires deterministic application conflict handling for unique `ExternalSystem.code`, family validation for `systemTypeId`, and fail-closed validation of populated `ownerOrganizationUnitId`.
  2. The authoritative registration path is `IntegrationApplicationService.registerExternalSystem(...)`, but `IntegrationApplicationService.java` is absent from the original HMR-047 allowlist.
  3. No `organization.application.contract.integration` package or equivalent deliberate Organization-to-Integration export exists on live `main`.
  4. The previously recorded Organization candidates are internal ports/use cases and must not be imported directly by Integration under the repository cross-module guardrails.
  5. `OrganizationUnitRepositoryPort.findById(...)` can resolve owner truth inside Organization and is sufficient for the reviewed existence-only obligation; HMR-047 does not authorize inventing Organization lifecycle or role eligibility.
  6. `hidra_integration_catalog_entry` already exists and can support `EXTERNAL_SYSTEM_TYPE` family checks from the Integration repository boundary.
  7. The registered HMR-047 Flyway migration remains sufficient for the concurrency-safe unique-code database constraint/index; no cross-module Organization FK is authorized.
- Required decisions:
  1. Add `IntegrationApplicationService.java` to the HMR-047 write allowlist.
  2. Organization must export a narrow Integration-facing OrganizationUnit existence contract returning neutral scalar/existence evidence only.
  3. The Organization implementation must use `OrganizationUnitRepositoryPort.findById(...)`; null/blank/unknown IDs fail closed when validation is requested.
  4. `IntegrationApplicationService.registerExternalSystem(...)` must reject an already-used ExternalSystem code before save for deterministic application semantics.
  5. The concurrency-safe authoritative uniqueness remains the HMR-047 database unique constraint/index; the pre-check is not a substitute for it.
  6. `IntegrationApplicationService.registerExternalSystem(...)` must reject a `systemTypeId` that does not belong specifically to `EXTERNAL_SYSTEM_TYPE`.
  7. Populated `ownerOrganizationUnitId` must resolve through the Organization-owned Integration contract before save.
  8. No Organization cross-module database FK is authorized.
  9. Register exactly `dz.sh.hidra.modules.organization.application.contract.integration` in both existing architecture exported-package registries without weakening their algorithms.
- Newly authorized HMR-047 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`
- Newly authorized HMR-047 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-047 remains the current production remediation after this prerequisite is observed. Do not start HMR-048 automatically.


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
- `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `e410c6cf4bab562589dede69d846a0870956d7af` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:2687](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2687)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-250"></a>
## HAPI-250 — remediate semantic review IntegrationExchangeMessage

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMR-056

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review IntegrationExchangeMessage

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationExchangeMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationExchangeMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationExchangeMessageJpaRepository.java`
- `src/main/resources/db/migration/V20261007_010__hmr_056_integration_exchange_message.sql`
- `src/test/java/dz/sh/hidra/modules/integration/infrastructure/persistence/IntegrationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationExchangeMessageJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationExchangeMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationExchangeMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationExchangeMessageJpaRepository.java`
- `src/main/resources/db/migration/V20261007_010__hmr_056_integration_exchange_message.sql`
- `src/test/java/dz/sh/hidra/modules/integration/infrastructure/persistence/IntegrationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-056 — integration.IntegrationExchangeMessage

- Source review: `HMSR-067`
- Exact commit: `fix(integration): remediate semantic review IntegrationExchangeMessage`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: integration.ExternalSystem, integration.IntegrationJobRun
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261007_010__hmr_056_integration_exchange_message.sql` (canonical Batch 11 admission)
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Integration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationExchangeMessageResponse.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationExchangeMessageSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationExchangeMessageJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_056__hmr_056_integration_integration_exchange_message.sql`
  - `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IntegrationExchangeMessageSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated `jobRunId` as a same-module IntegrationJobRun reference.** Preserve optionality, but prevent dangling run identities.
  2. **Protect and validate populated `endpointId`.** Resolve the Integration-owned endpoint and ensure it belongs to the same `externalSystemId` recorded by the message.
  3. **Resolve message-type and payload-format controlled-value semantics.** Generic IntegrationCatalogEntry existence is insufficient unless the repository explicitly defines the allowed family/eligibility contract; HMSR-067 does not invent missing family names.

- Batch 11 implementation: Optional run/endpoint existence, correlated endpoint/system ownership and active exact existing MESSAGE_TYPE/PAYLOAD_FORMAT catalogs enforced on saves. Forward V20261007_010 adds nullable/composite FKs and catalog guards without rewriting legacy evidence. Five dedicated and five PostgreSQL cases prepared. Local compile/focused Maven blocked before execution by uncached Boot parent; CI pending.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/integration/infrastructure/persistence/IntegrationSemanticPostgresIntegrationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `b51808934efb6ffbd2bde180c919921ed74bb52e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:91](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L91)
- [doc/model-remediation/RECONCILIATION.md:13703](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13703)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:3526](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L3526)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15443](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15443)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:90](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L90)
- [docs/roadmap/model-semantic-remediation.md:217](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L217)
- [docs/roadmap/model-semantic-remediation.md:3165](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3165)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:217 | HMR-056 | HMSR-067 | integration | IntegrationExchangeMessage | — | integration.ExternalSystem, integration.IntegrationJobRun | fix(integration): remediate semantic review IntegrationExchangeMessage | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:91 | HMR-056 | HMSR-067 | integration.IntegrationExchangeMessage | COMPLETED — CI #585 PASSED | Optional run/endpoint existence, correlated endpoint/system ownership and active exact existing MESSAGE_TYPE/PAYLOAD_FORMAT catalogs enforced on saves. Forward V20261007_010 adds nullable/composite FKs and catalog guards without rewriting legacy evidence. Five dedicated and five PostgreSQL cases prepared. Local compile/focused Maven blocked before execution by uncached Boot parent; CI #604 passed.
doc/model-remediation/RECONCILIATION.md:13703 | HMR-056 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:90 | HMR-056 | HMSR-067 | [integration.IntegrationExchangeMessage](../domain/SEMANTIC_DECISIONS.md#integration-integrationexchangemessage) | [IntegrationExchangeMessageSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java) | [V20261007_010__hmr_056_integration_exchange_message](../../src/main/resources/db/migration/V20261007_010__hmr_056_integration_exchange_message.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15443 | HMR-056 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-265"></a>
## HAPI-265 — remediate semantic review IntegrationDeadLetterRecord

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMR-071

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review IntegrationDeadLetterRecord

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/IntegrationResolverContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryService.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationDeadLetterRecordJpaRepository.java`
- `src/main/resources/db/migration/V20261007_011__hmr_071_integration_dead_letter_record.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/infrastructure/persistence/IntegrationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryService.java`, `src/test/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationDeadLetterRecordJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/IntegrationResolverContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryService.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationDeadLetterRecordJpaRepository.java`
- `src/main/resources/db/migration/V20261007_011__hmr_071_integration_dead_letter_record.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/infrastructure/persistence/IntegrationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-071 — integration.IntegrationDeadLetterRecord

- Source review: `HMSR-084`
- Exact commit: `fix(integration): remediate semantic review IntegrationDeadLetterRecord`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: integration.ExternalSystem, integration.IntegrationExchangeMessage, integration.IntegrationJobRun
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261007_011__hmr_071_integration_dead_letter_record.sql` (canonical Batch 11 admission)
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Integration.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationDeadLetterRecordJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_071__hmr_071_integration_integration_dead_letter_record.sql`
  - `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=IntegrationDeadLetterRecordSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce all DDD-required failure evidence before persistence.** `failureStage` and `reasonMessage` must be non-blank in addition to the already-enforced `reasonCode` and `status`.
  2. **Protect populated optional same-module evidence references.** `jobRunId`, `exchangeMessageId`, `inboundRecordId`, and `outboundRecordId` must fail closed to existing Integration-owned records.
  3. **Enforce the explicit manual-resolution trio.** When manual resolution is recorded, `resolvedByActorId`, `resolvedAt`, and `resolutionComment` must all be present; resolver identity should be validated through the Identity/security boundary without a cross-module FK.

- Batch 11 implementation: Required failure evidence, all-or-none manual trio and optional local references enforced. New manual evidence requires authenticated eligible Identity actor; recorded provenance is immutable without historical actor revalidation. Forward V20261007_011 supplies nullable FKs/checks and concurrent provenance guard. Eight focused methods, one Identity owner method and five added PostgreSQL cases prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI pending.

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
- `src/test/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integration/infrastructure/persistence/IntegrationSemanticPostgresIntegrationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.

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

- `b439c2b2a298856f8e080453eb03200e25a7b28f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:106](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L106)
- [doc/model-remediation/RECONCILIATION.md:13718](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13718)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:3546](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L3546)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15458](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15458)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:105](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L105)
- [docs/roadmap/model-semantic-remediation.md:237](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L237)
- [docs/roadmap/model-semantic-remediation.md:3925](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3925)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:237 | HMR-071 | HMSR-084 | integration | IntegrationDeadLetterRecord | — | integration.ExternalSystem, integration.IntegrationExchangeMessage, integration.IntegrationJobRun | fix(integration): remediate semantic review IntegrationDeadLetterRecord | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:106 | HMR-071 | HMSR-084 | integration.IntegrationDeadLetterRecord | COMPLETED — CI #585 PASSED | Required failure evidence, all-or-none manual trio and optional local references enforced. New manual evidence requires authenticated eligible Identity actor; recorded provenance is immutable without historical actor revalidation. Forward V20261007_011 supplies nullable FKs/checks and concurrent provenance guard. Eight focused methods, one Identity owner method and five added PostgreSQL cases prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI #604 passed.
doc/model-remediation/RECONCILIATION.md:13718 | HMR-071 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:105 | HMR-071 | HMSR-084 | [integration.IntegrationDeadLetterRecord](../domain/SEMANTIC_DECISIONS.md#integration-integrationdeadletterrecord) | [IntegrationDeadLetterRecordSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java) | [V20261007_011__hmr_071_integration_dead_letter_record](../../src/main/resources/db/migration/V20261007_011__hmr_071_integration_dead_letter_record.sql); [IntegrationResolverContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/IntegrationResolverContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15458 | HMR-071 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-324"></a>
## HAPI-324 — review integration IntegrationJobRun

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMSR-014

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review integration IntegrationJobRun

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobTriggerType.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobDefinitionJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Expose stable consumer contracts and govern external identities, exchange and recovery.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobRunStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobDefinitionJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobTriggerType.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobDefinitionJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 27. HMSR-014 — integration.IntegrationJobRun review

**Decision:** REVISE  
**Review code:** HMSR-014  
**Dependency level:** 0  
**Bounded context:** integration  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 2 — `integration.IntegrationExchangeMessage` and `integration.IntegrationDeadLetterRecord`  
**Transitive dependents:** 2  
**Unresolved/non-subject references:** 1 — `jobDefinitionId`, now semantically resolved to Integration-owned `IntegrationJobDefinition` outside the 123 HMS subject set

### 27.1 Semantic role and ordering rationale

`IntegrationJobRun` is the Integration-owned execution record for one run of a repeatable import, export, synchronization, replay, reconciliation or health-oriented integration job. It preserves execution provenance, trigger type, correlation identity, lifecycle state, processing counters and failure evidence.

It is Level 0 because its upstream business prerequisite, `IntegrationJobDefinition`, is a retained Integration read/persistence model outside the 123 HMS subject population. The two direct HMS dependents are `IntegrationExchangeMessage` and `IntegrationDeadLetterRecord`.

Other Integration persistence/read models outside the subject population also reference a job run, including `IntegrationJobRunStep`, retry evidence, inbound/outbound processing records and synchronization cursor/history structures. Those relationships do not change the HMS subject graph.

Integration owns transport/exchange execution evidence. The target business module remains the source of truth for accepted Hidra business facts.

### 27.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable job-run identity and persistence primary key. |
| `jobDefinitionId` | `String` | Mandatory | Parent Integration job-definition reference; resolved to `IntegrationJobDefinition`. |
| `runNumber` | `long` | Mandatory | Monotonic execution sequence number within the parent job definition. |
| `triggerType` | `JobTriggerType` | Mandatory | Trigger source: `SCHEDULED`, `MANUAL`, `EVENT`, `RETRY`, or `REPLAY`. |
| `triggeredByActorId` | `String` | Conditional/optional | Actor reference for manual-trigger provenance; not an HMS `identity.User` edge. |
| `status` | `JobRunStatus` | Mandatory | Run lifecycle: `PENDING`, `RUNNING`, `COMPLETED`, `COMPLETED_WITH_ERRORS`, `FAILED`, or `CANCELLED`. |
| `correlationId` | `String` | Optional technical reference | End-to-end technical/business correlation identity. |
| `startedAt` | `Instant` | Mandatory | Execution start timestamp. |
| `completedAt` | `Instant` | Optional until completion | Completion timestamp; when present it must not precede `startedAt`. |
| `receivedCount` | `long` | Mandatory | Number of records/messages received for processing. |
| `mappedCount` | `long` | Mandatory | Number successfully mapped/transformed to the target contract stage. |
| `acceptedCount` | `long` | Mandatory | Number accepted by the target module/external destination. |
| `rejectedCount` | `long` | Mandatory | Number rejected. |
| `deadLetterCount` | `long` | Mandatory | Number routed to dead-letter handling. |
| `retryCount` | `long` | Mandatory | Number of retry attempts. |
| `failureReason` | `String` | Optional | Failure diagnostic/evidence text when applicable. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The current domain constructor rejects blank `id` and `jobDefinitionId`, rejects null `triggerType` and `status`, requires `startedAt`, trims scalar text/reference fields and exposes `terminalStatus()` for completed/failed/cancelled states.

### 27.3 Job-definition dependency resolution

HMS-003 left `jobDefinitionId` unresolved because `IntegrationJobDefinition` is outside the 123 HMS subject set. Stronger repository evidence resolves the target without adding an HMS edge:

- Integration DDD defines `IntegrationJobDefinition ||--o{ IntegrationJobRun`; 
- `IntegrationJobDefinitionJpaEntity` persists the parent in `hidra_integration_job_definition`;
- HRA-111 installs `fk_hra111_integration_022`; 
- that FK points `hidra_integration_job_run.job_definition_id` to `hidra_integration_job_definition(id)` with `ON DELETE RESTRICT`;
- repository mirror classification retains `IntegrationJobDefinition` as an Integration read/persistence model outside the HMS subject population.

Therefore `jobDefinitionId` is a same-module domain reference to a non-subject prerequisite, not an unresolved or cross-module reference.

### 27.4 Job-definition eligibility and trigger governance

The Integration DDD gives `IntegrationJobDefinition` the configuration needed to decide whether a run may start, including connector, mapping profile, job type, direction, schedule, `manualRunAllowed`, retry policy and `active` state. It also states that automated jobs require an active connector instance and import/sync jobs require an appropriate mapping/target path.

The current `IntegrationApplicationService.startIntegrationJobRun()` does not load `IntegrationJobDefinition` at all. It receives `jobDefinitionId`, `runNumber`, trigger type and actor/correlation metadata from the command, constructs a `RUNNING` job run and saves it directly.

Consequently, the current start path does not demonstrate validation that:

- the referenced job definition is active/eligible to run;
- a `MANUAL` trigger is allowed by `manualRunAllowed`;
- automated execution has the required connector readiness;
- job-type-specific mapping/public-target prerequisites are satisfied;
- manual-trigger provenance has the actor identity required by the intended operational policy.

The database FK proves only that a job-definition row exists. It does not prove current run eligibility.

### 27.5 Run-number authority

The Integration DDD defines `runNumber` as a **monotonic run number per job**.

The current API request and application command both accept `runNumber` from the caller, and the start service persists that value unchanged. `IntegrationJobRunRepositoryPort` exposes only `save` and `findById`; the Spring Data repository exposes no next-run/max-run or job/run-number lookup. The live migration provides an ordinary index on `job_definition_id` but no uniqueness/ordering constraint for `(job_definition_id, run_number)`.

Therefore the sequence is currently client-controlled and race-prone rather than authoritative. The final correction must establish a concurrency-safe owner for run-number allocation and prevent duplicate/out-of-order sequence identities for the same job.

### 27.6 Counter and temporal invariants

The active Integration DDD explicitly requires:

```text
Counts must be non-negative.
completedAt must be greater than or equal to startedAt.
acceptedCount + rejectedCount + deadLetterCount must not exceed receivedCount
unless explicitly modeled as multi-record expansion.
```

The current constructor validates none of the six counters and does not compare `completedAt` with `startedAt`. The live table stores all counters as `bigint NOT NULL` but contains no demonstrated CHECK constraints for non-negativity, temporal ordering or aggregate count consistency.

`startIntegrationJobRun()` initializes all counters to zero and `completedAt` to null, which is valid for a new run, but persisted data reconstructed or updated through another path can still violate the target invariants.

### 27.7 Lifecycle monotonicity

The Integration DDD states:

```text
JobRun status is monotonic.
A terminal JobRun cannot return to RUNNING.
```

`JobRunStatus` correctly represents `PENDING`, `RUNNING`, `COMPLETED`, `COMPLETED_WITH_ERRORS`, `FAILED`, and `CANCELLED`, and `IntegrationJobRun.terminalStatus()` correctly identifies terminal states.

However, `terminalStatus()` is observational only. The record has no transition operation that compares previous and next status, and the current public application service contains only the start path; no repository evidence found during HMSR-014 demonstrates controlled completion/failure/cancellation transitions with counter/timestamp updates.

The final lifecycle implementation must enforce transition monotonicity at an application/domain boundary that has both prior and requested state. Constructor validation alone cannot prove a transition is legal.

### 27.8 Persistence consistency

The live domain and JPA models agree on all 18 declared components. The base Integration migration makes `id`, `job_definition_id`, `run_number`, `trigger_type`, `status`, `started_at`, all six counters, `created_at`, and `updated_at` non-null.

HRA-111 protects the parent job-definition reference. The schema also indexes status, actor, correlation ID and audit timestamps for operational querying.

No current database constraint found during HMSR-014 enforces run-number monotonicity, non-negative counters, completion-time ordering or terminal-state transition policy.

### 27.9 Required revisions

The target baseline cannot be APPROVED until these evidence-backed execution invariants are enforced:

1. **Authoritative monotonic run number:** stop treating `runNumber` as an unchecked caller-owned value; allocate/validate it per job in a concurrency-safe application/database design.
2. **Job-definition eligibility:** before starting a run, load/validate the parent job definition and enforce its `active` state plus applicable connector/mapping/target prerequisites.
3. **Manual-run governance:** enforce `manualRunAllowed` for `MANUAL` triggers and define/enforce actor provenance for manual execution.
4. **Non-negative counters:** enforce non-negative `received`, `mapped`, `accepted`, `rejected`, `deadLetter`, and `retry` counts.
5. **Counter consistency:** enforce the DDD aggregate-count rule, while preserving an explicit exception only where multi-record expansion is intentionally modeled.
6. **Completion-time ordering:** reject `completedAt < startedAt`.
7. **Monotonic lifecycle transitions:** terminal runs must not return to `RUNNING`; completion/failure/cancellation orchestration must update state through a transition-aware boundary.
8. **Required audit timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required.

HMSR-014 does not change production Java, JPA, Flyway, API/application contracts, tests, job definitions, run records, counters or integration data.

### 27.10 Operational and OT-safety interpretation

For SONATRACH/TRC, an IntegrationJobRun may represent a historian/SCADA data import, CMMS/ERP synchronization, laboratory/custody file exchange, reporting export, replay or reconciliation execution. Its counters and timestamps are operational evidence of data movement, not the underlying pipeline business facts.

The Integration DDD explicitly prohibits direct control actuation: Integration jobs must not execute commands that change PLC/RTU/SCADA state or issue valve, pump or compressor control payloads. A successful IntegrationJobRun therefore means the governed data-exchange execution completed under its integration contract; it does not authorize or prove a physical control action.

### 27.11 Review conclusion

**REVISE.** `IntegrationJobRun` is the correct Integration execution model, its 18-field shape and status/trigger vocabularies are semantically appropriate, and `jobDefinitionId` is now resolved to the Integration-owned `IntegrationJobDefinition` read/persistence model outside the HMS graph.

The target baseline cannot mark it APPROVED while run-number authority, parent-job eligibility, counter invariants, completion-time ordering and lifecycle monotonicity remain unenforced. HMS reconciliation must retain these obligations until an explicitly authorized Integration correction task resolves them or the target semantics are explicitly changed.

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

- `6bd02c670e275737e391e5fc63477db99a33d754` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:66](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L66)
- [docs/roadmap/model-semantic-review.md:209](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L209)
- [docs/roadmap/model-semantic-review.md:4691](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L4691)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:209 | HMSR-014 | 0 | integration | IntegrationJobRun | — | — | 2 | 2 | 1 | REVISE | Completed | docs(model-review): review integration IntegrationJobRun
doc/domain/SEMANTIC_DECISIONS.md:66 | HMSR-014 | [integration.IntegrationJobRun](#integration-integrationjobrun) | REVISE | HMR-014
~~~~~~~~

<a id="hapi-366"></a>
## HAPI-366 — review integration ExternalSystem

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMSR-056

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review integration ExternalSystem

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationCriticality.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationEnvironment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Expose stable consumer contracts and govern external identities, exchange and recovery.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationCriticality.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationEnvironment.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationCriticality.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationEnvironment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 69. HMSR-056 — integration.ExternalSystem review

**Decision:** REVISE  
**Review code:** HMSR-056  
**Dependency level:** 2  
**Bounded context:** integration  
**Confirmed upstream subject dependencies:** optional `organization.OrganizationUnit` through `ownerOrganizationUnitId`  
**Confirmed same-module non-HMS dependency:** `IntegrationCatalogEntry` through mandatory `systemTypeId`  
**Confirmed direct HMS subject dependents:** 2  
**Transitive HMS subject dependents:** 2

### 69.1 Semantic role and ordering rationale

`ExternalSystem` is the Integration-owned registry entry for a system outside Hidra such as SCADA, historian/PI, ERP/SAP, CMMS, IAM, BI, laboratory, metering, file exchange, or messaging infrastructure.

Integration owns the connector/system registry but does not own Organization units or business facts imported/exported through those systems.

It is Level 2 because optional `ownerOrganizationUnitId` references the already-reviewed `organization.OrganizationUnit` subject.

### 69.2 Field semantics

The 13-field model contains stable system identity/code, multilingual display names, mandatory system classification, optional responsible OrganizationUnit, technical deployment environment, criticality, lifecycle status, description, and audit timestamps.

The canonical constructor already requires nonblank ID, code and system-type ID and non-null environment, criticality and status.

### 69.3 System-type catalog semantics

The Integration DDD defines `systemTypeId` as a mandatory catalog reference with examples such as:

```text
SCADA
HISTORIAN
ERP
CMMS
IAM
BI
FILE_EXCHANGE
LAB
METERING
OTHER
```

and declares the catalog family:

```text
EXTERNAL_SYSTEM_TYPE
```

HRA-111 installs a validated same-module FK:

```text
system_type_id -> hidra_integration_catalog_entry.id
```

That FK proves catalog-row existence but not catalog-family membership.

`IntegrationApplicationService.registerExternalSystem()` accepts `systemTypeId` and persists the new DRAFT system directly; no family validation was found.

Therefore an unrelated Integration catalog entry can currently satisfy the FK and be used as an external-system type.

### 69.4 Explicit code uniqueness is not enforced

The Integration DDD explicitly states:

```text
ExternalSystem.code must be unique.
```

The same DDD's recommended database constraints include:

```sql
CREATE UNIQUE INDEX uk_hidra_integration_external_system_code
    ON hidra_integration_external_system (code);
```

The live base migration instead creates only:

```text
ix_hidra_integration_external_system_code
```

as a non-unique index.

`ExternalSystemRepositoryPort` exposes only `save()` and `findById()` and no application-level `findByCode`/duplicate guard was found.

Therefore duplicate ExternalSystem business codes can currently be persisted despite the explicit DDD invariant.

### 69.5 Organization owner boundary

`ownerOrganizationUnitId` is explicitly documented as the optional Organization unit responsible for the integration.

The Integration DDD allows neutral `organizationUnitId` references and requires cross-module interaction through public application ports/stable DTOs/events rather than domain imports or direct table coupling.

`registerExternalSystem()` currently persists a populated `ownerOrganizationUnitId` without resolving it through an Organization-owned lookup contract.

No cross-module database FK should be introduced, but a populated owner identity should fail closed if the OrganizationUnit does not exist.

Current evidence does not make the owner mandatory for every ExternalSystem, so HMSR-056 preserves nullability.

### 69.6 Environment, criticality and lifecycle enums

`IntegrationEnvironment`, `IntegrationCriticality`, and `ExternalSystemStatus` are explicit constrained vocabularies in the current domain and persistence model.

The Integration DDD's business-catalog list does not place environment, criticality or external-system lifecycle status in the IntegrationCatalogEntry families. HMSR-056 therefore does not convert these enums into catalogs merely because other Integration business classifications are catalog-backed.

The domain helper `canRunProductionJobs()` correctly expresses the local predicate `ACTIVE && PRODUCTION`.

The DDD rule that only ACTIVE systems may run production jobs must ultimately be enforced where job execution is authorized. HMSR-056 does not manufacture a new ExternalSystem mutation rule or duplicate downstream job-definition/run review work.

### 69.7 Retired-system dependent-resource rule

The DDD states that a RETIRED ExternalSystem cannot receive new active endpoints or connector instances.

That rule governs creation/activation of dependent `ExternalEndpoint` and `ConnectorInstance` records. It is not a locally decidable invariant of the ExternalSystem record itself.

HMSR-056 records no separate production correction on ExternalSystem for this dependent-resource rule; it remains relevant when those Integration models/write paths are reviewed.

### 69.8 Multilingual semantics

The DDD marks French display name as required and Arabic/English as optional.

Persistence enforces non-null `name_fr`; the canonical constructor normalizes display text but does not promote it to an intrinsic fail-fast invariant.

Repository invariant policy classifies generic human-readable text as persistence-only unless a separate domain hardening task explicitly changes that rule. The current persistence boundary already rejects null French names.

HMSR-056 therefore records no additional constructor correction for display text.

### 69.9 Domain/JPA/Flyway/application alignment

Domain and JPA agree on all 13 components.

Required structural fields align for ID, code, system type, environment, criticality and status; persistence additionally protects French display text and audit timestamps.

`systemTypeId` has same-module row-existence FK protection.

The remaining evidence-backed defects are semantic/integrity gaps rather than shape mismatches:

- external-system business-code uniqueness is absent;
- system-type catalog-family membership is absent; and
- populated Organization owner references are not validated through the owning bounded context.

### 69.10 Required revisions

Three evidence-backed production correction obligations remain:

1. **Enforce uniqueness of `ExternalSystem.code`.** Implement the explicit DDD invariant with a concurrency-safe authoritative mechanism, preferably the DDD-specified unique database index/constraint plus appropriate application conflict handling.

2. **Enforce `EXTERNAL_SYSTEM_TYPE` catalog-family semantics for `systemTypeId`.** Generic Integration catalog-row existence is insufficient; the selected row must belong to the documented external-system-type family.

3. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Do not add a cross-module database FK; fail closed through an Organization-owned lookup/application contract or equivalent boundary.

HMSR-056 does not modify production Java, JPA, Flyway, application/API contracts, tests, external-system data, Integration catalogs, Organization data, or provisioned data.

### 69.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, ExternalSystem is the governed registry identity for interfaces to SCADA/historian, SAP/ERP, CMMS, IAM, laboratory, metering and other enterprise/OT-adjacent systems.

Duplicate system codes can make routing, monitoring and audit correlation ambiguous. Wrong catalog-family references can misclassify a connected system, while dangling Organization ownership weakens accountability for integration operations.

These controls strengthen traceability without allowing Integration to own Organization hierarchy or bypass business-module validation.

### 69.12 Review conclusion

**REVISE.** `ExternalSystem` has a coherent 13-field Integration registry model, appropriate technical/lifecycle enums, a protected same-module catalog-row reference, and correct bounded-context separation from Organization.

The target baseline cannot mark it APPROVED while explicit code uniqueness is unenforced, system-type catalog-family membership is not fail-closed, and populated Organization owner references can dangle.

HMS reconciliation must retain these three obligations until explicitly authorized Integration production corrections resolve them or stronger repository evidence changes the governing Integration DDD.

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

- `c925c0b4573601b29914221862d6e3ab95e1ae43` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:108](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L108)
- [docs/roadmap/model-semantic-review.md:251](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L251)
- [docs/roadmap/model-semantic-review.md:11619](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L11619)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:251 | HMSR-056 | 2 | integration | ExternalSystem | — | organization.OrganizationUnit | 2 | 2 | 0 | REVISE | Completed | docs(model-review): review integration ExternalSystem
doc/domain/SEMANTIC_DECISIONS.md:108 | HMSR-056 | [integration.ExternalSystem](#integration-externalsystem) | REVISE | HMR-047
~~~~~~~~

<a id="hapi-377"></a>
## HAPI-377 — review integration IntegrationExchangeMessage

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMSR-067

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review integration IntegrationExchangeMessage

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExchangeMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationDirection.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/PayloadStorageMode.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Expose stable consumer contracts and govern external identities, exchange and recovery.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExchangeMessageStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExchangeMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationDirection.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/PayloadStorageMode.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 80. HMSR-067 — integration.IntegrationExchangeMessage review

**Decision:** REVISE  
**Review code:** HMSR-067  
**Dependency level:** 3  
**Bounded context:** integration  
**Confirmed upstream subject dependencies:** `integration.ExternalSystem`, optional `integration.IntegrationJobRun`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 1

### 80.1 Semantic role

`IntegrationExchangeMessage` is the Integration-owned inbound/outbound exchange envelope used to trace data crossing the Hidra boundary. It carries an external system, optional endpoint/job-run context, direction, message/payload classifications, payload trace metadata, transport timing, correlation and exchange lifecycle status.

Integration owns the exchange envelope and evidence. It does not own the accepted business fact carried by the payload.

### 80.2 External-system integrity

`externalSystemId` is mandatory and unambiguously references `integration.ExternalSystem`.

HRA-111 protects same-module row existence with:

```text
hidra_integration_exchange_message.external_system_id
    -> hidra_integration_external_system.id
```

This is the correct bounded-context ownership relationship.

### 80.3 Optional job-run reference

`jobRunId` is optional and refers to `integration.IntegrationJobRun`.

The DDD explicitly permits an exchange message to be related to a job run, but the current message table only stores/indexes the scalar ID; HMSR-067 found no message-side FK to `hidra_integration_job_run`.

Because this is a same-module reference rather than a neutral external identifier, a populated `jobRunId` should fail closed against the Integration-owned run.

HMSR-067 does not make job-run association mandatory for every message.

### 80.4 Optional endpoint and external-system consistency

`endpointId` is optional and refers to Integration-owned `ExternalEndpoint`.

`ExternalEndpoint` itself belongs to an `ExternalSystem`, and that ownership relationship is database-protected.

The current exchange-message schema does not FK-protect `endpointId`, and `IntegrationApplicationService.recordExchangeMessage()` accepts `externalSystemId` and `endpointId` independently without proving:

```text
endpoint.externalSystemId == message.externalSystemId
```

The authoritative write path must therefore fail closed for a populated endpoint and ensure it belongs to the selected external system.

### 80.5 Message-type and payload-format catalog semantics

`messageTypeId` and `payloadFormatId` are mandatory and HRA-111 protects both against generic `hidra_integration_catalog_entry` row existence.

The Integration DDD describes these fields as catalog references but current live evidence does not provide defensible named catalog-family constants for message type or payload format.

HMSR-067 therefore does not invent family names.

The target baseline still requires their controlled-value semantics to be explicit enough that unrelated Integration catalog rows cannot be accepted merely because they exist.

### 80.6 Payload storage semantics

`PayloadStorageMode` is a technical enum:

```text
INLINE_SANITIZED
OBJECT_STORAGE_REFERENCE
HASH_ONLY
```

The model also carries optional `payloadSanitized` and `payloadReference`, while `payloadHash` is always required.

The DDD describes inline sanitized payload and object-storage/document reference as optional according to allowed storage policy, but it does not state a complete field-coupling invariant such as requiring exactly one payload field for every mode.

HMSR-067 therefore does not invent mode-to-field cardinality rules.

The mandatory payload hash is already enforced by the domain and persistence model for traceability/deduplication.

### 80.7 Direction and exchange lifecycle

`IntegrationDirection` is a technical exchange enum and `ExchangeMessageStatus` is the exchange-message lifecycle:

```text
RECEIVED
MAPPED
SUBMITTED
ACCEPTED
REJECTED
SENT
ACKNOWLEDGED
DEAD_LETTERED
```

Current Integration evidence does not define a complete direction-specific status-transition matrix for this model. HMSR-067 therefore does not manufacture one.

### 80.8 External and correlation identities

`externalMessageId` is an optional identity supplied by the external system and remains an external reference, not a same-module relation.

`correlationId` remains a neutral technical correlation reference.

Neither should be converted into an HMS subject edge or database FK.

### 80.9 Industrial safety and ownership boundary

The Integration DDD is explicit that Integration moves data across boundaries but does not own the meaning of accepted business facts.

It must call target-module public import/export contracts and must not directly write foreign business tables or actuate PLC/RTU/SCADA/SIS/ESD equipment.

The exchange-message model is compatible with that boundary and does not require any ownership transfer.

### 80.10 Required revisions

Three evidence-backed obligations remain:

1. **Protect populated `jobRunId` as a same-module IntegrationJobRun reference.** Preserve optionality, but prevent dangling run identities.

2. **Protect and validate populated `endpointId`.** Resolve the Integration-owned endpoint and ensure it belongs to the same `externalSystemId` recorded by the message.

3. **Resolve message-type and payload-format controlled-value semantics.** Generic IntegrationCatalogEntry existence is insufficient unless the repository explicitly defines the allowed family/eligibility contract; HMSR-067 does not invent missing family names.

HMSR-067 does not modify production Java, JPA, Flyway, API/application contracts, tests, Integration catalog data, external-system data, job-run data, or provisioned data.

### 80.11 Review conclusion

**REVISE.** `IntegrationExchangeMessage` is a coherent 17-field Integration-owned exchange envelope. Mandatory external-system and generic catalog-row existence are already protected, payload hashing is required, external/correlation identities remain correctly non-relational, and the model respects the Integration/business-module/OT safety boundary.

The target baseline cannot mark it APPROVED while populated job-run and endpoint references can dangle, endpoint/external-system consistency is not enforced, and the controlled-value families for message type and payload format remain semantically unresolved beyond generic catalog-row existence.

HMS reconciliation must retain these obligations until explicitly authorized Integration production/DDD corrections resolve them or stronger repository evidence changes the governing model.

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

- `b9c27572d7d5c3e97b13ea6732a9cfbaebd1bd06` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:119](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L119)
- [docs/roadmap/model-semantic-review.md:262](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L262)
- [docs/roadmap/model-semantic-review.md:13065](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L13065)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:262 | HMSR-067 | 3 | integration | IntegrationExchangeMessage | — | integration.ExternalSystem, integration.IntegrationJobRun | 1 | 1 | 2 | REVISE | Completed | docs(model-review): review integration IntegrationExchangeMessage
doc/domain/SEMANTIC_DECISIONS.md:119 | HMSR-067 | [integration.IntegrationExchangeMessage](#integration-integrationexchangemessage) | REVISE | HMR-056
~~~~~~~~

<a id="hapi-394"></a>
## HAPI-394 — review integration IntegrationDeadLetterRecord

Phase: HPS-022 — Integration and Interoperability

Legacy IDs: HMSR-084

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `integration` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review integration IntegrationDeadLetterRecord

### 2. Existing Implementation

External-system/job/exchange-message registration, OpenAPI publication and HidraWEB artifact consumption exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/DeadLetterStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Expose stable consumer contracts and govern external identities, exchange and recovery.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `integration`; primary phase owns this obligation. No direct foreign-table write, live OT access or engine interchangeability is inferred. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/DeadLetterStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 97. HMSR-084 — integration.IntegrationDeadLetterRecord review

**Decision:** REVISE  
**Review code:** HMSR-084  
**Dependency level:** 4  
**Bounded context:** integration  
**Confirmed upstream subject dependencies:** mandatory `integration.ExternalSystem`, optional `integration.IntegrationExchangeMessage`, optional `integration.IntegrationJobRun`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0

### 97.1 Semantic role

`IntegrationDeadLetterRecord` is the Integration-owned failure-evidence record for an import/export/retry item that could not be processed safely. It preserves the external-system origin, optional job/message/inbound/outbound evidence links, target-module hint, failure stage/reason, sanitized payload evidence, dead-letter lifecycle state, and resolution metadata.

Integration owns the dead-letter evidence and replay orchestration. Identity owns actor identity. Target business modules continue to own accepted business facts.

### 97.2 Mandatory external-system integrity

`externalSystemId` is mandatory and same-module.

HRA-111 already protects it with:

```text
hidra_integration_dead_letter_record.external_system_id
  -> hidra_integration_external_system.id
```

The domain constructor also rejects a blank external-system ID.

No further basic existence correction is required for this mandatory ownership edge.

### 97.3 Required failure evidence

The Integration DDD defines these dead-letter fields as required:

```text
failureStage
reasonCode
reasonMessage
status
```

The live domain constructor already rejects blank `reasonCode` and null `status`, but it does not reject blank/null `failureStage` or `reasonMessage`.

JPA/Flyway persist both missing fields as `NOT NULL`.

Unlike a generic persistence-only nullability rule, the Integration DDD explicitly classifies both fields as required dead-letter evidence. The authoritative domain/application boundary must therefore fail fast for blank/null `failureStage` and `reasonMessage`.

### 97.4 Optional same-module evidence references

The optional fields:

```text
jobRunId
exchangeMessageId
inboundRecordId
outboundRecordId
```

reference Integration-owned execution/message evidence.

Repository evidence shows no HRA-111 foreign keys for these optional edges and no live application lookup path proving existence before persistence.

When populated, these references should fail closed to existing Integration-owned records. Same-module foreign keys are architecturally appropriate where the referenced record is stable, or an explicitly justified Integration application boundary may supplement them.

HMSR-084 does not invent a requirement that all four references must be present. The DDD intentionally allows dead letters to arise at different processing stages.

### 97.5 Replay and historical evidence

The Integration DDD explicitly states:

```text
Dead-letter records are not deleted after replay.
A replay creates a new run and links back to the original evidence.
Replay must create a new job run or retry attempt, not mutate historical evidence.
```

The live domain helper correctly treats only OPEN and UNDER_REVIEW records as replayable.

No live replay application service for this model was found in the reviewed evidence, so HMSR-084 does not invent an observed mutation defect. Future replay implementation must preserve the DDD's append/new-run semantics and historical dead-letter evidence.

### 97.6 Manual resolution invariant

The Integration DDD explicitly states:

```text
Manual resolution requires actor, timestamp, and comment.
```

The model stores:

```text
resolvedByActorId
resolvedAt
resolutionComment
```

but the constructor permits any partial combination of those fields.

Therefore manual-resolution metadata is not semantically fail-closed. The authoritative domain/application boundary must enforce the trio when a manual resolution is recorded.

HMSR-084 does not equate every terminal status with manual resolution. In particular, REPLAYED and IGNORED may arise through different flows. The obligation is limited to the DDD's explicit manual-resolution rule.

### 97.7 Resolver actor boundary

`resolvedByActorId` is an Identity-owned actor reference.

The Integration DDD allows neutral `actorId` references and explicitly forbids importing Identity domain models. The scalar-ID design is therefore correct.

When a manual-resolution use case is implemented, the resolver actor should be validated through the appropriate Identity/security boundary while preserving the scalar reference. No cross-module Identity database FK should be introduced.

### 97.8 Target-module and payload evidence

`targetModule` is an optional neutral target hint. It does not represent ownership of the target business object and must not become a cross-module relational dependency.

`payloadHash` and `sanitizedPayload` are failure-evidence fields. The DDD explicitly requires sanitized payload handling and forbids Integration from becoming a shortcut around target-module ownership.

HMSR-084 records no new target-module FK or payload-shape rule beyond those existing boundary/safety requirements.

### 97.9 Lifecycle enum

`DeadLetterStatus` contains:

```text
OPEN
UNDER_REVIEW
REPLAYED
IGNORED
RESOLVED
```

These are stable Integration dead-letter lifecycle states and are appropriate as a technical/domain enum.

Current evidence does not define a complete transition matrix beyond replayability and the manual-resolution requirement, so HMSR-084 does not invent one.

### 97.10 Required revisions

Three evidence-backed obligations remain:

1. **Enforce all DDD-required failure evidence before persistence.** `failureStage` and `reasonMessage` must be non-blank in addition to the already-enforced `reasonCode` and `status`.

2. **Protect populated optional same-module evidence references.** `jobRunId`, `exchangeMessageId`, `inboundRecordId`, and `outboundRecordId` must fail closed to existing Integration-owned records.

3. **Enforce the explicit manual-resolution trio.** When manual resolution is recorded, `resolvedByActorId`, `resolvedAt`, and `resolutionComment` must all be present; resolver identity should be validated through the Identity/security boundary without a cross-module FK.

HMSR-084 does not modify production Java, JPA, Flyway, API/application contracts, tests, Integration/Identity data, or provisioned data.

### 97.11 Review conclusion

**REVISE.** `IntegrationDeadLetterRecord` is a coherent Integration-owned failure-evidence model with correct mandatory external-system integrity, appropriate lifecycle enum semantics, replay-oriented historical evidence, and clean cross-module boundaries.

The target baseline cannot mark it APPROVED while two DDD-required failure fields can be blank, populated optional same-module evidence references are not fail-closed, and manual-resolution metadata can violate the explicit actor/timestamp/comment rule.

HMS reconciliation must retain these obligations until explicitly authorized Integration production corrections resolve them or stronger repository evidence changes the governing Integration DDD.

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

- `aa32cac76ef13fc9ee6848165774fc6a22595f90` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:136](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L136)
- [docs/roadmap/model-semantic-review.md:279](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L279)
- [docs/roadmap/model-semantic-review.md:15560](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L15560)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:279 | HMSR-084 | 4 | integration | IntegrationDeadLetterRecord | — | integration.ExternalSystem, integration.IntegrationExchangeMessage, integration.IntegrationJobRun | 0 | 0 | 2 | REVISE | Completed | docs(model-review): review integration IntegrationDeadLetterRecord
doc/domain/SEMANTIC_DECISIONS.md:136 | HMSR-084 | [integration.IntegrationDeadLetterRecord](#integration-integrationdeadletterrecord) | REVISE | HMR-071
~~~~~~~~

<!-- task-register:payload:end -->
