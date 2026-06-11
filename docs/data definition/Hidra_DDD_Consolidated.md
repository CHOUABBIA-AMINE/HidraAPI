# HIDRA — Consolidated Data Definition Document

```text
Document code : HIDRA-CONSOLIDATED-DDD
Repository    : HidraAPI
Folder        : docs/data definition
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
CreatedOn     : 2026-06-11
UpdatedOn     : 2026-06-11
Status        : Corrected and locked consolidated DDD master reference
Scope         : Consolidates and governs all Data Definition Documents under docs/data definition
```

---

## 1. Purpose

This document is the master Data Definition Document for Hidra / HyFlo.

It consolidates the DDD files under:

```text
docs/data definition
```

It defines the implementation contract for:

```text
module ownership
bounded-context boundaries
canonical package roots
data ownership rules
cross-module reference rules
forbidden dependencies
table-prefix decisions
missing-DDD blockers
safety constraints
code-generation guardrails
```

The detailed field-level module DDDs remain the reference for entity fields. This master document is the higher-level governing contract. If a source DDD conflicts with this master, this master wins until the source DDD is corrected.

---

## 2. Source DDD inventory

| # | Source file | Module / context | Canonical package root | Master status |
|---:|---|---|---|---|
| 1 | `Kernel.md` | kernel | `dz.sh.hidra.kernel` | Active foundation DDD |
| 2 | `Platform.md` | platform | `dz.sh.hidra.platform` | Active technical DDD |
| 3 | `Topology.md` | topology | `dz.sh.hidra.modules.topology` | Active operational-network DDD |
| 4 | `Telemetry.md` | telemetry | `dz.sh.hidra.modules.telemetry` | Active measurement DDD |
| 5 | `Planning.md` | planning | `dz.sh.hidra.modules.planning` | Active expected-state DDD |
| 6 | `Monitoring.md` | monitoring | `dz.sh.hidra.modules.monitoring` | Active deviation/evaluation DDD; alarm/incident remain blocked |
| 7 | `Simulation.md` | simulation | `dz.sh.hidra.modules.simulation` | Active decision-support DDD |
| 8 | `Assets.md` | assets / asset-management | `dz.sh.hidra.modules.assets` | Active maintainability DDD; table prefix locked below |
| 9 | `Integrity.md` | integrity | `dz.sh.hidra.modules.integrity` | Active integrity-condition DDD |
| 10 | `LeakDetection.md` | leakdetection | `dz.sh.hidra.modules.leakdetection` | Active leak-decision-support DDD; table prefix locked below |
| 11 | `Custody.md` | custody | `dz.sh.hidra.modules.custody` | Active official-transfer DDD |
| 12 | `Hse.md` | hse | `dz.sh.hidra.modules.hse` | Active HSE consequence/compliance DDD |
| 13 | `Audit.md` | audit | `dz.sh.hidra.modules.audit` | Active evidence-ledger DDD |
| 14 | `Workflow.md` | workflow | `dz.sh.hidra.modules.workflow` | Active process-execution DDD |
| 15 | `Identity.md` | identity | `dz.sh.hidra.modules.identity` | Active RBAC/ABAC/external-IDM DDD |
| 16 | `Organization.md` | organization | `dz.sh.hidra.modules.organization` | Active organization DDD; package root corrected below |
| 17 | `Integration.md` | integration | `dz.sh.hidra.modules.integration` | Active external-system-boundary DDD |
| 18 | `Configuration.md` | configuration | `dz.sh.hidra.modules.configuration` | Active governed-runtime-configuration DDD |
| 19 | `Notification.md` | notification | `dz.sh.hidra.modules.notification` | Active communication-delivery DDD |
| 20 | `Documents.md` | documents | `dz.sh.hidra.modules.documents` | Active document-metadata DDD |

---

## 3. Corrections applied in this locked version

This version applies the corrections requested after review of the first consolidated DDD.

### 3.1 Locked table-prefix decisions

The following decisions are now final for implementation:

| Module | Locked table prefix | Forbidden alternatives |
|---|---|---|
| `leakdetection` | `hidra_leak_detection_*` | `hidra_leakdetection_*` |
| `assets` | `hidra_asset_*` | `hidra_assets_*` |
| `organization` | `hidra_org_*` | `hidra_organization_*` unless a future migration ADR explicitly changes it |
| `topology` | `hidra_topology_*` | `hidra_asset_*`, `hidra_integrity_*` |
| `integrity` | `hidra_integrity_*` | `hidra_topology_*`, `hidra_asset_*` |
| `hse` | `hidra_hse_*` | `hidra_incident_*`, `hidra_integrity_*` |
| `custody` | `hidra_custody_*` | `hidra_telemetry_*`, `hidra_planning_*` |
| `configuration` | `hidra_configuration_*` | module-owned catalog prefixes |

Rule:

```text
A module MUST NOT create tables using another module's prefix.
Open prefix choices are no longer allowed in implementation tasks.
```

### 3.2 Corrected organization package root

`Organization.md` previously contained repository baseline wording that could be misread as `dz.sh.hidra` being the canonical package root.

The canonical package root is now locked as:

```text
dz.sh.hidra.modules.organization
```

Any older `dz.sh.hidra` organization wording is legacy/repository-baseline metadata only, not an implementation target.

### 3.3 Missing-DDD blockers

The following referenced contexts are blocked until each has its own explicit DDD file under `docs/data definition`:

```text
alarm management
incident management
party / external legal-entity master data
risk
analytics
reporting
contracts / commercial
finance / ERP accounting
```

Blocked means:

```text
Do not create Java packages.
Do not create migrations.
Do not create JPA entities.
Do not create repositories.
Do not create controllers.
Do not create DTOs.
Do not create package-info.java skeletons.
Do not infer entities from references in other DDDs.
```

Allowed until a DDD exists:

```text
neutral reference fields
snapshot fields
external target type codes
future-module notes in documentation
```

### 3.4 Boundary guardrails strengthened

The following boundary pairs are now implementation-critical:

```text
Topology vs Assets
Topology vs Integrity
Topology vs HSE
Topology vs Custody
Monitoring vs Alarm Management
Monitoring vs Incident Management
Monitoring vs Leak Detection
Leak Detection vs Incident Management
Incident Management vs HSE
Custody vs Telemetry
Custody vs Planning
Configuration vs module-owned catalogs
```

A generator or developer MUST stop if a task crosses these boundaries without an explicit application port, DTO, event, or neutral reference model.

### 3.5 Documentation and annotation rule locked

Domain, application, and infrastructure models must not use OpenAPI annotations.

Forbidden outside API request/response models:

```text
@Schema
@Tag
@Operation
@ApiResponse
@ApiResponses
@Parameter
```

`@Schema` is allowed only in:

```text
dz.sh.hidra.modules.<module>.api.rest.request
dz.sh.hidra.modules.<module>.api.rest.response
```

`@Tag`, `@Operation`, `@ApiResponse`, `@ApiResponses`, and `@Parameter` are allowed only in REST controller or API-layer types.

Domain models must use Javadoc for domain documentation, not Swagger/OpenAPI annotations.

---

## 4. Global architecture rule

Hidra uses a modular monolith with strict internal boundaries.

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

Allowed standard module package skeleton:

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

Module-specific infrastructure subpackages are allowed only when they represent concrete technology concerns, for example:

```text
storage
outbox
security
observability
```

---

## 5. System sequencing rule

The DDD files imply the following implementation order:

```text
kernel
  -> platform
      -> identity
      -> organization
      -> topology
          -> telemetry
              -> planning
                  -> monitoring
                      -> alarm management       [blocked: DDD missing]
                          -> leak detection
                              -> incident management [blocked: DDD missing]
                                  -> hse
                                  -> integrity
                                  -> assets
                                  -> custody
                                      -> workflow
                                      -> audit
                                      -> documents
                                      -> notification
                                      -> integration
                                      -> configuration
                                      -> simulation
```

This is not a Java import chain. It is a business data dependency and implementation sequencing chain.

Java imports must remain layer-safe and module-safe.

---

## 6. Cross-module reference policy

### 6.1 Allowed neutral reference pattern

When one module needs to reference another module's business object, use neutral references and snapshots:

```text
targetModule
targetTypeCode
targetId
targetCodeSnapshot
targetLabelSnapshot
```

For topology assets:

```text
topologyAssetTypeCode
topologyAssetId
topologyAssetCodeSnapshot
topologyAssetNameSnapshot
```

For actors:

```text
actorId
actorDisplayNameSnapshot
organizationUnitId
organizationUnitNameSnapshot
```

For workflow:

```text
workflowInstanceId
workflowTaskId
workflowStatusSnapshot
```

For audit:

```text
auditEventId
correlationId
requestId
```

### 6.2 Forbidden direct references

Forbidden in domain, application, and infrastructure models unless the referenced type belongs to the same module:

```text
Facility facility;
Pipeline pipeline;
Equipment equipment;
TelemetryReading reading;
TrustedTelemetryReading trustedReading;
WorkflowTask task;
WorkflowInstance workflowInstance;
AuditEvent auditEvent;
User user;
Employee employee;
Document document;
MaintenanceWorkOrder workOrder;
IntegrityCase integrityCase;
HseCase hseCase;
CustodyTransferTicket ticket;
```

Use IDs, snapshots, public ports, DTOs, or events instead.

### 6.3 Forbidden imports

A business module must not import another business module's:

```text
domain.model.*
domain.value.*
domain.service.*
infrastructure.persistence.entity.*
infrastructure.persistence.repository.*
infrastructure.persistence.mapper.*
api.rest.controller.*
api.rest.request.*
api.rest.response.*
```

Allowed cross-module mechanisms:

```text
application input ports
application output ports
stable DTOs
published domain/application events
platform outbox
read-only projections explicitly designed for integration
neutral reference IDs and snapshots
```

---

## 7. Module ownership summary

### 7.1 Kernel

Kernel owns generic contracts and primitives only:

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

Kernel must not own business entities such as `User`, `Employee`, `Facility`, `Pipeline`, `TelemetryReading`, `WorkflowTask`, `Alarm`, `Incident`, `SimulationRun`, or `KpiValue`.

### 7.2 Platform

Platform owns technical infrastructure mechanisms only:

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

Primary persistent entity:

```text
OutboxEvent -> hidra_platform_outbox_event
```

Platform outbox is not an audit ledger.

### 7.3 Identity

Identity owns security identity and authorization:

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

Identity does not own employees, organization units, topology assets, workflow tasks, audit records, HTTP filters, JWT parsing internals, Spring Security context plumbing, or secret storage.

### 7.4 Organization

Organization owns Sonatrach/TRC internal people and structure:

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

Critical distinction:

```text
OrganizationUnit(type = STATION)
```

means a people/responsibility unit for a station. It is not the physical station facility. The physical station is owned by `topology.Facility`.

### 7.5 Topology

Topology owns the physical and logical hydrocarbon transportation network:

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

Topology owns where assets are and how the network connects. It does not own maintainability, work orders, integrity assessments, HSE consequences, custody tickets, telemetry values, or workflow decisions.

### 7.6 Telemetry

Telemetry owns measured operational facts:

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

Rule:

```text
Telemetry owns actual measured facts.
Planning owns expected values.
Monitoring compares expected vs actual.
Custody owns official accepted quantities.
```

### 7.7 Planning

Planning owns expected operational state:

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

Planning does not own telemetry readings, topology assets, monitoring deviations, alarms, incidents, workflow tasks, audit records, custody actuals, invoices, maintenance work orders, or simulation runs.

### 7.8 Monitoring

Monitoring owns operational evaluation and deviation intelligence:

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

Monitoring may detect and explain deviations. It does not own formal alarm lifecycle or incident lifecycle.

### 7.9 Leak Detection

Leak Detection owns leak suspicion decision support:

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

Locked prefix:

```text
hidra_leak_detection_*
```

Leak Detection is decision support only. It must not actuate valves, pumps, compressors, PLCs, RTUs, SIS, ESD, or SCADA commands.

### 7.10 HSE

HSE owns health, safety, environment, and compliance consequences:

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

Rule:

```text
Incident Management says what operational problem happened and how it was handled.
HSE says what health, safety, environmental, and compliance consequences exist.
```

Do not merge incidents, leaks, alarms, HSE cases, and integrity cases into one table.

### 7.11 Integrity

Integrity owns the technical condition and integrity decision record for network assets:

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

Integrity references topology assets by neutral reference only. It may recommend maintenance, but Asset Management owns maintenance work orders.

### 7.12 Assets / Asset Management

Assets owns maintainability, asset lifecycle, and maintenance readiness:

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

Locked prefix:

```text
hidra_asset_*
```

Assets must not import topology domain classes. Use `AssetTopologyReference(topologyAssetTypeCode, topologyAssetId, topologyAssetCodeSnapshot, topologyAssetNameSnapshot)`.

### 7.13 Custody

Custody owns official accepted transfer records:

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

Rule:

```text
Telemetry says what was measured.
Planning says what was expected.
Custody says what was officially transferred and accepted.
Finance/ERP says what was invoiced or posted.
```

### 7.14 Workflow

Workflow owns the process, not the business fact:

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

### 7.15 Audit

Audit owns immutable evidence:

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

Audit records evidence. It does not decide, approve, reject, validate, repair, reconcile, calculate, notify, or integrate.

### 7.16 Documents

Documents owns controlled file metadata and business attachment:

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

Documents owns metadata, not binary storage internals and not the business object being documented.

### 7.17 Integration

Integration owns external-system boundary and exchange traceability:

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

Integration must never write directly to another module table.

### 7.18 Configuration

Configuration owns governed runtime settings:

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

Configuration must not become a generic dumping ground for module-owned business taxonomy.

### 7.19 Notification

Notification owns communication preparation, delivery, retry, and tracking:

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

Notification delivers communication. It does not decide operational truth, severity, approval, assignment, closure, or compliance.

### 7.20 Simulation

Simulation owns decision-support scenarios and recommendations:

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

Simulation recommends. Topology owns approved network state. Workflow approves. Audit proves.

---

## 8. Boundary decision matrix

| Ambiguous concept | Correct owner | Allowed reference elsewhere | Forbidden interpretation |
|---|---|---|---|
| Physical pipeline / station / facility | `topology` | `topologyAssetId`, snapshots | Assets or HSE owns physical topology |
| Maintainable asset lifecycle | `assets` | topology neutral reference | Topology owns maintenance work orders |
| Pipeline defect / corrosion / remaining life | `integrity` | topology neutral reference | Assets or topology owns integrity decisions |
| Safety/environment consequence | `hse` | incident/leak/alarm neutral reference | Incident record doubles as HSE case |
| Official transferred quantity | `custody` | telemetry/planning snapshots | Telemetry reading is fiscal truth |
| Deviation detection | `monitoring` | telemetry/planning references | Monitoring owns alarms or incidents |
| Formal alarm lifecycle | `alarm management` | blocked until DDD exists | Monitoring creates alarm tables |
| Operational incident lifecycle | `incident management` | blocked until DDD exists | Leak/HSE/Integrity creates incident tables |
| External legal party | `party` | neutral party reference only | Organization owns vendors or external legal parties |
| Runtime feature/config parameter | `configuration` | scoped config reference | Configuration owns module business taxonomy |
| Module business catalog | owning module | catalog ID/code snapshot | Central shared catalog table |

---

## 9. Catalog and duplication policy

User-facing business taxonomies are module-owned and catalog-backed.

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

The following repeated shapes are intentional and must not be collapsed into generic shared tables:

```text
CatalogEntry
CatalogTranslation
EvidenceLink
Case
StatusHistory
TargetReference
ApprovalReference
DocumentReference
```

Reason:

```text
The shape is similar, but the business meaning belongs to the owning bounded context.
```

---

## 10. Safety policy

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

## 11. Validation policy

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

## 12. Code-generation rules

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
16. Stop when a task requires a blocked module and report the missing DDD precondition.
```

---

## 13. Acceptance checklist

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
[ ] Table prefix belongs to the module and matches the locked prefix table.
[ ] User-facing taxonomy is catalog-backed.
[ ] Business invariant is enforced in domain/application, not only database.
[ ] Audit is append-only and not used as business state.
[ ] Workflow is process-only and does not own target business facts.
[ ] Integration does not directly write another module's table.
[ ] No OT/SCADA actuation behavior is introduced.
[ ] Missing referenced modules are not implemented without their own DDD.
[ ] @Schema is absent from domain, application, infrastructure, JPA, and persistence models.
```

---

## 14. Master bounded-context summary

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

## 15. Final rule

```text
A module owns meaning, not just tables.
A table belongs to the module that owns the business meaning of the data.
When another module needs that data, it references it through stable IDs, snapshots, public ports, events, or projections.
```
