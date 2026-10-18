# HPS-021 — Audit and Traceability: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-021 — Audit and Traceability

<a id="hapi-150"></a>
## HAPI-150 — scan audit domain models

Phase: HPS-021 — Audit and Traceability

Legacy IDs: HMC-005

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `audit` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 4 audit model files.

### 2. Existing Implementation

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
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

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve accountable actor, source, revision, approval and export evidence across operations.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `audit`; primary phase owns this obligation. Do not conflate a stored audit export request with a generated external artifact or successful delivery. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
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

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 14. HMC-005 — Audit module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `audit`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 83  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Audit model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| AuditAccessRecord | record | 11 | `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java` | Extracted |
| AuditBeforeAfterValue | record | 13 | `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java` | Extracted |
| AuditEvent | record | 45 | `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java` | Extracted |
| AuditExportRequest | record | 14 | `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java` | Extracted |

### Audit fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| AuditAccessRecord | 1 | id | String |
| AuditAccessRecord | 2 | actorId | String |
| AuditAccessRecord | 3 | actorDisplayNameSnapshot | String |
| AuditAccessRecord | 4 | accessType | AuditAccessType |
| AuditAccessRecord | 5 | auditEventId | String |
| AuditAccessRecord | 6 | searchFilterHash | String |
| AuditAccessRecord | 7 | exportRequestId | String |
| AuditAccessRecord | 8 | resultCount | Integer |
| AuditAccessRecord | 9 | purposeText | String |
| AuditAccessRecord | 10 | accessedAt | Instant |
| AuditAccessRecord | 11 | correlationId | String |
| AuditBeforeAfterValue | 1 | id | String |
| AuditBeforeAfterValue | 2 | auditEventId | String |
| AuditBeforeAfterValue | 3 | fieldPath | String |
| AuditBeforeAfterValue | 4 | fieldLabelSnapshot | String |
| AuditBeforeAfterValue | 5 | valueType | AuditValueType |
| AuditBeforeAfterValue | 6 | beforeValueText | String |
| AuditBeforeAfterValue | 7 | afterValueText | String |
| AuditBeforeAfterValue | 8 | beforeValueHash | String |
| AuditBeforeAfterValue | 9 | afterValueHash | String |
| AuditBeforeAfterValue | 10 | masked | boolean |
| AuditBeforeAfterValue | 11 | maskReasonId | String |
| AuditBeforeAfterValue | 12 | changed | boolean |
| AuditBeforeAfterValue | 13 | recordedAt | Instant |
| AuditEvent | 1 | id | String |
| AuditEvent | 2 | eventTypeId | String |
| AuditEvent | 3 | eventCategoryId | String |
| AuditEvent | 4 | severityId | String |
| AuditEvent | 5 | sourceModule | String |
| AuditEvent | 6 | sourceComponent | String |
| AuditEvent | 7 | sourceEventId | String |
| AuditEvent | 8 | actionCode | String |
| AuditEvent | 9 | actionLabelSnapshot | String |
| AuditEvent | 10 | eventStatus | AuditEventStatus |
| AuditEvent | 11 | actorId | String |
| AuditEvent | 12 | actorType | AuditActorType |
| AuditEvent | 13 | actorDisplayNameSnapshot | String |
| AuditEvent | 14 | actorUsernameSnapshot | String |
| AuditEvent | 15 | actorRoleCodeSnapshot | String |
| AuditEvent | 16 | organizationUnitId | String |
| AuditEvent | 17 | organizationUnitCodeSnapshot | String |
| AuditEvent | 18 | organizationUnitNameSnapshot | String |
| AuditEvent | 19 | targetModule | String |
| AuditEvent | 20 | targetType | String |
| AuditEvent | 21 | targetId | String |
| AuditEvent | 22 | targetCodeSnapshot | String |
| AuditEvent | 23 | targetLabelSnapshot | String |
| AuditEvent | 24 | operation | AuditOperation |
| AuditEvent | 25 | decisionCode | String |
| AuditEvent | 26 | reasonId | String |
| AuditEvent | 27 | reasonText | String |
| AuditEvent | 28 | commentText | String |
| AuditEvent | 29 | workflowInstanceId | String |
| AuditEvent | 30 | workflowTaskId | String |
| AuditEvent | 31 | workflowActionId | String |
| AuditEvent | 32 | workflowFromState | String |
| AuditEvent | 33 | workflowToState | String |
| AuditEvent | 34 | requestId | String |
| AuditEvent | 35 | correlationId | String |
| AuditEvent | 36 | causationId | String |
| AuditEvent | 37 | ipAddressMasked | String |
| AuditEvent | 38 | userAgentSnapshot | String |
| AuditEvent | 39 | sourceSystemCode | String |
| AuditEvent | 40 | occurredAt | Instant |
| AuditEvent | 41 | recordedAt | Instant |
| AuditEvent | 42 | retentionPolicyId | String |
| AuditEvent | 43 | hashValue | String |
| AuditEvent | 44 | previousHashValue | String |
| AuditEvent | 45 | payloadJson | String |
| AuditExportRequest | 1 | id | String |
| AuditExportRequest | 2 | requestedByActorId | String |
| AuditExportRequest | 3 | requestedByDisplayNameSnapshot | String |
| AuditExportRequest | 4 | purposeId | String |
| AuditExportRequest | 5 | filterJson | String |
| AuditExportRequest | 6 | format | String |
| AuditExportRequest | 7 | status | AuditExportStatus |
| AuditExportRequest | 8 | workflowInstanceId | String |
| AuditExportRequest | 9 | resultDocumentReferenceId | String |
| AuditExportRequest | 10 | recordCount | Integer |
| AuditExportRequest | 11 | checksum | String |
| AuditExportRequest | 12 | requestedAt | Instant |
| AuditExportRequest | 13 | completedAt | Instant |
| AuditExportRequest | 14 | expiresAt | Instant |

### HMC-005 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 83
zero-field models    = 0
```

The scan records only declarations under `audit/domain/model`. Audit domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, or database column metadata was mixed into this module scan.

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

- `6318ed2e37a86ea09687eb208b6b00fed780e585` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:133](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L133)
- [docs/roadmap/model-field-catalogue.md:615](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L615)
- [docs/roadmap/model-field-catalogue.md:722](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L722)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:133 | HMC-005 | docs(catalogue): scan audit domain models | Scan all 4 audit model files. | **Completed** — 4/4 models, 83 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-277"></a>
## HAPI-277 — remediate semantic review AuditExportRequest

Phase: HPS-021 — Audit and Traceability

Legacy IDs: HMR-083

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `audit` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AuditExportRequest

### 2. Existing Implementation

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RequestAuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditExportRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditExportRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditExportRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/AuditOwnerReferenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditExportRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/audit/AuditDocumentReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/audit/package-info.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/AuditDocumentReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/audit/AuditWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/audit/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261007_006__hmr_083_audit_export_request.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/AuditDocumentReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `audit`; primary phase owns this obligation. Do not conflate a stored audit export request with a generated external artifact or successful delivery. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditCatalogEligibilityPort.java`, `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditDocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditExportRequestRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditWorkflowReferencePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`, `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`, `src/main/java/dz/sh/hidra/modules/documents/application/service/AuditDocumentReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/AuditOwnerReferenceAdapter.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditCatalogEligibilityAdapter.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RequestAuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditExportRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditExportRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditExportRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/AuditOwnerReferenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditExportRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/audit/AuditDocumentReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/audit/package-info.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/AuditDocumentReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/audit/AuditWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/audit/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261007_006__hmr_083_audit_export_request.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/AuditDocumentReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-083 — audit.AuditExportRequest

- Source review: `HMSR-097`
- Exact commit: `fix(audit): remediate semantic review AuditExportRequest`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: documents.Document, workflow.WorkflowInstance
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261007_006__hmr_083_audit_export_request.sql` (accepted canonical Batch 10 admission)
- Owner-contract prerequisite: Existing candidate owner contract(s): documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Audit.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RequestAuditExportRequest.java`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditExportRequestResponse.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditExportRequestSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditExportRequestRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditExportRequestJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_083__hmr_083_audit_audit_export_request.sql`
  - `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuditExportRequestSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce required `filterJson` and `format` before persistence**, not only through DB `NOT NULL`.
  2. **Sanitize and policy-check `filterJson` before persistence/export execution**, preserving the DDD masking rule for sensitive values.
  3. **Validate `purposeId` against the active/eligible exact Audit `EXPORT_PURPOSE` catalog family** and add appropriate same-module integrity protection consistent with repository architecture.
  4. **Preserve Workflow/Documents ownership:** validate optional workflow approval and result-document references through owner-controlled contracts where required; never introduce cross-module database FKs.
  5. **Make audit export activity itself auditable**, including request/access/export execution evidence through Audit-owned application paths while avoiding recursive evidence creation.

- Batch 10 implementation: Required export metadata, active EXPORT_PURPOSE, owner-controlled optional Workflow/Documents references, bounded sanitized filters and one transactional EXPORT access record implemented. Generic writes admit REQUESTED only and persist/flush without merge. Forward V20261007_006; eight focused tests, two owner tests and four PostgreSQL/Spring/JPA tests prepared. Local Maven compile/focused blocked before compilation by uncached Boot 4.1.1 parent; production CI pending.

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
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java` — exact-head oracle suite 17 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/application/service/AuditDocumentReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `4fc2787aaaa428f3475994ce590e96ab738b317f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:118](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L118)
- [doc/model-remediation/RECONCILIATION.md:13730](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13730)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:3309](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L3309)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15470](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15470)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:117](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L117)
- [docs/roadmap/model-semantic-remediation.md:254](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L254)
- [docs/roadmap/model-semantic-remediation.md:4383](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4383)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:254 | HMR-083 | HMSR-097 | audit | AuditExportRequest | — | documents.Document, workflow.WorkflowInstance | fix(audit): remediate semantic review AuditExportRequest | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:118 | HMR-083 | HMSR-097 | audit.AuditExportRequest | COMPLETED — CI #584 GREEN | Required export metadata, active EXPORT_PURPOSE, owner-controlled optional Workflow/Documents references, bounded sanitized filters and one transactional EXPORT access record implemented. Generic writes admit REQUESTED only and persist/flush without merge. Forward V20261007_006; eight focused tests, two owner tests and four PostgreSQL/Spring/JPA tests prepared. Local Maven compile/focused blocked before compilation by uncached Boot 4.1.1 parent; production CI #584 passed.
doc/model-remediation/RECONCILIATION.md:13730 | HMR-083 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:117 | HMR-083 | HMSR-097 | [audit.AuditExportRequest](../domain/SEMANTIC_DECISIONS.md#audit-auditexportrequest) | [AuditExportRequestSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java) | [V20261007_006__hmr_083_audit_export_request](../../src/main/resources/db/migration/V20261007_006__hmr_083_audit_export_request.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15470 | HMR-083 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-289"></a>
## HAPI-289 — remediate semantic review AuditEvent

Phase: HPS-021 — Audit and Traceability

Legacy IDs: HMR-095

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `audit` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AuditEvent

### 2. Existing Implementation

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RecordAuditEventRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditEventResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/command/RecordAuditEventCommand.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditEventSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditEventRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditEventJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/resources/db/migration/V20261007_007__hmr_095_audit_event.sql`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `audit`; primary phase owns this obligation. Do not conflate a stored audit export request with a generated external artifact or successful delivery. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditCatalogEligibilityPort.java`, `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditEventRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`, `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditCatalogEligibilityAdapter.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RecordAuditEventRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditEventResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/command/RecordAuditEventCommand.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditEventSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditEventRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditEventJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/resources/db/migration/V20261007_007__hmr_095_audit_event.sql`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-095 — audit.AuditEvent

- Source review: `HMSR-112`
- Exact commit: `fix(audit): remediate semantic review AuditEvent`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit, workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowTask
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261007_007__hmr_095_audit_event.sql` (accepted canonical Batch 10 admission)
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Audit.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RecordAuditEventRequest.java`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditEventResponse.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/command/RecordAuditEventCommand.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditEventSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditEventRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditEventJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuditEventSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Fail closed on blank/null `sourceModule`, `targetModule`, and `targetType` before persistence.**
  2. **Validate mandatory `eventTypeId` / `eventCategoryId` and supplied optional `severityId` / `reasonId` against their correct Audit catalog families, not merely any Audit catalog row.**
  3. **Make AuditEvent persistence genuinely append-only so an existing event cannot be silently overwritten or mutated outside explicitly governed evidence-preserving lifecycle behavior.**
  4. **Sanitize/redact audit free-text/structured payload inputs and enforce an evidence-backed size limit for `payloadJson` before persistence; secrets and credential material must never enter the ledger.**

- Batch 10 implementation: Required source/target module and target type, active exact event/category/optional severity/reason families, bounded sanitized payload/free text and persist/flush insertion enforced. Forward V20261007_007 adds optional catalog FKs, family guards and immutable event UPDATE/DELETE denial. Five focused and four added PostgreSQL/JPA/concurrency checks prepared; local focused Maven blocked by uncached Boot parent; CI pending.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java` — exact-head oracle suite 17 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `d7332748a5a4c885be1fb8c90e0991b83b64b5a5` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:130](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L130)
- [doc/model-remediation/RECONCILIATION.md:13742](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13742)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:3349](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L3349)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15482](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15482)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:129](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L129)
- [docs/roadmap/model-semantic-remediation.md:271](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L271)
- [docs/roadmap/model-semantic-remediation.md:4780](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4780)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:271 | HMR-095 | HMSR-112 | audit | AuditEvent | — | organization.OrganizationUnit, workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowTask | fix(audit): remediate semantic review AuditEvent | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:130 | HMR-095 | HMSR-112 | audit.AuditEvent | COMPLETED — CI #584 GREEN | Required source/target module and target type, active exact event/category/optional severity/reason families, bounded sanitized payload/free text and persist/flush insertion enforced. Forward V20261007_007 adds optional catalog FKs, family guards and immutable event UPDATE/DELETE denial. Five focused and four added PostgreSQL/JPA/concurrency checks prepared; local focused Maven blocked by uncached Boot parent; CI #584 passed.
doc/model-remediation/RECONCILIATION.md:13742 | HMR-095 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:129 | HMR-095 | HMSR-112 | [audit.AuditEvent](../domain/SEMANTIC_DECISIONS.md#audit-auditevent) | [AuditEventSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java) | [V20261007_007__hmr_095_audit_event](../../src/main/resources/db/migration/V20261007_007__hmr_095_audit_event.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15482 | HMR-095 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-295"></a>
## HAPI-295 — remediate semantic review AuditAccessRecord

Phase: HPS-021 — Audit and Traceability

Legacy IDs: HMR-101

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `audit` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AuditAccessRecord

### 2. Existing Implementation

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditAccessRecordResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditAccessRecordSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditAccessRecordJpaRepository.java`
- `src/main/resources/db/migration/V20261007_008__hmr_101_audit_access_record.sql`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `audit`; primary phase owns this obligation. Do not conflate a stored audit export request with a generated external artifact or successful delivery. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditAccessRecordJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditAccessRecordResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditAccessRecordSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditAccessRecordJpaRepository.java`
- `src/main/resources/db/migration/V20261007_008__hmr_101_audit_access_record.sql`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-101 — audit.AuditAccessRecord

- Source review: `HMSR-118`
- Exact commit: `fix(audit): remediate semantic review AuditAccessRecord`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: audit.AuditEvent, audit.AuditExportRequest
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261007_008__hmr_101_audit_access_record.sql` (accepted canonical Batch 10 admission)
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Audit.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditAccessRecordResponse.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditAccessRecordSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditAccessRecordJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuditAccessRecordSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Make AuditAccessRecord persistence genuinely append-only so an existing record ID cannot be overwritten through generic save semantics.**
  2. **Fail closed on a supplied unknown `auditEventId` while preserving its documented optionality.**
  3. **Fail closed on a supplied unknown `exportRequestId` while preserving its documented optionality.**

- Batch 10 implementation: Access records use persist/flush without merge; populated optional AuditEvent/export IDs must exist. Forward V20261007_008 supplies nullable local FKs and UPDATE/DELETE denial. Three focused and four added PostgreSQL/JPA/concurrency/orphan checks prepared; local focused Maven blocked by uncached Boot parent; CI pending.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java` — exact-head oracle suite 17 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `36a0992586f5337a18798b0b7f54815662fe0928` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:136](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L136)
- [doc/model-remediation/RECONCILIATION.md:13748](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13748)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:3379](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L3379)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15488](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15488)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:135](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L135)
- [docs/roadmap/model-semantic-remediation.md:282](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L282)
- [docs/roadmap/model-semantic-remediation.md:5089](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L5089)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:282 | HMR-101 | HMSR-118 | audit | AuditAccessRecord | — | audit.AuditEvent, audit.AuditExportRequest | fix(audit): remediate semantic review AuditAccessRecord | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:136 | HMR-101 | HMSR-118 | audit.AuditAccessRecord | COMPLETED — CI #584 GREEN | Access records use persist/flush without merge; populated optional AuditEvent/export IDs must exist. Forward V20261007_008 supplies nullable local FKs and UPDATE/DELETE denial. Three focused and four added PostgreSQL/JPA/concurrency/orphan checks prepared; local focused Maven blocked by uncached Boot parent; CI #584 passed.
doc/model-remediation/RECONCILIATION.md:13748 | HMR-101 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:135 | HMR-101 | HMSR-118 | [audit.AuditAccessRecord](../domain/SEMANTIC_DECISIONS.md#audit-auditaccessrecord) | [AuditAccessRecordSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java) | [V20261007_008__hmr_101_audit_access_record](../../src/main/resources/db/migration/V20261007_008__hmr_101_audit_access_record.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15488 | HMR-101 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-296"></a>
## HAPI-296 — remediate semantic review AuditBeforeAfterValue

Phase: HPS-021 — Audit and Traceability

Legacy IDs: HMR-102

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `audit` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AuditBeforeAfterValue

### 2. Existing Implementation

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditBeforeAfterValueJpaRepository.java`
- `src/main/resources/db/migration/V20261007_009__hmr_102_audit_before_after_value.sql`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `audit`; primary phase owns this obligation. Do not conflate a stored audit export request with a generated external artifact or successful delivery. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditBeforeAfterValueJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditBeforeAfterValueJpaRepository.java`
- `src/main/resources/db/migration/V20261007_009__hmr_102_audit_before_after_value.sql`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-102 — audit.AuditBeforeAfterValue

- Source review: `HMSR-119`
- Exact commit: `fix(audit): remediate semantic review AuditBeforeAfterValue`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: audit.AuditEvent
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261007_009__hmr_102_audit_before_after_value.sql` (accepted canonical Batch 10 admission)
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Audit.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditBeforeAfterValueJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AuditBeforeAfterValueSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Fail closed on blank/null `fieldPath` before persistence.**
  2. **Enforce Audit sensitive-data policy:** masked/sensitive before/after evidence must not retain raw text; use hashes or masked/reference-only representations as appropriate.
  3. **Validate supplied `maskReasonId` against the Audit `MASK_REASON` catalog family while preserving optionality.**
  4. **Make AuditBeforeAfterValue persistence genuinely append-only so an existing evidence row cannot be overwritten through generic save semantics.**

- Batch 10 implementation: Required fieldPath, masked/sensitive raw-text exclusion, optional exact active MASK_REASON and existing parent event enforced. Hash-only evidence and changed=false remain legal. Persist/flush insertion plus V20261007_009 local FKs/checks/UPDATE/DELETE denial preserve immutable rows. Six focused and five added PostgreSQL/JPA/concurrency/legacy checks prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI pending.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java` — exact-head oracle suite 17 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.

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

- `07910b6abcda4fd2b84f455c3266c76c1d727e0f` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:137](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L137)
- [doc/model-remediation/RECONCILIATION.md:13749](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13749)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:3400](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L3400)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15489](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15489)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:136](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L136)
- [docs/roadmap/model-semantic-remediation.md:283](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L283)
- [docs/roadmap/model-semantic-remediation.md:5123](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L5123)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:283 | HMR-102 | HMSR-119 | audit | AuditBeforeAfterValue | — | audit.AuditEvent | fix(audit): remediate semantic review AuditBeforeAfterValue | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:137 | HMR-102 | HMSR-119 | audit.AuditBeforeAfterValue | COMPLETED — CI #584 GREEN | Required fieldPath, masked/sensitive raw-text exclusion, optional exact active MASK_REASON and existing parent event enforced. Hash-only evidence and changed=false remain legal. Persist/flush insertion plus V20261007_009 local FKs/checks/UPDATE/DELETE denial preserve immutable rows. Six focused and five added PostgreSQL/JPA/concurrency/legacy checks prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI #584 passed.
doc/model-remediation/RECONCILIATION.md:13749 | HMR-102 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:136 | HMR-102 | HMSR-119 | [audit.AuditBeforeAfterValue](../domain/SEMANTIC_DECISIONS.md#audit-auditbeforeaftervalue) | [AuditBeforeAfterValueSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java) | [V20261007_009__hmr_102_audit_before_after_value](../../src/main/resources/db/migration/V20261007_009__hmr_102_audit_before_after_value.sql) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15489 | HMR-102 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-407"></a>
## HAPI-407 — review audit AuditExportRequest

Phase: HPS-021 — Audit and Traceability

Legacy IDs: HMSR-097

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `audit` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review audit AuditExportRequest

### 2. Existing Implementation

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditExportStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve accountable actor, source, revision, approval and export evidence across operations.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `audit`; primary phase owns this obligation. Do not conflate a stored audit export request with a generated external artifact or successful delivery. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditExportStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditExportStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 110. HMSR-097 — audit.AuditExportRequest review

**Decision:** REVISE  
**Review code:** HMSR-097  
**Dependency level:** 5  
**Bounded context:** audit  
**Confirmed upstream subject dependencies:** optional Documents-owned export artifact reference; optional Workflow-owned approval instance reference  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 1  
**Unresolved/non-subject reference count:** 2

### 110.1 Semantic role

`AuditExportRequest` is Audit-owned controlled export-request state for auditors/compliance users.

It records who requested an export, why, the sanitized filter, output format, lifecycle status, optional approval-workflow reference, optional resulting Documents artifact reference, result metadata, request/completion timing, and expiry.

Audit remains the evidence owner. Workflow owns approval routing/decision semantics and Documents owns the generated artifact.

### 110.2 Required request content

The Audit DDD explicitly marks these request fields required:

```text
requestedByActorId
purposeId
filterJson
format
status
requestedAt
```

The live domain constructor already rejects missing `requestedByActorId`, `purposeId`, `status`, and `requestedAt`.

However, `filterJson` and `format` are only normalized. JPA/Flyway persist both as `NOT NULL`, so null/blank values can currently reach persistence and fail late.

The authoritative request boundary must therefore fail fast for both required values.

### 110.3 Filter sanitation

The DDD states:

```text
filterJson = export filter criteria. Must be sanitized.
Sensitive values remain masked in exports unless explicitly authorized by policy.
```

The current `requestAuditExport(...)` path passes `command.filterJson()` directly into the model and persists it. Repository evidence reviewed for HMSR-097 does not show a sanitizer/policy gate on this path.

HMSR-097 does not invent a specific sanitization algorithm, but the future implementation must make filter sanitation/policy enforcement explicit at the authoritative application boundary before persistence/export execution.

### 110.4 Export-purpose catalog semantics

`purposeId` is mandatory and typed in the DDD as an `AuditCatalogEntryId`.

The Audit catalog definition explicitly includes the exact family:

```text
EXPORT_PURPOSE
```

with examples such as `INTERNAL_AUDIT`, `INCIDENT_REVIEW`, and `COMPLIANCE`.

The live export-request table contains only an index on `purpose_id`; the reviewed evidence does not show a same-module FK or application-level family/active validation for this field.

The authoritative request path must therefore validate that `purposeId` resolves to an active/eligible Audit catalog entry in the exact `EXPORT_PURPOSE` family. Same-module persistence protection may be added consistently with repository architecture.

### 110.5 Workflow approval reference

`workflowInstanceId` is optional because not every export necessarily requires approval.

When supplied, it is Workflow-owned. Audit must not import Workflow aggregates or create cross-module database FKs.

If policy requires an approved workflow for a particular export, the application boundary must resolve/verify that through a Workflow-owned contract and bind the request to the proven approval evidence.

HMSR-097 does not invent which export purposes require approval; that remains policy-driven.

### 110.6 Result document ownership

`resultDocumentReferenceId` is explicitly described by the DDD as a Documents-module reference to the export artifact.

It must remain a cross-module scalar reference, with any existence/ownership validation performed through a Documents-owned application/reference contract. No cross-module FK is authorized.

### 110.7 Export request lifecycle

`AuditExportStatus` contains:

```text
REQUESTED
APPROVED
REJECTED
RUNNING
COMPLETED
FAILED
EXPIRED
```

These are technical lifecycle states and are appropriate as an enum on current evidence.

HMSR-097 does not convert this lifecycle into a business taxonomy/catalog.

### 110.8 Audit-the-export rule

The DDD explicitly requires:

```text
Audit export must be audited.
Access to audit evidence is itself auditable.
```

The live `AuditApplicationService.requestAuditExport(...)` only constructs and saves an `AuditExportRequest`; it does not record a corresponding `AuditEvent` or `AuditAccessRecord` in the reviewed method.

This means creation of the export request itself is not demonstrably producing the audit evidence required by the governing Audit DDD.

The future production correction must ensure export request/execution access is itself auditable through Audit-owned application semantics without recursive or duplicate evidence loops.

### 110.9 Completion metadata consistency

`resultDocumentReferenceId`, `recordCount`, `checksum`, `completedAt`, and `expiresAt` are optional because they describe later export-processing outcomes.

HMSR-097 does not invent lifecycle-dependent mandatory combinations beyond current DDD evidence. Future export-completion work should nevertheless preserve internal consistency between lifecycle state and completion/result metadata when that behavior is implemented.

### 110.10 Required revisions

Five evidence-backed production obligations remain:

1. **Enforce required `filterJson` and `format` before persistence**, not only through DB `NOT NULL`.
2. **Sanitize and policy-check `filterJson` before persistence/export execution**, preserving the DDD masking rule for sensitive values.
3. **Validate `purposeId` against the active/eligible exact Audit `EXPORT_PURPOSE` catalog family** and add appropriate same-module integrity protection consistent with repository architecture.
4. **Preserve Workflow/Documents ownership:** validate optional workflow approval and result-document references through owner-controlled contracts where required; never introduce cross-module database FKs.
5. **Make audit export activity itself auditable**, including request/access/export execution evidence through Audit-owned application paths while avoiding recursive evidence creation.

HMSR-097 does not modify production Java, JPA, Flyway, API/application contracts, tests, Audit/Workflow/Documents data, or provisioned data.

### 110.11 Review conclusion

**REVISE.** `AuditExportRequest` has the correct Audit ownership, technical lifecycle enum, and scalar cross-module reference shape.

The target baseline cannot mark it APPROVED while two DDD-required fields are only DB-enforced, export filters are persisted without an explicit sanitation/policy gate, `purposeId` is not proven family-qualified against `EXPORT_PURPOSE`, and the live request path does not itself produce the audit evidence explicitly required for audit exports.

HMS reconciliation must retain these obligations until explicitly authorized Audit production corrections resolve them or stronger repository evidence changes the governing Audit contract.

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

- `26dd43d5259a161cf9ff42ef93d092378a004e68` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:149](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L149)
- [docs/roadmap/model-semantic-review.md:292](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L292)
- [docs/roadmap/model-semantic-review.md:17249](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L17249)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:292 | HMSR-097 | 5 | audit | AuditExportRequest | — | documents.Document, workflow.WorkflowInstance | 1 | 1 | 2 | REVISE | Completed | docs(model-review): review audit AuditExportRequest
doc/domain/SEMANTIC_DECISIONS.md:149 | HMSR-097 | [audit.AuditExportRequest](#audit-auditexportrequest) | REVISE | HMR-083
~~~~~~~~

<a id="hapi-422"></a>
## HAPI-422 — review audit AuditEvent

Phase: HPS-021 — Audit and Traceability

Legacy IDs: HMSR-112

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `audit` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review audit AuditEvent

### 2. Existing Implementation

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve accountable actor, source, revision, approval and export evidence across operations.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `audit`; primary phase owns this obligation. Do not conflate a stored audit export request with a generated external artifact or successful delivery. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`, `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 125. HMSR-112 — audit.AuditEvent review

**Decision:** REVISE  
**Review code:** HMSR-112  
**Dependency level:** 6  
**Bounded context:** audit  
**Confirmed upstream subject dependencies:** Organization snapshot/reference semantics plus Workflow instance/task/action evidence references; target references are polymorphic across business modules  
**Confirmed direct HMS subject dependents:** 2  
**Transitive HMS subject dependents:** 2  
**Unresolved/non-subject reference count:** 3

### 125.1 Semantic role

`AuditEvent` is the canonical append-only evidence record for important actions, decisions, state changes, security events, and traceability context across Hidra.

The Audit DDD is explicit that Audit records evidence after business facts occur. It must not become the owner of business state, a command source, or a substitute for Workflow, Identity, Organization, or the target module.

### 125.2 Field and persistence alignment

The live domain record, JPA entity, persistence mapper, application command/service, and base Audit migration align on the 45-field `AuditEvent` shape.

The persistence layer marks the DDD-required event/category/source/action/status/actor-type/target/operation/timestamps as non-null. The domain already fail-fast validates `id`, `eventTypeId`, `eventCategoryId`, `actionCode`, `eventStatus`, `actorType`, `targetId`, `operation`, `occurredAt`, and `recordedAt`.

`AuditEventStatus` (`RECORDED`, `SEALED`, `REDACTED`, `EXPORT_LOCKED`) and `AuditActorType` (`USER`, `SYSTEM`, `INTEGRATION`, `SCHEDULED_JOB`, `SERVICE_ACCOUNT`) match the DDD lifecycle/type vocabulary and remain appropriate enums.

### 125.3 Required source/target identity fields

The DDD marks `sourceModule`, `targetModule`, and `targetType` as required business evidence, not merely persistence metadata.

The current domain record normalizes those fields but does not reject null/blank values. Consequently the authoritative `recordAuditEvent(...)` path can construct an invalid event that fails only when JPA/database constraints are reached.

Future production reconciliation must fail closed on blank/null `sourceModule`, `targetModule`, and `targetType` before persistence.

### 125.4 Audit catalog-family semantics

`eventTypeId` and `eventCategoryId` are mandatory same-module catalog references. HRA-111 correctly installs row-existence FKs to `hidra_audit_catalog_entry(id)`.

However, row existence alone does not prove semantic family membership. A valid catalog row from another Audit catalog family could satisfy the FK.

Future production reconciliation must validate `eventTypeId` as an Audit event-type entry and `eventCategoryId` as an Audit event-category entry through Audit-owned catalog-family policy.

`severityId` and `reasonId` are optional catalog-backed references. When supplied, they should likewise be validated against the appropriate Audit catalog family; their optionality must be preserved.

### 125.5 Append-only ledger semantics

The governing DDD states that `AuditEvent` is append-only and must never be updated to change business meaning.

The public repository contract currently exposes a generic `save(AuditEvent)` operation. The normal record path generates a fresh Audit ID and only creates new events, and repository search found no explicit AuditEvent update use case.

Even so, the persistence contract itself does not express or enforce insert-only semantics. Future production reconciliation must ensure AuditEvent persistence cannot silently overwrite an existing event ID or mutate previously recorded business evidence. The implementation mechanism may be repository/application/database protection, but HMSR-112 does not prescribe a specific technology without further implementation evidence.

Status changes associated with sealing/redaction/export locking must also preserve the DDD's evidence-ledger semantics rather than becoming unrestricted arbitrary updates.

### 125.6 Sensitive payload and free-text handling

The Audit DDD explicitly requires sensitive values to be masked or excluded and states that `payloadJson` must be sanitized and size-limited.

`AuditApplicationService.recordAuditEvent(...)` currently passes `reasonText` and `payloadJson` directly from the command into the persisted event. No sanitization or payload-size guard was found in the authoritative path.

Future production reconciliation must apply Audit-owned sanitization/redaction policy before persistence, including size limits for `payloadJson` and prevention of credentials/secrets/tokens/private keys or equivalent sensitive material from entering the audit ledger.

HMSR-112 does not invent a concrete byte limit because the current DDD does not specify one.

### 125.7 Actor semantics

`actorId` is deliberately optional: the DDD allows null only for justified technical/system events, while `actorType` is mandatory.

The live model preserves this distinction. HMSR-112 does not make `actorId` universally mandatory.

Identity-related actor fields are evidence snapshots. They must not become cross-module database FKs or require live Identity existence at read time, because audit evidence must remain historically readable after identity lifecycle changes.

### 125.8 Organization reference semantics

`organizationUnitId`, code snapshot, and name snapshot are optional historical evidence fields.

Organization remains owner of the live unit. Audit must preserve the recorded reference/snapshot even if the Organization unit is later renamed, reorganized, archived, or removed.

No Audit-to-Organization database FK should be introduced, and historical audit reads must not depend on a live Organization lookup.

### 125.9 Workflow evidence semantics

`workflowInstanceId`, `workflowTaskId`, and `workflowActionId` are optional references recording workflow evidence. `workflowFromState` and `workflowToState` are historical snapshots.

Workflow owns process lifecycle and decisions; Audit owns the evidence that a workflow action occurred.

These fields must remain historical cross-module references/snapshots rather than database FKs. Audit must not require the Workflow records to remain live in order for old evidence to be valid.

### 125.10 Polymorphic target semantics

`targetModule`, `targetType`, and `targetId` form a polymorphic target reference across Hidra modules.

This is intentionally non-relational at the Audit database boundary. No universal target FK is possible or desirable. The audit record preserves the target identity that existed at event time; target modules retain lifecycle ownership.

### 125.11 Correlation and timing semantics

`occurredAt` and `recordedAt` are both mandatory and already fail-fast validated. The application records `recordedAt` using its current timestamp and defaults a missing command occurrence time to the same current time.

The DDD allows correlation/request/causation context when available; it does not make `correlationId` universally mandatory. HMSR-112 therefore does not invent a blanket correlation requirement.

The current evidence also does not define a universal temporal ordering rule between `occurredAt` and `recordedAt` for delayed/imported audit ingestion, so HMSR-112 does not invent one.

### 125.12 Required revisions

Four evidence-backed production obligations remain:

1. **Fail closed on blank/null `sourceModule`, `targetModule`, and `targetType` before persistence.**
2. **Validate mandatory `eventTypeId` / `eventCategoryId` and supplied optional `severityId` / `reasonId` against their correct Audit catalog families, not merely any Audit catalog row.**
3. **Make AuditEvent persistence genuinely append-only so an existing event cannot be silently overwritten or mutated outside explicitly governed evidence-preserving lifecycle behavior.**
4. **Sanitize/redact audit free-text/structured payload inputs and enforce an evidence-backed size limit for `payloadJson` before persistence; secrets and credential material must never enter the ledger.**

Historical Organization, Identity, Workflow, and polymorphic target references must remain snapshot/reference evidence without cross-module database FKs or mandatory live lookups.

HMSR-112 does not modify production Java, JPA, Flyway, API/application contracts, tests, audit data, workflow data, organization data, identity data, or provisioned data.

### 125.13 Review conclusion

**REVISE.** The Audit ownership boundary, 45-field evidence shape, status/actor enums, same-module catalog row-existence FKs, timestamp behavior, historical snapshot strategy, and polymorphic-target design are sound.

The target baseline cannot be marked APPROVED while required source/target identity fields are not fail-fast protected, catalog-family membership is unenforced, append-only persistence is not explicit, and structured/free-text audit payload sanitization is absent from the authoritative recording path.

HMS reconciliation must retain these obligations until explicitly authorized Audit production work resolves them or stronger repository evidence changes the governing contract.

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

- `e9acb9fd68378d6f7bb9d631d5b89896867cfcdb` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:164](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L164)
- [docs/roadmap/model-semantic-review.md:307](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L307)
- [docs/roadmap/model-semantic-review.md:19113](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L19113)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:307 | HMSR-112 | 6 | audit | AuditEvent | — | organization.OrganizationUnit, workflow.WorkflowAction, workflow.WorkflowInstance, workflow.WorkflowTask | 2 | 2 | 3 | REVISE | Completed | docs(model-review): review audit AuditEvent
doc/domain/SEMANTIC_DECISIONS.md:164 | HMSR-112 | [audit.AuditEvent](#audit-auditevent) | REVISE | HMR-095
~~~~~~~~

<a id="hapi-428"></a>
## HAPI-428 — review audit AuditAccessRecord

Phase: HPS-021 — Audit and Traceability

Legacy IDs: HMSR-118

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `audit` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review audit AuditAccessRecord

### 2. Existing Implementation

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditAccessType.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve accountable actor, source, revision, approval and export evidence across operations.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `audit`; primary phase owns this obligation. Do not conflate a stored audit export request with a generated external artifact or successful delivery. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditAccessType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditAccessType.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 131. HMSR-118 — audit.AuditAccessRecord review

**Decision:** REVISE  
**Review code:** HMSR-118  
**Dependency level:** 7  
**Bounded context:** audit  
**Confirmed upstream subject dependencies:** optional same-module `audit.AuditEvent` and `audit.AuditExportRequest`; Identity actor reference is historical evidence  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 0

### 131.1 Semantic role

`AuditAccessRecord` is Audit's append-only evidence that audit evidence itself was searched, viewed, exported, verified, archived, or subjected to a purge request.

The Audit DDD explicitly states both that access to audit evidence is itself auditable and that `AuditAccessRecord` is append-only.

### 131.2 Field and persistence alignment

The live domain record, JPA entity, mapper, application command/service, repository adapter, and base Audit migration align on the 11-field shape.

The domain already fail-fast validates the DDD-required `id`, `actorId`, `accessType`, and `accessedAt` fields.

`AuditAccessType` contains `SEARCH`, `VIEW`, `EXPORT`, `VERIFY_SEAL`, `ARCHIVE`, and `PURGE_REQUEST`, exactly matching the DDD vocabulary. Current evidence supports retaining this as a stable technical action enum rather than converting it to a user-managed catalog.

### 131.3 Optional AuditEvent reference

`auditEventId` is optional because only event-specific access necessarily targets one AuditEvent.

The current `recordAuditAccess(...)` path accepts a supplied ID and persists it without checking that the referenced same-module AuditEvent exists.

Future production reconciliation must fail closed on an unknown supplied `auditEventId` through the Audit-owned repository/application boundary while preserving null for non-event-specific access.

HMSR-118 does not make `auditEventId` mandatory for every access type because the DDD explicitly defines it as optional.

### 131.4 Optional AuditExportRequest reference

`exportRequestId` is optional and links access evidence to a controlled Audit export request when applicable.

The current application path likewise persists a supplied ID without validating same-module existence.

Future production reconciliation must fail closed on an unknown supplied `exportRequestId` through the Audit-owned boundary while preserving optionality.

HMSR-118 does not invent a universal rule that every `EXPORT` record must carry `exportRequestId`; the DDD relationship supports linkage but does not state that invariant explicitly.

### 131.5 Append-only persistence gap

`AuditAccessRecordRepositoryPort` exposes generic `save(AuditAccessRecord)`, and `JpaAuditAccessRecordRepositoryAdapter` delegates directly to Spring Data `repository.save(...)`.

That behavior permits inserting a new access record but can also overwrite an existing record with the same ID, conflicting with the Audit DDD's append-only policy.

Future production reconciliation must make access-record persistence insert-only/append-only so recorded access evidence cannot be silently rewritten.

### 131.6 Actor semantics

`actorId` is mandatory evidence identifying who accessed audit information. `actorDisplayNameSnapshot` is optional historical display evidence.

Identity remains owner of live actor lifecycle. No Audit-to-Identity database FK is authorized, and historical access evidence must remain readable even after identity changes.

### 131.7 Search/export metadata

`searchFilterHash`, `resultCount`, `purposeText`, and `correlationId` are optional evidence fields.

The reviewed Audit DDD does not define a universal nonnegative `resultCount` invariant, mandatory purpose text, or access-type-specific metadata matrix, so HMSR-118 does not invent such rules.

### 131.8 Required revisions

Three evidence-backed production obligations remain:

1. **Make AuditAccessRecord persistence genuinely append-only so an existing record ID cannot be overwritten through generic save semantics.**
2. **Fail closed on a supplied unknown `auditEventId` while preserving its documented optionality.**
3. **Fail closed on a supplied unknown `exportRequestId` while preserving its documented optionality.**

HMSR-118 does not modify production Java, JPA, Flyway, API/application contracts, tests, Audit/Identity data, or provisioned data.

### 131.9 Review conclusion

**REVISE.** The 11-field shape, required actor/access/time invariants, access-type enum, optional evidence-link design, and historical actor boundary are appropriate.

The target baseline cannot be marked APPROVED while append-only persistence is not enforced and supplied same-module AuditEvent/AuditExportRequest references can remain unresolved.

HMS reconciliation must retain these obligations until explicitly authorized Audit production work resolves them or stronger repository evidence changes the governing Audit contract.

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

- `565dd817678bf9fb822fbe5643acbddad2007b57` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:170](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L170)
- [docs/roadmap/model-semantic-review.md:313](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L313)
- [docs/roadmap/model-semantic-review.md:19764](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L19764)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:313 | HMSR-118 | 7 | audit | AuditAccessRecord | — | audit.AuditEvent, audit.AuditExportRequest | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review audit AuditAccessRecord
doc/domain/SEMANTIC_DECISIONS.md:170 | HMSR-118 | [audit.AuditAccessRecord](#audit-auditaccessrecord) | REVISE | HMR-101
~~~~~~~~

<a id="hapi-429"></a>
## HAPI-429 — review audit AuditBeforeAfterValue

Phase: HPS-021 — Audit and Traceability

Legacy IDs: HMSR-119

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `audit` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review audit AuditBeforeAfterValue

### 2. Existing Implementation

Audit events/access/export records and owner validation contracts exist with secret/payload guards. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditValueType.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve accountable actor, source, revision, approval and export evidence across operations.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `audit`; primary phase owns this obligation. Do not conflate a stored audit export request with a generated external artifact or successful delivery. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditValueType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditValueType.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 132. HMSR-119 — audit.AuditBeforeAfterValue review

**Decision:** REVISE  
**Review code:** HMSR-119  
**Dependency level:** 7  
**Bounded context:** audit  
**Confirmed upstream subject dependencies:** mandatory same-module `audit.AuditEvent`; optional Audit mask-reason catalog reference  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 0

### 132.1 Semantic role

`AuditBeforeAfterValue` stores selective field-level before/after evidence associated with an AuditEvent. It is evidence, not a generic object dump and not business state.

The Audit DDD explicitly prohibits storing raw passwords, secrets, tokens, private keys, session tokens, client secrets, certificates, or raw credentials and instructs Audit to prefer hashes or `MASKED` markers for sensitive values.

### 132.2 Field and persistence alignment

The live domain record, JPA entity, persistence mapper, repository port/adapter, and base Audit migration align on the 13-field shape.

`AuditValueType` contains exactly the DDD vocabulary: `STRING`, `NUMBER`, `BOOLEAN`, `DATE`, `TIMESTAMP`, `CATALOG`, `REFERENCE`, `JSON`, and `MASKED`. Current evidence supports retaining it as a technical representation enum.

The domain already fail-fast validates `id`, `auditEventId`, `valueType`, and `recordedAt`.

### 132.3 Required fieldPath gap

The Audit DDD marks `fieldPath` as required, and the JPA/Flyway schema marks `field_path` `NOT NULL`.

The current domain constructor only normalizes `fieldPath`; blank/null values can therefore pass through the domain and fail only at persistence.

Future production reconciliation must fail closed on blank/null `fieldPath` before persistence.

### 132.4 Mandatory AuditEvent integrity

`auditEventId` is the mandatory same-module parent reference.

HRA-111 correctly installs:

```text
hidra_audit_before_after_value.audit_event_id
  -> hidra_audit_event.id
```

and the domain rejects a blank AuditEvent ID.

Repository search found no current application service that creates `AuditBeforeAfterValue`; therefore HMSR-119 does not invent a new producer. Any future producer must create before/after evidence only for an existing AuditEvent and preserve Audit ownership.

### 132.5 Sensitive-value masking defect

The DDD states:

```text
If before/after value is sensitive, raw values must not be stored.
Before/after values marked masked must not store raw sensitive value text.
```

The current record provides `masked`, `valueType`, before/after text, before/after hashes, and `storesMaskedEvidence()`, but the helper is not enforced.

A record can currently be created with `masked=true` or `valueType=MASKED` while still carrying `beforeValueText` and/or `afterValueText`.

Future production reconciliation must prevent raw value text from being persisted when the record is masked/sensitive and must use the permitted hash/marker representation instead. The implementation must also apply the Audit sensitive-data policy before persistence rather than relying on callers to self-sanitize.

### 132.6 Mask reason catalog semantics

`maskReasonId` is an optional Audit-owned catalog reference. The Audit DDD defines `MASK_REASON` as a catalog family.

The base schema indexes `mask_reason_id`, but HRA-111 does not install a mandatory FK because the field is optional, and no application/domain validation currently proves that a supplied value belongs to the `MASK_REASON` family.

Future production reconciliation must validate any supplied `maskReasonId` through Audit-owned `MASK_REASON` catalog-family semantics while preserving null when no masking reason is applicable.

HMSR-119 does not make `maskReasonId` universally mandatory because the DDD defines it as optional.

### 132.7 changed semantics

`changed` is an explicit required boolean describing whether the value actually changed.

The reviewed Audit DDD does not state that unchanged evidence rows are forbidden, so HMSR-119 does not invent a rule requiring `changed=true` for every record.

### 132.8 Append-only persistence gap

Audit's global policy forbids replacing before/after values and treats Audit evidence as append-only.

`AuditBeforeAfterValueRepositoryPort` exposes generic `save(AuditBeforeAfterValue)`, and the JPA adapter delegates directly to Spring Data `repository.save(...)`.

That permits inserting new evidence but can also overwrite a row with an existing identifier.

Future production reconciliation must make before/after persistence insert-only/append-only so previously recorded field evidence cannot be silently rewritten.

### 132.9 Required revisions

Four evidence-backed production obligations remain:

1. **Fail closed on blank/null `fieldPath` before persistence.**
2. **Enforce Audit sensitive-data policy:** masked/sensitive before/after evidence must not retain raw text; use hashes or masked/reference-only representations as appropriate.
3. **Validate supplied `maskReasonId` against the Audit `MASK_REASON` catalog family while preserving optionality.**
4. **Make AuditBeforeAfterValue persistence genuinely append-only so an existing evidence row cannot be overwritten through generic save semantics.**

HMSR-119 does not modify production Java, JPA, Flyway, API/application contracts, tests, Audit data, or provisioned data.

### 132.10 Review conclusion

**REVISE.** The 13-field shape, value-type enum, mandatory AuditEvent FK, hash-capable representation, and selective-capture design are structurally appropriate.

The target baseline cannot be marked APPROVED while required `fieldPath` validation is missing, masked records can retain raw values, mask-reason catalog semantics are unenforced, and the repository contract does not protect append-only evidence.

HMS reconciliation must retain these obligations until explicitly authorized Audit production work resolves them or stronger repository evidence changes the governing Audit contract.

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

- `e507ee81cddd19223823adbae2d6e7f4eed6c273` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:171](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L171)
- [docs/roadmap/model-semantic-review.md:314](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L314)
- [docs/roadmap/model-semantic-review.md:19847](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L19847)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:314 | HMSR-119 | 7 | audit | AuditBeforeAfterValue | — | audit.AuditEvent | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review audit AuditBeforeAfterValue
doc/domain/SEMANTIC_DECISIONS.md:171 | HMSR-119 | [audit.AuditBeforeAfterValue](#audit-auditbeforeaftervalue) | REVISE | HMR-102
~~~~~~~~

<!-- task-register:payload:end -->
