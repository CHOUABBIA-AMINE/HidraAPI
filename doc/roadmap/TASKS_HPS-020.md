# HPS-020 — Documents and Data Exchange: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-020 — Documents and Data Exchange

<a id="hapi-124"></a>
## HAPI-124 — add legacy data migration roadmap

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-001

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add legacy data migration roadmap

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/data-provisioning.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

This roadmap only: docs/roadmap/data-provisioning.md. PR review/merge gate; does not import data.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/data-provisioning.md`

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

- `49882036a0622a30d8c2291896ad085bd964f1cc` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/data-provisioning.md:97](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L97)
- [docs/roadmap/data-provisioning.md:147](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L147)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:97 | HDP-001 | docs(data-provisioning): add legacy data migration roadmap | This roadmap only: `docs/roadmap/data-provisioning.md`. PR review/merge gate; does **not** import data.
docs/roadmap/data-provisioning.md:147 | HDP-001 | Completed | Roadmap merged in PR #118, merge commit `3515d81ac17e3c26ae1c394c78d42a9485c03beb`; GitHub CI run #322 succeeded on source commit `49882036a0622a30d8c2291896ad085bd964f1cc`.
~~~~~~~~

<a id="hapi-125"></a>
## HAPI-125 — inventory hyflo source files and provenance

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-002

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

inventory hyflo source files and provenance

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data-provisioning/source-inventory.md`
- `docs/roadmap/data-provisioning.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

docs/data-provisioning/source-inventory.md: complete recursive manifest of both source directories (including LPL/), repo/ref/blob SHA, format, size, workbook tabs or SQL table names, row counts where obtainable, candidate date, owner, sensitivity, provenance, checksum. Flag missing/unreadable/binary/untrusted files; record real extraction methods. Never publish sensitive row values.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data-provisioning/source-inventory.md`
- `docs/roadmap/data-provisioning.md`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 6. HDP-002 validation and state

- [x] Verified source repository pinned commit and complete recursive Git tree (`truncated=false`), 31 files including nested `LPL/`.
- [x] Recorded per-file path, type, byte size and source blob SHA-1 for **every** Git-tracked file in both scoped directories.
- [x] Parsed accessible SQL source files for static table identifiers and statement counts; flagged inaccessible SQL separately.
- [x] Parsed the coordinate CSV header and syntactic row count without releasing individual rows.
- [x] Documented known credential exposure without copying credential values, and identified binary/unreviewed/conflicting source families.
- [ ] Extract real worksheet/tab names and row counts from all Excel workbooks using a reviewed, authorized binary-capable reader.
- [ ] Inspect two large SQL files sufficiently to list their SQL table identifiers and obtain verified counts where feasible.
- [ ] Confirm source ownership, distribution permissions and sensitivity classification with responsible source owner.
- [ ] Record raw-byte SHA-256 digests if required by downstream import design.
- [x] Close HDP-002 **as a bounded file-level inventory with the unresolved exceptions below explicitly retained**; repository maintainer requested completion and merge of this documentation work. This is not an authorization from any business data owner to publish, transform, or import restricted records.

**Validation performed:** deterministic manifest cardinality check (31 blobs / one nested directory), verified CSV parser (370 nonempty records / nine fields / zero malformed widths), source Git SHA checks and static SQL inspections; documentation-only change, so **Maven tests and PostgreSQL/Flyway migrations were not run**. No source files or secrets were copied into HidraAPI. GitHub PR/CI evidence belongs to the PR and should be recorded only after runs complete.

**Next:** HDP-003 is an independent, read-only *target-model* inventory and may begin after the HDP-002 status PR merges. Before source-dependent HDP-004/005, complete the safe inspection of the 13 workbooks and two inaccessible SQL dumps. G1 remains blocked until actual source-owner, confidentiality, and reuse approvals are documented.


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

- `a75d41f1dfdee52312f0eaacc356adca71764c21` — exact registered-message commit; surviving paths matched to current tree.
- `10158effa1f5fd6eff42f1c9003d706ac0d4b559` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data-provisioning/source-inventory.md:511](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data-provisioning/source-inventory.md#L511)
- [docs/data-provisioning/source-inventory.md:529](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data-provisioning/source-inventory.md#L529)
- [docs/roadmap/data-provisioning.md:98](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L98)
- [docs/roadmap/data-provisioning.md:148](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L148)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:98 | HDP-002 | docs(data-provisioning): inventory hyflo source files and provenance | docs/data-provisioning/source-inventory.md`: complete recursive manifest of both source directories (including `LPL/`), repo/ref/blob SHA, format, size, workbook tabs or SQL table names, row counts where obtainable, candidate date, owner, sensitivity, provenance, checksum. Flag missing/unreadable/binary/untrusted files; record real extraction methods. Never publish sensitive row values.
docs/roadmap/data-provisioning.md:148 | HDP-002 | Completed | Exhaustive Git-tracked file/provenance manifest (31 files), CSV syntactic count (370 records), six accessible SQL static metadata inventories. Inventory merged in PR #119 (`f22342197e4a8ca4d95cfc5187d1dfdeeb52f62f`), CI #324 passed. **Limited scope:** 13 workbook tabs/rows, two large SQL inventories, other binary contents, data-owner permission and credential remediation are still unverified. They block source-dependent classification/mapping, G1 and any import—not independent read-only HDP-003. Final status recorded by the HDP-002 documentation follow-up PR.
~~~~~~~~

<a id="hapi-126"></a>
## HAPI-126 — inventory hidra target models and schema

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-003

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `organization` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

inventory hidra target models and schema

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data-provisioning/target-inventory.md`
- `docs/roadmap/data-provisioning.md`
- `src/main/java/dz/sh/hidra/bootstrap/LocalAdministratorBootstrapRunner.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalConnectorPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationNotificationPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationWorkflowPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleExportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ConnectorInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalEndpointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalObjectReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDataContractJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationFieldMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationHealthSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationInboundRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationMappingProfileJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationOutboundRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationReconciliationIssueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationReconciliationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationRetryAttemptJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationRetryPolicyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationSchemaVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationSyncCursorJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationTransformationRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/AssignEmployeeUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/CreateOrganizationUnitUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/RegisterEmployeeUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/AdministrativeDistrictRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/AdministrativeLocalityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/AdministrativeStateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAddressRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationContactPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationDelegationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationHierarchySnapshotRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitTypeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/PositionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ReportingLineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ResponsibilityAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeDistrict.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeLocality.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeState.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAddress.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationContactPoint.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeDistrictJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeLocalityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeStateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAddressJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeJpaEntity.java`
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
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningApprovalUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/ExpectedFlowStateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/ForecastPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/ForecastSeriesJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationScheduleLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanActualReviewSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanApprovalReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanConstraintJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanScenarioJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlannedOperationWindowJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryDeviceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryExternalTagMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryIngestionBatchJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointStateSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryQualityAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryQuarantineRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryUnitJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryValidationRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
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
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/MeasurementLocationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologySnapshotJpaEntity.java`
- `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`
- `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`
- `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`
- `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`
- `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

docs/data-provisioning/target-inventory.md: actual domain model names, writable application contracts, JPA entity/table/column types and constraints, FK graph, enums, Flyway versions, existing catalog/seed/import implementations. Verify current main; distinguish absent functionality from uninspected functionality.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `organization`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`, `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/in/AssignEmployeeUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalConnectorPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`, `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ConnectorInstanceJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalEndpointJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalObjectReferenceJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data-provisioning/target-inventory.md`
- `docs/roadmap/data-provisioning.md`
- `src/main/java/dz/sh/hidra/bootstrap/LocalAdministratorBootstrapRunner.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RecordExchangeMessageUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/RegisterExternalSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/in/StartIntegrationJobRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalConnectorPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/ExternalSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationAuditPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationJobRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationNotificationPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationWorkflowPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleExportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/TargetModuleImportPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ConnectorInstanceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalEndpointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalObjectReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDataContractJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationFieldMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationHealthSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationInboundRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunStepJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationMappingProfileJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationOutboundRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationReconciliationIssueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationReconciliationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationRetryAttemptJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationRetryPolicyJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationSchemaVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationSyncCursorJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationTransformationRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/AssignEmployeeUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/CreateOrganizationUnitUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/RegisterEmployeeUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/AdministrativeDistrictRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/AdministrativeLocalityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/AdministrativeStateRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAddressRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/EmployeeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationContactPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationDelegationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationHierarchySnapshotRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitTypeRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/PositionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ReportingLineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ResponsibilityAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftAssignmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/ShiftRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeDistrict.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeLocality.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeState.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAddress.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationContactPoint.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeDistrictJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeLocalityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeStateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAddressJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeJpaEntity.java`
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
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreatePlanningPeriodUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningApprovalUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/PlanningQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningPeriodRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningQueryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/ExpectedFlowStateJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/ForecastPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/ForecastSeriesJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationScheduleLineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanActualReviewSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanApprovalReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanConstraintJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanScenarioJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlannedOperationWindowJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/CreateTelemetrySourceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/RegisterTelemetryPointUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/in/TelemetryQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryPointRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TelemetrySourceRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/port/out/TrustedTelemetryReadingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryDeviceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryExternalTagMappingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryIngestionBatchJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointBindingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointStateSnapshotJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryQualityAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryQuarantineRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceEndpointJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryUnitJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryValidationRuleJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/FacilityRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyConnectionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`
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
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/MeasurementLocationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemFacilityJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyNodeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologySnapshotJpaEntity.java`
- `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`
- `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`
- `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`
- `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`
- `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`

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

- `f83d55a8c50c9fcd3039b3f24fcb2638ff931465` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data-provisioning/target-inventory.md:1](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data-provisioning/target-inventory.md#L1)
- [docs/roadmap/data-provisioning.md:99](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L99)
- [docs/roadmap/data-provisioning.md:149](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L149)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:99 | HDP-003 | docs(data-provisioning): inventory hidra target models and schema | docs/data-provisioning/target-inventory.md`: actual domain model names, writable application contracts, JPA entity/table/column types and constraints, FK graph, enums, Flyway versions, existing catalog/seed/import implementations. Verify current `main`; distinguish absent functionality from uninspected functionality.
docs/roadmap/data-provisioning.md:149 | HDP-003 | Completed (documentation inventory) | Pinned Git tree, 24 module roots, 25 Flyway filenames, five candidate module domain/JPA/port inventories and five migration table/representative typed-column inventories recorded in `docs/data-provisioning/target-inventory.md`. Full constraints, enum values, public write semantics and live DB validation remain prerequisites for HDP-005/HDP-010; no data imported. PR CI and merge evidence to be checked on the exact commit.
~~~~~~~~

<a id="hapi-127"></a>
## HAPI-127 — classify source datasets and import eligibility

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-004

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

classify source datasets and import eligibility

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data-provisioning/source-classification.md`
- `docs/data-provisioning/source-inventory.md`
- `docs/data-provisioning/target-inventory.md`
- `docs/roadmap/data-provisioning.md`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Paused behind HMS prerequisite. Existing docs/data-provisioning/source-classification.md remains provisional evidence. Resume only after HMS-008 explicitly aligns the approved target-model semantic baseline. Then complete every input's reference/master/operational/history/derived/unknown classification, bounded-context owner, confidentiality, legal sharing limits, canonical/superseded versions, approval status and disposition (import/defer/reject). No unauthorized data copied to target repo.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data-provisioning/source-classification.md`
- `docs/data-provisioning/source-inventory.md`
- `docs/data-provisioning/target-inventory.md`
- `docs/roadmap/data-provisioning.md`

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

- `be212ecc0862f2e8a03375186a03857f3f72735c` — exact registered-message commit; surviving paths matched to current tree.
- `07f0e7f366d000c7e52ef6d59ae56f868eb4914c` — exact registered-message commit; surviving paths matched to current tree.
- `eca1bef24034a2be13e844db7d0d4660e4fea5a3` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data-provisioning/source-classification.md:1](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data-provisioning/source-classification.md#L1)
- [docs/roadmap/data-provisioning.md:100](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L100)
- [docs/roadmap/data-provisioning.md:150](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L150)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:100 | HDP-004 | docs(data-provisioning): classify source datasets and import eligibility | **Paused behind HMS prerequisite.** Existing `docs/data-provisioning/source-classification.md` remains provisional evidence. Resume only after HMS-008 explicitly aligns the approved target-model semantic baseline. Then complete every input's reference/master/operational/history/derived/unknown classification, bounded-context owner, confidentiality, legal sharing limits, canonical/superseded versions, approval status and disposition (import/defer/reject). No unauthorized data copied to target repo.
docs/roadmap/data-provisioning.md:150 | HDP-004 | Blocked | Owner affirmed authorization for project dataset use on 2026-09-21 and explicitly excluded `data/Fiche Passation.xlsx` from import; its `BadZipFile` no longer blocks in-scope work. HyFloAPI Actions run #35577651574 supplied structural metadata for 12 in-scope workbooks and eight SQL dumps. In-scope source business content, individual dataset eligibility, version precedence, appropriate confidentiality/security review and G1 remain unresolved. No records approved for import.
~~~~~~~~

<a id="hapi-128"></a>
## HAPI-128 — map legacy fields to hidra contracts

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-005

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

map legacy fields to hidra contracts

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: map legacy fields to hidra contracts.

### 4. Functional Requirements

docs/data-provisioning/source-target-mapping.md: per source column/table target model/property/DB field, type, nullable/enum/range, transform, units, source precedence, target owner, FK, identifier strategy, permitted loading contract, and explicit UNMAPPED disposition. Mapping is reviewed against code/SQL; do not invent missing fields.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- docs/data-provisioning/source-target-mapping.md: per source column/table target model/property/DB field, type, nullable/enum/range, transform, units, source precedence, target owner, FK, identifier strategy, permitted loading contract, and explicit UNMAPPED disposition. Mapping is reviewed against code/SQL; do not invent missing fields.
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

- [docs/roadmap/data-provisioning.md:101](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L101)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:101 | HDP-005 | docs(data-provisioning): map legacy fields to hidra contracts | docs/data-provisioning/source-target-mapping.md`: **per source column/table** target model/property/DB field, type, nullable/enum/range, transform, units, source precedence, target owner, FK, identifier strategy, permitted loading contract, and explicit `UNMAPPED` disposition. Mapping is reviewed against code/SQL; do not invent missing fields.
~~~~~~~~

<a id="hapi-129"></a>
## HAPI-129 — approve canonical sources and migration scope

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-006

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

approve canonical sources and migration scope

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: approve canonical sources and migration scope.

### 4. Functional Requirements

docs/data-provisioning/migration-decisions.md: resolved canonical snapshot/effective dates, duplicate/conflict policy, authoritative geographical reference version, sensitive-data disposition, excluded operational datasets, accountable reviewers, staged coverage, and G1 signoff or explicit blockers.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- docs/data-provisioning/migration-decisions.md: resolved canonical snapshot/effective dates, duplicate/conflict policy, authoritative geographical reference version, sensitive-data disposition, excluded operational datasets, accountable reviewers, staged coverage, and G1 signoff or explicit blockers.
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

- [docs/roadmap/data-provisioning.md:102](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L102)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:102 | HDP-006 | docs(data-provisioning): approve canonical sources and migration scope | docs/data-provisioning/migration-decisions.md`: resolved canonical snapshot/effective dates, duplicate/conflict policy, authoritative geographical reference version, sensitive-data disposition, excluded operational datasets, accountable reviewers, staged coverage, and G1 signoff or explicit blockers.
~~~~~~~~

<a id="hapi-130"></a>
## HAPI-130 — define algerian geographic normalization rules

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-007

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define algerian geographic normalization rules

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: define algerian geographic normalization rules.

### 4. Functional Requirements

docs/data-provisioning/geography-rules.md: approved Wilaya/Commune code/name references and effective edition; diacritics/aliases without erasing canonical names; coordinate order/CRS, point vs polyline meaning, spatial tolerances, missing-coordinate and ambiguous-locality quarantine. Review samples against source metadata; no guessed geocodes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- docs/data-provisioning/geography-rules.md: approved Wilaya/Commune code/name references and effective edition; diacritics/aliases without erasing canonical names; coordinate order/CRS, point vs polyline meaning, spatial tolerances, missing-coordinate and ambiguous-locality quarantine. Review samples against source metadata; no guessed geocodes.
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

- [docs/roadmap/data-provisioning.md:108](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L108)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:108 | HDP-007 | docs(data-provisioning): define algerian geographic normalization rules | docs/data-provisioning/geography-rules.md`: approved Wilaya/Commune code/name references and effective edition; diacritics/aliases without erasing canonical names; coordinate order/CRS, point vs polyline meaning, spatial tolerances, missing-coordinate and ambiguous-locality quarantine. Review samples against source metadata; no guessed geocodes.
~~~~~~~~

<a id="hapi-131"></a>
## HAPI-131 — define pipeline and engineering normalization rules

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-008

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define pipeline and engineering normalization rules

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: define pipeline and engineering normalization rules.

### 4. Functional Requirements

docs/data-provisioning/engineering-rules.md: units and conversions with dimensional tests, commodity/equipment controlled vocabularies, null vs zero, segment ordering/direction, design-vs-observed attributes, date/time precision, pressure/flow/length/diameter validation and non-inference rules.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- docs/data-provisioning/engineering-rules.md: units and conversions with dimensional tests, commodity/equipment controlled vocabularies, null vs zero, segment ordering/direction, design-vs-observed attributes, date/time precision, pressure/flow/length/diameter validation and non-inference rules.
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

- [docs/roadmap/data-provisioning.md:109](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L109)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:109 | HDP-008 | docs(data-provisioning): define pipeline and engineering normalization rules | docs/data-provisioning/engineering-rules.md`: units and conversions with dimensional tests, commodity/equipment controlled vocabularies, null vs zero, segment ordering/direction, design-vs-observed attributes, date/time precision, pressure/flow/length/diameter validation and non-inference rules.
~~~~~~~~

<a id="hapi-132"></a>
## HAPI-132 — define identifier and dependency mapping

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-009

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

define identifier and dependency mapping

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: define identifier and dependency mapping.

### 4. Functional Requirements

docs/data-provisioning/identifier-dependencies.md: legacy source-qualified key → stable Hidra ID approach, composite keys/collisions, external-reference reuse, duplicate resolution, FK load DAG and cross-context boundaries, snapshot versioning and safe retries. Preserve lineage; no positional/spreadsheet-row-number IDs unless a verified stable business key.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- docs/data-provisioning/identifier-dependencies.md: legacy source-qualified key → stable Hidra ID approach, composite keys/collisions, external-reference reuse, duplicate resolution, FK load DAG and cross-context boundaries, snapshot versioning and safe retries. Preserve lineage; no positional/spreadsheet-row-number IDs unless a verified stable business key.
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

- [docs/roadmap/data-provisioning.md:110](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L110)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:110 | HDP-009 | docs(data-provisioning): define identifier and dependency mapping | docs/data-provisioning/identifier-dependencies.md`: legacy source-qualified key → stable Hidra ID approach, composite keys/collisions, external-reference reuse, duplicate resolution, FK load DAG and cross-context boundaries, snapshot versioning and safe retries. Preserve lineage; no positional/spreadsheet-row-number IDs unless a verified stable business key.
~~~~~~~~

<a id="hapi-133"></a>
## HAPI-133 — approve reproducible provisioning design

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-010

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

approve reproducible provisioning design

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: approve reproducible provisioning design.

### 4. Functional Requirements

docs/data-provisioning/import-design.md: decide existing integration ports vs governed module-specific bootstrap; separate source extraction, validation, quarantine, transformation and write stages; explicit transaction boundaries, dry run, idempotency, upsert policy, audit/reconciliation, rollback and no production-write default. Confirm exact code/data/test file allowlists and deployment approvals for HDP-011 onward by an approved roadmap amendment if necessary. G2 signoff.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- docs/data-provisioning/import-design.md: decide existing integration ports vs governed module-specific bootstrap; separate source extraction, validation, quarantine, transformation and write stages; explicit transaction boundaries, dry run, idempotency, upsert policy, audit/reconciliation, rollback and no production-write default. Confirm exact code/data/test file allowlists and deployment approvals for HDP-011 onward by an approved roadmap amendment if necessary. G2 signoff.
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

- [docs/roadmap/data-provisioning.md:111](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L111)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:111 | HDP-010 | docs(data-provisioning): approve reproducible provisioning design | docs/data-provisioning/import-design.md`: decide existing integration ports vs governed module-specific bootstrap; separate source extraction, validation, quarantine, transformation and write stages; explicit transaction boundaries, dry run, idempotency, upsert policy, audit/reconciliation, rollback and no production-write default. Confirm exact code/data/test file allowlists and deployment approvals for HDP-011 onward by an approved roadmap amendment if necessary. G2 signoff.
~~~~~~~~

<a id="hapi-134"></a>
## HAPI-134 — add sanitized source fixtures and mapping contracts

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-011

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add sanitized source fixtures and mapping contracts

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: add sanitized source fixtures and mapping contracts.

### 4. Functional Requirements

Minimal non-sensitive, provenance-linked test fixtures and contract tests for supported source formats, nulls, date/number locales, duplicate keys, bad geocodes, malformed relations, conversion edge cases and excluded/sensitive rows; no use of confidential production datasets in CI.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- Minimal non-sensitive, provenance-linked test fixtures and contract tests for supported source formats, nulls, date/number locales, duplicate keys, bad geocodes, malformed relations, conversion edge cases and excluded/sensitive rows; no use of confidential production datasets in CI.
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

- [docs/roadmap/data-provisioning.md:119](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L119)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:119 | HDP-011 | test(data-provisioning): add sanitized source fixtures and mapping contracts | Minimal non-sensitive, provenance-linked test fixtures and contract tests for supported source formats, nulls, date/number locales, duplicate keys, bad geocodes, malformed relations, conversion edge cases and excluded/sensitive rows; no use of confidential production datasets in CI.
~~~~~~~~

<a id="hapi-135"></a>
## HAPI-135 — add deterministic extraction and quarantine

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-012

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add deterministic extraction and quarantine

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: add deterministic extraction and quarantine.

### 4. Functional Requirements

Approved loader extracts chosen source snapshots as data, never executes legacy SQL or scripts; emits typed staging records, origin and source digest; produces machine-readable rejects with reason and original source reference, no silent drop.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- Approved loader extracts chosen source snapshots as data, never executes legacy SQL or scripts; emits typed staging records, origin and source digest; produces machine-readable rejects with reason and original source reference, no silent drop.
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

- [docs/roadmap/data-provisioning.md:120](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L120)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:120 | HDP-012 | feat(data-provisioning): add deterministic extraction and quarantine | Approved loader extracts chosen source snapshots as **data**, never executes legacy SQL or scripts; emits typed staging records, origin and source digest; produces machine-readable rejects with reason and original source reference, no silent drop.
~~~~~~~~

<a id="hapi-136"></a>
## HAPI-136 — add geographic and reference transformations

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-013

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add geographic and reference transformations

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: add geographic and reference transformations.

### 4. Functional Requirements

Approved Wilaya/Commune and stable catalog mappings, source-qualified IDs, alias dictionaries, coordinate/CRS validation and reversible mapping artifacts; tests demonstrate no fabricated or cross-wilaya values.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- Approved Wilaya/Commune and stable catalog mappings, source-qualified IDs, alias dictionaries, coordinate/CRS validation and reversible mapping artifacts; tests demonstrate no fabricated or cross-wilaya values.
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

- [docs/roadmap/data-provisioning.md:121](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L121)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:121 | HDP-013 | feat(data-provisioning): add geographic and reference transformations | Approved Wilaya/Commune and stable catalog mappings, source-qualified IDs, alias dictionaries, coordinate/CRS validation and reversible mapping artifacts; tests demonstrate no fabricated or cross-wilaya values.
~~~~~~~~

<a id="hapi-137"></a>
## HAPI-137 — add pipeline and facility transformations

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-014

Priority: P3

Implementation Status: PARTIAL

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add pipeline and facility transformations

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: add pipeline and facility transformations.

### 4. Functional Requirements

Validated PipelineSystem/Pipeline/PipelineSegment/Facility/TopologyNode/TopologyConnection candidates using actual target contracts; units, directed relations, geometry, distinct physical/organizational entities and unresolvable FKs fail closed.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`, `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java`
- `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java`

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

- Validated PipelineSystem/Pipeline/PipelineSegment/Facility/TopologyNode/TopologyConnection candidates using actual target contracts; units, directed relations, geometry, distinct physical/organizational entities and unresolvable FKs fail closed.
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

- [docs/roadmap/data-provisioning.md:122](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L122)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:122 | HDP-014 | feat(data-provisioning): add pipeline and facility transformations | Validated `PipelineSystem`/`Pipeline`/`PipelineSegment`/`Facility`/`TopologyNode`/`TopologyConnection` candidates using actual target contracts; units, directed relations, geometry, distinct physical/organizational entities and unresolvable FKs fail closed.
~~~~~~~~

<a id="hapi-138"></a>
## HAPI-138 — add dry-run and idempotent import orchestration

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-015

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add dry-run and idempotent import orchestration

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: add dry-run and idempotent import orchestration.

### 4. Functional Requirements

Use existing applicable application/integration ports if sufficient; otherwise implement only independently approved module-owned adapters. Dry-run by default, validated dependency-order transactions, existing-record policy, source snapshot marker, same-source rerun neutrality and safe failure/rollback. Do not mutate another context's tables.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- Use existing applicable application/integration ports if sufficient; otherwise implement only independently approved module-owned adapters. Dry-run by default, validated dependency-order transactions, existing-record policy, source snapshot marker, same-source rerun neutrality and safe failure/rollback. Do not mutate another context's tables.
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

- [docs/roadmap/data-provisioning.md:123](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L123)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:123 | HDP-015 | feat(data-provisioning): add dry-run and idempotent import orchestration | Use existing applicable application/integration ports if sufficient; otherwise implement only independently approved module-owned adapters. Dry-run by default, validated dependency-order transactions, existing-record policy, source snapshot marker, same-source rerun neutrality and safe failure/rollback. Do not mutate another context's tables.
~~~~~~~~

<a id="hapi-139"></a>
## HAPI-139 — provision approved geographic and reference datasets

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-016

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

provision approved geographic and reference datasets

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: provision approved geographic and reference datasets.

### 4. Functional Requirements

Load approved static geographical and controlled reference values through the HDP-015 mechanism against current Flyway schema; validate known code/name/ID mappings, row counts and FK constraints. Any Flyway seed artifact requires new approved filename after inspecting current versions; never replace an applied migration.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- Load approved static geographical and controlled reference values through the HDP-015 mechanism against current Flyway schema; validate known code/name/ID mappings, row counts and FK constraints. Any Flyway seed artifact requires new approved filename after inspecting current versions; never replace an applied migration.
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

- [docs/roadmap/data-provisioning.md:124](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L124)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:124 | HDP-016 | db(data-provisioning): provision approved geographic and reference datasets | Load approved static geographical and controlled reference values through the HDP-015 mechanism against current Flyway schema; validate known code/name/ID mappings, row counts and FK constraints. Any Flyway seed artifact requires **new** approved filename after inspecting current versions; never replace an applied migration.
~~~~~~~~

<a id="hapi-140"></a>
## HAPI-140 — provision approved physical network master data

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-017

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

provision approved physical network master data

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: provision approved physical network master data.

### 4. Functional Requirements

Load pipeline systems, pipelines, segments, physical facilities, nodes/connections and approved related catalogs in dependency order; assert endpoints, ownership, uniqueness and complete legacy-ID reconciliation. No inferred physical connectivity.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- Load pipeline systems, pipelines, segments, physical facilities, nodes/connections and approved related catalogs in dependency order; assert endpoints, ownership, uniqueness and complete legacy-ID reconciliation. No inferred physical connectivity.
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

- [docs/roadmap/data-provisioning.md:125](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L125)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:125 | HDP-017 | db(data-provisioning): provision approved physical network master data | Load pipeline systems, pipelines, segments, physical facilities, nodes/connections and approved related catalogs in dependency order; assert endpoints, ownership, uniqueness and complete legacy-ID reconciliation. No inferred physical connectivity.
~~~~~~~~

<a id="hapi-141"></a>
## HAPI-141 — verify master-data imports and replay safety

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-018

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

verify master-data imports and replay safety

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: verify master-data imports and replay safety.

### 4. Functional Requirements

Fresh PostgreSQL + Flyway + Testcontainers integration tests, full mapped-source row reconciliation, exact duplicate/replay comparison, transactional rollback test, FK and attribute validations, and G3 review. No production DB used.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- Fresh PostgreSQL + Flyway + Testcontainers integration tests, full mapped-source row reconciliation, exact duplicate/replay comparison, transactional rollback test, FK and attribute validations, and G3 review. No production DB used.
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

- [docs/roadmap/data-provisioning.md:126](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L126)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:126 | HDP-018 | test(data-provisioning): verify master-data imports and replay safety | Fresh PostgreSQL + Flyway + Testcontainers integration tests, full mapped-source row reconciliation, exact duplicate/replay comparison, transactional rollback test, FK and attribute validations, and G3 review. No production DB used.
~~~~~~~~

<a id="hapi-142"></a>
## HAPI-142 — disposition operational and historical sources

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-019

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

disposition operational and historical sources

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: disposition operational and historical sources.

### 4. Functional Requirements

docs/data-provisioning/operational-disposition.md: specifically evaluate Fiche Passation, Plan 2026 BSR, hyflo-progress, historical SQL and telemetry for true event time, approval status, provenance, permission/privacy, retention and their correct owners. Decide separately import/defer/archive. Do not import them during reference-data tasks. Any authorized operational load needs a separate module-specific roadmap and tests.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- docs/data-provisioning/operational-disposition.md: specifically evaluate Fiche Passation, Plan 2026 BSR, hyflo-progress, historical SQL and telemetry for true event time, approval status, provenance, permission/privacy, retention and their correct owners. Decide separately import/defer/archive. Do not import them during reference-data tasks. Any authorized operational load needs a separate module-specific roadmap and tests.
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

- [docs/roadmap/data-provisioning.md:132](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L132)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:132 | HDP-019 | docs(data-provisioning): disposition operational and historical sources | docs/data-provisioning/operational-disposition.md`: specifically evaluate `Fiche Passation`, `Plan 2026 BSR`, `hyflo-progress`, historical SQL and telemetry for true event time, approval status, provenance, permission/privacy, retention and their correct owners. Decide separately import/defer/archive. **Do not import them during reference-data tasks.** Any authorized operational load needs a separate module-specific roadmap and tests.
~~~~~~~~

<a id="hapi-143"></a>
## HAPI-143 — add end-to-end reconciliation and security gates

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-020

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

add end-to-end reconciliation and security gates

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: add end-to-end reconciliation and security gates.

### 4. Functional Requirements

Automated per-source and per-target counts, mapped/rejected/deferred totals, FK/orphan report, topology consistency, accuracy spot checks, forbidden-secret and sensitive-data scans, ArchUnit and approved staging-to-target correctness tests. Every difference has a tracked explanation.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- Automated per-source and per-target counts, mapped/rejected/deferred totals, FK/orphan report, topology consistency, accuracy spot checks, forbidden-secret and sensitive-data scans, ArchUnit and approved staging-to-target correctness tests. Every difference has a tracked explanation.
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

- [docs/roadmap/data-provisioning.md:133](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L133)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:133 | HDP-020 | test(data-provisioning): add end-to-end reconciliation and security gates | Automated per-source and per-target counts, mapped/rejected/deferred totals, FK/orphan report, topology consistency, accuracy spot checks, forbidden-secret and sensitive-data scans, ArchUnit and approved staging-to-target correctness tests. Every difference has a tracked explanation.
~~~~~~~~

<a id="hapi-144"></a>
## HAPI-144 — document import operations and rollback

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-021

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

document import operations and rollback

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: document import operations and rollback.

### 4. Functional Requirements

docs/data-provisioning/runbook.md: pinned source SHAs/checksums, required roles/secrets, nonproduction dry-run, ordered execution, environment gates, backup/restore, repeatability, schema-version compatibility, failure/rollback, and signed exception workflow. Never present deletion of production data as an automatic rollback.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- docs/data-provisioning/runbook.md: pinned source SHAs/checksums, required roles/secrets, nonproduction dry-run, ordered execution, environment gates, backup/restore, repeatability, schema-version compatibility, failure/rollback, and signed exception workflow. Never present deletion of production data as an automatic rollback.
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

- [docs/roadmap/data-provisioning.md:134](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L134)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:134 | HDP-021 | docs(data-provisioning): document import operations and rollback | docs/data-provisioning/runbook.md`: pinned source SHAs/checksums, required roles/secrets, nonproduction dry-run, ordered execution, environment gates, backup/restore, repeatability, schema-version compatibility, failure/rollback, and signed exception workflow. Never present deletion of production data as an automatic rollback.
~~~~~~~~

<a id="hapi-145"></a>
## HAPI-145 — finalize data migration acceptance

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HDP-022

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `platform` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

finalize data migration acceptance

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- DISCOVERY_REQUIRED — no task-specific existing artifact was independently matched.

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: finalize data migration acceptance.

### 4. Functional Requirements

docs/data-provisioning/acceptance.md and this roadmap's execution status: source-to-target coverage, decisions, reproducible test evidence, accepted quarantine, security review, complete clean-tree and CI results, explicit release/no-release decision. G4 complete before production deployment; production execution itself is separately authorized.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `platform`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

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

- docs/data-provisioning/acceptance.md and this roadmap's execution status: source-to-target coverage, decisions, reproducible test evidence, accepted quarantine, security review, complete clean-tree and CI results, explicit release/no-release decision. G4 complete before production deployment; production execution itself is separately authorized.
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

- [docs/roadmap/data-provisioning.md:135](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/data-provisioning.md#L135)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/data-provisioning.md:135 | HDP-022 | docs(data-provisioning): finalize data migration acceptance | docs/data-provisioning/acceptance.md` and this roadmap's execution status: source-to-target coverage, decisions, reproducible test evidence, accepted quarantine, security review, complete clean-tree and CI results, explicit release/no-release decision. G4 complete before production deployment; production execution itself is separately authorized.
~~~~~~~~

<a id="hapi-153"></a>
## HAPI-153 — scan documents domain models

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMC-008

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 4 documents model files.

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve document versions/content and typed engineering exchange with validation and provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 17. HMC-008 — Documents module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `documents`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 75  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Documents model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| Document | record | 21 | `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java` | Extracted |
| DocumentStorageObject | record | 14 | `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java` | Extracted |
| DocumentTargetLink | record | 14 | `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java` | Extracted |
| DocumentVersion | record | 26 | `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java` | Extracted |

### Documents fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| Document | 1 | id | String |
| Document | 2 | code | String |
| Document | 3 | titleAr | String |
| Document | 4 | titleFr | String |
| Document | 5 | titleEn | String |
| Document | 6 | documentTypeId | String |
| Document | 7 | documentCategoryId | String |
| Document | 8 | classificationId | String |
| Document | 9 | confidentialityLevel | int |
| Document | 10 | status | DocumentStatus |
| Document | 11 | currentVersionId | String |
| Document | 12 | ownerModule | String |
| Document | 13 | ownerTargetTypeCode | String |
| Document | 14 | ownerTargetId | String |
| Document | 15 | ownerTargetCodeSnapshot | String |
| Document | 16 | ownerTargetLabelSnapshot | String |
| Document | 17 | createdByActorId | String |
| Document | 18 | createdByDisplayNameSnapshot | String |
| Document | 19 | createdAt | Instant |
| Document | 20 | updatedAt | Instant |
| Document | 21 | archivedAt | Instant |
| DocumentStorageObject | 1 | id | String |
| DocumentStorageObject | 2 | storageProviderId | String |
| DocumentStorageObject | 3 | bucketOrContainer | String |
| DocumentStorageObject | 4 | objectKey | String |
| DocumentStorageObject | 5 | objectUri | String |
| DocumentStorageObject | 6 | encrypted | boolean |
| DocumentStorageObject | 7 | encryptionKeyReference | String |
| DocumentStorageObject | 8 | contentLengthBytes | long |
| DocumentStorageObject | 9 | contentType | String |
| DocumentStorageObject | 10 | checksumAlgorithm | String |
| DocumentStorageObject | 11 | checksumValue | String |
| DocumentStorageObject | 12 | storageStatus | DocumentStorageStatus |
| DocumentStorageObject | 13 | createdAt | Instant |
| DocumentStorageObject | 14 | verifiedAt | Instant |
| DocumentTargetLink | 1 | id | String |
| DocumentTargetLink | 2 | documentId | String |
| DocumentTargetLink | 3 | documentVersionId | String |
| DocumentTargetLink | 4 | targetModule | String |
| DocumentTargetLink | 5 | targetTypeCode | String |
| DocumentTargetLink | 6 | targetId | String |
| DocumentTargetLink | 7 | targetCodeSnapshot | String |
| DocumentTargetLink | 8 | targetLabelSnapshot | String |
| DocumentTargetLink | 9 | linkRoleId | String |
| DocumentTargetLink | 10 | primaryLink | boolean |
| DocumentTargetLink | 11 | linkedByActorId | String |
| DocumentTargetLink | 12 | linkedAt | Instant |
| DocumentTargetLink | 13 | unlinkedAt | Instant |
| DocumentTargetLink | 14 | active | boolean |
| DocumentVersion | 1 | id | String |
| DocumentVersion | 2 | documentId | String |
| DocumentVersion | 3 | versionNumber | int |
| DocumentVersion | 4 | versionLabel | String |
| DocumentVersion | 5 | titleAr | String |
| DocumentVersion | 6 | titleFr | String |
| DocumentVersion | 7 | titleEn | String |
| DocumentVersion | 8 | description | String |
| DocumentVersion | 9 | storageObjectId | String |
| DocumentVersion | 10 | mimeType | String |
| DocumentVersion | 11 | originalFilename | String |
| DocumentVersion | 12 | fileExtension | String |
| DocumentVersion | 13 | fileSizeBytes | long |
| DocumentVersion | 14 | checksumAlgorithm | String |
| DocumentVersion | 15 | checksumValue | String |
| DocumentVersion | 16 | languageCode | String |
| DocumentVersion | 17 | documentDate | LocalDate |
| DocumentVersion | 18 | effectiveFrom | LocalDate |
| DocumentVersion | 19 | effectiveTo | LocalDate |
| DocumentVersion | 20 | versionStatus | DocumentVersionStatus |
| DocumentVersion | 21 | uploadedByActorId | String |
| DocumentVersion | 22 | uploadedByDisplayNameSnapshot | String |
| DocumentVersion | 23 | uploadedAt | Instant |
| DocumentVersion | 24 | approvedByWorkflowInstanceId | String |
| DocumentVersion | 25 | approvedAt | Instant |
| DocumentVersion | 26 | supersededByVersionId | String |

### HMC-008 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 75
zero-field models    = 0
```

The scan records only declarations under `documents/domain/model`. Documents domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, storage mapping, or database column metadata was mixed into this module scan.

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

- `4a2980887b31dfd1a1ec97472601f82a76ec10b6` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:136](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L136)
- [docs/roadmap/model-field-catalogue.md:895](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L895)
- [docs/roadmap/model-field-catalogue.md:994](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L994)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:136 | HMC-008 | docs(catalogue): scan documents domain models | Scan all 4 documents model files. | **Completed** — 4/4 models, 75 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-183"></a>
## HAPI-183 — remediate semantic review DocumentStorageObject

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMR-008

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review DocumentStorageObject

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentStorageObjectJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
- `src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentStorageObjectSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve document versions/content and typed engineering exchange with validation and provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`, `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentStorageObjectJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentStorageObjectJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
- `src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentStorageObjectSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-008 — documents.DocumentStorageObject

- Source review: `HMSR-008`
- Exact commit: `fix(documents): remediate semantic review DocumentStorageObject`
- Status: **Completed** — storage-provider identity is configurable and validated against an active `DOCUMENT_STORAGE_PROVIDER` entry before persistence; binary/storage-object metadata fails closed on missing checksum/integrity/creation data; object keys reject URI/signed-credential material; encryption-key metadata rejects credential-bearing values; additive database checks enforce nonnegative length, required metadata, and opaque object keys; failed provider validation deletes the newly written binary.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Documents.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentStorageObjectJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
  - `src/main/resources/db/migration/V20261004_008__hmr_008_documents_document_storage_object.sql`
  - `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentStorageObjectSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DocumentStorageObjectSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Storage-provider catalog identity mismatch / missing validation:** HRA-111 requires `storageProviderId` to resolve to `hidra_documents_catalog_entry(id)`, and the DDD assigns it to `DOCUMENT_STORAGE_PROVIDER`. The current local adapter returns the hard-coded value `local-filesystem`. No current seed/catalog evidence found during HMSR-008 proves that `local-filesystem` is an existing catalog-entry **ID** or that it belongs to the `DOCUMENT_STORAGE_PROVIDER` family. The upload path performs no catalog-family/active-entry validation before persistence.
  2. **Required storage-integrity metadata contract:** the DDD and schema require `contentType`, `checksumAlgorithm` and `checksumValue`. The current local adapter/service produce them, but `DocumentBinaryStoragePort.StoredBinary` has no constructor validation and the domain record itself does not reject null/blank checksum/content-type values. A future storage adapter can therefore satisfy the Java interface while producing semantically invalid metadata that fails only at persistence or propagates invalid state.
  3. **Required creation metadata boundary:** `createdAt` is required by the DDD/JPA/schema but is not guarded by the domain constructor. The current service supplies it, so this is not a current local-upload failure, but the target baseline needs one deliberate enforcement boundary rather than relying on every caller to remember the persistence constraint.
  4. **Secret/opaque-object-key policy:** the DDD explicitly says object keys must not contain credentials or signed URLs and encryption-key metadata must be reference-only. The current local adapter is safe because it derives `objectKey` from an opaque generated ID, but the outbound storage contract does not state or validate the same guarantee for other providers. Any S3/MinIO/SharePoint/DMS adapter must return stable non-secret metadata rather than pre-signed/credential-bearing values.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentStorageObjectSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `980b86887206bf5d70e1116cce751b82d947bba1` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Documents.md:971](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Documents.md#L971)
- [docs/roadmap/model-semantic-remediation.md:154](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L154)
- [docs/roadmap/model-semantic-remediation.md:698](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L698)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:154 | HMR-008 | HMSR-008 | documents | DocumentStorageObject | — | — | fix(documents): remediate semantic review DocumentStorageObject | **Completed**
~~~~~~~~

<a id="hapi-184"></a>
## HAPI-184 — amend DocumentStorageObject remediation scope

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMR-008A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

amend DocumentStorageObject remediation scope

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve document versions/content and typed engineering exchange with validation and provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`, `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`, `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-008A — documents storage-contract scope amendment

- Source: HMR-008 / HMSR-008.
- Exact commit: `docs(model-remediation): amend DocumentStorageObject remediation scope`
- Status: **Completed**
- Type: documentation-only scope correction; no production mutation.
- Evidence:
  - `DocumentBinaryStoragePort.StoredBinary` has no constructor validation for provider identity, object key, checksum algorithm/value, or opaque secret-free metadata.
  - `DocumentContentTransferService` trusts the returned binary metadata before creating `DocumentStorageObject`.
  - `LocalDocumentBinaryStorageAdapter` emits hard-coded `storageProviderId = "local-filesystem"`; HMSR-008 found no evidence that this literal is an existing Documents catalog-entry ID in the `DOCUMENT_STORAGE_PROVIDER` family.
- HMR-008 exact write allowlist is amended to additionally authorize:
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
  - `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
- Acceptance:
  1. storage adapters must return nonblank provider identity, opaque non-secret object key, checksum algorithm/value, and valid nonnegative length;
  2. object keys and encryption-key metadata must reject credential-bearing/signed-URL material rather than merely documenting a convention;
  3. local storage must no longer rely on an unverified hard-coded catalog identity; its configured provider ID is still validated against active `DOCUMENT_STORAGE_PROVIDER` membership before persistence;
  4. HMR-008 remains responsible for domain `contentType`/checksum/`createdAt` integrity and additive persistence constraints;
  5. no storage-provider catalog seed/value may be invented in HMR-008.


~~~~~~~~

### 7. API Contracts

No new API is part of this bounded documentation/historical outcome. Existing owner operations are listed in the runtime contract inventory below; no proposed endpoint is relabeled as implemented.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `f9094ca097ef0002da26083b90d8bb8d217b7f84` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:735](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L735)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-261"></a>
## HAPI-261 — remediate semantic review Document

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMR-067

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review Document

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
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
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/target/DocumentsOwnedTargetLookup.java`
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
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentsCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupService.java`
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
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentsCatalogEligibilityAdapter.java`
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
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/DocumentsPlanningTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/DocumentsTopologyTargetLookup.java`
- `src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/DocumentsPlanningTargetLookupTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/DocumentsTopologyTargetLookupTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentContentTransferException.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsBoundaryViolationException.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsDomainException.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/exception/InvalidDocumentValueException.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/in/LinkDocumentToTargetUseCase.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/in/RegisterDocumentUseCase.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentBinaryVersionUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentAuditEventPort.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`, `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupService.java`, `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`, `src/main/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/DocumentsInfrastructure.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/configuration/DocumentsModuleConfiguration.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/DocumentsExternalReferenceResolver.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/NoopDocumentsExternalReferenceResolver.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
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
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/target/DocumentsOwnedTargetLookup.java`
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
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentsCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupService.java`
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
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentsCatalogEligibilityAdapter.java`
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
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/DocumentsPlanningTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/DocumentsTopologyTargetLookup.java`
- `src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/DocumentsPlanningTargetLookupTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/DocumentsTopologyTargetLookupTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-067 — documents.Document

- Source review: `HMSR-079`
- Exact commit: `fix(documents): remediate semantic review Document`
- Status: **Completed — CI pending**
- Batch 9 implementation: Required title/creator display, active exact document catalogs, code uniqueness and same-document current-version pointers enforced. Registration binds authenticated eligible Identity actor and canonical owner snapshots; neutral registry supports Topology/Planning and denies missing/ambiguous owners. Forward V20261007_003; 11 focused methods passed with temporary APIs, four PostgreSQL cases added. Compile/focused Maven blocked before compilation by uncached Boot 4.1.1 parent; full CI pending.
- SCC: SCC-05
- Recorded upstream HMS dependencies: documents.DocumentVersion
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Documents.md`
  - `docs/roadmap/model-semantic-remediation.md`
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
  - `src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql`
  - `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
  - `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DocumentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce DDD-required `titleFr` and `createdByDisplayNameSnapshot` before persistence.**
  2. **Enforce PostgreSQL uniqueness for `Document.code`.**
  3. **Enforce exact catalog-family eligibility** for `documentTypeId`, `classificationId`, and populated `documentCategoryId`, including active/eligible semantics.
  4. **Protect `currentVersionId` and same-document membership.** When populated, it must reference an existing DocumentVersion belonging to the same Document.
  5. **Validate creator and populated owner-target references through owner-controlled contracts** while preserving scalar IDs/snapshots and avoiding cross-module database FKs.

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
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java` — exact-head oracle suite 12 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/planning/application/service/DocumentsPlanningTargetLookupTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/DocumentsTopologyTargetLookupTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `4576f389f85b3a264b1de3363c63c91e25650d0d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:102](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L102)
- [doc/model-remediation/RECONCILIATION.md:13714](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13714)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:3036](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L3036)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15454](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15454)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:101](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L101)
- [docs/data definition/Documents.md:994](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Documents.md#L994)
- [docs/roadmap/model-semantic-remediation.md:233](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L233)
- [docs/roadmap/model-semantic-remediation.md:3676](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3676)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:233 | HMR-067 | HMSR-079 | documents | Document | SCC-05 | documents.DocumentVersion | fix(documents): remediate semantic review Document | Completed — CI pending
doc/model-remediation/RECONCILIATION.md:102 | HMR-067 | HMSR-079 | documents.Document | COMPLETED — CI #583 GREEN | Required title/creator display, active exact document catalogs, code uniqueness and same-document current-version pointers enforced. Registration binds authenticated eligible Identity actor and canonical owner snapshots; neutral registry supports Topology/Planning and denies missing/ambiguous owners. Forward V20261007_003; 11 focused methods passed with temporary APIs, four PostgreSQL cases added. Compile/focused Maven blocked before compilation by uncached Boot 4.1.1 parent; full CI #583 passed.
doc/model-remediation/RECONCILIATION.md:13714 | HMR-067 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:101 | HMR-067 | HMSR-079 | [documents.Document](../domain/SEMANTIC_DECISIONS.md#documents-document) | [DocumentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java) | [V20261007_003__hmr_067_documents_document](../../src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql); [DocumentsActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15454 | HMR-067 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-262"></a>
## HAPI-262 — remediate semantic review DocumentVersion

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMR-068

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review DocumentVersion

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentVersionRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentVersionResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentVersionCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionContentDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/documents/DocumentsApprovalReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`, `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryService.java`, `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentVersionRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentVersionResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentVersionCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionContentDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/documents/DocumentsApprovalReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-068 — documents.DocumentVersion

- Source review: `HMSR-080`
- Exact commit: `fix(documents): remediate semantic review DocumentVersion`
- Status: **Completed — CI pending**
- Batch 9 implementation: Required upload metadata, positive per-document unique numbers, nullable existing supersession and owner-controlled Identity/Workflow references enforced. Generic upload derives authenticated uploader display. Binary prevalidates metadata and registers known-rollback new-blob cleanup; failed cleanup preserves original error, unknown commit outcome preserves content and logs reconciliation. Forward V20261007_004; 10 focused owner/version/cleanup methods passed with temporary APIs; nine combined PostgreSQL cases include transactional storage rollback and confirmed commit failure. Local focused Maven blocked by uncached Boot 4.1.1 parent; full CI pending.
- SCC: SCC-05
- Recorded upstream HMS dependencies: documents.Document, documents.DocumentStorageObject, documents.DocumentVersion, workflow.WorkflowInstance
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/in/WorkflowQueryUseCase.java; workflow:src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowEligibilityLookupPort.java
- Exact write allowlist:
  - `docs/data definition/Documents.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentVersionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentVersionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentVersionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionContentDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`
  - `src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql`
  - `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DocumentVersionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce all DDD-required textual upload metadata before persistence:** `mimeType`, `originalFilename`, `checksumAlgorithm`, `checksumValue`, and `uploadedByDisplayNameSnapshot`.
  2. **Enforce version-number semantics:** require `versionNumber >= 1` and add concurrency-safe PostgreSQL uniqueness for `(document_id, version_number)`.
  3. **Protect populated supersession lineage.** `supersededByVersionId` must resolve to an existing Documents-owned version while retaining the existing self-reference prohibition.
  4. **Validate optional Workflow approval references through a Workflow-owned contract** when approval metadata is populated; do not introduce a cross-module Workflow FK.
  5. **Validate mandatory uploader identity through an Identity-owned contract** while preserving the uploader display snapshot and avoiding a cross-module Identity FK.

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
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java` — exact-head oracle suite 12 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `ce60def926a97c3b45036ea40ee912dcdf2168a0` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:103](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L103)
- [doc/model-remediation/RECONCILIATION.md:13715](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13715)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:3158](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L3158)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15455](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15455)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:102](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L102)
- [docs/data definition/Documents.md:998](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Documents.md#L998)
- [docs/roadmap/model-semantic-remediation.md:234](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L234)
- [docs/roadmap/model-semantic-remediation.md:3801](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3801)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:234 | HMR-068 | HMSR-080 | documents | DocumentVersion | SCC-05 | documents.Document, documents.DocumentStorageObject, documents.DocumentVersion, workflow.WorkflowInstance | fix(documents): remediate semantic review DocumentVersion | Completed — CI pending
doc/model-remediation/RECONCILIATION.md:103 | HMR-068 | HMSR-080 | documents.DocumentVersion | COMPLETED — CI #583 GREEN | Required upload metadata, positive per-document unique numbers, nullable existing supersession and owner-controlled Identity/Workflow references enforced. Generic upload derives authenticated uploader display. Binary prevalidates metadata and registers known-rollback new-blob cleanup; failed cleanup preserves original error, unknown commit outcome preserves content and logs reconciliation. Forward V20261007_004; 10 focused owner/version/cleanup methods passed with temporary APIs; nine combined PostgreSQL cases include transactional storage rollback and confirmed commit failure. Local focused Maven blocked by uncached Boot 4.1.1 parent; full CI #583 passed.
doc/model-remediation/RECONCILIATION.md:13715 | HMR-068 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:102 | HMR-068 | HMSR-080 | [documents.DocumentVersion](../domain/SEMANTIC_DECISIONS.md#documents-documentversion) | [DocumentVersionSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java) | [V20261007_004__hmr_068_documents_document_version](../../src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql); [DocumentsActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java); [DocumentsApprovalReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/documents/DocumentsApprovalReferenceContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15455 | HMR-068 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-278"></a>
## HAPI-278 — remediate semantic review DocumentTargetLink

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMR-084

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review DocumentTargetLink

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentTargetLinkResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentTargetLinkSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java`
- `src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentTargetLinkResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentTargetLinkSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java`
- `src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-084 — documents.DocumentTargetLink

- Source review: `HMSR-098`
- Exact commit: `fix(documents): remediate semantic review DocumentTargetLink`
- Status: **Completed — CI pending**
- Batch 9 implementation: Required target module, active exact DOCUMENT_LINK_ROLE and owner-controlled target resolution enforced. Authenticated linking actor and canonical owner snapshots replace caller identity/display claims; optional version must belong to linked document, protected by composite FK. Forward V20261007_005; four focused methods passed with temporary APIs and three PostgreSQL cases added (12 combined). Both public export registries match 34 exact packages; all five forensic scans passed with temporary APIs. Focused Maven blocked by uncached parent; full CI pending.
- SCC: —
- Recorded upstream HMS dependencies: documents.Document, documents.DocumentVersion
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Documents.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentTargetLinkResponse.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentTargetLinkSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`
  - `src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql`
  - `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DocumentTargetLinkSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce required `targetModule` before persistence**, matching the DDD and database contract.
  2. **When `documentVersionId` is supplied, validate it fail-closed and verify that the version belongs to `documentId`; add appropriate same-module persistence protection consistent with repository architecture.**
  3. **Validate `linkRoleId` against the active/eligible exact `DOCUMENT_LINK_ROLE` catalog family**, not merely generic Documents catalog-row existence.
  4. **Use the Documents-owned `DocumentTargetLookupPort` (or equivalent owner-controlled application contract) to validate polymorphic targets where live existence validation is required**, while preserving neutral scalar references and avoiding cross-module database FKs.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java` — exact-head oracle suite 12 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `303962220798924cd56e236544427cdfa22eb1f4` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:119](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L119)
- [doc/model-remediation/RECONCILIATION.md:13731](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13731)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:3191](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L3191)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15471](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15471)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:118](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L118)
- [docs/data definition/Documents.md:1002](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Documents.md#L1002)
- [docs/roadmap/model-semantic-remediation.md:255](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L255)
- [docs/roadmap/model-semantic-remediation.md:4421](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4421)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:255 | HMR-084 | HMSR-098 | documents | DocumentTargetLink | — | documents.Document, documents.DocumentVersion | fix(documents): remediate semantic review DocumentTargetLink | Completed — CI pending
doc/model-remediation/RECONCILIATION.md:119 | HMR-084 | HMSR-098 | documents.DocumentTargetLink | COMPLETED — CI #583 GREEN | Required target module, active exact DOCUMENT_LINK_ROLE and owner-controlled target resolution enforced. Authenticated linking actor and canonical owner snapshots replace caller identity/display claims; optional version must belong to linked document, protected by composite FK. Forward V20261007_005; four focused methods passed with temporary APIs and three PostgreSQL cases added (12 combined). Both public export registries match 34 exact packages; all five forensic scans passed with temporary APIs. Focused Maven blocked by uncached parent; full CI #583 passed.
doc/model-remediation/RECONCILIATION.md:13731 | HMR-084 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:118 | HMR-084 | HMSR-098 | [documents.DocumentTargetLink](../domain/SEMANTIC_DECISIONS.md#documents-documenttargetlink) | [DocumentTargetLinkSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java) | [V20261007_005__hmr_084_documents_document_target_link](../../src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql); [DocumentsActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15471 | HMR-084 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-318"></a>
## HAPI-318 — review documents DocumentStorageObject

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMSR-008

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review documents DocumentStorageObject

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve document versions/content and typed engineering exchange with validation and provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 21. HMSR-008 — documents.DocumentStorageObject review

**Decision:** REVISE  
**Review code:** HMSR-008  
**Dependency level:** 0  
**Bounded context:** documents  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 2 — `documents.DocumentVersion` and `reporting.ReportOutputArtifact`  
**Transitive dependents:** 6  
**Unresolved/non-subject references:** 1 — `storageProviderId`, now semantically resolved to a Documents-owned catalog prerequisite outside the 123 HMS subjects

### 21.1 Semantic role and ordering rationale

`DocumentStorageObject` is the Documents-owned metadata pointer to one physical binary object in a storage backend. It carries storage-provider identity, opaque object location metadata, size/content metadata, checksum/integrity metadata, encryption-reference metadata and storage lifecycle state.

It is Level 0 because none of its prerequisites are another HMS subject model. Its only upstream semantic prerequisite is the Documents-owned storage-provider catalog, which is outside the 123 HMS subject population. `DocumentVersion` depends on the storage object through `storageObjectId`, and Reporting may reference a storage object for generated output artifacts.

The bounded-context rule remains explicit: Documents owns document/storage metadata, while the binary bytes live in object storage, DMS, archive or controlled file storage. Storage credentials, secret key material and signed URLs must not become relational business metadata.

### 21.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable storage-object identifier and persistence primary key. |
| `storageProviderId` | `String` | Mandatory | Documents-owned storage-provider catalog reference; semantically belongs to `DOCUMENT_STORAGE_PROVIDER`. |
| `bucketOrContainer` | `String` | Optional | Backend bucket, container, share, archive container or equivalent logical location. |
| `objectKey` | `String` | Mandatory | Provider-specific opaque object key/path. The Documents DDD explicitly forbids credentials or signed URLs in this field. |
| `objectUri` | `String` | Optional | Optional non-secret URI/reference to the stored object. |
| `encrypted` | `boolean` | Mandatory persisted state | Whether the object is stored encrypted. |
| `encryptionKeyReference` | `String` | Optional | External key reference only; never encryption key material or secret credentials. |
| `contentLengthBytes` | `long` | Mandatory | Stored binary length in bytes. |
| `contentType` | `String` | Mandatory | Media/content type persisted with the storage object. |
| `checksumAlgorithm` | `String` | Mandatory | Algorithm used for integrity checksum, currently supplied as `SHA-256` by the local adapter. |
| `checksumValue` | `String` | Mandatory | Integrity checksum value for the stored object. |
| `storageStatus` | `DocumentStorageStatus` | Mandatory | Storage lifecycle: `AVAILABLE`, `QUARANTINED`, `ARCHIVED`, `MISSING`, or `DELETED_LOGICAL`. |
| `createdAt` | `Instant` | Mandatory in persistence | Storage-object metadata creation timestamp. |
| `verifiedAt` | `Instant` | Optional | Last integrity-verification timestamp. |

The current domain constructor already rejects blank `id`, `storageProviderId`, and `objectKey`, rejects null `storageStatus`, trims string values and converts blank optional strings to `null`.

### 21.3 Storage-provider dependency resolution

HMS-003 left `storageProviderId` unresolved because no one of the 123 subject models was a defensible target. Stronger repository evidence now resolves the semantic target without adding a subject-graph edge:

- the Documents DDD identifies `storageProviderId` as a catalog/provider reference;
- `DOCUMENT_STORAGE_PROVIDER` is a recommended Documents catalog family;
- `DocumentCatalogEntry` is retained as a Documents read/persistence model outside the HMS subject set;
- HRA-111 installs `fk_hra111_documents_014`;
- that FK points `hidra_documents_storage_object.storage_provider_id` to `hidra_documents_catalog_entry(id)` with `ON DELETE RESTRICT`.

Therefore `storageProviderId` is a **Documents-owned value/catalog dependency**, not an unresolved subject-model relationship.

### 21.4 Persistence and binary-storage boundary

The live domain and JPA models agree on all 14 declared components.

The base Documents migration makes these columns non-null:

```text
id
storage_provider_id
object_key
encrypted
content_length_bytes
content_type
checksum_algorithm
checksum_value
storage_status
created_at
```

while bucket/container, object URI, encryption key reference and verification timestamp remain nullable.

The current `DocumentContentTransferService` obtains physical-storage metadata from `DocumentBinaryStoragePort.StoredBinary`, creates `DocumentStorageObject` with status `AVAILABLE`, defaults a missing upload content type to `application/octet-stream`, and persists the storage object before creating the corresponding DocumentVersion.

The local filesystem adapter currently:

- uses provider value `local-filesystem`;
- stores objects under a controlled Documents root;
- uses the generated storage-object/reference ID as the object key;
- calculates byte length while copying;
- calculates a SHA-256 checksum;
- emits no encryption key reference because local storage is currently unencrypted.

Those implementation details are valid local-adapter behavior, but they do not by themselves establish the catalog identity contract required by the database FK.

### 21.5 Required revisions

The model role and dependency direction are correct, but the live implementation does not consistently enforce all repository-defined storage semantics strongly enough for APPROVED status.

1. **Storage-provider catalog identity mismatch / missing validation:** HRA-111 requires `storageProviderId` to resolve to `hidra_documents_catalog_entry(id)`, and the DDD assigns it to `DOCUMENT_STORAGE_PROVIDER`. The current local adapter returns the hard-coded value `local-filesystem`. No current seed/catalog evidence found during HMSR-008 proves that `local-filesystem` is an existing catalog-entry **ID** or that it belongs to the `DOCUMENT_STORAGE_PROVIDER` family. The upload path performs no catalog-family/active-entry validation before persistence.
2. **Required storage-integrity metadata contract:** the DDD and schema require `contentType`, `checksumAlgorithm` and `checksumValue`. The current local adapter/service produce them, but `DocumentBinaryStoragePort.StoredBinary` has no constructor validation and the domain record itself does not reject null/blank checksum/content-type values. A future storage adapter can therefore satisfy the Java interface while producing semantically invalid metadata that fails only at persistence or propagates invalid state.
3. **Required creation metadata boundary:** `createdAt` is required by the DDD/JPA/schema but is not guarded by the domain constructor. The current service supplies it, so this is not a current local-upload failure, but the target baseline needs one deliberate enforcement boundary rather than relying on every caller to remember the persistence constraint.
4. **Secret/opaque-object-key policy:** the DDD explicitly says object keys must not contain credentials or signed URLs and encryption-key metadata must be reference-only. The current local adapter is safe because it derives `objectKey` from an opaque generated ID, but the outbound storage contract does not state or validate the same guarantee for other providers. Any S3/MinIO/SharePoint/DMS adapter must return stable non-secret metadata rather than pre-signed/credential-bearing values.

These corrections belong at the appropriate application/storage-adapter/database boundary. HMSR-008 does not require secret-detection heuristics inside the domain record, and it does not invent storage-provider catalog rows.

No production Java, JPA, Flyway, application/API contract, tests, catalog data or binary-storage data is changed by this review.

### 21.6 Lifecycle and integrity interpretation

`DocumentStorageStatus` is correctly represented as a stable technical enum:

- `AVAILABLE` — binary is expected to be retrievable;
- `QUARANTINED` — binary is retained but unavailable to normal consumers pending security/integrity handling;
- `ARCHIVED` — moved/retained in archival storage semantics;
- `MISSING` — metadata exists but physical content cannot currently be resolved;
- `DELETED_LOGICAL` — logically deleted according to Documents retention/governance rules.

Current download orchestration correctly requires both metadata status `AVAILABLE` and physical-adapter availability before returning content.

Checksum metadata is evidence for storage integrity; it does not by itself replace document-version approval, audit evidence, digital signatures, retention policy or workflow governance.

### 21.7 Operational interpretation

For SONATRACH/TRC operations, `DocumentStorageObject` may point to the physical bytes of pipeline drawings, inspection reports, certificates, operating procedures, maintenance manuals, HSE evidence, custody documentation or generated reports.

The storage object is deliberately infrastructure-facing metadata. Business modules should reference Document/DocumentVersion identities rather than learn bucket names, filesystem paths or provider credentials. Reporting may retain a stable storage-object reference for generated artifacts, but Documents remains owner of storage metadata.

### 21.8 Review conclusion

**REVISE.** `DocumentStorageObject` is the correct Documents-owned physical-storage metadata model and its two subject dependents are appropriate. The previously unresolved `storageProviderId` is now resolved as a Documents-owned catalog prerequisite outside the subject graph.

The target baseline cannot mark it APPROVED until storage-provider catalog identity/family semantics and required integrity-metadata enforcement are made consistent across the DDD, database FK, storage port and adapters. HMS reconciliation must retain these obligations for an explicitly authorized Documents correction task.

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

- `168874b6d688e30633a33a18f97cb69b6599b648` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:60](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L60)
- [docs/roadmap/model-semantic-review.md:203](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L203)
- [docs/roadmap/model-semantic-review.md:3926](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L3926)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:203 | HMSR-008 | 0 | documents | DocumentStorageObject | — | — | 2 | 6 | 1 | REVISE | Completed | docs(model-review): review documents DocumentStorageObject
doc/domain/SEMANTIC_DECISIONS.md:60 | HMSR-008 | [documents.DocumentStorageObject](#documents-documentstorageobject) | REVISE | HMR-008
~~~~~~~~

<a id="hapi-389"></a>
## HAPI-389 — review documents Document

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMSR-079

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review documents Document

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve document versions/content and typed engineering exchange with validation and provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 92. HMSR-079 — documents.Document review

**Decision:** REVISE  
**Review code:** HMSR-079  
**Dependency level:** 4  
**Bounded context:** documents  
**SCC:** SCC-05 with `documents.DocumentVersion`  
**Confirmed upstream subject dependencies:** optional current `documents.DocumentVersion`  
**Confirmed direct HMS subject dependents:** 4  
**Transitive HMS subject dependents:** 5

### 92.1 Semantic role

`Document` is the Documents-owned stable business identity for a controlled document, independent from file/content versions. It owns business code, multilingual title metadata, document type/category/classification, confidentiality ordering, lifecycle state, the current-version pointer, optional neutral business-owner attachment metadata, creator snapshot, and lifecycle timestamps.

Documents owns document metadata/versioning. Identity owns actors. Business modules own owner-target objects. The owner tuple is intentionally neutral/polymorphic and must not be converted into cross-module persistence coupling.

### 92.2 Required field semantics

The Documents DDD marks `titleFr` and `createdByDisplayNameSnapshot` required, and the live JPA/Flyway schema persists both as `NOT NULL`.

The live `Document` constructor does not enforce either field and normalizes blank values to null. `DocumentsApplicationService.registerDocument()` copies both values directly from the command.

The authoritative registration boundary must fail fast for the required French title and creator display-name snapshot rather than relying on persistence failure.

### 92.3 Document code uniqueness

The Documents DDD explicitly requires:

```text
Document.code must be unique.
```

The live schema creates only a non-unique index on `hidra_documents_document.code`, and `DocumentRepositoryPort` exposes no code lookup.

The authoritative baseline therefore requires concurrency-safe PostgreSQL uniqueness for document business code. Application duplicate detection may improve error reporting but cannot replace the database guarantee.

### 92.4 Document catalogs

The Documents DDD defines business taxonomies as catalog-backed and explicitly recommends these families for the reviewed fields:

```text
DOCUMENT_TYPE
DOCUMENT_CATEGORY
DOCUMENT_CLASSIFICATION
```

The live schema stores `documentTypeId`, optional `documentCategoryId`, and `classificationId` as scalar IDs with ordinary indexes. Repository evidence shows no same-module FKs from these fields to `hidra_documents_catalog_entry` and no exact-family eligibility checks in document registration.

The authoritative write boundary must therefore:

- require `documentTypeId` to resolve to an eligible/active `DOCUMENT_TYPE` entry;
- require `classificationId` to resolve to an eligible/active `DOCUMENT_CLASSIFICATION` entry;
- when populated, require `documentCategoryId` to resolve to an eligible/active `DOCUMENT_CATEGORY` entry.

Generic catalog-row existence alone is insufficient because it does not prove family membership.

### 92.5 Current-version pointer and SCC-05

`currentVersionId` is an optional same-module reference to `DocumentVersion`. Together with `DocumentVersion.documentId`, it forms SCC-05.

The Documents DDD explicitly states:

```text
Document.currentVersionId must refer to a version of the same document.
```

The base schema creates only an index on `current_version_id`, and repository evidence shows no same-module FK or application guard establishing either referenced-version existence or same-document correlation.

When populated, the current-version pointer must fail closed to an existing `DocumentVersion` whose `documentId` equals the owning `Document.id`. A simple FK to version ID alone would not prove the ownership correlation; implementation must preserve both existence and same-document membership.

The cyclic relationship must be introduced safely and additively without weakening the `DocumentVersion.documentId -> Document.id` ownership direction.

### 92.6 Owner-target tuple

The optional fields:

```text
ownerModule
ownerTargetTypeCode
ownerTargetId
ownerTargetCodeSnapshot
ownerTargetLabelSnapshot
```

represent a neutral business-owner attachment. The Documents DDD explicitly states that Documents must not own the business object being documented.

HMSR-079 therefore preserves this tuple as a polymorphic cross-module reference/snapshot. It must not receive a universal cross-module database FK.

Where registration accepts a populated owner tuple, validation should be delegated through owner-controlled target lookup contracts or equivalent neutral resolution, but this review does not invent a single concrete relational target because the DDD intentionally supports multiple owner modules/types.

### 92.7 Creator actor boundary

`createdByActorId` is mandatory and Identity-owned; `createdByDisplayNameSnapshot` is a historical display snapshot.

The current registration service copies both values directly from the command. Repository evidence does not show owner-controlled Identity validation in this use case.

The authoritative registration boundary should validate creator actor identity through an Identity-owned contract while retaining the snapshot. No cross-module Identity database FK should be introduced.

### 92.8 Lifecycle and physical-protection rule

`DocumentStatus` includes:

```text
DRAFT
ACTIVE
UNDER_REVIEW
APPROVED
OBSOLETE
ARCHIVED
REJECTED
QUARANTINED
DELETED_LOGICAL
```

The DDD defines document lifecycle state as Documents-owned and the domain helper `physicallyProtected()` marks APPROVED, ARCHIVED and OBSOLETE as protected.

The DDD also states that approved/archived documents must not be physically deleted through business APIs and that logical deletion must preserve auditability/retention policy.

These are lifecycle/technical states and are appropriate as an enum. HMSR-079 does not convert them to a catalog.

Repository evidence reviewed here does not show a physical-delete business API, so no additional deletion-path correction is recorded beyond preserving the DDD rule during future implementation.

### 92.9 Confidentiality level

`confidentialityLevel` is a required numeric ordering used for policy checks. The current source does not establish a closed numeric range or a deterministic mapping from `DOCUMENT_CLASSIFICATION` to confidentiality level.

HMSR-079 therefore preserves the field and does not invent a range or mapping. HMS-006 may reconcile it later if stronger Documents policy evidence appears.

### 92.10 Required revisions

Five evidence-backed obligations remain:

1. **Enforce DDD-required `titleFr` and `createdByDisplayNameSnapshot` before persistence.**

2. **Enforce PostgreSQL uniqueness for `Document.code`.**

3. **Enforce exact catalog-family eligibility** for `documentTypeId`, `classificationId`, and populated `documentCategoryId`, including active/eligible semantics.

4. **Protect `currentVersionId` and same-document membership.** When populated, it must reference an existing DocumentVersion belonging to the same Document.

5. **Validate creator and populated owner-target references through owner-controlled contracts** while preserving scalar IDs/snapshots and avoiding cross-module database FKs.

HMSR-079 does not modify production Java, JPA, Flyway, API/application contracts, tests, Documents catalogs/data, Identity, other business modules, or provisioned data.

### 92.11 Review conclusion

**REVISE.** `Document` is a coherent Documents-owned aggregate root with correct stable-identity/version separation, appropriate lifecycle enum semantics, and intentionally neutral cross-module owner-target references.

The target baseline cannot mark it APPROVED while DDD-required fields can reach persistence as null, document business-code uniqueness is unenforced, catalog families are not fail-closed, the SCC-05 current-version pointer lacks existence/same-document protection, and creator/owner-target references are trusted directly at registration.

HMS reconciliation must retain these obligations until explicitly authorized Documents production corrections resolve them or stronger repository evidence changes the governing Documents DDD.

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

- `b7198b8ec84e57ddb7755a8bdcf8690a901a08a7` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:131](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L131)
- [docs/roadmap/model-semantic-review.md:274](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L274)
- [docs/roadmap/model-semantic-review.md:14855](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L14855)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:274 | HMSR-079 | 4 | documents | Document | SCC-05 | documents.DocumentVersion | 4 | 5 | 3 | REVISE | Completed | docs(model-review): review documents Document
doc/domain/SEMANTIC_DECISIONS.md:131 | HMSR-079 | [documents.Document](#documents-document) | REVISE | HMR-067
~~~~~~~~

<a id="hapi-390"></a>
## HAPI-390 — review documents DocumentVersion

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMSR-080

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review documents DocumentVersion

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve document versions/content and typed engineering exchange with validation and provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`, `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 93. HMSR-080 — documents.DocumentVersion review

**Decision:** REVISE  
**Review code:** HMSR-080  
**Dependency level:** 4  
**Bounded context:** documents  
**SCC:** SCC-05 with `documents.Document`  
**Confirmed upstream subject dependencies:** mandatory `documents.Document`, mandatory `documents.DocumentStorageObject`, optional superseding `documents.DocumentVersion`, optional `workflow.WorkflowInstance`  
**Confirmed direct HMS subject dependents:** 3  
**Transitive HMS subject dependents:** 5

### 93.1 Semantic role

`DocumentVersion` is the Documents-owned immutable file/content revision metadata for a `Document`. It owns version numbering/labeling, storage linkage, content metadata, checksum evidence, effective dates, version lifecycle state, uploader snapshots, optional workflow approval reference, and optional supersession lineage.

Documents owns version metadata and storage-object references. Workflow owns approval process state. Identity owns uploader identity. Cross-module Workflow/Identity references must remain scalar/application-contract references rather than database foreign keys.

### 93.2 Parent document and storage-object integrity

`documentId` and `storageObjectId` are mandatory same-module references.

HRA-111 already protects both with Documents-local foreign keys:

```text
hidra_documents_document_version.document_id -> hidra_documents_document.id
hidra_documents_document_version.storage_object_id -> hidra_documents_storage_object.id
```

This correctly protects the two mandatory ownership references at the database boundary. No further basic existence correction is required for them.

### 93.3 Required upload metadata

The Documents DDD marks the following fields required:

```text
mimeType
originalFilename
fileSizeBytes
checksumAlgorithm
checksumValue
uploadedByActorId
uploadedByDisplayNameSnapshot
uploadedAt
```

The live constructor already protects `uploadedByActorId` and `uploadedAt`, but it does not reject blank/null `mimeType`, `originalFilename`, `checksumAlgorithm`, `checksumValue`, or `uploadedByDisplayNameSnapshot`.

JPA/Flyway persist those fields as `NOT NULL`, and the upload service copies values directly into the aggregate. The binary-transfer path often supplies storage-derived values, but the general `UploadDocumentVersionUseCase` remains directly callable.

The authoritative domain/application boundary must fail fast for all DDD-required textual upload metadata rather than relying on persistence failure.

HMSR-080 does not invent a minimum positive file-size rule because the current DDD only states that file size is required, not that zero-length content is forbidden.

### 93.4 Version-number semantics

The Documents DDD explicitly defines:

```text
versionNumber starting at 1
Version numbers must be unique per document.
```

It also recommends a unique PostgreSQL index on:

```text
(document_id, version_number)
```

The live domain record accepts zero or negative version numbers, and the base schema exposes only an ordinary index on `document_id`; repository evidence shows no database uniqueness constraint for the pair.

The authoritative baseline therefore requires a positive version number starting at 1 and concurrency-safe PostgreSQL uniqueness per document.

### 93.5 Supersession lineage

`supersededByVersionId` is an optional same-module self-reference. The domain already rejects direct self-reference, which is appropriate.

Repository evidence shows no same-module FK or application lookup proving that a populated superseding version exists. HRA-111 intentionally covered mandatory references, so the absence of this optional edge there is not evidence that it may remain unchecked.

When populated, `supersededByVersionId` should fail closed to an existing Documents-owned version. Current DDD wording identifies it as the version that supersedes the current version and requires previous-version history to be preserved.

This review does not invent a stronger ordering constraint or version-number comparison beyond current evidence.

### 93.6 Workflow approval boundary

`approvedByWorkflowInstanceId` is an optional Workflow-owned reference. The Documents DDD explicitly states that Workflow owns routing/decisions and Documents stores the workflow reference and document-state consequence.

Repository evidence shows the field persisted as a scalar ID and no cross-module database FK, which is architecturally correct.

When a version-approval path populates this field, the authoritative boundary should validate the workflow instance through a Workflow-owned application contract and verify that the process applies to the correct document/version context. No cross-module Workflow FK should be introduced.

### 93.7 Uploader identity boundary

`uploadedByActorId` is mandatory and Identity-owned; `uploadedByDisplayNameSnapshot` is a historical display snapshot.

The live upload service copies both directly from the command. Repository evidence does not show owner-controlled Identity validation in the reviewed path.

The authoritative upload boundary should validate uploader identity through an Identity-owned contract while retaining the snapshot. No cross-module Identity database FK should be introduced.

### 93.8 Effective-date ordering

The domain already enforces:

```text
effectiveFrom <= effectiveTo
```

when both values are present.

This matches the temporal semantics of the version metadata and requires no further correction in HMSR-080.

### 93.9 Version lifecycle and immutability

`DocumentVersionStatus` is the Documents-owned lifecycle for version state. The Documents DDD explicitly states:

```text
Approved/current versions are immutable.
Changing binary content creates a new DocumentVersion.
Superseding a current version must preserve previous version history.
```

The domain helper `immutableVersion()` recognizes APPROVED, CURRENT, SUPERSEDED and ARCHIVED as immutable states.

The reviewed application services currently create DRAFT versions and repository evidence did not identify a direct mutation path for immutable versions. HMSR-080 therefore preserves the explicit immutability rule without inventing an unobserved production defect.

Any future approval/update/supersession use case must enforce this rule at the authoritative write boundary.

### 93.10 Required revisions

Five evidence-backed obligations remain:

1. **Enforce all DDD-required textual upload metadata before persistence:** `mimeType`, `originalFilename`, `checksumAlgorithm`, `checksumValue`, and `uploadedByDisplayNameSnapshot`.

2. **Enforce version-number semantics:** require `versionNumber >= 1` and add concurrency-safe PostgreSQL uniqueness for `(document_id, version_number)`.

3. **Protect populated supersession lineage.** `supersededByVersionId` must resolve to an existing Documents-owned version while retaining the existing self-reference prohibition.

4. **Validate optional Workflow approval references through a Workflow-owned contract** when approval metadata is populated; do not introduce a cross-module Workflow FK.

5. **Validate mandatory uploader identity through an Identity-owned contract** while preserving the uploader display snapshot and avoiding a cross-module Identity FK.

HMSR-080 does not modify production Java, JPA, Flyway, API/application contracts, tests, Documents/Workflow/Identity data, or provisioned data.

### 93.11 Review conclusion

**REVISE.** `DocumentVersion` is a coherent Documents-owned immutable version model with protected mandatory parent/storage references, explicit checksum metadata, correct effective-date ordering, appropriate lifecycle semantics, and an intentionally scalar Workflow approval reference.

The target baseline cannot mark it APPROVED while required textual upload metadata can reach persistence as null, version numbers are neither positive nor unique per document, optional supersession lineage is not fail-closed, and uploader/workflow references are trusted without owner-controlled validation.

HMS reconciliation must retain these obligations until explicitly authorized Documents production corrections resolve them or stronger repository evidence changes the governing Documents DDD.

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

- `827cf2524bd7c822016cd054c31e7f2b0c858364` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:132](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L132)
- [docs/roadmap/model-semantic-review.md:275](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L275)
- [docs/roadmap/model-semantic-review.md:15008](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L15008)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:275 | HMSR-080 | 4 | documents | DocumentVersion | SCC-05 | documents.Document, documents.DocumentStorageObject, documents.DocumentVersion, workflow.WorkflowInstance | 3 | 5 | 2 | REVISE | Completed | docs(model-review): review documents DocumentVersion
doc/domain/SEMANTIC_DECISIONS.md:132 | HMSR-080 | [documents.DocumentVersion](#documents-documentversion) | REVISE | HMR-068
~~~~~~~~

<a id="hapi-408"></a>
## HAPI-408 — review documents DocumentTargetLink

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HMSR-098

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `documents` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review documents DocumentTargetLink

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Preserve document versions/content and typed engineering exchange with validation and provenance.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `documents`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`, `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 111. HMSR-098 — documents.DocumentTargetLink review

**Decision:** REVISE  
**Review code:** HMSR-098  
**Dependency level:** 5  
**Bounded context:** documents  
**Confirmed upstream subject dependencies:** mandatory `documents.Document`; optional `documents.DocumentVersion`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 0

### 111.1 Semantic role

`DocumentTargetLink` is the Documents-owned neutral association between a document (or optional specific document version) and a business object owned by another bounded context.

The governing Documents DDD explicitly requires neutral target references and forbids Documents from importing target-module domain or JPA classes.

### 111.2 Required neutral target coordinates

The DDD marks all three target coordinates required:

```text
targetModule
targetTypeCode
targetId
```

The live domain constructor already rejects blank `targetTypeCode` and `targetId`, but only normalizes `targetModule`.

JPA/Flyway persist `target_module` as `NOT NULL`.

The authoritative domain/application boundary must therefore fail fast for blank/null `targetModule` rather than relying on persistence failure.

### 111.3 Document ownership

`documentId` is mandatory and same-module.

HRA-111 already protects it with:

```text
hidra_documents_target_link.document_id
  -> hidra_documents_document.id
```

The domain constructor also rejects blank `documentId`.

No further basic document-existence correction is required for this mandatory parent reference.

### 111.4 Optional document-version integrity

`documentVersionId` is optional because a link may apply to the document in general.

When supplied, it denotes a real same-module `DocumentVersion` and must belong to the supplied `documentId`.

The live HRA-111 migration protects `document_id` and `link_role_id`, but does not show a foreign key from `hidra_documents_target_link.document_version_id` to `hidra_documents_document_version.id`.

The current `linkDocumentToTarget(...)` path also does not resolve the optional version or verify that it belongs to the supplied document.

The future production correction must therefore make a supplied version fail closed and verify same-document ownership. Same-module persistence protection may be added consistently with repository architecture.

### 111.5 Link-role catalog semantics

`linkRoleId` is mandatory and Documents-owned catalog data.

HRA-111 already protects generic Documents catalog-row existence:

```text
hidra_documents_target_link.link_role_id
  -> hidra_documents_catalog_entry.id
```

The Documents DDD defines the exact catalog family:

```text
DOCUMENT_LINK_ROLE
```

with roles such as `EVIDENCE`, `ATTACHMENT`, `PROCEDURE`, `CERTIFICATE`, `PHOTO`, `CONTRACT`, `DRAWING`, `MANUAL`, and `APPROVAL_SUPPORT`.

Generic row existence is therefore insufficient. The authoritative write boundary must validate `linkRoleId` against an active/eligible entry in the exact `DOCUMENT_LINK_ROLE` family.

### 111.6 External target validation

The DDD explicitly defines a `DocumentTargetLookupPort` as an outbound application port.

The live `DocumentsApplicationService.linkDocumentToTarget(...)` currently constructs and saves the link without invoking a target lookup/resolver.

Therefore the neutral target coordinates are persisted without any owner-controlled existence validation.

The production correction should introduce/use the Documents outbound target-lookup contract to validate `targetModule + targetTypeCode + targetId` where target existence is required. The implementation must remain polymorphic and owner-controlled and must not introduce cross-module database foreign keys.

### 111.7 Snapshot semantics

`targetCodeSnapshot` and `targetLabelSnapshot` are optional snapshots for readability/history.

They are not ownership references and must not be treated as relational keys.

### 111.8 Actor ownership

`linkedByActorId` is mandatory in the current domain/DDD and represents the actor who created the link.

Identity remains the owner of actor identity. Documents should retain the scalar actor identifier and must not add a cross-module Identity FK.

### 111.9 Link lifecycle

`primaryLink`, `linkedAt`, `unlinkedAt`, and `active` describe the link lifecycle.

The reviewed DDD does not establish additional invariant combinations such as mandatory `unlinkedAt` when inactive or uniqueness of primary links. HMSR-098 therefore does not invent those rules.

### 111.10 Required revisions

Four evidence-backed production obligations remain:

1. **Enforce required `targetModule` before persistence**, matching the DDD and database contract.
2. **When `documentVersionId` is supplied, validate it fail-closed and verify that the version belongs to `documentId`; add appropriate same-module persistence protection consistent with repository architecture.**
3. **Validate `linkRoleId` against the active/eligible exact `DOCUMENT_LINK_ROLE` catalog family**, not merely generic Documents catalog-row existence.
4. **Use the Documents-owned `DocumentTargetLookupPort` (or equivalent owner-controlled application contract) to validate polymorphic targets where live existence validation is required**, while preserving neutral scalar references and avoiding cross-module database FKs.

HMSR-098 does not modify production Java, JPA, Flyway, API/application contracts, tests, Documents/target-module data, or provisioned data.

### 111.11 Review conclusion

**REVISE.** `DocumentTargetLink` has the correct Documents ownership, neutral polymorphic target design, mandatory same-module document protection, and generic link-role catalog-row protection.

The target baseline cannot mark it APPROVED while required `targetModule` is only DB-enforced, optional document-version integrity is not fail-closed, link-role membership is not family-qualified, and the live linking path does not use the target-lookup contract prescribed by the Documents DDD.

HMS reconciliation must retain these obligations until explicitly authorized Documents production corrections resolve them or stronger repository evidence changes the governing Documents contract.

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

- `a9df0efcd9995f8fb141d854bdcb1cbbaca4eaa8` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:150](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L150)
- [docs/roadmap/model-semantic-review.md:293](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L293)
- [docs/roadmap/model-semantic-review.md:17391](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L17391)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:293 | HMSR-098 | 5 | documents | DocumentTargetLink | — | documents.Document, documents.DocumentVersion | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review documents DocumentTargetLink
doc/domain/SEMANTIC_DECISIONS.md:150 | HMSR-098 | [documents.DocumentTargetLink](#documents-documenttargetlink) | REVISE | HMR-084
~~~~~~~~

<a id="hapi-591"></a>
## HAPI-591 — Typed local engineering exchange and capabilities

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HPR-P25-052E

Priority: P2

Implementation Status: NOT_IMPLEMENTED

Planning Status: IN_PROGRESS

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-590

Cross-Repository Dependencies: HWEB-F02, HWEB-F03 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Supported local typed import/export and per-editor capability/completeness closure.

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: Supported local typed import/export and per-editor capability/completeness closure..

### 4. Functional Requirements

Versioned bounded packages, dry-run without writes, lossless precision/provenance, atomic admitted import and explicit unsupported states. Aggregate multi-owner exchange remains055.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringLocalExchange.java` — pure Java owned aggregate/value and invariants for versioned typed local import/export with bounded dry run, precision and atomic admitted writes.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringLocalExchangeUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringLocalExchangeRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringLocalExchangeApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringLocalExchangeController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringLocalExchangeRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

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

**PROPOSED, not existing:** `POST /api/v1/topology/engineering-exchanges/dry-runs` for versioned typed local import/export with bounded dry run, precision and atomic admitted writes. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

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

- Versioned bounded packages, dry-run without writes, lossless precision/provenance, atomic admitted import and explicit unsupported states. Aggregate multi-owner exchange remains055.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-590. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23360](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23360)
- [doc/model-remediation/RECONCILIATION.md:23738](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23738)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1046](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1046)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1298](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1298)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1046 | HPR-P25-052E | BLOCKED_SPECIFICATION | Typed local engineering exchange and capabilities
doc/roadmap/ULTIMATE_ROADMAP.md:1298 | HPR-P25-052E | Supported local typed import/export and per-editor capability/completeness closure. | Versioned bounded packages, dry-run without writes, lossless precision/provenance, atomic admitted import and explicit unsupported states. Aggregate multi-owner exchange remains055.
doc/model-remediation/RECONCILIATION.md:23360 | HPR-P25-052E | Supported local typed import/export and per-editor capability/completeness closure. | Versioned bounded packages, dry-run without writes, lossless precision/provenance, atomic admitted import and explicit unsupported states. Aggregate multi-owner exchange remains055.
doc/model-remediation/RECONCILIATION.md:23738 | HPR-P25-052E | BLOCKED_SPECIFICATION | Typed local engineering exchange and capabilities
~~~~~~~~
### 2026-10-25 — HAPI-591 source-backed local exchange execution contract

**Selected stage and authority.** Owner Go ahead accepts the proposed four-stage
HAPI-591 sequence: source-backed scope/registration, one implementation with
tests, actual artifact reconciliation, final acceptance. Execute only this first
stage now with exact message
`docs(topology): define local engineering exchange execution contract`.
Main `89e423938e9225af4a7a7fca0a94d02cb3c9d86d`, parent
d36f8c08206061d931bf0ef045a4956ee97fdf2d, tree
5b9adfdad4dd7533bd3b13b7fbd321ab7fcea535, passed
[Docs593](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/40233029937).
HAPI-590 and HAPI-778 are VERIFIED/COMPLETED. The accepted runtime/artifact
baseline remains successful Full899 on edd66b3a4dc27b724dbb99b6eaf6d0a456fe6ae3;
the intervening closures are governance-only. HAPI-591 moves only planning
BLOCKED to IN_PROGRESS; implementation remains NOT_IMPLEMENTED.

**Recovered source and concrete reuse.**
EngineeringNetworkDraftUseCase has CREATE/READ/UPDATE/VALIDATE plus graph/survey
commands. EngineeringGraphDraftUseCase and SurveyRouteProfileUseCase expose
typed reads and expected-version updates on the same shared draft version.
EngineeringNetworkDraftApplicationService implements all three and rejects an
outer transaction. Each operation prepares genuine native Identity, acquires
Party/Topology ownership, uses one original sample and its private compiled
publisher permit, flushes, executes its existing final guard, then returns after
observed physical commit. Do not call its outbound repositories directly.

ProtectedEngineeringDraftAdmissionCoordinator maps graph/survey READ to native
READ and UPDATE to native UPDATE; the company/network paired resource contract
already owns those meanings. Existing metadata/graph/survey families retain
31/34/35 ordered relation fences. Their exact mutation hashes, final SQL,
Identity/Party exports and caller guards need no extension for a facade calling
the existing public inbound owners.

Existing canonical codecs are:
- EngineeringNetworkDraftCodec: HIDRA_ENGINEERING_METADATA_V1, maximum32768
  decoded bytes, canonical null-aware UTF-8 metadata.
- EngineeringGraphDraftCodec: HIDRA_ENGINEERING_GRAPH_DRAFT_V1, maximum1048576
  decoded bytes, bounded canonical graph IDs/incidence and no physical values.
- SurveyRouteProfileCodec: HIDRA_ENGINEERING_SURVEY_DRAFT_V1, maximum16777216
  decoded bytes; BigDecimal unscaled integer plus scale, declared CRS/axes/
  units/reference conditions and optional elevation are preserved exactly.

Graph/survey snapshots retain their actual component version/digest and survey
bound-graph version/digest. Metadata READ returns current metadata only; do not
invent historical metadata read support. The39-case genuine
EngineeringNetworkDraftPostgresIntegrationTest already provides a reusable
signed-native HTTP, separated-role, graph/survey/metadata, CAS/rollback fixture;
inspect its real setup before extending it. Documents' binary transfer has its
own transactional storage lifecycle, not engineering import authority.
Integration exchange tracking likewise is not a protected draft writer.
Neither subsystem is added to this local facade.

**Bounded V1 product contract.** Support exactly three editor payloads:
METADATA, GRAPH, SURVEY. A package contains exactly one payload; imports target
an existing draft within the same company/network/draft identity, with the same
branch for SURVEY. No implicit create, clone, cross-company/draft remapping,
combined multi-editor update, published-ledger import, installed-unit/product/
manufacturer/baseline operation or archive/remote URL fetch is supported.
Reject these explicitly, rather than silently partially importing. Aggregate
multi-owner exchange remains HAPI-608; this task supplies a precise supported
local editor exchange and per-editor capability/completeness contract.

Use an immutable pure-Java EngineeringLocalExchange envelope with format
HIDRA_ENGINEERING_LOCAL_EXCHANGE_V1, editor, exact origin company/network/context/
draft selectors, observed shared draft version, actual component format/version/
SHA256 and canonical standard Base64 bytes. SURVEY also retains branch and its
original bound-graph version/SHA256. Metadata component version is its observed
current draft version. A graph or survey absence remains NOT_AUTHORED, with
no fabricated empty payload or synthetic version. Collections/bytes are copied.
Origin claims and hashes are untrusted portable data, not native authentication,
current authority, a signed attestation or a public qualification certificate.

The codec adapter reuses the original codecs unchanged and verifies digest,
format, canonical Base64 and decode/re-encode byte equality before dispatch.
Keep source origin metadata separate from new target observation/binding;
never rewrite unit/CRS/decimal scales, coerce absent values, normalize supplied
evidence or turn engineeringQualified true. The validated source payload bytes
are portable content; they carry no actor, permission, token, ticket, role,
credential, private proof or native sample.

**Read/export/dry-run/import semantics.**
- Each request supplies exact company/network/draft and editor selectors.
  Use existing native READ to establish the target context and current version
  before disclosing protected metadata/capability/export results. GRAPH/SURVEY
  reads additionally use their own existing typed READ, including station
  witnesses and graph linkage. Each call retains its own real protected owner
  sample; no claim of one atomic multi-editor read is made.
- Export returns a detached envelope only for an actually authored component.
  GRAPH/SURVEY may request a positive as-of shared version via existing readers;
  METADATA rejects a requested historical version as unsupported. Origin context
  remains the immutable exact target context. No update/publication is called.
- Dry-run accepts one package plus explicit positive target expectedVersion.
  Decode/validate bounded content without writes, then use authorized target
  READ(s) and require exact observed expectedVersion. Report checks actually
  performed: format/digest/structure/scope/current observed version and applicable
  current survey graph linkage. Dry-run is advisory, no write-authorization
  assertion, no reserved version, no receipt, no lock/admission handle and no
  guarantee that a later import remains admissible. Proposed graph station
  eligibility is rechecked by the actual write owner; never falsely mark those
  unperformed proposed-object checks passed.
- Import repeats those validations/reads afresh and dispatches exactly one old
  UPDATE owner for the selected editor, using the explicit target expectedVersion.
  This operation intentionally needs native READ for preview plus native UPDATE
  for the write; read permission alone cannot import. No scalar permission flag
  or dry-run success replaces either. Source versions are provenance, not CAS.
  Target context/company/network/draft must match the package origin selectors;
  SURVEY branch must match the encoded profile and selected target branch.
- For SURVEY, read the actual current target graph and require its digest equal
  to the package's original bound-graph digest. Its observed shared version must
  equal expectedVersion. The existing survey writer then rechecks the graph and
  binds the new snapshot to the actual target graph version/digest. Identical
  graph bytes at a newer target version may be accepted; report original and new
  binding versions separately. A different graph is409, not silent rebinding.
- A concurrent change between preview and write is rejected by the old shared
  expected-version CAS. Only that final existing UPDATE mutates; its complete
  metadata+component append remains one physical transaction. No outer
  @Transactional, loop of writes, new JDBC/JPA, publisher credential, executor
  permit, SQL template, migration, ledger or new authorization family is needed.
  A failed final guard/flush rolls back all existing write effects. Successful
  output is returned only after that owner observed commit; unavailable or
  INDETERMINATE completion never auto-retries. Same-version resubmission conflicts
  through CAS; no new exactly-once receipt promise or import journal is invented.
- Capability/completeness is per requested editor under native READ. Describe
  supported operations/formats/bounds and actual authored/not-authored state,
  missing title/evidence, graph diagnostics, survey presence/graph provenance
  and retained unqualified status. Do not repeat metadata's generic graph-not-
  authored diagnostic as a claim about an independently authored graph.
  Supported import does not mean the caller is authorized to write; explicitly
  leave write authorization unevaluated and defer it to the actual import.
  Missing engineering facts stay missing. No new GET mutates/validates a draft.

Each preview/read and the final write is separately admitted. This is atomicity
of the one imported editor write, not an aggregate transaction across reads,
publication or several editors. Existing500ms operation/250ms lock/750ms
statement/1000ms idle limits remain unchanged. Bounded codec/transport work
does not establish an end-to-end HTTP SLA or reuse the appointment7500ms cap.

**Four exact new REST tuples, one registered controller.**
Base /api/v1/topology/engineering-networks/{draftId}/exchanges/{editor}:
- GET /export -> export, operationId exportEngineeringLocalExchange.
- POST /dry-runs -> dryRun, operationId dryRunEngineeringLocalExchange.
- POST /imports -> importOne, operationId importEngineeringLocalExchange.
- GET /capabilities -> capabilities, operationId readEngineeringLocalExchangeCapabilities.

Require companyPartyId and pipelineSystemId query selectors; branchId is required
only for SURVEY, forbidden on other editors. Optional version is export-only
for GRAPH/SURVEY; capabilities/dry-run/import reject it. POST bodies contain
exactly expectedVersion and package. Reject unknown/duplicate/trailing keys,
mixed editors, coerced numbers/booleans/strings, fractional/overflowed versions
and noncanonical payloads. The scoped body advice bounds the original JSON
stream to25165824 bytes (24MiB), depth8, before typed conversion. The largest
16MiB payload plus Base64/envelope overhead fits that transport ceiling; editor
decoded-byte bounds still apply independently before large allocation.
Do not accept ZIP/GZIP/multipart, filenames/path traversal, external references
or unsafe polymorphic type hints.

Extend HidraRouteAuthorizationInterceptor only for the four exact compiled
new controller/method/verb/pattern tuples, after non-anonymous authentication;
each operation still calls its required protected READ/UPDATE owner. All sibling,
wrong-method/controller/verb/pattern requests retain normal permission checks.
Do not add an engineering-exchanges prefix bypass or any new permission grant.

New scalar REST request/response records and mapper never expose Domain/JPA/
native owner objects. The new exception advice is scoped to this controller
with fixed instance urn:hidra:engineering-local-exchange. Map malformed or
unsupported package/editor/format to400, anonymous401, protected denial403,
existing authorized NOT_FOUND404, stale/source-scope/graph/version conflict409,
storage unavailable or indeterminate completion503. Fixed codes/messages expose
no private exception text, foreign selectors/counts/proof or database details.
Keep old draft/publication handlers/routes/schemas unchanged.

**One registered future implementation — next after this Docs success.**
Exact message `feat(topology): implement typed local engineering exchange`.
CREATE exactly17 files:
- src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringLocalExchange.java
- src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringLocalExchangeUseCase.java
- src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringLocalExchangeCodecPort.java
- src/main/java/dz/sh/hidra/modules/topology/application/model/EngineeringLocalExchangeFailure.java
- src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringLocalExchangeApplicationService.java
- src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringLocalExchangeCodec.java
- src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringLocalExchangeController.java
- src/main/java/dz/sh/hidra/modules/topology/api/rest/request/EngineeringLocalExchangeRequests.java
- src/main/java/dz/sh/hidra/modules/topology/api/rest/response/EngineeringLocalExchangeResponses.java
- src/main/java/dz/sh/hidra/modules/topology/api/rest/mapper/EngineeringLocalExchangeRestMapper.java
- src/main/java/dz/sh/hidra/modules/topology/api/rest/EngineeringLocalExchangeApiExceptionHandler.java
- src/test/java/dz/sh/hidra/modules/topology/domain/model/EngineeringLocalExchangeTest.java
- src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/EngineeringLocalExchangeCodecTest.java
- src/test/java/dz/sh/hidra/modules/topology/application/service/EngineeringLocalExchangeApplicationServiceTest.java
- src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringLocalExchangeControllerTest.java
- src/test/java/dz/sh/hidra/modules/topology/api/rest/EngineeringLocalExchangeApiExceptionHandlerTest.java
- src/test/java/dz/sh/hidra/modules/topology/EngineeringLocalExchangeBoundaryGuardrailTest.java

MODIFY exactly5 existing files:
- src/main/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptor.java
- src/test/java/dz/sh/hidra/platform/permissions/HidraRouteAuthorizationInterceptorTest.java
- src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/EngineeringNetworkDraftPostgresIntegrationTest.java
- .github/ci-test-inventory.json
- doc/roadmap/TASKS_HPS-020.md

Zero DELETE. All17 new paths are absent and all5 modify paths are present at the
registered head. EngineeringLocalExchangeCodecPort is only a codec boundary;
do not instantiate the originally proposed new exchange repository/SQL adapter.
Reuse existing metadata/graph/survey codecs and inbound owners without edits.
No old source-format, migration, security proof, role, time bound, physical
dictionary or shared execution policy modification is selected. The three new
REST/helper/codec adapters are components as appropriate; application service
depends only on existing inbound ports plus its codec port. Exact input/response
records/mapper are contained in the listed files, not additional DTO files.

**Planned tests, not executed evidence.** Implement six new suites with exactly
the following33 source methods (parameterize where meaningful without inventing
additional method identities):
- EngineeringLocalExchangeTest: `requiresOneKnownEditorAndExactOriginSelectors`, `rejectsInvalidVersionsFormatsDigestsAndBounds`, `copiesPayloadAndDiagnosticsDefensively`, `preservesAbsentValuesWithoutInferringQualification`, `rejectsMixedEditorsAndRemapping`, `keepsOriginClaimsSeparateFromTargetAuthority`.
- EngineeringLocalExchangeCodecTest: `roundTripsAllThreeOriginalCanonicalFormats`, `preservesSurveyDecimalScaleUnitsAndMissingElevation`, `rejectsDigestBase64FormatAndTrailingByteTampering`, `rejectsMalformedUtf8NoncanonicalAndOversizedPayloads`, `retainsMetadataNullsAndGraphIdentityNamespaces`, `keepsOriginAndComponentVersionProvenanceExplicit`, `neverConvertsCoordinatesUnitsOrPhysicalValues`.
- EngineeringLocalExchangeApplicationServiceTest: `exportRequiresExistingNativeReadBeforeReturningPayload`, `dryRunUsesReadOnlyOwnerOperationsAndReturnsNoAdmission`, `importDispatchesOneExistingProtectedUpdateWithExplicitCas`, `staleTargetVersionNeverInvokesAnUpdate`, `surveyImportRequiresTheCurrentGraphDigestAndPreservesBindingProvenance`, `revocationAndIndeterminateFailureNeverRetryOrReturnSuccess`, `capabilitiesDescribeSupportWithoutGrantingWriteAuthority`, `unsupportedEditorsBatchesAndForeignScopesNeverReachAWrite`.
- EngineeringLocalExchangeControllerTest: `mapsFourExactRoutesAndThreeEditorsToTypedInputs`, `rejectsDuplicateUnknownCoercedAndOversizedBodiesBeforeOwnerDispatch`, `preservesScalarEnvelopesAndDetachedResults`, `requiresExactScopeVersionAndSurveyBranchSelectors`, `anonymousForeignAndSiblingRoutesCannotDelegate`.
- EngineeringLocalExchangeApiExceptionHandlerTest: `fixedErrorsDoNotExposeSelectorsOrExceptionMessages`, `mapsNativeConflictsDenialsAndIndeterminateCompletionExactly`, `adviceAndBodyBoundsApplyOnlyToTheExchangeController`.
- EngineeringLocalExchangeBoundaryGuardrailTest: `applicationCoordinatesOnlyExistingInboundOwnersAndCodecPort`, `exchangeHasNoSqlJpaTransactionPermitOrIdentityTicketAccess`, `restModelsDoNotExposeDomainOrAuthorityObjects`, `existingFormatsSqlGuardsAndBudgetsRemainUnchanged`.

Add exactly four actual methods to the existing real native/PostgreSQL fixture:
- genuineNativeEditorsExportDryRunAndImportUseExistingProtectedOwners
- dryRunAndCapabilityReadsLeaveAllDraftAndPublicationStorageUnchanged
- staleConcurrentAndRevokedImportsCannotAppendPartialSuccessors
- malformedForeignAndUnsupportedPackagesRevealNoOwnerDataAndRetainExactBytes

Use real signed LOCAL/OIDC/LDAP/AD, actual MVC/security/interceptor beans and
separated ordinary/publisher roles; only existing external source/directory
transports are synthetic. Prove all three editor roundtrips, decimal scale/
null/unit/provenance retention, complete row/count invariance for dry-run,
capabilities and export, scope/unknown/unauthenticated denial, explicit unsupported
batch/foreign input, graph mismatch, stale and concurrent shared-version CAS,
revocation between preview and write, flush/final-guard rollback and no
partial metadata/component successor. Do not count mocked owner tests as native
proof or simulated completion as an induced unknown database COMMIT.

Add one actual existing interceptor test:
exchangeDelegatesOnlyFourExactAuthenticatedControllerTuples.
Preserve all existing methods/cases. Inventory only the six actually implemented
new suites and changed existing test source hashes/cases; preserve all other
classes/weights/archives, the existing seven PostgreSQL shards and workflow mode.
New unit suites are fast/unit-architecture with explicitly unmeasured duration0.
No test is removed to shorten CI. Additional cases may parameterize these methods;
source/inventory must agree before publication.

**Remaining registered stages.**
3. After the implementation produces actual hosted outputs and responsible
   runtime failures are repaired: exact message
   `docs(topology): reconcile local engineering exchange artifacts`.
   MODIFY only doc/api/openapi.yaml, .github/documentation-validation.json and
   this phase record, using real generated source/run/attempt/artifact/member
   digests. No schema/JPA/ownership change is planned, so preserve the physical
   dictionary and verify actual normal API-lane catalog equality; widen scope
   only for a demonstrated real gap, never invented metadata. Strict equality
   and successful-base compatibility remain unchanged. Do not invent an
   updated snapshot before the actual application generates it.
4. After exact-source Full/Fast/Docs as applicable and case-level acceptance:
   `docs(topology): record local engineering exchange acceptance`.
   Only this phase's HAPI-591 statuses/evidence, HAPI-591 status cells in ROADMAP/
   PHASES and the matching index pointer may change (four governance files).
   Promote VERIFIED/COMPLETED only on actual required evidence. HAPI-592 and
   integrated topology closure remain separately selected stages; no frontend,
   multi-owner exchange, release, deployment, simulation or OT action follows.

These are four main delivery stages, not a promise of no CI repair commits.
A discovered genuine contract/authority gap must be recorded and repaired in
bounded scope rather than hidden by bypassing an owner or accumulating preflights.

**This stage's exact write scope and validation.** Zero CREATE / four MODIFY /
zero DELETE: this phase adds this record and changes only HAPI-591 planning to
IN_PROGRESS; ULTIMATE_ROADMAP.md and ULTIMATE_PHASES.md change only that task's
planning status; ULTIMATE_TASKS.md adds only its compact current execution pointer.
Implementation remains NOT_IMPLEMENTED. Preserve all old records and every
production/test/artifact/migration/workflow/manifest blob.

Actual available checks: complete selected task/governance and relevant live
owner/service/controller/domain/codec/source recovery,17 absent/5 present future
path check, old-history/status-only scope and target/anchor checks. Existing19
documentation-validator self-tests PASS. Validate focused921-row registry/index
and34-task phase agreement before publication. No new Java/Maven/PostgreSQL
execution, implementation, artifact generation or runtime acceptance is claimed.

Publish this one registration to main with expected89e4239 lease, verify exact
one-parent/tree/four-file scope, observe applicable exact-SHA Documentation CI
creation/queue/start and STOP without waiting or executing stage2 automatically.



### 2026-10-25 — HAPI-591 typed local editor exchange source delivery

Owner Next and the explicit request to try finishing in one turn select the
registered implementation `feat(topology): implement typed local engineering exchange`
after successful Docs594 on60cce8ad84348084e07f8f81bcc9eba807654e91.
The owner also authorizes continuing the registered artifact and acceptance
stages in this turn when their actual required CI/evidence is available; this
overrides the ordinary stop-after-start workflow for this requested continuation.
It never waives a failed gate, exact-source artifact provenance or final acceptance.

Source implements the17-CREATE/5-MODIFY contract above: one canonical bounded
METADATA/GRAPH/SURVEY payload; native READ preview and exactly one original
expected-version UPDATE; advisory dry-run; per-editor support/completeness,
explicit unsupported remapping/batches and fixed scoped errors.
No new database, migration, JDBC/JPA adapter, privilege, native family, SQL,
transaction permit, format rewrite or execution-budget change is introduced.
Both package origin fields and package digests remain untrusted content, never
proof of authorization. Final writes still use the original protected owner.

The codec composes the three original canonical codecs unchanged. Actual core
roundtrips retain null metadata, graph identifiers, exact survey BigDecimal
scale, units, evidence and absent elevation. Target graph bytes must match a
survey's original binding hash. Import does not invoke the current survey READ:
that read correctly refuses an old stale binding and would prevent legitimate
explicit rebinding after identical graph bytes are reauthored. Instead the
authorized current graph READ proves the preview; original survey UPDATE
revalidates its branch/graph and binds to the real target version under shared
CAS. Origin and new target binding versions are returned separately.

Four exact compiled REST tuples delegate to existing native owner authority,
not a prefix permission bypass. Anonymous/sibling/wrong tuple requests retain
denial. Scoped24MiB original-body advice and a constrained Jackson parser
reject duplicate/unknown/trailing/coerced/overflow/deep/oversized input.
Controller POSTs receive bounded raw bytes before a scoped typed Submission
decode. This avoids the global JSON converter's independent string ceiling
silently reducing the registered16MiB decoded survey payload limit; OpenAPI
explicitly describes the typed Submission schema. No global parser or limit is
changed. Scalar response packages can be resubmitted as request packages;
no response-only authority flag contaminates the portable envelope.

All33 registered new source methods in six suites exist; the existing genuine
native fixture adds all four registered methods, and the interceptor suite
adds its one registered matrix method. All previous method identities remain.
The new native cases exercise real native LOCAL/OIDC/LDAP/AD and HTTP filters,
exact byte roundtrips and rebind provenance, unchanged full draft/publication
storage images for advisory reads, stale/concurrent CAS, authority revocation
between preview and write, actual managed-flush failure and final-guard fault
rollback, malformed/foreign/batch denial and no protected selectors in errors.
Mocks in other suites prove only bounded transport/orchestration mechanics.

Inventory adds only six actual unit-architecture/fast suites with unmeasured
duration0; only two existing test entries' actual cases and source hashes change.
Existing weights, all other classes/metadata, four historical archives, seven
DB shards and CI policy/workflows remain unchanged. Active source coverage
becomes465 classes (469 registered including the four historical archives).
Expected executions increase by38 source methods; actual execution/parameter
expansion counts remain the hosted report's authority.

Local validation: all20 changed/new Java sources parse using the available
JDK17 compiler module. The actual pure domain, codec and application facade
typecheck against current original editor interfaces/domain/codecs; only the
two Spring stereotype annotations use temporary local compile stubs. A direct
core harness executes all three canonical payload roundtrips, survey scale
preservation, three no-write advisory dry-runs, one update per editor, explicit
same-byte graph rebinding and stale rejection successfully. This does not
claim Spring/Jackson/MVC/JUnit/native/database execution. Maven/Java21 and a
complete local service environment are unavailable and are not provisioned.
Existing inventory-validator32 and documentation-validator19 self-tests PASS.
Exact source/header/old-case/inventory hash/scope checks complete before publish.

The canonical API and manifest retain their actual prior generated bytes.
New endpoints/schemas require real hosted generation before the separately
registered artifact reconciliation. The first strict snapshot mismatch is
expected; no inferred schema is substituted and no compatibility ignore or
validator relaxation is allowed. Observe all actual source/DB/security results
and repair responsible failures before acceptance. Implementation is submitted,
not accepted; the frozen task table statuses await the registered final
four-file acceptance rather than drift during this source-only scope.

Exact scope is the registered17 CREATE/5 MODIFY/zero DELETE, including only
this HAPI-591 evidence in the phase record. All schema/JPA/catalog, original
editor/codec/owner/executor/Identity/Party, other routes and registry statuses
remain unchanged. HAPI-591 final acceptance, HAPI-592/HAPI-608, frontend,
engineering qualification, release/deployment/simulation and OT action are not
inferred from source submission.

Publish with expected60cce8a lease; verify exact parent/tree/message/all22 blobs
and observe applicable exact-source Full/Fast/Docs. Continue the authorized
same-turn repair/artifact/acceptance work only on actual returned evidence.


<a id="hapi-608"></a>
## HAPI-608 — Aggregate typed engineering exchange

Phase: HPS-020 — Documents and Data Exchange

Legacy IDs: HPR-P25-055D

Priority: P2

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `topology` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-607

Cross-Repository Dependencies: HWEB-F07, HWEB-F10 — legacy HidraWEB consumer references, not backend implementation tasks.

### 1. Objective

Supported aggregate engineering import/export, dry-run and conflict-safe replay.

### 2. Existing Implementation

Multipart upload and authorized version-content retrieval exist. Audit exports have their own narrower purpose. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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

Deliver engineering dry-run/import/export, precise diagnostics, atomicity and rollback through existing topology/baseline tasks; preserve paused legacy provisioning. The exact bounded remaining delivery is: Supported aggregate engineering import/export, dry-run and conflict-safe replay..

### 4. Functional Requirements

Version/size/type/digest/source/permission limits, lossless semantic round trip and dry-run without writes; admitted multi-owner writes use explicit atomic coordination, no generic document-upload substitute.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `topology`; primary phase owns this obligation. No production backfill or data import is selected. Documents do not own foreign engineering aggregates. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/in/CreatePipelineSystemUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/RegisterFacilityUseCase.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/in/TopologyMapVisualizationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EquipmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/PipelineSystemRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/topology/application/service/PipelineSystemApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologyOperationalScopeTargetQueryService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringAggregateExchange.java` — pure Java owned aggregate/value and invariants for typed aggregate package, owner policy for creating sources versus published-only references, dry-run diagnostics and rollback.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/in/EngineeringAggregateExchangeUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/port/out/EngineeringAggregateExchangeRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/topology/application/service/EngineeringAggregateExchangeApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/topology/api/rest/controller/EngineeringAggregateExchangeController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcEngineeringAggregateExchangeRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

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

**PROPOSED, not existing:** `POST /api/v1/topology/engineering-baseline-exchanges/dry-runs` for typed aggregate package, owner policy for creating sources versus published-only references, dry-run diagnostics and rollback. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

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

- Version/size/type/digest/source/permission limits, lossless semantic round trip and dry-run without writes; admitted multi-owner writes use explicit atomic coordination, no generic document-upload substitute.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-607. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23414](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23414)
- [doc/model-remediation/RECONCILIATION.md:23707](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23707)
- [doc/model-remediation/RECONCILIATION.md:23752](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23752)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1015](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1015)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1060](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1060)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1352](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1352)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1015 | HPR-P25-055D | Select whether aggregate import may create missing owner revisions atomically or accepts pre-existing published sources only; dry run and current authorization remain mandatory. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1060 | HPR-P25-055D | REQUIRES_OWNER_DECISION | Aggregate typed engineering exchange
doc/roadmap/ULTIMATE_ROADMAP.md:1352 | HPR-P25-055D | Supported aggregate engineering import/export, dry-run and conflict-safe replay. | Version/size/type/digest/source/permission limits, lossless semantic round trip and dry-run without writes; admitted multi-owner writes use explicit atomic coordination, no generic document-upload substitute.
doc/model-remediation/RECONCILIATION.md:23414 | HPR-P25-055D | Supported aggregate engineering import/export, dry-run and conflict-safe replay. | Version/size/type/digest/source/permission limits, lossless semantic round trip and dry-run without writes; admitted multi-owner writes use explicit atomic coordination, no generic document-upload substitute.
doc/model-remediation/RECONCILIATION.md:23707 | HPR-P25-055D | Select whether aggregate import may create missing owner revisions atomically or accepts pre-existing published sources only; dry run and current authorization remain mandatory. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23752 | HPR-P25-055D | REQUIRES_OWNER_DECISION | Aggregate typed engineering exchange
~~~~~~~~

<!-- task-register:payload:end -->
