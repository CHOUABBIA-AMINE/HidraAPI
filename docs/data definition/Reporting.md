# HIDRA — Reporting Data Definition Document

```text
Document     : HIDRA-REPORTING-DATA-DEFINITION-DOCUMENT.md
Module       : reporting
Package      : dz.sh.hidra.modules.reporting
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner        : Sonatrach / TRC : Digitalization Initiative
Author       : Abir MEDJERAB
Status       : Target DDD — no implemented reporting Java module found in repository search
CreatedOn    : 2026-06-11
```

---

## 1. Purpose

The **Reporting** module owns the preparation, generation, scheduling, export, distribution, and traceability of formal operational reports.

Reporting answers:

```text
Which report was requested?
For which period, scope, and module context?
Which data snapshot was used?
Which template and format were used?
When was it generated?
Who requested or approved it?
Where is the generated artifact stored?
Was it distributed?
Can the report be reproduced later?
```

Reporting is not the analytical brain of Hidra. It is the formal output and distribution layer.

---

## 2. Core Boundary Rule

```text
Analytics produces insight.
Reporting packages, freezes, exports, and distributes formal outputs.
```

More explicitly:

```text
Operational modules own business truth.
Analytics owns derived insight and KPIs.
Reporting owns formal report definitions, report runs, frozen report snapshots, generated files, and export/distribution traceability.
Documents owns reusable document metadata and storage references.
Notification owns delivery messages.
Audit owns immutable evidence of who did what.
```

---

## 3. Reporting Owns

Reporting owns:

```text
report definitions
report templates
report parameters
report schedules
report requests
report runs
report input snapshots
report output artifacts
report sections
report tables
report charts
report export formats
report distribution references
report reproducibility metadata
report publication status
report catalog and translations
```

---

## 4. Reporting Does Not Own

Reporting must not own:

```text
telemetry readings
validated telemetry facts
pipeline topology
workflow approvals
audit ledger
incident lifecycle
alarm lifecycle
risk scoring
analytics models
analytics insight generation
simulation calculations
custody transfer quantities
HSE compliance cases
maintenance work orders
binary object storage implementation
notification delivery attempts
```

---

## 5. Relationship With Other Modules

### 5.1 Telemetry

Telemetry owns raw and trusted readings.

Reporting may consume telemetry references or projections to produce reports such as:

```text
daily flow report
pressure report
telemetry quality report
missing readings report
validated readings export
```

Reporting must not correct readings or change telemetry state.

---

### 5.2 Topology

Topology owns the pipeline network, stations, facilities, equipment, measurement points, and topology snapshots.

Reporting may reference a topology snapshot to ensure that a report can be reproduced later.

Example:

```text
Report: Monthly Station Throughput
Topology snapshot: TOPO-SNAPSHOT-2026-05-ACTIVE
Scope: Station ST-014 and connected pipeline segments
```

---

### 5.3 Planning

Planning owns expected values, plan versions, and approval status.

Reporting may generate:

```text
plan vs actual report
approved plan summary
planning variance export
```

Reporting must not create or revise plans.

---

### 5.4 Monitoring

Monitoring owns operational evaluations, states, deviations, alert candidates, and risk signals.

Reporting may generate:

```text
deviation summary report
monitoring rule performance report
threshold violation report
operational state history report
```

Reporting must not decide whether a deviation exists.

---

### 5.5 Alarm Management

Alarm Management owns the alarm lifecycle.

Reporting may generate:

```text
alarm frequency report
alarm acknowledgement delay report
alarm closure report
standing alarms report
```

Reporting must not acknowledge, suppress, shelve, escalate, or close alarms.

---

### 5.6 Incident Management

Incident Management owns the operational incident lifecycle.

Reporting may generate:

```text
incident register report
incident closure status report
incident response time report
root cause distribution report
```

Reporting must not open, assign, resolve, or close incidents.

---

### 5.7 Custody Transfer

Custody Transfer owns official transferred quantities, custody tickets, reconciliation, quality certificates, and accepted quantities.

Reporting may generate:

```text
custody transfer statement
monthly custody quantity report
custody discrepancy report
quality certificate pack
```

Reporting must not calculate official custody quantities unless Custody Transfer delegates a read-only calculation snapshot.

---

### 5.8 HSE

HSE owns safety, health, environment, and compliance cases.

Reporting may generate:

```text
HSE incident report
permit-to-work summary
near-miss report
environmental event report
compliance obligation report
```

Reporting must not decide compliance status.

---

### 5.9 Risk

Risk owns scoring, treatment, acceptance, and residual risk.

Reporting may generate:

```text
risk register export
risk heatmap report
risk treatment status report
residual risk report
```

Reporting must not calculate or approve formal risk scores.

---

### 5.10 Analytics

Analytics owns KPIs, projections, trends, models, and insights.

Reporting may consume analytics outputs to create formal reports.

Example:

```text
Analytics creates monthly KPI values.
Reporting generates the official Monthly Operations Performance Report PDF.
```

---

### 5.11 Documents

Documents owns document metadata, versions, target links, and storage object references.

Reporting may create a generated report artifact and link it to Documents.

Rule:

```text
Reporting generates the report.
Documents manages the document/file metadata lifecycle.
```

---

### 5.12 Notification

Notification owns message delivery.

Reporting may request a notification after report generation.

Rule:

```text
Reporting says: report is ready.
Notification says: message delivered or failed.
```

---

### 5.13 Audit

Audit owns immutable evidence.

Reporting emits audit-ready events for:

```text
report definition created
report requested
report generated
report exported
report published
report distribution requested
report deleted/retired
restricted report accessed
```

Audit does not own the report file or report template.

---

## 6. Aggregate Design

Recommended aggregate roots:

```text
ReportDefinition
ReportRequest
ReportRun
ReportSchedule
ReportPublication
```

Supporting entities:

```text
ReportTemplate
ReportTemplateVersion
ReportParameterDefinition
ReportParameterValue
ReportDataSourceBinding
ReportInputSnapshot
ReportSectionDefinition
ReportSectionResult
ReportTableResult
ReportChartResult
ReportOutputArtifact
ReportDistributionTarget
ReportDistributionRecord
ReportExportFormat
ReportAccessPolicy
ReportCatalogEntry
ReportCatalogTranslation
```

---

## 7. Entity Definitions

## 7.1 ReportDefinition

Represents a reusable report type.

Examples:

```text
DAILY_OPERATIONS_REPORT
MONTHLY_THROUGHPUT_REPORT
PLAN_VS_ACTUAL_REPORT
INCIDENT_REGISTER_REPORT
CUSTODY_TRANSFER_STATEMENT
HSE_COMPLIANCE_REPORT
RISK_REGISTER_REPORT
```

Fields:

```text
id
code
nameAr
nameFr
nameEn
reportCategoryId
ownerModule
description
active
currentTemplateVersionId
requiresApproval
restricted
createdAt
updatedAt
```

Invariants:

```text
code is unique
French name is mandatory
ownerModule must be a known Hidra module
inactive definitions cannot be used for new report requests
restricted reports require explicit access policy
```

---

## 7.2 ReportTemplate

Represents the logical template family for a report.

Fields:

```text
id
reportDefinitionId
code
nameAr
nameFr
nameEn
templateEngine
active
createdAt
updatedAt
```

Example template engines:

```text
HTML
DOCX
JASPER
MARKDOWN
CSV_LAYOUT
EXCEL_LAYOUT
```

---

## 7.3 ReportTemplateVersion

Represents a versioned report layout.

Fields:

```text
id
reportTemplateId
versionNumber
status
layoutContentReference
styleReference
checksum
createdByActorId
createdByDisplayNameSnapshot
createdAt
activatedAt
retiredAt
```

Statuses:

```text
DRAFT
ACTIVE
RETIRED
```

Invariants:

```text
only one ACTIVE version per template
report runs must keep the exact template version used
active template versions cannot be edited directly
```

---

## 7.4 ReportParameterDefinition

Defines a parameter expected by a report.

Fields:

```text
id
reportDefinitionId
code
labelAr
labelFr
labelEn
parameterType
required
defaultValue
allowedValuesReference
validationExpression
sortOrder
active
createdAt
updatedAt
```

Parameter types:

```text
TEXT
NUMBER
BOOLEAN
DATE
DATE_TIME
DATE_RANGE
MODULE_REFERENCE
TOPOLOGY_ASSET_REFERENCE
ORGANIZATION_UNIT_REFERENCE
ENUM_REFERENCE
```

---

## 7.5 ReportRequest

Represents a user/system request to generate a report.

Fields:

```text
id
reportDefinitionId
requestedByActorId
requestedByUsernameSnapshot
requestedByDisplayNameSnapshot
requestedByRoleCodeSnapshot
organizationUnitId
organizationUnitNameSnapshot
requestedAt
purpose
status
correlationId
workflowReferenceId
createdAt
updatedAt
```

Statuses:

```text
DRAFT
SUBMITTED
APPROVED
REJECTED
QUEUED
CANCELLED
```

Invariants:

```text
restricted reports require access validation
reports requiring approval cannot be queued before workflow approval
request actor snapshot must be preserved
```

---

## 7.6 ReportParameterValue

Stores concrete parameter values for a request or run.

Fields:

```text
id
reportRequestId
parameterDefinitionId
parameterCode
valueType
valueText
valueNumber
valueBoolean
valueDate
valueDateTime
valueJson
createdAt
```

Invariants:

```text
only one value field should match valueType
required parameters must be provided before queueing a report run
```

---

## 7.7 ReportDataSourceBinding

Defines which source projection/API/read model is used by a report.

Fields:

```text
id
reportDefinitionId
sourceModule
sourceType
sourceName
sourceContractVersion
required
createdAt
updatedAt
```

Source types:

```text
APPLICATION_QUERY
REPORTING_PROJECTION
ANALYTICS_DATASET
DATABASE_VIEW
EXTERNAL_EXPORT
```

Rule:

```text
Reporting must use application/read ports or approved projections.
Reporting must not read foreign module tables directly unless an explicit reporting projection contract exists.
```

---

## 7.8 ReportInputSnapshot

Freezes the input context of a generated report.

Fields:

```text
id
reportRunId
snapshotType
sourceModule
sourceReferenceId
sourceReferenceCode
sourceReferenceLabel
sourceVersion
snapshotHash
capturedAt
metadataJson
```

Snapshot types:

```text
TOPOLOGY_SNAPSHOT
PLAN_VERSION
ANALYTICS_DATASET_VERSION
RISK_ASSESSMENT_VERSION
CUSTODY_PERIOD
TELEMETRY_TIME_RANGE
INCIDENT_FILTER
HSE_CASE_FILTER
```

Purpose:

```text
Report can be reproduced or explained later.
```

---

## 7.9 ReportRun

Represents one execution attempt of a report request.

Fields:

```text
id
reportRequestId
reportDefinitionId
templateVersionId
status
runMode
queuedAt
startedAt
completedAt
failedAt
failureReason
recordCount
outputCount
executionDurationMs
correlationId
createdAt
updatedAt
```

Run modes:

```text
MANUAL
SCHEDULED
SYSTEM_TRIGGERED
WORKFLOW_TRIGGERED
```

Statuses:

```text
QUEUED
RUNNING
COMPLETED
FAILED
CANCELLED
EXPIRED
```

Invariants:

```text
completed runs must have completedAt
failed runs must have failureReason
report run cannot modify source business data
```

---

## 7.10 ReportSectionDefinition

Defines sections within a report.

Fields:

```text
id
reportDefinitionId
code
titleAr
titleFr
titleEn
sectionType
sortOrder
visibleByDefault
createdAt
updatedAt
```

Section types:

```text
HEADER
SUMMARY
TABLE
CHART
MAP
TEXT
APPENDIX
SIGNATURE
```

---

## 7.11 ReportSectionResult

Stores generated section-level result metadata.

Fields:

```text
id
reportRunId
sectionDefinitionId
sectionCode
status
resultReference
rowCount
warningCount
createdAt
```

---

## 7.12 ReportTableResult

Stores structured generated table output metadata.

Fields:

```text
id
reportSectionResultId
columnSchemaJson
rowCount
contentReference
checksum
createdAt
```

Rule:

```text
Large generated table content should be stored as a document/storage object reference, not inline in operational tables.
```

---

## 7.13 ReportChartResult

Stores generated chart metadata.

Fields:

```text
id
reportSectionResultId
chartType
seriesSchemaJson
imageReference
interactiveSpecReference
createdAt
```

Chart types:

```text
LINE
BAR
STACKED_BAR
PIE
SCATTER
HEATMAP
MAP_OVERLAY
```

---

## 7.14 ReportOutputArtifact

Represents a generated file/export.

Fields:

```text
id
reportRunId
artifactType
format
fileName
mimeType
storageObjectReferenceId
documentReferenceId
checksum
sizeBytes
generatedAt
expiresAt
createdAt
```

Formats:

```text
PDF
XLSX
CSV
JSON
HTML
DOCX
ZIP
```

Invariants:

```text
completed report runs should have at least one output artifact unless configured as data-only
artifact must reference storage or document metadata
checksum is required for formal reports
```

---

## 7.15 ReportSchedule

Represents recurring generation rules.

Fields:

```text
id
reportDefinitionId
code
nameAr
nameFr
nameEn
cronExpression
timezone
active
nextRunAt
lastRunAt
createdByActorId
createdByDisplayNameSnapshot
createdAt
updatedAt
```

Invariants:

```text
inactive schedules cannot create new runs
schedule must preserve actor/system identity
```

---

## 7.16 ReportScheduleParameter

Stores default parameters for scheduled reports.

Fields:

```text
id
reportScheduleId
parameterDefinitionId
parameterCode
valueType
valueJson
createdAt
updatedAt
```

---

## 7.17 ReportPublication

Represents the controlled publication of a generated report.

Fields:

```text
id
reportRunId
publicationStatus
publishedByActorId
publishedByDisplayNameSnapshot
publishedAt
publicationNote
workflowReferenceId
auditReferenceId
createdAt
updatedAt
```

Statuses:

```text
DRAFT
PENDING_APPROVAL
APPROVED
PUBLISHED
RETRACTED
CANCELLED
```

Rule:

```text
Sensitive reports should be published only after workflow approval.
```

---

## 7.18 ReportDistributionTarget

Defines where a published report should be distributed.

Fields:

```text
id
reportDefinitionId
targetType
targetReference
channelId
active
createdAt
updatedAt
```

Target types:

```text
ACTOR
ORGANIZATION_UNIT
ROLE
EMAIL_ADDRESS
EXTERNAL_SYSTEM
DOCUMENT_REPOSITORY
```

---

## 7.19 ReportDistributionRecord

Tracks distribution requests and outcomes.

Fields:

```text
id
reportPublicationId
reportOutputArtifactId
targetType
targetReference
notificationRequestId
integrationOutboundRecordId
status
requestedAt
completedAt
failureReason
createdAt
updatedAt
```

Statuses:

```text
REQUESTED
SENT
FAILED
CANCELLED
```

Rule:

```text
Notification owns delivery attempts.
Integration owns external-system outbound records.
Reporting owns the fact that report distribution was requested and linked to a report artifact.
```

---

## 7.20 ReportAccessPolicy

Defines access constraints for report definitions or generated artifacts.

Fields:

```text
id
reportDefinitionId
scopeType
scopeReferenceId
permissionCode
restricted
maskSensitiveValues
createdAt
updatedAt
```

Scope types:

```text
GLOBAL
ORGANIZATION_UNIT
ROLE
ACTOR
MODULE_SCOPE
```

Rule:

```text
Identity evaluates permissions.
Reporting stores report-specific access policy metadata.
```

---

## 7.21 ReportCatalogEntry

Controlled reporting vocabulary.

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

Catalog names:

```text
REPORT_CATEGORY
REPORT_FORMAT
REPORT_RUN_MODE
REPORT_STATUS_REASON
REPORT_PUBLICATION_STATUS
REPORT_DISTRIBUTION_TARGET_TYPE
REPORT_SECTION_TYPE
```

---

## 7.22 ReportCatalogTranslation

Multilingual catalog label.

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

Supported locales:

```text
ar
fr
en
```

---

## 8. Recommended Tables

```text
hidra_reporting_report_definition
hidra_reporting_report_template
hidra_reporting_report_template_version
hidra_reporting_parameter_definition
hidra_reporting_request
hidra_reporting_parameter_value
hidra_reporting_data_source_binding
hidra_reporting_input_snapshot
hidra_reporting_run
hidra_reporting_section_definition
hidra_reporting_section_result
hidra_reporting_table_result
hidra_reporting_chart_result
hidra_reporting_output_artifact
hidra_reporting_schedule
hidra_reporting_schedule_parameter
hidra_reporting_publication
hidra_reporting_distribution_target
hidra_reporting_distribution_record
hidra_reporting_access_policy
hidra_reporting_catalog_entry
hidra_reporting_catalog_translation
```

---

## 9. Lifecycle

### 9.1 Report Definition Lifecycle

```text
DRAFT -> ACTIVE -> RETIRED
```

Rules:

```text
DRAFT can be edited
ACTIVE can be used for requests
RETIRED cannot be used for new requests
existing report runs preserve historical definition/template versions
```

---

### 9.2 Report Request Lifecycle

```text
DRAFT -> SUBMITTED -> APPROVED -> QUEUED -> RUNNING -> COMPLETED
                     -> REJECTED
                     -> CANCELLED
```

For reports without approval:

```text
SUBMITTED -> QUEUED
```

---

### 9.3 Report Run Lifecycle

```text
QUEUED -> RUNNING -> COMPLETED
                 -> FAILED
                 -> CANCELLED
                 -> EXPIRED
```

---

### 9.4 Report Publication Lifecycle

```text
DRAFT -> PENDING_APPROVAL -> APPROVED -> PUBLISHED
                           -> REJECTED
PUBLISHED -> RETRACTED
```

---

## 10. Domain Events

Recommended events:

```text
ReportDefinitionCreated
ReportDefinitionActivated
ReportDefinitionRetired
ReportTemplateVersionActivated
ReportRequested
ReportRequestApproved
ReportRequestRejected
ReportRunQueued
ReportRunStarted
ReportRunCompleted
ReportRunFailed
ReportOutputArtifactGenerated
ReportPublicationRequested
ReportPublished
ReportRetracted
ReportDistributionRequested
ReportDistributionCompleted
ReportDistributionFailed
RestrictedReportAccessed
```

Events must include:

```text
actor snapshot
target reference
correlation id
request id
organization scope
workflow reference when applicable
audit-ready payload
```

---

## 11. Application Ports

### 11.1 Inbound Ports

```text
CreateReportDefinitionUseCase
ActivateReportDefinitionUseCase
RetireReportDefinitionUseCase
CreateReportTemplateVersionUseCase
RequestReportUseCase
ApproveReportRequestUseCase
QueueReportRunUseCase
GenerateReportUseCase
PublishReportUseCase
RetractReportUseCase
ScheduleReportUseCase
CancelReportScheduleUseCase
GetReportRunUseCase
SearchReportsUseCase
DownloadReportArtifactUseCase
```

### 11.2 Outbound Ports

```text
ReportDefinitionRepositoryPort
ReportRunRepositoryPort
ReportTemplateRepositoryPort
ReportScheduleRepositoryPort
ReportSourceDataPort
ReportDocumentPort
ReportNotificationPort
ReportIntegrationPort
ReportWorkflowPort
ReportAuditEventPort
ReportAuthorizationPort
ReportStoragePort
```

---

## 12. API Surface

Recommended base path:

```text
/api/v1/reports
```

Controller groups:

```text
ReportDefinitionController
ReportTemplateController
ReportRequestController
ReportRunController
ReportScheduleController
ReportPublicationController
ReportArtifactController
ReportCatalogController
```

Endpoint groups:

```text
GET    /api/v1/reports/catalogs/{catalogName}
POST   /api/v1/reports/definitions
GET    /api/v1/reports/definitions
GET    /api/v1/reports/definitions/{definitionId}
POST   /api/v1/reports/definitions/{definitionId}/activate
POST   /api/v1/reports/definitions/{definitionId}/retire
POST   /api/v1/reports/templates
POST   /api/v1/reports/templates/{templateId}/versions
POST   /api/v1/reports/requests
GET    /api/v1/reports/requests
GET    /api/v1/reports/requests/{requestId}
POST   /api/v1/reports/requests/{requestId}/approve
POST   /api/v1/reports/requests/{requestId}/reject
POST   /api/v1/reports/requests/{requestId}/queue
POST   /api/v1/reports/runs
GET    /api/v1/reports/runs
GET    /api/v1/reports/runs/{runId}
GET    /api/v1/reports/runs/{runId}/artifacts
GET    /api/v1/reports/artifacts/{artifactId}/download
POST   /api/v1/reports/schedules
GET    /api/v1/reports/schedules
PATCH  /api/v1/reports/schedules/{scheduleId}
POST   /api/v1/reports/publications
POST   /api/v1/reports/publications/{publicationId}/publish
POST   /api/v1/reports/publications/{publicationId}/retract
```

---

## 13. Reproducibility Rules

A formal report is reproducible only if Reporting stores:

```text
report definition id and version
template version id
parameter values
input snapshot references
source contract versions
actor/request/correlation context
artifact checksum
generation timestamp
```

Rule:

```text
A report must never silently change because a source projection changed later.
```

---

## 14. Security and Access Rules

Reporting must support:

```text
restricted reports
organization-scoped report visibility
actor/role report access policies
sensitive value masking
report artifact expiry
access logging through audit
```

Forbidden:

```text
hard-coded permission checks inside controllers
report generation bypassing authorization
untracked access to restricted report artifacts
embedding secrets in report definitions or templates
```

---

## 15. Reporting vs Analytics vs Documents

```text
Analytics:
  calculates KPIs, trends, insights, projections, digital-twin-readiness views

Reporting:
  defines formal reports, freezes report inputs, generates outputs, exports files, manages schedules/publication

Documents:
  manages document/file metadata, versions, target links, storage references
```

Example:

```text
Analytics calculates monthly throughput KPI.
Reporting generates Monthly Operations Report PDF.
Documents stores the generated PDF metadata and attachment link.
Notification emails selected recipients that the report is ready.
Audit records who generated, published, and accessed the report.
```

---

## 16. Anti-Corruption Rules

Reporting must never import foreign module internals:

```text
forbidden: dz.sh.hidra.modules.telemetry.infrastructure.*
forbidden: dz.sh.hidra.modules.topology.infrastructure.*
forbidden: dz.sh.hidra.modules.analytics.infrastructure.*
forbidden: dz.sh.hidra.modules.risk.infrastructure.*
forbidden: dz.sh.hidra.modules.audit.infrastructure.*
```

Allowed integration style:

```text
application ports
read-only projections
published DTO contracts
stable references
input snapshots
```

---

## 17. Acceptance Criteria

Reporting is accepted when:

```text
report definitions can be created and versioned
report templates can be versioned
report requests preserve actor and organization snapshots
report parameters are validated
report runs are traceable and reproducible
report input snapshots are captured
report outputs are generated with checksums
PDF/XLSX/CSV/JSON exports can be represented
scheduled reports can be configured
restricted report access is controlled
report publication can require workflow approval
distribution is linked to notification or integration records
audit-ready events are emitted
Reporting never modifies source-of-truth operational state
```

---

## 18. Final Positioning

```text
Reporting is the formal operational output layer of Hidra.
It freezes trusted inputs, generates official artifacts, and tracks publication/distribution.
It is not analytics, not audit, not documents, and not the owner of operational truth.
```
