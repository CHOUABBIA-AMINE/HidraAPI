# HIDRA — Consolidated Data Definition Document

```text
Document code : HIDRA-CONSOLIDATED-DDD
Repository    : HidraAPI
Folder        : docs/data definition
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
CreatedOn     : 2026-06-11
Status        : Consolidated DDD master reference
Scope         : Consolidates all Data Definition Documents currently present under docs/data definition
```

---

## 1. Purpose

This document consolidates the Data Definition Documents currently located under:

```text
docs/data definition
```

It is intended to be the single navigable master reference for:

```text
module ownership
bounded-context boundaries
canonical package roots
core aggregate models
entity catalogues
data ownership rules
cross-module reference rules
forbidden dependencies
implementation sequencing
```

The original module DDD files remain useful as detailed field-level references. This consolidated document is the architectural contract that aligns them into one consistent Hidra data model.

---

## 2. Source DDD inventory

The following DDD files were identified under `docs/data definition` and consolidated here:

| # | Source file | Module / context | Canonical package root | Status in source |
|---:|---|---|---|---|
| 1 | `Kernel.md` | kernel | `dz.sh.hidra.kernel` | Architecture baseline candidate |
| 2 | `Platform.md` | platform | `dz.sh.hidra.platform` / `dz.sh.hidra` platform area | Repository-aligned architecture baseline |
| 3 | `Topology.md` | topology | `dz.sh.hidra.modules.topology` | Architecture baseline draft |
| 4 | `Telemetry.md` | telemetry | `dz.sh.hidra.modules.telemetry` | Architecture baseline candidate |
| 5 | `Planning.md` | planning | `dz.sh.hidra.modules.planning` | Target architecture baseline candidate |
| 6 | `Monitoring.md` | monitoring | `dz.sh.hidra.modules.monitoring` | Target DDD, not yet repository-implemented |
| 7 | `Simulation.md` | simulation | `dz.sh.hidra.modules.simulation` | Target DDD, architecture-aligned |
| 8 | `Assets.md` | assets / asset-management | `dz.sh.hidra.modules.assets` | Target DDD, repository-aligned boundary definition |
| 9 | `Integrity.md` | integrity | `dz.sh.hidra.modules.integrity` | Target data definition document |
| 10 | `LeakDetection.md` | leakdetection | `dz.sh.hidra.modules.leakdetection` | Target data definition document |
| 11 | `Custody.md` | custody | `dz.sh.hidra.modules.custody` | Target data definition |
| 12 | `Hse.md` | hse | `dz.sh.hidra.modules.hse` | Target DDD / not yet repository-implemented |
| 13 | `Audit.md` | audit | `dz.sh.hidra.modules.audit` | Target DDD, architecture-aligned |
| 14 | `Workflow.md` | workflow | `dz.sh.hidra.modules.workflow` | Repository-aligned data definition document |
| 15 | `Identity.md` | identity | `dz.sh.hidra.modules.identity` | Corrected RBAC + ABAC + External IDM baseline |
| 16 | `Organization.md` | organization | `dz.sh.hidra.modules.organization` target; repository metadata says `dz.sh.hidra` | Corrected organization baseline |
| 17 | `Integration.md` | integration | `dz.sh.hidra.modules.integration` | Target DDD, repository-aligned to macro-architecture |
| 18 | `Configuration.md` | configuration | `dz.sh.hidra.modules.configuration` | Target data definition |
| 19 | `Notification.md` | notification | `dz.sh.hidra.modules.notification` | Target DDD, repository-aligned to macro-architecture |
| 20 | `Documents.md` | documents | `dz.sh.hidra.modules.documents` | Target data definition |

Referenced but not currently represented by a dedicated DDD file in this folder:

```text
alarm management
incident management
risk
analytics
reporting
party / external legal-entity master data
contracts / commercial module
finance / ERP accounting
```

These modules must not be implemented from implicit references alone. They require explicit DDD files before code generation.

---

## 3. Global architecture rule

Hidra uses a modular monolith with strict bounded-context ownership.

The global rule is:

```text
Each module owns its own business facts.
Other modules may store only stable references and readable snapshots.
No module may import another module's domain model or persistence model directly.
Cross-module interaction must use public application ports, stable DTOs, published events, or platform outbox/event infrastructure.
```

Forbidden generic package names inside business modules:

```text
shared
common
core
utils
helper
helpers
misc
```

Allowed standard package skeleton:

```text
dz.sh.hidra.modules.<module>
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
    persistence
      entity
      repository
      mapper
      adapter
    configuration
    integration
    messaging
    projection
```

Module-specific infrastructure subpackages are allowed when they represent concrete technology concerns, for example:

```text
storage
outbox
security
observability
```

---

## 4. System dependency chain

The DDD files imply the following functional dependency order:

```text
kernel
  -> platform
      -> identity
      -> organization
      -> topology
          -> telemetry
              -> planning
                  -> monitoring
                      -> alarm management       [referenced, DDD missing]
                          -> leak detection
                              -> incident management [referenced, DDD missing]
                                  -> hse
                                  -> integrity
                                  -> assets
                                  -> custody
                                      -> documents
                                      -> workflow
                                      -> audit
                                      -> notification
                                      -> integration
                                      -> configuration
                                      -> simulation
```

This chain is not a Java import chain. It expresses business data dependency and implementation sequencing.

Java imports must remain layer-safe and module-safe.

---

## 5. Cross-module reference policy

### 5.1 Neutral reference pattern

When one module needs to reference another module's object, use neutral references and snapshots:

```text
targetModule
targetTypeCode
targetId
targetCodeSnapshot
targetLabelSnapshot
```

For topology assets, use:

```text
topologyAssetTypeCode
topologyAssetId
topologyAssetCodeSnapshot
topologyAssetNameSnapshot
```

For actors, use:

```text
actorId
actorDisplayNameSnapshot
organizationUnitId
organizationUnitNameSnapshot
```

For workflow, use:

```text
workflowInstanceId
workflowTaskId
workflowStatusSnapshot
```

For audit, use:

```text
auditEventId
correlationId
requestId
```

### 5.2 Forbidden cross-module references

Forbidden in all domain and infrastructure models unless explicitly owned by the module:

```text
Facility facility;
Pipeline pipeline;
TelemetryReading reading;
WorkflowTask task;
AuditEvent auditEvent;
User user;
Employee employee;
Document document;
```

Use IDs and snapshots instead.

---

## 6. Kernel consolidated definition

### 6.1 Purpose

Kernel is not a business module. It owns only minimal reusable primitives and contracts that are safe to share across bounded contexts.

Kernel owns:

```text
Entity
AggregateRoot
ValueObject
DomainEvent
DomainEventId
CorrelationId
RequestId
ActorId
DateRange
TimeRange
Command
Query
Result
Page
PageRequest
ApiResponse
PagedApiResponse
ApiErrorResponse
ValidationErrorDetail
DomainException
BusinessRuleViolationException
InvalidValueObjectException
```

Kernel must not own:

```text
User
Role
Permission
Employee
OrganizationUnit
Pipeline
Facility
Equipment
TelemetryReading
WorkflowTask
WorkflowInstance
AuditRecord
Alarm
Incident
SimulationRun
KpiValue
```

### 6.2 Boundary rule

```text
Kernel objects must be small, immutable where possible, domain-neutral, framework-independent, business-agnostic, and safe to import by every module.
```

Kernel must not depend on:

```text
Spring
JPA / Hibernate
Jackson annotations
database schemas
REST controllers
module-specific domain packages
module-specific repositories
external system clients
```

---

## 7. Platform consolidated definition

### 7.1 Purpose

Platform is a technical-support module. It provides reusable infrastructure required by all modules but does not own hydrocarbon transportation business facts.

Platform owns:

```text
technical outbox persistence
domain-event serialization support
technical security context access
request correlation and logging context
tenancy and organization-scope runtime context
technical configuration binding
exception mapping support
persistence and transaction infrastructure conventions
```

Platform does not own:

```text
users, roles, permissions, ABAC/RBAC policies
employees or organization units
business audit records
workflow instances or tasks
topology assets
telemetry readings
business notifications
integration connector business mapping
analytics, reports, AI decisions
```

### 7.2 Main persistent entity

| Entity | Table | Purpose |
|---|---|---|
| `OutboxEvent` | `hidra_platform_outbox_event` | Stores serialized domain events pending publication. |

Platform outbox is not an audit ledger.

---

## 8. Identity consolidated definition

### 8.1 Purpose

Identity owns application security identity, RBAC authorization, ABAC policy evaluation, and controlled external identity-provider mappings.

Identity owns:

```text
User
Group
Role
Permission
UserRoleGrant
RolePermissionGrant
UserPermissionGrant
UserGroupMembership
GroupRoleGrant
AuthorizationPolicy
AuthorizationPolicyVersion
AuthorizationPolicyRule
AttributeDefinition
SubjectSecurityAttribute
AuthorizationDelegationGrant
AuthorizationDecision
IdentityProvider
ExternalIdentity
ExternalGroupMapping
ExternalRoleMapping
ExternalPermissionMapping
IdentitySynchronizationJob
IdentitySynchronizationRecord
LoginSession
AuthenticationEvent
```

Identity does not own:

```text
employees
organization units
topology assets
workflow tasks
audit records
HTTP filters
JWT parsing internals
Spring Security context plumbing
secret storage
raw LDAP/OIDC/SAML protocol clients
```

### 8.2 Critical rules

```text
Use User as the canonical identity aggregate name.
LDAP/AD/Keycloak/Azure identifiers belong to ExternalIdentity, not User.
Passwords, client secrets, certificates, private keys, and tokens must not be stored in identity tables.
Security groups are identity groups, not organization units.
```

---

## 9. Organization consolidated definition

### 9.1 Purpose

Organization owns Sonatrach/TRC internal structure, employees, positions, assignments, reporting lines, responsibility scopes, and internal delegation of responsibility.

Organization owns:

```text
OrganizationUnit
OrganizationUnitType
OrganizationUnitTypeTranslation
Position
Employee
EmployeeAddress
AdministrativeState
AdministrativeDistrict
AdministrativeLocality
EmployeeAssignment
ReportingLine
ResponsibilityAssignment
OrganizationDelegation
Shift
ShiftAssignment
OrganizationContactPoint
OrganizationHierarchySnapshot
```

Organization must not own:

```text
User
Group
Role
Permission
Facility
Pipeline
Equipment
WorkflowTask
WorkflowInstance
AuditRecord
Party
Vendor
Contractor
Manufacturer
JointVenturePartner
TelemetryPoint
TelemetryReading
```

### 9.2 Critical distinction

```text
OrganizationUnit(type = STATION)
```

means a people/responsibility unit for a station. It is not the physical station facility. The physical station is owned by `topology.Facility`.

---

## 10. Topology consolidated definition

### 10.1 Purpose

Topology owns the physical and logical representation of the hydrocarbon transportation network.

Topology owns:

```text
PipelineSystem
Pipeline
PipelineSegment
TopologyNode
TopologyConnection
Facility
FacilityType
FacilityTypeVersion
FacilityAttributeDefinition
FacilityAttributeValue
PipelineSystemFacility
FacilityNodeBinding
Equipment
EquipmentType
EquipmentTypeVersion
EquipmentAttributeDefinition
EquipmentAttributeValue
MeasurementLocation
TopologySnapshot
Topology-versioned read models
Topology-owned party role assignments by reference
```

Topology does not own:

```text
employees
organization hierarchy
legal parties/vendors/manufacturers
users, roles, permissions
workflow decisions
audit records
raw telemetry readings
custody/fiscal records
contracts and procurement
```

### 10.2 Core design rules

```text
Hard-code the topology backbone.
Configure asset details.
```

Explicit structural entities:

```text
PipelineSystem
Pipeline
PipelineSegment
TopologyNode
TopologyConnection
Facility
Equipment
MeasurementLocation
```

Configurable details:

```text
FacilityType attributes
EquipmentType attributes
Facility-specific operational fields
Equipment-specific technical fields
```

Do not create one hard-coded table per facility profile such as `StationProfile`, `TerminalProfile`, or `ExportTerminalProfile`.

Use:

```text
FacilityType
FacilityTypeVersion
FacilityAttributeDefinition
FacilityAttributeValue
```

### 10.3 Table prefix

```text
hidra_topology_*
```

---

## 11. Telemetry consolidated definition

### 11.1 Purpose

Telemetry is the industrial acquisition bounded context.

It owns the chain:

```text
source -> device -> telemetry point -> topology binding -> reading -> quality -> trusted reading
```

Telemetry owns:

```text
TelemetrySource
TelemetrySourceEndpoint
TelemetryDevice
TelemetryPoint
TelemetryExternalTagMapping
TelemetryPointBinding
TelemetryIngestionBatch
TelemetryReading
TelemetryQualityAssessment
TrustedTelemetryReading
TelemetryQuarantineRecord
TelemetryCatalogEntry
TelemetryCatalogTranslation
TelemetryUnit
TelemetryValidationRule
TelemetryPointStateSnapshot
```

Telemetry does not own:

```text
physical topology
pipeline systems
facilities
pipeline segments
stations
equipment as topology assets
monitoring deviations
alarms
incidents
workflow approvals
audit records
flow calculation
hydraulic simulation
risk scoring
analytics models
reporting artifacts
custody-transfer fiscal records
maintenance work orders
```

### 11.2 Core rules

```text
Telemetry owns measured facts.
Monitoring interprets trusted readings.
Planning owns expected values.
Custody owns official accepted quantities.
```

Topology binding must use neutral topology references only.

---

## 12. Planning consolidated definition

### 12.1 Purpose

Planning owns the expected operational state for hydrocarbon transportation.

Planning answers:

```text
What should be transported?
When should it be transported?
From where to where?
On which pipeline system, facility, or delivery point?
At what planned volume, flow rate, pressure, and operating window?
Which plan version is active and approved?
Which constraints or nominations justify the plan?
What expected state should monitoring compare actual telemetry against?
```

Planning owns:

```text
PlanningPeriod
OperationalPlan
PlanRevision
PlanScenario
Nomination
NominationScheduleLine
PlanTarget
ExpectedFlowState
PlannedOperationWindow
PlanConstraint
PlanApprovalReference
PlanningCatalogEntry
PlanningCatalogTranslation
ForecastSeries
ForecastPoint
PlanActualReviewSnapshot
```

Planning must not own:

```text
telemetry_reading
telemetry_point
trusted_telemetry_reading
pipeline
pipeline_segment
facility
equipment
monitoring_deviation
alarm
incident
workflow_task
workflow_instance_details
audit_record
custody_transfer_actual
invoice
contract_master
maintenance_work_order
simulation_run
```

### 12.2 Core rule

```text
Planning owns expected values.
Telemetry owns actual measured facts.
Monitoring compares expected vs actual.
```

---

## 13. Monitoring consolidated definition

### 13.1 Purpose

Monitoring evaluates operational state using validated telemetry, approved planning targets, configured thresholds, monitoring rules, and operational asset references.

Monitoring owns:

```text
MonitoringRule
MonitoringThreshold
MonitoringEvaluation
OperationalState
OperationalStateSnapshot
PlanActualDeviation
MonitoringAlertCandidate
MonitoringAcknowledgement
RiskSignal
MonitoringCatalogEntry
MonitoringCatalogTranslation
```

Monitoring must not own:

```text
telemetry readings
telemetry ingestion
flow plans
plan approval workflow
topology assets
formal alarm lifecycle
incident lifecycle
notification delivery
risk scoring engine
analytics models
SCADA/PLC/RTU control commands
```

### 13.2 Core rule

```text
Monitoring detects and explains operational deviations.
Alarm Management owns formal alarm lifecycle.
Incident Management owns operational incident lifecycle.
Telemetry owns measured facts.
Planning owns expected values.
```

---

## 14. Leak Detection consolidated definition

### 14.1 Purpose

Leak Detection detects, evaluates, localizes, and explains suspected hydrocarbon leaks using trusted operational evidence.

Leak Detection owns:

```text
LeakDetectionProfile
LeakDetectionMethodCatalog
LeakDetectionMethodTranslation
LeakDetectionRule
LeakDetectionRun
LeakCandidate
LeakEvidenceLink
LeakLocalizationEstimate
LeakSeverityAssessment
LeakVerificationAction
LeakDetectionCase
LeakCaseStatusHistory
LeakEscalationReference
LeakDismissalReason
```

Leak Detection does not own:

```text
pipeline, facility, segment, node, equipment, or topology graph
telemetry readings
trusted telemetry readings
monitoring thresholds
monitoring evaluations
alarm lifecycle
incident lifecycle
notification delivery
maintenance work orders
SCADA commands
valve/pump/compressor actuation
hydraulic simulation engine
full digital twin engine
```

### 14.2 Non-negotiable safety rule

Leak Detection is decision support only.

Forbidden behavior:

```text
automatic valve closure
automatic pump shutdown
automatic compressor shutdown
automatic PLC/RTU/SCADA command
automatic ESD/SIS interaction
direct write to OT control systems
```

Allowed behavior:

```text
create leak candidate
raise leak suspicion
calculate confidence
estimate location
link evidence
request operator verification
escalate to Alarm Management or Incident Management
emit audit-ready events
```

---

## 15. HSE consolidated definition

### 15.1 Purpose

HSE manages health, safety, environment consequences, controls, compliance cases, permits, inspections, observations, corrective actions, and evidence.

HSE owns:

```text
HseCase
HseCaseStatusHistory
HseCaseEvidenceLink
HseImpactAssessment
HseCorrectivePreventiveAction
HseClosure
HazardReport
NearMissReport
SafetyObservation
EnvironmentalEvent
PermitToWork
HseInspection
EmergencyDrill
ComplianceObligation
ComplianceAssessment
HseCatalogEntry
HseCatalogTranslation
```

Recommended aggregate roots:

```text
HseCase
PermitToWork
HseInspection
ComplianceObligation
EmergencyDrill
```

HSE does not own:

```text
telemetry readings
monitoring rules
alarm lifecycle
leak localization algorithms
incident operational response lifecycle
physical topology
maintainable asset lifecycle
maintenance work orders
identity users, roles, permissions, or credentials
organization units or employee master records
risk scoring engine
audit storage
notification delivery
SCADA/OT actuation
```

### 15.2 Bounded-context rule

```text
Incident Management says: what operational problem happened and how was it handled?
HSE Management says: what health, safety, environmental, and compliance consequences exist?
```

Do not merge incident, leak, alarm, and HSE records into one table.

---

## 16. Network Integrity consolidated definition

### 16.1 Purpose

Integrity manages the technical condition of hydrocarbon pipeline network assets.

Integrity owns:

```text
IntegrityProgram
IntegrityAssessment
IntegrityAssessmentScope
InspectionCampaign
InspectionRun
InspectionFinding
PipelineDefect
DefectMeasurement
WallThicknessMeasurement
CorrosionFeature
CoatingConditionObservation
CathodicProtectionSurvey
CathodicProtectionMeasurement
IntegrityThreat
DefectAssessment
RemainingLifeEstimate
IntegrityRecommendation
IntegrityCase
IntegrityCaseStatusHistory
IntegrityEvidenceLink
IntegrityCatalogEntry
IntegrityCatalogTranslation
```

Integrity does not own:

```text
topology assets
telemetry readings
monitoring rules or operational state
alarm lifecycle
incident response lifecycle
maintenance work orders
spare parts or asset inventory
HSE compliance cases
enterprise risk scoring
workflow routing
audit event storage
document binaries
SCADA or OT actuation
```

### 16.2 Critical rules

```text
Integrity references topology assets by neutral reference only.
Integrity may recommend maintenance action but must not execute work orders.
Integrity may open a case from an incident, alarm, leak case, inspection result, or manual concern, but incident lifecycle remains outside integrity.
High-severity integrity decisions require evidence links.
```

### 16.3 Table prefix

```text
hidra_integrity_*
```

---

## 17. Asset Management consolidated definition

### 17.1 Purpose

Asset Management manages lifecycle, maintainability, configuration, ownership, warranty, spare-parts relation, and maintenance readiness of operational assets.

Asset Management owns:

```text
MaintainableAsset
AssetType
AssetTypeTranslation
AssetTechnicalAttributeDefinition
AssetTechnicalAttributeValue
AssetInstallation
AssetManufacturerReference
AssetModel
AssetSerialIdentity
AssetLifecycleEvent
MaintenanceStrategy
MaintenancePlan
MaintenanceTaskTemplate
MaintenanceWorkOrder
MaintenanceWorkOrderTask
MaintenanceExecutionRecord
SparePart
AssetSparePartCompatibility
AssetDocumentReference
AssetWarranty
AssetServiceContractReference
AssetConditionRecord
AssetMeterReadingReference
AssetCatalogEntry
AssetCatalogTranslation
```

Asset Management does not own:

```text
topology assets and equipment
organization units and employees
identity users, groups, roles, permissions
workflow instances and tasks
incident records
integrity cases and defects
telemetry readings
documents
external parties/vendors/manufacturers when Party exists
audit records
```

### 17.2 Boundary rules

Asset Management must not import topology domain classes.

Bad:

```text
Asset.facility: Facility
Asset.pipeline: Pipeline
Asset.equipment: EquipmentJpaEntity
```

Good:

```text
AssetTopologyReference(topologyAssetTypeCode, topologyAssetId, topologyAssetCode, topologyAssetNameSnapshot)
```

Asset Management may record maintenance observations and repair completion records, but Network Integrity owns engineering integrity decisions.

---

## 18. Custody Transfer consolidated definition

### 18.1 Purpose

Custody owns the official operational/fiscal record of hydrocarbon custody transfer.

Custody owns:

```text
CustodyTransferPoint
CustodyAgreement
CustodyAgreementParty
CustodyMeasurementPeriod
CustodyBatch
CustodyMeteringSystem
CustodyMeterRunSnapshot
CustodyMeasurementSnapshot
CustodyQualitySample
CustodyQualityCertificate
CustodyQuantityCalculation
CustodyCorrectionFactor
CustodyTransferTicket
CustodyTicketLine
CustodyReconciliation
CustodyDiscrepancy
CustodyApprovalReference
CustodyDocumentReference
CustodyCatalogEntry
CustodyCatalogTranslation
```

Custody must not own:

```text
raw telemetry readings
telemetry devices/tags/sources
pipeline topology
facilities/equipment physical placement
planning targets
monitoring deviations
alarm lifecycle
incident lifecycle
maintenance work orders
financial invoicing
ERP accounting postings
SCADA/PLC/RTU actuation
```

### 18.2 Core rule

```text
Telemetry says what was measured.
Planning says what was expected.
Custody says what was officially transferred and accepted.
Finance/ERP says what was invoiced or posted.
```

Custody must store immutable snapshots of official evidence used for transfer decisions.

---

## 19. Workflow consolidated definition

### 19.1 Purpose

Workflow owns human and organizational process execution around validation, approval, rejection, correction requests, delegation, escalation, task assignment, comments, timeline, and audit-ready decision evidence.

Workflow owns:

```text
workflow definitions
workflow definition versions
workflow steps
workflow transitions
workflow instances
workflow tasks
workflow assignments
workflow actions
workflow delegations
workflow escalation rules
workflow comments
workflow state history
workflow target references
actor snapshots
organization snapshots
workflow decision reasons
workflow priorities
workflow audit-ready event payloads
```

Workflow does not own:

```text
telemetry readings
telemetry values
telemetry correction values
topology assets
organization units
employees
identity users, roles, groups, credentials
planning targets
monitoring thresholds
alarm lifecycle
incident lifecycle
maintenance execution facts
risk scores
audit storage
notification delivery
analytics models
reporting artifacts
SCADA / PLC / RTU / OT control actions
```

### 19.2 Implemented baseline tables

```text
hidra_workflow_type_catalog
hidra_workflow_type_translation
hidra_workflow_definition
hidra_workflow_step
hidra_workflow_transition
hidra_workflow_instance
hidra_workflow_task
hidra_workflow_assignment
hidra_workflow_action
hidra_workflow_delegation
hidra_workflow_escalation_rule
hidra_workflow_comment
hidra_workflow_state_history
```

### 19.3 Core rule

```text
Workflow owns the process.
The target module owns the business fact.
Audit owns durable evidence storage.
Notification owns message delivery.
Identity owns security identity.
Organization owns real people and structure.
```

---

## 20. Audit consolidated definition

### 20.1 Purpose

Audit provides durable, append-only, searchable evidence for important actions and state changes in Hidra.

Audit owns:

```text
AuditEvent
AuditActorSnapshot
AuditTargetReference
AuditActionReference
AuditDecisionContext
AuditBeforeAfterValue
AuditCorrelationContext
AuditEvidenceLink
AuditSearchProjection
AuditIntegritySeal
AuditRetentionPolicy
AuditExportRequest
AuditAccessRecord
AuditCatalogEntry
AuditCatalogTranslation
```

Audit does not own:

```text
identity users, roles, permissions, credentials
organization employees, positions, units
workflow routing, task lifecycle, or decision rules
telemetry readings or reading state
planning targets or plans
monitoring rules, thresholds, or deviations
alarm lifecycle
incident lifecycle
asset maintenance execution
risk scoring
analytics models
notification delivery
external integration execution
```

### 20.2 Core rule

```text
Audit records evidence.
Audit does not decide, approve, reject, validate, repair, reconcile, calculate, notify, or integrate.
```

Forbidden:

```text
Business module imports audit.domain model directly.
Audit table is updated to modify business state.
Audit event is used as the source of operational truth.
Audit record is edited after creation.
Sensitive values are stored unmasked.
Controller writes audit rows directly instead of using application ports/events.
```

---

## 21. Documents consolidated definition

### 21.1 Purpose

Documents manages controlled operational document metadata, versions, storage references, links to business objects, classification, retention, access grants, and lifecycle state.

Documents owns:

```text
Document
DocumentVersion
DocumentStorageObject
DocumentTargetLink
DocumentAccessGrant
DocumentReviewReference
DocumentRetentionRecord
DocumentExtractionRecord
DocumentExternalReference
DocumentCatalogEntry
DocumentCatalogTranslation
```

Documents does not own:

```text
audit event ledger
workflow task routing
incident lifecycle
HSE case lifecycle
asset maintenance lifecycle
custody transfer calculation
report generation logic
notification delivery
external storage engine internals
identity users, roles, or permissions
business objects to which documents are attached
```

### 21.2 Core rule

```text
Documents owns file metadata and business attachment.
Audit owns evidence of who did what and when.
Workflow owns approval/routing.
Business modules own the business object being documented.
Storage infrastructure owns the binary object.
```

### 21.3 Aggregate roots

```text
Document
DocumentCatalogEntry
```

---

## 22. Integration consolidated definition

### 22.1 Purpose

Integration is the controlled boundary between Hidra and external systems.

Integration owns:

```text
ExternalSystem
ExternalEndpoint
ConnectorInstance
Connection configuration metadata
Credential references, never secrets
IntegrationDataContract
IntegrationSchemaVersion
IntegrationMappingProfile
IntegrationFieldMapping
IntegrationTransformationRule
ExternalObjectReference
IntegrationExchangeMessage
IntegrationInboundRecord
IntegrationOutboundRecord
ImportJob
ExportJob
SynchronizationJob
IntegrationJobRun
IntegrationJobRunStep
SyncCursor
RetryPolicy
IntegrationRetryAttempt
IntegrationDeadLetterRecord
IntegrationReconciliationRun
IntegrationReconciliationIssue
IntegrationHealthSnapshot
IntegrationCatalogEntry
IntegrationCatalogTranslation
```

Integration does not own:

```text
telemetry readings
telemetry devices or points
topology assets
pipeline systems, pipelines, facilities, equipment
planning periods, plans, nominations, targets
monitoring rules, deviations, operational states
alarm lifecycle
leak detection cases
incident lifecycle
asset maintenance work orders
custody transfer tickets
HSE cases
users, roles, groups, permissions
organization units, employees, positions
audit ledger storage
notification templates and delivery lifecycle
analytics models and KPIs
SCADA/PLC/RTU command execution
```

### 22.2 Core rule

```text
External system
  -> integration connector
      -> integration mapping / staging / validation envelope
          -> target module public import/export port
              -> target module domain validation
                  -> target module owns the accepted business fact
```

Integration must never write directly to another module table.

---

## 23. Configuration consolidated definition

### 23.1 Purpose

Configuration governs runtime-controlled settings, operational parameters, scoped overrides, feature flags, parameter profiles, validation rules, activation windows, and configuration change history.

Configuration owns:

```text
configuration namespaces
configuration definitions
configuration definition versions
configuration values
scoped configuration overrides
configuration profiles
profile entries
feature flags
feature flag rules
configuration validation rules
configuration change requests
configuration deployments
resolved configuration snapshots
configuration external references
configuration catalogs and translations
```

Configuration does not own:

```text
platform technical property binding
secrets or secret values
business taxonomies owned by modules
workflow definitions or workflow routing
monitoring thresholds owned by monitoring
alarm rules owned by alarm management
telemetry point definitions
topology asset types
identity roles and permissions
organization units or employees
integration connector execution
report generation logic
audit event ledger
```

### 23.2 Platform vs configuration module

```text
Platform configuration = technical boot/runtime infrastructure properties.
Configuration module  = governed business/runtime settings managed as Hidra data.
```

### 23.3 Forbidden misuse

Do not use configuration as a dumping ground for:

```text
facility types owned by topology
telemetry quality codes owned by telemetry
workflow definitions owned by workflow
monitoring thresholds owned by monitoring
alarm severity model owned by alarm management
incident classification owned by incidents
HSE compliance obligations owned by HSE
custody calculation formulas owned by custody transfer
asset maintenance strategies owned by asset management
identity permissions owned by identity
organization hierarchy owned by organization
```

---

## 24. Notification consolidated definition

### 24.1 Purpose

Notification is the controlled communication and delivery-tracking bounded context.

Notification owns:

```text
NotificationTemplate
NotificationTemplateVersion
LocalizedTemplateContent
NotificationChannel
RecipientProfile
RecipientReference
RecipientGroup
NotificationPreference
NotificationPolicy
NotificationRequest
NotificationMessage
NotificationBatch
DeliveryAttempt
DeliveryStatusHistory
RetrySchedule
SuppressionRule
QuietHourRule
EscalationNotificationRoute
DigestSchedule
NotificationAcknowledgementLink
NotificationEvidenceReference
NotificationCatalogEntry
NotificationCatalogTranslation
```

Notification does not own:

```text
telemetry readings
telemetry quality/state validation
monitoring thresholds
monitoring deviations
alarm lifecycle and severity decision
incident lifecycle and response actions
leak detection cases
planning approvals
workflow routing and decisions
audit ledger storage
HSE cases
asset maintenance work orders
custody transfer tickets
topology assets
users, roles, groups, permissions
organization units, employees, positions
external email/SMS/push provider implementation
business KPI/reporting content
```

### 24.2 Core rule

```text
Notification delivers communication.
It does not decide operational truth, severity, approval, assignment, closure, or compliance.
```

---

## 25. Simulation consolidated definition

### 25.1 Purpose

Simulation is the decision-support bounded context for running hydraulic, operational, optimization, and what-if scenarios over trusted topology and operational data.

Simulation owns:

```text
SimulationModel
SimulationModelVersion
SimulationScenario
SimulationScenarioAssumption
SimulationInputSnapshot
SimulationInputDataset
SimulationConstraint
SimulationObjective
SimulationRun
SimulationRunStep
SimulationSolverTrace
SimulationResultSummary
SimulationResultValue
SimulationResultSeriesReference
SimulationConstraintEvaluation
SimulationOptimizationCandidate
SimulationCandidateChange
SimulationCandidateOperatingCondition
SimulationCandidateScore
SimulationRecommendation
SimulationValidationFinding
SimulationSensitivityAnalysis
SimulationEvidenceLink
SimulationCatalogEntry
SimulationCatalogTranslation
```

Simulation does not own:

```text
physical topology
official topology snapshots
pipeline systems, pipelines, segments, stations, facilities, or equipment
telemetry readings
trusted telemetry readings
operational plans and nominations
monitoring rules and thresholds
alarm lifecycle
incident lifecycle
asset maintenance execution
network integrity assessments
custody transfer records
workflow approval state
audit ledger
notification delivery
SCADA commands or field actuation
```

### 25.2 Core rule

```text
Simulation recommends.
Topology owns the approved network state.
Workflow approves.
Audit proves.
```

A simulation result is not operational truth. A result becomes official only when the owning module accepts it.

---

## 26. Consolidated implementation sequencing

### 26.1 Foundation modules

Implement or harden in this order:

```text
1. kernel
2. platform
3. identity
4. organization
```

Reason:

```text
All later modules need stable contracts, actor identity, organization references, platform outbox, error handling, pagination, and API response contracts.
```

### 26.2 Operational foundation modules

```text
5. topology
6. telemetry
7. planning
8. monitoring
```

Reason:

```text
Topology defines the network.
Telemetry defines actual measured facts.
Planning defines expected operational state.
Monitoring compares actual vs expected and creates deviations/candidates.
```

### 26.3 Operational intelligence and response modules

```text
9. alarm management       [requires explicit DDD first]
10. leakdetection
11. incident management   [requires explicit DDD first]
12. hse
13. integrity
14. assets
```

Reason:

```text
Leak detection depends on trusted monitoring and alarm context.
HSE, integrity, and assets must reference incidents/alarms without owning their lifecycles.
```

### 26.4 Governance and support modules

```text
15. workflow
16. audit
17. documents
18. notification
19. integration
20. configuration
21. custody
22. simulation
```

Reason:

```text
Workflow, audit, documents, notification, integration, and configuration are cross-cutting support contexts.
Custody and simulation depend on mature topology, telemetry, planning, workflow, audit, and document references.
```

---

## 27. Consolidated table-prefix rules

| Module | Table prefix |
|---|---|
| platform | `hidra_platform_*` |
| identity | `hidra_identity_*` |
| organization | `hidra_org_*` |
| topology | `hidra_topology_*` |
| telemetry | `hidra_telemetry_*` |
| planning | `hidra_planning_*` |
| monitoring | `hidra_monitoring_*` |
| leakdetection | `hidra_leak_detection_*` or `hidra_leakdetection_*`; choose one before implementation |
| hse | `hidra_hse_*` |
| integrity | `hidra_integrity_*` |
| assets | `hidra_asset_*` or `hidra_assets_*`; choose one before implementation |
| custody | `hidra_custody_*` |
| workflow | `hidra_workflow_*` |
| audit | `hidra_audit_*` |
| documents | `hidra_documents_*` |
| integration | `hidra_integration_*` |
| configuration | `hidra_configuration_*` |
| notification | `hidra_notification_*` |
| simulation | `hidra_simulation_*` |

Rule:

```text
A module may not create tables using another module's prefix.
```

---

## 28. Consolidated catalog policy

User-facing business taxonomies should be catalog-backed, not hard-coded Java enums.

Catalog-backed examples:

```text
FacilityType
EquipmentType
Telemetry point type
Telemetry quality code
Planning period type
Nomination type
Monitoring severity
Monitoring rule type
Leak detection method
HSE classification
Integrity threat type
Custody agreement type
Document classification
Notification template category
Workflow reason and priority
```

Java enums are acceptable only for technical states that are not operator-managed and not business-taxonomy driven.

Examples:

```text
OutboxEventStatus
Internal processing result
Retry execution state
Low-level command type
```

---

## 29. Consolidated safety policy

No Hidra business module may directly actuate OT systems.

Forbidden unless a future dedicated safety-governed architecture decision explicitly allows it:

```text
automatic valve closure
automatic pump shutdown
automatic compressor shutdown
automatic PLC command
automatic RTU command
automatic SCADA write-back
automatic SIS/ESD interaction
```

Allowed pattern:

```text
detect
explain
recommend
request human verification
request workflow approval
notify responsible actors
record audit evidence
export approved information through integration
```

---

## 30. Consolidated validation policy

Validation responsibilities are split as follows:

| Layer / module type | Responsibility |
|---|---|
| API request | Shape, required input, format, min/max, syntactic constraints. |
| Application service | Use-case orchestration, command/query null checks, repository checks, cross-aggregate coordination through ports. |
| Domain model | Invariants, lifecycle rules, required business state. |
| Domain service / policy | Complex business rules involving multiple entities/value objects. |
| Repository adapter | Persistence mapping and database constraints only; must not invent business rules. |
| Database | Last-resort integrity constraints, uniqueness, foreign keys within the same module only. |

Cross-module foreign keys are not allowed by default.

Use stable references and snapshots instead.

---

## 31. Consolidated code-generation rules

Any generated code for these DDDs must satisfy the following conditions:

```text
1. Read the relevant module DDD before generating code.
2. Read this consolidated DDD before generating code.
3. Generate code only inside the requested module.
4. Never create shared/common/core/utils/helper/helpers/misc packages.
5. Never import another module's domain or infrastructure package from a business module.
6. Use kernel only for generic contracts and primitives.
7. Use platform only for technical infrastructure integration.
8. Represent cross-module dependencies as IDs, references, snapshots, ports, DTOs, or events.
9. Keep domain models framework-independent.
10. Keep JPA entities in infrastructure.persistence.entity only.
11. Keep REST request/response models in api.rest.request / api.rest.response only.
12. Use @Schema only on API request/response models, never on domain models.
13. Use Bean Validation on API request models, not as a substitute for domain invariants.
14. Do not generate missing modules that do not have a DDD file unless explicitly requested.
15. Do not implement alarm management, incident management, risk, analytics, reporting, party, finance, or contracts from references alone.
```

---

## 32. Acceptance checklist

Before accepting new code or migrations for any DDD module, verify:

```text
[ ] File is in the correct module package.
[ ] Package name matches the module DDD.
[ ] No forbidden generic package was introduced.
[ ] Domain model has no JPA, Swagger, Spring MVC, or Jackson dependency.
[ ] JPA entity is infrastructure-only.
[ ] API request/response model is API-only.
[ ] Cross-module references are neutral IDs/snapshots.
[ ] No direct import from another module's domain/infrastructure package.
[ ] Table prefix belongs to the module.
[ ] User-facing taxonomy is catalog-backed.
[ ] Business invariant is enforced in domain/application, not only database.
[ ] Audit is append-only and not used as business state.
[ ] Workflow is process-only and does not own target business facts.
[ ] Integration does not directly write another module's table.
[ ] No OT/SCADA actuation behavior is introduced.
[ ] Missing referenced modules are not implemented without their own DDD.
```

---

## 33. Master bounded-context summary

```text
Kernel        -> generic contracts and primitives only.
Platform      -> technical infrastructure mechanisms only.
Identity      -> who may do what.
Organization  -> internal people, units, positions, responsibility.
Topology      -> physical/logical hydrocarbon network.
Telemetry     -> measured operational facts.
Planning      -> expected operational state.
Monitoring    -> actual-vs-expected deviation intelligence.
LeakDetection -> leak suspicion, evidence, localization, verification support.
HSE           -> health, safety, environment, compliance consequences.
Integrity     -> technical condition and integrity decisions.
Assets        -> maintainability, lifecycle, maintenance readiness.
Custody       -> official transferred and accepted quantities.
Workflow      -> human/organizational process execution.
Audit         -> immutable evidence of actions and decisions.
Documents     -> document metadata, versions, links, retention.
Integration   -> external system boundary and exchange traceability.
Configuration -> governed runtime settings and feature/config profiles.
Notification  -> communication preparation, delivery, retry, tracking.
Simulation    -> decision-support scenarios, runs, recommendations.
```

---

## 34. Final rule

```text
A module owns meaning, not just tables.
A table belongs to the module that owns the business meaning of the data.
When another module needs that data, it references it through stable IDs, snapshots, public ports, events, or projections.
```
