# HIDRA — Analytics Data Definition Document

```text
Document        : HIDRA-ANALYTICS-DATA-DEFINITION-DOCUMENT.md
Module          : analytics
Package         : dz.sh.hidra.modules.analytics
Product         : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner           : Sonatrach / TRC : Digitalization Initiative
Author          : Abir MEDJERAB
CreatedOn       : 2026-06-11
Status          : Target DDD / Repository-aligned architecture definition
Evidence level  : Target architecture, grounded in Hidra macro architecture v1.2
```

---

## 1. Purpose

The **Analytics** module transforms validated operational history into derived intelligence.

It owns:

```text
KPIs
metric definitions
metric evaluations
analytic projections
trend analysis
risk analytics views
performance indicators
digital-twin-readiness views
analytic datasets
analytic model runs
analytic insights
```

Analytics answers:

```text
How did the network perform?
What trends are visible?
Where are deviations recurring?
Which assets, stations, lines, or operating regimes show degraded performance?
Which risks are increasing?
Which datasets are ready for simulation, AI, or digital twin usage?
What derived indicators should decision makers see?
```

Analytics is **not** the source of operational truth.

It consumes trusted facts from other modules and produces derived views.

---

## 2. Core boundary rule

```text
Analytics interprets trusted history.
It does not own operational truth.
```

More explicitly:

```text
Telemetry owns readings.
Planning owns expected targets.
Monitoring owns operational deviations.
Alarms own alarm lifecycle.
Incidents own incident lifecycle.
Integrity owns condition assessment.
Risk owns risk scoring and treatment.
Reporting owns formal report/export production.
Analytics owns derived indicators, trends, projections, and analytical views.
```

---

## 3. Why Analytics is separate from Reporting

Analytics and Reporting are not the same module.

```text
Analytics calculates and materializes indicators.
Reporting packages information for human/regulatory/management consumption.
```

Examples:

| Concern | Owner |
|---|---|
| KPI formula | Analytics |
| KPI daily value | Analytics |
| trend detection | Analytics |
| recurring deviation pattern | Analytics |
| dashboard-ready projection | Analytics |
| official monthly operations report | Reporting |
| PDF/Excel export layout | Reporting |
| scheduled report distribution | Reporting + Notification |
| report approval | Workflow |
| report evidence trail | Audit |

Rule:

```text
If it computes insight, it belongs to Analytics.
If it formats and distributes a document/report, it belongs to Reporting.
```

---

## 4. Why Analytics is separate from Risk

Risk Management owns formal risk scoring, treatment, acceptance, and residual risk.

Analytics may produce risk-related views, patterns, or indicators, but it does not approve or own enterprise risk state.

```text
Analytics can say:
  "corrosion-related incidents are increasing in this corridor"

Risk Management says:
  "this scenario has likelihood L4, consequence C5, rating CRITICAL, treatment plan TP-09"
```

---

## 5. Why Analytics is separate from Simulation

Simulation evaluates hypothetical scenarios and candidate futures.

Analytics evaluates historical and derived operational intelligence.

```text
Analytics tells what happened and what patterns exist.
Simulation tests what could happen under a scenario.
```

Analytics may provide datasets to Simulation, but Simulation owns the scenario, solver run, optimization candidate, and recommendation.

---

## 6. Source modules consumed by Analytics

Analytics may consume read-side references or exported trusted views from:

```text
topology
telemetry
planning
monitoring
alarms
incidents
leakdetection
integrity
assets
custody
hse
workflow
audit
risk
simulation
```

Analytics must access these through:

```text
application ports
published events
projection feeds
outbox events
integration/read-model adapters
```

It must not import foreign domain models or write into foreign module tables.

---

## 7. Analytics does not own

Analytics does not own:

```text
pipeline network topology
stations, facilities, equipment
raw telemetry readings
trusted telemetry validation workflow
planned operational targets
monitoring thresholds
alarm lifecycle
incident lifecycle
leak verification lifecycle
integrity assessment lifecycle
asset maintenance work orders
custody transfer tickets
HSE cases
workflow tasks
notification delivery
formal audit ledger
configuration secrets
external integration jobs
formal report templates and exports
```

Analytics must not:

```text
modify source-of-truth operational state
approve business decisions
close incidents
acknowledge alarms
validate telemetry readings
change topology snapshots
create custody transfer tickets
execute maintenance work orders
actuate SCADA/PLC/RTU/SIS/ESD systems
```

---

## 8. Main aggregates

```text
AnalyticsSubjectArea
AnalyticsDataset
AnalyticsProjectionDefinition
MetricDefinition
KpiDefinition
AnalyticsModel
AnalyticsInsight
```

Recommended aggregate ownership:

| Aggregate | Owns |
|---|---|
| `AnalyticsSubjectArea` | analytical grouping and ownership scope |
| `AnalyticsDataset` | dataset metadata, lineage, schema, quality-readiness |
| `AnalyticsProjectionDefinition` | projection computation specification |
| `MetricDefinition` | reusable metric formula and calculation policy |
| `KpiDefinition` | business KPI definition, target bands, display policy |
| `AnalyticsModel` | analytical model registry, versions, execution metadata |
| `AnalyticsInsight` | derived analytical finding or observation |

---

## 9. Entity definitions

### 9.1 AnalyticsSubjectArea

Represents a high-level analytical domain.

Examples:

```text
NETWORK_PERFORMANCE
TELEMETRY_QUALITY
PLANNED_VS_ACTUAL
INCIDENT_PERFORMANCE
INTEGRITY_TRENDS
ASSET_RELIABILITY
CUSTODY_RECONCILIATION
HSE_PERFORMANCE
RISK_EVOLUTION
DIGITAL_TWIN_READINESS
```

Fields:

```text
id
code
nameAr
nameFr
nameEn
description
ownerModule
active
createdAt
updatedAt
```

Rules:

```text
subject area must have unique code
subject area must not imply source data ownership
ownerModule identifies analytical stewardship, not transactional ownership
```

---

### 9.2 AnalyticsDataSourceReference

References a source feed, read model, event stream, or trusted module export.

Fields:

```text
id
sourceModule
sourceType
sourceName
sourceVersion
accessMode
refreshMode
trusted
lastAvailableAt
createdAt
updatedAt
```

Allowed `accessMode` examples:

```text
APPLICATION_PORT
PROJECTION_TABLE
OUTBOX_EVENT
EVENT_STREAM
EXTERNAL_EXPORT
READ_MODEL_API
```

Rules:

```text
source must be read-only from Analytics perspective
source must declare owning module
source version must be captured for lineage
```

---

### 9.3 AnalyticsDataset

Represents a curated analytical dataset.

Fields:

```text
id
code
nameAr
nameFr
nameEn
subjectAreaId
datasetType
refreshMode
lineageStatus
qualityStatus
schemaVersion
createdFrom
validFrom
validTo
createdAt
updatedAt
```

Dataset types:

```text
SNAPSHOT
TIME_SERIES
AGGREGATE
FEATURE_SET
TRAINING_DATASET
VALIDATION_DATASET
DASHBOARD_VIEW
DIGITAL_TWIN_READINESS_VIEW
```

Rules:

```text
dataset is derived
lineage must point to source references
training datasets must be immutable after publication
```

---

### 9.4 AnalyticsDatasetVersion

Represents a versioned dataset release.

Fields:

```text
id
datasetId
versionNumber
schemaHash
dataHash
rowCount
periodStart
periodEnd
qualityScore
published
publishedAt
publishedByActorId
createdAt
```

Rules:

```text
published dataset version is immutable
schemaHash and dataHash support reproducibility
periodStart must be before or equal to periodEnd
```

---

### 9.5 AnalyticsDatasetLineage

Captures source lineage for a dataset version.

Fields:

```text
id
datasetVersionId
sourceModule
sourceObjectType
sourceObjectId
sourceSnapshotId
sourceVersion
sourcePeriodStart
sourcePeriodEnd
lineageRole
createdAt
```

Examples:

```text
TELEMETRY_TRUSTED_READING_WINDOW
TOPOLOGY_SNAPSHOT
PLANNING_APPROVED_PLAN_VERSION
MONITORING_EVALUATION_SET
INCIDENT_TIMELINE_EXPORT
AUDIT_EVENT_RANGE
```

Rules:

```text
every published dataset version must have lineage
lineage must not be edited after publication
```

---

### 9.6 AnalyticsProjectionDefinition

Defines a materialized analytical projection.

Fields:

```text
id
code
nameAr
nameFr
nameEn
subjectAreaId
projectionType
calculationPolicy
refreshPolicy
retentionPolicy
active
createdAt
updatedAt
```

Projection types:

```text
DAILY_KPI
HOURLY_OPERATIONAL_SUMMARY
STATION_PERFORMANCE_VIEW
PIPELINE_PERFORMANCE_VIEW
INCIDENT_PERFORMANCE_VIEW
RISK_EVOLUTION_VIEW
ASSET_RELIABILITY_VIEW
CUSTODY_VARIANCE_VIEW
HSE_PERFORMANCE_VIEW
```

Rules:

```text
projection definition must be versioned when formula changes
projection must be rebuildable from source truth and audit history
```

---

### 9.7 AnalyticsProjectionRun

Represents execution of a projection definition.

Fields:

```text
id
projectionDefinitionId
runStatus
runMode
periodStart
periodEnd
startedAt
completedAt
sourceWatermark
recordsRead
recordsWritten
errorCode
errorMessage
correlationId
createdAt
```

Run modes:

```text
FULL_REBUILD
INCREMENTAL
BACKFILL
MANUAL_RECOMPUTE
SCHEDULED
```

Rules:

```text
failed run must retain error context
successful run must record source watermark
projection runs must be idempotent where possible
```

---

### 9.8 AnalyticsProjectionSnapshot

Represents a published projection state.

Fields:

```text
id
projectionDefinitionId
projectionRunId
snapshotCode
periodStart
periodEnd
snapshotStatus
publishedAt
publishedByActorId
createdAt
```

Rules:

```text
published snapshot is read-only
snapshot must be tied to one projection run
```

---

### 9.9 MetricDefinition

Defines a reusable analytical metric.

Fields:

```text
id
code
nameAr
nameFr
nameEn
subjectAreaId
metricType
formulaExpression
unitId
aggregationMethod
periodGranularity
active
createdAt
updatedAt
```

Metric examples:

```text
AVERAGE_FLOW_RATE
MAX_PRESSURE_DEVIATION
TELEMETRY_AVAILABILITY_RATE
VALIDATION_DELAY_MINUTES
INCIDENT_CLOSURE_TIME
ALARM_ACKNOWLEDGEMENT_TIME
PIPELINE_UTILIZATION_RATE
ENERGY_PER_VOLUME
CUSTODY_VARIANCE_PERCENT
HSE_CASE_RATE
```

Rules:

```text
formula change requires new MetricDefinitionVersion
metric must declare unit and granularity
metric must not mutate source data
```

---

### 9.10 MetricDefinitionVersion

Versioned metric formula and computation policy.

Fields:

```text
id
metricDefinitionId
versionNumber
formulaExpression
calculationDescription
validFrom
validTo
createdByActorId
createdAt
```

Rules:

```text
published metric version is immutable
validity periods must not overlap for active calculation use
```

---

### 9.11 MetricEvaluationRun

Represents a metric calculation execution.

Fields:

```text
id
metricDefinitionVersionId
runStatus
periodStart
periodEnd
scopeType
scopeId
startedAt
completedAt
recordsRead
recordsProduced
errorCode
errorMessage
correlationId
createdAt
```

Rules:

```text
scope must reference a topology asset, organization unit, product, or module-defined analytical scope
failed run must be auditable
```

---

### 9.12 MetricValue

Stores a calculated metric value.

Fields:

```text
id
metricEvaluationRunId
metricDefinitionId
metricDefinitionVersionId
scopeType
scopeId
periodStart
periodEnd
valueNumeric
valueText
unitId
qualityStatus
calculatedAt
```

Rules:

```text
MetricValue is derived and rebuildable
MetricValue must not be used as source of operational truth
```

---

### 9.13 KpiDefinition

Defines a business KPI derived from one or more metrics.

Fields:

```text
id
code
nameAr
nameFr
nameEn
subjectAreaId
primaryMetricDefinitionId
kpiCategoryId
displayUnitId
defaultGranularity
active
createdAt
updatedAt
```

KPI categories may include:

```text
OPERATIONAL_PERFORMANCE
TELEMETRY_QUALITY
PLANNING_PERFORMANCE
ALARM_PERFORMANCE
INCIDENT_PERFORMANCE
ASSET_RELIABILITY
INTEGRITY_HEALTH
CUSTODY_ACCURACY
HSE_PERFORMANCE
RISK_EVOLUTION
```

---

### 9.14 KpiBand

Defines KPI interpretation bands for dashboards and analytics.

Fields:

```text
id
kpiDefinitionId
bandCode
labelAr
labelFr
labelEn
minValue
maxValue
severityId
sortOrder
active
createdAt
updatedAt
```

Rules:

```text
KPI bands are analytical interpretation only
Monitoring thresholds remain owned by Monitoring
Risk ratings remain owned by Risk Management
```

---

### 9.15 KpiEvaluation

Stores evaluated KPI values.

Fields:

```text
id
kpiDefinitionId
metricValueId
scopeType
scopeId
periodStart
periodEnd
value
unitId
bandId
trendDirection
evaluatedAt
```

Rules:

```text
KPI evaluation is derived
KPI evaluation must reference source metric value or calculation run
```

---

### 9.16 TrendAnalysis

Represents an analytical trend detected over time.

Fields:

```text
id
subjectAreaId
trendType
scopeType
scopeId
metricDefinitionId
periodStart
periodEnd
trendDirection
confidenceScore
strengthScore
detectedAt
createdAt
```

Trend types:

```text
INCREASING
DECREASING
STABLE
SEASONAL
VOLATILE
STEP_CHANGE
DEGRADATION
RECOVERY
```

Rules:

```text
trend is analytical evidence, not a monitoring alert
trend must reference metric or dataset source
```

---

### 9.17 TrendPoint

Stores points used in trend analysis.

Fields:

```text
id
trendAnalysisId
periodStart
periodEnd
value
unitId
sourceMetricValueId
createdAt
```

---

### 9.18 AnalyticsModel

Represents an analytical model definition.

Fields:

```text
id
code
nameAr
nameFr
nameEn
modelType
subjectAreaId
ownerModule
status
createdAt
updatedAt
```

Model types:

```text
RULE_BASED
STATISTICAL
FORECASTING
ANOMALY_DETECTION
CLASSIFICATION
REGRESSION
CLUSTERING
OPTIMIZATION_SUPPORT
```

Rules:

```text
AnalyticsModel does not own simulation solver models
AnalyticsModel does not directly trigger operational actions
```

---

### 9.19 AnalyticsModelVersion

Version of an analytical model.

Fields:

```text
id
analyticsModelId
versionNumber
modelArtifactReference
trainingDatasetVersionId
validationDatasetVersionId
modelParametersJson
performanceSummaryJson
status
createdByActorId
createdAt
```

Rules:

```text
published model version is immutable
model artifact storage is external to Analytics table payload
training dataset lineage must be preserved
```

---

### 9.20 AnalyticsModelRun

Represents execution of an analytical model.

Fields:

```text
id
analyticsModelVersionId
runType
runStatus
inputDatasetVersionId
scopeType
scopeId
periodStart
periodEnd
startedAt
completedAt
outputDatasetVersionId
errorCode
errorMessage
correlationId
createdAt
```

Run types:

```text
TRAINING
VALIDATION
SCORING
BACKTEST
BATCH_INFERENCE
FEATURE_EXTRACTION
```

Rules:

```text
model run output is derived
failed model run must preserve diagnostics
```

---

### 9.21 AnalyticsFeatureSet

Represents features prepared for models or advanced analytics.

Fields:

```text
id
code
nameAr
nameFr
nameEn
subjectAreaId
sourceDatasetId
featureSchemaVersion
active
createdAt
updatedAt
```

---

### 9.22 AnalyticsFeatureValue

Stores calculated feature values when materialized in Hidra.

Fields:

```text
id
featureSetId
datasetVersionId
scopeType
scopeId
featureName
featureValueNumeric
featureValueText
featureValueBoolean
periodStart
periodEnd
calculatedAt
```

Rules:

```text
feature values are derived and rebuildable
feature values must preserve dataset lineage
```

---

### 9.23 AnalyticsInsight

Represents a derived analytical finding.

Fields:

```text
id
insightType
subjectAreaId
scopeType
scopeId
title
summary
severityId
confidenceScore
sourceProjectionSnapshotId
sourceTrendAnalysisId
sourceModelRunId
status
createdAt
updatedAt
```

Insight types:

```text
RECURRING_DEVIATION
PERFORMANCE_DEGRADATION
DATA_QUALITY_ISSUE
RISK_INCREASE_PATTERN
ASSET_RELIABILITY_PATTERN
INTEGRITY_DEGRADATION_PATTERN
CUSTODY_VARIANCE_PATTERN
HSE_TREND
DIGITAL_TWIN_READINESS_GAP
```

Rules:

```text
insight is advisory
insight does not open incidents directly
insight can be referenced by Monitoring, Risk, HSE, Integrity, Planning, or Reporting after review
```

---

### 9.24 AnalyticsInsightEvidence

Links insight to supporting evidence.

Fields:

```text
id
analyticsInsightId
evidenceType
sourceModule
sourceObjectType
sourceObjectId
sourceLabelSnapshot
weight
createdAt
```

Rules:

```text
evidence must preserve source module ownership
evidence must not duplicate full source record payload unless approved for snapshotting
```

---

### 9.25 DigitalTwinReadinessAssessment

Assesses whether a topology area, station, segment, or asset has enough data quality and model readiness for future digital twin scenarios.

Fields:

```text
id
scopeType
scopeId
topologySnapshotId
assessmentPeriodStart
assessmentPeriodEnd
telemetryCompletenessScore
telemetryQualityScore
topologyCompletenessScore
modelAvailabilityScore
lineageCompletenessScore
overallReadinessScore
readinessStatus
assessedAt
createdAt
```

Rules:

```text
readiness is analytical, not a digital twin runtime
readiness assessment must not alter topology, telemetry, or simulation models
```

---

### 9.26 AnalyticsAccessPolicy

Controls who can view or execute specific analytics assets.

Fields:

```text
id
analyticsObjectType
analyticsObjectId
accessScopeType
accessScopeId
permissionCode
active
createdAt
updatedAt
```

Rules:

```text
identity owns permission evaluation
analytics stores analytics-object access policy references only if required
```

---

### 9.27 AnalyticsCatalogEntry

Controlled vocabulary for analytics business taxonomy.

Fields:

```text
id
catalogName
code
active
sortOrder
systemDefined
createdAt
updatedAt
```

Examples:

```text
KPI_CATEGORY
MODEL_TYPE
TREND_TYPE
INSIGHT_TYPE
READINESS_STATUS
ANALYTICS_SEVERITY
DATASET_TYPE
PROJECTION_TYPE
REFRESH_MODE
```

---

### 9.28 AnalyticsCatalogTranslation

Localized labels for analytics catalog entries.

Fields:

```text
id
catalogEntryId
locale
name
description
createdAt
updatedAt
```

Rules:

```text
French labels are mandatory
Arabic and English should be supported where user-facing
```

---

## 10. Analytical scopes

Analytics should support neutral scope references.

Examples:

```text
NETWORK
PIPELINE_SYSTEM
PIPELINE
PIPELINE_SEGMENT
STATION
FACILITY
EQUIPMENT
MEASUREMENT_POINT
ORGANIZATION_UNIT
PRODUCT
CUSTODY_TRANSFER_POINT
HSE_SITE
RISK_AREA
```

Scope fields should use:

```text
scopeType
scopeId
scopeCodeSnapshot
scopeLabelSnapshot
topologySnapshotId when applicable
```

Rule:

```text
Analytics may reference topology objects by stable IDs and snapshots.
It must not own or mutate topology objects.
```

---

## 11. Lifecycle examples

### 11.1 KPI calculation lifecycle

```text
Define MetricDefinition
  -> Publish MetricDefinitionVersion
    -> Run MetricEvaluationRun
      -> Store MetricValue
        -> Evaluate KpiDefinition
          -> Store KpiEvaluation
            -> Publish AnalyticsProjectionSnapshot
```

### 11.2 Trend detection lifecycle

```text
MetricValue series available
  -> Run TrendAnalysis
    -> Store TrendPoint values
      -> Create AnalyticsInsight if pattern is meaningful
        -> Make insight available to Reporting / Risk / Planning / Monitoring
```

### 11.3 Digital twin readiness lifecycle

```text
TopologySnapshot selected
  -> Trusted telemetry coverage checked
    -> Model availability checked
      -> Data lineage checked
        -> DigitalTwinReadinessAssessment created
          -> Readiness gaps exposed as AnalyticsInsight
```

---

## 12. Domain events

Analytics may emit:

```text
AnalyticsDatasetPublished
AnalyticsProjectionCompleted
AnalyticsProjectionFailed
MetricValueCalculated
KpiEvaluated
TrendDetected
AnalyticsInsightCreated
AnalyticsInsightDismissed
AnalyticsModelVersionPublished
AnalyticsModelRunCompleted
DigitalTwinReadinessAssessed
```

Events must include:

```text
eventId
aggregateId
aggregateType
eventType
occurredAt
correlationId
actorId when human-initiated
sourceModule references
```

---

## 13. Commands

Recommended commands:

```text
CreateAnalyticsSubjectAreaCommand
RegisterAnalyticsDataSourceCommand
CreateAnalyticsDatasetCommand
PublishAnalyticsDatasetVersionCommand
CreateProjectionDefinitionCommand
RunProjectionCommand
CreateMetricDefinitionCommand
PublishMetricDefinitionVersionCommand
RunMetricEvaluationCommand
CreateKpiDefinitionCommand
EvaluateKpiCommand
RunTrendAnalysisCommand
RegisterAnalyticsModelCommand
PublishAnalyticsModelVersionCommand
RunAnalyticsModelCommand
CreateAnalyticsInsightCommand
DismissAnalyticsInsightCommand
AssessDigitalTwinReadinessCommand
```

---

## 14. Queries

Recommended queries:

```text
GetAnalyticsSubjectAreaQuery
ListAnalyticsDatasetsQuery
GetDatasetLineageQuery
GetMetricDefinitionQuery
ListMetricValuesQuery
GetKpiDefinitionQuery
GetKpiEvaluationQuery
ListKpisBySubjectAreaQuery
ListTrendsQuery
GetAnalyticsInsightQuery
ListInsightsByScopeQuery
GetDigitalTwinReadinessAssessmentQuery
ListProjectionSnapshotsQuery
GetAnalyticsModelRunQuery
```

---

## 15. Ports

### 15.1 Inbound ports

```text
AnalyticsDatasetUseCase
AnalyticsProjectionUseCase
MetricEvaluationUseCase
KpiEvaluationUseCase
TrendAnalysisUseCase
AnalyticsInsightUseCase
AnalyticsModelUseCase
DigitalTwinReadinessUseCase
```

### 15.2 Outbound ports

```text
AnalyticsDatasetRepositoryPort
AnalyticsProjectionRepositoryPort
MetricRepositoryPort
KpiRepositoryPort
TrendRepositoryPort
AnalyticsModelRepositoryPort
AnalyticsInsightRepositoryPort
TopologySnapshotLookupPort
TrustedTelemetryLookupPort
PlanningActualComparisonLookupPort
MonitoringEvaluationLookupPort
AlarmHistoryLookupPort
IncidentHistoryLookupPort
IntegrityAssessmentLookupPort
AssetReliabilityLookupPort
CustodyHistoryLookupPort
HseHistoryLookupPort
RiskAssessmentLookupPort
AuditHistoryLookupPort
DocumentReferencePort
```

Rules:

```text
outbound ports return read models or snapshots
outbound ports must not expose foreign aggregates or JPA entities
```

---

## 16. Persistence tables

Recommended tables:

```text
hidra_analytics_subject_area
hidra_analytics_data_source_reference
hidra_analytics_dataset
hidra_analytics_dataset_version
hidra_analytics_dataset_lineage
hidra_analytics_projection_definition
hidra_analytics_projection_run
hidra_analytics_projection_snapshot
hidra_analytics_metric_definition
hidra_analytics_metric_definition_version
hidra_analytics_metric_evaluation_run
hidra_analytics_metric_value
hidra_analytics_kpi_definition
hidra_analytics_kpi_band
hidra_analytics_kpi_evaluation
hidra_analytics_trend_analysis
hidra_analytics_trend_point
hidra_analytics_model
hidra_analytics_model_version
hidra_analytics_model_run
hidra_analytics_feature_set
hidra_analytics_feature_value
hidra_analytics_insight
hidra_analytics_insight_evidence
hidra_analytics_digital_twin_readiness_assessment
hidra_analytics_access_policy
hidra_analytics_catalog_entry
hidra_analytics_catalog_translation
```

---

## 17. Package structure

```text
src/main/java/dz/sh/hidra/modules/analytics
  api
    rest
      controller
      request
      response
      mapper
  application
    command
    query
    dto
    port
      in
      out
    service
    mapper
  domain
    model
    value
    event
    policy
    service
    exception
  infrastructure
    configuration
    persistence
      entity
      repository
      mapper
      adapter
    projection
    scheduler
    adapter
```

---

## 18. REST API surface

Recommended base path:

```text
/api/v1/analytics
```

Endpoint groups:

```text
/api/v1/analytics/subject-areas
/api/v1/analytics/data-sources
/api/v1/analytics/datasets
/api/v1/analytics/datasets/{datasetId}/versions
/api/v1/analytics/datasets/{datasetId}/lineage
/api/v1/analytics/projections
/api/v1/analytics/projections/{projectionId}/runs
/api/v1/analytics/projections/{projectionId}/snapshots
/api/v1/analytics/metrics
/api/v1/analytics/metrics/{metricId}/values
/api/v1/analytics/kpis
/api/v1/analytics/kpis/{kpiId}/evaluations
/api/v1/analytics/trends
/api/v1/analytics/models
/api/v1/analytics/models/{modelId}/versions
/api/v1/analytics/models/{modelId}/runs
/api/v1/analytics/insights
/api/v1/analytics/readiness/digital-twin
/api/v1/analytics/catalogs
```

---

## 19. Invariants

```text
Analytics cannot modify source-of-truth operational state.
Published dataset versions are immutable.
Published model versions are immutable.
Metric formula changes require a new version.
Projection snapshots must be rebuildable or lineage-preserved.
Analytical insights are advisory until accepted by an owning business module.
Every materialized dataset must preserve lineage.
Analytics must not import foreign module domain classes.
Analytics must not write directly into foreign module tables.
Analytics does not replace Monitoring, Risk, Simulation, Reporting, or Audit.
```

---

## 20. Relationship to dashboards and topology visualization

Analytics may produce dashboard-ready projections.

It does not own UI rendering or topology graph structure.

```text
Topology owns graph and network visualization base.
Monitoring owns deviation overlays.
Alarms own alarm overlays.
Incidents own incident overlays.
Risk owns risk overlays.
Simulation owns scenario/result overlays.
Analytics owns derived KPI/trend overlays.
```

Example:

```text
Pipeline utilization heat map
```

Ownership split:

```text
Topology -> pipeline geometry and graph
Telemetry -> trusted readings
Analytics -> calculated utilization score
Risk -> accepted risk rating overlay
UI -> visual rendering
```

---

## 21. Minimal v1 implementation recommendation

Implement first:

```text
AnalyticsSubjectArea
AnalyticsDataSourceReference
AnalyticsDataset
AnalyticsDatasetVersion
AnalyticsDatasetLineage
MetricDefinition
MetricDefinitionVersion
MetricEvaluationRun
MetricValue
KpiDefinition
KpiBand
KpiEvaluation
AnalyticsProjectionDefinition
AnalyticsProjectionRun
AnalyticsProjectionSnapshot
AnalyticsCatalogEntry
AnalyticsCatalogTranslation
```

Delay until later:

```text
AnalyticsModel
AnalyticsModelVersion
AnalyticsModelRun
AnalyticsFeatureSet
AnalyticsFeatureValue
Advanced anomaly detection
DigitalTwinReadinessAssessment
Complex ML lifecycle
```

Reason:

```text
Start with trusted KPI and projection foundations before advanced AI or digital twin analytics.
```

---

## 22. Acceptance criteria

Analytics is accepted when:

```text
metric definitions can be created and versioned
KPI definitions can be created and evaluated
KPI bands can classify values without becoming monitoring thresholds
projection definitions can be executed and materialized
projection runs keep status, watermark, and errors
dataset versions preserve lineage
analytics does not modify source operational tables
analytics can expose dashboard-ready derived views
analytics insights are advisory and traceable
```

---

## 23. Opinionated summary

```text
Analytics is the intelligence layer over trusted history.
It is not the operational brain that changes the network.
It is not the report printer.
It is not the risk owner.
It is not the simulation solver.
It is not the audit ledger.
```

The non-negotiable rule:

```text
Trusted facts first.
Analytics second.
Decisions remain owned by the business modules.
```
