# HPS-015 — Workflow and Approvals: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-015 — Workflow and Approvals

<a id="hapi-170"></a>
## HAPI-170 — scan workflow domain models

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMC-025

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 8 workflow model files.

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
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

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
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

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 34. HMC-025 — Workflow module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `workflow`  
**Baseline model files:** 8  
**Scanned models:** 8  
**Declared fields/components:** 131  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Workflow model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| WorkflowAction | record | 21 | `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java` | Extracted |
| WorkflowDefinition | record | 10 | `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java` | Extracted |
| WorkflowInstance | record | 21 | `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java` | Extracted |
| WorkflowStateHistory | record | 15 | `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java` | Extracted |
| WorkflowStep | record | 16 | `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java` | Extracted |
| WorkflowStepAssignmentRule | record | 12 | `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java` | Extracted |
| WorkflowTask | record | 24 | `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java` | Extracted |
| WorkflowTransition | record | 12 | `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java` | Extracted |

### Workflow fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| WorkflowAction | 1 | id | String |
| WorkflowAction | 2 | instanceId | String |
| WorkflowAction | 3 | taskId | String |
| WorkflowAction | 4 | actionType | WorkflowActionType |
| WorkflowAction | 5 | decision | WorkflowDecision |
| WorkflowAction | 6 | reasonId | String |
| WorkflowAction | 7 | decisionNote | String |
| WorkflowAction | 8 | commentText | String |
| WorkflowAction | 9 | actorId | String |
| WorkflowAction | 10 | actorUsernameSnapshot | String |
| WorkflowAction | 11 | actorDisplayNameSnapshot | String |
| WorkflowAction | 12 | actorRoleCodeSnapshot | String |
| WorkflowAction | 13 | organizationUnitId | String |
| WorkflowAction | 14 | organizationUnitNameSnapshot | String |
| WorkflowAction | 15 | organizationRoleCodeSnapshot | String |
| WorkflowAction | 16 | correlationId | String |
| WorkflowAction | 17 | actionSequence | long |
| WorkflowAction | 18 | sourceSystem | String |
| WorkflowAction | 19 | ipAddressHash | String |
| WorkflowAction | 20 | userAgentHash | String |
| WorkflowAction | 21 | actedAt | Instant |
| WorkflowDefinition | 1 | id | String |
| WorkflowDefinition | 2 | code | String |
| WorkflowDefinition | 3 | nameAr | String |
| WorkflowDefinition | 4 | nameFr | String |
| WorkflowDefinition | 5 | nameEn | String |
| WorkflowDefinition | 6 | typeId | String |
| WorkflowDefinition | 7 | status | WorkflowDefinitionStatus |
| WorkflowDefinition | 8 | version | int |
| WorkflowDefinition | 9 | createdAt | Instant |
| WorkflowDefinition | 10 | updatedAt | Instant |
| WorkflowInstance | 1 | id | String |
| WorkflowInstance | 2 | definitionId | String |
| WorkflowInstance | 3 | definitionVersion | int |
| WorkflowInstance | 4 | workflowPurposeId | String |
| WorkflowInstance | 5 | targetModule | String |
| WorkflowInstance | 6 | targetTypeId | String |
| WorkflowInstance | 7 | targetId | String |
| WorkflowInstance | 8 | targetCodeSnapshot | String |
| WorkflowInstance | 9 | targetLabelSnapshot | String |
| WorkflowInstance | 10 | status | WorkflowInstanceStatus |
| WorkflowInstance | 11 | currentStepId | String |
| WorkflowInstance | 12 | startedByActorId | String |
| WorkflowInstance | 13 | startedByUsernameSnapshot | String |
| WorkflowInstance | 14 | startedByDisplayNameSnapshot | String |
| WorkflowInstance | 15 | startedByRoleCodeSnapshot | String |
| WorkflowInstance | 16 | startedAt | Instant |
| WorkflowInstance | 17 | completedAt | Instant |
| WorkflowInstance | 18 | cancelledAt | Instant |
| WorkflowInstance | 19 | correlationId | String |
| WorkflowInstance | 20 | createdAt | Instant |
| WorkflowInstance | 21 | updatedAt | Instant |
| WorkflowStateHistory | 1 | id | String |
| WorkflowStateHistory | 2 | instanceId | String |
| WorkflowStateHistory | 3 | taskId | String |
| WorkflowStateHistory | 4 | fromStepId | String |
| WorkflowStateHistory | 5 | toStepId | String |
| WorkflowStateHistory | 6 | fromStatus | String |
| WorkflowStateHistory | 7 | toStatus | String |
| WorkflowStateHistory | 8 | actorId | String |
| WorkflowStateHistory | 9 | actorUsernameSnapshot | String |
| WorkflowStateHistory | 10 | actorDisplayNameSnapshot | String |
| WorkflowStateHistory | 11 | actorRoleCodeSnapshot | String |
| WorkflowStateHistory | 12 | actionId | String |
| WorkflowStateHistory | 13 | reasonId | String |
| WorkflowStateHistory | 14 | correlationId | String |
| WorkflowStateHistory | 15 | changedAt | Instant |
| WorkflowStep | 1 | id | String |
| WorkflowStep | 2 | definitionId | String |
| WorkflowStep | 3 | code | String |
| WorkflowStep | 4 | nameAr | String |
| WorkflowStep | 5 | nameFr | String |
| WorkflowStep | 6 | nameEn | String |
| WorkflowStep | 7 | stepOrder | int |
| WorkflowStep | 8 | mandatory | boolean |
| WorkflowStep | 9 | stepTypeId | String |
| WorkflowStep | 10 | defaultAssignmentRuleId | String |
| WorkflowStep | 11 | slaPolicyId | String |
| WorkflowStep | 12 | allowClaim | boolean |
| WorkflowStep | 13 | allowDelegation | boolean |
| WorkflowStep | 14 | allowEscalation | boolean |
| WorkflowStep | 15 | createdAt | Instant |
| WorkflowStep | 16 | updatedAt | Instant |
| WorkflowStepAssignmentRule | 1 | id | String |
| WorkflowStepAssignmentRule | 2 | definitionId | String |
| WorkflowStepAssignmentRule | 3 | stepId | String |
| WorkflowStepAssignmentRule | 4 | assignmentModeId | String |
| WorkflowStepAssignmentRule | 5 | actorId | String |
| WorkflowStepAssignmentRule | 6 | roleCode | String |
| WorkflowStepAssignmentRule | 7 | organizationUnitId | String |
| WorkflowStepAssignmentRule | 8 | organizationRoleCode | String |
| WorkflowStepAssignmentRule | 9 | targetOwnerMode | String |
| WorkflowStepAssignmentRule | 10 | active | boolean |
| WorkflowStepAssignmentRule | 11 | createdAt | Instant |
| WorkflowStepAssignmentRule | 12 | updatedAt | Instant |
| WorkflowTask | 1 | id | String |
| WorkflowTask | 2 | instanceId | String |
| WorkflowTask | 3 | stepId | String |
| WorkflowTask | 4 | status | WorkflowTaskStatus |
| WorkflowTask | 5 | assignedActorId | String |
| WorkflowTask | 6 | assignedActorUsernameSnapshot | String |
| WorkflowTask | 7 | assignedActorDisplayNameSnapshot | String |
| WorkflowTask | 8 | assignedOrganizationUnitId | String |
| WorkflowTask | 9 | assignedOrganizationUnitNameSnapshot | String |
| WorkflowTask | 10 | assignedRoleCodeSnapshot | String |
| WorkflowTask | 11 | priorityId | String |
| WorkflowTask | 12 | dueAt | Instant |
| WorkflowTask | 13 | claimedByActorId | String |
| WorkflowTask | 14 | claimedAt | Instant |
| WorkflowTask | 15 | completedByActorId | String |
| WorkflowTask | 16 | completedAt | Instant |
| WorkflowTask | 17 | assignmentModeId | String |
| WorkflowTask | 18 | taskLabelSnapshot | String |
| WorkflowTask | 19 | slaStatus | WorkflowSlaStatus |
| WorkflowTask | 20 | escalatedAt | Instant |
| WorkflowTask | 21 | delegatedAt | Instant |
| WorkflowTask | 22 | expiresAt | Instant |
| WorkflowTask | 23 | createdAt | Instant |
| WorkflowTask | 24 | updatedAt | Instant |
| WorkflowTransition | 1 | id | String |
| WorkflowTransition | 2 | definitionId | String |
| WorkflowTransition | 3 | fromStepId | String |
| WorkflowTransition | 4 | toStepId | String |
| WorkflowTransition | 5 | decision | WorkflowDecision |
| WorkflowTransition | 6 | reasonRequired | boolean |
| WorkflowTransition | 7 | commentRequired | boolean |
| WorkflowTransition | 8 | conditionExpression | String |
| WorkflowTransition | 9 | requiredPermissionCode | String |
| WorkflowTransition | 10 | targetModuleCallback | String |
| WorkflowTransition | 11 | createdAt | Instant |
| WorkflowTransition | 12 | updatedAt | Instant |

### HMC-025 reconciliation

```text
baseline model files = 8
extracted models     = 8
exceptions           = 0
declared fields      = 131
zero-field models    = 0
```

The scan records only declarations under `workflow/domain/model`. Workflow domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure orchestration adapter, identity implementation, organization implementation, target-module implementation, or database column metadata was mixed into this module scan.

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

- `22217b19faf22d7a6ba35200cfd56efc4403f10c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:153](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L153)
- [docs/roadmap/model-field-catalogue.md:2909](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2909)
- [docs/roadmap/model-field-catalogue.md:3068](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L3068)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:153 | HMC-025 | docs(catalogue): scan workflow domain models | Scan all 8 workflow model files. | **Completed** — 8/8 models, 131 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-175"></a>
## HAPI-175 — prepare executable remediation register

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMR-002

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Re-read all 104 REVISE sections against live main; populate exact per-task file allowlists, validations, prerequisites, already-fixed evidence, SCC ordering, and additive migration needs. Documentation only.

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/identity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/organization.md`
- `docs/roadmap/planning.md`
- `docs/roadmap/telemetry.md`
- `docs/roadmap/topology.md`
- `docs/roadmap/workflow.md`
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
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/request/CreateAnalyticsInsightRequest.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsInsightResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsProjectionRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/MetricEvaluationRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/command/CreateAnalyticsInsightCommand.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/command/FinalizeMetricEvaluationRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsInsightSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsProjectionRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/MetricEvaluationRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/mapper/AnalyticsApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsInsightUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsInsightRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsProjectionRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DigitalTwinReadinessAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationScopeResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsInsightRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaDigitalTwinReadinessAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricEvaluationRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightEvidenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsDatasetVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightEvidenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsProjectionRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/DigitalTwinReadinessAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricEvaluationRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/reference/AuthoritativeMetricEvaluationScopeResolverAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/CreateMaintenanceWorkOrderRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/RegisterMaintainableAssetRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/UpdateMaintainableAssetRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintainableAssetResponse.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintenanceWorkOrderResponse.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/command/CreateMaintenanceWorkOrderCommand.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/command/RegisterMaintainableAssetCommand.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintainableAssetSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintenanceWorkOrderSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/CreateMaintenanceWorkOrderUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/RegisterMaintainableAssetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/UpdateMaintainableAssetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsAuditReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsPartyReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsTelemetryReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsTopologyLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintainableAssetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintenanceWorkOrderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/exception/MaintainableAssetConflictException.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintainableAssetRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintainableAssetJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderTaskJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RecordAuditEventRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RequestAuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditAccessRecordResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditEventResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditExportRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/command/RecordAuditEventCommand.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditAccessRecordSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditEventSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditExportRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditEventRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditExportRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditAccessRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditBeforeAfterValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditEventJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditExportRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/request/CreateConfigurationDefinitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/request/CreateFeatureFlagRequest.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/request/SetConfigurationValueRequest.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/response/ConfigurationDefinitionResponse.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/response/ConfigurationValueResponse.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/response/FeatureFlagResponse.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/command/CreateConfigurationDefinitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/command/CreateFeatureFlagCommand.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/command/SetConfigurationValueCommand.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/dto/ConfigurationDefinitionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/dto/ConfigurationValueSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/dto/FeatureFlagSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/in/CreateConfigurationDefinitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/in/CreateFeatureFlagUseCase.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/in/SetConfigurationValueUseCase.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/out/FeatureFlagRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/exception/InvalidConfigurationValueException.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/service/ConfigurationValueGuard.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueType.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagEvaluationStrategy.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaFeatureFlagRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/mapper/ConfigurationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/ConfigurationDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/ConfigurationDefinitionVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/ConfigurationValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/FeatureFlagJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/FeatureFlagRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/CreateCustodyTransferTicketRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyDiscrepancyRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyMeasurementPeriodRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyDiscrepancyResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyMeasurementPeriodResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyTransferTicketResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/CreateCustodyTransferTicketCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyDiscrepancyCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyMeasurementPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyDiscrepancySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyMeasurementPeriodSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyTransferTicketSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDiscrepancyRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyMeasurementPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTransferTicketRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyDiscrepancyRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyMeasurementPeriodRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyDiscrepancyJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyMeasurementPeriodJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyTransferTicketJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/DocumentsApi.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsContentApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsRestApi.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/DocumentsController.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsController.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/mapper/DocumentsGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/mapper/DocumentsRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/LinkDocumentToTargetRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/RegisterDocumentRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentBinaryVersionRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentVersionRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentTargetLinkResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentVersionResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/LinkDocumentToTargetCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/RegisterDocumentCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentBinaryVersionCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentVersionCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentTargetLinkSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionContentDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/mapper/DocumentsApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/LinkDocumentToTargetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/RegisterDocumentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentBinaryVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentAuditEventPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentContentTransferException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsBoundaryViolationException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsDomainException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/InvalidDocumentValueException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/policy/DocumentsBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/service/DocumentStorageMetadataGuard.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentAccessLevel.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExternalSyncStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExtractionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExtractionType.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentId.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentPrincipalType.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentReviewStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/DocumentsInfrastructure.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/configuration/DocumentsModuleConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/DocumentsExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/NoopDocumentsExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsPersistence.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentAccessGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExternalReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExtractionRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentRetentionRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentReviewReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentAccessGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentCatalogTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentExternalReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentExtractionRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentRetentionRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentReviewReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentStorageObjectJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/DocumentStorageAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/NoopDocumentStorageAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/api/rest/request/CloseHseCaseRequest.java`
- `src/main/java/dz/sh/hidra/modules/hse/api/rest/request/OpenHseCaseRequest.java`
- `src/main/java/dz/sh/hidra/modules/hse/api/rest/response/HseCaseResponse.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/command/CloseHseCaseCommand.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/command/OpenHseCaseCommand.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/dto/HseCaseSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/in/CloseHseCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/in/HseQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/in/OpenHseCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseAlarmReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseAuditReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCorrectivePreventiveActionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseIncidentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseLeakDetectionReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseTopologyReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/service/HseCaseClosureGuard.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseSourceType.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCaseRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCorrectivePreventiveActionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseStatusHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/mapper/HsePersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseStatusHistoryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCorrectivePreventiveActionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/CreateUserRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/EvaluatePermissionRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/PermissionDecisionResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/UserResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/CreateUserCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/dto/PermissionDecisionDto.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/dto/UserSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/EvaluatePermissionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDecisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDelegationGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/ExternalRoleMappingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/GroupRoleGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/PermissionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RolePermissionGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RoleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/query/EvaluatePermissionQuery.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationDecisionValue.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/PermissionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/ExternalIdentityProviderClient.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/NoopExternalIdentityProviderClient.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDecisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDelegationGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaExternalRoleMappingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaGroupRoleGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaPermissionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRolePermissionGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRoleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDelegationGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalPermissionMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalRoleMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/PermissionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RoleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserGroupMembershipJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/AuthorizationDecisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/AuthorizationDelegationGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/ExternalPermissionMappingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/ExternalRoleMappingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/GroupRoleGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/IdentityProviderJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LoginSessionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/PermissionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RoleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RolePermissionGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserGroupMembershipJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserPermissionGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityEffectivePermissionSourceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/IncidentApi.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/IncidentRestApi.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/IncidentController.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/IncidentQueryController.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/SpringIncidentController.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/mapper/IncidentGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/mapper/IncidentRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/CloseIncidentRequest.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/OpenIncidentRequest.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/RecordIncidentResponseActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/response/IncidentResponse.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/command/CloseIncidentCommand.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/command/OpenIncidentCommand.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/command/RecordIncidentResponseActionCommand.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/dto/IncidentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/mapper/IncidentApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/in/CloseIncidentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/in/IncidentQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/in/OpenIncidentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/in/RecordIncidentResponseActionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRelatedIncidentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentResponseActionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentQueryApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/exception/IncidentDomainException.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/exception/IncidentLifecycleViolationException.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/exception/InvalidIncidentValueException.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/policy/IncidentBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/service/IncidentLifecycleGuard.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentEvidenceType.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentId.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentSourceType.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentStatus.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/IncidentInfrastructure.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/configuration/IncidentModuleConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/IncidentExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/NoopIncidentExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/IncidentPersistence.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRelatedIncidentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentResponseActionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentAttachmentReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentEscalationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentImpactAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResolutionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRootCauseAnalysisJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentTimelineEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/mapper/IncidentPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentAssignmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentAttachmentReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentCatalogTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentEscalationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentImpactAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentRelatedIncidentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentResolutionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentResponseActionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentRootCauseAnalysisJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentTimelineEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/RegisterExternalSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/StartIntegrationJobRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/ExternalSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationExchangeMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationJobRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/RegisterExternalSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/StartIntegrationJobRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/ExternalSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationExchangeMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationJobRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaExternalSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationJobRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/ExternalSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationDeadLetterRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationExchangeMessageJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunStepJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/CreateIntegrityAssessmentRequest.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/CreateIntegrityProgramRequest.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/OpenIntegrityCaseRequest.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityAssessmentResponse.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityCaseResponse.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityProgramResponse.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/command/CreateIntegrityAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/command/CreateIntegrityProgramCommand.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/command/OpenIntegrityCaseCommand.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityAssessmentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityCaseSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityProgramSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityProgramStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityCaseRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityProgramRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaPipelineDefectRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentScopeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseStatusHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityProgramJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/PipelineDefectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/mapper/IntegrityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityAssessmentScopeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityCaseStatusHistoryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityProgramJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/PipelineDefectJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/api/rest/request/CreateLeakCandidateRequest.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/api/rest/response/LeakCandidateResponse.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/command/CreateLeakCandidateCommand.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/dto/LeakCandidateSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/dto/LeakCandidateView.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/in/CreateLeakCandidateUseCase.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakCandidateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakDetectionCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakEscalationReferenceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/service/LeakDetectionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakCandidateStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakDetectionCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/integration/LeakDetectionExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/integration/NoopLeakDetectionExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakCandidateRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakDetectionCaseRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakEscalationReferenceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCandidateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEscalationReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/mapper/LeakDetectionPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/repository/LeakCandidateJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/repository/LeakDetectionCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/repository/LeakEscalationReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/request/CreateMonitoringRuleRequest.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/response/MonitoringRuleResponse.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/command/CreateMonitoringRuleCommand.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/dto/MonitoringRuleSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaMonitoringRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/PlanActualDeviationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/CreateNotificationMessageRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/ReceiveNotificationRequestRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationDeliveryAttemptResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/CreateNotificationMessageCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/ReceiveNotificationRequestCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationDeliveryAttemptSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationDeliveryAttemptRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationMessageRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationTemplateRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageVariableJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestRecipientJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationDeliveryAttemptJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageVariableJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestRecipientJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/api/rest/request/CreateOrganizationUnitRequest.java`
- `src/main/java/dz/sh/hidra/modules/organization/api/rest/response/OrganizationUnitResponse.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/command/CreateOrganizationUnitCommand.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/analytics/AnalyticsOrganizationScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/dto/OrganizationUnitSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/CreateOrganizationUnitUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationDelegationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationHierarchySnapshotRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitTypeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/PositionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ReportingLineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/EmployeeAssignmentApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/OrganizationUnitApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitKind.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionLevel.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingSubjectType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftType.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaEmployeeAssignmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationDelegationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationHierarchySnapshotRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitTypeRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaPositionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaReportingLineRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftAssignmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationDelegationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationHierarchySnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/EmployeeAssignmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationDelegationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationHierarchySnapshotJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationUnitJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationUnitTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/PositionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ReportingLineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ReportingLineTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ShiftAssignmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ShiftJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/party/api/PartyApi.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/PartyRestApi.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/controller/PartyController.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/controller/SpringPartyController.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/mapper/PartyGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/mapper/PartyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/request/AssignPartyRoleRequest.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/request/RegisterPartyRequest.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/response/PartyResponse.java`
- `src/main/java/dz/sh/hidra/modules/party/application/command/AssignPartyRoleCommand.java`
- `src/main/java/dz/sh/hidra/modules/party/application/command/RegisterPartyCommand.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/dto/PartySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/party/application/mapper/PartyApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/in/AssignPartyRoleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/in/RegisterPartyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRoleAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/PartyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/PartyRoleAssignmentApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/exception/BlockedPartySelectionException.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/exception/InvalidPartyValueException.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/exception/PartyDomainException.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/policy/PartyBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/service/PartySelectionService.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyAddressType.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyCatalogStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyCertificationStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyContactPersonStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyContactPointType.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyId.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRegistrationType.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRelationshipType.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRoleAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/PartyInfrastructure.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/configuration/PartyModuleConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/ExternalPartyMasterDataClient.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/NoopExternalPartyMasterDataClient.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/PartyPersistence.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRoleAssignmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyAddressJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyBankReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCertificationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyComplianceStatusJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyContactPersonJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyContactPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyDocumentReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyExternalReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyLegalProfileJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyOwnershipLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyQualificationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRegistrationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRelationshipJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRiskSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyStatusHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTaxIdentifierJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTypeTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/mapper/PartyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyAddressJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyBankReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyCatalogTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyCertificationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyComplianceStatusJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyContactPersonJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyContactPointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyDocumentReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyExternalReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyLegalProfileJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyOwnershipLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyQualificationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRegistrationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRelationshipJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRiskSnapshotJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleAssignmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyStatusHistoryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyTaxIdentifierJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyTypeTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreateOperationalPlanRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreatePlanningPeriodRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/OperationalPlanResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningPeriodResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/command/CreateOperationalPlanCommand.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/command/CreatePlanningPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/dto/OperationalPlanSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/dto/PlanningPeriodSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/OperationalPlanApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningPeriodApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/service/PlanningPeriodValidator.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaNominationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningPeriodRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationScheduleLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationScheduleLineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/OperationalPlanJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningPeriodJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/CreateReportDefinitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/QueueReportRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/RequestReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportDefinitionResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportOutputArtifactResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/CreateReportDefinitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/QueueReportRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportDefinitionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportOutputArtifactSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportOutputArtifactRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportOutputArtifactJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskAssessmentRequest.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskRegisterRequest.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskAssessmentResponse.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskRegisterResponse.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskRegisterCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskAssessmentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskRegisterSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMatrixCellRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskRegisterRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskMatrixCellRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskRegisterRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/ResidualRiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScopeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/ResidualRiskAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScopeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskRegisterJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationModelRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationScenarioRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/PublishSimulationRecommendationRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/QueueSimulationRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationModelResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRecommendationResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationScenarioResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationModelCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationScenarioCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/PublishSimulationRecommendationCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/QueueSimulationRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationModelSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRecommendationSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationScenarioSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationScenarioUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationCandidateChangeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStepStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioAssumptionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCandidateChangeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRecommendationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunStepJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioAssumptionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TrustTelemetryReadingUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/exception/TelemetryReadingValidationException.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/TelemetryTrustPolicy.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryPointRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetrySourceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryTrustEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointStateSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointStateSnapshotJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryReadingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceEndpointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/RegisterFacilityRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/FacilityResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/RegisterFacilityCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/FacilitySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/exception/InvalidTopologyConnectionException.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSegmentType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaEquipmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaTopologyConnectionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/ConnectionTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityNodeBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/ConnectionTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityNodeBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSegmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyConnectionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaLeakDetectionTopologyAssetContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionController.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowTaskRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ExecuteWorkflowTransitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RecordWorkflowActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTransitionExecutionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowTaskCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/ExecuteWorkflowTransitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/RecordWorkflowActionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowInstanceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTaskSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTransitionExecutionDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTransitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTransitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionDeniedException.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionTargetBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionTargetBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStateHistoryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTransitionJpaRepository.java`
- `src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql`
- `src/main/resources/db/migration/V20261004_004__hmr_004_party_party.sql`
- `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`
- `src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql`
- `src/main/resources/db/migration/V20261004_007__hmr_007_identity_role.sql`
- `src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql`
- `src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql`
- `src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql`
- `src/main/resources/db/migration/V20261004_011__hmr_011_identity_permission.sql`
- `src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql`
- `src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql`
- `src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql`
- `src/main/resources/db/migration/V20261004_015__hmr_015_leakdetection_leak_candidate.sql`
- `src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`
- `src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql`
- `src/main/resources/db/migration/V20261004_019__hmr_019_integrity_pipeline_defect.sql`
- `src/main/resources/db/migration/V20261004_020__hmr_020_organization_position.sql`
- `src/main/resources/db/migration/V20261004_021__hmr_021_organization_shift.sql`
- `src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql`
- `src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql`
- `src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql`
- `src/main/resources/db/migration/V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`
- `src/main/resources/db/migration/V20261004_026__hmr_026_configuration_feature_flag.sql`
- `src/main/resources/db/migration/V20261004_027__hmr_027_custody_custody_discrepancy.sql`
- `src/main/resources/db/migration/V20261004_028__hmr_028_organization_reporting_line.sql`
- `src/main/resources/db/migration/V20261004_029__hmr_029_risk_risk_matrix_cell.sql`
- `src/main/resources/db/migration/V20261004_030__hmr_030_telemetry_telemetry_source.sql`
- `src/main/resources/db/migration/V20261004_031__hmr_031_topology_topology_connection.sql`
- `src/main/resources/db/migration/V20261004_032__hmr_032_organization_organization_unit.sql`
- `src/main/resources/db/migration/V20261004_033__hmr_033_telemetry_telemetry_reading.sql`
- `src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql`
- `src/main/resources/db/migration/V20261004_035__hmr_035_notification_notification_request.sql`
- `src/main/resources/db/migration/V20261004_039__hmr_039_configuration_configuration_value.sql`
- `src/main/resources/db/migration/V20261004_040__hmr_040_monitoring_monitoring_rule.sql`
- `src/main/resources/db/migration/V20261004_041__hmr_041_party_party_role_assignment.sql`
- `src/main/resources/db/migration/V20261004_042__hmr_042_topology_pipeline.sql`
- `src/main/resources/db/migration/V20261004_043__hmr_043_workflow_workflow_step.sql`
- `src/main/resources/db/migration/V20261004_045__hmr_045_assets_maintainable_asset.sql`
- `src/main/resources/db/migration/V20261004_047__hmr_047_integration_external_system.sql`
- `src/main/resources/db/migration/V20261004_048__hmr_048_reporting_report_request.sql`
- `src/main/resources/db/migration/V20261004_049__hmr_049_risk_risk_register.sql`
- `src/main/resources/db/migration/V20261005_001__provision_risk_register_created_audit_taxonomy.sql`
- `src/main/resources/db/migration/V20261006_001__hmr_059_leak_escalation_candidate_integrity.sql`
- `src/main/resources/db/migration/V20261006_002__hmr_074_responsibility_assignment_id_required.sql`
- `src/main/resources/db/migration/V20261006_003__hmr_075_captured_by_employee_id_required.sql`
- `src/main/resources/db/migration/V20261006_004__hmr_076_organization_unit_id_required.sql`
- `src/main/resources/db/migration/V20261006_005__hmr_052_notification_message_composition.sql`
- `src/main/resources/db/migration/V20261006_006__hmr_060_notification_attempt_evidence.sql`
- `src/main/resources/db/migration/V20261006_008__hmr_053_trusted_telemetry_gate.sql`
- `src/main/resources/db/migration/V20261006_009__hmr_054_equipment_catalog_and_attachments.sql`
- `src/main/resources/db/migration/V20261006_010__hmr_063_identity_user_uniqueness.sql`
- `src/main/resources/db/migration/V20261006_011__hmr_086_delegation_contract.sql`
- `src/main/resources/db/migration/V20261007_001__hmr_064_planning_plan_revision.sql`
- `src/main/resources/db/migration/V20261007_002__hmr_065_planning_operational_plan.sql`
- `src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql`
- `src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql`
- `src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql`
- `src/main/resources/db/migration/V20261007_006__hmr_083_audit_export_request.sql`
- `src/main/resources/db/migration/V20261007_007__hmr_095_audit_event.sql`
- `src/main/resources/db/migration/V20261007_008__hmr_101_audit_access_record.sql`
- `src/main/resources/db/migration/V20261007_009__hmr_102_audit_before_after_value.sql`
- `src/main/resources/db/migration/V20261007_010__hmr_056_integration_exchange_message.sql`
- `src/main/resources/db/migration/V20261007_011__hmr_071_integration_dead_letter_record.sql`
- `src/main/resources/db/migration/V20261007_012__hmr_057_reporting_report_run.sql`
- `src/main/resources/db/migration/V20261007_013__hmr_093_reporting_report_output_artifact.sql`
- `src/main/resources/db/migration/V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`
- `src/main/resources/db/migration/V20261008_002__hmr_079_simulation_recommendation_integrity.sql`
- `src/main/resources/db/migration/V20261008_011__hmr_069_assets_maintenance_work_order.sql`
- `src/main/resources/db/migration/V20261008_012__hmr_070_custody_custody_transfer_ticket.sql`
- `src/main/resources/db/migration/V20261008_013__hmr_072_integrity_integrity_assessment.sql`
- `src/main/resources/db/migration/V20261008_014__hmr_082_hse_case_lifecycle.sql`
- `src/main/resources/db/migration/V20261008_015__hmr_096_hse_closure_atomic_evidence.sql`
- `src/main/resources/db/migration/V20261008_016__hmr_097_hse_capa_reference_catalog_integrity.sql`
- `src/main/resources/db/migration/V20261008_017__hmr_098_integrity_case_catalog_field_policy.sql`
- `src/main/resources/db/migration/V20261008_018__hmr_098_integrity_case_reference_integrity.sql`
- `src/main/resources/db/migration/V20261008_019__hmr_094_planning_target_value_policy.sql`
- `src/main/resources/db/migration/V20261008_020__hmr_094_planning_plan_target_integrity.sql`
- `src/main/resources/db/migration/V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
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
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsInsightSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsProjectionRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/DigitalTwinReadinessAssessmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricEvaluationRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricValueSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintainableAssetSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationDefinitionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationValueSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/configuration/semantic/FeatureFlagSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyMeasurementPeriodSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentStorageObjectSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCaseSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseClosureSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCorrectivePreventiveActionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityControllerPermissionScopeApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapperPermissionScopeTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDecisionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDelegationGrantSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/IdentityProviderSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/PermissionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/RoleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserPermissionGrantSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserRoleGrantSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/application/service/IncidentQueryApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentClosureSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentRelatedIncidentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentResponseActionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/ExternalSystemSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationJobRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityAssessmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityCaseSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityProgramSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/PipelineDefectSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakCandidateSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakDetectionCaseSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakEscalationReferenceSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationTemplateSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/command/CreateOrganizationUnitCommandTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ReportingLinePersistenceMapperTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/EmployeeAssignmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationDelegationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationHierarchySnapshotSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationUnitSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/PositionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/ReportingLineSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftAssignmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/party/semantic/PartyRoleAssignmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/party/semantic/PartySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanningPeriodSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportDefinitionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskMatrixCellSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskRegisterSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationModelSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationScenarioSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryReadingSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetrySourceSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaLeakDetectionTopologyAssetContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSystemSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/TopologyConnectionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowDefinitionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepAssignmentRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Re-read all 104 REVISE sections against live main; populate exact per-task file allowlists, validations, prerequisites, already-fixed evidence, SCC ordering, and additive migration needs. Documentation only.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmDomainException.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmLifecycleViolationException.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/AlarmSuppressionConflictException.java`, `src/main/java/dz/sh/hidra/modules/alarm/domain/exception/InvalidAlarmValueException.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AcknowledgeAlarmUseCase.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/AlarmSuppressionQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/CloseAlarmUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`, `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalService.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/AlarmInfrastructure.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/configuration/AlarmModuleConfiguration.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/AlarmExternalReferenceResolver.java`, `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/NoopAlarmExternalReferenceResolver.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/identity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/organization.md`
- `docs/roadmap/planning.md`
- `docs/roadmap/telemetry.md`
- `docs/roadmap/topology.md`
- `docs/roadmap/workflow.md`
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
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/request/CreateAnalyticsInsightRequest.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsInsightResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsProjectionRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/MetricEvaluationRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/command/CreateAnalyticsInsightCommand.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/command/FinalizeMetricEvaluationRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsInsightSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsProjectionRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/MetricEvaluationRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/mapper/AnalyticsApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsInsightUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsInsightRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsProjectionRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DigitalTwinReadinessAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationScopeResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsInsightRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaDigitalTwinReadinessAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricEvaluationRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightEvidenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsDatasetVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightEvidenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsProjectionRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/DigitalTwinReadinessAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricEvaluationRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/reference/AuthoritativeMetricEvaluationScopeResolverAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/CreateMaintenanceWorkOrderRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/RegisterMaintainableAssetRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/UpdateMaintainableAssetRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintainableAssetResponse.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintenanceWorkOrderResponse.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/command/CreateMaintenanceWorkOrderCommand.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/command/RegisterMaintainableAssetCommand.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintainableAssetSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintenanceWorkOrderSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/CreateMaintenanceWorkOrderUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/RegisterMaintainableAssetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/UpdateMaintainableAssetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsAuditReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsPartyReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsTelemetryReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsTopologyLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/AssetsWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintainableAssetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintenanceWorkOrderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/AssetsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/exception/MaintainableAssetConflictException.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintainableAssetRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintainableAssetJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderTaskJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RecordAuditEventRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RequestAuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditAccessRecordResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditEventResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditExportRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/command/RecordAuditEventCommand.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditAccessRecordSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditEventSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditExportRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditEventRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditExportRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditAccessRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditBeforeAfterValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditEventJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditExportRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/request/CreateConfigurationDefinitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/request/CreateFeatureFlagRequest.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/request/SetConfigurationValueRequest.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/response/ConfigurationDefinitionResponse.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/response/ConfigurationValueResponse.java`
- `src/main/java/dz/sh/hidra/modules/configuration/api/rest/response/FeatureFlagResponse.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/command/CreateConfigurationDefinitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/command/CreateFeatureFlagCommand.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/command/SetConfigurationValueCommand.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/dto/ConfigurationDefinitionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/dto/ConfigurationValueSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/dto/FeatureFlagSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/in/CreateConfigurationDefinitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/in/CreateFeatureFlagUseCase.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/in/SetConfigurationValueUseCase.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/configuration/application/port/out/FeatureFlagRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/exception/InvalidConfigurationValueException.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/service/ConfigurationValueGuard.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueType.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagEvaluationStrategy.java`
- `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagStatus.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaFeatureFlagRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/mapper/ConfigurationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/ConfigurationDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/ConfigurationDefinitionVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/ConfigurationValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/FeatureFlagJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/repository/FeatureFlagRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/CreateCustodyTransferTicketRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyDiscrepancyRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyMeasurementPeriodRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyDiscrepancyResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyMeasurementPeriodResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyTransferTicketResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/CreateCustodyTransferTicketCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyDiscrepancyCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyMeasurementPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyDiscrepancySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyMeasurementPeriodSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyTransferTicketSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDiscrepancyRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyMeasurementPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTransferTicketRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyDiscrepancyRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyMeasurementPeriodRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyDiscrepancyJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyMeasurementPeriodJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyTransferTicketJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/DocumentsApi.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsContentApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsRestApi.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/DocumentsController.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsController.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/mapper/DocumentsGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/mapper/DocumentsRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/LinkDocumentToTargetRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/RegisterDocumentRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentBinaryVersionRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentVersionRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentTargetLinkResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentVersionResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/LinkDocumentToTargetCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/RegisterDocumentCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentBinaryVersionCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentVersionCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentTargetLinkSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionContentDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/mapper/DocumentsApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/LinkDocumentToTargetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/RegisterDocumentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentBinaryVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentAuditEventPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentContentTransferException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsBoundaryViolationException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsDomainException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/InvalidDocumentValueException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/policy/DocumentsBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/service/DocumentStorageMetadataGuard.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentAccessLevel.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExternalSyncStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExtractionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExtractionType.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentId.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentPrincipalType.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentReviewStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/DocumentsInfrastructure.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/configuration/DocumentsModuleConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/DocumentsExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/NoopDocumentsExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsPersistence.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentAccessGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExternalReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExtractionRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentRetentionRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentReviewReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentAccessGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentCatalogTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentExternalReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentExtractionRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentRetentionRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentReviewReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentStorageObjectJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/DocumentStorageAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/NoopDocumentStorageAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/api/rest/request/CloseHseCaseRequest.java`
- `src/main/java/dz/sh/hidra/modules/hse/api/rest/request/OpenHseCaseRequest.java`
- `src/main/java/dz/sh/hidra/modules/hse/api/rest/response/HseCaseResponse.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/command/CloseHseCaseCommand.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/command/OpenHseCaseCommand.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/dto/HseCaseSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/in/CloseHseCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/in/HseQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/in/OpenHseCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseAlarmReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseAuditReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCorrectivePreventiveActionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseIncidentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseLeakDetectionReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseTopologyReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/service/HseCaseClosureGuard.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseSourceType.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCaseRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCorrectivePreventiveActionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseStatusHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/mapper/HsePersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseStatusHistoryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCorrectivePreventiveActionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/CreateUserRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/request/EvaluatePermissionRequest.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/PermissionDecisionResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/response/UserResponse.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/command/CreateUserCommand.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/dto/PermissionDecisionDto.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/dto/UserSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/in/EvaluatePermissionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDecisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDelegationGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/ExternalRoleMappingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/GroupRoleGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/PermissionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RolePermissionGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/RoleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/query/EvaluatePermissionQuery.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationDecisionValue.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/PermissionStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleType.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/ExternalIdentityProviderClient.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/integration/NoopExternalIdentityProviderClient.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDecisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDelegationGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaExternalRoleMappingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaGroupRoleGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaPermissionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRolePermissionGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRoleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDelegationGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalPermissionMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalRoleMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/PermissionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RoleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserGroupMembershipJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/mapper/IdentityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/AuthorizationDecisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/AuthorizationDelegationGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/ExternalPermissionMappingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/ExternalRoleMappingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/GroupRoleGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/IdentityProviderJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/LoginSessionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/PermissionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RoleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/RolePermissionGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserGroupMembershipJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserPermissionGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/repository/UserRoleGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityEffectivePermissionSourceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/IncidentApi.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/IncidentRestApi.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/IncidentController.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/IncidentQueryController.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/controller/SpringIncidentController.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/mapper/IncidentGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/mapper/IncidentRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/CloseIncidentRequest.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/OpenIncidentRequest.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/request/RecordIncidentResponseActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/incident/api/rest/response/IncidentResponse.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/command/CloseIncidentCommand.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/command/OpenIncidentCommand.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/command/RecordIncidentResponseActionCommand.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/dto/IncidentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/mapper/IncidentApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/in/CloseIncidentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/in/IncidentQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/in/OpenIncidentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/in/RecordIncidentResponseActionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRelatedIncidentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentResponseActionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentQueryApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/exception/IncidentDomainException.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/exception/IncidentLifecycleViolationException.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/exception/InvalidIncidentValueException.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/policy/IncidentBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/service/IncidentLifecycleGuard.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentEvidenceType.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentId.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentSourceType.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentStatus.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/IncidentInfrastructure.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/configuration/IncidentModuleConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/IncidentExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/NoopIncidentExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/IncidentPersistence.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRelatedIncidentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentResponseActionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentAttachmentReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentEscalationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentImpactAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResolutionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRootCauseAnalysisJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentTimelineEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/mapper/IncidentPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentAssignmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentAttachmentReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentCatalogTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentEscalationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentImpactAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentRelatedIncidentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentResolutionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentResponseActionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentRootCauseAnalysisJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentTimelineEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/RegisterExternalSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/request/StartIntegrationJobRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/ExternalSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationExchangeMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationJobRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/RegisterExternalSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/command/StartIntegrationJobRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/ExternalSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationExchangeMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationJobRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/service/IntegrationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaExternalSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationJobRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/ExternalSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationDeadLetterRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationExchangeMessageJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationJobRunStepJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/CreateIntegrityAssessmentRequest.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/CreateIntegrityProgramRequest.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/OpenIntegrityCaseRequest.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityAssessmentResponse.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityCaseResponse.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityProgramResponse.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/command/CreateIntegrityAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/command/CreateIntegrityProgramCommand.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/command/OpenIntegrityCaseCommand.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityAssessmentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityCaseSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityProgramSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityProgramUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityProgramRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityProgramStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityCaseRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityProgramRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaPipelineDefectRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentScopeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseStatusHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityProgramJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/PipelineDefectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/mapper/IntegrityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityAssessmentScopeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityCaseStatusHistoryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityProgramJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/PipelineDefectJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/api/rest/request/CreateLeakCandidateRequest.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/api/rest/response/LeakCandidateResponse.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/command/CreateLeakCandidateCommand.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/dto/LeakCandidateSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/dto/LeakCandidateView.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/in/CreateLeakCandidateUseCase.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakCandidateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakDetectionCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/port/out/LeakEscalationReferenceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/application/service/LeakDetectionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakCandidateStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakDetectionCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/integration/LeakDetectionExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/integration/NoopLeakDetectionExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakCandidateRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakDetectionCaseRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/adapter/JpaLeakEscalationReferenceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCandidateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEscalationReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/mapper/LeakDetectionPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/repository/LeakCandidateJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/repository/LeakDetectionCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/repository/LeakEscalationReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/request/CreateMonitoringRuleRequest.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/response/MonitoringRuleResponse.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/command/CreateMonitoringRuleCommand.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/dto/MonitoringRuleSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaMonitoringRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/PlanActualDeviationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/CreateNotificationMessageRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/request/ReceiveNotificationRequestRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationDeliveryAttemptResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/api/rest/response/NotificationRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/CreateNotificationMessageCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/command/ReceiveNotificationRequestCommand.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationDeliveryAttemptSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/dto/NotificationRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/CreateNotificationMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/in/ReceiveNotificationRequestUseCase.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationDeliveryAttemptRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/application/port/out/NotificationTemplateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationDeliveryAttemptRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationMessageRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/adapter/JpaNotificationTemplateRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageVariableJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestRecipientJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/mapper/NotificationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationDeliveryAttemptJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationMessageVariableJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationRequestRecipientJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/repository/NotificationTemplateVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/api/rest/request/CreateOrganizationUnitRequest.java`
- `src/main/java/dz/sh/hidra/modules/organization/api/rest/response/OrganizationUnitResponse.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/command/CreateOrganizationUnitCommand.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/analytics/AnalyticsOrganizationScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/AssetsOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/IntegrationOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/integration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/dto/OrganizationUnitSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/CreateOrganizationUnitUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationDelegationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationHierarchySnapshotRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitTypeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/PositionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ReportingLineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/EmployeeAssignmentApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/OrganizationUnitApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitKind.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionLevel.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingSubjectType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftType.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaEmployeeAssignmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationDelegationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationHierarchySnapshotRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaOrganizationUnitTypeRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaPositionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaReportingLineRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftAssignmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/adapter/JpaShiftRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationDelegationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationHierarchySnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OperationalScopePersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/OrganizationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ResponsibilityAssignmentPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/EmployeeAssignmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationDelegationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationHierarchySnapshotJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationUnitJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/OrganizationUnitTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/PositionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ReportingLineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ReportingLineTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ShiftAssignmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/repository/ShiftJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/party/api/PartyApi.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/PartyRestApi.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/controller/PartyController.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/controller/SpringPartyController.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/mapper/PartyGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/mapper/PartyRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/request/AssignPartyRoleRequest.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/request/RegisterPartyRequest.java`
- `src/main/java/dz/sh/hidra/modules/party/api/rest/response/PartyResponse.java`
- `src/main/java/dz/sh/hidra/modules/party/application/command/AssignPartyRoleCommand.java`
- `src/main/java/dz/sh/hidra/modules/party/application/command/RegisterPartyCommand.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/AssetsPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/topology/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/application/dto/PartySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/party/application/mapper/PartyApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/in/AssignPartyRoleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/in/RegisterPartyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/party/application/port/out/PartyRoleAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/PartyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/PartyRoleAssignmentApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/exception/BlockedPartySelectionException.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/exception/InvalidPartyValueException.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/exception/PartyDomainException.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/policy/PartyBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/service/PartySelectionService.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyAddressType.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyCatalogStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyCertificationStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyContactPersonStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyContactPointType.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyId.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRegistrationType.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRelationshipType.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRoleAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyStatus.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/PartyInfrastructure.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/configuration/PartyModuleConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/ExternalPartyMasterDataClient.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/NoopExternalPartyMasterDataClient.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/PartyPersistence.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JpaPartyRoleAssignmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyAddressJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyBankReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyCertificationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyComplianceStatusJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyContactPersonJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyContactPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyDocumentReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyExternalReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyLegalProfileJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyOwnershipLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyQualificationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRegistrationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRelationshipJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRiskSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyStatusHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTaxIdentifierJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyTypeTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/mapper/PartyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyAddressJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyBankReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyCatalogTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyCertificationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyComplianceStatusJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyContactPersonJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyContactPointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyDocumentReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyExternalReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyLegalProfileJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyOwnershipLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyQualificationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRegistrationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRelationshipJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRiskSnapshotJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleAssignmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyRoleTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyStatusHistoryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyTaxIdentifierJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyTypeTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreateOperationalPlanRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreatePlanningPeriodRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/OperationalPlanResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/PlanningPeriodResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/command/CreateOperationalPlanCommand.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/command/CreatePlanningPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/dto/OperationalPlanSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/dto/PlanningPeriodSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/OperationalPlanApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningPeriodApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/service/PlanningPeriodValidator.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaNominationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningPeriodRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationScheduleLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationScheduleLineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/OperationalPlanJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningPeriodJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/CreateReportDefinitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/QueueReportRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/RequestReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportDefinitionResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportOutputArtifactResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/CreateReportDefinitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/QueueReportRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportDefinitionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportOutputArtifactSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportOutputArtifactRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportOutputArtifactJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskAssessmentRequest.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskRegisterRequest.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskAssessmentResponse.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskRegisterResponse.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskRegisterCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskAssessmentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskRegisterSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMatrixCellRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskRegisterRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskMatrixCellRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskRegisterRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/ResidualRiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScopeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/ResidualRiskAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScopeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskRegisterJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationModelRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/CreateSimulationScenarioRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/PublishSimulationRecommendationRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/request/QueueSimulationRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationModelResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRecommendationResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/api/rest/response/SimulationScenarioResponse.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationModelCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/CreateSimulationScenarioCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/PublishSimulationRecommendationCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/command/QueueSimulationRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationModelSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRecommendationSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/dto/SimulationScenarioSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationModelUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/CreateSimulationScenarioUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/PublishSimulationRecommendationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/in/QueueSimulationRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/MonitoringContextLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/PlanningSnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationCandidateChangeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationModelRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationScenarioRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/TopologySnapshotLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStepStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/NoopSimulationExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/SimulationExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationModelRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationScenarioRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioAssumptionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/mapper/SimulationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCandidateChangeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationModelVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRecommendationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationRunStepJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioAssumptionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationScenarioJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/CreateTelemetrySourceRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/request/RegisterTelemetryPointRequest.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetryPointResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/response/TelemetrySourceResponse.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/CreateTelemetrySourceCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/command/RegisterTelemetryPointCommand.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetryPointSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/dto/TelemetrySourceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TrustTelemetryReadingUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryTrustEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetryPointApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TelemetrySourceApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/exception/TelemetryReadingValidationException.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/policy/TelemetryTrustPolicy.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/service/TelemetryReadingValueValidator.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryPointRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetrySourceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTelemetryTrustEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/adapter/JpaTrustedTelemetryReadingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointStateSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryPointStateSnapshotJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryReadingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceEndpointJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetrySourceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TrustedTelemetryReadingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/CreatePipelineSystemRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/request/RegisterFacilityRequest.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/FacilityResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/response/PipelineSystemResponse.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/CreatePipelineSystemCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/command/RegisterFacilityCommand.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/AssetsTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/leakdetection/LeakDetectionTopologyAssetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/organization/TopologyOperationalScopeTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/FacilitySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/dto/PipelineSystemSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/FacilityApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/exception/InvalidTopologyConnectionException.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/service/TopologyConnectionValidator.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionTypeReference.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSegmentType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaEquipmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaFacilityRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaPipelineSystemRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JpaTopologyConnectionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/ConnectionTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentAttributeValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentTypeVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityAttributeValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityNodeBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityTypeVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineTypeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/mapper/TopologyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/ConnectionTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentAttributeValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/EquipmentTypeVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityAttributeValueJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityNodeBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/FacilityTypeVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSegmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemFacilityJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineSystemTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/PipelineTypeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/repository/TopologyConnectionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaLeakDetectionTopologyAssetContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionController.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowTaskRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ExecuteWorkflowTransitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RecordWorkflowActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTransitionExecutionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowTaskCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/ExecuteWorkflowTransitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/RecordWorkflowActionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowInstanceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTaskSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTransitionExecutionDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTransitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTransitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionDeniedException.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionTargetBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionTargetBindingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStateHistoryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTransitionJpaRepository.java`
- `src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql`
- `src/main/resources/db/migration/V20261004_004__hmr_004_party_party.sql`
- `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`
- `src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql`
- `src/main/resources/db/migration/V20261004_007__hmr_007_identity_role.sql`
- `src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql`
- `src/main/resources/db/migration/V20261004_009__hmr_009_simulation_simulation_model.sql`
- `src/main/resources/db/migration/V20261004_010__hmr_010_identity_identity_provider.sql`
- `src/main/resources/db/migration/V20261004_011__hmr_011_identity_permission.sql`
- `src/main/resources/db/migration/V20261004_012__hmr_012_notification_notification_template.sql`
- `src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql`
- `src/main/resources/db/migration/V20261004_014__hmr_014_integration_integration_job_run.sql`
- `src/main/resources/db/migration/V20261004_015__hmr_015_leakdetection_leak_candidate.sql`
- `src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`
- `src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql`
- `src/main/resources/db/migration/V20261004_019__hmr_019_integrity_pipeline_defect.sql`
- `src/main/resources/db/migration/V20261004_020__hmr_020_organization_position.sql`
- `src/main/resources/db/migration/V20261004_021__hmr_021_organization_shift.sql`
- `src/main/resources/db/migration/V20261004_022__hmr_022_topology_pipeline_system.sql`
- `src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql`
- `src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql`
- `src/main/resources/db/migration/V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`
- `src/main/resources/db/migration/V20261004_026__hmr_026_configuration_feature_flag.sql`
- `src/main/resources/db/migration/V20261004_027__hmr_027_custody_custody_discrepancy.sql`
- `src/main/resources/db/migration/V20261004_028__hmr_028_organization_reporting_line.sql`
- `src/main/resources/db/migration/V20261004_029__hmr_029_risk_risk_matrix_cell.sql`
- `src/main/resources/db/migration/V20261004_030__hmr_030_telemetry_telemetry_source.sql`
- `src/main/resources/db/migration/V20261004_031__hmr_031_topology_topology_connection.sql`
- `src/main/resources/db/migration/V20261004_032__hmr_032_organization_organization_unit.sql`
- `src/main/resources/db/migration/V20261004_033__hmr_033_telemetry_telemetry_reading.sql`
- `src/main/resources/db/migration/V20261004_034__hmr_034_simulation_simulation_scenario.sql`
- `src/main/resources/db/migration/V20261004_035__hmr_035_notification_notification_request.sql`
- `src/main/resources/db/migration/V20261004_039__hmr_039_configuration_configuration_value.sql`
- `src/main/resources/db/migration/V20261004_040__hmr_040_monitoring_monitoring_rule.sql`
- `src/main/resources/db/migration/V20261004_041__hmr_041_party_party_role_assignment.sql`
- `src/main/resources/db/migration/V20261004_042__hmr_042_topology_pipeline.sql`
- `src/main/resources/db/migration/V20261004_043__hmr_043_workflow_workflow_step.sql`
- `src/main/resources/db/migration/V20261004_045__hmr_045_assets_maintainable_asset.sql`
- `src/main/resources/db/migration/V20261004_047__hmr_047_integration_external_system.sql`
- `src/main/resources/db/migration/V20261004_048__hmr_048_reporting_report_request.sql`
- `src/main/resources/db/migration/V20261004_049__hmr_049_risk_risk_register.sql`
- `src/main/resources/db/migration/V20261005_001__provision_risk_register_created_audit_taxonomy.sql`
- `src/main/resources/db/migration/V20261006_001__hmr_059_leak_escalation_candidate_integrity.sql`
- `src/main/resources/db/migration/V20261006_002__hmr_074_responsibility_assignment_id_required.sql`
- `src/main/resources/db/migration/V20261006_003__hmr_075_captured_by_employee_id_required.sql`
- `src/main/resources/db/migration/V20261006_004__hmr_076_organization_unit_id_required.sql`
- `src/main/resources/db/migration/V20261006_005__hmr_052_notification_message_composition.sql`
- `src/main/resources/db/migration/V20261006_006__hmr_060_notification_attempt_evidence.sql`
- `src/main/resources/db/migration/V20261006_008__hmr_053_trusted_telemetry_gate.sql`
- `src/main/resources/db/migration/V20261006_009__hmr_054_equipment_catalog_and_attachments.sql`
- `src/main/resources/db/migration/V20261006_010__hmr_063_identity_user_uniqueness.sql`
- `src/main/resources/db/migration/V20261006_011__hmr_086_delegation_contract.sql`
- `src/main/resources/db/migration/V20261007_001__hmr_064_planning_plan_revision.sql`
- `src/main/resources/db/migration/V20261007_002__hmr_065_planning_operational_plan.sql`
- `src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql`
- `src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql`
- `src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql`
- `src/main/resources/db/migration/V20261007_006__hmr_083_audit_export_request.sql`
- `src/main/resources/db/migration/V20261007_007__hmr_095_audit_event.sql`
- `src/main/resources/db/migration/V20261007_008__hmr_101_audit_access_record.sql`
- `src/main/resources/db/migration/V20261007_009__hmr_102_audit_before_after_value.sql`
- `src/main/resources/db/migration/V20261007_010__hmr_056_integration_exchange_message.sql`
- `src/main/resources/db/migration/V20261007_011__hmr_071_integration_dead_letter_record.sql`
- `src/main/resources/db/migration/V20261007_012__hmr_057_reporting_report_run.sql`
- `src/main/resources/db/migration/V20261007_013__hmr_093_reporting_report_output_artifact.sql`
- `src/main/resources/db/migration/V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`
- `src/main/resources/db/migration/V20261008_002__hmr_079_simulation_recommendation_integrity.sql`
- `src/main/resources/db/migration/V20261008_011__hmr_069_assets_maintenance_work_order.sql`
- `src/main/resources/db/migration/V20261008_012__hmr_070_custody_custody_transfer_ticket.sql`
- `src/main/resources/db/migration/V20261008_013__hmr_072_integrity_integrity_assessment.sql`
- `src/main/resources/db/migration/V20261008_014__hmr_082_hse_case_lifecycle.sql`
- `src/main/resources/db/migration/V20261008_015__hmr_096_hse_closure_atomic_evidence.sql`
- `src/main/resources/db/migration/V20261008_016__hmr_097_hse_capa_reference_catalog_integrity.sql`
- `src/main/resources/db/migration/V20261008_017__hmr_098_integrity_case_catalog_field_policy.sql`
- `src/main/resources/db/migration/V20261008_018__hmr_098_integrity_case_reference_integrity.sql`
- `src/main/resources/db/migration/V20261008_019__hmr_094_planning_target_value_policy.sql`
- `src/main/resources/db/migration/V20261008_020__hmr_094_planning_plan_target_integrity.sql`
- `src/main/resources/db/migration/V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
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
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsInsightSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsProjectionRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/DigitalTwinReadinessAssessmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricEvaluationRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricValueSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintainableAssetSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationDefinitionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationValueSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/configuration/semantic/FeatureFlagSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyMeasurementPeriodSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentStorageObjectSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCaseSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseClosureSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCorrectivePreventiveActionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityControllerPermissionScopeApiTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapperPermissionScopeTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDecisionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDelegationGrantSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/IdentityProviderSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/PermissionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/RoleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserPermissionGrantSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserRoleGrantSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/application/service/IncidentQueryApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentClosureSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentRelatedIncidentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentResponseActionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/ExternalSystemSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationJobRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityAssessmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityCaseSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityProgramSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/PipelineDefectSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakCandidateSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakDetectionCaseSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakEscalationReferenceSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationTemplateSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/command/CreateOrganizationUnitCommandTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ReportingLinePersistenceMapperTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/EmployeeAssignmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationDelegationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationHierarchySnapshotSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationUnitSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/PositionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/ReportingLineSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftAssignmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/party/semantic/PartyRoleAssignmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/party/semantic/PartySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanningPeriodSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportDefinitionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskMatrixCellSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskRegisterSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationModelSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationScenarioSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryReadingSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetrySourceSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaLeakDetectionTopologyAssetContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSystemSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/TopologyConnectionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowDefinitionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepAssignmentRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java`

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

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — exact-head oracle suite 16 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandlerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionControllerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/AlarmSuppressionRoutePermissionTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/controller/SpringAlarmControllerActorAttributionTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/api/rest/mapper/AlarmRestMapperTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/application/service/AlarmSuppressionApprovalServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmSuppressionPolicyTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSuppressionPersistenceMigrationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryJobTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsInsightSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsProjectionRunSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/DigitalTwinReadinessAssessmentSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricEvaluationRunSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricValueSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintainableAssetSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java` — exact-head oracle suite 11 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationDefinitionSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/configuration/semantic/ConfigurationValueSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/configuration/semantic/FeatureFlagSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyMeasurementPeriodSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentStorageObjectSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCaseSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseClosureSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCorrectivePreventiveActionSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityControllerPermissionScopeApiTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/mapper/IdentityRestMapperPermissionScopeTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationServiceTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDecisionSemanticRemediationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDelegationGrantSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/IdentityProviderSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/LoginSessionSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/PermissionSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/RoleSemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserPermissionGrantSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserRoleGrantSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/UserSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/incident/application/service/IncidentQueryApplicationServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentClosureSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentRelatedIncidentSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentResponseActionSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integration/semantic/ExternalSystemSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationJobRunSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityAssessmentSemanticRemediationTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityCaseSemanticRemediationTest.java` — exact-head oracle suite 12 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityProgramSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/PipelineDefectSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakCandidateSemanticRemediationTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakDetectionCaseSemanticRemediationTest.java` — exact-head oracle suite 9 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/leakdetection/semantic/LeakEscalationReferenceSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptIntegrityMigrationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationDeliveryAttemptSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageIntegrityMigrationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationMessageSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationRequestSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/notification/semantic/NotificationTemplateSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/command/CreateOrganizationUnitCommandTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/AssetsOrganizationUnitReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/IntegrationOrganizationUnitReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/ReportingLinePersistenceMapperTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/semantic/EmployeeAssignmentSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationDelegationSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationHierarchySnapshotSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/semantic/OrganizationUnitSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/semantic/PositionSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/semantic/ReportingLineSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftAssignmentSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/semantic/ShiftSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/party/application/service/AssetsPartyReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/party/application/service/TopologyPartyReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/party/semantic/PartyRoleAssignmentSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/party/semantic/PartySemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java` — exact-head oracle suite 11 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanningPeriodSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportDefinitionSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRequestSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java` — exact-head oracle suite 15 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskMatrixCellSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskRegisterSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationModelSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRunSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationScenarioSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTelemetryPointReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryReadingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetrySourceSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingIntegrityMigrationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/AssetsTopologyReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationScopeQueryServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaLeakDetectionTopologyAssetContractAdapterTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentIntegrityMigrationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/EquipmentSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/FacilitySemanticRemediationTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/PipelineSystemSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/semantic/TopologyConnectionSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionControllerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowDefinitionSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepAssignmentRuleSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `71c38e299b847fd0e10244a19787fcc15835e415` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:131](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L131)
- [docs/roadmap/model-semantic-remediation.md:335](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L335)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:131 | HMR-002 | docs(model-remediation): prepare executable remediation register | Re-read all 104 REVISE sections against live main; populate exact per-task file allowlists, validations, prerequisites, already-fixed evidence, SCC ordering, and additive migration needs. Documentation only. | **Completed** — 104/104 REVISE tasks reconciled against live tree; 101 executable tasks marked Planned and 3 tasks remain Blocked only where a required exported owner lookup contract was not found.
~~~~~~~~

<a id="hapi-176"></a>
## HAPI-176 — remediate semantic review WorkflowDefinition

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMR-003

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review WorkflowDefinition

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionTargetBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionTargetBindingJpaRepository.java`
- `src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowDefinitionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionTargetBindingJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionTargetBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionTargetBindingJpaRepository.java`
- `src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowDefinitionSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-003 — workflow.WorkflowDefinition

- Source review: `HMSR-002`
- Exact commit: `fix(workflow): remediate semantic review WorkflowDefinition`
- Status: **Completed** — domain now rejects blank `nameFr` and `version < 1`; adapter fails fast on duplicate `(code, version)` and structural mutation of an existing ACTIVE definition; additive Flyway adds the race-safe unique index and database version check; semantic tests cover all four obligations. Maven validation could not be executed in this connector-only environment because the repository cannot be cloned from the execution container (`Could not resolve host: github.com`).
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionTargetBindingJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionTargetBindingJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_003__hmr_003_workflow_workflow_definition.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowDefinitionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowDefinitionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Definition version minimum:** the Workflow data definition requires `version >= 1`, but the current domain constructor accepts zero or negative values and the live schema only declares `version integer NOT NULL` without a minimum-value check.
  2. **French name requirement at the domain boundary:** Workflow policy and JPA require `nameFr`, but the current domain constructor can normalize a blank French name to `null`, allowing invalid state to exist until persistence rejects it.
  3. **Definition identity/version uniqueness:** the Workflow data definition requires `(code, version)` to be unique. The live migration provides an index on `code` only; no repository evidence was found for a database unique constraint or an application uniqueness guard on `(code, version)`. The data-definition example named `idx_workflow_definition_code_version` is an ordinary index, not a uniqueness constraint, so it does not satisfy the stated rule.
  4. **Activation/version governance:** the approved semantic baseline must preserve the rule that an ACTIVE definition is not edited in place and a business change creates a new version. Any later Workflow correction task must enforce this at the appropriate application/domain boundary rather than relying on record immutability alone.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowDefinitionSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.

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

- `7911e5d001fab816db5f9887d2cd4345d8afefaa` — exact registered-message commit; surviving paths matched to current tree.
- `8ee7bd63a6f349ccaeab23405a9b030f444de9e9` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13692](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13692)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15432](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15432)
- [docs/roadmap/model-semantic-remediation.md:149](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L149)
- [docs/roadmap/model-semantic-remediation.md:363](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L363)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:149 | HMR-003 | HMSR-002 | workflow | WorkflowDefinition | — | — | fix(workflow): remediate semantic review WorkflowDefinition | **Completed**
doc/model-remediation/RECONCILIATION.md:13692 | HMR-003 | Historical reference; unchanged | INDEPENDENT_UNCHANGED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15432 | HMR-003 | Historical reference; unchanged | INDEPENDENT_UNCHANGED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-231"></a>
## HAPI-231 — remediate semantic review WorkflowStep

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMR-043

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review WorkflowStep

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepJpaRepository.java`
- `src/main/resources/db/migration/V20261004_043__hmr_043_workflow_workflow_step.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepJpaRepository.java`
- `src/main/resources/db/migration/V20261004_043__hmr_043_workflow_workflow_step.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-043 — workflow.WorkflowStep

- Source review: `HMSR-051`
- Exact commit: `fix(workflow): remediate semantic review WorkflowStep`
- Status: **Completed** — non-negative order, per-definition code/order uniqueness, and populated defaultAssignmentRuleId integrity are enforced at domain/repository/database boundaries while preserving the nullable SCC-02 reference.
- SCC: SCC-02
- Recorded upstream HMS dependencies: workflow.WorkflowDefinition, workflow.WorkflowStepAssignmentRule
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_043__hmr_043_workflow_workflow_step.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_043__hmr_043_workflow_workflow_step.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowStepSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce `stepOrder >= 0`.** The authoritative domain/application/database boundary must reject negative workflow-step ordering.
  2. **Enforce uniqueness of `(definitionId, code)`.** Two steps in the same WorkflowDefinition must not share the same stable step code.
  3. **Enforce uniqueness of `(definitionId, stepOrder)`.** Two steps in the same WorkflowDefinition must not occupy the same deterministic order position.
  4. **Protect populated `defaultAssignmentRuleId` references.** When supplied, the reference must resolve to WorkflowStepAssignmentRule through a nullable same-module FK, application validation, or another fail-closed mechanism that preserves SCC-02.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `b0240b2f509f9fa0475b93d0016271e808c23f3d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Workflow.md:1564](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Workflow.md#L1564)
- [docs/roadmap/model-semantic-remediation.md:199](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L199)
- [docs/roadmap/model-semantic-remediation.md:2424](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2424)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:199 | HMR-043 | HMSR-051 | workflow | WorkflowStep | SCC-02 | workflow.WorkflowDefinition, workflow.WorkflowStepAssignmentRule | fix(workflow): remediate semantic review WorkflowStep | Planned
~~~~~~~~

<a id="hapi-232"></a>
## HAPI-232 — remediate semantic review WorkflowStepAssignmentRule

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMR-044

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review WorkflowStepAssignmentRule

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepAssignmentRuleSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepAssignmentRuleSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-044 — workflow.WorkflowStepAssignmentRule

- Source review: `HMSR-052`
- Exact commit: `fix(workflow): remediate semantic review WorkflowStepAssignmentRule`
- Status: **Completed** — assignmentModeId must belong specifically to WORKFLOW_ASSIGNMENT_MODE and every rule must provide at least one concrete actor/candidate-pool strategy before persistence.
- SCC: SCC-02
- Recorded upstream HMS dependencies: organization.OrganizationUnit, workflow.WorkflowDefinition, workflow.WorkflowStep
- HMSR correction count: 2
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: HMR-045A registered — live preflight proved no Assets-facing Topology, Organization, or Party owner contracts exist and the actual registration/update orchestration file was outside the original allowlist.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStepAssignmentRuleRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStepAssignmentRuleJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepAssignmentRuleSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowStepAssignmentRuleSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce `WORKFLOW_ASSIGNMENT_MODE` catalog-family semantics for `assignmentModeId`.** Generic Workflow catalog-row existence is insufficient; the selected entry must belong to the documented assignment-mode family.
  2. **Require every WorkflowStepAssignmentRule to provide at least one resolvable actor or candidate-pool strategy.** The authoritative domain/application boundary must reject rules where `actorId`, `roleCode`, `organizationUnitId`, `organizationRoleCode`, and `targetOwnerMode` are all absent. Mode-specific eligibility may be resolved through owner-controlled ports, but Workflow must not persist a rule that cannot resolve any candidate source.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStepAssignmentRuleSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `1ecaa5b91bdb9c7a5dfe3f4e719ac6e4d3125f6e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Workflow.md:1581](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Workflow.md#L1581)
- [docs/roadmap/model-semantic-remediation.md:200](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L200)
- [docs/roadmap/model-semantic-remediation.md:2462](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2462)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:200 | HMR-044 | HMSR-052 | workflow | WorkflowStepAssignmentRule | SCC-02 | organization.OrganizationUnit, workflow.WorkflowDefinition, workflow.WorkflowStep | fix(workflow): remediate semantic review WorkflowStepAssignmentRule | Planned
~~~~~~~~

<a id="hapi-249"></a>
## HAPI-249 — remediate semantic review WorkflowInstance

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMR-055

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review WorkflowInstance

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/workflow/WorkflowActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/WorkflowActorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/workflow/WorkflowOrganizationContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/query/WorkflowOrganizationQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowInstanceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowConfigurationPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowExecutionOwnership.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowConfigurationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java`
- `src/main/resources/db/migration/V20261006_014__hmr_055_workflow_instance.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowConfigurationPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/WorkflowActorQueryService.java`, `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningWorkflowTargetLookup.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowExecutionOwnership.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/query/WorkflowOrganizationQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowConfigurationAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/workflow/WorkflowActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/WorkflowActorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/workflow/WorkflowOrganizationContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/query/WorkflowOrganizationQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowInstanceSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowConfigurationPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowExecutionOwnership.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowConfigurationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java`
- `src/main/resources/db/migration/V20261006_014__hmr_055_workflow_instance.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-055 — workflow.WorkflowInstance

- Source review: `HMSR-064`
- Exact commit: `fix(workflow): remediate semantic review WorkflowInstance`
- Status: **Completed — CI pending**
- Batch 7 implementation: Owner-bound starts enforce active definition/version and exact binding, governed purpose/type, current-step coherence and owner target/actor snapshots; nonterminal uniqueness and same-definition/version database guards. Planning target registry denies unsupported/ambiguous owners. Eight focused behavior checks passed with temporary stubs; local Maven blocked by uncached parent, not a JUnit/PostgreSQL pass. Canonical scope/migration admission supersedes legacy filenames.
- SCC: —
- Recorded upstream HMS dependencies: workflow.WorkflowDefinition, workflow.WorkflowStep
- HMSR correction count: 6
- Additive Flyway: `src/main/resources/db/migration/V20261004_055__hmr_055_workflow_workflow_instance.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowInstanceSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_055__hmr_055_workflow_workflow_instance.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowInstanceSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Resolve the definition before start and enforce ACTIVE/version consistency.** `definitionId` must identify an ACTIVE WorkflowDefinition and `definitionVersion` must match the definition version being instantiated.
  2. **Require an active WorkflowDefinitionTargetBinding for the exact definition + target module/type + workflow purpose combination before start.**
  3. **Reconcile workflow-purpose controlled-value semantics and nullability.** Purpose participates in binding and non-terminal uniqueness but its exact catalog-family contract is not currently explicit; do not invent one.
  4. **Validate target type and target identity correctly.** Enforce `WORKFLOW_TARGET_TYPE` family eligibility and validate `targetModule + targetTypeId + targetId` through an owner-controlled target lookup while preserving snapshots; do not add cross-module DB FKs.
  5. **Protect current-step and non-terminal-instance integrity.** A populated `currentStepId` must resolve to a WorkflowStep belonging to the selected definition, and the DDD rule of at most one non-terminal instance per target + purpose must be enforced transactionally/database-safe.
  6. **Validate the mandatory starter actor through the Identity-owned actor/authority boundary while preserving actor snapshots.**

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
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.

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

- `eecefae60ccbb1731b211935732a54876e74c113` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:90](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L90)
- [doc/model-remediation/RECONCILIATION.md:13702](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13702)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2684](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2684)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15442](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15442)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:89](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L89)
- [docs/roadmap/model-semantic-remediation.md:216](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L216)
- [docs/roadmap/model-semantic-remediation.md:3123](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3123)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:216 | HMR-055 | HMSR-064 | workflow | WorkflowInstance | — | workflow.WorkflowDefinition, workflow.WorkflowStep | fix(workflow): remediate semantic review WorkflowInstance | Planned
doc/model-remediation/RECONCILIATION.md:90 | HMR-055 | HMSR-064 | workflow.WorkflowInstance | COMPLETED — CI #581 GREEN | Owner-bound starts enforce active definition/version and exact binding, governed purpose/type, current-step coherence and owner target/actor snapshots; nonterminal uniqueness and same-definition/version database guards. Planning target registry denies unsupported/ambiguous owners. Eight focused behavior checks passed with temporary stubs; local Maven blocked by uncached parent, not a JUnit/PostgreSQL pass.
doc/model-remediation/RECONCILIATION.md:13702 | HMR-055 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:89 | HMR-055 | HMSR-064 | [workflow.WorkflowInstance](../domain/SEMANTIC_DECISIONS.md#workflow-workflowinstance) | [WorkflowInstanceSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowInstanceSemanticRemediationTest.java) | [V20261006_014__hmr_055_workflow_instance](../../src/main/resources/db/migration/V20261006_014__hmr_055_workflow_instance.sql); [WorkflowActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/workflow/WorkflowActorContract.java); [WorkflowOrganizationContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/workflow/WorkflowOrganizationContract.java); [WorkflowOwnedTargetLookup](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15442 | HMR-055 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-255"></a>
## HAPI-255 — remediate semantic review WorkflowTransition

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMR-061

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review WorkflowTransition

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionController.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ExecuteWorkflowTransitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTransitionExecutionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/ExecuteWorkflowTransitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTransitionExecutionDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTransitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTransitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionDeniedException.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTransitionJpaRepository.java`
- `src/main/resources/db/migration/V20261006_015__hmr_061_workflow_transition.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionConflictException.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionDeniedException.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTransitionUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTransitionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`, `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionController.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ExecuteWorkflowTransitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTransitionExecutionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/ExecuteWorkflowTransitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTransitionExecutionDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTransitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTransitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionConflictException.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionDeniedException.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTransitionJpaRepository.java`
- `src/main/resources/db/migration/V20261006_015__hmr_061_workflow_transition.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-061 — workflow.WorkflowTransition

- Source review: `HMSR-073`
- Exact commit: `fix(workflow): remediate semantic review WorkflowTransition`
- Status: **Completed — CI pending**
- Batch 7 implementation: Distinct same-definition steps and unique source decisions are protected in configuration persistence and PostgreSQL. Unsupported conditions/callbacks/COMMENT cannot attach to ACTIVE definitions or survive activation; runtime remains fail closed. Three focused behavior checks passed with temporary stubs; PostgreSQL validation pending CI. Canonical scope/migration admission supersedes legacy filenames.
- SCC: —
- Recorded upstream HMS dependencies: workflow.WorkflowDefinition, workflow.WorkflowStep
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_061__hmr_061_workflow_workflow_transition.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionController.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ExecuteWorkflowTransitionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTransitionExecutionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/command/ExecuteWorkflowTransitionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTransitionExecutionDto.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTransitionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTransitionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionConflictException.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/exception/WorkflowTransitionDeniedException.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTransitionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_061__hmr_061_workflow_workflow_transition.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionControllerTest.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowTransitionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce definition/step composition at transition configuration time.** Both source and destination steps must belong to the transition's WorkflowDefinition.
  2. **Enforce `fromStepId != toStepId` as a configuration/domain invariant**, not only as an execution-time guard.
  3. **Enforce uniqueness of `definitionId + fromStepId + decision`.** Prevent ambiguous duplicate configured decisions from one source step.
  4. **Prevent unsupported condition/callback configuration from becoming executable ACTIVE workflow configuration until governed evaluators/contracts exist**, or implement those contracts explicitly. Preserve the current fail-closed runtime behavior.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTransitionControllerTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `efdbfaa594b92ccbe858e1c3f1cdd4b766886c41` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:96](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L96)
- [doc/model-remediation/RECONCILIATION.md:13708](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13708)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2700](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2700)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15448](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15448)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:95](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L95)
- [docs/roadmap/model-semantic-remediation.md:222](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L222)
- [docs/roadmap/model-semantic-remediation.md:3349](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3349)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:222 | HMR-061 | HMSR-073 | workflow | WorkflowTransition | — | workflow.WorkflowDefinition, workflow.WorkflowStep | fix(workflow): remediate semantic review WorkflowTransition | Planned
doc/model-remediation/RECONCILIATION.md:96 | HMR-061 | HMSR-073 | workflow.WorkflowTransition | COMPLETED — CI #581 GREEN | Distinct same-definition steps and unique source decisions are protected in configuration persistence and PostgreSQL. Unsupported conditions/callbacks/COMMENT cannot attach to ACTIVE definitions or survive activation; runtime remains fail closed. Three focused behavior checks passed with temporary stubs; PostgreSQL validation confirmed by CI #604.
doc/model-remediation/RECONCILIATION.md:13708 | HMR-061 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:95 | HMR-061 | HMSR-073 | [workflow.WorkflowTransition](../domain/SEMANTIC_DECISIONS.md#workflow-workflowtransition) | [WorkflowTransitionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTransitionSemanticRemediationTest.java) | [V20261006_015__hmr_061_workflow_transition](../../src/main/resources/db/migration/V20261006_015__hmr_061_workflow_transition.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15448 | HMR-061 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-260"></a>
## HAPI-260 — remediate semantic review WorkflowTask

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMR-066

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review WorkflowTask

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowTaskRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowTaskCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTaskSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowExecutionOwnership.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/query/JpaWorkflowQueryAdapter.java`
- `src/main/resources/db/migration/V20261006_016__hmr_066_workflow_task.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowExecutionOwnership.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`, `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowTaskRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowTaskCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTaskSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowExecutionOwnership.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/query/JpaWorkflowQueryAdapter.java`
- `src/main/resources/db/migration/V20261006_016__hmr_066_workflow_task.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-066 — workflow.WorkflowTask

- Source review: `HMSR-078`
- Exact commit: `fix(workflow): remediate semantic review WorkflowTask`
- Status: **Completed — CI pending**
- Batch 7 implementation: Actionable assignment, live actor/unit membership, catalog eligibility, actor/time pairs and chronology are enforced. Terminal task evidence is immutable; generic creation is starter-bound, missing next-step rules fail closed, and execution/query paths no longer authorize by username snapshots. Seven focused behavior checks passed with temporary stubs; existing transition fixtures updated for new owner dependencies. Canonical scope/migration admission supersedes legacy filenames.
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowStep
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_066__hmr_066_workflow_workflow_task.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowTaskRequest.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowTaskCommand.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTaskSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_066__hmr_066_workflow_workflow_task.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowTaskSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce assignment-target presence.** An actionable WorkflowTask must have at least one assignment target: actor or organization unit.
  2. **Validate actor/organization assignment and execution eligibility through owner-controlled contracts.** Preserve scalar IDs/snapshots and do not add cross-module database FKs.
  3. **Enforce explicit claim/completion invariants and terminal immutability.** Couple claimed actor/time, completed actor/time, require `completedAt >= createdAt`, and prevent mutation of completed tasks outside explicitly audit-safe annotations.
  4. **Enforce the `WORKFLOW_PRIORITY` catalog family** for populated `priorityId`, including active/eligible semantics.
  5. **Enforce the `WORKFLOW_ASSIGNMENT_MODE` catalog family** for populated `assignmentModeId`, including active/eligible semantics.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.

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

- `49be2219aacf5b580561978cf216cdbf95dd9682` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:101](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L101)
- [doc/model-remediation/RECONCILIATION.md:13713](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13713)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2706](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2706)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15453](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15453)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:100](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L100)
- [docs/roadmap/model-semantic-remediation.md:232](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L232)
- [docs/roadmap/model-semantic-remediation.md:3635](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3635)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:232 | HMR-066 | HMSR-078 | workflow | WorkflowTask | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowStep | fix(workflow): remediate semantic review WorkflowTask | Planned
doc/model-remediation/RECONCILIATION.md:101 | HMR-066 | HMSR-078 | workflow.WorkflowTask | COMPLETED — CI #581 GREEN | Actionable assignment, live actor/unit membership, catalog eligibility, actor/time pairs and chronology are enforced. Terminal task evidence is immutable; generic creation is starter-bound, missing next-step rules fail closed, and execution/query paths no longer authorize by username snapshots. Seven focused behavior checks passed with temporary stubs; existing transition fixtures updated for new owner dependencies.
doc/model-remediation/RECONCILIATION.md:13713 | HMR-066 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:100 | HMR-066 | HMSR-078 | [workflow.WorkflowTask](../domain/SEMANTIC_DECISIONS.md#workflow-workflowtask) | [WorkflowTaskSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowTaskSemanticRemediationTest.java) | [V20261006_016__hmr_066_workflow_task](../../src/main/resources/db/migration/V20261006_016__hmr_066_workflow_task.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15453 | HMR-066 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-275"></a>
## HAPI-275 — remediate semantic review WorkflowAction

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMR-081

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review WorkflowAction

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RecordWorkflowActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/RecordWorkflowActionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java`
- `src/main/resources/db/migration/V20261006_017__hmr_081_workflow_action.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`, `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RecordWorkflowActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/RecordWorkflowActionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java`
- `src/main/resources/db/migration/V20261006_017__hmr_081_workflow_action.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-081 — workflow.WorkflowAction

- Source review: `HMSR-095`
- Exact commit: `fix(workflow): remediate semantic review WorkflowAction`
- Status: **Completed — CI pending**
- Batch 7 implementation: Generic recording permits comments only; configured transitions exclusively produce decisions using live Identity authority. Optional task ownership and conditional evidence are enforced; canonical actor snapshots and server-owned locked sequences replace caller evidence. Action persistence is insert-only with unique monotonic sequence and immutable database guards. Five focused behavior checks passed with temporary stubs; existing permission regression fixture updated. Canonical scope/migration admission supersedes legacy filenames.
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowTask
- HMSR correction count: 6
- Additive Flyway: `src/main/resources/db/migration/V20261004_081__hmr_081_workflow_workflow_action.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RecordWorkflowActionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/command/RecordWorkflowActionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActionSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_081__hmr_081_workflow_workflow_action.sql`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowActionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Close the generic decision-action bypass.** Decision-bearing actions must pass through the configured-transition authority; a generic action endpoint must not independently persist workflow decisions.
  2. **Validate supplied `taskId` fail-closed and verify task-to-instance ownership** for task-scoped actions.
  3. **Enforce DDD conditional evidence rules on every authoritative write path:** required reasons for reject/correction/return/delegation/escalation/cancel and required comment for request-correction.
  4. **Enforce required `actorDisplayNameSnapshot` before persistence** so actor evidence fails fast rather than at JPA/database time.
  5. **Make `actionSequence` Workflow-owned, monotonic, race-safe, and protected per instance**, rather than caller-controlled on the generic path.
  6. **Preserve Identity/Organization boundary ownership:** scalar actor/organization references and snapshots only, with owner-controlled validation where required; never introduce cross-module database FKs.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `dcc38468739beab87ef4b35f6d59b24e2d7b564d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:116](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L116)
- [doc/model-remediation/RECONCILIATION.md:13728](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13728)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2716](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2716)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15468](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15468)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:115](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L115)
- [docs/roadmap/model-semantic-remediation.md:252](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L252)
- [docs/roadmap/model-semantic-remediation.md:4294](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4294)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:252 | HMR-081 | HMSR-095 | workflow | WorkflowAction | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowTask | fix(workflow): remediate semantic review WorkflowAction | Planned
doc/model-remediation/RECONCILIATION.md:116 | HMR-081 | HMSR-095 | workflow.WorkflowAction | COMPLETED — CI #581 GREEN | Generic recording permits comments only; configured transitions exclusively produce decisions using live Identity authority. Optional task ownership and conditional evidence are enforced; canonical actor snapshots and server-owned locked sequences replace caller evidence. Action persistence is insert-only with unique monotonic sequence and immutable database guards. Five focused behavior checks passed with temporary stubs; existing permission regression fixture updated.
doc/model-remediation/RECONCILIATION.md:13728 | HMR-081 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:115 | HMR-081 | HMSR-095 | [workflow.WorkflowAction](../domain/SEMANTIC_DECISIONS.md#workflow-workflowaction) | [WorkflowActionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowActionSemanticRemediationTest.java) | [V20261006_017__hmr_081_workflow_action](../../src/main/resources/db/migration/V20261006_017__hmr_081_workflow_action.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15468 | HMR-081 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-293"></a>
## HAPI-293 — remediate semantic review WorkflowStateHistory

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMR-099

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review WorkflowStateHistory

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStateHistoryJpaRepository.java`
- `src/main/resources/db/migration/V20261006_018__hmr_099_workflow_state_history.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowExecutionPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`.
- Application services: `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStateHistoryJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Workflow.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/workflow.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStateHistoryJpaRepository.java`
- `src/main/resources/db/migration/V20261006_018__hmr_099_workflow_state_history.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowExecutionPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-099 — workflow.WorkflowStateHistory

- Source review: `HMSR-116`
- Exact commit: `fix(workflow): remediate semantic review WorkflowStateHistory`
- Status: **Completed — CI pending**
- Batch 7 implementation: Mandatory status/actor display evidence fails fast. History persistence inserts and flushes without upsert; optional task/step/action/reason references are checked for instance/definition and action evidence coherence. Database guards prohibit update/delete/truncate. Four focused behavior checks passed with temporary stubs; ten PostgreSQL/Hibernate cases added for CI, not locally executed. Canonical scope/migration admission supersedes legacy filenames.
- SCC: —
- Recorded upstream HMS dependencies: workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowStep, workflow.WorkflowTask
- HMSR correction count: 3
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Workflow.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `docs/roadmap/workflow.md`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowStateHistoryJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=WorkflowStateHistorySemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Fail closed on blank/null `toStatus` and `actorDisplayNameSnapshot` before persistence.**
  2. **Make WorkflowStateHistory persistence truly append-only so an existing history row cannot be overwritten or mutated through the generic save contract.**
  3. **For any future history producer outside the authoritative transition service, validate supplied optional task/step/action/reason references through Workflow-owned contracts and preserve instance/definition coherence without making optional references universally mandatory.**

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
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowExecutionPostgresTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `6bdae7200cee6a8a62849f908d448845261de869` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:134](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L134)
- [doc/model-remediation/RECONCILIATION.md:13746](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13746)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:2723](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L2723)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15486](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15486)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:133](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L133)
- [docs/roadmap/model-semantic-remediation.md:275](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L275)
- [docs/roadmap/model-semantic-remediation.md:4920](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4920)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:275 | HMR-099 | HMSR-116 | workflow | WorkflowStateHistory | — | workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowStep, workflow.WorkflowTask | fix(workflow): remediate semantic review WorkflowStateHistory | Planned
doc/model-remediation/RECONCILIATION.md:134 | HMR-099 | HMSR-116 | workflow.WorkflowStateHistory | COMPLETED — CI #581 GREEN | Mandatory status/actor display evidence fails fast. History persistence inserts and flushes without upsert; optional task/step/action/reason references are checked for instance/definition and action evidence coherence. Database guards prohibit update/delete/truncate. Four focused behavior checks passed with temporary stubs; ten PostgreSQL/Hibernate cases added for CI, not locally executed.
doc/model-remediation/RECONCILIATION.md:13746 | HMR-099 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:133 | HMR-099 | HMSR-116 | [workflow.WorkflowStateHistory](../domain/SEMANTIC_DECISIONS.md#workflow-workflowstatehistory) | [WorkflowStateHistorySemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowStateHistorySemanticRemediationTest.java) | [V20261006_018__hmr_099_workflow_state_history](../../src/main/resources/db/migration/V20261006_018__hmr_099_workflow_state_history.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15486 | HMR-099 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-312"></a>
## HAPI-312 — review workflow WorkflowDefinition

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMSR-002

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review workflow WorkflowDefinition

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 15. HMSR-002 — workflow.WorkflowDefinition review

**Decision:** REVISE  
**Review code:** HMSR-002  
**Dependency level:** 0  
**Bounded context:** workflow  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 4 — `workflow.WorkflowInstance`, `workflow.WorkflowStep`, `workflow.WorkflowStepAssignmentRule`, `workflow.WorkflowTransition`  
**Transitive dependents:** 36  
**Unresolved/non-subject references:** 1 — `typeId`, resolved by current persistence evidence to the Workflow-owned catalog/read model rather than an HMS subject model

### 15.1 Semantic role and ordering rationale

`WorkflowDefinition` is the Workflow-owned reusable process-template aggregate. It defines stable workflow identity, business code, localized names, workflow type, lifecycle status, and an integer definition version. It is Level 0 because none of its prerequisites are another one of the 123 HMS subject models. The four direct subject dependents all reference it by `definitionId`, so its semantics must be settled before those models are reviewed.

Workflow remains the process owner only. The target business fact remains owned by the relevant operational bounded context, while Identity owns security identity, Organization owns organizational structure, Audit owns durable evidence, and Notification owns delivery.

### 15.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable workflow-definition identifier and persistence primary key. |
| `code` | `String` | Mandatory | Stable language-neutral business code for the definition family. Together with `version`, current Workflow semantics require uniqueness. |
| `nameAr` | `String` | Optional in current persistence | Arabic display name. Current Workflow data definition identifies Arabic as a target production requirement, but live JPA remains nullable. |
| `nameFr` | `String` | Mandatory | French display name. Workflow policy explicitly makes French mandatory for direct localized names; JPA is `nullable = false`. |
| `nameEn` | `String` | Optional in current persistence | English display name. Current Workflow data definition recommends/targets multilingual production coverage while live JPA remains nullable. |
| `typeId` | `String` | Mandatory | Workflow-owned controlled-vocabulary reference for workflow type. Current HRA-111 persistence evidence resolves it to `hidra_workflow_type_catalog(id)`; `WorkflowCatalogEntry` is a retained read/persistence model, not one of the 123 HMS subjects. |
| `status` | `WorkflowDefinitionStatus` | Mandatory | Lifecycle state: `DRAFT`, `ACTIVE`, `INACTIVE`, or `RETIRED`. Only `ACTIVE` definitions may start new instances. |
| `version` | `int` | Mandatory | Definition version. Current Workflow data definition requires a minimum value of 1. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation audit timestamp; JPA and schema are non-null. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update audit timestamp; JPA and schema are non-null. |

The current record normalizes blank text to `null` after required-field guards. `id`, `code`, `typeId`, and `status` already fail fast in the domain constructor.

### 15.3 Catalog dependency resolution

HMS-003 left `typeId` unresolved because the 123-subject graph did not contain an unambiguous subject target. Stronger repository evidence now resolves its semantics without adding an HMS graph edge:

- `WorkflowDefinitionJpaEntity.typeId` maps to `hidra_workflow_definition.type_id`;
- HRA-111 adds and validates `fk_hra111_workflow_008`;
- that FK points to `hidra_workflow_type_catalog(id)`;
- repository mirror classification retains `WorkflowCatalogEntry` as a Workflow READ_PERSISTENCE_MODEL outside the 123 HMS subject set.

Therefore `typeId` is a **Workflow-owned value/catalog dependency**, not an unresolved subject-model dependency. The register's combined unresolved/non-subject count remains 1 because the dependency is deliberately outside the HMS subject population.

### 15.4 Lifecycle and versioning semantics

The current baseline semantics are:

- only `ACTIVE` definitions can start instances; the domain helper `canStartInstance()` already expresses this;
- `DRAFT` definitions may be edited;
- `ACTIVE` definitions are intended to be immutable except for controlled retirement/deactivation;
- changing an active definition requires a new version;
- `RETIRED` definitions cannot start new instances;
- the current direct `version` field is acceptable for the first baseline; the data definition's possible future `WorkflowDefinitionVersion` aggregate is an enhancement, not a prerequisite for this HMS decision.

### 15.5 Persistence and dependency consistency

The live domain and JPA models have the same 10 declared components. The initial Workflow migration persists all 10 fields and makes `id`, `code`, `name_fr`, `type_id`, `status`, `version`, `created_at`, and `updated_at` non-null.

Validated subject dependencies point **into** this model from:

- `WorkflowInstance.definitionId`;
- `WorkflowStep.definitionId`;
- `WorkflowStepAssignmentRule.definitionId`;
- `WorkflowTransition.definitionId`.

The catalog reference `typeId` is protected by a same-module FK to `hidra_workflow_type_catalog`. No cross-module database ownership is introduced.

### 15.6 Required revisions

The semantic shape is sound, but the live implementation does not yet enforce all repository-defined WorkflowDefinition invariants strongly enough for an APPROVED target baseline.

1. **Definition version minimum:** the Workflow data definition requires `version >= 1`, but the current domain constructor accepts zero or negative values and the live schema only declares `version integer NOT NULL` without a minimum-value check.
2. **French name requirement at the domain boundary:** Workflow policy and JPA require `nameFr`, but the current domain constructor can normalize a blank French name to `null`, allowing invalid state to exist until persistence rejects it.
3. **Definition identity/version uniqueness:** the Workflow data definition requires `(code, version)` to be unique. The live migration provides an index on `code` only; no repository evidence was found for a database unique constraint or an application uniqueness guard on `(code, version)`. The data-definition example named `idx_workflow_definition_code_version` is an ordinary index, not a uniqueness constraint, so it does not satisfy the stated rule.
4. **Activation/version governance:** the approved semantic baseline must preserve the rule that an ACTIVE definition is not edited in place and a business change creates a new version. Any later Workflow correction task must enforce this at the appropriate application/domain boundary rather than relying on record immutability alone.

These are correction requirements for a later explicitly authorized Workflow roadmap task. HMSR-002 does not modify production Java, JPA, Flyway, application contracts, tests, or database data.

### 15.7 Multilingual and operational interpretation

Workflow direct names use `nameAr/nameFr/nameEn`. Current persistence makes French mandatory while Arabic and English remain nullable; the Workflow data definition explicitly flags stronger production multilingual coverage as a target direction. This review does not invent a new storage model or translation structure.

For SONATRACH/TRC operations, a workflow definition represents a governed reusable approval/validation route for operational decisions such as telemetry validation, planning approval, alarm/incident review, integrity actions, maintenance, HSE, custody, and other Hidra processes. It does not own or mutate the underlying operational fact merely by defining the process.

### 15.8 Review conclusion

**REVISE.** `WorkflowDefinition` remains the correct Workflow aggregate and its fields/dependency direction are appropriate, but the target semantic baseline cannot mark it APPROVED while the documented version, French-name, uniqueness, and active-definition versioning rules are not consistently enforced.

The later HMS reconciliation must retain these four correction obligations until an explicitly authorized Workflow implementation task resolves them or the product owner explicitly changes the target semantics.

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

- `64f0f1c283f8b9256742cdc9224762e40eb95162` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:54](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L54)
- [docs/roadmap/model-semantic-review.md:197](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L197)
- [docs/roadmap/model-semantic-review.md:3250](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L3250)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:197 | HMSR-002 | 0 | workflow | WorkflowDefinition | — | — | 4 | 36 | 1 | REVISE | Completed | docs(model-review): review workflow WorkflowDefinition
doc/domain/SEMANTIC_DECISIONS.md:54 | HMSR-002 | [workflow.WorkflowDefinition](#workflow-workflowdefinition) | REVISE | HMR-003
~~~~~~~~

<a id="hapi-361"></a>
## HAPI-361 — review workflow WorkflowStep

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMSR-051

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review workflow WorkflowStep

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 64. HMSR-051 — workflow.WorkflowStep review

**Decision:** REVISE  
**Review code:** HMSR-051  
**Dependency level:** 2  
**Bounded context:** workflow  
**SCC:** SCC-02 with `workflow.WorkflowStepAssignmentRule`  
**Confirmed upstream subject dependencies:** `workflow.WorkflowDefinition`, optional `workflow.WorkflowStepAssignmentRule`  
**Confirmed direct HMS subject dependents:** 5  
**Transitive HMS subject dependents:** 35

### 64.1 Semantic role and ordering rationale

`WorkflowStep` is the Workflow-owned ordered step definition inside a reusable WorkflowDefinition.

It carries stable step identity/code, localized display names, deterministic ordering, mandatory/skippable semantics, optional step classification, optional default assignment rule, optional SLA policy, and claim/delegation/escalation capabilities.

It is Level 2 because it depends on the Level-0 WorkflowDefinition and participates in SCC-02 with WorkflowStepAssignmentRule: the rule points to its owning step while the step may optionally designate one default assignment rule.

### 64.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable workflow-step identity. |
| `definitionId` | `String` | Mandatory same-module reference to owning WorkflowDefinition. |
| `code` | `String` | Mandatory stable step code within the definition. |
| `nameAr` | `String` | Optional Arabic display name. |
| `nameFr` | `String` | French display name required by persistence; current architecture does not independently promote generic display text into an intrinsic constructor invariant. |
| `nameEn` | `String` | Optional English display name. |
| `stepOrder` | `int` | Mandatory deterministic ordering value; DDD requires non-negative values. |
| `mandatory` | `boolean` | Whether the step is mandatory in workflow execution. |
| `stepTypeId` | `String` | Optional Workflow-owned `WORKFLOW_STEP_TYPE` catalog reference. |
| `defaultAssignmentRuleId` | `String` | Optional same-module reference to WorkflowStepAssignmentRule. |
| `slaPolicyId` | `String` | Optional same-module reference to WorkflowSlaPolicy. |
| `allowClaim` | `boolean` | Whether eligible actors may claim a generated task. |
| `allowDelegation` | `boolean` | Whether delegation is allowed for the step. |
| `allowEscalation` | `boolean` | Whether escalation is allowed for the step. |
| `createdAt` | `Instant` | Persistence-required creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required update timestamp. |

The canonical constructor currently validates only nonblank ID, definition ID and code, then normalizes optional string fields.

### 64.3 WorkflowDefinition integrity

The final schema requires `definition_id NOT NULL`.

HRA-111 installs and validates:

```text
hidra_workflow_step.definition_id
    -> hidra_workflow_definition.id
```

Therefore the mandatory parent definition reference is fail-closed at persistence, and the domain also rejects blank definition IDs.

### 64.4 Explicit step-order invariant is unenforced

The Workflow DDD explicitly states:

```text
Step order must be >= 0.
```

The canonical `WorkflowStep` constructor does not validate `stepOrder`.

The base schema stores `step_order integer NOT NULL` but repository evidence reviewed for HMSR-051 shows no CHECK enforcing non-negative values.

No authoritative WorkflowStep creation service was found that independently validates this invariant before persistence.

Therefore negative step order is currently representable and persistable despite the explicit DDD rule.

### 64.5 Explicit per-definition uniqueness invariants are unenforced

The Workflow DDD explicitly requires:

```text
(definitionId, code) must be unique.
(definitionId, stepOrder) must be unique.
```

The base workflow migration creates ordinary indexes but no unique constraint for either pair, and repository-wide search found no later unique/partial-unique migration for these identities.

`WorkflowStepRepositoryPort` exposes only `save()` and `findById()`; no authoritative duplicate check is present at this boundary.

Consequently, a definition can currently persist duplicate step codes or duplicate step-order positions, contradicting the DDD's deterministic workflow-definition semantics.

### 64.6 Default assignment-rule SCC reference

`defaultAssignmentRuleId` is an optional same-module reference to the HMS subject `WorkflowStepAssignmentRule`.

This creates the accepted SCC-02 relationship because WorkflowStepAssignmentRule itself references its owning WorkflowStep.

The active transition application service attempts to resolve a populated default assignment rule when entering a target step and requires the resolved rule to be active. That protects runtime use but does not prevent a dangling ID from being persisted on WorkflowStep.

Current schema evidence shows no nullable FK from `hidra_workflow_step.default_assignment_rule_id` to `hidra_workflow_step_assignment_rule.id`, and no authoritative write-time validation was found.

Therefore populated default-assignment-rule references can dangle until runtime.

The production correction must preserve the SCC rather than removing one side merely to simplify graph ordering.

### 64.7 Step type and SLA policy semantics

The DDD explicitly describes `stepTypeId` as a Workflow catalog reference for step type, with examples such as REVIEW, APPROVAL, VALIDATION, CORRECTION and CLOSURE. HMSR-051 therefore strengthens its classification to the `WORKFLOW_STEP_TYPE` catalog family.

The DDD describes `slaPolicyId` as an optional SLA/due-date policy reference. Current architecture retains WorkflowSlaPolicy as a persistence/read model outside the 123-model HMS subject set.

The reviewed evidence does not establish a mandatory step type or SLA policy for every WorkflowStep.

HMSR-051 also does not invent catalog-family/FK correction obligations for these optional target-addition fields beyond the explicit rules already sufficient to require REVISE; HMS-006 may reconcile them with later Workflow model reviews if stronger evidence emerges.

### 64.8 Mandatory-step semantics

The DDD states:

```text
A mandatory step cannot be skipped unless transition policy explicitly allows return/cancel/escalation.
```

This is execution/transition policy involving WorkflowTransition and WorkflowInstance/Task orchestration, not a standalone scalar invariant on WorkflowStep construction.

HMSR-051 therefore records no fabricated constructor constraint for `mandatory`. The rule must be evaluated together with downstream transition/execution models in their own reviews.

### 64.9 Multilingual semantics

The DDD marks French name as required and Arabic/English as optional/target-improved.

The repository's domain-invariant architecture deliberately treats generic human-readable text required only by persistence as `PERSISTENCE_ONLY_TEXT` unless stronger independent domain evidence promotes it.

HMSR-051 therefore does not add a separate domain correction for `nameFr` solely because the JPA/schema column is non-null.

No rule requires Arabic, French and English simultaneously.

### 64.10 Domain/JPA/Flyway alignment

Domain and JPA agree on all 16 components.

Required structural fields align for ID, WorkflowDefinition ID and code. Persistence additionally requires French name, step order, boolean capability flags, and audit timestamps.

The major semantic defects are not field-shape mismatches but explicit DDD behavior/integrity rules that the current domain/schema/write boundary does not enforce:

- non-negative step order;
- unique code per definition;
- unique step order per definition; and
- integrity of populated default assignment-rule references.

### 64.11 Required revisions

Four evidence-backed production correction obligations remain:

1. **Enforce `stepOrder >= 0`.** The authoritative domain/application/database boundary must reject negative workflow-step ordering.

2. **Enforce uniqueness of `(definitionId, code)`.** Two steps in the same WorkflowDefinition must not share the same stable step code.

3. **Enforce uniqueness of `(definitionId, stepOrder)`.** Two steps in the same WorkflowDefinition must not occupy the same deterministic order position.

4. **Protect populated `defaultAssignmentRuleId` references.** When supplied, the reference must resolve to WorkflowStepAssignmentRule through a nullable same-module FK, application validation, or another fail-closed mechanism that preserves SCC-02.

HMSR-051 does not modify production Java, JPA, Flyway, application/API contracts, tests, workflow definitions, steps, assignment rules, SLA policies, catalogs, or provisioned data.

### 64.12 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, WorkflowStep defines ordered validation/approval activities around operational decisions such as telemetry validation, planning approval, incident handling, integrity decisions and other governed processes.

Duplicate order positions or duplicate step codes can make route interpretation ambiguous and can compromise repeatable process execution. Negative order is equally inconsistent with the documented ordered-step model.

A dangling default assignment rule can delay failure until a workflow transition reaches the step, which is undesirable for operational process reliability.

### 64.13 Review conclusion

**REVISE.** `WorkflowStep` has a coherent 16-field Workflow-owned ordered-step model, a protected mandatory WorkflowDefinition dependency, appropriate SCC-02 relationship with WorkflowStepAssignmentRule, neutral optional SLA/catalog extensions, and correct separation from target business ownership.

The target baseline cannot mark it APPROVED while the explicit DDD requirements for non-negative step order and per-definition uniqueness are unenforced and a populated default assignment-rule reference can dangle.

HMS reconciliation must retain these obligations until explicitly authorized Workflow production corrections resolve them or stronger repository evidence revises the governing Workflow DDD.

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

- `5becabb4f329d55e7e3e8e4038cf7acf9fdeae9b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:103](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L103)
- [docs/roadmap/model-semantic-review.md:246](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L246)
- [docs/roadmap/model-semantic-review.md:10743](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L10743)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:246 | HMSR-051 | 2 | workflow | WorkflowStep | SCC-02 | workflow.WorkflowDefinition, workflow.WorkflowStepAssignmentRule | 5 | 35 | 0 | REVISE | Completed | docs(model-review): review workflow WorkflowStep
doc/domain/SEMANTIC_DECISIONS.md:103 | HMSR-051 | [workflow.WorkflowStep](#workflow-workflowstep) | REVISE | HMR-043
~~~~~~~~

<a id="hapi-362"></a>
## HAPI-362 — review workflow WorkflowStepAssignmentRule

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMSR-052

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review workflow WorkflowStepAssignmentRule

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 65. HMSR-052 — workflow.WorkflowStepAssignmentRule review

**Decision:** REVISE  
**Review code:** HMSR-052  
**Dependency level:** 2  
**Bounded context:** workflow  
**SCC:** SCC-02 with `workflow.WorkflowStep`  
**Confirmed upstream subject dependencies:** `workflow.WorkflowDefinition`, `workflow.WorkflowStep`, optional `organization.OrganizationUnit`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 35

### 65.1 Semantic role and ordering rationale

`WorkflowStepAssignmentRule` is the Workflow-owned reusable assignment policy attached to a WorkflowStep.

It defines how a step resolves an actor or candidate pool without making Workflow the owner of Identity actors, Organization units, roles, positions, or target-business ownership.

It participates in SCC-02 because every rule points to a WorkflowStep while a WorkflowStep may designate one default assignment rule.

### 65.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable assignment-rule identity. |
| `definitionId` | `String` | Mandatory same-module reference to WorkflowDefinition. |
| `stepId` | `String` | Mandatory same-module reference to WorkflowStep. |
| `assignmentModeId` | `String` | Mandatory Workflow-owned `WORKFLOW_ASSIGNMENT_MODE` catalog reference. |
| `actorId` | `String` | Optional Identity actor selector. |
| `roleCode` | `String` | Optional Identity role/permission code requirement/snapshot. |
| `organizationUnitId` | `String` | Optional OrganizationUnit candidate-pool reference. |
| `organizationRoleCode` | `String` | Optional Organization role/position requirement. |
| `targetOwnerMode` | `String` | Optional instruction to resolve an owner from the workflow target through a lookup boundary. |
| `active` | `boolean` | Whether the rule may currently be used. |
| `createdAt` | `Instant` | Persistence-required creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required update timestamp. |

The canonical constructor requires nonblank ID, definition ID, step ID and assignment-mode ID, while all candidate-selector fields remain individually optional.

### 65.3 WorkflowDefinition and WorkflowStep reference integrity

The final schema requires both `definition_id` and `step_id`.

HRA-111 installs and validates:

```text
definition_id -> hidra_workflow_definition.id
step_id       -> hidra_workflow_step.id
```

Therefore both mandatory same-module references are fail-closed at persistence.

`stepId` is the reverse side of SCC-02. HMSR-052 preserves the cycle rather than attempting to flatten it into a false ordering.

Current DDD evidence does not explicitly define a separate persisted invariant that the referenced step's `definitionId` must equal this rule's `definitionId`; the runtime transition service verifies definition/step consistency when using a default rule. HMSR-052 does not invent a broader parent-consistency obligation without a stronger explicit rule.

### 65.4 Assignment-mode catalog semantics

The Workflow DDD identifies `assignmentModeId` as a mandatory catalog reference and explicitly lists:

```text
WORKFLOW_ASSIGNMENT_MODE
```

among the recommended Workflow catalog families.

HRA-111 protects `assignment_mode_id` with a same-module FK to `hidra_workflow_type_catalog.id`.

That FK proves that the referenced catalog row exists, but it does not prove that the row belongs to the `WORKFLOW_ASSIGNMENT_MODE` family.

No authoritative application validation was found that checks catalog-family membership before persisting or using a WorkflowStepAssignmentRule.

Therefore the rule can currently point to an unrelated Workflow catalog entry while still satisfying the database FK.

### 65.5 Explicit candidate-resolution invariant is unenforced

The Workflow DDD states:

```text
Assignment rule must resolve to at least one eligible actor or candidate pool.
Workflow may look up eligibility through ports, not direct imports.
```

The rule has five potential candidate/eligibility selectors:

```text
actorId
roleCode
organizationUnitId
organizationRoleCode
targetOwnerMode
```

The canonical constructor permits all five to normalize to `null` simultaneously.

No dedicated WorkflowStepAssignmentRule creation service or application guard was found that rejects a selector-less rule before save. The repository port exposes unrestricted `save()`.

As a result, a structurally valid ACTIVE assignment rule can exist that has no actor and no candidate-pool resolution mechanism, directly contradicting the DDD.

### 65.6 Runtime use does not cure invalid persisted rules

`WorkflowTransitionApplicationService` loads a target step's default assignment rule and, if found and active, maps its actor/organization/role/mode fields into the next WorkflowTask.

It verifies that the loaded rule belongs to the running definition and target step.

However, it does not transform a selector-less rule into a valid candidate pool. Such a rule can still produce a task without a resolvable assignee/candidate source.

Therefore runtime use is not a substitute for the DDD's assignment-rule validity invariant.

### 65.7 OrganizationUnit boundary

`organizationUnitId` is an optional cross-module reference to `organization.OrganizationUnit`.

Workflow does not own Organization hierarchy or unit lifecycle, and no cross-module database FK should be introduced.

The DDD's architectural rule is that Workflow may look up eligibility through ports rather than direct imports.

Current evidence reviewed for HMSR-052 does not define an explicit creation-time OrganizationUnit existence/status rule distinct from the broader “must resolve to at least one eligible actor or candidate pool” invariant. HMSR-052 therefore does not manufacture a second OrganizationUnit-specific correction obligation; the later production correction should use owner-controlled lookup semantics wherever the selected assignment mode requires organizational candidate resolution.

### 65.8 Actor, role and organization-role semantics

`actorId` is an Identity-owned actor reference.

`roleCode` is described by the DDD as an Identity role/permission code snapshot/requirement.

`organizationRoleCode` represents an Organization role/position requirement rather than an Identity role.

These fields intentionally remain neutral references/codes so Workflow does not import Identity or Organization implementation classes.

No DDD evidence requires any one of these fields individually, because assignment mode determines which selector is relevant. HMSR-052 therefore enforces only the documented “at least one resolvable actor/candidate pool” invariant rather than inventing per-mode combinations.

### 65.9 Target-owner semantics

`targetOwnerMode` instructs Workflow to resolve the owner of the target object through a lookup port.

This preserves the core Workflow boundary:

```text
Workflow owns the process.
The target module owns the business fact.
```

HMSR-052 does not convert target ownership into a direct foreign key or bounded-context import.

Current evidence does not define the allowed `targetOwnerMode` vocabulary or exact supported target-owner algorithms, so none are invented.

### 65.10 Active state and uniqueness rules deliberately not invented

The `active` flag is mandatory and used by the transition service when selecting the default rule.

Current Workflow DDD evidence does not state:

- that only one active rule may exist per step;
- that `(definitionId, stepId, assignmentModeId)` must be unique;
- that inactive rules must be immutable;
- a lifecycle transition matrix for the boolean active flag.

HMSR-052 therefore does not record those as production obligations.

### 65.11 Domain/JPA/Flyway alignment

Domain and JPA agree on all 12 components and requiredness.

The schema requires:

```text
id
definition_id
step_id
assignment_mode_id
active
created_at
updated_at
```

The domain enforces all mandatory business identifiers. HRA-111 protects the mandatory Definition, Step and assignment-mode catalog row identities.

The remaining defects are semantic:

- assignment-mode family membership is not fail-closed; and
- the explicit candidate-resolution invariant is not enforced.

### 65.12 Required revisions

Two evidence-backed production correction obligations remain:

1. **Enforce `WORKFLOW_ASSIGNMENT_MODE` catalog-family semantics for `assignmentModeId`.** Generic Workflow catalog-row existence is insufficient; the selected entry must belong to the documented assignment-mode family.

2. **Require every WorkflowStepAssignmentRule to provide at least one resolvable actor or candidate-pool strategy.** The authoritative domain/application boundary must reject rules where `actorId`, `roleCode`, `organizationUnitId`, `organizationRoleCode`, and `targetOwnerMode` are all absent. Mode-specific eligibility may be resolved through owner-controlled ports, but Workflow must not persist a rule that cannot resolve any candidate source.

HMSR-052 does not modify production Java, JPA, Flyway, application/API contracts, tests, workflow rules, definitions, steps, catalogs, Organization units, Identity records, or provisioned data.

### 65.13 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, WorkflowStepAssignmentRule determines who receives or may claim operational decision tasks such as telemetry validation, plan approval, integrity review, incident handling or HSE actions.

A rule with no candidate source can create an operational task that nobody can legitimately receive. A rule using the wrong catalog family can apply an unrelated Workflow taxonomy entry as an assignment mode, weakening deterministic routing and governance.

The correct architecture is to keep Workflow as process owner while resolving people, organizational pools and target owners through bounded-context contracts.

### 65.14 Review conclusion

**REVISE.** `WorkflowStepAssignmentRule` has coherent Definition/Step dependencies with validated same-module FKs, a correct SCC-02 relationship with WorkflowStep, neutral cross-module assignment selectors, and proper process-boundary separation from Identity/Organization/target modules.

The target baseline cannot mark it APPROVED while assignment-mode family semantics are unenforced and a rule may contain no actor/candidate-pool resolution strategy despite the explicit Workflow DDD requirement.

HMS reconciliation must retain these two obligations until explicitly authorized Workflow production corrections resolve them or stronger repository evidence revises the governing Workflow DDD.

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

- `1d37d09df39691451da83dafa6f223151952c6ad` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:104](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L104)
- [docs/roadmap/model-semantic-review.md:247](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L247)
- [docs/roadmap/model-semantic-review.md:10918](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L10918)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:247 | HMSR-052 | 2 | workflow | WorkflowStepAssignmentRule | SCC-02 | organization.OrganizationUnit, workflow.WorkflowDefinition, workflow.WorkflowStep | 1 | 35 | 0 | REVISE | Completed | docs(model-review): review workflow WorkflowStepAssignmentRule
doc/domain/SEMANTIC_DECISIONS.md:104 | HMSR-052 | [workflow.WorkflowStepAssignmentRule](#workflow-workflowstepassignmentrule) | REVISE | HMR-044
~~~~~~~~

<a id="hapi-374"></a>
## HAPI-374 — review workflow WorkflowInstance

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMSR-064

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review workflow WorkflowInstance

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 77. HMSR-064 — workflow.WorkflowInstance review

**Decision:** REVISE  
**Review code:** HMSR-064  
**Dependency level:** 3  
**Bounded context:** workflow  
**Confirmed upstream subject dependencies:** `workflow.WorkflowDefinition`, optional `workflow.WorkflowStep`  
**Confirmed direct HMS subject dependents:** 18  
**Transitive HMS subject dependents:** 32

### 77.1 Semantic role

`WorkflowInstance` is the Workflow-owned running execution of one workflow definition over a neutral target reference. It captures the definition/version used, workflow purpose, typed target identity and snapshots, current lifecycle/current step, starter actor snapshots, timestamps and correlation metadata.

Workflow owns process execution. It does not own the target business object, Identity actor, or target-module state.

### 77.2 Definition identity and version

`definitionId` is mandatory and HRA-111 protects same-module row existence with:

```text
hidra_workflow_instance.definition_id -> hidra_workflow_definition.id
```

`WorkflowDefinition` carries both a lifecycle status and an integer version, and its domain helper permits instance start only when status is `ACTIVE`. The DDD also states that RETIRED definitions cannot start new instances and that active structural changes create a new version.

The live `WorkflowApplicationService.startWorkflowInstance()` copies `definitionId` and `definitionVersion` directly from the command and saves the instance. It does not resolve the definition, prove that it is ACTIVE, or prove that `command.definitionVersion` equals the selected definition's current immutable version.

Therefore row existence alone is insufficient for authoritative start semantics.

### 77.3 Definition-target-purpose binding

The Workflow DDD defines `WorkflowDefinitionTargetBinding` specifically to prevent arbitrary workflows from starting against arbitrary target types.

Its governing rule is:

```text
Workflow instance can start only if an active binding exists.
```

The binding key is:

```text
definitionId + targetModule + targetTypeId + workflowPurposeId
```

The target-binding table/entity exists in persistence and HRA-111 protects its same-module definition/catalog references. However, the live start service does not query or validate an active binding before creating a WorkflowInstance.

The authoritative start path must fail closed unless the selected definition, target module/type and purpose match an active binding.

### 77.4 Workflow purpose semantics

`workflowPurposeId` participates in the active-binding contract and in the DDD's corrected non-terminal uniqueness rule.

The live domain/JPA field is optional, while `WorkflowDefinitionTargetBinding.workflowPurposeId` is required. Current repository evidence does not provide a clearly named `WORKFLOW_PURPOSE` catalog family alongside the documented `WORKFLOW_TARGET_TYPE`, `WORKFLOW_REASON` and related families.

HMSR-064 therefore does not invent a purpose family. The target baseline must reconcile whether purpose is mandatory for every start and explicitly define its controlled-value ownership before relying on it as part of binding/uniqueness semantics.

### 77.5 Target type and target-owner validation

`targetTypeId` is mandatory and HRA-111 protects generic Workflow catalog-row existence:

```text
hidra_workflow_instance.target_type_id -> hidra_workflow_type_catalog.id
```

The Workflow DDD explicitly defines `WORKFLOW_TARGET_TYPE` as a controlled catalog family. Generic catalog-row existence therefore does not prove correct family membership or active eligibility.

The actual business target remains a neutral typed reference:

```text
targetModule + targetTypeId + targetId
```

Workflow must not persist or import the target module's domain state.

The DDD/roadmap explicitly prescribe `WorkflowTargetLookupPort`-style owner-controlled validation, but no live target lookup is used by `startWorkflowInstance()`. The authoritative start path must validate the target through the owning module contract and then preserve code/label snapshots.

No cross-module database FK should be introduced.

### 77.6 Current-step integrity

`currentStepId` is optional and resolves to same-module `WorkflowStep`.

The current instance schema indexes `current_step_id`, but HMSR-064 found no HRA-111 instance-side FK for it. The start service accepts `currentStepId` directly from the command without resolving the step or proving:

```text
currentStep.definitionId == instance.definitionId
```

Later transition execution does protect target-step definition membership, but that does not repair an invalid instance at creation.

When a current/initial step is populated, the authoritative start path must resolve it through Workflow and ensure it belongs to the selected definition. HMSR-064 does not invent which step must be the initial step; that ordering policy requires explicit workflow-definition evidence.

### 77.7 One non-terminal instance per target and purpose

The Workflow DDD explicitly identifies the current uniqueness design as insufficient and states the target rule:

```text
At most one non-terminal workflow instance per target + workflow purpose.
```

Non-terminal statuses are:

```text
DRAFT
STARTED
IN_PROGRESS
WAITING
```

The live `WorkflowInstanceRepositoryPort` exposes save/find operations only; `startWorkflowInstance()` performs no non-terminal duplicate check. Current repository evidence does not show an applied partial unique index enforcing the corrected rule.

This must be enforced fail-closed at the database and/or transactional application boundary appropriate to PostgreSQL concurrency semantics.

### 77.8 Starter actor boundary

`startedByActorId` is mandatory and accompanied by username/display-name/role snapshots.

Workflow does not own Identity. The Workflow roadmap explicitly defines actor/authority lookup ports for this boundary, but the current start service merely copies actor IDs/snapshots from the command.

The authoritative start path should validate the starter through the Identity-owned actor/authority contract while preserving snapshots. No cross-module Identity FK should be added.

HMSR-064 does not invent additional role/permission requirements beyond the owner validation and authorization policy already assigned to Workflow/Identity integration.

### 77.9 Lifecycle and temporal semantics

`WorkflowInstanceStatus` is a genuine Workflow lifecycle enum:

```text
DRAFT
STARTED
IN_PROGRESS
WAITING
COMPLETED
CANCELLED
FAILED
```

The transition service already locks the instance, rejects terminal instances, validates transition/step ownership, and writes state history while advancing status.

The DDD explicitly states:

```text
completedAt >= startedAt
cancelledAt >= startedAt
```

The current controlled transition path assigns terminal timestamps using the current time, which is consistent with those rules. HMSR-064 does not add speculative transition states beyond the DDD and existing transition engine.

### 77.10 Required revisions

Six evidence-backed obligations remain:

1. **Resolve the definition before start and enforce ACTIVE/version consistency.** `definitionId` must identify an ACTIVE WorkflowDefinition and `definitionVersion` must match the definition version being instantiated.

2. **Require an active WorkflowDefinitionTargetBinding for the exact definition + target module/type + workflow purpose combination before start.**

3. **Reconcile workflow-purpose controlled-value semantics and nullability.** Purpose participates in binding and non-terminal uniqueness but its exact catalog-family contract is not currently explicit; do not invent one.

4. **Validate target type and target identity correctly.** Enforce `WORKFLOW_TARGET_TYPE` family eligibility and validate `targetModule + targetTypeId + targetId` through an owner-controlled target lookup while preserving snapshots; do not add cross-module DB FKs.

5. **Protect current-step and non-terminal-instance integrity.** A populated `currentStepId` must resolve to a WorkflowStep belonging to the selected definition, and the DDD rule of at most one non-terminal instance per target + purpose must be enforced transactionally/database-safe.

6. **Validate the mandatory starter actor through the Identity-owned actor/authority boundary while preserving actor snapshots.**

HMSR-064 does not modify production Java, JPA, Flyway, API/application contracts, tests, Workflow bindings/catalogs, Identity, target modules, or provisioned data.

### 77.11 Review conclusion

**REVISE.** `WorkflowInstance` is a coherent 21-field Workflow-owned execution model with a correct neutral-target architecture, a true lifecycle enum, same-module definition existence protection and a substantially hardened transition engine.

The target baseline cannot mark it APPROVED because authoritative instance start currently trusts caller-supplied definition/version, binding, target, step and actor information; the DDD's corrected non-terminal uniqueness rule is not enforced; and workflow-purpose semantics remain insufficiently resolved for the binding/uniqueness contract.

HMS reconciliation must retain these obligations until explicitly authorized Workflow production corrections resolve them or stronger repository evidence changes the governing Workflow DDD.

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

- `4f8c79ee749ff03f5765219f0f49f3db52820f22` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:116](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L116)
- [docs/roadmap/model-semantic-review.md:259](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L259)
- [docs/roadmap/model-semantic-review.md:12626](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L12626)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:259 | HMSR-064 | 3 | workflow | WorkflowInstance | — | workflow.WorkflowDefinition, workflow.WorkflowStep | 18 | 32 | 1 | REVISE | Completed | docs(model-review): review workflow WorkflowInstance
doc/domain/SEMANTIC_DECISIONS.md:116 | HMSR-064 | [workflow.WorkflowInstance](#workflow-workflowinstance) | REVISE | HMR-055
~~~~~~~~

<a id="hapi-383"></a>
## HAPI-383 — review workflow WorkflowTransition

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMSR-073

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review workflow WorkflowTransition

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDecisionGuard.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDecisionGuard.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 86. HMSR-073 — workflow.WorkflowTransition review

**Decision:** REVISE  
**Review code:** HMSR-073  
**Dependency level:** 3  
**Bounded context:** workflow  
**Confirmed upstream subject dependencies:** `workflow.WorkflowDefinition`, `workflow.WorkflowStep`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0

### 86.1 Semantic role

`WorkflowTransition` is the Workflow-owned configuration that declares one allowed state-advancing decision from a source step to a destination step inside one workflow definition.

It also carries decision-specific reason/comment requirements, optional authorization policy metadata and optional condition/callback configuration.

Workflow owns this process configuration. It does not own the business object being approved.

### 86.2 Same-module reference integrity

`definitionId`, `fromStepId` and `toStepId` are mandatory same-module references.

HRA-111 protects row existence for all three targets:

```text
definitionId -> WorkflowDefinition
fromStepId   -> WorkflowStep
toStepId     -> WorkflowStep
```

Those foreign keys correctly prevent dangling references.

However, independent FK existence does not prove semantic composition.

### 86.3 Definition/step consistency

The Workflow DDD explicitly requires:

```text
Transition source and destination steps must belong to the same definition.
```

The runtime `WorkflowTransitionApplicationService` correctly validates:

```text
transition.definitionId == running instance.definitionId
transition.fromStepId == current task.stepId
targetStep.definitionId == running instance.definitionId
```

This is strong execution-time protection.

But the `WorkflowTransition` domain record itself accepts arbitrary valid IDs, and repository search found no authoritative transition-configuration write use case that resolves both steps before saving the transition.

Therefore an internally inconsistent transition can still exist in persistence until execution rejects it.

The authoritative transition-configuration boundary must ensure:

```text
fromStep.definitionId == transition.definitionId
toStep.definitionId   == transition.definitionId
```

before a transition can be considered valid configuration.

### 86.4 Self-transition prohibition

The DDD explicitly states:

```text
fromStepId cannot equal toStepId.
```

The live execution service already rejects self-transitions before advancing an instance.

The domain model and persistence schema do not enforce that invariant at configuration time.

Because this rule is explicit and model-intrinsic, it should fail closed when transition configuration is created/updated rather than only when a user later attempts to execute it.

### 86.5 Decision uniqueness per source step

The Workflow DDD explicitly states:

```text
(definitionId, fromStepId, decision) must be unique.
Only configured decisions are allowed from a given step.
```

Current repository search found no database unique constraint for this tuple.

Without that protection, multiple transitions for the same definition/source-step/decision can create ambiguous execution configuration.

The target model therefore requires transactional/database-safe uniqueness for:

```text
definitionId + fromStepId + decision
```

HMSR-073 does not invent uniqueness on destination step or transition ID beyond the existing primary key.

### 86.6 Reason/comment execution semantics

The transition carries:

```text
reasonRequired
commentRequired
```

The runtime executor already combines:
- `WorkflowDecisionGuard` decision-level reason/comment semantics,
- transition-specific `reasonRequired`,
- transition-specific `commentRequired`.

This aligns with the DDD rule that reason/comment requirements must be enforced during action execution.

No additional semantic revision is required for those fields.

### 86.7 Permission semantics

`requiredPermissionCode` is optional.

The executor already validates the configured permission against the authenticated command's effective permission set, including the explicit wildcard authority behavior.

Workflow does not own Identity permission master data, so this field correctly remains a policy code rather than a cross-module database FK.

HMSR-073 records no new Identity relationship.

### 86.8 Condition and callback configuration

The model permits optional:

```text
conditionExpression
targetModuleCallback
```

The current executor explicitly fails closed when either is configured:

```text
conditional workflow transition execution is not available until a condition evaluator is configured
target-module callback execution is not available for this transition
```

That behavior is safe, because unsupported configuration cannot silently execute.

However, ACTIVE workflow definitions should not be allowed to treat such transitions as executable configuration until the corresponding controlled evaluator/callback architecture exists.

Later reconciliation should either:
- prevent unsupported condition/callback transitions from activation/use, or
- implement their governed execution contracts.

HMSR-073 does not invent an expression language, callback protocol, or direct target-module invocation mechanism.

### 86.9 WorkflowDecision enum

`WorkflowDecision` defines:

```text
APPROVE
REJECT
REQUEST_CORRECTION
CORRECT
RETURN
DELEGATE
ESCALATE
CANCEL
COMMENT
```

This is a stable Workflow process decision vocabulary used directly by the transition engine.

Although Workflow business taxonomies are generally catalog-backed, this enum is part of the executable process state-machine contract rather than a user-managed descriptive taxonomy.

Current evidence does not require replacing it with a catalog.

### 86.10 Required revisions

Four evidence-backed obligations remain:

1. **Enforce definition/step composition at transition configuration time.** Both source and destination steps must belong to the transition's WorkflowDefinition.

2. **Enforce `fromStepId != toStepId` as a configuration/domain invariant**, not only as an execution-time guard.

3. **Enforce uniqueness of `definitionId + fromStepId + decision`.** Prevent ambiguous duplicate configured decisions from one source step.

4. **Prevent unsupported condition/callback configuration from becoming executable ACTIVE workflow configuration until governed evaluators/contracts exist**, or implement those contracts explicitly. Preserve the current fail-closed runtime behavior.

HMSR-073 does not modify production Java, JPA, Flyway, API/application contracts, tests, Workflow definitions/steps/transitions, Identity data, target-module contracts, or provisioned data.

### 86.11 Review conclusion

**REVISE.** `WorkflowTransition` is a coherent 12-field Workflow-owned transition configuration model. All mandatory same-module identities are database-protected, and the runtime executor already performs strong definition/source/destination, permission, reason/comment and unsupported-feature checks.

The target baseline cannot mark it APPROVED while invalid cross-definition/self transitions and duplicate source-step decisions can still be persisted as configuration, and condition/callback transition configuration can exist even though execution intentionally rejects those features.

HMS reconciliation must retain these obligations until explicitly authorized Workflow production corrections resolve them or stronger repository evidence changes the governing Workflow DDD.

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

- `b3c345be07a3459e8fc6efdc26fd9a24e37851a3` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:125](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L125)
- [docs/roadmap/model-semantic-review.md:268](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L268)
- [docs/roadmap/model-semantic-review.md:13959](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L13959)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:268 | HMSR-073 | 3 | workflow | WorkflowTransition | — | workflow.WorkflowDefinition, workflow.WorkflowStep | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review workflow WorkflowTransition
doc/domain/SEMANTIC_DECISIONS.md:125 | HMSR-073 | [workflow.WorkflowTransition](#workflow-workflowtransition) | REVISE | HMR-061
~~~~~~~~

<a id="hapi-388"></a>
## HAPI-388 — review workflow WorkflowTask

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMSR-078

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review workflow WorkflowTask

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowSlaStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowSlaStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 91. HMSR-078 — workflow.WorkflowTask review

**Decision:** REVISE  
**Review code:** HMSR-078  
**Dependency level:** 4  
**Bounded context:** workflow  
**Confirmed upstream subject dependencies:** optional `organization.OrganizationUnit`, mandatory `workflow.WorkflowInstance`, mandatory `workflow.WorkflowStep`  
**Confirmed direct HMS subject dependents:** 4  
**Transitive HMS subject dependents:** 6

### 91.1 Semantic role

`WorkflowTask` is the Workflow-owned actionable work item for one workflow instance and one workflow step. It owns task lifecycle state, assignment snapshots, optional priority/SLA data, claim/completion evidence, delegation/escalation timestamps and audit/lifecycle timestamps.

Workflow owns task routing and lifecycle. Identity owns actors and authority, Organization owns organization units, while Workflow stores stable actor/organization references plus snapshots. Cross-module assignee references must not become database foreign keys.

### 91.2 Instance and step integrity

`instanceId` and `stepId` are mandatory same-module references. HRA-111 already protects both with Workflow-local foreign keys to `WorkflowInstance` and `WorkflowStep`.

The transition service additionally verifies that the task belongs to the workflow instance's current step before executing a transition.

No further basic existence correction is required for these two mandatory references.

### 91.3 Assignment-target invariant

The Workflow DDD explicitly states:

```text
A task must have at least one assignment target: actor or organization unit.
```

The live `WorkflowTask` constructor does not enforce this invariant. `WorkflowApplicationService.createWorkflowTask()` accepts both assignment IDs directly and can construct an OPEN task with neither actor nor organization unit populated.

The transition service's `createNextTask()` can likewise create an OPEN task with no actor or organization unit when no active assignment rule resolves.

The authoritative task-creation boundary must therefore reject creation of an actionable task that has no assignment target.

### 91.4 Assignee ownership and eligibility

`assignedActorId`, `claimedByActorId`, and `completedByActorId` are Identity-owned actor references. `assignedOrganizationUnitId` is Organization-owned. Username/display/unit/role fields are snapshots rather than owned master data.

The Workflow DDD requires:

```text
Only eligible actors may claim or complete a task.
```

The current transition path uses a local `belongsToActor(...)` comparison against assigned actor ID, claimed actor ID, or assigned username snapshot. Repository evidence does not show owner-controlled Identity/Organization eligibility resolution in the reviewed path, and organization/role pool assignments are not authoritatively resolved before transition execution.

The authoritative boundary must resolve assignment/eligibility through Workflow-owned outbound contracts backed by Identity/Organization owners, while retaining scalar references and snapshots. No cross-module database FK should be introduced.

### 91.5 Claim and completion invariants

The Workflow DDD explicitly states:

```text
If claimedByActorId exists, claimedAt must exist.
If completedByActorId exists, completedAt must exist.
completedAt must be >= createdAt.
Completed tasks are immutable except for audit-safe annotations.
```

The live `WorkflowTask` constructor enforces none of these couplings. It permits claimed/completed actor IDs without timestamps and does not validate completion time against creation time.

The transition service does populate `completedByActorId` and `completedAt` together when it completes a task, and it rejects transitions on non-open tasks. That protects the reviewed transition path but does not make the aggregate itself semantically fail-closed across all construction/persistence paths.

The authoritative domain/application boundary must enforce the explicit claim/completion timestamp couplings and `completedAt >= createdAt`. Completed-task immutability must remain guaranteed for all mutation paths, not only transition execution.

### 91.6 Priority catalog family

`priorityId` is an optional Workflow catalog reference. The Workflow DDD defines the dedicated family:

```text
WORKFLOW_PRIORITY
```

The base schema indexes `priority_id`, but repository evidence shows no same-module FK from `hidra_workflow_task.priority_id` to the Workflow catalog and no family-level validation in task creation.

When populated, `priorityId` must resolve to an eligible/active Workflow catalog entry from exactly `WORKFLOW_PRIORITY`. Generic catalog existence alone would not be sufficient.

### 91.7 Assignment-mode catalog family

`assignmentModeId` is an optional catalog-backed task routing classification. The Workflow DDD defines:

```text
WORKFLOW_ASSIGNMENT_MODE
```

with semantics such as DIRECT_ACTOR, ROLE_POOL, ORG_UNIT_POOL and MIXED.

Although HRA-111 protects `WorkflowStepAssignmentRule.assignment_mode_id`, repository evidence shows no equivalent fail-closed protection for `WorkflowTask.assignmentModeId`, and direct task creation copies the value without catalog-family validation.

When populated, task assignment mode must resolve to an eligible/active entry in the exact `WORKFLOW_ASSIGNMENT_MODE` family.

### 91.8 Lifecycle and SLA enums

`WorkflowTaskStatus` is the documented Workflow task lifecycle:

```text
OPEN
CLAIMED
IN_REVIEW
APPROVED
REJECTED
RETURNED
DELEGATED
ESCALATED
CANCELLED
EXPIRED
```

`WorkflowSlaStatus` contains:

```text
NORMAL
DUE_SOON
OVERDUE
BREACHED
```

Both are stable technical/lifecycle classifications owned by Workflow. Current evidence does not require replacing them with catalog-backed business taxonomies.

HMSR-078 does not invent additional temporal rules among dueAt, escalatedAt, delegatedAt and expiresAt beyond the explicit DDD rules cited above.

### 91.9 Required revisions

Five evidence-backed obligations remain:

1. **Enforce assignment-target presence.** An actionable WorkflowTask must have at least one assignment target: actor or organization unit.

2. **Validate actor/organization assignment and execution eligibility through owner-controlled contracts.** Preserve scalar IDs/snapshots and do not add cross-module database FKs.

3. **Enforce explicit claim/completion invariants and terminal immutability.** Couple claimed actor/time, completed actor/time, require `completedAt >= createdAt`, and prevent mutation of completed tasks outside explicitly audit-safe annotations.

4. **Enforce the `WORKFLOW_PRIORITY` catalog family** for populated `priorityId`, including active/eligible semantics.

5. **Enforce the `WORKFLOW_ASSIGNMENT_MODE` catalog family** for populated `assignmentModeId`, including active/eligible semantics.

HMSR-078 does not modify production Java, JPA, Flyway, API/application contracts, tests, Workflow catalogs/data, Identity, Organization, or provisioned data.

### 91.10 Review conclusion

**REVISE.** `WorkflowTask` is a coherent Workflow-owned task aggregate with correct same-module instance/step ownership, appropriate task/SLA lifecycle enums, and snapshot-based cross-context actor/organization semantics.

The target baseline cannot mark it APPROVED while actionable tasks may be created without an assignment target, assignee eligibility is not resolved through owner-controlled boundaries, explicit claim/completion invariants are not aggregate-safe, and task priority/assignment-mode catalog families are not fail-closed.

HMS reconciliation must retain these obligations until explicitly authorized Workflow production corrections resolve them or stronger repository evidence changes the governing Workflow DDD.

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

- `d18aa40a18567b1f23d65f0c6a1aff23695b4798` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:130](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L130)
- [docs/roadmap/model-semantic-review.md:273](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L273)
- [docs/roadmap/model-semantic-review.md:14706](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L14706)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:273 | HMSR-078 | 4 | workflow | WorkflowTask | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowStep | 4 | 6 | 1 | REVISE | Completed | docs(model-review): review workflow WorkflowTask
doc/domain/SEMANTIC_DECISIONS.md:130 | HMSR-078 | [workflow.WorkflowTask](#workflow-workflowtask) | REVISE | HMR-066
~~~~~~~~

<a id="hapi-405"></a>
## HAPI-405 — review workflow WorkflowAction

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMSR-095

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review workflow WorkflowAction

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 108. HMSR-095 — workflow.WorkflowAction review

**Decision:** REVISE  
**Review code:** HMSR-095  
**Dependency level:** 5  
**Bounded context:** workflow  
**Confirmed upstream subject dependencies:** mandatory `workflow.WorkflowInstance`; optional task-scoped `workflow.WorkflowTask`; optional Organization-owned `organization.OrganizationUnit` context  
**Confirmed direct HMS subject dependents:** 2  
**Transitive HMS subject dependents:** 4  
**Unresolved/non-subject reference count:** 1

### 108.1 Semantic role

`WorkflowAction` is Workflow-owned append-only action and decision evidence for a workflow instance and, when applicable, a workflow task.

The Workflow DDD treats these records as core decision evidence. They preserve who acted, the decision/action, reason/comment evidence, organization context snapshots, deterministic sequence, and timestamp. Workflow owns the action record; it does not own Identity actors or Organization units and must not directly mutate the target bounded context.

### 108.2 Instance and task integrity

`instanceId` is mandatory and same-module.

HRA-111 already protects it with:

```text
hidra_workflow_action.instance_id
  -> hidra_workflow_instance.id
```

The domain constructor also rejects blank `instanceId`.

`taskId` is optional because some actions may be instance-scoped, but when supplied it denotes a real same-module `WorkflowTask`. The current generic `recordWorkflowAction(...)` path does not resolve the task, verify existence, or verify that the task belongs to the supplied instance.

A task-scoped action must therefore fail closed against the Workflow-owned task and its parent instance. This is a same-module integrity concern, not a cross-module dependency.

### 108.3 Decision-bearing actions must use configured transitions

The Workflow DDD explicitly requires:

```text
Decision-bearing action must follow a configured transition.
Only configured decisions are allowed from a given step.
Reason/comment requirements must be enforced during action execution.
```

The live `WorkflowTransitionApplicationService` implements this correctly: it locks and validates task/instance state, checks actor entitlement and permission, resolves a configured transition, applies reason/comment guards, generates the next sequence, records the action, advances task/instance state, and records state history.

However, the separate public `WorkflowApplicationService.recordWorkflowAction(...)` path constructs and saves a `WorkflowAction` directly from caller-supplied `actionType`, `decision`, `taskId`, `reasonId`, `commentText`, and `actionSequence`, without configured-transition validation.

That alternate path can therefore persist decision-bearing actions independently of the authoritative transition execution rules.

The future production correction must close that bypass: decision-bearing actions must be recorded only through the transition-authoritative workflow path, while any retained generic recording capability must be restricted to action categories whose semantics do not advance/decide workflow state.

### 108.4 Required reason and comment evidence

The DDD requires `reasonId` for:

```text
REJECT
REQUEST_CORRECTION
RETURN
DELEGATE
ESCALATE
CANCEL
```

and requires `commentText` for `REQUEST_CORRECTION`.

The transition service enforces these rules through `decisionGuard` and transition metadata. The generic direct-recording path does not.

These conditional invariants must be enforced at every authoritative action-write boundary so invalid decision evidence cannot be persisted through an alternate endpoint.

### 108.5 Actor snapshot requirements

The DDD marks `actorDisplayNameSnapshot` required and states:

```text
Action must preserve actor snapshot.
```

JPA/Flyway also persist `actor_display_name_snapshot` as `NOT NULL`.

The live domain constructor enforces `actorId` but only normalizes `actorDisplayNameSnapshot`; a blank/null display-name snapshot can therefore reach persistence and fail late.

The authoritative action boundary must fail fast for the required display-name snapshot. Username/role snapshots remain optional on current DDD evidence.

`actorId` remains an Identity-owned scalar identity and must not gain a cross-module database FK.

### 108.6 Organization context ownership

`organizationUnitId` is optional Organization-owned context, while `organizationUnitNameSnapshot` and `organizationRoleCodeSnapshot` are action-time snapshots.

Workflow must preserve these as scalar/snapshot evidence and must not own or import Organization aggregates.

Where an action carries `organizationUnitId`, the authoritative action/authorization boundary should validate that reference through an Organization-owned public lookup/reference contract when operational authorization semantics depend on it. No cross-module database FK should be introduced.

### 108.7 Monotonic action sequence

The DDD requires:

```text
actionSequence = monotonic sequence inside instance for deterministic timeline
```

The transition path obtains it from `actionRepository.nextSequence(instance.id())`.

The generic `recordWorkflowAction(...)` path instead accepts `actionSequence` directly from the command, and the base schema exposes no unique `(instance_id, action_sequence)` constraint.

Therefore sequence correctness is not protected consistently across all write paths.

The production correction must make sequence allocation Workflow-owned and race-safe for every action creation path and enforce deterministic per-instance sequence integrity at the appropriate application/database layers.

### 108.8 Append-only semantics

The DDD states:

```text
Actions are append-only and must not be physically deleted.
```

The current application port exposes `save`, `findById`, and `nextSequence`; no action delete operation is exposed. Current creation paths generate new action identifiers.

HMSR-095 therefore does not claim an observed delete API defect. However, any future mutation capability must preserve append-only semantics; existing action evidence must not be overwritten as a state-update mechanism.

### 108.9 Action and decision enums

`WorkflowActionType` and `WorkflowDecision` represent stable workflow execution semantics explicitly enumerated by the DDD.

Current evidence supports retaining them as technical/domain enums rather than converting them to generic business catalogs.

`reasonId` is described as a catalog reference, but the DDD does not establish an exact Workflow reason catalog family in the reviewed evidence. HMSR-095 therefore does not invent a catalog-family name. HMS-006/future Workflow reconciliation must resolve that reference only when authoritative catalog ownership is established.

### 108.10 Required revisions

Six evidence-backed production obligations remain:

1. **Close the generic decision-action bypass.** Decision-bearing actions must pass through the configured-transition authority; a generic action endpoint must not independently persist workflow decisions.
2. **Validate supplied `taskId` fail-closed and verify task-to-instance ownership** for task-scoped actions.
3. **Enforce DDD conditional evidence rules on every authoritative write path:** required reasons for reject/correction/return/delegation/escalation/cancel and required comment for request-correction.
4. **Enforce required `actorDisplayNameSnapshot` before persistence** so actor evidence fails fast rather than at JPA/database time.
5. **Make `actionSequence` Workflow-owned, monotonic, race-safe, and protected per instance**, rather than caller-controlled on the generic path.
6. **Preserve Identity/Organization boundary ownership:** scalar actor/organization references and snapshots only, with owner-controlled validation where required; never introduce cross-module database FKs.

HMSR-095 does not modify production Java, JPA, Flyway, API/application contracts, tests, Workflow/Identity/Organization/Audit data, or provisioned data.

### 108.11 Review conclusion

**REVISE.** `WorkflowAction` has the correct bounded-context ownership, mandatory instance protection, append-only intent, and a strong transition-authoritative execution path.

The target baseline cannot mark it APPROVED while the generic recording path can bypass configured-transition enforcement and conditional decision evidence rules, task-scoped integrity is not fail-closed there, the required actor display-name snapshot is not domain-enforced, and action sequence ownership is inconsistent and caller-controlled outside the transition path.

HMS reconciliation must retain these obligations until explicitly authorized Workflow production corrections resolve them or stronger repository evidence changes the governing Workflow contract.

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

- `b52464de87bdbd3e6b2d43102f11c9d43722f71e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:147](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L147)
- [docs/roadmap/model-semantic-review.md:290](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L290)
- [docs/roadmap/model-semantic-review.md:16982](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L16982)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:290 | HMSR-095 | 5 | workflow | WorkflowAction | — | organization.OrganizationUnit, workflow.WorkflowInstance, workflow.WorkflowTask | 2 | 4 | 1 | REVISE | Completed | docs(model-review): review workflow WorkflowAction
doc/domain/SEMANTIC_DECISIONS.md:147 | HMSR-095 | [workflow.WorkflowAction](#workflow-workflowaction) | REVISE | HMR-081
~~~~~~~~

<a id="hapi-426"></a>
## HAPI-426 — review workflow WorkflowStateHistory

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HMSR-116

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review workflow WorkflowStateHistory

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStateHistoryRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 129. HMSR-116 — workflow.WorkflowStateHistory review

**Decision:** REVISE  
**Review code:** HMSR-116  
**Dependency level:** 6  
**Bounded context:** workflow  
**Confirmed upstream subject dependencies:** mandatory `workflow.WorkflowInstance`; optional same-module `workflow.WorkflowTask`, `workflow.WorkflowStep` (`fromStepId` / `toStepId`), and `workflow.WorkflowAction`; optional Workflow reason catalog reference plus Identity actor snapshot context  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 1

### 129.1 Semantic role

`WorkflowStateHistory` is Workflow's append-only state-transition evidence for workflow instances and, when applicable, tasks and steps.

The Workflow DDD requires every lifecycle transition to produce state history, requires every history row to preserve actor snapshot data, and requires the history to be sufficient to reconstruct the workflow timeline.

### 129.2 Field and persistence alignment

The live domain record, JPA entity, persistence mapper, state-history repository, transition execution path, and base Workflow migration align on the 15-field target shape.

The DDD's original 12 implemented fields are present together with the three documented target additions: `actionId`, `reasonId`, and `correlationId`.

The schema correctly marks `instanceId`, `toStatus`, `actorId`, `actorDisplayNameSnapshot`, and `changedAt` non-null.

### 129.3 Mandatory WorkflowInstance integrity

`instanceId` is the mandatory same-module parent reference.

HRA-111 correctly installs:

```text
hidra_workflow_state_history.instance_id
  -> hidra_workflow_instance.id
```

The authoritative transition service loads and locks the `WorkflowInstance` before advancing it, then writes state history in the same transactional path.

### 129.4 Required state and actor snapshot gaps

The DDD explicitly marks `toStatus` and `actorDisplayNameSnapshot` as required. The database also marks both columns `NOT NULL`.

The current `WorkflowStateHistory` constructor validates `id`, `instanceId`, `actorId`, and `changedAt`, but it only normalizes `toStatus` and `actorDisplayNameSnapshot`; blank/null values are not rejected before persistence.

Future production reconciliation must fail closed on blank/null `toStatus` and `actorDisplayNameSnapshot` before the history row reaches persistence.

### 129.5 Authoritative transition path

`WorkflowTransitionApplicationService.execute(...)` is a strong state-changing path. It:

- locks and validates the task and instance;
- validates the configured transition;
- records the corresponding `WorkflowAction`;
- updates the task and instance;
- creates the next task when the workflow remains non-terminal;
- writes `WorkflowStateHistory` with the old/new instance status, source/target steps, actor snapshot, action ID, reason ID, correlation ID, and a shared transition timestamp;
- executes under `@Transactional`.

This path therefore satisfies the DDD requirement that state-advancing transitions produce reconstructable history.

HMSR-116 does not classify initial workflow-instance creation as a lifecycle transition requiring a state-history row because the current DDD does not explicitly define creation as such.

### 129.6 Optional same-module references

`taskId`, `fromStepId`, `toStepId`, and `actionId` are optional same-module references. Their optionality is semantically valid because some state-history events may be instance-scoped rather than task-scoped.

The HRA-111 migration only installs the mandatory `instanceId` FK. The authoritative transition path nevertheless obtains the task, source step, target step, and action from validated live Workflow objects before writing history.

Future non-transition producers of `WorkflowStateHistory`, if introduced, must preserve the same fail-closed semantics for any supplied same-module IDs and must not allow a task/action/step from another instance or workflow definition to be attached to the history row.

HMSR-116 does not invent mandatory FKs for nullable references or require every history row to carry task/action/step IDs.

### 129.7 Reason semantics

`reasonId` is an optional Workflow-owned reason catalog reference. The DDD intentionally makes it optional because not every transition requires a reason.

When supplied, it must remain Workflow-owned and be validated according to Workflow reason catalog policy. The current model does not justify making `reasonId` universally mandatory.

### 129.8 Append-only persistence gap

The DDD is explicit that state history is append-only.

`WorkflowStateHistoryRepositoryPort` currently exposes generic `save(WorkflowStateHistory)`, and `JpaWorkflowStateHistoryRepositoryAdapter` delegates directly to Spring Data `repository.save(...)`.

That contract can insert a new row but can also overwrite an existing row with the same identifier. No repository-level insert-only guard was found.

Future production reconciliation must make WorkflowStateHistory persistence genuinely append-only so historical state evidence cannot be silently mutated by saving an existing ID.

### 129.9 Actor boundary

`actorId` is an Identity-owned scalar reference while username/display-name/role fields are historical snapshots captured by Workflow.

No Workflow-to-Identity database FK is authorized. History must remain readable even after identity lifecycle changes.

The DDD specifically requires actor snapshot preservation; therefore the required display-name snapshot must be protected at the Workflow domain/application boundary rather than reconstructed later from Identity.

### 129.10 Required revisions

Three evidence-backed production obligations remain:

1. **Fail closed on blank/null `toStatus` and `actorDisplayNameSnapshot` before persistence.**
2. **Make WorkflowStateHistory persistence truly append-only so an existing history row cannot be overwritten or mutated through the generic save contract.**
3. **For any future history producer outside the authoritative transition service, validate supplied optional task/step/action/reason references through Workflow-owned contracts and preserve instance/definition coherence without making optional references universally mandatory.**

HMSR-116 does not modify production Java, JPA, Flyway, API/application contracts, tests, Workflow/Identity data, or provisioned data.

### 129.11 Review conclusion

**REVISE.** The 15-field history shape, mandatory WorkflowInstance FK, transition-transaction integration, action/reason/correlation additions, and actor-snapshot architecture are appropriate.

The target baseline cannot be marked APPROVED while required `toStatus` and actor display-name snapshot values are not fail-fast protected and the persistence contract does not enforce the DDD's append-only history rule.

HMS reconciliation must retain these obligations until explicitly authorized Workflow production work resolves them or stronger repository evidence changes the governing Workflow contract.

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

- `df7e104381b39ae982af8ad4ebdd58a77b7a9d17` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:168](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L168)
- [docs/roadmap/model-semantic-review.md:311](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L311)
- [docs/roadmap/model-semantic-review.md:19539](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L19539)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:311 | HMSR-116 | 6 | workflow | WorkflowStateHistory | — | workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowStep, workflow.WorkflowTask | 0 | 0 | 1 | REVISE | Completed | docs(model-review): review workflow WorkflowStateHistory
doc/domain/SEMANTIC_DECISIONS.md:168 | HMSR-116 | [workflow.WorkflowStateHistory](#workflow-workflowstatehistory) | REVISE | HMR-099
~~~~~~~~

<a id="hapi-524"></a>
## HAPI-524 — record operator decisions and applied actions

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HPR-P25-012

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-523

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Record authenticated human approval, rejection/modification and the action actually applied with time/scope/context; preserve advisory boundaries and immutable links to the recommendation and runs.

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowQueryController.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RecordWorkflowActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/RecordWorkflowActionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTargetTransitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/HseWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/IntegrityAssessmentWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/MaintenanceWorkOrderWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Reuse these contracts for engineering publication and accountable operational action; qualify rollback and revocation across owners. The exact bounded remaining delivery is: Record authenticated human approval, rejection/modification and the action actually applied with time/scope/context; preserve advisory boundaries and immutable links to the recommendation and runs..

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTargetTransitionUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/HseWorkflowReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/IntegrityAssessmentWorkflowReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/MaintenanceWorkOrderWorkflowReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowQueryController.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RecordWorkflowActionRequest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/RecordWorkflowActionCommand.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTaskUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ExecuteWorkflowTargetTransitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RecordWorkflowActionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowStepAssignmentRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/HseWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/IntegrityAssessmentWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/MaintenanceWorkOrderWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java`

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

Canonical prerequisites: HAPI-523. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13664](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13664)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6103](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6103)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:863](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L863)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15404](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15404)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:863 | HPR-P25-012 | PENDING | Workflow / Audit / Simulation | Code/Test | Record authenticated human approval, rejection/modification and the action actually applied with time/scope/context; preserve advisory boundaries and immutable links to the recommendation and runs. | feat(workflow): record operator decisions and applied actions | HPR-P25-011
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15404 | HPR-P25-012 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Workflow / Audit / Simulation | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6103 | HPR-P25-012 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13664 | HPR-P25-012 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Workflow / Audit / Simulation | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
~~~~~~~~

<a id="hapi-657"></a>
## HAPI-657 — expose planning workflow contract

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: HRA-090

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Export Workflow contracts consumed by Planning; stop Planning importing Workflow's internal application package.

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/PlanningWorkflowContractAdapter.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/PlanningWorkflowContractAdapter.java`, `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationServiceTest.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/PlanningWorkflowContractAdapter.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanningApprovalApplicationServiceTest.java`

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

- `b96ad61708d689e19d20c94d9fb57e65d5bd7fed` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/repository-remediation.md:335](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/repository-remediation.md#L335)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/repository-remediation.md:335 | HRA-090 | refactor(workflow): expose planning workflow contract | Export Workflow contracts consumed by Planning; stop Planning importing Workflow's internal application package. | Planning depends only on deliberate Workflow contract packages; ArchUnit enforces it. | HRA-002 | **Completed** — introduced the narrow `workflow.application.contract.planning.PlanningWorkflowContract`, adapted existing Workflow internal query/transition use cases behind it, migrated Planning approval orchestration and tests to the exported contract, removed the Planning→Workflow transitional allowlist, and taught ArchUnit to allow only the deliberate contract package.
~~~~~~~~

<a id="hapi-896"></a>
## HAPI-896 — docs(workflow): prepare workflow module roadmap

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-001

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

docs(workflow): prepare workflow module roadmap

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/workflow.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/workflow.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-001 — docs(workflow): prepare workflow module roadmap

**Scope**

```text
Documentation only. This expanded roadmap is the executable module plan.
```

**Files to create**

```text
docs/roadmap/workflow.md
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:745](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L745)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-897"></a>
## HAPI-897 — chore(workflow): add workflow package skeleton

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-002

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

chore(workflow): add workflow package skeleton

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/api/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/query/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/policy/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/service/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/package-info.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/package-info.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/package-info.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/policy/package-info.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/service/package-info.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/package-info.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/package-info.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/workflow/application/service/package-info.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/package-info.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/package-info.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/package-info.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/package-info.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/api/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/command/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/dto/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/query/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/policy/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/service/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/package-info.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-002 — chore(workflow): add workflow package skeleton

**Scope**

```text
Create only production package-info.java files. No tests, no classes, no behavior.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/package-info.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/package-info.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:794](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L794)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-898"></a>
## HAPI-898 — feat(workflow): add workflow domain value objects

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-003

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow domain value objects

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTargetReference.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowAssignmentStatus.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowAssignmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTargetReference.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-003 — feat(workflow): add workflow domain value objects

**Scope**

```text
Add identifiers, localized names, references, status enums, and decision value objects. No aggregates.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowStepId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTransitionId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowAssignmentId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDelegationId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowEscalationRuleId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCommentId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCatalogId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCatalogTranslationId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCode.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowName.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowLocalizedName.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDescription.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowVersion.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTargetReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActorReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowOrganizationReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTypeReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowReasonReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowPriorityReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTargetTypeReference.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDueDate.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCommentText.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecisionNote.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowCorrelationId.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowAssignmentStatus.java
src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowEscalationStatus.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:870](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L870)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-899"></a>
## HAPI-899 — feat(workflow): add workflow catalog domain models

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-004

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow catalog domain models

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-004 — feat(workflow): add workflow catalog domain models

**Scope**

```text
Add catalog-backed workflow type/reason/priority metadata models.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTypeCatalog.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTypeTranslation.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:953](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L953)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-900"></a>
## HAPI-900 — feat(workflow): add workflow core domain models

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-005

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow core domain models

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-005 — feat(workflow): add workflow core domain models

**Scope**

```text
Add aggregates/entities only. No application services, persistence, or REST.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAssignment.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDelegation.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowEscalationRule.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowComment.java
src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1003](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1003)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-901"></a>
## HAPI-901 — feat(workflow): add workflow domain policies

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-006

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow domain policies

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-006 — feat(workflow): add workflow domain policies

**Scope**

```text
Add policies for definitions, transitions, decisions, assignments, delegation, escalation, and targets.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDefinitionPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowTransitionPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowAssignmentPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDecisionPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDelegationPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowEscalationPolicy.java
src/main/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowTargetPolicy.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1062](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1062)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-902"></a>
## HAPI-902 — feat(workflow): add workflow domain services

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-007

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow domain services

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-007 — feat(workflow): add workflow domain services

**Scope**

```text
Add orchestration domain services that apply policies and produce next state.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDefinitionDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowInstanceDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowTaskDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDecisionDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowAssignmentDomainService.java
src/main/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowEscalationDomainService.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1117](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1117)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-903"></a>
## HAPI-903 — feat(workflow): add workflow application commands and queries

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-008

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow application commands and queries

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-008 — feat(workflow): add workflow application commands and queries

**Scope**

```text
Add command/query records only. No services.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowDefinitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/ActivateWorkflowDefinitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/DeactivateWorkflowDefinitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowStepCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CreateWorkflowTransitionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/StartWorkflowInstanceCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CancelWorkflowInstanceCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/AssignWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/ClaimWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/ApproveWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/RejectWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/RequestWorkflowCorrectionCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/DelegateWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/EscalateWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/command/CommentWorkflowTaskCommand.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowDefinitionQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowDefinitionsQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowInstanceQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowInstancesQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowTimelineQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowTaskQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowTasksQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListMyWorkflowTasksQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/GetWorkflowCatalogTypeQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ListWorkflowCatalogTypesQuery.java
src/main/java/dz/sh/hidra/modules/workflow/application/query/ResolveWorkflowCatalogTypeQuery.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1171](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1171)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-904"></a>
## HAPI-904 — feat(workflow): add workflow application DTOs

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-009

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow application DTOs

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-009 — feat(workflow): add workflow application DTOs

**Scope**

```text
Add application DTOs for definitions, instances, tasks, actions, timeline, catalogs, and references.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowDefinitionDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowStepDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTransitionDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowInstanceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTaskDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActionDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowAssignmentDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowDelegationDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowEscalationDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowCommentDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTimelineDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowCatalogDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowCatalogTranslationDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTypeReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowReasonReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowPriorityReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowTargetReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowActorReferenceDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowLocalizedNameDto.java
src/main/java/dz/sh/hidra/modules/workflow/application/dto/WorkflowPageDto.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1245](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1245)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-905"></a>
## HAPI-905 — feat(workflow): add workflow application ports

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-010

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow application ports

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuditEventPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuditEventPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuditEventPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-010 — feat(workflow): add workflow application ports

**Scope**

```text
Add inbound use-case ports and outbound repository/lookup/audit ports.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ActivateWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/DeactivateWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowDefinitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowDefinitionsUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowStepUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CreateWorkflowTransitionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/StartWorkflowInstanceUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CancelWorkflowInstanceUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowInstanceUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowInstancesUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowTimelineUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowTasksUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListMyWorkflowTasksUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/AssignWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ClaimWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ApproveWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RejectWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/RequestWorkflowCorrectionUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/DelegateWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/EscalateWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/CommentWorkflowTaskUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/GetWorkflowCatalogTypeUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ListWorkflowCatalogTypesUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/in/ResolveWorkflowCatalogTypeUseCase.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowDefinitionRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowInstanceRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTaskRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowCatalogRepositoryPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowTargetLookupPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowActorLookupPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuthorityLookupPort.java
src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowAuditEventPort.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1313](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1313)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-906"></a>
## HAPI-906 — feat(workflow): add workflow application services

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-011

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow application services

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-011 — feat(workflow): add workflow application services

**Scope**

```text
Implement application service use cases using domain services and ports.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowDefinitionApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowInstanceApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTaskApplicationService.java
src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowCatalogApplicationService.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1395](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1395)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-907"></a>
## HAPI-907 — feat(workflow): add workflow infrastructure configuration

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-012

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow infrastructure configuration

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-012 — feat(workflow): add workflow infrastructure configuration

**Scope**

```text
Register workflow beans explicitly. Avoid ambiguous Spring beans with @Qualifier when needed.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/configuration/WorkflowConfiguration.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1447](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1447)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-908"></a>
## HAPI-908 — db(workflow): add workflow catalog and core tables migration

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-013

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

db(workflow): add workflow catalog and core tables migration

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-013 — db(workflow): add workflow catalog and core tables migration

**Scope**

```text
Create Flyway migration. Adjust V023 only if repository migration sequence advanced.
```

**Files to create**

```text
src/main/resources/db/migration/V023__create_workflow_catalog_and_core_tables.sql
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1496](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1496)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-909"></a>
## HAPI-909 — feat(workflow): add workflow persistence entities and repositories

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-014

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow persistence entities and repositories

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowCommentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDelegationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowEscalationRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowAssignmentJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowCommentJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowCommentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDelegationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowEscalationRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-014 — feat(workflow): add workflow persistence entities and repositories

**Scope**

```text
Add JPA entities and Spring Data repositories. No adapters.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTypeCatalogJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTypeTranslationJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowAssignmentJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDelegationJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowEscalationRuleJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowCommentJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTypeCatalogJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTypeTranslationJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowDefinitionJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowInstanceJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowTaskJpaRepository.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowActionJpaRepository.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1545](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1545)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-910"></a>
## HAPI-910 — feat(workflow): add workflow persistence mappers and adapters

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-015

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow persistence mappers and adapters

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-015 — feat(workflow): add workflow persistence mappers and adapters

**Scope**

```text
Add mapper and repository port adapter implementations. Register mapper as bean.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapper.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowCatalogRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowDefinitionRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowInstanceRepositoryAdapter.java
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowTaskRepositoryAdapter.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1612](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1612)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-911"></a>
## HAPI-911 — feat(workflow): add workflow telemetry target lookup adapter

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-016

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow telemetry target lookup adapter

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-016 — feat(workflow): add workflow telemetry target lookup adapter

**Scope**

```text
Validate TELEMETRY_READING target references through telemetry application ports only.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/infrastructure/telemetry/WorkflowTelemetryTargetLookupAdapter.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1665](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1665)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-912"></a>
## HAPI-912 — feat(workflow): add workflow REST request DTOs

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-017

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow REST request DTOs

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-017 — feat(workflow): add workflow REST request DTOs

**Scope**

```text
Add request DTOs with Jakarta validation and OpenAPI schemas.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowLocalizedNameRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowTypeReferenceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowTargetReferenceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/WorkflowActorReferenceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowDefinitionRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowStepRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CreateWorkflowTransitionRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/StartWorkflowInstanceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CancelWorkflowInstanceRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/AssignWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ClaimWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/ApproveWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RejectWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/RequestWorkflowCorrectionRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/DelegateWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/EscalateWorkflowTaskRequest.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/request/CommentWorkflowTaskRequest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1714](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1714)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-913"></a>
## HAPI-913 — feat(workflow): add workflow REST response DTOs

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-018

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow REST response DTOs

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java`
- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-018 — feat(workflow): add workflow REST response DTOs

**Scope**

```text
Add response DTOs with localized labels and neutral references.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowLocalizedNameResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTypeReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowReasonReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowPriorityReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTargetReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActorReferenceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowDefinitionResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowStepResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTransitionResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowInstanceResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTaskResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowActionResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowTimelineResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowCatalogResponse.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/response/WorkflowPageResponse.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1779](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1779)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-914"></a>
## HAPI-914 — feat(workflow): add workflow REST mapper

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-019

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow REST mapper

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/WorkflowRestMapper.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/WorkflowRestMapper.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-019 — feat(workflow): add workflow REST mapper

**Scope**

```text
Map REST requests to commands/queries and application DTOs to responses.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/mapper/WorkflowRestMapper.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1842](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1842)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-915"></a>
## HAPI-915 — feat(workflow): add workflow REST controllers

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-020

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

feat(workflow): add workflow REST controllers

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-020 — feat(workflow): add workflow REST controllers

**Scope**

```text
Thin controllers only. Depend on inbound ports and REST mapper.
```

**Files to create**

```text
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowCatalogController.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowDefinitionController.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowInstanceController.java
src/main/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowTaskController.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1891](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1891)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-916"></a>
## HAPI-916 — test(workflow): add workflow domain tests

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-021

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

test(workflow): add workflow domain tests

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-021 — test(workflow): add workflow domain tests

**Scope**

```text
Test value objects, catalog, aggregates, policies, and domain services.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/domain/support/WorkflowDomainTestFixtures.java
src/test/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowValueObjectTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowCatalogDomainTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinitionDomainTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstanceAndTaskDomainTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/policy/WorkflowDomainPolicyTest.java
src/test/java/dz/sh/hidra/modules/workflow/domain/service/WorkflowDomainServiceTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1943](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1943)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-917"></a>
## HAPI-917 — test(workflow): add workflow application service tests

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-022

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

test(workflow): add workflow application service tests

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-022 — test(workflow): add workflow application service tests

**Scope**

```text
Use in-memory fake ports. No Spring context.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/application/support/WorkflowApplicationServiceTestSupport.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowCatalogApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowDefinitionApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowInstanceApplicationServiceTest.java
src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTaskApplicationServiceTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:1998](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L1998)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-918"></a>
## HAPI-918 — test(workflow): add workflow persistence tests

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-023

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

test(workflow): add workflow persistence tests

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-023 — test(workflow): add workflow persistence tests

**Scope**

```text
Test mapper, repository, and adapters.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/support/WorkflowPersistenceTestFixtures.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/mapper/WorkflowPersistenceMapperTest.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/repository/WorkflowJpaRepositoryTest.java
src/test/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/WorkflowPersistenceAdapterTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:2051](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L2051)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-919"></a>
## HAPI-919 — test(workflow): add workflow REST mapper and controller tests

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-024

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

test(workflow): add workflow REST mapper and controller tests

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-024 — test(workflow): add workflow REST mapper and controller tests

**Scope**

```text
Unit-style REST mapper/controller tests. No persistence.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/api/rest/support/WorkflowRestTestFixtures.java
src/test/java/dz/sh/hidra/modules/workflow/api/rest/mapper/WorkflowRestMapperTest.java
src/test/java/dz/sh/hidra/modules/workflow/api/rest/controller/WorkflowRestControllerTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:2103](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L2103)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-920"></a>
## HAPI-920 — test(workflow): add workflow application boot smoke test

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-025

Priority: P2

Implementation Status: IMPLEMENTED

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

test(workflow): add workflow application boot smoke test

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/HidraApplication.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/HidraApplication.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-025 — test(workflow): add workflow application boot smoke test

**Scope**

```text
Verify real HidraApplication context loads with workflow beans.
```

**Files to create**

```text
src/test/java/dz/sh/hidra/modules/workflow/WorkflowApplicationBootSmokeTest.java
```

**Files to update**

```text
None
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
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

- [docs/roadmap/workflow.md:2154](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L2154)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-921"></a>
## HAPI-921 — docs(workflow): finalize workflow validation checklist

Phase: HPS-015 — Workflow and Approvals

Legacy IDs: WF-026

Priority: P2

Implementation Status: NOT_VERIFIABLE

Planning Status: SUPERSEDED

Owning Module: `workflow` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

docs(workflow): finalize workflow validation checklist

### 2. Existing Implementation

Inbox/detail/timeline, available-actions/transitions and exported approval contracts exist with lifecycle guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/workflow.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

This old execution plan is inactive; preserve its historical outcome and current replacement. Do not repeat foundation or remedial work solely to satisfy stale filenames.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Coordinate authorized human tasks, actions, transitions and owner-governed approval.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `workflow`; primary phase owns this obligation. Workflow records govern decisions; physical execution and numerical qualification belong to their owners. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/workflow.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
### WF-026 — docs(workflow): finalize workflow validation checklist

**Scope**

```text
Finalize checklist and mark roadmap status.
```

**Files to create**

```text
docs/roadmap/workflow_validation_checklist.md
docs/roadmap/workflow.md
```

**Files to update**

```text
docs/roadmap/workflow.md
```

**Files to delete**

```text
None
```

**Must not do**

```text
Do not execute later workflow tasks.
Do not add unrelated modules.
Do not create shared/common/core/utils/helper/misc packages.
Do not introduce old names.
Do not import forbidden cross-module domain or infrastructure packages.
Do not create business taxonomy enums.
```

**Acceptance criteria**

```text
All listed files exist.
All files use package root dz.sh.hidra.
All production Java files use canonical HidraAPI header.
All user-facing labels are localized or locale-aware.
Business classifications are catalog-backed.
No raw build logs are added to docs.
mvn -q -DskipTests compile passes after this task when production code is involved.
```


---

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

- [docs/roadmap/workflow.md:2203](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/workflow.md#L2203)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical foundation execution plan is absorbed by the current implementation, semantic-remediation and canonical regression gates; no duplicate foundation rebuild is authorized. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<!-- task-register:payload:end -->
