# HPS-011 — Alarms and Events: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-011 — Alarms and Events

<a id="hapi-001"></a>
## HAPI-001 — approve suppression lifecycle semantics

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-001

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

This document only. Record the approved suppression policy and implementation sequence.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`

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

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `e10f1ba9c738337b1dea9f57c1184cb5fe49d0b4` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:178](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L178)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:178 | ALM-SUP-001 | docs(alarm): approve suppression lifecycle semantics | This document only. Record the approved suppression policy and implementation sequence. | Domain decision blocker removed without production-code change. | **Completed**
~~~~~~~~

<a id="hapi-002"></a>
## HAPI-002 — add suppression application contracts

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-002

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add minimal Alarm application commands/queries/use-case ports and DTOs for create, release, expiry evaluation, and query behavior. Reuse existing domain/persistence concepts.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/CreateAlarmSuppressionCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/EvaluateAlarmSuppressionExpiryCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/ReleaseAlarmSuppressionCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionPageDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmSuppressionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/query/AlarmSuppressionQuery.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmSuppressionUseCase.java`.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/CreateAlarmSuppressionCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/EvaluateAlarmSuppressionExpiryCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/ReleaseAlarmSuppressionCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionPageDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmSuppressionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/query/AlarmSuppressionQuery.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 6. ALM-SUP-002 completion evidence

ALM-SUP-002 was executed against HidraAPI baseline `e10f1ba9c738337b1dea9f57c1184cb5fe49d0b4`.

Live-source reconciliation found that suppression already had persistence/JPA representation and domain enums, but no current `AlarmSuppression` domain aggregate and no dedicated suppression application repository port. The application contracts therefore remain infrastructure-neutral and use only existing suppression enums plus neutral identifiers.

Added contracts:

- `CreateAlarmSuppressionCommand` for exact scope/reference, optional persisted scope evidence, reason, authenticated actor reference, optional end time, optional workflow evidence, and correlation;
- `ReleaseAlarmSuppressionCommand`;
- `EvaluateAlarmSuppressionExpiryCommand` for backend-owned idempotent expiry evaluation;
- `AlarmSuppressionQuery`;
- `AlarmSuppressionDto` and `AlarmSuppressionPageDto`;
- `ManageAlarmSuppressionUseCase`;
- `AlarmSuppressionQueryUseCase`.

No domain lifecycle implementation, repository adapter, scheduler, Workflow integration, REST endpoint, permission seed, migration, or OpenAPI contract is included in ALM-SUP-002.

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

- Original bounded artifacts remain present or their stronger approved replacement is explicitly recorded.
- Source/document assertions and matched test/CI provenance remain independently reproducible; no broader workflow closure is inferred.

### 12. Dependencies and Blockers

Canonical prerequisites: —. This completed scope remains accepted within its recorded limits.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. No new source deliverable is selected for this retained task.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- `283307fe9f08c6405060699d2cd6e1462d61f637` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:179](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L179)
- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:201](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L201)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:179 | ALM-SUP-002 | feat(alarm): add suppression application contracts | Add minimal Alarm application commands/queries/use-case ports and DTOs for create, release, expiry evaluation, and query behavior. Reuse existing domain/persistence concepts. | Application contracts encode the approved policy without REST/JPA leakage or duplicate models. | **Completed**
~~~~~~~~

<a id="hapi-003"></a>
## HAPI-003 — implement suppression lifecycle policy

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-003

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Implement domain/application policy for ALARM overlay/restoration, broad-scope matching, acknowledgement/clear/close/escalation interaction, conflicts, and server-derived actor handling.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`, `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 7. ALM-SUP-003 completion evidence

ALM-SUP-003 was executed against HidraAPI baseline `283307fe9f08c6405060699d2cd6e1462d61f637`.

The implementation adds a pure domain `AlarmSuppressionPolicy` rather than a duplicate suppression aggregate. It defines:

- creation validation, including mandatory Workflow evidence for open-ended suppression;
- exact-scope deterministic matching for ALARM, ALARM_TYPE, TOPOLOGY_ASSET, MONITORING_RULE, and SOURCE;
- overlap rejection policy for an existing ACTIVE exact-scope suppression;
- release eligibility limited to ACTIVE suppression;
- idempotency-friendly expiry eligibility for ACTIVE time-bounded suppression;
- restoration precedence from existing authoritative Alarm evidence: CANCELLED, CLOSED, CLEARED, ACKNOWLEDGED, ESCALATED, then ACTIVE;
- acknowledgement and escalation remain allowed while a nonterminal alarm is suppressed;
- the existing close guard now permits a SUPPRESSED alarm to close only when its underlying clear evidence is present, preserving the pre-existing normal closure rule.

Focused unit coverage locks the approved semantics. No repository adapter, scheduler, Workflow adapter, REST endpoint, migration, or OpenAPI publication is included.

Actor identity remains a required application contract value and must be server-derived at the trusted API/application boundary in later integration work; ALM-SUP-003 does not introduce frontend actor trust.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.

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

- `b88af5dc5f7eb8d622cc343041f2ae788ad38fcd` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:180](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L180)
- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:219](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L219)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:180 | ALM-SUP-003 | feat(alarm): implement suppression lifecycle policy | Implement domain/application policy for ALARM overlay/restoration, broad-scope matching, acknowledgement/clear/close/escalation interaction, conflicts, and server-derived actor handling. | Approved lifecycle rules are deterministic and unit-tested. | **Completed**
~~~~~~~~

<a id="hapi-004"></a>
## HAPI-004 — expose suppression persistence queries

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-004

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Extend existing suppression repository adapter only as required by approved use cases: exact-scope active lookup, expiry candidates, history/query support.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
- `src/test/java/dz/sh/hidra/DomainPersistenceMirrorGuardrailTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
- `src/test/java/dz/sh/hidra/DomainPersistenceMirrorGuardrailTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 8. ALM-SUP-004 completion evidence

ALM-SUP-004 was executed against HidraAPI baseline `b88af5dc5f7eb8d622cc343041f2ae788ad38fcd`.

The existing `AlarmSuppressionJpaEntity` and `hidra_alarm_suppression` table remain authoritative persistence. The task adds:

- HRA-061 explicitly classifies `AlarmSuppression` as a retained infrastructure read/persistence model whose dedicated domain mirror, application repository port, and JPA adapter must remain retired;
- the existing `AlarmSuppressionJpaRepository` is therefore extended in place, without reintroducing those retired mirrors;
- exact active-scope/status existence lookup is available for overlap enforcement;
- deterministic due-expiry lookup is available for ACTIVE suppressions whose end time is at or before the authoritative evaluation instant;
- `JpaSpecificationExecutor` support remains infrastructure-owned for later filtered history/query composition without leaking JPA types into application contracts.

The initial ALM-SUP-004 commit temporarily reintroduced a retired repository port and adapter; CI guardrail `DomainPersistenceMirrorGuardrailTest` correctly rejected that architecture drift. The corrective commit removes them and preserves HRA-061.

No database migration, new table, application repository mirror, JPA adapter mirror, scheduler, Workflow approval adapter, REST endpoint, permission publication, or OpenAPI change is included.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/DomainPersistenceMirrorGuardrailTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `6f4bdacae0000fdf7f25698d863695fb8a5454c0` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:181](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L181)
- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:238](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L238)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:181 | ALM-SUP-004 | feat(alarm): expose suppression persistence queries | Extend existing suppression repository adapter only as required by approved use cases: exact-scope active lookup, expiry candidates, history/query support. | No duplicate persistence model; overlap and expiry queries are deterministic. | **Completed**
~~~~~~~~

<a id="hapi-005"></a>
## HAPI-005 — integrate suppression workflow approval

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-005

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Verify open-ended suppression through Workflow public contracts/ports and fail closed when approval is absent.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/alarm/AlarmSuppressionWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/alarm/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapter.java`
- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapter.java`, `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`, `src/test/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapterTest.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/alarm/AlarmSuppressionWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/alarm/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapter.java`
- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 10. ALM-SUP-005 completion evidence

ALM-SUP-005 was executed against HidraAPI baseline `c7ebe34e27e56506e70ad6d9de54988bdd3642e1`.

The implementation follows the repository's existing deliberate cross-module Workflow contract pattern:

- Workflow exports `AlarmSuppressionWorkflowContract` rather than exposing Workflow persistence or aggregates to Alarm;
- `AlarmSuppressionWorkflowContractAdapter` validates the instance targets module `alarm`, target type `ALARM_SUPPRESSION`, and the exact backend operation reference;
- the Workflow instance must be `COMPLETED`;
- the final timeline decision must be `APPROVE`;
- Alarm consumes the narrow contract through `AlarmSuppressionApprovalService`;
- time-bounded suppression does not invoke Workflow;
- open-ended suppression fails closed if approval evidence is absent, mismatched, pending, or rejected.

The stable approval operation reference is backend-derived as `<SCOPE_TYPE>:<scopeReferenceId>`, preventing approval evidence from being reused for a different suppression scope/reference. No Workflow transition-name inference and no direct Workflow domain/persistence dependency is introduced.

No scheduler, suppression mutation service, REST endpoint, permission publication, migration, or OpenAPI change is included.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `37580030f84acb66953b03b5a2e29f543ecb1d56` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:182](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L182)
- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:263](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L263)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:182 | ALM-SUP-005 | feat(alarm): integrate suppression workflow approval | Verify open-ended suppression through Workflow public contracts/ports and fail closed when approval is absent. | No transition-name inference or direct foreign aggregate dependency. | **Completed**
~~~~~~~~

<a id="hapi-006"></a>
## HAPI-006 — add suppression expiry orchestration

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-006

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add idempotent backend-owned expiry orchestration using the existing Spring scheduling mechanism.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJob.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestrator.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/AlarmSuppressionAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`
- `src/main/java/dz/sh/hidra/platform/realtime/HidraRealtimeConfiguration.java`
- `src/main/java/dz/sh/hidra/platform/realtime/HidraRealtimeHeartbeatJob.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJobTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJob.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestrator.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJob.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestrator.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/AlarmSuppressionAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`
- `src/main/java/dz/sh/hidra/platform/realtime/HidraRealtimeConfiguration.java`
- `src/main/java/dz/sh/hidra/platform/realtime/HidraRealtimeHeartbeatJob.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJobTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 11. ALM-SUP-006 precondition audit

ALM-SUP-006 was re-audited against HidraAPI baseline `5a11a1bf0030355a05b8c10ab81a4c3c421cb52d` before any production-code change.

The first precondition audit incorrectly concluded that no scheduling mechanism was configured. A deeper live-source inspection corrected that finding:

- `HidraRealtimeConfiguration` already enables Spring scheduling with `@EnableScheduling`;
- `HidraRealtimeHeartbeatJob` already uses `@Scheduled`.

Therefore the repository already has an approved platform scheduling mechanism that Alarm can reuse without introducing a second scheduler.

The remaining real prerequisite was suppression expiry audit evidence for every scope:
   - `AlarmSuppressionJpaEntity` records creation actor and manual release actor, but has no expiry actor/time fields;
   - `AlarmLifecycleEvent` can represent ALARM-scoped `UNSUPPRESSED` evidence because an alarm id exists;
   - broad scopes (`ALARM_TYPE`, `TOPOLOGY_ASSET`, `MONITORING_RULE`, `SOURCE`) have no alarm instance on which to attach that lifecycle event;
   - no exported Alarm-specific Audit contract or suppression lifecycle event persistence currently exists.

ALM-SUP-006A resolves the remaining prerequisite by exporting `Audit.application.contract.alarm.AlarmSuppressionAuditContract`. Its Audit-owned adapter:

- records target type `ALARM_SUPPRESSION` and the suppression id;
- records the exact suppression scope as target code `<scopeType>:<scopeReferenceId>`;
- attributes automatic expiry to `AuditActorType.SCHEDULED_JOB`;
- records decision `EXPIRED`;
- resolves `ALARM_SUPPRESSION_EXPIRED` and `BUSINESS` through active Audit taxonomy and fails closed if taxonomy is unavailable.

Migration `V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql` provisions the required event type idempotently. The Audit contract package is explicitly exported through the repository architecture guardrails.

Release fields remain reserved for manual release; automatic expiry is represented by status plus canonical Audit evidence.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJobTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `6d18f11baf12075ec1ac12cdaeab2d8edd256163` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:184](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L184)
- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:281](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L281)
- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:319](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L319)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:184 | ALM-SUP-006 | feat(alarm): add suppression expiry orchestration | Add idempotent backend-owned expiry orchestration using the existing Spring scheduling mechanism. | ACTIVE suppressions expire once; Audit evidence, actor attribution, and ALARM restoration are deterministic. | **Completed**
~~~~~~~~

<a id="hapi-007"></a>
## HAPI-007 — add suppression expiry audit contract

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-006A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Export a narrow Audit contract and provision canonical ALARM_SUPPRESSION_EXPIRED taxonomy for all suppression scopes.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/AlarmSuppressionAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/AlarmSuppressionAuditContractAdapter.java`
- `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/AlarmSuppressionAuditContractAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/AlarmSuppressionAuditContractAdapter.java`, `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/AlarmSuppressionAuditContractAdapterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/AlarmSuppressionAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/AlarmSuppressionAuditContractAdapter.java`
- `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/AlarmSuppressionAuditContractAdapterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

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
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/AlarmSuppressionAuditContractAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `dbea0996384c9b09d14cc1d52745d76620226441` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:183](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L183)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:183 | ALM-SUP-006A | feat(alarm): add suppression expiry audit contract | Export a narrow Audit contract and provision canonical `ALARM_SUPPRESSION_EXPIRED` taxonomy for all suppression scopes. | Broad-scope expiry can be attributed to a scheduled/system actor without overloading release fields. | **Completed**
~~~~~~~~

<a id="hapi-008"></a>
## HAPI-008 — publish suppression REST contracts

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-007

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Publish canonical create/release/query endpoints, generated OpenAPI schemas, route-permission metadata, deterministic 400/403/404/409 behavior.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmSuppressionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`
- `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmSuppressionConflictException.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`, `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapter.java`, `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmSuppressionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`
- `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 15. ALM-SUP-007 completion evidence

ALM-SUP-007 was executed against HidraAPI baseline `6d18f11baf12075ec1ac12cdaeab2d8edd256163`.

REST contract:

- `POST /api/v1/alarm/suppressions` creates a governed suppression;
- `POST /api/v1/alarm/suppressions/{suppressionId}/release` releases an ACTIVE suppression;
- `GET /api/v1/alarm/suppressions/{suppressionId}` returns suppression evidence;
- `GET /api/v1/alarm/suppressions` supports paged filtering by id, scope type/reference, alarm id, and status.

The API derives actor identity exclusively through `CurrentActorResolver`; actor ids are not accepted from request bodies.

HRA-061 is preserved by `AlarmSuppressionApplicationAdapter`: it implements the existing application input ports from infrastructure and owns access to the retained suppression JPA model. Controllers depend only on application input ports and never access JPA repositories.

Lifecycle behavior:

- exact ACTIVE overlap is pre-checked and additionally enforced by partial unique index `ux_alarm_suppression_active_scope`;
- both overlap paths map to stable HTTP `409 ALARM_SUPPRESSION_CONFLICT`;
- ALARM-scoped create sets the existing nonterminal Alarm state to `SUPPRESSED` and appends a `SUPPRESSED` lifecycle event;
- ALARM-scoped release restores authoritative state through `AlarmSuppressionPolicy.restorationState` when the Alarm is still visibly `SUPPRESSED`, then appends `UNSUPPRESSED`;
- broad scopes do not mass-mutate Alarm rows;
- open-ended creation continues to fail closed through `AlarmSuppressionApprovalService`;
- release uses a pessimistic row lock and rejects already-terminal suppression state.

Permission reconciliation:

The platform derives route permissions from URL + HTTP method. The published canonical route permissions are therefore:

- `alarm:suppressions:read` for GET routes;
- `alarm:suppressions:execute` for POST create/release routes.

These platform-derived names supersede the earlier conceptual `alarm:suppression:read/create/release` intent; no conflicting manual permission seed is introduced.

OpenAPI publication remains automatic through Spring MVC/springdoc registration of these routes.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `c8bc04e8e9659ac211c3e41238606b2d757e5083` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:185](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L185)
- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:351](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L351)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:185 | ALM-SUP-007 | feat(alarm): publish suppression REST contracts | Publish canonical create/release/query endpoints, generated OpenAPI schemas, route-permission metadata, deterministic 400/403/404/409 behavior. | No CRUD-style leakage; only approved lifecycle operations exposed. | **Completed**
~~~~~~~~

<a id="hapi-009"></a>
## HAPI-009 — verify suppression lifecycle end to end

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-008

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Domain/application/persistence/REST/Testcontainers coverage including ALARM and broad scopes, acknowledgement, clear/close, escalation preservation, workflow approval, expiry, conflicts, audit evidence, OpenAPI determinism.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionRoutePermissionTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`, `src/test/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapterTest.java`.
- Primary/secondary adapters: `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java`, `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionRoutePermissionTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 17. ALM-SUP-008 completion evidence

ALM-SUP-008 was executed against HidraAPI baseline `a5de4d1b1024b854bf8917141438f7cfb527baf2`.

The end-to-end verification layer now combines the focused tests added throughout ALM-SUP-003 through ALM-SUP-007 with new database-backed and route-permission evidence:

- `AlarmSuppressionPolicyTest` covers creation rules, exact-scope matching, expiry eligibility, overlap/release guards, restoration precedence, acknowledgement/escalation, and suppressed-close semantics;
- `AlarmSuppressionApprovalServiceTest` and `AlarmSuppressionWorkflowContractAdapterTest` cover approved open-ended Workflow evidence and fail-closed rejection paths;
- `AlarmSuppressionExpiryOrchestratorTest` covers broad-scope expiry and ALARM-scoped restoration/lifecycle evidence;
- `AlarmSuppressionControllerTest` proves REST commands derive actor identity from `CurrentActorResolver`;
- `AlarmSuppressionApiExceptionHandlerTest` proves stable HTTP 409 conflict mapping;
- `AlarmSuppressionPersistenceMigrationTest` migrates an empty PostgreSQL Testcontainer and proves:
  - the canonical `ALARM_SUPPRESSION_EXPIRED` Audit taxonomy is provisioned;
  - the partial unique index prevents a second exact-scope ACTIVE suppression;
  - historical terminal suppression rows for the same scope/reference remain allowed;
- `AlarmSuppressionRoutePermissionTest` proves canonical route-derived permissions:
  - `alarm:suppressions:read`;
  - `alarm:suppressions:execute`.

This task adds verification only. No suppression production semantics are changed.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionRoutePermissionTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AlarmSuppressionWorkflowContractAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `4c2a4d47abf6f624c2e47ba631ed48aa7e24521f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:186](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L186)
- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:396](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L396)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:186 | ALM-SUP-008 | test(alarm): verify suppression lifecycle end to end | Domain/application/persistence/REST/Testcontainers coverage including ALARM and broad scopes, acknowledgement, clear/close, escalation preservation, workflow approval, expiry, conflicts, audit evidence, OpenAPI determinism. | Full repository verification green and suppression behavior traceable. | **Completed**
~~~~~~~~

<a id="hapi-010"></a>
## HAPI-010 — finalize suppression capability

Phase: HPS-011 — Alarms and Events

Legacy IDs: ALM-SUP-009

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Record exact endpoints, permissions, events, validation evidence, remaining limits, and frontend-consumption boundary.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/AlarmSuppressionAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/alarm/AlarmSuppressionWorkflowContract.java`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/GAP-ALARM-004-suppression-semantics.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/alarm/AlarmSuppressionAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/alarm/AlarmSuppressionWorkflowContract.java`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 19. ALM-SUP-009 final capability record

ALM-SUP-009 finalizes GAP-ALARM-004 against verified repository baseline `5028a90248ddf3a04d344308f6cfc2510f1d0ca2`.

### 19.1 Verified implementation chain

The implemented suppression capability is traceable through the following primary commits:

- `e10f1ba9c738337b1dea9f57c1184cb5fe49d0b4` — `docs(alarm): approve suppression lifecycle semantics`;
- `283307fe9f08c6405060699d2cd6e1462d61f637` — `feat(alarm): add suppression application contracts`;
- `b88af5dc5f7eb8d622cc343041f2ae788ad38fcd` — `feat(alarm): implement suppression lifecycle policy`;
- `6f4bdacae0000fdf7f25698d863695fb8a5454c0` plus HRA-061 corrective commits — suppression persistence query support;
- `37580030f84acb66953b03b5a2e29f543ecb1d56` plus architecture-export correctives — Workflow approval integration;
- `dbea0996384c9b09d14cc1d52745d76620226441` — suppression expiry Audit contract and taxonomy;
- `6d18f11baf12075ec1ac12cdaeab2d8edd256163` — backend-owned suppression expiry orchestration;
- `c8bc04e8e9659ac211c3e41238606b2d757e5083` plus `a5de4d1b1024b854bf8917141438f7cfb527baf2` — REST publication and Spring proxy corrective;
- `4c2a4d47abf6f624c2e47ba631ed48aa7e24521f` plus `5028a90248ddf3a04d344308f6cfc2510f1d0ca2` — end-to-end verification and route-handler test corrective.

Repository CI run **#451** (`36996917162`) completed successfully on
`5028a90248ddf3a04d344308f6cfc2510f1d0ca2`, establishing the green verification gate used to close this roadmap.

### 19.2 Published backend contract

Canonical REST routes:

```text
POST /api/v1/alarm/suppressions
POST /api/v1/alarm/suppressions/{suppressionId}/release
GET  /api/v1/alarm/suppressions/{suppressionId}
GET  /api/v1/alarm/suppressions
```

Canonical route-derived permissions:

```text
alarm:suppressions:read
alarm:suppressions:execute
```

The earlier conceptual permission names `alarm:suppression:read/create/release` are superseded by the platform's canonical route-permission derivation and must not be independently seeded.

Authenticated actor identity is server-derived through `CurrentActorResolver`. Browser-supplied actor attribution is not part of the contract.

### 19.3 Workflow and Audit integration

Open-ended suppression remains fail-closed and requires Workflow evidence through the exported
`AlarmSuppressionWorkflowContract`. Approval is based on backend-owned instance status, decision evidence, target module/type, and exact operation reference; transition-name inference is prohibited.

Automatic expiry uses the exported `AlarmSuppressionAuditContract` and canonical
`ALARM_SUPPRESSION_EXPIRED` Audit taxonomy. Expiry actor type is `SCHEDULED_JOB`; manual release actor fields are not overloaded for automatic expiry.

### 19.4 Scheduling and lifecycle evidence

The capability reuses the repository's existing Spring scheduling infrastructure.

Automatic expiry:

- selects only ACTIVE due suppressions;
- pessimistically locks selected rows;
- re-checks expiry eligibility;
- restores ALARM-scoped state from authoritative Alarm evidence when the alarm is still visibly `SUPPRESSED`;
- appends `UNSUPPRESSED` lifecycle evidence for ALARM restoration;
- appends canonical Audit expiry evidence;
- transitions suppression status to `EXPIRED`;
- leaves manual release fields untouched;
- remains retry/idempotency oriented because terminal rows are not selected again.

Manual ALARM-scoped release similarly restores authoritative state when appropriate and records `UNSUPPRESSED` evidence.

### 19.5 Persistence and conflict guarantees

Suppression persistence remains an HRA-061 retained infrastructure model. No retired domain/application persistence mirror is reintroduced.

Exact-scope ACTIVE overlap is protected by both application pre-check and PostgreSQL partial unique index
`ux_alarm_suppression_active_scope`. Conflict is exposed as stable HTTP
`409 ALARM_SUPPRESSION_CONFLICT`.

Historical RELEASED/EXPIRED/CANCELLED rows remain available and do not prevent later suppression for the same exact scope/reference.

### 19.6 Frontend consumption boundary

HidraWEB and other clients must consume the REST/OpenAPI contract and must not:

- calculate suppression matching independently;
- derive restoration state;
- fabricate actor ids;
- infer Workflow approval from labels or transition names;
- mutate suppression persistence directly;
- treat shelving as suppression;
- assume broad-scope suppression means mass state mutation of existing alarms.

The backend remains authoritative for lifecycle policy, approval, expiry, conflict detection, state restoration, actor attribution, and Audit evidence.

### 19.7 Known residual boundaries

GAP-ALARM-004 closes the suppression lifecycle/API capability. The following are not claimed as newly implemented by this roadmap:

- wiring broad-scope suppression matching into every alarm-generation producer/evaluation path; the deterministic matching policy exists, but each producer boundary must explicitly consume it before claiming generation-time suppression coverage;
- a universal include/exclude-suppressed flag on all existing active-alarm query endpoints; suppression evidence has dedicated query endpoints, while any additional active-alarm filtering must be introduced through the owning query contract rather than client-side filtering;
- SCADA/PLC actuation or parked industrial-extension work.

These boundaries are not permission to implement them implicitly under this closed roadmap. Any required expansion must be separately authorized and traced.

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

- `5e301857882b59e9e35ecc474e9c6537d89cc96a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:187](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L187)
- [docs/roadmap/GAP-ALARM-004-suppression-semantics.md:426](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/GAP-ALARM-004-suppression-semantics.md#L426)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/GAP-ALARM-004-suppression-semantics.md:187 | ALM-SUP-009 | docs(alarm): finalize suppression capability | Record exact endpoints, permissions, events, validation evidence, remaining limits, and frontend-consumption boundary. | GAP-ALARM-004 closed with no undocumented semantics. | **Completed**
~~~~~~~~

<a id="hapi-147"></a>
## HAPI-147 — scan alarm domain models

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMC-002

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 4 alarm/domain/model files; record models, fields and Java types.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 11. HMC-002 — Alarm module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `alarm`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 67  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Alarm model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| Alarm | record | 37 | `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java` | Extracted |
| AlarmAcknowledgement | record | 9 | `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java` | Extracted |
| AlarmClosure | record | 10 | `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java` | Extracted |
| AlarmShelving | record | 11 | `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java` | Extracted |

### Alarm fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| Alarm | 1 | id | String |
| Alarm | 2 | alarmNumber | String |
| Alarm | 3 | alarmTypeId | String |
| Alarm | 4 | severityId | String |
| Alarm | 5 | priorityId | String |
| Alarm | 6 | titleAr | String |
| Alarm | 7 | titleFr | String |
| Alarm | 8 | titleEn | String |
| Alarm | 9 | descriptionAr | String |
| Alarm | 10 | descriptionFr | String |
| Alarm | 11 | descriptionEn | String |
| Alarm | 12 | sourceType | AlarmSourceType |
| Alarm | 13 | sourceReferenceId | String |
| Alarm | 14 | monitoringAlertCandidateId | String |
| Alarm | 15 | monitoringEvaluationId | String |
| Alarm | 16 | telemetryReadingId | String |
| Alarm | 17 | planningTargetId | String |
| Alarm | 18 | topologyAssetTypeCode | String |
| Alarm | 19 | topologyAssetId | String |
| Alarm | 20 | topologyAssetCode | String |
| Alarm | 21 | topologyAssetNameSnapshot | String |
| Alarm | 22 | currentState | AlarmState |
| Alarm | 23 | raisedAt | Instant |
| Alarm | 24 | firstDetectedAt | Instant |
| Alarm | 25 | lastUpdatedAt | Instant |
| Alarm | 26 | clearedAt | Instant |
| Alarm | 27 | closedAt | Instant |
| Alarm | 28 | acknowledgedAt | Instant |
| Alarm | 29 | acknowledgedByActorId | String |
| Alarm | 30 | owningOrganizationUnitId | String |
| Alarm | 31 | owningOrganizationUnitCode | String |
| Alarm | 32 | owningOrganizationUnitNameSnapshot | String |
| Alarm | 33 | workflowInstanceId | String |
| Alarm | 34 | incidentId | String |
| Alarm | 35 | correlationId | String |
| Alarm | 36 | createdAt | Instant |
| Alarm | 37 | updatedAt | Instant |
| AlarmAcknowledgement | 1 | id | String |
| AlarmAcknowledgement | 2 | alarmId | String |
| AlarmAcknowledgement | 3 | acknowledgedByActorId | String |
| AlarmAcknowledgement | 4 | acknowledgedByDisplayName | String |
| AlarmAcknowledgement | 5 | organizationUnitId | String |
| AlarmAcknowledgement | 6 | organizationUnitCode | String |
| AlarmAcknowledgement | 7 | acknowledgedAt | Instant |
| AlarmAcknowledgement | 8 | comment | String |
| AlarmAcknowledgement | 9 | correlationId | String |
| AlarmClosure | 1 | id | String |
| AlarmClosure | 2 | alarmId | String |
| AlarmClosure | 3 | closureType | AlarmClosureType |
| AlarmClosure | 4 | closureReasonId | String |
| AlarmClosure | 5 | closureComment | String |
| AlarmClosure | 6 | closedByActorId | String |
| AlarmClosure | 7 | closedAt | Instant |
| AlarmClosure | 8 | requiresReview | boolean |
| AlarmClosure | 9 | reviewWorkflowInstanceId | String |
| AlarmClosure | 10 | correlationId | String |
| AlarmShelving | 1 | id | String |
| AlarmShelving | 2 | alarmId | String |
| AlarmShelving | 3 | shelvingReasonId | String |
| AlarmShelving | 4 | reasonText | String |
| AlarmShelving | 5 | shelvedByActorId | String |
| AlarmShelving | 6 | shelvedAt | Instant |
| AlarmShelving | 7 | shelvedUntil | Instant |
| AlarmShelving | 8 | unshelvedAt | Instant |
| AlarmShelving | 9 | unshelvedByActorId | String |
| AlarmShelving | 10 | status | AlarmShelvingStatus |
| AlarmShelving | 11 | correlationId | String |

### HMC-002 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 67
zero-field models    = 0
```

The scan records only declarations under `alarm/domain/model`. Alarm domain values such as
`AlarmState`, `AlarmSourceType`, `AlarmClosureType`, and `AlarmShelvingStatus` are field types,
not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, suppression infrastructure model, or database
column metadata was mixed into this module scan.

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

- `bef21d93f13c4025e4d8c561c3117abb0fc82de2` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:130](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L130)
- [docs/roadmap/model-field-catalogue.md:271](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L271)
- [docs/roadmap/model-field-catalogue.md:362](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L362)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:130 | HMC-002 | docs(catalogue): scan alarm domain models | Scan all 4 `alarm/domain/model` files; record models, fields and Java types. | **Completed** — 4/4 models, 67 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-161"></a>
## HAPI-161 — scan notification domain models

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMC-016

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 4 notification model files.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/DeliveryAttemptStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/DeliveryAttemptStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 25. HMC-016 — Notification module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `notification`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 66  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Notification model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| NotificationDeliveryAttempt | record | 14 | `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java` | Extracted |
| NotificationMessage | record | 17 | `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java` | Extracted |
| NotificationRequest | record | 22 | `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java` | Extracted |
| NotificationTemplate | record | 13 | `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java` | Extracted |

### Notification fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| NotificationDeliveryAttempt | 1 | id | String |
| NotificationDeliveryAttempt | 2 | messageId | String |
| NotificationDeliveryAttempt | 3 | attemptNumber | int |
| NotificationDeliveryAttempt | 4 | channelId | String |
| NotificationDeliveryAttempt | 5 | providerReference | String |
| NotificationDeliveryAttempt | 6 | providerMessageId | String |
| NotificationDeliveryAttempt | 7 | attemptStatus | DeliveryAttemptStatus |
| NotificationDeliveryAttempt | 8 | attemptedAt | Instant |
| NotificationDeliveryAttempt | 9 | completedAt | Instant |
| NotificationDeliveryAttempt | 10 | failureCode | String |
| NotificationDeliveryAttempt | 11 | failureMessage | String |
| NotificationDeliveryAttempt | 12 | nextRetryAt | Instant |
| NotificationDeliveryAttempt | 13 | correlationId | String |
| NotificationDeliveryAttempt | 14 | createdAt | Instant |
| NotificationMessage | 1 | id | String |
| NotificationMessage | 2 | requestId | String |
| NotificationMessage | 3 | recipientId | String |
| NotificationMessage | 4 | channelId | String |
| NotificationMessage | 5 | templateId | String |
| NotificationMessage | 6 | templateVersionId | String |
| NotificationMessage | 7 | locale | String |
| NotificationMessage | 8 | subjectRendered | String |
| NotificationMessage | 9 | bodyRendered | String |
| NotificationMessage | 10 | shortTextRendered | String |
| NotificationMessage | 11 | payloadHash | String |
| NotificationMessage | 12 | priorityId | String |
| NotificationMessage | 13 | status | NotificationMessageStatus |
| NotificationMessage | 14 | scheduledAt | Instant |
| NotificationMessage | 15 | expiresAt | Instant |
| NotificationMessage | 16 | createdAt | Instant |
| NotificationMessage | 17 | updatedAt | Instant |
| NotificationRequest | 1 | id | String |
| NotificationRequest | 2 | sourceModule | String |
| NotificationRequest | 3 | sourceEventType | String |
| NotificationRequest | 4 | sourceEventId | String |
| NotificationRequest | 5 | targetType | String |
| NotificationRequest | 6 | targetId | String |
| NotificationRequest | 7 | targetCodeSnapshot | String |
| NotificationRequest | 8 | targetLabelSnapshot | String |
| NotificationRequest | 9 | categoryId | String |
| NotificationRequest | 10 | priorityId | String |
| NotificationRequest | 11 | policyId | String |
| NotificationRequest | 12 | templateId | String |
| NotificationRequest | 13 | templateVersionId | String |
| NotificationRequest | 14 | requestedByActorId | String |
| NotificationRequest | 15 | requestedByDisplayNameSnapshot | String |
| NotificationRequest | 16 | requestedAt | Instant |
| NotificationRequest | 17 | correlationId | String |
| NotificationRequest | 18 | requestId | String |
| NotificationRequest | 19 | status | NotificationRequestStatus |
| NotificationRequest | 20 | expiresAt | Instant |
| NotificationRequest | 21 | createdAt | Instant |
| NotificationRequest | 22 | updatedAt | Instant |
| NotificationTemplate | 1 | id | String |
| NotificationTemplate | 2 | code | String |
| NotificationTemplate | 3 | nameAr | String |
| NotificationTemplate | 4 | nameFr | String |
| NotificationTemplate | 5 | nameEn | String |
| NotificationTemplate | 6 | templateTypeId | String |
| NotificationTemplate | 7 | categoryId | String |
| NotificationTemplate | 8 | defaultChannelId | String |
| NotificationTemplate | 9 | status | NotificationTemplateStatus |
| NotificationTemplate | 10 | currentVersion | Integer |
| NotificationTemplate | 11 | systemDefined | boolean |
| NotificationTemplate | 12 | createdAt | Instant |
| NotificationTemplate | 13 | updatedAt | Instant |

### HMC-016 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 66
zero-field models    = 0
```

The scan records only declarations under `notification/domain/model`. Notification domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure provider adapter, channel implementation, or database column metadata was mixed into this module scan.

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

- `639e49e212b796379e81016a3cf1623e45997abb` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:144](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L144)
- [docs/roadmap/model-field-catalogue.md:1835](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L1835)
- [docs/roadmap/model-field-catalogue.md:1925](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L1925)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:144 | HMC-016 | docs(catalogue): scan notification domain models | Scan all 4 notification model files. | **Completed** — 4/4 models, 66 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-189"></a>
## HAPI-189 — remediate semantic review NotificationTemplate

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMR-012

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review NotificationTemplate

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Notification.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationTemplateRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateVersionJpaRepository.java`
- `src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationTemplateSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateVersionStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationTemplateRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateTranslationJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateVersionJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Notification.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationTemplateRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateVersionJpaRepository.java`
- `src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationTemplateSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-012 — notification.NotificationTemplate

- Source review: `HMSR-012`
- Exact commit: `fix(notification): remediate semantic review NotificationTemplate`
- Status: **Completed** — template code uniqueness is enforced at persistence and database boundaries; ACTIVE templates require a governed current ACTIVE version; request/message template selections fail closed on inactive templates or mismatched/ineligible versions; template/category catalog family membership and active default-channel integrity are enforced; French label/timestamps are intrinsic domain invariants.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 8
- Additive Flyway: `src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Notification.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateVersionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationTemplateRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateTranslationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateTranslationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateVersionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationTemplateSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NotificationTemplateSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Unique template code:** enforce the DDD-defined uniqueness of `NotificationTemplate.code` at the application/database boundary.
  2. **ACTIVE-only template selection:** request/message creation must validate that a template used for new message generation is ACTIVE; the current helper is not wired into orchestration.
  3. **Exact template-version integrity:** validate that `templateVersionId` exists, belongs to the selected `templateId`, and is eligible under the version lifecycle before rendering/sending.
  4. **`currentVersion` governance:** define and enforce the exact meaning of `currentVersion` and its consistency with template-version rows and template lifecycle.
  5. **Catalog-family validation:** `templateTypeId` must resolve to `TEMPLATE_TYPE`; when present, `categoryId` must resolve to `NOTIFICATION_CATEGORY`, not merely to any catalog row.
  6. **Optional channel-reference integrity:** when `defaultChannelId` is present and used, it must resolve to the intended NotificationChannel and satisfy the applicable active/availability policy.
  7. **French label persistence boundary:** reconcile the persistence-required `nameFr` with the domain constructor's nullable behavior without confusing label semantics with version-content localization.
  8. **Required timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required but not guarded by the record constructor.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationTemplateSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `539c553dfa88183ebfaab25a094d73808109449f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:158](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L158)
- [docs/roadmap/model-semantic-remediation.md:924](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L924)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:158 | HMR-012 | HMSR-012 | notification | NotificationTemplate | — | — | fix(notification): remediate semantic review NotificationTemplate | **Completed**
~~~~~~~~

<a id="hapi-219"></a>
## HAPI-219 — remediate semantic review NotificationRequest

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMR-035

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review NotificationRequest

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Notification.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/ReceiveNotificationRequestRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/ReceiveNotificationRequestCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestRecipientJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestRecipientJpaRepository.java`
- `src/main/resources/db/migration/V20261004_035__hmr_035_notification_notification_request.sql`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationRequestSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationRequestRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestRecipientJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Notification.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/ReceiveNotificationRequestRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/ReceiveNotificationRequestCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestRecipientJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestRecipientJpaRepository.java`
- `src/main/resources/db/migration/V20261004_035__hmr_035_notification_notification_request.sql`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationRequestSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-035 — notification.NotificationRequest

- Source review: `HMSR-039`
- Exact commit: `fix(notification): remediate semantic review NotificationRequest`
- Status: **Completed** — source context, category/priority catalog families, and populated policy/template/template-version references are enforced at write and database boundaries.
- SCC: —
- Recorded upstream HMS dependencies: notification.NotificationTemplate
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_035__hmr_035_notification_notification_request.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Notification.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/ReceiveNotificationRequestRequest.java`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationRequestResponse.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/command/ReceiveNotificationRequestCommand.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationRequestSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationRequestRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestRecipientJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestRecipientJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_035__hmr_035_notification_notification_request.sql`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationRequestSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NotificationRequestSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require nonblank source-module and source-event-type semantics at the authoritative domain/application boundary.** `sourceModule` and `sourceEventType` must not normalize to null while the DDD and final persistence contract require the source event context.
  2. **Enforce Notification category/priority catalog semantics.** Mandatory `categoryId` must resolve specifically to `NOTIFICATION_CATEGORY`; when optional `priorityId` is populated, it must resolve to `NOTIFICATION_PRIORITY`. Generic category row existence and an unprotected priority ID are insufficient.
  3. **Protect populated NotificationPolicy references.** When `policyId` is supplied, it must resolve to the Notification-owned policy target through an additive nullable same-module FK, application validation, or equivalent fail-closed boundary.
  4. **Protect populated NotificationTemplate references.** When `templateId` is supplied, it must resolve to the reviewed NotificationTemplate subject rather than allowing a dangling HMS dependency.
  5. **Protect populated NotificationTemplateVersion references.** When `templateVersionId` is supplied, it must resolve to the retained immutable template-version target; HMSR-039 does not additionally impose parent-pair semantics absent stronger DDD evidence.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationRequestSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `d3a7331ffd7e1be410e50c9449509f5d2eb18751` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:186](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L186)
- [docs/roadmap/model-semantic-remediation.md:2011](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2011)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:186 | HMR-035 | HMSR-039 | notification | NotificationRequest | — | notification.NotificationTemplate | fix(notification): remediate semantic review NotificationRequest | Planned
~~~~~~~~

<a id="hapi-246"></a>
## HAPI-246 — remediate semantic review NotificationMessage

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMR-052

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review NotificationMessage

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/CreateNotificationMessageRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/CreateNotificationMessageCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationMessageRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageVariableJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageVariableJpaRepository.java`
- `src/main/resources/db/migration/V20261006_005__hmr_052_notification_message_composition.sql`
- `src/main/resources/db/migration/V20261006_007__hmr_052_qualify_message_validator_parameter.sql`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationMessageRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationMessageRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageVariableJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/CreateNotificationMessageRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/CreateNotificationMessageCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationMessageRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageVariableJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageVariableJpaRepository.java`
- `src/main/resources/db/migration/V20261006_005__hmr_052_notification_message_composition.sql`
- `src/main/resources/db/migration/V20261006_007__hmr_052_qualify_message_validator_parameter.sql`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-052 — notification.NotificationMessage

- Source review: `HMSR-061`
- Exact commit: `fix(notification): remediate semantic review NotificationMessage`
- Status: **Completed — implemented; final Batch 2 CI pending**
- SCC: —
- Recorded upstream HMS dependencies: notification.NotificationRequest, notification.NotificationTemplate
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261006_005__hmr_052_notification_message_composition.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Notification.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/CreateNotificationMessageRequest.java`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationMessageResponse.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/command/CreateNotificationMessageCommand.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationMessageSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationMessageRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationMessageRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageVariableJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageVariableJpaRepository.java`
  - `src/main/resources/db/migration/V20261006_005__hmr_052_notification_message_composition.sql`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageIntegrityMigrationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NotificationMessageSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce request/recipient consistency.** A `recipientId` must identify a NotificationRequestRecipient owned by the same `requestId` carried by the message; independent FK existence is insufficient.
  2. **Enforce exact template-version traceability before READY/SCHEDULED/dispatch for template-rendered messages.** Resolve `templateVersionId`, preserve its relationship to `templateId`, and prevent a rendered/template-backed message from progressing without the exact immutable version used.
  3. **Enforce required template variables before READY.** Validate the selected template version's required variable contract against the message-owned variable set before the message can enter a sendable state.
  4. **Validate populated `priorityId` against the `NOTIFICATION_PRIORITY` family and eligibility semantics.**

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageIntegrityMigrationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `74e916bf5d892782353662ef935ed79110cb2c40` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:87](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L87)
- [doc/model-remediation/RECONCILIATION.md:13699](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13699)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15439](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15439)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:86](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L86)
- [docs/roadmap/model-semantic-remediation.md:208](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L208)
- [docs/roadmap/model-semantic-remediation.md:2998](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2998)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:208 | HMR-052 | HMSR-061 | notification | NotificationMessage | — | notification.NotificationRequest, notification.NotificationTemplate | fix(notification): remediate semantic review NotificationMessage | Completed — Batch 2; composition/version/required-input/eligible-priority guards; CI pending
doc/model-remediation/RECONCILIATION.md:87 | HMR-052 | HMSR-061 | notification.NotificationMessage | COMPLETED — HPR-P2-008 | Recipient/request composite FK; exact-version/template FK and pre-dispatch composition guard; required-input schema checked for sendable states; inputs/version frozen; active NOTIFICATION_PRIORITY eligibility; V20261006_005; dedicated unit and PostgreSQL tests added.
doc/model-remediation/RECONCILIATION.md:13699 | HMR-052 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:86 | HMR-052 | HMSR-061 | [notification.NotificationMessage](../domain/SEMANTIC_DECISIONS.md#notification-notificationmessage) | [NotificationMessageSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java) | [V20261006_005__hmr_052_notification_message_composition](../../src/main/resources/db/migration/V20261006_005__hmr_052_notification_message_composition.sql); [V20261006_007__hmr_052_qualify_message_validator_parameter](../../src/main/resources/db/migration/V20261006_007__hmr_052_qualify_message_validator_parameter.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15439 | HMR-052 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-254"></a>
## HAPI-254 — remediate semantic review NotificationDeliveryAttempt

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMR-060

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review NotificationDeliveryAttempt

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationDeliveryAttemptResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationDeliveryAttemptSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationDeliveryAttemptRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationDeliveryAttemptJpaRepository.java`
- `src/main/resources/db/migration/V20261006_006__hmr_060_notification_attempt_evidence.sql`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationDeliveryAttemptRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationDeliveryAttemptJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationDeliveryAttemptResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationDeliveryAttemptSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationDeliveryAttemptRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationDeliveryAttemptJpaRepository.java`
- `src/main/resources/db/migration/V20261006_006__hmr_060_notification_attempt_evidence.sql`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-060 — notification.NotificationDeliveryAttempt

- Source review: `HMSR-072`
- Exact commit: `fix(notification): remediate semantic review NotificationDeliveryAttempt`
- Status: **Completed — implemented; final Batch 2 CI pending**
- SCC: —
- Recorded upstream HMS dependencies: notification.NotificationMessage
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261006_006__hmr_060_notification_attempt_evidence.sql` — admitted by the user-authorized P2 Batch 2 envelope after live schema reconciliation.
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Notification.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationDeliveryAttemptResponse.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationDeliveryAttemptSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationDeliveryAttemptRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationDeliveryAttemptJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java`
  - `src/main/resources/db/migration/V20261006_006__hmr_060_notification_attempt_evidence.sql`
  - `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptIntegrityMigrationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=NotificationDeliveryAttemptSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce message/channel consistency.** The attempt's `channelId` must match the referenced NotificationMessage's selected channel unless an explicitly modeled future failover rule authorizes otherwise.
  2. **Enforce append-only delivery-attempt persistence.** Existing attempt evidence must not be mutable through a generic save/update path.
  3. **Prevent automatic retry scheduling after permanent failure/cancellation.** `FAILED_PERMANENT` and `CANCELLED` attempts must not carry an automatic `nextRetryAt`; manual requeue must be represented through the explicit Notification retry workflow.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptIntegrityMigrationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `058fe3b5e4fae3790a16852f5107720429cac99d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:95](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L95)
- [doc/model-remediation/RECONCILIATION.md:13707](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13707)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15447](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15447)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:94](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L94)
- [docs/roadmap/model-semantic-remediation.md:221](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L221)
- [docs/roadmap/model-semantic-remediation.md:3315](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3315)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:221 | HMR-060 | HMSR-072 | notification | NotificationDeliveryAttempt | — | notification.NotificationMessage | fix(notification): remediate semantic review NotificationDeliveryAttempt | Completed — Batch 2; channel-consistent create-only evidence and permanent-retry guard; CI pending
doc/model-remediation/RECONCILIATION.md:95 | HMR-060 | HMSR-072 | notification.NotificationDeliveryAttempt | COMPLETED — HPR-P2-008 | Channel/message composite FK; create-only EntityManager.persist plus PK race protection; update/delete/truncate rejected; permanent/cancelled automatic retry rejected; V20261006_006; dedicated unit and PostgreSQL tests added.
doc/model-remediation/RECONCILIATION.md:13707 | HMR-060 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:94 | HMR-060 | HMSR-072 | [notification.NotificationDeliveryAttempt](../domain/SEMANTIC_DECISIONS.md#notification-notificationdeliveryattempt) | [NotificationDeliveryAttemptSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java) | [V20261006_006__hmr_060_notification_attempt_evidence](../../src/main/resources/db/migration/V20261006_006__hmr_060_notification_attempt_evidence.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15447 | HMR-060 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-294"></a>
## HAPI-294 — remediate semantic review Alarm

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMR-100

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review Alarm

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/api/AlarmApi.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmRestApi.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmQueryController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/AcknowledgeAlarmRequest.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/CloseAlarmRequest.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/RaiseAlarmRequest.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/response/AlarmResponse.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/AcknowledgeAlarmCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/CloseAlarmCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/CreateAlarmSuppressionCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/EvaluateAlarmSuppressionExpiryCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/RaiseAlarmCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/ReleaseAlarmSuppressionCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionPageDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/mapper/AlarmApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AcknowledgeAlarmUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/CloseAlarmUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmSuppressionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/RaiseAlarmUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleActorPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleEventRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/query/AlarmSuppressionQuery.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmDomainException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmLifecycleViolationException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmSuppressionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/InvalidAlarmValueException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmCommentVisibility.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEscalationStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEscalationType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEvidenceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmId.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmLifecycleEventType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSuppressionScopeType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSuppressionStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/AlarmInfrastructure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/configuration/AlarmModuleConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/AlarmExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/NoopAlarmExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmPersistence.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmLifecycleEventRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCommentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEscalationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmLifecycleEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmRuleBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCatalogTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCommentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmEscalationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmLifecycleEventJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmRuleBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/validation/AlarmCatalogValidation.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/query/JpaAlarmQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJob.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestrator.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/security/AlarmLifecycleActorAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261008_022__hmr_100_alarm_lifecycle_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionRoutePermissionTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmControllerActorAttributionTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapperTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJobTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/security/AlarmLifecycleActorAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmDomainException.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmLifecycleViolationException.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmSuppressionConflictException.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/InvalidAlarmValueException.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AcknowledgeAlarmUseCase.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/CloseAlarmUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleActorPort.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleEventRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`, `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/AlarmInfrastructure.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/configuration/AlarmModuleConfiguration.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/AlarmExternalReferenceResolver.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/NoopAlarmExternalReferenceResolver.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/api/AlarmApi.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmRestApi.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmQueryController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmController.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/AcknowledgeAlarmRequest.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/CloseAlarmRequest.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/RaiseAlarmRequest.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/response/AlarmResponse.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/AcknowledgeAlarmCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/CloseAlarmCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/CreateAlarmSuppressionCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/EvaluateAlarmSuppressionExpiryCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/RaiseAlarmCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/ReleaseAlarmSuppressionCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionPageDto.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/mapper/AlarmApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AcknowledgeAlarmUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/CloseAlarmUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmSuppressionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/RaiseAlarmUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleActorPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleEventRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/query/AlarmSuppressionQuery.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmDomainException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmLifecycleViolationException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmSuppressionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/InvalidAlarmValueException.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmCommentVisibility.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEscalationStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEscalationType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEvidenceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmId.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmLifecycleEventType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSuppressionScopeType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSuppressionStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/AlarmInfrastructure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/configuration/AlarmModuleConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/AlarmExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/NoopAlarmExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmPersistence.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmLifecycleEventRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCommentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEscalationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmLifecycleEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmRuleBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCatalogTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCommentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmEscalationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmLifecycleEventJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmRuleBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/validation/AlarmCatalogValidation.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/query/JpaAlarmQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJob.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestrator.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/security/AlarmLifecycleActorAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261008_022__hmr_100_alarm_lifecycle_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionRoutePermissionTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmControllerActorAttributionTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapperTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJobTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/security/AlarmLifecycleActorAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-100 — alarm.Alarm

- Source review: `HMSR-117`
- Exact commit: `fix(alarm): remediate semantic review Alarm`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: incident.Incident, organization.OrganizationUnit, planning.PlanTarget, telemetry.TelemetryReading, workflow.WorkflowInstance
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_100__hmr_100_alarm_alarm.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Alarm.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/AlarmApi.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmRestApi.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandler.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmController.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmQueryController.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionController.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmController.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmGeneratedRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/AcknowledgeAlarmRequest.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/CloseAlarmRequest.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/RaiseAlarmRequest.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/api/rest/response/AlarmResponse.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/AcknowledgeAlarmCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/CloseAlarmCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/CreateAlarmSuppressionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/EvaluateAlarmSuppressionExpiryCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/RaiseAlarmCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/command/ReleaseAlarmSuppressionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionDto.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSuppressionPageDto.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/mapper/AlarmApplicationMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AcknowledgeAlarmUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmQueryUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/CloseAlarmUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmSuppressionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/RaiseAlarmUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/query/AlarmSuppressionQuery.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmDomainException.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmLifecycleViolationException.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmSuppressionConflictException.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/InvalidAlarmValueException.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmBoundaryPolicy.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicy.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmCommentVisibility.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEscalationStatus.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEscalationType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmEvidenceType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmId.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmLifecycleEventType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSuppressionScopeType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSuppressionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/AlarmInfrastructure.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/configuration/AlarmModuleConfiguration.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/AlarmExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/NoopAlarmExternalReferenceResolver.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmPersistence.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogEntryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCatalogTranslationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmCommentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEscalationJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmEvidenceLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmLifecycleEventJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmRuleBindingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmSuppressionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCatalogEntryJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCatalogTranslationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCommentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmEscalationJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmEvidenceLinkJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmLifecycleEventJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmRuleBindingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/query/JpaAlarmQueryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJob.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestrator.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapter.java`
  - `src/main/resources/db/migration/V20261004_100__hmr_100_alarm_alarm.sql`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionRoutePermissionTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmControllerActorAttributionTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapperTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJobTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AlarmSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Validate Alarm catalog-family membership:** `alarmTypeId` → `ALARM_TYPE`, `severityId` → `ALARM_SEVERITY`, supplied `priorityId` → `ALARM_PRIORITY`.
  2. **Fail closed on missing required title/message data before persistence; the live schema currently requires `titleFr`.**
  3. **Record exactly one append-only lifecycle event for Alarm creation/RAISED state.**
  4. **Make acknowledgement a transactional Alarm lifecycle operation:** load/fail closed on Alarm, reject closed alarms, persist acknowledgement, update Alarm acknowledgement/state fields, and emit exactly one lifecycle event.
  5. **Make closure a transactional Alarm lifecycle operation:** load/fail closed on Alarm, enforce clear-before-close unless cancelled, persist closure, update Alarm closed state/timestamps, and emit exactly one lifecycle event.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionRoutePermissionTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmControllerActorAttributionTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapperTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSemanticPostgresIntegrationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJobTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/security/AlarmLifecycleActorAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `2c1693390e943356346c23d611222cee18609979` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:135](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L135)
- [doc/model-remediation/RECONCILIATION.md:4396](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4396)
- [doc/model-remediation/RECONCILIATION.md:4484](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4484)
- [doc/model-remediation/RECONCILIATION.md:13747](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13747)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6503](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6503)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6591](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6591)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15487](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15487)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:134](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L134)
- [docs/roadmap/model-semantic-remediation.md:281](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L281)
- [docs/roadmap/model-semantic-remediation.md:4952](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4952)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:281 | HMR-100 | HMSR-117 | alarm | Alarm | — | incident.Incident, organization.OrganizationUnit, planning.PlanTarget, telemetry.TelemetryReading, workflow.WorkflowInstance | fix(alarm): remediate semantic review Alarm | Planned
doc/model-remediation/RECONCILIATION.md:135 | HMR-100 | HMSR-117 | alarm.Alarm | COMPLETED — CI #602 GREEN | Accepted ALRM-PREREQ-01; independent Batch 20 lifecycle commit with focused and PostgreSQL/Spring-JPA sources; Java 21 clean verify and OpenAPI compatibility passed at repaired head 74ef372c82a790cdad63a77038fd60afb0de9c44.
doc/model-remediation/RECONCILIATION.md:4484 | HMR-100 | V20261008_022__hmr_100_alarm_lifecycle_integrity.sql | Required title, exact type/severity/optional priority families with metadata race guards, append-only lifecycle history and fresh initial-event uniqueness; preserve published local FKs and valid history.
doc/model-remediation/RECONCILIATION.md:13747 | HMR-100 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:134 | HMR-100 | HMSR-117 | [alarm.Alarm](../domain/SEMANTIC_DECISIONS.md#alarm-alarm) | [AlarmSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java) | [V20261008_022__hmr_100_alarm_lifecycle_integrity](../../src/main/resources/db/migration/V20261008_022__hmr_100_alarm_lifecycle_integrity.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6591 | HMR-100 | V20261008_022__hmr_100_alarm_lifecycle_integrity.sql | Required title, exact type/severity/optional priority families with metadata race guards, append-only lifecycle history and fresh initial-event uniqueness; preserve published local FKs and valid history.
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15487 | HMR-100 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-298"></a>
## HAPI-298 — remediate semantic review AlarmAcknowledgement

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMR-104

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AlarmAcknowledgement

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmAcknowledgementSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmAcknowledgementSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-104 — alarm.AlarmAcknowledgement

- Source review: `HMSR-121`
- Exact commit: `fix(alarm): remediate semantic review AlarmAcknowledgement`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: alarm.Alarm, organization.OrganizationUnit
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Alarm.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AlarmAcknowledgementSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Load/fail closed on the owning `Alarm` and reject lifecycle-ineligible acknowledgement, including acknowledgement after closure.**
  2. **Persist acknowledgement evidence and update the owning Alarm's acknowledgement/state snapshot fields (`currentState`, `acknowledgedAt`, `acknowledgedByActorId`, `lastUpdatedAt`) atomically.**
  3. **Record exactly one corresponding append-only `AlarmLifecycleEvent` for the acknowledgement transition in the same transactional lifecycle operation.**

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmAcknowledgementSemanticPostgresIntegrationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `4373b97ad1e6cd5908bd05591e9b494499818af6` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:139](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L139)
- [doc/model-remediation/RECONCILIATION.md:4426](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4426)
- [doc/model-remediation/RECONCILIATION.md:4485](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4485)
- [doc/model-remediation/RECONCILIATION.md:13751](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13751)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6533](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6533)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6592](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6592)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15491](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15491)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:138](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L138)
- [docs/roadmap/model-semantic-remediation.md:290](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L290)
- [docs/roadmap/model-semantic-remediation.md:5189](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L5189)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:290 | HMR-104 | HMSR-121 | alarm | AlarmAcknowledgement | — | alarm.Alarm, organization.OrganizationUnit | fix(alarm): remediate semantic review AlarmAcknowledgement | Planned
doc/model-remediation/RECONCILIATION.md:139 | HMR-104 | HMSR-121 | alarm.AlarmAcknowledgement | COMPLETED — CI #602 GREEN | Accepted ALRM-PREREQ-01; independent Batch 20 lifecycle commit with focused and PostgreSQL/Spring-JPA sources; Java 21 clean verify and OpenAPI compatibility passed at repaired head 74ef372c82a790cdad63a77038fd60afb0de9c44.
doc/model-remediation/RECONCILIATION.md:4485 | HMR-104 | None | Existing own Alarm FK suffices; acknowledgement multiplicity remains legal. Runtime transaction/lock/event coherence is mandatory.
doc/model-remediation/RECONCILIATION.md:13751 | HMR-104 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:138 | HMR-104 | HMSR-121 | [alarm.AlarmAcknowledgement](../domain/SEMANTIC_DECISIONS.md#alarm-alarmacknowledgement) | [AlarmAcknowledgementSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java) | No dedicated subject migration in current chain; see decision | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6592 | HMR-104 | None | Existing own Alarm FK suffices; acknowledgement multiplicity remains legal. Runtime transaction/lock/event coherence is mandatory.
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15491 | HMR-104 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-299"></a>
## HAPI-299 — remediate semantic review AlarmClosure

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMR-105

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AlarmClosure

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261008_023__hmr_105_alarm_closure_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmClosureSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261008_023__hmr_105_alarm_closure_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmClosureSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-105 — alarm.AlarmClosure

- Source review: `HMSR-122`
- Exact commit: `fix(alarm): remediate semantic review AlarmClosure`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: alarm.Alarm, workflow.WorkflowInstance
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_105__hmr_105_alarm_alarm_closure.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Alarm.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_105__hmr_105_alarm_alarm_closure.sql`
  - `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AlarmClosureSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Make `closeAlarm(...)` load/fail closed on the owning Alarm, enforce clear-before-close unless cancelled, update the Alarm lifecycle fields, persist `AlarmClosure`, and emit exactly one closure lifecycle event transactionally.**
  2. **Enforce the DDD's one-closure-record-per-Alarm invariant.**
  3. **Preserve Workflow and Identity ownership boundaries:** validate current references through owner-controlled contracts when required and do not add cross-module database FKs.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmClosureSemanticPostgresIntegrationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `0445518741bc62ba35ed117a81b1c143ea1b0171` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:140](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L140)
- [doc/model-remediation/RECONCILIATION.md:4438](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4438)
- [doc/model-remediation/RECONCILIATION.md:4486](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4486)
- [doc/model-remediation/RECONCILIATION.md:13752](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13752)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6545](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6545)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6593](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6593)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15492](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15492)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:139](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L139)
- [docs/roadmap/model-semantic-remediation.md:291](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L291)
- [docs/roadmap/model-semantic-remediation.md:5219](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L5219)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:291 | HMR-105 | HMSR-122 | alarm | AlarmClosure | — | alarm.Alarm, workflow.WorkflowInstance | fix(alarm): remediate semantic review AlarmClosure | Planned
doc/model-remediation/RECONCILIATION.md:140 | HMR-105 | HMSR-122 | alarm.AlarmClosure | COMPLETED — CI #602 GREEN | Accepted ALRM-PREREQ-01; independent Batch 20 lifecycle commit with focused and PostgreSQL/Spring-JPA sources; Java 21 clean verify and OpenAPI compatibility passed at repaired head 74ef372c82a790cdad63a77038fd60afb0de9c44.
doc/model-remediation/RECONCILIATION.md:4486 | HMR-105 | V20261008_023__hmr_105_alarm_closure_integrity.sql | Fail on existing duplicate closures; install one-closure-per-Alarm uniqueness without deleting/merging evidence.
doc/model-remediation/RECONCILIATION.md:13752 | HMR-105 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:139 | HMR-105 | HMSR-122 | [alarm.AlarmClosure](../domain/SEMANTIC_DECISIONS.md#alarm-alarmclosure) | [AlarmClosureSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java) | [V20261008_023__hmr_105_alarm_closure_integrity](../../src/main/resources/db/migration/V20261008_023__hmr_105_alarm_closure_integrity.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6593 | HMR-105 | V20261008_023__hmr_105_alarm_closure_integrity.sql | Fail on existing duplicate closures; install one-closure-per-Alarm uniqueness without deleting/merging evidence.
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15492 | HMR-105 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-300"></a>
## HAPI-300 — remediate semantic review AlarmShelving

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMR-106

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AlarmShelving

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmShelvingPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/validation/AlarmCatalogValidation.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryJob.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryOrchestrator.java`
- `src/main/resources/db/migration/V20261008_024__hmr_106_alarm_shelving_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmShelvingSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryJobTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryOrchestratorTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmShelvingPolicy.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmShelvingPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/validation/AlarmCatalogValidation.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryJob.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryOrchestrator.java`
- `src/main/resources/db/migration/V20261008_024__hmr_106_alarm_shelving_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmShelvingSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryJobTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryOrchestratorTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-106 — alarm.AlarmShelving

- Source review: `HMSR-123`
- Exact commit: `fix(alarm): remediate semantic review AlarmShelving`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: alarm.Alarm
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_106__hmr_106_alarm_alarm_shelving.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Alarm.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_106__hmr_106_alarm_alarm_shelving.sql`
  - `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AlarmShelvingSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce Alarm lifecycle eligibility before shelving, including the DDD prohibition on shelving a closed alarm.**
  2. **Validate `shelvingReasonId` against the Alarm-owned `SHELVING_REASON` catalog family, not merely generic catalog-row existence.**
  3. **Enforce `shelvedUntil > shelvedAt` as an intrinsic model/persistence invariant and enforce only one ACTIVE shelving per Alarm with concurrency-safe protection.**
  4. **Make shelving and unshelving transactional Alarm lifecycle operations that synchronize the owning Alarm state/timestamps and emit exactly one corresponding append-only lifecycle event.**
  5. **Implement deterministic automatic expiry/unshelving at `shelvedUntil`, preserving shelving status/time evidence and Alarm lifecycle consistency.**

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmShelvingSemanticPostgresIntegrationTest.java` — exact-head oracle suite 13 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryJobTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryOrchestratorTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `3841da6332d8abda073c50871e4c28348cf3f4df` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:141](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L141)
- [doc/model-remediation/RECONCILIATION.md:4452](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4452)
- [doc/model-remediation/RECONCILIATION.md:4487](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L4487)
- [doc/model-remediation/RECONCILIATION.md:13753](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13753)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6559](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6559)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6594](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L6594)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15493](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15493)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:140](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L140)
- [docs/roadmap/model-semantic-remediation.md:292](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L292)
- [docs/roadmap/model-semantic-remediation.md:5251](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L5251)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:292 | HMR-106 | HMSR-123 | alarm | AlarmShelving | — | alarm.Alarm | fix(alarm): remediate semantic review AlarmShelving | Planned
doc/model-remediation/RECONCILIATION.md:141 | HMR-106 | HMSR-123 | alarm.AlarmShelving | COMPLETED — CI #602 GREEN | Accepted ALRM-PREREQ-01; independent Batch 20 lifecycle commit with focused and PostgreSQL/Spring-JPA sources; Java 21 clean verify and OpenAPI compatibility passed at repaired head 74ef372c82a790cdad63a77038fd60afb0de9c44.
doc/model-remediation/RECONCILIATION.md:4487 | HMR-106 | V20261008_024__hmr_106_alarm_shelving_integrity.sql | Fail on invalid intervals/families/ACTIVE duplicates; intrinsic interval and partial ACTIVE uniqueness plus used shelving-family guards.
doc/model-remediation/RECONCILIATION.md:13753 | HMR-106 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:140 | HMR-106 | HMSR-123 | [alarm.AlarmShelving](../domain/SEMANTIC_DECISIONS.md#alarm-alarmshelving) | [AlarmShelvingSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java) | [V20261008_024__hmr_106_alarm_shelving_integrity](../../src/main/resources/db/migration/V20261008_024__hmr_106_alarm_shelving_integrity.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:6594 | HMR-106 | V20261008_024__hmr_106_alarm_shelving_integrity.sql | Fail on invalid intervals/families/ACTIVE duplicates; intrinsic interval and partial ACTIVE uniqueness plus used shelving-family guards.
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15493 | HMR-106 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-304"></a>
## HAPI-304 — inventory target model dependency evidence

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMS-002

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Inventory candidate relationship evidence for all 123 models from domain source, JPA, Flyway, contracts and enums. Every candidate edge retains evidence/provenance; no review order yet.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsDatasetType.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsLineageStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRefreshMode.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunMode.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetConditionRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditAccessType.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditExportStatus.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditOperation.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditValueType.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationSensitivity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueType.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagEvaluationStrategy.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyTicketStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/PermitToWork.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/CapaStatus.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseSourceType.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/PermitStatus.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/PermitToWorkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationEventType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationDecisionValue.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/DelegationStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingMode.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantEffect.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/PermissionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthenticationEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDelegationGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalRoleMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/PermissionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RoleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentSourceType.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentStatus.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/ResponseActionStatus.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/ResponseTargetType.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java`
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
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/DefectStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/FindingSeverity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityProgramStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/ThreatType.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityProgramJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/PipelineDefectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakCandidateStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakDetectionCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakEscalationStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakEscalationTargetType.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakSeverityLevel.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCandidateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEscalationReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/DeliveryAttemptStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeDistrict.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeLocality.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeState.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Employee.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAddress.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OperationalScope.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationContactPoint.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/AddressType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ContactPointTargetReference.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ContactPointType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/DelegationStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/EmployeeStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/EmployeeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/HierarchySnapshotStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitKind.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionLevel.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingSubjectReference.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ResponsibilityAssigneeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ResponsibilityType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftType.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeDistrictJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeLocalityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeStateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAddressJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OperationalScopeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationContactPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationDelegationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationHierarchySnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ResponsibilityAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRoleAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java`
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
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportArtifactType.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportFormat.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java`
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
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationOptimizationCandidateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/ReadingState.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowSlaStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
- `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`
- `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`
- `src/main/resources/db/migration/V20260611_003__create_party_tables.sql`
- `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`
- `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`
- `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`
- `src/main/resources/db/migration/V20260611_007__create_monitoring_tables.sql`
- `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`
- `src/main/resources/db/migration/V20260611_009__create_leakdetection_tables.sql`
- `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`
- `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`
- `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`
- `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`
- `src/main/resources/db/migration/V20260611_014__create_assets_tables.sql`
- `src/main/resources/db/migration/V20260611_015__create_custody_tables.sql`
- `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`
- `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`
- `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`
- `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`
- `src/main/resources/db/migration/V20260611_020__create_configuration_tables.sql`
- `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`
- `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`
- `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`
- `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`
- `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`
- `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`
- `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`
- `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`
- `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`
- `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`
- `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`
- `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`
- `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`
- `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`
- `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Inventory candidate relationship evidence for all 123 models from domain source, JPA, Flyway, contracts and enums. Every candidate edge retains evidence/provenance; no review order yet.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsDatasetType.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsLineageStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRefreshMode.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunMode.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetConditionRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditAccessType.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditExportStatus.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditOperation.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditValueType.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationSensitivity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueType.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagEvaluationStrategy.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyTicketStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/PermitToWork.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/CapaStatus.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseSourceType.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/PermitStatus.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/PermitToWorkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationEventType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationDecisionValue.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/DelegationStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingMode.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantEffect.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/PermissionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthenticationEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDelegationGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalRoleMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/PermissionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RoleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentSourceType.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentStatus.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/ResponseActionStatus.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/ResponseTargetType.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java`
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
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/DefectStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/FindingSeverity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityProgramStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/ThreatType.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityProgramJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/PipelineDefectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakCandidateStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakDetectionCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakEscalationStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakEscalationTargetType.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakSeverityLevel.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCandidateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEscalationReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/DeliveryAttemptStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeDistrict.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeLocality.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeState.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Employee.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAddress.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OperationalScope.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationContactPoint.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/AddressType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ContactPointTargetReference.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ContactPointType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/DelegationStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/EmployeeStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/EmployeeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/HierarchySnapshotStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitKind.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionLevel.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingSubjectReference.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ResponsibilityAssigneeType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ResponsibilityType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftType.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeDistrictJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeLocalityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeStateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAddressJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OperationalScopeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationContactPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationDelegationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationHierarchySnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ResponsibilityAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRoleAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java`
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
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportArtifactType.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportFormat.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java`
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
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationOptimizationCandidateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/ReadingState.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowSlaStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
- `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`
- `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`
- `src/main/resources/db/migration/V20260611_003__create_party_tables.sql`
- `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`
- `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`
- `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`
- `src/main/resources/db/migration/V20260611_007__create_monitoring_tables.sql`
- `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`
- `src/main/resources/db/migration/V20260611_009__create_leakdetection_tables.sql`
- `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`
- `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`
- `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`
- `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`
- `src/main/resources/db/migration/V20260611_014__create_assets_tables.sql`
- `src/main/resources/db/migration/V20260611_015__create_custody_tables.sql`
- `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`
- `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`
- `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`
- `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`
- `src/main/resources/db/migration/V20260611_020__create_configuration_tables.sql`
- `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`
- `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`
- `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`
- `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`
- `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`
- `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`
- `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`
- `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`
- `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`
- `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`
- `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`
- `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`
- `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`
- `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`
- `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


Original detailed section exceeds this compact definition; its exact-SHA source link above and the retained historical source contain the full objective, file list, tests and limitations. No truncation is represented as complete recovery.

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

- `e950324f46df265959209451fbc08df54a4e53ff` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-review.md:155](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L155)
- [docs/roadmap/model-semantic-review.md:336](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L336)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:155 | HMS-002 | docs(model-review): inventory target model dependency evidence | Inventory candidate relationship evidence for all 123 models from domain source, JPA, Flyway, contracts and enums. Every candidate edge retains evidence/provenance; no review order yet. | **Completed** — 123/123 subjects reconciled; 627 candidate relationship fields inventoried; JPA/ports/Flyway/custom-type evidence registered; no classification/order performed.
~~~~~~~~

<a id="hapi-322"></a>
## HAPI-322 — review notification NotificationTemplate

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMSR-012

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review notification NotificationTemplate

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 25. HMSR-012 — notification.NotificationTemplate review

**Decision:** REVISE  
**Review code:** HMSR-012  
**Dependency level:** 0  
**Bounded context:** notification  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 2 — `notification.NotificationRequest` and `notification.NotificationMessage`  
**Transitive dependents:** 3  
**Unresolved/non-subject references:** 2 — `categoryId` and `defaultChannelId`, both now semantically resolved to Notification-owned non-subject targets

### 25.1 Semantic role and ordering rationale

`NotificationTemplate` is the Notification-owned aggregate root for reusable message-template identity and governance. It carries stable template identity, business classification, optional default channel, lifecycle state and a current-version marker; immutable message content belongs to `NotificationTemplateVersion` and localized version content belongs to `NotificationTemplateTranslation`.

It is Level 0 because none of its prerequisites are another HMS subject model. `NotificationRequest` and `NotificationMessage` are the two direct HMS subject dependents. `NotificationTemplateVersion`, `NotificationCatalogEntry` and `NotificationChannel` are Notification read/persistence models outside the 123 HMS subject population.

The source business module owns why a notification exists. Notification owns template selection, rendering, channel semantics and delivery evidence; it must not mutate the source business object's state.

### 25.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable NotificationTemplate identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable unique business code for the reusable template. |
| `nameAr` | `String` | Optional | Arabic template display label. |
| `nameFr` | `String` | Persistence-required | French template display label; JPA/schema require it although the aggregate DDD does not separately state field nullability. |
| `nameEn` | `String` | Optional | English template display label. |
| `templateTypeId` | `String` | Mandatory | Notification catalog reference in family `TEMPLATE_TYPE`. |
| `categoryId` | `String` | Optional | Notification catalog reference in family `NOTIFICATION_CATEGORY`. |
| `defaultChannelId` | `String` | Optional | Default Notification-owned channel reference; concrete request/policy logic may override it. |
| `status` | `NotificationTemplateStatus` | Mandatory | Template lifecycle: `DRAFT`, `ACTIVE`, `INACTIVE`, or `RETIRED`. |
| `currentVersion` | `Integer` | Optional | Denormalized/current template version number marker; it is not a model ID or HMS dependency edge. |
| `systemDefined` | `boolean` | Mandatory persisted state | Marks templates delivered/owned as system-defined reference configuration. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The current constructor rejects blank `id`, `code`, and `templateTypeId`, rejects null `status`, trims textual values and converts blank optional text to `null`. `usableForNewMessages()` correctly returns true only for `ACTIVE` templates.

### 25.3 Resolution of the two previously unresolved references

Current repository evidence resolves both HMSR non-subject references without adding subject-graph edges.

1. **`categoryId -> NOTIFICATION_CATEGORY`**
   - the Notification DDD defines `NotificationCatalogEntry` as the controlled-vocabulary persistence model;
   - `NOTIFICATION_CATEGORY` is an explicit Notification catalog family;
   - template business type/category must be catalog-backed rather than hard-coded enum taxonomy;
   - `NotificationCatalogEntry` is outside the 123 HMS subject set.

2. **`defaultChannelId -> NotificationChannel`**
   - the Notification DDD defines `NotificationChannel` as the owner of channel semantics such as EMAIL, SMS, WEB, MOBILE_PUSH, MESSAGING_APP, WEBHOOK and VOICE;
   - the live persistence model stores channels in `hidra_notification_channel`;
   - the same DDD explicitly uses `defaultChannelId -> NotificationChannel` for NotificationPolicy, supporting the same identifier semantics for template defaults;
   - `NotificationChannel` is outside the 123 HMS subject set.

`templateTypeId` is also sharpened from generic catalog/value semantics to the `TEMPLATE_TYPE` catalog family. HRA-111 installs `fk_hra111_notification_023` from `hidra_notification_template.template_type_id` to `hidra_notification_catalog_entry(id)`.

### 25.4 Template/version boundary

The active Notification DDD requires:

```text
Template code must be unique.
Only ACTIVE templates can be used for new messages.
A template version is immutable after activation.
A sent message must reference the exact template version used.
New wording requires a new version.
```

`NotificationTemplateVersion` is a separate Notification persistence/read model with `templateId`, `versionNumber`, lifecycle status and immutable rendered-source content. HRA-111 protects `NotificationTemplateVersion.templateId -> NotificationTemplate.id` with `ON DELETE RESTRICT`.

`currentVersion` on the aggregate is an integer version marker, not a foreign-key identifier. The repository currently contains no demonstrated application service that governs creation/activation of template versions or synchronizes `currentVersion` with an ACTIVE version. Before provisioning/editing template data, the target contract must explicitly define whether this field means latest version, currently active version, or another governed version marker and which status combinations are legal.

### 25.5 Persistence and reference integrity

The live domain and JPA models agree on all 13 declared components.

The base Notification migration makes `id`, `code`, `name_fr`, `template_type_id`, `status`, `system_defined`, `created_at`, and `updated_at` non-null. `category_id`, `default_channel_id`, and `current_version` are nullable.

The schema has ordinary indexes on template code, type, category, default channel, status and audit timestamps. No unique constraint on template `code` was found.

HRA-111 enforces only the mandatory `template_type_id` reference for this row. No current database FK was found for optional `category_id` or `default_channel_id`, and the live application layer does not expose a template-management service that demonstrates catalog-family/channel validation for those optional references.

### 25.6 Runtime use of templates and versions

The current `NotificationApplicationService` creates `NotificationRequest` and `NotificationMessage` directly from incoming command IDs. It does not inject `NotificationTemplateRepositoryPort` or a template-version repository and therefore does not demonstrate these DDD rules at the request/message creation boundary:

- selected template exists;
- selected template is `ACTIVE`; 
- `templateTypeId` belongs to `TEMPLATE_TYPE`; 
- optional `categoryId` belongs to `NOTIFICATION_CATEGORY`; 
- optional `defaultChannelId` resolves to a usable NotificationChannel when a default is relied upon;
- supplied `templateVersionId` exists and belongs to the supplied `templateId`; 
- the selected version is eligible for reproducible message generation.

`NotificationTemplate.usableForNewMessages()` expresses the ACTIVE-only rule correctly, but no current message/request orchestration path found during HMSR-012 calls that helper.

### 25.7 Multilingual interpretation

The aggregate stores multilingual **template labels** (`nameAr/nameFr/nameEn`), while actual localized message content belongs to `NotificationTemplateTranslation` for a specific template version.

The DDD explicitly states that French template-version content is mandatory for operational Hidra screens, with Arabic and English optional/recommended. This should not be confused with the aggregate label fields. The live JPA/schema independently require `NotificationTemplate.nameFr`; the domain constructor does not enforce that persistence requirement, so the final baseline should deliberately reconcile the label nullability boundary rather than infer content-localization rules from it.

### 25.8 Required revisions

The model's ownership and graph placement are correct, but the target baseline cannot be APPROVED until these evidence-backed gaps are reconciled:

1. **Unique template code:** enforce the DDD-defined uniqueness of `NotificationTemplate.code` at the application/database boundary.
2. **ACTIVE-only template selection:** request/message creation must validate that a template used for new message generation is ACTIVE; the current helper is not wired into orchestration.
3. **Exact template-version integrity:** validate that `templateVersionId` exists, belongs to the selected `templateId`, and is eligible under the version lifecycle before rendering/sending.
4. **`currentVersion` governance:** define and enforce the exact meaning of `currentVersion` and its consistency with template-version rows and template lifecycle.
5. **Catalog-family validation:** `templateTypeId` must resolve to `TEMPLATE_TYPE`; when present, `categoryId` must resolve to `NOTIFICATION_CATEGORY`, not merely to any catalog row.
6. **Optional channel-reference integrity:** when `defaultChannelId` is present and used, it must resolve to the intended NotificationChannel and satisfy the applicable active/availability policy.
7. **French label persistence boundary:** reconcile the persistence-required `nameFr` with the domain constructor's nullable behavior without confusing label semantics with version-content localization.
8. **Required timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`, which are persistence-required but not guarded by the record constructor.

HMSR-012 does not change production Java, JPA, Flyway, application/API contracts, tests, catalogs, channels, templates or template-version data.

### 25.9 Operational interpretation

For SONATRACH/TRC operations, NotificationTemplate may govern reusable communications for alarms, incidents, workflow tasks, planning events, integrity events or operational reminders. The template controls communication wording and default delivery semantics only.

Using a template must never imply approval, alarm acknowledgement, incident closure, SCADA actuation or any other source-domain state transition. Notification acknowledgement remains communication evidence, not business approval.

### 25.10 Review conclusion

**REVISE.** `NotificationTemplate` is the correct foundational Notification aggregate and its two subject dependency directions are sound. `templateTypeId`, `categoryId` and `defaultChannelId` are now semantically resolved to Notification-owned non-subject prerequisites.

The target baseline cannot mark it APPROVED while template-code uniqueness, ACTIVE-only selection, exact template/version integrity, `currentVersion` governance, catalog-family validation and optional channel-reference integrity remain unenforced or ambiguous. HMS reconciliation must retain these obligations until an explicitly authorized Notification correction task resolves them or the target semantics are explicitly changed.

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

- `9809d9bedd14fb4f0c6a83dc3e32778071d35744` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:64](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L64)
- [docs/roadmap/model-semantic-review.md:207](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L207)
- [docs/roadmap/model-semantic-review.md:4401](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L4401)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:207 | HMSR-012 | 0 | notification | NotificationTemplate | — | — | 2 | 3 | 2 | REVISE | Completed | docs(model-review): review notification NotificationTemplate
doc/domain/SEMANTIC_DECISIONS.md:64 | HMSR-012 | [notification.NotificationTemplate](#notification-notificationtemplate) | REVISE | HMR-012
~~~~~~~~

<a id="hapi-349"></a>
## HAPI-349 — review notification NotificationRequest

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMSR-039

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review notification NotificationRequest

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/ReceiveNotificationRequestRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/ReceiveNotificationRequestCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/ReceiveNotificationRequestRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/ReceiveNotificationRequestCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 52. HMSR-039 — notification.NotificationRequest review

**Decision:** REVISE  
**Review code:** HMSR-039  
**Dependency level:** 1  
**Bounded context:** notification  
**Confirmed upstream subject dependencies:** `notification.NotificationTemplate` through optional `templateId`  
**Confirmed direct HMS subject dependents:** 1 — `notification.NotificationMessage` through `requestId`  
**Transitive HMS subject dependents:** 2  
**Unresolved/non-subject references:** 4 in the original register; stronger current evidence classifies the source-event namespace, polymorphic target, actor boundary, and same-module non-HMS policy/template-version/catalog references without manufacturing extra HMS graph edges

### 52.1 Semantic role and ordering rationale

`NotificationRequest` is the Notification-owned intake record for a communication request emitted by a source business context. It captures the source event identity, optional typed target snapshot, governed category/priority/policy/template choices, requester snapshot, correlation metadata, request lifecycle and expiry/audit timestamps.

It is Level 1 because its optional `templateId` points to the already-reviewed HMS subject `NotificationTemplate`. `NotificationMessage` depends directly on the request and therefore remains downstream. Notification policy, template version and catalog entries are retained Notification persistence/read models outside the 123-model HMS subject set.

The Notification DDD is explicit that Notification delivers communication and tracks delivery; it does not own or mutate the operational truth that triggered the request.

### 52.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable request identity and persistence primary key. |
| `sourceModule` | `String` | Mandatory language-neutral bounded-context/source namespace for the triggering business event. |
| `sourceEventType` | `String` | Mandatory source-event classification/name used to describe why Notification was invoked. |
| `sourceEventId` | `String` | Mandatory typed/non-relational source-event identity in the source-event namespace; not a relational HMS target. |
| `targetType` | `String` | Optional discriminator for the business target being communicated about. |
| `targetId` | `String` | Optional polymorphic target identity; no single concrete HMS graph target is implied. |
| `targetCodeSnapshot` | `String` | Optional historical/readability snapshot of target code. |
| `targetLabelSnapshot` | `String` | Optional historical/readability snapshot of target label. |
| `categoryId` | `String` | Mandatory Notification-owned `NOTIFICATION_CATEGORY` catalog reference. |
| `priorityId` | `String` | Optional Notification-owned `NOTIFICATION_PRIORITY` catalog reference. |
| `policyId` | `String` | Optional same-module reference to NotificationPolicy. |
| `templateId` | `String` | Optional same-module HMS subject reference to NotificationTemplate. |
| `templateVersionId` | `String` | Optional same-module reference to immutable NotificationTemplateVersion. |
| `requestedByActorId` | `String` | Optional cross-module actor reference; Notification does not own Identity. |
| `requestedByDisplayNameSnapshot` | `String` | Optional actor display snapshot for traceability/readability. |
| `requestedAt` | `Instant` | Mandatory request-receipt timestamp. |
| `correlationId` | `String` | Optional technical correlation identifier. |
| `requestId` | `String` | Optional technical request identifier; not a NotificationRequest self-reference. |
| `status` | `NotificationRequestStatus` | Mandatory Notification request lifecycle state. |
| `expiresAt` | `Instant` | Optional expiry timestamp. Current evidence does not define an ordering invariant relative to `requestedAt`. |
| `createdAt` | `Instant` | Persistence-required audit timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp. |

The current compact constructor already rejects blank `id`, `sourceEventId`, and `categoryId`, and requires non-null `requestedAt` and `status`. It normalizes the remaining textual identifiers and snapshots.

### 52.3 Source-event namespace and required-source mismatch

The scalar-reference integrity inventory explicitly classifies:

```text
notification.NotificationRequest.sourceEventId
    -> source-event/correlation namespace
```

This is correct: `sourceModule + sourceEventType + sourceEventId` identify the triggering event in its owner context and must not be converted into a single cross-module database FK.

The base Notification migration and live JPA mapping require all three source fields:

```text
source_module     NOT NULL
source_event_type NOT NULL
source_event_id   NOT NULL
```

The DDD also describes NotificationRequest as a request from a business module triggered by a business event and states that every request must reference a source module.

The canonical domain constructor, however, enforces only `sourceEventId`. It normalizes blank/null `sourceModule` and `sourceEventType` to `null`, and the active receive service performs no separate nonblank validation before persistence.

Therefore the canonical domain can accept a source-less or type-less request that cannot satisfy the final persistence contract and does not carry the source context required by the Notification DDD.

### 52.4 Category and priority catalog semantics

Notification DDD defines Notification-owned controlled vocabularies including:

```text
NOTIFICATION_CATEGORY
NOTIFICATION_PRIORITY
```

and `NotificationCatalogEntry` stores the catalog family in `catalogName`.

HRA-111 protects mandatory `categoryId` only at generic row-existence level:

```text
hidra_notification_request.category_id
    -> hidra_notification_catalog_entry.id
```

That FK cannot prove that the selected row belongs to `NOTIFICATION_CATEGORY`. The active receive path does not query a catalog boundary before saving.

`priorityId` is optional, indexed, and semantically governed by `NOTIFICATION_PRIORITY`, but no nullable FK or application lookup demonstrates fail-closed row/family validation when a priority is supplied.

HMSR-039 therefore resolves both fields as catalog/value dependencies and records the missing family/reference protection without inventing additional priority-requiredness.

### 52.5 Policy, template and template-version reference integrity

The request can optionally carry `policyId`, `templateId`, and `templateVersionId`.

Current evidence resolves them respectively to:

```text
NotificationPolicy
NotificationTemplate
NotificationTemplateVersion
```

`templateId` is the confirmed HMS subject edge that places NotificationRequest at Level 1. `NotificationPolicy` and `NotificationTemplateVersion` are retained same-module persistence/read models outside the HMS subject set.

The base schema indexes all three fields but declares them nullable. HRA-111 intentionally covers mandatory same-module references only, so it does not install request-level FKs for these optional values. The active receive service also accepts and persists the three identifiers directly without same-module existence validation.

Consequently, populated policy/template/template-version identifiers can currently dangle.

HMSR-039 does not make any of these optional choices mandatory. It also does not invent a template/template-version parent-consistency rule for NotificationRequest because the current DDD does not state how policy resolution and late template selection interact at request intake.

### 52.6 Polymorphic target and actor boundaries

`targetType + targetId` represent an optional polymorphic business target. The Notification DDD gives examples such as alarms and incidents and explicitly keeps their lifecycle/ownership in the source modules.

No single relational target should therefore be manufactured for `targetId`, and no HMS graph edge is added merely because the field ends in `Id`.

`targetCodeSnapshot` and `targetLabelSnapshot` remain snapshot/readability values and do not create ownership.

`requestedByActorId` remains an optional cross-module actor reference. Notification does not own users, roles or permissions, and no cross-module database FK should be introduced. Current evidence does not define actor-status eligibility for request submission, so HMSR-039 does not invent it.

### 52.7 Lifecycle and request-processing semantics

`NotificationRequestStatus` exactly matches the DDD lifecycle vocabulary:

```text
RECEIVED
VALIDATED
RECIPIENTS_RESOLVED
MESSAGES_CREATED
SCHEDULED
COMPLETED
PARTIALLY_FAILED
FAILED
CANCELLED
EXPIRED
SUPPRESSED
```

The active receive service conservatively creates new requests as `RECEIVED`, which is consistent with intake semantics.

Current repository evidence does not define a complete transition matrix, status-specific required fields, cancellation authorization, or an exact `requestedAt <= expiresAt` rule. HMSR-039 does not manufacture those rules.

### 52.8 Application/API and persistence alignment

`ReceiveNotificationRequestRequest` and `ReceiveNotificationRequestCommand` carry the same 17 input fields and are mapped through the generated REST mapping boundary. The application service generates the Notification-owned ID, sets `requestedAt/createdAt/updatedAt` to the current instant, initializes status to `RECEIVED`, and saves through `NotificationRequestRepositoryPort`.

The live domain/JPA representations agree on all 22 components, but requiredness is not fully aligned because the domain omits guards for persistence-required `sourceModule` and `sourceEventType`.

The DDD records no multilingual fields on NotificationRequest itself; only display snapshots appear. No Arabic/French/English translation rule is therefore invented for this model.

Generic audit timestamp requiredness remains a persistence/application-boundary concern and is not promoted into additional constructor invariants solely because the schema is non-null.

### 52.9 Deliberately not invented rules

HMSR-039 does not infer:

- that every request must carry a target object when the source event itself provides the communication reason;
- a universal pairing rule for optional `targetType` and `targetId` beyond their documented typed-target role;
- mandatory `priorityId`, `policyId`, `templateId`, or `templateVersionId`;
- a request-level template/template-version parent-consistency rule absent explicit DDD evidence;
- actor authorization/eligibility semantics;
- a status transition matrix;
- expiry ordering or duration policy;
- source-module enumerations or a fixed list of allowed producer modules;
- any cross-module database foreign key for source events, targets, or actors.

### 52.10 Required revisions

Five evidence-backed correction obligations remain:

1. **Require nonblank source-module and source-event-type semantics at the authoritative domain/application boundary.** `sourceModule` and `sourceEventType` must not normalize to null while the DDD and final persistence contract require the source event context.

2. **Enforce Notification category/priority catalog semantics.** Mandatory `categoryId` must resolve specifically to `NOTIFICATION_CATEGORY`; when optional `priorityId` is populated, it must resolve to `NOTIFICATION_PRIORITY`. Generic category row existence and an unprotected priority ID are insufficient.

3. **Protect populated NotificationPolicy references.** When `policyId` is supplied, it must resolve to the Notification-owned policy target through an additive nullable same-module FK, application validation, or equivalent fail-closed boundary.

4. **Protect populated NotificationTemplate references.** When `templateId` is supplied, it must resolve to the reviewed NotificationTemplate subject rather than allowing a dangling HMS dependency.

5. **Protect populated NotificationTemplateVersion references.** When `templateVersionId` is supplied, it must resolve to the retained immutable template-version target; HMSR-039 does not additionally impose parent-pair semantics absent stronger DDD evidence.

HMSR-039 does not modify production Java, JPA, Flyway, API/application contracts, tests, notification templates/policies/catalogs, delivery data, or provisioned data.

### 52.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, NotificationRequest is the governed communication handoff from operational contexts such as alarms, incidents, workflows, HSE, planning or other pipeline-management processes into the Notification bounded context.

The source-event identity must remain attributable to the producing context without making Notification the owner of that operational state. Governed category/priority semantics and valid policy/template references are important for routing, escalation, delivery traceability and later audit evidence.

A dangling policy/template/version or misclassified category/priority can produce the wrong communication behavior even when the source operational event itself is valid.

### 52.12 Review conclusion

**REVISE.** `NotificationRequest` has a coherent 22-field Notification-owned intake model, correct Level-1 dependency on NotificationTemplate, correct non-relational source-event and polymorphic-target boundaries, exact request lifecycle vocabulary, conservative `RECEIVED` creation semantics, and correct separation from source business ownership.

The target baseline cannot mark it APPROVED while required source descriptors can disappear during canonical construction, catalog-family semantics are not fail-closed, and populated optional policy/template/template-version references can dangle.

HMS reconciliation must retain these obligations until an explicitly authorized Notification correction task resolves them or stronger repository evidence explicitly revises the governing Notification DDD semantics.

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

- `92ade4312b797d90a86c5ba07165b4faac198d92` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:91](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L91)
- [docs/roadmap/model-semantic-review.md:234](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L234)
- [docs/roadmap/model-semantic-review.md:8663](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L8663)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:234 | HMSR-039 | 1 | notification | NotificationRequest | — | notification.NotificationTemplate | 1 | 2 | 4 | REVISE | Completed | docs(model-review): review notification NotificationRequest
doc/domain/SEMANTIC_DECISIONS.md:91 | HMSR-039 | [notification.NotificationRequest](#notification-notificationrequest) | REVISE | HMR-035
~~~~~~~~

<a id="hapi-371"></a>
## HAPI-371 — review notification NotificationMessage

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMSR-061

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review notification NotificationMessage

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationAsyncPushPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationAsyncPushPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationAsyncPushPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 74. HMSR-061 — notification.NotificationMessage review

**Decision:** REVISE  
**Review code:** HMSR-061  
**Dependency level:** 2  
**Bounded context:** notification  
**Confirmed upstream subject dependencies:** `notification.NotificationRequest`, optional `notification.NotificationTemplate`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 1

### 74.1 Semantic role

`NotificationMessage` is the Notification-owned concrete delivery payload for one resolved recipient and one channel. It is generated from a NotificationRequest, optionally from a NotificationTemplate, and carries the exact rendered content, delivery priority, lifecycle status, scheduling/expiry timestamps and immutable-rendering trace metadata.

Notification owns message delivery state and rendering/delivery mechanics. The source business module retains ownership of the business state that caused the notification.

### 74.2 Request, recipient and channel integrity

The model requires `requestId`, `recipientId` and `channelId`.

HRA-111 protects same-module row existence with:

```text
hidra_notification_message.request_id -> hidra_notification_request.id
hidra_notification_message.recipient_id -> hidra_notification_request_recipient.id
hidra_notification_message.channel_id -> hidra_notification_channel.id
```

These are correct same-module ownership relationships.

However, `NotificationRequestRecipient` itself belongs to a request through its own `requestId`. The current message schema protects request existence and recipient existence independently but does not prove that the chosen recipient belongs to the same request recorded by the message.

The active `createNotificationMessage()` path also accepts both IDs independently and performs no consistency lookup.

The authoritative write path must therefore fail closed when `message.requestId` and the selected recipient's owning request differ.

### 74.3 Template and exact-version traceability

The Notification DDD states:

```text
Rendered payload must be traceable to template version and variables.
A sent message must reference the exact template version used.
```

It also defines NotificationTemplateVersion as an immutable version used for reproducible message generation.

The current model makes both `templateId` and `templateVersionId` optional. Neither field is protected by a message-side HRA-111 FK. More importantly, `createNotificationMessage()` can immediately create a message in `READY` or `SCHEDULED` state with `templateVersionId == null`, and READY messages are immediately handed to the asynchronous push port.

That permits delivery-state progression without the exact immutable rendering version required by the DDD.

Where a message is template-rendered, the authoritative creation path must resolve the referenced NotificationTemplateVersion, ensure it belongs to the supplied template when `templateId` is present, and retain exact-version traceability before the message becomes READY/SCHEDULED or is dispatched.

HMSR-061 does not require a template for message forms that the future authorized Notification design may explicitly define as non-template/manual content; current evidence does not authorize inventing that broader restriction.

### 74.4 Required variables before READY

The DDD explicitly requires:

```text
Required template variables must be present before message is READY.
```

`NotificationMessageVariable` is persisted as a separate message-owned child model, but the current `createNotificationMessage()` path does not resolve the selected template version's variable schema or verify required message variables before assigning `READY`.

This is a distinct semantic gap from template-version existence: an exact version alone does not prove its required rendering inputs were present.

The authoritative state transition to READY must therefore enforce the template-version variable contract using Notification-owned application/persistence contracts.

### 74.5 Priority semantics

`priorityId` is optional. Notification DDD defines the `NOTIFICATION_PRIORITY` catalog family.

The current create path accepts an arbitrary priority ID, and repository evidence does not show message-side family validation. If populated, the value must resolve to an eligible Notification-owned `NOTIFICATION_PRIORITY` entry rather than merely being treated as an opaque scalar.

HMSR-061 does not require priority to be present on every message.

### 74.6 Message lifecycle and timing

`NotificationMessageStatus` is Notification-owned:

```text
DRAFT
READY
SCHEDULED
DISPATCHING
SENT
DELIVERED
READ
ACKNOWLEDGED
FAILED
RETRY_PENDING
SUPPRESSED
CANCELLED
EXPIRED
```

The active creation service chooses `SCHEDULED` when `scheduledAt` is in the future and otherwise chooses `READY`.

Current invariant-classification evidence does not establish a general `scheduledAt <= expiresAt` domain rule for NotificationMessage, so HMSR-061 does not invent one.

The model's `terminalStatus()` helper is consistent with current lifecycle semantics and does not itself authorize additional transition rules.

### 74.7 Rendered content, locale and payload hash

`locale`, rendered subject/body/short-text and `payloadHash` are rendering/result state owned by Notification.

The DDD requires localized/versioned templates and exact traceability but does not require every channel to populate every rendered field. HMSR-061 therefore does not turn optional persistence columns into universal content invariants.

`payloadHash` remains optional trace metadata; current evidence does not establish a mandatory hashing rule for every message.

### 74.8 Delivery and safety boundary

READY messages are passed through `NotificationAsyncPushPort`, with delivery performed by Notification infrastructure. The Notification DDD explicitly states that Notification may inform people but must not actuate equipment, override SCADA, or execute operational control actions.

The current message/delivery architecture remains aligned with that boundary. HMSR-061 records no OT-control changes.

### 74.9 Required revisions

Four evidence-backed obligations remain:

1. **Enforce request/recipient consistency.** A `recipientId` must identify a NotificationRequestRecipient owned by the same `requestId` carried by the message; independent FK existence is insufficient.

2. **Enforce exact template-version traceability before READY/SCHEDULED/dispatch for template-rendered messages.** Resolve `templateVersionId`, preserve its relationship to `templateId`, and prevent a rendered/template-backed message from progressing without the exact immutable version used.

3. **Enforce required template variables before READY.** Validate the selected template version's required variable contract against the message-owned variable set before the message can enter a sendable state.

4. **Validate populated `priorityId` against the `NOTIFICATION_PRIORITY` family and eligibility semantics.**

HMSR-061 does not modify production Java, JPA, Flyway, API/application contracts, tests, Notification templates/variables/catalog data, delivery infrastructure, or provisioned data.

### 74.10 Review conclusion

**REVISE.** `NotificationMessage` is a coherent 17-field Notification-owned delivery model. Mandatory request, recipient and channel row existence is already protected by same-module FKs, its status enum is a genuine Notification lifecycle, and delivery remains separated from source-business ownership and OT actuation.

The target baseline cannot mark it APPROVED while request/recipient consistency is not enforced, template-rendered messages can become READY/SCHEDULED without exact immutable template-version traceability, required template variables are not proven before READY, and populated message priority is not family-validated.

HMS reconciliation must retain these obligations until explicitly authorized Notification production corrections resolve them or stronger repository evidence changes the governing DDD.

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

- `22536a68bc96966a4ce65027a6db11183d2e5239` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:113](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L113)
- [docs/roadmap/model-semantic-review.md:256](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L256)
- [docs/roadmap/model-semantic-review.md:12217](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L12217)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:256 | HMSR-061 | 2 | notification | NotificationMessage | — | notification.NotificationRequest, notification.NotificationTemplate | 1 | 1 | 3 | REVISE | Completed | docs(model-review): review notification NotificationMessage
doc/domain/SEMANTIC_DECISIONS.md:113 | HMSR-061 | [notification.NotificationMessage](#notification-notificationmessage) | REVISE | HMR-052
~~~~~~~~

<a id="hapi-382"></a>
## HAPI-382 — review notification NotificationDeliveryAttempt

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMSR-072

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review notification NotificationDeliveryAttempt

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/service/NotificationPayloadGuard.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/DeliveryAttemptStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/service/NotificationPayloadGuard.java`, `src/main/java/dz/sh/hidra/modules/notification/domain/value/DeliveryAttemptStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/service/NotificationPayloadGuard.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/DeliveryAttemptStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 85. HMSR-072 — notification.NotificationDeliveryAttempt review

**Decision:** REVISE  
**Review code:** HMSR-072  
**Dependency level:** 3  
**Bounded context:** notification  
**Confirmed upstream subject dependencies:** `notification.NotificationMessage`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0

### 85.1 Semantic role

`NotificationDeliveryAttempt` is the Notification-owned record of one attempt to deliver a NotificationMessage through a selected channel/provider.

It captures the parent message, attempt sequence metadata, channel, provider references, attempt lifecycle, timestamps, failure information, retry scheduling and correlation identity.

Notification owns delivery-attempt state. External providers own provider-specific delivery infrastructure and provider-assigned message identities.

### 85.2 Message and channel integrity

`messageId` and `channelId` are mandatory same-module references.

HRA-111 protects:

```text
hidra_notification_delivery_attempt.message_id
    -> hidra_notification_message.id

hidra_notification_delivery_attempt.channel_id
    -> hidra_notification_channel.id
```

Those row-existence protections are correct.

However, `NotificationMessage` itself already carries its selected `channelId`. The live `recordDeliveryAttempt()` path accepts `messageId` and `channelId` independently and does not load the message before persistence.

Therefore independent FK existence can still produce an attempt for message A through channel B even when message A was created for channel C.

The authoritative delivery-attempt write boundary must enforce:

```text
attempt.channelId == message.channelId
```

unless a future explicit Notification design introduces controlled channel failover semantics. HMSR-072 does not invent such failover behavior.

### 85.3 Append-only delivery evidence

The Notification DDD explicitly states:

```text
Attempts are append-only.
A message can have multiple attempts.
```

The current repository port exposes a generic `save(...)`, and the JPA adapter delegates directly to `repository.save(...)`. That persistence seam can update an existing row when the same ID is reused.

The target architecture must therefore enforce append-only attempt semantics at the authoritative application/persistence boundary so historical delivery evidence cannot be rewritten in place.

HMSR-072 does not prescribe one implementation mechanism; later reconciliation may use create-only repository semantics, persistence guards, database constraints/triggers, or another repository-consistent approach.

### 85.4 Permanent-failure retry semantics

The Notification DDD explicitly requires:

```text
A permanent failure must stop retry scheduling unless manually requeued.
```

`DeliveryAttemptStatus` includes:

```text
STARTED
SENT
DELIVERED
FAILED_TEMPORARY
FAILED_PERMANENT
TIMEOUT
CANCELLED
```

The domain helper already classifies `FAILED_PERMANENT` and `CANCELLED` as permanent failures.

However, the model and `recordDeliveryAttempt()` path currently allow `nextRetryAt` to be populated independently for any status, including permanent failure/cancellation.

The authoritative write path must therefore reject automatic retry scheduling for permanent-failure states unless the operation is an explicit manual requeue represented by the Notification retry workflow.

### 85.5 Attempt number

`attemptNumber` is mandatory at persistence level and is intended to distinguish repeated attempts for one message.

Current DDD evidence does not explicitly state:
- a minimum starting value,
- strict monotonic increment rules,
- uniqueness of `messageId + attemptNumber`.

The recommended index is non-unique.

HMSR-072 therefore does not invent numbering constraints beyond preserving the field as attempt-sequence metadata.

### 85.6 Provider references and secrets

`providerReference` and `providerMessageId` remain provider-facing metadata rather than Hidra-owned domain references.

The active application service applies `NotificationPayloadGuard.ensureNoProviderSecret(...)` to `providerReference`, consistent with the DDD rule that provider secrets must not be stored in Notification tables.

No relational target or external-provider FK is appropriate.

### 85.7 Lifecycle timestamps and failure metadata

`attemptedAt` is mandatory. `completedAt`, failure fields and `nextRetryAt` are optional.

Current Notification DDD does not define a complete status-to-field matrix beyond the permanent-failure retry rule. It also does not explicitly require `attemptedAt <= completedAt`.

HMSR-072 therefore does not manufacture additional timestamp ordering, mandatory failure-message, or provider-message-ID rules.

### 85.8 Delivery evidence and audit boundary

Notification owns operational delivery evidence. Audit owns immutable evidence of critical actions.

The DDD requires critical delivery events to be audit-ready, but this does not mean NotificationDeliveryAttempt itself should become an Audit-owned entity or gain a cross-module FK.

The reviewed model remains correctly owned by Notification.

### 85.9 Required revisions

Three evidence-backed obligations remain:

1. **Enforce message/channel consistency.** The attempt's `channelId` must match the referenced NotificationMessage's selected channel unless an explicitly modeled future failover rule authorizes otherwise.

2. **Enforce append-only delivery-attempt persistence.** Existing attempt evidence must not be mutable through a generic save/update path.

3. **Prevent automatic retry scheduling after permanent failure/cancellation.** `FAILED_PERMANENT` and `CANCELLED` attempts must not carry an automatic `nextRetryAt`; manual requeue must be represented through the explicit Notification retry workflow.

HMSR-072 does not modify production Java, JPA, Flyway, API/application contracts, tests, Notification message/channel/attempt data, provider integrations, Audit data, or provisioned data.

### 85.10 Review conclusion

**REVISE.** `NotificationDeliveryAttempt` is a coherent 14-field Notification-owned delivery-evidence model. Mandatory message and channel row existence is already database-protected, provider identities remain correctly external, and the lifecycle enum matches the Notification DDD.

The target baseline cannot mark it APPROVED while attempt/message channel consistency is unenforced, the persistence seam does not guarantee the DDD's append-only rule, and permanent failures can still be recorded with automatic retry scheduling.

HMS reconciliation must retain these obligations until explicitly authorized Notification production corrections resolve them or stronger repository evidence changes the governing DDD.

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

- `aaf9bb38a5bf32ec90e1dc08c36e4715323c8b15` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:124](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L124)
- [docs/roadmap/model-semantic-review.md:267](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L267)
- [docs/roadmap/model-semantic-review.md:13815](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L13815)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:267 | HMSR-072 | 3 | notification | NotificationDeliveryAttempt | — | notification.NotificationMessage | 0 | 0 | 2 | REVISE | Completed | docs(model-review): review notification NotificationDeliveryAttempt
doc/domain/SEMANTIC_DECISIONS.md:124 | HMSR-072 | [notification.NotificationDeliveryAttempt](#notification-notificationdeliveryattempt) | REVISE | HMR-060
~~~~~~~~

<a id="hapi-427"></a>
## HAPI-427 — review alarm Alarm

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMSR-117

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review alarm Alarm

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 130. HMSR-117 — alarm.Alarm review

**Decision:** REVISE  
**Review code:** HMSR-117  
**Dependency level:** 7  
**Bounded context:** alarm  
**Confirmed upstream subject dependencies:** optional cross-module `incident.Incident`, `organization.OrganizationUnit`, `planning.PlanTarget`, `telemetry.TelemetryReading`, `workflow.WorkflowInstance`; additional neutral Monitoring and Topology references; same-module Alarm catalog references  
**Confirmed direct HMS subject dependents:** 3  
**Transitive HMS subject dependents:** 3  
**Unresolved/non-subject reference count:** 4

### 130.1 Semantic role

`Alarm` is the Alarm-owned formal operational abnormality record. Monitoring detects candidates; Alarm owns formal alarm lifecycle; Incident owns incident response; Notification owns delivery; SCADA/OT actuation remains outside Alarm write authority.

Live implementation now exists under `dz.sh.hidra.modules.alarm`, so the opening metadata in `docs/data definition/Alarm.md` stating that no implemented Java module was found is stale documentary evidence and must not override the current repository implementation.

### 130.2 Field and persistence alignment

The live domain record, JPA entity, persistence mapper, application service, and base Alarm migration align on the 37-field Alarm shape.

`AlarmState` contains the DDD lifecycle values `RAISED`, `ACTIVE`, `ACKNOWLEDGED`, `SHELVED`, `SUPPRESSED`, `CLEARED`, `CLOSED`, `ESCALATED`, and `CANCELLED`.

`AlarmSourceType` contains the DDD source vocabulary `MONITORING_CANDIDATE`, `TELEMETRY_QUALITY`, `MANUAL`, `INTEGRATION`, `LEAK_DETECTION`, and `SAFETY_SYSTEM`.

The domain already fail-fast validates identity, alarm number, type, severity, source type, topology reference identity/code, current state, `raisedAt`, `lastUpdatedAt`, and `raisedAt <= closedAt`.

### 130.3 Catalog semantics

`alarmTypeId` and `severityId` are mandatory Alarm-owned catalog references. HRA-111 correctly installs row-existence FKs to `hidra_alarm_catalog_entry(id)`.

However, the Alarm DDD defines distinct catalog families including `ALARM_TYPE`, `ALARM_SEVERITY`, and `ALARM_PRIORITY`. Generic row existence does not prove membership in the intended family.

Future production reconciliation must validate `alarmTypeId` against `ALARM_TYPE`, `severityId` against `ALARM_SEVERITY`, and supplied `priorityId` against `ALARM_PRIORITY` through Alarm-owned catalog-family policy.

### 130.4 Minimum creation semantics

The Alarm DDD defines minimum creation data as alarm type, severity, source type, topology reference, raised time, and title/message.

The persistence schema specifically requires `titleFr`, but the domain record only normalizes it and does not reject blank/null values before persistence.

Future production reconciliation must fail closed on the required creation title/message according to the current Alarm language policy. The live schema currently makes French title the mandatory stored title, so the application/domain boundary must not allow a blank value to reach persistence.

### 130.5 Raise path and lifecycle-event gap

`AlarmApplicationService.raiseAlarm(...)` constructs an Alarm in `RAISED` state and saves it, but repository search found no corresponding `AlarmLifecycleEvent` persistence in this path.

The DDD requires every state change to produce exactly one append-only lifecycle event, and its acceptance criteria require traceable alarm lifecycle history.

Future production reconciliation must create the corresponding RAISED lifecycle event transactionally with alarm creation, or otherwise establish an equivalent authoritative event path that guarantees exactly-one lifecycle evidence for the initial formal state.

### 130.6 Acknowledgement lifecycle defect

`acknowledgeAlarm(...)` currently constructs and saves only `AlarmAcknowledgement`.

It does not load the referenced Alarm, reject acknowledgement after closure, update `currentState` / `acknowledgedAt` / `acknowledgedByActorId` / `lastUpdatedAt`, or create an `AlarmLifecycleEvent`.

This contradicts the DDD rule that an alarm cannot be acknowledged after closure and leaves the main Alarm row inconsistent with its acknowledgement evidence.

Future production reconciliation must make acknowledgement an Alarm-owned transactional lifecycle operation: load/fail closed on the Alarm, enforce lifecycle eligibility, persist acknowledgement evidence, update the Alarm snapshot/state fields, and write exactly one corresponding lifecycle event.

### 130.7 Closure lifecycle defect

`closeAlarm(...)` currently constructs and saves only `AlarmClosure`.

It does not load the Alarm, verify the DDD rule that normal closure follows clearing unless explicitly cancelled, update `currentState` / `closedAt` / `lastUpdatedAt`, or create an `AlarmLifecycleEvent`.

Future production reconciliation must make closure an Alarm-owned transactional lifecycle operation that loads/fails closed on the Alarm, enforces clear/cancel closure eligibility, persists closure evidence, updates the Alarm lifecycle fields, and records exactly one closure lifecycle event.

HMSR-117 does not invent additional closure prerequisites beyond the DDD's clear-before-close unless cancelled rule.

### 130.8 Cross-module reference boundaries

`telemetryReadingId`, `planningTargetId`, `workflowInstanceId`, `incidentId`, and `owningOrganizationUnitId` are cross-module references. Monitoring and Topology references are likewise neutral references/snapshots.

These must not gain cross-module database FKs. Where a write use case depends on current validity, validation belongs behind the owning module's public lookup/application contract.

Historical snapshot fields such as topology code/name and organization code/name must remain readable without live owner lookups.

### 130.9 Duplicate/noise rule

The Alarm DDD recommends avoiding duplicate active alarms for the same topology asset + alarm type + source reference, but labels this under recommended anti-noise rules rather than a hard invariant.

HMSR-117 therefore records no mandatory uniqueness constraint from that recommendation alone.

### 130.10 Production safety boundary

No reviewed Alarm production path performs SCADA/PLC/RTU/ESD/SIS actuation. The DDD's decision-support-only safety boundary is preserved.

### 130.11 Required revisions

Five evidence-backed production obligations remain:

1. **Validate Alarm catalog-family membership:** `alarmTypeId` → `ALARM_TYPE`, `severityId` → `ALARM_SEVERITY`, supplied `priorityId` → `ALARM_PRIORITY`.
2. **Fail closed on missing required title/message data before persistence; the live schema currently requires `titleFr`.**
3. **Record exactly one append-only lifecycle event for Alarm creation/RAISED state.**
4. **Make acknowledgement a transactional Alarm lifecycle operation:** load/fail closed on Alarm, reject closed alarms, persist acknowledgement, update Alarm acknowledgement/state fields, and emit exactly one lifecycle event.
5. **Make closure a transactional Alarm lifecycle operation:** load/fail closed on Alarm, enforce clear-before-close unless cancelled, persist closure, update Alarm closed state/timestamps, and emit exactly one lifecycle event.

Cross-module Monitoring/Telemetry/Planning/Topology/Organization/Workflow/Incident references must remain neutral scalars/snapshots with owner-controlled validation and no cross-module database FKs.

HMSR-117 does not modify production Java, JPA, Flyway, API/application contracts, tests, Alarm or upstream-module data, or provisioned data.

### 130.12 Review conclusion

**REVISE.** The 37-field Alarm shape, source/state enums, mandatory Alarm catalog row-existence FKs, topology snapshot design, cross-module reference boundaries, temporal close ordering guard, and OT safety boundary are structurally sound.

The baseline cannot be marked APPROVED while required title validation is missing, catalog-family membership is unenforced, and the authoritative raise/acknowledge/close paths do not maintain the Alarm row and append-only lifecycle-event timeline consistently.

HMS reconciliation must retain these obligations until explicitly authorized Alarm production work resolves them or stronger repository evidence changes the governing Alarm contract.

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

- `320763c974e2dfafb774a8bf07364f48d5144c6e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:169](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L169)
- [docs/roadmap/model-semantic-review.md:312](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L312)
- [docs/roadmap/model-semantic-review.md:19653](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L19653)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:312 | HMSR-117 | 7 | alarm | Alarm | — | incident.Incident, organization.OrganizationUnit, planning.PlanTarget, telemetry.TelemetryReading, workflow.WorkflowInstance | 3 | 3 | 4 | REVISE | Completed | docs(model-review): review alarm Alarm
doc/domain/SEMANTIC_DECISIONS.md:169 | HMSR-117 | [alarm.Alarm](#alarm-alarm) | REVISE | HMR-100
~~~~~~~~

<a id="hapi-431"></a>
## HAPI-431 — review alarm AlarmAcknowledgement

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMSR-121

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review alarm AlarmAcknowledgement

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 134. HMSR-121 — alarm.AlarmAcknowledgement review

**Decision:** REVISE  
**Review code:** HMSR-121  
**Dependency level:** 8  
**Bounded context:** alarm  
**Confirmed upstream subject dependencies:** mandatory same-module `alarm.Alarm`; optional Organization-owned `organization.OrganizationUnit`; Identity actor evidence remains a cross-module scalar/snapshot  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 0

### 134.1 Semantic role

`AlarmAcknowledgement` is Alarm-owned evidence that an actor has seen and taken responsibility for a formal alarm.

The acknowledgement is part of the Alarm lifecycle; it does not replace or independently own the Alarm's current state.

### 134.2 Field and persistence alignment

The live domain record, JPA entity, persistence mapper, application command/service path, repository adapter, and base Alarm migration align on the 9-field acknowledgement shape.

The domain already fail-fast validates the DDD-required `id`, `alarmId`, `acknowledgedByActorId`, and `acknowledgedAt` fields.

`acknowledgedByDisplayName`, Organization unit reference/code snapshot, optional comment, and correlation ID remain correctly optional.

### 134.3 Same-module Alarm integrity

`alarmId` is mandatory and HRA-111 correctly installs:

```text
hidra_alarm_acknowledgement.alarm_id
  -> hidra_alarm.id
```

This guarantees row existence at persistence, but the acknowledgement use case requires more than existence: it must enforce the owning Alarm lifecycle and keep the Alarm snapshot consistent with the acknowledgement evidence.

### 134.4 Application lifecycle defect

`AlarmApplicationService.acknowledgeAlarm(...)` currently constructs and persists only `AlarmAcknowledgement`.

It does not load the referenced `Alarm` through `AlarmRepositoryPort`, does not inspect the Alarm lifecycle before acknowledgement, and does not update the Alarm's acknowledgement/state fields.

The governing Alarm DDD states that an alarm cannot be acknowledged after closure. The authoritative acknowledgement path must therefore fail closed on an unknown Alarm and reject lifecycle-ineligible acknowledgement before writing evidence.

### 134.5 Alarm snapshot/state consistency

The main `Alarm` model contains `currentState`, `acknowledgedAt`, `acknowledgedByActorId`, and `lastUpdatedAt` fields specifically representing acknowledgement state on the formal Alarm.

Persisting only an `AlarmAcknowledgement` row permits split state: acknowledgement evidence can exist while the owning Alarm remains in `RAISED`/`ACTIVE` without its acknowledgement snapshot fields being updated.

Future production reconciliation must persist the acknowledgement evidence and the corresponding Alarm state/snapshot update atomically under the Alarm-owned lifecycle operation.

### 134.6 Lifecycle-event consistency

The Alarm DDD establishes `AlarmLifecycleEvent` as the append-only lifecycle timeline and requires lifecycle state changes to be traceable.

The current acknowledgement path does not write an `AlarmLifecycleEvent`.

Future production reconciliation must emit exactly one acknowledgement lifecycle event in the same transaction as the Alarm state update and acknowledgement evidence write.

### 134.7 Organization and actor boundaries

`organizationUnitId` and `organizationUnitCode` are optional Organization reference/snapshot fields. `acknowledgedByActorId` and display-name snapshot are Identity-owned actor evidence.

No Alarm-to-Organization or Alarm-to-Identity database FK is authorized. Historical acknowledgement evidence must remain readable after organizational or identity lifecycle changes.

Where live Organization or actor validity/authorization is required by the use case, validation belongs behind the owning module's public contract or security boundary, not through direct persistence coupling.

### 134.8 Duplicate acknowledgement semantics

The Alarm DDD does not state a one-row-only acknowledgement invariant or prohibit multiple acknowledgement records over an Alarm's lifetime.

HMSR-121 therefore does not invent a uniqueness constraint on `alarmId` or otherwise collapse acknowledgement history.

### 134.9 Required revisions

Three evidence-backed production obligations remain:

1. **Load/fail closed on the owning `Alarm` and reject lifecycle-ineligible acknowledgement, including acknowledgement after closure.**
2. **Persist acknowledgement evidence and update the owning Alarm's acknowledgement/state snapshot fields (`currentState`, `acknowledgedAt`, `acknowledgedByActorId`, `lastUpdatedAt`) atomically.**
3. **Record exactly one corresponding append-only `AlarmLifecycleEvent` for the acknowledgement transition in the same transactional lifecycle operation.**

Organization and Identity references remain cross-module scalars/snapshots with owner-controlled validation where required and no cross-module database FKs.

HMSR-121 does not modify production Java, JPA, Flyway, API/application contracts, tests, Alarm/Organization/Identity data, or provisioned data.

### 134.10 Review conclusion

**REVISE.** The 9-field shape, required acknowledgement invariants, same-module Alarm FK, and Organization/Identity snapshot boundaries are appropriate.

The baseline cannot be marked APPROVED while the authoritative acknowledgement path can persist evidence without validating the owning Alarm lifecycle, updating the Alarm's state/snapshot fields, or recording the corresponding lifecycle event.

HMS reconciliation must retain these obligations until explicitly authorized Alarm production work resolves them or stronger repository evidence changes the governing Alarm contract.

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

- `afa2fce76df4c702b6c82816dc0090e9bb8b67db` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:173](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L173)
- [docs/roadmap/model-semantic-review.md:316](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L316)
- [docs/roadmap/model-semantic-review.md:20038](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L20038)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:316 | HMSR-121 | 8 | alarm | AlarmAcknowledgement | — | alarm.Alarm, organization.OrganizationUnit | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review alarm AlarmAcknowledgement
doc/domain/SEMANTIC_DECISIONS.md:173 | HMSR-121 | [alarm.AlarmAcknowledgement](#alarm-alarmacknowledgement) | REVISE | HMR-104
~~~~~~~~

<a id="hapi-432"></a>
## HAPI-432 — review alarm AlarmClosure

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMSR-122

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review alarm AlarmClosure

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 135. HMSR-122 — alarm.AlarmClosure review

**Decision:** REVISE  
**Review code:** HMSR-122  
**Dependency level:** 8  
**Bounded context:** alarm  
**Confirmed upstream subject dependencies:** mandatory same-module `alarm.Alarm`; optional Workflow-owned `workflow.WorkflowInstance` review reference  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 0

### 135.1 Semantic role

`AlarmClosure` is the Alarm-owned administrative closure evidence created after normalization, cancellation, maintenance disposition, duplicate/false-alarm disposition, or escalation to Incident.

The DDD is explicit that closure is part of the Alarm lifecycle, must produce an `AlarmLifecycleEvent`, and allows only one closure record per Alarm.

### 135.2 Field and persistence alignment

The live domain record, JPA entity, application close command path, repository port, and base Alarm migration align on the 10-field closure shape.

The domain already fail-fast validates `id`, `alarmId`, `closureType`, `closedByActorId`, and `closedAt`.

`AlarmClosureType` contains `NORMALIZED`, `FALSE_ALARM`, `DUPLICATE`, `MAINTENANCE`, `CANCELLED`, and `ESCALATED_TO_INCIDENT`, matching the DDD vocabulary; current evidence supports retaining it as a constrained technical/business enum.

### 135.3 Same-module Alarm integrity

`alarmId` is mandatory and HRA-111 correctly installs:

```text
hidra_alarm_closure.alarm_id
  -> hidra_alarm.id
```

so a nonexistent Alarm fails at persistence.

That FK does not enforce lifecycle eligibility or consistency between closure evidence and the owning Alarm row.

### 135.4 Authoritative close-path defect

`AlarmApplicationService.closeAlarm(...)` currently constructs and saves only `AlarmClosure`.

It does not:

1. load the referenced Alarm;
2. fail closed on an unknown Alarm before constructing closure evidence;
3. enforce the DDD rule that normal closure occurs after clear unless explicitly cancelled;
4. update the Alarm `currentState`, `closedAt`, or `lastUpdatedAt`;
5. record the required `AlarmLifecycleEvent`.

Future production reconciliation must make closure one Alarm-owned transactional lifecycle operation that performs those steps together.

### 135.5 One-closure-per-alarm invariant

The DDD explicitly states: `Only one closure record per alarm.`

The live table has an index on `alarm_id` but no uniqueness constraint, and the repository/application path does not check for an existing closure.

Future production reconciliation must enforce one closure record per Alarm at an appropriate layer, with database reinforcement where consistent with the repository's chosen persistence invariant strategy.

### 135.6 Review-workflow semantics

`requiresReview` is mandatory and `reviewWorkflowInstanceId` is optional. The DDD describes the Workflow reference as applicable when review is required.

The current evidence does not provide a complete owner-controlled review workflow contract or an explicit invariant statement that `requiresReview=true` must always carry a non-null workflow instance at closure creation.

HMSR-122 therefore does not invent that mandatory coupling. If production policy later makes review workflow mandatory, validation must occur through Workflow-owned public contracts and no Alarm-to-Workflow database FK should be introduced.

### 135.7 Closure reason and actor boundaries

`closureReasonId` is optional. Current live evidence does not establish a precise Alarm catalog-family identifier for closure reasons strongly enough to invent one in HMSR-122.

`closedByActorId` is required evidence but remains Identity-owned; no Alarm-to-Identity database FK is authorized.

### 135.8 Required revisions

Three evidence-backed production obligations remain:

1. **Make `closeAlarm(...)` load/fail closed on the owning Alarm, enforce clear-before-close unless cancelled, update the Alarm lifecycle fields, persist `AlarmClosure`, and emit exactly one closure lifecycle event transactionally.**
2. **Enforce the DDD's one-closure-record-per-Alarm invariant.**
3. **Preserve Workflow and Identity ownership boundaries:** validate current references through owner-controlled contracts when required and do not add cross-module database FKs.

HMSR-122 does not modify production Java, JPA, Flyway, API/application contracts, tests, Alarm/Workflow/Identity data, or provisioned data.

### 135.9 Review conclusion

**REVISE.** The 10-field closure shape, closure-type enum, mandatory Alarm FK, and cross-module reference strategy are structurally appropriate.

The target baseline cannot be marked APPROVED while the authoritative close path can persist closure evidence without synchronizing Alarm lifecycle state or emitting lifecycle history, and while the explicit one-closure-per-Alarm invariant is unenforced.

HMS reconciliation must retain these obligations until explicitly authorized Alarm production work resolves them or stronger repository evidence changes the governing Alarm contract.

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

- `5dc9a2179aec416400b75544202bbfc6c244ee6d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:174](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L174)
- [docs/roadmap/model-semantic-review.md:317](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L317)
- [docs/roadmap/model-semantic-review.md:20132](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L20132)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:317 | HMSR-122 | 8 | alarm | AlarmClosure | — | alarm.Alarm, workflow.WorkflowInstance | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review alarm AlarmClosure
doc/domain/SEMANTIC_DECISIONS.md:174 | HMSR-122 | [alarm.AlarmClosure](#alarm-alarmclosure) | REVISE | HMR-105
~~~~~~~~

<a id="hapi-433"></a>
## HAPI-433 — review alarm AlarmShelving

Phase: HPS-011 — Alarms and Events

Legacy IDs: HMSR-123

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review alarm AlarmShelving

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`
- `src/main/java/dz/sh/hidra/platform/security/CurrentActorResolver.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 136. HMSR-123 — alarm.AlarmShelving review

**Decision:** REVISE  
**Review code:** HMSR-123  
**Dependency level:** 8  
**Bounded context:** alarm  
**Confirmed upstream subject dependencies:** mandatory same-module `alarm.Alarm`; required Alarm-owned shelving-reason catalog entry  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 0

### 136.1 Semantic role

`AlarmShelving` is Alarm-owned evidence for temporary, time-bounded, operator-approved reduction of a formal alarm's visibility/noise.

Shelving keeps the alarm known and within the Alarm lifecycle. It is distinct from suppression and does not delete, close, or transfer ownership of the alarm.

### 136.2 Field and persistence alignment

The live domain record, JPA entity, persistence mapper, repository adapter, application use case/service, and base Alarm migration align on the 11-field shelving shape.

The domain already fail-fast validates the required `id`, `alarmId`, `shelvingReasonId`, `shelvedByActorId`, `shelvedAt`, `shelvedUntil`, and `status` fields.

`AlarmShelvingStatus` contains `ACTIVE`, `EXPIRED`, `CANCELLED`, and `COMPLETED`, matching the DDD lifecycle vocabulary.

### 136.3 Same-module Alarm and catalog integrity

HRA-111 correctly installs same-module foreign keys:

```text
hidra_alarm_shelving.alarm_id
  -> hidra_alarm.id

hidra_alarm_shelving.shelving_reason_id
  -> hidra_alarm_catalog_entry.id
```

The first protects Alarm row existence. The second proves only that a generic Alarm catalog row exists; it does not prove membership in the required `SHELVING_REASON` catalog family.

Future production reconciliation must validate the shelving reason against the Alarm-owned `SHELVING_REASON` family rather than accepting any Alarm catalog entry.

### 136.4 Shelving lifecycle eligibility

`AlarmShelvingApplicationService.shelve(...)` loads the referenced Alarm and fails closed on an unknown identifier, but it does not inspect the Alarm's current lifecycle state.

The governing Alarm DDD requires only active/open alarms to be shelved and explicitly forbids shelving a closed alarm.

Future production reconciliation must enforce the DDD lifecycle eligibility before creating shelving evidence. HMSR-123 does not invent a broader exact state matrix beyond the states established by the Alarm lifecycle contract.

### 136.5 Time-bounded invariant

The DDD requires:

```text
shelvedUntil must be after shelvedAt
```

The authoritative create path currently checks that the requested end time is after the current instant and then uses that same instant as `shelvedAt`, so normal creation is protected.

The domain record itself, however, only checks both instants for non-nullity. Rehydration or another construction path can therefore represent `shelvedUntil <= shelvedAt`.

Future production reconciliation must enforce the intrinsic temporal ordering at the domain/persistence invariant boundary, not only in one application service path.

### 136.6 One-active-shelving invariant

The DDD explicitly allows only one active shelving record per alarm.

The current repository port exposes only `save(...)` and `findById(...)`; the shelving service does not check for another ACTIVE record, and the base schema has ordinary indexes but no concurrency-safe uniqueness protection for active shelving.

Future production reconciliation must enforce one ACTIVE shelving per Alarm under concurrent writes, using an Alarm-owned application/persistence strategy consistent with the repository's invariant architecture.

### 136.7 Alarm state and lifecycle-event consistency

The governing Alarm DDD states that every state change produces exactly one append-only `AlarmLifecycleEvent`, and the lifecycle vocabulary includes `SHELVED` and `UNSHELVED`.

The current shelving service writes or updates only `AlarmShelving`. It does not update the owning `Alarm.currentState` / `lastUpdatedAt`, and it does not record the corresponding lifecycle event.

That permits split state: an ACTIVE shelving record can exist while the formal Alarm row does not reflect shelving and the append-only lifecycle timeline contains no shelving transition.

Future production reconciliation must make shelving and unshelving Alarm-owned transactional lifecycle operations that keep the Alarm snapshot and exactly one corresponding lifecycle event consistent with the shelving evidence.

### 136.8 Automatic expiry

`shelvedUntil` is defined by the Alarm DDD as the automatic unshelving timestamp, and `AlarmShelvingStatus.EXPIRED` exists for expiry semantics.

The live shelving service supports explicit unshelving only. No shelving-expiry orchestration or due-expiry path was found in the reviewed live evidence.

Future production reconciliation must provide deterministic expiry handling so an ACTIVE shelving does not remain active indefinitely after `shelvedUntil`; expiry must preserve Alarm state/lifecycle-event consistency and record the resulting shelving status/time evidence without inventing an unsafe control action.

### 136.9 Actor and correlation semantics

`shelvedByActorId` is required and the public controller derives the actor from the authenticated principal through `CurrentActorResolver`, rather than trusting a browser-selected actor identifier.

`unshelvedByActorId` and `correlationId` remain appropriately optional in the model. Actor identifiers are Identity-owned evidence and must not become cross-module database foreign keys.

### 136.10 Required revisions

Five evidence-backed production obligations remain:

1. **Enforce Alarm lifecycle eligibility before shelving, including the DDD prohibition on shelving a closed alarm.**
2. **Validate `shelvingReasonId` against the Alarm-owned `SHELVING_REASON` catalog family, not merely generic catalog-row existence.**
3. **Enforce `shelvedUntil > shelvedAt` as an intrinsic model/persistence invariant and enforce only one ACTIVE shelving per Alarm with concurrency-safe protection.**
4. **Make shelving and unshelving transactional Alarm lifecycle operations that synchronize the owning Alarm state/timestamps and emit exactly one corresponding append-only lifecycle event.**
5. **Implement deterministic automatic expiry/unshelving at `shelvedUntil`, preserving shelving status/time evidence and Alarm lifecycle consistency.**

HMSR-123 does not modify production Java, JPA, Flyway, API/application contracts, tests, Alarm/Identity data, or provisioned data.

### 136.11 Review conclusion

**REVISE.** The 11-field shape, required field invariants, status enum, same-module Alarm/catalog foreign keys, authenticated actor sourcing, and time-bounded shelving concept are structurally appropriate.

The target baseline cannot be marked APPROVED while the authoritative shelving path does not enforce Alarm lifecycle eligibility, catalog-family semantics, the one-active-shelving invariant, Alarm/lifecycle-event synchronization, or deterministic expiry, and while the temporal ordering rule is not intrinsic to the model.

HMS reconciliation must retain these obligations until explicitly authorized Alarm production work resolves them or stronger repository evidence changes the governing Alarm contract.

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

- `c00614eec37085e986c19b60feb83e60eccf8a7c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:175](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L175)
- [docs/roadmap/model-semantic-review.md:318](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L318)
- [docs/roadmap/model-semantic-review.md:20224](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L20224)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:318 | HMSR-123 | 8 | alarm | AlarmShelving | — | alarm.Alarm | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review alarm AlarmShelving
doc/domain/SEMANTIC_DECISIONS.md:175 | HMSR-123 | [alarm.AlarmShelving](#alarm-alarmshelving) | REVISE | HMR-106
~~~~~~~~

<a id="hapi-652"></a>
## HAPI-652 — simplify boundary dto mapping

Phase: HPS-011 — Alarms and Events

Legacy IDs: HRA-071

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `alarm` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Pilot compile-time generated mapping for the HRA-070-approved exact alarm pairs only; retain security-enriched alarm mappings as hand-written code.

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/repository-remediation.md`
- `pom.xml`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/RaiseAlarmRequest.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/response/AlarmResponse.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/RaiseAlarmCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSummaryDto.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapperTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `alarm`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/repository-remediation.md`
- `pom.xml`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/request/RaiseAlarmRequest.java`
- `src/main/java/dz/sh/hidra/modules/alarm/api/rest/response/AlarmResponse.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/command/RaiseAlarmCommand.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/dto/AlarmSummaryDto.java`
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapperTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapperTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `28811d587a3ad18e4b8d1976ce4cdc51352ec2e4` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/repository-remediation.md:142](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/repository-remediation.md#L142)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/repository-remediation.md:142 | HRA-071 | refactor(alarm): simplify boundary dto mapping | Pilot compile-time generated mapping for the HRA-070-approved exact alarm pairs only; retain security-enriched alarm mappings as hand-written code. | RaiseAlarmRequest`→`RaiseAlarmCommand` and `AlarmSummaryDto`→`AlarmResponse` are generated with strict unmapped-property failures; acknowledgement/closure actor enrichment is unchanged; focused mapper coverage is added. | HRA-070 | **Completed** — introduced MapStruct compile-time mapping support, generated only the two exact alarm boundary mappings, preserved the existing static facade and hand-written authenticated acknowledgement/closure mappings, and added exact record-equality tests.
~~~~~~~~

<a id="hapi-707"></a>
## HAPI-707 — NOTIF-001 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-001

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-001 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-001 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1607](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1607)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1607 | NOTIF-001 | package skeleton | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-708"></a>
## HAPI-708 — NOTIF-002 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-002

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-002 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-002 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1608](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1608)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1608 | NOTIF-002 | catalog entries and translations | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-709"></a>
## HAPI-709 — NOTIF-003 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-003

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-003 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-003 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1609](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1609)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1609 | NOTIF-003 | template and template version model | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-710"></a>
## HAPI-710 — NOTIF-004 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-004

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-004 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-004 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1610](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1610)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1610 | NOTIF-004 | channel model | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-711"></a>
## HAPI-711 — NOTIF-005 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-005

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-005 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-005 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1611](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1611)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1611 | NOTIF-005 | recipient profile and contact point model | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-712"></a>
## HAPI-712 — NOTIF-006 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-006

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-006 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-006 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1612](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1612)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1612 | NOTIF-006 | notification policy and retry policy model | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-713"></a>
## HAPI-713 — NOTIF-007 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-007

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-007 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-007 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1613](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1613)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1613 | NOTIF-007 | notification request model | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-714"></a>
## HAPI-714 — NOTIF-008 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-008

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-008 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-008 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1614](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1614)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1614 | NOTIF-008 | message and message variable model | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-715"></a>
## HAPI-715 — NOTIF-009 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-009

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-009 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-009 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1615](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1615)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1615 | NOTIF-009 | delivery attempt and status history model | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-716"></a>
## HAPI-716 — NOTIF-010 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-010

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-010 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-010 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1616](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1616)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1616 | NOTIF-010 | scheduler and retry application services | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-717"></a>
## HAPI-717 — NOTIF-011 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-011

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-011 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-011 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1617](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1617)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1617 | NOTIF-011 | delivery gateway outbound port | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-718"></a>
## HAPI-718 — NOTIF-012 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-012

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-012 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-012 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1618](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1618)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1618 | NOTIF-012 | email/web stub adapters | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-719"></a>
## HAPI-719 — NOTIF-013 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-013

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-013 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-013 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1619](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1619)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1619 | NOTIF-013 | acknowledgement model | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-720"></a>
## HAPI-720 — NOTIF-014 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-014

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-014 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-014 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1620](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1620)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1620 | NOTIF-014 | audit event port | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-721"></a>
## HAPI-721 — NOTIF-015 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-015

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-015 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-015 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1621](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1621)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1621 | NOTIF-015 | REST API | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-722"></a>
## HAPI-722 — NOTIF-016 historical bounded obligation

Phase: HPS-011 — Alarms and Events

Legacy IDs: NOTIF-016

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `notification` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

NOTIF-016 historical bounded obligation

### 2. Existing Implementation

Alarm command/query lifecycle, approved suppression and append-only notification delivery records exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual event producers, delivery/provider behavior and end-to-end monitoring/alarm response before claiming realtime completion. The exact bounded remaining delivery is: NOTIF-016 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `notification`; primary phase owns this obligation. Recording a notification attempt does not establish external delivery; no invented STOMP event families. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`, `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/notification/api/rest/controller/SpringNotificationController.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/RecordDeliveryAttemptUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationIntegrationGatewayPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/service/NotificationApplicationService.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Govern alarm state, acknowledgement, shelving/suppression and traceable notification evidence.
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

- [docs/data definition/Notification.md:1622](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Notification.md#L1622)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Notification.md:1622 | NOTIF-016 | architecture tests | Deferred conceptual sequence
~~~~~~~~

<!-- task-register:payload:end -->
