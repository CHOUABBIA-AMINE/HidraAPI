# HIDRA OT SECURITY DATA DEFINITION DOCUMENT

```text
Document     : HIDRA-OT-SECURITY-DATA-DEFINITION-DOCUMENT.md
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Module       : otsecurity
Package      : dz.sh.hidra.modules.otsecurity
Owner        : Sonatrach / TRC Digitalization Initiative
Author       : Abir MEDJERAB
Status       : Target DDD / not yet implemented as Java module
Scope        : OT cybersecurity posture, OT asset cyber inventory, vulnerabilities, access evidence, zones, conduits, events, and OT security governance
```

---

## 1. Purpose

The **OT Security** module manages the cybersecurity view of operational technology used around hydrocarbon pipeline operations.

It answers:

```text
Which OT assets exist?
Which industrial zones and conduits connect them?
Which vulnerabilities affect them?
Which remote/local access sessions occurred?
Which security events were observed?
Which security controls apply?
Which OT risks require mitigation?
Which evidence supports OT security posture and compliance?
```

OT Security is not a SCADA control system, not a firewall manager, not an IAM replacement, and not an incident-management replacement.

Its mission is:

```text
observe -> classify -> assess -> evidence -> recommend -> escalate
```

not:

```text
connect -> command -> actuate -> control
```

---

## 2. Ownership

OT Security owns:

```text
OT cyber asset inventory
OT zone and conduit model
OT network segment references
OT communication-flow inventory
OT vulnerability records
OT vulnerability assessments
OT patch/advisory evidence
OT access-session evidence
OT remote-access approvals references
OT firewall/configuration snapshots
OT security events
OT anomaly records
OT security posture snapshots
OT control assessments
OT compliance assessments
OT security recommendations
OT security evidence links
OT security catalog values
```

OT Security does **not** own:

```text
SCADA control commands
PLC / RTU / SIS / ESD actuation
physical pipeline topology
pipeline facilities and stations
physical equipment master data
telemetry readings
monitoring thresholds
alarm lifecycle
operational incident lifecycle
identity users, credentials, roles, permissions
organization hierarchy
integration connectors
notification delivery
risk scoring engine
asset maintenance execution
audit ledger
document binary storage
reports and exports
```

---

## 3. Non-negotiable safety rule

```text
OT Security is decision-support and governance only.

It must not directly actuate valves, pumps, compressors, PLCs, RTUs, SIS, ESD, or SCADA control commands.
```

Allowed:

```text
record OT asset security posture
record vulnerability evidence
record observed security event
record remote access session metadata
recommend mitigation
request workflow approval
raise security case / incident reference
publish audit-ready event
notify responsible actors through Notification
```

Forbidden:

```text
open valve
close valve
start pump
stop pump
change PLC logic
write RTU setting
change SIS / ESD state
push firewall rule directly
disable security control directly
modify SCADA configuration directly
perform unapproved active scan against live OT assets
```

---

## 4. Position in Hidra architecture

OT Security is a supporting operational-security bounded context.

It sits beside:

```text
Topology
Telemetry
Monitoring
Alarm Management
Incident Management
Asset Management
Risk Management
Integration
Audit
Workflow
Notification
Documents
Reporting
```

It protects and governs the cyber side of operational infrastructure, but it does not replace the modules that own physical assets, readings, alarms, incidents, risk scores, or external connectors.

---

## 5. Core boundary rule

```text
Topology owns the physical network.
Asset Management owns maintainable equipment lifecycle.
Integration owns connectors to external systems.
Identity owns authentication and authorization.
Risk owns formal risk scoring and treatment.
Incidents own operational incident response.
OT Security owns OT cybersecurity posture and evidence.
```

The shortest rule:

```text
Topology says what exists physically.
OT Security says how it is cyber-exposed and protected.
```

---

## 6. Relationship with Topology

Topology owns:

```text
Facility
Station
Terminal
PipelineSystem
Pipeline
PipelineSegment
TopologyNode
TopologyConnection
Equipment
MeasurementPoint / measurement location
TopologySnapshot
TopologyVersion
```

OT Security references topology assets only through stable references:

```text
TopologyAssetReference
TopologySnapshotReference
FacilityReference
EquipmentReference
MeasurementPointReference
```

OT Security must not import topology domain classes and must not write topology tables.

Example:

```text
Topology:
  Equipment EQ-RTU-001 attached to Station ST-02

OT Security:
  OtCyberAsset OT-RTU-001
    assetKind = RTU
    topologyAssetReference = EQ-RTU-001
    zoneReference = ZONE-STATION-ST-02-CONTROL
    criticality = HIGH
    exposureStatus = RESTRICTED
```

---

## 7. Relationship with Integration

Integration owns:

```text
connector registry
SCADA adapter
historian adapter
OPC UA readiness
MQTT readiness
ingestion jobs
synchronization status
retry and dead-letter handling
external reference tracking
```

OT Security owns:

```text
security posture of external connection
security classification of connector target
security event/evidence linked to connector
remote access governance evidence
security recommendation for connector hardening
```

Integration may execute technical exchange through adapters.

OT Security may request or consume safe metadata through ports, but it must not bypass Integration to talk directly to SCADA, historian, PLC, RTU, firewall, or remote-access systems.

---

## 8. Relationship with Identity

Identity owns:

```text
users
roles
permissions
authorities
groups
authentication
authorization policies
permission decisions
external IAM mapping
```

OT Security owns:

```text
OT access session record
OT privileged access evidence
OT access risk assessment
OT security exception linked to actor snapshot
OT remote-access approval reference
```

OT Security references actors by snapshot:

```text
actorId
usernameSnapshot
displayNameSnapshot
roleCodeSnapshot
organizationUnitSnapshot
```

It must not store passwords, secrets, token values, private keys, or credential material.

---

## 9. Relationship with Incidents

Incident Management owns:

```text
incident lifecycle
classification
severity
response actions
root cause
resolution
closure
incident timeline
```

OT Security owns:

```text
security event
security anomaly
vulnerability evidence
security posture impact
security recommendation
OT security case pre-incident evidence
```

If an OT security event becomes an operational incident, Incident Management opens and controls the incident lifecycle.

```text
OTSecurityEvent -> IncidentReference
```

OT Security must not own the incident lifecycle.

---

## 10. Relationship with Risk

Risk Management owns:

```text
risk register
risk scenario
likelihood
consequence
exposure
risk score
risk treatment
risk acceptance
residual risk
```

OT Security owns:

```text
OT security vulnerability
OT security posture
OT control weakness
OT security recommendation
OT evidence supporting risk assessment
```

Risk may consume OT Security evidence to calculate risk.

OT Security must not own enterprise risk scoring.

---

## 11. Relationship with Audit

Audit owns:

```text
append-only evidence of who did what
actor/action/target context
before/after values
correlation/request IDs
decision evidence
searchable audit projections
```

OT Security owns the business security record.

Audit owns the immutable audit event around it.

Example:

```text
OT Security:
  OtVulnerabilityAssessment created

Audit:
  AuditEvent(actor, target, action, reason, timestamp, correlationId)
```

---

## 12. Aggregate design

Recommended aggregates:

```text
OtCyberAsset
OtSecurityZone
OtVulnerabilityAssessment
OtSecurityEvent
OtAccessSession
OtSecurityCase
OtSecurityPostureSnapshot
OtControlAssessment
OtCatalogEntry
```

Do not model every row as an aggregate root. Keep transactional consistency around the real business lifecycle.

---

## 13. Entity inventory

### 13.1 OtCyberAsset

Cyber representation of an OT asset.

Examples:

```text
SCADA server
HMI workstation
engineering workstation
historian node
OPC gateway
MQTT gateway
telemetry gateway
RTU
PLC
IED
network switch
firewall
router
remote access gateway
jump server
serial gateway
```

Fields:

```text
id
code
name
assetKindId
topologyAssetReferenceType
topologyAssetReferenceId
externalSystemReferenceId
zoneId
networkSegmentId
criticalityId
ownerOrganizationUnitId
custodianActorId
manufacturerPartyReferenceId
model
firmwareVersion
softwareVersion
serialNumberHash
ipAddressMasked
macAddressMasked
hostname
locationLabelSnapshot
status
commissionedAt
retiredAt
createdAt
updatedAt
```

Rules:

```text
code must be unique
assetKindId must reference OT security catalog
zoneId must reference active OT security zone
sensitive identifiers can be masked or hashed
secrets must never be stored
```

---

### 13.2 OtAssetAlias

Alternative names or tags for an OT cyber asset.

Fields:

```text
id
otCyberAssetId
aliasTypeId
aliasValue
sourceSystemReferenceId
active
createdAt
updatedAt
```

Examples:

```text
SCADA tag hostname
historian node name
engineering workstation alias
network discovery label
```

---

### 13.3 OtSecurityZone

Cybersecurity zone grouping assets with similar trust, exposure, and operational criticality.

Fields:

```text
id
code
nameAr
nameFr
nameEn
zoneTypeId
criticalityId
parentZoneId
siteTopologyReferenceId
description
status
createdAt
updatedAt
```

Examples:

```text
CONTROL_CENTER_ZONE
STATION_CONTROL_ZONE
SCADA_SERVER_ZONE
DMZ_ZONE
HISTORIAN_ZONE
ENGINEERING_ZONE
VENDOR_REMOTE_ACCESS_ZONE
```

---

### 13.4 OtConduit

Controlled communication relationship between two OT security zones.

Fields:

```text
id
code
name
sourceZoneId
targetZoneId
conduitTypeId
allowedDirectionId
securityLevelId
status
createdAt
updatedAt
```

Rules:

```text
sourceZoneId and targetZoneId must differ
inactive zones cannot be used in active conduit
conduit does not configure firewall directly
```

---

### 13.5 OtNetworkSegment

Logical network segment reference used for OT security classification.

Fields:

```text
id
code
name
zoneId
segmentTypeId
addressRangeMasked
vlanReferenceMasked
siteTopologyReferenceId
status
createdAt
updatedAt
```

No raw secrets or sensitive full network maps should be exposed through public APIs.

---

### 13.6 OtCommunicationFlow

Expected or observed communication between OT cyber assets or zones.

Fields:

```text
id
sourceAssetId
sourceZoneId
targetAssetId
targetZoneId
protocolId
portMasked
flowPurposeId
directionId
expected
observedFirstAt
observedLastAt
status
createdAt
updatedAt
```

Rules:

```text
unexpected flows may create OtSecurityEvent
communication flow does not grant access
communication flow does not change firewall rules
```

---

### 13.7 OtVulnerability

Known vulnerability or weakness affecting an OT cyber asset, product, firmware, software, or configuration.

Fields:

```text
id
vulnerabilityReference
sourceId
title
summary
assetKindId
affectedVendorReferenceId
affectedProduct
severityId
exploitabilityId
publishedAt
lastObservedAt
status
createdAt
updatedAt
```

Notes:

```text
vulnerabilityReference may hold CVE/vendor advisory/internal reference
summary must avoid sensitive exploit details in general user-facing views
```

---

### 13.8 OtVulnerabilityAssessment

Assessment of vulnerability impact on one or more OT assets.

Fields:

```text
id
assessmentNumber
scopeId
assessedByActorId
assessedByDisplayNameSnapshot
assessedAt
methodId
status
summary
createdAt
updatedAt
```

Children:

```text
OtVulnerabilityAssessmentItem
OtVulnerabilityAssessmentEvidence
```

Lifecycle:

```text
DRAFT -> IN_REVIEW -> APPROVED -> SUPERSEDED
DRAFT -> CANCELLED
```

---

### 13.9 OtVulnerabilityAssessmentItem

Asset-level vulnerability assessment result.

Fields:

```text
id
assessmentId
otCyberAssetId
vulnerabilityId
applicabilityStatusId
currentExposureId
compensatingControlSummary
recommendedActionId
priorityId
dueAt
riskReferenceId
status
createdAt
updatedAt
```

---

### 13.10 OtPatchAdvisory

Patch, firmware, workaround, hardening advisory, or vendor recommendation.

Fields:

```text
id
advisoryReference
sourceId
title
summary
vendorPartyReferenceId
affectedProduct
relatedVulnerabilityId
recommendedActionId
releasedAt
status
createdAt
updatedAt
```

OT Security records advisory evidence. Asset Management or OT operations executes maintenance work under its own process.

---

### 13.11 OtPatchPlanReference

Reference linking OT security recommendation to an implementation plan, without owning maintenance execution.

Fields:

```text
id
otCyberAssetId
patchAdvisoryId
maintenanceWorkOrderReferenceId
workflowReferenceId
plannedWindowStart
plannedWindowEnd
status
createdAt
updatedAt
```

---

### 13.12 OtSecurityEvent

Observed OT security event.

Examples:

```text
unexpected communication flow
unauthorized remote access attempt
new device observed
configuration drift detected
privileged access outside window
malware alert imported from external tool
failed login burst
firewall policy mismatch
unapproved engineering workstation connection
```

Fields:

```text
id
eventNumber
eventTypeId
sourceId
sourceSystemReferenceId
otCyberAssetId
zoneId
severityId
confidenceId
observedAt
receivedAt
summary
status
correlationId
createdAt
updatedAt
```

Rules:

```text
event is evidence, not incident lifecycle
event can reference Incident if escalated
event can reference Risk if used for risk assessment
```

Lifecycle:

```text
NEW -> TRIAGED -> LINKED_TO_CASE -> DISMISSED
NEW -> ESCALATED_TO_INCIDENT
```

---

### 13.13 OtSecurityAnomaly

Pattern or anomaly derived from one or more events.

Fields:

```text
id
anomalyNumber
anomalyTypeId
relatedAssetId
relatedZoneId
firstObservedAt
lastObservedAt
severityId
confidenceId
summary
status
createdAt
updatedAt
```

---

### 13.14 OtAccessSession

Evidence of access to OT systems, especially privileged, vendor, remote, or engineering access.

Fields:

```text
id
sessionNumber
accessTypeId
actorId
actorDisplayNameSnapshot
organizationUnitId
organizationUnitNameSnapshot
vendorPartyReferenceId
remoteAccessGatewayAssetId
targetAssetId
targetZoneId
approvedByWorkflowReferenceId
justification
startedAt
endedAt
status
createdAt
updatedAt
```

Rules:

```text
session record does not authenticate user
session record does not grant access
actual credentials remain in Identity/IAM or external PAM system
```

---

### 13.15 OtAccessApprovalReference

Reference to the approval decision for an OT access request.

Fields:

```text
id
otAccessSessionId
workflowReferenceId
approvalStatusId
approvedByActorId
approvedByDisplayNameSnapshot
approvedAt
validFrom
validTo
createdAt
updatedAt
```

---

### 13.16 OtConfigurationSnapshot

Snapshot of relevant security configuration metadata for OT cyber assets.

Examples:

```text
firewall rule snapshot
PLC firmware inventory snapshot
HMI software version snapshot
remote access gateway configuration snapshot
backup state snapshot
security tool policy snapshot
```

Fields:

```text
id
snapshotNumber
snapshotTypeId
otCyberAssetId
zoneId
capturedBySourceId
capturedAt
hash
summary
status
createdAt
updatedAt
```

Rules:

```text
snapshot stores metadata and evidence references
snapshot must not expose secrets
snapshot must not directly change device configuration
```

---

### 13.17 OtConfigurationDrift

Detected change between OT configuration snapshots.

Fields:

```text
id
otCyberAssetId
baselineSnapshotId
currentSnapshotId
driftTypeId
severityId
summary
approvedChangeReferenceId
status
detectedAt
createdAt
updatedAt
```

---

### 13.18 OtBackupEvidence

Evidence that critical OT configuration backups exist and were tested.

Fields:

```text
id
otCyberAssetId
backupTypeId
backupReference
backupLocationReferenceMasked
lastBackupAt
lastRestoreTestAt
status
createdAt
updatedAt
```

No backup binary or secret material should be stored in OT Security tables.

---

### 13.19 OtSecurityControl

Security control definition or requirement applied to OT assets/zones.

Fields:

```text
id
code
nameAr
nameFr
nameEn
controlTypeId
description
mandatory
active
createdAt
updatedAt
```

Examples:

```text
network segmentation
remote access approval
engineering workstation hardening
backup verification
malware protection
patch review
privileged access recording
configuration baseline
```

---

### 13.20 OtControlAssessment

Assessment of one or more OT security controls.

Fields:

```text
id
assessmentNumber
scopeId
assessedByActorId
assessedByDisplayNameSnapshot
assessedAt
methodId
overallStatusId
summary
createdAt
updatedAt
```

Children:

```text
OtControlAssessmentItem
OtControlFinding
```

---

### 13.21 OtControlAssessmentItem

Control result for a specific asset, zone, conduit, or site.

Fields:

```text
id
assessmentId
controlId
targetType
targetId
statusId
maturityLevelId
evidenceSummary
findingId
createdAt
updatedAt
```

---

### 13.22 OtControlFinding

Weakness, gap, or non-conformity found during control assessment.

Fields:

```text
id
findingNumber
assessmentId
controlId
targetType
targetId
severityId
summary
recommendation
ownerOrganizationUnitId
dueAt
status
createdAt
updatedAt
```

---

### 13.23 OtSecurityRecommendation

Recommended action to improve OT security posture.

Fields:

```text
id
recommendationNumber
sourceType
sourceId
targetType
targetId
recommendationTypeId
priorityId
summary
rationale
ownerOrganizationUnitId
workflowReferenceId
riskReferenceId
maintenanceWorkOrderReferenceId
status
createdAt
updatedAt
```

Lifecycle:

```text
PROPOSED -> ACCEPTED -> IN_PROGRESS -> IMPLEMENTED -> VERIFIED -> CLOSED
PROPOSED -> REJECTED
ACCEPTED -> RISK_ACCEPTED
```

---

### 13.24 OtSecurityCase

OT security case grouping related events, vulnerabilities, findings, access sessions, and recommendations.

Fields:

```text
id
caseNumber
caseTypeId
severityId
status
openedAt
openedByActorId
openedByDisplayNameSnapshot
closedAt
summary
relatedIncidentReferenceId
relatedRiskReferenceId
createdAt
updatedAt
```

Lifecycle:

```text
OPEN -> TRIAGED -> UNDER_REVIEW -> MITIGATION_PLANNED -> MITIGATED -> VERIFIED -> CLOSED
OPEN -> ESCALATED_TO_INCIDENT
OPEN -> CANCELLED
```

OTSecurityCase is not Incident. It can escalate to Incident Management when operational response is required.

---

### 13.25 OtSecurityCaseTimelineEntry

Append-only timeline inside an OT security case.

Fields:

```text
id
caseId
entryTypeId
actorId
actorDisplayNameSnapshot
organizationUnitId
organizationUnitNameSnapshot
entryText
occurredAt
createdAt
```

---

### 13.26 OtSecurityPostureSnapshot

Time-bound posture snapshot for an asset, zone, site, or whole OT scope.

Fields:

```text
id
snapshotNumber
scopeType
scopeId
snapshotAt
postureLevelId
criticalVulnerabilityCount
highVulnerabilityCount
openFindingCount
openSecurityEventCount
unapprovedAccessSessionCount
controlCompliancePercentage
summary
createdAt
updatedAt
```

This is a derived/summary entity within OT Security. It must be recalculable from evidence records.

---

### 13.27 OtComplianceAssessment

OT security compliance assessment against internal or external requirements.

Fields:

```text
id
assessmentNumber
frameworkReferenceId
scopeType
scopeId
assessedByActorId
assessedByDisplayNameSnapshot
assessedAt
overallStatusId
summary
createdAt
updatedAt
```

Children:

```text
OtComplianceRequirementResult
```

---

### 13.28 OtComplianceRequirementResult

Result for one requirement/control objective.

Fields:

```text
id
assessmentId
requirementReference
controlId
statusId
evidenceSummary
findingId
createdAt
updatedAt
```

---

### 13.29 OtEvidenceLink

Link from OT Security records to evidence kept in Documents, Audit, Integration, or external systems.

Fields:

```text
id
sourceType
sourceId
evidenceTypeId
evidenceReferenceModule
evidenceReferenceId
evidenceLabel
createdAt
createdByActorId
```

Rules:

```text
Documents owns file metadata
Audit owns immutable audit evidence
OT Security owns the business meaning of OT security evidence link
```

---

### 13.30 OtCatalogEntry

Controlled vocabulary for OT Security.

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

Catalog examples:

```text
OT_ASSET_KIND
OT_ZONE_TYPE
OT_CONDUIT_TYPE
OT_PROTOCOL
OT_CRITICALITY
OT_SEVERITY
OT_CONFIDENCE
OT_EVENT_TYPE
OT_ANOMALY_TYPE
OT_ACCESS_TYPE
OT_CONTROL_TYPE
OT_FINDING_STATUS
OT_RECOMMENDATION_TYPE
OT_POSTURE_LEVEL
OT_COMPLIANCE_STATUS
OT_EXPOSURE_STATUS
```

Business taxonomies must be catalog-backed, not hard-coded enums.

---

### 13.31 OtCatalogTranslation

Multilingual catalog labels.

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

Required locales should follow the Hidra multilingual policy used elsewhere:

```text
ar
fr
en optional when needed
```

---

## 14. Recommended database tables

```text
hidra_otsec_catalog_entry
hidra_otsec_catalog_translation
hidra_otsec_cyber_asset
hidra_otsec_asset_alias
hidra_otsec_zone
hidra_otsec_conduit
hidra_otsec_network_segment
hidra_otsec_communication_flow
hidra_otsec_vulnerability
hidra_otsec_vulnerability_assessment
hidra_otsec_vulnerability_assessment_item
hidra_otsec_patch_advisory
hidra_otsec_patch_plan_reference
hidra_otsec_security_event
hidra_otsec_security_anomaly
hidra_otsec_access_session
hidra_otsec_access_approval_reference
hidra_otsec_configuration_snapshot
hidra_otsec_configuration_drift
hidra_otsec_backup_evidence
hidra_otsec_security_control
hidra_otsec_control_assessment
hidra_otsec_control_assessment_item
hidra_otsec_control_finding
hidra_otsec_security_recommendation
hidra_otsec_security_case
hidra_otsec_security_case_timeline_entry
hidra_otsec_security_posture_snapshot
hidra_otsec_compliance_assessment
hidra_otsec_compliance_requirement_result
hidra_otsec_evidence_link
```

Naming note:

```text
Use otsecurity for Java package.
Use hidra_otsec_* for physical table prefix if shorter table names are preferred.
```

---

## 15. Recommended package structure

```text
src/main/java/dz/sh/hidra/modules/otsecurity
  package-info.java

src/main/java/dz/sh/hidra/modules/otsecurity/api
src/main/java/dz/sh/hidra/modules/otsecurity/api/rest
src/main/java/dz/sh/hidra/modules/otsecurity/api/rest/controller
src/main/java/dz/sh/hidra/modules/otsecurity/api/rest/request
src/main/java/dz/sh/hidra/modules/otsecurity/api/rest/response
src/main/java/dz/sh/hidra/modules/otsecurity/api/rest/mapper

src/main/java/dz/sh/hidra/modules/otsecurity/application
src/main/java/dz/sh/hidra/modules/otsecurity/application/command
src/main/java/dz/sh/hidra/modules/otsecurity/application/query
src/main/java/dz/sh/hidra/modules/otsecurity/application/dto
src/main/java/dz/sh/hidra/modules/otsecurity/application/service
src/main/java/dz/sh/hidra/modules/otsecurity/application/port/in
src/main/java/dz/sh/hidra/modules/otsecurity/application/port/out

src/main/java/dz/sh/hidra/modules/otsecurity/domain
src/main/java/dz/sh/hidra/modules/otsecurity/domain/model
src/main/java/dz/sh/hidra/modules/otsecurity/domain/value
src/main/java/dz/sh/hidra/modules/otsecurity/domain/event
src/main/java/dz/sh/hidra/modules/otsecurity/domain/policy
src/main/java/dz/sh/hidra/modules/otsecurity/domain/service
src/main/java/dz/sh/hidra/modules/otsecurity/domain/exception

src/main/java/dz/sh/hidra/modules/otsecurity/infrastructure
src/main/java/dz/sh/hidra/modules/otsecurity/infrastructure/configuration
src/main/java/dz/sh/hidra/modules/otsecurity/infrastructure/persistence/entity
src/main/java/dz/sh/hidra/modules/otsecurity/infrastructure/persistence/repository
src/main/java/dz/sh/hidra/modules/otsecurity/infrastructure/persistence/mapper
src/main/java/dz/sh/hidra/modules/otsecurity/infrastructure/persistence/adapter
src/main/java/dz/sh/hidra/modules/otsecurity/infrastructure/integration
src/main/java/dz/sh/hidra/modules/otsecurity/infrastructure/projection
```

Forbidden packages:

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

## 16. Value objects

Recommended value objects:

```text
OtCyberAssetId
OtSecurityZoneId
OtConduitId
OtNetworkSegmentId
OtVulnerabilityId
OtVulnerabilityAssessmentId
OtSecurityEventId
OtAccessSessionId
OtSecurityCaseId
OtSecurityPostureSnapshotId
OtControlAssessmentId
OtCatalogEntryId
OtCode
OtName
MaskedNetworkAddress
MaskedHardwareAddress
TopologyAssetReference
ExternalSystemReference
ActorSnapshot
OrganizationUnitSnapshot
WorkflowReference
AuditReference
IncidentReference
RiskReference
DocumentReference
```

---

## 17. Technical enums vs catalog-backed values

Allowed technical enums:

```text
OtSecurityCaseStatus
OtSecurityEventStatus
OtRecommendationStatus
OtAssessmentStatus
OtConfigurationDriftStatus
```

Catalog-backed business values:

```text
asset kind
zone type
conduit type
protocol
criticality
severity
confidence
access type
event type
finding type
control type
recommendation type
posture level
exposure status
compliance status
```

Rule:

```text
Business taxonomy must be catalog-backed.
Lifecycle state may be technical enum.
```

---

## 18. Inbound ports

```text
RegisterOtCyberAssetUseCase
UpdateOtCyberAssetUseCase
ClassifyOtCyberAssetUseCase
CreateOtSecurityZoneUseCase
CreateOtConduitUseCase
RecordOtCommunicationFlowUseCase
RecordOtVulnerabilityUseCase
AssessOtVulnerabilityUseCase
RecordOtSecurityEventUseCase
TriageOtSecurityEventUseCase
OpenOtSecurityCaseUseCase
EscalateOtSecurityCaseToIncidentUseCase
RecordOtAccessSessionUseCase
RecordOtConfigurationSnapshotUseCase
RecordOtConfigurationDriftUseCase
AssessOtSecurityControlUseCase
CreateOtSecurityRecommendationUseCase
CloseOtSecurityRecommendationUseCase
CreateOtSecurityPostureSnapshotUseCase
AssessOtComplianceUseCase
LinkOtEvidenceUseCase
```

---

## 19. Outbound ports

```text
TopologyAssetLookupPort
ExternalSystemLookupPort
IdentityActorSnapshotPort
OrganizationSnapshotPort
WorkflowRequestPort
AuditEventPort
IncidentEscalationPort
RiskEvidencePort
DocumentReferencePort
NotificationRequestPort
IntegrationMetadataPort
```

Forbidden:

```text
Direct SCADA client in domain
Direct PLC/RTU client in domain
Direct firewall API client in domain
Direct JPA repository access from API
Cross-module domain import
```

---

## 20. Domain events

```text
OtCyberAssetRegistered
OtCyberAssetClassified
OtSecurityZoneCreated
OtConduitCreated
OtCommunicationFlowObserved
OtVulnerabilityRecorded
OtVulnerabilityAssessmentApproved
OtSecurityEventRecorded
OtSecurityEventTriaged
OtSecurityEventEscalatedToIncident
OtAccessSessionRecorded
OtConfigurationSnapshotRecorded
OtConfigurationDriftDetected
OtControlFindingOpened
OtSecurityRecommendationCreated
OtSecurityRecommendationImplemented
OtSecurityRecommendationVerified
OtSecurityCaseOpened
OtSecurityCaseEscalated
OtSecurityCaseClosed
OtSecurityPostureSnapshotCreated
OtComplianceAssessmentCompleted
```

All critical events must be audit-ready.

---

## 21. API surface

Base path:

```text
/api/v1/ot-security
```

Recommended endpoint groups:

```text
/api/v1/ot-security/catalogs
/api/v1/ot-security/assets
/api/v1/ot-security/zones
/api/v1/ot-security/conduits
/api/v1/ot-security/network-segments
/api/v1/ot-security/communication-flows
/api/v1/ot-security/vulnerabilities
/api/v1/ot-security/vulnerability-assessments
/api/v1/ot-security/patch-advisories
/api/v1/ot-security/events
/api/v1/ot-security/anomalies
/api/v1/ot-security/access-sessions
/api/v1/ot-security/configuration-snapshots
/api/v1/ot-security/configuration-drifts
/api/v1/ot-security/controls
/api/v1/ot-security/control-assessments
/api/v1/ot-security/recommendations
/api/v1/ot-security/cases
/api/v1/ot-security/posture-snapshots
/api/v1/ot-security/compliance-assessments
```

---

## 22. Security and privacy rules

OT Security data is sensitive.

Rules:

```text
mask IP addresses where full value is not needed
mask MAC addresses where full value is not needed
never store passwords
never store private keys
never store token values
never expose raw firewall rules to unauthorized users
never expose exploit detail in generic views
apply organization and site scope filtering
preserve actor snapshots for security decisions
send all critical actions to Audit
```

---

## 23. Active vs passive discovery rule

Default position:

```text
Passive discovery and imported metadata are preferred.
```

Active scans against live OT assets require:

```text
approved scope
approved time window
workflow approval
asset owner acknowledgement
rollback/abort plan
post-scan evidence
```

OT Security records the plan and evidence. It must not perform unapproved active scans by default.

---

## 24. Example lifecycle: vulnerability to mitigation

```text
1. Vulnerability advisory imported through Integration.
2. OtVulnerability is recorded.
3. Affected OtCyberAsset records are matched.
4. OtVulnerabilityAssessment is opened.
5. Assessment item determines applicability and exposure.
6. OtSecurityRecommendation is created.
7. Workflow approval is requested if action affects operations.
8. Asset Management / OT operations executes maintenance or hardening work.
9. OT Security records verification evidence.
10. Risk Management updates residual risk if applicable.
11. Audit records critical decisions.
```

---

## 25. Example lifecycle: unexpected OT access

```text
1. External tool or log import indicates remote access session.
2. OtAccessSession is recorded.
3. OT Security checks approved workflow reference.
4. If approved, session remains evidence.
5. If unapproved, OtSecurityEvent is opened.
6. Event is triaged.
7. If operational impact exists, Incident Management opens incident.
8. If exposure exists, Risk Management receives risk evidence.
9. Notification informs responsible actors.
10. Audit records decisions and actions.
```

---

## 26. What not to implement in the first version

Do not start with:

```text
full SIEM replacement
full SOC platform
active vulnerability scanner
firewall manager
PLC logic management
SCADA command gateway
PAM vault
secret storage
packet capture repository
raw network forensics lake
automatic blocking/remediation engine
```

Start with:

```text
OT cyber asset inventory
zones and conduits
access-session evidence
vulnerability evidence
security events
control assessments
security recommendations
case management
posture snapshots
workflow/audit integration
```

---

## 27. Acceptance criteria

OT Security is accepted when:

```text
OT cyber assets can be registered and linked to topology references
zones and conduits can be modeled
communication flows can be recorded
vulnerabilities can be assessed against OT assets
security events can be triaged
remote access sessions can be evidenced
control assessments can produce findings
recommendations can be tracked to verification
security cases can be escalated to incidents
risk evidence can be exported to Risk Management
critical actions are audit-ready
no direct SCADA/PLC/RTU/SIS/ESD actuation exists
sensitive identifiers and secrets are protected
```

---

## 28. Final ownership statement

```text
OT Security manages cyber posture and evidence for operational technology.
It never owns physical topology, operational control, identity credentials, enterprise risk scoring, or incident lifecycle.
```

The most important rule:

```text
OT Security observes and governs.
It does not control the pipeline.
```
