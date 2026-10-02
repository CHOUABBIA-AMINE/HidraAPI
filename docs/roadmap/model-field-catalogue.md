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
| HMC-004 | `docs(catalogue): scan assets domain models` | Scan all 3 assets model files. | Planned |
| HMC-005 | `docs(catalogue): scan audit domain models` | Scan all 4 audit model files. | Planned |
| HMC-006 | `docs(catalogue): scan configuration domain models` | Scan all 3 configuration model files. | Planned |
| HMC-007 | `docs(catalogue): scan custody domain models` | Scan all 3 custody model files. | Planned |
| HMC-008 | `docs(catalogue): scan documents domain models` | Scan all 4 documents model files. | Planned |
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

## 13. Current next task

```text
HMC-004 — docs(catalogue): scan assets domain models
```

Do not start HMC-005 until HMC-004 is completed and its module counts are reported.

