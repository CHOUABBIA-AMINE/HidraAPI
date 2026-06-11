# HIDRA Topology Module — Data Definition Document

```text
Document code : HIDRA-TOPOLOGY-DDD
Repository    : HidraAPI
Module        : topology
Package root  : dz.sh.hidra.modules.topology
Table prefix  : hidra_topology_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.1
```

---

## 1. Purpose

The `topology` module owns the physical and logical hydrocarbon transportation network. It defines where pipeline systems, pipelines, segments, nodes, facilities, equipment, and measurement locations exist, and how they connect.

Topology is the canonical network backbone for visualization, routing, simulation input snapshots, operational references, telemetry anchoring, planning scopes, monitoring scopes, leak localization, integrity assessment, asset management references, HSE location references, and custody points.

---

## 2. Canonical implementation identity

```text
Module name   : topology
Package root  : dz.sh.hidra.modules.topology
Table prefix  : hidra_topology_*
```

Forbidden table prefixes inside topology:

```text
hidra_asset_*
hidra_assets_*
hidra_integrity_*
hidra_hse_*
hidra_custody_*
hidra_telemetry_*
```

---

## 3. Ownership

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
TopologyProjection
```

Topology does not own:

```text
MaintainableAsset
MaintenanceWorkOrder
AssetWarranty
SparePart
InspectionFinding
PipelineDefect
IntegrityCase
HseCase
CustodyTransferTicket
TelemetryReading
TrustedTelemetryReading
WorkflowTask
WorkflowInstance
AuditRecord
```

---

## 4. Boundary rules

Topology owns:

```text
where assets are
how pipeline systems connect
which facilities, nodes, segments, and equipment exist in the network
measurement locations as topology anchors
topology visualization graph inputs
network snapshots used by other modules
```

Topology does not own:

```text
maintainability
maintenance execution
integrity inspection results
HSE consequences
custody transfer acceptance
telemetry measured values
workflow approvals
audit evidence
```

---

## 5. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| PipelineSystem | `hidra_topology_pipeline_system` | Logical transportation system grouping pipelines and facilities. |
| Pipeline | `hidra_topology_pipeline` | Physical/logical pipeline within a system. |
| PipelineSegment | `hidra_topology_pipeline_segment` | Segment between two topology nodes or points. |
| TopologyNode | `hidra_topology_node` | Graph node such as source, junction, station, delivery point, tie-in. |
| TopologyConnection | `hidra_topology_connection` | Directed/undirected graph connection between nodes. |
| Facility | `hidra_topology_facility` | Physical facility such as station, terminal, depot, pump/compressor station. |
| FacilityType | `hidra_topology_facility_type` | Catalog of facility types. |
| FacilityTypeVersion | `hidra_topology_facility_type_version` | Versioned facility-type definition. |
| FacilityAttributeDefinition | `hidra_topology_facility_attribute_definition` | Type-specific facility attribute definition. |
| FacilityAttributeValue | `hidra_topology_facility_attribute_value` | Facility attribute value. |
| PipelineSystemFacility | `hidra_topology_pipeline_system_facility` | Association between system and facility. |
| FacilityNodeBinding | `hidra_topology_facility_node_binding` | Binding between facility and graph node. |
| Equipment | `hidra_topology_equipment` | Equipment as part of the topology/network. |
| EquipmentType | `hidra_topology_equipment_type` | Catalog of equipment types. |
| EquipmentTypeVersion | `hidra_topology_equipment_type_version` | Versioned equipment-type definition. |
| EquipmentAttributeDefinition | `hidra_topology_equipment_attribute_definition` | Type-specific equipment attribute definition. |
| EquipmentAttributeValue | `hidra_topology_equipment_attribute_value` | Equipment attribute value. |
| MeasurementLocation | `hidra_topology_measurement_location` | Topology anchor where telemetry/custody/monitoring measurements are associated. |
| TopologySnapshot | `hidra_topology_snapshot` | Versioned network snapshot. |

---

## 6. Cross-module reference pattern

Other modules must reference topology through neutral values:

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

## 7. Visualization and workflow boundary

Topology may expose graph data for topology visualization.

Topology must not own workflow definitions, workflow instances, workflow tasks, approval decisions, or operator action routing.

Workflow may target topology assets only by neutral topology references.

---

## 8. Documentation and annotation rule

Domain, application, and infrastructure topology models must not use `@Schema` or OpenAPI annotations.

`@Schema` is allowed only in topology API request/response models.
