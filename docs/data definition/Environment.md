# HIDRA Environment Data Definition Document

```text
Document     : HIDRA-ENVIRONMENT-DATA-DEFINITION-DOCUMENT.md
Module       : environment
Package      : dz.sh.hidra.modules.environment
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
Owner        : Sonatrach / TRC : Digitalization Initiative
Status       : Target DDD / not yet implemented as a Java module
CreatedOn    : 2026-06-11
```

---

## 1. Purpose

The **Environment** module manages environmental observations, environmental events, emissions, discharges, spills, waste, environmental samples, environmental impact evidence, remediation follow-up, and environmental compliance records for hydrocarbon transportation operations.

Environment answers:

```text
What environmental condition was observed?
Where did it happen?
Which topology asset or operating area is involved?
What pollutant, emission, discharge, spill, waste, or environmental medium is concerned?
What quantity, concentration, severity, or impact was recorded?
What evidence supports the environmental record?
What remediation or follow-up is required?
Has the environmental issue been closed?
```

Environment is a business bounded context. It is **not** a generic configuration area, not a document archive, not a reporting dashboard, and not an HSE catch-all.

---

## 2. Status

Repository searches did not find an implemented Java module for `environment`.

Therefore this document defines the **target DDD design** for:

```text
dz.sh.hidra.modules.environment
```

The module should be introduced after the core operational chain is stable:

```text
Topology
  -> Telemetry
  -> Planning
  -> Monitoring
  -> Alarm Management
  -> Leak Detection
  -> Incident Management
  -> HSE Management
  -> Environment
```

Environment can also be introduced in parallel with HSE once incidents and audit-ready evidence are available.

---

## 3. Boundary Position

Environment sits beside HSE, incidents, monitoring, telemetry, topology, risk, audit, workflow, documents, analytics, and reporting.

### 3.1 Core Rule

```text
Environment owns environmental facts, impacts, samples, emissions, waste, remediation, and environmental compliance evidence.

It does not own the operational incident, alarm lifecycle, telemetry reading, topology asset, HSE safety case, risk score, audit ledger, document binary, or report output.
```

### 3.2 Short Rule

```text
Incidents say what operational problem happened.
HSE says what safety/health/compliance case exists.
Environment says what environmental condition, release, impact, and remediation exist.
```

---

## 4. Ownership Matrix

| Concept | Owner |
|---|---|
| Pipeline, station, terminal, equipment, topology asset | `topology` |
| Sensor value, source tag, telemetry reading quality | `telemetry` |
| Threshold breach or deviation | `monitoring` |
| Formal alarm lifecycle | `alarms` |
| Leak suspicion and localization | `leakdetection` |
| Operational incident lifecycle | `incidents` |
| Safety case, near miss, injury, permit-to-work | `hse` |
| Environmental event, spill, emission, waste, sample, remediation | `environment` |
| Formal risk score and treatment plan | `risk` |
| Approval and routing | `workflow` |
| Immutable evidence trail | `audit` |
| File metadata and attachments | `documents` |
| KPI/trend/derived insight | `analytics` |
| Formal report generation/export | `reporting` |
| External regulator/system exchange | `integration` |
| Message delivery | `notification` |

---

## 5. Environment Owns

Environment owns:

```text
EnvironmentalEvent
EnvironmentalObservation
EnvironmentalMonitoringPoint
EnvironmentalSample
EnvironmentalSampleResult
PollutantCatalog
EnvironmentalMediumCatalog
EmissionRecord
DischargeRecord
SpillRecord
WasteGenerationRecord
WasteTransferRecord
WasteDisposalRecord
EnvironmentalImpactAssessment
EnvironmentalRemediationPlan
EnvironmentalRemediationAction
EnvironmentalComplianceObligation
EnvironmentalComplianceAssessment
EnvironmentalPermitReference
EnvironmentalInspection
EnvironmentalFinding
EnvironmentalCorrectiveAction
EnvironmentalClosure
EnvironmentalEvidenceLink
EnvironmentCatalogEntry
EnvironmentCatalogTranslation
```

---

## 6. Environment Does Not Own

Environment does not own:

```text
pipeline topology
equipment master data
telemetry readings
monitoring thresholds
alarm lifecycle
leak detection case
incident lifecycle
HSE injury/illness/permit-to-work
maintenance work orders
formal risk score
workflow tasks
audit events
document binaries
report generation
notification delivery
external connector execution
```

If Environment needs one of these objects, it references it by stable ID and snapshot.

---

## 7. Core Aggregates

### 7.1 EnvironmentalEvent

Represents an environmental event or condition requiring environmental tracking.

Examples:

```text
oil spill
gas release with environmental relevance
produced water discharge
soil contamination observation
abnormal emission
waste handling nonconformity
environmental complaint
environmental inspection finding
remediation follow-up case
```

Recommended fields:

```text
id
code
eventTypeId
eventStatus
severityId
detectedAt
reportedAt
occurredAt
closedAt
topologyAssetType
topologyAssetId
topologyAssetCodeSnapshot
topologyAssetLabelSnapshot
locationDescription
geoCoordinate
environmentalMediumId
primaryPollutantId
estimatedQuantity
estimatedQuantityUnitId
description
sourceIncidentId
sourceAlarmId
sourceLeakCaseId
reportedByActorId
reportedByDisplayNameSnapshot
organizationUnitId
organizationUnitNameSnapshot
createdAt
updatedAt
```

Lifecycle:

```text
DRAFT
REPORTED
UNDER_ASSESSMENT
CONFIRMED
REMEDIATION_REQUIRED
UNDER_REMEDIATION
MONITORING_REQUIRED
CLOSED
CANCELLED
```

Invariants:

```text
closedAt cannot exist unless status = CLOSED.
confirmed event must have environmental medium.
remediation-required event must have at least one remediation plan.
cancelled event must have cancellation reason.
quantity unit is required when quantity is present.
source incident is optional, not mandatory.
```

---

### 7.2 EnvironmentalObservation

Represents an observation that may or may not become a formal event.

Examples:

```text
visible sheen
odor observation
smoke plume observation
vegetation stress
unusual discharge color
soil staining
noise or vibration observation with environmental relevance
```

Recommended fields:

```text
id
observationCode
observationTypeId
observedAt
observedByActorId
observedByDisplayNameSnapshot
topologyAssetType
topologyAssetId
locationDescription
geoCoordinate
environmentalMediumId
observationDescription
severityId
convertedToEvent
environmentalEventId
createdAt
updatedAt
```

Invariants:

```text
convertedToEvent = true requires environmentalEventId.
observation cannot modify topology or incident state.
```

---

### 7.3 EnvironmentalMonitoringPoint

Represents a point used for environmental monitoring.

Examples:

```text
air quality point near station
surface water sampling point
groundwater well
soil sampling point
noise monitoring point
emission stack measurement point
discharge outlet point
```

Recommended fields:

```text
id
code
name
monitoringPointTypeId
environmentalMediumId
topologyAssetType
topologyAssetId
geoCoordinate
active
effectiveFrom
effectiveTo
createdAt
updatedAt
```

Boundary:

```text
EnvironmentalMonitoringPoint is not a telemetry point.
TelemetryPoint is for operational telemetry.
EnvironmentalMonitoringPoint is for environmental sampling/monitoring context.
```

If a live sensor exists, Environment references the telemetry point instead of owning the sensor reading.

---

### 7.4 EnvironmentalSample

Represents a sample taken from air, water, soil, waste, discharge, or other environmental medium.

Recommended fields:

```text
id
sampleCode
sampleTypeId
monitoringPointId
environmentalEventId
sampledAt
sampledByActorId
sampledByDisplayNameSnapshot
environmentalMediumId
sampleMatrixId
chainOfCustodyCode
laboratoryReference
sampleStatus
createdAt
updatedAt
```

Lifecycle:

```text
PLANNED
COLLECTED
SENT_TO_LAB
RECEIVED_BY_LAB
ANALYZED
VALIDATED
REJECTED
CANCELLED
```

Invariants:

```text
validated sample must have at least one result.
rejected sample requires rejection reason.
chain of custody should be preserved for regulatory samples.
```

---

### 7.5 EnvironmentalSampleResult

Represents an analytical result associated with a sample.

Recommended fields:

```text
id
sampleId
parameterId
pollutantId
resultValue
unitId
detectionLimit
methodReference
resultStatus
validatedAt
validatedByActorId
createdAt
updatedAt
```

Invariants:

```text
resultValue requires unitId.
validated result must preserve method reference if regulatory.
result cannot change after validation; correction creates a new version or correction record.
```

---

### 7.6 EmissionRecord

Represents an air emission record.

Examples:

```text
flaring emission
venting emission
combustion emission
fugitive emission
compressor station emission
```

Recommended fields:

```text
id
emissionCode
emissionTypeId
sourceAssetType
sourceAssetId
sourceAssetCodeSnapshot
periodStart
periodEnd
pollutantId
quantity
unitId
calculationMethodId
measurementSourceType
telemetryReadingReference
validated
validatedAt
createdAt
updatedAt
```

Invariants:

```text
periodEnd >= periodStart.
quantity requires unitId.
measurementSourceType determines whether telemetry reference is required.
```

---

### 7.7 DischargeRecord

Represents an authorized or unauthorized discharge to an environmental medium.

Recommended fields:

```text
id
dischargeCode
dischargeTypeId
environmentalMediumId
sourceAssetType
sourceAssetId
dischargePointId
periodStart
periodEnd
volume
volumeUnitId
qualityStatusId
permitReferenceId
authorized
createdAt
updatedAt
```

Invariants:

```text
authorized discharge should reference a permit where applicable.
unauthorized discharge should create or reference an environmental event.
```

---

### 7.8 SpillRecord

Represents a spill/release record with environmental relevance.

Recommended fields:

```text
id
spillCode
environmentalEventId
materialId
estimatedVolume
volumeUnitId
confirmedVolume
affectedMediumId
affectedArea
areaUnitId
containmentStatus
recoveredVolume
recoveredVolumeUnitId
createdAt
updatedAt
```

Invariants:

```text
spill requires environmentalEventId.
confirmedVolume cannot be negative.
recoveredVolume cannot exceed confirmedVolume unless justified by correction note.
```

---

### 7.9 WasteGenerationRecord

Represents waste generated by operations, maintenance, incidents, remediation, or projects.

Recommended fields:

```text
id
wasteRecordCode
wasteTypeId
hazardous
sourceAssetType
sourceAssetId
sourceActivityTypeId
generatedAt
quantity
unitId
storageLocation
status
createdAt
updatedAt
```

Lifecycle:

```text
GENERATED
STORED
TRANSFERRED
TREATED
DISPOSED
CANCELLED
```

---

### 7.10 WasteTransferRecord

Represents transfer of waste to another internal or external location.

Recommended fields:

```text
id
transferCode
wasteGenerationRecordId
fromLocation
toLocation
carrierPartyId
manifestNumber
transferredAt
quantity
unitId
receiverAcknowledged
createdAt
updatedAt
```

Boundary:

```text
carrierPartyId references Party/master data if a party module exists.
Do not store free-text company names as the official carrier identity.
```

---

### 7.11 WasteDisposalRecord

Represents final treatment or disposal evidence.

Recommended fields:

```text
id
disposalCode
wasteGenerationRecordId
disposalMethodId
disposalFacilityPartyId
disposedAt
quantity
unitId
certificateDocumentId
createdAt
updatedAt
```

Invariants:

```text
DISPOSED waste status requires a disposal record.
certificateDocumentId references Documents; Environment does not store the binary file.
```

---

### 7.12 EnvironmentalImpactAssessment

Represents assessed environmental impact for an event, project, incident, or activity.

Recommended fields:

```text
id
assessmentCode
targetType
targetId
targetCodeSnapshot
assessmentTypeId
assessmentDate
assessorActorId
assessorDisplayNameSnapshot
impactSeverityId
affectedMediumId
affectedArea
affectedAreaUnitId
impactDescription
shortTermImpact
longTermImpact
recommendedActions
createdAt
updatedAt
```

Invariants:

```text
assessment target must be a stable reference.
impact severity must be catalog-backed.
```

---

### 7.13 EnvironmentalRemediationPlan

Represents a plan to remediate an environmental event or impact.

Recommended fields:

```text
id
planCode
environmentalEventId
planStatus
objective
targetCompletionDate
approvedWorkflowReference
createdByActorId
createdAt
updatedAt
```

Lifecycle:

```text
DRAFT
SUBMITTED
APPROVED
IN_PROGRESS
COMPLETED
CLOSED
CANCELLED
```

Boundary:

```text
Workflow owns approval process.
Environment owns the remediation plan content and status.
Asset Management owns maintenance work orders if physical repair work is needed.
```

---

### 7.14 EnvironmentalRemediationAction

Represents an action executed under a remediation plan.

Recommended fields:

```text
id
planId
actionCode
actionTypeId
description
responsibleActorId
responsibleOrganizationUnitId
plannedStartAt
plannedEndAt
actualStartAt
actualEndAt
actionStatus
evidenceDocumentId
createdAt
updatedAt
```

Invariants:

```text
completed action requires actualEndAt.
evidenceDocumentId references Documents, not embedded binary.
```

---

### 7.15 EnvironmentalComplianceObligation

Represents a legal, regulatory, permit, or internal environmental obligation.

Recommended fields:

```text
id
obligationCode
obligationTypeId
jurisdictionId
regulatorPartyId
title
description
effectiveFrom
effectiveTo
active
createdAt
updatedAt
```

Boundary:

```text
Environment tracks environmental obligation content and assessment.
Audit tracks who changed the record.
Documents stores supporting files.
```

---

### 7.16 EnvironmentalComplianceAssessment

Represents an assessment against an environmental obligation.

Recommended fields:

```text
id
assessmentCode
obligationId
targetType
targetId
assessmentDate
assessorActorId
status
findingSummary
nonComplianceEventId
correctiveActionRequired
createdAt
updatedAt
```

Status examples:

```text
COMPLIANT
PARTIALLY_COMPLIANT
NON_COMPLIANT
NOT_APPLICABLE
UNDER_REVIEW
```

---

### 7.17 EnvironmentalPermitReference

Represents a permit, authorization, or regulatory reference relevant to environmental operations.

Recommended fields:

```text
id
permitCode
permitTypeId
issuingAuthorityPartyId
permitNumber
validFrom
validTo
scopeDescription
documentId
active
createdAt
updatedAt
```

Boundary:

```text
Documents owns the permit file metadata and storage reference.
Environment owns the environmental meaning and validity reference.
```

---

### 7.18 EnvironmentalInspection

Represents an environmental inspection or audit.

Recommended fields:

```text
id
inspectionCode
inspectionTypeId
inspectionDate
inspectorActorId
inspectorOrganizationUnitId
externalInspectorPartyId
targetType
targetId
inspectionStatus
summary
createdAt
updatedAt
```

---

### 7.19 EnvironmentalFinding

Represents a finding from an environmental inspection, assessment, event, or observation.

Recommended fields:

```text
id
findingCode
sourceType
sourceId
findingTypeId
severityId
description
requiresCorrectiveAction
status
createdAt
updatedAt
```

---

### 7.20 EnvironmentalCorrectiveAction

Represents a corrective or preventive action for environmental findings.

Recommended fields:

```text
id
actionCode
findingId
actionTypeId
description
responsibleActorId
responsibleOrganizationUnitId
dueAt
completedAt
status
verificationResultId
createdAt
updatedAt
```

Boundary:

```text
Environment owns environmental corrective action tracking.
Workflow owns approval/escalation if needed.
Asset Management owns maintenance execution work orders.
```

---

### 7.21 EnvironmentalClosure

Represents formal closure of an environmental event, finding, remediation plan, or compliance issue.

Recommended fields:

```text
id
closureCode
targetType
targetId
closureReasonId
closureSummary
closedByActorId
closedByDisplayNameSnapshot
closedAt
workflowReference
auditReference
createdAt
```

Invariants:

```text
closure target must be in a closable status.
closure requires actor, timestamp, and reason.
closure should be audit-ready.
```

---

### 7.22 EnvironmentalEvidenceLink

Links environmental records to supporting evidence.

Recommended fields:

```text
id
environmentTargetType
environmentTargetId
evidenceType
documentId
auditEventId
telemetryReadingId
monitoringEvaluationId
incidentId
sampleId
description
createdAt
```

Boundary:

```text
Environment links evidence.
It does not own the evidence source itself.
```

---

### 7.23 EnvironmentCatalogEntry

Controlled vocabulary for environmental types, statuses, media, pollutants, methods, severity values, obligations, and actions.

Recommended fields:

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

Examples of catalog names:

```text
ENVIRONMENTAL_EVENT_TYPE
ENVIRONMENTAL_EVENT_STATUS
ENVIRONMENTAL_MEDIUM
POLLUTANT
SAMPLE_TYPE
SAMPLE_MATRIX
ANALYSIS_PARAMETER
EMISSION_TYPE
DISCHARGE_TYPE
SPILL_MATERIAL
WASTE_TYPE
DISPOSAL_METHOD
REMEDIATION_ACTION_TYPE
COMPLIANCE_OBLIGATION_TYPE
COMPLIANCE_STATUS
ENVIRONMENTAL_FINDING_TYPE
ENVIRONMENTAL_SEVERITY
```

---

### 7.24 EnvironmentCatalogTranslation

Localized labels for Environment catalog entries.

Recommended fields:

```text
id
catalogEntryId
locale
name
description
createdAt
updatedAt
```

Localization rule:

```text
At minimum: ar, fr, en where user-facing.
French is mandatory for operational UI consistency.
```

---

## 8. Value Objects

Recommended value objects:

```text
EnvironmentalEventId
EnvironmentalObservationId
EnvironmentalMonitoringPointId
EnvironmentalSampleId
EnvironmentalSampleResultId
EmissionRecordId
DischargeRecordId
SpillRecordId
WasteRecordId
WasteTransferId
WasteDisposalId
EnvironmentalImpactAssessmentId
EnvironmentalRemediationPlanId
EnvironmentalRemediationActionId
EnvironmentalComplianceObligationId
EnvironmentalComplianceAssessmentId
EnvironmentalPermitReferenceId
EnvironmentalInspectionId
EnvironmentalFindingId
EnvironmentalCorrectiveActionId
EnvironmentalClosureId
EnvironmentCatalogEntryId
EnvironmentalEventCode
EnvironmentalQuantity
EnvironmentalConcentration
EnvironmentalMediumReference
PollutantReference
EnvironmentalSeverityReference
EnvironmentalTargetReference
EnvironmentalActorSnapshot
EnvironmentalOrganizationSnapshot
EnvironmentalGeoLocation
```

---

## 9. Domain Events

Recommended domain events:

```text
EnvironmentalEventReported
EnvironmentalEventConfirmed
EnvironmentalEventSeverityChanged
EnvironmentalEventLinkedToIncident
EnvironmentalSampleCollected
EnvironmentalSampleValidated
EmissionRecordRegistered
DischargeRecordRegistered
SpillRecordRegistered
WasteGenerated
WasteTransferred
WasteDisposed
EnvironmentalImpactAssessed
EnvironmentalRemediationPlanCreated
EnvironmentalRemediationPlanApproved
EnvironmentalRemediationActionCompleted
EnvironmentalComplianceAssessed
EnvironmentalFindingRaised
EnvironmentalCorrectiveActionAssigned
EnvironmentalCorrectiveActionCompleted
EnvironmentalCaseClosed
```

Event rule:

```text
Events must include actor snapshot, target reference, occurredAt, correlationId, and audit-ready metadata.
```

---

## 10. Application Commands

Recommended commands:

```text
ReportEnvironmentalEventCommand
UpdateEnvironmentalEventCommand
ConfirmEnvironmentalEventCommand
CancelEnvironmentalEventCommand
CloseEnvironmentalEventCommand

RecordEnvironmentalObservationCommand
ConvertObservationToEventCommand

CreateEnvironmentalMonitoringPointCommand
DeactivateEnvironmentalMonitoringPointCommand

RegisterEnvironmentalSampleCommand
RecordEnvironmentalSampleResultCommand
ValidateEnvironmentalSampleCommand
RejectEnvironmentalSampleCommand

RegisterEmissionRecordCommand
RegisterDischargeRecordCommand
RegisterSpillRecordCommand

RegisterWasteGenerationCommand
RegisterWasteTransferCommand
RegisterWasteDisposalCommand

CreateEnvironmentalImpactAssessmentCommand
CreateEnvironmentalRemediationPlanCommand
SubmitEnvironmentalRemediationPlanCommand
CompleteEnvironmentalRemediationActionCommand

CreateEnvironmentalComplianceObligationCommand
AssessEnvironmentalComplianceCommand

RegisterEnvironmentalInspectionCommand
RaiseEnvironmentalFindingCommand
AssignEnvironmentalCorrectiveActionCommand
CompleteEnvironmentalCorrectiveActionCommand

AttachEnvironmentalEvidenceCommand
```

---

## 11. Application Queries

Recommended queries:

```text
GetEnvironmentalEventQuery
SearchEnvironmentalEventsQuery
GetEnvironmentalEventTimelineQuery
GetEnvironmentalEventsByTopologyAssetQuery

GetEnvironmentalObservationQuery
SearchEnvironmentalObservationsQuery

GetEnvironmentalMonitoringPointQuery
SearchEnvironmentalMonitoringPointsQuery

GetEnvironmentalSampleQuery
SearchEnvironmentalSamplesQuery
GetEnvironmentalSampleResultsQuery

SearchEmissionRecordsQuery
SearchDischargeRecordsQuery
SearchSpillRecordsQuery
SearchWasteRecordsQuery

GetEnvironmentalImpactAssessmentQuery
SearchEnvironmentalImpactAssessmentsQuery

GetEnvironmentalRemediationPlanQuery
SearchEnvironmentalRemediationPlansQuery

SearchEnvironmentalComplianceObligationsQuery
SearchEnvironmentalComplianceAssessmentsQuery

SearchEnvironmentalFindingsQuery
SearchEnvironmentalCorrectiveActionsQuery

GetEnvironmentCatalogQuery
```

---

## 12. Outbound Ports

Environment should define outbound ports rather than importing foreign domain models.

Recommended outbound ports:

```text
EnvironmentTopologyLookupPort
EnvironmentTelemetryLookupPort
EnvironmentMonitoringLookupPort
EnvironmentIncidentLookupPort
EnvironmentHseLookupPort
EnvironmentWorkflowPort
EnvironmentAuditEventPort
EnvironmentDocumentPort
EnvironmentPartyLookupPort
EnvironmentNotificationPort
```

Usage:

```text
Topology lookup confirms asset references.
Telemetry lookup resolves reading references if measurements are used.
Monitoring lookup resolves deviation/evaluation references.
Incident lookup resolves linked incident context.
Workflow port starts approvals for remediation or closure.
Audit port emits append-only evidence.
Document port links supporting files.
Party lookup resolves external lab, carrier, regulator, disposal facility.
Notification port requests messages after Environment decides notification is needed.
```

---

## 13. REST API Surface

Recommended base path:

```text
/api/v1/environment
```

Recommended endpoint groups:

```text
/api/v1/environment/events
/api/v1/environment/observations
/api/v1/environment/monitoring-points
/api/v1/environment/samples
/api/v1/environment/emissions
/api/v1/environment/discharges
/api/v1/environment/spills
/api/v1/environment/waste
/api/v1/environment/impact-assessments
/api/v1/environment/remediation-plans
/api/v1/environment/compliance-obligations
/api/v1/environment/compliance-assessments
/api/v1/environment/inspections
/api/v1/environment/findings
/api/v1/environment/corrective-actions
/api/v1/environment/catalogs
```

---

## 14. Persistence Tables

Recommended schema/table names:

```text
hidra_environment_event
hidra_environment_observation
hidra_environment_monitoring_point
hidra_environment_sample
hidra_environment_sample_result
hidra_environment_emission_record
hidra_environment_discharge_record
hidra_environment_spill_record
hidra_environment_waste_generation_record
hidra_environment_waste_transfer_record
hidra_environment_waste_disposal_record
hidra_environment_impact_assessment
hidra_environment_remediation_plan
hidra_environment_remediation_action
hidra_environment_compliance_obligation
hidra_environment_compliance_assessment
hidra_environment_permit_reference
hidra_environment_inspection
hidra_environment_finding
hidra_environment_corrective_action
hidra_environment_closure
hidra_environment_evidence_link
hidra_environment_catalog_entry
hidra_environment_catalog_translation
```

---

## 15. Module Invariants

```text
1. Environment records environmental business facts only.
2. Environment references topology assets by stable references and snapshots.
3. Environment references incidents but does not control incident lifecycle.
4. Environment references HSE cases but does not own worker safety or permit-to-work.
5. Environment references telemetry but does not own raw readings.
6. Environment references documents but does not store binary files.
7. Environment emits audit-ready events for all critical actions.
8. Environment corrective actions do not replace Asset Management work orders.
9. Environment compliance assessments do not replace Audit evidence.
10. Environment outputs can feed Risk, Analytics, and Reporting but do not become owned by them.
```

---

## 16. Relationship With HSE

The split must be explicit.

```text
HSE:
  - worker safety
  - health
  - near miss
  - injury/illness
  - permit to work
  - safety controls
  - HSE case lifecycle

Environment:
  - emissions
  - spills
  - discharges
  - waste
  - samples
  - pollutants
  - environmental media
  - remediation
  - environmental compliance
```

Overlap handling:

```text
If an event has both safety and environmental consequences:
  Incident owns the operational event.
  HSE owns safety/health consequences.
  Environment owns environmental consequences.
  Risk may score exposure.
  Audit records evidence.
```

---

## 17. Relationship With Risk

Environment provides facts and evidence to Risk.

```text
Environment provides:
  environmental event severity
  affected medium
  pollutant
  quantity/concentration
  remediation status
  compliance status

Risk owns:
  likelihood
  consequence scoring
  exposure
  risk rating
  treatment plan
  risk acceptance
  residual risk
```

Environment must not calculate enterprise risk score unless it is a local environmental severity classification.

---

## 18. Relationship With Analytics and Reporting

```text
Environment owns operational environmental records.
Analytics derives trends and KPIs from environment history.
Reporting packages formal environmental reports and exports.
```

Examples:

```text
Environment:
  SpillRecord, WasteDisposalRecord, EnvironmentalSampleResult

Analytics:
  spill frequency trend, emissions trend, remediation delay KPI

Reporting:
  monthly environmental report PDF, regulatory export, waste manifest summary
```

---

## 19. Safety and Control Rule

Environment is decision-support and evidence management.

It must not directly actuate:

```text
valves
pumps
compressors
PLCs
RTUs
SCADA
ESD
SIS
```

If an environmental event requires operational action, that action is handled through Incident Management, Asset Management, Workflow, and human operational procedures.

---

## 20. Implementation Package

Recommended package structure:

```text
dz.sh.hidra.modules.environment
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
    messaging
    projection
```

Forbidden package names:

```text
shared
common
core
utils
helper
helpers
misc
```

---

## 21. First Implementation Slice

Recommended first slice:

```text
ENV-001 package skeleton
ENV-002 value objects
ENV-003 catalog tables
ENV-004 environmental event aggregate
ENV-005 environmental event persistence
ENV-006 event report/search REST APIs
ENV-007 evidence link
ENV-008 environmental observation
ENV-009 spill record
ENV-010 audit event port
ENV-011 workflow approval reference for closure
ENV-012 tests and architecture rules
```

First useful feature:

```text
Report environmental event
  -> classify medium and pollutant
  -> link topology asset
  -> link optional incident
  -> attach evidence reference
  -> track status
  -> emit audit-ready event
```

---

## 22. Final Ownership Statement

```text
Environment owns environmental condition, release, impact, sample, waste, emission, discharge, remediation, and environmental compliance records.

It references operational facts from other modules,
but it does not own those modules' state.
```

Short version:

```text
Incidents handle operational disruption.
HSE handles safety and health.
Environment handles environmental consequence and remediation.
```
