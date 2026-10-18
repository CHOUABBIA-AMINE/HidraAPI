# HPS-017 — Analytics and Reporting: task definitions

## Status

CURRENT — owner-authorized task-register split, 2026-10-18. Source `2148b270a863cee5f0b42d458508cd9327567591`; all task definitions and evidence retained without edits. TARGET/DEFERRED and unknown acceptance stay explicit.

[Task index and execution rules](ULTIMATE_TASKS.md) · [Phase baseline](ULTIMATE_PHASES.md)

<!-- task-register:payload:start -->
# HPS-017 — Analytics and Reporting

<a id="hapi-148"></a>
## HAPI-148 — scan analytics domain models

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMC-003

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 7 analytics model files.

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
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

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
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

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 12. HMC-003 — Analytics module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `analytics`  
**Baseline model files:** 7  
**Scanned models:** 7  
**Declared fields/components:** 102  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Analytics model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| AnalyticsDataset | record | 16 | `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java` | Extracted |
| AnalyticsDatasetVersion | record | 13 | `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java` | Extracted |
| AnalyticsInsight | record | 15 | `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java` | Extracted |
| AnalyticsProjectionRun | record | 15 | `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java` | Extracted |
| DigitalTwinReadinessAssessment | record | 15 | `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java` | Extracted |
| MetricEvaluationRun | record | 15 | `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java` | Extracted |
| MetricValue | record | 13 | `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java` | Extracted |

### Analytics fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| AnalyticsDataset | 1 | id | String |
| AnalyticsDataset | 2 | code | String |
| AnalyticsDataset | 3 | nameAr | String |
| AnalyticsDataset | 4 | nameFr | String |
| AnalyticsDataset | 5 | nameEn | String |
| AnalyticsDataset | 6 | subjectAreaId | String |
| AnalyticsDataset | 7 | datasetType | AnalyticsDatasetType |
| AnalyticsDataset | 8 | refreshMode | AnalyticsRefreshMode |
| AnalyticsDataset | 9 | lineageStatus | AnalyticsLineageStatus |
| AnalyticsDataset | 10 | qualityStatus | AnalyticsQualityStatus |
| AnalyticsDataset | 11 | schemaVersion | String |
| AnalyticsDataset | 12 | createdFrom | String |
| AnalyticsDataset | 13 | validFrom | Instant |
| AnalyticsDataset | 14 | validTo | Instant |
| AnalyticsDataset | 15 | createdAt | Instant |
| AnalyticsDataset | 16 | updatedAt | Instant |
| AnalyticsDatasetVersion | 1 | id | String |
| AnalyticsDatasetVersion | 2 | datasetId | String |
| AnalyticsDatasetVersion | 3 | versionNumber | int |
| AnalyticsDatasetVersion | 4 | schemaHash | String |
| AnalyticsDatasetVersion | 5 | dataHash | String |
| AnalyticsDatasetVersion | 6 | rowCount | Long |
| AnalyticsDatasetVersion | 7 | periodStart | Instant |
| AnalyticsDatasetVersion | 8 | periodEnd | Instant |
| AnalyticsDatasetVersion | 9 | qualityScore | BigDecimal |
| AnalyticsDatasetVersion | 10 | published | boolean |
| AnalyticsDatasetVersion | 11 | publishedAt | Instant |
| AnalyticsDatasetVersion | 12 | publishedByActorId | String |
| AnalyticsDatasetVersion | 13 | createdAt | Instant |
| AnalyticsInsight | 1 | id | String |
| AnalyticsInsight | 2 | insightType | String |
| AnalyticsInsight | 3 | subjectAreaId | String |
| AnalyticsInsight | 4 | scopeType | String |
| AnalyticsInsight | 5 | scopeId | String |
| AnalyticsInsight | 6 | title | String |
| AnalyticsInsight | 7 | summary | String |
| AnalyticsInsight | 8 | severityId | String |
| AnalyticsInsight | 9 | confidenceScore | BigDecimal |
| AnalyticsInsight | 10 | sourceProjectionSnapshotId | String |
| AnalyticsInsight | 11 | sourceTrendAnalysisId | String |
| AnalyticsInsight | 12 | sourceModelRunId | String |
| AnalyticsInsight | 13 | status | AnalyticsInsightStatus |
| AnalyticsInsight | 14 | createdAt | Instant |
| AnalyticsInsight | 15 | updatedAt | Instant |
| AnalyticsProjectionRun | 1 | id | String |
| AnalyticsProjectionRun | 2 | projectionDefinitionId | String |
| AnalyticsProjectionRun | 3 | runStatus | AnalyticsRunStatus |
| AnalyticsProjectionRun | 4 | runMode | AnalyticsRunMode |
| AnalyticsProjectionRun | 5 | periodStart | Instant |
| AnalyticsProjectionRun | 6 | periodEnd | Instant |
| AnalyticsProjectionRun | 7 | startedAt | Instant |
| AnalyticsProjectionRun | 8 | completedAt | Instant |
| AnalyticsProjectionRun | 9 | sourceWatermark | String |
| AnalyticsProjectionRun | 10 | recordsRead | Long |
| AnalyticsProjectionRun | 11 | recordsWritten | Long |
| AnalyticsProjectionRun | 12 | errorCode | String |
| AnalyticsProjectionRun | 13 | errorMessage | String |
| AnalyticsProjectionRun | 14 | correlationId | String |
| AnalyticsProjectionRun | 15 | createdAt | Instant |
| DigitalTwinReadinessAssessment | 1 | id | String |
| DigitalTwinReadinessAssessment | 2 | scopeType | String |
| DigitalTwinReadinessAssessment | 3 | scopeId | String |
| DigitalTwinReadinessAssessment | 4 | topologySnapshotId | String |
| DigitalTwinReadinessAssessment | 5 | assessmentPeriodStart | Instant |
| DigitalTwinReadinessAssessment | 6 | assessmentPeriodEnd | Instant |
| DigitalTwinReadinessAssessment | 7 | telemetryCompletenessScore | BigDecimal |
| DigitalTwinReadinessAssessment | 8 | telemetryQualityScore | BigDecimal |
| DigitalTwinReadinessAssessment | 9 | topologyCompletenessScore | BigDecimal |
| DigitalTwinReadinessAssessment | 10 | modelAvailabilityScore | BigDecimal |
| DigitalTwinReadinessAssessment | 11 | lineageCompletenessScore | BigDecimal |
| DigitalTwinReadinessAssessment | 12 | overallReadinessScore | BigDecimal |
| DigitalTwinReadinessAssessment | 13 | readinessStatus | DigitalTwinReadinessStatus |
| DigitalTwinReadinessAssessment | 14 | assessedAt | Instant |
| DigitalTwinReadinessAssessment | 15 | createdAt | Instant |
| MetricEvaluationRun | 1 | id | String |
| MetricEvaluationRun | 2 | metricDefinitionVersionId | String |
| MetricEvaluationRun | 3 | runStatus | AnalyticsRunStatus |
| MetricEvaluationRun | 4 | periodStart | Instant |
| MetricEvaluationRun | 5 | periodEnd | Instant |
| MetricEvaluationRun | 6 | scopeType | String |
| MetricEvaluationRun | 7 | scopeId | String |
| MetricEvaluationRun | 8 | startedAt | Instant |
| MetricEvaluationRun | 9 | completedAt | Instant |
| MetricEvaluationRun | 10 | recordsRead | Long |
| MetricEvaluationRun | 11 | recordsProduced | Long |
| MetricEvaluationRun | 12 | errorCode | String |
| MetricEvaluationRun | 13 | errorMessage | String |
| MetricEvaluationRun | 14 | correlationId | String |
| MetricEvaluationRun | 15 | createdAt | Instant |
| MetricValue | 1 | id | String |
| MetricValue | 2 | metricEvaluationRunId | String |
| MetricValue | 3 | metricDefinitionId | String |
| MetricValue | 4 | metricDefinitionVersionId | String |
| MetricValue | 5 | scopeType | String |
| MetricValue | 6 | scopeId | String |
| MetricValue | 7 | periodStart | Instant |
| MetricValue | 8 | periodEnd | Instant |
| MetricValue | 9 | valueNumeric | BigDecimal |
| MetricValue | 10 | valueText | String |
| MetricValue | 11 | unitId | String |
| MetricValue | 12 | qualityStatus | AnalyticsQualityStatus |
| MetricValue | 13 | calculatedAt | Instant |

### HMC-003 reconciliation

```text
baseline model files = 7
extracted models     = 7
exceptions           = 0
declared fields      = 102
zero-field models    = 0
```

The scan records only declarations under `analytics/domain/model`. Analytics domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

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

- `a15050377c65360de17f587611dde5b5a799da93` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:131](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L131)
- [docs/roadmap/model-field-catalogue.md:379](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L379)
- [docs/roadmap/model-field-catalogue.md:508](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L508)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:131 | HMC-003 | docs(catalogue): scan analytics domain models | Scan all 7 analytics model files. | **Completed** — 7/7 models, 102 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-165"></a>
## HAPI-165 — scan reporting domain models

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMC-020

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Scan all 4 reporting model files.

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportArtifactType.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportFormat.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-field-catalogue.md`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportArtifactType.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportFormat.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 29. HMC-020 — Reporting module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `reporting`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 59  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Reporting model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| ReportDefinition | record | 14 | `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java` | Extracted |
| ReportOutputArtifact | record | 13 | `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java` | Extracted |
| ReportRequest | record | 15 | `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java` | Extracted |
| ReportRun | record | 17 | `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java` | Extracted |

### Reporting fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| ReportDefinition | 1 | id | String |
| ReportDefinition | 2 | code | String |
| ReportDefinition | 3 | nameAr | String |
| ReportDefinition | 4 | nameFr | String |
| ReportDefinition | 5 | nameEn | String |
| ReportDefinition | 6 | reportCategoryId | String |
| ReportDefinition | 7 | ownerModule | String |
| ReportDefinition | 8 | description | String |
| ReportDefinition | 9 | active | boolean |
| ReportDefinition | 10 | currentTemplateVersionId | String |
| ReportDefinition | 11 | requiresApproval | boolean |
| ReportDefinition | 12 | restricted | boolean |
| ReportDefinition | 13 | createdAt | Instant |
| ReportDefinition | 14 | updatedAt | Instant |
| ReportOutputArtifact | 1 | id | String |
| ReportOutputArtifact | 2 | reportRunId | String |
| ReportOutputArtifact | 3 | artifactType | ReportArtifactType |
| ReportOutputArtifact | 4 | format | ReportFormat |
| ReportOutputArtifact | 5 | fileName | String |
| ReportOutputArtifact | 6 | mimeType | String |
| ReportOutputArtifact | 7 | storageObjectReferenceId | String |
| ReportOutputArtifact | 8 | documentReferenceId | String |
| ReportOutputArtifact | 9 | checksum | String |
| ReportOutputArtifact | 10 | sizeBytes | Long |
| ReportOutputArtifact | 11 | generatedAt | Instant |
| ReportOutputArtifact | 12 | expiresAt | Instant |
| ReportOutputArtifact | 13 | createdAt | Instant |
| ReportRequest | 1 | id | String |
| ReportRequest | 2 | reportDefinitionId | String |
| ReportRequest | 3 | requestedByActorId | String |
| ReportRequest | 4 | requestedByUsernameSnapshot | String |
| ReportRequest | 5 | requestedByDisplayNameSnapshot | String |
| ReportRequest | 6 | requestedByRoleCodeSnapshot | String |
| ReportRequest | 7 | organizationUnitId | String |
| ReportRequest | 8 | organizationUnitNameSnapshot | String |
| ReportRequest | 9 | requestedAt | Instant |
| ReportRequest | 10 | purpose | String |
| ReportRequest | 11 | status | ReportRequestStatus |
| ReportRequest | 12 | correlationId | String |
| ReportRequest | 13 | workflowReferenceId | String |
| ReportRequest | 14 | createdAt | Instant |
| ReportRequest | 15 | updatedAt | Instant |
| ReportRun | 1 | id | String |
| ReportRun | 2 | reportRequestId | String |
| ReportRun | 3 | reportDefinitionId | String |
| ReportRun | 4 | templateVersionId | String |
| ReportRun | 5 | status | ReportRunStatus |
| ReportRun | 6 | runMode | ReportRunMode |
| ReportRun | 7 | queuedAt | Instant |
| ReportRun | 8 | startedAt | Instant |
| ReportRun | 9 | completedAt | Instant |
| ReportRun | 10 | failedAt | Instant |
| ReportRun | 11 | failureReason | String |
| ReportRun | 12 | recordCount | Long |
| ReportRun | 13 | outputCount | Long |
| ReportRun | 14 | executionDurationMs | Long |
| ReportRun | 15 | correlationId | String |
| ReportRun | 16 | createdAt | Instant |
| ReportRun | 17 | updatedAt | Instant |

### HMC-020 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 59
zero-field models    = 0
```

The scan records only declarations under `reporting/domain/model`. Reporting domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure model, document implementation, workflow implementation, or database column metadata was mixed into this module scan.

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

- `d1ac5a1d8c7ecb8c1afc519a921c95cbf827b6bf` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-field-catalogue.md:148](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L148)
- [docs/roadmap/model-field-catalogue.md:2362](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2362)
- [docs/roadmap/model-field-catalogue.md:2445](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-field-catalogue.md#L2445)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-field-catalogue.md:148 | HMC-020 | docs(catalogue): scan reporting domain models | Scan all 4 reporting model files. | **Completed** — 4/4 models, 59 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`.
~~~~~~~~

<a id="hapi-190"></a>
## HAPI-190 — remediate semantic review ReportDefinition

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-013

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review ReportDefinition

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/CreateReportDefinitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportDefinitionResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/CreateReportDefinitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportDefinitionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`
- `src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportDefinitionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportDefinitionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/CreateReportDefinitionRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportDefinitionResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/CreateReportDefinitionCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportDefinitionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportDefinitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`
- `src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportDefinitionSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-013 — reporting.ReportDefinition

- Source review: `HMSR-013`
- Exact commit: `fix(reporting): remediate semantic review ReportDefinition`
- Status: **Completed** — `ReportDefinitionStatus` is now the sole persisted lifecycle representation; legacy `active()` is a derived compatibility projection and new definitions created through the existing service enter DRAFT. Code uniqueness, REPORT_CATEGORY family/activity, known owner-module values, French name/timestamps, current-template-version ownership/lifecycle, ACTIVE-only requests, restricted-policy scope gates, and approval-required run queueing are fail-closed. HRA-080 remains consistent because ReportDefinitionStatus is now independently persisted.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 11
- Additive Flyway: `src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Reporting.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/CreateReportDefinitionRequest.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportDefinitionResponse.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/command/CreateReportDefinitionCommand.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportDefinitionSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportDefinitionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql`
  - `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportDefinitionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ReportDefinitionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Definition lifecycle representation:** reconcile the DDD/HRA-retained `ReportDefinitionStatus {DRAFT, ACTIVE, RETIRED}` with the live boolean `active` model/schema. The creation path must not silently skip DRAFT unless the target lifecycle is explicitly changed.
  2. **Unique definition code:** enforce global uniqueness of `ReportDefinition.code` at the application/database boundary.
  3. **ACTIVE-only request use:** `requestReport()` must verify that the selected definition is eligible for new requests under the chosen lifecycle representation.
  4. **Restricted-report access policy:** restricted definitions must be validated through explicit Reporting authorization/access-policy rules before request/access/generation paths proceed.
  5. **Approval governance:** definitions with `requiresApproval = true` must not be queued before the relevant request has Workflow-backed approval.
  6. **Template-version integrity:** `currentTemplateVersionId` and queued `templateVersionId` must resolve to valid Reporting template versions whose parent templates belong to the selected definition and satisfy the applicable lifecycle rule.
  7. **Category-family validation:** `reportCategoryId` must resolve specifically to an active/usable `REPORT_CATEGORY` entry, not merely to any Reporting catalog row.
  8. **Known owner module:** `ownerModule` must be validated against the authoritative Hidra module catalog/contract.
  9. **Required French name:** reconcile DDD/JPA/schema-required `nameFr` with the domain constructor's nullable behavior at a deliberate validation boundary.
  10. **Required timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`.
  11. **HRA-080 documentation consistency:** final reconciliation must correct the architecture statement that ReportDefinition status values are persisted independently if the final model does not actually persist `ReportDefinitionStatus`.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportDefinitionSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `9899a1167c7a015dd513196945fde455dc6839f5` — exact registered-message commit; surviving paths matched to current tree.
- `955fd24de6a9dcae93aac14b2e38e84b06473b97` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13695](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13695)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15435](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15435)
- [docs/roadmap/model-semantic-remediation.md:159](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L159)
- [docs/roadmap/model-semantic-remediation.md:966](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L966)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:159 | HMR-013 | HMSR-013 | reporting | ReportDefinition | — | — | fix(reporting): remediate semantic review ReportDefinition | **Completed**
doc/model-remediation/RECONCILIATION.md:13695 | HMR-013 | Historical reference; unchanged | INDEPENDENT_UNCHANGED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15435 | HMR-013 | Historical reference; unchanged | INDEPENDENT_UNCHANGED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-194"></a>
## HAPI-194 — remediate semantic review MetricEvaluationRun

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-016

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review MetricEvaluationRun

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/MetricEvaluationRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/command/FinalizeMetricEvaluationRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/MetricEvaluationRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationScopeResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricEvaluationRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricEvaluationRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/reference/AuthoritativeMetricEvaluationScopeResolverAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/analytics/AnalyticsOrganizationScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapter.java`
- `src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricEvaluationRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapterTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationRunRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationScopeResolverPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricEvaluationRunRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricEvaluationRunJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/MetricEvaluationRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/command/FinalizeMetricEvaluationRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/MetricEvaluationRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationScopeResolverPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricEvaluationRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricEvaluationRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/reference/AuthoritativeMetricEvaluationScopeResolverAdapter.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/analytics/AnalyticsOrganizationScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapter.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapter.java`
- `src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricEvaluationRunSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapterTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-016 — analytics.MetricEvaluationRun

- Source review: `HMSR-018`
- Exact commit: `fix(analytics): remediate semantic review MetricEvaluationRun`
- Status: **Completed** — nonblank scope type is a domain invariant; current Analytics starts validate owner-backed Topology/Organization scopes through dedicated public contracts and fail closed for unsupported types; all current supported scopes require IDs. Metric-definition versions must exist and contain the requested evaluation period. The use case exposes governed terminal finalization for COMPLETED, COMPLETED_WITH_WARNINGS, FAILED and CANCELLED, preserves counters/diagnostics/correlation, requires completion timestamps, and prevents already-terminal outcomes from being changed or reopened. FAILED runs retain diagnostic or correlation evidence. No cross-module scope FK or unsupported scope owner was invented.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`
- Owner-contract prerequisite: **HMR-016A completed.** Use dedicated Analytics-facing owner contracts for Topology and Organization behind an Analytics-owned resolver. Do not import foreign repositories/domain internals and do not create cross-module database FKs.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/MetricEvaluationRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/dto/MetricEvaluationRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/command/FinalizeMetricEvaluationRunCommand.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationScopeResolverPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricEvaluationRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricEvaluationRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/reference/AuthoritativeMetricEvaluationScopeResolverAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/analytics/AnalyticsOrganizationScopeContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/topology/application/contract/analytics/AnalyticsTopologyScopeContract.java`
  - `src/main/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapter.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricEvaluationRunJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_016__hmr_016_analytics_metric_evaluation_run.sql`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricEvaluationRunSemanticRemediationTest.java`
  - `src/test/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapterTest.java`
  - `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapterTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MetricEvaluationRunSemanticRemediationTest,JpaAnalyticsOrganizationScopeContractAdapterTest,JpaAnalyticsTopologyScopeContractAdapterTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Required scope discriminator:** enforce nonblank `scopeType` at a deliberate domain/application boundary so invalid runs do not survive until database persistence.
  2. **Typed scope validation:** validate the `scopeType + scopeId` pair through a neutral Analytics/owning-module lookup contract while preserving foreign-module ownership and allowing explicitly identifier-less scope types where defined.
  3. **Metric-version eligibility:** before starting calculation, resolve `metricDefinitionVersionId` and enforce whatever version-validity/active-calculation rule is authoritative for the requested period; FK existence alone is insufficient.
  4. **Lifecycle completion/failure orchestration:** provide a governed path for terminal run outcomes so `COMPLETED`, `COMPLETED_WITH_WARNINGS`, `FAILED`, and `CANCELLED` can preserve `completedAt`, diagnostics, counters and correlation/audit evidence.
  5. **Failed-run auditability:** ensure failure execution records retain enough diagnostic/correlation evidence to satisfy the DDD rule without inventing unsupported constructor-level text requirements.

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
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricEvaluationRunSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/reference/JpaAnalyticsOrganizationScopeContractAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/reference/JpaAnalyticsTopologyScopeContractAdapterTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `6b0d4b6487940b1bcbfe8fb71420b370de43c7af` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:162](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L162)
- [docs/roadmap/model-semantic-remediation.md:1128](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1128)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:162 | HMR-016 | HMSR-018 | analytics | MetricEvaluationRun | — | — | fix(analytics): remediate semantic review MetricEvaluationRun | **Completed**
~~~~~~~~

<a id="hapi-203"></a>
## HAPI-203 — remediate semantic review AnalyticsInsight

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-023

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AnalyticsInsight

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/request/CreateAnalyticsInsightRequest.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsInsightResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/command/CreateAnalyticsInsightCommand.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsInsightSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsInsightUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsInsightRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsInsightRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightEvidenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightEvidenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightJpaRepository.java`
- `src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsInsightSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsInsightUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsInsightRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsInsightRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightEvidenceJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/request/CreateAnalyticsInsightRequest.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsInsightResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/command/CreateAnalyticsInsightCommand.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsInsightSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsInsightUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsInsightRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsInsightRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightEvidenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightEvidenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightJpaRepository.java`
- `src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsInsightSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-023 — analytics.AnalyticsInsight

- Source review: `HMSR-025`
- Exact commit: `fix(analytics): remediate semantic review AnalyticsInsight`
- Status: **Completed** — required classification/scope semantics enforced; INSIGHT_TYPE and ANALYTICS_SEVERITY catalog references fail closed; populated projection-snapshot, trend-analysis and model-run lineage is protected in application persistence and by additive same-module database constraints.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/api/rest/request/CreateAnalyticsInsightRequest.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsInsightResponse.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/command/CreateAnalyticsInsightCommand.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsInsightSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsInsightUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsInsightRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsInsightRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightEvidenceJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightEvidenceJpaRepository.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsInsightJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_023__hmr_023_analytics_analytics_insight.sql`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsInsightSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AnalyticsInsightSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require a nonblank, governed insight classification at the domain/application boundary.** `insightType` must not normalize to null and must resolve according to the Analytics-owned `INSIGHT_TYPE` catalog contract rather than accepting arbitrary free text.
  2. **Require nonblank `scopeType` consistently with the analytical-scope contract and final persistence schema.** HMSR-025 does not prescribe which scope types require `scopeId`; that mapping needs its own owner-resolved scope policy.
  3. **Protect optional severity semantics.** When `severityId` is supplied, it must resolve to the appropriate Analytics severity catalog entry/family rather than allowing a dangling or unrelated catalog ID.
  4. **Protect populated direct source lineage.** Each non-null `sourceProjectionSnapshotId`, `sourceTrendAnalysisId`, and `sourceModelRunId` must resolve to the corresponding Analytics-owned source record. The correction may use application validation, additive same-module FKs where appropriate, or another fail-closed Analytics-owned boundary in a separately authorized implementation task.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsInsightSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `8778c713ef964e2c74ae4e53794b9f74cd6fd3b6` — exact registered-message commit; surviving paths matched to current tree.
- `29155b3019f56fb7830bd9868fb13af9edac42c3` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/data definition/Analytics.md:1771](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/data definition/Analytics.md#L1771)
- [docs/roadmap/model-semantic-remediation.md:169](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L169)
- [docs/roadmap/model-semantic-remediation.md:1441](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1441)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:169 | HMR-023 | HMSR-025 | analytics | AnalyticsInsight | — | — | fix(analytics): remediate semantic review AnalyticsInsight | Planned
~~~~~~~~

<a id="hapi-204"></a>
## HAPI-204 — remediate semantic review AnalyticsProjectionRun

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-024

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AnalyticsProjectionRun

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsProjectionRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsProjectionRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/mapper/AnalyticsApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsProjectionRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsProjectionRunJpaRepository.java`
- `src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsProjectionRunSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsProjectionRunRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsProjectionRunJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsProjectionRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsProjectionRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/mapper/AnalyticsApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsProjectionRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsProjectionRunJpaRepository.java`
- `src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsProjectionRunSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-024 — analytics.AnalyticsProjectionRun

- Source review: `HMSR-026`
- Exact commit: `fix(analytics): remediate semantic review AnalyticsProjectionRun`
- Status: **Completed** — FAILED runs retain diagnostics, successful runs retain source watermarks, and every persisted run captures an immutable projection-definition computation version.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql` — authorized by HMR-024A.
- Owner-contract prerequisite: No cross-module owner contract is required; HMR-024A is the local reproducibility/persistence prerequisite.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/api/rest/response/AnalyticsProjectionRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/dto/AnalyticsProjectionRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/mapper/AnalyticsApplicationMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsProjectionRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsProjectionRunJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_024__hmr_024_analytics_projection_run.sql`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsProjectionRunSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AnalyticsProjectionRunSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Preserve terminal failure diagnostics.** A run persisted as `FAILED` must retain meaningful error context, as required by Analytics DDD. The implementation task must define the accepted error-code/message contract rather than silently allowing both to be absent.
  2. **Preserve successful-run source lineage.** Statuses classified by Analytics as successful must require a nonblank `sourceWatermark`, consistent with the DDD. The implementation task must explicitly define the successful-status set rather than relying on implicit interpretation.
  3. **Make projection runs reproducible across definition changes.** AnalyticsProjectionRun must durably identify the immutable computation/definition version that produced the run, or an equivalent immutable execution snapshot/audit version, because Analytics DDD requires definition versioning when formula changes and rebuildability from source truth/history.


~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsProjectionRunSemanticRemediationTest.java` — exact-head oracle suite 5 cases, zero failures/errors/skips.

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

- `3a1ff68096805494e5b74f7eead136ee64e8ee53` — exact registered-message commit; surviving paths matched to current tree.
- `0e80cbb0c68d4bb0a67af55e3867d250177230e3` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:170](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L170)
- [docs/roadmap/model-semantic-remediation.md:1481](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1481)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:170 | HMR-024 | HMSR-026 | analytics | AnalyticsProjectionRun | — | — | fix(analytics): remediate semantic review AnalyticsProjectionRun | Planned
~~~~~~~~

<a id="hapi-206"></a>
## HAPI-206 — remediate semantic review DigitalTwinReadinessAssessment

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-025

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review DigitalTwinReadinessAssessment

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DigitalTwinReadinessAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaDigitalTwinReadinessAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/DigitalTwinReadinessAssessmentJpaRepository.java`
- `src/main/resources/db/migration/V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/DigitalTwinReadinessAssessmentSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DigitalTwinReadinessAssessmentRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaDigitalTwinReadinessAssessmentRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/DigitalTwinReadinessAssessmentJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DigitalTwinReadinessAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaDigitalTwinReadinessAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/DigitalTwinReadinessAssessmentJpaRepository.java`
- `src/main/resources/db/migration/V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/DigitalTwinReadinessAssessmentSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-025 — analytics.DigitalTwinReadinessAssessment

- Source review: `HMSR-027`
- Exact commit: `fix(analytics): remediate semantic review DigitalTwinReadinessAssessment`
- Status: **Completed** — scopeType is required/nonblank and DigitalTwinReadinessStatus is the single authoritative bounded readiness vocabulary across domain/JPA/schema; the conflicting READINESS_STATUS catalog claim is retired.
- SCC: —
- Recorded upstream HMS dependencies: —
- HMSR correction count: 2
- Additive Flyway: `src/main/resources/db/migration/V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DigitalTwinReadinessAssessmentRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaDigitalTwinReadinessAssessmentRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/DigitalTwinReadinessAssessmentJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_025__hmr_025_analytics_digital_twin_readiness_assessment.sql`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/DigitalTwinReadinessAssessmentSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=DigitalTwinReadinessAssessmentSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require a nonblank analytical scope discriminator.** `scopeType` must be consistently mandatory at the domain/application boundary, matching the Analytics scope contract and final JPA/schema requiredness. HMSR-027 does not prescribe which scope types require `scopeId`.
  2. **Reconcile readiness-status representation.** Resolve the contradiction between DDD `READINESS_STATUS` catalog vocabulary and the current fixed `DigitalTwinReadinessStatus` enum/JPA representation. The implementation task must establish one authoritative controlled-vocabulary strategy across domain, persistence and future API/application contracts rather than leaving two competing semantics.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/analytics/semantic/DigitalTwinReadinessAssessmentSemanticRemediationTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.

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

- `d65bf0995d14d20967e086bb033d78a0364431e9` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:171](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L171)
- [docs/roadmap/model-semantic-remediation.md:1541](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L1541)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:171 | HMR-025 | HMSR-027 | analytics | DigitalTwinReadinessAssessment | — | — | fix(analytics): remediate semantic review DigitalTwinReadinessAssessment | Planned
~~~~~~~~

<a id="hapi-223"></a>
## HAPI-223 — remediate semantic review AnalyticsDatasetVersion

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-037

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review AnalyticsDatasetVersion

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsDatasetVersionJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsDatasetVersionJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsDatasetVersionJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-037 — analytics.AnalyticsDatasetVersion

- Source review: `HMSR-042`
- Exact commit: `fix(analytics): remediate semantic review AnalyticsDatasetVersion`
- Status: **Completed** — once an AnalyticsDatasetVersion is persisted as published, changed subsequent saves fail closed at the repository adapter boundary while identical re-saves are no-ops and the initial publish transition remains allowed.
- SCC: —
- Recorded upstream HMS dependencies: analytics.AnalyticsDataset
- HMSR correction count: 1
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/AnalyticsDatasetVersionJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=AnalyticsDatasetVersionSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Enforce immutability of published AnalyticsDatasetVersion state.** Once a dataset version is published, subsequent writes must not mutate its persisted release content/metadata contrary to the Analytics DDD. The production correction must choose an architecture-consistent fail-closed mechanism without rewriting this HMSR documentation task into implementation work.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/analytics/semantic/AnalyticsDatasetVersionSemanticRemediationTest.java` — exact-head oracle suite 3 cases, zero failures/errors/skips.

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

- `07c85795f996a16d74ae819e676bda3fac022b45` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:188](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L188)
- [docs/roadmap/model-semantic-remediation.md:2153](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2153)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:188 | HMR-037 | HMSR-042 | analytics | AnalyticsDatasetVersion | — | analytics.AnalyticsDataset | fix(analytics): remediate semantic review AnalyticsDatasetVersion | Planned
~~~~~~~~

<a id="hapi-224"></a>
## HAPI-224 — remediate semantic review MetricValue

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-038

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review MetricValue

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricValueJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricValueSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricValueRepositoryPort.java`.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricValueRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricValueJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Analytics.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricValueJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricValueSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-038 — analytics.MetricValue

- Source review: `HMSR-043`
- Exact commit: `fix(analytics): remediate semantic review MetricValue`
- Status: **Completed** — canonical MetricValue construction now rejects null/blank scopeType before persistence, preserving the analytical scope namespace required to interpret scopeId.
- SCC: —
- Recorded upstream HMS dependencies: analytics.MetricEvaluationRun
- HMSR correction count: 1
- Additive Flyway: not pre-authorized by HMR-002
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Analytics.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/MetricValueRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaMetricValueRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/mapper/AnalyticsPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/repository/MetricValueJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricValueSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=MetricValueSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Require nonblank MetricValue scopeType at the authoritative domain/application boundary.** The canonical model must not normalize `scopeType` to null when final persistence requires it and the Analytics scope-reference pattern depends on it to interpret `scopeId`.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/analytics/semantic/MetricValueSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `7dd2333c9d00e4f8a456ef47cf7dd8f3157a0245` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:189](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L189)
- [docs/roadmap/model-semantic-remediation.md:2181](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2181)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:189 | HMR-038 | HMSR-043 | analytics | MetricValue | — | analytics.MetricEvaluationRun | fix(analytics): remediate semantic review MetricValue | Planned
~~~~~~~~

<a id="hapi-239"></a>
## HAPI-239 — remediate semantic review ReportRequest

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-048

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review ReportRequest

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/RequestReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`
- `src/main/resources/db/migration/V20261004_048__hmr_048_reporting_report_request.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRequestRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`, `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRequestRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRequestJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/RequestReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`
- `src/main/resources/db/migration/V20261004_048__hmr_048_reporting_report_request.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-048 — reporting.ReportRequest

- Source review: `HMSR-057`
- Exact commit: `fix(reporting): remediate semantic review ReportRequest`
- Status: **Completed** — request creation now requires an ACTIVE ReportDefinition, restricted requests require matching Reporting policy plus Identity authorization, populated OrganizationUnit references resolve through Organization, and approval-required queueing requires ReportRequest APPROVED state plus Workflow-owned approval evidence.
- SCC: —
- Recorded upstream HMS dependencies: organization.OrganizationUnit, reporting.ReportDefinition
- HMSR correction count: 4
- Additive Flyway: `src/main/resources/db/migration/V20261004_048__hmr_048_reporting_report_request.sql`
- Owner-contract prerequisite: HMR-049A registered — live preflight proved the Risk create service, Risk-facing Organization/Topology scope contracts, and Risk-specific Audit contract were missing from the original execution scope.
- Exact write allowlist:
  - `docs/data definition/Reporting.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/RequestReportRequest.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRequestResponse.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRequestSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRequestRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRequestRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRequestJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_048__hmr_048_reporting_report_request.sql`
  - `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRequestSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ReportRequestSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Reject new requests against inactive ReportDefinition rows.** The authoritative request path must load/validate definition eligibility before creating ReportRequest.
  2. **Enforce access validation for restricted report definitions before request creation.** Use Reporting/Identity access-policy contracts rather than bypassing authorization.
  3. **Prevent queueing approval-required requests before workflow approval.** The queue path must validate the ReportRequest/ReportDefinition approval state through the appropriate Workflow contract before creating a queued ReportRun.
  4. **Validate populated `organizationUnitId` through the Organization owner boundary.** Do not add a cross-module database FK.

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
- `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRequestSemanticRemediationTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `27cf232f82b0a15bdf8c51657a090292e9585c25` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:204](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L204)
- [docs/roadmap/model-semantic-remediation.md:2723](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2723)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:204 | HMR-048 | HMSR-057 | reporting | ReportRequest | — | organization.OrganizationUnit, reporting.ReportDefinition | fix(reporting): remediate semantic review ReportRequest | Planned
~~~~~~~~

<a id="hapi-240"></a>
## HAPI-240 — register ReportRequest owner prerequisites

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-048A

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

register ReportRequest owner prerequisites

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`, `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`, `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/port/out/OrganizationUnitRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/planning/PlanningWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-048A — ReportRequest access/approval/Organization prerequisite

- Source: HMR-048 / HMSR-057 plus live Reporting/Identity/Workflow/Organization boundary evidence.
- Exact commit: `docs(model-remediation): register ReportRequest owner prerequisites`
- Status: **Completed** — the missing Reporting application orchestration file, consumer-specific Identity/Workflow/Organization contracts, owner-side implementations/tests, and architecture guardrail registrations are authorized; HMR-048A itself changes documentation only.
- Type: documentation/application-boundary prerequisite.
- Live evidence:
  1. HMR-048 requires inactive ReportDefinition rejection, restricted-report access validation, approval gating before queueing, and OrganizationUnit owner validation.
  2. The authoritative request and queue paths are `ReportingApplicationService.requestReport(...)` and `queueReportRun(...)`, but `ReportingApplicationService.java` is outside the original HMR-048 allowlist.
  3. No `identity.application.contract.reporting`, `workflow.application.contract.reporting`, or `organization.application.contract.reporting` package exists on live `main`.
  4. Reporting already owns `ReportAccessPolicy` persistence metadata, but the DDD states Identity evaluates permissions. Reporting must not replace Identity authorization with ad hoc local policy-only checks.
  5. Workflow already demonstrates the deliberate consumer-specific export pattern through `workflow.application.contract.planning.PlanningWorkflowContract`; Reporting requires its own narrow approval contract rather than importing Workflow private application packages.
  6. Organization can resolve OrganizationUnit existence via `OrganizationUnitRepositoryPort.findById(...)`; HMR-048 requires existence only and does not authorize inventing lifecycle/eligibility semantics.
  7. ReportDefinition and ReportRequest are Reporting-owned and may be loaded through existing Reporting repositories; no cross-module DB FK is required for Identity, Workflow, or Organization.
- Required decisions:
  1. Add `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java` to the HMR-048 write allowlist.
  2. Identity must export a narrow Reporting authorization contract that evaluates whether the requesting actor is allowed to request the specific restricted report under the Reporting access-policy context. The contract must return neutral allow/deny evidence only and must not leak Identity domain/JPA types.
  3. Reporting remains owner of ReportAccessPolicy metadata. The Reporting request path may assemble neutral policy scope inputs, but the authorization decision must come from Identity for restricted definitions.
  4. Workflow must export a narrow Reporting approval contract that can answer whether a populated workflow reference represents an approved workflow state suitable for queueing this report request. Do not import Workflow internal repositories/domain types into Reporting.
  5. Organization must export a narrow Reporting-facing OrganizationUnit existence contract implemented via `OrganizationUnitRepositoryPort.findById(...)`.
  6. `requestReport(...)` must load the selected ReportDefinition and fail closed unless it is ACTIVE.
  7. For restricted definitions, `requestReport(...)` must fail closed unless the Identity-owned Reporting access contract allows the request.
  8. Populated `organizationUnitId` must resolve through the Organization-owned Reporting contract before the ReportRequest is persisted.
  9. `queueReportRun(...)` must load both ReportRequest and ReportDefinition. If the definition requires approval, the request must be APPROVED, `workflowReferenceId` must be nonblank, and the Workflow-owned Reporting approval contract must confirm approval before a QUEUED ReportRun is created.
  10. No cross-module Identity/Workflow/Organization database FK is authorized.
  11. Register exactly these exported packages in both existing architecture guardrail registries:
      - `dz.sh.hidra.modules.identity.application.contract.reporting`
      - `dz.sh.hidra.modules.workflow.application.contract.reporting`
      - `dz.sh.hidra.modules.organization.application.contract.reporting`
      Do not weaken guardrail algorithms or add wildcard/transitional exceptions.
- Newly authorized HMR-048 production files in addition to the original allowlist:
  - `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryService.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/package-info.java`
  - `src/main/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryService.java`
- Newly authorized HMR-048 test/guardrail files:
  - `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java`
  - `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
  - `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- HMR-048 remains the current production remediation after this prerequisite is observed. Do not start HMR-049 automatically.


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
- `src/test/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/organization/application/service/ReportingOrganizationUnitReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/ReportingWorkflowApprovalQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.

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

- `7908db4b4ad97732e0ae6f7490dd00b7f3f576ab` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/model-semantic-remediation.md:2759](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L2759)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

<a id="hapi-251"></a>
## HAPI-251 — remediate semantic review ReportRun

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-057

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review ReportRun

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/QueueReportRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/QueueReportRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportQueueEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportQueueEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
- `src/main/resources/db/migration/V20261007_012__hmr_057_reporting_report_run.sql`
- `src/test/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/ReportingSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportQueueEvidenceSemanticTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportQueueEvidencePort.java`, `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportQueueEvidenceAdapter.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/QueueReportRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/QueueReportRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportQueueEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportQueueEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRunJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java`
- `src/main/resources/db/migration/V20261007_012__hmr_057_reporting_report_run.sql`
- `src/test/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/ReportingSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportQueueEvidenceSemanticTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-057 — reporting.ReportRun

- Source review: `HMSR-068`
- Exact commit: `fix(reporting): remediate semantic review ReportRun`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: reporting.ReportDefinition, reporting.ReportRequest
- HMSR correction count: 5
- Additive Flyway: `src/main/resources/db/migration/V20261007_012__hmr_057_reporting_report_run.sql` (accepted Batch 12)
- Owner-contract prerequisite: No cross-module owner-contract prerequisite recorded by this HMSR correction.
- Exact write allowlist:
  - `docs/data definition/Reporting.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/QueueReportRunRequest.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRunResponse.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/command/QueueReportRunCommand.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRunSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRunJpaRepository.java`
  - `src/main/resources/db/migration/V20261004_057__hmr_057_reporting_report_run.sql`
  - `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ReportRunSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Correct the erroneous HRA-111 foreign key for `reportRequestId`.** `hidra_reporting_run.report_request_id` must reference `hidra_reporting_request.id`, not `hidra_reporting_catalog_entry.id`.
  2. **Enforce request/definition consistency before queueing.** The selected ReportRequest must belong to the same ReportDefinition recorded by the run.
  3. **Enforce template-version lineage before queueing.** The exact ReportTemplateVersion must resolve through its ReportTemplate to the same ReportDefinition as the request/run.
  4. **Queue only an eligible request with all required parameters present.** Reuse the previously recorded HMSR-057 request approval/access semantics; do not bypass them from the run-queue path.
  5. **Enforce the explicit terminal-state invariants.** COMPLETED requires `completedAt`; FAILED requires `failureReason` at the authoritative domain/transition boundary.

- Batch 12 implementation: Queue eligibility/access/approval, exact template lineage, concrete required parameters and terminal evidence enforced. Forward 012 corrects run/parameter request FKs and guards lineage/history. Eight Run, four QueueEvidence and nine PostgreSQL cases prepared; local runtime validation follows; CI pending.

~~~~~~~~

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/ReportingSemanticPostgresIntegrationTest.java` — exact-head oracle suite 14 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportQueueEvidenceSemanticTest.java` — exact-head oracle suite 4 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java` — exact-head oracle suite 8 cases, zero failures/errors/skips.

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

- `c0122746c02a1de9f95329f786f563e6de4195e5` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:92](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L92)
- [doc/model-remediation/RECONCILIATION.md:13704](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13704)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15444](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15444)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:91](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L91)
- [docs/roadmap/model-semantic-remediation.md:218](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L218)
- [docs/roadmap/model-semantic-remediation.md:3200](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L3200)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:218 | HMR-057 | HMSR-068 | reporting | ReportRun | — | reporting.ReportDefinition, reporting.ReportRequest | fix(reporting): remediate semantic review ReportRun | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:92 | HMR-057 | HMSR-068 | reporting.ReportRun | COMPLETED — CI #588 GREEN | Queue eligibility/access/approval, exact template lineage, concrete required parameters and terminal evidence enforced. Forward 012 corrects run/parameter request FKs and guards lineage/history. Eight Run, four QueueEvidence and nine PostgreSQL cases prepared; local runtime validation follows; Full production CI #588 passed.
doc/model-remediation/RECONCILIATION.md:13704 | HMR-057 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:91 | HMR-057 | HMSR-068 | [reporting.ReportRun](../domain/SEMANTIC_DECISIONS.md#reporting-reportrun) | [ReportRunSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java) | [V20261007_012__hmr_057_reporting_report_run](../../src/main/resources/db/migration/V20261007_012__hmr_057_reporting_report_run.sql); [ReportingDocumentReferenceContract](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java); [ReportingAccessAuthorizationContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java); [ReportingOrganizationUnitReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/reporting/ReportingOrganizationUnitReferenceContract.java); [ReportingWorkflowApprovalContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/reporting/ReportingWorkflowApprovalContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15444 | HMR-057 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-287"></a>
## HAPI-287 — remediate semantic review ReportOutputArtifact

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMR-093

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

remediate semantic review ReportOutputArtifact

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportOutputArtifactResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportOutputArtifactSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportOutputArtifactRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportOutputArtifactJpaRepository.java`
- `src/main/resources/db/migration/V20261007_013__hmr_093_reporting_report_output_artifact.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/ReportingSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Existing gate retained; no reopening/selection; future scope needs owner authorization

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java`, `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryService.java`, `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`, `src/test/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryServiceTest.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`, `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportOutputArtifactJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportOutputArtifactResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportOutputArtifactSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportOutputArtifactRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportOutputArtifactJpaRepository.java`
- `src/main/resources/db/migration/V20261007_013__hmr_093_reporting_report_output_artifact.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/ReportingSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
#### HMR-093 — reporting.ReportOutputArtifact

- Source review: `HMSR-110`
- Exact commit: `fix(reporting): remediate semantic review ReportOutputArtifact`
- Status: **Implemented — CI pending**
- SCC: —
- Recorded upstream HMS dependencies: documents.Document, documents.DocumentStorageObject, reporting.ReportRun
- HMSR correction count: 3
- Additive Flyway: `src/main/resources/db/migration/V20261007_013__hmr_093_reporting_report_output_artifact.sql` (accepted Batch 12)
- Owner-contract prerequisite: Existing candidate owner contract(s): documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java; documents:src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java
- Exact write allowlist:
  - `docs/data definition/Reporting.md`
  - `docs/roadmap/model-semantic-remediation.md`
  - `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportOutputArtifactResponse.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportOutputArtifactSummaryDto.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportOutputArtifactRepositoryPort.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
  - `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportOutputArtifactJpaRepository.java`
  - `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java`
- Exact validation:
  - `./mvnw -q -DskipTests compile`
  - `./mvnw -q -Dtest=ReportOutputArtifactSemanticRemediationTest test`
  - `./mvnw -q test`
  - `./mvnw -q clean verify`
- HMSR obligations:
  1. **Correct HRA-111 so `report_run_id` references `hidra_reporting_run(id)`, not `hidra_reporting_catalog_entry(id)`.**
  2. **Make artifact generation fail closed when the referenced ReportRun does not exist.**
  3. **Enforce the DDD invariant that every artifact references at least one Documents storage object or Document metadata record; validate supplied cross-module references through Documents-owned contracts without adding cross-module DB FKs.**

- Batch 12 implementation: Existing run and nonblank Documents reference evidence required; every supplied reference is independently owner-validated. Forward 013 corrects artifact/run FK without Documents FK. Six focused methods, one Documents owner method and five additional PostgreSQL cases prepared; CI pending.

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
- `src/test/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryServiceTest.java` — exact-head oracle suite 1 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/ReportingSemanticPostgresIntegrationTest.java` — exact-head oracle suite 14 cases, zero failures/errors/skips.
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java` — exact-head oracle suite 6 cases, zero failures/errors/skips.

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

- `eaa3a820a50e344f494fc6c681be3d10d801513a` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:128](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L128)
- [doc/model-remediation/RECONCILIATION.md:13740](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13740)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15480](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15480)
- [doc/testing/REQUIREMENTS_TRACEABILITY.md:127](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/testing/REQUIREMENTS_TRACEABILITY.md#L127)
- [docs/roadmap/model-semantic-remediation.md:264](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L264)
- [docs/roadmap/model-semantic-remediation.md:4709](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-remediation.md#L4709)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Retained completed semantic remediation; legacy Planned/Blocked text is superseded by canonical reconciliation and current regression evidence. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-remediation.md:264 | HMR-093 | HMSR-110 | reporting | ReportOutputArtifact | — | documents.Document, documents.DocumentStorageObject, reporting.ReportRun | fix(reporting): remediate semantic review ReportOutputArtifact | Implemented — CI pending
doc/model-remediation/RECONCILIATION.md:128 | HMR-093 | HMSR-110 | reporting.ReportOutputArtifact | COMPLETED — CI #588 GREEN | Existing run and nonblank Documents reference evidence required; every supplied reference is independently owner-validated. Forward 013 corrects artifact/run FK without Documents FK. Six focused methods, one Documents owner method and five additional PostgreSQL cases prepared; Full production CI #588 passed.
doc/model-remediation/RECONCILIATION.md:13740 | HMR-093 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
doc/testing/REQUIREMENTS_TRACEABILITY.md:127 | HMR-093 | HMSR-110 | [reporting.ReportOutputArtifact](../domain/SEMANTIC_DECISIONS.md#reporting-reportoutputartifact) | [ReportOutputArtifactSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java) | [V20261007_013__hmr_093_reporting_report_output_artifact](../../src/main/resources/db/migration/V20261007_013__hmr_093_reporting_report_output_artifact.sql); [ReportingDocumentReferenceContract](../../src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java) | E1
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15480 | HMR-093 | CI-confirmed implemented in existing 57-task ledger | COMPLETED_PRESERVED | Existing accountable owner retained; original dependency edges unchanged | All original scope retained; no new simulator deferral | Existing gate retained; no reopening/selection; future scope needs owner authorization
~~~~~~~~

<a id="hapi-323"></a>
## HAPI-323 — review reporting ReportDefinition

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-013

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review reporting ReportDefinition

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportWorkflowPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportTemplateVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportWorkflowPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDefinitionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportWorkflowPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportDefinitionStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportTemplateVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportDefinitionJpaRepository.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 26. HMSR-013 — reporting.ReportDefinition review

**Decision:** REVISE  
**Review code:** HMSR-013  
**Dependency level:** 0  
**Bounded context:** reporting  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 2 — `reporting.ReportRequest` and `reporting.ReportRun`  
**Transitive dependents:** 3  
**Unresolved/non-subject references:** 1 — `currentTemplateVersionId`, now semantically resolved to Reporting-owned `ReportTemplateVersion` outside the 123 HMS subject set

### 26.1 Semantic role and ordering rationale

`ReportDefinition` is the Reporting-owned reusable formal report type. It defines stable report identity, multilingual display labels, report category, source/owner module, access/approval characteristics and the currently selected template-version reference used to support reproducible formal output.

It is Level 0 because none of its prerequisites are another HMS subject model. `ReportRequest` and `ReportRun` are its two direct HMS subject dependents. `ReportCatalogEntry`, `ReportTemplate`, `ReportTemplateVersion` and `ReportAccessPolicy` are Reporting read/persistence models outside the 123 HMS subject population.

Reporting owns formal output definition, execution, reproducibility, publication and distribution metadata. It must not become the owner of source operational truth from Telemetry, Planning, Risk, Custody, HSE, Integrity or other modules.

### 26.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable ReportDefinition identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable unique business code for the formal report type. |
| `nameAr` | `String` | Optional | Arabic report-definition display name. |
| `nameFr` | `String` | Mandatory | French report-definition display name; explicitly required by Reporting DDD and persistence. |
| `nameEn` | `String` | Optional | English report-definition display name. |
| `reportCategoryId` | `String` | Mandatory | Reporting-owned catalog reference in family `REPORT_CATEGORY`. |
| `ownerModule` | `String` | Mandatory | Stable Hidra module code identifying the business/source owner of the report definition; must resolve to a known Hidra module. |
| `description` | `String` | Optional | Human-readable definition purpose; current creation service applies the reporting secret-material guard. |
| `active` | `boolean` | Current persisted lifecycle proxy | Current implementation uses this boolean to determine `usableForNewRequests()`, but it cannot represent the target DDD's full definition lifecycle. |
| `currentTemplateVersionId` | `String` | Optional | Current Reporting-owned template-version reference used by the definition; target is `ReportTemplateVersion`, not an HMS subject. |
| `requiresApproval` | `boolean` | Mandatory persisted state | Whether requests/runs require Workflow-backed approval before queueing. |
| `restricted` | `boolean` | Mandatory persisted state | Whether explicit Reporting access policy/authorization is required. |
| `createdAt` | `Instant` | Mandatory in persistence | Creation timestamp. |
| `updatedAt` | `Instant` | Mandatory in persistence | Last-update timestamp. |

The current domain constructor rejects blank `id`, `code`, and `reportCategoryId`, trims string values, and converts blank optional text to `null`. It does not guard `nameFr`, `ownerModule`, timestamps or template-version consistency.

### 26.3 Reporting category dependency resolution

Current repository evidence sharpens `reportCategoryId` from a generic catalog dependency to the Reporting-owned `REPORT_CATEGORY` family:

- Reporting DDD defines `ReportCatalogEntry` as the controlled Reporting vocabulary;
- `REPORT_CATEGORY` is an explicit Reporting catalog name;
- HRA-111 installs `fk_hra111_reporting_014`; 
- that FK points `hidra_reporting_report_definition.report_category_id` to `hidra_reporting_catalog_entry(id)` with `ON DELETE RESTRICT`;
- `ReportCatalogEntry` is outside the 123 HMS subject set.

The FK proves only that a catalog row exists. It does not prove that the row belongs to `REPORT_CATEGORY` or is active/usable.

### 26.4 `currentTemplateVersionId` resolution

The previously unresolved `currentTemplateVersionId` is semantically resolved to Reporting-owned `ReportTemplateVersion`.

Repository evidence establishes the chain:

```text
ReportDefinition
  -> currentTemplateVersionId -> ReportTemplateVersion
ReportTemplateVersion
  -> reportTemplateId -> ReportTemplate
ReportTemplate
  -> reportDefinitionId -> ReportDefinition
```

`ReportTemplate` and `ReportTemplateVersion` are Reporting read/persistence models outside the 123 HMS subject population, so no HMS graph edge is added.

The base schema indexes `current_template_version_id` but no current FK was found from `hidra_reporting_report_definition.current_template_version_id` to `hidra_reporting_report_template_version(id)`. HRA-111 does protect `ReportTemplateVersion.reportTemplateId -> ReportTemplate.id` and `ReportTemplate.reportDefinitionId -> ReportDefinition.id`.

Therefore the final application/database contract must validate not only that the referenced template version exists, but that its parent template belongs to the same ReportDefinition.

### 26.5 Lifecycle contradiction — boolean `active` versus retained definition status

The active Reporting DDD defines the ReportDefinition lifecycle as:

```text
DRAFT -> ACTIVE -> RETIRED
```

with DRAFT editable, ACTIVE usable for requests, and RETIRED unavailable for new requests.

The repository also retains `ReportDefinitionStatus { DRAFT, ACTIVE, RETIRED }`. HRA-080 explicitly decided to KEEP `ReportDefinitionStatus` separate from `ReportTemplateVersionStatus`, stating that definition and template-version lifecycles are distinct.

However, the live `ReportDefinition` record, JPA entity and `hidra_reporting_report_definition` table contain only `boolean active`; no `ReportDefinitionStatus` component/column is present. Repository search found no active Java use of `ReportDefinitionStatus` beyond the enum declaration and architecture documentation.

`ReportingApplicationService.createReportDefinition()` also creates every new definition with `active = true`, so the current creation path skips the documented DRAFT phase entirely and cannot distinguish RETIRED from a generic inactive definition.

This is a direct target-model semantic contradiction. The final correction must explicitly choose and migrate to one authoritative lifecycle representation; it must not leave the retained enum and persisted boolean as conflicting sources of intent.

### 26.6 Request, approval and restricted-access governance

The Reporting DDD requires:

```text
inactive definitions cannot be used for new report requests
restricted reports require explicit access policy
reports requiring approval cannot be queued before workflow approval
```

The current `ReportingApplicationService` does not demonstrate those rules:

- `requestReport()` creates a request directly from `command.reportDefinitionId()` without loading the ReportDefinition or calling `usableForNewRequests()`;
- it does not validate `restricted` definitions through a Reporting authorization/access-policy port;
- `queueReportRun()` accepts `reportDefinitionId` and `templateVersionId` directly and does not load the definition/request to enforce `requiresApproval` or Workflow approval state;
- the current service has no injected `ReportAccessPolicy` or `ReportWorkflowPort` collaboration in these paths.

These are application/workflow/authorization responsibilities, not constructor-only invariants.

### 26.7 Persistence and creation consistency

The live domain and JPA models agree on all 14 declared components.

The base Reporting migration makes `id`, `code`, `name_fr`, `report_category_id`, `owner_module`, `active`, `requires_approval`, `restricted`, `created_at`, and `updated_at` non-null. The current template-version reference is nullable.

The schema has ordinary indexes on definition `code`, category, active state, current template version and timestamps. No unique constraint on `ReportDefinition.code` was found.

`ReportDefinitionRepositoryPort` and `ReportDefinitionJpaRepository` expose no code lookup/uniqueness method. The creation service saves directly without a duplicate-code check.

`nameFr` and `ownerModule` are persistence-required, but the domain constructor accepts blank/null values and normalizes them to `null`. The creation service performs no demonstrated known-module validation for `ownerModule` and no Reporting catalog-family validation for `reportCategoryId`.

### 26.8 Reproducibility and template-version governance

The Reporting DDD requires report runs to preserve the exact template version used and states that active template versions cannot be edited directly. Reproducibility requires definition/version identity, template-version identity, parameters, input snapshots, source contract versions, actor/correlation context, artifact checksum and generation timestamp.

`ReportDefinition.currentTemplateVersionId` may provide a default/current choice, but a completed `ReportRun.templateVersionId` is the historical execution reference. A later template change must never rewrite the version associated with an already executed run.

The current queue path accepts `templateVersionId` directly and does not demonstrate that the version is ACTIVE/eligible or belongs to the requested definition. That relationship must be validated through the Reporting-owned template/version chain before execution.

### 26.9 Required revisions

The target baseline cannot be APPROVED until these evidence-backed issues are reconciled:

1. **Definition lifecycle representation:** reconcile the DDD/HRA-retained `ReportDefinitionStatus {DRAFT, ACTIVE, RETIRED}` with the live boolean `active` model/schema. The creation path must not silently skip DRAFT unless the target lifecycle is explicitly changed.
2. **Unique definition code:** enforce global uniqueness of `ReportDefinition.code` at the application/database boundary.
3. **ACTIVE-only request use:** `requestReport()` must verify that the selected definition is eligible for new requests under the chosen lifecycle representation.
4. **Restricted-report access policy:** restricted definitions must be validated through explicit Reporting authorization/access-policy rules before request/access/generation paths proceed.
5. **Approval governance:** definitions with `requiresApproval = true` must not be queued before the relevant request has Workflow-backed approval.
6. **Template-version integrity:** `currentTemplateVersionId` and queued `templateVersionId` must resolve to valid Reporting template versions whose parent templates belong to the selected definition and satisfy the applicable lifecycle rule.
7. **Category-family validation:** `reportCategoryId` must resolve specifically to an active/usable `REPORT_CATEGORY` entry, not merely to any Reporting catalog row.
8. **Known owner module:** `ownerModule` must be validated against the authoritative Hidra module catalog/contract.
9. **Required French name:** reconcile DDD/JPA/schema-required `nameFr` with the domain constructor's nullable behavior at a deliberate validation boundary.
10. **Required timestamps:** retain a deliberate enforcement boundary for `createdAt` and `updatedAt`.
11. **HRA-080 documentation consistency:** final reconciliation must correct the architecture statement that ReportDefinition status values are persisted independently if the final model does not actually persist `ReportDefinitionStatus`.

HMSR-013 does not change production Java, JPA, Flyway, Workflow/authorization integration, API/application contracts, tests, catalogs, report definitions or template-version data.

### 26.10 Operational interpretation

For SONATRACH/TRC operations, a ReportDefinition represents a governed formal output such as a Daily Operations Report, Monthly Throughput Report, Plan-vs-Actual Report, Incident Register, Custody Transfer Statement, HSE Compliance Report or Risk Register Report.

The definition controls formal Reporting behavior and governance. It does not own or recalculate the source operational facts. Analytics calculates analytical results, source modules own business state, Documents manages file/document metadata, Notification delivers report-ready messages, Workflow governs approvals where required, and Audit records critical evidence.

### 26.11 Review conclusion

**REVISE.** `ReportDefinition` is the correct foundational Reporting aggregate and its two HMS dependency directions are sound. `reportCategoryId` is resolved to `REPORT_CATEGORY`, and `currentTemplateVersionId` is resolved to Reporting-owned `ReportTemplateVersion` outside the HMS graph.

The target baseline cannot mark it APPROVED while the definition lifecycle is internally contradictory, unique code and required-field semantics are unenforced, and request/access/approval/template-version governance is absent from the current orchestration paths. HMS reconciliation must retain these obligations until an explicitly authorized Reporting correction task resolves them or the target semantics are explicitly changed.

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

- `5516760162488b1a8fbc37782bddb29522453867` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:65](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L65)
- [docs/roadmap/model-semantic-review.md:208](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L208)
- [docs/roadmap/model-semantic-review.md:4531](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L4531)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:208 | HMSR-013 | 0 | reporting | ReportDefinition | — | — | 2 | 3 | 1 | REVISE | Completed | docs(model-review): review reporting ReportDefinition
doc/domain/SEMANTIC_DECISIONS.md:65 | HMSR-013 | [reporting.ReportDefinition](#reporting-reportdefinition) | REVISE | HMR-013
~~~~~~~~

<a id="hapi-327"></a>
## HAPI-327 — review analytics AnalyticsDataset

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-017

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review analytics AnalyticsDataset

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsDatasetType.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsLineageStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRefreshMode.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsSubjectAreaJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsDatasetType.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsLineageStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsSubjectAreaJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsDatasetType.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsLineageStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRefreshMode.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsSubjectAreaJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 30. HMSR-017 — analytics.AnalyticsDataset review

**Decision:** APPROVED  
**Review code:** HMSR-017  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `analytics.AnalyticsDatasetVersion` through `datasetId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 0

### 30.1 Semantic role and ordering rationale

`AnalyticsDataset` is the Analytics-owned metadata aggregate for one curated analytical dataset family. It defines stable identity, localized labels, analytical subject area, dataset/refresh classifications, lineage and quality state, schema/provenance metadata and an optional validity interval.

It is Level 0 because its only domain prerequisite, `AnalyticsSubjectArea`, is retained as an Analytics read/persistence model outside the 123 HMS subject population. `AnalyticsDatasetVersion` is the sole direct HMS subject dependent through `datasetId`.

Analytics remains a derived/read-oriented bounded context. The dataset metadata does not own or mutate Telemetry, Topology, Planning, Monitoring, Alarm, Incident, Integrity, Custody, HSE, Risk, Audit or other source-domain truth.

### 30.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable dataset identity and persistence primary key. |
| `code` | `String` | Mandatory | Stable dataset business code. The current Analytics DDD does not state a uniqueness invariant for dataset code, so HMSR-017 does not invent one. |
| `nameAr` | `String` | Optional | Arabic dataset display label. |
| `nameFr` | `String` | Persistence-required text | French dataset display label. JPA/schema require it, while HRA explicitly classifies generic names/labels as persistence-only text rather than automatic constructor invariants. |
| `nameEn` | `String` | Optional | English dataset display label. |
| `subjectAreaId` | `String` | Mandatory | Same-module reference to Analytics-owned `AnalyticsSubjectArea`. |
| `datasetType` | `AnalyticsDatasetType` | Mandatory | Stable dataset kind: `SNAPSHOT`, `TIME_SERIES`, `AGGREGATE`, `FEATURE_SET`, `TRAINING_DATASET`, `VALIDATION_DATASET`, `DASHBOARD_VIEW`, or `DIGITAL_TWIN_READINESS_VIEW`. |
| `refreshMode` | `AnalyticsRefreshMode` | Mandatory | Refresh policy classification: `MANUAL`, `SCHEDULED`, `EVENT_DRIVEN`, `INCREMENTAL`, or `FULL_REBUILD`. |
| `lineageStatus` | `AnalyticsLineageStatus` | Mandatory | Current lineage completeness state: `DRAFT`, `COMPLETE`, `PARTIAL`, `BROKEN`, or `UNKNOWN`. |
| `qualityStatus` | `AnalyticsQualityStatus` | Mandatory | Current analytical data-quality state: `UNKNOWN`, `READY`, `WARNING`, `FAILED`, or `REJECTED`. |
| `schemaVersion` | `String` | Optional | Dataset schema-version descriptor. |
| `createdFrom` | `String` | Optional provenance text | High-level creation/provenance descriptor; it does not replace version-level source lineage. |
| `validFrom` | `Instant` | Optional | Dataset metadata validity start. |
| `validTo` | `Instant` | Optional | Dataset metadata validity end; when both endpoints exist, must not precede `validFrom`. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. HRA intentionally keeps generic audit-timestamp nullability at persistence/application boundaries. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor correctly enforces `id`, `code`, `subjectAreaId`, all four enum/state fields, and `validFrom <= validTo` when both endpoints are present. It normalizes textual values consistently.

### 30.3 Subject-area dependency resolution

HMS-003 classified `subjectAreaId` generically as `CATALOG_OR_VALUE`. Stronger current repository evidence resolves the target precisely without adding an HMS subject edge:

- Analytics DDD defines `AnalyticsSubjectArea` as a main Analytics aggregate/reference concept;
- `AnalyticsSubjectAreaJpaEntity` persists it in `hidra_analytics_subject_area`;
- repository mirror classification retains `AnalyticsSubjectArea` as a `READ_PERSISTENCE_MODEL` outside the 123 HMS subjects;
- HRA-111 installs `fk_hra111_analytics_004`; 
- that FK points `hidra_analytics_dataset.subject_area_id` to `hidra_analytics_subject_area(id)` with `ON DELETE RESTRICT`.

Therefore `subjectAreaId` is a **same-module domain reference to a non-subject Analytics model**, not a generic catalog dependency and not a subject-graph edge.

The Analytics DDD requires unique subject-area codes and states that a subject area's `ownerModule` identifies analytical stewardship rather than transactional ownership. Those rules belong to `AnalyticsSubjectArea`, not to `AnalyticsDataset`.

### 30.4 Dataset/version/lineage ownership

The DDD deliberately separates dataset-family metadata from immutable released data:

```text
AnalyticsDataset
  -> AnalyticsDatasetVersion
       -> AnalyticsDatasetLineage
```

`AnalyticsDatasetVersion` owns `versionNumber`, schema/data hashes, row count, period, quality score and publication state. HRA-111 protects `AnalyticsDatasetVersion.datasetId -> AnalyticsDataset.id` with `ON DELETE RESTRICT`.

`AnalyticsDatasetLineage` owns source-module/object/version/snapshot provenance for a specific dataset version, and HRA-111 protects `datasetVersionId -> AnalyticsDatasetVersion.id`.

This separation correctly satisfies the Analytics DDD rule that published dataset versions preserve reproducibility and lineage. `AnalyticsDataset.createdFrom` is not treated as a replacement for version-level lineage.

The DDD rule that training datasets are immutable after publication is therefore governed through the dataset-version publication boundary. HMSR-017 does not invent an aggregate-level publication flag that the current model does not define.

### 30.5 Creation defaults and lifecycle interpretation

`AnalyticsApplicationService.createAnalyticsDataset()` creates new datasets with:

```text
lineageStatus = DRAFT
qualityStatus = UNKNOWN
validFrom = null
validTo = null
```

These defaults are conservative and semantically compatible with a newly registered derived dataset whose materialized/versioned content and lineage have not yet been published.

The current Analytics DDD does not define a separate `AnalyticsDatasetStatus` lifecycle for this aggregate. Publication/immutability state is represented on `AnalyticsDatasetVersion`, while lineage and quality are represented explicitly on the dataset metadata. HMSR-017 therefore does not introduce a missing-status defect.

### 30.6 Persistence consistency

The live domain and JPA models agree on all 16 declared components.

The base Analytics migration makes `id`, `code`, `name_fr`, `subject_area_id`, `dataset_type`, `refresh_mode`, `lineage_status`, `quality_status`, `created_at`, and `updated_at` non-null. Optional schema/provenance/validity fields remain nullable.

The database has ordinary indexes on dataset code, subject area and audit timestamps. The Analytics DDD does **not** state that dataset code is unique, so the absence of a unique code constraint is not treated as a semantic defect.

HRA's exact-head invariant classification intentionally keeps generic names/labels such as `nameFr` and audit timestamps as persistence-only concerns unless a later domain-specific rule says otherwise. HMSR-017 therefore does not repeat the earlier pattern of incorrectly promoting every `NOT NULL` display field into a domain-constructor invariant.

### 30.7 Enum versus Analytics catalog note

The Analytics DDD lists `DATASET_TYPE` and `REFRESH_MODE` among example `AnalyticsCatalogEntry` families, while the live `AnalyticsDataset` model and schema use the strongly typed enums `AnalyticsDatasetType` and `AnalyticsRefreshMode` directly.

No current dataset FK, application lookup, or seed evidence found during HMSR-017 makes `hidra_analytics_catalog_entry` authoritative for these two fields. The executable model and explicit dataset value lists are internally consistent.

For later data provisioning, this means `DATASET_TYPE` / `REFRESH_MODE` catalog rows must **not** be assumed to be required relational parents of `AnalyticsDataset` unless an explicit Analytics design change establishes that contract. This is a documentation/provisioning caution, not a production-model correction obligation.

### 30.8 Multilingual and operational interpretation

Dataset labels are display metadata only. Current authoritative analytical facts remain in source modules and versioned dataset content/lineage.

For SONATRACH/TRC, an AnalyticsDataset may represent curated trusted telemetry windows, plan-versus-actual aggregates, pipeline performance views, integrity trends, custody reconciliation sets, HSE/risk analytical views, or future digital-twin-readiness inputs. The dataset is always derived evidence; it does not become the operational source of truth.

Analytics may use the dataset to compute metrics, projections, KPI values, trends and advisory insights, but any operational decision/action remains owned by the relevant business module.

### 30.9 Review conclusion

**APPROVED.** `AnalyticsDataset` has a coherent 16-field model, correct Level-0 placement, correct `AnalyticsSubjectArea` same-module non-subject dependency, correct validity ordering, conservative creation defaults, and a clean separation between dataset metadata and version-level publication/lineage.

No production correction obligation is created by HMSR-017. The only retained note is for future provisioning/documentation: do not treat the example `DATASET_TYPE` / `REFRESH_MODE` catalog families as relational parents of the enum-backed dataset fields without a separately authorized design decision.

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

- `8ddf4757cb5108488e2ac948f2898354a483882d` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:69](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L69)
- [docs/roadmap/model-semantic-review.md:212](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L212)
- [docs/roadmap/model-semantic-review.md:5103](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L5103)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:212 | HMSR-017 | 0 | analytics | AnalyticsDataset | — | — | 1 | 1 | 0 | APPROVED | Completed | docs(model-review): review analytics AnalyticsDataset
doc/domain/SEMANTIC_DECISIONS.md:69 | HMSR-017 | [analytics.AnalyticsDataset](#analytics-analyticsdataset) | APPROVED | —
~~~~~~~~

<a id="hapi-328"></a>
## HAPI-328 — review analytics MetricEvaluationRun

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-018

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review analytics MetricEvaluationRun

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricDefinitionVersionJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricDefinitionVersionJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricDefinitionVersionJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 31. HMSR-018 — analytics.MetricEvaluationRun review

**Decision:** REVISE  
**Review code:** HMSR-018  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 1 — `analytics.MetricValue` through `metricEvaluationRunId`  
**Transitive dependents:** 1  
**Unresolved/non-subject references:** 1 — `metricDefinitionVersionId`, now semantically resolved to Analytics-owned `MetricDefinitionVersion` outside the 123 HMS subject set

### 31.1 Semantic role and ordering rationale

`MetricEvaluationRun` is the Analytics-owned execution record for calculating one versioned metric over a defined time period and analytical scope. It carries the exact metric-definition version, execution lifecycle, scope discriminator/reference, run timing, processing counters, diagnostic context and correlation identity.

It is Level 0 because its required upstream metric-definition version is a retained Analytics read/persistence model outside the HMS subject population. `MetricValue` is the one direct HMS subject dependent through `metricEvaluationRunId`.

Analytics remains a derived/read-oriented bounded context. A metric evaluation may calculate operational indicators from trusted source history, but it must not mutate the source modules or convert derived values into operational truth.

### 31.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable run identity and persistence primary key. |
| `metricDefinitionVersionId` | `String` | Mandatory | Exact Analytics-owned metric-definition version used for calculation. |
| `runStatus` | `AnalyticsRunStatus` | Mandatory | Run lifecycle: `PENDING`, `RUNNING`, `COMPLETED`, `COMPLETED_WITH_WARNINGS`, `FAILED`, or `CANCELLED`. |
| `periodStart` | `Instant` | Mandatory | Evaluation-period start. |
| `periodEnd` | `Instant` | Mandatory | Evaluation-period end; current domain rule allows equality and rejects `periodEnd < periodStart`. |
| `scopeType` | `String` | Mandatory in persistence and DDD semantics | Discriminator identifying the governed analytical scope namespace. |
| `scopeId` | `String` | Scope-dependent optional | Stable target reference selected by `scopeType`; some module-defined/global scopes may legitimately be identifier-less. |
| `startedAt` | `Instant` | Mandatory | Execution start timestamp. |
| `completedAt` | `Instant` | Optional | Completion timestamp once the run reaches an applicable terminal state. |
| `recordsRead` | `Long` | Optional | Number of source records read when the calculation engine records this metric. |
| `recordsProduced` | `Long` | Optional | Number of derived result records produced. |
| `errorCode` | `String` | Optional | Machine-readable diagnostic code for failed/warning execution. |
| `errorMessage` | `String` | Optional | Human-readable diagnostic context. |
| `correlationId` | `String` | Optional technical reference | End-to-end trace/correlation identity. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Run creation timestamp. |

The current constructor correctly enforces `id`, `metricDefinitionVersionId`, `runStatus`, `periodStart`, `periodEnd`, `startedAt`, and `periodStart <= periodEnd`, and normalizes textual values.

### 31.3 Metric-definition-version dependency resolution

HMS-003 left `metricDefinitionVersionId` unresolved because `MetricDefinitionVersion` is outside the 123 HMS subject set. Stronger current evidence resolves the target without adding a graph edge:

- Analytics DDD defines `MetricDefinitionVersion` as the versioned metric formula/calculation policy;
- `MetricDefinitionVersionJpaEntity` persists it in `hidra_analytics_metric_definition_version`;
- repository mirror classification retains `MetricDefinitionVersion` as an Analytics `READ_PERSISTENCE_MODEL` outside the HMS subjects;
- HRA-111 installs `fk_hra111_analytics_020`; 
- that FK points `hidra_analytics_metric_evaluation_run.metric_definition_version_id` to `hidra_analytics_metric_definition_version(id)` with `ON DELETE RESTRICT`.

Therefore the run correctly references an immutable/versioned calculation definition rather than only a mutable metric identity.

The DDD also gives metric-definition versions `validFrom/validTo` and states that validity periods must not overlap for active calculation use. The current run service does not load the version before starting a run, so the database FK proves existence only; it does not prove the version is eligible for the requested evaluation period.

### 31.4 Analytical scope semantics

The Analytics DDD explicitly states:

```text
scope must reference a topology asset, organization unit, product, or module-defined analytical scope
```

and the general Analytics scope section lists examples including `NETWORK`, `PIPELINE_SYSTEM`, `PIPELINE`, `PIPELINE_SEGMENT`, `STATION`, `FACILITY`, `EQUIPMENT`, `MEASUREMENT_POINT`, `ORGANIZATION_UNIT`, `PRODUCT`, `CUSTODY_TRANSFER_POINT`, `HSE_SITE`, and `RISK_AREA`.

`scopeType + scopeId` is therefore a typed/polymorphic reference pair. It must not be collapsed into one HMS subject edge or cross-module database FK.

However, the live implementation currently has two consistency gaps:

- `scope_type` is `NOT NULL` in JPA/schema and is semantically required to interpret the scope, but `MetricEvaluationRun` does not reject null/blank `scopeType`; 
- `AnalyticsApplicationService.runMetricEvaluation()` accepts `scopeType/scopeId` directly and performs no demonstrated scope-type vocabulary or target-resolution validation.

The final design needs a neutral lookup/validation contract appropriate to the selected scope namespace while preserving foreign-module ownership.

### 31.5 Run creation and lifecycle coverage

`AnalyticsApplicationService.runMetricEvaluation()` currently creates a run with:

```text
runStatus = RUNNING
startedAt = now
completedAt = null
recordsRead = 0
recordsProduced = 0
errorCode = null
errorMessage = null
createdAt = now
```

Those are coherent start-state defaults.

Repository search during HMSR-018 found no current application completion/failure/cancellation path for `MetricEvaluationRun`. The DDD requires failed runs to be auditable, but the current public orchestration only demonstrates run creation and therefore does not yet demonstrate how `FAILED`, `COMPLETED_WITH_WARNINGS`, `COMPLETED`, or `CANCELLED` are reached while preserving diagnostics and completion metadata.

HMSR-018 does **not** invent a transition matrix that the Analytics DDD does not define. It records only the missing lifecycle orchestration needed to make the existing status/diagnostic fields operationally meaningful.

### 31.6 Persistence consistency and deliberately unasserted invariants

The live domain and JPA models agree on all 15 declared components. The base Analytics migration makes `id`, `metric_definition_version_id`, `run_status`, `period_start`, `period_end`, `scope_type`, `started_at`, and `created_at` non-null. `scope_id`, completion fields, counters and diagnostics are nullable.

HRA-051 already enforces the only explicitly approved local temporal rule for this model: `periodStart <= periodEnd`.

No current Analytics source reviewed by HMSR-018 defines either of the following as target invariants:

- `completedAt >= startedAt`; 
- non-negative `recordsRead` / `recordsProduced`.

Those constraints may be reasonable future decisions, but they are **not** added to the semantic baseline by this review because the repository evidence does not currently establish them.

Likewise, HRA intentionally keeps generic audit-timestamp nullability at the persistence/application boundary, so `createdAt` is not promoted into an additional constructor correction obligation merely because the column is `NOT NULL`.

### 31.7 Failed-run auditability

The DDD's explicit rule is that a failed run must be auditable. The model already contains the necessary evidence carriers: run status, start/completion timestamps, error code/message, correlation ID and stable run identity.

The gap is orchestration rather than field shape: no current service path found during HMSR-018 demonstrates transition to `FAILED` while preserving diagnostic context. The correction should therefore live at the application/execution boundary, not as a speculative constructor rule requiring particular error strings for every failed instance.

### 31.8 Required revisions

The target baseline cannot be APPROVED until these evidence-backed execution rules are made explicit and enforceable:

1. **Required scope discriminator:** enforce nonblank `scopeType` at a deliberate domain/application boundary so invalid runs do not survive until database persistence.
2. **Typed scope validation:** validate the `scopeType + scopeId` pair through a neutral Analytics/owning-module lookup contract while preserving foreign-module ownership and allowing explicitly identifier-less scope types where defined.
3. **Metric-version eligibility:** before starting calculation, resolve `metricDefinitionVersionId` and enforce whatever version-validity/active-calculation rule is authoritative for the requested period; FK existence alone is insufficient.
4. **Lifecycle completion/failure orchestration:** provide a governed path for terminal run outcomes so `COMPLETED`, `COMPLETED_WITH_WARNINGS`, `FAILED`, and `CANCELLED` can preserve `completedAt`, diagnostics, counters and correlation/audit evidence.
5. **Failed-run auditability:** ensure failure execution records retain enough diagnostic/correlation evidence to satisfy the DDD rule without inventing unsupported constructor-level text requirements.

HMSR-018 does not change production Java, JPA, Flyway, scope contracts, metric-definition versions, API/application contracts, tests, evaluation runs or metric values.

### 31.9 Operational interpretation

For SONATRACH/TRC, a `MetricEvaluationRun` may calculate pipeline utilization, telemetry availability, pressure deviation, incident closure duration, alarm acknowledgement time, energy-per-volume, custody variance, HSE case rates or similar derived indicators over a governed operational scope and period.

The run is analytical evidence only. It cannot acknowledge alarms, modify plans, close incidents, alter risk ratings, change Topology, validate Telemetry, or trigger SCADA/PLC/RTU/SIS/ESD action.

### 31.10 Review conclusion

**REVISE.** `MetricEvaluationRun` has the correct 15-field execution shape, correct Level-0 placement, correct direct dependency on `MetricValue`, and a now-resolved same-module dependency on non-subject `MetricDefinitionVersion`. Its period-order invariant and start-state defaults are coherent.

The target baseline cannot mark it APPROVED while the required scope discriminator/pair is unchecked, metric-version eligibility is not validated before calculation, and the public application contract does not demonstrate terminal/failure orchestration needed for auditability. HMS reconciliation must retain these obligations until an explicitly authorized Analytics correction task resolves them or the target semantics are explicitly changed.

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

- `71bf4107a6f184e9047fba3b9bf66a84a00badcd` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:70](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L70)
- [docs/roadmap/model-semantic-review.md:213](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L213)
- [docs/roadmap/model-semantic-review.md:5224](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L5224)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:213 | HMSR-018 | 0 | analytics | MetricEvaluationRun | — | — | 1 | 1 | 1 | REVISE | Completed | docs(model-review): review analytics MetricEvaluationRun
doc/domain/SEMANTIC_DECISIONS.md:70 | HMSR-018 | [analytics.MetricEvaluationRun](#analytics-metricevaluationrun) | REVISE | HMR-016
~~~~~~~~

<a id="hapi-335"></a>
## HAPI-335 — review analytics AnalyticsInsight

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-025

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review analytics AnalyticsInsight

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 38. HMSR-025 — analytics.AnalyticsInsight review

**Decision:** REVISE  
**Review code:** HMSR-025  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 0  
**Transitive dependents:** 0  
**Unresolved/non-subject references:** 3 — all three source references are now semantically resolved to Analytics-owned read/persistence models outside the 123 HMS subject set

### 38.1 Semantic role and ordering rationale

`AnalyticsInsight` is the Analytics-owned derived finding/observation used to surface advisory analytical interpretation without taking ownership of operational action, incidents, risk acceptance, HSE response, integrity disposition, planning decisions or source-of-truth data.

It is Level 0 because none of its prerequisites are another HMS subject model. Its subject area, optional severity taxonomy and optional source objects are retained Analytics read/persistence models outside the 123-model HMS subject population, while `scopeType + scopeId` is a neutral polymorphic analytical-scope reference.

The active Analytics DDD is explicit that insights are advisory, do not open incidents directly, may be consumed by owning business modules after review, and must remain traceable.

### 38.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable AnalyticsInsight identity and persistence primary key. |
| `insightType` | `String` | Persistence-required analytical taxonomy code | Analytics-owned insight classification. The DDD lists examples and identifies `INSIGHT_TYPE` as an Analytics catalog vocabulary. |
| `subjectAreaId` | `String` | Mandatory | Same-module reference to AnalyticsSubjectArea. |
| `scopeType` | `String` | Persistence-required discriminator | Neutral analytical-scope discriminator such as pipeline system, pipeline, facility, organization unit, product or other approved scope. |
| `scopeId` | `String` | Optional | Stable target ID selected by `scopeType`; may be absent for scope kinds whose semantics do not require an object ID. |
| `title` | `String` | Persistence-required text | Human-readable insight title. |
| `summary` | `String` | Persistence-required text | Human-readable analytical finding summary. |
| `severityId` | `String` | Optional catalog reference | Analytics-owned severity reference; DDD catalog vocabulary includes `ANALYTICS_SEVERITY`. |
| `confidenceScore` | `BigDecimal` | Optional | Analytical confidence score. Current repository evidence does not define a numeric range. |
| `sourceProjectionSnapshotId` | `String` | Optional same-module source reference | Direct lineage/provenance reference to AnalyticsProjectionSnapshot. |
| `sourceTrendAnalysisId` | `String` | Optional same-module source reference | Direct lineage/provenance reference to TrendAnalysis. |
| `sourceModelRunId` | `String` | Optional same-module source reference | Direct lineage/provenance reference to AnalyticsModelRun. |
| `status` | `AnalyticsInsightStatus` | Mandatory | Insight lifecycle state: `DRAFT`, `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `DISMISSED`, or `ARCHIVED`. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |
| `updatedAt` | `Instant` | Persistence-required audit timestamp | Last-update timestamp. |

The compact constructor currently rejects blank `id` and `subjectAreaId`, requires non-null `status`, normalizes textual fields and exposes `advisoryOnly() == true`.

### 38.3 Subject-area dependency resolution

HMS-003 treated `subjectAreaId` generically as a catalog/value dependency. Stronger evidence resolves it to the Analytics-owned `AnalyticsSubjectArea` read/persistence model:

- Analytics DDD owns AnalyticsSubjectArea;
- `AnalyticsInsightJpaEntity.subjectAreaId` maps to mandatory `hidra_analytics_insight.subject_area_id`;
- repository mirror classification retains AnalyticsSubjectArea as an Analytics read/persistence model outside the HMS subject set;
- HRA-111 installs `fk_hra111_analytics_010`, linking `hidra_analytics_insight.subject_area_id -> hidra_analytics_subject_area(id)` with `ON DELETE RESTRICT`.

No HMS graph edge is added because AnalyticsSubjectArea is outside the 123 subject models.

### 38.4 Analytical scope semantics

The Analytics DDD defines neutral analytical scopes and gives examples including network, pipeline system, pipeline, segment, facility, equipment, measurement point, organization unit, product, custody transfer point, HSE site and risk area.

Therefore:

```text
scopeType + scopeId
```

is a polymorphic analytical reference, not an Analytics-owned aggregate FK.

The schema deliberately makes `scope_type` mandatory while `scope_id` is nullable. HMSR-025 does not invent which scope types require IDs or a cross-module database FK. Those type/ID rules require an explicit owner-resolved analytical-scope contract.

### 38.5 Insight-type catalog semantics and current gap

The active Analytics DDD defines `AnalyticsCatalogEntry` as controlled vocabulary for Analytics business taxonomy and explicitly lists:

```text
INSIGHT_TYPE
ANALYTICS_SEVERITY
```

among catalog examples.

It also enumerates example insight classifications such as recurring deviation, performance degradation, data-quality issue, risk-increase pattern, asset-reliability pattern, integrity-degradation pattern, custody-variance pattern, HSE trend and digital-twin-readiness gap.

The live model nevertheless stores `insightType` as an unrestricted String. The create request and command also accept an unrestricted String, and `AnalyticsApplicationService.createAnalyticsInsight()` persists it without demonstrating resolution against an `INSIGHT_TYPE` catalog entry/code.

The schema requires `insight_type NOT NULL`, while the domain constructor merely normalizes it. A null/blank insight type can therefore survive domain construction and fail only at persistence.

This is stronger than a generic text concern: the DDD explicitly defines insight type as controlled Analytics taxonomy.

### 38.6 Severity catalog semantics

`severityId` is nullable, so absence is currently allowed. When present, however, Analytics DDD evidence identifies severity as Analytics-owned controlled vocabulary through `ANALYTICS_SEVERITY`.

The base schema currently provides only an index on `hidra_analytics_insight.severity_id`. HRA-111 does not add an FK because its authorized scope covered mandatory same-module references.

No current create-path validation demonstrates that a supplied `severityId` resolves to an AnalyticsCatalogEntry, much less the intended severity catalog family.

HMSR-025 therefore does not make severity mandatory, but it records that a non-null severity reference must not be allowed to dangle or point to an unrelated Analytics catalog family.

### 38.7 Source lineage resolution and referential gap

HMS-003 left two source fields unresolved and treated the projection-snapshot field as snapshot/reference-only. Stronger repository evidence resolves all three to concrete same-module source records outside the HMS subject set:

```text
sourceProjectionSnapshotId
    -> analytics.AnalyticsProjectionSnapshot
    -> hidra_analytics_projection_snapshot.id

sourceTrendAnalysisId
    -> analytics.TrendAnalysis
    -> hidra_analytics_trend_analysis.id

sourceModelRunId
    -> analytics.AnalyticsModelRun
    -> hidra_analytics_model_run.id
```

All three target models are retained as Analytics read/persistence models. The base schema creates indexes for the three source columns but no FK. HRA-111 does not cover them because they are optional.

The current application service forwards caller-provided values directly into AnalyticsInsight without demonstrating existence checks.

The DDD also provides the broader `AnalyticsInsightEvidence` structure for heterogeneous evidence and HRA-111 protects its mandatory `analyticsInsightId -> AnalyticsInsight.id` relationship. That evidence model means HMSR-025 does **not** require one of the three direct source IDs to be non-null for every insight: an insight may have other valid evidence forms.

What is required is that any direct source ID that **is** populated remains traceable to the concrete Analytics source it claims to reference.

### 38.8 Domain/persistence requiredness gap

The final JPA/schema contract requires:

```text
insight_type NOT NULL
subject_area_id NOT NULL
scope_type NOT NULL
title NOT NULL
summary NOT NULL
status NOT NULL
```

The domain already requires `subjectAreaId` and `status`.

HMSR-025 promotes two additional requiredness obligations because repository semantics independently prove they are structured business identifiers/discriminators:

- `insightType` is controlled Analytics business taxonomy;
- `scopeType` selects the analytical-scope namespace.

The constructor currently permits both to normalize to `null`.

By contrast, `title` and `summary` remain generic human-readable payload. Under the established HRA invariant policy, database `NOT NULL` alone does not convert generic titles/summaries into constructor-level business invariants. HMSR-025 therefore does not invent domain guards for those fields.

### 38.9 Advisory lifecycle semantics deliberately not invented

The DDD states that an insight is advisory and may be referenced by business modules after review. The status enum provides `DRAFT`, `OPEN`, `UNDER_REVIEW`, `ACCEPTED`, `DISMISSED`, and `ARCHIVED`.

The current create service creates insights as `OPEN`. No active Analytics DDD rule reviewed here states whether creation must instead start at `DRAFT`, nor defines the complete allowed transition matrix. HMSR-025 therefore does not classify `OPEN` creation as defective and does not invent transition rules.

Similarly, no current evidence defines an allowed numeric range for `confidenceScore`; HMSR-025 does not assume 0..1 merely from the field name.

### 38.10 Required revisions

The reviewed model remains semantically useful, but four evidence-backed corrections are required:

1. **Require a nonblank, governed insight classification at the domain/application boundary.** `insightType` must not normalize to null and must resolve according to the Analytics-owned `INSIGHT_TYPE` catalog contract rather than accepting arbitrary free text.

2. **Require nonblank `scopeType` consistently with the analytical-scope contract and final persistence schema.** HMSR-025 does not prescribe which scope types require `scopeId`; that mapping needs its own owner-resolved scope policy.

3. **Protect optional severity semantics.** When `severityId` is supplied, it must resolve to the appropriate Analytics severity catalog entry/family rather than allowing a dangling or unrelated catalog ID.

4. **Protect populated direct source lineage.** Each non-null `sourceProjectionSnapshotId`, `sourceTrendAnalysisId`, and `sourceModelRunId` must resolve to the corresponding Analytics-owned source record. The correction may use application validation, additive same-module FKs where appropriate, or another fail-closed Analytics-owned boundary in a separately authorized implementation task.

HMSR-025 does not modify production Java, JPA, Flyway, API/application contracts, tests, analytics records or provisioned data.

### 38.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, an AnalyticsInsight is an advisory derived observation over trusted operational history. It may highlight recurring deviations, declining performance, data-quality concerns, reliability patterns, integrity degradation, custody variance, HSE trends or readiness gaps, but it must not become an automatic incident, risk decision, HSE action or planning instruction.

Its classification, scope and provenance therefore need to be trustworthy. An arbitrary type, missing scope namespace, unrelated severity code or dangling source reference would make the analytical finding difficult to govern, review and audit.

The owning operational module remains responsible for any subsequent business decision.

### 38.12 Review conclusion

**REVISE.** `AnalyticsInsight` has a coherent advisory role, correct Level-0 placement, appropriate subject-area ownership, neutral analytical scope concept, explicit lifecycle status and resolvable same-module source lineage.

The target baseline cannot mark it APPROVED while controlled `insightType` and mandatory `scopeType` can be absent at the domain boundary, while `insightType` is accepted as unrestricted text despite the explicit `INSIGHT_TYPE` catalog vocabulary, and while populated optional severity/source references lack demonstrated fail-closed integrity.

HMS reconciliation must retain these obligations until an explicitly authorized Analytics correction task resolves them or the target semantics are explicitly revised with stronger repository evidence.

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

- `c1f7c10ff9adf2a935598be4bed13e96144f7e80` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:77](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L77)
- [docs/roadmap/model-semantic-review.md:220](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L220)
- [docs/roadmap/model-semantic-review.md:6349](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L6349)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:220 | HMSR-025 | 0 | analytics | AnalyticsInsight | — | — | 0 | 0 | 3 | REVISE | Completed | docs(model-review): review analytics AnalyticsInsight
doc/domain/SEMANTIC_DECISIONS.md:77 | HMSR-025 | [analytics.AnalyticsInsight](#analytics-analyticsinsight) | REVISE | HMR-023
~~~~~~~~

<a id="hapi-336"></a>
## HAPI-336 — review analytics AnalyticsProjectionRun

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-026

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review analytics AnalyticsProjectionRun

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunMode.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunMode.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunMode.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 39. HMSR-026 — analytics.AnalyticsProjectionRun review

**Decision:** REVISE  
**Review code:** HMSR-026  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 0  
**Transitive dependents:** 0  
**Unresolved/non-subject references:** 1 — `projectionDefinitionId`, now semantically resolved to Analytics-owned `AnalyticsProjectionDefinition` outside the 123 HMS subject set

### 39.1 Semantic role and ordering rationale

`AnalyticsProjectionRun` is the Analytics-owned execution record for one materialized analytical projection computation. It captures which projection definition was run, run mode/status, optional analytical period, execution timing, source watermark, record counts, diagnostic error context and technical correlation identity.

It is Level 0 because its required projection-definition prerequisite is a retained Analytics read/persistence model outside the 123-model HMS subject population. It has no direct HMS subject dependent.

The active Analytics DDD states that projection definitions specify analytical computation, projection runs execute those definitions, and projection snapshots publish resulting states. Analytics remains derived/read-oriented and must not mutate source-of-truth operational state.

### 39.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable AnalyticsProjectionRun identity and persistence primary key. |
| `projectionDefinitionId` | `String` | Mandatory | Same-module reference to the AnalyticsProjectionDefinition being executed. |
| `runStatus` | `AnalyticsRunStatus` | Mandatory | Execution lifecycle state: `PENDING`, `RUNNING`, `COMPLETED`, `COMPLETED_WITH_WARNINGS`, `FAILED`, or `CANCELLED`. |
| `runMode` | `AnalyticsRunMode` | Mandatory | Execution mode: `FULL_REBUILD`, `INCREMENTAL`, `BACKFILL`, `MANUAL_RECOMPUTE`, or `SCHEDULED`. |
| `periodStart` | `Instant` | Optional | Start of the analytical period being processed, when the projection uses a bounded period. |
| `periodEnd` | `Instant` | Optional | End of the analytical period; current domain correctly rejects values before `periodStart` when both are present. |
| `startedAt` | `Instant` | Mandatory | Run execution start timestamp. |
| `completedAt` | `Instant` | Optional | Completion timestamp when recorded. Current DDD does not define exact status/timestamp pairing rules. |
| `sourceWatermark` | `String` | Optional in current model | Source-read watermark used for reproducibility/incremental lineage; the DDD requires successful runs to record it. |
| `recordsRead` | `Long` | Optional | Number of source records read. The current start path initializes it to zero. |
| `recordsWritten` | `Long` | Optional | Number of projection records written. The current start path initializes it to zero. |
| `errorCode` | `String` | Optional | Machine-oriented diagnostic error code. |
| `errorMessage` | `String` | Optional | Human-readable diagnostic context. |
| `correlationId` | `String` | Optional technical reference | Cross-request/job correlation identity; not a business-model dependency. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |

The compact constructor already requires nonblank `id` and `projectionDefinitionId`, non-null `runStatus`, `runMode`, and `startedAt`, and enforces `periodStart <= periodEnd` when both bounds are present.

### 39.3 Projection-definition dependency resolution

HMS-003 left `projectionDefinitionId` unresolved. Stronger live evidence resolves it to Analytics-owned `AnalyticsProjectionDefinition`:

- Analytics DDD owns AnalyticsProjectionDefinition as projection computation specification;
- repository mirror classification retains AnalyticsProjectionDefinition as an Analytics read/persistence model outside the HMS subject set;
- `AnalyticsProjectionRunJpaEntity.projectionDefinitionId` maps to mandatory `hidra_analytics_projection_run.projection_definition_id`;
- HRA-111 installs `fk_hra111_analytics_028`, linking `hidra_analytics_projection_run.projection_definition_id -> hidra_analytics_projection_definition(id)` with `ON DELETE RESTRICT`.

Therefore `projectionDefinitionId` is a required same-module domain reference to a non-HMS model. No HMS graph edge is added.

The current application run path does not explicitly look up the definition before save, but the database FK provides fail-closed row-existence protection. HMSR-026 does not invent a duplicate application existence check solely because the lookup is absent.

### 39.4 Current start-run behavior

`AnalyticsApplicationService.runProjection()` creates a new run with:

```text
runStatus      = RUNNING
startedAt      = now
completedAt    = null
sourceWatermark = null
recordsRead    = 0
recordsWritten = 0
errorCode      = null
errorMessage   = null
createdAt      = now
```

That initial state is semantically conservative for a run that has just started. The current command supplies `projectionDefinitionId`, `runMode`, optional period bounds and optional `correlationId`.

The live application surface does not currently expose a projection-run completion/failure transition use case. That absence does not remove the semantic obligations of the persisted run model: terminal AnalyticsProjectionRun rows can still exist through persistence/evolution, and the DDD explicitly specifies terminal-state evidence requirements.

### 39.5 Failed-run diagnostic obligation

Analytics DDD states:

```text
failed run must retain error context
```

The live record nevertheless permits:

```text
runStatus = FAILED
errorCode = null
errorMessage = null
```

because neither constructor nor persistence schema couples FAILED status to diagnostic fields.

The DDD does not state whether error context means `errorCode`, `errorMessage`, or both. HMSR-026 therefore does not invent an exact field combination, but the final model/application lifecycle must ensure a failed run cannot be persisted without meaningful retained error context.

### 39.6 Successful-run watermark obligation

Analytics DDD separately states:

```text
successful run must record source watermark
```

The current model permits terminal successful state with `sourceWatermark = null`. No database constraint or current transition path demonstrates a fail-closed watermark rule.

The enum includes `COMPLETED` and `COMPLETED_WITH_WARNINGS`; repository evidence does not separately define which statuses the phrase "successful run" covers. The correction must explicitly map successful terminal status semantics and enforce a nonblank source watermark for the statuses treated as successful rather than HMSR-026 guessing the mapping.

This is an analytical-lineage requirement, not generic text nullability: the source watermark is specifically required by the DDD for successful run reproducibility.

### 39.7 Projection-definition version/reproducibility gap

The strongest structural lineage issue is between the projection-definition rule and the run reference.

Analytics DDD states:

```text
projection definition must be versioned when formula changes
projection must be rebuildable from source truth and audit history
```

But the live repository contains no `AnalyticsProjectionDefinitionVersion` model/table/reference and no `projectionDefinitionVersionId` on AnalyticsProjectionRun. The run stores only:

```text
projectionDefinitionId
    -> hidra_analytics_projection_definition.id
```

while the definition row itself carries mutable computation-related fields such as `projectionType`, `calculationPolicy`, `refreshPolicy`, `retentionPolicy`, and `updatedAt`.

Therefore the current run record cannot, by its own persisted reference, identify which immutable projection-definition/formula version produced a historical result after the definition changes.

HMSR-026 does not prescribe that the correction must use a model literally named `AnalyticsProjectionDefinitionVersion`. A separately authorized Analytics task may satisfy the DDD through an immutable version record, immutable execution snapshot, audit-backed version identity, or another explicit reproducibility contract. What is required is durable run-to-computation-version lineage.

### 39.8 ProjectionSnapshot downstream persistence relationship

Although HMSR-026 has zero direct HMS subject dependents, retained Analytics persistence includes `AnalyticsProjectionSnapshot`.

HRA-111 protects:

```text
hidra_analytics_projection_snapshot.projection_run_id
    -> hidra_analytics_projection_run.id
```

through `fk_hra111_analytics_030` with `ON DELETE RESTRICT`.

The snapshot also references the projection definition independently. This reinforces the run as stable execution identity, but does not solve the formula-version lineage gap because both rows currently point only to the unversioned definition identity.

### 39.9 Run-mode, period, counters and timestamps deliberately not invented

The five `AnalyticsRunMode` values match the DDD exactly. No repository evidence reviewed by HMSR-026 defines mode-specific required period bounds, so the review does not require periods for BACKFILL, SCHEDULED, or any other mode.

The existing `periodStart <= periodEnd` invariant is already enforced. Equality is permitted by the repository-wide invariant policy.

No current Analytics rule states that `recordsRead` or `recordsWritten` must be nonnegative, mandatory at terminal state, or related by a particular inequality. HMSR-026 does not invent those constraints.

Likewise, the DDD does not explicitly state that terminal statuses require `completedAt`, that `completedAt >= startedAt`, or that CANCELLED carries diagnostics. Those potentially useful lifecycle rules are not promoted without stronger evidence.

The DDD says projection runs should be idempotent "where possible", but does not define an idempotency key or uniqueness contract. HMSR-026 therefore does not invent correlation-ID uniqueness or a compound run key.

### 39.10 Required revisions

Three evidence-backed correction obligations remain:

1. **Preserve terminal failure diagnostics.** A run persisted as `FAILED` must retain meaningful error context, as required by Analytics DDD. The implementation task must define the accepted error-code/message contract rather than silently allowing both to be absent.

2. **Preserve successful-run source lineage.** Statuses classified by Analytics as successful must require a nonblank `sourceWatermark`, consistent with the DDD. The implementation task must explicitly define the successful-status set rather than relying on implicit interpretation.

3. **Make projection runs reproducible across definition changes.** AnalyticsProjectionRun must durably identify the immutable computation/definition version that produced the run, or an equivalent immutable execution snapshot/audit version, because Analytics DDD requires definition versioning when formula changes and rebuildability from source truth/history.

HMSR-026 does not modify production Java, JPA, Flyway, REST/application contracts, tests, projection definitions/runs/snapshots, or provisioned data.

### 39.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, AnalyticsProjectionRun is execution evidence for derived operational-intelligence views such as hourly summaries, pipeline/station performance, custody variance, risk evolution or HSE performance.

A historical projection result must be explainable and reproducible: operators and auditors need to know which computation definition produced it, what source boundary/watermark was processed, and why a failed execution failed.

These controls remain analytical lineage and governance concerns. They do not grant Analytics ownership over the underlying telemetry, topology, planning, custody, HSE, risk or other operational source facts.

### 39.12 Review conclusion

**REVISE.** `AnalyticsProjectionRun` has a coherent 15-field execution model, correct Level-0 placement, correct run-mode vocabulary, an already-enforced period-order invariant, conservative RUNNING creation state, and a database-protected required reference to AnalyticsProjectionDefinition.

The target baseline cannot mark it APPROVED while failed runs may omit the DDD-required error context, successful runs may omit the DDD-required source watermark, and historical runs cannot identify an immutable projection-definition/formula version despite the explicit DDD versioning/rebuildability requirement.

HMS reconciliation must retain these three obligations until an explicitly authorized Analytics correction task resolves them or the target Analytics DDD is explicitly revised with stronger repository evidence.

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

- `bda1bd09699ba1c3b83cbd78fc4b59505fca6499` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:78](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L78)
- [docs/roadmap/model-semantic-review.md:221](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L221)
- [docs/roadmap/model-semantic-review.md:6532](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L6532)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:221 | HMSR-026 | 0 | analytics | AnalyticsProjectionRun | — | — | 0 | 0 | 1 | REVISE | Completed | docs(model-review): review analytics AnalyticsProjectionRun
doc/domain/SEMANTIC_DECISIONS.md:78 | HMSR-026 | [analytics.AnalyticsProjectionRun](#analytics-analyticsprojectionrun) | REVISE | HMR-024
~~~~~~~~

<a id="hapi-337"></a>
## HAPI-337 — review analytics DigitalTwinReadinessAssessment

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-027

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review analytics DigitalTwinReadinessAssessment

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`
- `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 40. HMSR-027 — analytics.DigitalTwinReadinessAssessment review

**Decision:** REVISE  
**Review code:** HMSR-027  
**Dependency level:** 0  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** none  
**Confirmed direct dependents:** 0  
**Transitive dependents:** 0  
**Unresolved/non-subject references:** 0

### 40.1 Semantic role and ordering rationale

`DigitalTwinReadinessAssessment` is the Analytics-owned derived assessment of whether a topology area, station, segment or asset has enough trusted data, topology completeness, model availability and lineage quality to support future digital-twin scenarios.

It is explicitly **not** a digital-twin runtime, simulation solver or topology owner. The domain method `runtimeDigitalTwin()` correctly returns `false`, and the Analytics DDD states that readiness assessment must not mutate Topology, Telemetry or Simulation models.

It is Level 0 because its only externally meaningful references are neutral analytical scope identity and a historical Topology snapshot reference, neither of which is another HMS subject-model dependency.

### 40.2 Field semantics

| Field | Type | Mandatory / optional | Reviewed meaning |
|---|---|---|---|
| `id` | `String` | Mandatory | Stable readiness-assessment identity and persistence primary key. |
| `scopeType` | `String` | Persistence-required analytical discriminator | Identifies the neutral analytical scope namespace/type being assessed. |
| `scopeId` | `String` | Optional polymorphic scope ID | Stable target ID interpreted under `scopeType`; current Analytics scope policy allows neutral cross-module references. |
| `topologySnapshotId` | `String` | Mandatory historical snapshot reference | Identifies the Topology-owned historical snapshot used as assessment context. |
| `assessmentPeriodStart` | `Instant` | Mandatory | Start of the trusted historical assessment window. |
| `assessmentPeriodEnd` | `Instant` | Mandatory | End of the trusted historical assessment window. |
| `telemetryCompletenessScore` | `BigDecimal` | Optional | Derived telemetry-coverage completeness score. |
| `telemetryQualityScore` | `BigDecimal` | Optional | Derived trusted-telemetry quality score. |
| `topologyCompletenessScore` | `BigDecimal` | Optional | Derived topology completeness score. |
| `modelAvailabilityScore` | `BigDecimal` | Optional | Derived availability/readiness score for analytical/model prerequisites. |
| `lineageCompletenessScore` | `BigDecimal` | Optional | Derived data-lineage completeness score. |
| `overallReadinessScore` | `BigDecimal` | Optional | Derived overall readiness score. |
| `readinessStatus` | `DigitalTwinReadinessStatus` | Mandatory in current model | Current fixed readiness classification: `NOT_READY`, `PARTIAL`, `READY`, `ADVANCED`, or `UNKNOWN`. |
| `assessedAt` | `Instant` | Mandatory | Timestamp at which readiness was assessed. |
| `createdAt` | `Instant` | Persistence-required audit timestamp | Creation timestamp. |

The compact constructor already requires nonblank `id` and `topologySnapshotId`, non-null assessment period bounds, non-null `readinessStatus`, and non-null `assessedAt`. It normalizes `scopeType`, `scopeId`, and `topologySnapshotId`.

### 40.3 Analytical-scope semantics

The Analytics DDD defines neutral analytical scopes and lists examples such as network, pipeline system, pipeline, pipeline segment, station, facility, equipment, measurement point, organization unit, product, custody transfer point, HSE site and risk area.

It states that scope fields should use:

```text
scopeType
scopeId
...
topologySnapshotId when applicable
```

Therefore `scopeType + scopeId` is a neutral analytical reference rather than an Analytics-owned FK. `scopeId` is not promoted into a subject-model graph edge and no cross-module database FK is appropriate.

The schema deliberately allows `scope_id` to be nullable, so HMSR-027 does not invent which scope kinds require an object ID.

### 40.4 Historical Topology snapshot boundary

The readiness lifecycle in Analytics DDD begins with:

```text
TopologySnapshot selected
  -> Trusted telemetry coverage checked
    -> Model availability checked
      -> Data lineage checked
        -> DigitalTwinReadinessAssessment created
```

The scalar-reference integrity inventory explicitly classifies:

```text
analytics.DigitalTwinReadinessAssessment.topologySnapshotId
    -> topology.TopologySnapshot
```

as historical/snapshot ownership rather than a live relational dependency.

That classification is correct. Analytics must preserve the immutable/historical snapshot identity and must not create a cross-module database FK into Topology.

A concrete Analytics-owned `TopologySnapshotLookupPort.available(referenceId)` also exists. No live `AssessDigitalTwinReadiness` application use case currently consumes that port, so HMSR-027 does not invent or implement a new write workflow during semantic review. When such a write path is implemented, the existing cross-module lookup boundary is the appropriate fail-closed mechanism for required snapshot availability rather than a database FK.

### 40.5 Confirmed scopeType domain/persistence inconsistency

The final persistence contract requires:

```text
scope_type varchar(80) NOT NULL
```

and `DigitalTwinReadinessAssessmentJpaEntity.scopeType` is mapped with `nullable = false`.

The domain constructor, however, performs only:

```text
scopeType = normalize(scopeType);
```

so null or blank input becomes `null` without rejection.

This is not merely generic display text. `scopeType` is the discriminator that gives semantic meaning to the polymorphic scope and is explicitly part of the Analytics analytical-scope contract. A readiness assessment without a scope namespace is semantically ambiguous and cannot be persisted under the final schema.

### 40.6 Readiness-status vocabulary inconsistency

The same Analytics DDD defines `AnalyticsCatalogEntry` as controlled vocabulary for Analytics business taxonomy and explicitly includes:

```text
READINESS_STATUS
```

among the catalog examples.

The live readiness model instead stores:

```text
DigitalTwinReadinessStatus readinessStatus
```

using a fixed Java enum persisted directly as `readiness_status`.

The enum values themselves are coherent readiness classifications, but the repository currently contains two competing representations for the same conceptual vocabulary:

- DDD-controlled Analytics catalog semantics for `READINESS_STATUS`;
- fixed Java enum/JPA string semantics in `DigitalTwinReadinessAssessment`.

HMSR-027 does not assume the correction must blindly replace the enum with a catalog-entry ID. A separately authorized Analytics correction must decide the authoritative representation: either make readiness classification catalog-backed/stable-code governed as the DDD indicates, or explicitly revise the DDD/catalog contract to establish `DigitalTwinReadinessStatus` as an intentional bounded enum exception.

The target semantic baseline cannot keep both interpretations unresolved.

### 40.7 Score semantics deliberately not invented

The model carries five component scores plus one overall readiness score, but current Analytics DDD does not define:

- a numeric range such as 0..1 or 0..100;
- mandatory presence of every component score;
- weighting or aggregation formula for `overallReadinessScore`;
- threshold bands mapping scores to `NOT_READY/PARTIAL/READY/ADVANCED`;
- whether `UNKNOWN` requires null scores;
- precision beyond the current persistence `numeric(10,6)` representation.

HMSR-027 therefore does not invent score validation or score/status consistency rules.

Those rules should be added only when an authoritative Analytics scoring policy/formula exists.

### 40.8 Assessment-period semantics deliberately not invented

Both assessment-period bounds are mandatory in the current domain and persistence model.

Unlike the twelve Analytics temporal invariants explicitly approved by the repository-wide invariant classification, that classification does **not** include a `DigitalTwinReadinessAssessment.assessmentPeriodStart <= assessmentPeriodEnd` rule.

The Digital Twin readiness DDD section also does not state period ordering explicitly. HMSR-027 therefore does not infer a new temporal invariant solely from field names.

If an assessment-window ordering rule is intended, it should be established by a dedicated Analytics semantic/business rule rather than silently introduced here.

### 40.9 Persistence/application consistency otherwise

The live domain and JPA representations agree on all 15 declared components.

The base Analytics schema requires:

```text
id
scope_type
topology_snapshot_id
assessment_period_start
assessment_period_end
readiness_status
assessed_at
created_at
```

and keeps the six score fields plus `scope_id` nullable.

No active inbound application command/use case or REST endpoint for readiness assessment exists in the current implementation, despite such contracts being recommended by the DDD. This is a capability gap, not evidence that the domain model should be removed.

Generic `createdAt` requiredness remains a persistence/audit concern and is not promoted into a constructor rule.

### 40.10 Required revisions

Two evidence-backed correction obligations remain:

1. **Require a nonblank analytical scope discriminator.** `scopeType` must be consistently mandatory at the domain/application boundary, matching the Analytics scope contract and final JPA/schema requiredness. HMSR-027 does not prescribe which scope types require `scopeId`.

2. **Reconcile readiness-status representation.** Resolve the contradiction between DDD `READINESS_STATUS` catalog vocabulary and the current fixed `DigitalTwinReadinessStatus` enum/JPA representation. The implementation task must establish one authoritative controlled-vocabulary strategy across domain, persistence and future API/application contracts rather than leaving two competing semantics.

HMSR-027 does not modify production Java, JPA, Flyway, API/application contracts, tests, readiness assessments, Topology snapshots or provisioned data.

### 40.11 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, this assessment is an analytical readiness view answering whether a defined operational scope has sufficiently complete topology, trusted telemetry, model availability and traceable lineage to support later digital-twin scenarios.

It is not authorization to run a digital twin, does not certify a simulation model, and must not mutate the physical-network or operational source-of-truth contexts.

The assessed scope must be unambiguous, and readiness classification must have one governed meaning across analytics, reporting and future digital-twin-readiness consumers.

### 40.12 Review conclusion

**REVISE.** `DigitalTwinReadinessAssessment` has a coherent 15-field analytical role, correct Level-0 placement, correct non-runtime boundary, appropriate historical Topology-snapshot semantics, and deliberately optional component/overall scores.

The target baseline cannot mark it APPROVED while `scopeType` is mandatory in DDD/persistence semantics but optional at domain construction, and while `readinessStatus` simultaneously exists as a fixed Java enum and as an explicitly named Analytics catalog vocabulary without a reconciled authoritative representation.

HMS reconciliation must retain these two obligations until an explicitly authorized Analytics correction task resolves them or the target Analytics DDD is explicitly revised with stronger repository evidence.

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

- `44e00ad04cf33d573ee38669fa9df6c7158328e8` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:79](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L79)
- [docs/roadmap/model-semantic-review.md:222](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L222)
- [docs/roadmap/model-semantic-review.md:6719](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L6719)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:222 | HMSR-027 | 0 | analytics | DigitalTwinReadinessAssessment | — | — | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review analytics DigitalTwinReadinessAssessment
doc/domain/SEMANTIC_DECISIONS.md:79 | HMSR-027 | [analytics.DigitalTwinReadinessAssessment](#analytics-digitaltwinreadinessassessment) | REVISE | HMR-025
~~~~~~~~

<a id="hapi-352"></a>
## HAPI-352 — review analytics AnalyticsDatasetVersion

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-042

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review analytics AnalyticsDatasetVersion

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 55. HMSR-042 — analytics.AnalyticsDatasetVersion review

**Decision:** REVISE  
**Review code:** HMSR-042  
**Dependency level:** 1  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** `analytics.AnalyticsDataset` through mandatory `datasetId`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 0 — `publishedByActorId` remains an Identity actor reference outside the HMS subject graph

### 55.1 Semantic role and ordering rationale

`AnalyticsDatasetVersion` represents one reproducible versioned release of an Analytics-owned dataset.

The Analytics DDD states that Analytics consumes trusted operational history and produces derived intelligence rather than owning operational truth. Dataset versions therefore preserve reproducibility metadata such as schema/data hashes, period boundaries, quality information and publication state.

It is Level 1 because mandatory `datasetId` references the already-reviewed `AnalyticsDataset` HMS subject.

### 55.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable dataset-version identity. |
| `datasetId` | `String` | Mandatory same-module reference to the parent AnalyticsDataset. |
| `versionNumber` | `int` | Required numeric version value; current evidence does not define positivity or uniqueness rules. |
| `schemaHash` | `String` | Mandatory schema reproducibility hash. |
| `dataHash` | `String` | Mandatory dataset-content reproducibility hash. |
| `rowCount` | `Long` | Optional row-count metadata. |
| `periodStart` | `Instant` | Optional beginning of the represented analytical period. |
| `periodEnd` | `Instant` | Optional end of the represented analytical period. |
| `qualityScore` | `BigDecimal` | Optional dataset-quality score; current evidence does not define a numeric range. |
| `published` | `boolean` | Publication-state flag. |
| `publishedAt` | `Instant` | Optional publication timestamp. |
| `publishedByActorId` | `String` | Optional publication actor reference. |
| `createdAt` | `Instant` | Persistence-required creation timestamp. |

The constructor requires nonblank ID, dataset ID, schema hash and data hash and rejects `periodEnd < periodStart` when both period values exist.

### 55.3 Parent AnalyticsDataset integrity

`datasetId` is a mandatory same-module reference to the AnalyticsDataset subject.

The base schema requires `dataset_id NOT NULL`, and HRA-111 installs a validated same-module FK from:

```text
hidra_analytics_dataset_version.dataset_id
    -> hidra_analytics_dataset.id
```

Therefore parent-dataset existence is fail-closed at persistence and no additional correction is recorded for the reference itself.

### 55.4 Reproducibility semantics

The Analytics DDD explicitly identifies `schemaHash` and `dataHash` as reproducibility support.

The domain constructor already rejects blank hashes and persistence requires both fields, so domain and schema align on this requirement.

No repository evidence establishes a specific hash algorithm, fixed length, uniqueness rule, or relationship between the two hashes. HMSR-042 does not invent one.

### 55.5 Period semantics

The DDD rule is explicit:

```text
periodStart must be before or equal to periodEnd
```

The constructor enforces exactly that rule when both values are present by rejecting only `periodEnd.isBefore(periodStart)`.

The schema keeps both fields nullable, consistent with the absence of a rule requiring a period for every dataset version.

No additional duration, timezone, or contiguous-version-window policy is invented.

### 55.6 Publication immutability gap

The Analytics DDD explicitly states:

```text
published dataset version is immutable
```

The domain model currently exposes:

```java
public boolean immutableAfterPublication() {
    return published;
}
```

This reports the intended state but does not enforce immutability.

The application repository contract still exposes unrestricted:

```text
save(AnalyticsDatasetVersion model)
```

and current repository evidence shows no authoritative application service, repository adapter guard, optimistic/version check, database rule, or other fail-closed mechanism that prevents an already-published dataset version from being overwritten with changed schema hash, data hash, period, quality metadata, publication metadata, or other persisted state.

Therefore the explicit DDD invariant is documented but not enforced by the live write boundary.

HMSR-042 does not prescribe the implementation technique. A later production correction may enforce append-only publication, reject updates to published rows, split draft/publication commands, or use another architecture-consistent mechanism.

### 55.7 Publication metadata deliberately not over-specified

Although `publishedAt` and `publishedByActorId` accompany the `published` flag, the current Analytics DDD does not explicitly state that either field becomes mandatory whenever `published = true`.

HMSR-042 therefore does not invent:

- mandatory `publishedAt` for published versions;
- mandatory `publishedByActorId`;
- actor-status/authorization semantics;
- a database FK from Analytics to Identity.

The actor field remains a neutral cross-module reference.

### 55.8 Version, row-count and quality rules deliberately not invented

Current repository evidence does not explicitly define:

- `versionNumber > 0`;
- uniqueness of `(datasetId, versionNumber)`;
- monotonic version sequencing;
- nonnegative `rowCount`;
- an allowed `qualityScore` range;
- mandatory quality score before publication.

The schema contains no unique dataset/version-number constraint. HMSR-042 therefore records none of these as correction obligations merely because they may appear desirable.

### 55.9 Lineage semantics

The Analytics DDD states that every published dataset version must have lineage and that lineage is immutable after publication.

That requirement is owned jointly with `AnalyticsDatasetLineage`, not represented as a scalar field on AnalyticsDatasetVersion.

HMSR-042 records the publication immutability defect proven directly for this model but does not manufacture a direct lineage field or graph edge. Whether publication orchestration must transactionally verify lineage belongs to later reconciliation of the DatasetVersion/Lineage write workflow with explicit implementation authorization.

### 55.10 Domain/JPA/Flyway alignment

Domain and JPA agree on all 13 components and requiredness.

Persistence requires:

```text
id
dataset_id
version_number
schema_hash
data_hash
published
created_at
```

with nullable row count, period, quality score and publication metadata.

The parent dataset reference is protected by HRA-111. The current model also correctly keeps `createdAt` as persistence/audit metadata rather than inventing domain ordering against publication time.

The principal semantic mismatch is behavioral rather than structural: publication immutability is declared by DDD and observable from the model but not enforced on writes.

### 55.11 Required revision

One evidence-backed production correction obligation remains:

1. **Enforce immutability of published AnalyticsDatasetVersion state.** Once a dataset version is published, subsequent writes must not mutate its persisted release content/metadata contrary to the Analytics DDD. The production correction must choose an architecture-consistent fail-closed mechanism without rewriting this HMSR documentation task into implementation work.

HMSR-042 does not modify production Java, JPA, Flyway, application/API contracts, tests, analytics datasets, lineage, or provisioned data.

### 55.12 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, an AnalyticsDatasetVersion can underpin KPI calculation, trend analysis, simulation/AI preparation, digital-twin readiness and management decision support.

Once a release is published, reproducibility depends on the same version continuing to mean the same data/schema release. Allowing silent mutation after publication would undermine comparison, traceability, model reproducibility and audit confidence even if the parent dataset identity remains valid.

### 55.13 Review conclusion

**REVISE.** `AnalyticsDatasetVersion` has a coherent 13-field versioned-release model, correct Level-1 dependency on AnalyticsDataset, validated parent FK protection, mandatory reproducibility hashes and correctly enforced period ordering.

The target baseline cannot mark it APPROVED while the explicit DDD rule that a published dataset version is immutable is not enforced by the live write boundary.

HMS reconciliation must retain this immutability obligation until an explicitly authorized Analytics production correction resolves it or stronger repository evidence explicitly revises the governing Analytics DDD semantics.

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

- `f4eb3f9bdda85f30dc264470fc9b68090552c7fc` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:94](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L94)
- [docs/roadmap/model-semantic-review.md:237](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L237)
- [docs/roadmap/model-semantic-review.md:9262](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L9262)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:237 | HMSR-042 | 1 | analytics | AnalyticsDatasetVersion | — | analytics.AnalyticsDataset | 0 | 0 | 0 | REVISE | Completed | docs(model-review): review analytics AnalyticsDatasetVersion
doc/domain/SEMANTIC_DECISIONS.md:94 | HMSR-042 | [analytics.AnalyticsDatasetVersion](#analytics-analyticsdatasetversion) | REVISE | HMR-037
~~~~~~~~

<a id="hapi-353"></a>
## HAPI-353 — review analytics MetricValue

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-043

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review analytics MetricValue

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`, `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java`
- `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 56. HMSR-043 — analytics.MetricValue review

**Decision:** REVISE  
**Review code:** HMSR-043  
**Dependency level:** 1  
**Bounded context:** analytics  
**Confirmed upstream subject dependencies:** `analytics.MetricEvaluationRun` through mandatory `metricEvaluationRunId`  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject references:** 3 in the register are now semantically resolved as retained MetricDefinition, retained MetricDefinitionVersion, and neutral analytical scope/unit dependencies without manufacturing new HMS graph edges

### 56.1 Semantic role and ordering rationale

`MetricValue` stores one calculated Analytics metric result for a defined period and analytical scope.

The Analytics DDD is explicit that MetricValue is derived and rebuildable and must not become a source of operational truth. It therefore records calculation context and analytical output while the authoritative operational facts remain in their owning modules.

It is Level 1 because `metricEvaluationRunId` references the already-reviewed HMS subject `MetricEvaluationRun`.

### 56.2 Field semantics

| Field | Type | Reviewed meaning |
|---|---|---|
| `id` | `String` | Mandatory stable metric-value identity. |
| `metricEvaluationRunId` | `String` | Mandatory same-module reference to the evaluation run that produced the value. |
| `metricDefinitionId` | `String` | Mandatory same-module reference to retained MetricDefinition state. |
| `metricDefinitionVersionId` | `String` | Mandatory same-module reference to retained MetricDefinitionVersion state. |
| `scopeType` | `String` | Mandatory analytical-scope discriminator in final persistence. |
| `scopeId` | `String` | Optional neutral scope identity selected/interpreted by `scopeType`. |
| `periodStart` | `Instant` | Mandatory inclusive analytical period start. |
| `periodEnd` | `Instant` | Mandatory analytical period end. |
| `valueNumeric` | `BigDecimal` | Optional numeric result representation. |
| `valueText` | `String` | Optional text result representation. |
| `unitId` | `String` | Optional unit/value dependency. |
| `qualityStatus` | `AnalyticsQualityStatus` | Mandatory analytical quality state. |
| `calculatedAt` | `Instant` | Mandatory calculation timestamp. |

The canonical constructor already rejects blank ID, evaluation-run ID, metric-definition ID and metric-definition-version ID; requires both period endpoints, quality status and calculation time; and enforces `periodStart <= periodEnd`.

### 56.3 Evaluation-run and metric-definition integrity

The final persistence model requires all three Analytics references:

```text
metric_evaluation_run_id
metric_definition_id
metric_definition_version_id
```

HRA-111 installs validated same-module FKs for all three:

```text
metric_evaluation_run_id
    -> hidra_analytics_metric_evaluation_run.id

metric_definition_id
    -> hidra_analytics_metric_definition.id

metric_definition_version_id
    -> hidra_analytics_metric_definition_version.id
```

Therefore dangling references to those three Analytics identities are already prevented at persistence.

`MetricDefinition` and `MetricDefinitionVersion` are retained Analytics read/persistence models outside the 123 HMS subject set, so HMSR-043 strengthens the dependency classification without adding graph edges.

Current Analytics DDD evidence does not explicitly require HMSR-043 to prove parent consistency between `metricDefinitionId` and `metricDefinitionVersionId` on every MetricValue row. No composite-parent rule is invented.

### 56.4 Analytical scope semantics

The Analytics DDD establishes neutral scope references as the correct pattern for derived analytics.

For MetricValue, `scopeType + scopeId` carry that neutral analytical scope. The scope may identify topology, organization, product, or another module-defined analytical target without transferring ownership to Analytics.

No single concrete HMS target is therefore manufactured for `scopeId`, and no cross-module database FK is appropriate.

The final JPA/schema contract requires `scope_type NOT NULL` while `scope_id` remains nullable, which is consistent with the possibility of a scope type that does not require a concrete target ID.

### 56.5 Scope-type requiredness mismatch

The canonical MetricValue constructor currently validates the major Analytics references and period state but does **not** require nonblank `scopeType`.

It then normalizes blank/null `scopeType` to `null`.

The final JPA mapping and Flyway schema both require:

```text
scope_type NOT NULL
```

and the Analytics DDD consistently models neutral analytical scope through the `scopeType + scopeId` pattern.

Therefore the canonical domain can admit a MetricValue state that cannot satisfy the final persistence contract and has no discriminator with which to interpret its analytical scope.

This is an evidence-backed domain/persistence semantic mismatch.

### 56.6 Period semantics

The DDD field model and repository invariant classification support the implemented chronology:

```text
periodStart <= periodEnd
```

The domain enforces this directly, and both endpoints are mandatory in domain and persistence.

No duration, granularity, non-overlap, contiguous-period, or relation to `calculatedAt` is defined by current evidence, so HMSR-043 does not invent one.

### 56.7 Result representation deliberately not over-specified

MetricValue provides both:

```text
valueNumeric
valueText
```

Current Analytics DDD lists both fields but does not state that exactly one must be populated, that at least one must be populated, or that a metric definition's data type determines a mandatory representation rule at this model boundary.

HMSR-043 therefore does not invent XOR/non-null semantics.

Similarly, current evidence does not define a required numeric range, precision beyond persistence representation, or text normalization rule beyond the existing blank-to-null normalization.

### 56.8 Unit semantics

`unitId` is optional in both domain and persistence.

Analytics DDD uses unit references across metric/KPI structures, but the reviewed evidence does not establish a MetricValue-specific mandatory unit, one exact Analytics catalog family, or a direct HMS subject target.

HMSR-043 therefore classifies it conservatively as a value/catalog dependency and records no production correction merely because no FK exists.

### 56.9 Quality and rebuildability

`qualityStatus` is mandatory in domain and persistence and uses the Analytics-owned `AnalyticsQualityStatus` vocabulary.

`derivedAndRebuildable()` returns true, matching the DDD statement that MetricValue is derived/rebuildable and must not be treated as operational truth.

No current evidence defines additional quality-status-dependent value requirements or a mandatory source-watermark field on MetricValue itself.

### 56.10 Domain/JPA/Flyway alignment

Domain and JPA agree on all 13 components.

The required persistence fields are:

```text
id
metric_evaluation_run_id
metric_definition_id
metric_definition_version_id
scope_type
period_start
period_end
quality_status
calculated_at
```

The domain already enforces all of these except `scopeType`.

The three Analytics reference FKs are protected; period chronology is enforced; optional `scopeId`, value representations and `unitId` remain nullable consistently.

The sole evidence-backed correction obligation for this review is therefore the missing authoritative nonblank scope-type invariant.

### 56.11 Required revision

One evidence-backed production correction obligation remains:

1. **Require nonblank MetricValue scopeType at the authoritative domain/application boundary.** The canonical model must not normalize `scopeType` to null when final persistence requires it and the Analytics scope-reference pattern depends on it to interpret `scopeId`.

HMSR-043 does not modify production Java, JPA, Flyway, application/API contracts, tests, metric values, metric definitions, evaluation runs, or provisioned data.

### 56.12 SONATRACH/TRC operational interpretation

For SONATRACH/TRC, MetricValue can represent derived performance, quality, planning-vs-actual, reliability, integrity, custody, HSE or risk-related analytical results across pipeline systems, facilities, organizational scopes or other governed subjects.

The scope discriminator is operationally important because the same scalar identifier format can exist in multiple bounded contexts. A calculated value without a usable `scopeType` cannot be interpreted reliably even when its metric definition and evaluation run are valid.

The model remains analytical evidence only; it must not replace telemetry, planning, monitoring, risk or other source-of-truth records.

### 56.13 Review conclusion

**REVISE.** `MetricValue` has a coherent 13-field derived-result model, correct Level-1 dependency on MetricEvaluationRun, protected same-module references to evaluation run/definition/version, correctly enforced period ordering, mandatory quality/calculation metadata, and proper neutral-scope architecture.

The target baseline cannot mark it APPROVED while the canonical domain permits a null/blank `scopeType` that final persistence rejects and that the neutral analytical-scope pattern needs for interpretation.

HMS reconciliation must retain this scope-type requiredness obligation until an explicitly authorized Analytics production correction resolves it or stronger repository evidence explicitly revises the final Analytics scope contract.

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

- `4571c2f631d4cc7feece8f7f6256843eb6c6d436` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:95](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L95)
- [docs/roadmap/model-semantic-review.md:238](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L238)
- [docs/roadmap/model-semantic-review.md:9444](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L9444)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:238 | HMSR-043 | 1 | analytics | MetricValue | — | analytics.MetricEvaluationRun | 0 | 0 | 3 | REVISE | Completed | docs(model-review): review analytics MetricValue
doc/domain/SEMANTIC_DECISIONS.md:95 | HMSR-043 | [analytics.MetricValue](#analytics-metricvalue) | REVISE | HMR-038
~~~~~~~~

<a id="hapi-367"></a>
## HAPI-367 — review reporting ReportRequest

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-057

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review reporting ReportRequest

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 70. HMSR-057 — reporting.ReportRequest review

**Decision:** REVISE  
**Review code:** HMSR-057  
**Dependency level:** 2  
**Bounded context:** reporting  
**Confirmed upstream subject dependencies:** `reporting.ReportDefinition`, optional `organization.OrganizationUnit`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 2

### 70.1 Semantic role

`ReportRequest` is the Reporting-owned user/system request to generate a formal report. It records the selected ReportDefinition, requester identity/snapshots, optional Organization scope, request time/purpose, lifecycle status, correlation identity and optional workflow/approval reference.

Reporting owns the request lifecycle but not Identity, Organization or Workflow business state.

### 70.2 ReportDefinition integrity and request eligibility

`reportDefinitionId` is mandatory and HRA-111 protects row existence with a validated same-module FK to `hidra_reporting_report_definition(id)`.

The Reporting DDD separately states:

```text
inactive definitions cannot be used for new report requests
```

`ReportingApplicationService.requestReport()` currently constructs a SUBMITTED ReportRequest directly from the command and saves it without loading the referenced ReportDefinition.

Therefore the FK proves existence but does not enforce the explicit active-definition eligibility rule.

### 70.3 Restricted-report access rule

The ReportRequest DDD explicitly requires:

```text
restricted reports require access validation
```

ReportDefinition carries the `restricted` flag and Reporting has an access-policy model, but `requestReport()` does not load the definition or invoke any access-policy/authorization boundary before persisting the request.

Therefore a restricted report may currently be requested without demonstrated access validation.

### 70.4 Approval-required queueing rule

The ReportRequest DDD explicitly states:

```text
reports requiring approval cannot be queued before workflow approval
```

ReportDefinition carries `requiresApproval`; ReportRequest carries status and optional `workflowReferenceId`.

`queueReportRun()` currently constructs and saves a QUEUED ReportRun directly from command IDs. It does not load the ReportRequest, inspect its status, inspect the definition's `requiresApproval`, or verify workflow approval.

Therefore an approval-required request can currently be queued without demonstrated approval.

HMSR-057 does not invent one concrete WorkflowInstance target for `workflowReferenceId`; the field remains a neutral approval/workflow reference until stronger repository evidence resolves its exact contract.

### 70.5 Organization boundary

`organizationUnitId` is optional and unambiguously references `organization.OrganizationUnit`, accompanied by a name snapshot.

`requestReport()` persists a populated OrganizationUnit ID without resolving it through an Organization-owned lookup contract.

No cross-module DB FK should be introduced. When populated, the scope/responsibility identity should fail closed through an Organization-owned application/lookup boundary.

Current evidence does not make Organization scope mandatory for every report request.

### 70.6 Requester snapshots

The DDD states that the request actor snapshot must be preserved. The model stores username, display-name and role-code snapshots alongside mandatory `requestedByActorId`.

Current evidence does not specify which individual snapshot fields are mandatory in every request, and persistence keeps them nullable. HMSR-057 therefore does not invent a field-level non-null rule.

Production corrections for access/approval orchestration must preserve whatever requester snapshots are supplied rather than replacing them with mutable current Identity state.

### 70.7 Status and timestamps

The domain requires non-null `requestedAt` and `ReportRequestStatus`; the active request path initializes new requests as `SUBMITTED`.

Current Reporting DDD does not provide a complete transition matrix for DRAFT/SUBMITTED/APPROVED/REJECTED/QUEUED/CANCELLED, so HMSR-057 does not invent additional status transitions beyond the explicit approval-before-queue rule.

No additional createdAt/updatedAt ordering rule is approved by the repository invariant classification.

### 70.8 Required revisions

Four evidence-backed production correction obligations remain:

1. **Reject new requests against inactive ReportDefinition rows.** The authoritative request path must load/validate definition eligibility before creating ReportRequest.

2. **Enforce access validation for restricted report definitions before request creation.** Use Reporting/Identity access-policy contracts rather than bypassing authorization.

3. **Prevent queueing approval-required requests before workflow approval.** The queue path must validate the ReportRequest/ReportDefinition approval state through the appropriate Workflow contract before creating a queued ReportRun.

4. **Validate populated `organizationUnitId` through the Organization owner boundary.** Do not add a cross-module database FK.

HMSR-057 does not modify production Java, JPA, Flyway, API/application contracts, tests, report requests, definitions, access policies, Workflow data, Organization data, or provisioned data.

### 70.9 Review conclusion

**REVISE.** `ReportRequest` has a coherent 15-field Reporting request model, a protected mandatory ReportDefinition identity, explicit requester/status state, optional Organization scope, and neutral workflow/correlation references.

The target baseline cannot mark it APPROVED while inactive/restricted definition eligibility is not enforced, approval-required requests can be queued without demonstrated workflow approval, and populated Organization scope can dangle.

HMS reconciliation must retain these obligations until explicitly authorized Reporting production corrections resolve them or stronger repository evidence changes the governing Reporting DDD.

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

- `2caae24fca38c37b355f71bd7bb638d045ebb611` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:109](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L109)
- [docs/roadmap/model-semantic-review.md:252](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L252)
- [docs/roadmap/model-semantic-review.md:11788](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L11788)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:252 | HMSR-057 | 2 | reporting | ReportRequest | — | organization.OrganizationUnit, reporting.ReportDefinition | 1 | 2 | 1 | REVISE | Completed | docs(model-review): review reporting ReportRequest
doc/domain/SEMANTIC_DECISIONS.md:109 | HMSR-057 | [reporting.ReportRequest](#reporting-reportrequest) | REVISE | HMR-048
~~~~~~~~

<a id="hapi-378"></a>
## HAPI-378 — review reporting ReportRun

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-068

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review reporting ReportRun

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/QueueReportRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/QueueReportRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 81. HMSR-068 — reporting.ReportRun review

**Decision:** REVISE  
**Review code:** HMSR-068  
**Dependency level:** 3  
**Bounded context:** reporting  
**Confirmed upstream subject dependencies:** `reporting.ReportDefinition`, `reporting.ReportRequest`  
**Confirmed direct HMS subject dependents:** 1  
**Transitive HMS subject dependents:** 1

### 81.1 Semantic role

`ReportRun` is the Reporting-owned execution attempt for a report request. It freezes the request/definition/template-version identity used for execution and carries run mode, lifecycle status, queue/start/terminal timestamps, failure information, output metrics and correlation metadata.

Reporting owns the run lifecycle and reproducibility envelope. Source business modules continue to own the underlying operational truth.

### 81.2 Report-request integrity defect

`reportRequestId` is mandatory and semantically references `reporting.ReportRequest`.

The live HRA-111 migration currently contains:

```text
hidra_reporting_run.report_request_id
    -> hidra_reporting_catalog_entry.id
```

This is not a catalog reference. It is a direct same-module domain reference to ReportRequest.

The current Flyway target is therefore semantically wrong and must be corrected so new writes and existing data are validated against:

```text
hidra_reporting_request.id
```

This is a concrete production-integrity obligation, not a speculative modeling preference.

### 81.3 Request/definition consistency

`reportDefinitionId` is mandatory and HRA-111 correctly protects:

```text
hidra_reporting_run.report_definition_id
    -> hidra_reporting_report_definition.id
```

`ReportRequest` itself also carries a mandatory `reportDefinitionId`, protected against the same definition table.

However, `ReportingApplicationService.queueReportRun()` accepts `reportRequestId` and `reportDefinitionId` independently from `QueueReportRunCommand` and does not load the request before saving the run.

Independent row existence is insufficient. The authoritative queue path must enforce:

```text
run.reportDefinitionId == request.reportDefinitionId
```

before creating the run.

### 81.4 Exact template-version lineage

`templateVersionId` is mandatory and HRA-111 correctly protects:

```text
hidra_reporting_run.template_version_id
    -> hidra_reporting_report_template_version.id
```

`ReportTemplateVersion` belongs to `ReportTemplate`, and `ReportTemplate` belongs to `ReportDefinition`; both relationships are same-module and database-protected.

The Reporting DDD requires report runs to preserve the exact template version used and identifies that version as part of the report's reproducibility contract.

The current queue service does not resolve the supplied template version or prove that its template belongs to the selected report definition.

The authoritative queue path must therefore fail closed unless the selected template-version lineage resolves to the same ReportDefinition as the request/run.

HMSR-068 does not require every historical run to reference a currently ACTIVE template version; reproducibility may legitimately preserve a version that later becomes RETIRED.

### 81.5 Request queue eligibility and approval boundary

The Reporting DDD defines request lifecycle/approval semantics and states that reports requiring approval cannot be queued before workflow approval.

HMSR-057 already recorded the production obligation to prevent approval-required requests from reaching queue state before approval.

`queueReportRun()` currently does not load or inspect the request at all; therefore it cannot enforce that previously recorded request eligibility rule before creating a run.

HMSR-068 does not duplicate or broaden HMSR-057's approval semantics. It records the run-side requirement that an authoritative queue operation must consume the request's validated queueable state rather than bypass it.

### 81.6 Required parameters before queueing

The Reporting DDD explicitly states:

```text
required parameters must be provided before queueing a report run
```

The live queue service does not resolve ReportParameterDefinition or ReportParameterValue data before creating a QUEUED run, and no application port for this validation is used in the active path.

Therefore the authoritative queue boundary must validate the request's required parameter set before persistence of a QUEUED ReportRun.

HMSR-068 does not invent new parameter types or value-shape rules beyond the existing Reporting DDD.

### 81.7 Run lifecycle invariants

`ReportRunStatus` is a genuine lifecycle enum:

```text
QUEUED
RUNNING
COMPLETED
FAILED
CANCELLED
EXPIRED
```

The DDD explicitly requires:

```text
completed runs must have completedAt
failed runs must have failureReason
```

The current domain record validates required identity/queue fields but permits construction of `COMPLETED` without `completedAt` and `FAILED` without `failureReason`.

The active application service only creates QUEUED runs, so this is not evidence of a current queue-path violation; it is nevertheless a semantic gap in the authoritative run state model/transition boundary.

Future authorized reconciliation must enforce those explicit DDD terminal-state invariants at the domain and/or lifecycle application boundary.

HMSR-068 does not invent additional timestamp ordering or failure-field rules not stated by the DDD.

### 81.8 Run mode and metrics

`ReportRunMode` is a technical execution enum used to distinguish manual, scheduled, system-triggered and workflow-triggered runs.

`recordCount`, `outputCount` and `executionDurationMs` are optional execution-result metrics. Current Reporting DDD does not specify additional bounds or mandatory presence rules for these fields.

HMSR-068 therefore does not invent numeric constraints.

### 81.9 Reproducibility and source ownership

The Reporting DDD requires reproducibility through exact definition/template version, request parameters, input snapshots, source contract versions and generated-artifact evidence.

The ReportRun model correctly anchors request, definition and exact template-version identity. Other reproducibility components live in supporting Reporting entities and are not collapsed into ReportRun.

Reporting must continue to consume source modules through read/application contracts and must not modify operational source state.

### 81.10 Required revisions

Five evidence-backed obligations remain:

1. **Correct the erroneous HRA-111 foreign key for `reportRequestId`.** `hidra_reporting_run.report_request_id` must reference `hidra_reporting_request.id`, not `hidra_reporting_catalog_entry.id`.

2. **Enforce request/definition consistency before queueing.** The selected ReportRequest must belong to the same ReportDefinition recorded by the run.

3. **Enforce template-version lineage before queueing.** The exact ReportTemplateVersion must resolve through its ReportTemplate to the same ReportDefinition as the request/run.

4. **Queue only an eligible request with all required parameters present.** Reuse the previously recorded HMSR-057 request approval/access semantics; do not bypass them from the run-queue path.

5. **Enforce the explicit terminal-state invariants.** COMPLETED requires `completedAt`; FAILED requires `failureReason` at the authoritative domain/transition boundary.

HMSR-068 does not modify production Java, JPA, Flyway, API/application contracts, tests, Reporting definitions/templates/requests/parameters, workflow data, or provisioned data.

### 81.11 Review conclusion

**REVISE.** `ReportRun` is a coherent 17-field Reporting-owned execution model whose definition and exact template-version references are already protected against dangling rows and whose lifecycle/run-mode enums are appropriate.

The target baseline cannot mark it APPROVED while the live Flyway constraint for `reportRequestId` points to the wrong table, queueing does not prove request/definition/template lineage, required request parameters and queue eligibility are bypassed, and explicit DDD terminal-state invariants are not enforced by the run model/lifecycle boundary.

HMS reconciliation must retain these obligations until explicitly authorized Reporting production corrections resolve them or stronger repository evidence changes the governing Reporting DDD.

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

- `e4eab24b85ec43d1a4ddb8b692d6fd6af4c0da2e` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:120](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L120)
- [docs/roadmap/model-semantic-review.md:263](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L263)
- [docs/roadmap/model-semantic-review.md:13199](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L13199)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:263 | HMSR-068 | 3 | reporting | ReportRun | — | reporting.ReportDefinition, reporting.ReportRequest | 1 | 1 | 0 | REVISE | Completed | docs(model-review): review reporting ReportRun
doc/domain/SEMANTIC_DECISIONS.md:120 | HMSR-068 | [reporting.ReportRun](#reporting-reportrun) | REVISE | HMR-057
~~~~~~~~

<a id="hapi-420"></a>
## HAPI-420 — review reporting ReportOutputArtifact

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HMSR-110

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

review reporting ReportOutputArtifact

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportArtifactType.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportFormat.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. It is a documentation/review/preflight outcome; broader application functionality remains separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`, `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportArtifactType.java`.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/model-semantic-review.md`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportArtifactType.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportFormat.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.


**Pinned original bounded record — historical applicability:**

~~~~~~~~text
## 123. HMSR-110 — reporting.ReportOutputArtifact review

**Decision:** REVISE  
**Review code:** HMSR-110  
**Dependency level:** 5  
**Bounded context:** reporting  
**Confirmed upstream subject dependencies:** mandatory `reporting.ReportRun`; optional Documents-owned `documents.Document` and `documents.DocumentStorageObject` references  
**Confirmed direct HMS subject dependents:** 0  
**Transitive HMS subject dependents:** 0  
**Unresolved/non-subject reference count:** 2

### 123.1 Semantic role

`ReportOutputArtifact` is the Reporting-owned generated file/export record for a report execution. Reporting owns generation metadata and traceability; Documents owns reusable document metadata and storage-object lifecycle.

The reviewed target contains 13 fields: report-run identity, artifact/format classification, file metadata, optional Documents references, checksum/size, generation/expiry time, and creation time.

### 123.2 Field and enum alignment

The live domain record, JPA entity, persistence mapper, command, DTO path, and Flyway table agree on the 13-field shape.

`ReportFormat` contains `PDF`, `XLSX`, `CSV`, `JSON`, `HTML`, `DOCX`, and `ZIP`, exactly matching the Reporting DDD format vocabulary.

`ReportArtifactType` remains a Reporting lifecycle/output discriminator (`PRIMARY_REPORT`, `ATTACHMENT`, `DATA_EXPORT`, `ARCHIVE`, `PREVIEW`). Current repository evidence does not require replacing it with a catalog.

### 123.3 Same-module ReportRun reference defect

`reportRunId` semantically references `reporting.ReportRun` and the DDD defines every artifact as the output of a report run.

However, the live HRA-111 migration currently installs:

```text
hidra_reporting_output_artifact.report_run_id
  -> hidra_reporting_catalog_entry.id
```

instead of:

```text
hidra_reporting_output_artifact.report_run_id
  -> hidra_reporting_run.id
```

This is a material reference-integrity defect: a valid ReportRun ID can fail while an unrelated Reporting catalog-entry ID can satisfy the FK.

Future production reconciliation must replace/correct this constraint with the proper same-module FK to `hidra_reporting_run(id)`.

### 123.4 Application fail-closed ReportRun validation

The authoritative `generateReportArtifact(...)` path constructs and saves the artifact directly. It does not load the referenced ReportRun through `ReportRunRepositoryPort` before persistence.

Given `reportRunId` is a mandatory same-module business reference, future production reconciliation must fail closed on an unknown ReportRun at the application boundary in addition to correcting the database FK.

The current DDD does not state that artifacts may only be registered after the run reaches `COMPLETED`; it instead says completed runs should have at least one artifact unless configured as data-only. HMSR-110 therefore does not invent a completed-only artifact creation rule.

### 123.5 Storage/document reference invariant

The Reporting DDD explicitly states:

```text
artifact must reference storage or document metadata
```

The live domain model exposes `reproducibleArtifact()`, which returns true only when a checksum exists and at least one of `storageObjectReferenceId` or `documentReferenceId` is present.

However, neither the record constructor nor `ReportingApplicationService.generateReportArtifact(...)` enforces that predicate. Both Documents references can therefore be null and the artifact can still be persisted.

Future production reconciliation must require at least one normalized Documents reference before persistence.

### 123.6 Documents bounded-context boundary

`storageObjectReferenceId` and `documentReferenceId` are optional cross-module references owned by Documents. They must not become Reporting-to-Documents database foreign keys.

When a supplied reference must be proven live/usable, Reporting should validate it through Documents-owned exported lookup/application contracts. Reporting must not reach directly into Documents tables or repositories.

The two references are semantically distinct: `storageObjectReferenceId` targets Documents storage-object metadata, while `documentReferenceId` targets reusable Document metadata.

### 123.7 Checksum semantics

The DDD requires checksum for formal reports. The live domain model is stricter and requires a nonblank checksum for every `ReportOutputArtifact`.

Current repository evidence does not establish that this stronger rule is invalid, and every persisted artifact column is also non-null. HMSR-110 therefore records no checksum relaxation obligation.

### 123.8 File metadata and audit timestamps

`fileName` and `mimeType` are database-required, while `generatedAt` is explicitly fail-fast validated by the domain model and the application path supplies both `generatedAt` and `createdAt` at generation time.

Under the repository invariant-classification rules, HMSR-110 does not invent additional domain validation merely from database `NOT NULL` declarations where the Reporting DDD does not state a stronger invariant.

### 123.9 Required revisions

Three evidence-backed production obligations remain:

1. **Correct HRA-111 so `report_run_id` references `hidra_reporting_run(id)`, not `hidra_reporting_catalog_entry(id)`.**
2. **Make artifact generation fail closed when the referenced ReportRun does not exist.**
3. **Enforce the DDD invariant that every artifact references at least one Documents storage object or Document metadata record; validate supplied cross-module references through Documents-owned contracts without adding cross-module DB FKs.**

HMSR-110 does not modify production Java, JPA, Flyway, API/application contracts, tests, Reporting data, Documents data, or provisioned data.

### 123.10 Review conclusion

**REVISE.** The ownership, 13-field model shape, format vocabulary, checksum behavior, and Documents boundary are broadly aligned, but the live ReportRun FK is materially incorrect and the mandatory storage/document-reference invariant is not enforced.

HMS reconciliation must retain these obligations until explicitly authorized Reporting production work resolves them or stronger repository evidence changes the governing contract.

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

- `88ee575826937d7c764c6aebc4883bc54bb00e12` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [doc/domain/SEMANTIC_DECISIONS.md:162](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/domain/SEMANTIC_DECISIONS.md#L162)
- [docs/roadmap/model-semantic-review.md:305](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L305)
- [docs/roadmap/model-semantic-review.md:18895](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/model-semantic-review.md#L18895)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Completed semantic review is documentation work; APPROVED/REVISE is the historical model verdict, not application completeness. Current implementation/planning states above are independent. Doc/preflight completion is not completion of the associated application workflow.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/model-semantic-review.md:305 | HMSR-110 | 5 | reporting | ReportOutputArtifact | — | documents.Document, documents.DocumentStorageObject, reporting.ReportRun | 0 | 0 | 2 | REVISE | Completed | docs(model-review): review reporting ReportOutputArtifact
doc/domain/SEMANTIC_DECISIONS.md:162 | HMSR-110 | [reporting.ReportOutputArtifact](#reporting-reportoutputartifact) | REVISE | HMR-093
~~~~~~~~

<a id="hapi-511"></a>
## HAPI-511 — reconstruct operational network state

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HPR-P25-005

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-510

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Reconstruct a versioned time-coherent network state using approved physical methods; distinguish measured, estimated and unknown values and observability; retain input/configuration lineage and uncertainty.

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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

Qualify actual computation/rendering/provider execution and reproducible outputs rather than treating run/artifact registration as execution. The exact bounded remaining delivery is: Reconstruct a versioned time-coherent network state using approved physical methods; distinguish measured, estimated and unknown values and observability; retain input/configuration lineage and uncertainty..

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/controller/SpringAnalyticsController.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`

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

Canonical prerequisites: HAPI-510. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13651](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13651)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6089](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6089)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:856](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L856)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15391](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15391)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:856 | HPR-P25-005 | PENDING | Analytics / Topology / Telemetry | Code/Test | Reconstruct a versioned time-coherent network state using approved physical methods; distinguish measured, estimated and unknown values and observability; retain input/configuration lineage and uncertainty. | feat(analytics): reconstruct operational network state | HPR-P25-004; approved estimator/model contract
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15391 | HPR-P25-005 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Analytics / Topology / Telemetry | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6089 | HPR-P25-005 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13651 | HPR-P25-005 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Analytics / Topology / Telemetry | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
~~~~~~~~

<a id="hapi-525"></a>
## HAPI-525 — evaluate observed decision outcomes

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HPR-P25-013

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-510, HAPI-524

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Compare post-action telemetry with predicted outcomes in an approved observation window; retain actual action, confounders, confidence and attribution limits; detect unexpected effects elsewhere.

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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

Qualify actual computation/rendering/provider execution and reproducible outputs rather than treating run/artifact registration as execution. The exact bounded remaining delivery is: Compare post-action telemetry with predicted outcomes in an approved observation window; retain actual action, confounders, confidence and attribution limits; detect unexpected effects elsewhere..

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/controller/SpringAnalyticsController.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`

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

Canonical prerequisites: HAPI-510, HAPI-524. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13665](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13665)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6104](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6104)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:864](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L864)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15405](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15405)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:864 | HPR-P25-013 | PENDING | Analytics / Telemetry / Audit | Code/Test | Compare post-action telemetry with predicted outcomes in an approved observation window; retain actual action, confounders, confidence and attribution limits; detect unexpected effects elsewhere. | feat(analytics): evaluate observed decision outcomes | HPR-P25-012; HPR-P25-004
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15405 | HPR-P25-013 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Analytics / Telemetry / Audit | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6104 | HPR-P25-013 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13665 | HPR-P25-013 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Analytics / Telemetry / Audit | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
~~~~~~~~

<a id="hapi-526"></a>
## HAPI-526 — govern learning from operational outcomes

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HPR-P25-014

Priority: P3

Implementation Status: NOT_VERIFIABLE

Planning Status: DEFERRED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-525

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Build provenance-controlled learning datasets from actual actions/outcomes; evaluate proposed detector, calibration or recommendation updates offline, version them and require validated promotion/rollback without weakening physical or safety constraints.

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

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

Qualify actual computation/rendering/provider execution and reproducible outputs rather than treating run/artifact registration as execution. The exact bounded remaining delivery is: Build provenance-controlled learning datasets from actual actions/outcomes; evaluate proposed detector, calibration or recommendation updates offline, version them and require validated promotion/rollback without weakening physical or safety constraints..

### 4. Functional Requirements

Independent cases A–G; mixed parent not closed

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java`, `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY:** DISCOVERY_REQUIRED for implementation; no executable source allowlist inferred from this historical or integration scope.

**PRESERVE / REUSE — current matched artifacts:**

- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/controller/SpringAnalyticsController.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsDatasetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/AnalyticsProjectionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/in/MetricEvaluationUseCase.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/AnalyticsDatasetVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/DocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/port/out/RiskAssessmentLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/analytics/application/service/AnalyticsApplicationService.java`

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

Canonical prerequisites: HAPI-525. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

### 13. Deliverables

The bounded objective, exact owner contracts/artifacts, meaningful verification and source-linked acceptance record. For implementation, deliver only the admitted CREATE/MODIFY set, public API evidence where required, fresh-schema proof where applicable and preserved historical behavior.

### 14. Evidence and History

Source applicability: `e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8`; exact-head Full #713 / Fast #41 / Docs #358 and raw report verification as described in the baseline. A cumulative passing run does not invent an unlocated original task commit.

- Independent original implementation commit identity was not matched for this bounded record; do not invent one.

Pinned task-definition/status/history references:

- [doc/model-remediation/RECONCILIATION.md:13666](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/model-remediation/RECONCILIATION.md#L13666)
- [doc/roadmap/ULTIMATE_ROADMAP.md:6105](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/ULTIMATE_ROADMAP.md#L6105)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:865](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L865)
- [doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15406](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md#L15406)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

Original bounded record status: DEFERRED / retained incomplete mixed parent.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:865 | HPR-P25-014 | PENDING | Analytics / Simulation | Code/Test/Doc | Build provenance-controlled learning datasets from actual actions/outcomes; evaluate proposed detector, calibration or recommendation updates offline, version them and require validated promotion/rollback without weakening physical or safety constraints. | feat(analytics): govern learning from operational outcomes | HPR-P25-013; approved learning objective/evaluation policy
doc/roadmap/history/ULTIMATE_ROADMAP_HISTORY.md:15406 | HPR-P25-014 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Analytics / Simulation | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
doc/roadmap/ULTIMATE_ROADMAP.md:6105 | HPR-P25-014 | DEFERRED / retained incomplete mixed parent | Independent engineering/monitoring share mapped 052–058; original simulator remainder not closed
doc/model-remediation/RECONCILIATION.md:13666 | HPR-P25-014 | PENDING | FOUNDATION_SCOPE_REFINEMENT | Analytics / Simulation | Independent successor/design 029; simulator-dependent parent edges retained for deferred scope | Independent source data, history, monitoring/action or foundation acceptance | Original simulation input/execution, prediction, calibration or numerical obligations | Independent cases A–G; mixed parent not closed | NOT SELECTED | 029 separately selected scope separation
~~~~~~~~

<a id="hapi-653"></a>
## HAPI-653 — simplify boundary dto mapping

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: HRA-072

Priority: P2

Implementation Status: VERIFIED

Planning Status: COMPLETED

Owning Module: `analytics` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: —

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Generate the eight HRA-070-approved exact analytics request/command and summary/response mappings.

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/mapper/AnalyticsGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/mapper/AnalyticsRestMapper.java`
- `src/test/java/dz/sh/hidra/modules/analytics/api/rest/mapper/AnalyticsRestMapperTest.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

No successor implementation is selected for this completed bounded objective. Preserve accepted source behavior; broader phase completion and SITE gates remain separate.

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `analytics`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Outbound ports: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Application services: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE / MODIFY / DELETE:** none selected. Preserve the completed or superseded bounded outcome.

**PRESERVE / REUSE — current matched artifacts:**

- `docs/roadmap/repository-remediation.md`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/mapper/AnalyticsGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/analytics/api/rest/mapper/AnalyticsRestMapper.java`
- `src/test/java/dz/sh/hidra/modules/analytics/api/rest/mapper/AnalyticsRestMapperTest.java`

Historical-only/deleted proposed paths in the original record are not presented as current files. Recover replacements only if a concrete regression is established.

### 7. API Contracts

Reuse only actual owner operations from the runtime contract inventory below. DISCOVERY_REQUIRED for any new operation/schema: specify explicit request/response DTOs, field validation/units, 400 malformed/invalid input, 401 unauthenticated, non-disclosing 403/404 policy, 409 stale version/idempotency conflict, applicable availability errors and bounded pagination. Publish exact-SHA OpenAPI and verify consumer compatibility; never expose persistence entities.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- `src/test/java/dz/sh/hidra/modules/analytics/api/rest/mapper/AnalyticsRestMapperTest.java` — exact-head oracle suite 2 cases, zero failures/errors/skips.

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

- `335d6f631630c79e4595aaadb604cf61ae40bd49` — exact registered-message commit; surviving paths matched to current tree.

Pinned task-definition/status/history references:

- [docs/roadmap/repository-remediation.md:143](https://github.com/CHOUABBIA-AMINE/HidraAPI/blob/e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8/docs/roadmap/repository-remediation.md#L143)

### 15. Current Reconciliation Record

Initial canonical adoption, 2026-10-16: Historical task retained independently; objective and record applicability are bounded by the pinned source. Current implementation/planning states above are independent.

No single stronger current disposition was independently extracted; original recorded evidence retains its dated applicability.

Future discoveries, refined exact file scope, accepted tests, blockers and decisions belong here. Retain prior evidence; synchronize only status fields into ROADMAP/PHASES. Newly standalone work stays PENDING_BASELINE_APPROVAL until authorized.

**Original task rows — quoted historical evidence, not competing current statuses:**

~~~~~~~~text
docs/roadmap/repository-remediation.md:143 | HRA-072 | refactor(analytics): simplify boundary dto mapping | Generate the eight HRA-070-approved exact analytics request/command and summary/response mappings. | All analytics exact-pair mappings use strict compile-time generation; existing static mapper API and wire/application types remain unchanged; focused equality tests cover all eight mappings. | HRA-071 | **Completed** — moved all four exact analytics Request→Command and all four SummaryDto→Response mappings behind `AnalyticsGeneratedRestMapper`, preserved the static `AnalyticsRestMapper` facade, and added focused equality tests for every approved pair.
~~~~~~~~

<a id="hapi-706"></a>
## HAPI-706 — Execute reproducible report rendering and artifact completion

Phase: HPS-017 — Analytics and Reporting

Legacy IDs: None — initial registration

Priority: P2

Implementation Status: PARTIAL

Planning Status: BLOCKED

Owning Module: `reporting` (actual source owner; package modules are not separate Maven artifacts)

Dependencies: HAPI-604

Cross-Repository Dependencies: None independently verified for this bounded task.

### 1. Objective

Execute an admitted report template against an exact authorized input revision, persist immutable output content through Documents, and complete or fail the existing run with reproducible provenance and idempotent recovery.

### 2. Existing Implementation

Dataset/projection/metric/insight records and definition/request/run/artifact lineage contracts exist. These are phase context, not automatic completion evidence for this task. Task-specific current artifacts were matched to the exact tree:

- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/controller/SpringReportingController.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/RequestReportUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDocumentPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportWorkflowPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`

No current file is invented to make a historical allowlist appear complete. Artifact presence and a tested narrow method do not establish the whole business workflow.

### 3. Missing or Incomplete Capabilities

Qualify actual computation/rendering/provider execution and reproducible outputs rather than treating run/artifact registration as execution. The exact bounded remaining delivery is: Execute an admitted report template against an exact authorized input revision, persist immutable output content through Documents, and complete or fail the existing run with reproducible provenance and idempotent recovery..

### 4. Functional Requirements

Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.

Preserve explicit lifecycle, required values, invalid-reference rejection, supported catalog families and uncertainty semantics from the original bounded record. Optional fields are not silently defaulted into engineering facts. A follow-on capability never changes the meaning of a completed preflight.

### 5. Architecture and Domain Design

Bounded Context: `reporting`; primary phase owns this obligation. No implemented LLM agent or automatic report synthesis was established by this audit. Aggregate/invariant ownership follows the existing domain artifacts below; proposed aggregates must not duplicate another owner. Domain remains pure Java; application coordinates authorization/transactions through ports; REST and persistence/integration remain adapters.

- Domain ownership/invariants: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.
- Inbound ports: `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`, `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`, `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/RequestReportUseCase.java`.
- Outbound ports: `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDocumentPort.java`, `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`, `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportWorkflowPort.java`.
- Application services: `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`.
- Primary/secondary adapters: DISCOVERY_REQUIRED for this task; reuse the exact owner inventory below before adding a proposed contract.

### 6. Exact File-Level Execution Plan

**CREATE — all filenames PROPOSED, not yet executable allowlist:**

- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRendererPort.java` — render exact approved template/input with bounded output and reproducible diagnostics.
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReproducibleReportExecutionApplicationService.java` — reuse existing request/run lineage, bind input revisions, execute idempotently and record terminal output.
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/rendering/ReportRendererAdapter.java` — PROPOSED selected renderer implementation; rendering format/engine decision DISCOVERY_REQUIRED.

**MODIFY — existing verified paths, if required by the admitted package:**

- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` — add only approved exported contract enforcement if the feature introduces an actual cross-owner boundary.

- `.github/ci-test-inventory.json` — register actual new test classes/cases/source hashes; preserve existing lanes and fallback.

**DELETE:** none.

**PRESERVE / REUSE:**

- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/controller/SpringReportingController.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/CreateReportDefinitionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/RequestReportUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportDocumentPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportWorkflowPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`

**DISCOVERY_REQUIRED:** close request/response DTO filenames, actual bootstrap wiring and additive schema migration filename after checking live ownership, current migration inventory and writer order. Proposed source types require their focused invariant/application/API/PostgreSQL tests under the corresponding existing module test root. Reuse an existing equivalent type instead of creating a duplicate.

### 7. API Contracts

**PROPOSED, not existing:** `POST /api/v1/reporting/runs/{id}/executions` for idempotent template execution from exact input revisions, Documents output persistence and atomic terminal lineage. Exact request/response DTO schemas, pagination/replay/idempotency fields and per-action effective grants require source-backed admission. Preserve all existing module operations in the runtime API inventory below.

### 8. Persistence and Transactions

Owner schema and existing mapped domain/persistence types are preserved. Flyway history is immutable; any new database requirement uses an additive migration whose actual filename/version remains DISCOVERY_REQUIRED until the live chain is checked. Enforce meaningful owned references, catalog family, immutable revision/digest, expected-version/uniqueness and concurrent-writer guarantees in the actual database as appropriate. Do not impose cross-module foreign aggregates or assume application pre-checks prevent races. PostgreSQL is current; MySQL equivalence is not established. Completed documentation work adds no schema requirement. Actual transaction/lock/time semantics in the original source record take precedence over this common preservation rule.

### 9. Security

Authenticate through the actual configured chain; authorize before revealing protected existence, count, history, export or payload. Preserve company/resource scope, current effective responsibility, DENY precedence, audit actor provenance and fail-closed unavailable/unknown evidence. Never trust caller-constructed actor/claim/permission evidence or stale grants. Keep industrial IT/OT segregation and human decision authority; no PLC/RTU command or autonomous actuation. Do not store raw IdP tokens, secrets or unrestricted personal/claim payloads.

### 10. Testing

Matched executed task-specific source suites:

- No task-specific executed suite was independently matched. The module-level exact-head suite inventory is recorded below; it does not automatically verify this whole objective.

For remaining code scope, add focused domain-invariant and application tests, actual request/DTO/error and authorization contract tests, fresh PostgreSQL/Flyway/JPA/constraint/rollback tests, relevant negative/replay/concurrency cases and architecture/mapper checks. Reuse existing module tests and add actual class/case/source hashes to the CI inventory only when those tests exist. A Docker skip is not database proof. Documentation/preflight tasks use actual documentation consistency/link/inventory checks without claiming Java behavior was newly executed. Full/Fast/Docs evidence must retain exact SHA/run/attempt and required case membership.

### 11. Acceptance Criteria

- Preserve the original bounded objective and verify its claimed output against current artifacts. Provide reproducible analytical lineage and governed role-specific reporting outputs.
- Prove the real owner/API/persistence path and negative cases; record exact source, actual executed and skipped cases and accepted contract artifact.
- Required transactions roll back fully; historical exact reads remain reproducible after source revision.
- Missing policy, unregistered file placement or unavailable authority is explicitly blocked rather than fabricated.
- Applicable Full/Fast/Docs acceptance succeeds on the implementation SHA; software acceptance remains separate from SITE commissioning.

### 12. Dependencies and Blockers

Canonical prerequisites: HAPI-604. A dependency edge does not select implementation. Resolve only the task-specific exact path/policy/source contract gaps described here before code; real deployment data is not a software-contract blocker.

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

<!-- task-register:payload:end -->
