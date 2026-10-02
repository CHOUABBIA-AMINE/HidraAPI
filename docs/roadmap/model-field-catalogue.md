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
| HMC-013 | `docs(catalogue): scan integrity domain models` | Scan all 4 integrity model files. | **Completed** — 4/4 models, 72 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-014 | `docs(catalogue): scan leak detection domain models` | Scan all 3 leakdetection model files. | **Completed** — 3/3 models, 47 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-015 | `docs(catalogue): scan monitoring domain models` | Scan all 2 monitoring model files. | **Completed** — 2/2 models, 37 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-016 | `docs(catalogue): scan notification domain models` | Scan all 4 notification model files. | **Completed** — 4/4 models, 66 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-017 | `docs(catalogue): scan organization domain models` | Scan all 17 organization model files. | **Completed** — 17/17 models, 179 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-018 | `docs(catalogue): scan party domain models` | Scan all 2 party model files. | **Completed** — 2/2 models, 21 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-019 | `docs(catalogue): scan planning domain models` | Scan all 5 planning model files. | **Completed** — 5/5 models, 95 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-020 | `docs(catalogue): scan reporting domain models` | Scan all 4 reporting model files. | **Completed** — 4/4 models, 59 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-021 | `docs(catalogue): scan risk domain models` | Scan all 4 risk model files. | **Completed** — 4/4 models, 78 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
| HMC-022 | `docs(catalogue): scan simulation domain models` | Scan all 6 simulation model files. | **Completed** — 6/6 models, 81 fields/components, 0 zero-field models, 0 exceptions; pinned source `5e301857882b59e9e35ecc474e9c6537d89cc96a`. |
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

## 22. HMC-013 — Integrity module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `integrity`  
**Baseline model files:** 4  
**Scanned models:** 4  
**Declared fields/components:** 72  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Integrity model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| IntegrityAssessment | record | 17 | `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java` | Extracted |
| IntegrityCase | record | 20 | `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java` | Extracted |
| IntegrityProgram | record | 17 | `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java` | Extracted |
| PipelineDefect | record | 18 | `src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java` | Extracted |

### Integrity fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| IntegrityAssessment | 1 | id | String |
| IntegrityAssessment | 2 | programId | String |
| IntegrityAssessment | 3 | assessmentNumber | String |
| IntegrityAssessment | 4 | title | String |
| IntegrityAssessment | 5 | description | String |
| IntegrityAssessment | 6 | assessmentTypeId | String |
| IntegrityAssessment | 7 | methodologyId | String |
| IntegrityAssessment | 8 | status | IntegrityAssessmentStatus |
| IntegrityAssessment | 9 | assessmentDate | Instant |
| IntegrityAssessment | 10 | assessedByActorId | String |
| IntegrityAssessment | 11 | reviewedByActorId | String |
| IntegrityAssessment | 12 | approvedByActorId | String |
| IntegrityAssessment | 13 | approvedAt | Instant |
| IntegrityAssessment | 14 | workflowInstanceId | String |
| IntegrityAssessment | 15 | auditReferenceId | String |
| IntegrityAssessment | 16 | createdAt | Instant |
| IntegrityAssessment | 17 | updatedAt | Instant |
| IntegrityCase | 1 | id | String |
| IntegrityCase | 2 | caseNumber | String |
| IntegrityCase | 3 | title | String |
| IntegrityCase | 4 | description | String |
| IntegrityCase | 5 | caseTypeId | String |
| IntegrityCase | 6 | status | IntegrityCaseStatus |
| IntegrityCase | 7 | severityId | String |
| IntegrityCase | 8 | topologyAssetTypeCode | String |
| IntegrityCase | 9 | topologyAssetId | String |
| IntegrityCase | 10 | topologyAssetCodeSnapshot | String |
| IntegrityCase | 11 | primaryDefectId | String |
| IntegrityCase | 12 | sourceIncidentId | String |
| IntegrityCase | 13 | sourceHseCaseId | String |
| IntegrityCase | 14 | responsibleOrganizationUnitId | String |
| IntegrityCase | 15 | workflowInstanceId | String |
| IntegrityCase | 16 | openedAt | Instant |
| IntegrityCase | 17 | closedAt | Instant |
| IntegrityCase | 18 | openedByActorId | String |
| IntegrityCase | 19 | createdAt | Instant |
| IntegrityCase | 20 | updatedAt | Instant |
| IntegrityProgram | 1 | id | String |
| IntegrityProgram | 2 | code | String |
| IntegrityProgram | 3 | nameAr | String |
| IntegrityProgram | 4 | nameFr | String |
| IntegrityProgram | 5 | nameEn | String |
| IntegrityProgram | 6 | description | String |
| IntegrityProgram | 7 | programTypeId | String |
| IntegrityProgram | 8 | ownerOrganizationUnitId | String |
| IntegrityProgram | 9 | ownerOrganizationUnitNameSnapshot | String |
| IntegrityProgram | 10 | status | IntegrityProgramStatus |
| IntegrityProgram | 11 | plannedStartAt | Instant |
| IntegrityProgram | 12 | plannedEndAt | Instant |
| IntegrityProgram | 13 | actualStartAt | Instant |
| IntegrityProgram | 14 | actualEndAt | Instant |
| IntegrityProgram | 15 | createdByActorId | String |
| IntegrityProgram | 16 | createdAt | Instant |
| IntegrityProgram | 17 | updatedAt | Instant |
| PipelineDefect | 1 | id | String |
| PipelineDefect | 2 | defectNumber | String |
| PipelineDefect | 3 | defectTypeId | String |
| PipelineDefect | 4 | threatType | ThreatType |
| PipelineDefect | 5 | status | DefectStatus |
| PipelineDefect | 6 | severity | FindingSeverity |
| PipelineDefect | 7 | topologyAssetTypeCode | String |
| PipelineDefect | 8 | topologyAssetId | String |
| PipelineDefect | 9 | topologyAssetCodeSnapshot | String |
| PipelineDefect | 10 | kilometerPoint | BigDecimal |
| PipelineDefect | 11 | latitude | BigDecimal |
| PipelineDefect | 12 | longitude | BigDecimal |
| PipelineDefect | 13 | description | String |
| PipelineDefect | 14 | detectedAt | Instant |
| PipelineDefect | 15 | closedAt | Instant |
| PipelineDefect | 16 | sourceFindingId | String |
| PipelineDefect | 17 | createdAt | Instant |
| PipelineDefect | 18 | updatedAt | Instant |

### HMC-013 reconciliation

```text
baseline model files = 4
extracted models     = 4
exceptions           = 0
declared fields      = 72
zero-field models    = 0
```

The scan records only declarations under `integrity/domain/model`. Integrity domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, inspection-data adapter, or database column metadata was mixed into this module scan.

## 23. HMC-014 — Leak Detection module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `leakdetection`  
**Baseline model files:** 3  
**Scanned models:** 3  
**Declared fields/components:** 47  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Leak Detection model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| LeakCandidate | record | 17 | `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java` | Extracted |
| LeakDetectionCase | record | 18 | `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java` | Extracted |
| LeakEscalationReference | record | 12 | `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java` | Extracted |

### Leak Detection fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| LeakCandidate | 1 | id | String |
| LeakCandidate | 2 | runId | String |
| LeakCandidate | 3 | profileId | String |
| LeakCandidate | 4 | candidateNumber | String |
| LeakCandidate | 5 | topologyAssetType | String |
| LeakCandidate | 6 | topologyAssetId | String |
| LeakCandidate | 7 | topologyAssetCode | String |
| LeakCandidate | 8 | topologyAssetNameSnapshot | String |
| LeakCandidate | 9 | suspectedAt | Instant |
| LeakCandidate | 10 | firstEvidenceAt | Instant |
| LeakCandidate | 11 | confidenceScore | BigDecimal |
| LeakCandidate | 12 | severityLevel | LeakSeverityLevel |
| LeakCandidate | 13 | status | LeakCandidateStatus |
| LeakCandidate | 14 | summary | String |
| LeakCandidate | 15 | correlationId | String |
| LeakCandidate | 16 | createdAt | Instant |
| LeakCandidate | 17 | updatedAt | Instant |
| LeakDetectionCase | 1 | id | String |
| LeakDetectionCase | 2 | caseNumber | String |
| LeakDetectionCase | 3 | primaryCandidateId | String |
| LeakDetectionCase | 4 | topologyAssetType | String |
| LeakDetectionCase | 5 | topologyAssetId | String |
| LeakDetectionCase | 6 | topologyAssetCode | String |
| LeakDetectionCase | 7 | owningOrganizationUnitId | String |
| LeakDetectionCase | 8 | status | LeakDetectionCaseStatus |
| LeakDetectionCase | 9 | severityLevel | LeakSeverityLevel |
| LeakDetectionCase | 10 | confidenceScore | BigDecimal |
| LeakDetectionCase | 11 | openedAt | Instant |
| LeakDetectionCase | 12 | closedAt | Instant |
| LeakDetectionCase | 13 | openedByActorId | String |
| LeakDetectionCase | 14 | closedByActorId | String |
| LeakDetectionCase | 15 | closureReasonId | String |
| LeakDetectionCase | 16 | correlationId | String |
| LeakDetectionCase | 17 | createdAt | Instant |
| LeakDetectionCase | 18 | updatedAt | Instant |
| LeakEscalationReference | 1 | id | String |
| LeakEscalationReference | 2 | caseId | String |
| LeakEscalationReference | 3 | candidateId | String |
| LeakEscalationReference | 4 | targetType | LeakEscalationTargetType |
| LeakEscalationReference | 5 | targetReferenceId | String |
| LeakEscalationReference | 6 | targetCodeSnapshot | String |
| LeakEscalationReference | 7 | targetNameSnapshot | String |
| LeakEscalationReference | 8 | status | LeakEscalationStatus |
| LeakEscalationReference | 9 | escalatedByActorId | String |
| LeakEscalationReference | 10 | escalatedAt | Instant |
| LeakEscalationReference | 11 | reasonText | String |
| LeakEscalationReference | 12 | correlationId | String |

### HMC-014 reconciliation

```text
baseline model files = 3
extracted models     = 3
exceptions           = 0
declared fields      = 47
zero-field models    = 0
```

The scan records only declarations under `leakdetection/domain/model`. Leak Detection domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, LeakDetectionAPI compute model, or database column metadata was mixed into this module scan.

## 24. HMC-015 — Monitoring module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `monitoring`  
**Baseline model files:** 2  
**Scanned models:** 2  
**Declared fields/components:** 37  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Monitoring model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| MonitoringRule | record | 17 | `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java` | Extracted |
| PlanActualDeviation | record | 20 | `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java` | Extracted |

### Monitoring fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| MonitoringRule | 1 | id | String |
| MonitoringRule | 2 | code | String |
| MonitoringRule | 3 | nameAr | String |
| MonitoringRule | 4 | nameFr | String |
| MonitoringRule | 5 | nameEn | String |
| MonitoringRule | 6 | ruleType | MonitoringRuleType |
| MonitoringRule | 7 | evaluationFrequencyId | String |
| MonitoringRule | 8 | topologyAssetType | String |
| MonitoringRule | 9 | topologyAssetId | String |
| MonitoringRule | 10 | topologyAssetCode | String |
| MonitoringRule | 11 | telemetryPointId | String |
| MonitoringRule | 12 | planningTargetTypeId | String |
| MonitoringRule | 13 | expression | String |
| MonitoringRule | 14 | status | MonitoringLifecycleStatus |
| MonitoringRule | 15 | createdByActorId | String |
| MonitoringRule | 16 | createdAt | Instant |
| MonitoringRule | 17 | updatedAt | Instant |
| PlanActualDeviation | 1 | id | String |
| PlanActualDeviation | 2 | evaluationId | String |
| PlanActualDeviation | 3 | planTargetId | String |
| PlanActualDeviation | 4 | expectedFlowStateId | String |
| PlanActualDeviation | 5 | trustedTelemetryReadingId | String |
| PlanActualDeviation | 6 | telemetryPointId | String |
| PlanActualDeviation | 7 | topologyAssetType | String |
| PlanActualDeviation | 8 | topologyAssetId | String |
| PlanActualDeviation | 9 | topologyAssetCode | String |
| PlanActualDeviation | 10 | actualValue | BigDecimal |
| PlanActualDeviation | 11 | expectedValue | BigDecimal |
| PlanActualDeviation | 12 | differenceValue | BigDecimal |
| PlanActualDeviation | 13 | differencePercent | BigDecimal |
| PlanActualDeviation | 14 | unitId | String |
| PlanActualDeviation | 15 | severity | DeviationSeverity |
| PlanActualDeviation | 16 | status | DeviationStatus |
| PlanActualDeviation | 17 | detectedAt | Instant |
| PlanActualDeviation | 18 | resolvedAt | Instant |
| PlanActualDeviation | 19 | reasonCode | String |
| PlanActualDeviation | 20 | reasonMessage | String |

### HMC-015 reconciliation

```text
baseline model files = 2
extracted models     = 2
exceptions           = 0
declared fields      = 37
zero-field models    = 0
```

The scan records only declarations under `monitoring/domain/model`. Monitoring domain value types referenced by record components are field types, not independent model rows for this catalogue scope.

No JPA entity, API DTO, migration, application contract, infrastructure model, telemetry implementation, planning implementation, or database column metadata was mixed into this module scan.

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

## 26. HMC-017 — Organization module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `organization`  
**Baseline model files:** 17  
**Scanned models:** 17  
**Declared fields/components:** 179  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Organization model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| AdministrativeDistrict | record | 9 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeDistrict.java` | Extracted |
| AdministrativeLocality | record | 10 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeLocality.java` | Extracted |
| AdministrativeState | record | 8 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeState.java` | Extracted |
| Employee | record | 22 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/Employee.java` | Extracted |
| EmployeeAddress | record | 12 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAddress.java` | Extracted |
| EmployeeAssignment | record | 10 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java` | Extracted |
| OperationalScope | record | 3 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/OperationalScope.java` | Extracted |
| OrganizationContactPoint | record | 10 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationContactPoint.java` | Extracted |
| OrganizationDelegation | record | 10 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java` | Extracted |
| OrganizationHierarchySnapshot | record | 8 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java` | Extracted |
| OrganizationUnit | record | 12 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java` | Extracted |
| OrganizationUnitType | record | 12 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java` | Extracted |
| Position | record | 12 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java` | Extracted |
| ReportingLine | record | 9 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java` | Extracted |
| ResponsibilityAssignment | record | 11 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java` | Extracted |
| Shift | record | 12 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java` | Extracted |
| ShiftAssignment | record | 9 | `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java` | Extracted |

### Organization fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| AdministrativeDistrict | 1 | id | String |
| AdministrativeDistrict | 2 | stateId | String |
| AdministrativeDistrict | 3 | code | String |
| AdministrativeDistrict | 4 | nameAr | String |
| AdministrativeDistrict | 5 | nameFr | String |
| AdministrativeDistrict | 6 | nameEn | String |
| AdministrativeDistrict | 7 | active | boolean |
| AdministrativeDistrict | 8 | createdAt | Instant |
| AdministrativeDistrict | 9 | updatedAt | Instant |
| AdministrativeLocality | 1 | id | String |
| AdministrativeLocality | 2 | districtId | String |
| AdministrativeLocality | 3 | code | String |
| AdministrativeLocality | 4 | nameAr | String |
| AdministrativeLocality | 5 | nameFr | String |
| AdministrativeLocality | 6 | nameEn | String |
| AdministrativeLocality | 7 | postalCode | String |
| AdministrativeLocality | 8 | active | boolean |
| AdministrativeLocality | 9 | createdAt | Instant |
| AdministrativeLocality | 10 | updatedAt | Instant |
| AdministrativeState | 1 | id | String |
| AdministrativeState | 2 | code | String |
| AdministrativeState | 3 | nameAr | String |
| AdministrativeState | 4 | nameFr | String |
| AdministrativeState | 5 | nameEn | String |
| AdministrativeState | 6 | active | boolean |
| AdministrativeState | 7 | createdAt | Instant |
| AdministrativeState | 8 | updatedAt | Instant |
| Employee | 1 | id | String |
| Employee | 2 | employeeNumber | String |
| Employee | 3 | firstNameAr | String |
| Employee | 4 | lastNameAr | String |
| Employee | 5 | firstNameLt | String |
| Employee | 6 | lastNameLt | String |
| Employee | 7 | displayNameAr | String |
| Employee | 8 | displayNameLt | String |
| Employee | 9 | dateOfBirth | LocalDate |
| Employee | 10 | birthLocalityId | String |
| Employee | 11 | birthPlaceAr | String |
| Employee | 12 | birthPlaceFr | String |
| Employee | 13 | birthPlaceEn | String |
| Employee | 14 | emailAddress | String |
| Employee | 15 | mobileNumber | String |
| Employee | 16 | employeeType | EmployeeType |
| Employee | 17 | status | EmployeeStatus |
| Employee | 18 | identityUserReference | String |
| Employee | 19 | hiredAt | Instant |
| Employee | 20 | terminatedAt | Instant |
| Employee | 21 | createdAt | Instant |
| Employee | 22 | updatedAt | Instant |
| EmployeeAddress | 1 | id | String |
| EmployeeAddress | 2 | employeeId | String |
| EmployeeAddress | 3 | addressType | AddressType |
| EmployeeAddress | 4 | localityId | String |
| EmployeeAddress | 5 | streetLine1 | String |
| EmployeeAddress | 6 | streetLine2 | String |
| EmployeeAddress | 7 | postalCodeSnapshot | String |
| EmployeeAddress | 8 | primaryAddress | boolean |
| EmployeeAddress | 9 | validFrom | Instant |
| EmployeeAddress | 10 | validTo | Instant |
| EmployeeAddress | 11 | createdAt | Instant |
| EmployeeAddress | 12 | updatedAt | Instant |
| EmployeeAssignment | 1 | id | String |
| EmployeeAssignment | 2 | employeeId | String |
| EmployeeAssignment | 3 | organizationUnitId | String |
| EmployeeAssignment | 4 | positionId | String |
| EmployeeAssignment | 5 | assignmentType | AssignmentType |
| EmployeeAssignment | 6 | validFrom | Instant |
| EmployeeAssignment | 7 | validTo | Instant |
| EmployeeAssignment | 8 | status | AssignmentStatus |
| EmployeeAssignment | 9 | createdAt | Instant |
| EmployeeAssignment | 10 | updatedAt | Instant |
| OperationalScope | 1 | id | Long |
| OperationalScope | 2 | type | OperationalScopeType |
| OperationalScope | 3 | targetId | String |
| OrganizationContactPoint | 1 | id | String |
| OrganizationContactPoint | 2 | contactPointType | ContactPointType |
| OrganizationContactPoint | 3 | target | ContactPointTargetReference |
| OrganizationContactPoint | 4 | label | String |
| OrganizationContactPoint | 5 | value | String |
| OrganizationContactPoint | 6 | primaryContact | boolean |
| OrganizationContactPoint | 7 | emergencyContact | boolean |
| OrganizationContactPoint | 8 | active | boolean |
| OrganizationContactPoint | 9 | createdAt | Instant |
| OrganizationContactPoint | 10 | updatedAt | Instant |
| OrganizationDelegation | 1 | id | String |
| OrganizationDelegation | 2 | delegatorEmployeeId | String |
| OrganizationDelegation | 3 | delegateEmployeeId | String |
| OrganizationDelegation | 4 | responsibilityAssignmentId | String |
| OrganizationDelegation | 5 | reason | String |
| OrganizationDelegation | 6 | validFrom | Instant |
| OrganizationDelegation | 7 | validTo | Instant |
| OrganizationDelegation | 8 | status | DelegationStatus |
| OrganizationDelegation | 9 | createdAt | Instant |
| OrganizationDelegation | 10 | revokedAt | Instant |
| OrganizationHierarchySnapshot | 1 | id | String |
| OrganizationHierarchySnapshot | 2 | snapshotCode | String |
| OrganizationHierarchySnapshot | 3 | capturedAt | Instant |
| OrganizationHierarchySnapshot | 4 | capturedByEmployeeId | String |
| OrganizationHierarchySnapshot | 5 | status | HierarchySnapshotStatus |
| OrganizationHierarchySnapshot | 6 | snapshotPayload | String |
| OrganizationHierarchySnapshot | 7 | description | String |
| OrganizationHierarchySnapshot | 8 | createdAt | Instant |
| OrganizationUnit | 1 | id | String |
| OrganizationUnit | 2 | code | String |
| OrganizationUnit | 3 | nameAr | String |
| OrganizationUnit | 4 | nameFr | String |
| OrganizationUnit | 5 | nameEn | String |
| OrganizationUnit | 6 | unitTypeId | String |
| OrganizationUnit | 7 | parentUnitId | String |
| OrganizationUnit | 8 | status | OrganizationUnitStatus |
| OrganizationUnit | 9 | validFrom | Instant |
| OrganizationUnit | 10 | validTo | Instant |
| OrganizationUnit | 11 | createdAt | Instant |
| OrganizationUnit | 12 | updatedAt | Instant |
| OrganizationUnitType | 1 | id | String |
| OrganizationUnitType | 2 | code | String |
| OrganizationUnitType | 3 | kind | OrganizationUnitKind |
| OrganizationUnitType | 4 | nameAr | String |
| OrganizationUnitType | 5 | nameFr | String |
| OrganizationUnitType | 6 | nameEn | String |
| OrganizationUnitType | 7 | descriptionAr | String |
| OrganizationUnitType | 8 | descriptionFr | String |
| OrganizationUnitType | 9 | descriptionEn | String |
| OrganizationUnitType | 10 | active | boolean |
| OrganizationUnitType | 11 | createdAt | Instant |
| OrganizationUnitType | 12 | updatedAt | Instant |
| Position | 1 | id | String |
| Position | 2 | code | String |
| Position | 3 | titleAr | String |
| Position | 4 | titleFr | String |
| Position | 5 | titleEn | String |
| Position | 6 | level | PositionLevel |
| Position | 7 | descriptionAr | String |
| Position | 8 | descriptionFr | String |
| Position | 9 | descriptionEn | String |
| Position | 10 | status | PositionStatus |
| Position | 11 | createdAt | Instant |
| Position | 12 | updatedAt | Instant |
| ReportingLine | 1 | id | String |
| ReportingLine | 2 | reportingLineType | ReportingLineType |
| ReportingLine | 3 | source | ReportingSubjectReference |
| ReportingLine | 4 | target | ReportingSubjectReference |
| ReportingLine | 5 | validFrom | Instant |
| ReportingLine | 6 | validTo | Instant |
| ReportingLine | 7 | active | boolean |
| ReportingLine | 8 | createdAt | Instant |
| ReportingLine | 9 | updatedAt | Instant |
| ResponsibilityAssignment | 1 | id | String |
| ResponsibilityAssignment | 2 | responsibilityType | ResponsibilityType |
| ResponsibilityAssignment | 3 | assigneeType | ResponsibilityAssigneeType |
| ResponsibilityAssignment | 4 | assigneeId | String |
| ResponsibilityAssignment | 5 | scopeId | Long |
| ResponsibilityAssignment | 6 | description | String |
| ResponsibilityAssignment | 7 | validFrom | Instant |
| ResponsibilityAssignment | 8 | validTo | Instant |
| ResponsibilityAssignment | 9 | status | AssignmentStatus |
| ResponsibilityAssignment | 10 | createdAt | Instant |
| ResponsibilityAssignment | 11 | updatedAt | Instant |
| Shift | 1 | id | String |
| Shift | 2 | code | String |
| Shift | 3 | nameAr | String |
| Shift | 4 | nameFr | String |
| Shift | 5 | nameEn | String |
| Shift | 6 | shiftType | ShiftType |
| Shift | 7 | startTime | String |
| Shift | 8 | endTime | String |
| Shift | 9 | timezone | String |
| Shift | 10 | active | boolean |
| Shift | 11 | createdAt | Instant |
| Shift | 12 | updatedAt | Instant |
| ShiftAssignment | 1 | id | String |
| ShiftAssignment | 2 | employeeId | String |
| ShiftAssignment | 3 | shiftId | String |
| ShiftAssignment | 4 | organizationUnitId | String |
| ShiftAssignment | 5 | validFrom | Instant |
| ShiftAssignment | 6 | validTo | Instant |
| ShiftAssignment | 7 | status | ShiftAssignmentStatus |
| ShiftAssignment | 8 | createdAt | Instant |
| ShiftAssignment | 9 | updatedAt | Instant |

### HMC-017 reconciliation

```text
baseline model files = 17
extracted models     = 17
exceptions           = 0
declared fields      = 179
zero-field models    = 0
```

The scan records only declarations under `organization/domain/model`. Organization domain value types and reference types used by record components remain declared field types and are not expanded into independent model rows.

This catalogue preserves the pinned source exactly. In particular, `OrganizationUnitType` is catalogued because it is a direct model file at the pinned baseline; HMC-017 does not redesign or correct organization modeling.

No JPA entity, API DTO, migration, application contract, infrastructure model, identity model, or database column metadata was mixed into this module scan.

## 27. HMC-018 — Party module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `party`  
**Baseline model files:** 2  
**Scanned models:** 2  
**Declared fields/components:** 21  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Party model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| Party | record | 12 | `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java` | Extracted |
| PartyRoleAssignment | record | 9 | `src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java` | Extracted |

### Party fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| Party | 1 | id | String |
| Party | 2 | code | String |
| Party | 3 | partyTypeId | String |
| Party | 4 | legalName | String |
| Party | 5 | tradeName | String |
| Party | 6 | shortName | String |
| Party | 7 | countryCode | String |
| Party | 8 | jurisdictionCode | String |
| Party | 9 | status | PartyStatus |
| Party | 10 | primaryRoleCodeSnapshot | String |
| Party | 11 | createdAt | Instant |
| Party | 12 | updatedAt | Instant |
| PartyRoleAssignment | 1 | id | String |
| PartyRoleAssignment | 2 | partyId | String |
| PartyRoleAssignment | 3 | roleId | String |
| PartyRoleAssignment | 4 | validFrom | Instant |
| PartyRoleAssignment | 5 | validTo | Instant |
| PartyRoleAssignment | 6 | status | PartyRoleAssignmentStatus |
| PartyRoleAssignment | 7 | qualificationRequired | boolean |
| PartyRoleAssignment | 8 | createdAt | Instant |
| PartyRoleAssignment | 9 | updatedAt | Instant |

### HMC-018 reconciliation

```text
baseline model files = 2
extracted models     = 2
exceptions           = 0
declared fields      = 21
zero-field models    = 0
```

The scan records only declarations under `party/domain/model`. Party domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure model, organization model, or database column metadata was mixed into this module scan.

## 28. HMC-019 — Planning module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `planning`  
**Baseline model files:** 5  
**Scanned models:** 5  
**Declared fields/components:** 95  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Planning model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| Nomination | record | 26 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java` | Extracted |
| OperationalPlan | record | 19 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java` | Extracted |
| PlanRevision | record | 15 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java` | Extracted |
| PlanTarget | record | 22 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java` | Extracted |
| PlanningPeriod | record | 13 | `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java` | Extracted |

### Planning fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| Nomination | 1 | id | String |
| Nomination | 2 | revisionId | String |
| Nomination | 3 | scenarioId | String |
| Nomination | 4 | code | String |
| Nomination | 5 | nominationTypeId | String |
| Nomination | 6 | productTypeId | String |
| Nomination | 7 | quantity | BigDecimal |
| Nomination | 8 | quantityUnitId | String |
| Nomination | 9 | rate | BigDecimal |
| Nomination | 10 | rateUnitId | String |
| Nomination | 11 | sourceAssetType | String |
| Nomination | 12 | sourceAssetId | String |
| Nomination | 13 | sourceAssetCode | String |
| Nomination | 14 | destinationAssetType | String |
| Nomination | 15 | destinationAssetId | String |
| Nomination | 16 | destinationAssetCode | String |
| Nomination | 17 | shipperPartyId | String |
| Nomination | 18 | shipperPartyCodeSnapshot | String |
| Nomination | 19 | counterpartyId | String |
| Nomination | 20 | contractReferenceId | String |
| Nomination | 21 | priority | Integer |
| Nomination | 22 | status | NominationStatus |
| Nomination | 23 | periodStart | Instant |
| Nomination | 24 | periodEnd | Instant |
| Nomination | 25 | createdAt | Instant |
| Nomination | 26 | updatedAt | Instant |
| OperationalPlan | 1 | id | String |
| OperationalPlan | 2 | periodId | String |
| OperationalPlan | 3 | code | String |
| OperationalPlan | 4 | nameAr | String |
| OperationalPlan | 5 | nameFr | String |
| OperationalPlan | 6 | nameEn | String |
| OperationalPlan | 7 | planTypeId | String |
| OperationalPlan | 8 | productTypeId | String |
| OperationalPlan | 9 | topologyScopeType | String |
| OperationalPlan | 10 | topologyScopeId | String |
| OperationalPlan | 11 | topologyScopeCode | String |
| OperationalPlan | 12 | topologyScopeNameSnapshot | String |
| OperationalPlan | 13 | responsibleOrganizationUnitId | String |
| OperationalPlan | 14 | status | OperationalPlanStatus |
| OperationalPlan | 15 | currentRevisionId | String |
| OperationalPlan | 16 | approvedRevisionId | String |
| OperationalPlan | 17 | createdByActorId | String |
| OperationalPlan | 18 | createdAt | Instant |
| OperationalPlan | 19 | updatedAt | Instant |
| PlanRevision | 1 | id | String |
| PlanRevision | 2 | planId | String |
| PlanRevision | 3 | revisionNumber | int |
| PlanRevision | 4 | revisionCode | String |
| PlanRevision | 5 | status | PlanRevisionStatus |
| PlanRevision | 6 | changeReasonCodeId | String |
| PlanRevision | 7 | changeReasonText | String |
| PlanRevision | 8 | baseRevisionId | String |
| PlanRevision | 9 | submittedByActorId | String |
| PlanRevision | 10 | submittedAt | Instant |
| PlanRevision | 11 | approvedByActorId | String |
| PlanRevision | 12 | approvedAt | Instant |
| PlanRevision | 13 | workflowInstanceId | String |
| PlanRevision | 14 | createdAt | Instant |
| PlanRevision | 15 | updatedAt | Instant |
| PlanTarget | 1 | id | String |
| PlanTarget | 2 | revisionId | String |
| PlanTarget | 3 | scenarioId | String |
| PlanTarget | 4 | nominationId | String |
| PlanTarget | 5 | targetTypeId | String |
| PlanTarget | 6 | topologyAssetType | String |
| PlanTarget | 7 | topologyAssetId | String |
| PlanTarget | 8 | topologyAssetCode | String |
| PlanTarget | 9 | topologyAssetNameSnapshot | String |
| PlanTarget | 10 | telemetryPointId | String |
| PlanTarget | 11 | telemetryPointCodeSnapshot | String |
| PlanTarget | 12 | targetValue | BigDecimal |
| PlanTarget | 13 | targetTextValue | String |
| PlanTarget | 14 | unitId | String |
| PlanTarget | 15 | toleranceLow | BigDecimal |
| PlanTarget | 16 | toleranceHigh | BigDecimal |
| PlanTarget | 17 | validFrom | Instant |
| PlanTarget | 18 | validTo | Instant |
| PlanTarget | 19 | priority | Integer |
| PlanTarget | 20 | status | PlanTargetStatus |
| PlanTarget | 21 | createdAt | Instant |
| PlanTarget | 22 | updatedAt | Instant |
| PlanningPeriod | 1 | id | String |
| PlanningPeriod | 2 | code | String |
| PlanningPeriod | 3 | nameAr | String |
| PlanningPeriod | 4 | nameFr | String |
| PlanningPeriod | 5 | nameEn | String |
| PlanningPeriod | 6 | periodTypeId | String |
| PlanningPeriod | 7 | periodStart | Instant |
| PlanningPeriod | 8 | periodEnd | Instant |
| PlanningPeriod | 9 | timeZone | String |
| PlanningPeriod | 10 | status | PlanningPeriodStatus |
| PlanningPeriod | 11 | createdByActorId | String |
| PlanningPeriod | 12 | createdAt | Instant |
| PlanningPeriod | 13 | updatedAt | Instant |

### HMC-019 reconciliation

```text
baseline model files = 5
extracted models     = 5
exceptions           = 0
declared fields      = 95
zero-field models    = 0
```

The scan records only declarations under `planning/domain/model`. Planning domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure model, monitoring implementation, party implementation, topology implementation, or database column metadata was mixed into this module scan.

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

## 31. HMC-022 — Simulation module scan evidence

**Source commit:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Module:** `simulation`  
**Baseline model files:** 6  
**Scanned models:** 6  
**Declared fields/components:** 81  
**Zero-field models:** 0  
**Extraction exceptions:** 0

### Simulation model index

| Model | Kind | Declared field count | Source path | Status |
|---|---|---:|---|---|
| SimulationCandidateChange | record | 13 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java` | Extracted |
| SimulationModel | record | 12 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java` | Extracted |
| SimulationOptimizationCandidate | record | 11 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java` | Extracted |
| SimulationRecommendation | record | 13 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java` | Extracted |
| SimulationRun | record | 16 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java` | Extracted |
| SimulationScenario | record | 16 | `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java` | Extracted |

### Simulation fields and declared Java types

| Model | Ordinal | Field | Declared Java Type |
|---|---:|---|---|
| SimulationCandidateChange | 1 | id | String |
| SimulationCandidateChange | 2 | candidateId | String |
| SimulationCandidateChange | 3 | changeTypeId | String |
| SimulationCandidateChange | 4 | targetType | String |
| SimulationCandidateChange | 5 | targetId | String |
| SimulationCandidateChange | 6 | beforeValue | String |
| SimulationCandidateChange | 7 | afterValue | String |
| SimulationCandidateChange | 8 | unitCode | String |
| SimulationCandidateChange | 9 | requiresTopologyChange | boolean |
| SimulationCandidateChange | 10 | requiresOperationalProcedure | boolean |
| SimulationCandidateChange | 11 | safetyCritical | boolean |
| SimulationCandidateChange | 12 | explanation | String |
| SimulationCandidateChange | 13 | createdAt | Instant |
| SimulationModel | 1 | id | String |
| SimulationModel | 2 | code | String |
| SimulationModel | 3 | nameAr | String |
| SimulationModel | 4 | nameFr | String |
| SimulationModel | 5 | nameEn | String |
| SimulationModel | 6 | modelTypeId | String |
| SimulationModel | 7 | topologyScopeType | String |
| SimulationModel | 8 | topologyScopeId | String |
| SimulationModel | 9 | status | SimulationModelStatus |
| SimulationModel | 10 | description | String |
| SimulationModel | 11 | createdAt | Instant |
| SimulationModel | 12 | updatedAt | Instant |
| SimulationOptimizationCandidate | 1 | id | String |
| SimulationOptimizationCandidate | 2 | runId | String |
| SimulationOptimizationCandidate | 3 | candidateNumber | int |
| SimulationOptimizationCandidate | 4 | candidateStatus | SimulationCandidateStatus |
| SimulationOptimizationCandidate | 5 | feasible | boolean |
| SimulationOptimizationCandidate | 6 | objectiveScore | BigDecimal |
| SimulationOptimizationCandidate | 7 | rank | Integer |
| SimulationOptimizationCandidate | 8 | summaryText | String |
| SimulationOptimizationCandidate | 9 | selectedByActorId | String |
| SimulationOptimizationCandidate | 10 | selectedAt | Instant |
| SimulationOptimizationCandidate | 11 | createdAt | Instant |
| SimulationRecommendation | 1 | id | String |
| SimulationRecommendation | 2 | runId | String |
| SimulationRecommendation | 3 | candidateId | String |
| SimulationRecommendation | 4 | recommendationTypeId | String |
| SimulationRecommendation | 5 | recommendationStatus | SimulationRecommendationStatus |
| SimulationRecommendation | 6 | title | String |
| SimulationRecommendation | 7 | description | String |
| SimulationRecommendation | 8 | confidenceLevelId | String |
| SimulationRecommendation | 9 | targetModule | String |
| SimulationRecommendation | 10 | targetProposalReference | String |
| SimulationRecommendation | 11 | publishedByActorId | String |
| SimulationRecommendation | 12 | publishedAt | Instant |
| SimulationRecommendation | 13 | createdAt | Instant |
| SimulationRun | 1 | id | String |
| SimulationRun | 2 | scenarioId | String |
| SimulationRun | 3 | modelVersionId | String |
| SimulationRun | 4 | inputSnapshotId | String |
| SimulationRun | 5 | runTypeId | String |
| SimulationRun | 6 | status | SimulationRunStatus |
| SimulationRun | 7 | requestedByActorId | String |
| SimulationRun | 8 | requestedByDisplayNameSnapshot | String |
| SimulationRun | 9 | queuedAt | Instant |
| SimulationRun | 10 | startedAt | Instant |
| SimulationRun | 11 | completedAt | Instant |
| SimulationRun | 12 | durationMillis | Long |
| SimulationRun | 13 | solverProfileId | String |
| SimulationRun | 14 | correlationId | String |
| SimulationRun | 15 | failureReason | String |
| SimulationRun | 16 | createdAt | Instant |
| SimulationScenario | 1 | id | String |
| SimulationScenario | 2 | code | String |
| SimulationScenario | 3 | nameAr | String |
| SimulationScenario | 4 | nameFr | String |
| SimulationScenario | 5 | nameEn | String |
| SimulationScenario | 6 | scenarioTypeId | String |
| SimulationScenario | 7 | modelId | String |
| SimulationScenario | 8 | modelVersionId | String |
| SimulationScenario | 9 | topologySnapshotId | String |
| SimulationScenario | 10 | planningReferenceId | String |
| SimulationScenario | 11 | monitoringContextId | String |
| SimulationScenario | 12 | status | SimulationScenarioStatus |
| SimulationScenario | 13 | createdByActorId | String |
| SimulationScenario | 14 | createdByDisplayNameSnapshot | String |
| SimulationScenario | 15 | createdAt | Instant |
| SimulationScenario | 16 | updatedAt | Instant |

### HMC-022 reconciliation

```text
baseline model files = 6
extracted models     = 6
exceptions           = 0
declared fields      = 81
zero-field models    = 0
```

The scan records only declarations under `simulation/domain/model`. Simulation domain value types referenced by record components remain declared field types and are not expanded into independent model rows.

No JPA entity, API DTO, migration, application contract, infrastructure model, solver implementation, topology implementation, planning implementation, monitoring implementation, or database column metadata was mixed into this module scan.

## 32. Current next task

```text
HMC-023 — docs(catalogue): scan telemetry domain models
```

Do not start HMC-024 until HMC-023 is completed and its module counts are reported.

