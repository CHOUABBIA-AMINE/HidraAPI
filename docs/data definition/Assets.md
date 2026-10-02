# HIDRA Assets Module — Data Definition Document

```text
Document code : HIDRA-ASSETS-DDD
Repository    : HidraAPI
Module        : assets
Package root  : dz.sh.hidra.modules.assets
Table prefix  : hidra_asset_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.1
```

---

## 1. Purpose

The `assets` module owns maintainability, asset lifecycle, maintenance strategy, maintenance planning, maintenance work execution, spare-part compatibility, warranty and service-contract references, and asset condition records.

It does not own the physical/logical topology object itself. Physical network identity remains owned by `topology`.

---

## 2. Canonical implementation identity

```text
Module name   : assets
Package root  : dz.sh.hidra.modules.assets
Table prefix  : hidra_asset_*
```

Forbidden table prefixes:

```text
hidra_assets_*
hidra_topology_*
hidra_integrity_*
hidra_hse_*
hidra_custody_*
```

---

## 3. Ownership

Assets owns:

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

Assets does not own:

```text
Facility
Pipeline
PipelineSegment
TopologyNode
TopologyConnection
Equipment as topology identity
PipelineDefect
InspectionFinding
RemainingLifeEstimate
IntegrityRecommendation
HseCase
CustodyTransferTicket
TelemetryReading
WorkflowTask
AuditRecord
```

---

## 4. Topology reference model

Assets references topology through a neutral value object:

```text
AssetTopologyReference
  topologyAssetTypeCode
  topologyAssetId
  topologyAssetCodeSnapshot
  topologyAssetNameSnapshot
```

Assets must not import `topology.domain.model.*` or `topology.infrastructure.persistence.entity.*`.

---

## 5. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| MaintainableAsset | `hidra_asset_maintainable_asset` | Asset maintained by the organization, linked to topology by neutral reference. |
| AssetType | `hidra_asset_type` | Catalog of maintainable asset types. |
| AssetTypeTranslation | `hidra_asset_type_translation` | Multilingual type labels. |
| AssetTechnicalAttributeDefinition | `hidra_asset_technical_attribute_definition` | Technical attribute definition. |
| AssetTechnicalAttributeValue | `hidra_asset_technical_attribute_value` | Technical attribute value. |
| AssetInstallation | `hidra_asset_installation` | Installation and commissioning record. |
| AssetManufacturerReference | `hidra_asset_manufacturer_reference` | Neutral reference to manufacturer/party. |
| AssetModel | `hidra_asset_model` | Asset model reference. |
| AssetSerialIdentity | `hidra_asset_serial_identity` | Serial/manufacturer identity. |
| AssetLifecycleEvent | `hidra_asset_lifecycle_event` | Lifecycle event such as installed, commissioned, retired. |
| MaintenanceStrategy | `hidra_asset_maintenance_strategy` | Maintenance strategy. |
| MaintenancePlan | `hidra_asset_maintenance_plan` | Planned maintenance structure. |
| MaintenanceTaskTemplate | `hidra_asset_maintenance_task_template` | Reusable task template. |
| MaintenanceWorkOrder | `hidra_asset_maintenance_work_order` | Maintenance work order. |
| MaintenanceWorkOrderTask | `hidra_asset_maintenance_work_order_task` | Work-order task. |
| MaintenanceExecutionRecord | `hidra_asset_maintenance_execution_record` | Execution record. |
| SparePart | `hidra_asset_spare_part` | Spare part catalogue. |
| AssetSparePartCompatibility | `hidra_asset_spare_part_compatibility` | Compatibility between asset and spare part. |
| AssetDocumentReference | `hidra_asset_document_reference` | Neutral reference to document metadata. |
| AssetWarranty | `hidra_asset_warranty` | Warranty record. |
| AssetServiceContractReference | `hidra_asset_service_contract_reference` | Neutral service-contract reference. |
| AssetConditionRecord | `hidra_asset_condition_record` | Operational condition record. |
| AssetMeterReadingReference | `hidra_asset_meter_reading_reference` | Reference to meter/telemetry reading. |
| AssetCatalogEntry | `hidra_asset_catalog_entry` | Asset-owned catalog entry. |
| AssetCatalogTranslation | `hidra_asset_catalog_translation` | Multilingual catalog labels. |

---

## 6. Integrity boundary

Integrity may recommend maintenance, but Assets owns maintenance work orders.

Correct flow:

```text
IntegrityRecommendation
  -> application port/event
      -> Asset Management creates MaintenanceWorkOrder
```

Assets must not own engineering defect assessment or remaining-life calculation.

---

## 7. Documentation and annotation rule

Domain, application, and infrastructure asset models must not use `@Schema` or OpenAPI annotations.

`@Schema` is allowed only in assets API request/response models.
