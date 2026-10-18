# HPS-016 — Risk and Operational Intelligence: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-016 — Risk and Operational Intelligence

<a id="hapi-166"></a>
## HAPI-166 — scan risk domain models

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMC-021

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 4 risk model files.

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 30. HMC-021 — Risk module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `risk`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 78  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Risk model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| RiskAssessment | record | 34 | `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java` | Extracted |
| RiskEvidenceLink | record | 11 | `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java` | Extracted |
| RiskMatrixCell | record | 12 | `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java` | Extracted |
| RiskRegister | record | 21 | `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java` | Extracted |

### Risk fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| RiskAssessment | 1 | id | String |
| RiskAssessment | 2 | riskRegisterId | String |
| RiskAssessment | 3 | assessmentNumber | String |
| RiskAssessment | 4 | title | String |
| RiskAssessment | 5 | description | String |
| RiskAssessment | 6 | assessmentTypeId | String |
| RiskAssessment | 7 | methodologyId | String |
| RiskAssessment | 8 | scopeId | String |
| RiskAssessment | 9 | riskScenarioId | String |
| RiskAssessment | 10 | status | RiskAssessmentStatus |
| RiskAssessment | 11 | assessmentDate | Instant |
| RiskAssessment | 12 | validFrom | Instant |
| RiskAssessment | 13 | validTo | Instant |
| RiskAssessment | 14 | assessedByActorId | String |
| RiskAssessment | 15 | assessedByDisplayNameSnapshot | String |
| RiskAssessment | 16 | reviewedByActorId | String |
| RiskAssessment | 17 | reviewedByDisplayNameSnapshot | String |
| RiskAssessment | 18 | approvedByActorId | String |
| RiskAssessment | 19 | approvedByDisplayNameSnapshot | String |
| RiskAssessment | 20 | approvedAt | Instant |
| RiskAssessment | 21 | inherentLikelihoodId | String |
| RiskAssessment | 22 | inherentConsequenceId | String |
| RiskAssessment | 23 | inherentScore | BigDecimal |
| RiskAssessment | 24 | inherentRatingId | String |
| RiskAssessment | 25 | residualLikelihoodId | String |
| RiskAssessment | 26 | residualConsequenceId | String |
| RiskAssessment | 27 | residualScore | BigDecimal |
| RiskAssessment | 28 | residualRatingId | String |
| RiskAssessment | 29 | confidenceLevelId | String |
| RiskAssessment | 30 | uncertaintyNote | String |
| RiskAssessment | 31 | workflowReferenceId | String |
| RiskAssessment | 32 | auditReferenceId | String |
| RiskAssessment | 33 | createdAt | Instant |
| RiskAssessment | 34 | updatedAt | Instant |
| RiskEvidenceLink | 1 | id | String |
| RiskEvidenceLink | 2 | riskAssessmentId | String |
| RiskEvidenceLink | 3 | evidenceModule | String |
| RiskEvidenceLink | 4 | evidenceType | String |
| RiskEvidenceLink | 5 | evidenceId | String |
| RiskEvidenceLink | 6 | evidenceCodeSnapshot | String |
| RiskEvidenceLink | 7 | evidenceLabelSnapshot | String |
| RiskEvidenceLink | 8 | evidenceTimestamp | Instant |
| RiskEvidenceLink | 9 | evidenceHash | String |
| RiskEvidenceLink | 10 | evidenceSummary | String |
| RiskEvidenceLink | 11 | createdAt | Instant |
| RiskMatrixCell | 1 | id | String |
| RiskMatrixCell | 2 | riskMatrixId | String |
| RiskMatrixCell | 3 | likelihoodLevelId | String |
| RiskMatrixCell | 4 | consequenceLevelId | String |
| RiskMatrixCell | 5 | scoreValue | BigDecimal |
| RiskMatrixCell | 6 | ratingId | String |
| RiskMatrixCell | 7 | colorCode | String |
| RiskMatrixCell | 8 | requiresTreatment | boolean |
| RiskMatrixCell | 9 | requiresApproval | boolean |
| RiskMatrixCell | 10 | requiresExecutiveAcceptance | boolean |
| RiskMatrixCell | 11 | createdAt | Instant |
| RiskMatrixCell | 12 | updatedAt | Instant |
| RiskRegister | 1 | id | String |
| RiskRegister | 2 | code | String |
| RiskRegister | 3 | nameAr | String |
| RiskRegister | 4 | nameFr | String |
| RiskRegister | 5 | nameEn | String |
| RiskRegister | 6 | description | String |
| RiskRegister | 7 | registerTypeId | String |
| RiskRegister | 8 | ownerOrganizationUnitId | String |
| RiskRegister | 9 | ownerOrganizationUnitNameSnapshot | String |
| RiskRegister | 10 | scopeType | String |
| RiskRegister | 11 | scopeId | String |
| RiskRegister | 12 | scopeCodeSnapshot | String |
| RiskRegister | 13 | scopeLabelSnapshot | String |
| RiskRegister | 14 | status | RiskRegisterStatus |
| RiskRegister | 15 | reviewFrequencyId | String |
| RiskRegister | 16 | effectiveFrom | Instant |
| RiskRegister | 17 | effectiveTo | Instant |
| RiskRegister | 18 | createdByActorId | String |
| RiskRegister | 19 | createdByDisplayNameSnapshot | String |
| RiskRegister | 20 | createdAt | Instant |
| RiskRegister | 21 | updatedAt | Instant |

### HMC-021 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 78
zero-field models    = 0
```

The scan records only declarations under `risk/domain/model`. Risk domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure model, integrity implementation, workflow implementation, audit implementation, or database column metadata was mixed into this module scan.

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

- `cd7ddd3e1113c4fa5070546ba1ae85132efab085` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:149](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L149)
- [docs/roadmap/model-field-catalogue.md:2459](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2459)
- [docs/roadmap/model-field-catalogue.md:2561](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2561)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:149 | HMC-021 | docs(catalogue): scan risk domain models | Scan all 4 risk model files. | **Completed** — 4/4 models, 78 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-211"></a>
## HAPI-211 — remediate semantic review RiskMatrixCell

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMR-029

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review RiskMatrixCell

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Risk.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMatrixCellRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskMatrixCellRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`
- `src/main/resources/db/migration/V20261004_029__hmr_029_risk_risk_matrix_cell.sql`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskMatrixCellSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMatrixCellRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskMatrixCellRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Risk.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMatrixCellRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskMatrixCellRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`
- `src/main/resources/db/migration/V20261004_029__hmr_029_risk_risk_matrix_cell.sql`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskMatrixCellSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-029 — risk.RiskMatrixCell

- Source review: `HMSR-032`
- Exact commit: `fix(risk): remediate semantic review RiskMatrixCell`
- Status: **Completed** — matrix-coordinate uniqueness, nonnegative score semantics, and exact likelihood/consequence Risk catalog-family validation are enforced at Java and PostgreSQL boundaries.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_029__hmr_029_risk_risk_matrix_cell.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Risk.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMatrixCellRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskMatrixCellRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_029__hmr_029_risk_risk_matrix_cell.sql`
  - `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskMatrixCellSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=RiskMatrixCellSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce matrix-coordinate uniqueness.** The combination `(riskMatrixId, likelihoodLevelId, consequenceLevelId)` must identify at most one RiskMatrixCell. A separately authorized Risk correction should implement this with an additive unique database constraint/index and aligned application behavior.
  2. **Enforce nonnegative score values.** `scoreValue < 0` must be rejected at an appropriate fail-fast domain/application boundary and/or by an additive database CHECK, consistent with the explicit Risk DDD invariant.
  3. **Protect catalog-family semantics for likelihood and consequence levels.** A populated `likelihoodLevelId` must resolve to the `RISK_LIKELIHOOD_LEVEL` Risk catalog family and `consequenceLevelId` to `RISK_CONSEQUENCE_LEVEL`; generic row-existence FKs alone do not establish the required taxonomy.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskMatrixCellSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `3e6de10b6e64bd989626a522d2de21b8ea501b8c` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:175](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L175)
- [docs/roadmap/model-semantic-remediation.md:1713](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1713)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:175 | HMR-029 | HMSR-032 | risk | RiskMatrixCell | — | — | fix(risk): remediate semantic review RiskMatrixCell | Planned
~~~~~~~~

<a id="hapi-241"></a>
## HAPI-241 — remediate semantic review RiskRegister

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMR-049

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review RiskRegister

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Risk.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskRegisterRequest.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskRegisterResponse.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskRegisterCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskRegisterSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskRegisterRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskRegisterRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskRegisterJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/resources/db/migration/V20261004_049__hmr_049_risk_risk_register.sql`
- `src/main/resources/db/migration/V20261005_001__provision_risk_register_created_audit_taxonomy.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskRegisterSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskRegisterRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`, `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskRegisterRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Risk.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskRegisterRequest.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskRegisterResponse.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskRegisterCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskRegisterSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskRegisterRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskRegisterRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskRegisterJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/main/resources/db/migration/V20261004_049__hmr_049_risk_risk_register.sql`
- `src/main/resources/db/migration/V20261005_001__provision_risk_register_created_audit_taxonomy.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskRegisterSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-049 — risk.RiskRegister

- Source review: `HMSR-058`
- Exact commit: `fix(risk): remediate semantic review RiskRegister`
- Status: **Completed** — registerTypeId is constrained to active RISK_REGISTER_TYPE, populated ownerOrganizationUnitId resolves through Organization, typed scopes fail closed through registered Organization/Topology owner contracts, reviewFrequencyId remains explicitly unresolved/opaque, and RiskRegister creation is transactionally recorded through an Audit-owned RISK_REGISTER_CREATED contract.
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_049__hmr_049_risk_risk_register.sql`
- Owner-contract prerequisite: Existing candidate owner contract(s): organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OperationalScopeQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/in/OrganizationAdministrationQueryUseCase.java; organization:src/main/java/dz/sh/hidra/modules/organization/application/port/out/OperationalScopeTargetResolverPort.java
- Exact write allowlist:
  - `docs/data definition/Risk.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskRegisterRequest.java`
  - `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskRegisterResponse.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskRegisterCommand.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskRegisterSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskRegisterRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskRegisterRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskRegisterJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_049__hmr_049_risk_risk_register.sql`
  - `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskRegisterSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=RiskRegisterSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce Risk register-type family eligibility.** A mandatory `registerTypeId` must resolve to an eligible `RiskCatalogEntry` in the `RISK_REGISTER_TYPE` family; generic FK row existence is insufficient.
  2. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Preserve snapshot semantics and do not add a cross-module database FK.
  3. **Make typed scope identity fail closed.** Preserve `scopeType + scopeId` as a non-relational typed namespace, require a semantically complete pair at the authoritative write boundary, and resolve supported types through their owning contracts rather than inventing one relational target.
  4. **Resolve `reviewFrequencyId` controlled-value semantics.** Current DDD does not identify a defensible review-frequency catalog family; do not map it to `RISK_REVIEW_TYPE` without stronger evidence.
  5. **Honor the explicit register creation/update audit-event contract.** Integrate the authoritative write path with the existing Risk audit/outbox architecture when production reconciliation is authorized.

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
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskRegisterSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `f0bd888486367435acba5ea38a02500843dcc878` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:205](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L205)
- [docs/roadmap/model-semantic-remediation.md:2809](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2809)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:205 | HMR-049 | HMSR-058 | risk | RiskRegister | — | organization.OrganizationUnit | fix(risk): remediate semantic review RiskRegister | Planned
~~~~~~~~

<a id="hapi-242"></a>
## HAPI-242 — register RiskRegister owner prerequisites

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMR-049A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

register RiskRegister owner prerequisites

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`, `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`, `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-049A — RiskRegister owner/scope/audit prerequisite

- Source: HMR-049 / HMSR-058 plus live Risk/Organization/Topology/Audit evidence.
- Exact commit: `docs(model-remediation): register RiskRegister owner prerequisites`
- Status: **Completed** — the missing Risk application orchestration file, deliberate owner contracts, Audit contract/adapter, focused tests, and architecture guardrail registrations are authorized; HMR-049A itself changes documentation only.
- Type: documentation/application-boundary prerequisite.
- Live evidence:
  1. The authoritative RiskRegister creation path is `RiskApplicationService.createRiskRegister(...)`, but `RiskApplicationService.java` is absent from the original HMR-049 allowlist.
  2. No `organization.application.contract.risk`, `topology.application.contract.risk`, or `audit.application.contract.risk` package exists on live `main`.
  3. `RiskRegister.registerTypeId` is a same-module Risk catalog reference, but generic row existence is insufficient; family membership must be `RISK_REGISTER_TYPE`.
  4. `ownerOrganizationUnitId` is cross-module and must remain free of a database FK.
  5. The repository has authoritative owner evidence for these Risk scope types only:
     - `ORGANIZATION_UNIT` -> OrganizationUnit repository;
     - `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, `EQUIPMENT` -> Topology repositories/contracts.
     Broader Risk DDD examples such as `PIPELINE_SEGMENT`, `OPERATIONAL_PLAN`, `INCIDENT`, `INTEGRITY_CASE`, `HSE_CASE`, and `SIMULATION_SCENARIO` do not yet have Risk-facing owner contracts and must not be silently accepted.
  6. The legacy generic module event/outbox path was explicitly rejected/removed by ADR 0006 / HRA-031B. The current repository pattern for business audit integration is consumer-specific Audit-owned application contracts such as `audit.application.contract.alarm` and `audit.application.contract.organization`, adapted to `RecordAuditEventUseCase`.
  7. `reviewFrequencyId` has no defensible catalog-family owner in current DDD. `RISK_REVIEW_TYPE` is not evidence for review frequency and must not be substituted.
- Required decisions:
  1. Add `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java` to HMR-049 production scope.
  2. Organization must export a narrow Risk-facing contract for:
     - OrganizationUnit existence;
     - resolution of `ORGANIZATION_UNIT` scope identity.
     It may use `OrganizationUnitRepositoryPort.findById(...)`; do not invent lifecycle eligibility beyond existence.
  3. Topology must export a narrow Risk-facing typed scope contract supporting only the owner-backed types currently evidenced: `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, and `EQUIPMENT`. Unsupported type codes fail closed.
  4. Risk creation must require nonblank `scopeType` and `scopeId` as a complete pair and resolve the pair through the corresponding owner contract before persistence.
  5. Scope types without a registered owner contract must fail closed; do not infer or create cross-module relational targets.
  6. `registerTypeId` must belong specifically to Risk catalog family `RISK_REGISTER_TYPE`, enforced through Risk repository/catalog lookup before save.
  7. Populated `ownerOrganizationUnitId` must resolve through the Organization-owned Risk contract before save.
  8. Preserve `reviewFrequencyId` as nullable opaque controlled-value state for this HMR. Do not validate it against `RISK_REVIEW_TYPE`, do not create a new catalog family, and document the unresolved semantic owner for later remediation/closure review.
  9. Audit must export a Risk-specific `RiskRegisterAuditContract` owned by the Audit module, with an Audit infrastructure adapter delegating to the existing `RecordAuditEventUseCase`. HMR-049 must record RiskRegister creation after successful persistence using neutral scalar/snapshot data.
  10. Do not recreate the removed generic platform outbox/event-publisher architecture.
  11. HMR-049 migration may harden same-table/domain-enforceable invariants only. No Organization/Topology/Audit cross-module FK is authorized.
  12. Register exactly these new exported packages in both architecture guardrail registries:
      - `dz.sh.hidra.modules.organization.application.contract.risk`
      - `dz.sh.hidra.modules.topology.application.contract.risk`
      - `dz.sh.hidra.modules.audit.application.contract.risk`
      Do not add wildcard/transitional exemptions.
- Newly authorized HMR-049 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskRegisterAuditContract.java`
  - `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapter.java`
- Newly authorized HMR-049 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-049 remains the current production remediation after this prerequisite is observed. Do not start HMR-050 automatically.


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
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskRegisterAuditContractAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/RiskOrganizationReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/application/service/RiskTopologyScopeReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `552cadf2bb56c124888b7ee3f988c9b340309db8` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:2848](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2848)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-252"></a>
## HAPI-252 — remediate semantic review RiskAssessment

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMR-058

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review RiskAssessment

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/data definition/Risk.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskAssessmentAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskAssessmentAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/RiskActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/RiskActorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskAssessmentRequest.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskAssessmentResponse.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/ApproveRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/RiskAssessmentScopeInput.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/ScoreRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskAssessmentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/integration/RiskAssessmentWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/ResidualRiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScopeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScoringJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/ResidualRiskAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScopeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScoringJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/RiskAssessmentApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/RiskAssessmentApprovalService.java`
- `src/main/resources/db/migration/V20261008_005__hmr_058_risk_assessment_governance.sql`
- `src/main/resources/db/migration/V20261008_006__provision_risk_assessment_audit_taxonomy.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskAssessmentAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/RiskActorQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/infrastructure/persistence/RiskGovernancePostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/RiskAssessmentApprovalServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/RiskActorQueryService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/RiskAssessmentApprovalService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskAssessmentAuditContractAdapter.java`, `src/main/java/dz/sh/hidra/modules/risk/infrastructure/integration/RiskAssessmentWorkflowTargetLookup.java`, `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/data definition/Risk.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskAssessmentAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskAssessmentAuditContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/RiskActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/RiskActorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskAssessmentRequest.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskAssessmentResponse.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/ApproveRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/RiskAssessmentScopeInput.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/ScoreRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskAssessmentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/integration/RiskAssessmentWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/ResidualRiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScopeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScoringJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/ResidualRiskAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScopeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScoringJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/RiskAssessmentApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/RiskAssessmentApprovalService.java`
- `src/main/resources/db/migration/V20261008_005__hmr_058_risk_assessment_governance.sql`
- `src/main/resources/db/migration/V20261008_006__provision_risk_assessment_audit_taxonomy.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskAssessmentAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/RiskActorQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/infrastructure/persistence/RiskGovernancePostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/RiskAssessmentApprovalServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-058 — risk.RiskAssessment

- Source review: `HMSR-069`
- Exact commit: `fix(risk): remediate semantic review RiskAssessment`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: risk.RiskRegister
- HMSR correction count: 5
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Risk.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskAssessmentRequest.java`
  - `src/main/java/dz/sh/hidra/modules/risk/api/rest/response/RiskAssessmentResponse.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskAssessmentCommand.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/dto/RiskAssessmentSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/ResidualRiskAssessmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScopeJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/ResidualRiskAssessmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScopeJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=RiskAssessmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce exact catalog-family eligibility for `assessmentTypeId` and `methodologyId`.** Generic RiskCatalogEntry existence is insufficient; use `RISK_ASSESSMENT_TYPE` and `RISK_METHODLOGY`.
  2. **Implement the mandatory assessment-scope contract.** A RiskAssessment must have at least one authoritative `RiskAssessmentScope`; reconcile the parent scalar `scopeId` as convenience/primary-scope state rather than treating it as a substitute for the required child scope set.
  3. **Protect populated scoring/confidence references.** Likelihood, consequence, rating and confidence IDs must resolve to the correct Risk catalog families, and scored tuples must be produced through the applicable Risk scoring/matrix policy.
  4. **Enforce evidence-before-approval and approval workflow/actor semantics.** Approval must verify evidence, resolve the authorized actor/workflow context, record approval metadata and emit audit-ready evidence without cross-module DB FKs.
  5. **Enforce approved-assessment immutability.** APPROVED/ACTIVE assessments must not be modified in place except through the DDD's review/revision path.

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
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskAssessmentAuditContractAdapterTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/identity/application/service/RiskActorQueryServiceTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/risk/infrastructure/persistence/RiskGovernancePostgresIntegrationTest.java` — exact-head oracle suite 10 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java` — exact-head oracle suite 7 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/RiskAssessmentApprovalServiceTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `76dbbd721822e3e8e109d02c0b82a4ed2d6e9285` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:93](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L93)
- [doc/model-remediation/RECONCILIATION.md:13705](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13705)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15445](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15445)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:92](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L92)
- [docs/roadmap/model-semantic-remediation.md:219](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L219)
- [docs/roadmap/model-semantic-remediation.md:3242](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3242)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:219 | HMR-058 | HMSR-069 | risk | RiskAssessment | — | risk.RiskRegister | fix(risk): remediate semantic review RiskAssessment | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:93 | HMR-058 | HMSR-069 | risk.RiskAssessment | COMPLETED — CI #593 GREEN | Atomic structured scopes, matrix provenance, authenticated Workflow/Audit approval and approved immutability; Java 21 clean verify and OpenAPI compatibility passed at cfc7798477c70d10e1c3e0afd4dd7e1b42676898.
doc/model-remediation/RECONCILIATION.md:13705 | HMR-058 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:92 | HMR-058 | HMSR-069 | [risk.RiskAssessment](../domain/SEMANTIC_DECISIONS.md#risk-riskassessment) | [RiskAssessmentSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java) | [V20261008_005__hmr_058_risk_assessment_governance](../../src/main/resources/db/migration/V20261008_005__hmr_058_risk_assessment_governance.sql); [RiskAssessmentAuditContract](../../src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskAssessmentAuditContract.java); [RiskActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/RiskActorContract.java); [RiskOrganizationReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/risk/RiskOrganizationReferenceContract.java); [RiskTopologyScopeReferenceContract](../../src/main/java/dz/sh/hidra/modules/topology/application/contract/risk/RiskTopologyScopeReferenceContract.java); [RiskAssessmentApprovalContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/RiskAssessmentApprovalContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15445 | HMR-058 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-271"></a>
## HAPI-271 — remediate semantic review RiskEvidenceLink

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMR-077

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review RiskEvidenceLink

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Risk.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/package-info.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/integration/NoopRiskExternalEvidenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/resources/db/migration/V20261008_004__hmr_077_risk_evidence_identity_integrity.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLinkRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/RiskEvidenceQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/assets/infrastructure/integration/RiskEvidenceQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskEvidenceQueryAdapter.java`, `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/RiskEvidenceQueryAdapter.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Risk.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/package-info.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/integration/NoopRiskExternalEvidenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/resources/db/migration/V20261008_004__hmr_077_risk_evidence_identity_integrity.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-077 — risk.RiskEvidenceLink

- Source review: `HMSR-091`
- Exact commit: `fix(risk): remediate semantic review RiskEvidenceLink`
- Status: **Planned**
- SCC: —
- Recorded upstream HMS dependencies: risk.RiskAssessment
- HMSR correction count: 2
- Additive Flyway: `src/main/resources/db/migration/V20261004_077__hmr_077_risk_risk_evidence_link.sql`
- Owner-contract prerequisite: Owner-controlled validation required by HMSR; no concrete upstream HMS owner is registered, so preserve neutral/reference semantics and do not invent a cross-module FK.
- Exact write allowlist:
  - `docs/data definition/Risk.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLinkRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/mapper/RiskPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskEvidenceLinkJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_077__hmr_077_risk_risk_evidence_link.sql`
  - `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=RiskEvidenceLinkSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce the complete external evidence identity tuple before persistence.** `evidenceModule`, `evidenceType`, and `evidenceId` must be nonblank.
  2. **Validate external evidence through owner-controlled Risk lookup/reference ports before saving the link.** Supported module/type combinations must resolve to existing/eligible source evidence; preserve scalar IDs and snapshots and do not introduce cross-module database FKs.

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
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java` — exact-head oracle suite 15 cases, zero failures/errors/skips.

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

- `93df7f22ab566d0894e00fca27dd047e1053b560` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:112](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L112)
- [doc/model-remediation/RECONCILIATION.md:2256](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L2256)
- [doc/model-remediation/RECONCILIATION.md:13724](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13724)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:4363](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L4363)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15464](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15464)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:111](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L111)
- [docs/data definition/Risk.md:1688](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1688)
- [docs/roadmap/model-semantic-remediation.md:243](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L243)
- [docs/roadmap/model-semantic-remediation.md:4138](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4138)
- [docs/roadmap/model-semantic-remediation.md:5415](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L5415)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:243 | HMR-077 | HMSR-091 | risk | RiskEvidenceLink | — | risk.RiskAssessment | fix(risk): remediate semantic review RiskEvidenceLink | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:112 | HMR-077 | HMSR-091 | risk.RiskEvidenceLink | COMPLETED — CI #593 GREEN | Typed owner evidence validation and named provider beans verified by Java 21 clean verify and OpenAPI compatibility at cfc7798477c70d10e1c3e0afd4dd7e1b42676898.
doc/model-remediation/RECONCILIATION.md:13724 | HMR-077 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:111 | HMR-077 | HMSR-091 | [risk.RiskEvidenceLink](../domain/SEMANTIC_DECISIONS.md#risk-riskevidencelink) | [RiskEvidenceLinkSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java) | [V20261008_004__hmr_077_risk_evidence_identity_integrity](../../src/main/resources/db/migration/V20261008_004__hmr_077_risk_evidence_identity_integrity.sql); [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15464 | HMR-077 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-342"></a>
## HAPI-342 — review risk RiskMatrixCell

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMSR-032

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review risk RiskMatrixCell

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 45. HMSR-032 — risk.RiskMatrixCell review

**Decision:** REVISE  
**Review code:** HMSR-032  
**Dependency level:** 0  
**Bounded context:** risk  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 2 in the original register; both are now resolved to retained Risk read/persistence models outside the 123 HMS subject set

### 45.1 Semantic role and ordering rationale

`RiskMatrixCell` is the Risk-owned scoring lookup for one likelihood/consequence pair within a specific RiskMatrix version. It supplies the score, rating and governance flags used when Risk assessments are scored.

It remains Level 0 because its required references resolve to Risk-owned `RiskMatrix`, `RiskCatalogEntry`, and `RiskRating` read/persistence models outside the 123-model HMS subject population.

### 45.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable matrix-cell identity and persistence primary key. |
| `riskMatrixId` | `String` | Mandatory | Same-module reference to the RiskMatrix/version that owns the cell. |
| `likelihoodLevelId` | `String` | Mandatory | Controlled Risk catalog entry representing a likelihood level. |
| `consequenceLevelId` | `String` | Mandatory | Controlled Risk catalog entry representing a consequence level. |
| `scoreValue` | `BigDecimal` | Mandatory | Numeric score for the matrix coordinate. Risk DDD explicitly requires it to be non-negative. |
| `ratingId` | `String` | Mandatory | Reference to RiskRating such as LOW/MEDIUM/HIGH/CRITICAL. |
| `colorCode` | `String` | Optional | Presentation/display color associated with the matrix cell. |
| `requiresTreatment` | `boolean` | Mandatory primitive state | Whether this cell/rating requires risk treatment. |
| `requiresApproval` | `boolean` | Mandatory primitive state | Whether this cell requires governed approval. |
| `requiresExecutiveAcceptance` | `boolean` | Mandatory primitive state | Whether this cell requires executive acceptance/escalation. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The canonical constructor already rejects blank IDs/references and a null score, normalizes textual IDs/color, and exposes `treatmentOrApprovalRequired()`.

### 45.3 Parent matrix and rating dependency resolution

HMS-003 left `riskMatrixId` and `ratingId` unresolved. HRA-111 now resolves both concretely:

```text
hidra_risk_matrix_cell.risk_matrix_id
    -> hidra_risk_matrix.id
    via fk_hra111_risk_027

hidra_risk_matrix_cell.rating_id
    -> hidra_risk_rating.id
    via fk_hra111_risk_026
```

Both constraints use `ON DELETE RESTRICT` and were validated against existing rows. RiskMatrix and RiskRating are retained Risk read/persistence models outside the HMS subject set, so no HMS graph edge is added.

The DDD requirement that `ratingId` be catalog-backed is therefore satisfied at the row-reference level by the dedicated RiskRating catalog-style model.

### 45.4 Likelihood and consequence catalog semantics

Risk DDD explicitly defines Risk catalog families:

```text
RISK_LIKELIHOOD_LEVEL
RISK_CONSEQUENCE_LEVEL
```

and the matrix-cell fields carry those exact semantics.

HRA-111 installs:

```text
consequence_level_id -> hidra_risk_catalog_entry.id
likelihood_level_id  -> hidra_risk_catalog_entry.id
```

through `fk_hra111_risk_024` and `fk_hra111_risk_025`.

Those FKs correctly prevent dangling catalog IDs, but they do not demonstrate catalog-family validation. A `likelihoodLevelId` could still reference an unrelated RiskCatalogEntry unless the write/provisioning boundary checks `catalogName = RISK_LIKELIHOOD_LEVEL`; the same issue applies to consequence level.

No current RiskMatrixCell application service or dedicated write policy was found that performs this family validation.

### 45.5 Missing matrix-cell uniqueness invariant

Risk DDD explicitly requires:

```text
unique cell per matrix, likelihood, consequence
```

The base schema has separate indexes for `risk_matrix_id`, `likelihood_level_id`, and `consequence_level_id`, but no unique constraint or unique index on the three-column matrix coordinate.

Repository search also found no application/domain uniqueness policy for RiskMatrixCell.

Therefore two different rows can currently represent the same matrix coordinate and potentially carry conflicting scores/ratings.

This is a master-data integrity defect because scoring must resolve deterministically to one cell for a given matrix version and likelihood/consequence pair.

### 45.6 Missing nonnegative-score invariant

Risk DDD explicitly states:

```text
scoreValue must be non-negative
```

The domain constructor only checks `scoreValue != null`. The JPA mapping and `numeric(18,6) NOT NULL` schema likewise permit negative values, and no later CHECK constraint was found.

Therefore a semantically invalid negative risk score can be constructed and persisted.

This obligation is stronger than a generic numeric-validation preference because the active Risk DDD states the rule directly.

### 45.7 Governance booleans and rating policy deliberately not over-inferred

`requiresTreatment`, `requiresApproval`, and `requiresExecutiveAcceptance` are stored directly on the matrix cell. `RiskRating` separately carries `requiresTreatment` and `requiresApproval` properties.

Current repository evidence does not state that the cell booleans must always equal the referenced RiskRating values. They may represent matrix-specific escalation policy beyond the reusable rating classification.

HMSR-032 therefore does not invent equality constraints between cell and rating flags, nor does it infer executive-acceptance thresholds from rating severity.

`colorCode` is presentation metadata; no authoritative format such as hex RGB is defined, so no format invariant is added.

### 45.8 Matrix completeness and immutability boundary

Risk DDD states that an approved matrix must contain complete cells and an active matrix version must be immutable.

Those are aggregate/matrix-level lifecycle rules rather than locally decidable RiskMatrixCell constructor invariants. HMSR-032 records no cell-level mutation rule beyond the explicit cell invariants above; the future RiskMatrix review/correction must own completeness and active-version immutability.

### 45.9 Required revisions

Three evidence-backed correction obligations remain:

1. **Enforce matrix-coordinate uniqueness.** The combination `(riskMatrixId, likelihoodLevelId, consequenceLevelId)` must identify at most one RiskMatrixCell. A separately authorized Risk correction should implement this with an additive unique database constraint/index and aligned application behavior.

2. **Enforce nonnegative score values.** `scoreValue < 0` must be rejected at an appropriate fail-fast domain/application boundary and/or by an additive database CHECK, consistent with the explicit Risk DDD invariant.

3. **Protect catalog-family semantics for likelihood and consequence levels.** A populated `likelihoodLevelId` must resolve to the `RISK_LIKELIHOOD_LEVEL` Risk catalog family and `consequenceLevelId` to `RISK_CONSEQUENCE_LEVEL`; generic row-existence FKs alone do not establish the required taxonomy.

HMSR-032 does not modify production Java, JPA, Flyway, API/application contracts, tests, risk matrices/cells/catalogs, or provisioned data.

### 45.10 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, RiskMatrixCell is governed risk-methodology master data. A single matrix version must map each likelihood/consequence coordinate deterministically to one nonnegative score and one controlled rating.

Duplicate coordinates, negative scores, or likelihood/consequence IDs drawn from the wrong taxonomy would make inherent/residual risk calculations inconsistent and could alter treatment, approval or executive-acceptance decisions.

The cell defines scoring policy; it does not own the operational event, pipeline condition, HSE case, integrity defect, or other source fact being assessed.

### 45.11 Review conclusion

**REVISE.** `RiskMatrixCell` has a coherent 12-field Risk-owned role, correct Level-0 placement, required reference validation, and database-protected parent matrix/rating/catalog row existence.

The target baseline cannot mark it APPROVED while the explicit DDD uniqueness and nonnegative-score invariants are unenforced and while likelihood/consequence catalog references are not demonstrably restricted to their required Risk catalog families.

HMS reconciliation must retain these obligations until an explicitly authorized Risk correction task resolves them or stronger repository evidence explicitly changes the Risk matrix semantics.

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

- `87733b910bdaaae1a708dc4c649cbbe38068876e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:84](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L84)
- [docs/roadmap/model-semantic-review.md:227](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L227)
- [docs/roadmap/model-semantic-review.md:7522](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L7522)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:227 | HMSR-032 | 0 | risk | RiskMatrixCell | — | — | 0 | 0 | 2 | REVISE | Completed | docs(model-review): review risk RiskMatrixCell
doc/domain/SEMANTIC_DECISIONS.md:84 | HMSR-032 | [risk.RiskMatrixCell](#risk-riskmatrixcell) | REVISE | HMR-029
~~~~~~~~

<a id="hapi-368"></a>
## HAPI-368 — review risk RiskRegister

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMSR-058

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review risk RiskRegister

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 71. HMSR-058 — risk.RiskRegister review

**Decision:** REVISE  
**Review code:** HMSR-058  
**Dependency level:** 2  
**Bounded context:** risk  
**Confirmed upstream subject dependencies:** optional `organization.OrganizationUnit`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 2

### 71.1 Semantic role

`RiskRegister` is the Risk-owned governed container for risks within a typed operational/business scope. The Risk DDD assigns it ownership of register identity, scope, classification, status and review policy. It carries multilingual names, an optional Organization owner snapshot, typed scope snapshots, lifecycle status, effective dates and creator snapshot metadata.

Risk owns the register and its risk-policy semantics. It does not own Organization, Identity or the external business objects selected by the typed scope namespace.

### 71.2 Register-type catalog semantics

`registerTypeId` is mandatory. HRA-111 protects same-module row existence with:

```text
hidra_risk_register.register_type_id -> hidra_risk_catalog_entry.id
```

The Risk DDD separately defines `RISK_REGISTER_TYPE` as the controlled catalog family for register classification.

The current create path accepts `registerTypeId` and saves the register directly. The generic FK proves only that some RiskCatalogEntry exists; it does not prove that the row belongs to `RISK_REGISTER_TYPE` or is eligible/active for new registers.

### 71.3 Organization ownership boundary

`ownerOrganizationUnitId` is optional and accompanied by `ownerOrganizationUnitNameSnapshot`. It unambiguously references Organization-owned identity.

`RiskApplicationService.createRiskRegister()` persists a populated owner OrganizationUnit ID without resolving it through an Organization-owned lookup/application contract.

No cross-module database FK should be introduced. When the owner is populated, its identity should fail closed through the Organization boundary while preserving the supplied historical/display snapshot semantics.

### 71.4 Typed scope semantics

The register requires `scopeId`, while persistence also requires `scopeType`. HRA-111 explicitly reclassified `risk.RiskRegister.scopeId` as `NON_RELATIONAL_IDENTIFIER` because `scopeType + scopeId` form a typed business-scope namespace with code/label snapshots.

This classification is correct and must be preserved: the scope must not be forced to `RiskAssessmentScope`, a Risk self-reference, or one cross-module database FK.

The current domain constructor guards `scopeId` but not `scopeType`, and the create path performs no owner-specific typed-scope resolution. The authoritative write path therefore needs to reject semantically incomplete scope identity and validate supported typed scopes through the appropriate owning boundary/contract when such validation is available.

HMSR-058 does not invent the allowed `scopeType` vocabulary.

### 71.5 Review policy reference

`reviewFrequencyId` is optional and belongs to the register's review-policy semantics, but current Risk DDD/catalog evidence does not define a corresponding review-frequency catalog family. The catalog list includes `RISK_REVIEW_TYPE`, which is not evidence that it is interchangeable with a review frequency.

Therefore HMSR-058 does not manufacture a catalog-family mapping. The field's controlled-value ownership must be explicitly resolved before the semantic baseline can treat this part of the model as approved.

### 71.6 Lifecycle and temporal semantics

`RiskRegisterStatus` is a Risk-owned lifecycle enum:

```text
DRAFT
ACTIVE
SUSPENDED
RETIRED
ARCHIVED
```

The active create path initializes new registers as `DRAFT`. Repository evidence does not provide a complete register transition matrix, so HMSR-058 does not invent additional transitions.

The domain already enforces the classified `effectiveFrom <= effectiveTo` invariant. No additional createdAt/updatedAt ordering rule is introduced.

### 71.7 Creation audit/event obligation

The Risk DDD explicitly lists `RiskRegisterCreated` as a domain event and requires risk register creation/update to emit audit-ready events. The current `createRiskRegister()` path only constructs and saves the register; its constructor has repository ports for register, assessment and evidence persistence only and demonstrates no audit/outbox publication for register creation.

This is retained as a production obligation. HMSR-058 does not implement event publication or invent an event payload beyond the DDD's existing audit requirements.

### 71.8 Multilingual and snapshot handling

The model stores `nameAr`, `nameFr` and `nameEn` directly and therefore already supports the project's three operating languages without a separate translation entity for the register itself.

Current evidence is insufficient to promote every persistence nullability choice into a domain invariant. In particular, HMSR-058 does not invent new field-level language requirements from JPA `NOT NULL` alone.

Organization/scope/creator snapshot fields remain snapshots and must not be converted into owning dependencies.

### 71.9 Required revisions

Five evidence-backed production/semantic obligations remain:

1. **Enforce Risk register-type family eligibility.** A mandatory `registerTypeId` must resolve to an eligible `RiskCatalogEntry` in the `RISK_REGISTER_TYPE` family; generic FK row existence is insufficient.

2. **Validate populated `ownerOrganizationUnitId` through the Organization owner boundary.** Preserve snapshot semantics and do not add a cross-module database FK.

3. **Make typed scope identity fail closed.** Preserve `scopeType + scopeId` as a non-relational typed namespace, require a semantically complete pair at the authoritative write boundary, and resolve supported types through their owning contracts rather than inventing one relational target.

4. **Resolve `reviewFrequencyId` controlled-value semantics.** Current DDD does not identify a defensible review-frequency catalog family; do not map it to `RISK_REVIEW_TYPE` without stronger evidence.

5. **Honor the explicit register creation/update audit-event contract.** Integrate the authoritative write path with the existing Risk audit/outbox architecture when production reconciliation is authorized.

HMSR-058 does not modify production Java, JPA, Flyway, API/application contracts, tests, catalog data, Organization data, audit infrastructure, or provisioned data.

### 71.10 Review conclusion

**REVISE.** `RiskRegister` is a coherent 21-field Risk-owned register model with explicit multilingual naming, lifecycle state, temporal bounds, Organization ownership metadata and typed scope snapshots. Its same-module register-type row existence and downstream RiskAssessment reference are database-protected where appropriate.

The target baseline cannot mark it APPROVED while register-type family eligibility is unenforced, populated Organization ownership is not owner-validated, typed scope completeness/owner resolution is not fail-closed, `reviewFrequencyId` has unresolved controlled-value semantics, and the DDD's register audit/event contract is absent from the live creation path.

HMS reconciliation must retain these obligations until explicitly authorized Risk production corrections resolve them or stronger repository evidence changes the governing Risk DDD.

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

- `ed3849873ea668c38b3423f20be76413163ff013` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:110](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L110)
- [docs/roadmap/model-semantic-review.md:253](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L253)
- [docs/roadmap/model-semantic-review.md:11894](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L11894)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:253 | HMSR-058 | 2 | risk | RiskRegister | — | organization.OrganizationUnit | 1 | 2 | 0 | REVISE | Completed | docs(model-review): review risk RiskRegister
doc/domain/SEMANTIC_DECISIONS.md:110 | HMSR-058 | [risk.RiskRegister](#risk-riskregister) | REVISE | HMR-049
~~~~~~~~

<a id="hapi-379"></a>
## HAPI-379 — review risk RiskAssessment

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMSR-069

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review risk RiskAssessment

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 82. HMSR-069 — risk.RiskAssessment review

**Decision:** REVISE  
**Review code:** HMSR-069  
**Dependency level:** 3  
**Bounded context:** risk  
**Confirmed upstream subject dependencies:** `risk.RiskRegister`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 1

### 82.1 Semantic role

`RiskAssessment` is the Risk-owned governed evaluation of one risk scenario over one or more scopes. It anchors the parent RiskRegister and scenario, assessment type/methodology, lifecycle state, validity interval, assessor/reviewer/approver snapshots, inherent/residual scoring components, confidence/uncertainty metadata and workflow/audit references.

Risk owns the assessment, scoring semantics and approval state. Identity, Workflow and Audit remain external bounded contexts.

### 82.2 Parent register and scenario integrity

`riskRegisterId` and `riskScenarioId` are mandatory same-module references.

HRA-111 protects:

```text
hidra_risk_assessment.risk_register_id
    -> hidra_risk_register.id

hidra_risk_assessment.risk_scenario_id
    -> hidra_risk_scenario.id
```

These are the correct same-module ownership relationships.

The active `createRiskAssessment()` path still accepts both IDs from the command and relies on persistence integrity. HMSR-069 finds no need to replace those same-module FKs with cross-module lookup semantics.

### 82.3 Assessment-type and methodology catalog semantics

`assessmentTypeId` and `methodologyId` are mandatory.

HRA-111 protects both against generic `hidra_risk_catalog_entry` row existence.

The Risk DDD defines exact catalog families:

```text
RISK_ASSESSMENT_TYPE
RISK_METHODLOGY
```

The repository spelling `RISK_METHODLOGY` is preserved here exactly.

Generic catalog-row existence is insufficient to prove family membership or active eligibility. The authoritative assessment creation/update path must validate these IDs against their required Risk catalog families.

### 82.4 Required assessment scope

The Risk DDD explicitly requires:

```text
Risk assessment must reference at least one scope.
```

The dedicated `RiskAssessmentScope` persistence model carries:

```text
riskAssessmentId
scopeType
scopeId
scopeCodeSnapshot
scopeLabelSnapshot
topologySnapshotId
operationalPeriodStart
operationalPeriodEnd
included
scopeNote
```

and HRA-111 protects `riskAssessmentId -> RiskAssessment`.

By contrast, the current `createRiskAssessment()` path only copies the optional scalar `command.scopeId()` into the parent RiskAssessment and does not create or validate any RiskAssessmentScope child row.

Therefore the active creation path can produce an assessment that violates the DDD's explicit at-least-one-scope rule.

The target baseline should treat `RiskAssessmentScope` as the authoritative multi-scope structure. HMSR-069 does not invent a mandatory meaning for the parent scalar `scopeId`; reconciliation should determine whether it remains a convenience/primary-scope pointer or is retired after the child-scope contract is implemented.

### 82.5 Scoring-component catalog semantics

The assessment can carry inherent and residual:

```text
likelihood
consequence
score
rating
```

plus `confidenceLevelId`.

The Risk DDD defines:

```text
RISK_LIKELIHOOD_LEVEL
RISK_CONSEQUENCE_LEVEL
RISK_RATING
RISK_CONFIDENCE_LEVEL
```

for those controlled values.

Current HRA-111 evidence does not provide assessment-side FKs for the populated likelihood, consequence, rating or confidence IDs.

When populated, these values must fail closed against the correct Risk-owned catalog families and eligibility semantics.

HMSR-069 does not require all scoring fields to be present at DRAFT creation; the current create path intentionally creates a DRAFT assessment before scoring.

### 82.6 Matrix-based score consistency

The Risk DDD explicitly avoids hard-coding one universal scoring formula and instead requires matrix-based scoring support through `RiskMatrix` and `RiskMatrixCell`.

Therefore HMSR-069 does not assert that `score == likelihood × consequence` in every case.

However, once an assessment is scored, its likelihood/consequence/rating tuple must be produced through the selected Risk scoring/matrix policy rather than accepting unrelated catalog IDs and free numeric scores independently.

The current `createRiskAssessment()` path does not populate scoring state, so this is a later scoring/transition obligation rather than a DRAFT-creation defect.

### 82.7 Evidence before approval

The Risk DDD explicitly requires:

```text
Risk assessment must have evidence before approval.
```

`RiskEvidenceLink` exists as a separate Risk-owned model, but its repository port currently exposes only save/find-by-id and the live application service implements only evidence addition and DRAFT assessment creation.

Repository search found no live `ApproveRiskAssessmentUseCase` implementation that verifies evidence before moving an assessment to APPROVED/ACTIVE.

The authoritative approval boundary must therefore fail closed unless required evidence exists.

### 82.8 Approval identity, workflow and immutability

The model carries optional:

```text
approvedByActorId
approvedByDisplayNameSnapshot
approvedAt
workflowReferenceId
auditReferenceId
```

The DDD defines approval/rejection inbound ports, Workflow integration, audit-ready approval events, and states:

```text
Approved risk assessment is immutable except through new review/revision.
```

The current domain record can represent APPROVED or ACTIVE state without requiring approver/timestamp context, and repository search found no live approval use case implementing the workflow/evidence/immutability contract.

Future authorized reconciliation must centralize approval so it validates the actor/authority through Identity/Workflow contracts, records approval metadata, emits audit-ready evidence, and prevents in-place mutation of approved assessments except through the modeled review/revision path.

HMSR-069 does not invent a new cross-module database FK for Workflow, Identity or Audit.

### 82.9 Temporal semantics

The domain already enforces:

```text
validFrom <= validTo
```

This matches the existing invariant-classification architecture.

Current Risk DDD evidence does not state additional mandatory ordering between `assessmentDate`, `approvedAt`, `validFrom` and `validTo`, so HMSR-069 does not invent such rules.

### 82.10 Lifecycle semantics

`RiskAssessmentStatus` is a genuine Risk lifecycle enum:

```text
DRAFT
UNDER_REVIEW
APPROVED
ACTIVE
SUPERSEDED
RETIRED
REJECTED
CANCELLED
EXPIRED
```

The live create path correctly starts new assessments as `DRAFT`.

The DDD names submit/approve/reject/review use cases but does not provide a complete transition matrix for every status. HMSR-069 therefore does not manufacture unsupported transitions.

### 82.11 Required revisions

Five evidence-backed obligations remain:

1. **Enforce exact catalog-family eligibility for `assessmentTypeId` and `methodologyId`.** Generic RiskCatalogEntry existence is insufficient; use `RISK_ASSESSMENT_TYPE` and `RISK_METHODLOGY`.

2. **Implement the mandatory assessment-scope contract.** A RiskAssessment must have at least one authoritative `RiskAssessmentScope`; reconcile the parent scalar `scopeId` as convenience/primary-scope state rather than treating it as a substitute for the required child scope set.

3. **Protect populated scoring/confidence references.** Likelihood, consequence, rating and confidence IDs must resolve to the correct Risk catalog families, and scored tuples must be produced through the applicable Risk scoring/matrix policy.

4. **Enforce evidence-before-approval and approval workflow/actor semantics.** Approval must verify evidence, resolve the authorized actor/workflow context, record approval metadata and emit audit-ready evidence without cross-module DB FKs.

5. **Enforce approved-assessment immutability.** APPROVED/ACTIVE assessments must not be modified in place except through the DDD's review/revision path.

HMSR-069 does not modify production Java, JPA, Flyway, API/application contracts, tests, Risk catalogs/matrices/scopes/evidence, Workflow/Identity/Audit data, or provisioned data.

### 82.12 Review conclusion

**REVISE.** `RiskAssessment` is a coherent 34-field Risk-owned assessment model with correct parent-register/scenario ownership, a valid lifecycle enum and an implemented validity-window invariant.

The target baseline cannot mark it APPROVED while mandatory assessment scopes are not created/validated, required catalog-family semantics are unenforced, scoring/reference consistency remains weak, evidence-before-approval and approval authority are not implemented, and approved-state immutability is not protected.

HMS reconciliation must retain these obligations until explicitly authorized Risk production corrections resolve them or stronger repository evidence changes the governing Risk DDD.

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

- `fb200b1e60b65cd466bd1c7aeb0a67c21f2bdd0b` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:121](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L121)
- [docs/roadmap/model-semantic-review.md:264](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L264)
- [docs/roadmap/model-semantic-review.md:13368](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L13368)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:264 | HMSR-069 | 3 | risk | RiskAssessment | — | risk.RiskRegister | 1 | 1 | 10 | REVISE | Completed | docs(model-review): review risk RiskAssessment
doc/domain/SEMANTIC_DECISIONS.md:121 | HMSR-069 | [risk.RiskAssessment](#risk-riskassessment) | REVISE | HMR-058
~~~~~~~~

<a id="hapi-401"></a>
## HAPI-401 — review risk RiskEvidenceLink

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HMSR-091

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review risk RiskEvidenceLink

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAlarmEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssetEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskHseEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskIncidentEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskIntegrityEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskTelemetryEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`, `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`, `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`, `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAlarmEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssetEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskDocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskHseEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAlarmEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssetEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskHseEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskIncidentEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskIntegrityEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskTelemetryEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 104. HMSR-091 — risk.RiskEvidenceLink review

**Decision:** REVISE  
**Review code:** HMSR-091  
**Dependency level:** 4  
**Bounded context:** risk  
**Confirmed upstream subject dependencies:** mandatory `risk.RiskAssessment`; neutral external evidence tuple owned by source modules  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0

### 104.1 Semantic role

`RiskEvidenceLink` is the Risk-owned association between a governed `RiskAssessment` and evidence owned by another bounded context.

Risk owns the fact that a piece of evidence supports an assessment. It does not own the underlying Monitoring, Alarm, Incident, HSE, Integrity, Assets, Simulation, Telemetry, Custody, Documents, or Audit record.

### 104.2 Risk-assessment integrity

`riskAssessmentId` is mandatory and same-module.

HRA-111 already protects it with:

```text
hidra_risk_evidence_link.risk_assessment_id
  -> hidra_risk_assessment.id
```

The domain constructor also rejects blank `riskAssessmentId`.

No further basic existence correction is required for the parent Risk assessment.

### 104.3 External evidence identity tuple

The Risk DDD defines the external evidence identity through:

```text
evidenceModule
evidenceType
evidenceId
```

and lists concrete external evidence examples such as MonitoringEvaluation, RiskSignal, Alarm, Incident, Integrity evidence, MaintenanceWorkOrder, SimulationRun, TelemetryReading, CustodyTransferTicket, Document and AuditEvent.

The live domain currently rejects blank `evidenceId` but only normalizes `evidenceModule` and `evidenceType`; both can therefore become null.

JPA/Flyway persist `evidenceModule` and `evidenceType` as `NOT NULL`.

For this model, module/type are not incidental labels: together with `evidenceId` they identify which owner boundary can resolve the evidence. The authoritative domain/application boundary must reject blank module/type values rather than relying on persistence failure.

### 104.4 Owner-controlled evidence validation

The Risk DDD explicitly defines owner-specific outbound lookup ports, including:

```text
RiskTelemetryEvidenceLookupPort
RiskMonitoringEvidenceLookupPort
RiskAlarmEvidenceLookupPort
RiskIncidentEvidenceLookupPort
RiskIntegrityEvidenceLookupPort
RiskHseEvidenceLookupPort
RiskAssetEvidenceLookupPort
RiskSimulationEvidenceLookupPort
RiskDocumentReferencePort
```

and states that Risk must consume evidence from other contexts without taking over their state.

The live `RiskApplicationService.addRiskEvidence()` copies the evidence tuple directly from the command and saves it; it does not resolve or validate the referenced external evidence through an owner-controlled port.

The authoritative add-evidence boundary should therefore dispatch validation according to the supported evidence module/type and fail closed when the referenced evidence cannot be resolved or is not eligible for Risk linkage.

No cross-module database foreign key should be introduced.

### 104.5 Evidence snapshots

`evidenceCodeSnapshot`, `evidenceLabelSnapshot`, `evidenceTimestamp`, `evidenceHash`, and `evidenceSummary` are appropriate historical/context snapshots attached to the link.

They should remain snapshots rather than becoming alternate ownership of source-module business data.

Current Risk evidence does not require every snapshot field to be populated, so HMSR-091 does not invent such a requirement.

### 104.6 Duplicate-link semantics

Current Risk DDD evidence reviewed for HMSR-091 does not define a uniqueness rule such as one link per `(riskAssessmentId, evidenceModule, evidenceType, evidenceId)`.

HMSR-091 therefore does not invent a database uniqueness constraint or idempotency policy for duplicate evidence links.

If a current Risk roadmap/application contract later establishes idempotent evidence linking, HMS-006 may reconcile it then.

### 104.7 Required revisions

Two evidence-backed production obligations remain:

1. **Enforce the complete external evidence identity tuple before persistence.** `evidenceModule`, `evidenceType`, and `evidenceId` must be nonblank.

2. **Validate external evidence through owner-controlled Risk lookup/reference ports before saving the link.** Supported module/type combinations must resolve to existing/eligible source evidence; preserve scalar IDs and snapshots and do not introduce cross-module database FKs.

HMSR-091 does not modify production Java, JPA, Flyway, API/application contracts, tests, Risk data, source-module data, or provisioned data.

### 104.8 Review conclusion

**REVISE.** `RiskEvidenceLink` correctly keeps Risk ownership limited to the association and already protects the parent `RiskAssessment` with a same-module FK.

The target baseline cannot mark it APPROVED while the live domain permits incomplete module/type evidence identity and the live add-evidence use case accepts external evidence without owner-controlled resolution despite the DDD's explicit lookup-port architecture.

HMS reconciliation must retain these obligations until explicitly authorized Risk production corrections resolve them or stronger repository evidence changes the governing Risk contract.

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

- `af3b885a7dd689965a6f4c4b1ff34517cb4e30c9` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:143](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L143)
- [docs/roadmap/model-semantic-review.md:286](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L286)
- [docs/roadmap/model-semantic-review.md:16447](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L16447)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:286 | HMSR-091 | 4 | risk | RiskEvidenceLink | — | risk.RiskAssessment | 0 | 0 | 1 | REVISE | Completed | docs(model-review): review risk RiskEvidenceLink
doc/domain/SEMANTIC_DECISIONS.md:143 | HMSR-091 | [risk.RiskEvidenceLink](#risk-riskevidencelink) | REVISE | HMR-077
~~~~~~~~

<a id="hapi-616"></a>
## HAPI-616 — Integrated closure: four observation/action/outcome packages

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HPR-P25-057

Priority: P2

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-620

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-057; child tasks own implementation. four observation/action/outcome packages

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-057; child tasks own implementation. four observation/action/outcome packages.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java`.
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
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTargetTransitionApplicationService.java`

**DELETE:** none.

First recover this bounded objective's exact acceptance/replacement from the pinned references, inspect only its current owner seams and decide whether a source change is still needed. If fulfilled, record evidence and status without rewriting working code. If a genuine successor is necessary, absorb it in its existing unfinished owner package or propose PENDING_BASELINE_APPROVAL.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HPR-P25-057 — four observation/action/outcome packages

Prerequisites:050/051/056, retained029 design and D4's target/action/observation policy.
Monitoring, Workflow and Audit retain their own semantics; Analytics consumes explicit
facts. Existing scalar deviation and Workflow transition interfaces do not prove outcomes.

| Planning stage | Remaining delivery | Evidence / exit criterion |
|---|---|---|
| HPR-P25-057A | Monitoring conditions and measured/declared/estimated/UNKNOWN observation evidence. | Eligible source/quality/time window and configurable threshold provenance, authorized current/history; stale/unknown/confounded input never becomes certainty. |
| HPR-P25-057B | Authenticated operator action declaration and actual supported Workflow target transitions. | Actor/target/operation authority server-owned, allowed/denied transitions and idempotency/commit-uncertainty behavior, no caller permission/identity promotion. |
| HPR-P25-057C | Subsequent observation/outcome correlation, factual audit/query and recovery. | Action requested/declaration distinct from physical action observed; missing/delayed/confounded observation explicit, action-without-outcome and observation-without-action supported. No unsupported causality or OT execution. |
| HPR-P25-057D | Cross-owner action/observation security, rollback and consumer qualification. | Withdrawn permission, spoofed actor, duplicate event, missing outcome, denied cross-company history and downstream failure fully covered through actual APIs/owners; no automatic learning promotion or solver prerequisite. |

Record outcomes supported by observed data only. Retain existing Alarm/Incident/HSE/Risk
lifecycle behavior and do not fabricate business-event publishers from configured transport.

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-620. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23438](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23438)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1376](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1376)
- [doc/roadmap/ULTIMATE_ROADMAP.md:5221](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L5221)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-617"></a>
## HAPI-617 — Monitoring conditions and observation evidence

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HPR-P25-057A

Priority: P2

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-610

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Monitoring conditions and measured/declared/estimated/UNKNOWN observation evidence.

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/monitoring/MonitoringModule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/MonitoringApi.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/MonitoringRestApi.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringQueryController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/SpringMonitoringController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/mapper/MonitoringGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/mapper/MonitoringRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/request/CreateMonitoringRuleRequest.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/response/MonitoringRuleResponse.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/command/CreateMonitoringRuleCommand.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/dto/MonitoringRuleSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/mapper/MonitoringApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/exception/InvalidMonitoringValueException.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/exception/MonitoringBoundaryViolationException.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/exception/MonitoringDomainException.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: Monitoring conditions and measured/declared/estimated/UNKNOWN observation evidence..

### 4. Functional Requirements

Eligible source/quality/time window and configurable threshold provenance, authorized current/history; stale/unknown/confounded input never becomes certainty.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/monitoring/domain/exception/InvalidMonitoringValueException.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/exception/MonitoringBoundaryViolationException.java`, `src/main/java/dz/sh/hidra/modules/monitoring/domain/exception/MonitoringDomainException.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/OperationalObservation.java` — pure Java owned aggregate/value and invariants for supported condition/target observations with source, time, qualification and explicit uncertainty.
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/OperationalObservationUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/OperationalObservationRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/OperationalObservationApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/OperationalObservationController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JdbcOperationalObservationRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/monitoring/MonitoringModule.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/MonitoringApi.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/MonitoringRestApi.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/MonitoringQueryController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/SpringMonitoringController.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/mapper/MonitoringGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/mapper/MonitoringRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/request/CreateMonitoringRuleRequest.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/response/MonitoringRuleResponse.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/command/CreateMonitoringRuleCommand.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/dto/MonitoringRuleSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/mapper/MonitoringApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/exception/InvalidMonitoringValueException.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/exception/MonitoringBoundaryViolationException.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/exception/MonitoringDomainException.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/monitoring/observations` for supported condition/target observations with source, time, qualification and explicit uncertainty. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Eligible source/quality/time window and configurable threshold provenance, authorized current/history; stale/unknown/confounded input never becomes certainty.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-610. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23446](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23446)
- [doc/model-remediation/RECONCILIATION.md:23710](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23710)
- [doc/model-remediation/RECONCILIATION.md:23759](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23759)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1018](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1018)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1067](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1067)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1384](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1384)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1018 | HPR-P25-057A | Select supported monitored condition/target families and approved configurable threshold policy; do not require real measurements or assume causality. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1067 | HPR-P25-057A | REQUIRES_OWNER_DECISION | Monitoring conditions and observation evidence
doc/roadmap/ULTIMATE_ROADMAP.md:1384 | HPR-P25-057A | Monitoring conditions and measured/declared/estimated/UNKNOWN observation evidence. | Eligible source/quality/time window and configurable threshold provenance, authorized current/history; stale/unknown/confounded input never becomes certainty.
doc/model-remediation/RECONCILIATION.md:23446 | HPR-P25-057A | Monitoring conditions and measured/declared/estimated/UNKNOWN observation evidence. | Eligible source/quality/time window and configurable threshold provenance, authorized current/history; stale/unknown/confounded input never becomes certainty.
doc/model-remediation/RECONCILIATION.md:23710 | HPR-P25-057A | Select supported monitored condition/target families and approved configurable threshold policy; do not require real measurements or assume causality. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23759 | HPR-P25-057A | REQUIRES_OWNER_DECISION | Monitoring conditions and observation evidence
~~~~~~~~

<a id="hapi-618"></a>
## HAPI-618 — Authenticated human action declarations and Workflow

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HPR-P25-057B

Priority: P2

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-617

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Authenticated operator action declaration and actual supported Workflow target transitions.

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: Authenticated operator action declaration and actual supported Workflow target transitions..

### 4. Functional Requirements

Actor/target/operation authority server-owned, allowed/denied transitions and idempotency/commit-uncertainty behavior, no caller permission/identity promotion.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/HumanOperationalAction.java` — pure Java owned aggregate/value and invariants for authenticated human declaration, intended action and approved Workflow reference distinct from physical execution.
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/HumanOperationalActionUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/HumanOperationalActionRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/HumanOperationalActionApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/HumanOperationalActionController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JdbcHumanOperationalActionRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

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
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/monitoring/human-actions` for authenticated human declaration, intended action and approved Workflow reference distinct from physical execution. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Actor/target/operation authority server-owned, allowed/denied transitions and idempotency/commit-uncertainty behavior, no caller permission/identity promotion.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-617. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23447](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23447)
- [doc/model-remediation/RECONCILIATION.md:23711](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23711)
- [doc/model-remediation/RECONCILIATION.md:23760](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23760)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1019](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1019)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1068](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1068)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1385](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1385)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1019 | HPR-P25-057B | Approve supported human declaration/action targets and whether approval precedes declared execution for each; no autonomous action is introduced. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1068 | HPR-P25-057B | REQUIRES_OWNER_DECISION | Authenticated human action declarations and Workflow
doc/roadmap/ULTIMATE_ROADMAP.md:1385 | HPR-P25-057B | Authenticated operator action declaration and actual supported Workflow target transitions. | Actor/target/operation authority server-owned, allowed/denied transitions and idempotency/commit-uncertainty behavior, no caller permission/identity promotion.
doc/model-remediation/RECONCILIATION.md:23447 | HPR-P25-057B | Authenticated operator action declaration and actual supported Workflow target transitions. | Actor/target/operation authority server-owned, allowed/denied transitions and idempotency/commit-uncertainty behavior, no caller permission/identity promotion.
doc/model-remediation/RECONCILIATION.md:23711 | HPR-P25-057B | Approve supported human declaration/action targets and whether approval precedes declared execution for each; no autonomous action is introduced. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23760 | HPR-P25-057B | REQUIRES_OWNER_DECISION | Authenticated human action declarations and Workflow
~~~~~~~~

<a id="hapi-619"></a>
## HAPI-619 — Observation/outcome correlation and recovery

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HPR-P25-057C

Priority: P2

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-618

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Subsequent observation/outcome correlation, factual audit/query and recovery.

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: Subsequent observation/outcome correlation, factual audit/query and recovery..

### 4. Functional Requirements

Action requested/declaration distinct from physical action observed; missing/delayed/confounded observation explicit, action-without-outcome and observation-without-action supported. No unsupported causality or OT execution.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/ObservedActionOutcome.java` — pure Java owned aggregate/value and invariants for independently observed factual outcome and correlation with UNKNOWN/confounder semantics.
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/ObservedActionOutcomeUseCase.java` — typed command/query input and authorization-aware orchestration contract.
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/ObservedActionOutcomeRepositoryPort.java` — owned persistence and expected-version contract.
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/ObservedActionOutcomeApplicationService.java` — owner transaction, permission evidence and domain coordination.
- `src/main/java/dz/sh/hidra/modules/monitoring/api/rest/controller/ObservedActionOutcomeController.java` — explicit request/response DTO mapping and documented errors.
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JdbcObservedActionOutcomeRepositoryAdapter.java` — PostgreSQL implementation with parameterized statements, constraints and concurrency checks.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

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
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/monitoring/action-outcomes` for independently observed factual outcome and correlation with UNKNOWN/confounder semantics. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Action requested/declaration distinct from physical action observed; missing/delayed/confounded observation explicit, action-without-outcome and observation-without-action supported. No unsupported causality or OT execution.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-618. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23448](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23448)
- [doc/model-remediation/RECONCILIATION.md:23712](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23712)
- [doc/model-remediation/RECONCILIATION.md:23761](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23761)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1020](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1020)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1069](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1069)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1386](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1386)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1020 | HPR-P25-057C | Select factual outcome statuses and correlation/confounder policy; UNKNOWN must remain available without inferring industrial causality. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/roadmap/ULTIMATE_ROADMAP.md:1069 | HPR-P25-057C | REQUIRES_OWNER_DECISION | Observation/outcome correlation and recovery
doc/roadmap/ULTIMATE_ROADMAP.md:1386 | HPR-P25-057C | Subsequent observation/outcome correlation, factual audit/query and recovery. | Action requested/declaration distinct from physical action observed; missing/delayed/confounded observation explicit, action-without-outcome and observation-without-action supported. No unsupported causality or OT execution.
doc/model-remediation/RECONCILIATION.md:23448 | HPR-P25-057C | Subsequent observation/outcome correlation, factual audit/query and recovery. | Action requested/declaration distinct from physical action observed; missing/delayed/confounded observation explicit, action-without-outcome and observation-without-action supported. No unsupported causality or OT execution.
doc/model-remediation/RECONCILIATION.md:23712 | HPR-P25-057C | Select factual outcome statuses and correlation/confounder policy; UNKNOWN must remain available without inferring industrial causality. | Business/security data owner for policy; accountable bounded-context owner and independent architecture/database reviewer for technical registration
doc/model-remediation/RECONCILIATION.md:23761 | HPR-P25-057C | REQUIRES_OWNER_DECISION | Observation/outcome correlation and recovery
~~~~~~~~

<a id="hapi-620"></a>
## HAPI-620 — Cross-owner operational action/observation qualification

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: HPR-P25-057D

Priority: P2

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `monitoring` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-619

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Cross-owner action/observation security, rollback and consumer qualification.

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: Cross-owner action/observation security, rollback and consumer qualification..

### 4. Functional Requirements

Withdrawn permission, spoofed actor, duplicate event, missing outcome, denied cross-company history and downstream failure fully covered through actual APIs/owners; no automatic learning promotion or solver prerequisite.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `monitoring`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/CreateMonitoringRuleUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/MonitoringQueryUseCase.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/in/RecordDeviationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/MonitoringRuleRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`, `src/main/java/dz/sh/hidra/modules/monitoring/application/service/MonitoringRuleApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — PROPOSED:**

- `src/test/java/dz/sh/hidra/modules/monitoring/qualification/OperationalActionOutcomeQualificationTest.java` — actual owner/filter/database qualification of this task's exit matrix.

**MODIFY:** `.github/ci-test-inventory.json` — actual class/case/hash admission; `doc/roadmap/ULTIMATE_TASKS.md` — exact-source evidence and limitations.

**DELETE:** none.

**PRESERVE / REUSE:**

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
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`

**DISCOVERY_REQUIRED:** fixture/support filenames and exact test partition; use synthetic owners and real transactions, not fabricated prior results.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/MonitoringRuleSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Withdrawn permission, spoofed actor, duplicate event, missing outcome, denied cross-company history and downstream failure fully covered through actual APIs/owners; no automatic learning promotion or solver prerequisite.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-619. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:23449](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23449)
- [doc/model-remediation/RECONCILIATION.md:23762](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L23762)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1070](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1070)
- [doc/roadmap/ULTIMATE_ROADMAP.md:1387](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L1387)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/ULTIMATE_ROADMAP.md:1070 | HPR-P25-057D | BLOCKED_SPECIFICATION | Cross-owner operational action/observation qualification
doc/roadmap/ULTIMATE_ROADMAP.md:1387 | HPR-P25-057D | Cross-owner action/observation security, rollback and consumer qualification. | Withdrawn permission, spoofed actor, duplicate event, missing outcome, denied cross-company history and downstream failure fully covered through actual APIs/owners; no automatic learning promotion or solver prerequisite.
doc/model-remediation/RECONCILIATION.md:23449 | HPR-P25-057D | Cross-owner action/observation security, rollback and consumer qualification. | Withdrawn permission, spoofed actor, duplicate event, missing outcome, denied cross-company history and downstream failure fully covered through actual APIs/owners; no automatic learning promotion or solver prerequisite.
doc/model-remediation/RECONCILIATION.md:23762 | HPR-P25-057D | BLOCKED_SPECIFICATION | Cross-owner operational action/observation qualification
~~~~~~~~

<a id="hapi-705"></a>
## HAPI-705 — Implement accountable AI-assisted evidence synthesis

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: None — initial registration

Priority: P2

Implementation Status: NOT_IMPLEMENTED

Planning Status: BLOCKED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-604, HAPI-616

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Add a governed, provider-neutral synthesis adapter behind an outbound port; use authorized versioned evidence, deterministic safety checks, provenance, explicit uncertainty and human approval without granting model output operational authority.

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/controller/SpringAnalyticsController.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: Add a governed, provider-neutral synthesis adapter behind an outbound port; use authorized versioned evidence, deterministic safety checks, provenance, explicit uncertainty and human approval without granting model output operational authority..

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/OperationalEvidenceSynthesisPort.java` — provider-neutral bounded request/result/error model; no vendor types inward.
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/OperationalEvidenceSynthesisApplicationService.java` — authorize and pin evidence, record prompt/model provenance and deterministic review gates.
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/integration/OperationalEvidenceSynthesisAdapter.java` — PROPOSED provider adapter; provider and configuration contracts DISCOVERY_REQUIRED.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/controller/SpringAnalyticsController.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/analytics/evidence-syntheses` for authorized immutable evidence sets, provider-neutral synthesis, evidence citations, deterministic safety filters and accountable human review. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-604, HAPI-616. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- Initial standalone successor registration, grounded in the module source inventory and finding register above.

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: New bounded verification/correction obligation admitted by initial baseline authorization; preserve predecessor behavior. Exact owner policy/path admission remains explicit. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-800"></a>
## HAPI-800 — RISK-001 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-001

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-001 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-001 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1650](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1650)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1650 | RISK-001 | package skeleton | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-801"></a>
## HAPI-801 — RISK-002 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-002

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-002 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-002 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1651](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1651)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1651 | RISK-002 | risk catalogs and translations | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-802"></a>
## HAPI-802 — RISK-003 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-003

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-003 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-003 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1652](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1652)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1652 | RISK-003 | risk matrix and matrix cells | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-803"></a>
## HAPI-803 — RISK-004 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-004

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-004 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-004 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1653](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1653)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1653 | RISK-004 | risk register | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-804"></a>
## HAPI-804 — RISK-005 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-005

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-005 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-005 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1654](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1654)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1654 | RISK-005 | risk scenario and threat catalogs | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-805"></a>
## HAPI-805 — RISK-006 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-006

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-006 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-006 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1655](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1655)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1655 | RISK-006 | risk assessment aggregate | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-806"></a>
## HAPI-806 — RISK-007 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-007

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-007 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-007 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1656](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1656)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1656 | RISK-007 | assessment scope and source | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-807"></a>
## HAPI-807 — RISK-008 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-008

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-008 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-008 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1657](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1657)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1657 | RISK-008 | evidence links | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-808"></a>
## HAPI-808 — RISK-009 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-009

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-009 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-009 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1658](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1658)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1658 | RISK-009 | likelihood/consequence/score calculation | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-809"></a>
## HAPI-809 — RISK-010 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-010

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-010 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-010 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1659](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1659)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1659 | RISK-010 | controls | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-810"></a>
## HAPI-810 — RISK-011 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-011

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-011 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-011 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1660](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1660)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1660 | RISK-011 | treatment plan and actions | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-811"></a>
## HAPI-811 — RISK-012 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-012

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-012 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-012 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1661](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1661)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1661 | RISK-012 | risk acceptance | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-812"></a>
## HAPI-812 — RISK-013 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-013

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-013 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-013 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1662](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1662)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1662 | RISK-013 | residual risk assessment | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-813"></a>
## HAPI-813 — RISK-014 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-014

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-014 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-014 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1663](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1663)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1663 | RISK-014 | risk review | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-814"></a>
## HAPI-814 — RISK-015 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-015

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-015 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-015 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1664](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1664)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1664 | RISK-015 | aggregation snapshots | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-815"></a>
## HAPI-815 — RISK-016 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-016

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-016 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-016 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1665](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1665)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1665 | RISK-016 | workflow approval integration | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-816"></a>
## HAPI-816 — RISK-017 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-017

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-017 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-017 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1666](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1666)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1666 | RISK-017 | audit event port | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-817"></a>
## HAPI-817 — RISK-018 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-018

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-018 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-018 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1667](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1667)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1667 | RISK-018 | REST API | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-818"></a>
## HAPI-818 — RISK-019 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-019

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-019 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-019 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1668](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1668)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1668 | RISK-019 | read models and search | Deferred conceptual sequence
~~~~~~~~

<a id="hapi-819"></a>
## HAPI-819 — RISK-020 historical bounded obligation

Phase: HPS-016 — Risk and Operational Intelligence

Legacy IDs: RISK-020

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `risk` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

RISK-020 historical bounded obligation

### 2. Existing Implementation

Structured Risk scope, evidence, score/submit/approve and immutable approved assessment rules exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Deliver independent observation/action/outcome evidence and explicit uncertainty; AI inference and learning remain unverified targets. The exact bounded remaining delivery is: RISK-020 historical bounded obligation.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `risk`; primary phase owns this obligation. No claim of predictive intelligence, automatic causality or autonomous control follows from dataset/insight records. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`, `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`, `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/SpringRiskController.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/CreateRiskRegisterUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskMonitoringEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskSimulationEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`

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

- Preserve the original bounded objective and verify its claimed output against current artifacts. Explain deterministic risk and connect observations, human decisions and independently observed outcomes.
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

- [docs/data definition/Risk.md:1669](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Risk.md#L1669)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Unactivated recommended sequence from data-definition design, not an accepted current executable roadmap. Preserve as deferred scope; reconcile existing domain records before any implementation. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/data definition/Risk.md:1669 | RISK-020 | architecture tests | Deferred conceptual sequence
~~~~~~~~

<!-- task-register:payload:end -->
