# HidraAPI Target Model Semantic Review Roadmap

**Status:** Active — HMS-002 completed; candidate dependency evidence inventoried, classification not yet started.

**Repository:** `CHOUABBIA-AMINE/HidraAPI`  
**Roadmap:** `docs/roadmap/model-semantic-review.md`  
**Roadmap code:** `HMS`  
**Created:** 2026-10-02  
**Live baseline at creation:** `5e289aa31a5d622e50176d69fd858c3ea0b4aa34`  
**Catalogue source:** `docs/roadmap/model-field-catalogue.md`  
**Pinned catalogue model source:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Catalogue scope:** 24 modules, 123 direct domain models, 1,867 declared fields/components  
**Execution mode:** exactly one roadmap task/commit at a time; never continue automatically.

## 1. Purpose

Establish an authoritative semantic baseline for the 123 catalogued HidraAPI domain models before legacy-source classification, source-to-target mapping, or provisioning decisions continue.

The review sequence is dependency-driven:

```text
models with no confirmed model dependencies
    ↓
models whose confirmed dependencies have already been reviewed
    ↓
progressively deeper dependent models
    ↓
terminal/consumer models that are not prerequisites of other models
```

Within the same dependency level, models with more confirmed downstream dependents are reviewed first. Cyclic dependencies are never forced into an artificial linear order; strongly connected components are explicitly grouped and resolved.

## 2. Why this roadmap exists

HMC-001 through HMC-028 produced and validated a model/field/type catalogue, but that catalogue intentionally records Java declarations only. It does not establish:

- semantic meaning of `...Id` fields;
- persistence foreign keys;
- cross-module ownership;
- mandatory/optional relationship meaning;
- snapshot/reference-only semantics;
- lifecycle rules;
- multilingual modeling correctness;
- whether a model should be retained, revised, deferred, or removed.

HDP-004 and HDP-005 therefore remain paused until this target-model semantic review produces an approved baseline.

## 3. Authoritative evidence

Dependency and semantic decisions must use current/live repository evidence where available:

1. verified HMC catalogue and its pinned model source;
2. current domain model source;
3. JPA mappings/entities;
4. current Flyway migrations and foreign keys;
5. application ports/contracts;
6. enums/value types;
7. module roadmaps and architectural boundaries.

A Java field type alone is insufficient. For example, a `String organizationUnitId` may represent a real dependency even though the declared Java type is only `String`.

Do not infer a dependency merely from similar names. Every edge must have evidence and a classification.

## 4. Dependency edge classifications

Every confirmed model-to-model edge must be classified as one of:

| Classification | Meaning |
|---|---|
| Persistence dependency | Backed by target persistence FK/constraint or equivalent owned persistence relation. |
| Domain reference | Explicit domain relationship not necessarily represented by a database FK. |
| Cross-module reference | Stable reference into another bounded context. |
| Optional reference | Semantically optional dependency. |
| Snapshot/reference-only | Snapshot/code/name field that must not be treated as an owning dependency. |
| Value/catalog dependency | Reference to controlled value/catalog semantics rather than aggregate ownership. |
| Unresolved | Evidence is insufficient or contradictory; interactive decision required. |

Snapshot fields such as `...NameSnapshot` or `...CodeSnapshot` are not dependencies unless repository evidence establishes otherwise.

## 5. Graph and ordering rules

For each of the 123 models record:

- module;
- model;
- confirmed upstream dependencies;
- dependency evidence;
- dependency classification;
- confirmed downstream dependents;
- inbound dependent count;
- outbound dependency count;
- dependency depth;
- strongly connected component, if any;
- review level;
- review readiness;
- unresolved edges.

Ordering algorithm:

1. validate all dependency edges first;
2. collapse strongly connected components for ordering;
3. topologically order the resulting graph;
4. Level 0 contains models with no confirmed upstream model dependency;
5. Level N contains models whose confirmed upstream dependencies are in prior levels;
6. within a level, sort by downstream dependent count descending;
7. use module then model name only as deterministic tie-breakers;
8. terminal/consumer models naturally appear toward the end because they have few or no downstream dependents.

## 6. Interactive model-review protocol

HMS-005 will generate the authoritative review register and assign one task code per model:

```text
HMSR-001
HMSR-002
...
HMSR-123
```

The codes and exact commit messages are generated only after dependency ordering is validated. Do not preassign model codes before HMS-005.

Each interactive model session must present:

- model identity and bounded context;
- dependency level and why it appears there;
- confirmed upstream dependencies;
- confirmed downstream dependents;
- every declared field and type;
- intended business meaning of each field;
- `...Id`, code, snapshot and enum/reference semantics;
- mandatory vs optional semantics;
- lifecycle/status semantics;
- multilingual handling;
- bounded-context ownership;
- persistence/FK consistency;
- relevant pipeline/SONATRACH operational interpretation;
- unresolved questions requiring user decision.

Allowed model decisions:

```text
APPROVED
REVISE
DEFER
REMOVE
```

A review task records the decision and evidence. It must not silently change production code. Any approved production-model correction requires its own appropriate module roadmap/task before the semantic baseline can treat that correction as implemented.

## 7. Task roadmap

| Code | Exact commit message | Deliverable / acceptance | Status |
|---|---|---|---|
| HMS-001 | `docs(model-review): establish target model semantic review roadmap` | Create this roadmap and amend HDP so HDP-004 is explicitly paused behind the semantic-review prerequisite. No dependency analysis or production code changes. | **Completed** |
| HMS-002 | `docs(model-review): inventory target model dependency evidence` | Inventory candidate relationship evidence for all 123 models from domain source, JPA, Flyway, contracts and enums. Every candidate edge retains evidence/provenance; no review order yet. | **Completed** — 123/123 subjects reconciled; 627 candidate relationship fields inventoried; JPA/ports/Flyway/custom-type evidence registered; no classification/order performed. |
| HMS-003 | `docs(model-review): classify target model dependencies` | Resolve candidate edges into the dependency classifications in section 4; separate true dependencies from snapshots/codes and record unresolved edges. | Planned |
| HMS-004 | `docs(model-review): validate dependency graph and cycles` | Reconcile graph against persistence/contracts, identify strongly connected components, missing targets, contradictory edges and cross-module boundary concerns. | Planned |
| HMS-005 | `docs(model-review): generate dependency ordered model review register` | Compute deterministic review levels/order and generate the 123-model review register with `HMSR-001…HMSR-123` codes and exact per-model commit messages. | Planned |
| HMS-006 | `docs(model-review): reconcile interactive model decisions` | After all HMSR tasks are resolved, reconcile APPROVED/REVISE/DEFER/REMOVE decisions, outstanding corrections, unresolved cycles and dependency impacts. | Planned |
| HMS-007 | `docs(model-review): finalize approved target model semantic baseline` | Publish the final target-model semantic baseline only when every model has a resolved disposition and any required model corrections are implemented or explicitly deferred. | Planned |
| HMS-008 | `docs(data-provisioning): align provisioning roadmap to semantic model baseline` | Amend HDP target assumptions, record the approved HMS baseline, and determine whether HDP-004 may resume. Does not itself classify source data or start HDP-005. | Planned |

## 8. HMSR review tasks

The model-specific section is intentionally empty until HMS-005.

HMS-005 must populate a deterministic register containing, at minimum:

| Review Code | Level | Module | Model | Upstream dependencies | Downstream dependents | Decision | Status |
|---|---:|---|---|---|---:|---|---|

Each HMSR code is an independent interactive review commit. Execute only the model explicitly authorized by the user.

## 9. Completion gate

The semantic baseline is complete only when:

- all 123 catalogued models appear exactly once in the review register;
- every dependency edge has evidence/classification or an explicit unresolved decision;
- graph cycles are identified and resolved/grouped;
- every model has an interactive decision;
- model corrections required by REVISE/REMOVE decisions are implemented through their owning module roadmaps or explicitly deferred;
- dependency impacts are recalculated after corrections;
- HMS-007 is complete.

Only HMS-008 may then decide whether HDP-004 can resume.

## 10. HMS-002 — Candidate dependency evidence inventory

**Task:** HMS-002 — inventory target model dependency evidence  
**Live repository baseline:** `efa8b9033a64d509b9439c7f720ea7cf5ea6c9a7`  
**Catalogue baseline:** `5e301857882b59e9e35ecc474e9c6537d89cc96a`  
**Subject set:** 123 / 123 catalogue models  
**Live scoped reconciliation:** 123 present, 0 missing, 0 extra  
**Purpose:** evidentiary inventory only; no dependency classification, graph ordering, review levels, cycle decisions, or HMSR codes are produced here.

### 10.1 Evidence policy

A field is recorded as a candidate relationship field when its catalogue name ends in an ID/reference-ID form or when its declared type directly names one of the 123 subject models. This is deliberately broader than a confirmed dependency.

For every subject model this inventory retains the HMC declaration evidence, current live domain path/blob SHA, source-drift flag, matching JPA entity path/blob when present, the module application-port corpus, the module Flyway corpus, and non-scalar/custom domain component types with support-source paths where mechanically resolvable.

Field-name similarity is not a dependency decision. Snapshot/code/name fields, catalog IDs, actor IDs, polymorphic targets, workflow references and external references remain unclassified until HMS-003.

### 10.2 Reconciliation summary

| Measure | Result |
|---|---:|
| Subject models | 123 |
| Live domain paths present | 123 |
| Subject domain blobs changed since HMC pinned source | 0 |
| Matching subject JPA entities present | 122 |
| Candidate relationship fields inventoried | 627 |
| Direct subject-model typed candidate fields | 0 |
| Custom/non-scalar component types inventoried | 161 |
| Current Flyway files | 40 |
| Code-search hits for FOREIGN KEY in migration paths | 6 |
| Code-search hits for REFERENCES in migration paths | 0 |

These migration search counts are evidence signals only. HMS-003 must inspect relevant SQL definitions directly before deciding persistence dependency semantics.

### 10.3 Module evidence corpus

| Module | Subject models | App in/out ports | Flyway files | Domain support files |
|---|---:|---:|---:|---:|
| alarm | 4 | 13 | 3 | 27 |
| analytics | 7 | 28 | 1 | 24 |
| assets | 3 | 16 | 1 | 26 |
| audit | 4 | 15 | 3 | 18 |
| configuration | 3 | 13 | 1 | 23 |
| custody | 3 | 15 | 1 | 22 |
| documents | 4 | 17 | 1 | 21 |
| hse | 4 | 17 | 1 | 24 |
| identity | 15 | 27 | 2 | 47 |
| incident | 4 | 10 | 1 | 16 |
| integration | 4 | 15 | 1 | 37 |
| integrity | 4 | 16 | 4 | 21 |
| leakdetection | 3 | 9 | 1 | 24 |
| monitoring | 2 | 7 | 1 | 23 |
| notification | 4 | 18 | 1 | 27 |
| organization | 17 | 31 | 7 | 34 |
| party | 2 | 6 | 1 | 32 |
| planning | 5 | 13 | 1 | 24 |
| reporting | 4 | 21 | 1 | 29 |
| risk | 4 | 22 | 1 | 19 |
| simulation | 6 | 25 | 1 | 22 |
| telemetry | 4 | 9 | 1 | 23 |
| topology | 5 | 10 | 1 | 26 |
| workflow | 8 | 22 | 1 | 24 |

### 10.4 Per-model evidence register

#### alarm.Alarm

- Domain: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java` @ `8561b8257e7402d8853aa466bc2a03f2efb8c5c7` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmJpaEntity.java` @ `80729f1c1d4f897fc4a14cea7929645bb81e67fb`.
- Candidate relationship fields (15): `id:String`, `alarmTypeId:String`, `severityId:String`, `priorityId:String`, `sourceReferenceId:String`, `monitoringAlertCandidateId:String`, `monitoringEvaluationId:String`, `telemetryReadingId:String`, `planningTargetId:String`, `topologyAssetId:String`, `acknowledgedByActorId:String`, `owningOrganizationUnitId:String`, `workflowInstanceId:String`, `incidentId:String`, `correlationId:String`.
- Custom component types: `AlarmSourceType` → `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmSourceType.java`; `AlarmState` → `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmState.java`.
- Application-contract corpus: 13 current port files in module `alarm`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`.

#### alarm.AlarmAcknowledgement

- Domain: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmAcknowledgement.java` @ `bd31d48a5c0fb636324d5d0264226a1933b54dc0` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmAcknowledgementJpaEntity.java` @ `90828ba83daa08d8d76af9a5dec6fdbfb1152eaf`.
- Candidate relationship fields (5): `id:String`, `alarmId:String`, `acknowledgedByActorId:String`, `organizationUnitId:String`, `correlationId:String`.
- Custom component types: none.
- Application-contract corpus: 13 current port files in module `alarm`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`.

#### alarm.AlarmClosure

- Domain: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmClosure.java` @ `0630ea0024d45b2f7fd453e58bde143d5ef1b472` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmClosureJpaEntity.java` @ `4b4a794aaa65026ce7ee8da211a198b358adca85`.
- Candidate relationship fields (6): `id:String`, `alarmId:String`, `closureReasonId:String`, `closedByActorId:String`, `reviewWorkflowInstanceId:String`, `correlationId:String`.
- Custom component types: `AlarmClosureType` → `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmClosureType.java`.
- Application-contract corpus: 13 current port files in module `alarm`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`.

#### alarm.AlarmShelving

- Domain: `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java` @ `9f90489e42cca1d8b8a057d176aab1b383986a34` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmShelvingJpaEntity.java` @ `b98f96b2f8068a831210ed728300f83c6ff9686e`.
- Candidate relationship fields (6): `id:String`, `alarmId:String`, `shelvingReasonId:String`, `shelvedByActorId:String`, `unshelvedByActorId:String`, `correlationId:String`.
- Custom component types: `AlarmShelvingStatus` → `src/main/java/dz/sh/hidra/modules/alarm/domain/value/AlarmShelvingStatus.java`.
- Application-contract corpus: 13 current port files in module `alarm`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_008__create_alarm_tables.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_002__enforce_active_alarm_suppression_uniqueness.sql`.

#### analytics.AnalyticsDataset

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDataset.java` @ `314f2d95fdc1fdbb9ade52611bdf84c1c1bd372f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetJpaEntity.java` @ `33ab5133e66774c59be8e9786aa918b7b861c1c0`.
- Candidate relationship fields (2): `id:String`, `subjectAreaId:String`.
- Custom component types: `AnalyticsDatasetType` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsDatasetType.java`; `AnalyticsRefreshMode` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRefreshMode.java`; `AnalyticsLineageStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsLineageStatus.java`; `AnalyticsQualityStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.AnalyticsDatasetVersion

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsDatasetVersion.java` @ `3003674c5b624efe83d53dc3c9a10f7c29720937` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetVersionJpaEntity.java` @ `32d1f3331335d20629914b65d5fdee71769e227b`.
- Candidate relationship fields (3): `id:String`, `datasetId:String`, `publishedByActorId:String`.
- Custom component types: none.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.AnalyticsInsight

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsInsight.java` @ `88779a67538a5bb136770e27bd21692e6c85e0cd` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsInsightJpaEntity.java` @ `d3ba714dc4b00549e03888525362378667dcb3fb`.
- Candidate relationship fields (7): `id:String`, `subjectAreaId:String`, `scopeId:String`, `severityId:String`, `sourceProjectionSnapshotId:String`, `sourceTrendAnalysisId:String`, `sourceModelRunId:String`.
- Custom component types: `AnalyticsInsightStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsInsightStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.AnalyticsProjectionRun

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/AnalyticsProjectionRun.java` @ `dbfdca2e9dede8bf7feb02f677d4698121ed0de3` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsProjectionRunJpaEntity.java` @ `b8d2561d08d0b31ac35126d6d1f55375ca4a5c61`.
- Candidate relationship fields (3): `id:String`, `projectionDefinitionId:String`, `correlationId:String`.
- Custom component types: `AnalyticsRunStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`; `AnalyticsRunMode` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunMode.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.DigitalTwinReadinessAssessment

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/DigitalTwinReadinessAssessment.java` @ `02656c55474f5c3f49631257efcfc0e1a9645cbd` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/DigitalTwinReadinessAssessmentJpaEntity.java` @ `35213a2e8dc5ec0053e5d0e649dcbd01df6e997a`.
- Candidate relationship fields (3): `id:String`, `scopeId:String`, `topologySnapshotId:String`.
- Custom component types: `DigitalTwinReadinessStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/DigitalTwinReadinessStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.MetricEvaluationRun

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricEvaluationRun.java` @ `1f94a0756c5e17a7e87a7975ac5cbfc14770a48f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricEvaluationRunJpaEntity.java` @ `ed642071646edf73fcdc5adeae611c7203ea12f7`.
- Candidate relationship fields (4): `id:String`, `metricDefinitionVersionId:String`, `scopeId:String`, `correlationId:String`.
- Custom component types: `AnalyticsRunStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsRunStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### analytics.MetricValue

- Domain: `src/main/java/dz/sh/hidra/modules/analytics/domain/model/MetricValue.java` @ `827d8dccdba064b5059febcc852eb37afa0fad84` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/MetricValueJpaEntity.java` @ `83d13b3e0440dcdc633cc374ad6a7e48bd82f3aa`.
- Candidate relationship fields (6): `id:String`, `metricEvaluationRunId:String`, `metricDefinitionId:String`, `metricDefinitionVersionId:String`, `scopeId:String`, `unitId:String`.
- Custom component types: `AnalyticsQualityStatus` → `src/main/java/dz/sh/hidra/modules/analytics/domain/value/AnalyticsQualityStatus.java`.
- Application-contract corpus: 28 current port files in module `analytics`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_023__create_analytics_tables.sql`.

#### assets.AssetConditionRecord

- Domain: `src/main/java/dz/sh/hidra/modules/assets/domain/model/AssetConditionRecord.java` @ `75e2aa4dccaa2d545c7395f67179493ce56fd92e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/AssetConditionRecordJpaEntity.java` @ `e0ea28f10afeab2e9c25beb51bd62892ad81db3b`.
- Candidate relationship fields (5): `id:String`, `maintainableAssetId:String`, `conditionTypeId:String`, `sourceReferenceId:String`, `observedByActorId:String`.
- Custom component types: `AssetConditionStatus` → `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetConditionStatus.java`.
- Application-contract corpus: 16 current port files in module `assets`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_014__create_assets_tables.sql`.

#### assets.MaintainableAsset

- Domain: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintainableAsset.java` @ `198eb3abf9e9e16388b2b5587341feae9b585f1b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintainableAssetJpaEntity.java` @ `f97564c21814b38dbfa60517daf745de417c5007`.
- Candidate relationship fields (10): `id:String`, `assetTypeId:String`, `topologyAssetId:String`, `parentAssetId:String`, `criticalityId:String`, `ownerOrganizationUnitId:String`, `manufacturerPartyId:String`, `modelId:String`, `serialIdentityId:String`, `createdByActorId:String`.
- Custom component types: `AssetLifecycleStatus` → `src/main/java/dz/sh/hidra/modules/assets/domain/value/AssetLifecycleStatus.java`.
- Application-contract corpus: 16 current port files in module `assets`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_014__create_assets_tables.sql`.

#### assets.MaintenanceWorkOrder

- Domain: `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java` @ `2a2c37d51402a1587938c0f9d9e72e219955f326` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java` @ `16ce1508ecc09b71694ff850cd3315122e7147b1`.
- Candidate relationship fields (10): `id:String`, `maintainableAssetId:String`, `maintenancePlanId:String`, `sourceRecommendationId:String`, `workOrderTypeId:String`, `priorityId:String`, `assignedOrganizationUnitId:String`, `assignedActorId:String`, `workflowInstanceId:String`, `createdByActorId:String`.
- Custom component types: `MaintenanceWorkOrderStatus` → `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`.
- Application-contract corpus: 16 current port files in module `assets`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_014__create_assets_tables.sql`.

#### audit.AuditAccessRecord

- Domain: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java` @ `33aecb5141eca64d2ebf8ba7c04c141a24858dd8` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java` @ `4e388681514ddb1abb95307c88fec506c9675a28`.
- Candidate relationship fields (5): `id:String`, `actorId:String`, `auditEventId:String`, `exportRequestId:String`, `correlationId:String`.
- Custom component types: `AuditAccessType` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditAccessType.java`.
- Application-contract corpus: 15 current port files in module `audit`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`.

#### audit.AuditBeforeAfterValue

- Domain: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java` @ `72e93122a1ee2d61474dfd04d3c2f2f621ad54d8` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java` @ `85ce0b4e1e8cfe3701dbdfa78483f969abe675da`.
- Candidate relationship fields (3): `id:String`, `auditEventId:String`, `maskReasonId:String`.
- Custom component types: `AuditValueType` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditValueType.java`.
- Application-contract corpus: 15 current port files in module `audit`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`.

#### audit.AuditEvent

- Domain: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java` @ `dc22f110ae5fec740db31dd2b3130d92635cae6b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java` @ `d7a3fa2d6562ec12adf58c900a2487233833dba5`.
- Candidate relationship fields (16): `id:String`, `eventTypeId:String`, `eventCategoryId:String`, `severityId:String`, `sourceEventId:String`, `actorId:String`, `organizationUnitId:String`, `targetId:String`, `reasonId:String`, `workflowInstanceId:String`, `workflowTaskId:String`, `workflowActionId:String`, `requestId:String`, `correlationId:String`, `causationId:String`, `retentionPolicyId:String`.
- Custom component types: `AuditEventStatus` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`; `AuditActorType` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditActorType.java`; `AuditOperation` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditOperation.java`.
- Application-contract corpus: 15 current port files in module `audit`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`.

#### audit.AuditExportRequest

- Domain: `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java` @ `1f14b4388228903d60e18a6f2bb26cb9eabf8d45` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java` @ `72b4ec82b944ac811111db094a4e38e5d92a9859`.
- Candidate relationship fields (5): `id:String`, `requestedByActorId:String`, `purposeId:String`, `workflowInstanceId:String`, `resultDocumentReferenceId:String`.
- Custom component types: `AuditExportStatus` → `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditExportStatus.java`.
- Application-contract corpus: 15 current port files in module `audit`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_017__create_audit_tables.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`, `src/main/resources/db/migration/V20261002_001__provision_alarm_suppression_expiry_audit_taxonomy.sql`.

#### configuration.ConfigurationDefinition

- Domain: `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java` @ `7bc10ae709f60531d38536afe23e27421fdb6653` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionJpaEntity.java` @ `943aee9d199274aaaf2befaf176088fbb378dfbf`.
- Candidate relationship fields (2): `id:String`, `namespaceId:String`.
- Custom component types: `ConfigurationValueType` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueType.java`; `ConfigurationSensitivity` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationSensitivity.java`; `ConfigurationDefinitionStatus` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationDefinitionStatus.java`.
- Application-contract corpus: 13 current port files in module `configuration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_020__create_configuration_tables.sql`.

#### configuration.ConfigurationValue

- Domain: `src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java` @ `812bc82ab6296bfd2fcd70c9e65689162d2d3562` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValueJpaEntity.java` @ `43c40fab84de4a4e801c0e92b2307ee031ffb643`.
- Candidate relationship fields (4): `id:String`, `definitionId:String`, `definitionVersionId:String`, `createdByActorId:String`.
- Custom component types: `ConfigurationValueStatus` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/ConfigurationValueStatus.java`.
- Application-contract corpus: 13 current port files in module `configuration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_020__create_configuration_tables.sql`.

#### configuration.FeatureFlag

- Domain: `src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java` @ `f4a193733e0b7dbd79b1806146be29389ff5db0c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagJpaEntity.java` @ `873a934706115d50e06c5ae3a21caa9bc32449b9`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `FeatureFlagStatus` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagStatus.java`; `FeatureFlagEvaluationStrategy` → `src/main/java/dz/sh/hidra/modules/configuration/domain/value/FeatureFlagEvaluationStrategy.java`.
- Application-contract corpus: 13 current port files in module `configuration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_020__create_configuration_tables.sql`.

#### custody.CustodyDiscrepancy

- Domain: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyDiscrepancy.java` @ `6ae7cd8844236dfce1eca3882b1a4645dc77acd7` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyDiscrepancyJpaEntity.java` @ `43aa26e563b8249de8c2a361844f912b28b9440e`.
- Candidate relationship fields (5): `id:String`, `reconciliationId:String`, `discrepancyTypeId:String`, `quantityUnitId:String`, `assignedActorId:String`.
- Custom component types: `CustodyDiscrepancyStatus` → `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyDiscrepancyStatus.java`.
- Application-contract corpus: 15 current port files in module `custody`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_015__create_custody_tables.sql`.

#### custody.CustodyMeasurementPeriod

- Domain: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyMeasurementPeriod.java` @ `fe88fc8be06e024265bebc94aca3ad23a2ee15a9` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyMeasurementPeriodJpaEntity.java` @ `9f298c3ee9072d632568fa681b28bceeaaa0455f`.
- Candidate relationship fields (5): `id:String`, `agreementId:String`, `transferPointId:String`, `lockedByActorId:String`, `approvedByActorId:String`.
- Custom component types: `CustodyPeriodStatus` → `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyPeriodStatus.java`.
- Application-contract corpus: 15 current port files in module `custody`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_015__create_custody_tables.sql`.

#### custody.CustodyTransferTicket

- Domain: `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java` @ `24e258556cd4210b88cfc9196f8efda0080b78ca` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java` @ `38ee89aac2ceef4f04e662b7a888f7c43c029ffa`.
- Candidate relationship fields (10): `id:String`, `measurementPeriodId:String`, `agreementId:String`, `transferPointId:String`, `batchId:String`, `quantityCalculationId:String`, `issuedByActorId:String`, `approvedByActorId:String`, `workflowInstanceId:String`, `auditReferenceId:String`.
- Custom component types: `CustodyTicketStatus` → `src/main/java/dz/sh/hidra/modules/custody/domain/value/CustodyTicketStatus.java`.
- Application-contract corpus: 15 current port files in module `custody`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_015__create_custody_tables.sql`.

#### documents.Document

- Domain: `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java` @ `2374c7764b911fc2dfc718986281f3265dfe6b34` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java` @ `2d8c9637193170abb66c087d1d3de7800ead01e3`.
- Candidate relationship fields (7): `id:String`, `documentTypeId:String`, `documentCategoryId:String`, `classificationId:String`, `currentVersionId:String`, `ownerTargetId:String`, `createdByActorId:String`.
- Custom component types: `DocumentStatus` → `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`.
- Application-contract corpus: 17 current port files in module `documents`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`.

#### documents.DocumentStorageObject

- Domain: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java` @ `88c69e394ae5447cad06ea441fad9f8ac19efc11` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java` @ `2baafe5cbc3a8f0db2276569f477145ff8aad488`.
- Candidate relationship fields (2): `id:String`, `storageProviderId:String`.
- Custom component types: `DocumentStorageStatus` → `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`.
- Application-contract corpus: 17 current port files in module `documents`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`.

#### documents.DocumentTargetLink

- Domain: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java` @ `41812389c9061c3cb7f68dc21739aeb03802218b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java` @ `c3b536a5af492ae7c13fbbdf73846f9e6a3ef7b9`.
- Candidate relationship fields (6): `id:String`, `documentId:String`, `documentVersionId:String`, `targetId:String`, `linkRoleId:String`, `linkedByActorId:String`.
- Custom component types: none.
- Application-contract corpus: 17 current port files in module `documents`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`.

#### documents.DocumentVersion

- Domain: `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java` @ `12e02c7d4cdda7c0ffd96045789267a38613a45e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java` @ `03feeb66d6eca23929cc10a8d8940f218d71df0f`.
- Candidate relationship fields (6): `id:String`, `documentId:String`, `storageObjectId:String`, `uploadedByActorId:String`, `approvedByWorkflowInstanceId:String`, `supersededByVersionId:String`.
- Custom component types: `DocumentVersionStatus` → `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`.
- Application-contract corpus: 17 current port files in module `documents`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_018__create_documents_tables.sql`.

#### hse.HseCase

- Domain: `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java` @ `fb37bfead66085fce4337a152f8267a5b8677b64` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java` @ `a60ec5fb350424d380fa9ca41de84ee4cbd621ba`.
- Candidate relationship fields (10): `id:String`, `caseTypeId:String`, `severityId:String`, `priorityId:String`, `incidentReferenceId:String`, `targetId:String`, `reportedByActorId:String`, `responsibleOrganizationUnitId:String`, `workflowInstanceId:String`, `auditReferenceId:String`.
- Custom component types: `HseCaseStatus` → `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseStatus.java`; `HseCaseSourceType` → `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseSourceType.java`.
- Application-contract corpus: 17 current port files in module `hse`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`.

#### hse.HseClosure

- Domain: `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java` @ `71115680f505afbdca2ede7eaf9b9f273e05338c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java` @ `7ec97b74a4c1f391b03dfa0f882c8a43bfebcaa9`.
- Candidate relationship fields (4): `id:String`, `hseCaseId:String`, `closedByActorId:String`, `workflowInstanceId:String`.
- Custom component types: none.
- Application-contract corpus: 17 current port files in module `hse`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`.

#### hse.HseCorrectivePreventiveAction

- Domain: `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java` @ `7a28512cb5f04e8065d1c58868a752b78950972d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java` @ `6ac99c9ab5a65c47574f00307a76ce2e5bc4fc25`.
- Candidate relationship fields (8): `id:String`, `hseCaseId:String`, `actionTypeId:String`, `ownerActorId:String`, `ownerOrganizationUnitId:String`, `verifiedByActorId:String`, `linkedWorkOrderId:String`, `workflowTaskId:String`.
- Custom component types: `CapaStatus` → `src/main/java/dz/sh/hidra/modules/hse/domain/value/CapaStatus.java`.
- Application-contract corpus: 17 current port files in module `hse`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`.

#### hse.PermitToWork

- Domain: `src/main/java/dz/sh/hidra/modules/hse/domain/model/PermitToWork.java` @ `554628dcc37994794fe5bbed3fd041b61c0e98d2` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/PermitToWorkJpaEntity.java` @ `45b89dacbe0cccc52e3cca0049b83314ed4dca93`.
- Candidate relationship fields (6): `id:String`, `permitTypeId:String`, `targetId:String`, `requestedByActorId:String`, `approvedByActorId:String`, `workflowInstanceId:String`.
- Custom component types: `PermitStatus` → `src/main/java/dz/sh/hidra/modules/hse/domain/value/PermitStatus.java`.
- Application-contract corpus: 17 current port files in module `hse`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_012__create_hse_tables.sql`.

#### identity.AuthenticationEvent

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java` @ `f8b05a2ca852fdf11efc441fceffc3547c4d75dd` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthenticationEventJpaEntity.java` @ `905d5df225750a5774645e2e9f4555d1c23a33e7`.
- Candidate relationship fields (5): `id:String`, `userId:String`, `identityProviderId:String`, `externalIdentityId:String`, `correlationId:String`.
- Custom component types: `AuthenticationEventType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationEventType.java`; `AuthenticationProtocol` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthenticationProtocol.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.AuthorizationDecision

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java` @ `91850487114b970c89036c6b148284dc1d62d8ca` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java` @ `36e425623f3a0b9a4574eecc911c6a2d253a405d`.
- Candidate relationship fields (7): `id:String`, `userId:String`, `resourceReferenceId:String`, `matchedGrantIds:String`, `matchedPolicyRuleIds:String`, `correlationId:String`, `requestId:String`.
- Custom component types: `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `AuthorizationDecisionValue` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationDecisionValue.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.AuthorizationDelegationGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java` @ `f423d478d54f189214516a6ecc09d3d17fc679d2` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDelegationGrantJpaEntity.java` @ `0a055cfc35b63f58131e78525be8d85a8baee28e`.
- Candidate relationship fields (6): `id:String`, `delegatorUserId:String`, `delegateUserId:String`, `permissionId:String`, `roleId:String`, `approvedByWorkflowId:String`.
- Custom component types: `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `DelegationStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/DelegationStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.ExternalRoleMapping

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java` @ `cb238fc3b378f4a36b7302934df58f77dddd4ff0` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalRoleMappingJpaEntity.java` @ `a0d147a2ca914e1f4b1a31c55d602ef869486a3f`.
- Candidate relationship fields (3): `id:String`, `identityProviderId:String`, `roleId:String`.
- Custom component types: `ExternalMappingMode` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingMode.java`; `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `ExternalMappingStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/ExternalMappingStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.GroupRoleGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java` @ `b10324fff0d134b2e44e9abd55c2f4b03210036d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupRoleGrantJpaEntity.java` @ `813fa39de9412e16581aa02b120872d126a74aa1`.
- Candidate relationship fields (4): `id:String`, `groupId:String`, `roleId:String`, `approvedByWorkflowId:String`.
- Custom component types: `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `GrantStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.HidraPrincipal

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java` @ `240c6d4972cbb2bd1f2b5e6252e9ddd2a72d929c` — unchanged from HMC pinned blob.
- JPA evidence: no matching `HidraPrincipalJpaEntity` path in the expected live module entity directory.
- Candidate relationship fields (2): `userId:String`, `identityProviderId:String`.
- Custom component types: `ProviderType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.IdentityProvider

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java` @ `46f0e278976c77d21242f5f9bb1dd7b1f1544abe` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java` @ `dd3114b2b3121de20446647bcc0dacbf73a95b4e`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `ProviderType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/ProviderType.java`; `IdentityProviderStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/IdentityProviderStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.LocalCredential

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java` @ `f0347e0e37f72c39e9479c1e102c5ef81cc244a4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java` @ `5d20e1c5eb228d94e9a49e688bba0e2ed78c667b`.
- Candidate relationship fields (2): `id:String`, `userId:String`.
- Custom component types: none.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.LoginSession

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java` @ `4ed254552fec6e0fa56584f3707da10dba71e31d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java` @ `f67e2a9077238d2792ba6207aa65f5667d72906a`.
- Candidate relationship fields (5): `id:String`, `userId:String`, `identityProviderId:String`, `externalIdentityId:String`, `correlationId:String`.
- Custom component types: `LoginSessionStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/LoginSessionStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.Permission

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java` @ `3b3c2bd4493622f8217e8ee05b24a6375541d23b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/PermissionJpaEntity.java` @ `83a344f5dc58de25f6af4cb122be836e6f241a9d`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `PermissionStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/PermissionStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.Role

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java` @ `71fc4998af6e19b0a584ee48940b7aaf75730cc4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RoleJpaEntity.java` @ `98a08494d43d48d04391373fabd760c088418197`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `RoleType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleType.java`; `RoleStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/RoleStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.RolePermissionGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java` @ `9ea5ff60c90022f6c649af2c2ce2af8c354f7424` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java` @ `c5c7270ee60d3962db41277558931420e3415e6e`.
- Candidate relationship fields (3): `id:String`, `roleId:String`, `permissionId:String`.
- Custom component types: `GrantEffect` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantEffect.java`; `GrantStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.User

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java` @ `11461447721c0b9144f9f467687ef940f8a7b451` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java` @ `80f8ed4253d1ce7069c564773f4a72bc20191ff3`.
- Candidate relationship fields (2): `id:String`, `employeeReferenceId:String`.
- Custom component types: `UserType` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserType.java`; `UserStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/UserStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.UserPermissionGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java` @ `d8648c3ce5e11b1b9ad18bad3499a7226c54aba8` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java` @ `beb596fe855e45cac574a289aa9991c81402aa49`.
- Candidate relationship fields (4): `id:String`, `userId:String`, `permissionId:String`, `approvedByWorkflowId:String`.
- Custom component types: `GrantEffect` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantEffect.java`; `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `GrantStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### identity.UserRoleGrant

- Domain: `src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java` @ `4a72ae40d58752450f1ce162dec149058d671e41` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java` @ `03b47e1aa772cb38d7c5eeeaa66fc56b28af6b74`.
- Candidate relationship fields (4): `id:String`, `userId:String`, `roleId:String`, `approvedByWorkflowId:String`.
- Custom component types: `AuthorizationScope` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/AuthorizationScope.java`; `GrantStatus` → `src/main/java/dz/sh/hidra/modules/identity/domain/value/GrantStatus.java`.
- Application-contract corpus: 27 current port files in module `identity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_001__create_identity_tables.sql`, `src/main/resources/db/migration/V20260915_001__create_identity_local_credential.sql`.

#### incident.Incident

- Domain: `src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java` @ `a35109045ba76d3f328b4d9ef0e7badd803a82ce` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentJpaEntity.java` @ `56e000a910c8403ce9023d101bd575dce4ec7a1e`.
- Candidate relationship fields (10): `id:String`, `classificationId:String`, `severityId:String`, `priorityId:String`, `sourceReferenceId:String`, `topologyAssetId:String`, `responsibleOrganizationUnitId:String`, `responsibleActorId:String`, `workflowInstanceId:String`, `createdByActorId:String`.
- Custom component types: `IncidentStatus` → `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentStatus.java`; `IncidentSourceType` → `src/main/java/dz/sh/hidra/modules/incident/domain/value/IncidentSourceType.java`.
- Application-contract corpus: 10 current port files in module `incident`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`.

#### incident.IncidentClosure

- Domain: `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java` @ `f29b03eac51b1ce40872e3917279ff85b49cba21` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentClosureJpaEntity.java` @ `4a95b3013bfdd7a5439a6ac3ba8b33ae36c07936`.
- Candidate relationship fields (4): `id:String`, `incidentId:String`, `closedByActorId:String`, `workflowInstanceId:String`.
- Custom component types: none.
- Application-contract corpus: 10 current port files in module `incident`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`.

#### incident.IncidentRelatedIncident

- Domain: `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java` @ `962ebb8edbe7589a32047e72de0909868bf3b8e7` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentRelatedIncidentJpaEntity.java` @ `592ab3c8abac06291c5e199a75a64df9098bb44a`.
- Candidate relationship fields (5): `id:String`, `incidentId:String`, `relatedIncidentId:String`, `relationshipTypeId:String`, `createdByActorId:String`.
- Custom component types: none.
- Application-contract corpus: 10 current port files in module `incident`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`.

#### incident.IncidentResponseAction

- Domain: `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java` @ `abee396c20ef861bdb1be5d79eb9e21beef4c678` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/entity/IncidentResponseActionJpaEntity.java` @ `9aa37cec25de424b34809a26af6ecf8f516fc39c`.
- Candidate relationship fields (6): `id:String`, `incidentId:String`, `actionTypeId:String`, `targetReferenceId:String`, `performedByActorId:String`, `organizationUnitId:String`.
- Custom component types: `ResponseActionStatus` → `src/main/java/dz/sh/hidra/modules/incident/domain/value/ResponseActionStatus.java`; `ResponseTargetType` → `src/main/java/dz/sh/hidra/modules/incident/domain/value/ResponseTargetType.java`.
- Application-contract corpus: 10 current port files in module `incident`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_010__create_incident_tables.sql`.

#### integration.ExternalSystem

- Domain: `src/main/java/dz/sh/hidra/modules/integration/domain/model/ExternalSystem.java` @ `a4874f7c6a858818cd46b1216dc3e977b1d07011` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/ExternalSystemJpaEntity.java` @ `c01c2dc5cd367f4905c6b5411fc853d8bb5cc607`.
- Candidate relationship fields (3): `id:String`, `systemTypeId:String`, `ownerOrganizationUnitId:String`.
- Custom component types: `IntegrationEnvironment` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationEnvironment.java`; `IntegrationCriticality` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationCriticality.java`; `ExternalSystemStatus` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExternalSystemStatus.java`.
- Application-contract corpus: 15 current port files in module `integration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`.

#### integration.IntegrationDeadLetterRecord

- Domain: `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java` @ `8209f48143e53f3e676bea615d0c5f1205e225a1` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java` @ `a0b629c9b2a742e3978b4a46923607a5186b42c9`.
- Candidate relationship fields (7): `id:String`, `externalSystemId:String`, `jobRunId:String`, `exchangeMessageId:String`, `inboundRecordId:String`, `outboundRecordId:String`, `resolvedByActorId:String`.
- Custom component types: `DeadLetterStatus` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/DeadLetterStatus.java`.
- Application-contract corpus: 15 current port files in module `integration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`.

#### integration.IntegrationExchangeMessage

- Domain: `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java` @ `34cf7dec9de05e43d89deb1be137b96b67c0556c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java` @ `a985eabf0834e3ccd9b5d7aa03583dc2a35eecf1`.
- Candidate relationship fields (8): `id:String`, `jobRunId:String`, `externalSystemId:String`, `endpointId:String`, `messageTypeId:String`, `externalMessageId:String`, `payloadFormatId:String`, `correlationId:String`.
- Custom component types: `IntegrationDirection` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/IntegrationDirection.java`; `PayloadStorageMode` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/PayloadStorageMode.java`; `ExchangeMessageStatus` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/ExchangeMessageStatus.java`.
- Application-contract corpus: 15 current port files in module `integration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`.

#### integration.IntegrationJobRun

- Domain: `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationJobRun.java` @ `64ae0983e5dc963e080178ca286eaee0c1356999` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationJobRunJpaEntity.java` @ `a7643063fb76fb729ed92ef9468b602db851c484`.
- Candidate relationship fields (4): `id:String`, `jobDefinitionId:String`, `triggeredByActorId:String`, `correlationId:String`.
- Custom component types: `JobTriggerType` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobTriggerType.java`; `JobRunStatus` → `src/main/java/dz/sh/hidra/modules/integration/domain/value/JobRunStatus.java`.
- Application-contract corpus: 15 current port files in module `integration`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_019__create_integration_tables.sql`.

#### integrity.IntegrityAssessment

- Domain: `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java` @ `237361a88167caf6979cf4d0d61390b8dbc3b4ce` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java` @ `b00bf9108daa55fea001df2a5b37c0e23757633c`.
- Candidate relationship fields (9): `id:String`, `programId:String`, `assessmentTypeId:String`, `methodologyId:String`, `assessedByActorId:String`, `reviewedByActorId:String`, `approvedByActorId:String`, `workflowInstanceId:String`, `auditReferenceId:String`.
- Custom component types: `IntegrityAssessmentStatus` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityAssessmentStatus.java`.
- Application-contract corpus: 16 current port files in module `integrity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`, `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`.

#### integrity.IntegrityCase

- Domain: `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java` @ `c1ccaeb1ebb3c547027b4a88a18699f3be4577e9` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java` @ `cd2aabac980bd8218a2139e961978aff1b68c851`.
- Candidate relationship fields (10): `id:String`, `caseTypeId:String`, `severityId:String`, `topologyAssetId:String`, `primaryDefectId:String`, `sourceIncidentId:String`, `sourceHseCaseId:String`, `responsibleOrganizationUnitId:String`, `workflowInstanceId:String`, `openedByActorId:String`.
- Custom component types: `IntegrityCaseStatus` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityCaseStatus.java`.
- Application-contract corpus: 16 current port files in module `integrity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`, `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`.

#### integrity.IntegrityProgram

- Domain: `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityProgram.java` @ `213b41c6281f0a8d1d82ef7f86c2e7ddbe0f57cc` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityProgramJpaEntity.java` @ `6946abecc283e4b792693397f7e11de1448771ca`.
- Candidate relationship fields (4): `id:String`, `programTypeId:String`, `ownerOrganizationUnitId:String`, `createdByActorId:String`.
- Custom component types: `IntegrityProgramStatus` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityProgramStatus.java`.
- Application-contract corpus: 16 current port files in module `integrity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`, `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`.

#### integrity.PipelineDefect

- Domain: `src/main/java/dz/sh/hidra/modules/integrity/domain/model/PipelineDefect.java` @ `a6004398f0caa5647ca4f681c1a584da84d6ca30` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/PipelineDefectJpaEntity.java` @ `2e829b62dce56f5737c19ac0694aa3f55b73eb56`.
- Candidate relationship fields (4): `id:String`, `defectTypeId:String`, `topologyAssetId:String`, `sourceFindingId:String`.
- Custom component types: `ThreatType` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/ThreatType.java`; `DefectStatus` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/DefectStatus.java`; `FindingSeverity` → `src/main/java/dz/sh/hidra/modules/integrity/domain/value/FindingSeverity.java`.
- Application-contract corpus: 16 current port files in module `integrity`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_013__create_integrity_tables.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql`, `src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql`.

#### leakdetection.LeakCandidate

- Domain: `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakCandidate.java` @ `5d92f187ec860004a81803b06436d8cb7532b27d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakCandidateJpaEntity.java` @ `a999ec72982f8323fb440c619d2a0613fe34ab1d`.
- Candidate relationship fields (5): `id:String`, `runId:String`, `profileId:String`, `topologyAssetId:String`, `correlationId:String`.
- Custom component types: `LeakSeverityLevel` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakSeverityLevel.java`; `LeakCandidateStatus` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakCandidateStatus.java`.
- Application-contract corpus: 9 current port files in module `leakdetection`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_009__create_leakdetection_tables.sql`.

#### leakdetection.LeakDetectionCase

- Domain: `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakDetectionCase.java` @ `2e742da22a0710f1a0016005922e5568cd367752` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakDetectionCaseJpaEntity.java` @ `16fd6728431a9436632dd4254f0f7c2e637f6115`.
- Candidate relationship fields (8): `id:String`, `primaryCandidateId:String`, `topologyAssetId:String`, `owningOrganizationUnitId:String`, `openedByActorId:String`, `closedByActorId:String`, `closureReasonId:String`, `correlationId:String`.
- Custom component types: `LeakDetectionCaseStatus` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakDetectionCaseStatus.java`; `LeakSeverityLevel` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakSeverityLevel.java`.
- Application-contract corpus: 9 current port files in module `leakdetection`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_009__create_leakdetection_tables.sql`.

#### leakdetection.LeakEscalationReference

- Domain: `src/main/java/dz/sh/hidra/modules/leakdetection/domain/model/LeakEscalationReference.java` @ `8d2ba4d58f15e6c6a1d606e22717ec9825eb2341` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/leakdetection/infrastructure/persistence/entity/LeakEscalationReferenceJpaEntity.java` @ `afc859e914c8e4b202c30170c0320a53b1af30e2`.
- Candidate relationship fields (6): `id:String`, `caseId:String`, `candidateId:String`, `targetReferenceId:String`, `escalatedByActorId:String`, `correlationId:String`.
- Custom component types: `LeakEscalationTargetType` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakEscalationTargetType.java`; `LeakEscalationStatus` → `src/main/java/dz/sh/hidra/modules/leakdetection/domain/value/LeakEscalationStatus.java`.
- Application-contract corpus: 9 current port files in module `leakdetection`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_009__create_leakdetection_tables.sql`.

#### monitoring.MonitoringRule

- Domain: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/MonitoringRule.java` @ `7b31d59e3c112969ba4df68a25d6044f4f29cd4d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/MonitoringRuleJpaEntity.java` @ `c029be86d255ce6069ec8ab276ecc66bd2c02830`.
- Candidate relationship fields (6): `id:String`, `evaluationFrequencyId:String`, `topologyAssetId:String`, `telemetryPointId:String`, `planningTargetTypeId:String`, `createdByActorId:String`.
- Custom component types: `MonitoringRuleType` → `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringRuleType.java`; `MonitoringLifecycleStatus` → `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/MonitoringLifecycleStatus.java`.
- Application-contract corpus: 7 current port files in module `monitoring`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_007__create_monitoring_tables.sql`.

#### monitoring.PlanActualDeviation

- Domain: `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java` @ `4fdceb2db374bca1f6529c730c732a437c53f3e4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java` @ `f28548a5d371da11b67f49e69c7d9c20d0bed373`.
- Candidate relationship fields (8): `id:String`, `evaluationId:String`, `planTargetId:String`, `expectedFlowStateId:String`, `trustedTelemetryReadingId:String`, `telemetryPointId:String`, `topologyAssetId:String`, `unitId:String`.
- Custom component types: `DeviationSeverity` → `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationSeverity.java`; `DeviationStatus` → `src/main/java/dz/sh/hidra/modules/monitoring/domain/value/DeviationStatus.java`.
- Application-contract corpus: 7 current port files in module `monitoring`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_007__create_monitoring_tables.sql`.

#### notification.NotificationDeliveryAttempt

- Domain: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationDeliveryAttempt.java` @ `c97afd5095da074816855d1f4fd8b497fd89fb4e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationDeliveryAttemptJpaEntity.java` @ `f560b13198152a5f24da4c6a6fa4f6a5a8dfbfde`.
- Candidate relationship fields (5): `id:String`, `messageId:String`, `channelId:String`, `providerMessageId:String`, `correlationId:String`.
- Custom component types: `DeliveryAttemptStatus` → `src/main/java/dz/sh/hidra/modules/notification/domain/value/DeliveryAttemptStatus.java`.
- Application-contract corpus: 18 current port files in module `notification`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`.

#### notification.NotificationMessage

- Domain: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationMessage.java` @ `fd32797447789db1c5ac2ef8b694b6cfe6398a2f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationMessageJpaEntity.java` @ `e360119d113f4a350a2bc5f5dab3b260341b792b`.
- Candidate relationship fields (7): `id:String`, `requestId:String`, `recipientId:String`, `channelId:String`, `templateId:String`, `templateVersionId:String`, `priorityId:String`.
- Custom component types: `NotificationMessageStatus` → `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationMessageStatus.java`.
- Application-contract corpus: 18 current port files in module `notification`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`.

#### notification.NotificationRequest

- Domain: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationRequest.java` @ `d4a1913539101ad6b6b90c77bb34c5b2b7218f18` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationRequestJpaEntity.java` @ `8867bd492ff64897422575dd8e8061582ababddb`.
- Candidate relationship fields (11): `id:String`, `sourceEventId:String`, `targetId:String`, `categoryId:String`, `priorityId:String`, `policyId:String`, `templateId:String`, `templateVersionId:String`, `requestedByActorId:String`, `correlationId:String`, `requestId:String`.
- Custom component types: `NotificationRequestStatus` → `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationRequestStatus.java`.
- Application-contract corpus: 18 current port files in module `notification`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`.

#### notification.NotificationTemplate

- Domain: `src/main/java/dz/sh/hidra/modules/notification/domain/model/NotificationTemplate.java` @ `d24522d17909702e81e02a73e4ba1c8179cc91b4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/notification/infrastructure/persistence/entity/NotificationTemplateJpaEntity.java` @ `6f8ff529cff2f1e096b96a9461952a3f8438f899`.
- Candidate relationship fields (4): `id:String`, `templateTypeId:String`, `categoryId:String`, `defaultChannelId:String`.
- Custom component types: `NotificationTemplateStatus` → `src/main/java/dz/sh/hidra/modules/notification/domain/value/NotificationTemplateStatus.java`.
- Application-contract corpus: 18 current port files in module `notification`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_021__create_notification_tables.sql`.

#### organization.AdministrativeDistrict

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeDistrict.java` @ `83182d46789eb3309c6b00c96a29affa6eb5f8d1` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeDistrictJpaEntity.java` @ `031d2e1dc5e35689c8fa20bcd377f87ded6439bc`.
- Candidate relationship fields (2): `id:String`, `stateId:String`.
- Custom component types: none.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.AdministrativeLocality

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeLocality.java` @ `3b25c187eb2c09c9d554044cddba11b2bf345dec` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeLocalityJpaEntity.java` @ `54c8ab2e7ff65d75c7dbfe416523c4242073280d`.
- Candidate relationship fields (2): `id:String`, `districtId:String`.
- Custom component types: none.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.AdministrativeState

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/AdministrativeState.java` @ `4aa38d466386dd1437b40d2ccfff7c40604c869d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/AdministrativeStateJpaEntity.java` @ `85a2149b6a15666ada8b15383fed391f71e21611`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: none.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.Employee

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/Employee.java` @ `5274853a4ffc75caeac5ceb6f671542964e31063` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeJpaEntity.java` @ `115d5031d065cca1bbdbceef4e56382609134c09`.
- Candidate relationship fields (2): `id:String`, `birthLocalityId:String`.
- Custom component types: `EmployeeType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/EmployeeType.java`; `EmployeeStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/EmployeeStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.EmployeeAddress

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAddress.java` @ `549ce43942f6457c2dc81c02a5f3dddb015b45ea` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAddressJpaEntity.java` @ `f4bbe0f7b2f9a880ab02498cbc80138bbd60e4bf`.
- Candidate relationship fields (3): `id:String`, `employeeId:String`, `localityId:String`.
- Custom component types: `AddressType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/AddressType.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.EmployeeAssignment

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/EmployeeAssignment.java` @ `fb8baa91856a0f1daf71934560cf47b6b702cdca` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/EmployeeAssignmentJpaEntity.java` @ `2c6d6f32493d976dd6ffaad3d2503225d891c624`.
- Candidate relationship fields (4): `id:String`, `employeeId:String`, `organizationUnitId:String`, `positionId:String`.
- Custom component types: `AssignmentType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentType.java`; `AssignmentStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OperationalScope

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OperationalScope.java` @ `48a242b4faec7f67b56fe14a112aea2162b9bd49` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OperationalScopeJpaEntity.java` @ `4e41c19859fee3294339a508f3c5f9e6c0e12c76`.
- Candidate relationship fields (2): `id:Long`, `targetId:String`.
- Custom component types: `OperationalScopeType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/OperationalScopeType.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationContactPoint

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationContactPoint.java` @ `2b3225bbdd485a8987962e0827d4781a665e0e31` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationContactPointJpaEntity.java` @ `c6ab8ebed5109addb3cc9847d973a7fa98540541`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `ContactPointType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ContactPointType.java`; `ContactPointTargetReference` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ContactPointTargetReference.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationDelegation

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationDelegation.java` @ `254f4fa04b6a40b3cc730e8c01838d0b1cc585e5` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationDelegationJpaEntity.java` @ `0e7641e5e5d4af8f72f54553d5669588df0255cd`.
- Candidate relationship fields (4): `id:String`, `delegatorEmployeeId:String`, `delegateEmployeeId:String`, `responsibilityAssignmentId:String`.
- Custom component types: `DelegationStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/DelegationStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationHierarchySnapshot

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationHierarchySnapshot.java` @ `06c94c9e4a22d99667a885a87fc86375bce10c9d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationHierarchySnapshotJpaEntity.java` @ `97839e5c16306813fb0f788f47a6abf211225df9`.
- Candidate relationship fields (2): `id:String`, `capturedByEmployeeId:String`.
- Custom component types: `HierarchySnapshotStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/HierarchySnapshotStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationUnit

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnit.java` @ `a16aae695395900e2fdf94d2dfb9333e61d7b74c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitJpaEntity.java` @ `f5a6ceff2d44ee5c412f43bde2a4ee05ba36a605`.
- Candidate relationship fields (3): `id:String`, `unitTypeId:String`, `parentUnitId:String`.
- Custom component types: `OrganizationUnitStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.OrganizationUnitType

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/OrganizationUnitType.java` @ `479c1fb239c84f2b0bbce2b2cbaa4f076c66272d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/OrganizationUnitTypeJpaEntity.java` @ `a5316df16a431d9023039366b7a9cbe0afdc78b5`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `OrganizationUnitKind` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/OrganizationUnitKind.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.Position

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/Position.java` @ `308e1640335ca148078e96cff16f853a001cd799` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/PositionJpaEntity.java` @ `fbd5f0d3a9ad72abe7356a42fc6f531259e342ec`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `PositionLevel` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionLevel.java`; `PositionStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/PositionStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.ReportingLine

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/ReportingLine.java` @ `e9142856cb5f67799fe1adc7d660a2eb0fae368c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ReportingLineJpaEntity.java` @ `04005d00a82726ac789db2d65f1e4028ca0463f1`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `ReportingLineType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingLineType.java`; `ReportingSubjectReference` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ReportingSubjectReference.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.ResponsibilityAssignment

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/ResponsibilityAssignment.java` @ `885f4eb94a7cc187edc47fc31f457cebb9a9d18e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ResponsibilityAssignmentJpaEntity.java` @ `eb692ec98f0bc8747302be026d876869a704e844`.
- Candidate relationship fields (3): `id:String`, `assigneeId:String`, `scopeId:Long`.
- Custom component types: `ResponsibilityType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ResponsibilityType.java`; `ResponsibilityAssigneeType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ResponsibilityAssigneeType.java`; `AssignmentStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/AssignmentStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.Shift

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/Shift.java` @ `89053e407bce55c2f647f8e7979b4c8ba095d47f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftJpaEntity.java` @ `25467d09ebaf35d9623e69efffb3521f18d41dea`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `ShiftType` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftType.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### organization.ShiftAssignment

- Domain: `src/main/java/dz/sh/hidra/modules/organization/domain/model/ShiftAssignment.java` @ `4ecb5a4eb14e70a5b671bed782ca40df0bcf12a2` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/entity/ShiftAssignmentJpaEntity.java` @ `deae79e8aee97258fc4d69dca55b3d9974775851`.
- Candidate relationship fields (4): `id:String`, `employeeId:String`, `shiftId:String`, `organizationUnitId:String`.
- Custom component types: `ShiftAssignmentStatus` → `src/main/java/dz/sh/hidra/modules/organization/domain/value/ShiftAssignmentStatus.java`.
- Application-contract corpus: 31 current port files in module `organization`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_002__create_organization_tables.sql`, `src/main/resources/db/migration/V20260927_002__add_embedded_organization_multilingual_fields.sql`, `src/main/resources/db/migration/V20260927_003__retire_organization_unit_type_translation_table.sql`, `src/main/resources/db/migration/V20260927_004__enforce_organization_internal_reference_integrity.sql`, `src/main/resources/db/migration/V20260927_005__retire_organization_multilingual_compatibility_columns.sql`, `src/main/resources/db/migration/V20260929_004__provision_organization_scope_permissions.sql`, `src/main/resources/db/migration/V20260929_005__provision_organization_responsibility_audit_taxonomy.sql`.

#### party.Party

- Domain: `src/main/java/dz/sh/hidra/modules/party/domain/model/Party.java` @ `dc33cbf1a1d0a8264d13eacf0cd3a2e1f5d67d12` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyJpaEntity.java` @ `83de397fc16582bc6567adbefde1de9a852f1822`.
- Candidate relationship fields (2): `id:String`, `partyTypeId:String`.
- Custom component types: `PartyStatus` → `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyStatus.java`.
- Application-contract corpus: 6 current port files in module `party`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_003__create_party_tables.sql`.

#### party.PartyRoleAssignment

- Domain: `src/main/java/dz/sh/hidra/modules/party/domain/model/PartyRoleAssignment.java` @ `0ca1a3a47f45c97fec78997ed6a810fcbfe60600` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/entity/PartyRoleAssignmentJpaEntity.java` @ `92f815a2082169178d71cea72569edb73afce884`.
- Candidate relationship fields (3): `id:String`, `partyId:String`, `roleId:String`.
- Custom component types: `PartyRoleAssignmentStatus` → `src/main/java/dz/sh/hidra/modules/party/domain/value/PartyRoleAssignmentStatus.java`.
- Application-contract corpus: 6 current port files in module `party`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_003__create_party_tables.sql`.

#### planning.Nomination

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java` @ `99ce28cecd9d178ebd4174bf331ba25a9eb1af73` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java` @ `2489a6d3c82c585c2542dda287449de43802ca7e`.
- Candidate relationship fields (12): `id:String`, `revisionId:String`, `scenarioId:String`, `nominationTypeId:String`, `productTypeId:String`, `quantityUnitId:String`, `rateUnitId:String`, `sourceAssetId:String`, `destinationAssetId:String`, `shipperPartyId:String`, `counterpartyId:String`, `contractReferenceId:String`.
- Custom component types: `NominationStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/NominationStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### planning.OperationalPlan

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java` @ `3ed411d3fa31f6e3109c63d32fdf077b63c1ea4f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java` @ `5825cc10f5e79c2aa0b41ab8efa69811eb0d0231`.
- Candidate relationship fields (9): `id:String`, `periodId:String`, `planTypeId:String`, `productTypeId:String`, `topologyScopeId:String`, `responsibleOrganizationUnitId:String`, `currentRevisionId:String`, `approvedRevisionId:String`, `createdByActorId:String`.
- Custom component types: `OperationalPlanStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### planning.PlanRevision

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java` @ `945ebc3896d97eb9123a48f21f86365af72078db` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java` @ `8a1459b799113a99ee710287baa7db3a55eb42a2`.
- Candidate relationship fields (7): `id:String`, `planId:String`, `changeReasonCodeId:String`, `baseRevisionId:String`, `submittedByActorId:String`, `approvedByActorId:String`, `workflowInstanceId:String`.
- Custom component types: `PlanRevisionStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### planning.PlanTarget

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java` @ `1f653e56a356e09334e3fed6bb5fc421a9be2df7` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java` @ `13a87e326601990e05844ba4f1c567bbf3977c96`.
- Candidate relationship fields (8): `id:String`, `revisionId:String`, `scenarioId:String`, `nominationId:String`, `targetTypeId:String`, `topologyAssetId:String`, `telemetryPointId:String`, `unitId:String`.
- Custom component types: `PlanTargetStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### planning.PlanningPeriod

- Domain: `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanningPeriod.java` @ `8323d0959d638912e620cc2263cdc83928129381` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanningPeriodJpaEntity.java` @ `7308a0a4b791636ff8e204aa5af9c66d650fe136`.
- Candidate relationship fields (3): `id:String`, `periodTypeId:String`, `createdByActorId:String`.
- Custom component types: `PlanningPeriodStatus` → `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanningPeriodStatus.java`.
- Application-contract corpus: 13 current port files in module `planning`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_006__create_planning_tables.sql`.

#### reporting.ReportDefinition

- Domain: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportDefinition.java` @ `a49e35df1e54133b727eaaad14a04ca5af31e686` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportDefinitionJpaEntity.java` @ `f993e34f6e6632b8cbb4b6f1293933871152ba05`.
- Candidate relationship fields (3): `id:String`, `reportCategoryId:String`, `currentTemplateVersionId:String`.
- Custom component types: none.
- Application-contract corpus: 21 current port files in module `reporting`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`.

#### reporting.ReportOutputArtifact

- Domain: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java` @ `b31cb9be178fae822e3ea9d88dddd5f45a023327` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java` @ `b16797af106a882e67e2d7cd1a031d684a9a3b82`.
- Candidate relationship fields (4): `id:String`, `reportRunId:String`, `storageObjectReferenceId:String`, `documentReferenceId:String`.
- Custom component types: `ReportArtifactType` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportArtifactType.java`; `ReportFormat` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportFormat.java`.
- Application-contract corpus: 21 current port files in module `reporting`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`.

#### reporting.ReportRequest

- Domain: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRequest.java` @ `6b87d76de895a0c3f81799bb5caead7723aaef76` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRequestJpaEntity.java` @ `5b468d6f32073e27923ce7181e0d8754e017e5b6`.
- Candidate relationship fields (6): `id:String`, `reportDefinitionId:String`, `requestedByActorId:String`, `organizationUnitId:String`, `correlationId:String`, `workflowReferenceId:String`.
- Custom component types: `ReportRequestStatus` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRequestStatus.java`.
- Application-contract corpus: 21 current port files in module `reporting`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`.

#### reporting.ReportRun

- Domain: `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java` @ `fe40b3c4eed61171f9bd2bb42639776e2431f6a4` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java` @ `ffb151945f1f099bbbd2aa0afed41284263acee4`.
- Candidate relationship fields (5): `id:String`, `reportRequestId:String`, `reportDefinitionId:String`, `templateVersionId:String`, `correlationId:String`.
- Custom component types: `ReportRunStatus` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`; `ReportRunMode` → `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`.
- Application-contract corpus: 21 current port files in module `reporting`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_024__create_reporting_tables.sql`.

#### risk.RiskAssessment

- Domain: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java` @ `38f55d8d5806bf60c5c616225819699ab042cec3` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentJpaEntity.java` @ `359df73749c144ad1529ef4902298f447cb92635`.
- Candidate relationship fields (18): `id:String`, `riskRegisterId:String`, `assessmentTypeId:String`, `methodologyId:String`, `scopeId:String`, `riskScenarioId:String`, `assessedByActorId:String`, `reviewedByActorId:String`, `approvedByActorId:String`, `inherentLikelihoodId:String`, `inherentConsequenceId:String`, `inherentRatingId:String`, `residualLikelihoodId:String`, `residualConsequenceId:String`, `residualRatingId:String`, `confidenceLevelId:String`, `workflowReferenceId:String`, `auditReferenceId:String`.
- Custom component types: `RiskAssessmentStatus` → `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskAssessmentStatus.java`.
- Application-contract corpus: 22 current port files in module `risk`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`.

#### risk.RiskEvidenceLink

- Domain: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java` @ `aae26edcd53e6ca3424f914116e97eb947402e8b` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskEvidenceLinkJpaEntity.java` @ `2b68ae2a3e2733ea901485eb90ca1ff37e59f3db`.
- Candidate relationship fields (3): `id:String`, `riskAssessmentId:String`, `evidenceId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `risk`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`.

#### risk.RiskMatrixCell

- Domain: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskMatrixCell.java` @ `068add9a3c599c6b67905daea2abc92b149b9bea` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskMatrixCellJpaEntity.java` @ `fc1c06b4f727cad793db278def33d4f6216c2f91`.
- Candidate relationship fields (5): `id:String`, `riskMatrixId:String`, `likelihoodLevelId:String`, `consequenceLevelId:String`, `ratingId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `risk`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`.

#### risk.RiskRegister

- Domain: `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskRegister.java` @ `b2ae8d0bf2d5fbe24b72a73d3c3cf1b9d7345692` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskRegisterJpaEntity.java` @ `9f183aea285be50527785a984c1d7fb60dd1710c`.
- Candidate relationship fields (6): `id:String`, `registerTypeId:String`, `ownerOrganizationUnitId:String`, `scopeId:String`, `reviewFrequencyId:String`, `createdByActorId:String`.
- Custom component types: `RiskRegisterStatus` → `src/main/java/dz/sh/hidra/modules/risk/domain/value/RiskRegisterStatus.java`.
- Application-contract corpus: 22 current port files in module `risk`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_011__create_risk_tables.sql`.

#### simulation.SimulationCandidateChange

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java` @ `93afc7b98b75066b6e1bdda180216be8802d295a` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationCandidateChangeJpaEntity.java` @ `9a8dd011ddee3892b27fcd6c500bd13bfea0faff`.
- Candidate relationship fields (4): `id:String`, `candidateId:String`, `changeTypeId:String`, `targetId:String`.
- Custom component types: none.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationModel

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationModel.java` @ `dce60115dea1a8bd17bc88eb9d3373e741b70e02` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationModelJpaEntity.java` @ `689bc928fa80bbe5dbd10cb989dcd17a33809c3d`.
- Candidate relationship fields (3): `id:String`, `modelTypeId:String`, `topologyScopeId:String`.
- Custom component types: `SimulationModelStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationModelStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationOptimizationCandidate

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationOptimizationCandidate.java` @ `be3b50e6985efcdb9b2a36d5e7a1f3e8c5966761` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationOptimizationCandidateJpaEntity.java` @ `de1c3a03dfca225085d0f47a68c67b9911ea06f0`.
- Candidate relationship fields (3): `id:String`, `runId:String`, `selectedByActorId:String`.
- Custom component types: `SimulationCandidateStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationCandidateStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationRecommendation

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java` @ `1d25c0ad99d79e2925ba06b21d5fd41e53b225c5` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRecommendationJpaEntity.java` @ `d745ff231a0b0d03bfbde20a312aaff5de1367ef`.
- Candidate relationship fields (6): `id:String`, `runId:String`, `candidateId:String`, `recommendationTypeId:String`, `confidenceLevelId:String`, `publishedByActorId:String`.
- Custom component types: `SimulationRecommendationStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRecommendationStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationRun

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRun.java` @ `36241f71e8b2d87446bf94e4309d09bb407b5cf1` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationRunJpaEntity.java` @ `94ae34ad23a86a502a0f35f3856fb577cf063f6c`.
- Candidate relationship fields (8): `id:String`, `scenarioId:String`, `modelVersionId:String`, `inputSnapshotId:String`, `runTypeId:String`, `requestedByActorId:String`, `solverProfileId:String`, `correlationId:String`.
- Custom component types: `SimulationRunStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationRunStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### simulation.SimulationScenario

- Domain: `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationScenario.java` @ `37d1ec54ffacf2307dcce317eeff593d8481f481` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/entity/SimulationScenarioJpaEntity.java` @ `ecfd2be18aa9e5de65300466fc75a2d881dbb76d`.
- Candidate relationship fields (8): `id:String`, `scenarioTypeId:String`, `modelId:String`, `modelVersionId:String`, `topologySnapshotId:String`, `planningReferenceId:String`, `monitoringContextId:String`, `createdByActorId:String`.
- Custom component types: `SimulationScenarioStatus` → `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationScenarioStatus.java`.
- Application-contract corpus: 25 current port files in module `simulation`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_022__create_simulation_tables.sql`.

#### telemetry.TelemetryPoint

- Domain: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryPoint.java` @ `d400e3d0b5367524b48c4989499815c1085e78ca` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryPointJpaEntity.java` @ `6644f6abe45b298954e2e4c40156428ef4750f55`.
- Candidate relationship fields (6): `id:String`, `deviceId:String`, `pointTypeId:String`, `signalTypeId:String`, `unitId:String`, `defaultAggregationMethodId:String`.
- Custom component types: `TelemetryLifecycleStatus` → `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`.
- Application-contract corpus: 9 current port files in module `telemetry`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`.

#### telemetry.TelemetryReading

- Domain: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java` @ `9c5d8fd95608a7a530ebaa9113e1773dcfb227de` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetryReadingJpaEntity.java` @ `a935d92413bb2cb0382629894d246d9ad32b5e6e`.
- Candidate relationship fields (6): `id:String`, `pointId:String`, `qualityCodeId:String`, `ingestionBatchId:String`, `correlationId:String`, `externalTagMappingId:String`.
- Custom component types: `ReadingState` → `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/ReadingState.java`.
- Application-contract corpus: 9 current port files in module `telemetry`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`.

#### telemetry.TelemetrySource

- Domain: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetrySource.java` @ `bbcd387202fd55934d16b71bd37423dfb68dd74c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TelemetrySourceJpaEntity.java` @ `9bb82fdd06808778dcc6d797878e4ef86f15b162`.
- Candidate relationship fields (3): `id:String`, `sourceTypeId:String`, `protocolId:String`.
- Custom component types: `TelemetryLifecycleStatus` → `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TelemetryLifecycleStatus.java`.
- Application-contract corpus: 9 current port files in module `telemetry`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`.

#### telemetry.TrustedTelemetryReading

- Domain: `src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TrustedTelemetryReading.java` @ `e5c6e50f904d1669ab18e0f1c88a3228a5ca6b1e` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/entity/TrustedTelemetryReadingJpaEntity.java` @ `de6fb059056dbb5fd2782fb4625b1367a446c48a`.
- Candidate relationship fields (9): `id:String`, `readingId:String`, `pointId:String`, `unitId:String`, `qualityCodeId:String`, `qualityAssessmentId:String`, `topologyAssetId:String`, `topologySnapshotId:String`, `ingestionBatchId:String`.
- Custom component types: `TrustLevel` → `src/main/java/dz/sh/hidra/modules/telemetry/domain/value/TrustLevel.java`.
- Application-contract corpus: 9 current port files in module `telemetry`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_005__create_telemetry_tables.sql`.

#### topology.Equipment

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Equipment.java` @ `7e220479a4213de3e3661ffafc436decda11ee4f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/EquipmentJpaEntity.java` @ `d49ee792cf7798f6457071411f17222a66a9fa3f`.
- Candidate relationship fields (6): `id:String`, `facilityId:String`, `nodeId:String`, `pipelineSegmentId:String`, `equipmentTypeId:String`, `manufacturerPartyId:String`.
- Custom component types: `EquipmentKind` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentKind.java`; `EquipmentStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/EquipmentStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### topology.Facility

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Facility.java` @ `794e17a24b1f1c7dd1072c8c17f7392b15817f42` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/FacilityJpaEntity.java` @ `11bfe6b0486dbeab86c7381469b05f33d3428df4`.
- Candidate relationship fields (3): `id:String`, `facilityTypeId:String`, `ownerPartyId:String`.
- Custom component types: `FacilityKind` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityKind.java`; `FacilityStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/FacilityStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### topology.Pipeline

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/Pipeline.java` @ `c06d25c7ce730482591880d7ebd28b384599843d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineJpaEntity.java` @ `8f7f37a2ecd11c666a032cf5a2f4528391cb8158`.
- Candidate relationship fields (2): `id:String`, `pipelineSystemId:String`.
- Custom component types: `PipelineType` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineType.java`; `TopologyStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### topology.PipelineSystem

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/PipelineSystem.java` @ `c461146a5836af0885e20cd71290f08ad9959376` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/PipelineSystemJpaEntity.java` @ `fe2cc949360225ad8ff372a79e018d25e61bbd06`.
- Candidate relationship fields (1): `id:String`.
- Custom component types: `PipelineSystemType` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/PipelineSystemType.java`; `TopologyStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### topology.TopologyConnection

- Domain: `src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyConnection.java` @ `d4b5fd62f38624207c219f6d31c35f812651cef5` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/entity/TopologyConnectionJpaEntity.java` @ `8419cd1024473db4d412737de0e38ac2ce994be4`.
- Candidate relationship fields (4): `id:String`, `fromNodeId:String`, `toNodeId:String`, `pipelineSegmentId:String`.
- Custom component types: `ConnectionType` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/ConnectionType.java`; `FlowDirection` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/FlowDirection.java`; `TopologyStatus` → `src/main/java/dz/sh/hidra/modules/topology/domain/value/TopologyStatus.java`.
- Application-contract corpus: 10 current port files in module `topology`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_004__create_topology_tables.sql`.

#### workflow.WorkflowAction

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java` @ `392bbe7bcf396959b50785c68d94bc833493aa8c` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowActionJpaEntity.java` @ `704015cf8374c3332b32525364e127bae5a08dec`.
- Candidate relationship fields (7): `id:String`, `instanceId:String`, `taskId:String`, `reasonId:String`, `actorId:String`, `organizationUnitId:String`, `correlationId:String`.
- Custom component types: `WorkflowActionType` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowActionType.java`; `WorkflowDecision` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowDefinition

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowDefinition.java` @ `b52929723c976d53100711969eb7bf1d26f4cb41` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowDefinitionJpaEntity.java` @ `a09108eba1e916d73c4be59b1185d33e0117453a`.
- Candidate relationship fields (2): `id:String`, `typeId:String`.
- Custom component types: `WorkflowDefinitionStatus` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDefinitionStatus.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowInstance

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java` @ `f26f949eb9859ba804257b8fd2653a37f479811d` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowInstanceJpaEntity.java` @ `893178f7cb42814b01b2db30c7075d5846cae48e`.
- Candidate relationship fields (8): `id:String`, `definitionId:String`, `workflowPurposeId:String`, `targetTypeId:String`, `targetId:String`, `currentStepId:String`, `startedByActorId:String`, `correlationId:String`.
- Custom component types: `WorkflowInstanceStatus` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowInstanceStatus.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowStateHistory

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java` @ `0811ba1b2d39b7ed690529bb4ef2d1af3968694f` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStateHistoryJpaEntity.java` @ `48e810a73bb2427bb3dc0ff3fb6b2010e730b717`.
- Candidate relationship fields (9): `id:String`, `instanceId:String`, `taskId:String`, `fromStepId:String`, `toStepId:String`, `actorId:String`, `actionId:String`, `reasonId:String`, `correlationId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowStep

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStep.java` @ `7f722c0440627694507a8631733833606b70c6d3` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepJpaEntity.java` @ `d15b68b818d3557b79263c5101398c6f617a9c07`.
- Candidate relationship fields (5): `id:String`, `definitionId:String`, `stepTypeId:String`, `defaultAssignmentRuleId:String`, `slaPolicyId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowStepAssignmentRule

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStepAssignmentRule.java` @ `6fe60cfd174defc97882393a23a3c80c590491b6` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowStepAssignmentRuleJpaEntity.java` @ `88f95b31c6dcc1a302f0745fb74eb97f8f153ab1`.
- Candidate relationship fields (6): `id:String`, `definitionId:String`, `stepId:String`, `assignmentModeId:String`, `actorId:String`, `organizationUnitId:String`.
- Custom component types: none.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowTask

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java` @ `750267c4bd9cfbd8c7799abe6f6b551b17599964` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTaskJpaEntity.java` @ `434886986e04383c82734ec54a382bfb4bb50b10`.
- Candidate relationship fields (9): `id:String`, `instanceId:String`, `stepId:String`, `assignedActorId:String`, `assignedOrganizationUnitId:String`, `priorityId:String`, `claimedByActorId:String`, `completedByActorId:String`, `assignmentModeId:String`.
- Custom component types: `WorkflowTaskStatus` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowTaskStatus.java`; `WorkflowSlaStatus` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowSlaStatus.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

#### workflow.WorkflowTransition

- Domain: `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java` @ `9b16c1afaccb8609d5fdcf53f3965469342898c3` — unchanged from HMC pinned blob.
- JPA evidence: `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/entity/WorkflowTransitionJpaEntity.java` @ `4ef8685872022dbdd289598201c662acbb791b56`.
- Candidate relationship fields (4): `id:String`, `definitionId:String`, `fromStepId:String`, `toStepId:String`.
- Custom component types: `WorkflowDecision` → `src/main/java/dz/sh/hidra/modules/workflow/domain/value/WorkflowDecision.java`.
- Application-contract corpus: 22 current port files in module `workflow`; exact model relationship semantics deferred to HMS-003.
- Flyway corpus: `src/main/resources/db/migration/V20260611_016__create_workflow_tables.sql`.

### 10.5 HMS-002 outcome

- All 123 catalogue models have a current live path/blob evidence record.
- Candidate ID/reference fields and direct model-typed fields are inventoried without assigning dependency meaning.
- Matching JPA-entity presence is recorded without inferring an ORM relationship from scalar IDs.
- Application-port, Flyway, and domain support-type evidence corpora are identified for every module.
- Source drift relative to the HMC pinned model blobs is explicitly surfaced for HMS-003.
- No topological order, dependency depth, downstream dependent count, strongly connected component, or interactive review code has been computed.
- No production Java, JPA, Flyway, application contract, enum/value type, or database data was modified.

## 11. Current next task

```text
HMS-003 — docs(model-review): classify target model dependencies
```

Do not start HMS-004 or any interactive HMSR review until HMS-003 is completed and reported.
