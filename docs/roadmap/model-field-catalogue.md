# HidraAPI Model / Field / Type Catalogue Roadmap

**Status:** Active — roadmap prepared; module-by-module scan may begin with HMC-002.

**Baseline repository:** `CHOUABBIA-AMINE/HidraAPI`  
**Baseline branch:** `main`  
**Pinned source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Prepared:** 2026-10-02

## 1. Objective

Produce a verified catalogue of the current HidraAPI domain models by scanning the repository in a controlled order:

```text
module
  -> model
      -> declared field
          -> declared Java type
```

The final deliverable is a readable Excel workbook generated from the pinned HidraAPI source tree, with complete source traceability.

This catalogue documents Java domain-model declarations. It is **not** a PostgreSQL schema catalogue and must not be used to infer JPA/Flyway column types, nullability, indexes, foreign keys, or database constraints without inspecting the persistence layer separately.

## 2. Authoritative scan scope

Only current production model sources matching:

```text
src/main/java/dz/sh/hidra/modules/<module>/domain/model/*.java
```

are in scope.

Rules:

- exclude `package-info.java`;
- inspect the top-level Java declaration matching the source filename;
- classify each model as `class`, `record`, `interface`, or `enum`;
- for records, capture declared record components;
- for classes, capture explicit declared instance fields;
- do not infer inherited fields;
- do not invent fields from getters, methods, JPA entities, DTOs, migrations, or API schemas;
- interfaces/enums with no declared model fields remain valid catalogue models with zero field rows and must still appear in the model index;
- nested types are not independent model rows unless they are separate source files in the scoped directory;
- preserve declared Java type text as represented by the source declaration;
- every extracted row must retain module, model, source path, and pinned source commit.

## 3. Baseline module inventory

The pinned source tree contains **24 modules** and **123 direct domain/model Java files**.

| Order | Module | Model files at baseline | Scan task |
|---:|---|---:|---|
| 1 | alarm | 4 | HMC-002 |
| 2 | analytics | 7 | HMC-003 |
| 3 | assets | 3 | HMC-004 |
| 4 | audit | 4 | HMC-005 |
| 5 | configuration | 3 | HMC-006 |
| 6 | custody | 3 | HMC-007 |
| 7 | documents | 4 | HMC-008 |
| 8 | hse | 4 | HMC-009 |
| 9 | identity | 15 | HMC-010 |
| 10 | incident | 4 | HMC-011 |
| 11 | integration | 4 | HMC-012 |
| 12 | integrity | 4 | HMC-013 |
| 13 | leakdetection | 3 | HMC-014 |
| 14 | monitoring | 2 | HMC-015 |
| 15 | notification | 4 | HMC-016 |
| 16 | organization | 17 | HMC-017 |
| 17 | party | 2 | HMC-018 |
| 18 | planning | 5 | HMC-019 |
| 19 | reporting | 4 | HMC-020 |
| 20 | risk | 4 | HMC-021 |
| 21 | simulation | 6 | HMC-022 |
| 22 | telemetry | 4 | HMC-023 |
| 23 | topology | 5 | HMC-024 |
| 24 | workflow | 8 | HMC-025 |
|  | **Total** | **123** |  |

If the live source tree changes after this roadmap is prepared, do not silently mix commits. Either finish against the pinned commit or explicitly re-baseline this roadmap before continuing.

## 4. Per-model extraction record

For each model, record:

| Column | Meaning |
|---|---|
| Module | Owning bounded context |
| Model | Top-level Java model name |
| Model Kind | class / record / interface / enum |
| Field | Declared instance field or record component |
| Declared Java Type | Exact normalized source declaration type |
| Field Ordinal | Declaration order inside the model |
| Field Count | Number of declared fields/components for the model |
| Source Path | Repository-relative Java source path |
| Source Commit | Pinned HidraAPI SHA |
| Extraction Status | Extracted / Zero-field / Exception |
| Notes | Parser ambiguity or manual-verification note only |

A separate model-index row must exist for every scanned model, including models with zero fields.

## 5. Module scan acceptance criteria

A module task is complete only when:

1. every baseline model file for that module has been inspected;
2. every model has a model-index record;
3. every declared class instance field or record component has a field row;
4. declared Java type is captured without conversion to database or JSON types;
5. field declaration order is preserved;
6. source path and pinned source SHA are present;
7. zero-field interfaces/enums/models are explicitly represented;
8. parsing exceptions are zero, or each exception is manually inspected and documented;
9. the module counts reconcile:

```text
baseline model files
= extracted models
+ explicitly documented exceptions
```

10. no application, infrastructure, API, JPA, Flyway, DTO, or legacy HyFlo source is mixed into the module model catalogue.

## 6. Execution tasks

| Code | Exact commit message | Scope | Status |
|---|---|---|---|
| HMC-001 | `docs(catalogue): add module model scan roadmap` | Create this roadmap only. No model extraction. | **Completed** — roadmap created at `11118373ea112031342813abe8e4264c7bcd6018`; no model extraction performed. |
| HMC-002 | `docs(catalogue): scan alarm domain models` | Scan all 4 `alarm/domain/model` files; record models, fields and Java types. | **Completed** — 4/4 models, 67 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-003 | `docs(catalogue): scan analytics domain models` | Scan all 7 analytics model files. | **Completed** — 7/7 models, 102 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-004 | `docs(catalogue): scan assets domain models` | Scan all 3 assets model files. | **Completed** — 3/3 models, 56 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-005 | `docs(catalogue): scan audit domain models` | Scan all 4 audit model files. | **Completed** — 4/4 models, 83 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-006 | `docs(catalogue): scan configuration domain models` | Scan all 3 configuration model files. | **Completed** — 3/3 models, 40 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-007 | `docs(catalogue): scan custody domain models` | Scan all 3 custody model files. | **Completed** — 3/3 models, 45 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-008 | `docs(catalogue): scan documents domain models` | Scan all 4 documents model files. | **Completed** — 4/4 models, 75 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-009 | `docs(catalogue): scan hse domain models` | Scan all 4 hse model files. | Planned |
| HMC-010 | `docs(catalogue): scan identity domain models` | Scan all 15 identity model files. | Planned |
| HMC-011 | `docs(catalogue): scan incident domain models` | Scan all 4 incident model files. | Planned |
| HMC-012 | `docs(catalogue): scan integration domain models` | Scan all 4 integration model files. | Planned |
| HMC-013 | `docs(catalogue): scan integrity domain models` | Scan all 4 integrity model files. | Planned |
| HMC-014 | `docs(catalogue): scan leak detection domain models` | Scan all 3 leakdetection model files. | Planned |
| HMC-015 | `docs(catalogue): scan monitoring domain models` | Scan all 2 monitoring model files. | Planned |
| HMC-016 | `docs(catalogue): scan notification domain models` | Scan all 4 notification model files. | Planned |
| HMC-017 | `docs(catalogue): scan organization domain models` | Scan all 17 organization model files. | Planned |
| HMC-018 | `docs(catalogue): scan party domain models` | Scan all 2 party model files. | Planned |
| HMC-019 | `docs(catalogue): scan planning domain models` | Scan all 5 planning model files. | Planned |
| HMC-020 | `docs(catalogue): scan reporting domain models` | Scan all 4 reporting model files. | Planned |
| HMC-021 | `docs(catalogue): scan risk domain models` | Scan all 4 risk model files. | Planned |
| HMC-022 | `docs(catalogue): scan simulation domain models` | Scan all 6 simulation model files. | Planned |
| HMC-023 | `docs(catalogue): scan telemetry domain models` | Scan all 4 telemetry model files. | Planned |
| HMC-024 | `docs(catalogue): scan topology domain models` | Scan all 5 topology model files. | Planned |
| HMC-025 | `docs(catalogue): scan workflow domain models` | Scan all 8 workflow model files. | Planned |
| HMC-026 | `docs(catalogue): reconcile module model catalogue` | Reconcile all 24 module outputs against the pinned 123-file baseline; resolve extraction exceptions and duplicate/missing rows. | Planned |
| HMC-027 | `docs(catalogue): generate final model field workbook` | Generate the consolidated Excel document from the reconciled catalogue. | Planned |
| HMC-028 | `docs(catalogue): validate and finalize model field catalogue` | Validate workbook readability, row/model/module counts, provenance, and final delivery evidence. | Planned |

Only **one HMC task** may be executed per commit. Do not scan the next module automatically.

## 7. Module scan artifact structure

During HMC-002 through HMC-025, each module scan will be recorded in a deterministic machine-readable staging artifact outside production Java code.

Recommended logical structure:

```text
catalogue/
  module-index
  alarm
  analytics
  ...
  workflow
```

The implementation mechanism may be local/transient when the final workbook is the user deliverable. No temporary extractor, staging JSON, or binary workbook is merged into `main` unless explicitly authorized.

Each module result must report:

```text
module
baseline model count
scanned model count
declared field count
zero-field model count
exception count
source commit
```

## 8. Final document — HMC-027

The final Excel workbook will be named:

```text
HidraAPI_Model_Field_Type_Catalogue_5e301857.xlsx
```

Workbook structure:

1. **README**
   - purpose;
   - repository;
   - source commit;
   - generation date;
   - scope definition;
   - warning that Java field types are not PostgreSQL/JPA column types.

2. **Module Index**
   - module;
   - model count;
   - field count;
   - zero-field model count;
   - exception count;
   - completion status.

3. **Model Index**
   - one row per model;
   - module;
   - model;
   - kind;
   - declared field count;
   - source path;
   - source commit;
   - extraction status.

4. **All Fields**
   - one row per declared field/component across all modules;
   - full column set defined in section 4.

5. **One worksheet per module**
   - the module's models and fields;
   - models grouped together and ordered by source/model name;
   - declaration order preserved.

6. **Validation**
   - expected vs actual module/model counts;
   - total field rows;
   - zero-field models;
   - extraction exceptions;
   - duplicate-key checks;
   - source SHA verification;
   - workbook readability result.

## 9. Final validation — HMC-028

The final catalogue is accepted only when:

- all 24 modules are Completed;
- all 123 pinned model files are reconciled;
- there are no unexplained missing models;
- there are no unexplained extraction exceptions;
- every field row has module/model/field/type/source path/source commit;
- model-index counts equal the sum of module scans;
- `All Fields` equals the sum of module field counts;
- per-module worksheets reconcile with `Module Index`;
- duplicate key `(module, model, field ordinal)` is absent;
- source commit is consistently `5e301857882b59e9e35ecc474e9c6537d89cc96a`;
- the generated workbook can be opened and all required sheets are readable;
- final counts and validation results are reported to the user.

## 10. Relationship to HDP-004 / HDP-005

This catalogue is a prerequisite reference artifact for controlled source-to-target analysis, but it does not complete HDP-004 and does not authorize HDP-005.

After HMC-028:

1. use the verified catalogue as one target-model reference for HDP-004 semantic review;
2. continue to inspect JPA mappings, Flyway schema, application contracts and enums where database/import semantics are required;
3. do not treat Java field type alone as target database type;
4. do not import any source data until the HDP gates are satisfied.

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

## 13. HMC-004 — Assets module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `assets`  
**Baseline model files:** 3  
**Scanned models:** 3  
**Declared fields/components:** 56  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Assets model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| AssetConditionRecord | record | 11 | `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java` | Extracted |
| MaintainableAsset | record | 25 | `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java` | Extracted |
| MaintenanceWorkOrder | record | 20 | `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java` | Extracted |

### Assets fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| AssetConditionRecord | 1 | id | String |
| AssetConditionRecord | 2 | maintainableAssetId | String |
| AssetConditionRecord | 3 | conditionStatus | AssetConditionStatus |
| AssetConditionRecord | 4 | conditionTypeId | String |
| AssetConditionRecord | 5 | sourceModule | String |
| AssetConditionRecord | 6 | sourceReferenceId | String |
| AssetConditionRecord | 7 | summary | String |
| AssetConditionRecord | 8 | conditionScore | BigDecimal |
| AssetConditionRecord | 9 | observedAt | Instant |
| AssetConditionRecord | 10 | observedByActorId | String |
| AssetConditionRecord | 11 | createdAt | Instant |
| MaintainableAsset | 1 | id | String |
| MaintainableAsset | 2 | assetNumber | String |
| MaintainableAsset | 3 | assetCode | String |
| MaintainableAsset | 4 | assetName | String |
| MaintainableAsset | 5 | assetTypeId | String |
| MaintainableAsset | 6 | topologyAssetTypeCode | String |
| MaintainableAsset | 7 | topologyAssetId | String |
| MaintainableAsset | 8 | topologyAssetCodeSnapshot | String |
| MaintainableAsset | 9 | topologyAssetNameSnapshot | String |
| MaintainableAsset | 10 | parentAssetId | String |
| MaintainableAsset | 11 | status | AssetLifecycleStatus |
| MaintainableAsset | 12 | criticalityId | String |
| MaintainableAsset | 13 | ownerOrganizationUnitId | String |
| MaintainableAsset | 14 | ownerOrganizationUnitNameSnapshot | String |
| MaintainableAsset | 15 | manufacturerPartyId | String |
| MaintainableAsset | 16 | manufacturerNameSnapshot | String |
| MaintainableAsset | 17 | modelId | String |
| MaintainableAsset | 18 | serialIdentityId | String |
| MaintainableAsset | 19 | registeredAt | Instant |
| MaintainableAsset | 20 | installedAt | Instant |
| MaintainableAsset | 21 | commissionedAt | Instant |
| MaintainableAsset | 22 | retiredAt | Instant |
| MaintainableAsset | 23 | createdByActorId | String |
| MaintainableAsset | 24 | createdAt | Instant |
| MaintainableAsset | 25 | updatedAt | Instant |
| MaintenanceWorkOrder | 1 | id | String |
| MaintenanceWorkOrder | 2 | workOrderNumber | String |
| MaintenanceWorkOrder | 3 | maintainableAssetId | String |
| MaintenanceWorkOrder | 4 | maintenancePlanId | String |
| MaintenanceWorkOrder | 5 | sourceRecommendationId | String |
| MaintenanceWorkOrder | 6 | workOrderTypeId | String |
| MaintenanceWorkOrder | 7 | priorityId | String |
| MaintenanceWorkOrder | 8 | status | MaintenanceWorkOrderStatus |
| MaintenanceWorkOrder | 9 | title | String |
| MaintenanceWorkOrder | 10 | description | String |
| MaintenanceWorkOrder | 11 | assignedOrganizationUnitId | String |
| MaintenanceWorkOrder | 12 | assignedActorId | String |
| MaintenanceWorkOrder | 13 | plannedStartAt | Instant |
| MaintenanceWorkOrder | 14 | plannedEndAt | Instant |
| MaintenanceWorkOrder | 15 | startedAt | Instant |
| MaintenanceWorkOrder | 16 | completedAt | Instant |
| MaintenanceWorkOrder | 17 | workflowInstanceId | String |
| MaintenanceWorkOrder | 18 | createdByActorId | String |
| MaintenanceWorkOrder | 19 | createdAt | Instant |
| MaintenanceWorkOrder | 20 | updatedAt | Instant |

### HMC-004 reconciliation

```text
baseline model files = 3
extracted models     = 3
exceptions           = 0
declared fields      = 56
zero-field models    = 0
```

The scan records only declarations under `assets/domain/model`. Assets domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, or database column metadata was mixed into this module scan.

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

## 15. HMC-006 — Configuration module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `configuration`  
**Baseline model files:** 3  
**Scanned models:** 3  
**Declared fields/components:** 40  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Configuration model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| ConfigurationDefinition | record | 15 | `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java` | Extracted |
| ConfigurationValue | record | 13 | `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java` | Extracted |
| FeatureFlag | record | 12 | `src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java` | Extracted |

### Configuration fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| ConfigurationDefinition | 1 | id | String |
| ConfigurationDefinition | 2 | namespaceId | String |
| ConfigurationDefinition | 3 | key | String |
| ConfigurationDefinition | 4 | displayNameFr | String |
| ConfigurationDefinition | 5 | displayNameAr | String |
| ConfigurationDefinition | 6 | displayNameEn | String |
| ConfigurationDefinition | 7 | valueType | ConfigurationValueType |
| ConfigurationDefinition | 8 | sensitivity | ConfigurationSensitivity |
| ConfigurationDefinition | 9 | status | ConfigurationDefinitionStatus |
| ConfigurationDefinition | 10 | scoped | boolean |
| ConfigurationDefinition | 11 | requiresApproval | boolean |
| ConfigurationDefinition | 12 | defaultValue | String |
| ConfigurationDefinition | 13 | description | String |
| ConfigurationDefinition | 14 | createdAt | Instant |
| ConfigurationDefinition | 15 | updatedAt | Instant |
| ConfigurationValue | 1 | id | String |
| ConfigurationValue | 2 | definitionId | String |
| ConfigurationValue | 3 | definitionVersionId | String |
| ConfigurationValue | 4 | environment | String |
| ConfigurationValue | 5 | rawValue | String |
| ConfigurationValue | 6 | jsonValue | String |
| ConfigurationValue | 7 | secretReference | String |
| ConfigurationValue | 8 | status | ConfigurationValueStatus |
| ConfigurationValue | 9 | effectiveFrom | Instant |
| ConfigurationValue | 10 | effectiveTo | Instant |
| ConfigurationValue | 11 | createdByActorId | String |
| ConfigurationValue | 12 | createdAt | Instant |
| ConfigurationValue | 13 | updatedAt | Instant |
| FeatureFlag | 1 | id | String |
| FeatureFlag | 2 | code | String |
| FeatureFlag | 3 | nameFr | String |
| FeatureFlag | 4 | nameAr | String |
| FeatureFlag | 5 | nameEn | String |
| FeatureFlag | 6 | owningModule | String |
| FeatureFlag | 7 | status | FeatureFlagStatus |
| FeatureFlag | 8 | evaluationStrategy | FeatureFlagEvaluationStrategy |
| FeatureFlag | 9 | defaultEnabled | boolean |
| FeatureFlag | 10 | description | String |
| FeatureFlag | 11 | createdAt | Instant |
| FeatureFlag | 12 | updatedAt | Instant |

### HMC-006 reconciliation

```text
baseline model files = 3
extracted models     = 3
exceptions           = 0
declared fields      = 40
zero-field models    = 0
```

The scan records only declarations under `configuration/domain/model`. Configuration domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, or database column metadata was mixed into this module scan.

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

## 18. Current next task

```text
HMC-009 — docs(catalogue): scan hse domain models
```

Do not start HMC-010 until HMC-009 is completed and its module counts are reported.

