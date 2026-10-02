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
| HMC-009 | `docs(catalogue): scan hse domain models` | Scan all 4 hse model files. | **Completed** — 4/4 models, 77 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-010 | `docs(catalogue): scan identity domain models` | Scan all 15 identity model files. | **Completed** — 15/15 models, 180 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-011 | `docs(catalogue): scan incident domain models` | Scan all 4 incident model files. | **Completed** — 4/4 models, 74 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-012 | `docs(catalogue): scan integration domain models` | Scan all 4 integration model files. | **Completed** — 4/4 models, 66 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
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

## 18. HMC-009 — HSE module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `hse`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 77  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### HSE model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| HseCase | record | 30 | `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java` | Extracted |
| HseClosure | record | 11 | `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java` | Extracted |
| HseCorrectivePreventiveAction | record | 20 | `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java` | Extracted |
| PermitToWork | record | 16 | `src/main/java/dz/sh/hidra/modules/hse/domain/model/PermitToWork.java` | Extracted |

### HSE fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| HseCase | 1 | id | String |
| HseCase | 2 | caseNumber | String |
| HseCase | 3 | title | String |
| HseCase | 4 | description | String |
| HseCase | 5 | caseTypeId | String |
| HseCase | 6 | severityId | String |
| HseCase | 7 | priorityId | String |
| HseCase | 8 | status | HseCaseStatus |
| HseCase | 9 | sourceType | HseCaseSourceType |
| HseCase | 10 | incidentReferenceId | String |
| HseCase | 11 | incidentCodeSnapshot | String |
| HseCase | 12 | incidentTitleSnapshot | String |
| HseCase | 13 | targetModule | String |
| HseCase | 14 | targetTypeCode | String |
| HseCase | 15 | targetId | String |
| HseCase | 16 | targetCodeSnapshot | String |
| HseCase | 17 | targetLabelSnapshot | String |
| HseCase | 18 | occurredAt | Instant |
| HseCase | 19 | reportedAt | Instant |
| HseCase | 20 | reportedByActorId | String |
| HseCase | 21 | reportedByDisplayNameSnapshot | String |
| HseCase | 22 | responsibleOrganizationUnitId | String |
| HseCase | 23 | responsibleOrganizationUnitNameSnapshot | String |
| HseCase | 24 | workflowInstanceId | String |
| HseCase | 25 | auditReferenceId | String |
| HseCase | 26 | controlledAt | Instant |
| HseCase | 27 | resolvedAt | Instant |
| HseCase | 28 | closedAt | Instant |
| HseCase | 29 | createdAt | Instant |
| HseCase | 30 | updatedAt | Instant |
| HseClosure | 1 | id | String |
| HseClosure | 2 | hseCaseId | String |
| HseClosure | 3 | closureSummary | String |
| HseClosure | 4 | impactAssessed | boolean |
| HseClosure | 5 | capaCompleted | boolean |
| HseClosure | 6 | evidenceReviewed | boolean |
| HseClosure | 7 | regulatoryReviewed | boolean |
| HseClosure | 8 | closedByActorId | String |
| HseClosure | 9 | closedByDisplayNameSnapshot | String |
| HseClosure | 10 | closedAt | Instant |
| HseClosure | 11 | workflowInstanceId | String |
| HseCorrectivePreventiveAction | 1 | id | String |
| HseCorrectivePreventiveAction | 2 | hseCaseId | String |
| HseCorrectivePreventiveAction | 3 | actionNumber | String |
| HseCorrectivePreventiveAction | 4 | actionTypeId | String |
| HseCorrectivePreventiveAction | 5 | title | String |
| HseCorrectivePreventiveAction | 6 | description | String |
| HseCorrectivePreventiveAction | 7 | ownerActorId | String |
| HseCorrectivePreventiveAction | 8 | ownerDisplayNameSnapshot | String |
| HseCorrectivePreventiveAction | 9 | ownerOrganizationUnitId | String |
| HseCorrectivePreventiveAction | 10 | ownerOrganizationUnitNameSnapshot | String |
| HseCorrectivePreventiveAction | 11 | targetDate | Instant |
| HseCorrectivePreventiveAction | 12 | completedAt | Instant |
| HseCorrectivePreventiveAction | 13 | verificationRequired | boolean |
| HseCorrectivePreventiveAction | 14 | verifiedByActorId | String |
| HseCorrectivePreventiveAction | 15 | verifiedAt | Instant |
| HseCorrectivePreventiveAction | 16 | status | CapaStatus |
| HseCorrectivePreventiveAction | 17 | linkedWorkOrderId | String |
| HseCorrectivePreventiveAction | 18 | workflowTaskId | String |
| HseCorrectivePreventiveAction | 19 | createdAt | Instant |
| HseCorrectivePreventiveAction | 20 | updatedAt | Instant |
| PermitToWork | 1 | id | String |
| PermitToWork | 2 | permitNumber | String |
| PermitToWork | 3 | permitTypeId | String |
| PermitToWork | 4 | title | String |
| PermitToWork | 5 | description | String |
| PermitToWork | 6 | targetModule | String |
| PermitToWork | 7 | targetTypeCode | String |
| PermitToWork | 8 | targetId | String |
| PermitToWork | 9 | requestedByActorId | String |
| PermitToWork | 10 | approvedByActorId | String |
| PermitToWork | 11 | validFrom | Instant |
| PermitToWork | 12 | validTo | Instant |
| PermitToWork | 13 | status | PermitStatus |
| PermitToWork | 14 | workflowInstanceId | String |
| PermitToWork | 15 | createdAt | Instant |
| PermitToWork | 16 | updatedAt | Instant |

### HMC-009 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 77
zero-field models    = 0
```

The scan records only declarations under `hse/domain/model`. HSE domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, or database column metadata was mixed into this module scan.

## 19. HMC-010 — Identity module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `identity`  
**Baseline model files:** 15  
**Scanned models:** 15  
**Declared fields/components:** 180  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Identity model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| AuthenticationEvent | record | 13 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java` | Extracted |
| AuthorizationDecision | record | 15 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java` | Extracted |
| AuthorizationDelegationGrant | record | 12 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java` | Extracted |
| ExternalRoleMapping | record | 10 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java` | Extracted |
| GroupRoleGrant | record | 10 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java` | Extracted |
| HidraPrincipal | record | 7 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java` | Extracted |
| IdentityProvider | record | 23 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java` | Extracted |
| LocalCredential | record | 7 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java` | Extracted |
| LoginSession | record | 11 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java` | Extracted |
| Permission | record | 13 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java` | Extracted |
| Role | record | 10 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java` | Extracted |
| RolePermissionGrant | record | 9 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java` | Extracted |
| User | record | 15 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java` | Extracted |
| UserPermissionGrant | record | 13 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java` | Extracted |
| UserRoleGrant | record | 12 | `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java` | Extracted |

### Identity fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| AuthenticationEvent | 1 | id | String |
| AuthenticationEvent | 2 | userId | String |
| AuthenticationEvent | 3 | identityProviderId | String |
| AuthenticationEvent | 4 | externalIdentityId | String |
| AuthenticationEvent | 5 | externalSubject | String |
| AuthenticationEvent | 6 | eventType | AuthenticationEventType |
| AuthenticationEvent | 7 | protocol | AuthenticationProtocol |
| AuthenticationEvent | 8 | clientIp | String |
| AuthenticationEvent | 9 | userAgent | String |
| AuthenticationEvent | 10 | failureReason | String |
| AuthenticationEvent | 11 | riskScore | java.math.BigDecimal |
| AuthenticationEvent | 12 | occurredAt | Instant |
| AuthenticationEvent | 13 | correlationId | String |
| AuthorizationDecision | 1 | id | String |
| AuthorizationDecision | 2 | userId | String |
| AuthorizationDecision | 3 | permissionCode | String |
| AuthorizationDecision | 4 | resourceType | String |
| AuthorizationDecision | 5 | resourceReferenceId | String |
| AuthorizationDecision | 6 | scope | AuthorizationScope |
| AuthorizationDecision | 7 | decision | AuthorizationDecisionValue |
| AuthorizationDecision | 8 | reasonCode | String |
| AuthorizationDecision | 9 | reasonMessage | String |
| AuthorizationDecision | 10 | matchedGrantIds | String |
| AuthorizationDecision | 11 | matchedPolicyRuleIds | String |
| AuthorizationDecision | 12 | externalClaimsUsed | String |
| AuthorizationDecision | 13 | evaluatedAt | Instant |
| AuthorizationDecision | 14 | correlationId | String |
| AuthorizationDecision | 15 | requestId | String |
| AuthorizationDelegationGrant | 1 | id | String |
| AuthorizationDelegationGrant | 2 | delegatorUserId | String |
| AuthorizationDelegationGrant | 3 | delegateUserId | String |
| AuthorizationDelegationGrant | 4 | permissionId | String |
| AuthorizationDelegationGrant | 5 | roleId | String |
| AuthorizationDelegationGrant | 6 | scope | AuthorizationScope |
| AuthorizationDelegationGrant | 7 | approvedByWorkflowId | String |
| AuthorizationDelegationGrant | 8 | validFrom | Instant |
| AuthorizationDelegationGrant | 9 | validTo | Instant |
| AuthorizationDelegationGrant | 10 | status | DelegationStatus |
| AuthorizationDelegationGrant | 11 | createdAt | Instant |
| AuthorizationDelegationGrant | 12 | revokedAt | Instant |
| ExternalRoleMapping | 1 | id | String |
| ExternalRoleMapping | 2 | identityProviderId | String |
| ExternalRoleMapping | 3 | roleId | String |
| ExternalRoleMapping | 4 | externalRoleCode | String |
| ExternalRoleMapping | 5 | claimName | String |
| ExternalRoleMapping | 6 | mappingMode | ExternalMappingMode |
| ExternalRoleMapping | 7 | scope | AuthorizationScope |
| ExternalRoleMapping | 8 | status | ExternalMappingStatus |
| ExternalRoleMapping | 9 | createdAt | Instant |
| ExternalRoleMapping | 10 | updatedAt | Instant |
| GroupRoleGrant | 1 | id | String |
| GroupRoleGrant | 2 | groupId | String |
| GroupRoleGrant | 3 | roleId | String |
| GroupRoleGrant | 4 | scope | AuthorizationScope |
| GroupRoleGrant | 5 | grantReason | String |
| GroupRoleGrant | 6 | approvedByWorkflowId | String |
| GroupRoleGrant | 7 | validFrom | Instant |
| GroupRoleGrant | 8 | validTo | Instant |
| GroupRoleGrant | 9 | status | GrantStatus |
| GroupRoleGrant | 10 | createdAt | Instant |
| HidraPrincipal | 1 | userId | String |
| HidraPrincipal | 2 | username | String |
| HidraPrincipal | 3 | displayName | String |
| HidraPrincipal | 4 | authenticationType | ProviderType |
| HidraPrincipal | 5 | identityProviderId | String |
| HidraPrincipal | 6 | roles | Set<String> |
| HidraPrincipal | 7 | permissions | Set<String> |
| IdentityProvider | 1 | id | String |
| IdentityProvider | 2 | code | String |
| IdentityProvider | 3 | name | String |
| IdentityProvider | 4 | providerType | ProviderType |
| IdentityProvider | 5 | issuerUri | String |
| IdentityProvider | 6 | authorizationEndpoint | String |
| IdentityProvider | 7 | tokenEndpoint | String |
| IdentityProvider | 8 | jwksUri | String |
| IdentityProvider | 9 | directoryBaseDn | String |
| IdentityProvider | 10 | userSearchBase | String |
| IdentityProvider | 11 | groupSearchBase | String |
| IdentityProvider | 12 | usernameAttribute | String |
| IdentityProvider | 13 | emailAttribute | String |
| IdentityProvider | 14 | displayNameAttribute | String |
| IdentityProvider | 15 | externalIdAttribute | String |
| IdentityProvider | 16 | groupMembershipAttribute | String |
| IdentityProvider | 17 | syncEnabled | boolean |
| IdentityProvider | 18 | justInTimeProvisioningEnabled | boolean |
| IdentityProvider | 19 | status | IdentityProviderStatus |
| IdentityProvider | 20 | metadata | String |
| IdentityProvider | 21 | secretReference | String |
| IdentityProvider | 22 | createdAt | Instant |
| IdentityProvider | 23 | updatedAt | Instant |
| LocalCredential | 1 | id | String |
| LocalCredential | 2 | userId | String |
| LocalCredential | 3 | passwordHash | String |
| LocalCredential | 4 | credentialStatus | String |
| LocalCredential | 5 | passwordChangedAt | Instant |
| LocalCredential | 6 | createdAt | Instant |
| LocalCredential | 7 | updatedAt | Instant |
| LoginSession | 1 | id | String |
| LoginSession | 2 | userId | String |
| LoginSession | 3 | identityProviderId | String |
| LoginSession | 4 | externalIdentityId | String |
| LoginSession | 5 | startedAt | Instant |
| LoginSession | 6 | lastSeenAt | Instant |
| LoginSession | 7 | expiresAt | Instant |
| LoginSession | 8 | clientIp | String |
| LoginSession | 9 | userAgent | String |
| LoginSession | 10 | status | LoginSessionStatus |
| LoginSession | 11 | correlationId | String |
| Permission | 1 | id | String |
| Permission | 2 | code | String |
| Permission | 3 | nameAr | String |
| Permission | 4 | nameFr | String |
| Permission | 5 | nameEn | String |
| Permission | 6 | description | String |
| Permission | 7 | permissionDomain | String |
| Permission | 8 | resourceType | String |
| Permission | 9 | action | String |
| Permission | 10 | sensitive | boolean |
| Permission | 11 | status | PermissionStatus |
| Permission | 12 | createdAt | Instant |
| Permission | 13 | updatedAt | Instant |
| Role | 1 | id | String |
| Role | 2 | code | String |
| Role | 3 | nameAr | String |
| Role | 4 | nameFr | String |
| Role | 5 | nameEn | String |
| Role | 6 | description | String |
| Role | 7 | roleType | RoleType |
| Role | 8 | status | RoleStatus |
| Role | 9 | createdAt | Instant |
| Role | 10 | updatedAt | Instant |
| RolePermissionGrant | 1 | id | String |
| RolePermissionGrant | 2 | roleId | String |
| RolePermissionGrant | 3 | permissionId | String |
| RolePermissionGrant | 4 | effect | GrantEffect |
| RolePermissionGrant | 5 | conditionExpression | String |
| RolePermissionGrant | 6 | validFrom | Instant |
| RolePermissionGrant | 7 | validTo | Instant |
| RolePermissionGrant | 8 | status | GrantStatus |
| RolePermissionGrant | 9 | createdAt | Instant |
| User | 1 | id | String |
| User | 2 | username | String |
| User | 3 | emailAddress | String |
| User | 4 | displayName | String |
| User | 5 | userType | UserType |
| User | 6 | status | UserStatus |
| User | 7 | employeeReferenceId | String |
| User | 8 | lastAuthenticatedAt | Instant |
| User | 9 | failedLoginCount | int |
| User | 10 | lockedUntil | Instant |
| User | 11 | createdAt | Instant |
| User | 12 | activatedAt | Instant |
| User | 13 | suspendedAt | Instant |
| User | 14 | disabledAt | Instant |
| User | 15 | updatedAt | Instant |
| UserPermissionGrant | 1 | id | String |
| UserPermissionGrant | 2 | userId | String |
| UserPermissionGrant | 3 | permissionId | String |
| UserPermissionGrant | 4 | effect | GrantEffect |
| UserPermissionGrant | 5 | scope | AuthorizationScope |
| UserPermissionGrant | 6 | grantReason | String |
| UserPermissionGrant | 7 | approvedByWorkflowId | String |
| UserPermissionGrant | 8 | emergencyAccess | boolean |
| UserPermissionGrant | 9 | validFrom | Instant |
| UserPermissionGrant | 10 | validTo | Instant |
| UserPermissionGrant | 11 | status | GrantStatus |
| UserPermissionGrant | 12 | createdAt | Instant |
| UserPermissionGrant | 13 | revokedAt | Instant |
| UserRoleGrant | 1 | id | String |
| UserRoleGrant | 2 | userId | String |
| UserRoleGrant | 3 | roleId | String |
| UserRoleGrant | 4 | scope | AuthorizationScope |
| UserRoleGrant | 5 | grantReason | String |
| UserRoleGrant | 6 | approvedByWorkflowId | String |
| UserRoleGrant | 7 | validFrom | Instant |
| UserRoleGrant | 8 | validTo | Instant |
| UserRoleGrant | 9 | status | GrantStatus |
| UserRoleGrant | 10 | createdAt | Instant |
| UserRoleGrant | 11 | revokedAt | Instant |
| UserRoleGrant | 12 | revokedReason | String |

### HMC-010 reconciliation

```text
baseline model files = 15
extracted models     = 15
exceptions           = 0
declared fields      = 180
zero-field models    = 0
```

The scan records only declarations under `identity/domain/model`. Identity domain value types and Java collection element types referenced by record components remain declared field types; they are not independent model rows for this catalogue scope.

No application, infrastructure, API, JPA, Flyway, DTO, authentication-provider implementation, credential persistence mapping, or database column metadata was mixed into this module scan.

## 20. HMC-011 — Incident module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `incident`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 74  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Incident model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| Incident | record | 37 | `src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java` | Extracted |
| IncidentClosure | record | 11 | `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java` | Extracted |
| IncidentRelatedIncident | record | 7 | `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java` | Extracted |
| IncidentResponseAction | record | 19 | `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java` | Extracted |

### Incident fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| Incident | 1 | id | String |
| Incident | 2 | incidentNumber | String |
| Incident | 3 | title | String |
| Incident | 4 | description | String |
| Incident | 5 | classificationId | String |
| Incident | 6 | severityId | String |
| Incident | 7 | priorityId | String |
| Incident | 8 | status | IncidentStatus |
| Incident | 9 | sourceType | IncidentSourceType |
| Incident | 10 | sourceReferenceId | String |
| Incident | 11 | sourceReferenceCode | String |
| Incident | 12 | detectedAt | Instant |
| Incident | 13 | reportedAt | Instant |
| Incident | 14 | occurredAt | Instant |
| Incident | 15 | topologyAssetTypeCode | String |
| Incident | 16 | topologyAssetId | String |
| Incident | 17 | topologyAssetCode | String |
| Incident | 18 | topologyAssetNameSnapshot | String |
| Incident | 19 | locationDescriptionAr | String |
| Incident | 20 | locationDescriptionLt | String |
| Incident | 21 | latitude | BigDecimal |
| Incident | 22 | longitude | BigDecimal |
| Incident | 23 | responsibleOrganizationUnitId | String |
| Incident | 24 | responsibleOrganizationUnitCode | String |
| Incident | 25 | responsibleOrganizationUnitNameSnapshot | String |
| Incident | 26 | responsibleActorId | String |
| Incident | 27 | responsibleActorNameSnapshot | String |
| Incident | 28 | workflowInstanceId | String |
| Incident | 29 | currentEscalationLevel | int |
| Incident | 30 | containedAt | Instant |
| Incident | 31 | resolvedAt | Instant |
| Incident | 32 | closedAt | Instant |
| Incident | 33 | cancelledAt | Instant |
| Incident | 34 | createdByActorId | String |
| Incident | 35 | createdByActorNameSnapshot | String |
| Incident | 36 | createdAt | Instant |
| Incident | 37 | updatedAt | Instant |
| IncidentClosure | 1 | id | String |
| IncidentClosure | 2 | incidentId | String |
| IncidentClosure | 3 | closureSummary | String |
| IncidentClosure | 4 | resolutionVerified | boolean |
| IncidentClosure | 5 | evidenceReviewed | boolean |
| IncidentClosure | 6 | rootCauseReviewed | boolean |
| IncidentClosure | 7 | followUpActionsCreated | boolean |
| IncidentClosure | 8 | closedByActorId | String |
| IncidentClosure | 9 | closedByActorNameSnapshot | String |
| IncidentClosure | 10 | closedAt | Instant |
| IncidentClosure | 11 | workflowInstanceId | String |
| IncidentRelatedIncident | 1 | id | String |
| IncidentRelatedIncident | 2 | incidentId | String |
| IncidentRelatedIncident | 3 | relatedIncidentId | String |
| IncidentRelatedIncident | 4 | relationshipTypeId | String |
| IncidentRelatedIncident | 5 | comment | String |
| IncidentRelatedIncident | 6 | createdByActorId | String |
| IncidentRelatedIncident | 7 | createdAt | Instant |
| IncidentResponseAction | 1 | id | String |
| IncidentResponseAction | 2 | incidentId | String |
| IncidentResponseAction | 3 | actionTypeId | String |
| IncidentResponseAction | 4 | actionStatus | ResponseActionStatus |
| IncidentResponseAction | 5 | description | String |
| IncidentResponseAction | 6 | targetType | ResponseTargetType |
| IncidentResponseAction | 7 | targetReferenceId | String |
| IncidentResponseAction | 8 | targetReferenceCode | String |
| IncidentResponseAction | 9 | plannedStartAt | Instant |
| IncidentResponseAction | 10 | plannedEndAt | Instant |
| IncidentResponseAction | 11 | startedAt | Instant |
| IncidentResponseAction | 12 | completedAt | Instant |
| IncidentResponseAction | 13 | performedByActorId | String |
| IncidentResponseAction | 14 | performedByActorNameSnapshot | String |
| IncidentResponseAction | 15 | organizationUnitId | String |
| IncidentResponseAction | 16 | resultSummary | String |
| IncidentResponseAction | 17 | failureReason | String |
| IncidentResponseAction | 18 | createdAt | Instant |
| IncidentResponseAction | 19 | updatedAt | Instant |

### HMC-011 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 74
zero-field models    = 0
```

The scan records only declarations under `incident/domain/model`. Incident domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, or database column metadata was mixed into this module scan.

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

## 22. Current next task

```text
HMC-013 — docs(catalogue): scan integrity domain models
```

Do not start HMC-014 until HMC-013 is completed and its module counts are reported.

