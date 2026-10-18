# HPS-014 — Custody and Measurement: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-014 — Custody and Measurement

<a id="hapi-152"></a>
## HAPI-152 — scan custody domain models

Phase: HPS-014 — Custody and Measurement

Legacy IDs: HMC-007

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 3 custody model files.

### 2. Existing Implementation

Measurement-period/ticket/discrepancy creation and governed audit/reference checks exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyTicketStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve commercial measurement periods, transfer tickets and discrepancy provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Recorded quantities and method metadata are distinct from certified metrology or fiscal acceptance. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyTicketStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 16. HMC-007 — Custody module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `custody`  
**Baseline model files:** 3  
**Scanned models:** 3  
**Declared fields/components:** 45  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Custody model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| CustodyDiscrepancy | record | 16 | `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java` | Extracted |
| CustodyMeasurementPeriod | record | 13 | `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java` | Extracted |
| CustodyTransferTicket | record | 16 | `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java` | Extracted |

### Custody fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| CustodyDiscrepancy | 1 | id | String |
| CustodyDiscrepancy | 2 | discrepancyNumber | String |
| CustodyDiscrepancy | 3 | reconciliationId | String |
| CustodyDiscrepancy | 4 | discrepancyTypeId | String |
| CustodyDiscrepancy | 5 | status | CustodyDiscrepancyStatus |
| CustodyDiscrepancy | 6 | differenceQuantity | BigDecimal |
| CustodyDiscrepancy | 7 | quantityUnitId | String |
| CustodyDiscrepancy | 8 | description | String |
| CustodyDiscrepancy | 9 | rootCauseText | String |
| CustodyDiscrepancy | 10 | resolutionText | String |
| CustodyDiscrepancy | 11 | assignedActorId | String |
| CustodyDiscrepancy | 12 | openedAt | Instant |
| CustodyDiscrepancy | 13 | resolvedAt | Instant |
| CustodyDiscrepancy | 14 | closedAt | Instant |
| CustodyDiscrepancy | 15 | createdAt | Instant |
| CustodyDiscrepancy | 16 | updatedAt | Instant |
| CustodyMeasurementPeriod | 1 | id | String |
| CustodyMeasurementPeriod | 2 | periodCode | String |
| CustodyMeasurementPeriod | 3 | agreementId | String |
| CustodyMeasurementPeriod | 4 | transferPointId | String |
| CustodyMeasurementPeriod | 5 | periodStart | Instant |
| CustodyMeasurementPeriod | 6 | periodEnd | Instant |
| CustodyMeasurementPeriod | 7 | status | CustodyPeriodStatus |
| CustodyMeasurementPeriod | 8 | lockedByActorId | String |
| CustodyMeasurementPeriod | 9 | lockedAt | Instant |
| CustodyMeasurementPeriod | 10 | approvedByActorId | String |
| CustodyMeasurementPeriod | 11 | approvedAt | Instant |
| CustodyMeasurementPeriod | 12 | createdAt | Instant |
| CustodyMeasurementPeriod | 13 | updatedAt | Instant |
| CustodyTransferTicket | 1 | id | String |
| CustodyTransferTicket | 2 | ticketNumber | String |
| CustodyTransferTicket | 3 | measurementPeriodId | String |
| CustodyTransferTicket | 4 | agreementId | String |
| CustodyTransferTicket | 5 | transferPointId | String |
| CustodyTransferTicket | 6 | batchId | String |
| CustodyTransferTicket | 7 | quantityCalculationId | String |
| CustodyTransferTicket | 8 | status | CustodyTicketStatus |
| CustodyTransferTicket | 9 | ticketDate | Instant |
| CustodyTransferTicket | 10 | issuedByActorId | String |
| CustodyTransferTicket | 11 | approvedByActorId | String |
| CustodyTransferTicket | 12 | approvedAt | Instant |
| CustodyTransferTicket | 13 | workflowInstanceId | String |
| CustodyTransferTicket | 14 | auditReferenceId | String |
| CustodyTransferTicket | 15 | createdAt | Instant |
| CustodyTransferTicket | 16 | updatedAt | Instant |

### HMC-007 reconciliation

```text
baseline model files = 3
extracted models     = 3
exceptions           = 0
declared fields      = 45
zero-field models    = 0
```

The scan records only declarations under `custody/domain/model`. Custody domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

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

- `907135e44fc4ef45e784f649851e01cdb34a9a23` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:135](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L135)
- [docs/roadmap/model-field-catalogue.md:813](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L813)
- [docs/roadmap/model-field-catalogue.md:881](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L881)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:135 | HMC-007 | docs(catalogue): scan custody domain models | Scan all 3 custody model files. | **Completed** — 3/3 models, 45 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-197"></a>
## HAPI-197 — remediate semantic review CustodyMeasurementPeriod

Phase: HPS-014 — Custody and Measurement

Legacy IDs: HMR-018

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review CustodyMeasurementPeriod

### 2. Existing Implementation

Measurement-period/ticket/discrepancy creation and governed audit/reference checks exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Custody.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyMeasurementPeriodRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyMeasurementPeriodResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyMeasurementPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyMeasurementPeriodSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyMeasurementPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyMeasurementPeriodRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyMeasurementPeriodJpaRepository.java`
- `src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyMeasurementPeriodSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve commercial measurement periods, transfer tickets and discrepancy provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Recorded quantities and method metadata are distinct from certified metrology or fiscal acceptance. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyMeasurementPeriodRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyMeasurementPeriodRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyMeasurementPeriodJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Custody.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyMeasurementPeriodRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyMeasurementPeriodResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyMeasurementPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyMeasurementPeriodSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyMeasurementPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyMeasurementPeriodRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyMeasurementPeriodJpaRepository.java`
- `src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyMeasurementPeriodSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-018 — custody.CustodyMeasurementPeriod

- Source review: `HMSR-020`
- Exact commit: `fix(custody): remediate semantic review CustodyMeasurementPeriod`
- Status: **Completed** — every `CustodyMeasurementPeriod` save resolves the selected Custody-owned `CustodyAgreement` and requires its mandatory `transferPointId` to equal the period `transferPointId`. PostgreSQL independently enforces the same relationship through a composite `(agreement_id, transfer_point_id)` foreign key to the agreement row. No agreement-status, agreement-validity, or period-transition rule was invented.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Custody.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyMeasurementPeriodRequest.java`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyMeasurementPeriodResponse.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyMeasurementPeriodCommand.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyMeasurementPeriodSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyMeasurementPeriodUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyMeasurementPeriodRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyMeasurementPeriodRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyMeasurementPeriodJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_018__hmr_018_custody_custody_measurement_period.sql`
  - `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyMeasurementPeriodSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=CustodyMeasurementPeriodSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce agreement/transfer-point coherence when opening or persisting a measurement period.** A `CustodyMeasurementPeriod` must not reference a `CustodyAgreement` whose mandatory `transferPointId` identifies a different transfer point from the period's own `transferPointId`. The correction must fail closed at an appropriate Custody-owned application/domain/persistence boundary; HMSR-020 does not prescribe a repository-port shape or a composite database constraint without a separately authorized implementation task.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyMeasurementPeriodSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `0c7ffe9c4052ae831d44ebfc0a912099136adb14` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:164](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L164)
- [docs/roadmap/model-semantic-remediation.md:1236](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1236)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:164 | HMR-018 | HMSR-020 | custody | CustodyMeasurementPeriod | — | — | fix(custody): remediate semantic review CustodyMeasurementPeriod | **Completed**
~~~~~~~~

<a id="hapi-208"></a>
## HAPI-208 — remediate semantic review CustodyDiscrepancy

Phase: HPS-014 — Custody and Measurement

Legacy IDs: HMR-027

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review CustodyDiscrepancy

### 2. Existing Implementation

Measurement-period/ticket/discrepancy creation and governed audit/reference checks exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Custody.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyDiscrepancyRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyDiscrepancyResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyDiscrepancyCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyDiscrepancySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDiscrepancyRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyDiscrepancyRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyDiscrepancyJpaRepository.java`
- `src/main/resources/db/migration/V20261004_027__hmr_027_custody_custody_discrepancy.sql`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve commercial measurement periods, transfer tickets and discrepancy provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Recorded quantities and method metadata are distinct from certified metrology or fiscal acceptance. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDiscrepancyRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyDiscrepancyRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyDiscrepancyJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Custody.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyDiscrepancyRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyDiscrepancyResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyDiscrepancyCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyDiscrepancySummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDiscrepancyRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyDiscrepancyRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyDiscrepancyJpaRepository.java`
- `src/main/resources/db/migration/V20261004_027__hmr_027_custody_custody_discrepancy.sql`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-027 — custody.CustodyDiscrepancy

- Source review: `HMSR-029`
- Exact commit: `fix(custody): remediate semantic review CustodyDiscrepancy`
- Status: **Completed** — optional quantityUnitId now has additive same-module FK protection to CustodyCatalogEntry; null remains allowed and no catalog-family rule is invented.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 1
- Additive Flyway: `src/main/resources/db/migration/V20261004_027__hmr_027_custody_custody_discrepancy.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Custody.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/OpenCustodyDiscrepancyRequest.java`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyDiscrepancyResponse.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyDiscrepancyCommand.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyDiscrepancySummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/in/OpenCustodyDiscrepancyUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyDiscrepancyRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
  - `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyDiscrepancyRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyDiscrepancyJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_027__hmr_027_custody_custody_discrepancy.sql`
  - `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=CustodyDiscrepancySemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated quantity-unit references.** When `CustodyDiscrepancy.quantityUnitId` is non-null, it must resolve to the Custody-owned controlled-value/catalog target rather than allowing a dangling ID. A separately authorized Custody correction may enforce this through application validation, an additive nullable same-module FK, or another fail-closed Custody-owned boundary. HMSR-029 does not prescribe a catalog-family name that current DDD evidence does not define.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyDiscrepancySemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `dbb57494c21de6366634d17ecda26e38e35d6723` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Custody.md:202](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Custody.md#L202)
- [docs/roadmap/model-semantic-remediation.md:173](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L173)
- [docs/roadmap/model-semantic-remediation.md:1609](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1609)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:173 | HMR-027 | HMSR-029 | custody | CustodyDiscrepancy | — | — | fix(custody): remediate semantic review CustodyDiscrepancy | Planned
~~~~~~~~

<a id="hapi-264"></a>
## HAPI-264 — remediate semantic review CustodyTransferTicket

Phase: HPS-014 — Custody and Measurement

Legacy IDs: HMR-070

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review CustodyTransferTicket

### 2. Existing Implementation

Measurement-period/ticket/discrepancy creation and governed audit/reference checks exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Custody.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/CustodyTicketAuditReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/CustodyTicketAuditReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/CreateCustodyTransferTicketRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyTransferTicketResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/CreateCustodyTransferTicketCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyTransferTicketSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTransferTicketRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyTransferTicketReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyTransferTicketJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyTransferTicketActorReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/CustodyTransferTicketActorReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyTransferTicketWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/CustodyTransferTicketWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261008_012__hmr_070_custody_custody_transfer_ticket.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/CustodyTicketAuditReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/CustodyTransferTicketSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/CustodyTransferTicketActorReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/CustodyTransferTicketWorkflowReferenceContractTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Recorded quantities and method metadata are distinct from certified metrology or fiscal acceptance. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTransferTicketRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/audit/application/service/CustodyTicketAuditReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/CustodyTransferTicketActorReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/CustodyTransferTicketWorkflowReferenceQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyTransferTicketReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Custody.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/CustodyTicketAuditReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/CustodyTicketAuditReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/CreateCustodyTransferTicketRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyTransferTicketResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/CreateCustodyTransferTicketCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyTransferTicketSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTransferTicketRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyTransferTicketReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyTransferTicketJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyTransferTicketActorReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/CustodyTransferTicketActorReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyTransferTicketWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/CustodyTransferTicketWorkflowReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261008_012__hmr_070_custody_custody_transfer_ticket.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/CustodyTicketAuditReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/CustodyTransferTicketSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/CustodyTransferTicketActorReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/CustodyTransferTicketWorkflowReferenceContractTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-070 — custody.CustodyTransferTicket

- Source review: `HMSR-082`
- Exact commit: `fix(custody): remediate semantic review CustodyTransferTicket`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: custody.CustodyMeasurementPeriod, workflow.WorkflowInstance
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261008_012__hmr_070_custody_custody_transfer_ticket.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Custody.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/CreateCustodyTransferTicketRequest.java`
  - `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyTransferTicketResponse.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/command/CreateCustodyTransferTicketCommand.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyTransferTicketSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTransferTicketRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyTransferTicketJpaRepository.java`
  - `src/main/resources/db/migration/V20261008_012__hmr_070_custody_custody_transfer_ticket.sql`
  - `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=CustodyTransferTicketSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Protect populated same-module evidence references.** `batchId` and `quantityCalculationId` must fail closed to existing Custody-owned records.
  2. **Validate populated Identity/Workflow references through owner-controlled contracts.** This covers issuer/approver actor IDs and `workflowInstanceId`; do not introduce cross-module database FKs.
  3. **Preserve Audit ownership for `auditReferenceId` and validate/populate it through an Audit-owned application/publication boundary** rather than direct persistence coupling.

- Accepted Batch 16 exhaustive scope in canonical Ultimate Roadmap supersedes the original client-only scope.
- Execution: Nullable batch/calculation references now fail closed through Custody-owned checks and validated forward 012 FKs. Identity validates new/changed issuer and approver IDs; Workflow attests exact ticket context and configured binding; Audit owner resolves populated evidence for the exact Custody ticket. Locked transactional adapter saves prevent bypass while unchanged historical provenance remains preserved. Creation retains null approval/Audit values and optionality. No invented approval coupling, transition/temporal rule or cross-module FK. Changed production and 21 focused unit signatures compiled against temporary APIs; nine actual reference behavior checks passed with controlled owner fixtures. Four real PostgreSQL tests prepared, including legacy orphan rollback and concurrent parent deletion. Focused Maven stopped before execution at uncached offline Boot 4.1.1 parent. Final-head CI pending.

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
- `src/test/java/dz/sh/hidra/modules/audit/semantic/CustodyTicketAuditReferenceContractTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/CustodyTransferTicketSemanticPostgresIntegrationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/semantic/CustodyTransferTicketActorReferenceContractTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/CustodyTransferTicketWorkflowReferenceContractTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.

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

- `8ee951a2f35a8f194ebec823cb99df9cf20d28cf` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:105](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L105)
- [doc/model-remediation/RECONCILIATION.md:2989](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L2989)
- [doc/model-remediation/RECONCILIATION.md:13717](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13717)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:5096](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L5096)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15457](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15457)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:104](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L104)
- [docs/data definition/Custody.md:220](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Custody.md#L220)
- [docs/roadmap/model-semantic-remediation.md:236](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L236)
- [docs/roadmap/model-semantic-remediation.md:3886](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3886)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:236 | HMR-070 | HMSR-082 | custody | CustodyTransferTicket | — | custody.CustodyMeasurementPeriod, workflow.WorkflowInstance | fix(custody): remediate semantic review CustodyTransferTicket | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:105 | HMR-070 | HMSR-082 | custody.CustodyTransferTicket | COMPLETED — CI #596 GREEN | Batch 16 passed full Java 21/PostgreSQL/OpenAPI verification at 68e330562b03cf92c5500b99ffceca1fd024d083.
doc/model-remediation/RECONCILIATION.md:13717 | HMR-070 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:104 | HMR-070 | HMSR-082 | [custody.CustodyTransferTicket](../domain/SEMANTIC_DECISIONS.md#custody-custodytransferticket) | [CustodyTransferTicketSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java) | [V20261008_012__hmr_070_custody_custody_transfer_ticket](../../src/main/resources/db/migration/V20261008_012__hmr_070_custody_custody_transfer_ticket.sql); [CustodyTicketAuditReferenceContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/CustodyTicketAuditReferenceContract.java); [CustodyTransferTicketActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyTransferTicketActorReferenceContract.java); [CustodyTransferTicketWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyTransferTicketWorkflowReferenceContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15457 | HMR-070 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-330"></a>
## HAPI-330 — review custody CustodyMeasurementPeriod

Phase: HPS-014 — Custody and Measurement

Legacy IDs: HMSR-020

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review custody CustodyMeasurementPeriod

### 2. Existing Implementation

Measurement-period/ticket/discrepancy creation and governed audit/reference checks exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyMeasurementPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve commercial measurement periods, transfer tickets and discrepancy provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Recorded quantities and method metadata are distinct from certified metrology or fiscal acceptance. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyPeriodStatus.java`, `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyMeasurementPeriodCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyPeriodStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 33. HMSR-020 — custody.CustodyMeasurementPeriod review

**Decision:** REVISE  
**Review code:** HMSR-020  
**Dependency level:** 0  
**Bounded context:** custody  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `custody.CustodyTransferTicket` through `measurementPeriodId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 2 — `agreementId` and `transferPointId`, now semantically resolved to Custody-owned read/persistence models outside the 123 HMS subject set

### 33.1 Semantic role and ordering rationale

`CustodyMeasurementPeriod` is the Custody-owned fiscal/official time window under which accepted transfer evidence is grouped and governed. It binds a period code and closed time interval to one Custody agreement and one official Custody transfer point, while carrying lifecycle state plus optional lock/approval audit metadata.

It is Level 0 because its required domain prerequisites, `CustodyAgreement` and `CustodyTransferPoint`, are retained Custody read/persistence models outside the HMS subject population. `CustodyTransferTicket` is the one direct HMS subject dependent through `measurementPeriodId`.

The Custody DDD keeps this model on the official-transfer side of the architecture boundary: Telemetry owns observed readings, Planning owns expected state, Custody owns officially transferred/accepted evidence, and Finance/ERP ownership remains outside Custody.

### 33.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable measurement-period identity and persistence primary key. |
| `periodCode` | `String` | Mandatory | Business code identifying the fiscal/official measurement period. Current Custody DDD/schema do not state a uniqueness scope, so HMSR-020 does not invent one. |
| `agreementId` | `String` | Mandatory | Same-module reference to the governing `CustodyAgreement`. |
| `transferPointId` | `String` | Mandatory | Same-module reference to the official `CustodyTransferPoint` for the period. |
| `periodStart` | `Instant` | Mandatory | Start instant of the measurement period. |
| `periodEnd` | `Instant` | Mandatory | End instant of the measurement period. Current repository evidence enforces only that it is not before `periodStart`. |
| `status` | `CustodyPeriodStatus` | Mandatory | Period lifecycle state: `OPEN`, `LOCKED`, `CALCULATED`, `APPROVED`, `CLOSED`, `REOPENED`, or `CANCELLED`. |
| `lockedByActorId` | `String` | Optional cross-module actor reference | Identity/platform actor that performed the lock action when such lifecycle metadata exists. |
| `lockedAt` | `Instant` | Optional | Lock timestamp. |
| `approvedByActorId` | `String` | Optional cross-module actor reference | Identity/platform actor associated with approval when such lifecycle metadata exists. |
| `approvedAt` | `Instant` | Optional | Approval timestamp. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The current compact constructor correctly rejects blank `id`, `periodCode`, `agreementId`, and `transferPointId`; requires non-null `periodStart`, `periodEnd`, and `status`; enforces `periodStart <= periodEnd`; and normalizes textual IDs. The creation service conservatively opens new periods as `OPEN` with lock/approval metadata unset.

### 33.3 Agreement and transfer-point dependency resolution

HMS-003 left `agreementId` and `transferPointId` unresolved because their targets are outside the 123 HMS subject set. Stronger current repository evidence resolves both without adding subject-model graph edges:

- the Custody DDD explicitly owns `CustodyAgreement`, `CustodyTransferPoint`, and `CustodyMeasurementPeriod`;
- the DDD describes `CustodyAgreement` as the agreement governing custody transfer and `CustodyTransferPoint` as the official transfer point;
- repository mirror classification retains both targets as Custody read/persistence models outside the HMS subject population;
- HRA-111 installs `fk_hra111_custody_012` for `hidra_custody_measurement_period.agreement_id -> hidra_custody_agreement(id)`;
- HRA-111 installs `fk_hra111_custody_013` for `hidra_custody_measurement_period.transfer_point_id -> hidra_custody_transfer_point(id)`;
- both constraints use `ON DELETE RESTRICT`.

Therefore both fields are required same-module domain references to non-subject Custody models. They remain outside the HMS graph while their row existence is protected at the database boundary.

`lockedByActorId` and `approvedByActorId` remain cross-module Identity/platform actor references. They are not converted into Custody ownership or cross-module database foreign keys.

### 33.4 Measurement-period downstream boundary

The validated HMS graph contains one direct subject-model edge:

```text
CustodyTransferTicket.measurementPeriodId
    -> CustodyMeasurementPeriod.id
```

HRA-111 also protects several Custody read/persistence-model references to the period, including batch, measurement snapshot, meter-run snapshot, quality sample, quantity calculation, reconciliation, and transfer ticket records.

That broader persistence fan-out is semantically appropriate: a fiscal/official period acts as a stable evidence container. It does not change the HMS register's direct-subject dependent count because those additional models are outside the 123-subject population.

### 33.5 Agreement/transfer-point consistency gap

Current persistence verifies the two required references independently, but stronger same-module evidence establishes a relationship between them:

```text
CustodyAgreement.transferPointId
    -> CustodyTransferPoint.id

CustodyMeasurementPeriod.agreementId
    -> CustodyAgreement.id

CustodyMeasurementPeriod.transferPointId
    -> CustodyTransferPoint.id
```

`CustodyAgreement.transferPointId` is mandatory in JPA/schema and HRA-111 protects it with a same-module FK. However, `OpenCustodyMeasurementPeriodCommand` accepts `agreementId` and `transferPointId` independently, and `CustodyApplicationService.openMeasurementPeriod()` constructs/saves the period without demonstrating that the selected agreement governs the selected transfer point.

The database also enforces only independent FK existence. It can therefore accept an internally inconsistent period whose `agreementId` names an agreement for transfer point A while `transferPointId` names transfer point B.

For an official custody period, that breaks the repository's own relationship structure: the period would claim two incompatible same-module ownership facts at once.

### 33.6 Temporal and lifecycle semantics deliberately not invented

The current domain invariant is:

```text
periodStart <= periodEnd
```

The Custody DDD does not state a stricter exclusive-end or non-zero-duration rule for this model, so HMSR-020 does not import PlanningPeriod semantics or require `periodStart < periodEnd`.

Likewise, current repository evidence does not define a complete allowed transition matrix among `OPEN`, `LOCKED`, `CALCULATED`, `APPROVED`, `CLOSED`, `REOPENED`, and `CANCELLED`. The model exposes `closedLifecycle()` for `CLOSED`/`CANCELLED`, but no current source proves exact transition prerequisites.

The presence of `lockedByActorId + lockedAt` and `approvedByActorId + approvedAt` strongly identifies lifecycle audit metadata, but no active Custody DDD/application contract reviewed here states the exact pair-presence rules for every status. HMSR-020 therefore does not invent constructor constraints such as “LOCKED must always carry both lock fields” or “APPROVED must always carry both approval fields.”

Similarly, `CustodyAgreement` has status and validity dates and `CustodyTransferPoint` has status/effective dates, but the active Custody DDD does not specify the exact eligibility/temporal-containment policy required when opening a period. HMSR-020 records no unproven ACTIVE-only or date-containment rule.

### 33.7 Persistence and application consistency

The live domain and JPA models agree on all 13 declared components.

The base Custody migration makes `id`, `period_code`, `agreement_id`, `transfer_point_id`, `period_start`, `period_end`, `status`, `created_at`, and `updated_at` non-null. Lock/approval actor and timestamp fields are nullable.

The application boundary accepts exactly the five inputs needed to open a period:

```text
periodCode
agreementId
transferPointId
periodStart
periodEnd
```

and creates `status = OPEN` with null lock/approval metadata. That creation default is conservative.

No current schema evidence establishes global or scoped uniqueness for `periodCode`, and no current Custody DDD rule states one, so no uniqueness obligation is added.

Generic `createdAt`/`updatedAt` requirements remain persistence/application-boundary concerns under the existing HRA invariant policy rather than new constructor obligations.

### 33.8 Required revision

The model shape, time invariant, ownership boundary, and creation default are otherwise coherent. One evidence-backed correction remains:

1. **Enforce agreement/transfer-point coherence when opening or persisting a measurement period.** A `CustodyMeasurementPeriod` must not reference a `CustodyAgreement` whose mandatory `transferPointId` identifies a different transfer point from the period's own `transferPointId`. The correction must fail closed at an appropriate Custody-owned application/domain/persistence boundary; HMSR-020 does not prescribe a repository-port shape or a composite database constraint without a separately authorized implementation task.

HMSR-020 does not change production Java, JPA, Flyway, API/application contracts, tests, Custody agreements/points/periods, or provisioned data.

### 33.9 SONATRACH/TRC operational interpretation

For SONATRACH/TRC pipeline operations, the measurement period is the governed fiscal/official window against which accepted metering, quantity, quality, reconciliation, and transfer-ticket evidence can be associated.

The period is not a telemetry sampling interval and does not own raw SCADA/telemetry truth. Its agreement and official transfer point must identify one coherent custody-transfer context before downstream official evidence is attached.

Locking, calculation, approval, closing, reopening, and cancellation are governance/lifecycle concepts; their detailed transition and authorization rules must come from explicit Custody/Workflow/Audit contracts rather than being guessed during semantic review.

### 33.10 Review conclusion

**REVISE.** `CustodyMeasurementPeriod` has a coherent 13-field model, correct Level-0 placement, correct temporal-order guard, conservative OPEN creation, and correctly resolvable non-subject dependencies on `CustodyAgreement` and `CustodyTransferPoint`.

The target baseline cannot mark it APPROVED while agreement existence and transfer-point existence are checked independently but their required same-module relationship is not checked. HMS reconciliation must retain the agreement/transfer-point coherence obligation until an explicitly authorized Custody correction task resolves it or the target semantics are explicitly changed.

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

- `5b99bcd1a1fc33b7087f9e63e7fef6c1755200b5` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:72](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L72)
- [docs/roadmap/model-semantic-review.md:215](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L215)
- [docs/roadmap/model-semantic-review.md:5489](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L5489)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:215 | HMSR-020 | 0 | custody | CustodyMeasurementPeriod | — | — | 1 | 1 | 2 | REVISE | Completed | docs(model-review): review custody CustodyMeasurementPeriod
doc/domain/SEMANTIC_DECISIONS.md:72 | HMSR-020 | [custody.CustodyMeasurementPeriod](#custody-custodymeasurementperiod) | REVISE | HMR-018
~~~~~~~~

<a id="hapi-339"></a>
## HAPI-339 — review custody CustodyDiscrepancy

Phase: HPS-014 — Custody and Measurement

Legacy IDs: HMSR-029

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review custody CustodyDiscrepancy

### 2. Existing Implementation

Measurement-period/ticket/discrepancy creation and governed audit/reference checks exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyDiscrepancyCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve commercial measurement periods, transfer tickets and discrepancy provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Recorded quantities and method metadata are distinct from certified metrology or fiscal acceptance. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/OpenCustodyDiscrepancyCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/service/CustodyApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 42. HMSR-029 — custody.CustodyDiscrepancy review

**Decision:** REVISE  
**Review code:** HMSR-029  
**Dependency level:** 0  
**Bounded context:** custody  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 1 — the former unresolved `reconciliationId` is now resolved; optional `quantityUnitId` remains a non-subject catalog dependency

### 42.1 Semantic role and ordering rationale

`CustodyDiscrepancy` is the Custody-owned exception/deviation record attached to an official custody reconciliation. It records the discrepancy identity/type, optional quantity difference and unit, investigation/resolution narrative, optional assigned actor, lifecycle status and timestamps.

It is Level 0 because its required same-module prerequisites — `CustodyReconciliation` and `CustodyCatalogEntry` — are retained Custody read/persistence models outside the 123 HMS subject set. It has no direct HMS subject dependent.

The active Custody DDD keeps this model inside official accepted-transfer governance: Custody owns reconciliation and discrepancies while Telemetry owns measurement truth, Planning owns expected state, and Finance/ERP remains outside Custody.

### 42.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable discrepancy identity and persistence primary key. |
| `discrepancyNumber` | `String` | Mandatory | Business identifier for the discrepancy record. No repository evidence establishes a uniqueness scope. |
| `reconciliationId` | `String` | Mandatory | Same-module reference to the CustodyReconciliation that produced/owns the discrepancy context. |
| `discrepancyTypeId` | `String` | Mandatory | Custody-owned controlled classification reference backed by CustodyCatalogEntry. |
| `status` | `CustodyDiscrepancyStatus` | Mandatory | Lifecycle state: `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `REJECTED`, `RESOLVED`, `CLOSED`, or `CANCELLED`. |
| `differenceQuantity` | `BigDecimal` | Optional | Quantity difference associated with the discrepancy when applicable. |
| `quantityUnitId` | `String` | Optional | Custody-owned controlled unit reference associated with `differenceQuantity` when supplied. |
| `description` | `String` | Optional | Human-readable discrepancy description. |
| `rootCauseText` | `String` | Optional | Recorded root-cause analysis text. |
| `resolutionText` | `String` | Optional | Recorded resolution narrative. |
| `assignedActorId` | `String` | Optional cross-module actor reference | Identity/platform actor assigned to discrepancy handling when applicable. |
| `openedAt` | `Instant` | Mandatory | Discrepancy opening timestamp. |
| `resolvedAt` | `Instant` | Optional | Resolution timestamp when recorded. |
| `closedAt` | `Instant` | Optional | Closure timestamp when recorded. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor already rejects blank `id`, `discrepancyNumber`, `reconciliationId`, and `discrepancyTypeId`; requires non-null `status` and `openedAt`; enforces `openedAt <= closedAt` when a closure timestamp exists; and normalizes textual identifiers/narrative fields.

### 42.3 Reconciliation dependency resolution

HMS-003 left `reconciliationId` unresolved. Stronger current evidence resolves it unambiguously:

- Custody DDD owns both CustodyReconciliation and CustodyDiscrepancy;
- `CustodyReconciliation` is retained as a Custody read/persistence model outside the HMS subject set;
- `CustodyDiscrepancyJpaEntity.reconciliationId` maps to mandatory `hidra_custody_discrepancy.reconciliation_id`;
- HRA-111 installs `fk_hra111_custody_011`;
- that FK links `reconciliation_id -> hidra_custody_reconciliation(id)` with `ON DELETE RESTRICT`.

Therefore `reconciliationId` is a required same-module domain reference to a non-HMS Custody model. No subject-model graph edge is added.

The active open-discrepancy service does not explicitly query the reconciliation before save, but the database FK already provides fail-closed row-existence protection. HMSR-029 does not require duplicate application existence validation solely because the service does not perform a lookup.

### 42.4 Discrepancy-type controlled-value semantics

HMS-003 classified `discrepancyTypeId` generically as a catalog/value dependency. HRA-111 now proves its concrete same-module persistence target:

```text
hidra_custody_discrepancy.discrepancy_type_id
    -> hidra_custody_catalog_entry.id
```

through `fk_hra111_custody_010` with `ON DELETE RESTRICT`.

That is sufficient to resolve the field to CustodyCatalogEntry without adding an HMS graph edge.

Current Custody DDD does not name a specific catalog family/code such as `DISCREPANCY_TYPE`, nor define active-entry eligibility. HMSR-029 therefore does not invent a catalog-name rule beyond the proven Custody-owned catalog target.

### 42.5 Optional quantity-unit integrity gap

`quantityUnitId` is nullable in domain/JPA/schema, so HMSR-029 does not make it mandatory.

However, stronger same-module persistence evidence identifies the intended controlled-value owner:

- HMS-003 already classified `quantityUnitId` as a catalog/value dependency;
- Custody owns `CustodyCatalogEntry`;
- other canonical Custody quantity fields such as `CustodyQuantityCalculation.quantityUnitId` and `CustodyTicketLine.quantityUnitId` are HRA-111-protected against `hidra_custody_catalog_entry(id)`;
- `CustodyDiscrepancy.quantity_unit_id` is indexed as a reference-like field;
- the active `OpenCustodyDiscrepancyCommand` accepts `quantityUnitId` and `CustodyApplicationService.openDiscrepancy()` persists it directly.

Unlike the required quantity-unit references protected by HRA-111, the optional discrepancy `quantity_unit_id` has no FK and no demonstrated application lookup/validation.

Therefore a populated discrepancy can carry a dangling quantity-unit catalog ID even though the repository's Custody quantity-unit pattern identifies the controlled-value owner.

HMSR-029 does not invent a required pairing rule between `differenceQuantity` and `quantityUnitId` because the current DDD does not state whether one may legitimately appear without the other.

### 42.6 Actor-reference boundary

`assignedActorId` remains an optional cross-module actor reference.

There is no Actor subject model in the 123-model HMS population, and Custody must not own Identity state. No cross-module database FK should be introduced.

Current repository evidence also does not define an actor-status/eligibility contract for discrepancy assignment, so HMSR-029 does not invent one.

### 42.7 Lifecycle and temporal rules deliberately not invented

The current status vocabulary contains seven lifecycle labels and the domain already enforces:

```text
openedAt <= closedAt
```

when `closedAt` is present.

The repository-wide invariant classification explicitly retains that invariant but does not define:

- `openedAt <= resolvedAt`;
- `resolvedAt <= closedAt`;
- RESOLVED requiring `resolvedAt`;
- CLOSED requiring both `resolvedAt` and `closedAt`;
- root-cause/resolution text requirements by status;
- allowed status transition sequences.

HMSR-029 therefore does not invent those rules.

The create service conservatively opens a discrepancy as `OPEN`, with root cause, resolution, resolved timestamp and closed timestamp unset.

### 42.8 Quantity and numbering semantics deliberately not invented

Current Custody DDD does not state whether `differenceQuantity` may be signed, must be nonnegative absolute variance, or must match the parent reconciliation's quantity unit.

Likewise, no current schema/DDD evidence establishes global or reconciliation-scoped uniqueness for `discrepancyNumber`.

HMSR-029 records no such obligations.

### 42.9 Persistence/application consistency otherwise

The live domain and JPA representations agree on all 16 declared components.

The base schema requires:

```text
id
discrepancy_number
reconciliation_id
discrepancy_type_id
status
opened_at
created_at
updated_at
```

and keeps quantity, unit, narrative, actor, resolution and closure fields nullable.

The active application path creates `status = OPEN` and defaults `openedAt` to the current instant when the caller omits it. That behavior is conservative and consistent with the mandatory opening timestamp.

Generic audit timestamps remain persistence/application-boundary concerns rather than additional domain constructor invariants.

### 42.10 Required revision

One evidence-backed correction obligation remains:

1. **Protect populated quantity-unit references.** When `CustodyDiscrepancy.quantityUnitId` is non-null, it must resolve to the Custody-owned controlled-value/catalog target rather than allowing a dangling ID. A separately authorized Custody correction may enforce this through application validation, an additive nullable same-module FK, or another fail-closed Custody-owned boundary. HMSR-029 does not prescribe a catalog-family name that current DDD evidence does not define.

HMSR-029 does not modify production Java, JPA, Flyway, API/application contracts, tests, discrepancies, reconciliations, catalogs or provisioned data.

### 42.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC custody-transfer operations, a discrepancy records a governed mismatch or exception discovered during official reconciliation. Its parent reconciliation and discrepancy classification must remain traceable, while any recorded quantity variance must use a valid governed unit when a unit reference is supplied.

The discrepancy lifecycle supports investigation and resolution without making Custody the owner of Identity actor state, telemetry source truth, planning expectations or financial posting.

A dangling quantity-unit reference would weaken the auditability and interpretability of an official custody variance.

### 42.12 Review conclusion

**REVISE.** `CustodyDiscrepancy` has a coherent 16-field Custody-owned model, correct Level-0 placement, a resolved and database-protected parent reconciliation, a database-protected required discrepancy-type catalog reference, conservative OPEN creation semantics, and the approved `openedAt <= closedAt` invariant.

The target baseline cannot mark it APPROVED while a populated optional `quantityUnitId` can persist without demonstrated referential protection to the Custody-controlled quantity-unit catalog target.

HMS reconciliation must retain this optional quantity-unit integrity obligation until an explicitly authorized Custody correction task resolves it or stronger repository evidence explicitly changes the quantity-unit semantics.

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

- `d4417a096ea5b6786f2aa2cdfaacadad545e8ec4` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:81](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L81)
- [docs/roadmap/model-semantic-review.md:224](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L224)
- [docs/roadmap/model-semantic-review.md:7105](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L7105)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:224 | HMSR-029 | 0 | custody | CustodyDiscrepancy | — | — | 0 | 0 | 1 | REVISE | Completed | docs(model-review): review custody CustodyDiscrepancy
doc/domain/SEMANTIC_DECISIONS.md:81 | HMSR-029 | [custody.CustodyDiscrepancy](#custody-custodydiscrepancy) | REVISE | HMR-027
~~~~~~~~

<a id="hapi-392"></a>
## HAPI-392 — review custody CustodyTransferTicket

Phase: HPS-014 — Custody and Measurement

Legacy IDs: HMSR-082

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `custody` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review custody CustodyTransferTicket

### 2. Existing Implementation

Measurement-period/ticket/discrepancy creation and governed audit/reference checks exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyTicketStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve commercial measurement periods, transfer tickets and discrepancy provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `custody`; primary phase owns this obligation. Recorded quantities and method metadata are distinct from certified metrology or fiscal acceptance. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyTicketStatus.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyTicketStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 95. HMSR-082 — custody.CustodyTransferTicket review

**Decision:** REVISE  
**Review code:** HMSR-082  
**Dependency level:** 4  
**Bounded context:** custody  
**Confirmed upstream subject dependencies:** mandatory `custody.CustodyMeasurementPeriod`, optional `workflow.WorkflowInstance`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0

### 95.1 Semantic role

`CustodyTransferTicket` is the Custody-owned official transfer-ticket record. It identifies the ticket, anchors it to the governing Custody measurement period/agreement/transfer point, optionally records batch and quantity-calculation evidence, owns the ticket lifecycle and ticket date, and carries actor/workflow/audit references for issuance and approval.

Custody owns the official accepted transfer record. Workflow owns process state, Identity owns actor identity, and Audit owns audit records. Cross-module references must remain scalar/application-contract references rather than database foreign keys.

### 95.2 Mandatory Custody ownership references

The mandatory fields:

```text
measurementPeriodId
agreementId
transferPointId
```

are all same-module Custody references.

HRA-111 already protects them with Custody-local foreign keys to:

```text
CustodyMeasurementPeriod
CustodyAgreement
CustodyTransferPoint
```

The domain constructor also rejects blank IDs. No further basic existence correction is required for these mandatory ownership edges.

### 95.3 Optional batch and quantity-calculation references

`batchId` and `quantityCalculationId` are optional same-module references to Custody-owned evidence.

The base schema stores/indexes them, but repository evidence shows no HRA-111 foreign keys for these optional references and the reviewed creation service performs no existence lookup.

When populated, both references should fail closed to existing Custody-owned records. Same-module foreign keys are architecturally appropriate, or an explicitly justified Custody application check may supplement them.

The current Custody DDD does not state stronger correlation rules tying the selected batch/calculation to the same measurement period/agreement, so HMSR-082 does not invent those rules.

### 95.4 Ticket lifecycle enum

`CustodyTicketStatus` contains:

```text
DRAFT
SUBMITTED
UNDER_REVIEW
APPROVED
REJECTED
CANCELLED
CLOSED
```

These values represent the Custody-owned ticket lifecycle and are appropriate as a technical/domain enum.

The current Custody DDD reviewed for this task does not define a complete transition matrix or terminal-state immutability policy for `CustodyTransferTicket`; HMSR-082 therefore preserves the enum without inventing unsupported transition rules.

### 95.5 Approval actor/time coupling

The model carries optional:

```text
approvedByActorId
approvedAt
```

but the live domain constructor does not enforce coupling between them.

The Custody DDD establishes approval as part of the Custody-owned official transfer record, but it does not explicitly state that these two fields must always co-exist for every non-null occurrence or every APPROVED/CLOSED state.

HMSR-082 therefore does not invent a hard aggregate invariant beyond requiring owner-controlled validation when approval metadata is populated. HMS-006 may strengthen this only if live Custody approval workflow evidence explicitly establishes the coupling.

### 95.6 Workflow boundary

`workflowInstanceId` is optional and Workflow-owned.

The current model correctly stores it as a scalar reference and no cross-module database FK is present.

When populated, the authoritative Custody boundary should validate the Workflow instance through a Workflow-owned application contract and ensure it targets the intended Custody ticket/approval context. No cross-module Workflow database FK should be introduced.

### 95.7 Identity actor boundaries

`issuedByActorId` and `approvedByActorId` are Identity-owned actor references.

The create service copies `issuedByActorId` directly from the command; later approval paths may populate `approvedByActorId`. Repository evidence reviewed for this use case does not show owner-controlled Identity validation.

The authoritative Custody boundary should validate populated actor IDs through an Identity-owned contract while preserving scalar references. No cross-module Identity database FK should be introduced.

### 95.8 Audit reference boundary

`auditReferenceId` is an optional reference to Audit-owned evidence.

Custody does not own the audit ledger. The field must therefore remain a scalar/reference boundary and must not receive a cross-module database FK.

When populated by a Custody write flow, validation/resolution should use an Audit-owned application contract or an evidence-backed audit publication mechanism rather than importing Audit persistence models.

### 95.9 Ticket number and date semantics

The domain already enforces non-blank `ticketNumber` and non-null `ticketDate`.

Current Custody DDD evidence does not explicitly state global uniqueness for `ticketNumber`, nor does it define additional temporal ordering constraints between `ticketDate`, `approvedAt`, `createdAt`, and `updatedAt`.

HMSR-082 therefore does not invent uniqueness or time-order rules beyond the current explicit required-field contract.

### 95.10 Required revisions

Three evidence-backed obligations remain:

1. **Protect populated same-module evidence references.** `batchId` and `quantityCalculationId` must fail closed to existing Custody-owned records.

2. **Validate populated Identity/Workflow references through owner-controlled contracts.** This covers issuer/approver actor IDs and `workflowInstanceId`; do not introduce cross-module database FKs.

3. **Preserve Audit ownership for `auditReferenceId` and validate/populate it through an Audit-owned application/publication boundary** rather than direct persistence coupling.

HMSR-082 does not modify production Java, JPA, Flyway, API/application contracts, tests, Custody/Identity/Workflow/Audit data, or provisioned data.

### 95.11 Review conclusion

**REVISE.** `CustodyTransferTicket` is a coherent Custody-owned official transfer record with correct mandatory Custody ownership, appropriate lifecycle enum semantics, and clean scalar boundaries toward Workflow, Identity and Audit.

The target baseline cannot mark it APPROVED while populated optional same-module batch/calculation references are not fail-closed and cross-context actor/workflow/audit references are accepted without owner-controlled validation.

HMS reconciliation must retain these obligations until explicitly authorized Custody production corrections resolve them or stronger repository evidence changes the governing Custody DDD.

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

- `7a7c53455ceaa780e48cb93c0f5ce63ed2b68a1a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:134](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L134)
- [docs/roadmap/model-semantic-review.md:277](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L277)
- [docs/roadmap/model-semantic-review.md:15301](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L15301)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:277 | HMSR-082 | 4 | custody | CustodyTransferTicket | — | custody.CustodyMeasurementPeriod, workflow.WorkflowInstance | 0 | 0 | 5 | REVISE | Completed | docs(model-review): review custody CustodyTransferTicket
doc/domain/SEMANTIC_DECISIONS.md:134 | HMSR-082 | [custody.CustodyTransferTicket](#custody-custodytransferticket) | REVISE | HMR-070
~~~~~~~~

<!-- task-register:payload:end -->
