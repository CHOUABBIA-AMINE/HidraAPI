# HIDRA — Module-Specific DDD Corrections Overlay

```text
Document code : HIDRA-DDD-MODULE-CORRECTIONS
Repository    : HidraAPI
Folder        : docs/data definition
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
CreatedOn     : 2026-06-11
Status        : Active correction overlay
Applies to    : Specific DDDs listed below and Hidra_DDD_Consolidated.md
```

---

## 1. Purpose

This document applies the requested correction pass to the existing detailed DDDs without deleting their field-level entity tables.

It is a module-specific correction overlay for:

```text
Organization.md
Topology.md
Assets.md
Integrity.md
Hse.md
Custody.md
Monitoring.md
LeakDetection.md
Configuration.md
Hidra_DDD_Consolidated.md
```

When a rule in this correction overlay conflicts with an older sentence in one of those source DDDs, this overlay and `Hidra_DDD_Consolidated.md` are authoritative until the source DDD is manually normalized.

---

## 2. Correction: Organization.md

### 2.1 Canonical package root

The canonical package root for the organization module is:

```text
dz.sh.hidra.modules.organization
```

Any older reference to:

```text
dz.sh.hidra
```

inside `Organization.md` is repository-baseline metadata only and must not be used as a new implementation package target.

### 2.2 Table prefix

The locked table prefix is:

```text
hidra_org_*
```

Do not introduce:

```text
hidra_organization_*
hidra_identity_*
hidra_topology_*
hidra_workflow_*
hidra_audit_*
```

### 2.3 Boundary clarification

Organization owns internal people, units, positions, reporting, assignments, responsibility, shifts, delegations, and Algerian administrative-location references for employee civil address data.

Organization does not own:

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
TelemetryPoint
TelemetryReading
Vendor
Contractor
Manufacturer
JointVenturePartner
```

External legal parties remain blocked until a dedicated `party` DDD exists.

---

## 3. Correction: Topology.md

### 3.1 Ownership lock

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
```

### 3.2 Boundary clarification

Topology owns:

```text
where assets are
how pipeline systems connect
which facilities, nodes, segments, and equipment exist in the network
measurement locations as topology anchors
```

Topology does not own:

```text
maintainability
maintenance work orders
asset warranties
spare parts
inspection findings
integrity defects
HSE consequences
custody tickets
telemetry values
workflow decisions
```

### 3.3 Cross-module reference rule

Other modules may reference topology only through neutral topology references:

```text
topologyAssetTypeCode
topologyAssetId
topologyAssetCodeSnapshot
topologyAssetNameSnapshot
```

Forbidden outside topology:

```text
Facility facility;
Pipeline pipeline;
PipelineSegment segment;
Equipment equipment;
TopologyNode node;
TopologyConnection connection;
```

---

## 4. Correction: Assets.md

### 4.1 Locked table prefix

The asset-management table prefix is locked as:

```text
hidra_asset_*
```

Do not introduce:

```text
hidra_assets_*
hidra_topology_*
hidra_integrity_*
hidra_hse_*
```

### 4.2 Ownership lock

Assets / Asset Management owns maintainability, lifecycle, configuration, warranty, spare-parts relation, and maintenance readiness.

It owns:

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

### 4.3 Boundary clarification

Assets does not own the physical topology object. It references topology through:

```text
AssetTopologyReference(
  topologyAssetTypeCode,
  topologyAssetId,
  topologyAssetCodeSnapshot,
  topologyAssetNameSnapshot
)
```

Forbidden in assets domain and persistence models:

```text
Facility facility;
Pipeline pipeline;
Equipment equipment;
TopologyNode node;
```

Assets may record maintenance execution and condition records, but Network Integrity owns engineering integrity decisions.

---

## 5. Correction: Integrity.md

### 5.1 Boundary clarification

Integrity owns engineering condition, inspection findings, defects, remaining-life estimates, threats, and integrity recommendations.

Integrity does not own:

```text
physical topology assets
maintainable asset lifecycle
maintenance work orders
HSE case lifecycle
incident lifecycle
audit ledger
document binaries
SCADA or OT actuation
```

### 5.2 Topology reference rule

Integrity must reference topology assets by neutral reference only:

```text
topologyAssetTypeCode
topologyAssetId
topologyAssetCodeSnapshot
topologyAssetNameSnapshot
```

### 5.3 Assets boundary rule

Integrity may recommend a maintenance action, but it must not create or own maintenance work orders directly.

Correct flow:

```text
IntegrityRecommendation
  -> application port/event
      -> Asset Management creates MaintenanceWorkOrder
```

Forbidden:

```text
integrity.domain.model.MaintenanceWorkOrder
integrity.infrastructure.persistence.entity.AssetJpaEntity
```

---

## 6. Correction: Hse.md

### 6.1 Boundary clarification

HSE owns health, safety, environment, and compliance consequences.

HSE does not own:

```text
incident lifecycle
alarm lifecycle
leak localization algorithms
physical topology
maintainable asset lifecycle
maintenance work orders
identity users, roles, permissions
organization units or employee master records
audit storage
notification delivery
SCADA/OT actuation
```

### 6.2 Incident vs HSE rule

```text
Incident Management says: what operational problem happened and how was it handled?
HSE says: what health, safety, environmental, and compliance consequences exist?
```

Do not merge incidents, leak cases, alarm records, HSE cases, and integrity cases into one generic `Case` table.

### 6.3 Blocker rule

Since `incident management` does not yet have a dedicated DDD file, HSE may reference only neutral incident placeholders:

```text
incidentReferenceId
incidentCodeSnapshot
incidentTitleSnapshot
```

HSE must not create `hidra_incident_*` tables or `dz.sh.hidra.modules.incident*` packages.

---

## 7. Correction: Custody.md

### 7.1 Boundary clarification

Custody owns official accepted transfer records, not raw telemetry and not planning targets.

Correct ownership:

```text
Telemetry says what was measured.
Planning says what was expected.
Custody says what was officially transferred and accepted.
Finance/ERP says what was invoiced or posted.
```

### 7.2 Snapshot rule

Custody must store immutable snapshots of the telemetry/planning/quality evidence used for official transfer decisions.

Allowed:

```text
CustodyMeasurementSnapshot
CustodyMeterRunSnapshot
CustodyQualitySample
CustodyQualityCertificate
CustodyQuantityCalculation
CustodyTransferTicket
```

Forbidden:

```text
Custody owns TelemetryReading
Custody owns OperationalPlan
Custody writes hidra_telemetry_* tables
Custody writes hidra_planning_* tables
Custody creates finance invoice/posting tables
```

---

## 8. Correction: Monitoring.md

### 8.1 Boundary clarification

Monitoring owns deviation detection and operational-state evaluation.

It owns:

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

Monitoring does not own:

```text
formal alarm lifecycle
incident lifecycle
leak-detection case lifecycle
notification delivery
SCADA/PLC/RTU control commands
```

### 8.2 Missing module blocker

`alarm management` and `incident management` are referenced by Monitoring but are blocked until explicit DDD files exist.

Monitoring may produce:

```text
MonitoringAlertCandidate
RiskSignal
DeviationDetectedEvent
```

Monitoring must not create:

```text
hidra_alarm_*
hidra_incident_*
dz.sh.hidra.modules.alarm*
dz.sh.hidra.modules.incident*
```

---

## 9. Correction: LeakDetection.md

### 9.1 Locked table prefix

The leak-detection table prefix is locked as:

```text
hidra_leak_detection_*
```

Do not introduce:

```text
hidra_leakdetection_*
hidra_alarm_*
hidra_incident_*
hidra_monitoring_*
hidra_topology_*
```

### 9.2 Boundary clarification

Leak Detection owns leak suspicion, evidence, localization estimate, verification support, and leak case state.

Leak Detection does not own:

```text
pipeline, facility, segment, node, equipment, or topology graph
telemetry readings
monitoring thresholds
monitoring evaluations
formal alarm lifecycle
incident lifecycle
maintenance work orders
SCADA commands
valve/pump/compressor actuation
```

### 9.3 Escalation rule

Allowed:

```text
LeakDetectionCase
  -> LeakEscalationReference
      -> neutral alarm/incident/workflow/notification reference
```

Forbidden until the missing DDDs exist:

```text
Leak Detection creates alarm tables
Leak Detection creates incident tables
Leak Detection imports alarm or incident domain model
Leak Detection performs OT actuation
```

---

## 10. Correction: Configuration.md

### 10.1 Boundary clarification

Configuration owns governed runtime settings, not module-owned business taxonomies.

Configuration owns:

```text
configuration namespaces
configuration definitions
configuration values
scoped configuration overrides
configuration profiles
feature flags
configuration change requests
configuration deployments
resolved configuration snapshots
```

Configuration must not absorb:

```text
FacilityType
EquipmentType
TelemetryQualityCode
WorkflowDefinition
MonitoringThreshold
AlarmSeverity
IncidentClassification
HseComplianceObligation
CustodyCalculationFormula
AssetMaintenanceStrategy
IdentityPermission
OrganizationHierarchy
```

### 10.2 Rule

If a value has business meaning inside one bounded context, that bounded context owns it.

Configuration may hold:

```text
cross-cutting parameters
runtime toggles
scoped overrides
activation metadata
operator preferences
technical references requiring governance
```

---

## 11. Global coding and documentation correction

### 11.1 Framework-free domain rule

Domain models must not import:

```text
Spring
JPA / Hibernate
Jackson
Swagger / OpenAPI
HTTP / Servlet APIs
```

### 11.2 `@Schema` rule

`@Schema` is forbidden in:

```text
domain.model
domain.value
domain.service
application.dto
application.command
application.query
infrastructure.persistence.entity
infrastructure.persistence.mapper
```

`@Schema` is allowed only in:

```text
api.rest.request
api.rest.response
```

Controllers may use:

```text
@Tag
@Operation
@ApiResponse
@ApiResponses
@Parameter
```

Domain models must use Javadoc for domain documentation.

---

## 12. Final enforcement rule

```text
This correction overlay is part of the DDD contract.
A future code-generation task must read this file together with Hidra_DDD_Consolidated.md and the specific module DDD before creating code.
```
